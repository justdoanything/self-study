JPA (Java Persistence API)
===
![image](https://user-images.githubusercontent.com/21374902/148677553-e1d6501f-6716-4c47-9565-cc474bf2dbd8.png)

- JPA는 Java ORM 기술에 대한 표준 명세로 Java에서 제공하는 API
- JPA는 Interface 이지 특정 기능을 하는 Library는 아니다.

>＊ORM (Object-relational mapping)
　객체는 객체대로 설계하고 관계형 데이터베이스는 관계형 데이터베이스대로 설계
　ORM 프레임워크가 중간에서 매핑


- 기존 EJB에서 제공되던 EntityBean을 대체하는 기술로 ORM 이기 때문에 Java Class와 Database Table을 Mapping 한다. (SQL을 Mapping 하지 않음.)

- Mapper는 필드를 SQL에 매핑하는데 목적이라면 ORM은 DB Table ↔ Java Entity를 매핑하면서 RDB의 관계를 Object 관점에서 반영하는 것이다.

- SQL Mapper : Mybatis, jdbcTemplate, ...
  ORM : JPA, Hibernate, ...

- SpringFramework 에서 제공하는 spring-data-JPA는 JPA와 같지 않고 JPA → Hibernate → Spring-data-JPA 순으로 추상화되어 있다고 보면 된다.
>＊Spring-data-JPA를 사용하면 좋은 이유
　Spring-data-JPA, MongoDB, Redis 등 findALL(), save() 등을 동일한 인터페이스로 갖고 있기 때문에 저장소 교체에 용이하다.

![image](https://user-images.githubusercontent.com/21374902/148677964-351165d1-d6b6-4485-aefb-86bf63e0efa4.png)
#### JPA 특징
- JPA는 Application과 JDBC 사이에서 동작하면서 개발자는 JPA를 사용하고 직접 JDBC를 사용하진 않는다.
- Java Entity와 Database Table 사이에 매핑 설정을 통해 SQL을 생성해준다.
- Entity를 통해 작성할 수 있는 JPQL(Java Persistence Query Language)를 지원
- SQL 중심 개발에서 Entity 중심으로 개발할 수 있으며 유지보수가 쉽다.

#### JPA Hibernate
- JPA 구현체의 한 종류 (EclipseLink, DataNucleus, OpenJPA, ...)
- HQL(Hibernate Query Language) 제공
  - SQL과 유사하여 추가적인 Convention을 정의할 수 있다.
  - HQL은 객체지향으로 상속, 다형성, 관계 등의 객체지향이 갖는 강점을 사용할 수 있다.
  - Pagination, Dynamic Profiling 기능 제공
  - 여러 테이블을 작업할 때 명시적인 join을 요구하지 않는다.
> Pagination
추후 업데이트

> Dynamic Profiling
추후 업데이트

#### Persistence Framework
  - Architecture에서 데이터에 영속성을 부여해주는 계층
  - JDBC를 이용해 직접 구현할 수 있지만 대부분 Persistence Framework를 사용한다.
![image](https://user-images.githubusercontent.com/21374902/148678370-cbbf194a-5e48-4e60-a9f1-9174058e3cd6.png)
    - Presentaion Layer : UI 계층
    - Application Layer : Service 계층
    - Business Logic Layer : Damain 계층
    - Data Access Layer : Perstence 계층
  - Persistence Framework는 SQL Mapper와 ORM으로 나눌 수 있다.

#### JPA's Persistence
![image](https://user-images.githubusercontent.com/21374902/148678614-3dd6c12b-1c97-4a86-8366-ded8a9fa7875.png)  
  - Persistence Context
    - Entity를 담고 있는 집합으로 JPA는 Persistence Context 안에 있는 Entity를 DB에 반영한다.
    - Entity를 검색, 삭제, 추가하게 되면 Persistence Context의 내용이 DB에 반영된다.
    - Persistence Context는 직접 접근은 불가능하고 Entity Magener를 통해서만 접근이 가능하다.
  - Entity가 Persistence Context에 포함이 되는지 안되는지에 따라 영속성이 달라진다.
  - Entity Manager가 활성화 되있는 상태에서 @Transactional 안에서 DB 데이터를 가져오면 데이터는 영속성이 유지된다.
  - 이 상태에서 데이터값을 변경하면 Transaction이 끝나는 시점에 해당 테이블에 변경 내용을 반영(Commit)한다. 
  - 따라서 Entity의 필드 값을 변경한 후에 update()를 명시하지 않아도 자동으로 DB 내 데이터가 변하게 된다. 이를 `Dirty Checking` 이라고 한다.

### JPA 성능 최적화
  - Buffering
    + 쓰기 지연(Transactional write-behind)
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
-  - 지연 로딩(Lazy Loading)  == `FetchType.LAZY`
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
- Reference
  - [adam2's blog](https://velog.io/@adam2/JPA%EB%8A%94-%EB%8F%84%EB%8D%B0%EC%B2%B4-%EB%AD%98%EA%B9%8C-orm-%EC%98%81%EC%86%8D%EC%84%B1-hibernate-spring-data-jpa)
  - [InfoWorld's article](https://www.infoworld.com/article/3379043/what-is-jpa-introduction-to-the-java-persistence-api.html)

---
