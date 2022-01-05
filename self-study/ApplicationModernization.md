JPA (Java Persistence API)

ORM (Object-relational mapping)

Orchestration 
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
