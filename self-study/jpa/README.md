# [Github - JPA 정리](#https://github.com/justdoanything/self-study/blob/main/self-study/05%20JPA.md)

# postgres 설치 및 접속
  - docker run -p 5432:5432 -e POSTGRES_PASSWORD=yongwoo -e POSTGRES_USER=yongwoo -e POSTGRES_DB=springdata --name postgres_boot -d postgres
  - docker exec -it postgres_boot bash
  - psql -U yongwoo springdata

# @Value
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
