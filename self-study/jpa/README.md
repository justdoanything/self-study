# JPA 실습 내용 정리

## [Link to Github - 05 JPA](https://github.com/justdoanything/self-study/blob/main/self-study/05%20JPA.md)

## postgres 설치 및 접속
  - docker run -p 5432:5432 -e POSTGRES_PASSWORD=yongwoo -e POSTGRES_USER=yongwoo -e POSTGRES_DB=springdata --name postgres_boot -d postgres
  - docker exec -it postgres_boot bash
  - psql -U yongwoo springdata

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

## Entity 상태 전파
- Cascade
  ```java
  /*
   * Post와 Comment의 양방향 관계를 맺고 
   * session.save(post); 만 해주게 되면 Comment엔 데이터가 저장되지 않는다.
   * casecade = CasecadeType.PRESIST 옵션을 주면 Post가 저장될 때 상태를 전파한다.
   * casdcade = CasecadeType.REMOVE 옵션을 주면 Post가 삭제될 때 상태를 전파한다.
   * Database의 casecade 옵션을 주는 것과 같다.
   */ 
   public class Post {
    @OneToMany(mappedBy = "post", cascade = { CascadeType.PERSIST, CasecaseType.REMOVE})
    private Set<Comment> comments = new HashSet<>();
   }
   


  ```


## Reference
- [스프링 데이터 JPA / 백기선 / 인프런](https://www.inflearn.com/course/%EC%8A%A4%ED%94%84%EB%A7%81-%EB%8D%B0%EC%9D%B4%ED%84%B0-jpa/dashboard)