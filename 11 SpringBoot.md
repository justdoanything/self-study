<!-- TOC -->
* [Intro](#intro)
* [AOP (Aspect Oriented Programming)](#aop--aspect-oriented-programming-)
  * [AOP란?](#aop란)
  * [핵심 관심사](#핵심-관심사)
  * [횡단 관심사](#횡단-관심사)
  * [Weaving](#weaving)
  * [AOP 요약](#aop-요약)
  * [Spring AOP 활용](#spring-aop-활용)
* [Resolver](#resolver)
* [VO와 DTO의 활용](#vo와-dto의-활용)
* [Request에 Enum 처리하기](#request에-enum-처리하기)
  * [@Enum과 EnumValidator](#enum과-enumvalidator)
  * [Converter](#converter)
  * [Code 값을 갖는 Enum 형태는?](#code-값을-갖는-enum-형태는)
  * [ConverterFactory](#converterfactory)
  * [Jackson의 Serializer와 Deserializer](#jackson의-serializer와-deserializer)
* [Exception 공통 처리 - Exception Advice](#exception-공통-처리---exception-advice)
  * [@ExceptionHandler](#exceptionhandler)
  * [@ControllerAdvice](#controlleradvice)
  * [@ControllerAdvice와 @RestControllerAdvice](#controlleradvice와-restcontrolleradvice)
* [Service Interface는 왜 만들어야 할까?](#service-interface는-왜-만들어야-할까)
* [자주 쓰이는 Controller Annotation](#자주-쓰이는-controller-annotation)
* [자주 쓰이는 Service Annotation](#자주-쓰이는-service-annotation)
* [Transaction](#transaction)
* [Redis](#redis)
* [Flyway](#flyway)
* [Reference](#reference)
<!-- TOC -->

---

Intro
===
Spring은 Library가 아닌 Framework이고 Spring이 구동되면서 우리가 짜놓은 코드를 사용한다.
Spring은 jackson 등 기본적으로 많인 기능들을 기본적으로 제공하며
특정한 상황에서 개발자가 원하는 기능을 추가할 수 있도록 확장성을 제공한다.

부록 : Spring과 SpringBoot의 차이점

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

Resolver
===
[Spring MVC](https://github.com/justdoanything/self-study/blob/main/10%20Spring.md#spring-mvc)를 정리한 자료를 보면 Spring이 어떻게 MVC 패턴으로 동작하고 변해왔는지 순서대로 알 수 있다. 이러한 변화과정은 개발자가 Spring을 사용할 때 좀 더 편하고 빠르게 개발할 수 있게 해준다. SpringBoot가 되면서 좀 더 빠르고 가벼워졌으며 개발자가 Spring을 사용할 때 반드시 해줘야했던 configure나 의존성 설정 등이 없어졌다.
예전에 Spring을 사용할 땐 tomcat을 따로 설치하고 Spring과 설정해줘야 동작했었는데 SpringBoot에선 embedded tomcat을 사용해서 별도의 설치나 설정 없이 바로 웹서버가 동작하고 있다.

이렇듯 대부분의 공통처리를 Spring에서 해주지만 상황에 따라 개발자가 customize해서 사용할 수 있게 동작한다. 예를들어 Resolver를 따로 정의하지 않아도 기본적인 동작들이 수행되고 아래에서 추가할 특정 타입, 상황에 따라 개발자가 원하는 로직을 사용하고 싶다면 Spring Handler를 상속받아 특정 함수를 Override해서 사용할 수 있다.

주로 프로젝트에선 Request/Response 공통 처리를 위해 Jackson의 Serializer/Deserializer를 정의해서 사용하곤 한다. 로그인이나 인증을 처리하기 위해서 특정 DTO가 들어왔을 때 동작할 함수를 따로 정의하거나 Enum 타입의 Request가 왔을 때 validation하는 동작을 따로 정의하거나 응답하는 객체의 Date 타입에 따라 특정 format을 지정하는 등 공통처리를 하고 싶을 때 많이 사용했었다.

특히 여러 사람이 동시에 개발하는 경우 공통 기능을 함수로 만들거나 어노테이션으로 만든다면 누락되거나 사용성에 문제가 있을 수 있기 때문에 Resolver로 처리하는 것이 안정적이다.

---

VO와 DTO의 활용
===
VO와 DTO의 사전적인(?) 의미를 보면 `VO`는 `Value Object`로 특정 값을 표현하기 위한 객체이고 `DTO`는 `Data Transfer Object`로 데이터 전달을 위한 객체라고 볼 수 있다.

사전적 의미대로 정석대로 사용한다면 VO는 특정 값을 표현하기 위한 `불변 객체`이기 때문에 객체가 다르더라도 가지고 있는 값이 같다면 같은 객체로 봐야한다.
따라서 `equals()`와 `hashCode()` 함수를 구현해줘야 한다. 대신 객체가 가진 값은 불변해야하기 때문에 setter 함수를 갖지 않고 객체 내 다른 비지니스 로직을 포함하고 있어도 된다.

DTO는 각 Layer 사이에서 데이터를 전달할 때 사용하는 객체라고 볼 수 있다. 갖고 있는 값이 같아도 같은 객체로 보지 않으며 setter/getter 함수를 모두 가질 수 있다.
대신 데이터 전달을 위한 객체이므로 다른 비지니스 로직을 포함하고 있지 않다.

내가 일했던 환경에서는 VO와 DTO를 나누지 않고 VO만 사용했으며 불변 객체로 사용하지 않았다. 대신 VO를 크게 3가지로 나눠서 사용했었다. 주로 Front-end와 Back-end를 나눠서 개발했고 React와 SpringBoot를 사용했다.
- RequestVO/ResponseVO
  - 주로 Front-end와 통신할 때 사용되며 Front-end로 부터 넘어오는 값들을 받을 때 사용하고 Front-end로 값들을 넘길 때 사용되기 때문에 VO 내의 필드 값은 Front-end에 맞춰져 있었다.
  - 안전하게 사용하기 위해서 RequestVO -> 일반VO(Service) -> 테이블VO(Repository) -> 일반VO(Service) -> ResponseVO 단계를 거치도록 개발했었다.
- 테이블VO
  - JPA의 Entity와 동일한 목적으로 Table은 주로 flyway로 관리하고 생성된 테이블에 매칭되는 테이블VO를 만들어서 사용했다.
  - SQL에서 Parameter로 사용되는 VO들을 SQL 하나당 하나를 만든다면 너무 많은 VO가 생성되기 때문에 주로 Service Layer에서 RequestVO, 일반VO로 테이블VO를 만들고 테이블VO를 SQL에서 사용했었다.
  - 하니의 테이블에 데이터를 CUD 하거나 Parameter로 사용되는 필드들이 하나의 테이블에 종속되어 있다면 하나의 테이블VO에 값을 set하고 SQL에서 사용하도록 했다.
  - 테이블VO와 대부분의 필드를 공유하는 경우에 테이블VO를 상속받는 VO를 만들어서 사용했고 따라서 테이블VO는 주로 SuperBuild를 사용했다.
- 일반VO
  - 테이블이 JOIN 되어 있거나 중간에 테이블VO로 모든 필드를 사용할 수 없는 경우 추가로 VO를 생성해서 사용했다.
  - 테이블VO를 상속해서 사용하지만 RequestVO/ResponseVO를 상속하지 않으며 다른 VO로 변환하는 toVO와 값 변환에 필요한 비지니스 로직을 포함하는 VO로 사용했다.
- 각 VO들의 사용성
  - 테이블VO는 테이블과 매칭되는 테이블로 별도의 toVO 함수나 비지니스 로직을 포함하지 않았다. 주로 setter/getter로만 사용하기 때문에 DTO의 사전적인 의미와 비슷하다고 생각했다.
  - RequestVO/ResponseVO는 주로 toVO 함수를 갖고 있으면서 Front-end와 Back-end 사이에 존재하는 필드 이름에 대한 차이와 단순한 값 변환헤 들어가는 로직들을 포함했다.
  - Front-end와 Back-end의 Dabatase 필드값이 같을 경우, RequestVO를 SQL에서 바로 사용하는 경우도 있는데 이는 유지보수에 안좋다고 생각했다.
    Front-end의 필드명이 바뀌는 경우 혹은 데이터베이스의 필드명이 변경될 경우 수정해야 하는 파일들이 너무 많게 되고 Request에서 들어오는 값들을 SQL에서 그대로 사용하는 것은 위험성이 높다고 생각했다.
  - toVO 함수를 사용한다면 Request에서 오는 값을 검증하는 로직 후 테이블VO를 만들 수 있으며 Front-end와 데이터베이스의 필드명 변경은 toVO 함수만 수정하면 되기 때문에 유지보수에 유리하다.
  - 회사에선 한 스토리로 Front-end와 Back-end를 한 사람이 개발하는 경우가 많아서 Front-end 필드와 데이터베이스 필드가 같아서 RequestVO를 SQL에서 바로 사용하는 사람들이 많았는데
    toVO를 사용하는 것이 안전하다고 생각해서 같은 필드 이름을 쓰더라도 꼭 toVO를 사용했었다.

---

Request에 Enum 처리하기
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
@Enum 어노테이션으로는 아래와 같은 @PathVariable로 Enum 클래스를 바로 사용하는 경우를 처리하지 못했고 이를 처리하기 위해서 각 Enum 클래스마다 Converter 만들어서 사용했었다.
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
- 사용하는 Enum 클래스만큼 Converter를 각각 만들어줘야하는 점
- VO에 각 필드에 @Enum 어노테이션을 넣어줘야 하는 점
- `Code 값을 갖는 Enum 형태`를 호환하지 못하는 점

## Code 값을 갖는 Enum 형태는?
Code Enum은 주로 공통코드를 관리하거나 한 변수가 name, code 혹은 다른 값들을 동시에 갖을 때 주로 사용했다. 

프로젝트에서 공통 코드는 데이터베이스에서 테이블로 관리하고 Back-end에서 공통 코드 조회 API를 만들어서 Front-end에서 사용할 수 있도록 했었다. 
공통 코드는 주로 name과 code값이 한 쌍으로 관리되며 Front-end에선 주로 name 값을 화면에 보여주고 내부적으론 code 값을 사용했다. 
Back-end에서는 공통 코드를 사용할 때마다 데이터베이스를 조회할 수 없었기 때문에 `OrderTypeCode`와 같은 Code Enum 클래스를 만들어서 사용했었다.
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

Back-end 코드 내부에서 `OrderTypeCode.001.equals(contents)`와 같이 사용한다면 `001`이 어떤 값을 의미하는지 한 눈에 알 수 없기 때문에 Code Enum 형태의 Enum을 만들어서 사용했다.

EnumValidator와 Converter에는 장단점이 존재한다.
- 장점
  - NULL 값을 호환한다는 점
  - excludeEnumType, message 등 좀 더 유연한 비교가 가능하다는 점
- 단점
  - 사용하는 Enum 클래스만큼 Converter를 각각 만들어줘야하는 점
  - VO에 각 필드에 @Enum 어노테이션을 넣어줘야 하는 점
  - `Code 값을 갖는 Enum 형태`를 호환하지 못하는 점

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

## Jackson의 Serializer와 Deserializer
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

Code Enum 형태를 처리하기 위해 만든 `EnumDeserializer`는 Enum 클래스를 처리하는 함수`public Enum<? extends Enum> deserialize(JsonParser jsonParser, DeserializationContext deserializationContext)`를 오버라이딩해서 커스터마이징 했다.
타입이 Enum 클래스일 경우 Enum 클래스 내에 value 함수가 있는지 확인하고 value 함수가 있으면 value 함수의 결과인 코드 값으로 매칭되는 값이 있는지 확인하도록 했다. https://d2.naver.com/helloworld/0473330 를 참고했다.
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

즉, FeedContentsTypeCode에서 기존에 비교하던 범위가 `[NORMAL, VOTE, SHARE, VIDEO]` 였다면 `[NORMAL, VOTE, SHARE, VIDEO, 001, 002, 003, 004]`로 코드 값까지 비교할 수 있도록 했다. VO 내에 타입에 Enum 클래스를 바로 사용하면 되기 때문에 필드마다 어노테이션을 사용해야 하는 불편함도 없어졌다.
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

Exception 공통 처리 - Exception Advice
===
- Java에서 기본적으로 사용되는 Exception은 여러 종류가 있고 필요할 땐 직접 Exception을 만들어서 사용할 수 있다. 이러한 Exception을 처리하기 위해서 try-catch문을 사용할 수도 있고 Controller, Service 와 같은 클래스 및 함수에서 throw Exception...을 사용하고 최상단에서 공통처리할 수 있다.
- 하지만 `@ControllerAdvice`, `@ExceptionHandler`를 사용하면 공통 클래스 내에서 예외 처리를 할 수 있다. 특정 Exception에 따라서 status, code, message 등을 다르게 처리하기에도 용이하다.

## @ExceptionHandler
- @Controller, @RestController가 적용된 Bean 내에서 발생하는 Exception을 잡아서 처리할 수 있도록 해준다.
- @ExceptionHandler를 단독으로 사용하면 하나의 Controller 클래스 안에 위치해야하고 해당 Controller에서 발생하는 Exception만 처리해야 하기 때문에 모든 Controller에 적용하기에는 무리가 있다.
- 따라서 주로 @ControllerAdvice와 함께 사용된다.

## @ControllerAdvice
- @Controller가 사용된 모든 Controller 클래스에서 발생하는 모든 Exception을 잡아서 처리할 수 있도록 해준다.
- Exception 종류별로 나눠서 처리할 수 있다.
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
## @ControllerAdvice와 @RestControllerAdvice
- 이름이 비슷한 2개의 어노테이션은 비슷한 기능을 수행하지만 약간의 차이점이 존재한다. 사용성엔 정해진 것이 없지만 주로 아래와 같이 사용된다.
  ```java
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
  @ControllerAdvice
  @ResponseBody
  public @interface RestControllerAdvice { ... }
  ```
  - 정의된 코드를 보면 `@RestControllerAdvice`는 `@ControllerAdvice` + `@ResponseBody` 인 것을 볼 수 있다.
  - `@ControllerAdvice`는 주로 Spring MVC에서 `@Controller`를 사용하고 있는 Controller에서 예외 처리를 하고 ModelAndView를 반환할 때 사용된다.
  - `@RestControllerAdvice`는 `@ResponseBody`를 포함하고 있기 때문에 주로 `@RestController`를 사용하고 있는 RESTful 기반의 Controller의 예외을 처리할 때 사용된다.
- 하지만 `@ExceptionHandler`는 어떤 응답 객체도 가질 수 있기 때문에 @ControllerAdvice로 예외를 수집하고 CommonResponseEntity와 같은 응답 객체를 만들어서 사용할 수 있다.
- 수행했던 프로젝트에선 Service Layer에서 비지니스 로직에 대한 예외 처리를 위해서 CustomizeException 클래스를 만들고 RuntimeException을 상속받아서 사용했다.

```java
@Slf4j
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

---

Service Interface는 왜 만들어야 할까?
===
예전부터 Spring을 사용한 사람이라면 Service를 구현할 때 Interface Service와 SericeImpl을 구현하는게 익숙할 수 있다. 규칙처럼 무심코 만들어서 사용했었는데 Interface Service를 사용하는 이유를 ChatGPT에게 물어보면 아래와 같은 답변을 한다.
1. 추상화와 구체화의 분리 : 인터페이스를 사용하면 추상적인 개념과 구체적인 구현을 분리할 수 있다. 인터페이스는 서비스의 기능과 동작을 정의하고 실제 구현은 해당 인터페이스를 구현하는 클래스에서 수행된다. 이렇게 함으로써, 클라이언트는 인터페이스를 통해 서비스에 접근하고 사용할 수 있으며 실제 구현에 대한 의존성 줄일 수 있다.
2. 유연성과 확장성 : 인터페이스를 사용하면 다른 구현을 손쉽게 교체할 수 있다. 예를 들어, 기존의 ServiceImpl 클래스를 다른 구현 클래스로 교체하더라도 클라이언트는 인터페이스를 통해 동일한 방식으로 서비스를 사용할 수 있다. 이는 유연성과 확장성을 높여준다.
3. 단위 테스트 : 인터페이스를 사용하면 단위 테스트를 수행할 때, Mock 객체를 이용하여 테스트를 할 수 있다. Mock 객체는 인터페이스를 구현한 가짜 객체로 실제 구현과는 독립적으로 동작하여 테스트를 더욱 쉽게할 수 있다.
4. 코드의 명확성과 가독성 : 인터페이스를 사용하면 코드의 목적과 의도를 명확하게 전달할 수 있다. 인터페이스는 서비스의 기능과 사용법을 정의하기 때문에 코드를 읽는 사람이 빠르게 이해하고 사용할 수 있다.
5. 다형성 구현 : 인터페이스를 통해 다형성을 구현할 수 있다. 다형성은 객체 지향 프로그래밍의 중요한 개념으로 한 인터페이스를 여러 개의 클래스가 구현할 수 있다. 이를 통해 동일한 인터페이스를 사용해서 다양한 구현체를 처리할 수 있으며 실행 시점에 구체적인 구현체를 결정할 수 있다.
6. 구현 클래스 교체 : 인터페이스를 사용하면 구현 클래스를 쉽게 교체할 수 있다. 예를 들어, 현재 ServiceImpl 클래스를 사용하고 있다면 나중에 필요에 따라 다른 구현 클래스로 변경할 수 있다. 이 때 클라이언트 코드는 변경 없이도 인터페이스를 통해 동일한 방식으로 서비스를 호출할 수 있다. 이는 코드의 유지보수와 확장에 용이하다.
7. 의존성 주입(Dependency Injection) : 인터페이스를 사용하면 의존성 주입을 쉽게 구현할 수 있다. 의존성 주입은 객체 간의 의존 관계를 외부에서 주입하여 코드 간의 결합도를 낮추고 유연한 구조를 구현하는 패턴이다. 인터페이스를 통해 의존성을 주입하면 다른 구현체를 주입해서 동일한 인터페이스를 사용하는 코드를 유지할 수 있다.

Spring에서 Interface Service를 구현했을 때 얻는 장점이 아니라 Java Interface의 장점을 답변한 것 같았다. 추가적으로 구글링하면서 찾았던 내용들을 아래에 정리했다.

짧게 정리하면, 예전에 Spring은 어노테이션을 관리하기 위해 사용되는 AOP Proxy가 JDK Dynamic Proxy 방식을 사용했었고 JDK Dynamic Proxy는 interface 기준으로 만들고 관리하기 때문에 Interface Service를 반드시 만들어서 사용해야만 했다.
예를들어 Serivce 함수에 많이 쓰이는 @Transactional과 같은 어노테이션은 AOP Proxy를 만들어서 트랜잭션을 처리하기 때문에 인터페이스가 있어야 동작했었다.

하지만 Class 기준으로 관리하는 CGLIB 방식을 사용되면서 Interface Service를 사용하지 않아도 됐다. 처음엔 Spring 설정에서 설정할 수 있게 했었고 Spring 3.2부터 CGLIB가 내장됐다.
```yml
spring.aop.proxy-target-class=true # CGLIB
spring.aop.proxy-target-class=false # JDK Dynamic Proxy
```

네이버 로그인 Service, 카카오 로그인 Service와 같이 비슷한 기능을 하는 서비스를 관리하기 위해서 Interface Service를 사용하는 경우도 있으며
하나의 Interface Serivce를 여러 개의 ServiceImpl이 상속받아 사용한다면 @Primary, @Qulifier를 사용해서 상황에 맞는 특정 클래스를 주입해서 사용해야 한다.
Mockito와 같은 Mocking 라이브러리를 사용하면 인터페이스를 사용하지 않아도 단위 테스트를 무리없이 진행할 수 있다.

- ## IoC 컨테이너와 Spring AOP Proxy
  - 사용자의 특정 호출 시점에 IoC 컨테이너에 의해 AOP을 할 수 있는 Proxy Bean을 생성해준다. 동적으로 생성된 Proxy Bean은 target의 메소드가 호출되는 시점에 부가기능을 추가할 메소드를 자체적으로 판단하고 가로채서 부가기능을 주입해주는데 이처럼 호출 시점에 동적으로 위빙한다해서 Runtime Weaving 이라고 한다.
  - Spring AOP는 런타임 위빙의 방식을 기반으로 동작하고 있으며 Spring에선 런타임 위빙을 할 수 있도록 상황에 따라 JDK Dynamic Proxy와 CGLIB 방식을 통해 Proxy Bean을 생성해준다.
  - 타겟이 하나 이상의 인터페이스를 구현하고 있는 클래스라면 JDK Dynamic Proxy의 방식으로 생성되고 인터페이스를 구현하지 않은 클래스라면 CGLIB의 방식으로 AOP 프록시를 생성해줍니다.
  - ### JDK Dynamic Proxy
    - Java의 리플렉션 패키지에 존재하는 Proxy라는 클래스를 통해 생성되는 Proxy를 의미한다. 리플랙션의 Proxy 클래스가 동적으로 Proxy를 생성해준다해서 붙여진 이름이다.
    - JDK Dynamic Proxy는 인터페이스 기준으로 Proxy를 생성해준다는게 핵심이다. 인터페이스 기준으로 Proxy 객체를 생성하기 때문에 구현체는 인터페이스를 상속받아야 하고 @Autowired를 통해 생성된 Proxy Bean을 사용하기 위해선 반드시 인터페이스 타입으로 지정해줘야 한다.
    - 아래 코드에선 @Autowired를 사용하는 객체가 인터페이스가 아니라 구현체라서 Runtime 에러가 발생한다.

      ```java
      @Controller
      public class UserController {
        @Autowired
        private MemberService memberService; // <- Runtime Error 발생...
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
    - JDK Dynamic Proxy는 Proxy 객체에 InvocationHandler를 포함시켜 하나의 객체로 변환한다. 타겟의 인터페이스는 ProxyFactory에 의해 타겟의 인터페이스를 상속한 Proxy 객체를 생성한다. JDK Dynamic Proxy를 사용하면 인터페이스를 사용해야 하는 이유이다.
    - 사용자의 요청이 기존의 타겟을 그대로 바라볼 수 있도록 타겟의 대한 위임코드를 Proxy 객체에 전달해줘야 하는데 이를 InvocationHandler에 작성한다. 따라서 사용자의 요청이 최종적으로 생성된 Proxy의 메소드를 통해 호출될 때 내부적으로 invoke에 대한 검증과정이 이뤄진다.
    - 결과적으로 코드는 아래와 같다. 아래 과정에서 검증 과정이 이뤄지는 이유는 다름 아닌 Proxy가 기본적으로 인터페이스에 대한 Proxy만을 생성해주기 때문이다. 따라서 개발자가 타겟에 대한 정보를 잘못주입할 경우를 대비해서 JDK Dynamic Proxy는 내부적으로 주입된 타겟에 대한 검증코드를 형성하고 있다.
      ```java
      Object proxy = Proxy.newProxyInstance(ClassLoader     // 클래스로더
                                    , Class<?>[]            // 타겟의 인터페이스
                                    , InvocationHandler);   // 타겟의 정보가 포함된 Handler
      ```
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
  - ## CGLib(Code Generator Library)
    - 클래스의 바이트코드를 사용해서 Proxy 객체를 생성해주는 라이브러리이다.
    - 인터페이스가 아니더라도 타겟의 클래스에 대해서도 Proxy를 생성해준다. Enhancer라는 클래스를 통해 Proxy를 생성할 수 있다.
    - 타겟 클래스에 포함된 모든 메소드를 재정의하여 Proxy를 생성한다.
    - Final 메소드 또는 클래스에 대해 재정의를 할 수 없으므로 Proxy를 생성할 수 없다는 단점이 있지만 바이트 코드를 조작해서 Proxy를 생성해주기 때문에 성능에 대한 부분이 JDK Dynamic Proxy보다 훨씬 좋다.
    - CGLIB는 타겟에 대한 정보를 제공받기 때문에 성능의 차이가 발생한다. CGLIB는 제공받은 타겟 클래스에 대한 바이트 코드를 조작해서 Proxy를 생성하기 때문에 Handler 안에서 타겟의 메소드가 처음 호출되었을 때 동적으로 타겟의 클래스의 바이트 코드를 조작한다. 이후엔 조작된 바이트 코드를 재사용한다.
      ```java
      Enhancer enhancer = new Enhancer();
      enhancer.setSuperclass(MemberService.class);  // 타겟 클래스
      enhancer.setCallback(MethodInterceptor);      // Handler
      Object proxy = enhancer.create();             // Proxy 생성
      ```
      ```java
      public Object invoke(Object proxy, Method proxyMethod, Object[] args) throws Throwable {
        Method targetMethod = target.getClass().getMethod(proxyMethod.getName(), proxyMethod.getParameterTypes());
        Ojbect retVal = targetMethod.invoke(target, args);
        return retVal;
      }
      ```
  - 최초에 CGLIB의 3가지 한계점을 갖고 있었다.
    - Spring이 기본적으로 지원하지 않는 방식이라서 별도의 의존성(`net.sf.cglib.proxy.Enhancer`)을 추가해야 했다.
    - 구현을 위해선 반드시 파라미터가 없는 default 생성자가 필요했다.
    - 타겟의 생성자 두 번 호출됐다.
  - 이후에 SpringBoot 3.2부터는 CGLIB 방식이 공식적으로 내장되면서 한계점이 해결되었다.
    - 기본적으로 내장되어 있기 때문에 별도의 의존성 추가하지 않아도 된다.
    - 4버전부터는 Objensis 라이브러리의 도움을 받아 default 생성자 없이 Proxy를 생성할 수 있다.
    - 따라서 생성자가 2번 호출되지 않는다.
  - DK Dynamic Proxy는 Spring AOP의 AOP 기술의 근간이 되는 방식이기 때문에 Spring에서 사용되는 AOP의 기술들은 Proxy 메커니즘을 따르고 있습니다. 즉 CGLib이든 JDK Dynamic Proxy든 Proxy 메커니즘을 따른다는 점을 인지해야 한다.

---

자주 쓰이는 Controller Annotation
===
- @RequestParam
- @RequestBody
- @PathVariable
- Response - 조립 & Builder & Superbuilder

---

자주 쓰이는 Service Annotation
===


---

Transaction
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

Reference
===
- https://velog.io/@hsw0194/Spring-Boot에서-interface를-사용해야-할까
- https://gmoon92.github.io/spring/aop/2019/04/20/jdk-dynamic-proxy-and-cglib.html
- https://gmoon92.github.io/spring/aop/2019/01/15/aspect-oriented-programming-concept.html
- http://hayunstudy.tistory.com/53