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

## Custom Repository

---

## Reference

- [스프링 데이터 JPA / 백기선 / 인프런](https://www.inflearn.com/course/%EC%8A%A4%ED%94%84%EB%A7%81-%EB%8D%B0%EC%9D%B4%ED%84%B0-jpa/dashboard)
