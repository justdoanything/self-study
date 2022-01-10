JPA (Java Persistence API)
===
![image](https://user-images.githubusercontent.com/21374902/148677553-e1d6501f-6716-4c47-9565-cc474bf2dbd8.png)

. JPA는 Java ORM 기술에 대한 표준 명세로 Java에서 제공하는 API
. JPA는 Interface 이지 특정 기능을 하는 Library는 아니다.

>ORM (Object-relational mapping)
　객체는 객체대로 설계하고 관계형 데이터베이스는 관계형 데이터베이스대로 설계
　ORM 프레임워크가 중간에서 매핑


. 기존 EJB에서 제공되던 EntityBean을 대체하는 기술로 ORM 이기 때문에 Java Class와 Database Table을 Mapping 한다. (SQL을 Mapping 하지 않음.)

. Mapper는 필드를 SQL에 매핑하는데 목적이라면 ORM은 DB Table ↔ Java Entity를 매핑하면서 RDB의 관계를 Object 관점에서 반영하는 것이다.

. SQL Mapper : Mybatis, jdbcTemplate, ...
. ORM : JPA, Hibernate, ...

. SpringFramework 에서 제공하는 spring-data-JPA는 JPA와 같지 않고 JPA → Hibernate → Spring-data-JPA 순으로 추상화되어 있다고 보면 된다.
> Spring-data-JPA를 사용하면 좋은 이유
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
　ㅋㅋ

> Dynamic Profiling
　ㅋㅋ

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

###JPA 성능 최적화
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

Orchestration 
===
Kubernetes, AWS ECS, Docker Swarm, Apache Mesos, ...
Docker - Container
Kubernetes - Pod


Monolithic Architecture : 기존 방식의 아키텍처

CBD : Component Based Development
SOA : Service Oriented Architecture

보상 트랜잭션과 SAGA 패턴
  Orchestration : 로컬 트랜잭션을 지시하는 Command 기반 방식.
  기존과 비슷한 방식으로 한개의 서비스가 동작하고 결과를 저장.
  실패 시 보상트랜잭션을 발생.

  Choreography : 참가 서비스들이 Message Broker/Queue를 이용해서 Message로 Event를 주고 받으며 실행 여부를 결정
  Local Transaction 처리 후 결과를 Event로 발생(Publish)시켜서 구독(Subscribe)하고 있는 서비스들이 프로세스 처리를 이어가도록 함.

분산처리를 위한 API Composition and CQRS(Command Query Responsibility Segregation)
여러 서비스의 API 호출 결과를 조합해서 Response 하는 방식.
각 서비스의 API 결과를 기록하고 기록한 것들을 종합해서 처리.
예약현황 조회 서비스는 예약 서비스와 승인 서비스가 만든 결과를 종합해서 처리

저장과 조회의 책임을 분리하는 패턴
예약현황 조회 서비스, 예약 서비스, 승인 서비스를 만들어뒀을 때
예약은 예약만하고 결과를 예약현황 조회 서비스에 기록
승인도 마찬가지
예약현황 조회는 자신의 DB를 조회하여 결과 출력 

Service Mesh
  - Sidecar Pattern
  circuit breaker
   Sidecar Proxy : 공통기능 갖고 있을 수 있음. LB 기능, 컨테이너가 응답을 안하면 대신 에러응답을 뱉어줄 수 있음.

Backing Service
    
Data Backing
    데이터를 다루는 서비스로 데이터베이스와 캐쉬 시스템 등을 의미
    MSA 환경에서 Container는 Stateless 속성을 갖고 있기 때문에 Container가 삭제되면 데이터가 모두 삭제될 수 있음. 따라서 volumn을 할당해서 외부에 저장 및 사용

    캐쉬 시스템은 세션을 관리해줄수잇음.
    Redis의 경우엔, Cookie-based : cookie에 사용자 정보 등을 갖고 있어 요청을 받았을 때 사용자 세션을 인지
    
    sticky sessionss : 일반적으로 cookie를 참조해서 redis가 같은 서버로 보내주도록 설정.
        만약에 서버가 다운되서 session을 받을 수 없는 상태라면 세션 데이터는 날아갈것이다.
        이를 해결하기 위한 2개의 전략이 있음.
        1. 로드밸런서가 죽은 서버로 부터 받은 요청을 다른 서버로 돌려줌. 새로운 서버는 세션을 카피해 오는데..어디서?

        2. RDBMS나 NoSQL Database 같은 세션 드라이버를 이용해서 세션을 해결.

    non-sticky sessions : 
Message Backing
  분산 시스템간 주고 받는 메세지로 메시지 큐 시스템 기반으로 브로커를 통해 비동기 방식으로 커뮤니케이션함.
  동시 다발적으로 요청이 발생하면 부하를 발생시키지만 큐 시스템은 순차적으로 처리해서 이를 방지함.



Telemetry
  logging
    중앙 로그 수집.
  tracing
    추적관리. 발생한 event 순으로 나열해서 보여줌.
  monitoring



CI/CD
  Continuous Integration
  Continuous Delivery
  Continuous Deployment


- Reference
  - MSA 구현 강의


---

DevOps
===
DevOps : Development + Operations
5가지 철학 : 문화, 자동화, 측정, 공유, 축적
soft skill : 문제인식, 선택과 집중, 결정, 업의 속성, 사용자
technical skill : 프로그래밍, 운영체제, 서버관리, 오픈소스, 클라우드

## IaC
- IaC (Infrastructure as Code)
→ Infra를 이루는 서버, 미들웨어, 서비스 등 Infra 구성요소들을 코드를 통해 구축하는 것
- Terraform
  - 구성요소 : provider, resource, state, output, module, remote
  - 명령어 : init, plan, apply, import, state, destroy
- AWS EC2 (Amazon Elastic Compute Cloud)
  - 크기 조정이 가능한 컴퓨텅 용량을 클라우드에서 제공하는 웹 서비스
  - EC2의 Public IP/22로 SSH 연결
  - VPC 안에 EC2가 있다면 Security Group을 확인할 것.
  - AMI : 주로 OS를 뜻함
  - chmod 600 {pem key} : key 파일의 권한이 다 오픈되어 있으면 에러 뱉기 때문에
  - ssh -i {pem key} {id}@{EC2 public IP}
  - open port 확인 : netstat -lntp
- Zsh 및 Oh-my-zsh 설치
  - EC2 Instance에 접속해서 Zsh 설치
    sudo yum install zsh
   - chsh 명령어를 사용하기 위한 util 설치
     - sudo yum install util-linux-user.x86_64
   - 기본 Shell 프로그램을 zsh로 설정
     - chsh
     - /bin/zsh
  - Oh-my-zsh 설치
- AWS Credentials
  - IAM 설정 : Terraform도 AWS API를 호출하는 형태이기 때문에 설정을 해줘야 한다.
  - ECS2 내에서 aws configure 명령어 실행
  - AWS Console에 있는 Access ID/KEY 설정
- Terraform 작동원리
  - Local 코드 : 현재 개발자가 작성/수정하는 코드
  - AWS 실제 인프라 : AWS에 배포되있는 인프라
  - Backend에 저장된 상태 : 가장 최근에 배포한 테라폼 코드
  - `AWS 실제 인프라`와 `Backend에 저장된 상태`는 100% 일치해야 함
  - `provider.tf` 작성
    terraform init
    `s3.tf` (Resource) 작성
    terraform plan # build랑 비슷한 느낌
    terraform apply # 실제 적용. deploy랑 비슷한 느낌
    terraform import # AWS 인프라에 배포된 리소스를 가져옴. pull이랑 비슷한 느낌



  


---
- Reference
  - DevOps : Infrastructure as Code with 테라폼(Terraform) and AWS 초급, 입문편 by [Inflearn](https://www.inflearn.com/)
  - [Terraform & AWS 101](https://terraform101.inflearn.devopsart.dev/)