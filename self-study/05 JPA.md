# JPA (Java Persistence API)

![image](https://user-images.githubusercontent.com/21374902/148677553-e1d6501f-6716-4c47-9565-cc474bf2dbd8.png)

- JPA는 Java ORM 기술에 대한 표준 명세로 Java에서 제공하는 API
- JPA는 Interface 이지 특정 기능을 하는 Library는 아니다.

> ＊ORM (Object-relational mapping)\
> 　객체는 객체대로 설계하고 관계형 데이터베이스는 관계형 데이터베이스대로 설계\
> 　 ORM 프레임워크가 중간에서 매핑\

- 기존 EJB에서 제공되던 EntityBean을 대체하는 기술로 ORM 이기 때문에 Java Class와 Database Table을 Mapping 한다. (SQL을 Mapping 하지 않음.)

- Mapper는 필드를 SQL에 매핑하는데 목적이라면 ORM은 DB Table ↔ Java Entity를 매핑하면서 RDB의 관계를 Object 관점에서 반영하는 것이다.

- SQL Mapper : Mybatis, jdbcTemplate, ...
  ORM : JPA, Hibernate, ...

- SpringFramework 에서 제공하는 spring-data-JPA는 JPA와 같지 않고 JPA → Hibernate → Spring-data-JPA 순으로 추상화되어 있다고 보면 된다.
  > ＊Spring-data-JPA를 사용하면 좋은 이유\
  > 　 Spring-data-JPA, MongoDB, Redis 등 findALL(), save() 등을 동일한 인터페이스로 갖고 있기 때문에 저장소 교체에 용이하다.

![image](https://user-images.githubusercontent.com/21374902/148677964-351165d1-d6b6-4485-aefb-86bf63e0efa4.png)

## JPA 특징
- JPA는 Application과 JDBC 사이에서 동작하면서 개발자는 JPA를 사용하고 직접 JDBC를 사용하진 않는다.
- Java Entity와 Database Table 사이에 매핑 설정을 통해 SQL을 생성해준다.
- Entity를 통해 작성할 수 있는 JPQL(Java Persistence Query Language)를 지원
- SQL 중심 개발에서 Entity 중심으로 개발할 수 있으며 유지보수가 쉽다.

## JPA Hibernate
- JPA 구현체의 한 종류 (EclipseLink, DataNucleus, OpenJPA, ...)
- HQL(Hibernate Query Language) 제공
  - SQL과 유사하여 추가적인 Convention을 정의할 수 있다.
  - HQL은 객체지향으로 상속, 다형성, 관계 등의 객체지향이 갖는 강점을 사용할 수 있다.
  - `Pagination`, `Dynamic Profiling` 기능 제공
  - 여러 테이블을 작업할 때 명시적인 join을 요구하지 않는다.
- Dynamic Profiling
  - `@DynamicUpdate` 사용해서 변경된 Column만 찾아서 업데이트를 실행한다.
- Pagination (페이징 처리)

  - `PagingAndSortingRepository` 객체는 페이징 처리를 위한 메소드를 제공한다.
  - Page를 사용하면 Page의 총 수를 알아내기 위해서 추가적인 쿼리 파생되면서 실행될 수 있다.

  ```java
  /**
   * CrudRepository 객체를 상속 받는다.
   */
  public interface PagingAndSortingRepository<T, ID extends Serializable>
  extends CrudRepository<T, ID> {
    Iterable<T> findAll(Sort sort);
    Page<T> findAll(Pageable pageable);
    /*
     * Page를 통해서 사용 가능한 데이터의 총 개수 및 전체 페이지 수를 알수 있다.
     * 이는 카운트 쿼리를 실행함으로써 전체 페이지 수 및 전체 데이터의 개수를 알아낸다.
     */
    Page<User> findByLastname(String lastname, Pageable pageable);
    /*
     * 카운트 쿼리가 많은 비용이 드는 경우에는 Slice를 사용하면 된다.
     * Slice는 다음 Slice가 존재하는지 여부만 알고 있다.
     * 전체 데이터 셋의 크기가 큰 경우에는 Slice를 사용하는게 성능상 유리하다.
     */
    Slice<User> findByLastname(String lastname, Pageable pageable);
    /*
     * Pageable을 통해서도 정렬을 할 수 있지만
     * 정렬만 하는 경우에는 그냥 Sort를 사용하는게 좋다.
     */
    List<User> findByLastname(String lastname, Sort sort);
    /*
     * 결과를 단순히 List로 받을 수 있다. \
     * 이 경우엔 Page 인스턴스를 생성하기 위한 메타데이터가 생성되지 않기 때문에 카운트 쿼리가 실행되지 않는다.
     * 단순히 주어진 범위내의 엔티티를 검색하기 위한 쿼리만 실행된다.
     */
    List<User> findByLastname(String lastname, Pageable pageable);
  }

  /**
   * 페이징 크기는 20이고 두번째 페이지를 가져오는 예제
   */
  public interface ItemRepository extends PagingAndSortingRepository<Item, Integer> {
    Page<Item> findAll(Pageable pageable);
  }
  Page<Item> items = repository.findAll(new PageRequest(1, 20));
  ```

  - ###### Sorting

  ```java
  Pageable sortedByName = PageRequest.of(0, 3, Sort.by("name"));
  Pageable sortedByPriceDesc = PageRequest.of(0, 3, Sort.by("price").descending());
  Pageable sortedByPriceDescNameAsc = PageRequest.of(0, 5, Sort.by("price").descending().and(Sort.by("name")));
  ```

  - ###### PageableHandlerMethodArgumentResolver

    - 아래의 예제는 컨트롤러의 메소드 파라미터로 Pageable를 사용한 예이다.
    - PageableHandlerMethodArgumentResolver는 컨트롤러 메소드에 Pageable 타입의 파라미터가 존재하는 경우, 요청 파라미터를 토대로 PageRequest 객체를 생성한다.

      ```java
      @Controller
      @RequestMapping("/users")
      class UserController {

        private final UserRepository repository;

        UserController(UserRepository repository) {
          this.repository = repository;
        }

        @RequestMapping
        String showUsers(Model model, Pageable pageable) {
          model.addAttribute("users", repository.findAll(pageable));
          return "users";
        }
      }
      ```

    - 위의 예제 처럼 컨트롤러의 메소드 파라미터로 Pageable 타입이 존재하는 경우, Spring MVC가 요청 파라미터로부터 Pageable 객체를 생성하려고 시도한다. 이 때 사용되는 기본 설정은 아래와 같다.
    - Pageable 객체를 생성하기 위해서 사용되는 요청 파라미터
      - page : 가져올 페이지 (기본값 : 0)
      - size : 페이지의 크기 (기본값 : 20)
      - sort : 정렬 기준으로 사용할 속성으로 기본적으로 오름차순으로 한다.
        정렬 기준 속성이 2개 이상인 경우에는 sort 파라미터를 2개 이상 넣어주면된다.
        예) sort=firstname&sort=lastname,asc.
    - Pageable 파라미터에 @PageableDefault 어노테이션을 사용하면 디폴트 값을 변경할 수 있다.
    - 커스터마이징을 해야하는 경우는 PageableHandlerMethodArgumentResolverCustomizer 또는 SortHandlerMethodArgumentResolverCustomizer 인터페이스를 구현하는 빈을 등록하면 된다.
    - customize() 메소드를 통해서 설정을 변경할 수 있다. 아래는 간단한 예제이다.
      ```java
      @Bean
      SortHandlerMethodArgumentResolverCustomizer sortCustomizer() {
          return s -> s.setPropertyDelimiter("<-->");
      }
      ```
    - 기본 MethodArgumentResolver의 설정을 변경하는 것으로 충분하지 않다면, SpringDataWebConfiguration를 상속해서 pageableResolver() 또는 sortResolver() 메소드를 오버라이딩 하면된다.
    - 하나의 요쳥으로 두 개 이상의 Pageable 또는 Sort 객체를 생성하는 경우에, @Qualifier 어노테이션을 사용해서 하나씩 구별할 수 있다. 그리고 요청 파라미터는 ${qualifier}\_. 접두사가 있어야한다. 아래와 같은 경우 foo_page, bar_page 등의 파라미터가 있어야한다.

    ```java
    String showUsers(Model model,
      @Qualifier("foo") Pageable first,
      @Qualifier("bar") Pageable second) { … }
    ```

  - ###### Hypermedia support for Pageables

    - Spring HATEOAS에서 PagedResources 클래스를 제공한다. PagedResources 클래스는 Page 객체의 메타데이터와 내용 뿐만 아니라 이전 페이지나 다음 페이지에 대한 링크를 포함하고 있다.
    - Page 객체를 PagedResources로 변환하는 작업은 ResourceAssembler 인터페이스의 구현체인 PagedResourcesAssembler를 통해서 할 수 있다. 아래는 간단한 예제이다.

    ```java
    @Controller
    class PersonController {

      @Autowired PersonRepository repository;

      @RequestMapping(value = "/persons", method = RequestMethod.GET)
      HttpEntity<PagedResources<Person>> persons(Pageable pageable, PagedResourcesAssembler assembler) {
        Page<Person> persons = repository.findAll(pageable);
        return new ResponseEntity<>(assembler.toResources(persons), HttpStatus.OK);
      }
    }
    ```

    - toResources() 메소드를 호출하면 아래와 같은 일이 발생한다.
    - Page 객체의 내용은 PagedResources 객체의 내용이 된다.
    - PageMetadata 객체가 Page 객체의 정보를 기반으로 생성되고 PagedResources 객체에 포함된다.
    - Page의 상태에 따라서 PagedResources의 prev, next 링크가 생성된다. 링크는 다음 또는 이전 페이지에 대한 URL이며 페이징과 관련된 파라미터를 포함하고 있다.
    - 아래의 간단한 예를 들어보자면, 만약 데이터베이스에 30명의 유저정보가 있다고 가정해보자. 만약 유저 정보를 가져오는 GET : http://localhost:8080/persons 요청을 보냈다면 아래와 같은 응답이 올 것이다.

    ```json
    {
      "links" : [
        {
          "rel" : "next",
          "href" : "http://localhost:8080/persons?page=1&size=20
        }
      ],
      "content" : [
        … // 20 Person instances rendered here
      ],
      "pageMetadata" : {
        "size" : 20,
        "totalElements" : 30,
        "totalPages" : 2,
        "number" : 0
      }
    }
    ```

    - assembler가 올바른 Link가 생성된 것을 볼 수 있다. 기본적으로 Link는 현재 호출된 컨트롤러 메소드를 기반으로 생성이 된다.

## Persistence Framework
- Architecture에서 데이터에 영속성을 부여해주는 계층
- JDBC를 이용해 직접 구현할 수 있지만 대부분 Persistence Framework를 사용한다.
  ![image](https://user-images.githubusercontent.com/21374902/148678370-cbbf194a-5e48-4e60-a9f1-9174058e3cd6.png) - Presentaion Layer : UI 계층 - Application Layer : Service 계층 - Business Logic Layer : Damain 계층 - Data Access Layer : Perstence 계층
- Persistence Framework는 SQL Mapper와 ORM으로 나눌 수 있다.

## JPA's Persistence
![image](https://user-images.githubusercontent.com/21374902/148678614-3dd6c12b-1c97-4a86-8366-ded8a9fa7875.png)

- Persistence Context
  - Entity를 담고 있는 집합으로 JPA는 Persistence Context 안에 있는 Entity를 DB에 반영한다.
  - Entity를 검색, 삭제, 추가하게 되면 Persistence Context의 내용이 DB에 반영된다.
  - Persistence Context는 직접 접근은 불가능하고 Entity Magener를 통해서만 접근이 가능하다.
- Entity가 Persistence Context에 포함이 되는지 안되는지에 따라 영속성이 달라진다.
- Entity Manager가 활성화 되있는 상태에서 @Transactional 안에서 DB 데이터를 가져오면 데이터는 영속성이 유지된다.
- 이 상태에서 데이터값을 변경하면 Transaction이 끝나는 시점에 해당 테이블에 변경 내용을 반영(Commit)한다.
- 따라서 Entity의 필드 값을 변경한 후에 update()를 명시하지 않아도 자동으로 DB 내 데이터가 변하게 된다. 이를 `Dirty Checking` 이라고 한다.

## JPA 성능 최적화
- Buffering
  - 쓰기 지연(Transactional write-behind)
    - commit을 하기 전까지 Query는 메모리에 쌓아둔다.
    - commit이 되면 JDBC Batch SQL 기능을 사용해서 한번에 SQL을 실행한다.
    - JDBC Batch를 사용하는 것보다 지연 로딩(Lazy Loading) 옵션을 사용한다.

```java
        // Insert
        transaction.begin(); // Start this transaction
        em.persist(memberA);
        em.persist(memberB);
        em.persist(memberC);
        // 여기까지 INSERT SQL을 데이터베이스에 보내지 않는다.
        transaction.commit(); // Commit을 하는 순간에 위 3개의 Query를 실행
```

```java
        // Update
        transaction.begin(); // Start this transaction
        changeMember(memberA);
        deleteMember(memberB);
        execute_business_logic();
        // 여기까지 change, delete를 데이터베이스에 보내지 않는다.
        transaction.commit(); // Commit을 하는 순간에 update, delete를 실행
        // Update, Delete로 인한 Row Lock 시간을 최소화 할 수 있음.
```

- - 지연 로딩(Lazy Loading) == `FetchType.LAZY`
    - 지연로딩은 값이 실제로 필요한 시점에 JPA가 Team에 대한 Select를 수앻한다.
    - 만약에 Member과 Team을 대부분 동시에 사용한다면 `즉시 로딩`을 사용한다.
  - 즉시 로딩(Eager Loading) == `FetchType.EAGER`
    - 즉시 로딩은 Entity 조회 시 연관 관계가 있는 데이터까지 한 번에 조회해온다.
    - 따라서 Member와 Team이 연관 관계가 있으면 Member 객체를 조회해도 Team 객체까지 조회하는 쿼리가 수행된다.
    - 되도록 지연 로딩을 사용하고 즉시 로딩은 진짜 필요할 때만 사용하는게 좋다.
      한 객체에 연관된 객체가 100개라면 즉시로딩은 101번의 쿼리를 수행할 수 있다.
  - 연관 관계별로 기본값은 아래와 같다.
    @MonayToOne, @OneToOne : EAGER
    @ManyToMany, @oneToMany : LAZY

```java
        Member member = memberDAO.find(memberId); // Select 수행
        /*
        * 지연로딩의 경우엔, Team Entity는 Proxy Entity로 가져온 후에
        * 실제 Team Entity를 사용하는 시점에 Team Entity가 초기화되고 쿼리를 실행한다.
        */
        Team team = member.getTeam();  // Proxy Entity를 조회한 상태
        String teamName = team.getName(); // Team에 있는 Field에 접근할 때 실제로 쿼리가 실행
```

- Caching
  - 1차 Cache와 동일성(identity) 보장
  - 같은 Transaction 안에서 같은 Entity를 반환해서 약간의 조회 성능 향상
  - DB isolation Level이 Read Commit 이어도 Application에서 Repeatable Read 보장
  - 아래 예제에선 SQL을 한번만 수행.

```java
        String memberId = "100";
        Member m1 = jpa.find(Member.class, memberId); // SQL 실행
        Member m2 = jpa.find(Member.class, memberId); // Caching (SQL 실행 X, 위에서 가져온 m1을 사용)
        if(m1 == m2 ) // true
```

## JPA Entity LifeCycle
<img width="470" alt="image" src="https://user-images.githubusercontent.com/21374902/167240752-83cf22e1-0416-40fc-bdc6-cbb2b71718ba.png">

- ###### new/transient : 순수한 객체 상태로 `영속성 컨테스트와 관련이 없는 상태`
  ```java
  Member member = new Member();
  ```
- ###### managed : EntityManager를 통해 엔티티를 영속성 컨테스트에 저장되어 `영속성 컨테스트가 관리`하는 상태
  ```java
  em.persist(member); // 객체 저장
  ```
- ###### detached : 영속성 컨테스트에 저장되었다가 분리된 상태
  ```java
  em.detach(member); // 특정 엔티티를 분리
  em.close(); // 영속성 컨테스트 닫기
  em.clear(); // 영속성 컨테스트 초기화
  ```

  - detach() : 특정 엔티티를 준영속 상태로 만들어준다. 더이상 엔티티를 관리하지 않고 1차 캐시, 쓰기지연 SQL 저장소에서 해당 엔티티를 관리하기 위한 모든 정보가 삭제된다. 직접 사용하는 일은 거의 없다.
  - clear() : 영속성 컨텍스트를 초기화해 해당 영속성 컨테스트에 존재하ㅡㄴ 모든 엔티티를 준영속 상태로 만든다. 모든 것이 초기화되었기 때문에 새로 만든 영속성 컨테스트 상태와 동일하다. 때문에 같은 엔티티를 조회할 때 SELECT 쿼리가 발생하게 된다. 1차 캐시에 관계 없는 쿼리를 확인하고 싶을 때 사용한다.
  - close() : 영속성 컨테스트를 종료하고 관리하던 모든 엔티티가 준영속 상태가 된다.
  - 특징
    - 거의 비영속 상태와 동일 : 영속성 컨테스트가 관리하지 않기 때문에 제공하는 어떤 기능도 동작하지 않음.
    - 식별값을 보유 : 비영속 상태는 식별값이 얻을 수 있지만 준영속 상태는 영속상태였기 때문에 반드시 식별자를 보유하고 있다.
    - 지연 로딩 불가 : 지연 로딩은 실제 객체 대신 프록시 객체를 로딩하고 해당 객체를 실제 사용시 영속성 컨테스트를 통해 데이터를 불러온다. 하지만 더이상 영속성 컨테스트가 관리하지 않기 때문에 징녀 로딩시 문제가 발생한다.
  - merged() : 준영속 상태의 엔티티를 다시 영속상태로 변환한다. 준영속 상태의 엔티티를 받아 갖고 있는 정보로 새 영속 상태의 엔티티를 반환한다. 병합은 비영속 상태의 엔티티도 영속 상태로 만들 수 있다. 파라미터로 넘어온 엔티티의 식별자 값으로 영속성 컨테스트를 조회하고 찾는 엔티티가 없으면 데이터베이스에서 조회하고 영속 상태로 만든다. 만약 데이터베이스에서도 없으면 새로운 엔티티를 생성한다. 만약 불러온 엔티티와 파라미터로 넘긴 객체와 값이 다를 경우 값을 채우고 병합이 이루어진다. 따라서 병합은 save or update를 수행한다.
- ###### removed : 엔티티를 영속성 컨테스트와 데이터베이스에서 삭제한 상태
  ```java
  em.remove(member);
  ```
- 




- Reference
  - [adam2's blog](https://velog.io/@adam2/JPA%EB%8A%94-%EB%8F%84%EB%8D%B0%EC%B2%B4-%EB%AD%98%EA%B9%8C-orm-%EC%98%81%EC%86%8D%EC%84%B1-hibernate-spring-data-jpa)
  - [InfoWorld's article](https://www.infoworld.com/article/3379043/what-is-jpa-introduction-to-the-java-persistence-api.html)
  - [gunju-jo's github](https://gunju-ko.github.io/spring/2018/05/01/Spring-Data-JPA-Paging.html)
  - https://girawhale.tistory.com/122#%EC%A4%80%EC%98%81%EC%86%8D-%EC%83%81%ED%83%9C
---

# 스프링 데이터 JPA / 백강선 / 인프런

https://github.com/justdoanything/self-study/blob/main/self-study/jpa/README.md
