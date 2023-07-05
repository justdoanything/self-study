<!-- TOC -->
* [VO와 DTO](#vo와-dto)
* [Enum Deserializer](#enum-deserializer)
* [Redis](#redis)
* [Flyway](#flyway)
<!-- TOC -->

# VO와 DTO
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

# Resolver
[Spring MVC](https://github.com/justdoanything/self-study/blob/main/12%20Spring.md#spring-mvc)를 정리한 자료를 보면 Spring이 어떻게 MVC 패턴으로 동작하고 변해왔는지 순서대로 알 수 있다. 이러한 변화과정은 개발자가 Spring을 사용할 때 좀 더 편하고 빠르게 개발할 수 있게 해준다. SpringBoot가 되면서 좀 더 빠르고 가벼워졌으며 개발자가 Spring을 사용할 때 반드시 해줘야했던 configure나 의존성 설정 등이 없어졌다.
예전에 Spring을 사용할 땐 tomcat을 따로 설치하고 Spring과 설정해줘야 동작했었는데 SpringBoot에선 embedded tomcat을 사용해서 별도의 설치나 설정 없이 바로 웹서버가 동작하고 있다.

이렇듯 대부분의 공통처리를 Spring에서 해주지만 상황에 따라 개발자가 customize해서 사용할 수 있게 동작한다. 예를들어 Resolver를 따로 정의하지 않아도 기본적인 동작들이 수행되고 아래에서 추가할 특정 타입, 상황에 따라 개발자가 원하는 로직을 사용하고 싶다면 Spring Handler를 상속받아 특정 함수를 Override해서 사용할 수 있다.

주로 프로젝트에선 Request/Response 공통 처리를 위해 Jackson의 Serializer/Deserializer를 정의해서 사용하곤 한다. 로그인이나 인증을 처리하기 위해서 특정 DTO가 들어왔을 때 동작할 함수를 따로 정의하거나 Enum 타입의 Request가 왔을 때 validation하는 동작을 따로 정의하거나 응답하는 객체의 Date 타입에 따라 특정 format을 지정하는 등 공통처리를 하고 싶을 때 많이 사용했었다.

특히 여러 사람이 동시에 개발하는 경우 공통 기능을 함수로 만들거나 어노테이션으로 만든다면 누락되거나 사용성에 문제가 있을 수 있기 때문에 Resolver로 처리하는 것이 안정적이다.


# Enum Deserializer
Spring에서 특정 범위를 갖는 값을 사용할 때 Enum 클래스를 만들어서 사용하곤 했다. 처음으로 접했던 방법은 `@Enum` 어노테이션과 `EnumValidator`을 구현해서 아래와 같은 방법으로 사용했었다.
```java
public FeedVO {
    @EnumValid(enumClass = ContentsType.class)
    private String contentsType;
    private String writer;
    private String title;
    private String contents;
}
```
```java
@Target({ElementType.TYPE_USE, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EnumValidator.class)
public @interface EnumValid {
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

@EnumValid 어노테이션으로는 아래와 같은 @PathVariable로 Enum 클래스를 바로 사용하는 경우를 처리하지 못했고 이를 처리하기 위해서 각 Enum 클래스마다 Converter 만들어서 사용했었다.
```java
@RestController
@RequestMapping("/feeds")
public class UserController {
    
    @GetMapping("/{contentsType}")
    public ResponseEntity<String> getFeedsByContentsType(@PathVariable("contentsType") ContentsType contentsType) {
        return ResponseEntity.ok();
    }
}
```
```java
@Component
public class ContentsTypeConverter implements Converter<String, ContentsType> {
    @Override
    public ContentsType convert(String value) {
        return ContentsType.valueOf(value.toUpperCase());
    }
}
```

이렇게 사용할 경우 몇가지 문제점이 발생한다.
- 사용하는 Enum 클래스만큼 Converter를 각각 만들어줘야하는 점
- VO에 각 필드에 @Enum 어노테이션을 넣어줘야 하는 점
- `Code Enum` 형태를 호환하지 못하는 점

프로젝트에서 공통 코드는 데이터베이스에서 테이블로 관리하고 Back-end에서 공통 코드 조회 API를 만들어서 Front-end에서 사용할 수 있도록 했었다. 공통 코드는 주로 name과 code값이 한 쌍으로 관리되며 Front-end에선 주로 name 값을 화면에 보여주고 내부적으론 code 값을 사용했다.
Back-end에서는 공통 코드를 사용할 때마다 데이터베이스를 찾을 수 없기 때문에 아래에 있는 `FeedContentsTypeCode`와 같은 Code Enum 클래스를 만들어서 사용했었다.
```java
// 일반적인 Enum 형태
public enum Gender {
  MAN,
  WOMAN,
  BOTH
} 
```
```java
// Code Enum 형태
public enum FeedContentsTypeCode {
  NORMAL("001"),
  VOTE("002"),
  SHARE("003"),
  VIDEO("004");

  private String code;

  FeedContentsTypeCode(String code){
    this.code = code;
  }
  
  public String code() {
    return code;
  }
}
```

Front-end에서 화면에 노출하는 값은 name 값인 `[NORMAL, VOTE, SHARE, VIDEO]`, 실제로 코드 내에선 사용하는 값은 code 값인 `[001, 002, 003, 004]` 값으로 사용했다. 
Back-end와 통신할 땐 code 값이 전송되기 때문에 일반적인 enum 형태로 만들어서 사용한다면 Service Layer에서 `FeedContentsTypeCode.001.equals(contents)`와 같이 `001`이 어떤 값인지 알 수 없는 문제가 생기기 때문에 Code Enum 클래스를 만들어서 사용했다.

물론 기존에 만들어둔 @EnumValid 방식도 장점이 있었다.
- NULL 값을 호환한다는 점
- excludeEnumType, message 등 좀 더 유연한 비교가 가능하다는 점

하지만 Jackson의 Serializer와 Deserialzer를 사용하면 기존에 있던 단점을 보완하고 @EnumValid의 장점도 모두 사용할 수 있었다. 
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
# Service와 ServiceImpl을 사용하면 얻는 이점
1. 추상화와 구체화의 분리 : 인터페이스를 사용하면 추상적인 개념과 구체적인 구현을 분리할 수 있다. 인터페이스는 서비스의 기능과 동작을 정의하고 실제 구현은 해당 인터페이스를 구현하는 클래스에서 수행된다. 
이렇게 함으로써, 클라이언트는 인터페이스를 통해 서비스에 접근하고 사용할 수 있으며 실제 구현에 대한 의존성 줄일 수 있다.
2. 유연성과 확장성 : 인터페이스를 사용하면 다른 구현을 손쉽게 교체할 수 있다. 예를 들어, 기존의 ServiceImpl 클래스를 다른 구현 클래스로 교체하더라도 클라이언트는 인터페이스를 통해 동일한 방식으로 서비스를 사용할 수 있다. 이는 유연성과 확장성을 높여준다.
3. 단위 테스트 : 인터페이스를 사용하면 단위 테스트를 수행할 때, Mock 객체를 이용하여 테스트를 할 수 있다. Mock 객체는 인터페이스를 구현한 가짜 객체로 실제 구현과는 독립적으로 동작하여 테스트를 더욱 쉽게할 수 있다.
4. 코드의 명확성과 가독성 : 인터페이스를 사용하면 코드의 목적과 의도를 명확하게 전달할 수 있다. 인터페이스는 서비스의 기능과 사용법을 정의하기 때문에 코드를 읽는 사람이 빠르게 이해하고 사용할 수 있다.
5. 다형성 구현 : 인터페이스를 통해 다형성을 구현할 수 있다. 다형성은 객체 지향 프로그래밍의 중요한 개념으로 한 인터페이스를 여러 개의 클래스가 구현할 수 있다. 이를 통해 동일한 인터페이스를 사용해서 다양한 구현체를 처리할 수 있으며 실행 시점에 구체적인 구현체를 결정할 수 있다.
6. 구현 클래스 교체 : 인터페이스를 사용하면 구현 클래스를 쉽게 교체할 수 있다. 예를 들어, 현재 ServiceImpl 클래스를 사용하고 있다면 나중에 필요에 따라 다른 구현 클래스로 변경할 수 있다. 이 때 클라이언트 코드는 변경 없이도 인터페이스를 통해 동일한 방식으로 서비스를 호출할 수 있다. 이는 코드의 유지보수와 확장에 용이하다.
7. 의존성 주입(Dependency Injection) : 인터페이스를 사용하면 의존성 주입을 쉽게 구현할 수 있다. 의존성 주입은 객체 간의 의존 관계를 외부에서 주입하여 코드 간의 결합도를 낮추고 유연한 구조를 구현하는 패턴이다. 인터페이스를 통해 의존성을 주입하면 다른 구현체를 주입해서 동일한 인터페이스를 사용하는 코드를 유지할 수 있다.
8. 단위 테스트 : 인터페이스를 사용하면 단위 테스트를 수행하기 용이하다. 인터페이스를 구현한 Mock 객체를 사용해서 테스트를 진행할 수 있으며 실제 구현과 독립적으로 테스트할 수 있다. 이는 코드의 품질과 안정성을 향상시킨다.

# Service와 ServiceImpl을 사용하는 이유
- Spring의 AOP Proxy를 만드는 방식 (Annotation을 위한 Proxy)
  - 예전에 Spring이 JDK Dynamic Proxy를 사용했을 땐 JDK Dynamic Proxy는 오직 interface을 위해서만 만들어져서 interface을 작성해줘야 했다.
  - 예를들어 @Transactional 과 같은 어노테이션은 AOP Proxy를 만들어서 트랜잭션을 처리하기 때문에 인터페이스가 있어야 동작했다.
  - Spring이 CGLIB proxy도 지원하면서 별도의 interface을 요구하지 않는다.
  - spring.aop.proxy-target-class=true : CGLIB 사용해서 클래스를 상속받아 AOP Proxyt를 만듬
  - false : JDK Dynamic Proxy를 사용하여 인터페이스를 데코레이션해서 AOP Proxy를 만듬
  - Spring 3.2는 CGLIB가 내장되어서 별도의 설정 없이 사용 가능하다.
- 느슨한 결합
  - 비슷한 기능을 하는 서비스가 2개가 있다면 사용할만하다.
  - 여러 개의 서비스가 비슷할 때 사용될 때 1순위로 @Primary를 사용해서 지정해준다. 다른걸 사용할 땐 @Qualifier를 사용해서 특정한 다른 클래스를 주입해서 사용한다.
- 테스트
  - Mockito와 같은 mocking 라이브러리를 사용하면 해결할 수 있따.

# Spring AOP Proxy
- AOP : 핵심 기능과 공통된 기능 또는 관심사를 분리해서 모듈화하는 것을 목표로 한다. 관심사의 기능을 재사용 가능한 모듈인 관점(Aspect)로 추출하고 이를 핵심 기능에 횡단(cross-cutting)하게 적용한다. 
- 관점(Aspect) 공통된 기능을 모듈화한 단위. 로깅이나 보안과 같은 관심사는 관점으로 표현될 수 있다.
- 조인 포인트(Join Point) 관점이 적용될 수 있는 실행 지점을 의미한다. 메서드 실행, 예외 발생 등이 조인 포인트의 예시이다.
- Advice : 관점이 언제, 어떻게 적용될지 정의하는 코드이다. 메서드 실행 전수에 로깅하는 어드바이스가 있을 수 있따.
- Point cutting : 어떤 조인 포인트에 관점을 적용할지를 선택하는 표현식이다. 예를들어 특정 패키지 내의 모든 메서드에 관점을 적용하는 포인트컷을 정의할 수 있다.
- Weaving : 관점을 핵심 로직에 적용하는 과정을 의미합니다. 컴파일 시점, 실행 시점 등에 위빙을 발생할 수 있다.
- https://gmoon92.github.io/spring/aop/2019/01/15/aspect-oriented-programming-concept.html
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
- IoC 컨테이너와 AOP Proxy
  - 사용자의 특정 호출 시점에 IoC 컨테이너에 의해 AOP을 할 수 있는 Proxy Bean을 생성해준다. 동적으로 생성된 Proxy Bean은 target의 메소드가 호출되는 시점에 부가기능을 추가할 메소드를 자체적으로 판단하고
  가로채서 부가기능을 주입해주는데 이처럼 호출 시점에 동적으로 위빙한다해서 Runtime Weaving 이라고 한다.
  - Spring AOp는 런타임 위빙의 방식을 기반으로 동작하고 있으며 Spring에선 런타임 위빙을 할 수 있도록 상황에 따라 JDK Dynamic Proxy와 CGLIB 방식을 통해 Proxy Bean을 생성해준다.
  - 타깃이 하나 이상의 인터페이스를 구현하고 있는 클래스라면 JDK Dynamic Proxy의 방식으로 생성되고 인터페이스를 구현하지 않은 클래스라면 CGLIB의 방식으로 AOP 프록시를 생성해줍니다.
- JDK Dynamic Proxy
  - Java의 리플렉션 패키지에 존재하는 Proxy라는 클래스를 통해 생성되는 Proxy를 의미한다. 리플랙션의 Proxy 클래스가 동적으로 Proxy를 생성해준다해서 붙여진 이름읻.
  - 얘는 인터페이스 기준으로 Proxy를 생성해준다는게 핵심이다.
  - JDK Dynamic Proxy가 파라미터들을 가지고 Proxy 객체를 생성하는 과정
    - 타깃의 인터페이스를 자체적인 검증 로직을 통해 ProxyFactory에 의해 타깃의 인터페이스를 상속한 Proxy 객체 생성
    - Proxy 객체에 InvocationHandler를 포함시켜 하나의 객체로 변환
    ```java
    Object proxy = Proxy.newProxyInstance(ClassLoader       // 클래스로더
                                    , Class<?>[]        // 타깃의 인터페이스
                                    , InvocationHandler // 타깃의 정보가 포함된 Handler
    );
    ```
  - 인터페이스 기준으로 Proxy 객체를 생성하기 때문에 구현체는 인터페이스를 상속받아야 하고 @Autowired를 통해 생성된 Proxy Bean을 사용하기 위해선 반드시 인터페이스 타입으로 지정해줘야 한다.
  - 아래 코드는 Runtime 에러가 발생하는데 @Autowired를 사용하는 객체가 인터페이스가 아니라 구현체라서 에러가 발생한다.
    @Autowired
    private UserService userService; 이렇게 사용해야 한다.
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
  - 사용자의 요청이 기존의 타깃을 그대로 바라볼 수 있도록 타깃의 대한 위임코드를 Proxy 객체에 작성해줘야 ㅑ하는데 이게 InvocationHandler에 작성해야 한다. 따라서 사용자의 요청이 최종적으로 생성된 Proxy의 메소드를 통해 호출할 때 내부적으로 invoke에 대한 검증과정이 이뤄집니다.
  - 결과적으로 코드는 아래와 같다. 아래 과정에서 검증 과정이 이뤄지는 이유는 다름 아닌 Proxy가 기본적으로 인터페이스에 대한 Proxy만을 생성해주기 때문이다. 따라서 개발자가 타깃에 대한 정보를 잘못주입할 경우를 대비해서 JDK Dynamic Proxy는 내부적으로 주입된 타깃에 대한 검증코드를 형성하고 있다.
    ```java
    public Object invoke(Object proxy, Method proxyMethod, Object[] args) throws Throwable {
      Method targetMethod = null;
      // 주입된 타깃 객체에 대한 검증 코드
      if (!cachedMethodMap.containsKey(proxyMethod)) {
        targetMethod = target.getClass().getMethod(proxyMethod.getName(), proxyMethod.getParameterTypes());
        cachedMethodMap.put(proxyMethod, targetMethod);
      } else {
        targetMethod = cachedMethodMap.get(proxyMethod);
      }

      // 타깃의 메소드 실행
      Ojbect retVal = targetMethod.invoke(target, args);
      return retVal;
    }
    ```
- CGLib(Code Generator Library)
  - 클래스의 바이트코드를 사용해서 Proxy 객체를 생성해주는 라이브러리이다.
  - 인터페이스가 아니더라고 타깃의 클래스에 대해서도 Proxy를 생성해준다. Enhancer라는 클래스를 통해 Proxy를 생성할 수 있다. (자세히 : https://www.baeldung.com/cglib)
    ```java
    Enhancer enhancer = new Enhancer();
    enhancer.setSuperclass(MemberService.class); // 타깃 클래스
    enhancer.setCallback(MethodInterceptor);     // Handler
    Object proxy = enhancer.create(); // Proxy 생성
    ```
  - 타깃 클래스에 포함된 모든 메소드를 재정의하여 Proxy를 생성해준다.
  - Final 메소드 또는 클래스에 대해 재정의를 할 수 없으므로 Proxy를 생성할 수 없다는 단점이 있지만 바이트 코드를 조작해서 Proxy를 생성해주기 때문에 성능에 대한 부분이 JDK Dynamic Proxy보다 훨씬 좋습니다.
  - Invoke 차이, 성능 차이
    - CGLIB는 제공받은 타깃 클래스에 대한 바이트 코드를 조작해서 Proxy를 생성하기 때문에 Handler 안에서 타깃의 메소드를 호출할 때 다음과 같은 코드가 형성된다.
      ```java
      public Object invoke(Object proxy, Method proxyMethod, Object[] args) throws Throwable {
        Method targetMethod = target.getClass().getMethod(proxyMethod.getName(), proxyMethod.getParameterTypes());
        Ojbect retVal = targetMethod.invoke(target, args);
        return retVal;
      }
      ```
    - 메소드가 처음 호출되었을 때 동적으로 타깃의 클래스의 바이트 코드를 조작
    - 이후 호출시엔 조작된 바이트 코드를 재사용
  - CGLIB의 3가지 한계점
    - Spring이 기본적으로 지원하지 않는 방식이라서 별도의 의존성을 추가해야함. net.sf.cglib.proxy.Enhancer 의존성 추가
    - 구현을 위해선 반드시 파라미터가 없는 default 생성자가 필요함
    - 타깃의 생성자 두 번 호출됨
  - 이후에 SpringBoot 3.2부터는 CGLIB 방식으로 Proxy를 생성하도록 변경됨.
    - 기본적으로 내장하기 때문에 별도의 의존성 추가 X
    - 4버전부터는 Objensis 라이브러리의 도움을 받아 default 생성자 없이 Proxy를 생성할 수 있게 됨
    - 따라서 생성자가 2번 호출되지 않음.
- DK Dynamic Proxy는 Spring AOP의 AOP 기술의 근간이 되는 방식이기 때문에 Spring에서 사용되는 AOP의 기술들은 Proxy 메커니즘을 따르고 있습니다. 즉 CGLib이든 JDK Dynamic Proxy든 Proxy 메커니즘을 따른다는 점을 인지해야 한다.
  


# Request
- @RequestParam
- @RequestBody
- @PathVariable

# Response
- 조립
- Builder & Superbuilder

# Redis
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

# Flyway



- Thanks to ChatGPT
- https://velog.io/@hsw0194/Spring-Boot에서-interface를-사용해야-할까
- https://gmoon92.github.io/spring/aop/2019/04/20/jdk-dynamic-proxy-and-cglib.html
- https://gmoon92.github.io/spring/aop/2019/01/15/aspect-oriented-programming-concept.html
- http://hayunstudy.tistory.com/53