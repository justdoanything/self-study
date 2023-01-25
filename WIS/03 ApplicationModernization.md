목차
===
- [Web Server와 WAS](#web-server-and-was)
- [NGINX](#nginx)
- [Redis](#redis)
- [Middleware](#middleware)
- [Apache와 NGINX](#apache-and-nginx)
- [WAF](#waf-web-application-firewall)
- [Yarn](#yarn)
- [Flyway](#flyway)
- [OAuth 2.0](#oauth-20)
- [REST API](#rest-api)
- [TCP / UDP / HTTP](#tcp--udp--http)
- [Netty와 NIO](#netty와-nio)
- [SQL 실행계획](#SQL-실행계획)
- [SQL Indexing](#SQL-Indexing)
- [기타](#기타)
- [공부](#공부할-것들)
  - [JVM](#1️⃣-jvm)
    - [JVM 이란](#1-jvm이란)
    - [JVM 동작 방식](#2-jvm-동작-방식)
    - [JVM 구조와 설명](#3-jvm-구조와-설명)
  - [Garbage Collection (GC)](#2️⃣-garbage-collection-gc)
    - [GC의 특징](#1-gc의-특징)
    - [Heap 메모리 구조](#2-heap-메모리-구조)
    - [Young 영역](#3-young-영역)
    - [Old 영역](#4-old-영역)
    - [Minor GC 과정](#5-minor-gc-과정)
    - [Major GC 과정](#6-major-gc-과정)
    - [GC의 종류](#7-gc의-종류)
  - [REST API](#3️⃣-rest-api)
    - [REST API 종류와 역할](#1-rest-api-종류와-역할)
    - [PUT과 PATCH의 차이](#2-put과-patch의-차이)
  - [Threadlocal](#4️⃣-threadlocal)
  - [Java Design Pattern](#5️⃣-java-design-pattern)
    - [Creational Pattern](#1-creational-pattern)
    - [Structural Pattern](#2-structural-pattern)
    - [Behavioral Pattern](#3-behavioral-pattern)
  - [KAFKA](#6️⃣-kafka)
---


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



NGINX
===
- NGINX is open source software for `web serving`, `reverse proxying`, `caching`, `load balancing`, `media streaming`, and more. It started out as `a web server` designed for `maximum performance` and `stability`. In addition to its HTTP server capabilities, NGINX can also function as `a proxy server` for email (IMAP, POP3, and SMTP) and `a reverse proxy` and `load balancer` for HTTP, TCP, and UDP servers.
- NGINX had solved the C10K problem with event-driven and asynchronous architecture.

- NGINX is a web server software such as Apache. and handled by event-driven.

- `Web Server` means that designed primarily to serve static content such as images or static HTML, etc. A web server identify dynamic content and forwarding requests to the app server.

- `Reverse Proxy` means that receives a client's request, sends it to the web server, receives a response from web server, and delivers it back to the client. This is placed between the client and the web server.

- ###### NGINX & PHP & PHP-FPM
  - `PHP (Hypertext Preprocessor)`
    - C언어를 기반으로 만들어진 서버 측에서 실행되는 서버 사이드 스크립트 언어이다.
    - PHP는 정적인 페이지만을 전달해주는 HTML을 보완하여 동적인 페이지를 표현할 수 있게 도와주고 HTML과 PHP를 다른 파일로 작성하는 것이 일반적이다.
    - 따로 작성된 PHP는 웹서버가 아닌 PHP-fpm을 통해 실행된다.
  - `PHP-FPM (FastCGI Process Manager)`
    -  NGINX가 PHP을 해석할 수 없기 때문에 PHP-FPM은 NGINX로부터 전달 받은 경로의 PHP 파일을 실행시키고 그 결과를 NGINX로 반환하는 독립적인 실행 프로그램
    - FastCGI는 클라이언트의 요청을 전달할 때마다 새로운 프로세스를 생성하는 것이 아니라 이미 생성된 프로세스를 재활용하는 방식을 사용하여 처리가 빠르다.
  - `CGI (Common Gateway Interface)` : 동적인 페이지 구현을 위한 프로그램에 클라이언트의 요청을 전달해주는 프로그램.
  - `NGINX`에서 `PHP-FPM`으로 `PHP` 파일이 전달되는 과정
    - NGINX에 PHP파일을 요청받게되면 PHP 처리가 정의 되어 있는 LOCATION 안의 제어문을 처리합니다.
    - LOCATION 제어문 안에 있는 지시어를 활용하여 PHP-fpm에게 전달할 php 파일을 지정합니다.
    - 전달하기 위해 지정된 파일을 fastcgi_pass 경로의 PHP-fpm에 전달합니다.
    - php 파일을 전달 받은 PHP-fpm은 php가 실행될 수 있도록 외부 프로그램에 연결시켜 줍니다.
  
- ###### NGINX as a Web Server
    - The goal behind NGINX was to create the `fastest web server` around, and `maintaining that excellence` is still a central goal of the project. NGINX consistently beats Apache and other servers in benchmarks measuring web server performance. Since the original release of NGINX, however, websites have expanded f`rom simple HTML pages to dynamic, multifaceted content`. NGINX has grown along with it and now supports all the components of the modern Web, including `WebSocket`, `HTTP/2`, `gRPC`, and `streaming of multiple video formats (HDS, HLS, RTMP, and others)`.
    - `Load Balancing `: It controls so that numerous requests do not flow to one server.
    - `Protect Server` : The service and client do not need a IP of app server so that the nervous like DDoS Attack is able to attack NGINX, not app server.
    - `Caching` : NGINX can cache the contents. It's possible to make response faster.
- ###### NGINX Beyond Web Serving
    - Though NGINX became famous as the fastest web server, the scalable underlying architecture has proved ideal for many web tasks beyond serving content. Because it can handle `a high volume of connections`, NGINX is commonly used as `a reverse proxy` and `load balancer` to manage incoming traffic and distribute it to slower upstream servers – anything from `legacy database` servers to `microservices`.
    - NGINX also is frequently `placed between clients and a second web server`, to serve as an SSL/TLS terminator or web accelerator. Acting as an intermediary, NGINX efficiently handles tasks that might slow down your web server, such as negotiating SSL/TLS or compressing and caching content `to improve performance`. Dynamic sites, built using anything from Node.js to PHP, commonly deploy NGINX as a content cache and reverse proxy to reduce load on application servers and make the most effective use of the underlying hardware.
- Reference
  - https://www.NGINX.com/resources/glossary/NGINX/
  - https://www.php.net/manual/en/install.fpm.php
  - https://velog.io/@du0928/PHP-fpm
  - https://medium.com/@su_bak/NGINX-NGINX%EB%9E%80-cf6cf8c33245
---



Apache and NGINX
===
- Apache (WAS)
  - Thread-Process 기반 구조로 Request 하나당 Thread 하나가 처리하는 구조
  - 사용자가 많으면 많은 Thread가 생성되기 때문에 Memory 및 CPU 낭비가 심함
  - One Thread - One Client 구조

- NGINX (Web Server)
  - 비동기 Event-Driven 기반 구조
  - 다수의 연결을 효과적으로 처리 가능
  - 대부분의 Core Module이 Apache보다 적은 Resource로 더 빠르게 동작가능
  - 더 작은 Thread로 Clinet Request를 처리 가능

- Thread-Process 방식은 하나의 Connection당 하나의 Thread를 잡아 먹지만 Event-Driven 방식은 여러개의 Connection을 모두 다 Event Handler를 통해 비동기 방식으로 처리해 먼저 처리되는 것부터 로직이 진행되게끔 합니다.

![image](https://user-images.githubusercontent.com/21374902/150284927-d9b7fd92-7f26-4126-bdc0-c25b4bb8c69d.png)

![image](https://user-images.githubusercontent.com/21374902/150284969-db228409-e99e-4583-9a52-3de516011a31.png)

- Reference
  - https://m.blog.naver.com/jhc9639/220967352282

---
Redis
===
- ### Intro
  - Redis : Remote Dictionary Server의 약어
  - Redis는 `빠른 속도`를 자랑하는 Open Source이며 `in-memory`이면서 `Key-value` 데이터 구조 스토어입니다.\
  ❓ _`In-Memory Database (IMDB)`_ : 데이터를 Disk driver가 아니라 컴퓨터 메인 메모리에 저장한다. 빠른 응답 시간으로 실시간 데이터 관리에 사용됩니다.
  - Redis는 다양한 `in-memory data structure` 집합을 제공하므로 다양한 사용자 
  정의 애플리케이션을 손쉽게 생성할 수 있습니다. 휘발성 메모리에 저장되다보니 전력이 중단되면 데이터가 날아갈 수 있는데 Redis를 `Snapshot`과 `AOF`로 보완하고 있습니다.
  - Redis는 주로 `Caching`, `Session Management`, `Pub/Sub`에 사용됩니다.
  - Redis는 현재 가장 인기 있는 `Key-Value 스토어`로서, BSD 라이선스가 있고, 최적화된 `C 코드`로 작성되었으며, 다양한 개발 언어를 지원합니다. 
  - AWS는 Redis용 `Amazon ElastiCache (완전관리형 데이터베이스)`을 제공하고 빠른 성능을 요구하는 기술에 사용됩니다.
  
- ### Redis 특징
  - ###### 빠른 성능
    - Disk 혹은 SSD에 저장하는 대부분의 데이터베이스 관리 시스템과는 달리 Redis는 `Server의 주 메모리`에 상주합니다.
    - Redis와 같은 in-memory database는 Disk에 Access 할 필요가 없기 때문에 `검색 시간으로 인한 지연을 방지`하고 `CPU 명령을 적게 사용`하는 좀 더 간단한 알고리즘으로 데이터에 액세스할 수 있습니다.
    - 일반적으로 작업을 실행하는 데 1ms 미만이 소요됩니다.

  - ###### In-memory Data Structure
    - Redis를 사용하면 다양한 Data Type에 매핑되는 key를 저장할 수 있습니다. 
    - 기본적인 Data Type은 `String`으로서, 텍스트 또는 이진 데이터가 이에 해당하며 `최대 크기는 512MB`입니다.
    - Redis는 문자열이 추가된 순서대로 유지되는 `List of String`, `Set of unordered String`, 점수에 따라 정렬되는 `Sorted Set`, 필드와 값 목록을 저장하는 `Hash`, Dataset에서 고유한 항목을 세는 `HyperLogLog`를 지원합니다. 

  - ###### 다양성과 사용 편의성
    - Redis는 개발과 운영을 좀 더 쉽고 좀 더 빠르게 수행할 수 있는 여러 가지 도구를 제공합니다. 
    - `Pub/Sub`는 메시지를 채널에 게시하며, 채널에서 구독자에게 전달됩니다.\
    (MSA에 적용되는 Netflix의 Kafka)
    - 채팅과 메시징 시스템에 매우 적합하고 `TTL Key`를 가질 수 있습니다.\
    ❓ _`TTL(Time to Live)`_  : 해당 기간 후에는 스스로를 삭제하는 지정된 값

  - ###### 복제 및 지속성
    - Redis는 `Master-Slave Architecture`를 사용하며 `비동기식 복제`를 지원하여 데이터가 여러 Slave Server에 복제될 수 있습니다. 
    - Main 서버에 장애가 발생하는 경우 요청이 여러 서버로 분산될 수 있기 때문에 향상된 `읽기 성능`과 `복구 기능`을 모두 제공할 수 있습니다.
    - Redis는 안정성을 제공하기 위해 특정 시점에 `Snapshot`과 `AOF`을 모두 지원해서 데이터 복구를 신속하게 할 수 있습니다.
      - `Snapshot` : Redis의 Dataset을 Disk로 복사
      - `AOF` (Append Only File) : (데이터가 변경될 때마다 이를 디스크에 저장)

  - ###### 선호하는 개발 언어 지원
    - Redis는 Open Source로 다수의 언어를 지원하고 있습니다.

- ### Redis 사용 사례
  - ###### Caching
    - Redis는 주로 다른 Database 앞쪽에 배치되서 액세스 지연 시간을 줄이고 처리량을 늘리며 Database의 부담을 덜어줍니다.

  - ###### Session Management
    - Redis를 Session Key에 대한 적절한 TTL과 함께 사용하면 간단하게 세션 정보를 관리할 수 있습니다. 
  - ###### 실시간 순위표
    - Sorted Set 구조를 사용하면 값이 목록에 유지되고 점수에 따라 정렬됩니다. 이를 통해 손쉽게 동적 순위표를 생성하여 게임에서 앞서있는 사람이 누구인지 보여주거나, 좋아요를 가장 많이 받은 메시지를 게시하거나, 선두에 있는 사람이 누구인지 보여주려는 다양한 사례에 사용할 수 있습니다.

  - ###### 속도 제한
    - Redis는 이벤트 속도를 측정하고 필요한 경우 제한할 수 있습니다. 클라이언트의 API Key에 연결된 Redis Counter를 사용하여 특정 기간 동안 Access Request의 수를 세고 한도가 초과되는 경우 조치를 취할 수 있습니다. 속도 제한기는 Forum의 게시물 수를 제한하고, 리소스 사용량을 제한하며, Spamer의 영향을 억제하는 데 주로 사용됩니다.

  - ###### 대기열
    - Redis List 데이터 구조를 사용하면 간단한 영구 대기열을 손쉽게 구현할 수 있습니다. Redis List는 자동 작업 및 차단 기능을 제공하므로 신뢰할 수 있는 Message Brocker 또는 순환 목록이 필요한 다양한 애플리케이션에 적합합니다.

  - ###### 채팅 및 메시징
    - Redis에서는 패턴 매칭과 더불어 Pub/Sub 표준을 지원합니다. Redis를 사용하여 고성능 채팅방, 실시간 코멘트 스트림 및 서버 상호 통신을 지원할 수 있습니다. 또한 Pub/Sub를 사용하여 게시된 이벤트를 기반으로 작업을 트리거할 수 있습니다.

- Reference : 
  - https://aws.amazon.com/ko/elasticache/what-is-redis/

---

Middleware
===
- ### Definition
  - OS에서 제공하지 않는 일반적인 서비스와 기능을 Application에 제공하는 Software
  - Data Management, Application Service, Messaging, Authorization, API Management는 주로 Middleware를 통해서 처리됩니다.
  - 개발자들이 Application을 효율적으로 구축할 수 있도록 지원하며 Application, Data, User 사이를 연결하는 요소처럼 작동합니다.
- ### Tpyes of middleware
  - ###### Runtime 제공
    - 개발자 및 아키텍트는 기반 런타임, 프레임워크 및 프로그래밍 언어 세트를 준수하는 다양한 플랫폼 전반에서 민첩하게 작업할 수 있습니다. Middleware는 Web Server, SSO(Single Sign-On), Messaging, in-memory caching과 같이 일반적으로 사용되는 기능도 제공합니다.\
    ![image](https://user-images.githubusercontent.com/21374902/153546835-68fb2b08-9c2c-4267-bb54-8d81e84cf83b.png)

  - ###### 기존 애플리케이션 최적화
    - 향상된 성능과 이식성을 지원하여 개발자들이 유용한 툴을 활용하면서 `Legacy Monolithic Application을 Cloud Native Application으로 변경`할 수 있도록 도와줍니다.\
    ![image](https://user-images.githubusercontent.com/21374902/153546965-4f8661ce-39dc-4ea7-b616-c6b5b678296e.png)

  - ###### 확장성
    - 중요한 내부 및 외부 시스템을 연결합니다. Transformation, Connectivity, Composability and Enterprise Messaging과 같은 통합 기능이 SSO 인증과 결합되어 개발자들이 다양한 애플리케이션 전체에 기능을 보다 손쉽게 확장할 수 있습니다.\
    ![image](https://user-images.githubusercontent.com/21374902/153547001-10b19186-1a15-4801-83d0-5931e9cfaeb0.png)

  - ###### Application Programming Interface, API
    - 많은 미들웨어 서비스는 애플리케이션 간 통신을 지원하는 툴, 정의 및 프로토콜 세트인 API를 통해 액세스할 수 있습니다. API를 활용하면 공통 레이어를 통해 완전히 다른 제품 및 서비스를 연결할 수 있습니다.\
    ![image](https://user-images.githubusercontent.com/21374902/153547019-86f4dc78-e798-40ac-9faf-904802f9ad10.png)

  - ###### 데이터 스트리밍
    - 비동기 데이터 스트리밍을 들 수 있습니다. 비동기 데이터 스트리밍은 데이터가 여러 애플리케이션 간에 공유될 수 있는 중간 저장소에 데이터 세트를 복제합니다. 실시간 데이터 스트리밍을 위해 널리 사용되는 오픈소스 미들웨어 툴 중 하나로 Apache Kafka가 있습니다.\
    ![image](https://user-images.githubusercontent.com/21374902/153547033-4cb247cb-9cc1-4247-b729-06962e937aa8.png)

  - ###### 지능형 비즈니스 자동화
    - 미들웨어는 개발자, 아키텍트, IT 팀 및 비즈니스 리더들이 수동 의사 결정을 자동화하도록 지원하며 자동화를 통해 리소스 관리와 전반적인 효율성을 개선할 수 있습니다.\
    ![image](https://user-images.githubusercontent.com/21374902/153547042-de6db5e0-b7c2-492e-9e93-4597273739e1.png)

- ### Cloud Computing에서 Middleware가 중요한 이유는 무엇일까요?
  - Cloud Native 개발은 다양한 이점을 제공하지만 동시에 복잡성도 증가합니다. Application은 On-promise System에서 Public Cloud에 이르는 여러 인프라 전반에 배포될 수 있고, 아키텍처는 매우 다양합니다. 개발자들은 다양한 툴, 언어 및 프레임워크를 균형 있게 다룰 수 있어야 하고, 낮은 비용으로 짧은 시간에 더 많은 작업을 수행해야 하는 부담을 안고 있습니다.
  - 조직들은 이러한 복잡성을 관리하고 애플리케이션 개발을 신속하고 비용 효율적으로 유지하기 위해 미들웨어로 전환합니다. 미들웨어는 고도로 분산된 플랫폼 전반에서 원활하고 일관되게 작동하는 애플리케이션 환경을 지원할 수 있습니다. 한 곳에서 구축하고 다른 곳에 배포할 수 있으며, 애플리케이션의 기반인 미들웨어의 이점을 활용하여 동일하게 작동합니다.

- ### Application 개발에서 미들웨어의 역할은 무엇일까요?
  - 현대적인 Business Application은 스케일에 따라 On-promise 및 Cloud 전반에서 실행되도록 설계됩니다. 이를 구축하기 위해 개발자들은 통합 기반 기능을 갖춘 애플리케이션 환경을 필요로 합니다. 미들웨어는 바로 이러한 환경을 조합하는 핵심 요소입니다.

- ### Middleware의 4개의 레이어와 툴링
  - Container Layer
    - 이 미들웨어 레이어는 일관된 방식으로 애플리케이션 라이프사이클 제공 측면을 관리하며, CI/CD, 컨테이너 관리 및 서비스 메쉬 기능과 함께 DevOps 기능을 제공합니다.

  - Runtime Layer
    - 이 레이어는 사용자 지정 코드에 대한 실행 환경을 포함합니다. 미들웨어는 마이크로서비스, 빠른 데이터 액세스를 위한 인메모리 캐싱, 빠른 데이터 전송용 메시징 등 고도로 분산된 클라우드 환경에 대해 경량화된 런타임 및 프레임워크를 제공할 수 있습니다.

  - Integration Layer
    - 통합 미들웨어는 커스텀 애플리케이션 및 구매한 애플리케이션을 연결하는 서비스를 제공하는 것은 물론, 가동 시스템을 구축하기 위해 메시징, 통합 및 API를 통해 SaaS(서비스로서의 소프트웨어) 자산을 제공합니다. 또한 인메모리 데이터베이스 및 데이터 캐시 서비스, 데이터/이벤트 스트리밍 및 API 관리도 제공합니다.

  - Process automation and decision management layer
    - 이 마지막 개발 미들웨어 레이어는 중요 인텔리전스, 최적화 및 자동화, 의사 결정 관리를 추가합니다.

  - Tooling
    - 미들웨어 레이어 4가지와 더불어 애플리케이션 개발 툴링이 있습니다. 툴링을 통해 개발 팀은 미리 설정된 템플릿 및 컨테이너를 사용하여 애플리케이션을 구축하고, 효율적인 코드 공유 및 공동 개발을 지원합니다. 툴링은 온프레미스 및 클라우드에서 지속적이고 일관된 애플리케이션 개발 및 제공 경험을 지원합니다.

- Reference
  - https://www.redhat.com/ko/topics/middleware/what-is-middleware

---


WAF (Web Application Firewall)
===
- It helps protect web applications by filtering and monitoring HTTP traffic between a web apllication and the internet. It typically protects web applications from attacks such as cross-site forgery, cross-site-scripting(XSS), file inclusion and SQL injection among others.
- The value of a WAF comes in part from the speed and ease with which policy modification can be implemented, allowing for faster response to varying attack vectors; during a DDos attack, rate limiting can be quickly implemented by modifying WAF policies.
- WAF can do the reverse proxy server and protect api gateway against SQL injection, XSS and other attacks.
- WAF can be implemented one of three different ways, each with its own benefits and shortcomings.
  - ###### Network-based
    This is generally `hardware-based`. Since they are installed locally they minimize latency, but network-based WAFs are `the most expensive` option and also require `the storage` and maintanance of `physical equipment`.
    - ###### Host-based : 
    This may be fully integrated into an application's `software`. This solution is less expensive than a network-based WAF and offers `more customizability`. The `downside` of a host-based WAF is the `consumption of local server resources`, `implementation complexity`, and `maintenance costs`. These components typically require engineering `time` and may be `costly`.
  - ###### Cloud-based :
    This offers an affordable option that is very `easy to implement`.They usually offer a turnkey installation that is as simple as a change in DNS to redirect traffic. Cloud-based WAFs also have `a minimal upfront cost`, as users pay monthly or annually for security as a service. `Cloud-based WAFs can also offer a solution that is consistently updated to protect against the newest threats without any additional work or cost on the user's end`. The `drawback` of a cloud-based WAF is that users `hand over the responsibility` to a third party, therefore some features of the WAF may be a `black box` to them.

- Reference
  - https://www.cloudflare.com/ko-kr/learning/ddos/glossary/web-application-firewall-waf/
---



Yarn
===
- #### Javscript Package Management Tool 중에 하나로 비슷한 Tool로는 npm이 있다.
- #### npm의 단점들 보완하기 위해 페이스북에서 만든 Tool 이고 npm의 단점은 아래와 같다.
  - ###### 비효율적인 의존성 탐색
    > `require.resolve.paths('react')` 명령어를 통해서 `react` 패키지를 찾기 위해 순회하는 경로를 볼 수 있는데 상위 폴더인 `node_module` 폴더를 계속 탐색하기 때문에 패키지를 바로 찾지 못하면 `readdir`, `stat` 와 같은 느린 I/O 호출이 반복된다.
    ```sh
    $ node
    Welcome to Node.js v12.16.3.
    Type ".help" for more information.
    > require.resolve.paths('react')
    [
      '/Users/toss/dev/toss-frontend-libraries/repl/node_modules',
      '/Users/toss/dev/toss-frontend-libraries/node_modules',
      '/Users/toss/node_modules',
      '/Users/node_modules',
      '/node_modules',
      '/Users/toss/.node_modules',
      '/Users/toss/.node_libraries',
      '/Users/toss/.nvm/versions/node/v12.16.3/lib/node',
      '/Users/toss/.node_modules',
      '/Users/toss/.node_libraries',
      '/Users/toss/.nvm/versions/node/v12.16.3/lib/node'
    ]
    ```
  - ###### 환경에 따라 달라질 수 있는 구성
    > 패키지를 탐색할 때 상위 폴더의 `node_module` 폴더를 순회하는데 그 `node_module`에 원하는 패키지가 없다면 의존성을 불러오지 못할 수 있다. 혹은 다른 버전의 의존성을 불러올 수 있다. 
  - ###### 비효율적인 설치
    > npm은 설치할 때 전체 패키지를 다운받기 때문에 `node_module` 폴더는 큰 공간과 많은 I/O 작업을 필요로 한다. 또한 이러한 복잡한 구조는 유효한지 검증하기 어렵다.
  - ###### 유령 의존성 (Phantom Dependency)
    > 아래 Dependency Tree를 보면 A, B 패키지는 중복 설치되는 것을 볼 수 있다.\
      이를 해결하기 위해서 NPM은 Hosting 기법을 사용하는데 이렇게 되면 원래 `require` 할 수 없었던 `B (1.0)` 패키지를 불러올 수 있게 된다.\
      이렇게 직접 의존하고 있지 않은 패키지를 가져올 수 있는 것을 `유령 의존성` 이라고 한다.\
      즉, `package.json`에 명시하지 않은 패키지를 사용할 수 있게 되고 다른 의존성을 `package.json` 에서 제거했을 때 같이 사라지기도 합니다. 

    ![image](https://user-images.githubusercontent.com/21374902/152487628-367f40d7-2ac7-4b49-8e1d-173d320c4fd5.png)    

- #### Yarn의 특징
  - ###### 명확한 의존성
    > yarn은 Cache 파일(`.yarn/cache`)에 의존성 정보를 저장하고 `.pnp.cjs` 파일에 의존성을 찾을 수 있는 정보를 저장한다.\
    `.pnp.cjs` 파일을 이용하면 disk I/O 없이 어떤 패키지가 어떤 의존성을 갖고 있는지 각 위치는 어디인지 알 수 있다.\
    `react` 패키지는 `.pnp.cjs` 파일에 다음과 같이 명시되어 있다.
    ```cjs
    /* react 패키지 중에서 */
    ["react", [
      /* npm:17.0.1 버전은 */
      ["npm:17.0.1", {
        /* 이 위치에 있고 */
        "packageLocation": "./.yarn/cache/react-npm-17.0.1-98658812fc-a76d86ec97.zip/node_modules/react/",
        /* 이 의존성들을 참조한다. */
        "packageDependencies": [
          ["loose-envify", "npm:1.4.0"],
          ["object-assign", "npm:4.1.1"]
        ],
      }]
    ]],
    ```
  - ###### 빠른 속도
    > yarn은 다운받은 패키지 데이터를 캐시(cache)에 저장하여 이후에 중복된 데이터는 다운로드하지않고 저장된 파일을 활용한다. 또한 여러개의 패키지를 설치할 때 병렬로 처리하기 때문에 속도가 빠른편이다. (npm은 순차적 설치한다.)
  - ###### 안정성(stability)
    > `.pnp.cjs` 파일을 이용하여 관리되기 때문에 더 이상 외부 환경에 영향을 받지 않고 다양한 기기 및 CI 환경에서 `require()` 또는 `import`가 동일한 버전으로 동작하는 것을 보장할 수 있다.
  - ###### Zero Install 
    > yarn은 의존성을 압축 파일로 관리하기 때문에 npm의 `node_module` 폴더에 비해 크기가 매우 적다. 따라서 의존성을 버전 관리에 포함할 수 있는데 이를 `Zero Install` 이라고 부른다.\
    `Zero Install`을 하면 새로 저장소를 복제하거나 브랜치를 바꿨을 때 yarn install을 실행하지 않아도 되고 CI에서 의존성을 설치하는데 소요되는 시간과 용량을 크게 절약할 수 있다.
- ##### Reference 
  - https://toss.tech/article/node-modules-and-yarn-berry

---
Flyway
===
- ### Flyway 란?
  - Git으로 Source Code를 형상관리하면서 여러 환경에 동일하게 배포 및 관리를 할 수 있듯이 Flyway는 Database Schema와 Data를 형상관리를 할 수 있게 해줍니다.
  - Code 기반으로 관리하며 Data Migration에도 용이합니다.
  - 개발자가 Local에서 변경한 Database Schema와 Data를 실제 환경(개발, 운영)에 누락없이 안전하게 반영할 수 있게 됩니다.\
  ![image](https://user-images.githubusercontent.com/21374902/153998066-6a914dc7-58e9-4e2c-b035-dcd04dab4dc1.png)
  - Flyway는 Meta Table을 만들고 사용자가 정의한 SQL을 실행하여 Migration을 하고 History를 Meta Table에 저장합니다.
  - History Table을 통해 이전의 이력(DDL/DML)을 관리해서 database 중복 작업을 방지하고 이전에 수행했던 작업을 재수행하기 쉽고 동일한 결과를 보장합니다.
  - 별도로 설정하지 않는 경우 비워져있지않으면 'Found non-empty schema(s) "tableName" without schema history table!' 이와 같은 에러 발생한다.
  - Target Schema(Database)에 flyway_schema_history(flyway 변경 이력 관리 meta table)을 생성합니다.
- ### Flyway 동작방식
  - 기본적인 시작 조건은 2가지 이다.
    - Migration file (Database Schema 변경에 대한 SQL 파일)이 있어야 한다.
      
      ![image](https://user-images.githubusercontent.com/21374902/153998100-3613f969-b32a-4b09-9ee5-9311cbc362a6.png)
    
    - 기본적으로 Migration을 하려는 Schema(Database)는 비워져있어야한다.
      
      ![image](https://user-images.githubusercontent.com/21374902/153998380-31cbe6d8-2275-48b7-ac8e-5181340bcdf1.png)
  
  - flyway의 `schema_version` 테이블이 생성되거나 기존재한다면 flyway는 지정된 classpath에서 `SQL 파일` 혹은 `Java 파일`을 탐색하여 버전 순서대로 실행한다.
  
  ![image](https://user-images.githubusercontent.com/21374902/153998388-c5b50387-bbd9-4802-b31d-075d4f7dbd14.png) 

  - 이때 실행해야하는 순서, version, description, checksum 등은 schema_version 테이블에 저장된다.

  ![image](https://user-images.githubusercontent.com/21374902/153998396-eca9bd45-a57d-4bd4-9f92-8a3e3a1c6620.png)

  - flyway는 Migration 대상 파일의 version을 파일명 기준으로 판단하는데 Naming Rule은 아래와 같다.

  ![image](https://user-images.githubusercontent.com/21374902/153998406-8044eacd-ff55-4b1a-81cb-3dfcbf25d18a.png)
  

- ### SQL Naming Rule
  - prefix
    - V : default 값으로 Version Migration용 접두사
    - R : 반복 Migration용 접두사
  - version : `Version Migration(prefix=V)`에서만 사용되며 숫자, 마침표(.), 언더바(_) 조합으로 구성할 수 있습니다.
  - separator : 설명 구문을 구분하기 위한 값이며 언더바 2개(__)로 사용합니다.
  - description : schema_version 테이블에 저장할 때 설명으로 사용됩니다.
  - suffix : 기본 확장자는 .sql 입니다.
  - 예시 : V1_1_022_member.sql, R__Create_view.sql

- ### Flyway Command
  - 6개의 명령을 지원하며 Execution Mode에 따라 다르게 동작한다.
  
  - Migrate : database를 migration 합니다.\
  ![image](https://user-images.githubusercontent.com/21374902/153998132-4dce9b27-c1bd-4e28-a680-b6d67e23c77a.png)
  
  - Info : 모든 migration 상세정보를 출력합니다.\
  ![image](https://user-images.githubusercontent.com/21374902/153998148-b0edea83-3384-4820-9fca-69f3a6f0ba87.png)
  
  - Validate : database에 적용된 migration 정보의 유효성을 검증합니다.\
  ![image](https://user-images.githubusercontent.com/21374902/153998164-f07c3121-f23f-41e7-a1a0-04b9dfd34ecc.png)
  
  - Baseline : flyway로 관리되기 이전의 database가 존재하면 해당 database를 flyway baseline으로 설정할 수 있습니다.\
  ![image](https://user-images.githubusercontent.com/21374902/153998188-6003d2b6-a113-4f61-809c-cf0a61e79cf4.png)
  
  - Repair : Meta Table 문제를 해결할 때 사용합니다. 메타 데이터 테이블 문제를 해결하기 위해 사용하는데 두가지 용법이 존재한다. 
    - 실패한 Migration 항목 제거 (DDL Transaction을 지원하지 않는 database에만 해당)
    - 적용된 Migration의 체크섬을 사용 가능한 Migration의 Checksum으로 재정렬. \
  ![image](https://user-images.githubusercontent.com/21374902/153998205-9d5111ce-dcb5-48e3-a0b7-bd89716ae43b.png)
  
  - Clean : database의 schema_version 테이블을 포함한 모든 요소를(table, view, procedure, ...)\
  ![image](https://user-images.githubusercontent.com/21374902/153998219-14a5a9c2-6e46-423a-94f4-5ab6d70d102e.png)
- ### Flyway 실행방법
  - Command-line : 콘솔에서 명령을 입력하여 실행하는 방법
  - API(Java/Android) : Java로 작성된 프로그램내에서 API를 이용하여 실행.
  - Maven : Maven에 통합하여 실행.
  - Gradle : Gradle에 통합하여 실행.
  - Ant : Ant에 통합하여 실행.
  - SBT : SBT(Scala 빌드 도구)에 통합하여 실행.

- ### Flyway 실습예제(Gradle 기반) with SpringBoot
  - build.gradle
    ```yml
    plugins{
      ...
      id 'org.flywaydb.flyway' version '6.5.5'
      ...
    }

    dependencies {
      ...
      implementation 'org.flywaydb:flyway-core'
      ...
    }

    flyway {
      // database source 입력
      url = 'jdbc:mariadb://localhost:3330/spring?useUnicode=yes&characterEncoding=UTF-8&createDatabaseIfNotExist=true&allowPublicKeyRetrieval=true&useSSL=false'
      user = 'root'
      password = 'yongwoo'
      schemas = ['spring']
      // sql 파일 경로 명시
      locations = ['filesystem:./src/main/resources/db/migration/common', 'filesystem:./src/main/resources/db/migration/env/local']
      sqlMigrationSuffixes = ['.sql']
      outOfOrder = true
    }
    ```
  - FlywayConfig.java
    ```java
    @Configuration
    public class FlywayConfig {
        @Value(value = "${spring.profiles.active}")
        private String profile;

        @Bean
        public FlywayMigrationStrategy cleanMigrationStrategy() {
            return flyway -> {
                if (profile.equals("feature") || profile.equals("dev")) {
                    flyway.clean();
                }
                flyway.migrate();
            };
        }
    }
    ```
  - application.yml
    ```yml
    spring:
      flyway:
        url: jdbc:mariadb://localhost:3330/spring?useUnicode=yes&characterEncoding=UTF-8&createDatabaseIfNotExist=true&allowPublicKeyRetrieval=true&useSSL=false
        schema: spring
        user: root
        password: yongwoo
        locations: classpath:/db/migration
        out-of-order: true
    ```
  - SQL 폴더 구성
    - resources/db/migration/common : DDL과 같은 SQL Versioing
    - resources/db/migration/env/local : DML과 같은 SQL Versioning, spring profile 별로 나눌 수 있다.

- ### Flyway 실습예제(Maven 기반)
  - flayway dependency 추가 & build 설정 추가
  ```xml
  <dependencies>
   <dependency>
      <groupId>com.h2database</groupId>
      <artifactId>h2</artifactId>
      <version>1.4.193</version>
   </dependency>
  </dependencies>
  <build>
    <plugins>
        <plugin>
          <groupId>org.flywaydb</groupId>
          <artifactId>flyway-maven-plugin</artifactId>
          <version>4.0.3</version>
          <configuration>
              <url>jdbc:h2:tcp://192.168.56.101:9092/~/test,h2.version=1.4.193</url>
              <user>sa</user>
              <locations>
                <location>classpath:db/migration</location>
              </locations>
          </configuration>
        </plugin>
    </plugins>
  </build>
  ```
  - Migration SQL 생성
    - 원하는 경로에 SQL 파일 생성\    
    ![image](https://user-images.githubusercontent.com/21374902/154786923-6b9960c3-6472-4a74-8511-ddd036e59812.png)
  - Migration 수행
    - `mvn flyway:migrate`를 실행하면 db/migration 하위에 있는 sql 파일들이 version에 따라 실행된다.\
    ![image](https://user-images.githubusercontent.com/21374902/154786952-e2a9f14e-b8e6-4ad1-a065-a0fee2b7a873.png)
  - Migration 확인
    - `mvn flyway:info` 명령어를 통해 Schema_version에 실행한 이력을 확인할 수 있다.\
    ![image](https://user-images.githubusercontent.com/21374902/154786978-b9298012-cbeb-48c2-aa65-a3eb78cb3735.png)

- ### 잔여과제
  - Docker, AWS 배포 환경에서 자동으로 migration을 수행하려면 어떤 작업이 필요한가?
  - 수동으로 해야하면 어떤 시점에 어떻게 수동으로 해야하며 자동으로 하려면 어떻게 해야하고 어떤 점들을 챙겨야 하는가 ? 
- ### Reference
  - https://flywaydb.org/documentation/
  - https://www.popit.kr/%EB%82%98%EB%A7%8C-%EB%AA%A8%EB%A5%B4%EA%B3%A0-%EC%9E%88%EB%8D%98-flyway-db-%EB%A7%88%EC%9D%B4%EA%B7%B8%EB%A0%88%EC%9D%B4%EC%85%98-tool/



---

OAuth 2.0
===
- ### 역할
  - `Resource Server` : Google, Apple, Facebook과 같이 Resource Owner의 정보를 갖고 있고 OAuth 기반의 서비스를 제공하는 서버
  - `Authorization Server` : Resource Server에 대한 인증을 담당하는 서버. Resource Server의 한 기능으로 생략되기도 한다.
  - `Resource Owner` : Resource Server의 계정을 보유하고 있는 사용자(Google, Apple, Facebook 사용자)
  - `Client` : Google, Apple, Facebook 등에서 OAuth를 통해 제공하는 서비스를 이용하고자하는 서버 (일반적으론 우리가 만드는 서버-클라이언트)

- ### 서비스 등록
  - Google, Apple, Facebook 등에서 제공하는 서비스를 이용하기 위해선 등록이 되어 있어야 한다.
  - 필요한 정보로는 Client ID, Client Server, Authorized Redirect URI 등이 있다.

- ### 주요 용어
  - `Authorization Code` : Access Token과 Refresh Token을 발급받기 위해 필요한 Code. 로그인 인증을 하면 Authorization Server가 사용자 정보와 사용자가 획득한 권한 정보를 저장하고 제공한다.
  - `Access Token` : Resource Server로부터 Resource Owner의 보호된 자원을 획득할 때 사용되는 만료 기간이 있는 토큰. Access Token은 수명이 짧아 만료되면 Refresh Token을 Authorization Server에 전달해서 새로운 Access Token을 발급 받는다.
  - `Refresh Token` : 일정 기간 동안 로그인을 하지 않아도 만료된 Access Token을 갱신할 수 있는 토큰. Refresh Token은 외부에 노출되면 안되기 때문에 대부분 DB에 저장해놓고 사용한다.
  - `Scope (optional)` : Resource Server가 OAuth를 통해 제공하는 서비스나 정보의 범위
  - `Code Verifier` : PKCE 방식에서 코드 검증에 사용되는 값
  - `Code Challenge` : PKCE 방식에서 코드 검증에 사용되는 암호화된 값

- ### Authorization Code 방식
  - Access Token 발행 전에 Authorization Code 교환을 수행함으로써 효율적으로 사용자(Resource Owner)의 정보를 보호할 수 있다.
  - Client와 Authorization Server 사이에 backend 채널을 통해 Access Token이 발급되고 교환되기 때문에 공격자가 중간에서 가로채는 것을 막을 수 있다.

<img width="647" alt="image" src="https://user-images.githubusercontent.com/21374902/168973635-12890ba2-a1f3-40b9-9211-82e390c9aaf3.png">

- ### Authorization Code with PKCE
  - PKCE : Proof Key for Code Exchange
  - Authorization Code 방식에 PKCE 절차를 추가한 방식
  - Authorization Code가 탈취되더라도 Access Token을 발급받지 못하도록 막을 수 있다.

<img width="637" alt="image" src="https://user-images.githubusercontent.com/21374902/168975805-1c029125-7da8-4ad0-afc1-03bf765fd7a8.png">

- ### Client의 동작
  - 네이버 로그인 API : https://developers.naver.com/docs/login/api/api.md
  - 로그인 인증 요청
    - 요청 : https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=jyvqXeaVOVmV&redirect_uri=http%3A%2F%2Fservice.redirect.url%2Fredirect&state=hLiDdL2uhPtsftcU
    - 응답 : http://콜백URL/redirect?code={code값}&state={state값}
  - Access Token 발급 요청
    - 요청 : https://nid.naver.com/oauth2.0/token?grant_type=authorization_code&client_id=jyvqXeaVOVmV&client_secret=527300A0_COq1_XV33cf&code=EIc5bFrl4RibFls1&state=9kgsGTfH4j7IyAkg
    - 응답
      ```json
      {
        "access_token":"AAAAQosjWDJieBiQZc3to9YQp6HDLvrmyKC+6+iZ3gq7qrkqf50ljZC+Lgoqrg",
        "refresh_token":"c8ceMEJisO4Se7uGisHoX0f5JEii7JnipglQipkOn5Zp3tyP7dHQoP0zNKHUq2gY",
        "token_type":"bearer",
        "expires_in":"3600"
      }
      ```
  - Access Token 갱신 요청
    - 요청 : https://nid.naver.com/oauth2.0/token?grant_type=refresh_token&client_id=jyvqXeaVOVmV&client_secret=527300A0_COq1_XV33cf&refresh_token=c8ceMEJisO4Se7uGCEYKK1p52L93bHXLn
    - 응답
      ```json
      {
        "access_token":"AAAAQjbRkysCNmMdQ7kmowPrjyRNIRYKG2iGHhbGawP0xfuYwjrE2WTI3p44SNepkFXME/NlxfamcJKPmUU4dSUhz+R2CmUqnN0lGuOcbEw6iexg",
        "token_type":"bearer",
        "expires_in":"3600"
      }
      ```
  - Access Token 삭제 요청
    - 요청 : https://nid.naver.com/oauth2.0/token?grant_type=delete&client_id=jyvqXeaVOVmV&client_secret=527300A0_COq1_XV33cf&access_token=c8ceMEJisO4Se7uGCEYKK1p52L93bHXLnaoETis9YzjfnorlQwEisqemfpKHUq2gY&service_provider=NAVER
    - 응답
      ```json
      {
        "access_token":"c8ceMEjfnorlQwEisqemfpM1Wzw7aGp7JnipglQipkOn5Zp3tyP7dHQoP0zNKHUq2gY",
        "result":"success"
      }
      ```


- ### Access Token 획득 후 동작
  - Application은 Resource 서버의 API를 호출하기 위해 인증(로그인) 후에 Access Token을 획득
  - Client는 발급 받은 Access Token과 Refresh Token을 안전한 공간에 보관
  - Access Token은 Application의 신원과 권한을 증명하며 API 요청에 Access Token을 담아 Resource Server를 호출
  - Access Token이 만료되었다는 응답을 받은 경우 Refresh Token을 사용해서 Access Token을 새로 발급받고 저장

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
    `GET` | 조회
    `PUT` | 정보 변경
    `POST` | 정보 생성
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

Netty와 NIO
===
- 비동기 호출을 지원하는 디자인 패턴
  - Ticket을 사용한 `Future Pattern`
  - Event Listener를 사용한 `Observer Pattern`
  - `Callback Pattern` 👉 Nodejs
  - `Reactor Pattern` 👉 Netty  
- Blocking vs Non-Blocking
  - Blocking
    - Java에는 `ServerSocket`, `Socket`과 같은 Blocking Socket이 존재한다.
    - Client가 Server로 연결을 요청하면 Server는 요청을 수락하고 Client와 연결된 Socket을 새로 만든 후 연결이 끝날 때까지 Thread의 blocking이 발생한다.
    - Client가 여러개일 땐 Client의 요청마다 Socket과 Thread를 생성할 수 있는데 accept 에서 병목 현상이 일어날 수 있기 때문에 대량의 Client를 커버하기는 어렵다.
    - 또한 Socket과 Thread를 무한정 늘리면 heap을 많이 사용해서 GC 발생 주기가 길어지면서 Stop-the-world 시간이 길어지고 Context Switching에 많은 자원이 소모되기 때문에 문제가 될 수 있다.
  - Non-Blocking
    - Java 1.4 부터는 `ServerSocketChannel`, `SocketChannel`과 같은 Non-Blocking Socket이 존재한다.
    - 

- Reference : https://velog.io/@monami/Netty

---

SQL 실행계획
===
- SQL이 실행될 때 Optimizer가 수행하는 작업절차를 뜻한다.
- 실행계획은 3단계로 분리되는데 `SQL 해석` → `실행계획 수립` → `실행` 순이다.
- SQL은 SQL Parser가 Parser Tree를 만들고 Tree 기준으로 Optimizer가 분석하여 실행 계획을 만든다.
- Optimizer는 비용 기반으로 쿼리 대상 테이블의 레코드 수와 통계 정보를 바탕으로 비용이 가장 적게 발생할 것 같은 방향으로 실행 계획을 생성한다.
- 통계 정보가 정확할 수록 좋은 실행계획을 만들 수 있는데 `innodb_stats_sample_pages`은 통계 정보를 위해 분석할 수 있는 페이지의 수를 지정한다. (기본값: 8)
- 통계 수집 중엔 테이블에 Read/Write가 안되기 때문에 성능을 고려해야 하고 최대 16~24개까지만 설정하는게 좋다.
- Mysql 8.0 부터는 인덱스 말고 일반 컬럼에도 통계 정보를 수집하기 때문에 더 좋은 실행 계획을 수립할 수 있다.
- 실행 계획은 SELECT절 앞에 `EXPLAIN`키워드를  사용해서 볼 수 있다. 실행 계획에서 나누는 필드는 아래 특성을 가진다.
  - `id`
    - SELECT 쿼리를 구분하기 위한 용도로 JOIN으로 연결되어 있는 쿼리는 하나로 보기 때문에 같은 id 값을 가진다.
  - `select_type`
    - SELECT 쿼리가 어떤 타입인지 나타낸다.
    - | 타입                     | 설명                                                                                                                                                                                                                                                            |
      |------------------------|---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
      | `SIMPLE`               | UNION 이나 Sub Query를 사용하지 않은 단순 SELECT. JOIN하고 있는 쿼리도 따로 보지 않고 하나의 `SIMPLE`로 본다.                                                                                                                                                                               |
      | `PRIMARY`              | UNION 이나 Sub Query가 포함된 SELECT에서 가장 바깥쪽에 있는 SELECT. PRIMARY인 쿼리는 반드시 하나만 존재한다.                                                                                                                                                                                |
      | `UNION`                | UNION을 사용한 SELECT에서 두번째 이후 쿼리는 모두 UNION 으로 표시된다. (첫번째 쿼리는 `DERIVED` 이다.)                                                                                                                                                                                      |
      | `UNION RESULT`         | UNION의 결과를 담는 임시 테이블을 의미한다. (Mysql 8.0 부터는 UNION ALL을 쓸 때 임시 테이블을 사용하지 않기로해서 보이지 않지만 UNION, UNION DISTINCT 쿼리는 임시 테이블에 결과를 담는다.)                                                                                                                              |
      | `SUBQUERY`             | FROM절 이외에 사용된 Sub Query에 표시된다. (FROM절에 사용된 Sub Query는 `DERIVED` 로 표시된다.)                                                                                                                                                                                      |
      | `DEPENDENT SUBQUERY`   | FROM절 이외에 사용된 Sub Query가 바깥쪽 SELECT에서 정의된 컬럼을 사용할 경우 해당 Sub Query에 표시된다.                                                                                                                                                                                      |
      | `DERIVED`              | FROM절에 사용된 Sub Query로 SELECT 결과로 Memory나 Disk에 임시 테이블을 만드는 경우에 표시된다.                                                                                                                                                                                          |
      | `DEPENDENT DERIVED`    | LATERAL JOIN을 사용했을 떄 표시된다. (Mysql 8.0 이전에는 FROM절의 Sub Query에 외부 컬럼을 사용할 수 없었지만 8.0 이후로는 가능하고 LATERAL JOIN으로 FROM절의 Sub Query가 외부 컬럼을 참좋라 수 있다.)                                                                                                               |
      | `UNCACHEABLE SUBQUERY` | `SUBQUERY`, `DEPENDENT SUBQUERY`는 Sub Query 결과를 Caching 할 수 있는데 툭정 조건 때문에 Caching 할 수 없을 때 표시된다. (사용자 변수가 Sub Query에 있거나 UUID(), RAND() 와 같은 결과값이 호출할 때마다 변경되는 함수가 Sub Query에 있을 때, NOT-DETERMINISTIC 속성의 Stored Function이 Sub Query에 들어간 경우 Caching 하지 못한다.) |
      | `DEPENDENT UNION`      | UNION을 사용한 SELECT에서 UNION으로 결합된 쿼리가 외부 쿼리에 영향을 받는 경우이다. 내부 쿼리가 외부 값을 참조하는 경우이다.                                                                                                                                                                               |
      | `MATERIALIZED`         | FROM절이나 IN 형태의 쿼리에 사용된 Sub Query를 최적화할 때 표시된다. 보통 Sub Query보다 외부 쿼리의 테이블을 먼저 읽어서 비효율적으로 실행되는데 Sub Query 내용을 임시 테이블로 구체화한 후 외부 테이블과 Join 하여 최적화한다. 이 때 Sub Query가 먼저 구체화 되었다는 것을 표시한다.                                                                         |
  - `table`
    - 실행 계획을 확인할 때 SELECT 쿼리가 아니고 테이블 기준으로 분류해서 나온다.
    - `<>` 로 감싸져 있으면 임시 테이블을 뜻한다.
  - `partitions`
    - Partitioning 으로 테이블이 관리되고 있을 때 어떤 Partitioning을 읽었는지 표시해준다.
  - `type`
    - ⭐️ index를 참조했는지 알려주는 중요한 컬럼이다.
    - ALL 을 빼면 index를 사용했다고 볼 수 있고 type 컬럼의 종류는 아래와 같다. (성능이 좋은 순으로 정렬되어 있다.)
    - | 타입                | 설명                                                                                                                                                                                                                                                             |
      |----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|---|
      | `system`          | record가 0건 또는 1건만 존재하는 테이블을 접근할 때 표시된다.                                                                                                                                                                                                                        |
      | `const`           | Query에 Primary/Unique Key를 이용하는 WHERE 조건이 있으면거 결과가 반드시 1건을 반환하는 쿼리로 접근할 때 표시된다. (=UNIQUE INDEX SCAN) 단, DBMS가 결과가 1개인 것을 예측할 수 있어야 한다. QUERY 결과가 1건인 것이랑은 별개이다.                                                                                                |
      | `eq_ref`          | 여러 테이블이 JOIN 되는 QUERY 에서만 발생한다. JOIN에서 처음 읽은 테이블의 컬럼 값을 두번째 읽는 테이블의 Primary/Unique Index Column(NOT NULL)의 동등 조건에 사용될 때 (=반드시 1건만 존재한다는 보장이 있을 때) 사용되는 접근 방법                                                                                                   |
      | `ref`             | eq_ref 와 달리 JOIN 순서에 상관없이 사용되며 Primary/Unique Index 등의 제약도 상관없이 사용된다. Index 종류와 상관없이 동등 조건이 사용될 때 사용되는 접근 방법이다. (단, 레코드가 반드시 1건이라는 보장이 없으므로 eq_ref보다 느리지만 전체로 봤을 땐 아주 빠른 index 이다.)                                                                            |
      | `fulltext`        | 전문 검색 Index를 사용해서 Record에 접근하는 방법으로 검색할 컬럼에 Index가 있어야 한다. (MATCH ... AGAINST ... 구문을 사용해서 실행된다.)                                                                                                                                                              |
      | `ref_or_null`     | ref와 같은데 NULL 비교가 추가된 형태이다.                                                                                                                                                                                                                                    |
      | `unique_subqery`  | WHERE 조건에 IN 형태(Sub Query)를 갖을 때, Sub Query에서 중복되지 않는 Unique한 값만 반환될 때 사용된다.                                                                                                                                                                                   |
      | `index_subquery`  | IN 연산자 특성상 IN 괄호 조건에 나오는 목록에 중복값이 제거 되어야 한다. Unique 하지 않은 경우에 Index를 이용해서 중복을 제거한다.                                                                                                                                                                            |
      | `range`           | 인덱스를 하나의 값이 아니라 범위로 검색하는 경우에 사용된다. 주로 `<`, `>`, `IS NULL`, `BETWEEN`, `IN`, `LIKE` 등의 연산자로 Index를 검색하는 경우에 사용된다. 통상적으로 Index Scan 이라고 하면 `range`, `const`, `ref`를 묶어서 지칭한다.                                                                                    |
      | `index_merge`     | 2개 이상의 Index를 이용해서 각각의 검색 결과를 만든 후 결과를 합치는 접근 방식으로 실제 우선순위가 range 보다 높지만 효율적으로 동작히지 않는다.                                                                                                                                                                       |
      | `index`           | Index를 처음부터 끝까지 읽어야 하는 경우에 쓰이는 비효율적인 방식이다. 아래 2가지 조건에서 발생한다.<br/> - Index Scan(range, const, ref)이 불가능하고 Index에 포함된 컬럼만으로 처리할 수 있는 경우(데이터 파일을 읽지 않아도 되는 경우)<br/>- Index Scan(range, const, ref)이 불가능하고 Index를 이용해 정렬이나 Grouping 작업이 가능한 경우 (정렬 작업을 피할 수 있는 경우) |
      | `ALL`             | Full Table Scan으로 일반적인 조회 환경에서 가장 안좋은 방식이다. 잘못된 Index를 사용하는 것보다 이 방식이 효율적일 수 있다.                                                                                                                                                                               |
  - `possible_keys`
    - Optimizer가 Query를 처리하기 위해 여러 처리 방법을 고려하던 중에 사용된 후보 Index List
  - `key`
    - possible_keys 컬럼에서 보여진 후보 Index List 중 실제로 사용된 Index를 의미한다. Index를 사용하지 못한 경우 NULL로 표시된다.
  - `key_len`
    - Index가 다중 컬럼으로 만들어졌을 경우, Index 중에서 몇 byte까지 사용했는지 알려준다. 각 Index 컬럼에 할당된 byte를 알 수 있어서 몇 개의 Index 컬럼이 사용되었는지 추산할 수 있다.
    - dept_emp 테이블은 다중 컬럼(dept_no, emp_no)으로 만들어진 Primary가 있고 아래 Query로 테이블을 조회할 때 key_len = 4 가 나온다. dept_no가 INTEGER(byte=4) 이기 때문에 Index 중에서 앞에 dep_no만 쓰였다는걸 추론할 수 있다.
      ```sql
      SELECT *
      FROM dept_emp
      WHERE dept_no=3
      ```
  - `ref`
    - type 컬럼에서 접근 방법이 ref 이면 어떤 컬럼이 조건에 사용되었는지 보여준다.
    - 가공된 컬럼이 사용됐을 땐 `func` 라고 표시된다.
  - `row`
    - Optimizer가 비용을 산정하기 위해 얼마나 많은 레코드를 읽고 비교했는지 예측한 값으로 정확한 값은 아니고 통계에 의한 값이다.
  - `Extra`
    - MySQL이 Query를 어떻게 풀었는지 부가 정보를 표시해준다.
    - | 타입                                                                       | 설명                                                                                                                                                                                                                                                   |
      |------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|---|
      | `const row not found`                                                    | const 접근 방식으로 읽었으나 레코드가 0개인 것을 의미                                                                                                                                                                                                                    |
      | `distinct`                                                               | JOIN 할 때 중복된 값을 제거하고 필요한 필드만 JOIN 했음을 의미                                                                                                                                                                                                             |
      | `Full scan on NULL key`                                                  | WHERE 조건에 nullable 컬럼이 있는 경우 null 일 때 Full Scan을 하겠다는 의미                                                                                                                                                                                             |
      | `Impossible HAVING`<br/>(Query 수정 해야함)                                   | HAVING 조건에 만족하는 레코드가 없는 경우를 의미                                                                                                                                                                                                                       |
      | `Impossible WHERE`<br/>(Query 수정 해야함)                                    | WHERE 조건이 항상 False인 경우를 의미                                                                                                                                                                                                                           |
      | `Impossible WHERE noticed after reading const tables`<br/>(Query 수정 해야함) | Query를 실행해보니 WHERE 조건이 항상 False 라는 의미                                                                                                                                                                                                                |
      | `No matching min/max row`<br/>(Query 수정 해야함)                             | MIN(), MAX() 와 같은 집합 함수가 있는 Query의 WHERE 조건절에 일치하는 레코드가 하나도 없는 경우를 의미                                                                                                                                                                                |
      | `No matching row in const table`<br/>(Query 수정 해야함)                      | const 방식으로 접근할 때 일치하는 레코드가 없는 경우를 의미                                                                                                                                                                                                                 |
      | `No tables used`                                                         | FROM 절이 없거나 DUAL 테이블을 사용한 경우를 의미                                                                                                                                                                                                                     |
      | `Not exists`                                                             | A 테이블에는 존재하지만 B 테이블에 존재하지 않는 값을 조회할 때 `Anti Join`, `Left Outer Join`을 사용한다. <br/>- `Anti Join` : NOT INT, NOT EXISTS 연산자를 사용한 Query<br/>- `Left Outer Join` : 레코드가 많을 때 Anti Join보다 유리한 방식.                                                          | 
      | `Range checked for each record(index map: N)`                            | WHERE 조건에 변수가 2개 이상이 사용되고 그 값이 계속 바뀌는 경우, 레코드마다 인덱스를 탈지 Full Scan을 할지 결정해야 한다. ```SELECT * FROM employees e1, employees e2 WHERE e1.emp_no >= e2.emp_no```                                                                                           |
      | `Scanned N databases`                                                    | Database 메타 정보를 담고 있는 테이블을 조회하는 경우를 의미                                                                                                                                                                                                               |
      | `Select tables optimized away`                                           | SELECT에서 MIN(), MAX()만 사용하거나 GROUP BY로 MIN(), MAX()를 조회하는 쿼리가 적절한 인덱스를 사용해서 레코드 1개만 읽는 형태로 최적화됐을 경우 의미                                                                                                                                               |
      | `unique row not found`                                                   | 2개의 테이블이 Primary Key, Unique Column으로 Outer Join을 할 때 Outer Table에 일치하는 레코드가 없을 경우를 의미<br/>A 테이블에만 있는 레코드로 B 테이블에 조인하려고할 때 B 테이블과 조인하는 컬럼 값이 없는 경우도 생긴다.                                                                                             |
      | `Using filesort`<br/>(튜닝해야함)                                           | ORDER BY를 처리할 때 주로 인덱스를 이용하지만 적절한 인덱스를 사용하지 못한 경우 Mysql이 Sort Buffer에 레코드를 복사해서 정렬는 비효율적인 작업이라는 의미 (튜닝 대상이다.)<br/>`index`는 Full Scan이고 `Using index`는 Covering Index이다.                                                                              |
      | `Using index`                                                            | 데이터 파일을 읽지 않고 인덱스만 읽어서 처리할 수 있는 경우를 의미(=`Covering Index`)                                                                                                                                                                                            |
      | `Using index for group-by`                                               | GROUP BY 처리를 위해 인덱스를 이용하는 경우 즉 `Loose Index Scan`을 의미한다.                                                                                                                                                                                             |
      | `Using join buffer`                                                      | 일반적으로 Join에 사용되는 컬럼은 인덱스를 생성한다.<br/>- `Driving Table` : Join을 하기 위한 먼저 읽은 테이블<br/>- `Driven Table` : 나중에 읽는 테이블<br/>Driving Table에 JOIN 되 컬럼에 인덱스가 없으면 Join Buffer을 사용해서 Join을 하는데 이럴 경우 표시된다.<br/>조인 조건이 없는 `Cartesian Join`은 항상 Join Buffer를 사용한다. |
      | `Using intersect`                                                        | `type=index_merge`인 경우, 2개 이상의 인덱스를 사용될 수 있고 2개의 인덱스로부터 읽은 결과를 어떻게 합쳤는지 보여줄 때 사용된다.<br/>각 인덱스를 사용하는 조건이 `AND`로 연결된 경우 처리 결과에서 AND로 추출했다는 의미.                                                                                                         |
      | `Using union`                                                            | `type=index_merge`인 경우, 2개 이상의 인덱스를 사용될 수 있고 2개의 인덱스로부터 읽은 결과를 어떻게 합쳤는지 보여줄 때 사용된다.<br/>각 인덱스를 사용하는 조건이 `OR`로 연결된 경우 처리 결과에서 OR로 추출했다는 의미.                                                                                                           |
      | `Using sort_union`                                                       | `type=index_merge`인 경우, 2개 이상의 인덱스를 사용될 수 있고 2개의 인덱스로부터 읽은 결과를 어떻게 합쳤는지 보여줄 때 사용된다.<br/>`Using union`으로 처리될 수 없는 경우(=OR로 연결된 레코드가 대량의 range일 경우), Primary Key만 읽어서 먼저 정렬하고 병합한 후에 레코드를 읽어서 반환할 수 있다.                                                 |
      | `Using temporary`                                                        | Mysql은 쿼리를 처리하면서 중간 결과를 담아두기 위해서 임시 테이블을 사용하는데 이 임시 테이블을 사용했을 때 표시된다.<br/>임시 테이블이 생성되는 경우<br/>- FROM절에 Sub Query를 쓸 경우<br/>- `count(distinct name)`과 같이 Index를 사용할 수 없는 경우<br/>- `UNION`, `UNION ALL`이 사용된 쿼리<br/>- Index를 사용하지 못한 정렬 작업             |
      | `Using where`                                                            | Mysql Engine이 별도로 가공/필터 작업을 처리한 경우 표시된다. 범위 조건(`BETWEEN`)은 Storage Engine에서 처리되서 레코드를 반환해주지만 체크 조건은 Mysql Engine에서 처리된다<br/>`SELECT NAME FROM employee WHERE age BETWEEN 20 AND 30 AND gender = 'M'`                                                   |
  - `Filtered`
    - `EXPLAIN` 키워드에 `EXTENDED` 키워드를 붙이면 `Filtered` 컬럼을 볼 수 있다.
    - `Extra` 컬럼에 표시되는 `Using where`은 Storage Engine이 반환해준 레코드들을 MySQL Engine이 필터링하면 등장한다.
    - 이 때, 얼마나 많은 레코드들이 필터링 되는지 보여주는 컬림이다.
    - 필터링 후 몇퍼센트 정도 남았는지 표시해주는데 이는 `통계값이지 정확한 값이 아니다.`
      ```sql
      EXPLAIN EXTENDED
      SELECT *
      FROM dept_emp
      WHERE emp_no BETWEEN 1 AND 10
      ```
- Reference : https://jeong-pro.tistory.com/243

---

SQL Indexing
===

- ## Indexing을 타지 않는 경우
  - 인덱스 컬럼을 변형시킨 경우
    - 인덱스로 잡혀 있는 컬럼에 함수를 적용하거나 그 컬럼 자체를 변형시킨 경우 인덱스를 타지 않는다.
      ```sql
      SELECT * FROM employee WHERE SUBSTR(name, 5) = 'lee';
      SELECT * FROM employee WHERE age + 10 = 30; -- 인덱스를 타지 않음
      SELECT * FROM employee WHERE age = 30 - 10; -- 인덱스를 사용
      ```
  - NULL 조건을 사용했을 경우
    - NULL 조건식을 사용할 경우 `Table Full Scan`이 동작한다.
      ```sql
      SELECT * FROM employee WHERE name IS NULL;
      SELECT * FROM employee WHERE dept IS NOT NULL;
      ```
  - 부정 연산자를 사용한 경우
    - 무조건 인덱스를 타지 않는건 아니고 일반적으로 NOT에 사용된 값이 아닌 경우가 데이터가 많기 때문에 인덱스를 타지 않는다.
      ```sql
      SELECT * FROM employee WHERE age != 30; -- 인덱스를 타지 않음
      SELECT * FROM employee WHERE age > 20 AND age < 30; -- 인덱스를 사용
      ```
  - LIKE 문에서 전체 범위를 설정했을 경우
    ```sql
    SELECT * FROM employee WHERE name LIKE '%yongwoo%'; -- 인덱스를 타지 않음
    SELECT * FROM employee WHERE name LIKE 'yongwoo%'; -- 인덱스를 사용
    ```
  - IN 연산자를 사용했을 경우
    - 부정 연산자와 마찬가지도 무조건 인덱스를 타지 않는건 아니고 조건에 해당하는 데이터가 많다고 판단할 경우 `Tall Full Scan`으로 동작한다.
      ```sql
      SELECT * FROM employee WHERE age IN ( 29, 30, 31);
      ```
  - 복합 인덱스일 경우 첫 인덱스가 첫 조건으로 안들어갔을 경우
    - name, age가 복합 인덱스일 경우 name -> age 순으로 조건을 걸어야 인덱스를 탈 수 있다.
      ```sql
      SELECT * FROM employee WHERE age = 30 AND name = 'yongwoo'-- 인덱스를 타지 않음
      SELECT * FROM employee WHERE name = 'yongwoo' AND age = 30; -- 인덱스를 사용 
      ``` 
  - 인덱스 컬럼이 내부적으로 데이터를 변환할 경우
    - 인덱스 컬럼이 갖고 있는 정확한 데이터 타입을 넣어줘야 한다.
      ```sql
      SELECT * FROM employee WHERE age = '30' -- 인덱스를 타지 않음
      SELECT * FROM employee WHERE age = to_number('30') -- 인덱스를 사용
      ```
- Reference : https://hckcksrl.medium.com/index를-타지않는-쿼리-41f0417bfe03


---

기타
===
- ## HATEOAS
  - HATEOAS = Hypermedia As The Engine Of Application State
  - 하이퍼미디어 특징을 이용하여 HTTP 응답 메세지를 전달할 때 관련 리소스 링크 정보나 다음에 수행할 수 있는 작업 링크 정보를 포함하여 리턴하는 것
  - 응답 데이터에 대한 가독성이 증대되고 리소스 간의 관계를 일관성 있는 링크 형태로 관리할 수 있드는 장점이 있다.
  - 응답 데이터가 다른 리소스 URI와 의존성을 갖게 되기 때문에 구현이 다소 까다롭다는 단점이 있다.
  - HATEOAS 응답 예제
    ```json
    {
      "account_id": 12345,
      "balance": "350,000",
      "links": [
        {
          "rel": "self",
          "href": "http://localhost:8080/accounts/1"
        },
        {
          "rel": "withdraw",
          "href": "http://localhost:8080/accounts/1/withdraw"
        },
        {
          "rel": "transfer",
          "href": "http://localhost:8080/accounts/1/transfer"
        }
      ]
    }
    ```
- ## GraphQL과 REST API
  - #### GraphQL의 등장 배경
    - RESTful API 로는 다양한 기종에서 필요한 정보들을 일일히 구현하는 것이 힘들었다.
    - 예를들어, iOS 와 Android 에서 필요한 정보들이 조금씩 달랐고, 그 다른 부분마다 API 를 구현하는 것이 힘들었다.
    - 이 때문에 정보를 사용하는 측에서 원하는 대로 정보를 가져올 수 있고, 보다 편하게 정보를 수정할 수 있도록 하는 표준화된 Query language 를 만들게 되었다.

  - #### GraphQL과 REST의 차이점
    - REST는 하나의 Resource마다 하나의 Endpoint를 갖고있고 GraphQL은 주로 하나의 Endpoint에 거의 모든 것들를 담고 있다.
    - REST는 응답 구조가 정해져 있는 방면 GraphQL은 요청할 때 사용한 Query 문에 따라 응답의 구조가 달라진다.
  
  - #### GraphQL의 장단점
    - GraphQL은 HTTP 요청 횟수를 줄일 수 있고 응답 크기를 줄일 수 있다.
    - GraphQL은 File 전송 등 Text 만으로 하기 힘든 내용들을 처리하기 복잡하다. (물론 GraphQL 에서 File 전송을 할 수 없는 것은 아니나, 일반적인 GraphQL API 에 비해서 복잡해지거나 외부의 Service 에 의존해야하는 등 문제가 발생한다.)
    - 고정된 요청과 응답만 필요할 경우에는 Query 로 인해 요청의 크기가 RESTful API 의 경우보다 더 커진다.
    - 재귀적인 Query 가 불가능하다. (결과에 따라 응답의 깊이가 얼마든지 깊어질 수 있는 API 를 만들 수 없다.)
  - #### GraphQL과 REST 선택기준
    - ###### GraphQL
      - 서로 다른 모양의 다양한 요청들에 대해 응답할 수 있어야 할 때
      - 대부분의 요청이 CRUD(Create-Read-Update-Delete) 에 해당할 때
    - ###### REST
      - HTTP 와 HTTPs 에 의한 Caching 을 잘 사용하고 싶을 때
      - File 전송 등 단순한 Text 로 처리되지 않는 요청들이 있을 때
      - 요청의 구조가 정해져 있을 때
- Reference
  - https://www.holaxprogramming.com/2018/01/20/graphql-vs-restful-api/

---

### PWA (Progressive Web App)
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

### Web App 비교
Category|Native App | Progressive Web App | Hybrid App | Mobile Web App
---|---|---|---|---
장점 | 성능이 제일 좋고 네이티브 API로 플랫폼에 최적화할 수 있다. | Service Wroker를 사용해서 오프라인이나 느린 네트워크에서도 동작한다. HTTPS를 통해서 제공되서 보안성이 좋다. 플랫폼에 한정받지 않는다. 네이티브 웹보다 저렴하고 빠르게 개발할 수 있다. 설치할 필요가 없고 검색 엔진에서 검색기 가능하다. | 네이티브 API와 브라우저 API를 모두 사용가능하다. 여러 플랫폼에 대응할 수 있다. | 비용이 저렴하고 업데이트가 쉽다. 설치가 필요 없고 모든 기기와 브라우저에서 접근 가능하다.
단점 | 플랫폼에 한정적이다. 설치를 해야한다. | 오래된 브라우저는 PWA를 지원하지 않는다. 배터리 전력 소모가 비교적 크다. | 웹뷰에서 실행하는 경우엔 브라우저 성능이 중요하다. 성능이 안나올때가 많다. UI/UX를 처리하기 어렵다. | 플랫폼 API는 사용하지 못하고 브라우저 API만 사용 가능하다.
특징 |  |  | 
- https://www.hanl.tech/blog/native-vs-hybrid-vs-pwa/

--- 
### BFF (Backend for Frontend)
- `versus API 구조`
  - MSA 환경에서 API 엔트포인트가 분리될 때 팔로업 이슈
  - CORS 이슈
  - API 입장에서 여러 플랫폼과 스펙을 맞출 때의 커뮤니케이션 비용
  - 플랫폼별로 다를 수 밖에 없는 인증 방식을 통합하려는 무리한 시도
  - 클라이언트의 꿈인 '화면에 필요한 데이터만 받는' Partial response를 하기 어려운 이슈
- 이를 해결하고자 프론트엔드를 위한 중간 서버 역할을 하는 BFF가 등장
- 하나의 Frontend에 하나의 BFF가 존재해야 요구사항에 맞게 구현할 수 있다.
- reference : https://fe-developers.kakaoent.com/2022/220310-kakaopage-bff/

### Micro Frontend
- `relate BFF`
- MSA 처럼 전체 화면을 동작할 수 있는 단위로 나누어 개발한 후 서로 조립하는 방식이다.
- Micro Frontent를 어떻게 통합할지 고려해야 한다.
- Backend 호출 구성은 BFF를 사용한다.
--- 
### State Design Pattern
- react의 state를 생각하면 된다.
- 객체가 직접 상태를 체크하여 상태에 따라 행위를 호출하는게 아니라 상태를 객체화하여 상태가 행동을 할 수 있도록 위임하는 디자인 패턴이다.
```java
public interface PowerState {
  public void powerPush();
}

public class On implements PowerState {
  public void powerPush() {
    // 전원 Off
    System.out.println("전원 off");
  }
}

public class On implements PowerState {
  public void powerPush() {
    // 전원 On
    System.out.println("전원 on");
  }
}

public class Saving implements PowerState {
  public void powerPush() {
    // 절전 모드
    System.out.println("전원 절전 모드");
  }
}

/***************************************************/

public class Laptop {
  private PowerState powerState;

  public Laptop() { this.powerState = new Off(); }

  public void setPowerState(PowerState powerState) { this.powerState = powerState; }
  public void powerPush() { powerState.powerPush(); }
}

/***************************************************/

public class Client {
  public static void main(String[] agrs) {
    Laptop laptop = new Laptop();
    On on = new On();
    Off off = new Off();
    Saving saving = new Saving();

    laptop.powerPush();
    laptop.setPowerState(on);
    laptop.powerPush();
    laptop.setPowerState(saving);
    laptop.powerPush();
    laptop.setPowerState(off);
    laptop.powerPush();
    laptop.setPowerState(on);
  }
}
```
- reference : https://victorydntmd.tistory.com/294

---
### Flux Design Pattern
--- 
### SSR vs CSR
- `relate SPA and MPA`
- SSR
  - 초기 로딩 속도가 빠르다.
  - 서버와 통신이 많기 때문에 서버 사용량이 많다.
  - HTML에 대한 정보가 처음에 포함되어 데이터를 수집할 수 있다. (SEO)
- CSR
  - 초기 로딩 속도가 느리다.
  - 서버와 통신이 적어서 서버 사용량이 적다.
  - 처음에 HTML 정보가 비어있어서 크롤러가 데이터를 수집할 수 없다.
### SPA vs MPA
- `relate SSR and CSR`
- SPA
  - 초기 접속 속도 느리다
  - 동적 컨텐츠 로드
  - 검색 엔진 노출을 원하면 MPA
  - 페이지 전환이 빠르고 깜빡거리지 않음
- MPA
  - 전통적인 탐색(검색)이 가능함

--- 
### Lazy Loading

--- 
### CORS(Cross-Origin Resource Sharing)

--- 
### SOLID (객체지향 설계) 원칙
- `S`ingle Responsibility Principle (단일 책임 원칙) : 한 클래스는 하나의 책임만 가져야 한다.
- `O`pen/Closed Principle (개방 폐쇠 원칙) : 소프트웨어 요소는 확장에는 열려 있으나 변경에는 닫혀 있어야 한다.
- `L`iskov substitution Principle (리스코프 치환 원칙) : 프로그램의 객체는 프로그램의 정확성을 깨드리지 않으면서 하위 타입의 인스턴스로 바꿀 수 있어야 한다. 
- `I`nterface Segregration Pringciple (인터페이스 분리 원칙) : 특정 클라이언트를 위한 인터페이스 여러 개가 범용 인터페이스 하나보다 낫다.
- `D`ependency Inversion Principle (의존관계 역전 원칙) : 프로그래머는 추상화에 의존해야지 구체화에 의존하면 안된다.
- reference : https://ko.wikipedia.org/wiki/SOLID_(%EA%B0%9D%EC%B2%B4_%EC%A7%80%ED%96%A5_%EC%84%A4%EA%B3%84)

### 캡슐화
- 객체의 속성과 행위를 하나로 묶는다
- 실제 구현 내용은 내부에 감추어 은닉한다.

### 응집도와 결합도
- `높은` 응집도 : 모듈에 포함된 내부 요소들이 하나의 책임/목적을 위해 연결되어 있는지에 대한 정도
- `낮은` 결합도 : 다른 모듈과의 의존성을 나타내는 정도


### 공부할 것들
# 1️⃣ JVM
## 1. JVM이란?
- Java는 플랫폼에 상관없이 사용할 수 있다. 
  - JVM은 하나의 byte code인 .class를 사용하고 .class는 JVM 위에서 사용되고 JVM은 운영체제에 따라 알아서 실행파일을 만들고 실행합니다. 
  - 따라서 운영체제와 상관없이 Java 언어로 프로그램을 만들 수 있습니다.
- JVM은 플랫폼에 종속적이다.
  - 플랫폼에 따라 JVM은 달리지며 설치되어야 합니다.
- byte code를 읽는 방식
  - JVM은 byte code를 명령어 단위로 읽어서 해석하는데 `Interpreter` 방식과 `JIT Compile` 방식 2가지를 혼용합니다.
  - Interpreter 방식 : byte code를 한 줄씩 해석해서 실행하는 방식. 하지만 속도가 느립니다.
    - JIT(Just In Time) Compile 방식 : btye code를 실제 실행하는 시점에 각 플랫폼에 맞는 Native Code로 변환시켜서 실행하는 방식. 하지만 Native Code로 변환시킬 때 비용이 많이 소요되므로 모든 코드를 JIT Compiler 방식으로 하지 않고 Interpreter 방식을 사용하다가 일정 기준이 넘어가면 JIT Compiler 방식으로 명령어를 실행합니다.
    - 인터프리터에서 JIT로 변경하는 기준은 ?
  - JIT Compiler
    - 같은 코드를 계속 해석하지 않고 코드를 실행할 때 컴파일 하면서 해당 코드를 caching 하고 이후에는 바뀐 코드만 다시 컴파일하고 기존에 있던 코드는 캐싱된 코드를 사용하기 때문에 Interpreter에 비해 속도가 월등히 빠릅니다.
    
<img width="657" alt="image" src="https://user-images.githubusercontent.com/21374902/205311306-c1e245fb-2a44-4ae6-98ca-eccb14409bbd.png">

## 2. JVM 동작 방식
- Java Application이 실행되면 JVM은 OS로부터 Memory를 할당한다.
- Java Compiler(javac)가 Java Code(.java)를 Java Byte Code(.class)로 Compile 한다.
- Class Loader를 통해서 JVM Runtime Data Area로 Loading 한다.
- Runtime Data Area에 Loading 된 Java Byte Code(.class)는 Excution Engine을 통해 해석한다.
- 해석된 Byte Code는 Runtime Data Area의 각 영역에 배치되어 수행되는 과정에서 Execution Engine에 의해 GC 동작과 Thread 동기화가 이뤄진다.

## 3. JVM 구조와 설명
- ### `Class Loader`
  - Java는 동적으로 Class를 불러오기 때문에 프로그램이 실행 중일 때 (Runtime), 모든 코드가 JVM과 연결된다. 이러한 동적으로 Class를 Load 해주는 것이 Class `Loader` 이다. `Class Loader`는 .class 파일을 묶어서 JVM이 운영체제로 부터 할당 받은 `Runtime Data Area`로 적재한다. (Compiler는 .java를 .class로 변환해준다.)
  
    <img width="342" alt="스크린샷 2022-12-10 15 36 02" src="https://user-images.githubusercontent.com/21374902/206893474-4bb88536-1f23-41a2-9eea-488275e4ec49.png">

- ### `Execution Engine`
  - JVM의 `Runtime Data Area`의 `Method Area`에 배치된 Byte Code(.class)을 `Execution Engine`에 제공하여 정의된대로 Byte Code를 실행시키는 역할을 한다. 짧게 말하면 Byte Code를 명령어 단위로 읽어서 실행시키는 Runtime Module 이라고 할 수 있다. 
- ### `Garbage Collector (GC)`
  - 더이상 사용하지 않는 메모리를 자동으로 회수해주는 역할을 한다. 이는 개발자가 따로 메모리 관리를 하지 않아도 되서 프로그래밍이 쉬워진다. Heap 메모리에 생성되있는 객체들 중에서 참조되지 않는 객체들을 탐색하고 제거해주는 역할도 하며 시점은 특정할 수 없다. GC가 수행되면 GC 역할을 수행하는 Thread가 아닌 다른 Thread들은 모두 일시중지가 된다.
    
    <img width="355" alt="스크린샷 2022-12-10 15 36 07" src="https://user-images.githubusercontent.com/21374902/206893547-9e4f6e59-fb52-45a6-b72a-606564860d28.png">

- ### `Runtime Data Area`
  
  <img width="781" alt="스크린샷 2022-12-10 15 36 21" src="https://user-images.githubusercontent.com/21374902/206893557-0350bdbb-eee7-487c-9cf6-dd1d6af7d958.png">
  
  - JVM의 메모리 영역으로 Java Application을 실행할 때 사용되는 데이터들을 적재해서 사용하는 영역
  - 모든 Thread가 공유해서 사용하는 영역 (GC의 대상)
    - #### `Method Area` : 클래스 멤버 변수의 이름, 데이터 타입, 접근 제어자 정보와 같은 각종 필드 정보들과 메서드 정보, 데이터 Type 정보, Constant Pool, static변수, final class 등이 생성되는 영역
    - #### `Heap Area` : new 키워드로 생성된 객체와 배열이 생성되는 영역
    
      <img width="587" alt="스크린샷 2022-12-10 15 36 25" src="https://user-images.githubusercontent.com/21374902/206893577-dd6f728d-e975-4e47-99df-f5f503bfb358.png">

      - ##### `Young Generation` : 객체가 생성됐을 때 저장되는 영역으로 Heap 영역에 객체가 최초로 생성되면 `Eden` 영역에 할당된다. `Eden 영역에 데이터가 어느정도 쌓이게 되면 참조 정도에 따라 `Servivor`의 빈 공간으로 이동되거나 회수된다. 
      - ##### `Tenured Generation` : `Young Generation` 영역이 어느정도 차게되면 참조 정도에 따라 `Old` 영역으로 이동되거나 회수 된다.
        - ###### `Minor GC` : `Young`과 `Tenured` 에서 실행되는 GC
        - ###### `Major GC` : `Old` 영역에 할당된 메모리가 허용치를 넘게되서 `Old` 영역 내 모든 객체들을 검사하고 사용하지 않는 객체는 삭제하는 작업`(Stop-The-World)`으로 시간이 오래 걸리고 그동안 모든 Thread는 중단된다. 
      - ##### `Permanent Generation` : Java 8 부터 사라진 영역. Class, Method Code가 저장되는 영역이다.
- ### `각 Thread가 생성하는 영역`
  - #### `Stack Area` : 지역변수, 파라미터, 리턴 값, 연산에 사용되는 임시 값 등이 생성되는 영역
  - #### `PC Register` : Thread가 생성될 때마다 생성되는 영역으로 프로그램 카운터, 즉 현재 Thread가 실행되는 부분의 주소와 명령을 저장하고 있는 영역
  - #### `Native Method Stack` : Java 이외의 언어(C, C++, 어셈블리 등)로 작성된 Native Code를 실행할 때 사용되는 메모리 영역으로 일반적인 C 스택을 사용하고 보통 C/C++ 등의 코드를 수행하기 위한 스택을 말하며 `(JNI)` Java Compiler에 의해 변환된 Java Byte Code를 읽고 해석하는 역할을 하는 것이 `Java Interpreter`
## Reference
- [[Java] 자바 가상머신 JVM(Java Virtual Machine) 총정리](https://coding-factory.tistory.com/827)

---

# 2️⃣ Garbage Collection (GC)
## 1. GC의 특징
  - GC란 JVM의 Heap 영역에서 동적으로 할당했던 메모리 중 필요 없게 된 메모리 객체를 모아서 주기적으로 제거하는 프로세스
  - GC의 동작 시점을 알 수 없고 GC가 동작하는 동안에는 다른 동작을 모두 멈추기 때문에 Overhead가 발생할 수 있다.
  - Application이 동작하면 객체들을 Head 영역에 생성되고 Method Area나 Stack Area 등 Root Area에서는 Heap Area에 생성 된 객체의 주소만 참조하는 형식으로 구성된다.
  - Method가 끝나거나 생명 주기가 끝나거나 특정 이벤트로 인해서 Heap Area의 객체를 참조하고 있던`(Reachable)` 참조 변수들이 삭제되면 Heap Area에는 아무런 참조도 되지 않는 객체`(Unreachable)`들이 생기게 되고 이 객체들을 GC의 대상이 된다. 즉, GC는 객체를 `Reachable`/`Unreachable`로 구분하고 `Unreachable` 객체가 GC 대상이 된다.
  - GC는 `Mark And Sweep` 알고리즘을 사용해서 동작한다.
    - `Mark(식별)` : Root로부터 그래프 순회를 돌면서 연결된 객체들을 찾아내어 각각 어떤 객체를 참조하고 있는지 Marking 한다.
    - `Sweep(제거)` : Unreachable한 객체들을 Heap Area에서 제거한다.
    - `Compact(재구성)` : Sweep 후에 분산된 객체들을 Heap의 시작 주소로 모아 메모리가 할당된 부분과 그렇지 않은 부분으로 압축한다. (GC 종류에 따라 하지 않는 경우도 있음)
    - GC의 Root로부터 해당 객체에 접근이 가능한지가 Sweep의 대상이 되는데 Heap 메모리 영역을 참조하는 method area, static 변수, stack, native method stack이 Root Space가 된다.

## 2. Heap 메모리 구조
  - JVM의 Heap 영억은 Dynamic Reference 객체들이 저장되는 공간으로 GC의 대상이 되는 공간이다.
  - Heap 영역은 2가지 전제로 설계되었다. (객체는 대부분 일회성이며 메모리에 오랫동안 남아 있는 경우는 드물다는 것)
    - 대부분의 객체는 금방 접근 불가능한 상태(`Unreachable`)가 된다.
    - 오래된 객체에서 새로운 객체로의 참조는 아주 적게 존대한다.
  - 이러한 설계 조건을 바탕으로 효율적인 메모리 관리를 위해 객체에 생존 기간에 따라 물리적으로 Heap 영역을 `Young 영역`과 `Old 영역`으로 설계했다. (`Permanent 영역`은 Java 8부터 제거됨)
  - `Permanent 영역` : 생성된 객체들의 주소값이 저장되는 공간으로 Class Loader에 의해 load 된 Class, Mathod에 대한 Meta 정보가 저장되는 영역이고 JVM에 의해 사용된다. Java 8 이후에는 Native Method Stack에 편입된다.

## 3. Young 영역
  - 새롭게 생성된 객체가 할당되는 영역으로 대부분의 객체가 금방 `Unreachable` 상태가 되기 때문에 많은 객체가 생성되었다가 금방 사라진다.
  - `Young 영역` 대상으로 이뤄지는 GC를 `Minor GC` 라고 한다.
  - `Young 영역`은 3가지 영역으로 나뉜다.
    - `Eden` : `new`를 통해 생성된 객체가 위치하고 정기적으로 Garbage 수집 후 살아남은 객체들을 `Survivor` 영역으로 보낸다.
    - `Survivor 0`, `Survivor 1` : 최소 1번 이상 GC 대상에서 살아남은 객체가 존재하는 영역으로 `Survivor 0`과 `Survivor 1` 둘 중 하나는 꼭 비어 있어야 한다.

## 4. Old 영역
  - `Young 영역`에서 `Reachable` 상태를 유지해서 살아남은 객체가 복사되는 영역으로 `Young 영역`보단 물리적으로 크게 할당되며 영역의 크기가 큰 만큼 Garbage는 `Young 영역`보다 적게 발생한다.
  - `Old 영역` 대상으로 이뤄지는 GC를 `Majob GC` 또는 `Full GC` 라고 한다.

## 5. Minor GC 과정
  - (1) 처음 생성된 객체는 __`Eden`__ 영역에 생성
  - (2) __`Eden`__ 영역이 가득 찼을 때 `Minor GC` 실행 → `Reachable` 객체를 _`Mark`_
  - (3) _`Mark`_ 된 객체들을 __`Survivor 0`__ 영역으로 이동
  - (4) __`Eden`__ 영역에 `Unreachable` 객체를 _`Sweep`_
  - (5) __`Survivor 0`__ 영역에 있는 객체들의 age를 1씩 증가
  - (6) __`Eden`__ 영역이 가득 찼을 때 `Minor GC` 실행 → `Reachable` 객체를 _`Mark`_
  - (7) __`Eden`__, __`Survivor 0`__ 에 있는 _`Mark`_ 된 객체들을 __`Survivor 2`__ 영역으로 이동
  - (8) __`Eden`__, __`Survivor 0`__ 영역에 `Unreachable` 객체를 _`Sweep`_
  - (9) __`Survivor 1`__ 영역에 있는 객체들의 age를 1씩 증가
  - (10) 위 과정을 반복
  ```
  ❓ 객체의 `age` 란 ?
      Survivor 영역에서 살아남은 횟수를 의미하고 Object Header에 기록된다.
      age의 임계값에 다다르면 Promotion(Old 영역으로 이동) 여부를 결정한다.
      JVM의 일반적인 HotSpot JVM의 경우 age의 임계값은 31이다. 
      (Object Header에 age를 기록하는 부분이 6 bit 이기 때문이다.)
  ```

## 6. Major GC 과정
  - `Minor GC` 과정에서 살아남은 객체들(age가 임계치에 다다른 객체들)은 `Old 영역`에 존재하고 `Old 영역`의 메모리가 부족해지면 `Major GC`가 발생한다.
  - `Old 영역`에 있는 모든 객체들을 검사해서 참조되어 있지 않은 객체들을 삭제한다.
  - `Young 영역`은 1초 내외로 `Minor GC`가 끝나지만 `Old 영역`은 큰 공간을 차지하고 있기 때문에 객체를 정리하는 시간이 오래 걸린다.
  - 이 때, `Stop-the-world` 문제가 발생하고 CPU에 부담을 준다. 이를 해결하기 위해 GC 알고리즘은 발전해왔다. GC 알고리즘은 상황에 따라 설정을 통해 특정 알고리즘을 적용할 수 있다.
  ```
  ❓ 객체의 `Promotion` 이란?
      age의 임계치가 되서 Old 영역으로 이동되는 것
  ```

## 7. GC의 종류
- ### `Serial GC`
  - CPU 코어가 1개인 경우 사용하기 위해 개발된 가장 단순한 GC 알고리즘
  - Minor GC에는 `Mark-Sweep`, Major GC에는 `Mark-Sweep-Compact`를 사용한다.
  - CPU 코어가 1개이기 때문에 Stop-the-world 시간이 가장 길다.
  - 실행 명령어 : `java -XX:+UseSerialGC -jar Application.java` 
- ### `Parallel GC`
  - Java 8의 기본 GC 알고리즘
  - Serial GC와 알고리즘은 같지만 Minor GC를 Multi Thread로 수행하고 Major GC는 Single Thread로 실행한다.
  - Serial GC에 비하면 Stop-the-world 시간이 감소한다.
  - 실행 명령어 : `java -XX:+UseParallelGC -XX:ParallelGCThreads=4 -jar Application.java ` 
    - `ParallelGCThreads` : 사용할 쓰레드의 갯수
- ### `Parallel Old GC` (Parallel Compacting Collector GC)
  - Parellel GC를 개선한 버전으로 Major GC에서도 Multi Thread로 실행한다.
  - 기존과 다른 `Mark-Summary-Compact` 방식을 사용한다.
  - 실행 명령어 : `java -XX:+UseParallelOldGC -XX:ParallelGCThreads=4 -jar Application.java`
    - `ParallelGCThreads` : 사용할 쓰레드의 갯수
  ```
  ❓ Mark-Summary-Compact
      Summary 단계는 Sweep과 달리 GC를 수행한 영역에 대한 객체 식별을 거친다.
  ```
- ### `CMS GC` (Concurrent Mark & Sweep GC)
  - Application의 Thread와 GC의 Thread를 동시에 실행해서 Stop-the-world 시간을 최대한으로 줄이기 위한 GC
  - 각 단계에서 한가지 일만 하기 때문에 Stop-the-world로 인한 부하가 가작 짧기 때문에 응답 속도가 매우 중요한 작업에서 많이 사용한다.
  - GC 대상을 파악하는 과정이 복잡한 여러단계로 수행되기 때문에 다른 GC에 비해 CPU 사용량이 높다.
  - Old 영역에 대한 Compact 작업이 기본적으로 이뤄지지 않기 때문에 메모리 파편화(Memory Fragmentation)가 발생하기 때문에 Compact 작업이 자주 일어나는지 확인해야 한다. Compact 작업에 따른 Stop-the-world 시간이 다른 GC보다 길어질 수 있다.
  - Java9 버전부터 deprecated 되었고 Java14 버전부터는 사용이 중지되었다.
  - 실행 명령어 : `java -XX:+UseConcMarkSweepGC -jar Application.java`
  ```
  ❓ CMS GC의 과정
        `Initial Mark` : Class Loader에서 가장 가까운 객체 중 살아있는 객체를 Mark 한다. (가장 짧은 Stop-the-world)
        `Concurrent Mark` : Initial Mark를 통해 살아있다고 확인한 객체에서 참조하는 객체를 Mark 한다.
        `Remark` : Concurrent Mark 단계에서 새로 추가되거나 참조가 끊긴 객체를 확인한다.
        `Concurrent Sweep` : Application Thread가 실행 중인 상태에서 Garbage를 수집한다.
  ```
- ### `G1 GC` (Garbage First)
  - CMS GC를 대체하기 위해 JDK 7 버전부터 Release 된 GC
  - Java 9 버전 이상부터 Default GC
  - 4 GB 이상의 메모리와 Stop-the-world 시간이 0.5초 정도 필요한 상황에 적합하고 Heap이 너무 작을 경우는 미사용 권장
  - Heap 영역을 Young, Old 영역으로 나누지 않고 Region 이라는 영역으로 체스같이 분할하여 상황에 따라 Eden, Survivor, Old 영역의 역할을 동적으로 부여
  - Garbage로 가득한 영역을 빠르게 회수하여 빈 공간을 확보하므로 GC 빈도가 줄어드는 효과가 있다.
  - Young Region 영역은 Parallel GC를 사용하고 Old Region 영역은 Stop-the-world와 Multi-thread 방식 둘 다 사용 가능하다.
  - 살아있는 객체들은 Young/Old의 GC 동안 Virtual Memory Address로 이동하기 때문에 메모리 파편화가 발생하지 않는다.
  - 실행 명령어 : `java -XX:+UseG1GC -jar Application.java`
 ```
 ❓ G1 GC의 Region
       기존의 GC들처럼 메모리를 탐색하면서 객체를 제거하지 않는 방식이 아닌 Garbage가 많은 Region을 우선적으로 GC 한다. 
       Heap 메모리를 Region 영역으로 나눠 탐색하고 Region 단위로 GC 하는 것이다.
       
       기존의 GC는 Eden -> Survivor0 -> Survivor1 으로 순차적으로 이동했지만
       G1 GC에서는 순차적으로 이동시키지 않고 효율적이라고 생각하는 위치로 객체를 Reallocate 시킨다.
 ```
 ```
 ❓ G1 GC의 과정
       `Initial Mark` : Old Region에 존재하는 객체들이 참조하는 Survivor Region을 Mark (Stop-the-world 발생)
       `Root Region Scan` : Initial Mark를 통해 찾은 Survivor Region에 대한 GC 대상 Scan 
       `Concurrent Mark` : 전체 Region을 Scan하고 GC 대상이 없는 Region은 제외
       `Remark` : 최종적인 GC 대상을 Remark (Stop-the-world 발생)
       `Cleanup` : 살아남은 객체가 가장 적은 Region을 대상으로 GC 수행 (Stop-the-world 발생)
                   비워진 Region을 재사용하기 위해 Free List에 추가
       `Copy` : GC가 동작했지만 완전히 비워지지 않은 Region은 새 Region으로 복사해서 Compaction 작업 수행
 ```   

- ### `Shenandoah GC`
  - JDK 12 버전부터 Release 된 GC (Redhat 에서 개발했다.)
  - CMS GC가 갖고 있는 메모리 파편화, G1 GC가 갖고 있는 pause 문제를 해결
  - 강력한 Concurrency와 가벼운 GC 로직으로 Heap 사이즈에 영향을 받지 않고 일정한 pause 시간이 소요되는 것이 특징
  - 실행 명령어 : `java -XX:+UseShenandoahGC -jar Application.java`

- ### `ZGC` (Z Garbage Collector)
  - JDK 15 버전부터 Release 된 GC (Redhat 에서 개발했다.)
  - 큰 사이즈의 메모리 (8MB ~ 16TB)를 low-latency로 처리하기 위해 고안된 GC
  - G1 GC의 Region 처럼 ZPage 라는 영역을 사용하며 Region은 크기가 고정이지만 ZPage는 2MB 배수로 크기를 동적으로 사용된다.
  - Heap 메모리의 크기가 증가해도 Stop-the-world 시간이 10ms를 넘지 않는 것이 큰 장점이다.
  - 실행 명령어 : `java -XX:+UnlockExperimentalVMOptions -XX:+UseZGC -jar Application.java`

## Reference
- [JAVA-☕-가비지-컬렉션GC-동작-원리-알고리즘-💯-총정리](https://inpa.tistory.com/entry/JAVA-%E2%98%95-%EA%B0%80%EB%B9%84%EC%A7%80-%EC%BB%AC%EB%A0%89%EC%85%98GC-%EB%8F%99%EC%9E%91-%EC%9B%90%EB%A6%AC-%EC%95%8C%EA%B3%A0%EB%A6%AC%EC%A6%98-%F0%9F%92%AF-%EC%B4%9D%EC%A0%95%EB%A6%AC)

---

# 3️⃣ REST API
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

---

# 4️⃣ ThreadLocal
## 1. Thread 공통
- 한 Thread에서 읽고 쓰여질 수 있는 변수를 할당하여 접근할 수 있도록 한다. Multi Thread 환경에서 각 Thread 마다 get(), set() method를 통해 독립적으로 변수에 접근이 필요할 때 유용하다.
- Thread의 장점 : Multi Thread는 Multi Processing에 비해 `문맥 교환(Context Switch)`이라는 Overhead가 일어나지 않고 자원을 공유하기 때문에 Process 끼리 통신하는 것보다 비용이 적고 문맥교환이 적어서 효율적인 작업이 가능하다. CPU 사용률을 향상시키고 자원을 적게 소모하며 코드가 간결해진다.
- Thread의 단점 : 여러 Thread가 하나의 Process 내에서 자원을 공유하기 때문에 `동기화(Synchronization)` 문제, `교착상태(Deadlock)`와 같은 문제가 발생할 수 있다.
- Thread Safe
  - Multi Thread 환경에서 Thread끼리 객체를 공유할 때가 있는데 Thread가 동시에 접근하면 안되는 영역을 `임계 영역(Critical Section)` 이라고 하고 이 문제를 해결하기 위해서 `세마포어(Semaphore)`, `상호배제(Mutex)` 등의 개념이 있다.
  - `세마포어(Semaphore)` : 공유된 자원의 데이터를 여러 Process가 접근하지 못하도록 막는 것 
  - `상호배제(Mutex)` : 공유된 자원의 데이터를 여러 Thread가 접근하지 못하도록 막는 것
  - Java의 `Synchronized` keyword를 사용해서 현재 데이터를 사용하고 있는 해당 Thread를 제외하고 다른 Thread는 데이터에 접근할 수 없도록 할 수 있고 이것을 `thread safe` 환경이라고 한다.
  - `thread safe`한 공유 자원에 다른 Thread가 접근하려고 하면 wait 상태가 되기 때문에 성능 저하가 발생할 수 있다.
## 2. ThreadLocal
  - ThreadLocal은 Java에서 제공하는 Class 중에 하나이고 간단히 말하면 하나의 Thread에 의해서만 read/write가 가능한 변수라고 할 수 있다.
  - 2개의 Thread가 같은 코드를 실행하고 하나의 ThreadLocal 변수를 참조하더라고 서로의 ThreadLocal은 각 Thread에서 독립적으로 사용되고 서로의 ThreadLocal을 볼 수 없다.
  - 하나의 Thread에서 실행되는 코드가 동일한 객체를 사용해야할 때 `.set()`, `.get()` 를 사용해서 데이터를 사용하고 메모리 누수의 원인이 될 수 있기 때문에 사용이 끝나면 반드시 `.remove()`로 삭제해줘야 한다.
  - 아래 예제코드에선 ClassA 에서 set(...)한 Date를 ClassB, ClassC 에서 get() 해서 사용한다. 즉 Parameter로 객체를 전달하지 않아도 여러 Thread에서 한 객체의 값을 참조하여 사용할 수 있다.
    ```java
    public static ThreadLocal<Date> tl = new ThreadLocal<>();
    
    class ClassA {
      public void execute() {
        tl.set(new Date());
        ClassB classB = new ClassB();
        classB.execute();
        tl.remove();
      }  
    }
    
    class ClassB {
      public void execute() {
        Date date = tl.get();
        ClassC classC = new ClassC();
        classC.execute();
      }  
    }
    
    class ClassC {
      public void execute() {
        Date date = tl.get();
      }  
    }      
    ```
## Reference
- https://yeonbot.github.io/java/ThreadLocal/

---

# 5️⃣ Java Design Pattern
### 🔰 예제코드 : https://github.com/justdoanything/self-study/tree/main/WIS/Java/src/main/java/book/pattern
## 1) Creational Pattern
  - ###### Instance를 만드는 절차를 추상화해서 객체를 생성, 합성하는 방법이나 객체의 표현 방법을 시스템과 분리해준다.
  - ###### 시스템이 어떤 Concrete Class를 사용하는지에 대한 정보를 캡슐화한다.
  - ###### Class의 Instance들이 어떻게 만들어지고 결합되는지에 대한 부분을 가려준다.
- ### Factory Method Pattern 
  - 여러 개의 Sub Class를 갖고 있는 Super Class가 있을 때 Input Parameter에 따라 하나의 Sub Class를 생성해주는 방식
  - Instance를 필요로 하는 Application에서 Sub Class에 대한 정보는 모른채 Instance를 생성할 수 있다.
  - Factory Class는 Singleton으로 구현하거나 Sub Class를 만드는 함수를 static으로 구현해야 한다.
  - Class 간의 종속성을 낮추고 결합도를 느슨하게 하며 확장을 쉽게 한다.
  ```java
  public abstract class Employee {
    public abstract String getId();
    public abstract String getName();
    public abstract String getDepartment();
  }
  ```
  ```java
  public class Developer extends Employee {
    private String id;
    private String name;
    private String department;

    public Developer(String id, String name, String department) {
      this.id = id;
      this.name = name;
      this.department = department;
    }

    @Override
    public String getId() {
      return this.id;
    }
    public String getName() {
      return this.name;
    }
    public String getDepartment() {
      return this.department;
    }
  }
  ```
  ```java
  public class Tester extends Employee {
    private String id;
    private String name;
    private String department;

    public Tester(String id, String name, String department) {
      this.id = id;
      this.name = name;
      this.department = department;
    }

    @Override
    public String getId() {
      return this.id;
    }
    public String getName() {
      return this.name;
    }
    public String getDepartment() {
      return this.department;
    }
  }
  ```
  ```java
  public class FactoryDesignPattern {
    public static Employee getEmployee(String type, String id, String name, String department) {
      switch(type){
        case Type.Developer : 
          return new Developer(id, name, department);
        case Type.Tester : 
          return new Tester(id, name, department);
        default :
          return null;
      }
    }
  }
  ```
- ### Abstract Factory Pattern
  - Factory Pattern와 유사한 Pattern으로 Factory of Factory 라고 볼 수 있다. 
  - Factory Pattern에서 input parameter로 Sub Class를 구분했었다면 하나의 Factory Class를 input parameter로 받아서 Sub Class를 생성한다.
  - Interface를 위한 코드 접근법을 제공하고 Sub Class를 확장할 때 용이하다. 만약에 Designer Class를 추가하고자 한다면 DesignerFactory만 작성해주면 된다.
  ```java
  public abstract class Employee { ... }
  public class Developer extends Employee { ... }
  public class Tester extends Employee { ... }
  ```
  ```java
  public interface EmployeeAbstractFactory {
    public Employee createEmployee();
  }
  ```
  ```java
  public class DeveloperFactory implements EmployeeAbstractFactory {
    private String id;
    private String name;
    private String department;

    public DeveloperFactory(String id, String name, String department) {
      this.id = id;
      this.name = name;
      this.department = department;
    }

    @Override
    public Employee createEmployee() {
      return new Developer(id, name, department);
    }
  }
  ```
  ```java
  public class TesterFactory implements EmployeeAbstractFactory {
    private String id;
    private String name;
    private String department;

    public TesterFactory(String id, String name, String department) {
      this.id = id;
      this.name = name;
      this.department = department;
    }

    @Override
    public Employee createEmployee() {
      return new Terster(id, name, department);
    }
  }
  ```
  ```java
  public class EmployeeFactory {
    public static Employee getEmployee(EmployeeAbstractFactory factory) {
      return factory.createEmployee();
    }
  }
  ```
  ```java
  public class AbstractFactoryPattern {
    public static void main(String[] args) {
      Employee developer = EmployeeFactory.getEmployee(new DeveloperFactory("1","John","DEV"));
      Employee tester = EmployeeFactory.getEmployee(new TesterFactory("2","Dan","TEST"));
    }
  }
  ```
- ### Singleton Pattern
  - 프로그램 시작부터 종료까지 Singleton Class의 Instance는 메모리 상에 하나만 존재하고 어디에서나 접근할 수 있도록 하는 패턴이다. 어디에서나 접근 가능하도록 하는 방법으로 static을 떠올릴 수 있지만 Instance로 접근하는 방법을 자체적으로 관리하는 방법도 있다.
  - Singleton Class를 구현하는 방법은 여러가지가 있고 아래와 같은 공통적인 특징을 갖는다.
    - private 생성자를 정의해 외부로부터 Instance 생성을 차단한다.
    - Singleton을 구현하고자 하는 클래스 내부에 멤버 변수로 private static 객체 변수를 만든다.
    - public static 메소드를 통해 외부에서 Singleton에 접근할 수 있도록 접점을 제공한다.
  - Singleton Class 생성 방법
    ##### 1. `Eager Initialization`
      - Application에서 해당 Class를 사용하지 않아도 생성한다.
      - Exception에 대한 Handling도 제공하지 않는다.
        ```java
        public class SingletonClass {
          private static final SingletonClass instance = new SingletonClass();
          
          private SingletonClass(){ }
          
          public static SingletonClass getInstance() {
            return instance;
          }
        }
        ```
    ##### 2. `Static Block Initialization`
      - Application에서 해당 Class를 사용하지 않아도 생성한다.
      - Exception에 대한 Handling은 제공한다.
        ```java
        public class SingletonClass {
          private static SingletonClass instance;
          
          private SingletonClass(){}

          static {
            try {
              instance = new SingletonClass();
            }catch(Exception e){
              throw new RuntimeException("There is an exception in creating singleton class");
            }
          }

          public static SingletonClass getInstance() {
            return instance;
          }
        }
        ```
    ##### 3. `Lazy Initialization`
      - Application에서 해당 Class를 사용할 때 초기화 된다.
      - Multi-thread 환경에서 여러 thread가 동시에 getInstance()를 호출하면 동기화 문제가 발생할 수 있다. 따라서 이 방법은 single thread가 보장되는 환경에서 사용해야 한다.
        ```java
        public class SingletonClass {
          private static SingletonClass instance;
          
          private SingletonClass(){}

          public static SingletonClass getInstance() {
            if(instance == null) {
              instance = new SingletonClass();
            }
            return instance;
          }
        }
        ```
    ##### 4. `Thread Safe Singleton`
      - `Lazy Initialization` 방법의 Multi-thread 동기화 문제를 해결하기 위해서 getInstance() 함수에 synchronized를 사용한다.
      - synchronized는 임계 영역(Critical Section)을 형성해 해당 영역에 오직 하나의 Thread만 접근 가능하게 한다. 하지만 synchronized에 사용되는 비용이 크기 때문에 Application의 성능이 떨어질 수 있다.
      - 위 문제를 해결하기 위해 double checked locking을 사용하서 instance가 null일 때에만 synchronized을 실행하게 한다.
        ```java
        public class SingletonClass {
          private static SingletonClass instance;

          private SingletonClass(){}

          public static synchronized SingletonClass getInstance() {
            if(instance == null) {
              instance = new SingletonClass();
            }
            return instance;
          }
        }
        ```
        ```java
        public class SingletonClass {
          private static SingletonClass instance;

          private SingletonClass(){}

          /* double checked locking */
          public static SingletonClass getInstance() {
            if(instance == null) {
              synchronized (SingletonClass.class) {
                if(instance == null) {
                  instance = new SingletonClass();
                }
              }
            }
            return instance;
          }
        }
        ```
    ##### 5. `Bill Pugh Singleton Implementation`
      - inner static helper class를 사용하는 방식이다.
      - 위 방법들이 갖고 있는 문제를 거의 해결한 방식으로 현재 가장 보편적으로 사용되고 있다.
      - synchronized를 사용하지 않으며 getInstance()가 호출될 때 JVM에 Load 되고 instance를 생성한다.
        ```java
        public class SingletonClass {
          private SingletonClass(){}

          private static class SingletonClassHelper {
            private static final SingletonClass INSTANCE = new SingletonClass();
          }

          public static SingletonClass getInstance()  {
            return SingletonClassHelper.INSTANCE;
          }
        }
        ```
    ##### 6. `Enum Singleton`
      - 위 방법은 Java의 Reflection을 통해서 Singleton Class를 파괴할 수 있기 때문에 완벽하게 안전하지는 않다.
      - Application에서 해당 Class를 사용하지 않아도 생성한다.
        ```java
        public enum SingletonClass {
          INSTANCE;

          public static void doingMethod() {
            // doing
          }
        }
        ```
- ### Prototype Pattern
  - Instance를 만드는 절차를 추상화하는 패턴
  - Java의 clone() 메소드를 사용
  - DB로 부터 데이터를 가져와서 만든 하나의 객체를 사용해서 수정하거나 재사용할 경우, 그때마다 DB에서 데이터를 가져오지 않고 하나의 Prototype 객체를 복사해서 사용한다.
  ```java
  public class Employee implements Cloneable {
    private List<String> employees;
    
    public Employee() {
      this.employees = new ArrayList<>();
    }
    
    public Employee(List<String> list) {
      this.employees = list;
    }
    
    public void loadData() {
      employees.add("John");
      employees.add("Pika");
      employees.add("Pie");
      employees.add("Apple");
    }
    
    public List<String> getEmployees() {
      return this.employees;
    }

    public void addEmployee(String employee){
      this.employees.add(employee);
    }
    
    @Override
    public Object clone() throws CloneNotSupportedException {
      List<String> temp = new ArrayList<>();
      for(String emp : this.employees){
        temp.add(emp);
      }
      return new Employee(temnp);
    }
  }
  ```
  ```java
  public class PtorotypeDesignPattern {
    public static void main(String[] args) throws ClloneNotSupportedException {
      Employee employee = new Employee();
      employee.loadData();

      Employee newEmployee1 = (Employee) employee.clone();
      Employee newEmployee2 = (Employee) employee.clone();
      
      newEmployee1.addEmployee("Teresa");
      newEmployee2.addEmployee("Dan");

      System.out.println(employee.getEmployees()); // [John, Pika, Pie, Apple]
      System.out.println(newEmployee1.getEmployees()); // [John, Pika, Pie, Apple, Teresa]
      System.out.println(newEmployee2.getEmployees()); // [John, Pika, Pie, Apple, Dan]
    }
  }
  ```
- ### Builder Pattern
  - Factory Pattern이나 Abstract Factory Pattern은 많은 속성 값을 갖고 있는 Class를 다룰 때 아래와 같은 이슈가 발생한다.
    - 많은 속성 값을 설정할 때 타입, 순서 등에 대한 관리가 어려워 에러가 발생할 확률이 높아진다.
    - 필요 없는 속성에 null을 설정해야 하는 경우가 있다.
    - 생성해야 하는 Sub Class가 무거워지고 복잡해지면서 Factory Class 또한 복잡해질 수 있다.
  - 내부에 Builder Class를 만들어 필수 속성값은 생성자를 통해 받고 선택적인 속성값은 함수를 통해 입력받은 후에 build() 함수를 통해 하나의 instance를 반환하는 방식이다.
  - Builder Class는 Static Nested Class로 생성하고 생성자는 public으로 필수 속성값에 대한 값을 Parameter로 받는다. 생성 대상이 되는 객체의 생성자는 private으로 Builder Class를 통해 속성값들을 설정한다.
  - 선택적인 속성 값 설정은 함수로 제공하고 각 함수의 반환값은 반드시 Builder Class 자신이어야 한다.
  - 생성할 Class는 getter 함수만 갖고 있으며 public 생성자는 존재하지 않아야 한다.
    ```java
    public class Employee {
      private int id;
      private String name;
      private String department;

      private int age;
      private String city;
      private boolean isLeader;

      public int getId() { return id; }
      public String getName() { return name; }
      public String getDepartment() { return department; }

      public int getAge() { return age; }
      public String getCity() { return city; }
      public boolean isLeader() { return isLeader; }

      private Employee(EmployeeBuilder builder) {
        this.id = builder.id;
        this.name = builder.name;
        this.department = builder.department;
        
        this.age = builder.age;
        this.city = builder.city;
        this.isLeader = builder.isLeader;
      }

      public static class EmployeeBuilder {
        private int id;
        private String name;
        private String department;

        private int age;
        private String city;
        private boolean isLeader; 

        public EmployeeBuilder(int id, String name, String department) {
          this.id = id;
          this.name = name;
          this.department = department;
        }

        public EmployeeBuilder age(int age){
          this.age = age;
          return this;
        }

        public EmployeeBuilder city(String city){
          this.city = city;
          return this;
        }

        public EmployeeBuilder isLeader(boolean isLeader){
          this.isLeader = isLeader;
          return this;
        }

        public Employee builder() {
          return new Employee(this);
        }
      }
    }
    ```
- ### Object Pool Pattern
  - Thread Pool 처럼 한정된 Resource서 자원을 재사용하는 방식을 적용할 때 사용하면 성능 개선에 좋다.
  - Connection을 관리하고 이를 재사용하고 공유할 수 있고 객체의 최대 수를 관리할 수 있다.
  - Class Instance의 빠른 초기화가 필요할 때 유용하다.
  - 사용 비용이 높은 객체가 자주 필요한 경우 유용하다.
  - 서로 다른 시간에 동일한 Resource를 필요로 하는 여러개의 Client가 있는 경우 유용하다.
    ```java
    public abstract class ObjectPool<T> {
      private ConcurrentLinkedQueue<T> pool;
      private ScheduledExecutorService executorService;

      protected abstract T createObject();

      private void initialize(final int minObjects) {
        pool = new ConcurrentLinkedQueue<T>();
        for(int i=0; i<minObjects; i++){
          pool.add(createObject());
        }
      }

      public ObjectPool(final int minObjects) {
        initialize(minObjects);
      }

      public ObjectPool(final int minObjects, final int maxObjects, final long validationInterval) {
        initialize(minObjects);

        executorService = Executors.newSingleThreadScheduledExecutor();
        executorService.scheduleWithFixedDelay(new Runnable() {
          @Override
          public void run() {
            int size = pool.size();

            if(size < minObjects) {
              int sizeToBeAdded = minObjects + size;
              for(int i=0; i<sizeToBeAdded; i++){
                pool.add(createObject());
              }
            }else if(size > maxObjects) {
              int sizeToBeRemoved = size - maxObjects;
              for(int i=0; i<sizeToBeRemoved; i++){
                pool.poll();
              }
            }
          }
        }, validationInterval, validationInterval, TimeUnit.SECONDS);
      }

      public T borrowObject() {
        T object;
        if((object == pool.poll()) == null) {
          object = createObject();
        }
        return object;
      }

      public void returnObject(T object) {
        if(object == null) {
          return;
        }
        this.poll.offer(object);
      }

      public void shutdown() {
        if(executorService != null) {
          executeService.shutdown();
        }
      }
    }
    ```
    ```java
    public class ExportingProcess {
      private long processNo;

      public ExportingProcess(long processNo) {
        this.processNo = processNo;
        System.out.println("Object with process no. " + processNo + " was created");
      }

      public long getProcessNo() {
        return processNo;
      }
    }
    ```
    ```java
    public class ExportingTask implements Runnable {
      private ObjectPool<ExportingProcess> pool;
      private int threadNo;

      public ExportingTask(ObjectPool<ExportingProcess> pool, int threadNo) {
        this.pool = pool;
        this.threadNo = threadNo;
      }

      public void run() {
        ExportingProcess exportingProcess = pool.borrowObject();
        System.out.printin("Thread " + threadNo + ": Object with process no. "
        + exportingProcess.getProcessNo() + " was borrowed");

        pool.returnObject(exportingProcess);
        System.out.printin("Thread " + threadNo + ": Object with process no. "
        + exportingProcess.getProcessNo() + " was returned");
      }
    }
    ```
    ```java
    public class ObjectPoolDemo {
      private ObjectPool<ExportingProcess> pool;
      private AtomicLong processNo = new AtomicLong(0);

      public void setUp() {
        pool = new ObjectPool<ExportingProcess>(4, 10, 5){
          @Override
          protected ExportingProcess createObject() {
            return new ExportingProcess(processNo.incrementAndGet());
          }
        }
      }

      public void tearDown() {
        pool.shutdown();
      }

      public void testObjectPool() {
        ExecutorService executor = Executors.newFixedTreadPool(8);
        executor.execute(new ExportingTask(pool, 1));
        executor.execute(new ExportingTask(pool, 2));
        executor.execute(new ExportingTask(pool, 3));
        executor.execute(new ExportingTask(pool, 4));
        executor.execute(new ExportingTask(pool, 5));
        executor.execute(new ExportingTask(pool, 6));
        executor.execute(new ExportingTask(pool, 7));
        executor.execute(new ExportingTask(pool, 8));

        executor.shoutdown();
        try {
          executor.awaitTermination(30, TimeUnit.SECONDS);
        }catch(InterruptedException e){
          e.printStackTrace();
        }
      }

      public static void main(String[] args) {
        ObjectPoolDemo opd = new ObjectPoolDemo();
        opd.setUp();
        opd.tearDown();
        opd.testObjectPool();
      }
    }
    ```

## 2) Structural Pattern
- ###### Structural Pattern은 Class들을 상속과 합성을 이용해서 더 큰 Class를 생성하는 방법을 제공하는 Pattern 이다.
- ###### Interface를 제공하거나 구현을 복잡하게 하는 것이 아니라 객체를 합성하는 방법을 제공한다.
- ###### Compile 단계가 아닌 Runtime 단계에서 복합 방법이나 대상을 변경할 수 있다는 유연성을 갖는다.
- ### Adapter Pattern
- Class의 Interface를 사용자가 기대하는 Interface 형태로 변환시키는 Pattern
- 서로 일치하지 않는 Interface를 갖는 Class들을 함께 동작시킨다.
- Class Adapter : 상속(Inheritance)을 이용한 방법
- Object Adapter : 합성(Composite)을 이용한 방법
  ```java
  public class Employee { // POJO Class
    private String department;

    public Employee(String department) {
      this.department = department;
    }

    public String getDepartment() {
      return department;
    }

    public void setDepartment(String name) {
      this.department = department;
    }

    public String toString() {
      return department;
    }
  }
  ```
  ```java
  public class Team {
    public List<Employee> createDevTeam(){
      return Arrays.asList(new Employee("DEV"));
    }
  }
  ```
  ```java
  public interface EmployeeAdapter {
    public List<Employee> createDevEmployees();
    public List<Employee> createTestEmployees();
    public List<Employee> createManagerEmployees();
  }
  ```
  ```java
  public class EmployeeClassAdapterImpl extends Team implements EmployeeAdapter {
    @Override
    public List<Employee> createDevEmployees(){
      return createDevTeam();
    }

    @Override
    public List<Employee> createTestEmployees(){
      List<Employee> emp = createDevTeam();
      return convertTeam(emp, "TEST");
    }

    @Override
    public List<Employee> createManagerEmployees(){
      List<Employee> emp = createDevTeam();
      return convertTeam(emp, "MANAGER");
    }

    private List<Employee> convertTeam(List<Employee> employees, String teamName) {
      employees.forEach(employee -> employee.setDepartment(teamName));
      return employees;
    }
  }
  ```
  ```java
  public class EmployeeObjectAdapterImpl implements EmployeeAdapter {
    private Team team = new Team();

    @Override
    public List<Employee> createDevEmployees(){
        return team.createDevTeam();
    }

    @Override
    public List<Employee> createTestEmployees(){
        List<Employee> emp = team.createDevTeam();
        return convertTeam(emp, "TEST");
    }

    @Override
    public List<Employee> createManagerEmployees(){
        List<Employee> emp = team.createDevTeam();
        return convertTeam(emp, "MANAGER");
    }

    private List<Employee> convertTeam(List<Employee> employees, String teamName) {
        employees.forEach(employee -> employee.setDepartment(teamName));
        return employees;
    }
  }
  ```
  ```java
  public class AdapterPattern {
    public static void main(String[] args) {
      EmployeeAdapter employeeClassAdapter = new EmployeeClassAdapterImpl();
      employeeClassAdapter.createDevEmployees().forEach(System.out::println);
      employeeClassAdapter.createTestEmployees().forEach(System.out::println);
      employeeClassAdapter.createManagerEmployees().forEach(System.out::println);

      EmployeeAdapter employeeObjectAdapter = new EmployeeClassAdapterImpl();
      employeeObjectAdapter.createDevEmployees().forEach(System.out::println);
      employeeObjectAdapter.createTestEmployees().forEach(System.out::println);
      employeeObjectAdapter.createManagerEmployees().forEach(System.out::println);
    }
  }
  ```
- ### Bridge Pattern
  - 추상화(abstraction)을 구현으로부터 분리하여 각각 독립적으로 변화할 수 있도록 하는 Pattern
  - 추상화와 구현을 독립적으로 다른 계층 구조를 가질 수 있도록 하고 외부로부터 구현을 숨기고 싶을 때 사용한다.
  ```java
  public abstract class Employee {
    protected Team team;

    public Employee(Team team) {
        this.team = team;
    }

    abstract public void applyTeam();
  }
  ```
  ```java
  public class EmployeeDev extends Employee {
    public EmployeeDev(Team team) {
        super(team);
    }

    @Override
    public void applyTeam() {
        System.out.print("This developer is in ");
        team.applyTeam();
    }
  }
  ```
  ```java
  public class EmployeeTester extends Employee {
    public EmployeeTester(Team team) {
        super(team);
    }

    @Override
    public void applyTeam() {
        System.out.print("This tester is in ");
        team.applyTeam();
    }
  }
  ```
  ```java
  public interface Team {
    public void applyTeam();
  }
  ```
  ```java
  public class TeamDev implements Team {
    @Override
    public void applyTeam() {
        System.out.println("Dev Team");
    }
  }
  ```
  ```java
  public class TeamTest implements Team {
    @Override
    public void applyTeam() {
        System.out.println("Test Team");
    }
  }
  ```
  ```java
  public class BridgePattern {
    public static void main(String[] args) {
        Employee devEmployee = new EmployeeDev(new TeamDev());
        devEmployee.applyTeam();

        Employee testerEmployee = new EmployeeTester(new TeamTest());
        testerEmployee.applyTeam();
    }
  }
  ```
- ### Composite Pattern
  - 어느 것도 구분하지 않고 한 가지 동작을 가능하도록 하는 것처럼 일괄적인 관리가 가능하도록 하는 Pattern
  - `Base Component` : Client가 Composition 내의 Objects를 다루기 위해 제공되는 Interface 로 모든 Objects에 공통되는 Method를 갖고 있다.
  - `Leaf` : Composition 내 Objects의 행동을 정의한다. 즉, Base Component를 구현한다.
  - `Composite` : Leaf Object로 이루어져 있으며 Base Component 내 명령들을 구현한다.
  - `Composite`도 `Base Component`를 구현하면서 다른 `Leaf`와 같이 사용할 수 있어야 한다.
  ```java
  /* Base Component */
  public interface Employee {
    public void doWork(String work);
  }
  ```  
  ```java
  /* Leaf Object */
  public class DevEmployee implements Employee {
    private String name;

    public DevEmployee(String name){
        this.name = name;
    }

    public String toString() {
        return name;
    }

    @Override
    public void doWork(String work) {
        System.out.println("DevEmployee " + name + " is doing " + work);
    }
  }
  ```
  ```java
  /* Leaf Object */
  public class TestEmployee implements Employee {
    private String name;

    public TestEmployee(String name){
        this.name = name;
    }

    public String toString() {
        return name;
    }

    @Override
    public void doWork(String work) {
        System.out.println("TestEmployee " + name + " is doing " + work);
    }
  }
  ```
  ```java
  /* Composite Object */
  public class Working implements Employee {
    private List<Employee> employees = new ArrayList<>();

    @Override
    public void doWork(String work) {
        for(Employee employee: employees) {
            employee.doWork(work);
        }
    }

    public void add(Employee employee) {
        System.out.println("Add Employee : " + employee);
        this.employees.add(employee);
    }

    public void remove(Employee employee) {
        System.out.println("Remove Employee : " + employee);
        this.employees.remove(employee);
    }

    public void clear() {
        System.out.println("Remove All of Employee");
        this.employees.clear();
    }
  }
  ```
  ```java
  public class CompositePattern {
    public static void main(String[] args) {
        Employee devEmployee = new DevEmployee("John");
        Employee testEmployee = new TestEmployee("Lin");

        // Leaf 객체들을 Grouping 후 사용
        Working working = new Working();
        working.add(devEmployee);
        working.add(testEmployee);
        working.doWork("Daily Scrum Meeting.");

        List<Employee> employees = new ArrayList<>();
        // Composite Object(Working Class)도 Base Component(Employee Class)를 구현하고 있기 때문에 다른 Leaf Class와 같이 사용할 수 있다.
        employees.add(working);
        employees.add(new DevEmployee("Beaver"));
        employees.add(new TestEmployee("Dan"));
        for(Employee employee: employees) {
            employee.doWork("Coffee Time");
        }
    }
  }
  ```
- ### Decorator Pattern
  - 상속과 합성을 사용해서 한 객체에 동적인 책임을 추가할 수 있게 한다.
  - Runtime 단계에서 여러 개의 Class의 특징을 모두 갖는 Class를 얻고 싶을 때 사용할 수 있다.
  ```java
  public interface Employee {
    public void assemble();
  }
  ```
  ```java
  public class NormalEmployee implements Employee {
    @Override
    public void assemble() {
        System.out.println("Normal Employee.");
    }
  }
  ```
  ```java
  public class EmployeeDecorator implements Employee {
    protected Employee employee;

    public  EmployeeDecorator(Employee employee) {
        this.employee = employee;
    }

    @Override
    public void assemble() {
        this.employee.assemble();
    }
  }
  ```
  ```java
  public class DevEmployee extends EmployeeDecorator {
    public DevEmployee(Employee employee){
        super(employee);
    }

    @Override
    public void assemble() {
        super.assemble();
        System.out.println("  Adding features of Dev Employee.");
    }
  }
  ```
  ```java
  public class TestEmployee extends EmployeeDecorator {
    public TestEmployee(Employee employee){
        super(employee);
    }

    @Override
    public void assemble() {
        super.assemble();
        System.out.println("  Adding features of Test Employee.");
    }
  }
  ```
  ```java
  public class DecoratorPattern {
    public static void main(String[] args) {
        Employee devEmployee = new DevEmployee(new NormalEmployee());
        devEmployee.assemble();
        System.out.println("********************");

        // 2개의 Class 특성을 갖는 Class 생성
        Employee multiEmployee = new DevEmployee(new TestEmployee(new NormalEmployee()));
        multiEmployee.assemble();
    }
  }
  ```
- ### Facade Pattern
- Sub System을 더 쉽게 사용할 수 있도록 Higher-level Interface를 정의하고 제공한다.
  ```java
  import java.sql.Connection;

  public class MySqlHelper {
    public static Connection getConnection() {
        return null;
    }

    public static void generatePdfReport(String tableName, Connection connection) {

    }

    public static void generateHtmlReport(String tableName, Connection connection) {

    }
  }
  ```
  ```java
  import java.sql.Connection;

  public class OracleHelper {
    public static Connection getConnection() {
        return null;
    }

    public static void generatePdfReport(String tableName, Connection connection) {

    }

    public static void generateHtmlReport(String tableName, Connection connection) {

    }
  }
  ```
  ```java
  import java.sql.Connection;

  public class FacadeHelper {
    public static enum DBTypes {
        MYSQL, ORACLE
    }

    public static enum ReportTypes {
        HTML, PDF
    }

    public static void generateReport(DBTypes dbTypes, ReportTypes reportTypes, String tableName) throws RuntimeException {
        Connection connection = null;
        switch (dbTypes) {
            case MYSQL:
                connection = MySqlHelper.getConnection();
                switch (reportTypes){
                    case HTML:
                        MySqlHelper.generateHtmlReport(tableName, connection);
                        break;
                    case PDF:
                        MySqlHelper.generatePdfReport(tableName, connection);
                        break;
                    default:
                        throw new RuntimeException("Report Type Error.");
                }
                break;
            case ORACLE:
                connection = OracleHelper.getConnection();
                switch (reportTypes){
                    case HTML:
                        OracleHelper.generateHtmlReport(tableName, connection);
                        break;
                    case PDF:
                        OracleHelper.generatePdfReport(tableName, connection);
                        break;
                    default:
                        throw new RuntimeException("Report Type Error.");
                }
                break;
            default:
                throw new RuntimeException("Database Type Error.");
        }
    }
  }
  ```
  ```java
  public class FacadePattern {
    public static void main(String[] args) {
        final String tableName = "Employee";

        FacadeHelper.generateReport(FacadeHelper.DBTypes.MYSQL, FacadeHelper.ReportTypes.HTML, tableName);
        FacadeHelper.generateReport(FacadeHelper.DBTypes.MYSQL, FacadeHelper.ReportTypes.PDF, tableName);

        FacadeHelper.generateReport(FacadeHelper.DBTypes.ORACLE, FacadeHelper.ReportTypes.HTML, tableName);
        FacadeHelper.generateReport(FacadeHelper.DBTypes.ORACLE, FacadeHelper.ReportTypes.PDF, tableName);
    }
  }
  ```
- ### Flyweight Pattern
  - 공유를 통해서 대량의 객체들을 효과적으로 지원하는 방법
  - 대량의 객체를 생성해야할 때 사용되며 메모리를 효율적으로 관리할 수 있다.
  - Flyweight Pattern이 유리한 상황
    - Application에 의해 생성되는 객체의 수가 많을 때
    - 생성된 객체가 오래되면 메모리에 상주하고 사용되는 횟수가 많을 때
    - 객체의 특성을 내적 속성(Intrinsic Properties)과 외적 속성(Extrinsic Properties)로 나눴을 때 외적 특성이 Client로부터 정의될 때
      ```
      ❓ 내적 속성 (Intrinsic Properties)
         객체를 유니크하게 하는 것

      ❓ 외적 속성 (Extrinsic Properties)
         Client로부터 설정되어 다른 동작을 수행하도록 하는 특성
      ```
  - 예제코드에서 Oval Class는 fill 이라는 내적 속성을 갖는다.
  - 예제코드에서 객체가 생성될 때 2초의 delay를 줘서 Flyweight Pattern의 효과를 확인할 수 있다. 
  - Factory Class는 Client가 객체의 Instance를 생성할 때 사용하고 객체들은 Factory Class 내부에서 Map으로 관리됩니다. Client가 객체에 대한 Instance를 얻기 위해 호출할 때 기존에 객체가 있으면 Map에서 반환하고 없다면 새로운 객체를 생성하고 Map에 넣은 후 반환한다.
  ```java
  import java.awt.Color;
  import java.awt.Graphics;

  public interface Shape {
    public void draw(Graphics graphics, int x, int y, int width, int height, Color color);
  }
  ```
  ```java
  import java.awt.Color;
  import java.awt.Graphics;

  public class Line implements Shape {
    public Line() {
        System.out.println("Create Line");
        try{
            Thread.sleep(2000);
        }catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void draw(Graphics graphics, int x, int y, int width, int height, Color color) {
        graphics.setColor(color);
        graphics.drawLine(x, y, width, height);
    }
  }
  ```
  ```java
  import java.awt.Color;
  import java.awt.Graphics;

  public class Oval implements Shape {
    // 내적 속성 (intrinsic Property)
    private boolean fill;

    public Oval(boolean fill) {
        this.fill = fill;
        System.out.println("Create Oval with fill : " + fill);
        try {
            Thread.sleep(2000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    @Override
    public void draw(Graphics graphics, int x, int y, int width, int height, Color color) {
        graphics.setColor(color);
        graphics.drawOval(x, y, width, height);
        if(fill){
            graphics.fillOval(x, y, width, height);
        }
    }
  }
  ```
  ```java
  public class FlyweightFactory {
    public enum ShapeTypes {
        OVAL, OVAL_FILL, LINE;
    }
    public static final HashMap<ShapeTypes, Shape> shapes = new HashMap<>();

    public static Shape getShape(ShapeTypes shapeTypes) {
        Shape shape = shapes.get(shapeTypes);

        if(shape == null) {
            switch (shapeTypes){
                case LINE:
                    shape = new Line();
                    break;
                case OVAL:
                    shape = new Oval(false);
                    break;
                case OVAL_FILL:
                    shape = new Oval(true);
                    break;
                default:
                    throw new RuntimeException("Shape Types Error.");
            }
            shapes.put(shapeTypes, shape);
        }
        return shape;
    }
  }
  ```
  ```java
  import javax.swing.JButton;
  import javax.swing.JFrame;
  import javax.swing.JPanel;
  import java.awt.BorderLayout;
  import java.awt.Color;
  import java.awt.Container;
  import java.awt.Graphics;
  import java.awt.event.ActionEvent;
  import java.awt.event.ActionListener;

  public class FlyweightPattern extends JFrame {
    private final int width;
    private final int height;

    private static final FlyweightFactory.ShapeTypes shapes[] = { FlyweightFactory.ShapeTypes.LINE, FlyweightFactory.ShapeTypes.OVAL, FlyweightFactory.ShapeTypes.OVAL_FILL };
    private static final Color colors[] = { Color.RED, Color.GREEN, Color.YELLOW };

    public FlyweightPattern(int width, int height) {
        this.width = width;
        this.height = height;
        Container container = getContentPane();

        JButton jButton = new JButton("Draw");
        final JPanel jPanel = new JPanel();

        container.add(jPanel, BorderLayout.CENTER);
        container.add(jButton, BorderLayout.SOUTH);
        setSize(width, height);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

        jButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Graphics graphics = jPanel.getGraphics();
                for(int i=0; i<20; i++){
                    Shape shape = FlyweightFactory.getShape(getRandomShape());
                    shape.draw(graphics, getRandomX(), getRandomY(), getRandomWidth(), getRandomHeight(), getRandomColor());
                }
            }
        });
    }

    private FlyweightFactory.ShapeTypes getRandomShape() {
        return shapes[(int) (Math.random() * shapes.length)];
    }

    private int getRandomX() {
        return (int) (Math.random() * width);
    }

    private int getRandomY() {
        return (int) (Math.random() * height);
    }

    private int getRandomWidth() {
        return (int) (Math.random() * (width / 10));
    }

    private int getRandomHeight() {
        return (int) (Math.random() * (height / 10));
    }

    private Color getRandomColor() {
        return colors[(int) (Math.random() * colors.length)];
    }

    public static void main(String[] args) {
        FlyweightPattern drawing = new FlyweightPattern(500,600);
    }
  }
  ```
- ### Proxy Pattern
  - 다른 객체로 접근하는 것을 통제하기 위해서 그 객체의 대리자(surrogate)나 자리표시자(placeholder)의 역할을 하는 객체를 제공하는 Pattern
  - Client에게 객체 자체를 제공하지 않고 앞단에서 특정 로직을 처리하는 Proxy 객체를 사용한다.
  ```java
  public interface CommandExecutor {
    public void runCommand(String command) throws Exception;
  }
  ```
  ```java
  public class CommandExecutorImpl implements CommandExecutor {
    @Override
    public void runCommand(String command) throws Exception {
        System.out.println("Execute Command : " + command);
        Runtime.getRuntime().exec(command);
    }
  }
  ```
  ```java
  public class CommandExecutorProxy implements CommandExecutor {
    private boolean isAdmin;
    private CommandExecutor commandExecutor;

    public CommandExecutorProxy(String user, String pwd) {
        if("Admin".equals(user) && "Password".equals(pwd)){
            isAdmin = true;
        }
        commandExecutor = new CommandExecutorImpl();
    }

    @Override
    public void runCommand(String command) throws Exception {
        if(isAdmin) {
            commandExecutor.runCommand(command);
        }else {
            if(command.trim().startsWith("rm")){
                throw new RuntimeException("This command cannot run if not admin.");
            }else{
                commandExecutor.runCommand(command);
            }
        }
    }
  }
  ```
  ```java
  public class ProxyPattern {
    public static void main(String[] args) {
        CommandExecutor commandExecutor = new CommandExecutorProxy("Admin", "WrongPassword");
        try{
            commandExecutor.runCommand("ls -ltr");
            commandExecutor.runCommand("rm -rf test.test");
        }catch (Exception e){
            e.printStackTrace();
        }
    }
  }
  ```

## 3) Behavioral Pattern
- ### Chain of Responsibility
- ### Command Pattern
- ### Interpreter Pattern
- ### Iterator Pattern
- ### Mediator Pattern
- ### Memento Pattern
- ### Observer Pattern
- ### State Pattern
- ### Strategy Pattern
- ### Template Pattern
### Reference
- https://www.javatpoint.com/design-patterns-in-java
- https://readystory.tistory.com/category/JAVA/Design%20Pattern
- https://johngrib.github.io/wiki/pattern/#documents
### 🔰 예제코드 : https://github.com/justdoanything/self-study/tree/main/WIS/Java/src/main/java/book/pattern

---

# 6️⃣ KAFKA
- Apache Kafka는 분산 스트리밍 플랫폼이며 데이터 파이프 라인을 만들 때 주로 사용하는 오픈소스 솔루션
- Kafka는 대용량의 실시간 로그 처리에 특화되어 있는 솔루션이며 데이터를 유실 없이 안전하게 전달하는 것이주목적인 메세지 시스템에서 fault-tolerant한 안정적인 아키텍쳐와 빠른 퍼포먼스로 데이터를 처리하는용도로 사용
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
- Kafka 실행
  - Zookeeper 실행 : `bin/zookeeper-server-start.sh -daemon config/zookeeper.properties`
  - Kafka 실행 : `bin/kafka-server-start.sh -daemon config/server.properties`
  - Zookeeper&Kafka 실행 확인 : `netstat -an | grep 2181`
  - Kafka topic 생성 : `bin/kafka-topics.sh --create --bootstrap-server localhost:9092 --replication-factor 1 --partitions 1 --topic test`
  - Kafka topic 생성 확인 : `bin/kafka-topics.sh --list --bootstrap-server localhost:9092`
  - Kafka 메세지 발행 : `bin/kafka-console-producer.sh --bootstrap-server localhost:9092 --topic test`
  - Kafka 메세지 소비 : `bin/kafka-console-consumer.sh --bootstrap-server localhost:9092 --topic test --from-beginning`
  - Reference : https://developer-youngjun.tistory.com/13

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