목차
===
1. [Web Server와 WAS](#Web-Server-And-WAS)
2. [NGINX](#NGINX)
3. [Apache와 NGINX](#Apache-and-NGINX)
4. [WAF](#WAF-(Web-Application-Firewall))
5. [ECS와 EC2](#AWS-ECS-and-EC2)
6. [Middle Ware](#Middle-Ware)
7. [Yarn](#Yarn)
8. [Redis](#Redis)


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
  - 쓰레드 / 프로세스 기반 구조로 요청 하나당 쓰레드 하나가 처리하는 구조
  - 사용자가 많으면 많은 쓰레드 생성, 메모리 및 CPU 낭비가 심함
  - 하나의 쓰레드 : 하나의 클라이언트 라는 구조

- NGINX (Web Server)
  - 비동기 Event-Driven 기반 구조. 
  - 다수의 연결을 효과적으로 처리가능. 
  - 대부분의 코어 모듈이 Apache보다 적은 리소스로 더 빠르게 동작가능
  - 더 작은 쓰레드로 클라이언트의 요청들을 처리가능

- 이렇게 쓰레드 기반은 하나의 커넥션당 하나의 쓰레드를 잡아 먹지만 이벤트 드라이븐방식은 여러개의 커넥션을 몽땅 다 Event Handler를 통해 비동기 방식으로 처리해 먼저 처리되는 것부터 로직이 진행되게끔 합니다. 이것과 관한 비동기 방식과 이벤트 드라이븐 방식은 아래 글을 참고 하면 됩니다. 

![image](https://user-images.githubusercontent.com/21374902/150284927-d9b7fd92-7f26-4126-bdc0-c25b4bb8c69d.png)

![image](https://user-images.githubusercontent.com/21374902/150284969-db228409-e99e-4583-9a52-3de516011a31.png)

- Reference
  - https://m.blog.naver.com/jhc9639/220967352282

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
AWS ECS and EC2
===
- #### ECS (Elastic Container Service)
  - AWS 카테고리로 봤을 땐 `Container Orchestration`에 속한다.
  - Cluster에서 Container를 손쉽게 실행, 중지 및 관리할 수 있게 해주는 컨테이너 관리 서비스 
  - 간단한 API 호출을 통해서 Container 기반의 Application을 제어할 수 있습니다.
  - 리소스, 격리 정책, 가용성 요구사항을 기반으로 Cluster에 Container 배치를 예약할 수 있습니다.
  - 일관된 배포 및 구축 환경을 생성할 수 있고 배치 및 ETL(Extract-Transform-Load) 워크로드를 관리할 수 있습니다.
  - VPC 내에 Amazon ECS Cluster를 생성할 수 있고 Coinater Image는 ECR(Elastic Container Registry)에 저장됩니다.
  - `Task Definition` : JSON 타입의 파일로 사용할 자원, 실행할 container 개수 등을 정의한다. 여러개의 Task를 정의해서 Application을 시작할 때 시작시킬 수 있다.
  - `Task` and `Scheduling` : Task는 Cluster 내에서 Task Definition 기반으로 수행되는 인스턴스입니다. Scheduling은 cluster 내에서 수행할 task를 관리한다.
  - `ECS Cluster` : ECS Cluster는 Task 또는 Service의 논리적 그룹입니다. Cluster에 1개 이상의 EC2를 등록하고 여기에서 Task를 실행할 수 있습니다. 
  - `Container Agent` : ECS Cluster 내에 있는 Container 안에서 실행되고 Resource 사용률에 대한 정보를 ECS 로 전송합니다. Agent는 ECS로부터 요청을 받을때마다 시작되거나 중지된다.
  ![image](https://user-images.githubusercontent.com/21374902/150706403-5c721f9c-5b06-412d-8018-e46c3fffe862.png)
- #### EC2 (Elastic Compute Cloud)
  - AWS 카테고리로 봤을 땐 `Computing Option` 이고 `Cloud Computing Service` 라고도 불린다.
  - AWS 내에 컴퓨터 1개를 구성하는 것과 비슷한 개념으로 AWS가 제공하는 URL(Public DNS)을 통해 이 컴퓨터에 접근할 수 있다. (=> 기존에는 H/W를 구매해서 `컴퓨터`를 구성했지만 AWS에선 클릭 몇번으로 더 쉽게 구매와 설정이 가능해지도록 만들었다. )
  - 사용한만큼 비용을 내며 생성/삭제가 아주 간단하다.
  - SSH를 통해 외부에서 이 컴퓨터에 연결할 수 있다.
  - 보안, 네트워킹 그리고 스토리지 관리에 유용하다.
  - EC2가 제공하는 기능
    - AMI (Amazon Machine Image) : 서버에 필요한 운영체제와 여러 소프트웨어들이 적절히 구성된 상태로 제공되는 템플릿으로 인스턴스를 쉽게 만들 수 있다.
    - CPU, Memory, Storage, Network 등 여러 종류의 구성을 제공
    - Key Pair를 사용하여 인스턴스 로그인 정보 보호
    - Instance Store Volumn : 임시 데이터를 저장하는 스토리지 볼륨으로 인스턴스 중단, 최대 절전 모드로 전환 또는 종료 시 삭제됨
    - EBS (Elastic Block Store)를 사용해서 영구 Storage에 데이터 저장 가능


- Reference
  - https://docs.aws.amazon.com/ko_kr/AmazonECS/latest/developerguide/Welcome.html
  - https://docs.aws.amazon.com/ko_kr/AWSEC2/latest/UserGuide/concepts.html
---
Middle-Ware
===


---

Yarn
===


---



Redis
===


---