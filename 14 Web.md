Web
===

# 목차
<!-- TOC -->
* [Web](#web)
* [목차](#목차)
* [Web Server And WAS](#web-server-and-was)
* [REST API](#rest-api)
* [TCP / UDP / HTTP](#tcp--udp--http)
* [Restful API](#restful-api)
* [PWA (Progressive Web App)](#pwa--progressive-web-app-)
* [Web App 비교](#web-app-비교)
* [BFF (Backend for Frontend)](#bff--backend-for-frontend-)
* [Micro Frontend](#micro-frontend)
<!-- TOC -->

Web Server And WAS
===
- #### Web Page의 구분
    - 정적 페이지(Static Page)
        - Web Server는 경로를 받아서 contents를 응답한다.
        - Image, html, css, javascript 와 같은 서버에 저장되있는 파일 등을 응답한다.
    - 동적 페이지(Dynamic Page)
        - Parameter에 맞는 contents를 선택적으로 응답한다.
        - Server에서 실행되는 프로그램이 만들어진 결과를 응답한다.   
          ![image](https://user-images.githubusercontent.com/21374902/150668577-2a92a4d7-f028-40c2-aac9-90cd2bf8bc1b.png)
- #### Web Server
    - ###### Web Server의 정의
        - Hardware : Web Server가 설치되어 있는 Hardware
        - Software : Client(Web Browser)로부터 HTTP 요청을 받아 정적 컨텐츠`(Static Contents)`를 제공하는 Program
    - ###### Web Server의 기능
        - HTTP Protocol 기반으로 Client의 요청을 처리하는 기능
        - 기능1 : WAS를 거치지 않고 바로 `Static Contents` 등을 제공한다
        - 기능2 : `Dynamic Contents` 제공을 위한 `요청을 전달`한다.
          Client의 요청을 WAS에 보내고 WAS가 처리한 결과를 Client에게 반환한다.
    - ###### 대표적인 예 : Apache Server, Nginx, IIS
- #### WAS (Web Application Server)
    - ###### WAS의 정의
        - 여러 로직을 포함한 `Dynamic Contents를 제공`하기 위해 만들어진 Application Server
        - HTTP를 통해 컴퓨터나 장치에 애플리케이션을 수행해주는 미들웨어(Software Engine)
        - Web Container, Servlet Container라고도 불린다.
          _`Container는 Servlet를 실행할 수 있는 S/W로 JSP, Servlet 구동 환경을 제공한다.`_
    - ###### WAS의 기능
        - WAS = Web Server + Web Container
        - 분산 트랜잭션, 보안, 메시징, 쓰레드 처리 등 분산 처리 환경에서 Web Server의 기능들을 구조적으로 분리하여 처리하고자하는 목적
        - 기능1 : 프로그램 실행 환경과 DB 접속 기능 제공
        - 기능2 : 여러 개의 트랜잭션(논리적인 작업 단위) 관리 기능
        - 기능3 : 업무를 처리하는 비즈니스 로직 수행
    - ###### 대표적인 예 : Tomcat, JBoss, Jeus, Web Sphere
![image](https://user-images.githubusercontent.com/21374902/150668615-2fcefac4-fd16-462c-842a-4bd74aa97831.png)
![image](https://user-images.githubusercontent.com/21374902/150669867-07ca2a0f-73ae-4388-91ad-4a46803cc660.png)
- #### Web Server와 WAS를 구분하는 이유
    - Browser(Client)에 Image(Static Content)가 보여지는 과정을 예로 들면,
        - 최초에 HTML 파일이 Client에게 전달될 때 Image 파일들이 동시에 전달되지는 않는다.
        - Client가 HTML 파일을 Load하면서 필요한 파일들을 Server로 요청하면 그때 Image가 전달된다.
        - Web Server는 이러한 Static Contents를 갖고 있으면서 Client의 요청이 왔을 때 그 요청을 Application 단으로 전달하지 않고 바로 보내준다.
    - 따라서 Web Server는 Static Content를 빠르게 처리하면서 기능을 분배하여 Server의 부담을 줄일 수 있다.
    - Web 페이지는 Client의 요청에 따라 동적인 컨텐츠를 반환해주기도 해야하는데 DB를 읽어오거나 특정 로직을 적용해서 데이터를 반환해야하는데 이를 모두 Web Server에 다 만들어놓고 반환하려면 자원이 부족하기 때문에 동적인 컨텐츠는 WAS에서 처리한다.
- #### Web Server와 WAS를 나누면서 얻는 이점
    - ###### 기능을 분리하면서 서버 부하 방지
        - WAS는 DB 처리, 다양한 로직을 처리한 후 동적 처리
        - Web Server는 정적인 컨텐츠를 빠르게 응답
    - ###### 물리적으로 분리하여 보안 강화
        - Web Server가 앞단에서 SSL에 대한 암복화 처리나 White/Black List 관리
    - ###### 여러개의 WAS를 사용할 때 유용
        - Web Server가 Load Balancing 기능을 함
        - 장애 극복(Fail Over), Fail Back 처리에 유리
        - WAS만 재부팅 하는 등 무중단 배포가 가능
        - PHP Applaction과 JAVA Application을 함께 사용할 수 있음
- #### Web Server와 WAS를 같이 사용했을 때의 처리과정
    - WEB은 `Client - Web Server - DB` 만 쓸수도 있고 `Client - WAS - DB` 형태로 쓰거나 `Client - Web Server - WAS - DB` 형태로 사용할 수 있다.
    - `Client - Web Server - WAS - DB` 형태로 처리하는 과정은 아래와 같다.
        - Client가 보낸 HTTP 요청을 Web Server가 받고 WAS로 전달한다.
        - WAS는 Servlet을 Memory에 올려둔다.
        - WAS는 web.xml을 참조하여 해당 Servlet에 대한 Thread를 생성한다.
        - `HttpServletRequest`와 `HttpServletResponse` 객체를 생성하여 Servlet에 전달한다.
        - Thread는 Servlet의 service() method를 호출하고 호출되는 method에 맞게 생성된 Reponse를 객체에 담아 WAS에 전달한다.
        - WAS는 Response 객체를 HttpResponse 형태로 바꾸어 Web Server에 전달한다.
        - 생성된 Thread를 종료하고, HttpServletRequest와 HttpServletResponse 객체를 제거한다.
- #### 별첨. Spring 수행 과정
    - Servlet과 Servlet Class, URL를 연결
    - DispatcherServlet : Spring Container를 생성 및 관리
    - Client의 요청이 들어오면 DispatcherServlet이 해당하는 Servlet에 대한 Thread를 생성 및 실행하면 HttpsServletRequest, HttpsServletResponse가 생성되고 Servlet에 전달된다.
    - 실행된 Thread는 Servlet에 해당하는 service method 를 호출하고 정해진 로직을 수행된 후에 HttpsServletResponse를 만들어서 전달
    - Lifecyle에 따라 생성했던 객체와 Thread 종료 및 제거
      ![image](https://user-images.githubusercontent.com/21374902/150670026-239d1d74-aeaa-4579-93de-a8723c7b4886.png)


- Reference
    - https://gmlwjd9405.github.io/2018/10/27/webserver-vs-was.html
---


REST API
===
- ## REST 란?
    - REST(`Representational State Transfer`)는 네트워크 기반 아키텍처로 리소트 상태(`Resource State`)의 표현 (`Representation`)을 전송(`Transfer`)하는 방식에 대해 정의한 것이다.
    - 각 리소스에 대한 URI를 부여하고 해당 리소스에 대한 CRUD 작업을 `POST`, `GET`, `PUT`, `DELETE`와 같은 HTTP 메소드를 이용해 처리하는 것이다.
    - Client에게 제공하고자 하는 리소스를 먼저 정의하고 해당 리소스에 대한 CURD 기반의 Operation을 정의한다.
    - HTTP 메소드 종류
      값 | 의미  
      ---|:---:
      `GET` | 정보 조회
      `PUT` | 정보 생성
      `POST` | 정보 변경
      `DELETE` | 정보 삭제
    - Customer 예제
        - URI : `/customers`
            - GET : Get all customers
            - PUT : -
            - POST : Add a new customer
            - DELETE : -
        - URI : `/customers/{id}`
            - GET : Get details of the customer
            - PUT : Update the customer
            - POST : -
            - DELETE : Delete the customer
        - URI : `/customers/{id}/orders`
            - GET : Get all orders for the customer
            - PUT : -
            - POST : Add an order to the customer
            - DELETE : Cancel all orders for the customer
- ## REST API 설계 원칙
    - #### Uniform Interface
        - `리소스 기반의 표현` : 각 리소스는 고유의 식별자인 URI를 가져야 한다.
        - `표준 HTTP 메소드 사용` : CURD 기준으로 POST, GET, PUT, DELETE 메소드를 사용한다.
        - `자기 서술성(Self-Descriptiveness을 통한 직광성 확보` : 리소스 이름, URI, HTTP 메소드, 데이터 형식만 보고도 직관적으로 무엇을 하는 API인지 이해할 수 있도록 설계해야 한다.
        - `API와 Client 간의 느슨한 결합(Loosely Coupling)` : 특정 기술, 플랫폼, 개발언어에 종속적이지 않아야 한다.
    - #### Stateless
        - HTTP 요청에 대한 상태는 저장하지 않으며 정보는 리소스 자체에만 저장되어야 한다.
        - API는 별도의 상태 정보를 관리하지 않고 요청 메세지만 사용해서 비지니스 로직을 처리하면 되기 때문에 구현을 단순화할 수 있다.
    - #### Cache
        - REST API는 HTTP 기반으로 동작하기 때문에 HTTP에서 제공하는 Cache 기능을 사용할 수 있다.
        - 조회(GET) 성격의 API를 설계할 때는 Cache 관련 HTTP 헤더를 이용해서 구현하여 사용하는 것을 권장한다.
        - HTTP/1.1 기반 `ETag` 헤더를 사용한 Cache
            - Client → REST API : GET | /customers/1234
            - Client ← REST API : 200 OK | / ETag : dyddnrhdck
            - Client → REST API : GET | /customers/1234 | If-None-Match: "dyddnrhdck"
            - Client ← REST API : 304 Not Modified
    - #### Client-Server Architecture
        - Server는 비지니스 로직을 처리할 수 있는 API를 제공한다.
        - Client는 API를 호출하기 위한 인증 정보, Context 등을 직접 관리한다.
        - 이러한 구조는 Server와 Client 간의 `의존성을 낮출 수 있다`.
    - #### Stratification
        - Client는 정해진 스펙에 맞게 API를 호출하면 된다. 대상 Server를 직접 호출하는건지 중간 Server를 거치는건지 상관할 필요가 없다.
        - 반면에 Server는 여러 계층 구조로 아키텍처를 구성할 수 있다.
        - WAS 앞 단에 인증/인가, 암호화, 공유 캐시, 로드 밸런싱 등의 기능을 제공하는 서버를 추가할 수 있다.
        - API Gateway와 같은 `APIM(API Management)` 시스템은 REST API의 계층화 특성을 대표하는 솔루션 중 하나이다.
- ## Resource 및 URI 설계
    - #### Resource 설계
        - REST API는 리소스 중심으로 설계되며 각 리소스마다 고유하게 식별할 수 있는 URI가 존재해야 한다.
        - 단일 리소스 : /customers, 상태 정보를 가지며 0개 이상의 서브 리소스를 가짐
        - 컬렉션 리소스 : /customers/12/phoneNumbers, 동일한 타입의 리소스 목록을 포함하며 컬렉션 내의 특정 리소스 구분자로 {id} 사용
        - `API를 통해 제공하고자 하는 비즈니스 엔터티에 집중해야 한다.`
            - 특정 업무 요건에서 제공해야 할 엔터티 및 해당 엔터티에서 수행할 수 있는 작업을 모델링 해야 한다.
            - 여기서의 엔터티를 단일 물리적 데이터 항목과 혼돈해선 안된다.
            - Customer Management라는 리소스는 내부적으로 여러 개의 테이블로 구현되어 있지만 실제 Client에게는 하나의 비지니스 엔터티로 표시해 제공되어야 한다.
        - `단일 API 요청을 통해 필요한 정보를 검색할 수 있도록 리소스 결합을 고려해야 한다.`
            - 리소스를 너무 작게 나눌 경우 하나의 비즈니스를 처리하기 위해서 여러 개의 API를 호출해야 한다.
            - 반면 너무 크게 설계하면 API의 재사용성이 떨어질 수 있다. Client 입장에서 API의 사용성을 고려하는 것이 필요하다.
    - #### URI 설계
        - 자기 서술성을 통한 직관성을 확보하고 리소스 모델을 잘 전달할 수 있는 구조로 설계해야 한다.
        - URI 기본 포맷은 `RFC 3986`에서 정의한 기본 문법을 준수한다.\
          `URI = scheme "://" authority "/" path [ "?" query ] [ "#" fragment ]`
            - scheme : URI Instance를 초기화하는데 사용되는 타입 반환
            - authority : Server의 DNS hostname 또는 IP 주소 및 포트 번호
            - path : 해당 자원의 위치 경로를 순차적으로 의미
            - query : 특정 값 전달
            - fragment : 특정 계층에서 보조 리소스 검색 시 사용
            - 얘제 : https "://" openapi.naver.com "/" map "?" city=seoul
- ## 명명 규칙
    - API 이름은 `명사`를 사용해야하며 API의 목적을 명확하게 나타낼 수 있는 `특화된 이름`을 사용한다.
    - URI를 구성하는 각 리소스는 `명사`를 사용해야 하며 `Cacel Case`를 사용한다.
    - Collection 성격의 리소스를 `복수 명사`를 사용한다.
    - URI 구성 시 CRUD 성격의 메소드 이름 `create`, `retrieve`, `update`, `delete`, `get`, `set` 등의 동사를 포함하면 안된다.
    - 계층 관계를 나태내기 위해 구분자로 `"/"`를 사용한다. 마지막 문자에 `"/"`는 포함하지 않는다.
    - URI 가독성을 높이기 위해 특수문자는 사용하지 않고 경우에 따라 `"-"`는 사용해도 된다.
    - URI 길이 제한은 없지만 `가독성`을 고려해야 하고 5단계를 넘지 않는 것이 좋다.
- ## CRUD 기반 HTTP 요청 메소드 정의
  메소드 | 설명 | 응답코드 | 에러코드
    ---|---|---|---
  `GET` | _Read Operation_ | 200 OK | 404 Not Found
  `PUT` | _Create Operation_ | 201 Created | 400 Bad Request
  `POST` | _Update Operation_ | 200 OK, 204 No-Content | 409 Conflict
  `DELETE` | _Delete Operation_ | 204 No-Content | 404 Not Found
  `HEAD` | 응답 헤더를 조회할 때 사용 | 200 OK |
  `OPTIONS` | Allow 응답 헤더를 이용해 리소스에서 사용 가능한 메소드를 표기하는 용도 | 200 OK |
    - CRUD 성격으로 구분할 수 없는 경우엔 `POST`를 사용한다.
    - `HEAD`는 GET 요청을 통해 특정 리소스를 조회하기 전에 `결과 데이터 크기를 파악`하고 싶을 때 HEAD 메소드를 사용하면 된다. 응답 헤더의 `Content-Length` 값을 알면 데이터 크기를 알 수 있다.
- ## 주의사항
    - #### GET, POST 메소드를 이용한 터널링 금지
        - GET /customerBillManagement/customerBill?~~method=update~~&id=8297\
          → 조회성 메소드에서 실제 메소드인 update를 전달하면 안된다.
        - POST /customerBillManagement/customerBill\
          { ~~"skill":"getCustomerBill"~~, "id":"8297" }\
          → 조회 성격의 Operation을 POST로 구현하면 안된다.
    - #### POST 메소드를 사용 시 멱등성(Idempotent) 고려
        - CRUD 성격으로 매핑이 불가능한 Operation의 경우 POST 메소드를 사용하는데 반드시 멱등성(Idempotent)를 고려해야 한다.
        - 예를들어 DELETE 메소드를 여러번 호출하면 응답코드는 200 OK → 404 Not Found로 변하지만 리소스가 삭제된 결과는 동일하다. GET, PUT, DELETE도 멱등성이 보장되는 메소드이다.
        - 하지만 조회 수를 1 증가시키는 POST Operation은 멱등성이 보장되지 않는다.
        - POST 사용 시 프레임워크와 같은 공통 모듈 단에서 멱등성을 보장할 수 있는 별도의 트랜잭션 처리를 고려해야 한다. 예를 들었던 조회수 1을 증가는 Operation은 처리 중 오류가 발생하면 1을 감소시키는 작업을 해줘야 한다.

- ## API Versioning
    - #### Version Format
        - APIM에 API를 등록한다거나 API 스펙 문서를 관리할 때 버전 정보를 사용한다.
        - Format : `MAJOR`.`MINOR`.`REVISION`
            - 기존 버전과 호환이 되지 않게 API가 변경된 경우 `MAJOR` 버전을 올린다.
            - 기존 버전과 호환이 되면서 새로운 기능이 추가된 경우 `MINOR` 버전을 올린다.
            - 기존 버전과 호환이 되면서 버그를 수정한 경우 `REVISION` 버전을 올린다.
    - #### URI Version
        - URI 상에 버전 정보를 관리할 때 사용한다. Version format에서 `MAJOR` 정보만 이용하며 `접두사 v`를 사용한다.
        - Format : `{serverRoot}`/`{apiName}`/`{apiVersion}`/`{subResource}`
            - serverRoot : 호스트명 또는 IP/PORT
            - apiName : API 이름
            - apiVersion : API 버전 (v1, v2, ...)
            - subResource : API에 속해있는 하위 리소스명
        - 기존 버전과 새로운 버전을 분리해서 여러 버전을 동시에 사용할 수 있다.
        - API Lifecycle : 특정 시점에 특정 버전을 `사용안함(deprecated)` 상태로 만들었다가 `폐기(retired)`상태로 만들어서 `EOL(End of Life)` 공지 후 `삭제(remove)`
    - #### 하위 호환성
        - 버전 변경 시 반드시 하위 호환성을 고려해야 한다.
        - 하위 호환성을 유지하면서 API가 변경되는 대표적인 경우
            - 서비스, 인터페이스, 필드, 메소드 삭제 또는 이름 변경
            - HTTP 바인딩 변경
            - URL 포맷 변경
            - 기 존재하는 요청에 대한 동작 변경
            - 새롭게 추가된 데이터 객체(또는 객체 내 세부 필드)에 대한 사용 여부 변경
            - 기존 데이터 필드에서 허용 가능한 값의 크기 증가
            - 열거 데이터형 사용 시 기존 항목의 유지된 상태에서 새로운 항목 추가, 삭제, 이름 변경
            - 기존 Operation이 유지된 상태에서 새로운 오퍼레이션 추가
            - Operation에서 사용하는 파라미터가 기존 것은 그대로 유지된 상태이고 새로 추가

---
TCP / UDP / HTTP
===
- ### IP (Internet Protocol)
    - IP 패킷은 우체국 송장처럼 전송 데이터를 무사히 목적지까지 전송하기 위해 출발지 IP, 목적지 IP와 같은 정보가 포함되어 있습니다.
    - 한계점
        - 비연결성 : 패킷을 받을 대상이 없거나 서비스 불능 상태여도 패킷을 전송
        - 비신뢰성 : 전송 과정에서 패킷이 사라질 수 있고 순서를 보장하지 않음

- ### TCP (Transmission Control Protocol)
    - 네트워크에서 데이터를 송수신할 수 있도록 IP 패킷을 사용하기 전에, TCP 데이터를 소켓에 담기고 그 데이터가 IP 패킷에 담겨 전송됩니다.
    - TCP Segment에는 IP 패킷의 출발지 IP/PORT, 목적지 IP/PORT, 전송 제어, 순서 등의 정보를 포함하고 있습니다.
    - IP 의 한계였던 데이터 전달과 순서를 보증 및 보장하고, TCP 3 way Handshake를 통한 연결 지향적 프로토콜로, 신뢰성 있는 프로토콜입니다
    - TCP 3 Ways Handshake
        - 클라이언트는 서버에 접속을 요청하는 SYN(Syncronize) 패킷을 보냅니다.
        - 서버는 SYN 패킷을 받고, 클라이언트에게 접속 요청을 수락한다는 ACK(Acknowledgment)와 SYN가 설정된 패킷을 발송합니다.
        - 클라이언트가 서버에게 ACK 요청을 보냅니다. 이후로 클라이언트와 서버간의 연결이 성립됩니다.
        - 데이터를 전송합니다.

<img width="763" alt="image" src="https://user-images.githubusercontent.com/21374902/184526572-79632bbe-7a80-4620-8b35-b973f9134499.png">

- ### UDP(User Datagram Protocol)
    - IP에 PORT, 체크섬 필드 정보만 추가된 TCP 보다 단순한 프로토콜입니다.
    - 신뢰성이 낮고 순서를 보증하지 않지만 빠른 속도를 보장합니다.
    - 비 연결지향이고 데이터 수신 여부를 확인하지 않습니다.

- ### HTTP (HyperText Transfer Protocol)
    - HTTP는 Server-Client 구조를 갖습니다.
    - 서버가 클라이언트의 상태를 보존하지 않는 무상태(Stateless) Protocol 입니다. 따라서 서버 확장이 용이합니다.
    - HTTP는 비연결성을 유지하기 때문에 최소한의 자원으로 서버 유지가 가능합니다.

- Reference
    - https://velog.io/@shitaikoto/CS-IP-TCP-UDP-HTTP

---
Restful API
===
- Restful API란?
- 
## 1. REST API 종류와 역할
메소드 | 설명                                         | 응답코드               | 에러코드
---|--------------------------------------------|--------------------|---
`GET` | 조회                                         | _Read Operation_   | 200 OK                 | 404 Not Found
`POST` | 정보 생성                                      | _Update Operation_ | 200 OK, 204 No-Content | 409 Conflict
`PUT` | 정보 변경 (속성 전체)                              | _Create Operation_ | 201 Created            | 400 Bad Request
`PATCH` | 정보 변경 (속성 일부)                              | _Create Operation_ | 201 Created            | 400 Bad Request
`DELETE` | 정보 삭제 | _Delete Operation_ | 204 No-Content     | 404 Not Found          
`HEAD` | 응답 헤더를 조회할 때 사용                            |                    | 200 OK                 |
`OPTIONS` | Allow 응답 헤더를 이용해 리소스에서 사용 가능한 메소드를 표기하는 용도 |                    | 200 OK             |
- CRUD 성격으로 구분할 수 없는 경우엔 `POST`를 사용한다.
- `HEAD`는 GET 요청을 통해 특정 리소스를 조회하기 전에 `결과 데이터 크기를 파악`하고 싶을 때 HEAD 메소드를 사용하면 된다. 응답 헤더의 `Content-Length` 값을 알면 데이터 크기를 알 수 있다.

## 2. PUT과 PATCH의 차이
- 간단히 요약하면 `PATCH`는 Resource에 일부분만 수정할 때 사용하고 `PUT`은 Resource의 모든 속성을 수정할 때 사용한다.
- `PUT`으로 요청할 때 Resource의 일부분만 보냈을 경우, 나머지는 기본값으로 수정되는게 원칙이다. 따라서 바꾸지 않을 속성도 같이 보내줘야 한다.
- `PATCH`는 요청한 일부분만 수정한다.

## 3. GraphQL과 REST API
- ### GraphQL의 등장 배경
  - RESTful API 로는 다양한 기종에서 필요한 정보들을 일일히 구현하는 것이 힘들었다.
  - 예를들어, iOS 와 Android 에서 필요한 정보들이 조금씩 달랐고, 그 다른 부분마다 API 를 구현하는 것이 힘들었다.
  - 이 때문에 정보를 사용하는 측에서 원하는 대로 정보를 가져올 수 있고, 보다 편하게 정보를 수정할 수 있도록 하는 표준화된 Query language 를 만들게 되었다.

- ### GraphQL과 REST의 차이점
  - REST는 하나의 Resource마다 하나의 Endpoint를 갖고있고 GraphQL은 주로 하나의 Endpoint에 거의 모든 것들를 담고 있다.
  - REST는 응답 구조가 정해져 있는 방면 GraphQL은 요청할 때 사용한 Query 문에 따라 응답의 구조가 달라진다.

- ### GraphQL의 장단점
  - GraphQL은 HTTP 요청 횟수를 줄일 수 있고 응답 크기를 줄일 수 있다.
  - GraphQL은 File 전송 등 Text 만으로 하기 힘든 내용들을 처리하기 복잡하다. (물론 GraphQL 에서 File 전송을 할 수 없는 것은 아니나, 일반적인 GraphQL API 에 비해서 복잡해지거나 외부의 Service 에 의존해야하는 등 문제가 발생한다.)
  - 고정된 요청과 응답만 필요할 경우에는 Query 로 인해 요청의 크기가 RESTful API 의 경우보다 더 커진다.
  - 재귀적인 Query 가 불가능하다. (결과에 따라 응답의 깊이가 얼마든지 깊어질 수 있는 API 를 만들 수 없다.)
- ### GraphQL과 REST 선택기준
  - ##### GraphQL
    - 서로 다른 모양의 다양한 요청들에 대해 응답할 수 있어야 할 때
    - 대부분의 요청이 CRUD(Create-Read-Update-Delete) 에 해당할 때
  - ##### REST
    - HTTP 와 HTTPs 에 의한 Caching 을 잘 사용하고 싶을 때
    - File 전송 등 단순한 Text 로 처리되지 않는 요청들이 있을 때
    - 요청의 구조가 정해져 있을 때
- Reference
    - https://www.holaxprogramming.com/2018/01/20/graphql-vs-restful-api/

---

PWA (Progressive Web App)
===

- `versus Native App`
- Web과 Native App의 기능을 모두 갖춘 Web-App으로 설치가 가능하고 오프라인에서도 동작한다.
- 웹앱의 완성도를 측정해주는 [lighthouse](https://developer.chrome.com/docs/lighthouse/overview/)
- PWA 핵심 원칙
    - 발견 가능 : 검색 엔진을 통해서 컨텐츠를 찾을 수 있다.
    - 설치 가능 : 기기의 홈 화면에 설치해서 사용할 수 있다.
    - 연결 가능 : URL을 전송해서 공유할 수 있다.
    - 네트워크 독립적 : 오프라인 환경에서 동작한다.
    - 점진적 : 브라우저의 기본 기능을 사용할 수 있다. (최신 브라우저 기능은 제약될 수 있음)
    - 재참여 가능 : 새 컨텐츠가 사용 가능할 때마다 알림을 보낼 수 있다.
    - 반응형 : 모든 기기의 화면이나 브라우저에서 사용할 수 있다.
    - 안전 : 제 3자로부터 안전하다.
- 장점
    - Service Workers를 사용한 캐싱 덕분에 로딩 시간과 데이터를 절약할 수 있다.
    - 업데이트가 있을 때 변경된 컨텐츠만 업데이트할 수 있다. (네이티브 앱은 어플리케이션 전체를 업데이트 해야함.)
    - 시스템 알림 및 푸시 메세지를 통해 사용자의 재참여를 이끌어 낼 수 있다.
- reference : https://developer.mozilla.org/ko/docs/Web/Progressive_web_apps/Introduction

---

Web App 비교
===

Category|Native App | Progressive Web App | Hybrid App | Mobile Web App
---|---|---|---|---
장점 | 성능이 제일 좋고 네이티브 API로 플랫폼에 최적화할 수 있다. | Service Wroker를 사용해서 오프라인이나 느린 네트워크에서도 동작한다. HTTPS를 통해서 제공되서 보안성이 좋다. 플랫폼에 한정받지 않는다. 네이티브 웹보다 저렴하고 빠르게 개발할 수 있다. 설치할 필요가 없고 검색 엔진에서 검색기 가능하다. | 네이티브 API와 브라우저 API를 모두 사용가능하다. 여러 플랫폼에 대응할 수 있다. | 비용이 저렴하고 업데이트가 쉽다. 설치가 필요 없고 모든 기기와 브라우저에서 접근 가능하다.
단점 | 플랫폼에 한정적이다. 설치를 해야한다. | 오래된 브라우저는 PWA를 지원하지 않는다. 배터리 전력 소모가 비교적 크다. | 웹뷰에서 실행하는 경우엔 브라우저 성능이 중요하다. 성능이 안나올때가 많다. UI/UX를 처리하기 어렵다. | 플랫폼 API는 사용하지 못하고 브라우저 API만 사용 가능하다.
특징 |  |  |
- https://www.hanl.tech/blog/native-vs-hybrid-vs-pwa/

--- 

BFF (Backend for Frontend)
===

- `versus API 구조`
    - MSA 환경에서 API 엔트포인트가 분리될 때 팔로업 이슈
    - CORS 이슈
    - API 입장에서 여러 플랫폼과 스펙을 맞출 때의 커뮤니케이션 비용
    - 플랫폼별로 다를 수 밖에 없는 인증 방식을 통합하려는 무리한 시도
    - 클라이언트의 꿈인 '화면에 필요한 데이터만 받는' Partial response를 하기 어려운 이슈
- 이를 해결하고자 프론트엔드를 위한 중간 서버 역할을 하는 BFF가 등장
- 하나의 Frontend에 하나의 BFF가 존재해야 요구사항에 맞게 구현할 수 있다.
- reference : https://fe-developers.kakaoent.com/2022/220310-kakaopage-bff/

---

Micro Frontend
===

- `relate BFF`
- MSA 처럼 전체 화면을 동작할 수 있는 단위로 나누어 개발한 후 서로 조립하는 방식이다.
- Micro Frontent를 어떻게 통합할지 고려해야 한다.
- Backend 호출 구성은 BFF를 사용한다.

--- 

