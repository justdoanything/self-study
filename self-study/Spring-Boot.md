
```
1️⃣ Spring Framework
App
Spring Framework
cotext (/WEB-INF/web.xml, /lib, /classes)
Container x n
WAS (.exe)
JDK
OS
```
```
2️⃣ Spring Boot
App
Spring boot (.exe)
OS
```

```
3️⃣ Spring 주요 특징
    🎈 제어의 역전(IOC = Inversion of Control)
        Java Application 초기엔 객체의 생성,객체 간의 의존관계 연결 등 제어권을 개발자가 갖고 있었지만
        Servlet, EJB가 등장하면서 개발자가 독점적으로 갖고 있는 제어권을 넘기게 된다.
        이처럼 객체의 생성과 생명주기의 관리까지 제어권이 Spring으로 바뀐 것을 IOC 라고 한다.

        ✳ EJB (Enterprice Java Beans) :Java 객체를 재사용 가능하도록 컴포넌트화 시킬 수 있도록 정한 개발 표준 방식
        하지만 불필요한 코드가 많고 단위 테스트가 불가능하다는 단점이 존재했다.

    🎈 의존성 주입(DI = Dependency Injection)
        객체 사이의 의존 관계를 객체 자신이 아닌 외부의 조립기(Assembler)가 수행하는 개념
        객체 사이의 필요한 의존 관계에 대해서 Spring Container가 어느정도는 자동으로 연결해준다.
        Spring Container에 Bean을 주입하는 방식은 XML을 사용하거나 @Annotation을 사용할 수 있다.

    🎈 관점 지향 프로그래밍(AOP = Aspect Oriented Programming)
        어떤 로직을 기준으로 핵심적인 부분과 부가적인 부분을 나눠보고 그 기준으로 모듈화를 하는 것이다.
        흩어진 관심사를 Crosscutting Concerns이라고 부르고 이런 흩어진 관심사를 Aspect로 모듈화하고 핵심적인 모듈에서 분리해서 사용할 수 있다.

        특정 함수를 @Aspect로 감싸고 특정 Package에 적용하도록 할 수 있다.
        원하는 모듈을 합쳐서 만들고 1개의 모듈로 사용할 수 있다는 것이다.

    🎈 POJO (Plain Old Java Object)
        EJB와 같은 방식에서 사용하던 Object 그대로를 Spring에 녹일 수 있는 방식이다.
        환경과 기술에 종속되지 않고 필요에 따라 활용하여 사용할 수 있는 Object를 말한다.
        간단한 예로는 DTO, VO와 같이 getter/setter로 되있는 단순 Object를 말하기도 한다.
```

```
*️⃣ 참고자료  
https://m.blog.naver.com/sillllver/220593543939
https://hoon93.tistory.com/56
https://engkimbs.tistory.com/746
```