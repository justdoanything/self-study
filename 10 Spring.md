Spring
===

# 목차
<!-- TOC -->
* [Spring](#spring)
* [목차](#목차)
* [Spring](#spring-1)
* [Spring MVC](#spring-mvc)
* [Spring Cloud](#spring-cloud)
* [Spring Boot](#spring-boot)
* [Spring Batch](#spring-batch)
* [Spring JPA](#spring-jpa)
* [Spring Testing](#spring-testing)
* [SpringBoot의 비동기 처리 방식](#springboot의-비동기-처리-방식)
<!-- TOC -->

---

Spring
===
- #### Spring Framework vs. Spring Boot Framework
  - 기존에 Spring을 설치하고 설정할 때 수많은 context를 설정해야하고 tomcat을 따로 설정하고 Hibernate, datasource 등 환경설정에 소요되는 시간이 너무 많았었는데 Spring Boot는 이런 기본적인 부분들은 모두 package로 붙어서 자동으로 설정 및 사용해주고 개발자는 변경하고 싶은 설정은 yml에 정의하거나 @Configuration 어노테이션으로 Spring이 실행될 때 같이 실행되도록 할 수 있다.
  - Spring Boot는 spring-boot-starter라는 dependency를 사용하여 개발자가 따로 설정하지 않아도 자동으로 구성해주기 때문에 spring을 구동하는 방식이 아주 간단해 집니다.

![image](https://user-images.githubusercontent.com/21374902/148175368-23100def-25b9-47e7-9574-fb67f770a3b3.png)

- #### Spring 주요 특징
  🎈 제어의 역전(IOC = Inversion of Control)
    - Java Application 초기엔 객체의 생성,객체 간의 의존관계 연결 등 제어권을 개발자가 갖고 있었지만 Servlet, EJB가 등장하면서 개발자가 독점적으로 갖고 있는 제어권을 넘기게 된다. 이처럼 객체의 생성과 생명주기의 관리까지 제어권이 Spring으로 바뀐 것을 IOC 라고 한다.
    - EJB (Enterprice Java Beans) :Java 객체를 재사용 가능하도록 컴포넌트화 시킬 수 있도록 정한 개발 표준 방식 하지만 불필요한 코드가 많고 단위 테스트가 불가능하다는 단점이 존재했다.

  🎈 의존성 주입(DI = Dependency Injection)
    - 객체 사이의 의존 관계를 객체 자신이 아닌 외부의 조립기(Assembler)가 수행하는 개념
    - 객체 사이의 필요한 의존 관계에 대해서 Spring Container가 어느정도는 자동으로 연결해준다.
    - Spring Container에 Bean을 주입하는 방식은 XML을 사용하거나 @Annotation을 사용할 수 있다.

  🎈 관점 지향 프로그래밍(AOP = Aspect Oriented Programming)
    - 어떤 로직을 기준으로 핵심적인 부분과 부가적인 부분을 나눠보고 그 기준으로 모듈화를 하는 것이다.
    - 흩어진 관심사를 Crosscutting Concerns이라고 부르고 이런 흩어진 관심사를 Aspect로 모듈화하고 핵심적인 모듈에서 분리해서 사용할 수 있다.
    - 특정 함수를 @Aspect로 감싸고 특정 Package에 적용하도록 할 수 있다. 원하는 모듈을 합쳐서 만들고 1개의 모듈로 사용할 수 있다는 것이다.

  🎈 POJO (Plain Old Java Object)
    - EJB와 같은 방식에서 사용하던 Object 그대로를 Spring에 녹일 수 있는 방식이다.
    - 환경과 기술에 종속되지 않고 필요에 따라 활용하여 사용할 수 있는 Object를 말한다. 간단한 예로는 DTO, VO와 같이 getter/setter로 되있는 단순 Object를 말하기도 한다.
- Reference
  - https://m.blog.naver.com/sillllver/220593543939
  - https://hoon93.tistory.com/56
  - https://engkimbs.tistory.com/746

---

Spring MVC
===
### ⚠️ 스프링 MVC 1편 - 백엔드 웹 개발 핵심 기술 / 김영한 / 인프런 강의를 보고 Spring MVC를 만드는 과정에 대해서 그림만 참조하고 내용은 스스로 작성해본다.

- ### Version 0
  - `Servlet`은 HTTP Protocol에 맞는 request, response를 자동으로 만들어준다.
  - 개발자가 직접 IP/PORT에 들어온 Request를 파싱하거나 처리할 필요 없이 `HttpServletRequest`,`HttpServletResponse` 객체에서 데이터를 빼서 쓰거나 넣어주면 된다.
  - Content-Type, Content-Length 등 Servlet이 HTTP 규격에 맞게 다 세팅 및 변환을 해주기 때문에 개발자는 원하는 항목만 다루면 된다.
  - `HttpServlet`의 `service` 함수를 override 받아서 사용한다.
  <img width="531" alt="image" src="https://user-images.githubusercontent.com/21374902/185284956-2d692cbf-7833-4795-97e0-25ed2d7b84c2.png">

- ### Version 1
  - 기존에는 각 도메인 별로 HttpServlet의 service를 구현했었는데 `Front Controller`와 `Controller`를 만들어서 HttpServlet은 Front Controller에서만 상속받고 들어온 request, response를 조건에 맞는 Controller로 전달해준다.
  - Front Controller가 HttpServlet의 serivce 함수를 구현하고 있고 여러 Controller의 `Mapping 정보`도 갖고 있어야 한다.
  - Front Controller는 1개의 Controller를 찾고 비지니스 로직을 담고 있는 `process` 함수를 호출한다.
  - Controller에선 `process` 함수를 구현하고 `RequestDispatcher`를 사용해서 Model을 담아서 View로 forwarding 해준다.
    ```java
    // Controller
    request.setAttribute("member", member);
    String viewPath = "/WEB-INF/views/save-result.jsp";
    RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
    dispatcher.forward(request, response);
    ```
    <img width="739" alt="image" src="https://user-images.githubusercontent.com/21374902/185026509-31e85ef2-5fba-4475-acec-ed8c03f1df69.png">

- ### Version 2
  - Controller에 불필요한 공통 코드들이 많기 때문에 제거하고 공통 View를 만들어서 처리한다.
  - 기존 Front Controller에선 하나의 Controller를 선택하고 로직 수행(`process`)만 하고 
  Controller에서 데이터 바인딩 후 View를 forwarding 했다면,
  - Version 2에선 공통 View가 view(jsp) 선택하고 forward(`render`) 하고 Front Controller는 `process` 이후에 `render`를 호출한다. Controller는 데이터 바인딩 후 view(jsp) 선택만 수행한다. 
    ```java
    // Front Controller
    process(request, response);

    👇👇👇👇👇👇👇👇👇👇👇👇👇👇👇👇👇👇👇👇👇👇
    
    CommonView view = controller.process(request, response);
    view.render(request, response;)
    ```
    ```java
    // Controller
    request.setAttribute("member", member);
    String viewPath = "/WEB-INF/views/save-result.jsp";
    RequestDispatcher dispatcher = request.getRequestDispatcher(viewPath);
    dispatcher.forward(request, response);
    
    👇👇👇👇👇👇👇👇👇👇👇👇👇👇👇👇👇👇👇👇👇👇
    
    return new CommonView("/WEB-INF/views/save-result.jsp");
    ```
    <img width="731" alt="image" src="https://user-images.githubusercontent.com/21374902/185026560-793006a8-fb2c-4fbe-ba6c-deada2084513.png">

- ### Version 3
  - `Servlet 종속성 제거`
    - Controller에서 직접 request, response 제어하는 부분을 제거한다.
    - Front Controller에서 HttpServletRequest가 제공하는 Parameter를 공통으로 정리해서 Controller에게 넘겨준다.
  - `View 선택 중복 제거`
    - Controller에서 jsp의 경로 전체를 지정해서 반환하는 부분을 제거한다.
    - Controller에서 view(jsp)의 전체 경로를 반환하지 않고 논리적인 view 이름만 반환하도록 수정한다.
    - ModelAndView를 추가해서 view(jsp)에 넘겨줘야하는 attribute와 view 이름을 갖고 있도록 한다.
    - Controller는 ModelAndView에 request attribute와 view 이름을 지정해준다.
    - Front Controller는 ModelAndView를 사용해서 view(jsp)에 request attribute를 설정해주고 지정된 view로 forward 한다.
    ```java
    // Controller
    return new CommonView("/WEB-INF/views/new-form.jsp");

    👇👇👇👇👇👇👇👇👇👇👇👇👇👇👇👇👇👇👇👇👇👇
    
    ModelAndView mv = new ModelAndView("save-result"); // view 이름
    mv.getModel().put("member", member);  // attribute 세팅
    return mv;
    ```
    ```java
    // Front Controller
    public CommonView process(HttpServletRequest request, HttpServletResponsresponse);

    👇👇👇👇👇👇👇👇👇👇👇👇👇👇👇👇👇👇👇👇👇👇

    public ModelAndView process(Map<String, String> parameters);
    
    // ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ

    ModelAndView mv = controller.process(parameters);
    CommonView view = this.viewResolver(mv.getViewName());
    view.render(mv.getModel(), request, response);

    // viewResolver 함수
    private MyView viewResolver(String viewName) {
        return new MyView("/WEB-INF/views/" + viewName + ".jsp");
    }
    ```
    <img width="733" alt="image" src="https://user-images.githubusercontent.com/21374902/185026603-584998d3-ca27-45ea-bd41-d5ec48415a0d.png">

- ### Version 4
  - Controller에서 ModelAndView와 CommonView를 매번 생성하고 반환하는 부분이 번거롭기 때문에 제거한다.
  - 
    ```java
    // Controller
    public ModelAndView process(Map<String, String> parameters){
      ModelAndView mv = new ModelAndView("save-result"); // view 이름
      mv.getModel().put("member", member);  // attribute 세팅
      return mv;
    }
    
    👇👇👇👇👇👇👇👇👇👇👇👇👇👇👇👇👇👇👇👇👇👇

    public String process(Map<String, String> parameters, Map<String, Object> model){
      model.put("member", member);
      return "save-result";
    }
    ```
    ```java
    // Front Controller
    ModelAndView mv = controller.process(parameters);
    CommonView view = this.viewResolver(mv.getViewName());
    view.render(mv.getModel(), request, response);

    👇👇👇👇👇👇👇👇👇👇👇👇👇👇👇👇👇👇👇👇👇👇
    
    String viewName = controller.process(paramMap, model);
    CommonView view = viewResolver(viewName);
    view.render(model, request, response);
    ```
    <img width="731" alt="image" src="https://user-images.githubusercontent.com/21374902/185026651-7be6d556-4eaf-4201-8ce9-c1d2b6bec828.png">

- ### Version 5
  - 여러 개의 Controller를 조금 더 능동적으로 사용하기 위해서 HandlerAdapter를 추가한다.
  - Controller가 여러개 있다고 해보자. Front Controller는 `handlerMappingMap`과 `handlerAdapters`를 갖고 있다.
  - `Map<String, Object> handlerMappingMap`는 URI와 Controller 객체를 갖고 있다.
  - `List<CommonHandlerAdapter> handlerAdapters`는 Adapter 객체를 갖고 있다.
  - Front Controller의 `service` 함수에서 아래와 같이 동작한다.
    - URI로 Controller를 찾는다 : `handlerMappingMap`
    - 찾은 Controller로 Adapter를 찾는다 : `handlerAdapters`
    - 찾은 Adapter의 `handle` 함수는 Controller의 `process` 함수를 실행한다.
    - process 함수에서 ModelAndView를 반환받아서 view를 `render` 한다.
    ```java
    // Front Controller
    handlerMappingMap.put("/v1/members/new-form", new MemberControllerV1());
    handlerMappingMap.put("/v2/members/new-form", new MemberControllerV2());
    handlerMappingMap.put("/v3/members/new-form", new MemberControllerV3());

    handlerAdapters.add(new ControllerV1HandlerAdapter());
    handlerAdapters.add(new ControllerV2HandlerAdapter());
    handlerAdapters.add(new ControllerV3HandlerAdapter());

    //ㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡㅡ

    @Override
    protected void service(HttpServletRequest request, HttpServletResponseresponse) {
      Object handler = handlerMappingMap.get(request.getRequestURI);
      CommonHandlerAdapter adapter = {
        for(CommonHandlerAdapter adapter : handlerAdapters) {
          if(adapter.support(handler))
            return adapter;
        }
        throw new IllegalArgumentException("handler adapter를 찾을 수 없습니다. handler=" + handler);
      };

      ModelAndView mv = adapter.handle(request, response, handler);
      CommonView view = this.viewResolver(mv.getViewName());
      view.render(mv.getModel(), request, response);
    }
    ```
    ```java
    // CommonHandlerAdapter (V1)
    public boolean supports(Object handler) {
      return (handler instanceof MemberControllerV1);
    }

    public ModelAndView handle(HttpServletRequest request, HttpServletResponseresponse, Object handler) {
      MemberControllerV1 controller = (MemberControllerV1) handler;
      ...
      String viewName = controller.process(paramMap, model);
      ModelAndView mv = new ModelAndView(viewName);
      mv.setModel(model);
      return mv;
    }
    ```
    <img width="735" alt="image" src="https://user-images.githubusercontent.com/21374902/185026703-70fd24d0-e9fb-45f1-bab7-d87734a265d3.png">
  
  - ### 부록
    - Annotation Pattern을 사용해서 Spring이 시작될 때, handlerMappingMap, handlerAdapters을 만들어주면 자동으로 등록이 된다.
    
- Reference
  - [스프링 MVC 1편 / 김영한 / 인프런](https://www.inflearn.com/course/%EC%8A%A4%ED%94%84%EB%A7%81-mvc-1)

---
Spring Cloud
===
- ### Spring Cloud Gateway
  - Spring Cloud Gateway는 tomcat이 아니라 netty를 사용한다.
    ```
    netty 란 ? 
    비동기 + 논블로킹 + 이벤트 기반 + 추상화로 되어 있어서 대규모 트래픽을 좀 더 효율적으로 처리할 수 있다.
    netty는 이벤트를 Inboud / Outbound 로 구분해서 추상화하여 사용한다.
    자세한 내용은 👉 https://github.com/justdoanything/self-study/blob/main/03%20ApplicationModernization.md#netty와-nio
    ```

---

Spring Boot
===
Spring Boot는 경량화한 Spring 이라고 볼 수 있다. 개발자가 해야할 일을 줄여주고 빠르게 어플리케이션을 만들고 사용할 수 있게 다양한 기능들을 제공한다. Modern Application에서 RESTful API를 만들 때 사용하기 좋다.

먼저, Spring은 개발자가 기본적으로 해야할 작업들이 엄청 많았다. 직접 설정 파일을 작성하고 스프링 컨테이너를 구성하고 필요한 빈 객체를 등록 후 의존성을 설정해야 했다. 예를들어 특정 dependency를 하나 추가해도 그 기능을 사용하기 위해서 코드 내부에 빈 객체를 생성하고 설정해줘야 하는 부분들이 많았다. 만약 아주 세세한 부분까지 설정하고 관리하고 싶다면 Spring을 사용하는 것이 바람직하고 볼 수 있다.

반면에, Spring Boot는 기본적으로 많은 기능들을 제공한다. 
  - `Embedded Tomcat` 제공 : Web Server를 설치하고 설정할 필요 없이 Embedded Tomcat이 내장되어 있기 때문에 별도의 설치와 설정 없이 바로 사용할 수 있다.
  - `빠른 배포` : Embedded Tomcat을 기반으로 실행 가능한 JAR를 만들고 바로 실행할 수 있다.
  - `spring-boot-starter-*` 통한 dependency 자동화 : spring-boot-starter-* 추가하면 필요한 라이브러리를 자동으로 추가하고 기본적인 설정들을 다 해주기 때문에 서버를 바로 구동하고 API를 만들어서 사용할 수 있다. dependency의 버전을 변경했을 때 연관된 dependency를 다 찾아서 버전을 바꿔야 하는 불편함도 덜 수 있다. 
  - `Spring Actuator` : 어플리케이션 모니터링과 관리할 수 있는 기능을 제공한다.
  - `AutoConfiguration` : @SpringBootApplication와 같은 어노테이션을 통해 기본적인 설정과 빈 등록 및 관리를 편리하게 할 수 있도록 한다.
    ```java
    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @Documented
    @Inherited
    @SpringBootConfiguration
    @EnableAutoConfiguration
    @ComponentScan(excludeFilters = { @Filter(type = FilterType.CUSTOM, classes = TypeExcludeFilter.class),
    @Filter(type = FilterType.CUSTOM, classes = AutoConfigurationExcludeFilter.class) })
    public @interface SpringBootApplication { ... }
    ```
    - `@ComponentScan` : @Component, @Controller, @Repository, @Service 등 특정 어노테이션이 붙어있는 객체들을 스캔해 Bean으로 등록해준다.
    - `@EnableAutoConfiguration` : @ComponentScan 이후 사전에 정의한 라이브러리들을 Bean으로 등록해줍니다.

따라서 개발자가 직접 설정 파일을 작성할 필요가 없이 프로젝트 설정과 라이브러리 의존성을 의존성을 자동으로 처리해주는 기능을 제공한다.

---

Spring Batch
===

- ### Listener

<img width="844" alt="image" src="https://user-images.githubusercontent.com/21374902/187106588-6d6b9f4e-eee8-4c11-aa80-35049ac53b63.png">

종류 | 실행 시점
---|---
JobExecutionListener | Job 실행 전/후
StepExecutionListener | Step 실행 전/후
ChunkListener | Chunk 실행 전후(Tasklet 실행 전후), 오류 시점
ItemReaderListener | ItemReader 실행 전후, 오류 시점, (단, item이 null일 경우에는 호출 X)
ItemProcessorListener | ItemProcessor 실행 전후, 오류 시점, (단, item이 null일 경우에는 호출 X)
ItemWriterListener | ItemWriter 실행 전후, 오류 시점, (단, item이 null일 경우에는 호출 X)
SkipListener | item 처리가 Skip 될 경우 Skip된 item을 추적
RetryListener | Retry 시작, 종료, 에러 시점

  - 사용 방법
    - 별도의 Listener 클래스를 선언해서 StepBuilderFactory에 주입해서 사용한다.
    - 별도로 정의한 Listener 클래스에 위의 표를 참고해서 필요한 Listener 클래스를 구현하는 방법
    - @BeforeChunk, @AfterRead, @OnWriteError 처럼 Annotation을 사용하는 방법
    - Annotation을 사용할 땐 Return Type/Parameter Type을 확인하고 사용해야 한다.
  - 종류
    ```java
    /* Job */
    @BeforeJob public void beforeJob(JobExecution jobExecution);
    @AfterJob  public void afterJob(JobExecution jobExecution);
    
    /* Step */
    @BeforeStep public void beforeStep(StepExecution stepExecution);
    @AfterStep public ExitStatus afterStep(StepExecution stepExecution);

    /* Chunk */
    @BeforeChunk public void beforeChunk(ChunkContext context);
    @AfterChunk public void afterChunk(ChunkContext context);
    @AfterChunkError public void afterChunkError(ChunkContext context);

    /* Read */
    @BeforeRead public void beforeRead();
    @AfterRead public void afterRead(Object obj);
    @OnReadError public void onReadError(Exception ex);

    /* Process */
    @BeforeProcess public void beforeProcess(Object obj);
    @AfterProcess public void afterProcess(Object obj1, Object2 obj2);
    @OnProcessError public void onProcessError(Object obj, Exception e);

    /* Write */
    @BeforeWrite public void beforeWrite(List<? extends Object> objs);
    @AfterWrite public void afterWrite(List<? extends Object> objs);
    @OnWriteError public void onWriteError(Exception exception, List<? extends Object> objs);
    ```
    
---

Spring JPA
===
- [Spring JPA 이론](https://github.com/justdoanything/self-study/blob/main/12%20JPA.md)
- [Spring JPA Project](https://github.com/justdoanything/self-study/tree/main/Spring%20Data%20JPA)

---

Spring Testing
===
- ## Unit Test Best Practices: JUnit Reference Guide
  - #### 단위 테스트는 버그를 찾기 위함이 아니다.(Unit Testing is not about finding bugs.)
    - 단위 테스트는 버그를 찾기에 적합하지 않다. 모든 단위들은 완벽하게 하나의 유기체로 동작해야 하지만 독립적으로 분리되어 있는 컴포넌트가 서로 호환되는 것까지 검증하지는 않는다.
    - 단위 테스트는 `TDD`에서 말하는 것처럼 시스템 디자인 단계의 일부분으로 보아야 한다.
  - #### 하나의 테스트 케이스는 하나의 시나리오만 테스트해야 한다. (Tips for writing great unit tests.)
    - 하나의 단위 테스트는 복수의 테스트 시나리오를 가지면 안된다.
    - 이는 리팩토링시 코드의 의도된 기능을 망가뜨렸는지 확인할 수 있어서 효과적이다.
  - #### 불필요한 검증 구문은 작성하지 않아야 한다. (Do not make unnecessary assertions.)
    - 단위 테스트는 시스템의 특정 단위가 어떻게 동작하는지에 대한 디자인 스펙이지 코드에 대한 검증이 아니다.
    - 테스트 하려고 하는 하나의 시나리오에만 집중해서 코드를 작성해야 한다. 그렇지 않으면 하나의 이유로 여러 테스트 케이스가 실패할 수 있다.
  - #### 각 테스트 케이스는 독립적이어야 한다. (Make each test independent to all the others.)
    - 다른 테스트 케이스에 의존적인 테스트 케이스를 만들지 않아야 한다.
    - 다른 테스트와 선결 조건을 설정하려면 @Before, @After 같은 annotation을 사용해라.
  - #### 테스트에 필요한 모든 서비스와 상태들은 모두 `Stub`으로 제공되어야 한다. (Mock out all external services and state.)
    - 테스트 케이스가 공통된 외부 조건을 사용하는 경우 결과가 서로에게 영향을 미친다.
    - 테스트 실행 순서와 네트워크 설정 등이 테스트 결과에 영향을 미칠 수 있다는 의미이다.
  - #### 시스템 설정 파일에 대한 단위 테스트는 작성하지 않는다. (Do not unit-test configuration settings.)
    - 시스템 설정 값에 대한 단위 테스트가 필요하다면 설정값을 읽는 모듈에 대해서만 수행한다.
  - #### 단위 테스트 케이스의 이름은 명확하고 일관되어야 한다. (Name your unit tests clearly and consistently.)
    - 좋은 예
      - TestCreateEmployee_NullId_ShouldThrowException
      - TestCreateEmployee_NegativeId_ShouldThrowException
      - TestCreateEmployee_DuplicateId_ShouldThrowException
      - TestCreateEmployee_ValidId_ShouldPass
  - #### 외부 시스템, 서비스에 대한 의존성이 낮은 서비스들부터 테스트를 작성하고 확장해 나가야 한다. (Write tests for methods that have the fewest dependencies first, and work your way up.)
    - Employee 모듈을 테스트한다고 했을 때 가장 낮은 외부 의존성을 갖는 'Employee 생성' 테스트 코드부터 작성하는 것이 유리하다.
  - #### private 함수를 포함한 모든 함수들은 가시 범위에 상관없이 단위 케이스를 작성해야 한다. (All methods, regardless of visibility, should have appropriate unit tests.)
    - 논란거리가 될 수 있지만 중요한 로직을 갖고 있다면 모두 단위 케이스를 작성해야 한다고 생각한다.
  - #### 단위 테스트 함수는 정확히 하나의 검증구문만 가져야 한다. (Aim for each unit test method to perform exactly one assertion.)
  - #### 예외 케이스를 처리하는 단위 테스트 코드를 작성해야 한다. (Create unit tests that target exceptions.)
    - try/catch 문을 사용하지 않는다.
    - `@Test(expected=SomeDomainSpecificException.SubException.class)` 를 사용할 수 있다.
  - #### 테스트를 위한 코드는 Product 코드에서 분리되어 있어야 한다. (Ensure that test code is separated from production code.)
  - #### 단위 테스트 내에선 아무것도 출력하지 않아야 한다. (Do not print anything out in unit tests.)
  - #### 정적 변수를 단위 테스트에 사용하지 않아야 한다. (Do not use static members in a test class. If you have used then re-initialize for each test case.)
    - 독립성이 중요하기 때문에 static 변수는 사용하지 않으며 필요하다면 각 테스트 케이스 시작 전에 초기화 해야한다.
  - #### Exception 처리에 catch 구문을 작성하지 않아야 한다. (Do not write your own catch blocks that exist only to fail a test.)
  - #### 단위 테스트를 자동으로 실행할 수 있는 스크립트를 작성해야 한다. (Integrate Testcases with build script.)
  - #### 단위 테스트를 생략하지 않아야 한다. (@Ignore 사용 금지) (Do not skip unit test.)
    - `@Ignore` 를 사용해서 임의로 생략하는 것은 하지 말아야 한다.
  - #### 테스트 결과를 XML 형태로 출력해야 한다. (Capture results using the XML formatter.)
  - ### Reference
    - https://dzone.com/articles/unit-testing-best-practices
    - http://jinson.tistory.com/entry/번역-Unit-Testing-Best-Practices-JUnit-Reference-Guide-단위-테스트-활용-방법-JUnit-참조-가이드
- ## JUnit의 Mockito
  - ### Version
    - Spring Boot Version : 2.6.6
    - Spring Test Version : 5.3.18
    - JUnit Version : 5.8.2
    - Mockito Version : 4.0.0
  - ### build.gradle
    ```gradle
    dependencies {
      ...
      testImplementation 'org.springframework.boot:spring-boot-starter-test'
      testImplementation 'org.mockito:mockito-inline'
    }
    ```
  - ### Controller Test
  - ### Service Test
    - `@ExtendWith(MockitoExtension.class)` : Mockito를 사용하기 위한 annotation. Class Level에 붙여준다.
    - `@InjectMocks` : 테스트 할 대상 서비스 클래스에 붙여준다.
    - `@Mock` : 대상 서비스에서 참조하고 있는 다른 Bean을 Mocking 하기 위해 사용한다.
    - `@Test` : 테스트할 함수 단위로 붙여준다.
    - `@DisplayName` : 테스트 함수에 대한 설명을 적는다.

    ```java
    @ExtendWith(MockitoExtension.class)
    class SpringBootMockitoTest {
        @InjectMocks
        private SpringServiceImpl service;
    
        @Mock
        private SpringRepository repository;
    
        @Mock
        private FileService fileService;
    
        @Mock
        private CommonService commonService;
    
        @Test
        @DisplayName("테스트 케이스에 대한 설명을 적습니다.")
        void doTest_ReturnTrue() {
          ...
        }
    }
    ```
    - #### Session Mocking
      -  Service에서 Session을 사용할 때 Session을 Mocking 해주기 위해서 공통 코드로 관리할 수 있다.
      -  Session 관련 코드는 [Modern Spring Project](https://github.com/justdoanything/self-study/tree/main/Modern/SpringBoot/MODERN) 참고
      
      ```java
      @ExtendWith(MockitoExtension.class)
      class SpringBootMockitoTest {
        private static MockedStatic<SessionUtil> sessionMock;
      
        @BeforeAll
        public static void beforeAll() {
          sessionMock = mockStatic(SessionUtil.class);
          SessionVO session = SessionVO.builder().sessionId(1).memberId(1).build();
          sessionMock.when(SessionUtil::getSessionContext).thenReturn(session);
        }
      
        @AfterAll
        public static void afterAll() {
          sessionMock.close();
        }
      }
      ```
    - #### Test 코드 사전작업
      - 테스트 코드는 기본적으로 `given` → `when` → `then` 구조를 가진다.
      - 개인적인 방법으로 테스트 코드를 짤 때 역순으로 생각하면서 코드를 작성한다.\
        테스트 케이스를 정의하고 → `then`\
        해당 테스트에 필요한 조건(함수 호출)과 결과를 정의하고 → `when`\
        호출하는 함수에서 필요로 하는 값(파라미터)를 정의한다. → `then`
      - 만약에 함수 중간에서 Exception 및 Fail case를 짤 때 `when` 에서 Exception/Fail 이후에 호출되는 함수를 작성하면 Junit이 `Following stubbings are unnecessary`을 발생시킨다.\
        불필요한 검증 구문을 작성하지 말아야하기 때문이다. ([Unit Test Best Practice 참고](#unit-test-best-practices-junit-reference-guide))
      - static 변수는 단위 케이스마다 초기화나 재지정을 해야하며 공통 처리하는 부분이 있으면 `@After`, `@Before` 를 사용한다.
    - #### Test 코드 작성하기
      - `given` : 특정 값이 필요한 객체는 `given` 단계에서 직접 선언 후 사용하며 그 외엔 `any`, `Collections`로 빈 값을 사용한다.
      - `when` : `when` ~ `then` 문법보단 `doReturn` ~ `when` 사용을 권장한다.
        - Service에서 사용되는 파라미터의 형식을 잘 지정해서 넣어줘야 하며 불필요한 검증 구문을 작성하면 에러가 반환된다.
      - `then` : 실제 반환되는 값을 검증하는 부분으로 `assert` 구문을 사용한다.
        - `assertDoesNotThrow`, `assertEquals`, `assertSame`, `assertInstanceOf` 등을 사용한다.
        - Exception의 경우 상세값까지 비교하면 좋다.
          ```java
          BusinessException businessException =
                assertThrows(BusinessException.class, () -> service.createEmployee(employeeNo));
          assertEquals(businessException.getMessage(), "Employee No is duplicated.");
          assertEquals(businessException.getStatusCode(), ExceptionStatusCode.FAIL);
          ```
        - 여러 값을 한번에 비교하고 싶을 땐 `assertAll`을 사용한다.
          ```java
          assertAll( 
            () -> assertDoesNotThrow(..)
            , () -> assertEquals(..) 
          )
          ```

SpringBoot의 비동기 처리 방식
===
- `CompletableFuture`
  - Java 8부터 제공되는 기능으로 SpringBoot에서 제공하는 기능은 아니다.
  - `supplyAsync()`, `runAsync()`를 사용하여 비동기 작업을 생성하고 `thenApply()`, `thenAccept()`를 사용하여 처리 결과를 핸들링할 수 있습니다.

    ```java
    @Service
    public class MyService {
      public CompletableFuture<String> asyncMethod() {
        return CompletableFuture.supplyAsync(() -> {
            // 비동기 처리 작업 수행
            // ...
            return "비동기 작업 결과";
        });
      }
    }
    ```

- `@Async 어노테이션`
  - SpringBoot에서 제공하는 기능으로 세모드를 비동기적으로 실행할 수 있다.
  - 메소드 실행 시 새로운 스레드를 생성하여 비동기적으로 실행한다.
  - 메소는 반드시 public 이어야 한다.

    ```java
    @Service
    public class MyService {
      @Async
      public CompletableFuture<String> asyncMethod() {
        // 비동기 처리 작업 수행
        // ...
        return CompletableFuture.completedFuture("비동기 작업 결과");
      }
    }
    ```

- `WebFlux`
  - Spring 5부터는 WebFlux라는 새로운 웹 프레임워크를 제공한다.
  - WebFlux는 기존의 Spring MVC와 달리 Reactive Programming을 지원하고 Reactive Programming은 비동기적으로 데이터 스트림을 처리하는 방식을 지원한다.

    ```java
    @Service
    public class MyService {
      public Mono<String> asyncMethod() {
        return Mono.fromCallable(() -> {
          // 비동기 처리 작업 수행
          // ...
          return "비동기 작업 결과";
        });
      }
    }
    ```

- `Callable`과 `DeferredResult`
  - `Callable`은 일반적인 자바에서 제공하는 Callable 인터페이스와 동일하며 비동기 처리 결과를 반환하는데 사용된다.
  - `DeferredResult`는 비동기 처리 결과를 담아두는 있다가 나중에 반환할 수 있도록 한다. `DeferredResult` 객체를 반환하면, 요청 스레드는 즉시 반환되고, 비동기 처리는 백그라운드에서 수행된다.
  - 비동기 처리를 위해 스레드 풀을 사용하려면, `taskExecutor`라는 스프링 프레임워크에서 제공하는 TaskExecutor 인터페이스를 사용해야 한다.
  - `ListenableFuture` 또는 `CompletableFuture`는 더욱 강력한 기능을 제공하며, 비동기 처리를 보다 효율적으로 수행할 수 있다.
  - `Callable`은 요청 스레드에서 작업을 처리하고 결과를 반환하는 반면, `DeferredResult`는 비동기적으로 작업을 처리하고 결과를 반환하는 방식이다. 따라서 `DeferredResult`는 스레드 풀을 사용하여 더 많은 요청을 처리할 수 있다.

    ```java
    @RestController
    public class AsyncController {
      @Autowired
      private TaskExecutor taskExecutor;
    
      /**
      * 반환되는 Callable 객체는 call() 메소드가 호출되면 비동기 처리가 시작되며 Callable 객체는 실행이 완료될 때까지 block 된다.
      * 실행이 종료되면 call() 메소드가 결과를 반환한다.
      */
      @RequestMapping("/callable")
      public Callable<String> callable() {
        return () -> {
          Thread.sleep(5000);
          return "Callable Result";
        };
      }
      /**
      * 반환되는 DeferredResult 객체는 비동기 처리 결과를 나중에 반환할 수 있도록 해준다.
      * DeferredResult 객체를 반환하면 클라이언트 요청이 즉시 반환되고 비동기 처리는 백그라운드에서 수행된다.
      * 비동기 처리가 완료되면 DeferredResult 객체의 setResult() 메소드를 호출하여 결과를 반환하고 클라이언트로 전송된다.
      */
      @RequestMapping("/deferred")
      public DeferredResult<String> deferred() {
        DeferredResult<String> result = new DeferredResult<>();
        taskExecutor.execute(() -> {
          try {
            Thread.sleep(5000);
            result.setResult("DeferredResult Result");
          } catch (InterruptedException e) {
            result.setErrorResult(e.getMessage());
          }
          });
          return result;
      }
    }
    ```
    