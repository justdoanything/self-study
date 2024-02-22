<!-- TOC -->
* [Intro](#intro)
* [AOP (Aspect Oriented Programming)](#aop--aspect-oriented-programming-)
  * [AOP란?](#aop란)
  * [핵심 관심사](#핵심-관심사)
  * [횡단 관심사](#횡단-관심사)
  * [Weaving](#weaving)
  * [AOP 요약](#aop-요약)
  * [Spring AOP 활용](#spring-aop-활용)
* [Service Interface는 왜 만들어야 할까?](#service-interface는-왜-만들어야-할까)
  * [Service Interface](#service-interface)
  * [JDK Dynamic Proxy와 CGLIB Proxy](#jdk-dynamic-proxy와-cglib-proxy)
  * [IoC 컨테이너와 Spring AOP Proxy](#ioc-컨테이너와-spring-aop-proxy)
    * [1. JDK Dynamic Proxy](#1-jdk-dynamic-proxy)
      * [(1) 이름의 의미](#-1--이름의-의미)
      * [(2) Proxy 객체 생성 과정](#-2--proxy-객체-생성-과정)
      * [(3) 인터페이스를 사용해야 하는 이유](#-3--인터페이스를-사용해야-하는-이유)
    * [2. CGLIB(Code Generator Library)](#2-cglib--code-generator-library-)
      * [(1) Proxy 객체 생성 방식](#-1--proxy-객체-생성-방식)
      * [(2) CGLIB의 장단점](#-2--cglib의-장단점)
      * [(3) 초기 단계의 한계점과 보완된 점](#-3--초기-단계의-한계점과-보완된-점)
* [VO 활용](#vo-활용)
  * [VO와 DTO](#vo와-dto)
  * [VO 종류](#vo-종류)
    * [1. RequestVO/ResponseVO](#1-requestvoresponsevo)
    * [2. 테이블VO](#2-테이블vo)
    * [3. 일반VO](#3-일반vo)
    * [4. 정리](#4-정리)
* [Exception 공통 처리](#exception-공통-처리)
  * [@ExceptionHandler](#exceptionhandler)
  * [@Controller/@ControllerAdvice와 @RestController/@RestControllerAdvice](#controllercontrolleradvice와-restcontrollerrestcontrolleradvice)
  * [@ControllerAdvice](#controlleradvice)
  * [ResponseStatusException](#responsestatusexception)
  * [ResponseUtility](#responseutility)
* [Request에 enum 클래스 처리하기](#request에-enum-클래스-처리하기)
  * [@Enum과 EnumValidator](#enum과-enumvalidator)
  * [Converter](#converter)
  * [Code 값을 갖는 Enum 형태는?](#code-값을-갖는-enum-형태는)
  * [ConverterFactory](#converterfactory)
  * [Converter 한 번에 등록하기](#converter-한-번에-등록하기)
  * [Jackson의 Deserializer](#jackson의-deserializer)
* [Jackson](#jackson)
  * [Serializer/Deserializer](#serializerdeserializer)
  * [HandlerMethodArgumentResolver](#handlermethodargumentresolver)
  * [ObjectMapper](#objectmapper)
    * [JSON 요청을 Java 객체로 역직렬화](#json-요청을-java-객체로-역직렬화)
    * [Java 객체를 JSON 응답으로 직렬화](#java-객체를-json-응답으로-직렬화)
* [Aspect](#aspect-1)
* [자주 쓰이는 Controller Annotation](#자주-쓰이는-controller-annotation)
* [자주 쓰이는 Service Annotation](#자주-쓰이는-service-annotation)
* [자주 쓰이는 Model Annotation](#자주-쓰이는-model-annotation)
* [@Transactional](#transactional-1)
* [Redis](#redis)
* [Flyway](#flyway)
* [Reference](#reference)
<!-- TOC -->

---

Intro
===
Spring은 Library가 아닌 Framework 이기 때문에 Spring이 구동되면서 우리가 짜놓은 코드를 사용한다.
Spring은 jackson 등 기본적으로 많은 기능들을 기본적으로 제공하여 생산성을 높여주며 특정한 상황에서 개발자가 원하는 기능을 추가할 수 있도록 확장성을 제공한다.

Spring AOP를 시작으로 Modern Application에서 Spring은 어떻게 사용되고 있으며, Spring이 기본적으로 제공하는 기능을 개발자가 어떻게 활용해서 특정 기능을 추가할 수 있을지 알아본다. 
또한, 실제 프로젝트에서 사용했던 Spring AOP 방법과 개발하면서 고민하고 사용했던 내용들을 정리한다.

[실제 코드와 테스트 코드](https://github.com/justdoanything/self-study/tree/main/Modern/SpringBoot/MODERN)를 작성하면서 아래 내용을 작성한다.

부록 : [Spring과 SpringBoot의 차이점](https://github.com/justdoanything/self-study/blob/main/10%20Spring.md#spring-boot)

---

AOP (Aspect Oriented Programming)
===
절차적 프로그래밍 → 객체 지향 프로그래밍(OOP) → 관점 지향 프로그래밍(AOP) 으로 변해가고 있다.

AOP의 매커니즘은 프로그램을 `관심사` 기준으로 크게 `핵심 관심사(Core Concerns)`와 `횡단 관심사(Cross-cutting Concerns`로 분류 한다. 프로그램의 핵심 가치와 목적이 그대로 드러나있는 관심 영역을 `핵심 관심사`라고 하고 일반적으로 비즈니스 기능과 관계없는 부가적으로 발생하는 중복된 코드를 `횡단 관심사`라고 한다. 대표적인 `횡단 관심사`는 Security, Logging, Transaction Management 등이 있다.

AOP는 분리된 횡단 관심사를 `Aspect` 라는 모듈 형태로 만들어서 설계하고 관리한다. `Aspect` 모듈에는 부가 기능(횡단 관심사)을 내포하고 있으며 자체적으로 부가 기능을 여러 객체의 핵심기능에 교차로 적용을 시켜주기 때문에 추상화를 통해 분리하는 작업도 필요가 없어짐으로 횡단 관심사 모듈을 효율적으로 관리할 수 있게 된다. 무엇보다 Aspect 모듈의 가장 큰 장점은 핵심기능에 부가 기능의 코드가 남아 있지 않아도 된다는 점이다. 이러한 이유엔 대부분의 AOP 프레임워크들이 Interceptors를 통해 핵심기능에 부가 기능을 결합하는 방식을 사용하기 때문이다.

## AOP란?

| 분류      | 용어                            | 의미                                               |
|---------|-------------------------------|--------------------------------------------------|
| 핵심 관심사  | Target Object                 | Advice가 적용될 핵심 객체                                |
| 핵심 관심사  | Joinpoint                     | 프로그램 실행될 때 발생하는 여러 위치<br/>메소드 실행, Exception 처리 등 |
| 횡단 관심사  | Aspect                        | 횡단 관심사의 모듈화                                      |
| 횡단 관심사  | Advice                        | 특정 JoinPoint에 적용할 횡단 관심 코드                       |
| 횡단 관심사  | Pointcut                      | 여러 JoinPoint 중에 Advice를 적용할 특정 위치                |
| 횡단 관심사  | Introduction<br/>(inter-type) | Aspect 모듈 내부에서 선언한 클래스, 인터페이스, 변수, 메소드           |
| 횡단 관심사  | AOP Proxy                     | Aspect를 대신 수행하기 위해 AOP 프레임워크에 의해 생성된 객체          |
| 횡단 관심사  | Weaving                       | 핵심 객체에 Advice하여 횡단 관심 코드가 포함한 객체가 생성되는 일련의 과정    |

## 핵심 관심사
- ### `TargetObject`
  - 횡단 기능(Advice)에 적용될 객체를 뜻한다.
  - 핵심 모듈이라고 할 수 있고 Spring AOP에선 Advice 받는 객체라고 해서 Adviced Object라고 한다.
  - Spring AOP에선 실제 적용할 객체 대신 Runtime Proxy를 사용해서 구현하기 때문에 TargetObject는 항상 Proxy Object 이다.
- ### `JoinPoint`
  - TargetObject 안에서 횡단 기능(Advice)이 적용될 수 있는 여러 위치를 뜻한다.
  - 프로그램이 실행될 때 예외가 발생한다거나 객체가 생성된다거나 특정 메소드가 호출되는 시점 등 프로그램 실행 중에 있는 모든 시점은 횡단 기능이 적용될 수 있는 위치이다.
  - Spring AOP에는 기본적으로 메소드 Interceptor를 기반으로 하고 있어서 JoinPoint는 항상 메소드 단위이다.

## 횡단 관심사
- ### `Aspect`
  - 횡단 관심사를 모듈화하는 것을 Aspect라고 하고 Java에선 표준으로 가장 많이 쓰이는 AspectJ 확장 기능을 통해 Aspect를 구현할 수 있다.
  - Spring AOP에서 제공하는 @Aspect 어노테이션을 통한 구현 방식은 AspectJ 보다 쉬운 설정이 가능하며 기본적으로 클래스로 구현하기 때문에 쉽게 접근할 수 있다.
  - Spring AOP는 2가지 방식을 통해 Aspect를 구현할 수 있다.
    - XML (스키마 기반 접근)
    - @Aspect(어노테이션 기반 접근)
  - Aspect 모듈은 핵심 모듈에 횡단 코드를 적용하기 위한 최종 목적을 갖고 있고 횡단 모듈의 관리 유용성을 증가시키기 위한 횡단 관심사의 집합체다.
  - 횡단 코드의 어떤 동작을/어디서/언제 적용할지 구현해야한다.
  - Aspect = Advice + Pointcut + Introduction(inter-type)
- ### `Advice`
  - JoinPoint에 적용할 횡단 코드이다.
  - Spring AOP는 Interceptor로 Advice를 모델링하고 JoinPoint 주변의 Interceptor의 결합된 상태의 체인을 유지하고 실제 런타임 시 결합된 코드가 실행된다.
  - JoinPoint는 항상 메소드 실행을 바라보기 때문에 Type의 매개 변수를 선언하여 JoinPoint 정보를 Advice에서 사용할 수 있다.
  - Advice는 JoinPoint와 횡단 코드의 각기 다른 결합점을 제어할 수 있도록 다양한 Advice를 제공하고 있다.
  - `@Before` : JoinPoint 이전에 실행한다. 단 Exception을 throw 하지 않는 한 실행 흐름이 JoinPoint로 진행되는 것을 방지하는 기능은 없다.
  - `@AfterReturning` : JoinPoint가 정상적으로 완료된 후 실행한다. 예를 들어 메소드가 Exception을 발생시키지 않고 리턴하는 경우 실행된다.
  - `@AfterThrowing` : Exception을 throw하여 메소드가 종료된 경우 실행된다.
  - `@After(finally)` : JoinPoint의 상태(Exception, 정상)와 무관하고 JoinPoint가 실행된 후 무조건 실행된다.
  - `@Around` : Before와 After가 합쳐진 Advice. 메소드 호출 전과 후에 실행 또한 JoinPoint로 진행할지 또는 자체 반환 값을 반환하거나 Exception를 throw하여 특정 메소드를 호출할 수 있다.

    <br/>
    <img width="430" height="400" alt="image" src="https://github.com/justdoanything/self-study/assets/21374902/f4b0ca2c-7864-4c32-aff1-19bdd7129434"><br/><br/>

- ### `Pointcut`
  - 여러 개의 JoinPoint 중 실제적으로 Advice할 JoinPoint 이다.
  - Advice는 여러 JoinPoint 중에서 Pointcut의 표현식에 명시된 JoinPoint에서 실행된다. 예를들어 여러 실행 포인트 중에서 특정 이름의 메소드에서 Advice를 하거나 제외해서 실행시킬 수 있다.
  - Pointcut 표현식과 일치하는 JoinPoint를 실행한다는 개념은 AOP의 핵심 개념이다.
  - Spring AOP에선 Pointcut과 Advice를 합쳐 Advisor라고 불리며 AspectJ Pointcut 언어를 사용한다.

    <br/>
    <img width="500" height="150" alt="image" src="https://github.com/justdoanything/self-study/assets/21374902/6fac0a7f-c67d-4b93-935a-ef7e259d61f9"><br/>
    <img width="500" height="150" alt="image" src="https://github.com/justdoanything/self-study/assets/21374902/53b89ff2-45ee-4f00-8f0a-5df4d5f927ea"><br/>
    <img width="500" height="250" alt="image" src="https://github.com/justdoanything/self-study/assets/21374902/30a446d7-55f7-4553-b401-164f18e3fa5a"><br/><br/>

- ### `Introduction(inter-type)`
  - Aspect 모듈 내부에 선언된 클래스 또는 인터페이스, 메소드와 그 외 모든 필드를 뜻한다.
  - 주된 목적은 기존 클래스에 새로운 인터페이스 및 해당 구현 객체를 추가히기 위함이다.
  - OOP에서 말하는 상속이나 확장과는 다른 방식으로 Advice 또는 Aspect를 이용해서 기존 클래스에 없는 인터페이스를 동적으로 추가할 수 있다.
  - 특히 Spring AOP를 사용하면 Proxy 된 객체에 새로운 인터페이스를 도입할 수 있다. Bean이 인터페이스를 구현하도록 쉽게 캐싱할 수 있다.
- ### `AOP Proxy`
  - Aspect를 대신 수행하기 위해 AOP 프레임워크에 의해 생성된 객체이다.
  - 일반적으로 Spring을 포함한 많은 AOP 프레임워크에선 핵심 관심 코드에 직접적인 Aspect를 하지 않고 Proxy Object를 활용해서 Aspect를 한다.
  - 횡단 관심 객체와 핵심 관심 객체의 느슨한 결합 구조를 만들고 필요 여부에 따라 부가 기능을 탈부착하기 용이하게 해준다.
  - 직접적인 참조가 아닌 Proxy를 사용해서 동적으로 참조하고 부가 기능의 탈부탁이 용이하다.
  - Spring AOP에선 Proxy를 사용하여 동적으로 Advice하기 위해 Java에서 제공해주는 `java.lang.reflect.Proxy`를 사용해서 Proxy 객체를 동적으로 생성해준다.
  - 구체적으로 JDK Dynamic Proxy와 CGLIB Proxy의 방식이 존재한다.

    <br/>
    <img width="500" height="200" alt="image" src="https://github.com/justdoanything/self-study/assets/21374902/8a8799fb-4cd1-4bb3-8750-e87b6581ae9d"><br/>
    <img width="500" height="250" alt="image" src="https://github.com/justdoanything/self-study/assets/21374902/991f4711-7618-4d75-9c77-2871a58bf686"><br/>
    <br/><br/>

## Weaving
- AOP의 특정 JoinPoint에 Advice하여 핵심 기능과 횡단 기능이 교차하여 새롭게 생성된 객체를 프로세스에 적용하는 일련의 모든 과정을 Weaving 이라고 한다.
- 수행 시점에 따라 CTW, LTW, RTW로 분류한다.
  - CTW : Compile-Time Weaving (AsepctJ Compiler)
  - LTW : Load-Time Weaving (AspectJ Compiler)
  - RTW : Run-Time Weaving (Spring AOP)
- AspectJ와 Spring AOP는 독립적인 대상으로 Weaving에서도 차이가 있다.
- AspectJ는 바이트 코드 기반으로 기존 클래스 코드를 조작해서 AspectJ Compiler에 의해 Aspect를 Waeving 하는 방식을 취하고 있다. 따라서 CTW, LTW 방식을 기본적으로 사용한다.
- Spring AOP는 Dynamic Proxy 기반으로 기본적으로 RTW를 사용하고 Waeving 방식이 AspectJ 보다 가볍다.

  <br />
  <img width="700" height="250" alt="image" src="https://github.com/justdoanything/self-study/assets/21374902/b129acbb-d136-44c6-a936-9c10644ebfcc"><br/>
  <img width="700" height="300" alt="image" src="https://github.com/justdoanything/self-study/assets/21374902/7169e2ef-2fd9-4599-a4d2-a64c3769b812"><br/>

## AOP 요약
- AOP의 주요 개념
  - `Aspect` 공통된 기능을 모듈화한 단위. 로깅이나 보안과 같은 관심사는 관점으로 표현될 수 있다.
  - `Join Point` : 관점이 적용될 수 있는 실행 지점을 의미한다. 메서드 실행, 예외 발생 등이 조인 포인트의 예시이다.
  - `Advice` : 관점이 언제, 어떻게 적용될지 정의하는 코드이다. 메서드 실행 전수에 로깅하는 어드바이스가 있을 수 있다.
  - `Pointcut` : 어떤 조인 포인트에 관점을 적용할지를 선택하는 표현식이다. 예를들어 특정 패키지 내의 모든 메서드에 관점을 적용하는 포인트컷을 정의할 수 있다.
  - `Weaving` : 관점을 핵심 로직에 적용하는 과정을 의미합니다. 컴파일 시점, 실행 시점 등에 위빙을 발생할 수 있다.
- 횡단 기능이 `적용될 객체`는 `TargetObject` 이고 TargetObject 기준으로 횡단 기능이 `적용될 수 있는 위치`를 `JoinPoint`라고 한다.
- Spring AOP에선 메소드 `Interceptor 기반`으로 동작하기 때문에 JoinPoint는 항상 `메소드 단위`이다.
- 적용할 횡단 기능의 `동작 코드`는 `Advice`에 정의되어 있으며 `JoinPoint` 중에서 `실제로 적용될 시점`을 `Pointcut` 이라고 한다.
- 즉, `Advice`는 많은 `JoinPoint` 중에서 `Pointcut`에 명시된 `JoinPoint`에서 실행된다.
- Spring AOP에선 `Advice` + `Pointcut` = `Advisor` 라고 한다.
- 횡단 관심사를 모듈화한 `Aspect` = `Advice` + `Pointcut` + `Introduction(inter-type)`
- `Introduction(inter-type)`는 `Aspect` 내부에 선언된 모든 요소(클래스, 인터페이스, 메소드, 그 외 모든 필드)
- `AOP Proxy`는 Aspect를 대신 수행하기 위해 AOP 프레임워크에 의해 생성된 객체이다.<br/>
  대부분의 AOP 프레임워크에선 핵심 관심 코드에 직접적인 Aspect를 하지 않고 `Proxy Object`를 활용해서 Aspect를 한다.<br/>
  횡단 관심 객체와 핵심 관심 객체의 느슨한 결합 구조를 만들고 필요 여부에 따라 부가 기능을 탈부착하기 용이하게 해준다.
- `Weaving`은 특정 JoinPoint에 Advice하여 핵심 기능과 횡단 기능이 교차하고 `새롭게 생성된 객체(AOP Proxy)를 프로세스에 적용하는 일련의 모든 과정`이다.
- Spring AOP는 Dynamic Proxy 기반으로 기본적으로 `Runtime Weaving(RTW)`를 사용하고 Waeving 방식이 AspectJ 보다 `가볍다`.

## Spring AOP 활용
- Logging
  ```java
  @Aspect
  @Component
  public class LoggingAspect {
    
      @Before("execution(* com.example.service.*.*(..))")
      public void beforeMethodExecution(JoinPoint joinPoint) {
          // 메서드 실행 전에 로깅
          System.out.println("Before executing method: " + joinPoint.getSignature().getName());
      }
    
      @After("execution(* com.example.service.*.*(..))")
      public void afterMethodExecution(JoinPoint joinPoint) {
          // 메서드 실행 후에 로깅
          System.out.println("After executing method: " + joinPoint.getSignature().getName());
      }
  }
  ```
- Transaction
  ```java
  @Aspect
  @Component
  public class TransactionAspect {
    
      @Autowired
      private PlatformTransactionManager transactionManager;
    
      @Around("execution(* com.example.service.*.*(..))")
      public Object manageTransaction(ProceedingJoinPoint joinPoint) throws Throwable {
          TransactionStatus status = transactionManager.getTransaction(new DefaultTransactionDefinition());
          try {
              // 메서드 실행 전에 트랜잭션 시작
              Object result = joinPoint.proceed();
              // 메서드 실행 후에 트랜잭션 커밋
              transactionManager.commit(status);
              return result;
          } catch (Exception ex) {
              // 예외 발생 시 트랜잭션 롤백
              transactionManager.rollback(status);
              throw ex;
          }
      }
  }
  ```
- Security
  ```java
  @Aspect
  @Component
  public class SecurityAspect {
    
      @Before("@annotation(com.example.annotation.RequiresPermission)")
      public void checkPermission(JoinPoint joinPoint) {
          // 특정 권한이 있는지 확인하고 접근 허용 여부 결정
          if (!hasPermission()) {
              throw new SecurityException("Access denied");
          }
      }
    
      private boolean hasPermission() {
          // 권한 확인 로직
          // ...
      }
  }
  ```

---

Service Interface는 왜 만들어야 할까?
===

## Service Interface

예전부터 Spring을 사용했던 개발자라면 Service를 구현할 때 아래 사진처럼 Interface Service와 ServiceImpl을 한 쌍으로 구현하는 코드를 볼 수 있었을 것이다.

<img width="225" alt="image" src="https://github.com/justdoanything/self-study/assets/21374902/c0dff341-321d-4251-881c-d6be62c62fb0">

규칙처럼 무심코 만들어서 사용했었는데 Interface Service를 사용하는 이유를 ChatGPT에게 물어보면 아래와 같은 답변을 얻을 수 있습니다.
1. 추상화와 구체화의 분리 : 인터페이스를 사용하면 추상적인 개념과 구체적인 구현을 분리할 수 있다. 인터페이스는 서비스의 기능과 동작을 정의하고 실제 구현은 해당 인터페이스를 구현하는 클래스에서 수행된다. 이렇게 함으로써, 클라이언트는 인터페이스를 통해 서비스에 접근하고 사용할 수 있으며 실제 구현에 대한 의존성 줄일 수 있다.
2. 유연성과 확장성 : 인터페이스를 사용하면 다른 구현을 손쉽게 교체할 수 있다. 예를 들어, 기존의 ServiceImpl 클래스를 다른 구현 클래스로 교체하더라도 클라이언트는 인터페이스를 통해 동일한 방식으로 서비스를 사용할 수 있다. 이는 유연성과 확장성을 높여준다.
3. 단위 테스트 : 인터페이스를 사용하면 단위 테스트를 수행할 때, Mock 객체를 이용하여 테스트를 할 수 있다. Mock 객체는 인터페이스를 구현한 가짜 객체로 실제 구현과는 독립적으로 동작하여 테스트를 더욱 쉽게할 수 있다.
4. 코드의 명확성과 가독성 : 인터페이스를 사용하면 코드의 목적과 의도를 명확하게 전달할 수 있다. 인터페이스는 서비스의 기능과 사용법을 정의하기 때문에 코드를 읽는 사람이 빠르게 이해하고 사용할 수 있다.
5. 다형성 구현 : 인터페이스를 통해 다형성을 구현할 수 있다. 다형성은 객체 지향 프로그래밍의 중요한 개념으로 한 인터페이스를 여러 개의 클래스가 구현할 수 있다. 이를 통해 동일한 인터페이스를 사용해서 다양한 구현체를 처리할 수 있으며 실행 시점에 구체적인 구현체를 결정할 수 있다.
6. 구현 클래스 교체 : 인터페이스를 사용하면 구현 클래스를 쉽게 교체할 수 있다. 예를 들어, 현재 ServiceImpl 클래스를 사용하고 있다면 나중에 필요에 따라 다른 구현 클래스로 변경할 수 있다. 이 때 클라이언트 코드는 변경 없이도 인터페이스를 통해 동일한 방식으로 서비스를 호출할 수 있다. 이는 코드의 유지보수와 확장에 용이하다.
7. 의존성 주입(Dependency Injection) : 인터페이스를 사용하면 의존성 주입을 쉽게 구현할 수 있다. 의존성 주입은 객체 간의 의존 관계를 외부에서 주입하여 코드 간의 결합도를 낮추고 유연한 구조를 구현하는 패턴이다. 인터페이스를 통해 의존성을 주입하면 다른 구현체를 주입해서 동일한 인터페이스를 사용하는 코드를 유지할 수 있다.

CharGPT의 답변은 Spring에서 Interface Service를 구현했을 때 얻는 장점이 아니라 일반적으로 Java에서 사용하는 Interface의 장점을 답변한 것처럼 보였습니다.

개발하면서 새로운 서비스 함수를 작성하거나 기존 함수를 수정할 때 Interface에 있는 함수를 찾고 수정해야 하는 작업은 꽤나 번거로운 작업이 되기도 했기 때문에 Spring에선 왜 Service Interface를 작성해야 하고 꼭 작성해야 하는지에 대해 찾아보았습니다.

## JDK Dynamic Proxy와 CGLIB Proxy
앞에서 설명한 [AOP (Aspect Oriented Programming)](https://github.com/justdoanything/self-study/blob/main/11%20SpringBoot.md#aop--aspect-oriented-programming-) 부분에서 AOP Proxy에 대한 내용을 설명할 때 JDK Dynamic Proxy와 CGLIB Proxy에 대한 내용이 잠깐 나왔었습니다.

결론부터 말하자면 기존에 Spring은 어노테이션을 관리하기 위해 사용되는 AOP Proxy가 JDK Dynamic Proxy 방식을 사용했었고 JDK Dynamic Proxy 방식은 interface 기준으로 만들고 관리하기 때문에 Interface Service를 반드시 만들어서 사용해야만 했습니다. 예를들어 Service 함수에서 많이 쓰이는 @Transactional 과 같은 어노테이션은 AOP Proxy를 만들어서 트랜잭션을 처리하기 때문에 Service Interface가 있어야 동작했습니다.

하지만 Class 기준으로 관리하는 CGLIB 방식을 사용하면서 Interface Service를 사용하지 않아도 됩니다.
CGLIB 방식이 처음 도입됐을 땐 Spring 설정에서 JDK Dynamic Proxy 방식을 사용할지 CGLIB 방식을 사용할지 정할 수 있게 만들어두었고 Spring 3.2 부터는 CGLIB가 내장됐습니다.

```yml
spring.aop.proxy-target-class=true # CGLIB
spring.aop.proxy-target-class=false # JDK Dynamic Proxy
```

Spring 3.2 버전부터는 CGLIB 방식이 내장되었기 때문에 CGLIB 방식을 사용하면 Service Interface를 구현하지 않아도 되지만 파라미터가 없는 default 생성자가 필수로 필요했고 4.0 버전부터는 default 생성자 없이 사용할 수 있게 되었습니다.
Mockito와 같은 Mocking 라이브러리를 사용하면 인터페이스를 사용하지 않아도 단위 테스트를 무리없이 진행할 수 있습니다.

하지만 상황에 따라 Interface Service를 구현하는게 더 유리한 경우도 있습니다. 

예를들어 네이버 로그인 Service, 카카오 로그인 Service와 같이 비슷한 기능을 하는 서비스를 관리하기 위해서 Interface Service를 사용하는 경우도 있으며
하나의 Interface Serivce를 여러 개의 ServiceImpl이 상속받아 사용한다면 @Primary, @Qulifier를 사용해서 상황에 맞는 특정 클래스를 주입해서 사용해야 합니다.



추가적으로 Spring 4.3 버전부터 생성자 주입(Constructor Injection) 방식을 도입해서 @Autowired를 사용하지 않고 private final 필드를 직접 사용해서 의존성을 주입할 수 있습니다.
```java
// @Autowired를 통한 의존성 주입
@Autowired
private NaverService naverService;

// private final을 통한 의존성 주입
private final KakaoService kakaoService;
```

## IoC 컨테이너와 Spring AOP Proxy
Spring AOP는 사용자의 호출 시점에 IoC 컨테이너에 의해 AOP를 할 수 있는 Proxy 객체를 생성해줍니다. 동적으로 생성 된 Proxy 객체는 메소드가 호출되는 시점에 부가기능을 추가할 메소드를 자체적으로 판단하고 주입해주는데 이처럼 호출 시점에 동적으로 Weaving 한다고 해서 Runtime Weaving 이라고 합니다.

이렇듯 Spring AOP는 Runtime Weaving의 방식을 기반으로 동작하고 있으며 Spring에선 Runtime Weaving을 할 수 있도록 상황에 따라 JDK Dynamic Proxy와 CGLIB 방식을 통해 Proxy 객체를 생성해줍니다.

타겟이 하나 이상의 인터페이스를 구현하고 있는 클래스라면 JDK Dynamic Proxy 방식으로 생성되고 인터페이스를 구현하지 않은 클래스라면 CGLIB 방식으로 AOP Proxy를 생성해줍니다.

### 1. JDK Dynamic Proxy
#### (1) 이름의 의미

JDK Dynamic Proxy는 Java Reflection 패키지에 존재하는 Proxy라는 클래스를 통해서 생성되는 Proxy를 의미합니다. 이 이름은 Reflection 패키지의 Proxy 클래스가 동적으로 Proxy를 생성해준다고 해서 붙여진 이름이기도 합니다.

이 Proxy의 핵심은 인터페이스를 기준으로 Proxy를 생성해준다는 점입니다. 인터페이스를 기준으로 Proxy 객체를 생성하기 때문에 구현체는 인터페이스를 반드시 상속받아야 하고 @Autowired를 통해 생성된 Proxy 객체를 사용하기 위해선 반드시 이니터페이스 타입으로 사용해야 합니다.

아래 코드에선 @Autowired를 사용하는 객체가 인터페이스가 아닌 구현체라서 Runtime 에러가 발생하게 됩니다.
```java
@Controller
public class UserController {
  @Autowired
  private MemberService memberService; // ❌Runtime Error 발생❌
  ...
}

@Service
public class MemberService implements UserService {
  @Override
  public Map<String, Object> findUserId(Map<String, Object> params){
    ...
    return params;
  }
}
```

#### (2) Proxy 객체 생성 과정
JDK Dynamic Proxy를 사용해서 Proxy 객체를 생성하기 위해선 Reflection 패키지의 newProxyInstance 메소드를 사용하면 됩니다.

타겟의 인터페이스를 자체적인 검증 로직을 통해 Proxy Factory에 의해 타겟의 인터페이스를 상속한 Proxy 객체를 생성하고 Proxy 객체에 InvocationHandler를 포함시켜 하나의 객체로 반환홥니다.

즉, Proxy 객체는 인터페이스를 기준으로 생성이 되게 됩니다.
```java
// Proxy 객체에 invocationHandler를 포함시켜 하나의 객체로 반환
Object proxy = Proxy.newProxyInstance(ClassLoader     // 클래스로더
                              , Class<?>[]            // 타겟의 인터페이스
                              , InvocationHandler);   // 타겟의 정보가 포함된 Handler
```

#### (3) 인터페이스를 사용해야 하는 이유
JDK Dynamic Proxy는 사용했을 때 인터페이스를 사용해야 하는 이유는 JDK Dynamic Proxy는 Proxy 객체에 invocationHandler를 포함시켜 하나의 객체로 변환하고 타겟의 인터페이스는 Proxy Factory에 의해 타겟의 인터페이스를 상속한 Proxy 객체를 생성하기 때문입니다.

사용자의 요청이 기존 타겟을 바라볼 수 있도록 타겟에 대한 위임코드를 Proxy 객체에 작성해줘야 하고 생성된 Proxy 객체에 대한 위임코드는 InvocationHandler에 작성해줘야 합니다.

따라서 사용자의 요청이 최종적으로 생성된 Proxy 메소드를 통해 호출될 때 내부적으로 invoke에 대한 검증과정이 이뤄집니다.

아래 코드에서 검증 과정이 이뤄지는 이유는 Proxy가 기본적으로 인터페이스에 대한 Proxy만 생성해주기 때문입니다. 따라서 개발자가 타겟에 대한 정보를 잘못 주입할 경우를 대비해서 JDK Dynamic Proxy는 내부적으로 주입된 타겟에 대한 검증코드를 갖고 있습니다.

```java
public Object invoke(Object proxy, Method proxyMethod, Object[] args) throws Throwable {
    Method targetMethod = null;

    // 주입된 타겟 객체에 대한 검증 코드
    if (!cachedMethodMap.containsKey(proxyMethod)) {
        targetMethod = target.getClass().getMethod(proxyMethod.getName(), proxyMethod.getParameterTypes());
        cachedMethodMap.put(proxyMethod, targetMethod);
    } else {
        targetMethod = cachedMethodMap.get(proxyMethod);
    }

    // 타겟의 메소드 실행
    Ojbect retVal = targetMethod.invoke(target, args);
    return retVal;
}
```

### 2. CGLIB(Code Generator Library)
#### (1) Proxy 객체 생성 방식
CGLIB는 클래스의 바이트 코드를 사용해서 Proxy 객체를 생성해주는 라이브러리 입니다. 클래스에 대해 Proxy를 생성해주기 때문에 인터페이스가 필요하지 않으며 Enhancer라는 클래스를 통해 Proxy를 생성합니다.

```java
Enhancer enhancer = new Enhancer();
enhancer.setSuperclass(MemberService.class);  // 타겟 클래스
enhancer.setCallback(MethodInterceptor);      // Handler
Object proxy = enhancer.create();             // Proxy 생성
```

#### (2) CGLIB의 장단점
CGLIB는 타겟 클래스가 갖고 있는 모든 메소드를 재정의해서 Proxy 객체를 생성하기 때문에 final 메소드 또는 클래스에 대해선 재정의를 할 수 없는 단점이 있습니다.

반면에 CGLIB는 바이트 코드를 조작해서 Proxy를 생성하기 때문에 성능에 대한 부분이 JDK Dynamic Proxy에 보다 훨씬 좋습니다. CGLIB는 타겟 클래스에 대한 바이트 코드를 조작해서 Proxy를 생성하고 Handler 안에서 타겟의 메소드가 처음 호출될 때 동적으로 타겟의 클래스 바이트 코드를 조작합니다. 이후에 조작된 바이트 코드를 재사용합니다.

```java
public Object invoke(Object proxy, Method proxyMethod, Object[] args) throws Throwable {
  Method targetMethod = target.getClass().getMethod(proxyMethod.getName(), proxyMethod.getParameterTypes());
  Ojbect retVal = targetMethod.invoke(target, args);
  return retVal;
}
```

#### (3) 초기 단계의 한계점과 보완된 점
- 첫번째 한계점: Spring이 기본적으로 지원하지 않는 방식이라서 별도의 의존성(`net.sf.cglib.proxy.Enhancer`)을 추가해야 했습니다.
- 두번째 한계점: 구현을 위해선 반드시 파라미터가 없는 default 생성자가 필요했습니다.
- 세번째 한계점: 불필요하게 타겟의 생성자 두 번 호출 됐습니다.
- 개선점: Spring 3.2 버전부터는 CGLIB 방식이 공식적으로 내장되었습니다. -> 첫번째 한계점 해결
- 개선점: Spring 4.0 버전부터는 Objensis 라이브러리의 도움을 받아 default 생성자 없이 Proxy를 생성할 수 있습니다. -> 두번째, 세번째 한계점 해결

---

VO 활용
===

프로젝트 초반에 Code Convention과 관련한 내용을 SE와 SA가 모여 정할 때 React에 관련된 Convention은 크게 이견이 없지만 Spring에서 VO를 어떻게 사용할지에 대한 내용은 다양한 의견이 나오곤 했습니다.

왜냐하면 Spring과 MyBatis를 같이 사용했을 때 코드 규모가 커짐에 따라(SQL의 수가 많아지면) 가장 많아지는 클래스는 VO 클래스이기 때문입니다.

이는 프로젝트 후반부에 기능을 수정할 때 원하는 VO를 찾는 것 부터 수정해야 하는 코드의 범위(Controller, Service, Repository, SQL, 테스트 코드)에 영향을 미치며 사이드 이펙트를 발생시키는 원인이 됩니다.

따라서 VO를 효율적으로 관리하는 것은 코드 품질과 관련된 중요한 문제 중 하나라고 생각합니다.

(근래에 말하는 좋은 코드는 재사용성과 확장성이 좋고 직관적이고 읽기 편하며 읽으면서 이해할 수 있는 코드를 말한다고 생각합니다. 
그리고 코드 규모가 커짐에 따라 유지보수와 디버깅이 수월한 코드 구조를 만드는 것 또한 중요한 문제이고 이를 위해선 코드의 중복을 줄이고 의존성을 낮추는 것이 중요합니다.)

VO 활용에 대한 문제는 정답이 없는 문제이기 때문에 다양한 의견이 존재하고 상황에 따라 적절하게 사용하는 것이 중요한 것 같습니다. 
여러 의견을 나누며 토론하고 프로젝트 상황에 맞는 방향을 정하는 과정은 좋은 코드를 만들기 위한 중요한 과정이라고 생각합니다.

쿠팡은 Micro Model 이라고 해서 API 하나에 VO를 한 그룹씩 만들어서 사용하고 각각의 API는 모델을 공유하지 않는 방법도 있다고 들었었는데 제 프로젝트에선 업무 구분과 사용 용도에 따라 VO를 만들어서 사용했습니다.

이 페이지에선 제가 경험했던 프로젝트를 바탕으로 VO를 어떻게 활용하고 사용했는지 정리해보았습니다.  

## VO와 DTO
Spring에서 Model을 다룰 때 VO와 DTO를 사용합니다. VO와 DTO의 사전적인 의미를 보면 `VO`는 `Value Object`로 특정 값을 표현하기 위한 객체이고 `DTO`는 `Data Transfer Object`로 데이터 전달을 위한 객체라고 볼 수 있습니다.

사전적 의미처럼 정석적으로 사용한다면 `VO`는 특정 값을 표현하기 위한 `불변 객체`이기 때문에 객체가 다르더라도 가지고 있는 값이 같다면 같은 객체로 봐야 합니다.
따라서 `equals()`와 `hashCode()` 함수를 구현해줘야 하고 객체가 가진 값은 불변해야하기 때문에 setter 함수를 갖지 않고 객체 내 비지니스 로직을 포함하고 있을 수 있습니다.

`DTO`는 각 Layer 사이에서 데이터를 전달할 때 사용하는 객체입니다. 갖고 있는 값이 같아도 같은 객체로 보지 않으며 setter/getter 함수를 모두 갖고 있고 대신 데이터 전달을 위한 객체이므로 다른 비지니스 로직을 포함하지 않아야 합니다.


|                     | VO                                                                              | DTO                           |
|---------------------|---------------------------------------------------------------------------------|-------------------------------|
| 객체 종류               | 불변 객체                                                                           | 가변 객체                         |
| 객체 비교               | 다른 객체라도 갖고 있는 값이 같다면 같은 객체로 봐야함<br/>따라서 객체 비교를 위한 equals()와 hashCode()를 구현해줘야 함 | 갖고 있는 값이 모두 같더라도 같은 객체로 보지 않음 |
| getter/setter 사용 여부 | getter                                                                          | setter/getter                 |
| 비지니스 로직 포함 여부       | 비지니스 로직을 포함                                                                     | 비지니스 로직을 포함하지 않음              |


제가 수행했던 프로젝트에서는 VO와 DTO를 나누지 않고 VO라는 이름으로 Model 객체를 사용했으며 사전적 의미와 다르게 불변 객체로 사용하지 않고 각 Layer 사이에서 데이터를 전달할 때 사용했습니다. 그리고 **toVO(💡¹)** 와 같은 함수를 만들어 비지니스 로직을 포함하기도 했습니다.

즉, VO와 DTO가 갖는 사전적 의미를 합쳐서 하나로 사용했습니다.

## VO 종류
### 1. RequestVO/ResponseVO
- 주로 Front-end(FE)와 통신할 때 사용되며 FE로 부터 넘어오는 데이터를 받거나 FE로 데이터를 보내는 용도로 사용합니다.
- RequestVO/ResponseVO는 데이터를 주고 받을 용도로 쓰이기 때문에 VO의 사전적인 의미와 가장 닮아있다고 볼 수 있습니다. 따라서 Service Layer에서 RequestVO는 불변 객체로 사용되고 ResponseVO는 가장 마지막에 불변 객체로 만들어 사용합니다.
- **RequestVO/ResponseVO를 가장 안전하게 사용하는 방법(💡²)** 은 `RequestVO` → `일반VO(Service)` → `테이블VO(Repository)` → `일반VO(Service)` → `ResponseVO` 단계를 거치는 것 입니다. 

  <img width="859" alt="image" src="https://github.com/justdoanything/self-study/assets/21374902/41d31e95-0b59-4d84-97f9-d884918d0b61">

- #### RequestVO
  - FE에서 넘어오는 데이터를 받기 위해서 Controller Layer에서 사용되며 RequestVO는 주로 **Session 정보(💡³)** 와 함께 Service Layer로 전달합니다.
  - RequestVO는 VO의 사전적 의미처럼 toVO 같은 비지니스 로직을 담을 수 있지만 그 안에 있는 값을 변경하지 않아야 합니다. (setter 사용 ❌)
  - 대신 API 하나에 RequestVO/ResponseVO는 하나씩 사용되기 때문에 필요하지 않다면 equals()와 hashCode()를 구현해주지 않아도 됩니다. (Controller의 함수 : RequestVO : ResponseVO = 1 : 1 : 1)
  - RequestVO는 주로 Service 함수 초반부에 toVO 함수를 통해 일반VO를 생성하거나 Service 내에서 FE로 부터 받은 값을 사용하기 위해 getter 함수를 통해 final 변수에 할당해서 사용합니다.
- #### ResponseVO
  - FE로 데이터를 넘기기 위한 불변 객체로 사용되어야 하며 주로 Service의 반환 객체에 사용됩니다.
  - Service 함수 마지막에 생성자 혹은 Builder를 사용해서 **ResponseVO를 생성(💡⁴)** 하고 반환합니다.
  - ResponseVO를 비지니스 로직 중간에 생성하고 비지니스 로직에 따라 내부 값을 변경(setter)하는 것은 지양해야 합니다. (setter 사용 ❌)
- #### RequestVO와 ResponseVO의 사용 예시
  ```java
  public ResponseVO method(RequestVO requestVO, String customerId) {

    // toVO 사용과 final 변수 할당 이후 requestVO는 변경되거나 사용하지 않아야 함
    GetVO getVO = requestVO.toGetVO();
    final String name = requestVO.getName();
    
    /* 비지니스 로직 */
    ResultVO resultVO = repository.findSomething(getVO);
  
    // ResponseVO는 함수 마지막에 생성자 혹은 Builder를 사용해서 생성하고 반환
    return ResponseVO.builder()
      .name(resultVO.getName())
      .age(resultVO.getAge())
      .build(); 
  }
  ```

  ```java
  // ❌ 지양해야할 코드 ❌
  public ResponseVO method(RequestVO requestVO, String customerId) {
  
    // ❌ RequestVO가 Repository에 바로 사용됨
    GetVO getVO = repository.getSomething(requestVO);
    
    /* 비지니스 로직 */
  
    // ❌ 함수 중간에 생성된 값이 requestVO에 새로 할당되고 변경 된 requestVO가 또 다시 비지니스 로직에 사용됨
    String changedName = repository.findName();
    requestVO.setName(changedName);
    externalService.updateSomething(requestVO);
  
    /* 비지니스 로직 */
  
    // ❌ ResponseVO가 함수 중간에 생성되고 비지니스에 의해 값이 변경되거나 내부 값이 사용됨
    ResponseVO responseVO = repository.findResponse();
    externalService.updateSomething(responseVO.getName());
    /* 비지니스 로직 */
    reponseVO.setAge(newAge);
    return responseVO;
  }
  ```
- 
- #### 💡¹) toVO함수의 사용
  - toVO 함수는 VO에서 VO로 변환할 때 사용합니다. toVO 함수도 비지니스 로직 중 한 부분이기 때문에 VO에선 갖지만 DTO에선 갖지 않습니다.
  - toVO 함수를 사용하는 이유는 Service 함수 내에는 중요한 비지니스 부분만 나타내기 위함입니다.
  - toVO 함수에서 하는 단순한 값 복사나 값 변환 같은 코드를 Service에 두게 되면 Service 코드를 봤을 때 비지니스 로직에 집중하기 어려워 집니다.
  - 따라서 VO에서 다른 VO를 만들 때 필요한 값 복사나 값의 변환은 toVO 함수 내에 적어야 합니다.
  
    ```java
    /* RequestVO의 값 복사 */
    public class RequestVO {
        private String name;
        private int age;
    
        public GetVO toGetVO() {
            return GetVO.builder()
                .name(name)
                .age(age)
                .build();
        }
    }  
    ```
    ```java
    /* VO의 값 변환 */
    public class CustomGetVO {
        private int id;
        private ReplyTypeCode replyTypeCode;
        private String name;
        private String contents;
    
        public ReplyUpdateVO toReplyUpdateVO(String customerId) {
            String type = replyTypeCode == ReplyTypeCode.NORMAL ? ReplyTypeCode.NORMAL.code() : ReplyTypeCode.SECRET.code();
            return ReplyUpdateVO.builder()
                        .id(id)
                        .type(type)
                        .name(name)
                        .contents(contents)
                        .customerId(customerId)
                        .build(); 
        }
    }
    ```
  
- #### 💡²) 일반VO를 꼭 써야할까? SQL에서 RequestVO와 ResponseVO를 바로 사용한다면?
  - 회사에서 경험했던 프로젝트에서 SE(개발자)의 역할은 Agile 기반의 Sprint를 수행하면서 Story 기반의 개발을 할 때 한 명의 개발자가 FE/BE(React/SpringBoot)를 모두 개발했었습니다.
  - 이렇게하다보니 FE에서 사용하는 인터페이스 필드와 RequestVO 필드의 이름을 모두 같게 작명하고 간단한 조회성 API의 경우 아래 코드와 같이 RequestVO와 ResponseVO를 SQL에서 바로 사용해도 기능상엔 문제가 없습니다.
    ```java
    public ResponseVO method(RequestVO requestVO, String customerId) {
      return repository.findSomething(requestVO);
    }
    ```
  - FE와 BE에서 사용되는 변수의 용도는 조금씩 다를 수 있고 FE와 BE에 Naming Convention이 같으면 좋지 않다고 생각을 하지만 한 명의 개발자가 FE와 BE 기능 모두를 개발하기 때문에 편의상 필드 이름을 같게하고 SQL에서 사용하는 필드 이름도 맞춰서 사용하기 때문에 `SQL에서 RequestVO와 ResponseVO를 바로 사용한다고해도 기능상에는 문제가 없을 것 같습니다.`
  - 하지만 이는 유지보수와 코드 품질엔 좋지 않다고 생각합니다. 기본적으로 RequestVO와 ResponseVO를 독립적으로 사용해야 하고 필드명이 수정되면 FE, BE, SQL 모두 수정해야하는 번거로움이 있습니다. SQL에서의 오타는 테스크 코드 등을 통해서 방지할 수 있지만 기본적으로 런타임 에러이기 때문에 유지보수에 안좋다고 생각합니다.
    ```java
    public ResponseVO method(RequestVO requestVO, String customerId) {
      GetVO getVO = requestVO.toGetVO();
      GetResultVO getResultVO = repository.findSomething(getVO);
      return new ResponseVO(getResultVO);
    }
    ```
  - 따라서 FE와 BE의 필드명을 다르게 가져간다면 toVO로 일반VO를 만들어서 사용하는 것이 안정적이고 그렇지 않다면 아래와 같은 장단점이 있습니다.
  - 일반VO를 사용했을 때의 단점
    - toVO 함수는 단순한 값을 복사만 하는 함수이기 때문에 불필요한 코드의 작성이 될 수 있습니다.
    - Service 함수의 코드가 다소 길어집니다.
    - 필드 이름이 변경되었을 경우 toVO 함수를 수정해야 합니다. (하지만 컴파일 에러가 발생하기 때문에 찾기는 쉽습니다.)
  - 일반VO를 사용했을 때의 장점
    - RequestVO의 필드명이 바뀌거나 Table의 필드명이 바꼈을 경우 toVO 함수만 수정하면 되기 때문에 유지보수에 유리합니다.
    - RequestVO와 ResponseVO를 독립적으로 사용할 수 있어서 버그가 발생했을 때 RequestVO, Service 로직, SQL, ResponseVO를 분리해서 디버깅하기 쉽습니다.
    - 그리고 나중에 비지니스 로직이 변경되서 Service 함수를 수정해야할 때 Service 로직만 수정하면 되기 때문에 유지보수에 유리합니다. (첫번째 예시 코드는 `repository.findSomething(requestVO)`를 분리해서 작성해야하는데 수정 범위가 넓고 사이드 이펙트를 발생시킬 수 있습니다.)
    - SQL에서 공통 코드 값을 사용할 때 일반VO에 private final 필드를 선언해서 사용할 수 있습니다. RequestVO에 안에도 private final 필드를 선언할 수 있지만 RequestVO의 의미와 맞지 않으며 Swagger를 사용하면 @Ignore와 같은 어노테이션으로 숨겨야합니다.
  - 결론적으로 코드의 재사용성과 확장성, 유지보수의 편리함을 생각해봤을 때 일반VO를 사용하는 것이 좋다고 생각합니다. 하지만 경우에 따라 간단한 로직의 경우엔 첫번째 예시 코드처럼 SQL에서 RequestVO와 ResponseVO를 바로 사용하는 것도 나쁘지 않다고 생각합니다.

  - 📌 VO 안에서 private final 필드 사용 - 공통 코드와 enum 필드
    - 주문 타입, 컨텐츠 타입 등 공통 코드를 만들어서 관리하는 방법은 여러가 입니다.
    - 변경이 자주 된다면 사용할 때마다 공통 코드를 조회해서 사용하거나 주기적으로 공통 코드를 조회해서 static 변수로 사용할 수 있습니다.
    - 프로젝트에선 공통 코드가 거의 변하지 않아서 enum 변수를 만들어서 사용했었고 enum으로 선언한 코드를 SQL에서 사용하기 위해 VO 안에 private final 변수로 사용했습니다.
    - 변수의 이름은 반드시 SQL에도 의미를 알 수 있도록 변수 이름을 정했습니다.
      ```java
      public enum ContentsTypeCode {
          FEED("001"),
          COMMENT("002"),
          NOTICE("003"),
          COUPON("004"),
          VOTE("005");
      
          ContentsTypeCode(String code) {
              this.code = code;
          }
      
          private String code;
      
          public String code() {
              return code;
          }
      }
      ```
      ```java
      public class ContentsFeedGetVO {
          private String contentsType;
          private String title;
          private String contents;
          private String customerId;
          private final String contentsTypeFeedCode = ContentsTypeCode.FEED.code();
      }
      ```
      ```sql
      SELECT ...
      FROM contents c
      WHERE c.contents_type = #{contentsTypeFeedCode}
      ```
- #### 💡³)Session 관련된 정보들은 Controller에서 넘겨줘야 할까? 아니면 Service 안에서 가져와야 할까?
  - 주로 로그인한 사용자의 customerId는 Session에서 정보를 가져옵니다. 이 Session 정보는 Controller 함수 내에서 가져와서 Service에 넘겨주거나 Service 함수 내부에서 가져올 수 있습니다.
    ```java
    /* Controller 함수 내에서 customerId 가져온 후 Service로 전달 */
    private final Service service;
    public CommonResponseVO<ResponseVO> method(RequestVO requestVO) {
      String customerId = SessionUtility.getCustomerId();
      return CommonResponseUtility.createSuccess(service.method(requestVO, customerId));
    }
    ```
    ```java
    /* Service 함수 내에서 customerId 가져오기 */
    public ResponseVO method(RequestVO requestVO) {
      String customerId = SessionUtility.getCustomerId();
      ...
      return new ResponseVO();
    }
    ```
  - 결론적으로 customerId는 Controller에서 가져온 후 Servce 함수로 전달해주는게 맞다고 생각합니다.
  - 주로 권한 체크나 유저 등급 체크, 로그인 여부 체크 등 권한과 관련된 공통 처리는 Service 함수를 호출하기 전에 Controller에서 수행하기 때문에 세션 정보인 customerId도 Controller에서 먼저 취득 후 Service로 넘겨주는게 자연스럽습니다. 공통 처리를 위해 customerId를 Controller에서 취득하고 Service 함수 내에서 또 취득한다면 중복 코드가 됩니다.
  - Service 함수는 다른 Service에서 호출될 수 있습니다. 만약 다른 Service에서 로그인한 유저의 customerId가 아니라 특정 유저의 customerId로 데이터를 가져오고 싶을 때 Service 함수 내부에서 customerId를 취득한다면 이를 구분하기 위해서 Service 내 비지니스 로직이 불필요하게 복잡해지고 Service 함수를 호출하는 입장에서도 신경 써야할 부분들이 늘어나게 됩니다.
  - Service 함수의 파라미터로 customerId를 명시함으로서 해당 Service 함수는 내부에서 Session 정보를 사용하는 함수인지 사용하지 않는 함수인지 직관적으로 알 수 있습니다.
- #### 💡⁴) ResponseVO 조립
  - 일반적으로 Service 함수 내 비지니스 로직에서 발생되는 값으로 ResponseVO를 만들 경우 `Builder 패턴`을 사용해서 ResponseVO를 만듭니다. (생성자의 파라미터로 각 필드를 넘기지 않습니다. 이는 Builder를 쓰는 이유에 반대됩니다.)
    ```java
    public ResponseVO method(RequestVO requestVO) {
      return ResponseVO.builder()
        .name(changedName)
        .age(changedAge)
        .build();
    }
    ```
  - 만약 사용하는 필드가 너무 많거나 특정 VO로 ResponseVO를 만들 경우 `생성자`를 이용합니다. 아니면 `toVO 함수`를 만들어서 사용할 수 있습니다.
    ```java
    public ResponseVO method(RequestVO requestVO) {
      return new ResponseVO(getVO);
    }
    ```
    ```java
    // ResponseVO의 생성자 활용
    public class ResponseVO {
      private String name;
      private int age;
      
      public ResponseVO(GetVO getVO) {
        this.name = getVO.getName();
        this.age = getVO.getAge();
      }
    }
    ```
    ```java
    // toVO 함수를 만들어서 활용
    public class GetVO {
      private String name;
      private int age;
    
      public ResponseVO toResponseVO() {
        return ResponseVO.builder()
          .name(this.name)
          .age(this.age)
          .build();
      }
    }
    ```
  - 만약 조회 결과를 사용해서 List<ReponseVO>를 만들 경우 `Stream과 생성자`를 활용해서 만들 수 있습니다.
    ```java
    // Stream&생성자 활용
    public ResponseVO method(RequestVO requestVO) {
      List<GetVO> getVOs = repository.findSomethings(requestVO);
      return getVOs.stream().map(ResponseVO::new).collect(Collectors.toList());
    }
    ```

**📌 Check Point**
- RequestVO와 ResponseVO를 불변 객체로 Service 함수 처음과 마지막에만 사용하고 비지니스 로직 중간에 값을 변경하거나 직접적으로 사용하는 것을 지양합니다.
   (RequestVO와 ResponseVO엔 setter 함수를 구현하지 않는 것이 좋습니다.)
- FE와 BE의 Naming Convention을 될 수 있으면 다르게 가져가고 편의상 같은 필드 이름을 써도 크게 상관은 없습니다.
- 코드의 확장성과 유지보수 용이성을 위해서 `RequestVO` → `일반VO(Service)` → `테이블VO(Repository)` → `일반VO(Service)` → `ResponseVO` 단계를 거치도록 설계합니다.

  <img width="859" alt="image" src="https://github.com/justdoanything/self-study/assets/21374902/41d31e95-0b59-4d84-97f9-d884918d0b61">

- RequestVO는 Service 함수 초반에 toVO 함수를 통해 일반VO를 생성하거나 Service 내에서 FE로 부터 받은 값을 사용하기 위해 getter 함수를 통해 final 변수에 할당해서 사용합니다.
- ResponseVO는 Service 함수 마지막에 생성자 혹은 Builder를 사용해서 생성하고 반환합니다.
- Session과 관련된 정보는 Controller에서 권한이나 유저 정보와 관련된 공통 처리를 한 후 Service 함수로 전달해줍니다.

### 2. 테이블VO
프로젝트에서 가장 의견이 많았던 부분 중 하나는 테이블VO의 사용성이었습니다.

테이블VO는 테이블과 1대1로 맵핑되는 VO로 JPA의 Entity와 비슷한 개념으로 사용했습니다. (프로젝트에서 테이블 생성 및 관리는 Flyway를 사용했습니다.)

CUD(생성/수정/삭제)는 주로 테이블에 존재하는 필드만 사용하기 때문에 테이블VO를 활용했습니다.

R(조회)은 테이블에 존재하는 필드 뿐만 아니라 FE로부터 받은 값이나 공통 코드 값을 사용하기 때문에 GetVO를 만들어서 활용했습니다.

예를들어 하나의 테이블의 각기 다른 필드를 수정하는 API가 4개 있다고 가정해보겠습니다. (Repository와 SQL도 각 4개로 존재한다고 가정하겠습니다.)

테이블VO를 사용하지 않는다면 UpdateVO를 만들어서 사용해야 합니다.

UpdateVO는 API마다 1개씩해서 총 4개를 만들어서 사용하거나 하나의 테이블을 수정하기 때문에 수정하는 필드를 모두 포함하고 있는 하나의 UpdateVO를 만들 수 있습니다.

하지만, 하나의 UpdateVO를 만드는 것은 테이블VO와 유사하며 API가 무수히 많을 때 4개의 API를 그룹 지을 수 있는 명확한 기준이 없다면 VO를 사용하는 기준이 모호해지기 때문에 권장하는 방법은 아닙니다.

따라서 UpdateVO를 4개 만들어서 사용하는 경우는 아래와 같은 장단점이 있습니다.

1. API 별로 VO가 독립적으로 있어서 디버깅이나 수정에 용이합니다.
2. 비슷한 목적의 VO가 4개가 필요하기 때문에 코드의 규모가 불필요하게 커집니다.
3. 테이블의 필드가 변경되었을 경우 코드와 VO 4개를 수정해야 하기 때문에 수정해야 할 코드의 양이 많습니다. (이는 테이블VO를 사용해도 비슷합니다.)
4. UpdateVO와 테이블의 관계를 나타내고 있지 않기 때문에 테이블 수정에 따른 수정 범위에 누락될 수 있습니다.

CUD(생성/수정/삭제)에서 테이블VO를 만들어서 사용하는 가장 큰 이유는 CUD에선 테이블에 속한 필드만 사용하기 때문입니다.
1. 테이블VO를 사용한다면 불필요하게 중복되는 필드를 갖고 있는 VO 클래스를 여러개 만들 필요가 없어집니다. 하지만 테이블이 수정됐을 경우 테이블VO를 수정하고 테이블VO를 사용하고 있는 SQL도 수정해야 합니다. 이 때, UpdateVO를 여러개 만들어서 사용하는 것과 비슷하지만 테이블VO는 사용되고 있는 곳이 명확하고 찾기 쉽기 때문에 수정을 더 쉽게 할 수 있습니다.
2. 모든 SQL에서 테이블VO에 있는 필드 모두를 사용하는 것이 아니기 때문에 NPE을 조심해야 합니다. (Mybatis를 사용할 때 공통적으로 주의해야 하는 사항입니다.)
3. 조회 결과에서 테이블에 속한 대부분의 필드를 사용할 경우 GetVO에 테이블VO를 상속해서 사용할 수 있습니다.
4. 테이블VO는 toVO와 같은 비지니스 로직을 담지 않으며 주로 getter/setter만 사용합니다.²³⁴

```java
/* 테이블VO */
public class ReplyVO {
    private int id;
    private String type;
    private String contents;
    private String registerId;
    private LocalDateTime registrationDateTime;
    private String updaterId;
    private LocalDateTime updateDateTime;
}
```
```java
/* Repository */
@Mapper
public interface CommunityRepository {
    int insertReplies(List<ReplyVO> replies);
    int updateReplyContentsByNameAndType(ReplyVO reply);
    int deleteRepliesByNameAndType(ReplyVO reply);
}
```
```sql
/* SQL */
<insert id="insertReplies" parameterType="ReplyVO">
    INSERT INTO reply (id, type, contents, register_id, updater_id)
    VALUES
    <foreach collection="replies" item="reply" separator=",">
        (#{reply.id}, #{reply.type}, #{reply.contents}, #{reply.registerId}, #{reply.updaterId})
    </foreach>
</insert>

<update id="updateReplyContentsByNameAndType" parameterType="ReplyVO">
    UPDATE reply
    SET contents = #{contents}
    WHERE name = #{name} 
      AND type = #{type}
</update>

<delete id="deleteRepliesByNameAndType" parameterType="ReplyVO">
    DELETE FROM reply
    WHERE name = #{name} 
      AND type = #{type}
</delete>
```

코드의 규모가 커질수록 테이블VO를 사용하는 것이 장점이 더 많다고 생각하지만 정답은 아니기 때문에 프로젝트 상황에 맞춰 정해야 합니다.

**📌 Check Point**
- 테이블VO는 JPA의 Entity처럼 테이블과 1대1로 맵핑되는 VO 입니다.
- 테이블VO를 사용하는 가장 큰 이유는 CUD(생성/수정/삭제)는 테이블 안에 있는 필드만 사용하기 때문에 하나의 테이블VO로 통일해서 사용하기 위함입니다.
- 테이블VO를 사용하면 불필요하게 중복된 필드를 갖는 VO를 여러개 만들 필요가 없으며 테이블이 수정되면 테이블VO를 수정하고 테이블VO가 사용되고 있는 곳을 추적하기 쉽기 때문에 수정에도 용이합니다.
- 테이블VO를 사용하지 않고 각각의 VO를 만들어서 사용한다면 독립적으로 동작하기 때문에 디버깅이나 유지보수에 유리합니다. 하지만 코드의 규모가 불필요하게 너무 커지게 됩니다.

### 3. 일반VO
일반VO는 상황에 따라 데이터를 전달하는 용도, SQL에서 사용하는 용도 등으로 만들어서 사용했습니다.

CRUD에서 사용되는 경우 목적성을 나타내기 위해 ~GetVO, ~UpdateVO, ~CreateVO, ~DeleteVO 등으로 네이밍해서 사용했습니다.

일반VO는 사용되는 목적에 맞게 네이밍을 해서 직관적인 코드를 짤 수 있도록 하는게 중요 포인트 입니다.

조회에 사용되는 VO의 경우 테이블에 JOIN이 걸려있거나 FE로부터 받은 값들을 사용하기 때문에 GetVO와 같이 일반VO를 만들어서 사용했습니다.

GetVO는 RequestVO에서 toVO 함수를 통해 만들고 공통 코드(private final) 값을 받아서 사용했습니다.

### 4. 정리

프로젝트 후반부에 코드의 규모가 커지고 같은 코드를 여러명의 개발자가 수정하면서 혹은 공통 부분이 수정되면서 영향을 받는 부분들은 늘어나게 됩니다.

디버깅하고 버그를 수정하거나 요구사항이 변경되어 미리 짜놓은 코드를 수정해야할 때 내가 수정해야하는 부분과 연관되어 있는 클래스와 기능을 찾는 것이 점점 어려워지고 예상하지 못한 사이드 이펙트가 발생하기도 합니다.

이를 방지하고 최대한 리스크를 줄이기 위해서 코드의 품질은 가장 중요하다고 생각합니다.

여러 프로젝트를 경험하면서 직관적이고 확장성이 좋은 코드, 독립적인 코드를 효율적으로 잘 짜는게 중요하다고 매번 느끼게 됩니다.

코드에 정답은 없기 때문에 다른 개발자와 토론을 거쳐 양질의 코드를 만들어내는 과정이 가장 중요하다고 생각합니다.

---

Exception 공통 처리
===
Spring에서 기본적으로 사용되는 Exception이 있고 필요할 땐 직접 Exception을 만들어서 사용할 수 있습니다.

여러가지의 Exception을 처리하기 위해서 기본적으로 `try-catch`문을 사용하거나 Controller/Service와 같은 클래스 및 함수에서 `throw new ...`를 사용하고 최상단 클래스에서 처리하기도 합니다.

하지만 위와 같은 방법은 모든 클래스/함수에서 예외 처리에 대한 코드를 작성해야 하기 때문에 코드의 중복이 발생하고 예외 처리에 대한 통일성이 없을 수 있습니다.

따라서 Spring에선 `@ExceptionHandler`, `@ControllerAdvice`를 사용해서 Exception을 공통으로 처리할 수 있습니다.

(Exception은 HandlerExceptionResolver를 사용해서 공통 처리할 수 있지만 사용법이 약간 복잡하고 응답에 message를 담을 수 없기 때문에 SpringBoot에선 주로 `@ExceptionHandler`, `@ControllerAdvice`를 사용해서 처리합니다.)

## @ExceptionHandler
- `@ExceptionHandler`는 `@Controller`, `@ControllerAdvice`가 적용된 클래스에서 사용할 수 있습니다.
- `@Controller`가 적용된 Controller 클래스 안에서 `@ExceptionHandler`를 사용하면 해당 Controller 안에서 발생하는 Exception만 처리할 수 있습니다.
- 따라서 Controller 클래스가 여러개라면 모든 클래스 안에 `@ExceptionHandler`에 대한 코드를 작성해서 처리해야 하기 때문에 코드가 중복되고 일관적인 에러 처리가 어려워집니다.
```java
/* ExcetpionHandler의 사용 예시 */
@Controller
public class FirstController {

    @GetMapping("/first/example")
    public ResponseEntity<CommonResponseVO> method() {
        return service.method();   
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<String> handleCustomException(CustomException ex) {
        return new ResponseEntity<>(ResponseUtil.createError(ex.getMessage(), HttpStatus.BAD_REQUEST));
    }
}
```
```java
/* 모든 Controller 클래스에 @ExceptionHandler 코드를 적어줘야 한다. */
@Controller
public class SecondController {

    @GetMapping("/second/example")
    public ResponseEntity<CommonResponseVO> method() {
        return service.method();
    }

    /* 중복 코드 */
    @ExceptionHandler({CustomException.class, BindException.class})
    public ResponseEntity<String> handleCustomException(CustomException ex) {
        return new ResponseEntity<>(ResponseUtil.createError(ex.getMessage(), HttpStatus.BAD_REQUEST));
    }
}
```

## @Controller/@ControllerAdvice와 @RestController/@RestControllerAdvice
이름이 비슷한 2개의 어노테이션은 비슷한 기능을 수행하지만 약간의 차이점이 존재합니다.

아래의 정의된 코드를 보면 `@RestController`는 `@Controller` + `@ResponseBody`이고 `@RestControllerAdvice`는 `@ControllerAdvice` + `@ResponseBody`인 것을 볼 수 있습니다.

즉, @Rest~로 시작하는 어노테이션에는 `@ResponseBody`를 포함되어 있고 이는 주로 RESTful API의 응답 데이터를 만드는데 사용된다는 것을 알 수 있습니다.

- `@Controller`
  - 주로 전통적인 Spring MVC의 Controller를 나타냅니다. 
  - View를 반환하며, 주로 JSP, Thymeleaf, Freemarker 등과 같은 템플릿 엔진을 이용하여 HTML 페이지를 생성합니다. 
  - 반환되는 데이터가 주로 모델(Model)을 통해 View에 전달되어 화면을 렌더링하는 데 사용됩니다.

- `@RestController`
  - 주로 RESTful 웹 서비스를 처리할 때 사용됩니다. 
  - `@ResponseBody` 어노테이션을 포함하고 있고 메서드 자체가 응답 데이터를 반환하며, 주로 JSON 또는 XML 형식으로 데이터를 반환합니다.

하지만 RESTful API에서 반드시 @Rest~를 사용해야 하는 것은 아닙니다.

예를들어 `@ExceptionHandler`는 어떠한 응답 객체도 가질 수 있기 때문에 `@ControllerAdvice`로 예외를 수집하고 RESTful API처럼 ResponseEntity 객체를 만들어서 사용할 수 있습니다.

```java
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Controller
@ResponseBody
public @interface RestController { ... }

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface ControllerAdvice { ... }
```

```java
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Controller { ... }

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ControllerAdvice
@ResponseBody
public @interface RestControllerAdvice { ... }
```

## @ControllerAdvice
- Spring 3.2 버전부터 사용할 수 있습니다.
- `@ControllerAdvice`를 선언한 클래스 안에서 `@ExceptionHandler`를 통해서 원하는 Exception를 분류해서 처리할 수 있습니다.
- `@ControllerAdvice`는 `@Controller`가 사용된 모든 클래스에서 발생하는 모든 Exception을 공통적으로 처리할 수 있습니다.
```java
@ControllerAdvice
public class ExceptionAdvice {
    @ExceptionHandler({RuntimeException.class, Exception.class})
    protected CommonResponseEntity handleRuntimeException(RuntimeException e) {
        return CommonResponseEntity.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .code("INTERNAL_SERVER_ERROR")
                .message(e.getMessage())
                .build();
    }
}
```
```java
@RestControllerAdvice
public class ExceptionAdvisor {
  
  @ExceptionHandler(Exception.class)
  public ResponseEntity<CommonResponseVO> customExceptionHandler(Exception exception) {
    log.error(exception.getMessage(), exception);
    return ResponseUtil.createFailResponse(StatusCodeMessageConstant.BAD_REQUEST, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(ApplicationException.class)
  public ResponseEntity<CommonResponseVO> applicationExceptionHandler(ApplicationException applicationException){
    log.error(applicationException.getMessage(), applicationException);
    return ResponseUtil.createFailResponse(applicationException.getStatusCodeMessage());
  }

  @ExceptionHandler({
          ConstraintViolationException.class
          , MethodArgumentTypeMismatchException.class
          , HttpMessageNotReadableException.class
  })
  public ResponseEntity<CommonResponseVO> invalidParameterValueExceptionHandler(Exception exception) {
    log.error(exception.getMessage(), exception);
    return ResponseUtil.createFailResponse(StatusCodeMessageConstant.PARAMETER_VALUE_ERROR, HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler({
          MissingServletRequestParameterException.class
          , MethodArgumentNotValidException.class
          , BindException.class
  })
  public ResponseEntity<CommonResponseVO> invalidMandatoryParameterExceptionHandler(Exception exception) {
    log.error(exception.getMessage(), exception);
    return ResponseUtil.createFailResponse(StatusCodeMessageConstant.MANDATORY_PARAMETER_ERROR, HttpStatus.BAD_REQUEST);
  }
}
```

## ResponseStatusException
직접 사용해보진 않았지만 Spring 5 버전부터는 ResponseStatusException 클래스를 사용할 수 있다고 합니다.

간단하게 HTTP 응답 코드와 메세지를 만들어서 사용할 수 있는 장점이 있지만 공통 처리를 위해 사용하기에는 적합하지 않다고 합니다.

```java
@GetMapping("/user/{id}")
public ResponseEntity method(@PathVariable Long id){
    try{
        return service.metohd(id);
    }catch(Exception e)
        throw new ResponseStatusException(HttpStatus.NOT_FOUND,"error");
    }
}
```

## ResponseUtility
RESTful API는 ResponseEntity를 갖으며 여러 HTTP 상태 코드와 메세지를 만들기 위해서 ResponseUtility를 사용하기도 했습니다.

`@UtilityClass`를 사용하면 final 클래스로 생성하고 private 생성자를 자동으로 생성해주며 정적 메서드로 인스턴스 생성 없이 사용할 수 있습니다.

```java
@UtilityClass
public class ResponseUtility를 {
    
    public ResponseEntity<CommonResponseVO> createSuccessResponse() {
        return this.createSuccessResponse(null, HttpStatus.OK);
    }
    
    public ResponseEntity<CommonResponseVO> createSuccessResponse(CommonResponseVO commonResponseVO) {
        return this.createSuccessResponse(commonResponseVO, HttpStatus.OK);
    }
    
    public ResponseUtility<CommonResponseVO> createSuccessResponse(HttpStatus httpStatus) {
        return this.createSuccessResponse(null, httpStatus);
    }

    public ResponseEntity<CommonResponseVO> createSuccessResponse(CommonResponseVO commonResponseVO, HttpStatus httpStatus) {
        return ResponseEntity.status(httpStatus).body(commonResponseVO);
    }
}
```

---

Request에 enum 클래스 처리하기
===
우리는 특정 타입에 대해서 범위를 지정하고 검증하고 싶을 때 enum 클래스를 자주 사용한다. SpringBoot에선 주로 Request로 들어오는 값 중에서 특정 필드의 값이 원하는 범위에 속해야할 때 이를 처리하기 위해서 사용했다.

예를들어 아래와 같은 RequestVO에 있는 필드 중에서 orderType은 `[ORDER, CANCEL, REFUND]` 중 하나만 올 수 있어야 한다고 가정했을 때 if문을 사용해서 검증할 수 있겠지만 이는 많은 중복코드를 발생시킬 수 있고 가독성을 떨어트리게 된다. 
```java
public class OrderStatusRequestVO {
    private String orderType;
    private String item;
    private int price;
    private String address;
}
```
```java
final String orderType = requestVO.getOrderType();
if ("ORDER".equalsIgnoreCase(orderType) || "CANCEL".equalsIgnoreCase(orderType) || "REFUND".equalsIgnoreCase(orderType)) { ... }
```

## @Enum과 EnumValidator 
위 문제를 해결하기 위해서 처음으로 접했던 방법은 `@Enum`과 `EnumValidator`을 구현해서 아래와 같은 방법으로 사용했었다.
```java
@Target({ElementType.TYPE_USE, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EnumValidator.class)
public @interface Enum {
    String message() default "유효하지 않은 enum 타입입니다.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    Class<? extends java.lang.Enum<?>> enumClass();

    boolean ignoreCase() default false;

    String[] excludeEnumType() default {};
}
```
```java
public class EnumValidator implements ConstraintValidator<EnumValid, String> {

    private List<String> enumValues;
    private EnumValid annotation;

    @Override
    public void initialize(EnumValid enumValid) {
        this.annotation = enumValid;
        List<String> excludeEnumType =
                Arrays.stream(this.annotation.excludeEnumType()).collect(Collectors.toList());

        enumValues = Arrays.stream(this.annotation.enumClass().getEnumConstants())
                .map(constants ->
                        this.annotation.ignoreCase() ? constants.name().toUpperCase() : constants.name())
                .filter(constants -> !excludeEnumType.contains(constants))
                .collect(Collectors.toList());
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        boolean retVal = false;

        if (value != null && !value.isEmpty()) {
            retVal = enumValues.contains(this.annotation.ignoreCase() ? value.toUpperCase() : value);
        } else {
            retVal = true;
        }

        return retVal;
    }
}
```
```java
public class OrderStatusRequestVO {
    @Enum(enumClass = OrderType.class)
    private String orderType;
    private String item;
    private int price;
    private String address;
}
```

## Converter
@Enum 어노테이션으로는 아래와 같은 @PathVariable로 enum 클래스를 바로 사용하는 경우를 처리하지 못했고 이를 처리하기 위해서 각 enum 클래스마다 Converter 만들어서 사용했었다.
```java
@Component
public class ContentsTypeConverter implements Converter<String, ContentsType> {
    @Override
    public ContentsType convert(String value) {
        return ContentsType.valueOf(value.toUpperCase());
    }
}
```
```java
@RestController
@RequestMapping("/order")
public class OrderController {
    @GetMapping("/{orderType}")
    public ResponseEntity<String> getFeedsByContentsType(@PathVariable OrderType orderType) {
        return ResponseEntity.ok();
    }
}
```

EnumValidator와 Converter는 몇가지 문제점이 있다.
- 사용하는 enum 클래스만큼 Converter를 각각 만들어줘야하는 점
- VO에 각 필드에 @Enum 어노테이션을 넣어줘야 하는 점
- `Code 값을 갖는 Enum 형태`를 호환하지 못하는 점

## Code 값을 갖는 Enum 형태는?
Code enum은 주로 공통코드를 관리하거나 한 변수가 name, code 혹은 다른 값들을 동시에 갖을 때 주로 사용했다. 

프로젝트에서 공통 코드는 데이터베이스에서 테이블로 관리하고 Back-end에서 공통 코드 조회 API를 만들어서 Front-end에서 사용할 수 있도록 했었다. 
공통 코드는 주로 name과 code값이 한 쌍으로 관리되며 Front-end에선 주로 name 값을 화면에 보여주고 내부적으론 code 값을 사용했다. 
Back-end에서는 공통 코드를 사용할 때마다 데이터베이스를 조회할 수 없었기 때문에 `OrderTypeCode`와 같은 Code enum 클래스를 만들어서 사용했었다.
```java
// 일반적인 Enum 형태
public enum OrderType {
  ORDER,
  CANCEL,
  REFUND
} 
```
```java
// Code Enum 형태
public enum OrderTypeCode {
  ORDER("001"),
  CANCEL("002"),
  REFUND("003");

  private String code;

  FeedContentsTypeCode(String code){
    this.code = code;
  }
  
  public String code() {
    return code;
  }
}
```

Front-end에서 화면에는 name 값`[ORDER, CANCEL, REFUND]`을 노출하고, Back-end와 통신할 때는 code 값`[001, 002, 003]`을 사용했다.

Back-end 코드 내부에서 `OrderTypeCode.001.equals(contents)`와 같이 사용한다면 `001`이 어떤 값을 의미하는지 한 눈에 알 수 없기 때문에 Code enum 형태의 enum을 만들어서 사용했다.

EnumValidator와 Converter에는 장단점이 존재한다.
- 장점
  - NULL 값을 호환한다는 점
  - excludeEnumType, message 등 좀 더 유연한 비교가 가능하다는 점
- 단점
  - 사용하는 enum 클래스만큼 Converter를 각각 만들어줘야하는 점
  - VO에 각 필드에 @Enum 어노테이션을 넣어줘야 하는 점
  - `Code 값을 갖는 enum 형태`를 호환하지 못하는 점

```java
// 여러개의 값을 갖는 Code Enum
public enum CommonCode {
    ORDER_TYPE("001", "", "", "", ""),
    MODEL_TYPE("002", "", "", "", ""),
    POS_TYPE("003", "", "", "", ""),
    VAN_TYPE("004", "", "", "", ""),
    CARD_TYPE("005", "", "", "", "")
    ;

    private final String code;
    private final String referenceField1;
    private final String referenceField2;
    private final String referenceField3;
    private final String referenceField4;
    
    CommonCode(String code, String referenceField1, String referenceField2, String referenceField3, String referenceField4) {
        this.code = code;
        this.referenceField1 = referenceField1;
        this.referenceField2 = referenceField2;
        this.referenceField3 = referenceField3;
        this.referenceField4 = referenceField4;
    }
    
    public String code() {
        return code;
    }
    public String getReferenceField1() {
        return referenceField1;
    }
    public String getReferenceField2() {
        return referenceField2;
    }
    public String getReferenceField3() {
        return referenceField3;
    }
    public String getReferenceField4() {
        return referenceField4;
    }
}
```

## ConverterFactory
첫번째와 두번째 단점을 해결하기 위해서 ConverterFactory를 만들어서 사용할 수 있다. 여러 VO에 @Enum 어노테이션을 적어줄 필요도 없고 각 enum 클래스마다 Converter를 만들어줄 필요도 없다.
```java
public class EnumConverterFactory implements ConverterFactory<String, Enum<?>> {

    @Override
    public <T extends Enum<?>> Converter<String, T> getConverter(Class<T> targetType) {
        return new StringToEnumConverter(getEnumType(targetType));
    }

    private static class StringToEnumConverter<T extends Enum<?>> implements Converter<String, T> {

        private final Class<T> enumType;

        public StringToEnumConverter(Class<T> enumType) {
            this.enumType = enumType;
        }

        @Override
        public T convert(String source) {
            if (source.isEmpty()) {
                return null;
            }
            return (T) Enum.valueOf(this.enumType, source.trim().toUpperCase());
        }
    }
    
    private static Class<?> getEnumType(Class targetType) {
        Class<?> enumType = targetType;
        while (enumType != null && !enumType.isEnum()) {
            enumType = enumType.getSuperclass();
        }
        Assert.notNull(enumType, "The target type " + targetType.getName() + " does not refer to an enum");
        return enumType;
    }
}
```
```java
public class OrderStatusRequestVO {
  private OrderType orderType;
  private String item;
  private int price;
  private String address;
}
```

## Converter 한 번에 등록하기
위에서 Converter를 설명할 때 enum 클래스 개수만큼 등록해야한다고 설명했었지만 아래 코드를 보면 여러 Formatter, Converter, ConverterFactory를 한 번에 등록할 수 있다.
```java
@Configuration
public class FormatterConfig implements WebMvcConfigurer {
    @Autowired
    private Formatter<?>[] formatters;
    
    @Autowired
    private Converter<?, ?>[] converters;
    
    @Autowired
    private ConverterFactory<?, ?>[] converterFactories;
    
    @Override
    public void addFormatters(FormatterRegistry formatterRegistry) {
        if(!ObjectUtils.isEmpty(formatters)){
            formatters.forEach(formatterRegistry::addFormatter);
        }

        if(!ObjectUtils.isEmpty(converters)){
            converters.forEach(formatterRegistry::addFormatter);
        }

        if(!ObjectUtils.isEmpty(converterFactories)){
            converterFactories.forEach(formatterRegistry::addConverterFactory);
        }
    }
}
```

---

## Jackson의 Deserializer
Jackson의 Serializer와 Deserialzer를 사용하면 기존에 있던 단점을 모두 보완하고 장점도 모두 사용할 수 있다.
Jackson의 Serializer와 Deserializer는 Request로 들어오는 VO 객체, Response로 반환하는 VO 객체 내에 있는 필드의 특정 타입에 대한 공통 처리를 정의하거나 Jackson 내에 정의되어 있는 특정 함수들을 오버라이딩해서 커스터마이징이 가능했다.

예를들어 날짜 타입의 형식을 지정할 때 DateUtil에 함수를 만들어서 Service Layer나 toVO 함수 내에서 지정할 필요 없이 LocalDate, LocalTime, LocalDateTime 타입에 대한 형식을 지정해주거나 Enum 타입의 값이 들어왔을 때 toUpperCase() 적용한 후에 비교를 할 수 있게 할 수 있다.
```java
public class JacksonMappingBuilderConfig implements Jackson2ObjectMapperBuilderCustomizer {
  @Override
  public void customize(Jackson2ObjectMapperBuilder jacksonObjectMapperBuilder) {
    jacksonObjectMapperBuilder
            .featuresToEnable(MapperFeature.ACCEPT_CASE_INSENSITIVE_ENUMS)
            .featuresToDisable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            .timeZone(TimeZone.getDefault())
            .locale(Locale.getDefault())
            .simpleDateFormat("yyyy-MM-dd HH:mm:ss")
            .serializers(
                    new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyyMMdd")),
                    new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                    new LocalTimeSerializer(DateTimeFormatter.ofPattern("HH:mm:ss"))
            )
            .deserializers(
                    new LocalDateDeserializer(DateTimeFormatter.ofPattern("yyyyMMdd")),
                    new LocalDateTimeDeserializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                    new LocalTimeDeserializer(DateTimeFormatter.ofPattern("HH:mm:ss")),
                    new EnumDeserializer(Enum.class)
            );
  }
}
```

Code enum 형태를 처리하기 위해 만든 `EnumDeserializer`는 enum 클래스를 처리하는 함수`public Enum<? extends Enum> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)`를 오버라이딩해서 커스터마이징 했다.
타입이 enum 클래스일 경우 enum 클래스 내에 value 함수가 있는지 확인하고 value 함수가 있으면 value 함수의 결과인 코드 값으로 매칭되는 값이 있는지 확인하도록 했다. https://d2.naver.com/helloworld/0473330 를 참고했다.
```java
public class EnumDeserializer extends StdDeserializer<Enum <? extends Enum>> implements ContextualDeserializer {

    protected EnumDeserializer(Class<?> vc) {
        super(vc);
    }

    @SuppressWarnings("unchecked")
    @Override
    public Enum<? extends Enum> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        JsonNode jsonNode = jsonParser.getCodec().readTree(jsonParser);
        String input = jsonNode.asText().trim().toUpperCase();
        Class<? extends Enum> enumType = (Class<? extends Enum>) this._valueClass;

        if(ObjectUtils.isEmpty(input))
            return null;

        boolean isPlainEnum = EnumUtils.isValidEnum(enumType, input);
        if(isPlainEnum){
            return Enum.valueOf(enumType, input);
        } else {
            boolean isEnumCode = Arrays.stream(enumType.getMethods()).anyMatch(method -> "value".equals(method.getName()));

            if(isEnumCode){
                Enum mathcEnum = null;
                String enumValue;
                for(Enum constant : enumType.getEnumConstants()){
                    try {
                        enumValue = (String) constant.getClass().getMethod("value").invoke(constant);
                        if(enumValue.equals(input.trim().toUpperCase())){
                            mathcEnum = constant;
                            break;
                        }
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }

                if(mathcEnum == null)
                    throw new IllegalArgumentException("No enum constant " + enumType.getCanonicalName() + "." +input);

                return Enum.valueOf(enumType,mathcEnum.name());
            } else {
                throw new IllegalArgumentException("No enum constant " + enumType.getCanonicalName() + "." +input);
            }
        }
    }

    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext context, BeanProperty beanProperty) {
        return new EnumDeserializer(beanProperty.getType().getRawClass());
    }
}
```

즉, FeedContentsTypeCode에서 기존에 비교하던 범위가 `[NORMAL, VOTE, SHARE, VIDEO]` 였다면 `[NORMAL, VOTE, SHARE, VIDEO, 001, 002, 003, 004]`로 코드 값까지 비교할 수 있도록 했다. VO 내에 타입에 enum 클래스를 바로 사용하면 되기 때문에 필드마다 어노테이션을 사용해야 하는 불편함도 없어졌다.
```java
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class FeedGetVO extends PagingVO{
  private FeedContentsTypeCode feedContentsTypeCode;
}
```

@PathVariable는 Converter를 타기 때문에 공통 처리 Converter도 정의해줬다.
```java
@Component
public class EumConverterFactory implements ConverterFactory<String, Enum> {

  @Override
  public <T extends Enum> Converter<String, T> getConverter(Class<T> enumType) {
    return new EnumConverter(getEnumType(enumType));
  }

  private class EnumConverter<T extends Enum> implements Converter<String, T> {

    private final Class<T> enumType;

    private EnumConverter(Class<T> enumType) {
      this.enumType = enumType;
    }

    @Override
    public T convert(String input) {
      if(input.isEmpty() || input == null)
        return null;

      input = input.trim().toUpperCase();

      boolean isPlainEnum = EnumUtils.isValidEnum(enumType, input);
      if(isPlainEnum){
        return (T) Enum.valueOf(this.enumType, input);
      } else {
        boolean isEnumCode = Arrays.stream(enumType.getMethods()).anyMatch(method -> "value".equals(method.getName()));

        if(isEnumCode){
          Enum mathcEnum = null;
          String enumValue;
          for(Enum constant : enumType.getEnumConstants()){
            try {
              enumValue = (String) constant.getClass().getMethod("value").invoke(constant);
              if(enumValue.equals(input.trim().toUpperCase())){
                mathcEnum = constant;
                break;
              }
            } catch (Exception e) {
              throw new RuntimeException(e);
            }
          }

          if(mathcEnum == null)
            throw new IllegalArgumentException("No enum constant " + enumType.getCanonicalName() + "." +input);

          return (T) Enum.valueOf(enumType,mathcEnum.name());
        } else {
          throw new IllegalArgumentException("No enum constant " + enumType.getCanonicalName() + "." +input);
        }
      }
    }
  }

  private Class<?> getEnumType(Class classType) {
    Class<?> enumType = classType;
    while(enumType != null && !enumType.isEnum()){
      enumType = enumType.getSuperclass();
    }
    if(enumType == null) {
      throw new IllegalArgumentException("This type " + enumType.getName() + " is not an enum type.");
    }
    return enumType;
  }
}
```

---

Jackson
===

## Serializer/Deserializer
JSON 데이터를 Java 객체로 변환하기 (Deserialization)
Java 객체를 JSON으로 직렬화하기 (Serialization)

## HandlerMethodArgumentResolver
[Spring MVC](https://github.com/justdoanything/self-study/blob/main/10%20Spring.md#spring-mvc)를 정리한 자료를 보면 Spring이 어떻게 MVC 패턴으로 동작하고 변해왔는지 순서대로 알 수 있다. 이러한 변화과정은 개발자가 Spring을 사용할 때 좀 더 편하고 빠르게 개발할 수 있게 해준다. SpringBoot가 되면서 좀 더 빠르고 가벼워졌으며 개발자가 Spring을 사용할 때 반드시 해줘야했던 configure나 의존성 설정 등이 없어졌다.
예전에 Spring을 사용할 땐 tomcat을 따로 설치하고 Spring과 설정해줘야 동작했었는데 SpringBoot에선 embedded tomcat을 사용해서 별도의 설치나 설정 없이 바로 웹서버가 동작하고 있다.

이렇듯 대부분의 공통처리를 Spring에서 해주지만 상황에 따라 개발자가 customize해서 사용할 수 있게 동작한다. 예를들어 Resolver를 따로 정의하지 않아도 기본적인 동작들이 수행되고 아래에서 추가할 특정 타입, 상황에 따라 개발자가 원하는 로직을 사용하고 싶다면 Spring Handler를 상속받아 특정 함수를 Override해서 사용할 수 있다.

주로 프로젝트에선 Request/Response 공통 처리를 위해 Jackson의 Serializer/Deserializer를 정의해서 사용하곤 한다. 로그인이나 인증을 처리하기 위해서 특정 DTO가 들어왔을 때 동작할 함수를 따로 정의하거나 Enum 타입의 Request가 왔을 때 validation하는 동작을 따로 정의하거나 응답하는 객체의 Date 타입에 따라 특정 format을 지정하는 등 공통처리를 하고 싶을 때 많이 사용했었다.

특히 여러 사람이 동시에 개발하는 경우 공통 기능을 함수로 만들거나 어노테이션으로 만든다면 누락되거나 사용성에 문제가 있을 수 있기 때문에 Resolver로 처리하는 것이 안정적이다.

```java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface RequestParameter {
    boolean required() default true;
}

```
```java
@Component
public class RequestParameterResolver implements HandlerMethodArgumentResolver {
    private final ObjectMapper c;
    
    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(RequestParameter.class);
    }
    
    @Override
    public Object resolveArgument(
            MethodParameter methodParameter, 
            ModelAndViewContainer modelAndViewContainer, 
            NativeWebRequest nativeWebRequest, 
            WebDataBinderFactory webDataBinderFactory) throws Exception {
        Map<String, String> requestParameters =
                nativeWebRequest.getParameterMap()
                        .entrySet()
                        .stream()
                        .collect(Collectors.toMap(Map.Entry::getKey, e -> e.getValue()[0]));
        
        if(requestParameters.size() == 0) {
            RequestParameter requestParameter = methodParameter.getParameterAnnotation(RequestParameter.class);
            if(requestParameter != null && requestParameter.required()) {
                HttpServletRequest httpServletRequest = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
                ServletServerHttpRequest servletServerHttpRequest = new ServletServerHttpRequest(httpServletRequest);
                throw new MissingServletRequestParameterException(
                        "Required request parameter is empty : " + methodParameter.getExecutable().toGenericString(),
                        servletServerHttpRequest);
            } else {
                // 검증 로직 추가
            }
        }

        Object resolver = mapper.convertValue(requestParameters, methodParameter.getParameterType());
        
        if(methodParameter.hasParameterAnnotation(Valid.class)) {
            String parameterName = Conventions.getVariableNameForParameter(methodParameter);
            WebDataBinder binder = webDataBinderFactory.createBinder(nativeWebRequest, resolver, parameterName);
            if(binder.getTarget() != null) {
                binder.validate();
                BindingResult bindingResult = binder.getBindingResult();
                if(bindingResult.hasError()) {
                    throw new MethodArgumentNotValidException(methodParameter, bindingResult);
                }
            }
        }
        
        return resolver;
    }
}

```

## ObjectMapper
### JSON 요청을 Java 객체로 역직렬화
```java
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class MyController {
    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping("/createUser")
    public ResponseEntity<User> createUser(@RequestBody String json) throws IOException {
        User user = objectMapper.readValue(json, User.class);
        // user 객체를 사용하여 작업을 수행
        return ResponseEntity.ok(user);
    }
}
```

### Java 객체를 JSON 응답으로 직렬화
```java
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class MyController {
    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping("/getUser")
    public ResponseEntity<String> getUser() throws JsonProcessingException {
        User user = new User("John", "Doe");
        String json = objectMapper.writeValueAsString(user);
        return ResponseEntity.ok(json);
    }
}
```

---

Aspect
===
LogAspect

Aspect
```java
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ProhibitedWordChecker {
    String checkerType();
}
```
```java
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ProhibitedWordField {}
```
```java
@Aspect
@Component
@RequiredArgsConstructor
public class ProhibitedWordAspect {
    private final ProhibitedWordService prohibitedWordService;
    
    List<Object> fields = new ArrayList<>();
    
    @Around("@annotation(prohibitedWordChecker)")
    public Object prohibitedWordCheck(
            ProceedingJoinPoint joinPoint,
            ProhibitedWordChecker prohibitedWordChecker) throws Throwable {
        for(Object args: joinPoint.getArgs()) {
            if(String.class.equals(args.getClass()) && Arrays.stream(
                    ((MethodSignature) joinPoint.getSignature())
                            .getMethod()
                            .getParameters())
                    .filter(
                            parameter -> parameter.isAnnotationPresent(ProhibitedWordField.class))
                    .count() == 1) {
                fields.add(field.get(args));
            }else {
                for (Field field: args.getClass().getDeclaredFields()) {
                    if(field.isAnnotationPresent(ProhibitedWordField.class)) {
                        field.setAccessible(true);
                        fields.add(field.get(args));
                    }
                }
            }
        }
        
        ProhibitedWordCheckerResponseVO prohibitedWordCheckerResponseVO =
                prohibitedWordService.doProhibitedWordVerification(
                        prohibitedWordChecker.checkerType(), fields);
        
        // 성공, 실패에 따른 Success Response 또는 Exception 반환
        if(prohibitedWordCheckerResponseVO.isProhibitedWordExist()){
            return ResponseUtil.createFailResponse(prohibitedWordCheckerResponseVO);
        } else {
            return joinPoint.proceed();
        }
    }
}
```
```java
@RequiredArgsConstructor
@Service
@Validated
public class prohibitedWordService {
    private final prohibitedWordRepository prohibitedWordRepository;
    
    public ProhibitedWordCheckerResponseVO doProhibitedWordVerification(String checkerType, List<Object> fields) {
        // 금칙어 대상 가져오기
        List<String> prohibitedWords = prohibitedWordRepository.findAllByCheckerType(checkerType);
        
        // 금칙어 검증
        Trie prohibitedWordTrie = Trie.builder().addKeywords(prohibitedWords).build();
        List<String> verifiedProhibitedWords = fields.stream()
                .flatMap(
                        field -> prohibitedWordTrie.parseText(field.toString()).stream().map(Emit::getKeyword))
                .distinct()
                .toList();
        
        // 응답 생성
        new ProhibitedWordCheckerResponseVO.builder()
                .prohibitedWordExist(!verifiedProhibitedWords.isEmpty())
                .prohibitedWords(verifiedProhibitedWords)
                .build();
    }
}
```

- Trie ?


---

자주 쓰이는 Controller Annotation
===
```java
@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
@Api(tags = "Order", description = "주문 API")
public class VanController {
    
}
```
- ## Class
  - ### @RestController
  - ### @RequiredArgsConstructor
  - ### @RequestMapping
  - ### @RequiredArgsConstructor
  - ### @Api, @Tag
    - @Tag(name = "Order", description = "주문 API")
    - @Operation(summary = "주문 조회", description = "주문 조회 API")
- ## Method
  - ### @ApiOperation, @Operation
  - ### @RequestMapping
    - #### @GetMapping
    - #### @PostMapping
    - #### @PutMapping
    - #### @DeleteMapping
    - #### @PatchMapping
    - #### path, produces, consumes
- ## Parameter
  - ### @PathVariable
    - Method : `GET`
    - URL 경로에서 변수를 추출하는 데 사용됩니다. 주로 RESTful 웹 서비스에서 경로 매개변수를 추출하는 데 쓰입니다.
    - URL 경로에서 특정 부분을 변수로 설정하고 사용하려면 @PathVariable을 사용합니다. 예를 들어, /products/{id}와 같은 경로에서 id를 추출할 때 사용합니다.
  - ### @RequestParam
    - Method : `GET`
    - 요청의 쿼리 매개변수(파라미터)를 추출하는 데 사용됩니다. URL의 ? 뒤에 오는 매개변수를 읽어옵니다.
    - 쿼리 매개변수를 추출할 때 사용합니다. 예를 들어, /products?id=123와 같이 URL에서 id를 추출할 때 사용합니다.
  - ### @RequestBody
    - Method : `POST`, `PUT`, `PATCH`
    - HTTP 요청의 본문(body) 부분을 특정 자바 객체로 매핑하도록 지시합니다.
    - 주로 POST나 PUT 요청과 함께 사용되며, 클라이언트가 JSON 또는 XML 형식으로 데이터를 전송할 때 사용됩니다.
    - 예를 들어, 클라이언트가 JSON으로 데이터를 보내고 이를 자바 객체로 변환하려면 @RequestBody를 사용합니다.
  - ### @Valid

---

자주 쓰이는 Service Annotation
===
- ## Class
  - ### @Service
  - ### @RequiredArgsConstructor
  - ### @Validated
- ## Method
  - ### @Transactional
    - #### readOnly
    - #### rollbackFor
    - #### noRollbackFor
    - #### propagation
    - #### isolation
    - #### timeout

---

자주 쓰이는 Model Annotation
===
- ## Class
  - ### @AllArgsConstrutor(access = AccessLevel.PRIVATE)
  - ### @NoArgsConstrutor
  - ### @Data
  - ### @Builder, @SuperBuilder
- ## Method

---

@Transactional
===

---

Redis
===
- docker exec -it springboot-redis-1 redis-cli
  set testkey testvalue

- 삭제
  - del testkey
  - 전체 : flushall

- 조회
  - get testkey
  - 전체 : keys *


- 만료일 조회 : ttl [key]
  - ttl testkey

---

Flyway
===

---

package prj.yong.modern.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
* @Reference : https://jsy1110.github.io/2022/enum-class-validation/ @Description JAVA에서 기본적으로 제공하는
* 원시타입의 경우 @NotBlacn, @NotNull 등 제공되는 annotation으로 validation을 할 수 있다. 개발자가 원하는 필드의 원하는 validation을
* 걸고 싶다면 따로 정의해줘야 한다.
*
* <p>예를들어, private Gender gender; 필드가 있을 때 Gender는 MEN, WOMEN만 가능하다고 해보자. public enum Gender { MEN,
* WOMEN } 으로 정의하고 @EnumValid annotaion을 적용해서 원하는 validation을 걸어줄 수 있다. @Useage @EnumValid(enumClass
* = Gender.class) private Gender gender; @EnumValid(enumClass = EnumConstants.StoreType.class)
* private String storeType;
  */
  public class SpringValidator implements ConstraintValidator<EnumValid, String> {
  /**
  * @Description 검증할 필드의 Type을 ConstraintValidator의 파라미터로 넘겨준다. String 타입의 필드를 검증하려면
  * ConstraintValidator<EnumValid, String>가 되어야 하고 enum 타입의 필드를 검증하려면
  * ConstraintValidator<EnumValid, Enum>가 되야 한다.
    */
    private EnumValid annotation;

  /** @param enumValid annotation instance for a given constraint declaration */
  @Override
  public void initialize(EnumValid enumValid) {}

  /**
  * 실제 Validation 에 사용할 코드
  *
  * @param value object to validate
  * @param context context in which the constraint is evaluated
  * @return
    */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
    return false;
    }
    }

---

package prj.yong.modern.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
* @Reference : https://engkimbs.tistory.com/m/746 @Description AOP : Aspect Oriented Programming 관점
* 지향 프로그래밍으로 어떤 로직을 기준으로 핵심적인 관점, 부가적인 괌점으로 나누어서 보고 그 관점을 기준으로 모듈화한다. 흩어진 관심사(Crosscutting
* Concerns)를 Aspect로 모듈화하고 핵심적인 비지니스에선 분리하여 재사용하겠다는 것이다.
  */
  @Component
  @Aspect
  public class SpringAspect {
  /**
  * @Aspect : 흩어진 관심사를 모듈화한 결과 @Target : @Aspect 를 사용하는 곳 @Advice : 부가 기능이 실질적으로 어떻게 동작하는지 명시해놓은
  * 구현체 @JoinPoint : @Advice 가 적용될 위치, 끼어들 수 있는 지점으로 Method 진입 시잠, 생성자 호출 시점 등 특정한 시점에 적용할 수
  * 있다. @PointCut : @JoinPoint 의 상세한 스펙을 정의한 것으로 "메서드 진입 시점에 호출할 것" 과 같이 구체적인 @Advice 실행 시점을 정할 수
  * 있다.
    */
    @Before( // Target 메서드를 감싸서 특정 @Advice 를 실행하기 위한 Annotation
    "execution(* modern.service..*(..))" // 특정 패키지 아래에 있는 모든 함수에 @Aspect 를 적용
    )
    public void beforeMethod(JoinPoint joinPoint) {
    System.out.println("Before Method");
    }

  @After("execution(* modern.service..*(..))")
  public void afterMethod(JoinPoint joinPoint) {
  System.out.println("After Method");
  }
  }

---

    /**
     * @Description
     * @Reference https://johnmarc.tistory.com/152
     *  Enum 객체의 비교에서 == 와 .equals()의 차이
     *  == 은 주소값을 비교하기 때문에 컴파일 시 타입 체크를 하고 다른 타입일 경우 컴파일 에러가 발생한다.
     *  하지만 NPE는 검증하지 않는다.
     *
     *  .equals() 는 Object 값을 비교할 때 내부적으로 == 을 사용하고 컴파일 단계에서 타입 체크를 하지 않는다.
     *  런타임에서 NPE는 잡을 수 있다.
     *
     */

---

package prj.yong.modern.aop;

import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
@Aspect
@Slf4j
public class LogAspect {
private static final String CLASS_LOG_FORMAT = "Class Name : [";
private static final String METHOD_LOG_FORMAT = "Method Name : [";

    @Before("(execution(* modern.exception..*(..))"
            + " || execution(* modern.interceptor..*(..))"
            + " || execution(* modern.repository..*(..)))"
            + " && !@annotation(NoLoggingAspect)")
    public void beforeMethod(JoinPoint joinPointØ) {
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        StringBuilder logStringBuilder = new StringBuilder();
        String logInfo = logStringBuilder.append("원하는 LOG 내용").toString();

        log.info(logInfo);
    }

    @AfterReturning(
            pointcut = "(execution(* modern.exception..*(..))"
                    + " || execution(* modern.interceptor..*(..))"
                    + " || execution(* modern.repository..*(..)))"
                    + " && !@annotation(NoLoggingAspect)",
            returning = "result")
    public void afterMethod(JoinPoint joinPoint, Object result) {
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        StringBuilder logStringBuilder = new StringBuilder();
        String logInfo = logStringBuilder.append("원하는 LOG 내용").toString();

        log.info(logInfo);
    }
}

---

package prj.yong.modern.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NoLoggingAspect {}

---

package prj.yong.modern.aop;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import prj.yong.modern.exception.CustomException;

@Aspect
@Component
public class ResponseAspect {
@Autowired
private Validator validator;

    @AfterReturning(pointcut = "execution(* modern.controller..*(..))", returning = "response")
    public void validateResponse(JoinPoint joinPoint, Object response) throws CustomException {
        validateResponse(response);
    }

    private void validateResponse(Object object) throws CustomException {

        Set<ConstraintViolation<Object>> validationResults = validator.validate(object);

        if (validationResults.size() > 0) {

            StringBuffer sb = new StringBuffer();

            for (ConstraintViolation<Object> error : validationResults) {
                sb.append(error.getPropertyPath())
                        .append(" - ")
                        .append(error.getMessage())
                        .append("\n");
            }

            String msg = sb.toString();
            throw new CustomException(msg);
        }
    }
}

---

package prj.yong.modern.interceptor;

import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.jwk.source.JWKSource;
import com.nimbusds.jose.jwk.source.RemoteJWKSet;
import com.nimbusds.jose.proc.JWSKeySelector;
import com.nimbusds.jose.proc.JWSVerificationKeySelector;
import com.nimbusds.jose.util.DefaultResourceRetriever;
import com.nimbusds.jose.util.ResourceRetriever;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.proc.ConfigurableJWTProcessor;
import com.nimbusds.jwt.proc.DefaultJWTProcessor;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.ObjectUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import prj.yong.modern.constants.HttpHeaderConstant;
import prj.yong.modern.constants.HttpUrlConstant;
import prj.yong.modern.exception.CustomException;
import prj.yong.modern.model.session.SessionVO;
import prj.yong.modern.service.session.SessionService;

@Component
public class AuthenticationInterceptor implements HandlerInterceptor {

    private static final String HTTP_METHOD_OPTIONS = "OPTIONS";
    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Value("${cloud.token.connect.timeout}")
    private int TOKEN_CONNECT_TIMEOUT;

    @Value("${cloud.token.read.timeout}")
    private int TOKEN_READ_TIMEOUT;

    @Autowired
    SessionService sessionService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws CustomException {
        String sessionId = request.getHeader(HttpHeaderConstant.SESSION_ID);
        if (HTTP_METHOD_OPTIONS.equals(request.getMethod())) return true;
        else if (!ObjectUtils.isEmpty(sessionId)) {
            String authorization = request.getHeader(HttpHeaderConstant.AUTHORIZATION);

            if (ObjectUtils.isEmpty(authorization)) {
                throw new CustomException("Authorization is required");
            }

            if (Pattern.matches("^Bearer .*", authorization)) {
                authorization = authorization.replaceAll("^Bearer( )*", "");
            }

            if (!verifyToken(authorization)) {
                throw new CustomException("Unauthorized");
            }

            SessionVO sessionUser = sessionService.getSession(sessionId);
            if (sessionUser == null) {
                throw new CustomException("Session is expired");
            } else {
                //                SessionScopeUtil.setContextSession(sessionUser);
                return true;
            }
        } else if (isExcludePattern(HttpMethod.valueOf(request.getMethod()), request.getRequestURI())) {
            return true;
        } else {
            throw new CustomException("session_id is required");
        }
    }

    private boolean verifyToken(String token) {
        try {
            JWTClaimsSet jwtClaimsSet = configurableJWTProcessor().process(token, null);
            String cognitoPoolUrl = String.format("cognito url", "region", "userPoolId");
            if (!jwtClaimsSet.getIssuer().equals(cognitoPoolUrl)
                    || !jwtClaimsSet.getAudience().get(0).equals("clientId")
                    || !jwtClaimsSet.getClaim("token_use").equals("id")) {
                return false;
            }

            Date now = new Date(System.currentTimeMillis());
            if (!now.before(jwtClaimsSet.getExpirationTime())) {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private ConfigurableJWTProcessor configurableJWTProcessor() throws MalformedURLException {
        ResourceRetriever resourceRetriever = new DefaultResourceRetriever(TOKEN_CONNECT_TIMEOUT, TOKEN_READ_TIMEOUT);
        URL url = new URL(String.format("cognito url", "region", "userPoolId"));
        JWKSource jwkSource = new RemoteJWKSet(url, resourceRetriever);
        ConfigurableJWTProcessor configurableJWTProcessor = new DefaultJWTProcessor();
        JWSKeySelector jwsKeySelector = new JWSVerificationKeySelector(JWSAlgorithm.RS256, jwkSource);
        configurableJWTProcessor.setJWSKeySelector(jwsKeySelector);
        return configurableJWTProcessor;
    }

    private boolean isExcludePattern(HttpMethod httpMethod, String requestUri) {
        List<String> uriList = HttpUrlConstant.NO_AUTH_SESSION_HTTP_URI.get(httpMethod);
        for (String uri : uriList) {
            if (antPathMatcher.match(uri, requestUri)) return true;
        }
        return false;
    }
}

---

package prj.yong.modern.repository;

import java.util.concurrent.TimeUnit;
import javax.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;
import prj.yong.modern.model.session.SessionVO;

@Repository
public class SessionRepository {
@Value(value = "${spring.redis.session-ttl}")
private int redisSessionTtl;

    @Autowired
    private RedisTemplate<String, SessionVO> redisSessionTemplate;

    /**
     * @Description
     * @return
     */
    @Resource(name = "redisSessionTemplate")
    ValueOperations<String, SessionVO> valueOperations;

    public void createSession(String key, SessionVO sessionVO) {
        /**
         * @Description
         * @return
         */
        valueOperations.set(key, sessionVO);
        redisSessionTemplate.expire(key, redisSessionTtl, TimeUnit.SECONDS);
    }

    public SessionVO getSession(String key) {
        redisSessionTemplate.expire(key, redisSessionTtl, TimeUnit.SECONDS);
        return valueOperations.get(key);
    }

    public Boolean deleteSession(String key) {
        return redisSessionTemplate.delete(key);
    }
}

---

package prj.yong.modern.util;

import lombok.experimental.UtilityClass;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import prj.yong.modern.constants.SessionConstant;
import prj.yong.modern.model.session.SessionVO;

@UtilityClass
public class SessionUtil {
public static void setSessionContext(String key, Object value) {
RequestContextHolder.getRequestAttributes().setAttribute(key, value, RequestAttributes.SCOPE_REQUEST);
}

    public static Object getSessionContext(String key) {
        return RequestContextHolder.getRequestAttributes().getAttribute(key, RequestAttributes.SCOPE_REQUEST);
    }

    public static void setSession(Object value) {
        setSessionContext(SessionConstant.sessionKey, value);
    }

    public static SessionVO getSessionVO() {
        return (SessionVO) getSessionContext(SessionConstant.sessionKey);
    }
}

---
```java
package prj.yong.modern.util;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.experimental.UtilityClass;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import prj.yong.modern.constants.HttpStatusConstant;
import prj.yong.modern.model.spring.CommonResponseVO;

@UtilityClass
public class ResponseUtil {
    @JsonSerialize
    public static class EmptyJsonResponse {}

    public static ResponseEntity<CommonResponseVO> createSuccessResponse() {
        return createSuccessCommonResponseVO(HttpStatusConstant.SUCCESS, null, HttpStatus.OK);
    }

    public static ResponseEntity<CommonResponseVO> createSuccessResponse(Object data) {
        return createSuccessResponse(HttpStatusConstant.SUCCESS, data);
    }

    public static ResponseEntity<CommonResponseVO> createSuccessResponse(String status, Object data) {
        return createSuccessResponse(status, data, HttpStatus.OK);
    }

    public static ResponseEntity<CommonResponseVO> createSuccessResponse(
            String status, Object data, HttpStatus httpStatus) {
        return createSuccessCommonResponseVO(status, data, httpStatus);
    }

    private static ResponseEntity<CommonResponseVO> createSuccessCommonResponseVO(
            String status, Object data, HttpStatus httpStatus) {
        if (data == null) {
            data = new EmptyJsonResponse();
        }
        return new ResponseEntity<>(
                CommonResponseVO.builder().status(status).data(data).build(), httpStatus);
    }

    public static ResponseEntity<CommonResponseVO> createFailResponse() {
        return createFailResponse(HttpStatus.OK);
    }

    public static ResponseEntity<CommonResponseVO> createFailResponse(HttpStatus httpStatus) {
        return createFailResponse(HttpStatusConstant.FAIL, httpStatus);
    }

    public static ResponseEntity<CommonResponseVO> createFailResponse(String status) {
        return createFailResponse(status, HttpStatus.OK);
    }

    public static ResponseEntity<CommonResponseVO> createFailResponse(String status, HttpStatus httpStatus) {
        return new ResponseEntity<>(createFailCommonResponseVO(status), httpStatus);
    }

    public static ResponseEntity<CommonResponseVO> createFailResponse(
            String status, HttpHeaders httpHeaders, HttpStatus httpStatus) {
        return new ResponseEntity<>(createFailCommonResponseVO(status), httpHeaders, httpStatus);
    }

    private static CommonResponseVO createFailCommonResponseVO(String status) {
        return CommonResponseVO.builder().status(status).build();
    }
}
```

---
```java
package prj.yong.modern.constants;

import java.util.ArrayList;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import org.springframework.http.HttpMethod;

public class HttpUrlConstant {

    public static final Map<HttpMethod, List<String>> NO_AUTH_SESSION_HTTP_URI = new EnumMap<>(HttpMethod.class);

    static {
        String[] getExcludeUrl = {};

        String[] postExcludeUrl = {};

        String[] putExcludeUrl = {};

        String[] deleteExcludeUrl = {};

        NO_AUTH_SESSION_HTTP_URI.put(HttpMethod.GET, new ArrayList<>());
        NO_AUTH_SESSION_HTTP_URI.put(HttpMethod.POST, new ArrayList<>());
        NO_AUTH_SESSION_HTTP_URI.put(HttpMethod.PUT, new ArrayList<>());
        NO_AUTH_SESSION_HTTP_URI.put(HttpMethod.DELETE, new ArrayList<>());
        NO_AUTH_SESSION_HTTP_URI.put(HttpMethod.PATCH, new ArrayList<>());

        addExcludeHttpPathUrl(HttpMethod.GET, getExcludeUrl);
        addExcludeHttpPathUrl(HttpMethod.POST, postExcludeUrl);
        addExcludeHttpPathUrl(HttpMethod.PUT, putExcludeUrl);
        addExcludeHttpPathUrl(HttpMethod.DELETE, deleteExcludeUrl);
    }

    private static void addExcludeHttpPathUrl(HttpMethod httpMethod, String[] patternList) {
        List<String> urlList = NO_AUTH_SESSION_HTTP_URI.get(httpMethod);
        Collections.addAll(urlList, patternList);
    }
}
```
---
```java
package prj.yong.modern.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")
                .exposedHeaders("Content-Disposition")
                .allowCredentials(true);
    }
}
```
---
```java
package prj.yong.modern.config;

import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import prj.yong.modern.config.converter.SpringConverter;
import prj.yong.modern.interceptor.AuthenticationInterceptor;

@Configuration
@EnableWebMvc
public class InterceptorConfig implements WebMvcConfigurer {
@Autowired
private AuthenticationInterceptor authenticationInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry interceptorRegistry) {
        List<String> urlPatterns = Arrays.asList("/**");
        interceptorRegistry
                .addInterceptor(authenticationInterceptor)
                .addPathPatterns(urlPatterns)
                .excludePathPatterns("");
    }

    /**
     * @Description
     *  Controller 단에서 Param으로 enum 형태를 바로 받을 수 있다.
     *  URL의 경우엔 대부분 소문자로 되어 있고 enum은 대문자로 되어 있기 때문에 이럴 경우 Not Match Error가 발생한다.
     *  URL에서 넘어온 enum 값을 자동으로 대문자로 변환하도록 아래와 같이 Converter를 추가해줄 수 있다.
     * @param registry
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new SpringConverter());
    }
}
```
---
```java
package prj.yong.modern.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.repository.configuration.EnableRedisRepositories;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import prj.yong.modern.model.session.SessionVO;

@Configuration
@EnableRedisRepositories
public class RedisConfig {

    @Value(value = "${spring.redis.host}")
    private String redisHost;

    @Value(value = "${spring.redis.port}")
    private int redisPort;

    /**
     * @Description
     * @return
     */
    @Bean
    public LettuceConnectionFactory connectionFactory() {
        return new LettuceConnectionFactory(new RedisStandaloneConfiguration(redisHost, redisPort));
    }

    /**
     * @Description
     * @return
     */
    @Bean
    public RedisTemplate<String, SessionVO> redisSessionTemplate() {
        RedisTemplate<String, SessionVO> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(this.connectionFactory());
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(SessionVO.class));
        return redisTemplate;
    }
}
```

---

```java
package prj.yong.modern.config;

import java.nio.charset.Charset;
import java.time.Duration;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
* @Description
*
* @Reference
*   - https://www.baeldung.com/spring-rest-template-builder
      */
      @Configuration
      public class RestTemplateConfig {
      @Value(value = "${spring.rest-template.connection-timeout}")
      private int connectionTimeout;

@Value(value = "${spring.rest-template.read-timeout}")
private int readTimeout;

@Value(value = "${spring.rest-template.max-connection}")
private int maxConnection;

@Value(value = "${spring.rest-template.max-per-route}")
private int maxPerRoute;

@Bean
public RestTemplate restTemplate(final RestTemplateBuilder restTemplateBuilder) {
HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
factory.setHttpClient(HttpClientBuilder.create()
.setMaxConnTotal(maxConnection) // 최대 오픈되는 Connection 수. (연결할 유지할 최대 숫자)
.setMaxConnPerRoute(maxPerRoute) // IP, PORT 쌍에 대해서 수행할 Connection 수. (특정 경로당 최대 숫자)
.build());
return restTemplateBuilder
.requestFactory(() -> factory)
.setConnectTimeout(Duration.ofMillis(connectionTimeout)) // connection=timeout
.setReadTimeout(Duration.ofMillis(readTimeout)) // read-timeout
.additionalMessageConverters(new StringHttpMessageConverter(Charset.forName("UTF-8")))
.defaultHeader("Content-Type", "application/json")
.build();
}
}
```


Reference
===
- https://velog.io/@hsw0194/Spring-Boot에서-interface를-사용해야-할까
- https://gmoon92.github.io/spring/aop/2019/04/20/jdk-dynamic-proxy-and-cglib.html
- https://gmoon92.github.io/spring/aop/2019/01/15/aspect-oriented-programming-concept.html
- http://hayunstudy.tistory.com/53