# JPA 실습 내용 정리

#### [참고 - (개념) 05 JPA](https://github.com/justdoanything/self-study/blob/main/05%20JPA.md)
#### [참고 - (코드) Spring Data JPA](https://github.com/justdoanything/self-study/tree/main/Spring%20Data%20JPA) 

## Java Statement

- Try with resources

  ```java
  try(Resource resource = new Resources()){
    ...
  }catch(Excption e){
    ...
  }
  ```

- Method Reference
  ```java
  ...
  posts.forEach(System.out::print);
  ```
- JPA Annotation
  - @Lob : 255자가 넘을때
---

## postgres 설치 및 접속

- docker run -p 5432:5432 -e POSTGRES_PASSWORD=yongwoo -e POSTGRES_USER=yongwoo -e POSTGRES_DB=springdata --name postgres_boot -d postgres
- docker exec -it postgres_boot bash
- psql -U yongwoo springdata

---

## Quick Guide
- ###### 차례대로 정리한 내용은 [Spring Data JPA](https://github.com/justdoanything/self-study/tree/main/Spring%20Data%20JPA)를 참조하면 됩니다.
- ###### Entity를 API 결과값으로 반환하는 것은 매우 위험하기 때문에 별도의 DTO를 만들어서 사용해야 한다.
  ```java
  Page<MemberDto> dtoPage = page.map(member -> new MemberDto(member.getId(), membergetName(), member.getCity()));
  ```
- ###### N + 1 문제
  - FetchType.LAZY로 되어 있으면 Member를 조회할 때 Team 객체를 바로 조회하지 않고 Proxy 객체를 조회한다.
  - 실제로 getTeam()을 했을 때 Team 객체를 조회하는데 이 때 teamA, teamB를 따로 조회하게 된다. 아래 예제에선 조회 쿼리는 3번 날아가게 된다. => member 조회 쿼리, teamA 조회 쿼리, teamB 조회 쿼리
    ```java
    List<Member> members = memberRepository.findAll();
    members.forEach((m) -> {
      System.out.println("==> member name = " + m.getName());
      System.out.println("==> member age = " + m.getAge());
      System.out.println("==> member team = " + m.getTeam().getName());
    });
    ```
  - fetch join을 사용하면 member를 조회할 때 left join을 사용해서 team 정보까지 한 번에 가져오기 때문에 쿼리는 1번만 날아가게 된다.
    ```java
    // @Query("select m from Member m left join fetch m.team")
    List<Member> members = memberRepository.findMemberFetchJoin();
    members.forEach((m) -> {
      System.out.println("==> member name = " + m.getName());
      System.out.println("==> member age = " + m.getAge());
      System.out.println("==> member team = " + m.getTeam().getName());
    });
    ```
- ###### CustomerRepository를 구현할 때 Naming 규칙을 지켜줘야 한다. 
  - 지키지 않으면 Failed to load ApplicationContext 에러가 발생한다.
  - Naming 규칙 : `Repository Interface Name` + `Impl` 혹은 `Custom Interface Name` + `Impl`
  - `interface: CustomRepository` -> `class: CustomRepositoryImpl` -> `class: MemberRepository`
  - Naming 규칙을 변경하는 방법
    - `@EnableJpaRepositories(basePackages = "prj.jpa.kyh.repository", repositoryImplementationPostfix = "Impl")`

- ###### Auditing
  - BaseEntity.java, BaseTimeEntity.java 참고
  - SpringApplication.java
    ```java
    @EnableJpaAuditing
    ...
    public class JpaApplication {
      ...
      @Bean
      public AuditorAware<String> auditorProvider() {
        return () -> Optional.of(UUID.randomUUID().toString());
      }
    }
    ```
- ###### Web 확장
  - `prj.jpa.kyh.controller.MemberController.java` 참고

- ###### @Qualifier
  ```java
  // /members?member_page=0&order_page=1
  public String list (
    @Qualifier("member") Pageable memberPageable,
    @Qualifier("order") Pageable orderPageable,
  )
  ```

- ###### 새로운 Entity 기준 정의
  ```java
  @Entity
  @EntityListener(AuditingEntityListener.class)
  @Data
  public class Item implements Persistable<String> {
    @Id
    private String id;

    @CreatedDate
    private LocalDateTime createdDate;

    @Override
    public String getId() {
      return id;
    }

    @Override
    public boolean isNew() {
      return createdDate == null;
    }
  }
  ```

- ###### Projections
  - 전체 Entity가 아니고 DTO로 조회하는 방법
  ```java
  // Interface 방식
  public interface MemberProjectionNameOnlyInterface {
    // select 절에 name 필드만 존재
    String getName();

    // select 절에서 전체 필드를 조회하고 결과를 조합
    @Value("#{target.name + ' ' + target.age}")
    String getNameAndAge();
  }

  // ----------------------------------------

  // Class 방식
  public class MemberProjectionNameOnlyClass {
    private final String name;

    public MemberProjectionNameOnlyClass(String name) {
      this.name = name;
    }
  }

  // ----------------------------------------

  List<MemberNameOnly> findProjectionsByName(@Param("name") String name);
  ```

- ###### Native Query
  - 제약사항
    - Sort 파라미터를 통한 정렬이 정상 동작하지 않을 수 있음
    - JPQL처럼 Application Loading 시점에 문법 오류 확인 불가능
    - 동적 쿼리 불가능 
    - Native SQL을 DTO로 조회할땐 JdbcTemplate, myBatis 추천

- ###### 메모
  - JPA만 독단적으로 사용하지 않고 JdbcTemplate, myBatis, jooq와 같이 사용한다. hibernate를 사용하는 방법도 익혀두는게 좋을 것 같다.
    ```java
    // hibernate 사용
    String sql = "select m.name as name from member m";

    List<MemberDto> result = em.createNativeQuery(sql)
                                    .setFirstResult(0)
                                    .setMaxResults(10)
                                    .unwrap(NativeQuery.class)
                                    .addScalar("name")
                                    .setResultTransformer(Transformers.aliasToBean(MemberDto.class))
                                    .getResultList();
    ```
---

## @Value

- @Entity 안에 한 Column으로 Class를 갖기 위해선 @Embeddable, @Embedded 을 사용해준다.

  ```java
  // Column이 될 Class
  @Embeddable
  public class Address { ... }

  // Address Class를 Column으로 사용
  ...
  @Id @GeneratedValue
  private Long id;
  private String username;
  private String password;

  @Embedded
  @AttributeOverrides({
      @AttributeOverride(name="street", column = @Column(name="homeStreet")),
      @AttributeOverride(name="city", column = @Column(name="homeCity")),
      @AttributeOverride(name="state", column = @Column(name="homeState")),
      @AttributeOverride(name="zipCode", column = @Column(name="homeZipCode"))

  })
  private Address homeAddress;

  @Embedded
  @AttributeOverrides({
      @AttributeOverride(name="street", column = @Column(name="officeStreet")),
      @AttributeOverride(name="city", column = @Column(name="officeCity")),
      @AttributeOverride(name="state", column = @Column(name="officeState")),
      @AttributeOverride(name="zipCode", column = @Column(name="officeZipCode"))

  })
  private Address officeAddrss;
  ```

- @OneToMany, @ManyToOne

  ```java
  // @ManyToOne
  public class Study {
    ...
    @ManyToOne
    private Account owner;
  }

  // @OnyToMany : join 된 테이블이 생성됨
  // mappedBy를 넣어주지 않으면 단방향 관계가 2개만 생길뿐 양방향 관계라는 것을 인지하지 못함.
  public class Account {
    ...
    @OneToMany(mappedBy = "owner")
    private Set<Study> studies = new HashSet<>();
  }


  public class JpaRunner {
    // 🌟🌟 관계에 대한 설정을 반드시 헤줘야함. 🌟🌟
    // account.getStudies().add(study);
    // study.setOwner(account);
    account.addStudy(study);
  }
  ```

---

## Entity 상태 전파

- Cascade
  ```java
  /*
   * Post와 Comment의 양방향 관계를 맺고
   * session.save(post); 만 해주게 되면 Comment엔 데이터가 저장되지 않는다.
   *
   * casecade = CasecadeType.PRESIST 옵션을 주면 Post가 저장될 때 상태를 전파한다.
   * casdcade = CasecadeType.REMOVE 옵션을 주면 Post가 삭제될 때 상태를 전파한다.
   *
   * Database의 casecade 옵션을 주는 것과 같다.
   */
   public class Post {
    @OneToMany(mappedBy = "post", cascade = { CascadeType.PERSIST, CasecaseType.REMOVE})
    private Set<Comment> comments = new HashSet<>();
   }
  ```

---

## Fetch : 데이터를 가져오는 시점

```java
/*
 * @ManyToOne의 기본값은 EAGER : 연관있는 데이터를 모두 select
 * @OneToMany의 기본값은 LAZY : 실제 데이터에 대해서 select이 발생했을 때 query를 실행
 */

...
@ManyToOne(fetch = FatchType.EAGER)
private Account owner;

...
@OneToMany(mappedBy = "post", cascade = {CascadeType.PERSIST, CascadeType.REMOVE}, fetch = FetchType.LAZY)
private Set<Comment> comments = new HashSet<>();
```

---

## Query를 직접 사용하는 방법

- JPQL
  ```java
  CriteriaBuilder builder = entityManager.getCriteriaBuilder();
  CriteriaQuery<Post> criteriaQuery = builder.createQuery(Post.class);
  Root<Post> criteriaRoot = criteriaQuery.from(Post.class);
  criteriaQuery.select(criteriaRoot);
  ```
- Type Safe한 방법 : 실제 query를 명시하지 않음
  ```java
  List<Post> criteriaPost = entityManager.createQuery(criteriaQuery).getResultList();
  System.out.println("=====Criteria=====");
  criteriaPost.forEach(System.out::println);
  ```
- Native Query
  ```java
  List<Post> nativePosts = entityManager.createNativeQuery("select * from Post AS p", Post.class).getResultList();
  System.out.println("=====Native=====");
  nativePosts.forEach(System.out::println);
  ```

---

## Spring JPA 사용

- JpaRepository 상속받아서 사용
- 기본으로 제공하는 함수 기반으로 Customize 함수 사용 가능

  ```java
  public interface PostRepository extends JpaRepository<Post, Long> {
    // Customize Function 사용 가능
    Page<Post> findByTitleContains(String title, PageRequest pageRequest);
    long countByTitleContains(String title);
  }

  // PostRepositoryTest.java
  ```

- 기본으로 제공하는 함수 전체 중에서 일부만 사용하고 싶을 때

  ```java
  // 사용할 함수만 담고 있는 Interface
  @NoRepositoryBean
  public interface CustomRepository<T, Id extends Serializable> extends Repository<T, Id> {
    <E extends T> E save(E entity);
    List<T> findAll();
    long count();
  }

  // 위 함수를 상속받아 Comment 객체를 사용하는 Interface
  public interface CommentRepository extends CustomRepository<Comment, Long> {
    // save(Comment comment);
    // fineAll();
    // count();
  }

  // CommentRepositoryTest.java
  ```

---

## Null 체크

- Optional 함수 사용

```java
Optional<Comment> byId = commentRepository.findById(100l);
Assertions.assertThat(byId).isEmpty();
Comment comment = byId.orElseThrow(IllegalArgumentException::new);
```

- Spring Annotation 사용
  - @NonNull, @Nullable

```java
<E extends T> E save(@NonNull E entity);

@Nullable
<E extends T> Optional<E> findById(@Nullable Id id);
```

---

## Query 선언

- 함수명으로 정의
  ```java
  List<Comment> findByTitleContains(String title);
  List<Comment> findByTitleContainsIgnoreCase(String title);
  ```
  - 함수명 규칙과 리턴 타입
    - find, get, query, count, ...
    - distinct, first, top, ...
    - ignoreCase, between, lessThan, greaterThan, like, ...
    - orderByAsc, orderByDesc, ...
    - E, Optional, List<E>, Page<E>, Slice<E>, Stream<E>, ...
    - Pageable, Sort, ...
- @Query 사용
  - 기본값은 `JPQL`
    ```java
    @Query("SELECT c FROM Comment AS c")
    List<Comment> findByComment();
    ```
  - `SQL`을 사용하려면 `nativeQuery` 옵션 필요
    ```java
    @Query(value = "SELECT * FROM Comment AS c", nativeQuery = true)
    List<Comment> findByComment();
    ```
- Query를 생성하는 전략을 지정할 수 있다 : `queryLookupStrategy`
  - CREATE : 함수 이름을 분석해서 쿼리 생성
  - CREATE_IF_NOT_FOUND (기본값) : 미리 정의해 둔 쿼리를 찾고 없으면 함수명으로 쿼리 생성
  - USE_DECLARED_QUERY : 미리 정의해 둔 쿼리를 찾아서 사용

---

## 비동기로 Query 실행

- `@SpringBootApplication`에 `@EnableAsync` 추가
- Repository 안에 있는 함수에 `@Async` 추가
- Future Class 사용

  - Future
  - CompletableFuture
  - ListenableFuture : CallBack 함수 사용할 수 있음

  ```java
  @Async
  Future<List<Comment>> findByCommentContains(String keyword);

  @Async
  ListenableFuture<List<Comment>> findByComment(String keyword);

  ListenableFuture<List<Comment>> listenFuture = commentRepository.findByComment("테스트 코멘트1");
  listenFuture.addCallback(new ListenableFutureCallback<List<Comment>>() {
      @Override
      public void onFailure(Throwable ex){
          System.out.println(ex);
      }
      @Override
      public void onSuccess(@Nullable List<Comment> result) {
          System.out.println("============");
          result.forEach(System.out::println);
      }
  });
  ```

- 비동기 방식을 추천하지 않는 이유

  - JPA는 실제로 데이터를 사용하지 않는 경우 Database에 영향을 미치지 않고 모두 Context에만 갖다.
  - 비동기 방식으로 처리할 경우, Main Thread에서 insert해도 실제 Database에 insert를 하지 않고 있기 때문에 다른 Thread(Select 하는 함수)에서 데이터가 검색되지 않는다.
  - 각 Thread는 서로가 어떤 작업을 하고 있는지 모르기 때문이다.
  - CommonRepositoryTest.java 참고

- JPA에서 remove()를 하기 전에 merge()를 하는 이유
  - merge()는 Detached 상태를 Persistence(managed) 상태로 되돌리는 것인데 remove를 하기 전에 merge()를 통해서 loading을 하고 remove 상태로 바꾼다. 굳이 loading을 하는 이유는 Entity Manager가 casecade 제약조건으로 엮여 있는 데이터를 고려하거나 여러 부분들을 참고해서 동작하기 때문이다.
  - remove()를 해도 바로 Database에서 delete를 하는게 아니고 removed 상태로 변경하는 것이다.

## Custom Repository
- Custom Repository 예제코드
  ```java
  public interface PostRepository extends JpaRepositor<Post, Long>, PostCustomRepository<Post> {

  }
  
  // ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ
  
  public interface PostCustomRepository {
    List<Post> findByPost();
  }

  // ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ

  public class PostCustomRepositoryImpl implements PostCustomRepository {
    @Autowired
    EntityManager entityManager;

    @Override
    public List<Post> findByPost() {
      return entityManager.createQuery("SELECT p FROM Post AS p", Post.class).getResultList();
    }
  }
  ```
- 모든 Repository에 추가할 공통을 만드는 예제코드
  ```java
  // 중간에 사용되는 Repository는 @NoRepositoryBean 이 필요
  @NoRepositoryBean
  public interface MyRepository<T, ID extends Serializable> extends JpaRepository<T, ID> {
    boolean contains(T entity);
  }

  // ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ

  public class SimpleMyRepository<T, ID extends Serializable> extends SimpleJpaRepository<T, ID> implements MyRepository<T, ID> {
    
    private EntityManager entityManager;

    // 생성자 필요
    public SimpleMyRepository(JpaEntityInformation<T, ?> entityInformation, EntityManager entityManager) {
      supuer(entityInformation, entityManager);
      this.entityManager = entityManager;
    }

    @Override
    public boolean contains(T entity) {
      return entityManager.contains(entity);
    }
  }

  // ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ

  // SpringApplication 있는 클래스에 repositoryBaseClass 지정
  @SpringBootApplication
  @EnableJpaRepositories(repositoryBaseClass = SimpleRepository.class)
  public class Application {
    public static void main(String[] args) { SpringApplication.run(); }
  }

  // ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ

  // 정의한 Repository 사용
  public interface PostRepository extends MyRepository<Post, Long> {

  }
  ```

## Damain Event
- ApplicationContext은 EventPublisher를 상속 받고 있다.
- Event를 Publishing 하는 예제코드
  ```java

  public class PostPublishedEvent extends ApplicationEvent {
    
    private final Post post;
    
    public PostPublishedEvent(Object source) {
      super(source);
      this.post = source;
    }

    public Post getPost() {
      return post;
    }
  }

  /*ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ*/

  public class PostListener implements ApplicationListener<PostPublishedEvent> {

    @Override
    public void onApplicationEvent(PostPublishedEvent event) {
      System.out.println(event.getPost().getTitle() + " is published");
    }
  }

  /*ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ*/

  @Configuration
  public class PostRepositoryConfig {
    
    // Bean으로 등록해주기 위한 코드
    @Bean
    public PostListener postListener() {
      return new PostListener();
    }
  }

  /*ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ*/

  // Bean으로 등록한 객체를 사용하기 위한 코드
  @Import(PostRepositoryConfig.class)
  public class PostRepository {

    @Autowired
    ApplicationContext applicationContext;
    
    @Test
    public void event() {
      Post post = new Post();
      post.setTitle("event");
      PostPublishedEvent event = new PostPublishedEvent(post);
      
      applicationContext.publishedEvent(event);
    }
  }
  ```
- save()를 하면 Aggregate에 쌓여있던 이벤트를 모두 발생시킨다.
  ```java

  @Entity
  public class Post extends AbstractAggregateRoot<Post> {
    ...

    @Test
    public void curd() {
      Post post = new Post();
      post.setTitle("event");

      postRepository.save(post.publish());
    }

    public Post publish() {
      this.registerEvent(new PostPublishedEvent(this));
      return this;
    }    
  }

  /*ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ*/

  public class PostListener {

    // ApplicationListener를 인터페이스 받지 않고 구현하는 방법
    @EventListener
    public void onApplicationEvent(PostPublishedEvent event) {
      System.out.println(event.getPost().getTitle() + " is published");
    }
  }

  /*ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ*/

  @Configuration
  public class PostRepositoryConfig {
    
    // 별도의 class를 만들지 않고 처리하는 방법
    @Bean
    public ApplicationListener<PostPublishedEvent> postListener() {
      return new ApplicationListener<PostPublishedEvent>() {
        @Override
        public void onApplicationEvent(PostPublishedEvent event) {
          System.out.println(event.getPost().getTitle() + " is published");
        }
      }
    }
  }
  ```

## QueryDSL 연동
- QueryDSL (http://www.querydsl.com/)
- 조건문을 표현하는 방법이 type safe 하고 조건을 조합하거나 모아서 관리할 수 있다.
- 기존에는 조건이 많아질수록 `findByFirstNameIngoreCaseAndLastNameStartsWithIgnoreCase(String firstName, String lastName)`와 같은 메서드를 계속 만들어야 하는 단점이 있었다.
- 주로 `Optional<T> findOne(Predicate)`, `List<T>|Page<T>|.. findAll(Predicate)` 를 많이 쓴다.
- QuerydslPredicateExecutor 인터페이스를 사용하면 조건이 있는 쿼리를 짜기 쉽다.
- 예제코드
  ```java
  @Entity
  public class Account {
    @Id @GeneratedValue
    private Long id;

    private String username;
  }

  /*ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ*/

  public class AccountRepository extends JpaRepository<Account, Long>, QuerydslPredicateExecutor<Account> {

  }

  /*ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ*/

  public class AccountRepositoryTest {
    @Autowired
    AccountRepository accountRepository;

    @Test
    public void crud() {
      Predicate predicate = QAccount.account
                              .firstnName.containsIgnoreCase("yongwoo")
                              .and(account.lastName.startWith("lee"));
      Optional<Account> one = accountRepository.findOne(predicate);
    }
  }
  ```
- 의존성 추가
  ```xml
  <dependency>
      <groupId>com.querydsl</groupId>
      <artifactId>querydsl-apt</artifactId>
  </dependency>
  <dependency>
      <groupId>com.querydsl</groupId>
      <artifactId>querydsl-jpa</artifactId>
  </dependency>

  <plugin>
      <groupId>com.mysema.maven</groupId>
      <artifactId>apt-maven-plugin</artifactId>
      <version>1.1.3</version>
      <executions>
          <execution>
              <goals>
                  <goal>process</goal>
              </goals>
              <configuration>
                  <outputDirectory>target/generated-sources/java</outputDirectory>
                  <processor>com.querydsl.apt.jpa.JPAAnnotationProcessor</processor>
              </configuration>
          </execution>
      </executions>
  </plugin>
  ```
- maven compile을 실행하면 지정한 경로에 쿼리 랭귀지를 바로 만들어줌.
- Repository 클래스에 QuerydslPredicateExecutor 추가해주고 Predicate 만들어서 사용하면 된다,


## 웹 기능
- DomainClassConverter
  ```java
  // 일반
  @GetMapping("/posts/{id}")
  public String getPost(@PathVariable Long id) {
    Optional<Post> byId = postRepository.findById(id);
    Post post = byId.get();
    return post.getTitle;
  }

  /*ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ*/

  // domain converter (Long -> Post)
  @GetMapping("/posts/{id}")
  public String getPost(@PathVariable("id") Post post) {
    return post.getTitle;
  }
  ```

- Pageable, Sort
  ```java
  @GetMapping("/posts/{id}")
  public String getPost(@PathVariable("id") Post post) {
    return post.getTitle;
  }

  @GetMapping("/posts/")
  public Page<Post> getPosts(Pageable pageable) {
    return postRepository.findAll(pageable);
  }

  /*ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ*/

  // 테스트 코드
  @Test
  public void getPosts() {
    Post post = new Post();
    post.setTitle("jpa");
    postRepository.save(post);

    mockMvc.perform(get("posts/")
                      .param("page", "0")
                      .param("size", "10")
                      ,param("sort", "created, desc")
                      ,param("sort", "title"))
            .andDo(print())
            .andExpect(status().isOk())
            .andExpect(jsonPath("$.content[0].title", is("jpa")));
  }

  ```

- HATEOAS
  ```java
  @GetMapping
  public pagedResources<Resource<Post>> getPosts(Pageable pageable, PagedResourcesAssembler<Post> assembler) {
    return assembler.toResource(post.findAll(pageable));
  }
  ```
  - 응답에 "_links" 필드를 보면 페이지 URL이 나온다.
- ~~Payload Projection (비추천)~~
  - 요청으로 들어오는 데이터 중 일부만 바인딩하기
  - @ProjectedPaylaod, @XBRead, @JsonPath
- ~~QuerySQL을 Predicate로 받아서 사용하기 (비추천)~~


## Entity 저장하기
- @Repository를 명시해주면 SQLException, JPA 관련 Exception을 Spring의 DataAccessException으로 변환해준다.
- save()는 객체가 기존에 존재하는지 아닌지 판단하고 persist()를 하거나 merge()를 한다.
- Transient : 새로 만들어진 객체로 hibernate나 database에 매핑이 되지 않은 상태. 고유한 id를 갖고 있지 않고 database에 record가 없는 상태.
- Persistent : PersistContext가 캐싱하고 있는 상태. 관리하고 있는 상태.
- Detached : 한 번 이라도 database에 Persistent가 됐던 개체
- JPA가 Transient인지 Detached인지 판단하는 기준
  - Entity의 @id 값을 찾고 null 이면 Transient 상태, @id가 존재하면 Detached 상태로 판단한다.
    ```java
    public void curd() {
      Post post new Post();
      post.setTitle("jpa");
      Post savedPost = postRepository.save(post);  // persist, insert

      /**
       * save()를 했을 때 persist 상태가 되면서 insert를 하는데
       * save()가 반환하는 값은 기존에 생성한 값(post)과 완전하게 동일하다.
       * post와 savedPost는 같은 값이다.
       */
      assertThat(entityManager.contains(post)).isTrue();
      assertThat(entityManager.contains(savedPost)).isTrue();
      assertThat(savedPost == post);



      Post postUpdate = new Post();
      postUpdate.setId(post.getId());
      postUpdate.setTitle("hibernate");
      Post updatedPost = postRepository.save(postUpdate);  // merge, update

      /**
       * 하지만 merge()가 일어나면 기존에 객체(postUpdate)의 복사본을 만들고
       * 복사본(updatedPost)을 Persist로 만들고 database에 작업하고 return 해주기 때문에 
       * postUpdate와 updatedPost는 다른 값이다.
       */
      assertThat(entityManager.contains(updatedPost)).isTrue(); // 복사된 객체는 영속화되어 있다.
      assertThat(entityManager.contains(postUpdate)).isfalse(); // 기존 객체는 영속화가 되지 않는다.
      assertThat(updatedPost != postUpdate);

      /**
       * 따라서 무조건 return 받은 객체를 사용하는게 안전하다.
       */
    }    

    ```

## JPA Query Method
- Keywords
  - https://docs.spring.io/spring-data/jpa/docs/current/reference/html/#jpa.query-methods.query-creation
  - And, Or, Is, Equals, LessThan, LessThanEqual, GreaterThan, GreaterThanEqual
  - After, Before, IsNull, IsNotNull, NotNull, Like, NotLike
  - StartingWith, EndingWith, COntaining, OrderBy
  - Not, In, NotIn, True, False, IgnoreCase

- @NamedQuery, @NamedNativeQuery
  - Class 위에 @Entity 레벨에 적는 Annotation
    ```java
    @Entity
    @NamedQuery(name = "Post.findBytitle", query = "SELECT p FROM post AS p WHERE p.title = ?1")
    public class Post { ... }
    ```

- @Query
  - Class가 아니라 Method 레벨에 적는 Annotation
    ```java
    @Entity
    public class Post{
      @Query("SELECT p FROM post AS p WHERE p.title = ?1")
      List<Post> findByTitle(String title);
    }
    ```

## Sort
- Pageable이나 Sort를 매개변수로 사용할 수 있는데 @Query와 같이 사용할 땐 제약사항이 존재한다. Order by 절에서 함수를 호출하면 Sort를 사용하지 못한다.
- JpaSort.unsafe()를 사용해야 합니다.
- Sort는 property 또는 alias가 entity에 없는 경우에 예외가 발생한다.
  ```java
  @Entity
  public class Post{
    @Query("SELECT p FROM post AS p WHERE p.title = ?1")
    List<Post> findByTitle(String title, Sort sort);
  }

  postRepository.findByTitle("jpa", Sort.by("title")); // property 니까 가능
  postRepository.findByTitle("jpa", Sort.by("LENGTH(title)"));  // 함수라서 불가능
  postRepository.findByTitle("jpa", JpaSort.unsafe("LENGTH(title)"));  // unsafe(로 사용 가능
  ```

## NamedParameter
```java
public interface PostRepository extends JpaRepository<Post, Long> {
  @Query("SELECT p FROM Post AS p WHERE p.title = :title")
  List<Post> findBytitle(@Param("title") String keyword, Sort sort);

  @Query("SELECT m FROM Member m WHERE m.username = :username and m.age = :age")
  List<Member> findUser(@Param("username") String username, @Param("age") int age);

  @Query("select m from Member m where m.name in :names")
    List<Member> findByNames(@Param("names") Collection<String> names);
}
```

## DTO 객체로 조회하기
```java
@Query("select new study.datajpa.dto.MemberDto(m.id, m.username, t.name) from Member m join m.team t")
List<MemberDto> findMemberDto();
```

## Parameter Binding
```java
// 위치 기반 - 비추천!!
@Query("UPDATE Post p SET p.title = ?1 WHERE p.id = ?2")
int updateTitle(String title, Long id);

// 이름 기반
@Query("SELECT m FROM Member m WHERE m.username = :username and m.age = :age")
List<Member> findUser(@Param("username") String username, @Param("age") int age);

// 이름 기반
@Query("SELECT m FROM Member m WHERE m.username in :names")
List<Member> findByNames(@Param("names") Collection<String> names);
```
## SpEL (스프링 표현 언어)
  - https://docs.spring.io/spring/docs/current/spring-framework-reference/core.html#expressions
  ```java
  public interface PostRepository extends JpaRepository<Post, Long> {
    @Query("SELECT p FROM #(#entityName) AS p WHERE p.title = :title")
    List<Post> findBytitle(@Param("title") String keyword, Sort sort);
  }
  ```

## Update Query
- 객체의 상태에 따라서 JPA가 자동으로 update를 해주기 때문에 우리가 임의로 해줄 필요가 없었다.
- Update Query를 임의로 정의해서 사용하는 방법
  ```java
  public interface PostRepository extends JpaRepository<Post, Long> {
    @Modifying
    @Query("UPDATE Post p SET p.title = ?1 WHERE p.id = ?2")
    int updateTitle(String title, Long id);
  }
  ```
- 위 방법을 추천하지 않는 이유는 update를 해서 실제 database에는 update가 수행됐지만 사용한 객체는 persist 상태여서 update 후에 select을 했을 때 database의 값이 아니라 cache에 있는 값을 가져온다. 즉, update는 cache에 있는 값이 아니라 database에 있는 값만 바꾸기 때문에 select이 발생했을 때 데이터의 일관성이 깨질 수 있다.
- @Modifying(clearAutomatically = true, flushAutomatically = true) 를 cache를 clear 하거나 flush를 사용해서 처리할 수는 있다.

## EntityGraph
- fetch 기능을 유연하게 적용시킬 수 있는 방법
- `@ManyToOne`은 `FetchType.EAGER` 가 기본값이기 때문에 Comment를 가져올 때 Post를 left join으로 같이 가져오게 된다. `FetchType.LAZY`로 설정하면 Comment만 가져온다.
- `EntityGraph`는 `attributeNodes`로 설정한 속성은 `EAGER`, 나머지는 `LAZY`를 적용한다.
- 아래 예제코드에서 post는 `LAZY`로 설정했지만 `findById` 함수를 이용하면 `EntityGraph` 설정에 따라 `EAGER`로 데이터를 가져오게 된다.
- EntityGraph 적용 코드
  ```java
  @Entity
  public class Comment { 
    ...
    @ManyToOne(fetch = FetchType.LAZY)
    private Post post;
    ...
  }

  /*ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ*/

  public interface CommentRepository extends JpaRepository<Comment, Long> {
    @EntityGraph(attributePaths = {"post"})
    Optional<Comment> findById(Long id);
    // post를 EAGER 로 가져온다.
  }
  ```
## Projection
- SELECT를 할 때 전체(*)가 아닌 특정 필드만 가져오도록 명시하는 방법
- Interface 기반 Projection
  - Nested Projection
  - Closed Projection
    - 가져오려고 하는 attritubte를 알고 있기 때문에 Query를 최적화할 수 있다. 
    - Java 8의 Default Method를 사용해서 연산할 수 있다.
      ```java
      public interface CommentSummary {
        String getComment();
        int getUp();
        int getDown();

        default String getVotes(){
          return getUp() + " " + getDown();
        }
      }

      /*ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ*/

      public interface CommentRepositroy extends JpaRepository<Comment, Long> {
        List<CommentSummary> findByPost_Id(Long id);
        // comment, up, down 필드만 select 함
      }
      ```
  - Open Projection
    - @Value(SpEL)를 사용해서 연산할 수 있다.
    - Spring Bean의 Method도 호출 가능하다.
    - SpEL을 Entity 대상으로 사용하기 때문에 Query 최적화는 할 수 없다. 
      ```java
      public interface CommentSummary {
        String getComment();
        int getUp();
        int getDown();

        @Value("#{target.up + ' ' + target.down}")
        String getVotes();
        // 전체를 다 select 함. 대신 문자열을 만들어서 가져올 수 있음.
      }

      /*ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ*/

      public interface CommentRepositroy extends JpaRepository<Comment, Long> {
        List<CommentSummary> findByPost_Id(Long id);
      }
      ```
- Class 기반 Projection
  - 선언 방식은 interface와 동일하지만 생성자와 getter, setter를 만들어주고 정의를 다 해줘야하기 때문에 코드가 길어지는 단점이 있다.
- Dynamic Projection
  - Projection용 함수 하나만 정의하고 실제 Projection 필드는 타입 인자로 전달받기
    ```java
    public interface CommentRepositroy extends JpaRepository<Comment, Long> {
      // Dynamic Projection
      <T> List<T> findByPost_Id(Long id, Class<T> type);
    }
    
    /*ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ*/
    
    commentRepository.findByPost_Id(post.getId(), CommentSummary.class).forEach( c -> {
      System.out.println(c.getVotes());
    });
    ```

## Specifications
- 에릭 에반스의 책 DDD에서 언급하는 Specification 개념을 차용한 것으로 QueryDSL의 Predicate와 비슷하다.
- 사전준비
  - 의존성 추가
    ```xml
    <dependency>
      <groupId>org.hibernate</groupId>
      <artifactId>hibernate-jpamodelgen</artifactId>
    </dependency>
    ```
  - 플러그인 설정
    ```xml
    <plugin>
      <groupId>org.bsc.maven</groupId>
      <artifactId>maven-processor-plugin</artifactId>
      <version>2.0.5</version>
      <executions>
          <execution>
              <id>process</id>
              <goals>
                  <goal>process</goal>
              </goals>
              <phase>generate-sources</phase>
              <configuration>
                  <processors>
                      <processor>org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor</processor>
                  </processors>
              </configuration>
          </execution>
      </executions>
      <dependencies>
          <dependency>
              <groupId>org.hibernate</groupId>
              <artifactId>hibernate-jpamodelgen</artifactId>
              <version>${hibernate.version}</version>
          </dependency>
      </dependencies>
    </plugin>
    ```
  - IDE에 Annotaion 설정 추가
    - org.hibernate.jpamodelgen.JPAMetaModelEntityProcessor
- 예제 코드
  ```java
  public class CommentSpecs {
    public static Specification<Comment> isBest() {
      return (Specification<Comment>)
        (root, query, builder) ->
          builder.isTrue(root.get(Comment_.best));
    }

    public static Specification<Comment> isGood() {
      return (Specification<Comment>)
        (root, query, builder) -> 
          builder.greaterThanOrEqualTo(root.get(Comment_.up, 10));
    }
  }

  /*ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ*/

  @Test
  public void specs() {
    commentRepository.findAll(CommentSpecs.isBest());
    Page<Comment> page = commentRepository.findAll(isBest().or(isGood()), PageRequest.of(0, 10));
  }
  ```

## Transaction
- Spring Data JPA가 제공하는 Repository의 모든 메소드에는 기본적으로 @Transaction이 적용되어 있다.
- RuntimeException, Error가 발생하면 Transaction을 Rollback 시킨다.
- Class, Interface, Method 에 사용할 수 있으며, 메소드에 가장 가까운 애노테이션이 우선 순위가 높다.
- Transaction 옵션
  - `default` : database의 기본값을 따름. 
  - `read_committed` : 한 트랜잭션이 끝나기 전에 다른 트랜잭션이 그 데이터에 접근할 수 없음. _dirty read_ 를 방지하고 _non-repeatable read_, _phantom read_ 는 발생할 수 있다.
  - `read_uncommitted`
  - `repeatable_read` : _dirty read_, _non-repeatable read_ 를 방지하고 _phantom read_ 는 발생할 수 있다.
  - `serializable` : 3가지 read를 모두 막는다. database에 접근할 수 있는 트랜잭션이 1개만 가능하기 때문에 성능이 좋지 않다.
- JPA 구현체로 Hibernate를 사용할 때 트랜잭션을 readOnly를 사용하고 Flush 모드를 NEVER로 설정하여, Dirty checking을 하지 않도록 한다.
  - flush는 commit이나 데이터를 가쟈오기 전에 수행하는데 flush 모드 설정을 통해서 데이터베이스에 Sync를 하는 타이밍을 설정할 수 있다.


## Auditing
- Entity의 변경 시점에 언제, 누가 변경했는지에 대한 정보를 기록하는 기능.
- `@SpringBootApplication` 아래에 `@EnableJpaAuditing` 추가
- Auditing 기능을 사용할 Entity에 `@EntityListeners(AuditingEntityListener.class)` 추가
  ```java
  @Entity
  @EntityListeners
  public class Comment {
    ...

    @CreatedDate
    private Date created;

    @LastModifiedDate
    private Date updated;

    @CreatedBy
    @ManyToOne
    private Account createdBy;

    @LastModifiedBy
    @ManyToOne
    private Account updatedBy;
  }
  ```

- Account 객체는 Spring이 모르기 때문에 Bean을 `@EnableJpaAuditing`에 설정하고 현재 User는 Spring Security를 사용해서 알아낼 수 있다.
  ```java
  @Entity
  @EnableJpaAuditing(auditorAwareRef="accountAuditAware")
  // AccountAuditAware의 service bean 이름은 accountAuditAware
  public class Comment {
    ...
  }
  
  /*ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ*/
  
  @Service
  public class AccountAuditAware implements AuditorAware<Account> {
    @Override
    public Optional<Account> getCurrentAudior(){
      Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

      if(authentication == null || !authentication.isAuthenticated()){
        return null;
      }
      return ((Account)authentication.getPrincipal()).getUser();
    }
  }
  ```

- JPA Callback Life Cycle Event를 활용하는 방법
  - 객체가 생성, 삭제, 업데이트 전/후로 callBack 이벤트를 설정할 수 있다.
  - @PreRemove, PostPersist, PostRemove, PreUpdate, PostUpdate, PostLoad
  - https://docs.jboss.org/hibernate/orm/4.0/hem/en-US/html/listeners.html
    ```java
    // entity가 호출되기 전에 수행, callback life cycle event
    @PrePsersist
    public void prePersist() {
      this.created = new Date();
      this.createdBy = getCurrentAudior();
      ...
    }
    ```


---

## Reference

- [스프링 데이터 JPA / 백기선 / 인프런](https://www.inflearn.com/course/%EC%8A%A4%ED%94%84%EB%A7%81-%EB%8D%B0%EC%9D%B4%ED%84%B0-jpa/dashboard)
- [실전! 스프링 데이터 JPA / 김영한 / 인프런](https://www.inflearn.com/course/%EC%8A%A4%ED%94%84%EB%A7%81-%EB%8D%B0%EC%9D%B4%ED%84%B0-JPA-%EC%8B%A4%EC%A0%84)