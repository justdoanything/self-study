Application Modernization
===

# 목차
<!-- TOC -->
* [NGINX](#nginx)
* [Apache and NGINX](#apache-and-nginx)
* [Redis](#redis)
* [Middleware](#middleware)
* [WAF (Web Application Firewall)](#waf--web-application-firewall-)
* [Yarn](#yarn)
* [OAuth 2.0](#oauth-20)
* [Netty와 NIO](#netty와-nio)
* [SSR vs CSR](#ssr-vs-csr)
* [SPA vs MPA](#spa-vs-mpa)
* [Lazy Loading](#lazy-loading)
* [CORS(Cross-Origin Resource Sharing)](#cors--cross-origin-resource-sharing-)
* [Flyway](#flyway)
<!-- TOC -->

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

SSR vs CSR
===
- `relate SPA and MPA`
- SSR
  - 초기 로딩 속도가 빠르다.
  - 서버와 통신이 많기 때문에 서버 사용량이 많다.
  - HTML에 대한 정보가 처음에 포함되어 데이터를 수집할 수 있다. (SEO)
- CSR
  - 초기 로딩 속도가 느리다.
  - 서버와 통신이 적어서 서버 사용량이 적다.
  - 처음에 HTML 정보가 비어있어서 크롤러가 데이터를 수집할 수 없다.

---

SPA vs MPA
===
- `relate SSR and CSR`
- SPA
  - 초기 접속 속도 느리다
  - 동적 컨텐츠 로드
  - 검색 엔진 노출을 원하면 MPA
  - 페이지 전환이 빠르고 깜빡거리지 않음
- MPA
  - 전통적인 탐색(검색)이 가능함

--- 

Lazy Loading
===

---

CORS(Cross-Origin Resource Sharing)
===

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