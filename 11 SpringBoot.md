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
      * [RequestVO](#requestvo)
      * [ResponseVO](#responsevo)
      * [RequestVO와 ResponseVO의 사용 예시](#requestvo와-responsevo의-사용-예시)
      * [💡¹) toVO함수의 사용](#--tovo함수의-사용)
      * [💡²) 일반VO를 꼭 써야할까? SQL에서 RequestVO와 ResponseVO를 바로 사용한다면?](#--일반vo를-꼭-써야할까-sql에서-requestvo와-responsevo를-바로-사용한다면)
      * [📌 VO 안에서 private final 필드 사용 - 공통 코드와 enum 필드](#-vo-안에서-private-final-필드-사용---공통-코드와-enum-필드)
      * [💡³)Session 관련된 정보들은 Controller에서 넘겨줘야 할까? 아니면 Service 안에서 가져와야 할까?](#--session-관련된-정보들은-controller에서-넘겨줘야-할까-아니면-service-안에서-가져와야-할까)
      * [💡⁴) ResponseVO 조립](#--responsevo-조립)
      * [**📌 Check Point**](#-check-point)
    * [2. 테이블VO](#2-테이블vo)
      * [**📌 Check Point**](#-check-point-1)
    * [3. 일반VO](#3-일반vo)
    * [4. 정리](#4-정리)
* [Exception 공통 처리](#exception-공통-처리)
  * [@ExceptionHandler](#exceptionhandler)
  * [@Rest~의 차이](#rest--의-차이)
  * [@ControllerAdvice](#controlleradvice)
  * [CustomException](#customexception)
  * [ResponseStatusException](#responsestatusexception)
  * [ResponseUtility](#responseutility)
* [자주 쓰이는 Controller Annotation](#자주-쓰이는-controller-annotation)
* [자주 쓰이는 Service Annotation](#자주-쓰이는-service-annotation)
* [자주 쓰이는 Model Annotation](#자주-쓰이는-model-annotation)
* [Request에 enum 클래스 처리하기](#request에-enum-클래스-처리하기)
  * [테스트 환경](#테스트-환경)
  * [RequestVO에 enum 타입 바로 사용하기](#requestvo에-enum-타입-바로-사용하기)
  * [@Enum과 EnumValidator](#enum과-enumvalidator)
    * [1. 가장 먼저 `@Enum`과 `EnumValidator` 작성](#1-가장-먼저-enum-과-enumvalidator-작성)
    * [2. RequestVO에서 검사할 필드에 `@Enum`과 옵션 적용](#2-requestvo에서-검사할-필드에-enum-과-옵션-적용)
    * [3. Controller에서 `@Enum`과 `@Valid` 적용](#3-controller에서-enum-과-valid-적용)
    * [4. `@Enum`에서 만든 message를 사용하기 위해서 `ResponseVO`와 `CommonExceptionHandler` 생성](#4-enum-에서-만든-message를-사용하기-위해서-responsevo-와-commonexceptionhandler-생성)
    * [5. 결과 및 정리](#5-결과-및-정리)
  * [Converter](#converter)
    * [1. Converter 생성](#1-converter-생성)
    * [2. Converter 등록](#2-converter-등록)
    * [3. Controller에서 enum 타입 바로 사용](#3-controller에서-enum-타입-바로-사용)
    * [4. ExceptionHandler에 IllegalArgumentException 추가](#4-exceptionhandler에-illegalargumentexception-추가)
    * [5. 정리](#5-정리)
  * [Jackson의 Deserializer와 ConverterFactory](#jackson의-deserializer와-converterfactory)
    * [1. EnumDeserializer 작성](#1-enumdeserializer-작성)
    * [2. Jackson2ObjectMapperBuilderCustomizer를 상속받은 클래스 작성](#2-jackson2objectmapperbuildercustomizer를-상속받은-클래스-작성)
    * [3. ConverterFactory 작성](#3-converterfactory-작성)
    * [4. Formatter 일괄 등록](#4-formatter-일괄-등록)
    * [5. NULL 케이스를 처리하기 위한 어노테이션 작성](#5-null-케이스를-처리하기-위한-어노테이션-작성)
    * [6. 부록 : Jackson과 ObjectMapper](#6-부록--jackson과-objectmapper)
    * [7. 정리](#7-정리)
  * [마무리](#마무리)
      * [(1) EnumValidator (Test Coverage : 100%)](#-1--enumvalidator--test-coverage--100-)
      * [(2) Converter (Test Coverage : 90%)](#-2--converter--test-coverage--90-)
      * [(3) Jackson의 Deserializer & (4) ConverterFactory (Test Coverage : 100%)](#-3--jackson의-deserializer---4--converterfactory--test-coverage--100-)
* [Annotation 만들어서 처리하기](#annotation-만들어서-처리하기)
  * [1. @annotation을 사용한 특정 지점 지정 (JoinPoint)](#1-annotation을-사용한-특정-지점-지정--joinpoint-)
  * [2. @Around를 사용한 특정 동작 지정](#2-around를-사용한-특정-동작-지정)
  * [3. Serializer/Deserializer와 같이 사용](#3-serializerdeserializer와-같이-사용)
* [Logging](#logging)
* [Interceptor](#interceptor)
* [Redis와 Session](#redis와-session)
    * [RedisConfig](#redisconfig)
    * [SessionRepository](#sessionrepository)
    * [SessionUtil](#sessionutil)
* [@Transactional](#transactional)
* [Flyway](#flyway)
* [HandlerMethodArgumentResolver](#handlermethodargumentresolver)
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

#### RequestVO
- FE에서 넘어오는 데이터를 받기 위해서 Controller Layer에서 사용되며 RequestVO는 주로 **Session 정보(💡³)** 와 함께 Service Layer로 전달합니다.
- RequestVO는 VO의 사전적 의미처럼 toVO 같은 비지니스 로직을 담을 수 있지만 그 안에 있는 값을 변경하지 않아야 합니다. (setter 사용 ❌)
- 대신 API 하나에 RequestVO/ResponseVO는 하나씩 사용되기 때문에 필요하지 않다면 equals()와 hashCode()를 구현해주지 않아도 됩니다. (Controller의 함수 : RequestVO : ResponseVO = 1 : 1 : 1)
- RequestVO는 주로 Service 함수 초반부에 toVO 함수를 통해 일반VO를 생성하거나 Service 내에서 FE로 부터 받은 값을 사용하기 위해 getter 함수를 통해 final 변수에 할당해서 사용합니다.

#### ResponseVO
- FE로 데이터를 넘기기 위한 불변 객체로 사용되어야 하며 주로 Service의 반환 객체에 사용됩니다.
- Service 함수 마지막에 생성자 혹은 Builder를 사용해서 **ResponseVO를 생성(💡⁴)** 하고 반환합니다.
- ResponseVO를 비지니스 로직 중간에 생성하고 비지니스 로직에 따라 내부 값을 변경(setter)하는 것은 지양해야 합니다. (setter 사용 ❌)

#### RequestVO와 ResponseVO의 사용 예시
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

#### 💡¹) toVO함수의 사용
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
  
#### 💡²) 일반VO를 꼭 써야할까? SQL에서 RequestVO와 ResponseVO를 바로 사용한다면?
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

#### 📌 VO 안에서 private final 필드 사용 - 공통 코드와 enum 필드
- 주문 타입, 컨텐츠 타입 등 공통 코드를 만들어서 관리하는 방법은 여러가 입니다.
- 변경이 자주 된다면 사용할 때마다 공통 코드를 조회해서 사용하거나 주기적으로 공통 코드를 조회해서 static 변수로 사용할 수 있습니다.
- 프로젝트에선 공통 코드가 거의 변하지 않아서 enum 변수를 만들어서 공통 코드를 관리했습니다.
- enum 타입은 내부적으로 code 필드를 갖고 있어서 name과 code 값을 모두 갖고 Spring 코드 내에선 `ContentsTypeCode.FEED.code()`처럼 사용해서 코드의 가독성을 높였습니다.<br/>(`ContentsTypeCode.001.eqauls(contents)`와 같이 사용한다면 `001`이 어떤 값을 의미하는지 한 눈에 알 수 없습니다.)
- 공통 코드를 SQL에서 사용하기 위해서 VO 내부에 `private final` 변수를 만들어서 사용했었고 SQL에서도 해당 변수가 어떤 코드 값을 나타내고 있는지 한 눈에 알아볼 수 있도록 변수 이름을 정하는게 포인트였습니다. 
- SQL에서 #{contentsTypeFeedCode}을 봤을 때 ContentsType 중에 FEED에 대한 내용인 것을 바로 알 수 있습니다.
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

- 여러 개의 하위 값을 갖는 enum 타입
  ```java
  public enum CommonCode {
      ORDER_TYPE("001", "", "", "", ""),
      MODEL_TYPE("002", "", "", "", ""),
      POS_TYPE("003", "", "", "", ""),
      VAN_TYPE("004", "", "", "", ""),
      CARD_TYPE("005", "", "", "", "");
  
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
      public String referenceField1() {
          return referenceField1;
      }
      public String referenceField2() {
          return referenceField2;
      }
      public String referenceField3() {
          return referenceField3;
      }
      public String referenceField4() {
          return referenceField4;
      }
  }
  ```

#### 💡³)Session 관련된 정보들은 Controller에서 넘겨줘야 할까? 아니면 Service 안에서 가져와야 할까?
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

#### 💡⁴) ResponseVO 조립
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

#### **📌 Check Point**
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

#### **📌 Check Point**
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

## @Rest~의 차이
@Controller와@ControllerAdvice, @RestController와@RestControllerAdvice 비슷한 2개의 어노테이션은 비슷한 기능을 수행하지만 약간의 차이점이 존재합니다.

아래의 정의된 코드를 보면 `@RestController`는 `@Controller` + `@ResponseBody`이고 `@RestControllerAdvice`는 `@ControllerAdvice` + `@ResponseBody`인 것을 볼 수 있습니다.

즉, @Rest~로 시작하는 어노테이션에는 `@ResponseBody`를 포함되어 있고 이는 주로 RESTful API의 응답 데이터를 만드는데 사용된다는 것을 알 수 있습니다.

- `@Controller`
  - 주로 전통적인 Spring MVC의 Controller를 나타냅니다. 
  - View를 반환하며, 주로 JSP, Thymeleaf, Freemarker 등과 같은 템플릿 엔진을 이용하여 HTML 페이지를 생성합니다. 
  - 반환되는 데이터가 주로 모델(Model)을 통해 View에 전달되어 화면을 렌더링하는 데 사용됩니다.

- `@RestController`
  - 주로 RESTful 웹 서비스를 처리할 때 사용됩니다. 
  - `@ResponseBody` 어노테이션을 포함하고 있고 메서드 자체가 응답 데이터를 반환하며, 주로 JSON 또는 XML 형식으로 데이터를 반환합니다.

```java
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Controller { ... }

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
@Controller
@ResponseBody
public @interface RestController { ... }

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@ControllerAdvice
@ResponseBody
public @interface RestControllerAdvice { ... }
```

하지만 RESTful API에서 반드시 @Rest~를 사용해야 하는 것은 아닙니다.

예를들어 `@ExceptionHandler`는 어떠한 응답 객체도 가질 수 있기 때문에 `@ControllerAdvice`로 예외를 수집하고 RESTful API처럼 ResponseEntity 객체를 만들어서 사용할 수 있습니다.

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

## @ControllerAdvice
- Spring 3.2 버전부터 사용할 수 있습니다.
- `@ControllerAdvice`를 선언한 클래스 안에서 `@ExceptionHandler`를 통해서 원하는 Exception를 분류해서 처리할 수 있습니다.
- `@ControllerAdvice`는 `@Controller`가 사용된 모든 클래스에서 발생하는 모든 Exception을 공통적으로 처리할 수 있습니다.<br>(`@RestController`가 사용된 클래스에 대한 Exception도 처리할 수 있습니다.)

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

## CustomException
비지니스 로직이 실패한 경우 처리하는 방법으로 (1) HTTP Status는 200으로 보내고 응답 객체(CommonResponseVO)에 status=fail로 보내는 경우가 있고 (2) Service Layer 안에서 `throw new ...`를 통해 Exception을 발생시켜서 처리하는 경우가 있었습니다.

HTTP Status에 따른 처리는 Front-end에서도 공통 처리하는 경우가 대부분이라서 일정한 그라운드 룰을 정해놓고 사용했습니다.

만약 Service Layer 안에서 `throw new ...`를 통해 Excetpion을 발생시킬 경우 RuntimeException을 사용합니다.

최상위 오브젝트인 Exception을 그대로 사용하면 (`throw new Exception`) Service 함수마다 `throws Exception { ... }`을 붙여줘야 하기 때문에 불필요하게 코드가 길어지게 됩니다.

그리고 Service Layer에서 비지니스 로직 처리 중간에 임의로 발생시키는 Exception 이기 때문에 RuntimeException을 사용하는게 의미상 일치합니다.

따라서 최상단 오브젝트인 Exception을 사용하는 것은 지양하고 @ExceptionHandler와 @ControllerAdvice를 통해 Exception 종류마다 처리하는 것이 가장 좋은 방법입니다.

프로젝트에선 주로 RuntimeException을 바로 사용하지 않고 BusinessException, CustomException 클래스를 만들어서 RuntimeException을 상속했습니다.

필요한 전/후처리를 명시할 수 있고 Service Layer 안에서 사용되기 때문에 의미상으로도 BusinessException이 더 적합하다고 생각했습니다.

필요하다면 여러 개의 CustomException을 만들고 @ExceptionHandler에서 HTTP Status나 message 등을 공통 처리할 수 있습니다.

```java
public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
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

상황에 따라 success와 fail을 나누어서 사용하기도 했습니다. fail은 Exception을 발생시키지 않고 내부적으로 응답 안에 status를 만들어 성공, 실패 여부를 반환했습니다. 

📌 `@UtilityClass`를 사용하면 (1) final 클래스로 생성하고 (2) private 생성자를 자동으로 생성하고 (3) 내부 함수를 정적 메서드로 사용할 수 있습니다.

```java
@UtilityClass
public class ResponseUtility {
    
    public ResponseEntity<CommonResponseVO> createSuccessResponse() {
        return this.createSuccessResponse(null, HttpStatus.OK);
    }
    
    public ResponseEntity<CommonResponseVO> createSuccessResponse(CommonResponseVO commonResponseVO) {
        return this.createSuccessResponse(commonResponseVO, HttpStatus.OK);
    }
    
    public ResponseEntity<CommonResponseVO> createSuccessResponse(HttpStatus httpStatus) {
        return this.createSuccessResponse(null, httpStatus);
    }

    public ResponseEntity<CommonResponseVO> createSuccessResponse(CommonResponseVO commonResponseVO, HttpStatus httpStatus) {
        return ResponseEntity.status(httpStatus).body(commonResponseVO);
    }
}
```

---

자주 쓰이는 Controller Annotation
===

| 이름                                                                            | 위치        | 내용                                                                                                                                                                                                                                                                                                                                                                                |
|-------------------------------------------------------------------------------|-----------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| @RestController                                                               | Class     | Spring의 컴포넌트 스캔 대상이 되도록 하고 HTTP 요청과 응답을 자동으로 매핑되도록 하는 등 Spring에서 Controller로 동작할 수 있도록 기본적인 동작을 담고있는 어노테이션입니다.<br>@Controller와 @RequestBody가 합쳐진 어노테이션으로 RESTful 웹 서비스에서 주로 사용됩니다.                                                                                                                                                                                                |
| @RequiredArgsConstructor                                                      | Class     | Spring 4.3부터는 @Autowired를 통한 의존성 주입보다 생성자를 통합 의존성 주입을 권장하고 있습니다.<br>@RequiredArgsConstructor를 사용하면 생성자 코드를 따로 적지 않아도 됩니다.<br>`private final Service service`와 같이 사용됩니다.                                                                                                                                                                                                           |
| @RequestMapping                                                               | Class     | 함수 레벨에 사용할 수 있지만 함수 레벨에는 @GetMapping과 같은 HTTP 메소드 레벨의 어노테이션을 사용하고 클래스 레벨에는 @RequestMapping을 사용합니다.<br>Controller에 포함된 모든 함수에 공통 적용할 공통 경로와 같은 내용을 적습니다.                                                                                                                                                                                                                           |
| @Api, @Tag                                                                    | Class     | Swagger를 사용할 때 사용하는 어노테이션입니다.<br>Swagger 버전에 따라 사용되는 어노테이션의 이름이 조금씩 다릅니다.                                                                                                                                                                                                                                                                                                         |
| @ApiOperation, @Operation                                                     | Method    | Swagger를 사용할 때 사용하는 어노테이션입니다.<br>Swagger 버전에 따라 사용되는 어노테이션의 이름이 조금씩 다릅니다.                                                                                                                                                                                                                                                                                                         |
| @GetMapping<br>@PostMapping<br>@PutMapping<br>@DeleteMapping<br>@PatchMapping | Method    | - @RequestMapping의 하위 어노테이션으로 각 함수마다 사용하는 HTTP 메소드에 따라 지정해서 사용합니다.<br/>- path : 각 API의 하위 경로를 명시<br/>- consumes(요청), produces(응답) : 요청과 응답의 형식을 지정할 때 사용합니다. 주로 JSON 형태로 통신하기 때문에 `MediaType.APPLICATION_JSON_VALUE`를 사용했습니다.                                                                                                                                                     |
| @PathVariable                                                                 | Parameter | - Method : `GET`<br>- URL 경로에서 변수를 추출하는 데 사용됩니다. 주로 RESTful 웹 서비스에서 경로 매개변수를 추출하는 데 쓰입니다.<br>- URL 경로에서 특정 부분을 변수로 설정하고 사용하려면 @PathVariable을 사용합니다. <br/>- `/products/{id}`와 같은 경로에서 id를 추출할 때 사용합니다.                                                                                                                                                                             |
| @RequestParam                                                                 | Parameter | - Method : `GET`<br>- 요청의 쿼리 매개변수(파라미터)를 추출하는 데 사용됩니다. URL의 ? 뒤에 오는 매개변수를 읽어옵니다.<br>- 쿼리 매개변수를 추출할 때 사용합니다. <br/>- `/products?id=123`와 같이 URL에서 id를 추출할 때 사용합니다.<br/>- 전달되는 파라미터가 많을 경우 VO 클래스를 사용하면 클래스 내 필드와 자동으로 맵핑해줍니다. (데이터 바인딩)<br/>- `POST`, `PUT`, `PATCH`에도 Request가 form 데이터 형식으로 온다면 (URL 뒤에 Query String 형태로 온다면) 사용할 수 있지만 JSON 형태는 수용하지 못하기 때문에 주로 `GET`에서만 사용합니다.) |
| @RequestBody                                                                  | Parameter | - Method : `POST`, `PUT`, `PATCH`<br>- HTTP 요청의 본문(body) 부분을 특정 자바 객체로 매핑하도록 지시합니다.<br>- 주로 POST나 PUT 요청과 함께 사용되며, 클라이언트가 JSON 또는 XML 형식으로 데이터를 전송할 때 사용됩니다.<br>- 예를 들어, 클라이언트가 JSON으로 데이터를 보내고 이를 자바 객체로 변환하려면 @RequestBody를 사용합니다.                                                                                                                                              |
| @Valid                                                                        | Parameter | 요청 파라미터를 데이터 바인딩 할 때 객체의 유효성을 검사합니다. <br/>@NotNull, @NotEmpty, @Size, @Pattern와 같은 Hibernate Validator로 정의된 어노테이션과 같이 사용됩니다.<br/>VO 클래스를 사용할 경우 각 필드에 필요한 유효성 검사 어노테이션을 붙여서 요청을 손쉽게 필터링할 수 있습니다.                                                                                                                                                                                  |

```java
@RestController
@RequestMapping("/v1")
@Api(tags = {"Search"})
@RequiredArgsConstructor
public class SearchController {

    private final SearchService searchService;

    @ApiOperation(value = "카카오 블로그 검색", httpMethod = "GET", notes = "카카오 블로그 사이트를 블로그를 검색한다. 카카오 API가 작동하지 않을 시 네이버 API를 대체하여 사용한다.")
    @GetMapping(path = "/kakao/blogs", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponseVO> getKakaoBlogs(@Valid SearchKakaoBlogRequestVO searchKakaoBlogRequestVO) {
        return ResponseUtil.createSuccessResponse(searchService.getKakaoBlogs(searchKakaoBlogRequestVO));
    }

    @ApiOperation(value = "네이버 블로그 검색", httpMethod = "GET", notes = "네이버 블로그 사이트를 블로그를 검색한다.")
    @GetMapping(path = "/naver/blogs", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponseVO> getNaverBlogs(@Valid SearchNaverBlogRequestVO naverBlogRequestVO) {
        return ResponseUtil.createSuccessResponse(searchService.getNaverBlogs(naverBlogRequestVO));
    }

    @ApiOperation(value = "인기 키워드 검색", httpMethod = "GET", notes = "특정 기간 안에 있는 인기 검색어 목록을 검색한다.")
    @GetMapping(path = "/popular-keyword", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<CommonResponseVO> getPopularKeywords(@Valid SearchPopularKeywordRequestVO searchPopularKeywordRequestVO) {
        return ResponseUtil.createSuccessResponse(searchService.getPopularKeywords(searchPopularKeywordRequestVO));
    }

}
```
---

자주 쓰이는 Service Annotation
===

| 이름                       | 위치     | 내용                                                                                                                                                                                                                                                     |
|--------------------------|--------|--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| @Service                 | Class  | @Controller와 비슷한 역할을 합니다.<br/>컴포넌트 스캔 대상이 되고 Spring Container에 Bean으로 등록됩니다.<br/>해당 클래스가 비지니스 로직을 처리하는 Service 클래스라는 것을 나타내기도 합니다.                                                                                                                     |
| @RequiredArgsConstructor | Class  | Controller에서 사용되는 이유와 같습니다. 생성자 의존성 주입을 위해서 사용되며 <br/>`private final Repository repository`와 같이 사용됩니다.                                                                                                                                                 |
| @Transactional           | Method | DB의 트랜잭션을 만들고 관리하기 위해서 사용합니다.<br/>단순히 @Transactional만 적어주면 실행되는 함수가 하나의 트랜잭션으로 묶여서 중간에 에러가 발생하면 DB에 Rollback을 실행합니다.<br/>readOnly, rollbackFor, noRollbackFor, propagation, isolation, timeout와 같은 옵션으로 트랜잭션을 나눌 수 있습니다.<br/>자세한 내용은 별도의 파트에서 다룹니다. |

```java
@Service
@RequiredArgsConstructor
public class SearchService {
    private final SearchKeywordHistoryRepository searchKeywordHistoryRepository;

    private final KakaoService kakaoService;
    private final NaverService naverService;

    @PersistenceContext
    private EntityManager em;

    @Transactional
    @HystrixCommand(fallbackMethod = "fallbackGetKakaoBlogs")
    public PaginationResponseVO getKakaoBlogs(SearchKakaoBlogRequestVO searchKakaoBlogRequestVO) {
        KakaoBlogRequestDTO kakaoBlogRequestDTO = searchKakaoBlogRequestVO.toKakaoBlogRequestDTO();
        KakaoBlogResponseDTO kakaoBlogResponseDTO = kakaoService.getKakaoBlog(kakaoBlogRequestDTO);
        kakaoBlogResponseDTO.getMeta().setQuery(kakaoBlogRequestDTO.getQuery());
        searchKeywordHistoryRepository.save(SearchKeywordHistoryEntity.builder().keyword(searchKakaoBlogRequestVO.getKeyword()).build());
        return new PaginationResponseVO(kakaoBlogResponseDTO.getMeta(), kakaoBlogResponseDTO.getDocuments());
    }

    @Transactional
    @HystrixCommand(fallbackMethod = "fallbackGetNaverBlogs")
    public PaginationResponseVO getNaverBlogs(SearchNaverBlogRequestVO searchNaverBlogRequestVO) {
        NaverBlogRequestDTO naverBlogRequestDTO = searchNaverBlogRequestVO.toNaverBlogRequestDTO();
        NaverBlogResponseDTO naverBlogResponseDTO = naverService.getNaverBlog(naverBlogRequestDTO);
        searchKeywordHistoryRepository.save(SearchKeywordHistoryEntity.builder().keyword(searchNaverBlogRequestVO.getKeyword()).build());
        return new PaginationResponseVO(naverBlogResponseDTO.getMeta(naverBlogRequestDTO.getQuery()), naverBlogResponseDTO.getItems());
    }

    public PaginationResponseVO fallbackGetKakaoBlogs(SearchKakaoBlogRequestVO searchKakaoBlogRequestVO) {
        return getNaverBlogs(searchKakaoBlogRequestVO.toSearchNaverBlogRequestVO());
    }

    public PaginationResponseVO fallbackGetNaverBlogs(SearchNaverBlogRequestVO searchNaverBlogRequestVO) {
        return getKakaoBlogs(searchNaverBlogRequestVO.toSearchKakaoBlogRequestVO());
    }
}
```

---

자주 쓰이는 Model Annotation
===
| 이름                                               | 위치    | 내용                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                              |
|--------------------------------------------------|-------|-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| @AllArgsConstrutor(access = AccessLevel.PRIVATE)<br/>@NoArgsConstrutor | Class | 기본 생성자와 모든 필드를 포함한 생성자를 자동으로 생성해줍니다.<br/>필드가 많을 경우 생성자로 객체를 생성하면 필드 순서와 타입을 모두 맞춰야하기 때문에 휴먼에러를 만들어낼 가능성이 높아지게 됩니다.<br/>따라서 VO는 Builder로 생성하는 것을 추천하며 외부에서 생성자를 사용할 수 없도록 AccessLevel을 설정하기도 합니다.<br/>📌 Builder 패턴이 갖는 장점<br/>- **가독성과 유지보수 향상** : 생성자에 많은 매개변수가 있을 때 인자의 순서와 의미를 혼동할 수 있습니다. Builder 패턴을 사용하면 각각의 설정 메서드에 의미 있는 이름을 부여하고 가독성이 향상됩니다.<br/>- **필수 및 선택적 매개변수 지원** : 일부 필드는 필수이고 일부는 선택적일 때 Builder 패턴은 특히 유용합니다. 필요한 필드만 설정하고 나머지는 기본값으로 초기화할 수 있습니다.<br/>- **인자 순서에 구애받지 않음** : 생성자의 인자 순서에 구애받지 않아도 되므로 새로운 필드를 추가하거나 기존 필드를 변경해도 기존 코드에 영향을 주지 않으면서 객체를 생성할 수 있습니다.<br/>- **가변성을 감소시켜 객체의 일관성 강화** : Builder 패턴을 사용하면 가변성을 감소시킴으로써 객체의 일관성을 강화할 수 있습니다. 객체 생성 이후에는 더 이상 변경할 수 없게 되어 객체의 상태를 안정적으로 유지할 수 있습니다. |
| @Data                                            | Class | @Getter, @Setter, @ToString, @EqualsAndHashCode, @RequiredArgsConstructor 를 합친 Annotation<br/>@RequiredArgsConstructor은 초기화 되지 않은 모든 final 필드, @NonNull과 같이 제약조건이 설정되어있는 모든 필드들에 대한 생성자를 자동으로 생성한다.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                             |
| @Builder                                         | Class | Builder 패턴을 자동으로 생성해줍니다.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        |
| @SuperBuilder                                    | Class | VO를 상속 받아서 사용하는 경우 부모의 필드를 Builder에서 사용할 수 있도록 해줍니다.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            |
| @Builder.default                                 | Field | Builder로 생성할 때 기본값을 지정해주기 위해서 사용합니다.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                            |
| @ApiModelProperty                                | Field | Swagger를 사용할 때 사용하는 어노테이션입니다.<br>Swagger 버전에 따라 사용되는 어노테이션의 이름이 조금씩 다릅니다.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                       |
| @Min(1)<br/>@Max(20)                             | Field | @Vaild와 같이 사용되며 VO 안에 있는 필드 값의 유효성 검사를 해줍니다.<br/>@Min, @Max 이외에도 @Positive, @PositiveOrZero 등 여러 종류의 Annotation을 사용할 수 있습니다.                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                    |
| @NotNull<br/>@NotEmpty<br/>@NotBlank             | Field | @NotNull : Null 여부 체크 <br/>@NotEmpty : Null 여부, 빈 문자열(""), 빈 객체(List)인지 체크<br/>@NotBlank : Null 여부, 빈 문자열(""), 빈 객체(List), 공백 문자열(" ")인지 체크                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                     | 

```java
@Getter
@ToString
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@EqualsAndHashCode(callSuper = false)
public class SearchKakaoBlogRequestVO extends PaginationRequestVO {
    @Builder.Default
    @Enum(enumClass = DefaultBlogSortType.class)
    @ApiModelProperty(value = "블로그를 조회하는 정렬 기준 (기본값: ACCURACY)", notes = "블로그 조회 정렬 기준", example = "RECENCY", allowableValues = "ACCURACY, RECENCY")
    private String sortType = DefaultBlogSortType.ACCURACY.name();

    @ApiModelProperty(value = "특정 블로그 URL", notes = "특정 블로그 글만 검색하고 싶은 경우")
    private String blogUrl;

    @ApiModelProperty(value = "블로그를 검색하는 기준 키워드", notes = "블로그 검색 키워드", required = true)
    @NotEmpty
    private String keyword;

    @Min(1)
    @Max(50)
    @Builder.Default
    @ApiModelProperty(value = "Page 단위로 조회 시 한 번에 가져올 Page의 크기 (기본값: 10)", notes = "1~50 사이의 정수", example = "10")
    private Integer pageSize = 10;

    @Min(1)
    @Max(50)
    @Builder.Default
    @ApiModelProperty(value = "Page 단위로 조회 시 데이터 조회 시작 건 수 (기본값: 1)", notes = "1~50 사이의 정수", example = "1")
    private Integer start = 1;

    public KakaoBlogRequestDTO toKakaoBlogRequestDTO() {
        String query = ObjectUtils.isEmpty(blogUrl) ? keyword : blogUrl + " " + keyword;
        String sort = sortType.toLowerCase();
        return KakaoBlogRequestDTO.builder()
                .query(query)
                .sort(sort)
                .page(this.start)
                .size(this.pageSize)
                .build();
    }

    public SearchNaverBlogRequestVO toSearchNaverBlogRequestVO() {
        return SearchNaverBlogRequestVO.builder()
                .keyword(keyword)
                .sortType(sortType)
                .start(this.start)
                .pageSize(this.pageSize)
                .build();
    }
}
```

---

Request에 enum 클래스 처리하기
===
여러 개발자들이 사용해야 하는 공통 기능은 더 적은 코드로 간단하게 적용할 수 있어야 마이너한 버그르 방지할 수 있습니다.

Spring에서 특정 범위의 값을 갖는 변수를 처리하기 위해서 `enum` 타입을 만들어서 사용합니다.<br>
특히 공통 코드를 관리하는 방법은 여러가지가 있지만 프로젝트에선 주로 enum 타입으로 만들어서 관리했습니다.<br>
[(📌 VO 안에서 private final 필드 사용 - 공통 코드와 enum 필드)](#-vo-안에서-private-final-필드-사용---공통-코드와-enum-필드)<br>
그리고 이 공통 코드는 FE에서도 사용되고 FE가 BE와 통신할 때에도 사용됩니다.

결과적으로 공통 코드는 FE/BE/SQL에서 모두 사용되며 BE에서 enum 타입으로 관리된다면 FE와 통신할 때 RequestVO에 enum 타입을 바로 사용해서 enum 타입의 장점을 사용해야 합니다.

공통 코드가 name과 code 값을 갖는 형태라면 FE에선 name 값만 필요하고 DB에서는 code 값으로 저장되기 때문에 BE는 FE로부터 name 값을 받아 code 값으로 DB에 접근해야 합니다.
1. RequestVO에 enum 타입을 바로 사용해서 enum 범위에서 벗어난 값이 들어오면 자동으로 유효성 검사가 되도록 하고 싶습니다.
2. BE에서 code 필드를 갖는 enum 타입으로 관리되고 있다면 FE에서 name 값이나 code 값이 왔을 때 모두 수용하고 싶습니다.<br>(실제 프로젝트에선 FE에서도 code&name 값을 모두 갖고 있었기 때문에 확장성을 고려해서 BE는 code 값이 오나 name 값이 오나 모두 수용할 수 있도록 만들고 싶습니다.)

    <img width="568" alt="image" src="https://github.com/justdoanything/self-study/assets/21374902/daae32f0-5dda-49ba-bbc3-de5294ef1b3a">

예를들어 아래와 같은 RequestVO에 있는 필드 중에서 contentsType을 별도의 처리 없이 ContentsTypeCode 타입으로 사용하면 제대로 동작하지 않습니다.

String 타입으로 받으면 별도의 검증 코드를 만들어서 처리해야 하는데 enum 타입을 쓰는 곳마다 코드를 검증 코드를 사용해야 하고 enum 탕비이 수십개가 된다면 불필요한 코드의 양도 늘어나서 관리하기 어려워집니다.

이 문제를 해결하기 위한 방법을 하나씩 살펴보며 가장 좋은 방법이 뭔지 찾아보겠습니다.

```java
public class RequestVO {
    // ❌ 별도의 처리 없이 ContentsTypeCode를 타입으로 바로 받으면 제대로 동작하지 않음.
    private ContentsTypeCode contentsType;
    private String title;
    private String contents;
}
```
```java
public static boolean isValidName(String name) {
    for(ContentsTypeCode contentsTypeCode : ContentsTypeCode.values()) {
        if(contentsTypeCode.name().equalsIgnoreCase(name) || contentsTypeCode.code().equalsIgnoreCase(name)) {
            return true;
        }
    }
    return false;
}   
```

<h3>< Enum 객체의 비교에서 `==` 와 `.equals()`의 차이 ></h3>
- `==`은 주소값을 비교하기 때문에 컴파일 시 타입 체크를 하고 다른 타입일 경우 컴파일 에러가 발생한다. 하지만 런타임에서 NPE는 검증하지 않는다.
- `.equals()`는 Object 값을 비교할 때 내부적으로 == 을 사용하고 컴파일 단계에서 타입 체크를 하지 않는다. 런타임에서 NPE는 잡을 수 있다.

## 테스트 환경
- Framework : SpringBoot version 3.2.3
- Language : OpenJDK 17
- Dependency
  - implementation 'org.springframework.boot:spring-boot-starter-web'
  - implementation 'org.springframework.boot:spring-boot-starter-validation'
  - implementation 'org.apache.commons:commons-lang3:3.12.0'
  - testImplementation 'org.springframework.boot:spring-boot-starter-test'
  - compileOnly 'org.projectlombok:lombok'
  - annotationProcessor 'org.projectlombok:lombok'
- Project : [REQUEST_ENUM_TEST](https://github.com/justdoanything/request-enum-test)
  - enum
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
  - RequestVO
    ```java
    @Getter
    @ToString
    @Builder
    public class RequestVO {
        private ContentsTypeCode contentsTypeCode;
        private String title;
        private String contents;
    }
    ```
  - Controller
    ```java
    @RestController
    @RequestMapping("/v1")
    @RequiredArgsConstructor
    public class SimpleController {
  
        @PostMapping("/post/request")
        public ResponseEntity methodPostRequest(@RequestBody RequestVO requestVO) {
            return ResponseEntity.ok().body(requestVO);
        }
    
        @GetMapping("/get/request")
        public ResponseEntity methodGetRequest(RequestVO requestVO) {
            return ResponseEntity.ok().body(requestVO);
        }
    
        @GetMapping("/get/request/request-param")
        public ResponseEntity methodGetRequestRequestParam(@RequestParam ContentsTypeCode contentsTypeCode) {
            return ResponseEntity.ok().body(contentsTypeCode);
        }

        @GetMapping("/get/request/path-variable/{contentsTypeCode}")
        public ResponseEntity methodGetRequestPathVariable(@PathVariable ContentsTypeCode contentsTypeCode) {
            return ResponseEntity.ok().body(contentsTypeCode);
        }
    }
    ```

  - Test

    <details>
    <summary>PostTest</summary>
    
    ```java
    @SpringBootTest
    @AutoConfigureMockMvc
    class PostTest {
    
        @Autowired
        private MockMvc mockMvc;
    
        @Autowired
        private ObjectMapper objectMapper;
    
        @Test
        @DisplayName("성공_올바른 ContentsTypeCode의 name 값을 사용했을 때 성공한다.")
        public void success_enum_name_case() throws Exception {
            //given
            Map<String, String> request = Map.of(
                    "title", "title",
                    "contents", "contents",
                    "contentsTypeCode", "FEED"
            );
    
            //then
            mockMvc.perform(post("/v1/post/request")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(print());
        }
    
        @Test
        @DisplayName("실패_범위에서 벗어난 ContentsTypeCode의 name 값을 사용했을 때 실패한다.")
        public void fail_out_of_enum_name_case() throws Exception {
            //given
            Map<String, String> request = Map.of(
                    "title", "title",
                    "contents", "contents",
                    "contentsTypeCode", "YOUTUBE"
            );
    
            //then
            mockMvc.perform(post("/v1/post/request")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(MockMvcResultMatchers.status().isBadRequest())
                    .andDo(print());
        }
    
        @Test
        @DisplayName("성공_올바른 ContentsTypeCode의 code 값을 사용했을 때 성공한다.")
        public void success_enum_code_case() throws Exception {
            //given
            Map<String, String> request = Map.of(
                    "title", "title",
                    "contents", "contents",
                    "contentsTypeCode", "005"
            );
    
            //then
            mockMvc.perform(post("/v1/post/request")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(print());
        }
    
        @Test
        @DisplayName("실패_범위에서 벗어난 ContentsTypeCode의 code 값을 사용했을 때 실패한다.")
        public void fail_out_of_enum_code_case() throws Exception {
            //given
            Map<String, String> request = Map.of(
                    "title", "title",
                    "contents", "contents",
                    "contentsTypeCode", "006"
            );
    
            //then
            mockMvc.perform(post("/v1/post/request")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(MockMvcResultMatchers.status().isBadRequest())
                    .andDo(print());
        }
    
        @Test
        @DisplayName("실패_올바른 ContentsTypeCode의 소문자 값을 보냈을 때 성공한다.")
        public void success_enum_name_lower_case() throws Exception {
            //given
            Map<String, String> request = Map.of(
                    "title", "title",
                    "contents", "contents",
                    "contentsTypeCode", "feed"
            );
    
            //then
            mockMvc.perform(post("/v1/post/request")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(print());
        }
    
        @Test
        @DisplayName("실패_ContentsTypeCode의 null 값을 사용했을 때 실패한다.")
        public void fail_enum_null_case() throws Exception {
            //given
            Map<String, String> request = Map.of(
                    "title", "title",
                    "contents", "contents"
            );
    
            //then
            mockMvc.perform(post("/v1/post/request")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(MockMvcResultMatchers.status().isBadRequest())
                    .andDo(print());
        }
    
        @Test
        @DisplayName("실패_ContentsTypeCode의 index 값을 사용했을 때 실패한다.")
        public void happy_enum_index_case() throws Exception {
            //given
            Map<String, String> request = Map.of(
                    "title", "title",
                    "contents", "contents",
                    "contentsTypeCode", "0"
            );
    
            //then
            mockMvc.perform(post("/v1/post/request")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andExpect(MockMvcResultMatchers.status().isBadRequest())
                    .andDo(print());
        }
    
        @Test
        @DisplayName("성공_범위에서 벗어난 ContentsTypeCode을 사용했을 때 응답에 에러 문구가 있다.")
        public void happy_enum_error_message_case() throws Exception {
            //given
            Map<String, String> request = Map.of(
                    "title", "title",
                    "contents", "contents",
                    "contentsTypeCode", "HOT"
            );
    
            //when
            MvcResult result = mockMvc.perform(post("/v1/post/request")
                            .contentType(MediaType.APPLICATION_JSON)
                            .content(objectMapper.writeValueAsString(request)))
                    .andDo(print())
                    .andReturn();
    
            //then
            Assertions.assertEquals(result.getResponse().getStatus(), HttpStatus.BAD_REQUEST.value());
            Assertions.assertTrue(result.getResponse().getContentAsString().length() > 0);
        }
    }
    ```
    
    </details>
    
    <details>
    <summary>GetTest</summary>
    
    ```java
    @SpringBootTest
    @AutoConfigureMockMvc
    class GetTest {
    
        @Autowired
        private MockMvc mockMvc;
    
        @Test
        @DisplayName("성공_올바른 ContentsTypeCode의 name 값을 사용했을 때 성공한다.")
        public void success_enum_name_case() throws Exception {
            //given
            String contentsTypeCode = "FEED";
    
            //then
            mockMvc.perform(get("/v1/get/request")
                            .param("contentsTypeCode", contentsTypeCode))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(print());
        }
    
        @Test
        @DisplayName("실패_범위에서 벗어난 ContentsTypeCode의 name 값을 사용했을 때 실패한다.")
        public void fail_out_of_enum_name_case() throws Exception {
            //given
            String contentsTypeCode = "YOUTUBE";
    
            //then
            mockMvc.perform(get("/v1/get/request")
                            .param("contentsTypeCode", contentsTypeCode))
                    .andExpect(MockMvcResultMatchers.status().isBadRequest())
                    .andDo(print());
        }
    
        @Test
        @DisplayName("성공_올바른 ContentsTypeCode의 code 값을 사용했을 때 성공한다.")
        public void success_enum_code_case() throws Exception {
            //given
            String contentsTypeCode = "005";
    
            //then
            mockMvc.perform(get("/v1/get/request")
                            .param("contentsTypeCode", contentsTypeCode))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(print());
        }
    
        @Test
        @DisplayName("실패_범위에서 벗어난 ContentsTypeCode의 code 값을 사용했을 때 실패한다.")
        public void fail_out_of_enum_code_case() throws Exception {
            //given
            String contentsTypeCode = "006";
    
            //then
            mockMvc.perform(get("/v1/get/request")
                            .param("contentsTypeCode", contentsTypeCode))
                    .andExpect(MockMvcResultMatchers.status().isBadRequest())
                    .andDo(print());
        }
    
        @Test
        @DisplayName("실패_올바른 ContentsTypeCode의 소문자 값을 보냈을 때 성공한다.")
        public void success_enum_name_lower_case() throws Exception {
            //given
            String contentsTypeCode = "feed";
    
            //then
            mockMvc.perform(get("/v1/get/request")
                            .param("contentsTypeCode", contentsTypeCode))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(print());
        }
    
        @Test
        @DisplayName("실패_ContentsTypeCode의 null 값을 사용했을 때 실패한다.")
        public void fail_enum_null_case() throws Exception {
            //given
            String contentsTypeCode = null;
    
            //then
            mockMvc.perform(get("/v1/get/request")
                            .param("contentsTypeCode", contentsTypeCode))
                    .andExpect(MockMvcResultMatchers.status().isBadRequest())
                    .andDo(print());
        }
    
        @Test
        @DisplayName("실패_ContentsTypeCode의 index 값을 사용했을 때 실패한다.")
        public void happy_enum_index_case() throws Exception {
            //given
            String contentsTypeCode = "0";
    
            //then
            mockMvc.perform(get("/v1/get/request")
                            .param("contentsTypeCode", contentsTypeCode))
                    .andExpect(MockMvcResultMatchers.status().isBadRequest())
                    .andDo(print());
        }
    
        @Test
        @DisplayName("성공_범위에서 벗어난 ContentsTypeCode을 사용했을 때 응답에 에러 문구가 있다.")
        public void happy_enum_error_message_case() throws Exception {
            //given
            String contentsTypeCode = "HOT";
    
            //when
            MvcResult result = mockMvc.perform(get("/v1/get/request")
                            .param("contentsTypeCode", contentsTypeCode))
                    .andDo(print())
                    .andReturn();
    
            //then
            Assertions.assertEquals(result.getResponse().getStatus(), HttpStatus.BAD_REQUEST.value());
            Assertions.assertTrue(result.getResponse().getContentAsString().length() > 0);
        }
    }
    ```
    
    </details>
    
    <details>
    <summary>GetRequestParamTest</summary>
    
    ```java
    @SpringBootTest
    @AutoConfigureMockMvc
    class GetRequestParamTest {
    
        @Autowired
        private MockMvc mockMvc;
    
        @Test
        @DisplayName("성공_올바른 ContentsTypeCode의 name 값을 사용했을 때 성공한다.")
        public void success_enum_name_case() throws Exception {
            //given
            String contentsTypeCode = "FEED";
    
            //then
            mockMvc.perform(get("/v1/get/request/request-param")
                            .param("contentsTypeCode", contentsTypeCode))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(print());
        }
    
        @Test
        @DisplayName("실패_범위에서 벗어난 ContentsTypeCode의 name 값을 사용했을 때 실패한다.")
        public void fail_out_of_enum_name_case() throws Exception {
            //given
            String contentsTypeCode = "YOUTUBE";
    
            //then
            mockMvc.perform(get("/v1/get/request/request-param")
                            .param("contentsTypeCode", contentsTypeCode))
                    .andExpect(MockMvcResultMatchers.status().isBadRequest())
                    .andDo(print());
        }
    
        @Test
        @DisplayName("성공_올바른 ContentsTypeCode의 code 값을 사용했을 때 성공한다.")
        public void success_enum_code_case() throws Exception {
            //given
            String contentsTypeCode = "005";
    
            //then
            mockMvc.perform(get("/v1/get/request/request-param")
                            .param("contentsTypeCode", contentsTypeCode))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(print());
        }
    
        @Test
        @DisplayName("실패_범위에서 벗어난 ContentsTypeCode의 code 값을 사용했을 때 실패한다.")
        public void fail_out_of_enum_code_case() throws Exception {
            //given
            String contentsTypeCode = "006";
    
            //then
            mockMvc.perform(get("/v1/get/request/request-param")
                            .param("contentsTypeCode", contentsTypeCode))
                    .andExpect(MockMvcResultMatchers.status().isBadRequest())
                    .andDo(print());
        }
    
        @Test
        @DisplayName("실패_올바른 ContentsTypeCode의 소문자 값을 보냈을 때 성공한다.")
        public void success_enum_name_lower_case() throws Exception {
            //given
            String contentsTypeCode = "feed";
    
            //then
            mockMvc.perform(get("/v1/get/request/request-param")
                            .param("contentsTypeCode", contentsTypeCode))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(print());
        }
    
        @Test
        @DisplayName("실패_ContentsTypeCode의 null 값을 사용했을 때 실패한다.")
        public void fail_enum_null_case() throws Exception {
            //given
            String contentsTypeCode = null;
    
            //then
            mockMvc.perform(get("/v1/get/request/request-param")
                            .param("contentsTypeCode", contentsTypeCode))
                    .andExpect(MockMvcResultMatchers.status().isBadRequest())
                    .andDo(print());
        }
    
        @Test
        @DisplayName("실패_ContentsTypeCode의 index 값을 사용했을 때 실패한다.")
        public void happy_enum_index_case() throws Exception {
            //given
            String contentsTypeCode = "0";
    
            //then
            mockMvc.perform(get("/v1/get/request/request-param")
                            .param("contentsTypeCode", contentsTypeCode))
                    .andExpect(MockMvcResultMatchers.status().isBadRequest())
                    .andDo(print());
        }
    
        @Test
        @DisplayName("성공_범위에서 벗어난 ContentsTypeCode을 사용했을 때 응답에 에러 문구가 있다.")
        public void happy_enum_error_message_case() throws Exception {
            //given
            String contentsTypeCode = "HOT";
    
            //when
            MvcResult result = mockMvc.perform(get("/v1/get/request/request-param")
                            .param("contentsTypeCode", contentsTypeCode))
                    .andDo(print())
                    .andReturn();
    
            //then
            Assertions.assertEquals(result.getResponse().getStatus(), HttpStatus.BAD_REQUEST.value());
            Assertions.assertTrue(result.getResponse().getContentAsString().length() > 0);
        }
    }
    ```
    
    </details>
    
    <details>
    <summary>GetPathVariableTest</summary>
    
    ```java
    @SpringBootTest
    @AutoConfigureMockMvc
    class GetPathVariableTest {
    
        @Autowired
        private MockMvc mockMvc;
    
        @Autowired
        private ObjectMapper objectMapper;
    
        @Test
        @DisplayName("성공_올바른 ContentsTypeCode의 name 값을 사용했을 때 성공한다.")
        public void success_enum_name_case() throws Exception {
            //given
            String contentsTypeCode = "FEED";
    
            //then
            mockMvc.perform(get("/v1/get/request/path-variable/{contentsTypeCode}", contentsTypeCode))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(print());
        }
    
        @Test
        @DisplayName("실패_범위에서 벗어난 ContentsTypeCode의 name 값을 사용했을 때 실패한다.")
        public void fail_out_of_enum_name_case() throws Exception {
            //given
            String contentsTypeCode = "YOUTUBE";
    
            //then
            mockMvc.perform(get("/v1/get/request/path-variable/{contentsTypeCode}", contentsTypeCode))
                    .andExpect(MockMvcResultMatchers.status().isBadRequest())
                    .andDo(print());
        }
    
        @Test
        @DisplayName("성공_올바른 ContentsTypeCode의 code 값을 사용했을 때 성공한다.")
        public void success_enum_code_case() throws Exception {
            //given
            String contentsTypeCode = "005";
    
            //then
            mockMvc.perform(get("/v1/get/request/path-variable/{contentsTypeCode}", contentsTypeCode))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(print());
        }
    
        @Test
        @DisplayName("실패_범위에서 벗어난 ContentsTypeCode의 code 값을 사용했을 때 실패한다.")
        public void fail_out_of_enum_code_case() throws Exception {
            //given
            String contentsTypeCode = "006";
    
            //then
            mockMvc.perform(get("/v1/get/request/path-variable/{contentsTypeCode}", contentsTypeCode))
                    .andExpect(MockMvcResultMatchers.status().isBadRequest())
                    .andDo(print());
        }
    
        @Test
        @DisplayName("실패_올바른 ContentsTypeCode의 소문자 값을 보냈을 때 성공한다.")
        public void success_enum_name_lower_case() throws Exception {
            //given
            String contentsTypeCode = "feed";
    
            //then
            mockMvc.perform(get("/v1/get/request/path-variable/{contentsTypeCode}", contentsTypeCode))
                    .andExpect(MockMvcResultMatchers.status().isOk())
                    .andDo(print());
        }
    
        @Test
        @DisplayName("실패_ContentsTypeCode의 null 값을 사용했을 때 실패한다.")
        public void fail_enum_null_case() throws Exception {
            //given
            String contentsTypeCode = null;
    
            //then
            mockMvc.perform(get("/v1/get/request/path-variable/{contentsTypeCode}", contentsTypeCode))
                    .andExpect(MockMvcResultMatchers.status().isNotFound())
                    .andDo(print());
        }
    
        @Test
        @DisplayName("실패_ContentsTypeCode의 index 값을 사용했을 때 실패한다.")
        public void happy_enum_index_case() throws Exception {
            //given
            String contentsTypeCode = "0";
    
            //then
            mockMvc.perform(get("/v1/get/request/path-variable/{contentsTypeCode}", contentsTypeCode))
                    .andExpect(MockMvcResultMatchers.status().isBadRequest())
                    .andDo(print());
        }
    
        @Test
        @DisplayName("성공_범위에서 벗어난 ContentsTypeCode을 사용했을 때 응답에 에러 문구가 있다.")
        public void happy_enum_error_message_case() throws Exception {
            //given
            String contentsTypeCode = "HOT";
    
            //when
            MvcResult result = mockMvc.perform(get("/v1/get/request/path-variable/{contentsTypeCode}", contentsTypeCode))
                    .andDo(print())
                    .andReturn();
    
            //then
            Assertions.assertEquals(result.getResponse().getStatus(), HttpStatus.BAD_REQUEST.value());
            Assertions.assertTrue(result.getResponse().getContentAsString().length() > 0);
        }
    }
    ```
    
    </details>


## RequestVO에 enum 타입 바로 사용하기
예전에 프로젝트를 진행할 때 별도의 처리 없이 enum 타입을 바로 사용하면 제대로 동작하지 않았던 것으로 기억하고 있는데 이번에 다시 테스트를 해보니 enum 타입을 바로 사용해도 간단한 동작은 했습니다.

Request에 `[FEED, COMMENT, NOTICE, COUPON, VOTE]` 이 외의 값을 넣으면 400 Bad Request가 발생하고 `[001, 002, 003, 004]` 값을 넣어도 정상적으로 200 응답을 받았습니다.

하지만 `005`는 400 에러를 발생시켰고 좀 더 찾아보니 enum이 갖고 있는 값의 인덱스는 수용하고 있었습니다.

즉, ContenteTypeCode는 5개의 값을 포함하고 있기 때문에`[001, 002, 003 ,004]` 값을 자동으로 `[1, 2, 3, 4]` 으로 인식하고 정상 응답을 한 것이었습니다. 

하지만 인덱스는 0부터 4까지 잡히기 때문에 `000`은 200 성공 응답이 오지만 `005`는 400 에러를 발생시켰습니다.

결과적으로 아무런 처리 없이 enum 타입을 바로 사용하면 아래와 같습니다.<br>(화살표로 표시한 아래 조건을 모두 만족하는 코드를 짜는게 목표입니다.)
- `enum의 name 값의 범위만 보장합니다.`<br>→ ContentsTypeCode는 name과 code 값을 한 쌍으로 갖고 있고 <u>code에 대한 값도 수용도 하고 싶습니다.</u>
- `name의 대소문자가 다르면 400 에러를 발생시킵니다.`<br>→ <u>대소문자를 구분하지 않고 수용하고 싶습니다.</u>
- `enum이 갖고 있는 값만큼의 index 값을 수용합니다.`<br>→ 001,002,003,004 등 code 값과 혼동될 수 있는 <u>index 값은 수용하고 싶지 않습니다.</u>
- `null 값을 허용합니다.`<br>→ <u>상황에 따라 null 값을 허용하고 싶지 않습니다.</u>
- `범위를 벗어났을 때 발생하는 에러에 대한 핸들링이 어렵습니다.`<br>→ 범위를 벗어났을 때 응답을 수신하는 쪽에서 에러 원인을 파악하기 쉽도록 <u>에러 메세지를 만들어서 반환하고 싶습니다.</u>

| 테스트 시나리오                                            | POST        | GET<br>(VO 객체 사용) | GET<br/>(RequestParam) | GET<br/>(PathVariable) |
|-----------------------------------------------------|-------------|-------------------|------------------------|------------------------|
| 성공_올바른 ContentsTypeCode의 name 값을 사용했을 때 성공한다.       | O           | O                 | O                      | O                      |
| 실패_범위에서 벗어난 ContentsTypeCode의 name 값을 사용했을 때 실패한다.  | O           | O                 | O                      | O                      |
| 성공_올바른 ContentsTypeCode의 code 값을 사용했을 때 성공한다.       | X           | X                 | X                      | X                      |
| 실패_범위에서 벗어난 ContentsTypeCode의 code 값을 사용했을 때 실패한다.  | O           | O                 | O                      | O                      |
| 실패_올바른 ContentsTypeCode의 소문자 값을 보냈을 때 성공한다.         | X           | X                 | X                      | X                      |
| 실패_ContentsTypeCode의 null 값을 사용했을 때 실패한다.           | X<br/>(200) | X<br/>(200)       | O                      | O                      |
| 실패_ContentsTypeCode의 index 값을 사용했을 때 실패한다.          | X           | O                 | O                      | O                      |
| 성공_범위에서 벗어난 ContentsTypeCode을 사용했을 때 응답에 에러 문구가 있다. | X           | X                 | X                      | X                      |


## @Enum과 EnumValidator 
우선 처음으로 적용해볼 방법은 `@Enum`과 `EnumValidator`를 만들어서 사용하는 방법입니다.

`@Enum`은 `@Constraint`를 사용해서 만드는 어노테이션으로 유효성 검사를 할 필드를 지정하는 역할을 합니다.

`EnumValidator`은 `ConstraintValidator`을 상속 받아서 `initialize`와 `isValid` 함수에 어떤 유효성 검사를 할지 정의합니다.

### 1. 가장 먼저 `@Enum`과 `EnumValidator` 작성
```java
@Target({ElementType.TYPE_USE, ElementType.FIELD, ElementType.PARAMETER,})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EnumValidator.class)
public @interface Enum {
    String message() default "유효하지 않은 enum 타입입니다.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    Class<? extends java.lang.Enum<?>> enumClass();

    boolean ignoreCase() default false;

    boolean isNullable() default false;

    String[] excludeEnumType() default {};
}
```
```java
public class EnumValidator implements ConstraintValidator<Enum, String> {
    private List<String> enumNames;
    private List<String> enumCodes;
    private Enum annotation;

    @Override
    public void initialize(Enum value) {
        this.annotation = value;

        List<String> excludeEnumType =
                Arrays.stream(this.annotation.excludeEnumType()).toList();

        enumNames = Arrays.stream(this.annotation.enumClass().getEnumConstants())
                .map(constants ->
                        this.annotation.ignoreCase() ? constants.name().toUpperCase() : constants.name())
                .filter(constants -> !excludeEnumType.contains(constants))
                .collect(Collectors.toList());

        boolean isHaveCodeMethod = Arrays.stream(value.enumClass().getMethods()).anyMatch(method -> "code".equals(method.getName()));

        if (isHaveCodeMethod) {
            enumCodes = Arrays.stream(this.annotation.enumClass().getEnumConstants()).map(constant -> {
                try {
                    return (String) constant.getClass().getMethod("code").invoke(constant);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }).collect(Collectors.toList());
        } else {
            enumCodes = Collections.emptyList();
        }
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if (value == null) {
            return this.annotation.isNullable();
        } else {
            value = this.annotation.ignoreCase() ? value.toUpperCase() : value;
            return enumNames.contains(value) || enumCodes.contains(value);
        }
    }
}
```

### 2. RequestVO에서 검사할 필드에 `@Enum`과 옵션 적용
```java
@Getter
@ToString
@Builder
public class RequestVO {
    @Enum(enumClass = ContentsTypeCode.class
            , message = "유효하지 않은 ContentsTypeCode 입니다."
            , excludeEnumType = {"COMMENT"}
            , isNullable = true
            , ignoreCase = true)
    private String contentsTypeCode;
    private String title;
    private String contents;
}
```

### 3. Controller에서 `@Enum`과 `@Valid` 적용
```java
@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class SimpleController {

    @PostMapping("/post/request")
    public ResponseEntity methodPostRequest(@RequestBody @Valid RequestVO requestVO) {
        return ResponseEntity.ok().body(requestVO);
    }

    @GetMapping("/get/request")
    public ResponseEntity methodGetRequest(@Valid RequestVO requestVO) {
        return ResponseEntity.ok().body(requestVO);
    }

    @GetMapping("/get/request/path-variable/{contentsTypeCode}")
    public ResponseEntity methodGetRequestPathVariable(@PathVariable
                                                       @Valid
                                                       @Enum(enumClass = ContentsTypeCode.class
                                                               , message = "유효하지 않은 ContentsTypeCode 입니다."
                                                               , excludeEnumType = {"COMMENT"}
                                                               , ignoreCase = true) String contentsTypeCode) {
        return ResponseEntity.ok().body(contentsTypeCode);
    }

    @GetMapping("/get/request/request-param")
    public ResponseEntity methodGetRequestRequestParam(@RequestParam
                                                       @Valid
                                                       @Enum(enumClass = ContentsTypeCode.class
                                                               , message = "유효하지 않은 ContentsTypeCode 입니다."
                                                               , excludeEnumType = {"COMMENT"}
                                                               , ignoreCase = true) String contentsTypeCode) {
        return ResponseEntity.ok().body(contentsTypeCode);
    }
}
```

### 4. `@Enum`에서 만든 message를 사용하기 위해서 `ResponseVO`와 `CommonExceptionHandler` 생성
```java
@Setter
@Getter
@ToString
@Builder
public class ResponseVO {
    private String status;
    private String message;
}
```
```java
@ControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ResponseVO> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {

        StringBuilder errors = new StringBuilder();
        ex.getBindingResult().getAllErrors().forEach(error ->
                errors.append(error.getDefaultMessage()).append("; "));

        return new ResponseEntity<>(ResponseVO.builder()
                .status("fail")
                .message(errors.toString())
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ResponseVO> handleMethodArgumentNotValidException(HandlerMethodValidationException ex) {

        StringBuilder errors = new StringBuilder();
        ex.getAllErrors().forEach(error ->
                errors.append(error.getDefaultMessage()).append("; "));

        return new ResponseEntity<>(ResponseVO.builder()
                .status("fail")
                .message(errors.toString())
                .build(), HttpStatus.BAD_REQUEST);
    }
}
```

### 5. 결과 및 정리
| 테스트 시나리오                                            | POST | GET<br>(VO 객체 사용) | GET<br/>(RequestParam) | GET<br/>(PathVariable) |
|-----------------------------------------------------|------|-------------------|------------------------|------------------------|
| 성공_올바른 ContentsTypeCode의 name 값을 사용했을 때 성공한다.       | O    | O                 | O                      | O                      |
| 실패_범위에서 벗어난 ContentsTypeCode의 name 값을 사용했을 때 실패한다.  | O    | O                 | O                      | O                      |
| 성공_올바른 ContentsTypeCode의 code 값을 사용했을 때 성공한다.       | O    | O                 | O                      | O                      |
| 실패_범위에서 벗어난 ContentsTypeCode의 code 값을 사용했을 때 실패한다.  | O    | O                 | O                      | O                      |
| 실패_올바른 ContentsTypeCode의 소문자 값을 보냈을 때 성공한다.         | O    | O                 | O                      | O                      |
| 실패_ContentsTypeCode의 null 값을 사용했을 때 실패한다.           | O    | O                 | O                      | O                      |
| 실패_ContentsTypeCode의 index 값을 사용했을 때 실패한다.          | O    | O                 | O                      | O                      |
| 성공_범위에서 벗어난 ContentsTypeCode을 사용했을 때 응답에 에러 문구가 있다. | O    | O                 | O                      | O                      |

테스트 결과를 보면 모두 성공하는 것을 볼 수 있습니다.

`@Enum`과 `EnumValidator`의 장점으론 ignoreCase, excludeEnumType, isNullable 등 <u>여러 속성 값을 만들어서 유효성 검증을 확장성 있게 만들 수 있다는 점입니다.</u>

첫번째 한계점은 사용할 곳에 `@Enum`을 붙여야 하기 때문에 <u>(1) 누락이 될 수 있고</u> 

두번째 한계점은 @PathVariable 처럼 VO가 아닌 파라미터에 직접 사용하는 경우 <u>(2) 코드가 불필요하게 길어져서 가독성이 떨어지게 됩니다.</u>

세번째 한계점은 결정적으로 `@Enum`은 enum 타입이 아닌 String 과 같은 <u>(3) 일반 타입을 사용해야 합니다. </u> 왜냐하면 enum 타입을 사용할 경우 Spring이 enum 타입에 대한 기본 유효성 검사를 먼저 하고 그 다음에 `EnumValidator`의 유효성 검사를 하기 때문입니다. 즉, code 값을 받았을 때 enum 타입에 대한 **기본 검사에 위배(name 값 범위를 벗어남)** 되기 때문에 `EnumValidator`를 거치기 전에 에러를 반환합니다. 

네번째 한계점은 code 값을 받으면 code 값 그대로 Service Layer로 넘겨야 하는데 해당 값이 어떤 name 값과 맵핑되는지 알 수 없기 때문에 <u>(4) code 값으로 enum을 찾아주는 별도의 로직이 필요합니다.</u>

("001"이 들어왔을 때 ContentsTypeCode.FEED을 찾아주는 별도의 로직이 필요합니다.)

위와 같은 이유로 `@Enum`과 `EnumValidator`를 사용하기엔 완벽하지 않았습니다.

## Converter
일반 타입이 아니라 enum 타입을 바로 사용하기 위해서 적용해볼 수 있는 고전적인 방법은 `Converter`를 사용하는 사용하는 방법입니다.

`Converter`는 주로 데이터를 타입 간에 변환할 때 사용됩니다. 문자열을 숫자로 변환하거나 enum 타입으로 변환할 때 사용할 수 있습니다.

Spring에서는 Converter 인터페이스를 구현하여 커스텀한 검증 로직을 정의할 수 있습니다. 이는 주로 데이터 바인딩 시에 사용됩니다.

이 방법을 사용하면 `EnumValidator`의 단점을 대부분 해소할 수 있지만 enum 타입 하나당 하나의 Converter를 만들어줘야 하기 때문에 enum 타입이 많아지면 코드의 양이 불필요하게 늘어나게 됩니다.

따라서 실제 프로젝트에선 사용하지 않았습니다.

### 1. Converter 생성
```java
public class ContentsTypeCodeConverter implements Converter<String, ContentsTypeCode> {
    Class<? extends Enum> enumClass = ContentsTypeCode.class;

    @Override
    public ContentsTypeCode convert(String source) {
        source = source.trim().toUpperCase();

        if (ObjectUtils.isEmpty(source))
            throw new IllegalArgumentException("유효하지 않은 ContentsTypeCode 입니다.");

        boolean isPlainEnum = EnumUtils.isValidEnum(enumClass, source);

        if (isPlainEnum) {
            return ContentsTypeCode.valueOf(source);
        } else {
            boolean isEnumCode = Arrays.stream(enumClass.getMethods()).anyMatch(method -> "code".equals(method.getName()));

            if (isEnumCode) {
                ContentsTypeCode matchedEnum = null;
                String enumCode;
                for (Enum constant : enumClass.getEnumConstants()) {
                    try {
                        enumCode = (String) constant.getClass().getMethod("code").invoke(constant);
                        if (enumCode.equals(source)) {
                            matchedEnum = (ContentsTypeCode) constant;
                            break;
                        }
                    } catch (Exception e) {
                        throw new IllegalArgumentException("유효하지 않은 ContentsTypeCode 입니다.");
                    }
                }

                if (matchedEnum == null)
                    throw new IllegalArgumentException("유효하지 않은 ContentsTypeCode 입니다.");

                return ContentsTypeCode.valueOf(matchedEnum.name());
            } else {
                throw new IllegalArgumentException("유효하지 않은 ContentsTypeCode 입니다.");
            }
        }
    }
}
```

### 2. Converter 등록
```java
@Configuration
public class WebConfiguration implements WebMvcConfigurer {
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new ContentsTypeCodeConverter());
    }
}
```

### 3. Controller에서 enum 타입 바로 사용
```java
@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class SimpleController {

    @PostMapping("/post/request")
    public ResponseEntity methodPostRequest(@RequestBody RequestVO requestVO) {
        return ResponseEntity.ok().body(requestVO);
    }

    @GetMapping("/get/request")
    public ResponseEntity methodGetRequest(RequestVO requestVO) {
        return ResponseEntity.ok().body(requestVO);
    }

    @GetMapping("/get/request/request-param")
    public ResponseEntity methodGetRequestRequestParam(@RequestParam ContentsTypeCode contentsTypeCode) {
        return ResponseEntity.ok().body(contentsTypeCode);
    }

    @GetMapping("/get/request/path-variable/{contentsTypeCode}")
    public ResponseEntity methodGetRequestPathVariable(@PathVariable ContentsTypeCode contentsTypeCode) {
        return ResponseEntity.ok().body(contentsTypeCode);
    }
}
```

### 4. ExceptionHandler에 IllegalArgumentException 추가
```java
@ControllerAdvice
public class CommonExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ResponseVO> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {

        StringBuilder errors = new StringBuilder();
        ex.getBindingResult().getAllErrors().forEach(error ->
                errors.append(error.getDefaultMessage()).append("; "));

        return new ResponseEntity<>(ResponseVO.builder()
                .status("fail")
                .message(errors.toString())
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HandlerMethodValidationException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ResponseVO> handleMethodArgumentNotValidException(HandlerMethodValidationException ex) {

        StringBuilder errors = new StringBuilder();
        ex.getAllErrors().forEach(error ->
                errors.append(error.getDefaultMessage()).append("; "));

        return new ResponseEntity<>(ResponseVO.builder()
                .status("fail")
                .message(errors.toString())
                .build(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ResponseEntity<ResponseVO> handleMethodArgumentNotValidException(IllegalArgumentException ex) {

        String errorMessage = ex.getMessage();

        return new ResponseEntity<>(ResponseVO.builder()
                .status("fail")
                .message(errorMessage)
                .build(), HttpStatus.BAD_REQUEST);
    }
}
```

### 5. 정리

| 테스트 시나리오                                            | POST        | GET<br>(VO 객체 사용) | GET<br/>(RequestParam) | GET<br/>(PathVariable) |
|-----------------------------------------------------|-------------|-------------------|------------------------|------------------------|
| 성공_올바른 ContentsTypeCode의 name 값을 사용했을 때 성공한다.       | X           | O                 | O                      | O                      |
| 실패_범위에서 벗어난 ContentsTypeCode의 name 값을 사용했을 때 실패한다.  | O           | O                 | O                      | O                      |
| 성공_올바른 ContentsTypeCode의 code 값을 사용했을 때 성공한다.       | O           | O                 | O                      | O                      |
| 실패_범위에서 벗어난 ContentsTypeCode의 code 값을 사용했을 때 실패한다.  | O           | O                 | O                      | O                      |
| 실패_올바른 ContentsTypeCode의 소문자 값을 보냈을 때 성공한다.         | X           | O                 | O                      | O                      |
| 실패_ContentsTypeCode의 null 값을 사용했을 때 실패한다.           | X<br/>(200) | X<br/>(200)       | O<br/>(400)            | O<br/>(404)            |
| 실패_ContentsTypeCode의 index 값을 사용했을 때 실패한다.          | X           | O                 | O                      | O                      |
| 성공_범위에서 벗어난 ContentsTypeCode을 사용했을 때 응답에 에러 문구가 있다. | X           | O                 | O                      | O                      |

POST의 결과를 보면 아무 처리도 안했을 떄와 동일한 것을 볼 수 있습니다.

Converter는 @RequestParam, @PathVariable에서 타입을 변경할 때 해당 타입에 대한 Converter가 있으면 그 Converter가 동작하게 됩니다. 

하지만 @RequestBody는 MappingJackson2HttpMessageConverter가 기본적으로 동작하기 때문에 별도의 ContentsTypeCodeConverter가 동작하지 않습니다.

아래 표에서 ContentsTypeCodeConverter 동작 여부를 표시했습니다.

| 테스트 시나리오                                            | POST        | GET<br>(VO 객체 사용) | GET<br/>(RequestParam) | GET<br/>(PathVariable) |
|-----------------------------------------------------|-------------|-------------------|------------------------|------------------------|
| 성공_올바른 ContentsTypeCode의 name 값을 사용했을 때 성공한다.       | X           | O                 | O                      | O                      |
| 실패_범위에서 벗어난 ContentsTypeCode의 name 값을 사용했을 때 실패한다.  | X           | O                 | O                      | O                      |
| 성공_올바른 ContentsTypeCode의 code 값을 사용했을 때 성공한다.       | X           | O                 | O                      | O                      |
| 실패_범위에서 벗어난 ContentsTypeCode의 code 값을 사용했을 때 실패한다.  | X           | O                 | O                      | O                      |
| 실패_올바른 ContentsTypeCode의 소문자 값을 보냈을 때 성공한다.         | X           | O                 | O                      | O                      |
| 실패_ContentsTypeCode의 null 값을 사용했을 때 실패한다.           | X<br/>(200) | X<br/>(200)       | X<br/>(400)            | X<br/>(404)            |
| 실패_ContentsTypeCode의 index 값을 사용했을 때 실패한다.          | X           | O                 | O                      | O                      |
| 성공_범위에서 벗어난 ContentsTypeCode을 사용했을 때 응답에 에러 문구가 있다. | X           | O                 | O                      | O                      |

POST의 경우엔 @RequestBody를 사용했기 때문에 Converter를 모두 거치지 않았고 나머지의 경우엔 null case를 제외하고 모두 Converter를 거쳤습니다.

Converter에 타입을 String으로 명시했기 때문에 null case는 Converter를 거치지 않았지만 응답코드는 200, 404로 상이했습니다.

Converter를 단독으로 사용하면 (1) RequestBody에서 동작하지 않는 점과 (2) enum 타입마다 Converter를 만들어줘야하고 (3) formatter 등록을 다 해줘야해서 불필요한 코드가 많아진다는 한계점이 있습니다.

(ConverterFactory를 사용하면 (2)번, (3)번 한계점을 해결할 수 있습니다. 이 부분은 아래에서 서술합니다.)

## Jackson의 Deserializer와 ConverterFactory
Spring에선 `@RequestBody`를 사용했을 때와 `@RequestParam`, `@PathVariable`을 사용했을 때 각각 다른 방식으로 동작합니다.

@RequestBody를 사용하면 `HttpMessageConverter`가 HTTP의 Request Body 내의 데이터를 객체로 변환합니다. (역직렬화)

Content-Type이 application/json일 경우 `MappingJackson2HttpMessageConverter`를 사용해서 역직렬화를 하고 `MappingJackson2HttpMessageConverter`는 `ObjectMapper`를 사용해서 동작합니다.

`@PathVariable`, `@RequestParam`을 사용했을 때는 타입에 대한 `Converter(org.springframework.core.convert.converter.Converter)`가 기본적으로 동작합니다.

따라서 아무런 처리를 하지 않으면 Spring이 기본적으로 제공하는 검증 로직을 사용하게 되지만 필요할 경우 <u>특정 타입에 대해서 개발자가 원하는 검증 로직을 만들어서 사용할 수 있습니다.</u>

`@RequestBody`의 경우 `Jackson2ObjectMapperBuilderCustomizer`의 `customize` 메소드를 오버라이딩해서 우리가 원하는 Serializer/Deserializer를 등록해서 사용할 수 있습니다.

`@RequestParam`, `@PathVariable`의 경우 특정 타입의 `ConverterFactory`를 구현하고 `FormatterRegistry`의 `addConverter` 메소드를 사용해서 Converter를 등록해서 사용할 수 있습니다.

윗 글에 `Converter`에서는 특정 Enum 타입인 `ContentsTypeCode`을 사용했지만 ConverterFactory에서는 Enum 타입 자체에 대한 처리를 합니다. 

- Converter : `public class ContentsTypeCodeConverter implements Converter<String, ContentsTypeCode>`
- ConverterFactory : `public class EnumConverterFactory implements ConverterFactory<String, Enum>`

<h3>요약</h3>
- `@RequestBody`
  - `@RequestBody`를 사용하면 `HttpMessageConverter`가 Request Body를 역직렬화하고 Content-Type이 application/json일 경우 `MappingJackson2HttpMessageConverter`가 동작합니다.
  - 특정 타입에 대해서 별도의 검증 로직을 사용하고 싶다면 `Serializer/Deserializer`를 구현하고 `Jackson2ObjectMapperBuilderCustomizer`의 `customize` 메소드에서 등록하여 사용할 수 있습니다.
- `@PathVariable`, `@RequestParam`
  - `@PathVariable`, `@RequestParam`을 사용하면 타입에 대한 `Converter`가 기본적으로 동작합니다.
  - 특정 타입에 대해서 별도의 검증 로직을 사용하고 싶다면 `ConverterFactory`를 구현하고 `FormatterRegistry`의 `addConverter` 메소드를 사용해서 Converter를 등록하여 사용할 수 있습니다.

### 1. EnumDeserializer 작성
```java
public class EnumDeserializer extends StdDeserializer<Enum <? extends Enum>> implements ContextualDeserializer {

    public EnumDeserializer(Class<?> vc) {
        super(vc);
    }

    @Override
    public Enum<? extends Enum> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext) throws IOException {
        Class<? extends Enum> enumType = (Class<? extends Enum>) this._valueClass;
        JsonNode jsonNode = jsonParser.getCodec().readTree(jsonParser);
        String input = jsonNode.asText().trim().toUpperCase();

        if(ObjectUtils.isEmpty(input))
            throw new IllegalArgumentException("유효하지 않은 ContentsTypeCode 입니다.");

        boolean isPlainEnum = EnumUtils.isValidEnum(enumType, input);

        if(isPlainEnum){
            return Enum.valueOf(enumType, input);
        } else {
            boolean isEnumCode = Arrays.stream(enumType.getMethods()).anyMatch(method -> "code".equals(method.getName()));

            if(isEnumCode){
                Enum mathcEnum = null;
                String enumValue;
                for(Enum constant : enumType.getEnumConstants()){
                    try {
                        enumValue = (String) constant.getClass().getMethod("code").invoke(constant);
                        if(enumValue.equals(input.trim().toUpperCase())){
                            mathcEnum = constant;
                            break;
                        }
                    } catch (Exception e) {
                        throw new IllegalArgumentException("유효하지 않은 ContentsTypeCode 입니다.");
                    }
                }

                if(mathcEnum == null)
                    throw new IllegalArgumentException("유효하지 않은 ContentsTypeCode 입니다.");

                return Enum.valueOf(enumType,mathcEnum.name());
            } else {
                throw new IllegalArgumentException("유효하지 않은 ContentsTypeCode 입니다.");
            }
        }
    }

    @Override
    public JsonDeserializer<?> createContextual(DeserializationContext context, BeanProperty beanProperty) {
        return new EnumDeserializer(beanProperty.getType().getRawClass());
    }
}
```

### 2. Jackson2ObjectMapperBuilderCustomizer를 상속받은 클래스 작성
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

### 3. ConverterFactory 작성
```java
@Component
public class EnumConverterFactory implements ConverterFactory<String, Enum> {

    @Override
    public <T extends Enum> Converter<String, T> getConverter(Class<T> enumType) {
        return new EnumConverter(getEnumType(enumType));
    }

    private Class<?> getEnumType(Class classType) {
        Class<?> enumType = classType;
        while (enumType != null && !enumType.isEnum()) {
            enumType = enumType.getSuperclass();
        }
        if (enumType == null) {
            throw new IllegalArgumentException("This type " + enumType.getName() + " is not an enum type.");
        }
        return enumType;
    }

    private class EnumConverter<T extends Enum> implements Converter<String, T> {

        private final Class<T> enumType;

        private EnumConverter(Class<T> enumType) {
            this.enumType = enumType;
        }

        @Override
        public T convert(String input) {
            input = input.trim().toUpperCase();

            if(ObjectUtils.isEmpty(input))
                throw new IllegalArgumentException("유효하지 않은 ContentsTypeCode 입니다.");

            boolean isPlainEnum = EnumUtils.isValidEnum(enumType, input);

            if (isPlainEnum) {
                return (T) Enum.valueOf(this.enumType, input);
            } else {
                boolean isEnumCode = Arrays.stream(enumType.getMethods()).anyMatch(method -> "code".equals(method.getName()));

                if (isEnumCode) {
                    Enum mathcEnum = null;
                    String enumValue;
                    for (Enum constant : enumType.getEnumConstants()) {
                        try {
                            enumValue = (String) constant.getClass().getMethod("code").invoke(constant);
                            if (enumValue.equals(input.trim().toUpperCase())) {
                                mathcEnum = constant;
                                break;
                            }
                        } catch (Exception e) {
                            throw new IllegalArgumentException("유효하지 않은 ContentsTypeCode 입니다.");
                        }
                    }

                    if (mathcEnum == null)
                        throw new IllegalArgumentException("유효하지 않은 ContentsTypeCode 입니다.");

                    return (T) Enum.valueOf(enumType, mathcEnum.name());
                } else {
                    throw new IllegalArgumentException("유효하지 않은 ContentsTypeCode 입니다.");
                }
            }
        }
    }
}
```
### 4. Formatter 일괄 등록
```java
@Configuration
@RequiredArgsConstructor
public class FormatterConfiguration implements WebMvcConfigurer {

    private final Formatter<?>[] formatters;

    private final Converter<?, ?>[] converters;

    private final ConverterFactory<?, ?>[] converterFactories;

    @Override
    public void addFormatters(FormatterRegistry formatterRegistry) {
        if (!ObjectUtils.isEmpty(formatters)) {
            for (final Formatter<?> formatter : formatters){
                formatterRegistry.addFormatter(formatter);
            }
        }

        if (!ObjectUtils.isEmpty(converters)) {
            for (final Converter<?, ?> converter : converters){
                formatterRegistry.addConverter(converter);
            }
        }

        if (!ObjectUtils.isEmpty(converterFactories)) {
            for (final ConverterFactory<?, ?> converterFactory : converterFactories){
                formatterRegistry.addConverterFactory(converterFactory);
            }
        }
    }
}
```

### 5. NULL 케이스를 처리하기 위한 어노테이션 작성
- RequestVO에 필요할 경우 `@NotNull` 적용
```java
@Getter
@ToString
@Builder
public class RequestVO {
    @NotNull
    private ContentsTypeCode contentsTypeCode;
    private String title;
    private String contents;
}
```

- Controller에 `@Valid` 적용
```java
@RestController
@RequestMapping("/v1")
@RequiredArgsConstructor
public class SimpleController {

    @PostMapping("/post/request")
    public ResponseEntity methodPostRequest(@RequestBody @Valid RequestVO requestVO) {
        return ResponseEntity.ok().body(requestVO);
    }

    @GetMapping("/get/request")
    public ResponseEntity methodGetRequest(@Valid RequestVO requestVO) {
        return ResponseEntity.ok().body(requestVO);
    }

    @GetMapping("/get/request/request-param")
    public ResponseEntity methodGetRequestRequestParam(@RequestParam ContentsTypeCode contentsTypeCode) {
        return ResponseEntity.ok().body(contentsTypeCode);
    }

    @GetMapping("/get/request/path-variable/{contentsTypeCode}")
    public ResponseEntity methodGetRequestPathVariable(@PathVariable ContentsTypeCode contentsTypeCode) {
        return ResponseEntity.ok().body(contentsTypeCode);
    }
}
```

### 6. 부록 : Jackson과 ObjectMapper
Jackson은 spring-boot-starter-web에 포함되어 있기 때문에 별도의 의존성을 추가할 필요가 없습니다.

주로 `ObjectMapper`를 사용해서 JSON 요청을 객체로 직렬화 하거나 역직렬화 할 때 사용합니다.

**JSON 요청을 Java 객체로 역직렬화**
```java
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class MyController {
    @Autowired
    private ObjectMapper objectMapper;

    @PostMapping("/createUser")
    public ResponseEntity<User> createUser(@RequestBody String json) {
        User user = objectMapper.readValue(json, User.class);
        // user 객체를 사용하여 작업을 수행
        return ResponseEntity.ok(user);
    }
}
```

**Java 객체를 JSON 응답으로 직렬화**
```java
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
public class MyController {
    @Autowired
    private ObjectMapper objectMapper;

    @GetMapping("/getUser")
    public ResponseEntity<String> getUser() {
        User user = new User("John", "Doe");
        String json = objectMapper.writeValueAsString(user);
        return ResponseEntity.ok(json);
    }
}
```

앞서 설명한 것처럼 Spring은 Contents-Type이 application/json 이면 ObjectMapper를 사용해서 Request Body를 VO로 역직렬화 하거나 VO를 Response Body로 직렬화할 때 사용합니다.

요청 값의 경우 `@RequestBody`를 사용한 곳에서 동작하며 응답 값의 경우 `@ResponseBody`를 사용한 곳에서 동작하는데 `@RestController`는 `@ResponseBody`를 포함하고 있습니다.

직렬화를 할 때는 Serializer를 사용하고 역직렬화를 할 때는 Deserializer를 사용하는데 개발자가 직접 만들어서 사용할 수 있도록 `Jackson2ObjectMapperBuilderCustomizer`를 제공하고 있습니다.

따라서 특정 타입에 대해서 공통 처리를 하거나 원하는 검증 로직을 사용하고 싶다면 `Jackson2ObjectMapperBuilderCustomizer`를 사용해서 `Serializer/Deserializer`를 등록해서 사용할 수 있습니다.

이 외에도 `@JsonInclude`, `@JsonIncludeProperties`, `@JsonIdentityInfo`, `@JsonTypeInfo`, `@JsonValueInstantiator`을 사용해서 필요한 정보만 직렬화하거나 제외할 수 있습니다.

### 7. 정리
| 테스트 시나리오                                            | POST | GET<br>(VO 객체 사용) | GET<br/>(RequestParam) | GET<br/>(PathVariable) |
|-----------------------------------------------------|------|-------------------|------------------------|------------------------|
| 성공_올바른 ContentsTypeCode의 name 값을 사용했을 때 성공한다.       | O    | O                 | O                      | O                      |
| 실패_범위에서 벗어난 ContentsTypeCode의 name 값을 사용했을 때 실패한다.  | O    | O                 | O                      | O                      |
| 성공_올바른 ContentsTypeCode의 code 값을 사용했을 때 성공한다.       | O    | O                 | O                      | O                      |
| 실패_범위에서 벗어난 ContentsTypeCode의 code 값을 사용했을 때 실패한다.  | O    | O                 | O                      | O                      |
| 실패_올바른 ContentsTypeCode의 소문자 값을 보냈을 때 성공한다.         | O    | O                 | O                      | O                      |
| 실패_ContentsTypeCode의 null 값을 사용했을 때 실패한다.           | O    | O                 | O                      | O                      |
| 실패_ContentsTypeCode의 index 값을 사용했을 때 실패한다.          | O    | O                 | O                      | O                      |
| 성공_범위에서 벗어난 ContentsTypeCode을 사용했을 때 응답에 에러 문구가 있다. | O    | O                 | O                      | O                      |


최종적으로 Jackson과 ConverterFactory를 사용해서 enum 타입을 처리했습니다.

enum의 개수만큼 클래스를 생성 하지 않아도 되고 검증할 곳에 @Enum과 같은 어노테이션을 사용하지 않아도 되기 때문에 누락의 위험성도 사라졌습니다.

검증 로직은 EnumDeserializer와 EnumConverterFactory 클래스 안에 있기 때문에 쉽게 찾고 수정할 수 있습니다.

그리고 별도의 클래스 몇개만 더 만들면 enum 뿐만 아니라 다양한 타입을 공통 처리할 수 있게 되었습니다.

단점이라면 `ignoreCase()`과 같은 옵션 값을 받기는 어려워서 상황에 따른 처리를 할 수 없다는 점이 있습니다. 


## 마무리

Enum 타입을 처리하기 위해서 4가지의 방법을 정리해보았고 첫 페이지에서 정했던 목표를 기준으로 각 방법의 장단점을 살펴보겠습니다.

- 목표
  - ContentsTypeCode는 name과 code 값을 한 쌍으로 갖고 있고 <u>code에 대한 값도 수용도 하고 싶습니다.</u>
  - <u>대소문자를 구분하지 않고 수용하고 싶습니다.</u>
  - 001,002,003,004 등 code 값과 혼동될 수 있는 <u>index 값은 수용하고 싶지 않습니다.</u>
  - <u>상황에 따라 null 값을 허용하고 싶지 않습니다.</u>
  - 범위를 벗어났을 때 응답을 수신하는 쪽에서 에러 원인을 파악하기 쉽도록 <u>에러 메세지를 만들어서 반환하고 싶습니다.</u>

#### (1) EnumValidator (Test Coverage : 100%)
- 모든 케이스를 성공하고 ignoreCase, isNullable 등 여러 옵션 값을 지정해서 확장성 있게 사용할 수 있습니다.
- 사용할 필드마다 @Enum을 명시해줘야 하기 때문에 누락될 위험성이 있습니다.
- @RequestParam이나 @PathVariable처럼 VO가 아닌 파라미터에서 바로 사용할 때 코드가 불필요하게 길어져서 가독성이 떨어지게 됩니다. (Converter를 사용하면 이 부분은 해결할 수 있습니다.)
- Enum 타입을 직접 사용하지 않고 일반 타입을 사용하기 때문에 Service Layer에서 사용할 때 Enum 타입으로 변환해주는 별도의 로직이 필요합니다.

#### (2) Converter (Test Coverage : 90%)
- @RequestBody에서 동작하지 않아서 POST 일 때 실패 케이스가 많습니다.
- Enum 타입마다 Converter를 만들고 등록해줘야 합니다.

#### (3) Jackson의 Deserializer & (4) ConverterFactory (Test Coverage : 100%)
- 모든 케이스를 만족하며 검증 로직을 쉽게 찾고 수정할 수 있습니다.
- @RequestBody는 Deserializer를 거치고 @RequestParam, @PathVariable은 ConverterFactory를 거치게 됩니다.
- enum 개수만큼 클래스를 생성하지 않아도 되고 검증할 필드를 따로 지정하지 않아도 되서 누락의 위험성이 없습니다.
- ignoreCase, isNullable 등 여러 옵션 값을 사용하지 못합니다.

결과적으로 Enum 타입을 처리하기 위해선 Jackson의 `Deserializer`와 `ConverterFactory`만 알고 있으면 됩니다.

하지만 Deserializer와 ConverterFactory를 포함해서 @Enum과 Validator를 활용한 처리 방법, Converter는 Enum 타입 뿐만 아니라 <u>여러 상황에서 활용될 수 있는 기법이기 때문에 알고 있으면 좋을 것 같아서 같이 정리해봤습니다.</u>

예를들어 String 타입으로 오는 핸드폰 번호에 대한 유효성 검증을 하고 싶다면 @Enum과 Validator를 활용한 방법이 가장 적절할 수 있습니다.

Spring에서 사용할 수 있는 여러 기법들을 알고 상황에 맞게 사용할 수 있어야 합니다.

---

Annotation 만들어서 처리하기
===
@NotNull, @JsonInclude 등 Spring, Jakarta, Jackson에서 기본적으로 제공하는 annotation을 사용할 수 있지만 개발자가 직접 annotation을 만들고 사용할 수 있습니다.

아래는 몇가지 사례만 정리해두었고 annotation과 Aspect를 활용한 방법의 원리를 이해하고 특정한 상황에서 적절하게 사용할 수 있어야 합니다.

## 1. @annotation을 사용한 특정 지점 지정 (JoinPoint)
annotation으로 특정 지점을 지정하고 그 지점에서 동작을 정의할 수 있습니다.

예를들어 `@NoLogging` 이라는 Annotation을 만들어주고 @Target에 맞는 곳에 사용하고 @Before, @After 등 Advice와 관련된 동작을 정의하는 곳에서 사용할 수 있습니다.

annotation을 단순한 지점을 지정할 때 사용됩니다.

```java
// annotation 생성
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface NoLogging {}
```

```java
// annotation 사용
@Before("(execution(* yong.config..*.*(..))"
        + " or execution(* yong.controller..*.*(..)))"
        + " && !@annotation(yong.annotation.NoLogging)")
public void beforeMethod(final JoinPoint joinPoint) {
    // ...
}
```

```java
// annotation 지정
@Service
@RequiredArgsConstructor
public class SimpleService {
    @NoLogging
    public ResponseEntity<String> method(RequestVO requestVO) {
        ...
    }
}
```
## 2. @Around를 사용한 특정 동작 지정
공통 처리를 하기 위해서 annotation을 만들고 @Around를 사용해서 어떤 동작을 할지 정의할 수 있습니다.

만든 annotation을 사용해서 동작할 지점을 지정하면 해당 지점에서 정의해놓은 코드가 동작합니다.

예를들어 금칙어 검사나 특정한 로직을 공통적으로 적용해야할 필드에 사용합니다.

@Enum과 EnumValidator와 유사한 방식이지만 @Constraint을 사용하지는 않습니다.

예제 코드에선 금칙어 그룹을 지정하고 금칙어 체크를 할 필드를 지정하는 방법입니다.

- @ForbiddenWordsMethod : method 단위에서 사용되며 금칙어 그룹을 정합니다.
- @ForbiddenWordsField : field, parameter 단위에서 사용되며 금칙어 체크를 할 필드를 정합니다.

```java
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ForbiddenWordsMethod {
    String group();
}
```

```java
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
public @interface ForbiddenWordsField {}
```

```java
@Aspect
@Component
@RequiredArgsConstructor
public class ForbiddenWordsAspect {
    private final ForbiddenWordsService forbiddenWordsService;
    
    @Around("@annotation(forbiddenWordsMethod)")
    public Object forbiddenWordsMethod(final ProceedingJoinPoint joinPoint, ForbiddenWordsMethod forbiddenWordsMethod) throws Throwable {
        String group = forbiddenWordsMethod.group();
        List<Object> fields = new ArrayList<>();
        
        for (Object arg : joinPoint.getArgs()) {
            if(String.class.equals(arg.getClass())
                    && Arrays.stream(((MethjodSignature) joinPoint.getSignature())
                                              .getMethod()
                                              .getParameters())
                    .filter(parameter -> parameter.isAnnotationPresent(ForbiddenWordsField.class))
                    .count() == 1) {
                fields.add(arg);
            } else {
                for(Field field : arg.getClass().getDeclaredFields()) {
                    if(field.isAnnotationPresent(ForbiddenWordsField.class)) {
                        field.setAccessible(true);
                        fields.add(field.get(arg));
                    }
                }
            }
        }

        ForbiddenWordsValidResponse forbiddenWordsValidResponse = forbiddenWordsService.validForbiddenWords(group, fields);
        
        if(forbiddenWordsValidResponse.getStatus().equals("fail")) {
            return ResponseUtility.createFailResponse(
                    CommonResponseVO.builder()
                            .message(forbiddenWordsValidResponse.getMessage())
                            .build());
        } else {
            return joinPoint.proceed();
        }
    }
}
```

```java
@Service
@RequiredArgsConstructor
public class ForbiddenWordsService {
    private final ForbiddenWordsRepository forbiddenWordsRepository;
    
    public ForbiddenWordsValidResponse validForbiddenWords(String group, List<Object> fields) {

        List<String> forbiddenWords = forbiddenWordsRepository.findForbiddenWordsByGroup(group);

        LinkedHashSet<String> forbiddenWordsSet = new LinkedHashSet<>(forbiddenWords);
        
        Trie trie = Trie.builder()
                .addKeywords(forbiddenWordsSet)
                .build();

        List<String> forbiddenWords = fields.stream()
                .filter(Objects::nonNull)
                .flatMap(field -> trie.parseText(field.toString().replaceAll("<[^>]*>", ""))
                        .stream()
                        .map(Emit::getKeyword))
                .distinct()
                .toList();
        
        return ForbiddenWordsValidResponse.builder()
                .status(forbiddenWords.isEmpty() ? "success" : "fail")
                .message(forbiddenWords.isEmpty() ? "" : "금칙어가 포함되어 있습니다.")
                .build();
    }
}
```

## 3. Serializer/Deserializer와 같이 사용

Serializer/Deserializer에 대한 동작을 별도의 클래스에 정의해두고 @JsonSerialize, @JsonDeserialize를 사용해서 특정 타입에 대한 동작을 정의할 수 있습니다.

```java
// annotation 생성
@Target({ElementType.FIELD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@JacksonAnnotationsInside
@JsonSerialize(using = CustomSerializer.class)
public @interface CustomAnnotation {
    String value() default "";
}
```

```java
// annotation 동작 정의
public class CustomSerializer extends StdSerializer<CustomAnnotation> implements ContextualSerializer {
    String value;
    
    protected CustomSerializer() {
        super(String.class);
    }
    
    protected CustomSerializer(String value) {
        super(String.class);
        this.value = value;
    }
    
    @Override
    public void serialize(CustomAnnotation value, JsonGenerator gen, SerializerProvider provider)  {
        gen.writeString(value.value());
    }
}
```

```java
// annotation 지정
public class RequestVO {
    @CustomAnnotation("custom")
    private String custom;
}
```

---

Logging
===

기본적으로 함수 시작 전, 종료 후, 에러 발생 후 시점을 기준으로 자동으로 로그를 생성하도록 Aspect를 만들어서 사용할 수 있습니다.

execution() 안에서 로그를 자동으로 남길 범위를 정할 수 있습니다.

로깅이 필요하지 않은 함수의 경우 @NoLogging 어노테이션을 만들어서 처리할 수 있습니다. (민감 정보를 갖고 있거나 로깅이 필요하지 않은 함수 등)

| Advice         | 실행 시점   | 설명                                                    |
|----------------|---------|-------------------------------------------------------|
| @Before        | 함수 시작 전 | 함수가 속한 클래스 이름, 함수 이름, 함수로 들어온 파라미터(args) 등을 로그로 남깁니다. |
| @AfterReurning | 함수 종료 후 | 함수가 속한 클래스 이름, 함수 이름, 함수가 반환한 값(args) 등을 로그로 남깁니다.    |
| @AfterThrowing | 에러 발생 후 | 함수가 속한 클래스 이름, 함수 이름, 에러 메시지 등을 로그로 남깁니다.             |

```java
@Component
@Aspect
@Slf4j
public class LogAspect {
    private static final String CLASS_LOG_FORMAT = "Class Name : [";
    private static final String METHOD_LOG_FORMAT = "Method Name : [";
    private static final Logger LOGGER = LoggerFactory.getLogger(LogAspect.class);

    @Before("(execution(* yong.exception..*(..))"
            + " || execution(* yong.controller..*(..)))"
            + " || execution(* yong.service..*(..)))"
            + " && !@annotation(* yong.annotation.NoLogging)")
    public void beforeMethod(final JoinPoint joinPoint) {
        HttpServletRequest request =
                ObjectUtils.isEmpty(RequestContextHolder.getRequestAttributes())
                        ? null
                        : ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        String declaringTypeName = joinPoint.getSignature().getDeclaringTypeName();
        String name = joinPoint.getSignature().getName();
        String args = Arrays.toString(joinPoint.getArgs());

        StringBuilder logStringBuilder = new StringBuilder();
        String logInfo = logStringBuilder
                .append("원하는 LOG 내용")
                .toString();

        LOGGER.info("{}", logInfo);
    }

    @AfterReturning(
            pointcut = "(execution(* yong.exception..*(..))"
                    + " || execution(* yong.controller..*(..)))"
                    + " || execution(* yong.service..*(..)))"
                    + " && !@annotation(* yong.annotation.NoLogging)",
            returning = "result")
    public void afterMethod(final JoinPoint joinPoint, final Object result) {
        HttpServletRequest request =
                ObjectUtils.isEmpty(RequestContextHolder.getRequestAttributes())
                        ? null
                        : ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        String declaringTypeName = joinPoint.getSignature().getDeclaringTypeName();
        String name = joinPoint.getSignature().getName();

        StringBuilder logStringBuilder = new StringBuilder();
        String logInfo = logStringBuilder
                .append("원하는 LOG 내용")
                .toString();

        LOGGER.info("{}", logInfo);
    }

    @AfterThrowing(
            pointcut = "(execution(* yong.exception..*(..))"
                    + " || execution(* yong.controller..*(..)))"
                    + " || execution(* yong.service..*(..)))"
                    + " && !@annotation(* yong.annotation.NoLogging)",
            throwing = "exception")
    public void afterThrowing(final JoinPoint joinPoint, final Exception exception) {
        HttpServletRequest request =
                ObjectUtils.isEmpty(RequestContextHolder.getRequestAttributes())
                        ? null
                        : ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        String declaringTypeName = joinPoint.getSignature().getDeclaringTypeName();
        String name = joinPoint.getSignature().getName();
        String exceptionMessage = exception.getMessage();

        StringBuilder logStringBuilder = new StringBuilder();
        String logInfo = logStringBuilder
                .append("원하는 LOG 내용")
                .toString();

        LOGGER.info("{}", logInfo);
    }
}
```

---

Interceptor
===

Interceptor는 Controller의 요청과 응답을 가로채서 특정한 동작을 수행할 수 있습니다.

Interceptor는 전체 애플리케이션에서 일관된 작업을 수행하고 로깅, 보안, 인증, 권한 부여 등과 같은 공통된 로직을 처리하는 데 유용합니다.

Interceptor가 `HandlerInterceptor` 인터페이스를 구현하고 preHandle(), postHandle(), afterCompletion() 메소드를 오버라이드하여 사용합니다. 

| 함수             | 동작시점                                        |
|----------------|---------------------------------------------|
| preHandle      | Controller 실행 이전                            |
| postHandle     | Controller 실행 이후                            |
| afterCompletion | View 처리 완료 이후 <br/>(RESTful API에서는 사용하지 않음) |


비슷한 기능으로는 Filter와 AOP가 있습니다.

| 항목          | 설명                                                                                                                                                                                                                                                               |
|-------------|------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------|
| Interceptor | - Spring MVC에서 제공되는 기능으로 HandlerInterceptor 인터페이스를 구현하여 생성<br/>- 컨트롤러의 요청과 응답을 가로채고 처리하는 데 사용<br/>- 주로 Spring MVC의 컨트롤러 로직 주변에서 요청 전/후 처리, 요청 핸들러 실행 전/후 처리, 예외 처리 등을 수행<br/>- 사용 사례 : 인증, 권한 부여 등과 같은 공통된 로직을 처리할 때 사용됩니다.                                      |
| Filter      | - Java Servlet 스펙에서 제공되는 기능으로 javax.servlet.Filter 인터페이스를 구현하여 생성<br/>- Servlet 컨테이너 수준에서 요청과 응답을 가로채고 처리하는 데 사용<br/>- Servlet 컨테이너의 모든 요청에 대해 적용되며, Spring 컨텍스트와는 별도로 동작<br/>- 사용 사례 : 인코딩 변환, 캐싱, 보안 필터 등과 같은 Servlet 컨테이너 수준의 처리를 하고자 할 때 사용                  |
| AOP         | - 관점 지향 프로그래밍의 개념을 기반으로 한 기술로, 메소드 실행 전/후 또는 메소드 실행 중에 특정한 작업을 수행<br/>- Spring AOP는 프록시를 사용하여 메소드 호출을 가로채고, 특정한 관심사(Aspect)를 적용<br/>- Interceptor와 비슷한 기능을 할 수 있지만 AOP는 비니지스를 중심으로 핵심 비즈니스 로직과는 별도로 관심사를 분리하여 코드를 구조화하고 유지보수성을 높일 때 사용<br/>- 사용 사례 : 로깅, 트랜잭션 관리 |

## CorsConfig
```java
@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("GET", "HEAD", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")
                .exposedHeaders("Content-Disposition")
                .allowCredentials(true)
                .maxAge(3600L);
    }
}
```

## HandlerInterceptor를 활용한 방법

```java
@Configuration
@EnabledWebMvc
public class InterceptorConfig implements WebMvcConfigurer {

    @Autowired
    private AuthenticationInterceptor authenticationInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(authenticationInterceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/health")
                .excludePathPatterns("/swagger-ui/**")
                .excludePathPatterns("/v1/auth/**");
    }
}
```

```java
@UtilityClass
public class HttpUrlConstant {

    public static final Map<HttpMethod, List<String>> NO_AUTH_SESSION_HTTP_URL = new EnumMap<>(HttpMethod.class);

    static {
        String[] getExcludeUrl = {
                "/v1/get/exclude/url"
        };

        String[] postExcludeUrl = {
                "/v1/post/exclude/url"
        };

        String[] putExcludeUrl = {
                "/v1/put/exclude/url"
        };

        String[] patchExcludeUrl = {
                "/v1/patch/exclude/url"
        };

        String[] deleteExcludeUrl = {
                "/v1/delete/exclude/url"
        };

        NO_AUTH_SESSION_HTTP_URL.put(HttpMethod.GET, new ArrayList<>());
        NO_AUTH_SESSION_HTTP_URL.put(HttpMethod.POST, new ArrayList<>());
        NO_AUTH_SESSION_HTTP_URL.put(HttpMethod.PUT, new ArrayList<>());
        NO_AUTH_SESSION_HTTP_URL.put(HttpMethod.PATCH, new ArrayList<>());
        NO_AUTH_SESSION_HTTP_URL.put(HttpMethod.DELETE, new ArrayList<>());

        addExcludeHttpPathUrl(HttpMethod.GET, getExcludeUrl);
        addExcludeHttpPathUrl(HttpMethod.POST, postExcludeUrl);
        addExcludeHttpPathUrl(HttpMethod.PUT, putExcludeUrl);
        addExcludeHttpPathUrl(HttpMethod.PATCH, patchExcludeUrl);
        addExcludeHttpPathUrl(HttpMethod.DELETE, deleteExcludeUrl);
    }

    private static void addExcludeHttpPathUrl(HttpMethod httpMethod, String[] patternList) {
        List<String> urlList = NO_AUTH_SESSION_HTTP_URL.get(httpMethod);
        Collections.addAll(urlList, patternList);
    }
}
```

```java
@Component
@RequiredArgsConstructor
public class AuthenticationInterceptor implements HandlerInterceptor {

    private final AntPathMatcher antPathMatcher = new AntPathMatcher();

    @Value("${token.connect.timeout}")
    private final int TOKEN_CONNECT_TIMEOUT;

    @Value("${token.read.timeout}")
    private final int TOKEN_READ_TIMEOUT;

    @Value("${cloud.aws.cognito.client-id}")
    private final String CLIENT_ID;

    @Value("${cloud.aws.cognito.region}")
    private final String REGION;

    @Value("${cloud.aws.cognito.user-pool-id}")
    private final String USER_POOL_ID;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        String sessionId = request.getHeader(HttpHeaderConstants.SESSION_ID);
        String authorization = request.getHeader(HttpHeaderConstants.AUTHORIZATION);

        if (HttpMethod.OPTIONS.name().equals(request.getMethod())) {
            return true;
        } else if (isExcludePattern(HttpMethod.valueOf(request.getMethod()), request.getRequestURI())) {
            if (!ObjectUtils.isEmpty(sessionId)) {
                if (ObjectUtils.isEmpty(authorization)) {
                    return true;
                }

                if (Pattern.matches("^Bearer .*", authorization)) {
                    authorization = authorization.replaceAll("^Bearer( )*", "");
                }

                SessionVO sessionUser = SessionUtility.getSessionVO(sessionId);
                if (sessionUser != null && this.verifyToken(authorization)) {
                    SessionUtility.setContextSession(sessionUser);
                }
            }
            return true;
        } else {
            if (ObjectUtils.isEmpty(sessionId) || ObjectUtils.isEmpty(authorization)) {
                throw new BusinessException("Authorization is required.");
            }

            if (Pattern.matches("^Bearer .*", authorization)) {
                authorization = authorization.replaceAll("^Bearer( )*", "");
            }

            if (!this.verifyToken(authorization)) {
                throw new BusinessException("Session is unauthorized.");
            }

            SessionVO sessionUser = SessionUtility.getSessionVO(sessionId);
            if (sessionUser == null) {
                throw new BusinessException("Session is expired.");
            }

            SessionUtility.setContextSession(sessionUser);
            return true;
        }
    }

    private boolean isExcludePattern(HttpMethod httpMethod, String requestUri) {
        List<String> uriList = HttpUrlConstant.NO_AUTH_SESSION_HTTP_URL.get(httpMethod);
        return uriList.stream().anyMatch(uri -> antPathMatcher.match(uri, requestUri));
    }

    private boolean verifyToken(String token) {
        try {
            JWTClaimsSet jwtClaimsSet = configurableJWTProcessor().process(token, null);
            String cognitoPoolUrl = String.format("https://cognito-idp.%s.amazonaws.com/%s", REGION, USER_POOL_ID);
            if (!jwtClaimsSet.getIssuer().equals(cognitoPoolUrl)
                    || !jwtClaimsSet.getAudience().get(0).equals(CLIENT_ID)
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

        URL url = new URL(String.format("https://cognito-idp.%s.amazonaws.com/%s", REGION, USER_POOL_ID));

        JWKSource jwkSource = new RemoteJWKSet(url, resourceRetriever);

        ConfigurableJWTProcessor configurableJWTProcessor = new DefaultJWTProcessor();
        JWSKeySelector jwsKeySelector = new JWSVerificationKeySelector(JWSAlgorithm.RS256, jwkSource);

        configurableJWTProcessor.setJWSKeySelector(jwsKeySelector);
        return configurableJWTProcessor;
    }
}
```


- Interceptor vs Filter

```

---

Redis와 Session
===
Redis는 key-value 구조의 data store로 disk가 아니라 memory에 저장되는 in-memory 방식입니다.

Sorted-set 구조로 실시간 순위표를 만들 때 사용하거나 TTL을 사용해서 Session을 관리할 때 사용합니다.

Redis의 대한 기본 설명은 [03 ApplicationModernization - Redis](https://github.com/justdoanything/self-study/blob/main/03%20ApplicationModernization.md#redis) 페이지에 정리했습니다.

## Docker를 사용한 간단한 Redis 설치 및 테스트
- ###  Redis 설치
  - `docker run -d -p 6379:6379 --name redis redis`
- ### Redis cli 접속
  - `docker exec -it redis redis-cli`
- ### Redis cli 테스트
  - 생성 : `set ${key} ${value}`
  - 조회 : `get ${key}` 
  - 전체 조회 : `keys *`
  - 삭제 : `del ${key}`
  - 전체 삭제 : `flushall`
  - 만료일 조회 : `ttl ${key}`

### RedisConfig
```java
@Configuration
@EnableRedisRepositories
public class RedisConfig {

    @Value(value = "${spring.redis.host}")
    private String redisHost;

    @Value(value = "${spring.redis.port}")
    private int redisPort;

    @Bean
    public LettuceConnectionFactory connectionFactory() {
        return new LettuceConnectionFactory(new RedisStandaloneConfiguration(redisHost, redisPort));
    }

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

### SessionRepository
```java
@Repository
public class SessionRepository {
    @Value(value = "${spring.redis.session-ttl}")
    private int redisSessionTtl;

    @Autowired
    private RedisTemplate<String, SessionVO> redisSessionTemplate;

    @Resource(name = "redisSessionTemplate")
    ValueOperations<String, SessionVO> valueOperations;

    public void createSession(String key, SessionVO sessionVO) {
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
```

### SessionUtil
```java
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
```

---


@Transactional
===

---

Flyway
===
```java
@Configuration
@RequiredArgsConstructor
@Profile({"local", "dev"})
public class FlywayConfig {

    @Value("${spring.datasource.url}")
    private final String url;

    @Value("${spring.datasource.user}")
    private final String user;

    @Value("${spring.datasource.password}")
    private final String password;

    @Value("${spring.profiles.active}")
    private final String profile;

    @PostConstruct
    public void migrate() {
        Flyway flyway = Flyway.configure()
                .dataSource(url, user, password)
                .outOfOrder(true)
                .locations("classpath:db/migration"
                        , "classpath:db/data")
                .baselineOnMigrate(true)
                .cleanDisabled(false)
                .baselineVersion("0.0")
                .cleanOnValidationError(
                        "local".equals(profile)
                                || "dev".equals(profile))
                .load();

        flyway.migrate();
    }
}
```

---

Feign
===
decoder

Retryer

---

HandlerMethodArgumentResolver
===


```java
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface RequestBind {
    boolean required() default true;
}
```
```java
@Component
@RequiredArgsConstructor
public class RequestBindResolver implements HandlerMethodArgumentResolver {
    private final ObjectMapper mapper;

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.hasParameterAnnotation(RequestBind.class);
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
            RequestBind requestBind = methodParameter.getParameterAnnotation(RequestBind.class);
            if(requestBind != null && requestBind.required()) {
                HttpServletRequest httpServletRequest = nativeWebRequest.getNativeRequest(HttpServletRequest.class);
                ServletServerHttpRequest servletServerHttpRequest = new ServletServerHttpRequest(httpServletRequest);
                throw new HttpMessageNotReadableException(
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
                if(bindingResult.hasErrors()) {
                    throw new MethodArgumentNotValidException(methodParameter, bindingResult);
                }
            }
        }

        return resolver;
    }
}
```

---

Reference
===
- https://velog.io/@hsw0194/Spring-Boot에서-interface를-사용해야-할까
- https://gmoon92.github.io/spring/aop/2019/04/20/jdk-dynamic-proxy-and-cglib.html
- https://gmoon92.github.io/spring/aop/2019/01/15/aspect-oriented-programming-concept.html
- http://hayunstudy.tistory.com/53