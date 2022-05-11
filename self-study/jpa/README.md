# JPA 실습 내용 정리

## [Link to Github - 05 JPA](https://github.com/justdoanything/self-study/blob/main/self-study/05%20JPA.md)

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
ApplicationContext extends ApplicationEventPublisher
- event
  - extends ApplicationEvent
- listener
  - implements ApplicationListener<E extends ApplicationEvent>
  - @EventListener

- 스프링 데이터의 도메인 이벤트 Publisher
  - @DomainEvents
  - @AfterDomainEventPublication extends AbstractAggregateRoot<E>
  - 현재는 save() 할 때만 발생 합니다.

## QueryDSL 연동
- findByFirstNameIngoreCaseAndLastNameStartsWithIgnoreCase(String firstName, String lastName) 
  - 여러 쿼리 메소드는 대부분 두 가지 중 하나.
  - Optional<T> findOne(Predicate): 이런 저런 조건으로 무언가 하나를 찾는다.
  - List<T>|Page<T>|.. findAll(Predicate): 이런 저런 조건으로 무언가 여러개를 찾는다.
  - QuerydslPredicateExecutor 인터페이스

- QueryDSL (http://www.querydsl.com/)
  - 타입 세이프한 쿼리 만들 수 있게 도와주는 라이브러리
  - JPA, SQL, MongoDB, JDO, Lucene, Collection 지원
  - QueryDSL JPA 연동 가이드
  - 스프링 데이터 JPA + QueryDSL
  - 인터페이스: QuerydslPredicateExecutor<T>
  - 구현체: QuerydslPredicateExecutor<T>

- 연동 방법
  - 기본 리포지토리 커스터마이징 안 했을 때.
  - 기본 리포지토리 커스타마이징 했을 때.
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

public interface AccountRepository extends JpaRepository<Account, Long>, QuerydslPredicateExecutor<Account> {
}




---

## Reference

- [스프링 데이터 JPA / 백기선 / 인프런](https://www.inflearn.com/course/%EC%8A%A4%ED%94%84%EB%A7%81-%EB%8D%B0%EC%9D%B4%ED%84%B0-jpa/dashboard)
