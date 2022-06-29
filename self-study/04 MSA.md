MSA
===
- [MSA 구현 Summary](#msa-구현-summary)
- [MSA Architecture 구성](#msa-architecture-구성)
- [Hystrix](#hystrix)
- [Spring Cloud](#spring-cloud)
- [Ribbon Client](#load-balancing) 
- [Eureka](#eureka)
- [API Gateway](#api-gateway)
- [SAGA](#saga)
- [Kafka](#kafka)
- [OpenFeign](#openfeign)
- [API Composition](#api-composition)
- [CQRS](#cqrs)
- [부록](#부록)
- [프로젝트 구현](#프로젝트-구현)


---
- ## MSA 구현 Summary
  - [Master Branch](https://github.com/justdoanything/PaymentApprovalServer)
  - [프로젝트 초기 구성 및 Hystrix 구성](https://github.com/justdoanything/PaymentApprovalServer/tree/P01-hystrix)
  - [eureka server-client 구성](https://github.com/justdoanything/PaymentApprovalServer/tree/P02-eureka)
  - [gateway 구성](https://github.com/justdoanything/PaymentApprovalServer/tree/P03-gateway)
  - [kafka 구성](https://github.com/justdoanything/PaymentApprovalServer/tree/P04-kafka)
  - 참고한 모델

    ![image](https://user-images.githubusercontent.com/21374902/176366449-68b59fc8-97c7-49e6-b791-6a5e11b28fd0.png)


- ## MSA 구현 Note
  - ### Spring Boot 버전과 Spring Spring 버전
    - Spring Boot 버전과 Spring Cloud의 버전을 맞춰주는게 중요하다. 아래 코드를 사용해서 Spring Cloud 버전을 정의하고 그에 맞는 Spring Boot 버전을 맞춰서 설정하면 된다.
    - https://spring.io/projects/spring-cloud/
      ```yml
      ext {
        set('springCloudVersion', 'Hoxton.SR12')
      }

      dependencyManagement {
        imports {
          mavenBom "org.springframework.cloud:spring-cloud-dependencies:${springCloudVersion}"
        }
      }
      ```
  - ### Eureka Server 적용 후 UI 화면이 안뜨는 경우
    - 아래 속성을 추가해주면 된다.
      ```yml
      spring.freemarker.template-loader-path=classpath:/templates/
      spring.freemarker.prefer-file-system-access=false
      ```

  - ### Hystrix를 사용한 보상 트랜잭션
    - Hystrix를 사용해서 한 서비스 혹은 기능이 Exception이 발생하거나 timeout이 발생했을 때 어떻게 보상 트랜잭션을 처리할건지 명시할 수 있다.
      ```java
      @Service
      public class VanFdkServiceImpl implements VanFdkService{
        @Override
        @HystrixCommand(commandKey = "sendApprovalMessageToVanFdk", fallbackMethod ="fallbackSendApprovalMessageToVanFdk")
        public ResponseEntity<Object> sendApprovalMessageToVanFdk(JsonObject request)throws Exception {
            // TODO Auto-generated method stub
            return null;
        }

        public ResponseEntity<Object> fallbackSendApprovalMessageToVanFdk(JsonObjectrequest, Throwable throwable){
            // Gateway를 다시 호출해서 다른 VAN 서비스가 호출되게 해야함.
            return null;
        }
      }
      ```
    - ### Gateway가 Eureka Client 매핑하는 방법
      - Gateway도 다른 서비스와 마찬가지로 Eureka Client로 등록이 된다.
      - Eureka Server UI 화면에서 볼 수 있듯이 등록된 Eureka Client들은 Application ID를 갖는다.
      - Application ID은 Client의 `spring.application.name` 값과 동일하다.
      - Gateway에 routes를 설정할 때 `uri: lb://FDK`을 설정하는데 Eureka에서 Application ID 값과 `lb://` 뒤에 있는 값을 매핑해서 라우팅을 해주게 된다.
      - 즉, Gateway는 Eureka Client의 Application ID 값을 기준으로 다른 Client들에게 라우팅을 해준다.
    - ### Kafka를 사용해서 Queue를 사용한 보상 트랜잭션 처리

---

- ## 용어 정리
  - Circuit Breaker
    - Hystrix
  - Ribbon Client
  - Eureka
  - API Gateway
  - SAGE
  - Kafka
    - Publisher : Producer
    - Subscriber : Consumer
    - Zookeeper
    - RabbitMQ
  - OpenFeign
  - API Composition
  - CQRS
  - Service Mash
  - Backing Service
  - Telemetry
  - Outer Architecture / Inner Architecture
  - Choreography
  - Orchestration


---
- ## MSA Architecture 구성
    - #### Outer Architecture (= NS구조, North to South)
      - ###### API Gateway (Published APIs)
      - ###### Service Mesh
        Configuration, Routing, Monitoring, AuthN / AuthZ, Discovery, Load Balancing, Availability, Dependency
      - ###### Container
      - ###### Backing Service
        Messaging Channelds(Message Queue)
      - ###### CI/CD
        Deployment Automation, Build and Test Automation
      - ###### Telemetry
        Diagnostics and Instrumentation, Monitoring and Alerting
    - #### Inner Architecture(= WE 구조, West to East)
      - Microservice A,B,C, ...

---

- ## Circuit Breaker
  - Circuit Breaker 란 전기 기기에서 과부하나 과전류가 들어왔을 때 메인 기기를 보호하기 위해 흔히 쓰는 회로 차단기를 의미
  - MSA에서는 특정 Micro Service의 장애로 인해 다른 서비스에도 장애를 일으키면서 시스템 전체가 down 될 수 있는 것을 방지하기 위한 것을 말한다.
  - MSA 구조에서 가장 중요한 부분으로 Discovery, Routing, Load Balancing, +ɑ 로 구성
    - Discovery : 특정 서비스를 찾고
    - Routing : 최적의 경로를 찾고
    - Load Balancing : 가장 부하가 적은 서비스로
  - ###### Hystrix
    - MSA로 가장 유명한 Netflix가 Amazon AWS에 MSA System을 구축할 때 개발한 Software기반 Circuit breaker로 Java로 구성되어 동작
    - `Spring Cloud Hystrix`는 Netflix OSS 기반의 Hystrix Library를 Spring Cloud에 적용할 수 있는 Libarary로 변형한 Library
    - Circuit breaker처럼 중간에 에러가 발생하면 뒤에 있는 로직을 처리하지 않고 앞으로 오는 요청도 막으며 에러 처리를 위한 콜백 동작을 하도록 함.
    - Service 쪽에 @HystrixCommand 을 추가해서 해당 서비스가 실패했을 때 수행할 보상 트랜잭션과 연결한다.
    - 대표적인 기능들
      - Thread timeout
      - 장애 대응 등을 설정해 장애시 정해진 루트를 따르도록 처리
      - 미리 정해진 임계치를 넘으면 장애가 있는 로직을 실행하지 않고 우회 하도록 처리
    - Hystrix 적용하는 방법
      - `spring-cloud-starter-netflix-hystrix` 라이브러리 추가
      - Main Application에 `@EnableCircuitBreaker` 추가
      - Circuit Breaker를 추가하고자 하는 메소드에 `@HystrixCommand` 추가
      ```java
      @HystrixCommand(fallbackMethod = "doFallbackProcess")
      public String getOtherServiceMessage(String param) {
        return this.restTemplate.getForObject(url, String.class);
      }

      public String doFallbackProces() {
        return "Process you want";
      }
      ```
    - Hystrix 설정 변경
      - `@HystrixCommand`에 `@HystrixProperty`를 추가해서 상세한 제어 가능
      ```java
      @HystrixCommand(commandKey = "commandKeyExample", fallbackMethod = "doFallbackProcess", commandProperties = {
        @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "2000")
        }
      )
      ```
      - `@HystrixCommand` 대신 application.yml을 통해 설정 가능
      ```yml
      hystrix:
        command:
          commandKeyExample:
            execution:
              isolation:
                thread:
                  timeoutInMilliseconds: 3000
      ```

---

- ## Spring Cloud
  - Spring Cloud는 분산 시스템 상에서 공통된 개발 패턴을 통해 개발 효율을 제공해주기 위해 개발
  - Spring Cloud를 통해 분산 시스템 상에서 필요한 여러 패턴들을 Boiler Plate(표준 문안 혹은 패턴)화 시켜 손쉽게 개발할 수 있도록 지원
  - Spring Cloud 구성
    - Distributed/versioned configuration
    - Service Registration and discovery
    - Routing
    - Service-to-service calls
    - Load balancing
    - Circuit Breakers
    - Global locks
    - Leadership election and cluster state
    - distrubuted messaging
  - 대부분이 Netflix OSS 기반으로 Spring에 맞게 만든 것.

---

- ## Load Balancing
  - Ribbon Client
    - `Round Robbin` 방식으로 Load Balancing을 해줌 
    - Netflix OSS Library 중 Hardware 적인 Load Balancer를 대신해 L7 Layer에서 Client Side Load Balancer 역할을 담당
    - FeignClient는 기본적으로 Ribbon 기능을 포함하고 있음
    - Ribbon의 장점
      - REST API를 호출하는 서비스에 탑제되는 SW 모듈
      - 주어진 서버 목록에 대해 Load Balancing 수행
      - 매우 다양한 설정이 가능 (서버 선택, 실패 시 skip시간, Ping체크 등)
      - Ribbon에는 Retry 기능이 내장되어 있고 Eureka와 함께 사용하면 매우 강력한 기능이 됨
    - Ribbon 적용 방법
      - dependency 추가 \
        `org.spring.framework.cloud:spring-cloud-starter-Netflix-ribbon`
      - properties 추가 (application.properties)
        ```yml
        serviceB:
          ribbon:
            listOfServers: localhost:8081, localhost:8082
        ```
      - `@LoadBalanced` 추가
        ```java
        @LoadBalanced
        public RestTemplate restTemplate(0 {
          return new RestTemplate();
        })
        ```
      - 호출 URL 변경
        ```java
        private final String url = "https://serviceB/serviceBApi";
        ```
      - 기타 속성 추가
        ```yml
        serviceB:
          ribbon:
            listOfServers: localhost:8082, localhost:8083
              MaxAutoRetries: 0
            MaxAutoRetriesNextServer: 1 #실패 시 다른 서버로 재시도 하는 횟수
        ```
        구분|Eureka|SpringCloud Kubernetes
        ---|---|---
        Ribbon|O|O
        FeignClient+Ribbon|X|O

---

- ## Discovery
  - ## Eureka
    - Micro Service를 구성하는 서버들의 목록과 위치(IP,PORT)가 동적으로 변하는 환경 하에서 서비스들을 효율적으로 관리하기 위해 Netflix OSS 기반으로 개발한 Service Discovery Server/Client로 구성
    - Serivce가 Scale out/in 될 때 변경되는 주소를 호출하는 입장에서 다 알수가 없기 때문에 Eureka를 사용
    - Java로 개발된 Netflix OSS 기반 Library는 Spring Cloud의 Libaray와 통합되어 Spring Cloud-Eureka로 적용됨
      - `Eureka Server` : Eureka Service가 자기 자신을 등록(Service Registration)하는 서버이자 Eureka Client가 가용한 서비스 목록(Service Registry)을 요청하는 서버
      - `Eureka Client` : Service의 위치 정보를 Eureka Server로부터 Fetch 하는 서비스
    - SpringBoot 위에다가 Eureka를 Add-on 해주면 자동으로 Eureka Zone을 만들고 Server Library를 만들고 나머지 서비스엔 Eureka Client를 추가하고 Eureka Zone을 명시해줌.
  - Eureka Server 적용하는 방법
    - Dependency 추가\
      `org.springframework.cloud:spring-cloud-starter-netflix-eureka-server`
    - Application Main에 `@EnableEurekaServer` 추가
    - application.yml에 설정 추가
      ```yml
      server:
        port: 8761 # default : 8761
      spring:
        application:
          name: eureka-server
      eureke:
        server:
          response-cache-update-interval-ms: 1000 # default : 30s
          enableSelfPreservation: false # develop mode
        client:
          register-with-eureka: false # develop mode
          fetch-registry: false # develop mode
          service-url:
            defaultZone: http://localhost:8761/eureka # default
        instance:
          prefer-ip-address: true # 각 서버별 접글을 IP로 하겠다는 의미
      ```
  - Eureka Client 적용하는 방법
    - Dependency 추가\
      `org.springframework.cloud:spring-cloud-starter-netflix-eureka-client`
    - Application Main에 @EnableEurekaServer 추가
    - application.yml에 설정 추가
      ```yml
      spring:
        application:
          name: service-B # 이 이름으로 eureka에 등록됨
      eureka:
        instance:
          prefer-ip-address: true #Eureka Server에 IP로 서비스 등록
        client:
          service-url:
            defaultZone: http://127.0.0.1:8761/eureka #Eureka Server 주소 입력
      ```
    - Eureka를 사용하면 Ribbon 의존성과 설정 항목을 삭제해야함.
    - Eureka Server에 Client를 연결한 화면
      <img width="1717" alt="image" src="https://user-images.githubusercontent.com/21374902/175874191-0292b9ec-d544-45c3-b515-f1b29cd86d7c.png">


---

- ## API Gateway
  - 다수의 Service로 구성된 Micro Service에서 각 Service들의 IP/PORT에 대한 단일화된 Endpoint 제공
  - 각 Service들에서 필요한 인증/인가, 사용량 제어, 요청/응답 변조 등의 기능을 대신 담당
  - 기능
    - Service Request 라우팅 기능
    - Service Load balancing
    - Service Request에 대한 endpoint 딘알화
    - Service Mesh와 연계를 통한 장애 대응 기능
    - service Filtering 기능
    - Authentication / Authorizing 기능
    - Logging / Monitoring 기능
  - Open API는 3개로 구성되어 있다.
    - API Gateway
    - APIM
    - Portal
  - 내부 Service 사이에서 관리(분배, 재활성화 ...)하는 것은 `Service Mesh`이고 외부 자원에 대해서 관리하는 것은 `API Gatewway`라고 할 수 있다.
  - `API Gateway`는 `Eureka`에 등록된 `서비스명`으로 실제 서비스의 `주소`를 알아낼 수 있다.
  - 적용하는 방법
    - Dependency 추가\
      `org.springframework.cloud:spring-cloud-starter-gateway`
      `org.springframework.cloud:spring-cloud-netflix-eureka-client`
    - Application Main에 `@EnableEurekaClient` 추가
    - Eureka 관련 설정 추가 (eureka 설정 참고) 및 아래 항목 추가
      ```yml
      spring:
        spplication:
          name: sample-apigateway
        cloud:
          gateway:
            globalcors:
              cors-configurations:
                '[/**]':
                  allow-credentials: true
            routes:
            - id: sample-service        #routing에 사용할 ID
              url: lb://sample-service  #[load balancing]://[eureka에 등록한 서비스]
              predicates:
              - Path= /sample/service/** #[api gateway 주소 뒤에 올 서비스명]
            - id: sample-service2        #routing에 사용할 ID
              url: lb://sample-service2  #[load balancing]://[eureka에 등록한 서비스]
              predicates:
              - Path= /sample/service2/** #[api gateway 주소 뒤에 올 서비스명]
      ```
  - Gateway - Client 매핑 방법
    - Client은 Eureka Server에 client로서 등록되고 Application ID 값을 부여 받는다.
    - Application ID은 Client의 `spring.application.name` 값과 동일하다.
    - Gateway에 routes를 설정할 때 `uri: lb://FDK`을 설정하는데 Eureka에서 Application ID 값과 `lb://` 뒤에 있는 값을 매핑해서 라우팅을 해주게 된다.

---

- ## SAGA
  - MSA에선 각 Service와 Database가 분리되어 있다.
  - 한 서비스에 CUD가 발생을 했을 때, 다른 서비스에 있는 데이터와 반드시 싱크되어 있어야 한다고 가정했을 때 아래와 같이 처리할 수 있다.
    1. `Two-Phase Commit` : A 서비스에서 B 서비스에 할당된 Database에 직접 붙어서 데이터를 조작하는게 일반적이었다.
    2. `SAGA` : 서비스 간에 트랜잭션을 묶어주는 개념도 있었다.
  - 하지만 MSA의 가장 중요한 원칙 중 하나가 N2PC(Not two phase commit)이다.\
    한 서비스가 다른 서비스에 할당된 DB에 직접 처리를 하는 것 (2단계 Commit)을 하지 않는 것이다. 이를 처리하기 위해서 `SAGA Pattern`이 등장한다.
  - SAGA Pattern 방식
    - 각 서비스 단위 트랜잭션은 해당 서비스에서 로컬 트랜잭션으로 관리하고 서비스간 트랜잭션은 보상 트랜잭션으로 처리
    - `Choreography` 방식(결자해지 방식)
      > Error가 발생한 서비스가 Rollback 처리 등을 직접 수행하며 다른 서비스에게 발생한 Error를 전파하면 각 서비스가 보상처리를 동작한다.\
      동기식 방식으로 서비스들은 Rest를 이용해서 Message를 주고 받는다.
    - `Orchestration` 방식 (3자 위임 방식, Event Sourcing 방식)
      > 제 3자가 있어서 A서비스가 한 것을 기록\
      A서비스가 B서비스로 내용 전달\
      B서비스가 CUD하다가 에러났다고 기록\
      제 3자가 B가 실패한것을 보고 A서비스쪽에 에러 처리할 수 있도록 동작\
      비동기식 방식으로 서비스들은 Parallel Queue를 이용하고 Message는 제 3자인 Queue가 관리한다.

---

- ## Kafka
    - Apache Kafka는 분산 스트리밍 플랫폼이며 데이터 파이프 라인을 만들 때 주로 사용하는 오픈소스 솔루션
    - Kafka는 대용량의 실시간 로그 처리에 특화되어 있는 솔루션이며 데이터를 유실 없이 안전하게 전달하는 것이 주목적인 메세지 시스템에서 fault-tolerant한 안정적인 아키텍쳐와 빠른 퍼포먼스로 데이터를 처리하는 용도로 사용
    - Apache Kafka
      - Publisher/Subscriber Model
        - 데이터 큐를 중간에 두고 서로 간 독립적으로 데이터를 생산하고 소비하는 모델.
        - 이런 느슨한 결합을 통해 Publisher나 Subscriber가 변경이나 장애시에도 서로 간에 의존성이 없으므로 안정적인 데이터 처리 가능.
        - Pub/Sub가 죽어도 메모리에 메세지는 남아있다.
      - 고가용성(High Availability) 및 확장성 (Scalability) 보장
        - Kafka는 클러스터로 작동하기 때문에 fault-tolerant한 고가용성 서비스를 제공할 수 있고 분산 처리를 통해 빠른 데이터 처리를 가능하게 합니다. 또한 서버를 수평적으로 늘려 안정성 및 성능을 향상시키는 Scale-out이 가능
      - 디스크 순차 저장 및 처리(Sequential Store and Process in Disk)
        - 메세지를 메모리 큐에 적재하는 기존 메세지 시스템과 다르게 카프카는 메세지를 디스크에 순차적으로 저장
        - 서버에 장애가 나도 메세지가 디스크에 저장되어 있으므로 데이터 유실이 상대적으로 적음
        - 디스크가 순차적으로 저장되어 있으므로 디스크 I/O가 줄어들어 성능이 향상됨
      - 분산처리 (Distributed Procssing)
        - Kafka는 Partition 이란 개념을 도입하여 여러 개의 Partition을 서버들에 분산시켜 나누어 처리할 수 있음. 이로서 메세지를 상황에 맞추어 빠르게 처리할 수 있음.
      - `Zookeeper`는 Kafka 앞단에서 클러스터링을 해주는 솔류션인데 Kafka와 같이 쓰인다.
      - `RabbitMQ`는 Message를 메모리에 저장해서 전달해준다.
    - Kafka Publisher 적용 방법
      - Dependency 적용\
        `org.springframework.kafka:spring-kafka`
      - application.properties에 BootStrapAddress 추가(kafka 서버주소)\
        ```yml
        #kafka
        kafka.bootstrapAddress=localhost:9092
        ```
      - KafkaProducerConfig 구현
        ```java
        @Configuration
        public class KafkaProducerConfig {
            @Value(value = "${kafka.bootstrapAddress}")
            private String bootstrapAddress;

            @Bean
            public ProducerFactory<String, TransferHistory> transferProducerFactory() {
                Map<String, Object> configProps = new HashMap<>();
                configProps.put(ProducerConfig.BOOTSTRAP.SERVERS_CONFIG, bootstrapAddress);
                configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
                configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
                return new DefaultKafkaProducerFactory<>(configProps);
            }

            @Bean
            public Kafka Template<String, TeransferHistory> transferKafkaTemplate() {
                return new KafkaTemplate<>(transferProducerFactory());
            }
        }

        // Prodcuer Class 구현
        @Autowired
        private KafkaTemplate<String, TransferHistory> transferKafkaTemplate;

        @Value(value = "${b2b.transfer.topic.name}")
        private String b2bTransferTopicName;

        public void sendB2BTransferMessage(TransferHistory transfer) {
            ListenableFuture<SendResult<String, TransferHistory>> future = transferKafkaTemplate.send(b2bTransferTopicName, transfer);

            future.addCallback(new ListenableFutureCallback<SendResult<String, TransferHistory>>() {
                @Override
                public void onSuccess(SendResult<String, TransferHistory> result) {
                    TransferHistory g = result.getProducerRecord().value();
                    LOGGER.info("Send message=[" + g.getCstmId() + "] with offset = [" + result.getRecordMetadata().offset() + "]");
                }

                @Override
                public void onFailure(Throwable ex){
                    // needed to do compensation transaction.
                    LOGGER.error("Unable to send message=[" + transfer.getCstmId() + "] due to : " + ex.getMessage());
                    throw new SystemException("Kafka 데이터 전송 에러");
                }
            })
        }
        ```
    - Kafka Consumer 적용 방법
      - Dependency 추가\
        `org.springframework.kafka:spring-kafka`
      - application.properties 에 BootStrapAddress 추가(Kafka 서버 주소)
        ```properties
        #kafka
        kafka.bootstrapAddress=localhopst:9092
        ```
      - KafkaConsumerConfig 구현
        ```java
        @EnableKafka
        @Configuration
        public class KafkaConsumerConfig {
            @Value(value="${kafka.bootstrapAddress}");
            private String bootstrapAddress;

            public ConsumerFactory<String, TransferHistory> b2bTransferResultConsumerFactory() {
                Map<String, Object> props = new HashMap<>();
                props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFOIG, bootstrapAddress);
                props.put(ConsumerConfig.GROUP_ID_CONFIG, "b2btransfer");
                props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
                props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
                props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");

                return new DefaultKafkaConsumerFactory<>(props, new StringDeserializer(), new JsonDeserializer<>(TransferHistory.class, false));
            }

            @Autowired
            TransferProducer transferProducer;

            @KafkaListener(topics = "${b2b.transfer.result.topic.name}", containerFactory = "b2bTransferResultKafkaListenerContainerFactory")
            public void b2bTransferResultListener(TransferHistory transferResult, Acknowledgment ack) throws Exception {
                LOGGER.info("Received B2B transfer result message : " + transferResult.getWthdAcntNo());

                String statusCode = transferResult.getStsCd();
                // ...

                transferService.createTransferHistory(transferResult);
                // CQRS
                transferProducer.sendCQRSTransferMessage(transferResult);

                ack.acknowledge(); // 모든 CRUD 작업이 완료되어야만 kafka의 read off-set 값을 변경하도록 한다.
            } catch(Exception e){
                String msg = "Error";
                LOGGER.error(msg, e);
                throw new SystemException(msg);
            }

            @Bean
            public ConcurrentKafkaListenerContainerFactory<String, TransferHistory> b2bTransferResultKafkaListenerContainerFactory() {
                ConcurrentKafkaListenerContainerFactory<String, TransferHistory> factory = new ConcurrentKafkaListenerContainerFacotry<>();
                factory.setConsumerFactory(b2bTransferResultConsumerFactory());
                factory.setContainerProperties().setAckMode(AckMode.MANUAL_IMMEDIATE);
                factory.setErrorHandler(new SeekToCurrentErrorHandler());

                return factory;
            }
        }
        ```

---

- ## OpenFeign
  - Circuit Breaker를 RestTemplate + Hystix로 구현했는데 OpenFeign은 한번에 해결할 수 있다. 동기식이다.
  - OpenFeign 적용 방법
    - Dependency\
      `org.springframework.cloud:spring-cloud-starter-openfeign`
    - Application Main에 `@EnableFeignClients` 추가
    - application.properties에 feign 속성추가
      ```properties
      feign.hystrix.enabled = true
      # 별도 feign client에 이름을 지정하지 않고 default로 지정해서 모든 클라이언트에 동일한 속성 사용
      # connection try time out 설정 30초
      feign.client.config.default.connet-timeout=30000
      # connection read timeout(최대 작업 시간) 설정 30초
      feign.client.config.default.read-timeout=30000
      ...
      ```
    - Interface 구현
      ```java
      @FeignClient(name = "sample", url = "http://localhost:8070", fallbackFactory = SampleFeignClientFallbackFactory.class)
      public interface SampleFeignClient {
          @GetMapping("/sample/sample/list/rest/v1.0/{id}")
          List<Sample> retrieveSampleList(@PathVariable("id") String id);
      }

      // fallback 구현
      @Component
      class SampleSignClientFallbackFactory implements FallbackFactory<SampleFeignClient>{
        @Override
        public SampleFeignClient create(Throwable t){
            return new SampleFeignClient(){
                private final Logger LOGGER = LoggerFactory.getLogger(SampleFeignClientFallbackFactory.class);

                @Override
                public List<Sample> retrieveSampleList(String id) throws Exception {
                    // 외부 통신 에러시 필요한 후속 조치를 여기서 설정
                    String msg = "FeignClient를 이용한 " + id + " 사용자의 리스트 호출에 문제가 있습니다.";

                    LOGGER.error(msg, t);
                    throw new Exception();
                    }
                };
            }
        } 
        ```

---

- ## API Composition
  - 서비스마다 다른 Database를 갖고 있는데 UI 화면은 1개이기 때문에 화면엔 통일된 데이터를 보여줘야 한다.
  - 서비스 앞단에서 데이터를 Composition해서 UI로 통일된 데이터를 전달한다.
  - 혹은 A라는 서비스가 B,C 서비스로 데이터를 동기화(데이터 변경에 대해서 알려줘야 한다.)

---

- ## CQRS
  - CQRS : Command Query Responsibility Segregation
  - 서비스가 쪼개져 있는데 각 서비스에 종속되어 있는 데이터를 JOIN 해서 보여줘야하는 경우엔 API Composition에서 처리할 수 있겠지만 복잡하기 때문에 CQRS 패턴을 사용해서 처리할 수 있다.
  - 각 서비스 DB를 Queue에 
  서비스 A에 CUD가 발생하면 본인 DB에 CUD하고 그 내용을 Queue에 전달
  별도로 있는 CQRS DB(집계성 DB)는 Queue를 읽어서 데이터를 총 취합하고 UI는 CQRS 서비스를 통해서 JOIN 데이터를 조회할 수 있다.

--- 

- ## 부록 
  - Monolithic Architecture : 기존 방식의 아키텍처
  - CBD : Component Based Development
  - SOA : Service Oriented Architecture
  - 보상 트랜잭션 & SAGA 패턴
    - 보상 트랜잭션
      - Orchestration : 로컬 트랜잭션을 지시하는 Command 기반 방식.
      - 기존과 비슷한 방식으로 한개의 서비스가 동작하고 결과를 저장.
      - 실패 시 보상트랜잭션을 발생.
    - SAGA 패턴
      - Choreography : 참가 서비스들이 Message Broker/Queue를 이용해서 Message로 Event를 주고 받으며 실행 여부를 결정
      - Local Transaction 처리 후 결과를 Event로 발생(Publish)시켜서 구독(Subscribe)하고 있는 서비스들이 프로세스 처리를 이어가도록 함.

  - 분산처리를 위한 API Composition and CQRS
    - API Composition\
      여러 서비스의 API 호출 결과를 조합해서 Response 하는 방식.
      각 서비스의 API 결과를 기록하고 기록한 것들을 종합해서 처리.
      예약현황 조회 서비스는 예약 서비스와 승인 서비스가 만든 결과를 종합해서 처리
    - CQRS (Command Query Responsibility Segregation)\
      저장과 조회의 책임을 분리하는 패턴
      예약현황 조회 서비스, 예약 서비스, 승인 서비스를 만들어뒀을 때 예약은 예약만하고 결과를 예약현황 조회 서비스에 기록. 예약현황 조회는 자신의 DB를 조회하여 결과 출력 

  - Service Mesh
    - Sidecar Pattern (Circuit breaker)\
      Sidecar Proxy : 공통기능 갖고 있을 수 있음. LB 기능, 컨테이너가 응답을 안하면 대신 에러응답을 뱉어줄 수 있음.

  - Data Backing
    - 데이터를 다루는 서비스로 데이터베이스와 캐쉬 시스템 등을 의미
    - MSA 환경에서 Container는 Stateless 속성을 갖고 있기 때문에 Container가 삭제되면 데이터가 모두 삭제될 수 있음. 따라서 volumn을 할당해서 외부에 저장 및 사용

  - Session 관리
    - Cookie-based\
      Redis와 같이 cookie에 사용자 정보 등을 갖고 있어 요청을 받았을 때 사용자 세션을 인지
    - sticky sessionss\
      일반적으로 cookie를 참조해서 redis가 같은 서버로 보내주도록 설정.
      만약에 서버가 다운되서 session을 받을 수 없는 상태라면 세션 데이터는 날아갈것이다.
      이를 해결하기 위한 2개의 전략이 있음.
      1. Load Balancer가 죽은 서버로 부터 받은 요청을 다른 서버로 돌려줌.
      2. RDBMS나 NoSQL Database 같은 세션 드라이버를 이용해서 세션을 해결.

    - non-sticky sessions\
    Message Backing : 분산 시스템간 주고 받는 메세지로 Message Queue 시스템 기반으로 브로커를 통해 비동기 방식으로 커뮤니케이션함. 동시 다발적으로 요청이 발생하면 부하를 발생시키지만 큐 시스템은 순차적으로 처리해서 이를 방지함.
  - Telemetry
    - logging : 중앙 로그 수집.
    - tracing : 추적관리. 발생한 event 순으로 나열해서 보여줌.
    - monitoring
  - CI/CD\
    - Continuous Integration\
    - Continuous Delivery\
    - Continuous Deployment

  - Spring 구동할 때마다 DDL, DML 자동실행
    - sql 파일 경로
    ```
    src/main/resource/sql/CUSTOMER_DDL.sql
    src/main/resource/sql/CUSTOMER_DML.sql
    ```
    - application.properties
    ```properties
    spring.datasource.schema = classpath:sql/CUSTOMER_DDL.sql
    spring.datasource.data = classpath:sql/CUSTOMER_DML.sql
    spring.datasource.initialization-mode = always
    ```
  - MSA 구조에선 각 서비스가 별도의 DB를 갖기 때문에 DAO 보단 repository라고 쓴다.\
  Mybatis + SqlMapper OR ORM + Hibernate + JPA

  - @Mapper를 사용해서 별도의 쿼리 작성 없이 Entity로 바로 CURD 실행
    ```java
    @Mapper
    public interface CustomerRepository {
        // 함수 이름을 SqlMapper에 id로 사용하면 된다.
        int insertCustomer(Customer customer) throws Exception;
        int deleteCustomer(Customer customer) throws Exception;
        Customer selectCustomer(Customer customer) throws Exception;
        int existsCustomer(Customer customer) throws Exception;
    }
    ```
  - West to East 사이에 각 서비스들이 호출하는 룰을 정하는 것이 Serice Mesh 이다.
  - Backing Service : DBMS - Persistence, Cache, Queue
---

- ## 프로젝트 구현
  - https://github.com/justdoanything/PaymentApprovalServer
  - 결제 승인 서버를 MSA로 구현해보고 Hystrix, Ribbon, Eureka, Kafka 등을 적용해본다.
  - 배포는 Docker Container 기반으로 되도록 한다.
  - 프로젝트 구현은 한 번에 모든 기술을 적용하지 않고 Hystrix부터 한개씩 차례대로 적용해보면서 각 기능이 하는 동작과 이점에 대해서 알아본다.
  - 테스트 자동화 코드까지 적용해본다.