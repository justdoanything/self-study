# TODO

Netty와 NIO

redis와 순위표 개발하기

FE/BE 정리하기
-  async 함수 정의 : 함수 이름 변경

Lazy Loading

CORS

Lambda 코드 정리

인강 듣기


---
# Quick

- ### Java에서 equals()가 있고 hashCode()가 없는 클래스를 Key로 HashMap을 사용하면 어떻게 동작하는가?
  ```
  hashCode()가 없을 경우 HashMap은 기본 Object.hashCode()를 사용해서 코드를 생성한다.
  따라서 equals()가 같은 값을 반환하더라도 HashMap은 항상 다른 객체로 인식한다.
  HashMap의 Key로 사용하고 싶다면 반드시 equals()와 hashCode()를 둘 다 정의해야 한다.
  ```

- ### JPA Entity가 Serializable을 구현하기를 권장하는 이유는?
  ```
  우선 직렬화란, 객체를 파일이나 네트워크 전송 등 외부 시스템에서 사용할 수 있도록 이진 데이터 형태(byte)로 변환하는 것을 의미한다.
  주로 분산 환경에서 저장, 복원을 할 때 사용되는 개념이다.
  
  JPA Entity는 Persistence Context에서 관리가 되는데 Serializable을 구현하면 객체의 상태를 저장하고 복원할 때 사용할 수 있다.
  서로 다른 트랜잭션에서 같은 Entity를 조회하고 수정할 때 Serializable가 없으면 상태를 저장하고 복원할 수 없어서 변경된 상태가 적용되지 않을 수 있다. (데이터 불일치 방지)
  분산 환경에서 여러 서버에서 같은 데이터베이스를 사용할 경우 Entity의 상태가 공유되야 할 때 사용된다.
  Spring Security와 JPA가 같이 사용될 때 JPA Entity를 Session에 저장할 때 사용된다.
  ```

- ### Java의 Syncronized
  ```java
  Syncronized는 멀티 스레딩 환경에서 공유 자원에 대한 접근을 동기화(synchronization)할 수 있는 기능을 제공한다.
  1. Instance Method 동기화
     public synchronized void method() { ... }
    
     Instance Method에 synchronized 키워드를 붙이면 해당 객체에 대한 동기화가 이루어집니다. 즉, 한 번에 하나의 스레드만 해당 객체의 synchronized 메서드에 접근할 수 있습니다.
     해당 Method를 호출하는 thread는 해당 객체의 Monitor lock을 획득하고 끝나면 반환한다. 이 때 다른 thread가 접근하려 할 경우, lock이 풀릴 때까지 대기합니다.
  
  2. Block 동기화
     public void method() {
       synchronized (this) { ... }
     }
  
  멀티 스레딩 환경에서 공유 자원에 대한 접근을 동기화함으로써, 스레드 간의 경합 조건(race condition)과 같은 문제를 방지할 수 있습니다.
  ```
---
# 면접대비

- 알고리즘
- 지원 동기
    1. 지원한 이유?
    2. 이직하고 싶은 이유?
    3. 팀에 지원한 이유?
    4. 개발과 운영 중 하고 싶은 것?
- 기술 경험 질문
    1. 써본 기술?
    2. 경험한 프로젝트?
    3. 경험한 아키텍처?
    4. 트래픽 경험?
    5. 꼬리물기 질문 다수
- 기술 질문
    1. JVM 구조
    2. JVM의 GC 종류 및 GC 사용 경험
    3. GC 절차 및 GC 튜닝 경험
    4. REST API에 대해서 나열 후 개발 과정
    5. PUT과 PATCH의 차이와 개발 경험
    6. THREAD LOCAL의 개념과 개발 경험
    7. JPA란?
    8. 디자인 패턴이란?
    9. 써봤던 디자인패턴 나열
    10. MSA란, MSA 구조 경험한 적 있나?
    11. KAFKA란, KAFKA로 스트리밍을 경험해본적 있나
- 책 추천
    1. 자바의 정석(한번 훑는 것을 추천)
    2. 이펙티브 자바(틈틈히)
    3. Head First 디자인 패턴 (한번 훑기)
    4. 클린코드(그냥 스키밍, 이런게 있구나 정도로)
    5. 모던 자바 (Stream, Optional, Lambda, Functional interface 등의 개념 공부)
    6. 친절한 SQL 튜닝 (안 친절함, 근데 공부할 것들 많음)

---

# 면접 대비 공부한 것들 복습 및 요약

- NGNIX : 웹서버 역할, Event-Driven 방식, 다수의 연결을 효과적으로 처리, 여러개의 Connection을 모두 Event Handler에서 비동기 방식으로 처리
- Apache : Thread 방식, Connection과 Thread가 1대1 매핑, Connection이 증가하면 CPU, Memory 낭비 심함

- Redis : key-value 구조의 data store, in-memory(disk가 아니라 memory에 저장), Sorted-set 구조로 실시간 순위표

- Flyway의 out-of-order : 여러 브랜치에서 서로 다른 버전으로 만들어도 작동하도록 함. 이미 실행된 마이그레이션 파일의 실행 이력을 삭제하고 이전의 실행된 마이그레이션보다 높은 버전의 마이그레이션을 실행하는 경우 용이하다. Prod에선 사용하지 않아야 한다.

- HTTP
    - GET / 조회 / 200 OK / 404 Not Found
    - PUT / 전체 변경 / 201 Created / 400 Bad Request
    - POST / 생성 / 200 OK, 204 No-Content / 409 Conflict
    - DELETE / 삭제 / 204 No-Content / 404 Not Found
    - HEAD / 응답헤더조회 / 200 OK
    - OPTIONS / 사용 가능한 메소드 표기 / 200 OK
    - PATCH / 일부 변경 / 201 Created / 400 Bad Request
    - PUT은 수정하지 않을 속성값도 모두 기존값을 보내줘야 한다.

- SQL 실행계획 : SQL 해석 -> 실행계획 수립 -> 실행
    - Extra 필드를 보고 Query 수정 여부 판단
    - type 필드를 보고 Index 참조 여부 판단

- JVM
    - byte code를 Interpreter 방식으로 읽다가 일정 기준이 지나면 JIT Compile 방식(빠르지만 자원소모가 큼)으로 읽음.
    - java compiler : .java -> .class
    - class loader : Runtime Data Area로 loading
    - execution engine : .class를 해석하고 해석된 byte code는 data area에 각 영역에 배치되서 수행. 이 때 GC과 Thread 동기화가 이뤄짐
    - runtime data area : static, heap, stack
        - heap : new 키워드로 생성된 객체와 배열이 생성
            - Young Generation : Eden, Survivor0, Survivor1
        - stack : 지역 변수, 파라미터 값

- Java GC
    - Mark And Sweep
        - 식별 Mark
        - 제거 Sweep
        - 재구성 Compact
    - MinorGC : Young 영역
        - Eden 영역에 Mark 된 객체를 Survivor 0으로 이동
        - Eden 영역을 Sweep (Survivor 0에 있는 객체 age+1) age는 일반적으로 31이 임계값
        - Eden, Survivor 0 영역에 Mark 된 객체를 Survivor1 으로 이동
        - 반복

- ThreadLocal
    - 1개의 Thread가 사용하는 변수는 다른 Thread가 접근할 수 없다.
    - Thread에 의해서만 read/write가 가능한 변수. set(), get()을 사용해서 데이터를 사용하고 반드시 remve() 해야 한다.

- JPA
    - 쓰기지연 : SQL 한번에 실행
    - 지연로딩 : 값이 실제로 필요한 시점에 조회.
    - 즉시로딩 : Entity 조회 시 연관된 데이터 모두 조회. 쿼리가 많이 수행되서 성능 문제.
    - Life Cycle
        - new : 순서 객체 상태로 컨텍스트와 관련 없는 상태
        - managed : 컨텍스트가 관리하는 상태
        - detached : 컨텍스트에 있다가 분리된 상태
            - detach, clear, close, merged
        - removed : 컨텍스트와 데이터베이스에서 삭제된 상태

- Kafka
    - 순차적 처리
    - 대용량 실시간 처리 대용량의 Batch Job 처리
    - RabbitMQ는 장시간 실행되는 작업, 안정적인 백그라운드 작업

- MSA
    - Load Balancing : Feign Client는 기본적으로 Load Balancing을 담고 있음.
    - N2PC : Not Two Phase Commit
    - SAGA : 서비스 간 트랜잭션을 묶어주는 개념
        - 결재해지 방식 : 에러가 발생한 서비스가 처리
        - 3자 위임 방식 : Kafka나 다른 서비스에 알리고 처리 안함.
    - API Composition : 각 마이크로 서비스 앞단에서 데이터를 취합하고 하나의 UI로 보여줌. CQRS에 비해서 복잡함
    - CQRS : 데이터를 집계해서 취합하는 기능을 하고 보여줌.