<!-- TOC -->
* [VO와 DTO](#vo와-dto)
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
    Front-end의 필드명이 바뀌는 경우 혹은 Database의 필드명이 변경될 경우 수정해야 하는 파일들이 너무 많게 되고 Request에서 들어오는 값들을 SQL에서 그대로 사용하는 것은 위험성이 높다고 생각했다.
  - toVO 함수를 사용한다면 Request에서 오는 값을 검증하는 로직 후 테이블VO를 만들 수 있으며 Front-end와 Database의 필드명 변경은 toVO 함수만 수정하면 되기 때문에 유지보수에 유리하다.
  - 회사에선 한 스토리로 Front-end와 Back-end를 한 사람이 개발하는 경우가 많아서 Front-end 필드와 Database 필드가 같아서 RequestVO를 SQL에서 바로 사용하는 사람들이 많았는데 
    toVO를 사용하는 것이 안전하다고 생각해서 같은 필드 이름을 쓰더라도 꼭 toVO를 사용했었다.

# Enum Deserializer
Spring에서 특정 범위를 갖는 값을 사용할 때 Enum 클래스를 만들어서 사용하곤 했다. 처음으로 접했던 방법은 `@Enum` annotation과 `EnumValidator`을 구현해서 아래와 같은 방법으로 사용했었다.
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

@EnumValid annotation은 아래와 같은 @PathVariable로 Enum 클래스를 바로 사용하는 경우를 처리하지 못했고 따라서 각 Enum 클래스마다 Converter 만들어서 사용했었다.
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
- VO에 각 필드에 @Enum annotation을 넣어줘야 하는 점
- `Code Enum` 형태를 호환하지 못하는 점

공통 코드를 Database에서 관리하고 Front-end, Back-end에서 같이 사용하는 경우, `Code Enum` 형태를 사용했는데 Front-end에선 Code 값으로 사용하기 때문에 "001", "002", "003", "004"와 같은 값들을 사용하고
Back-end Service Layer에선 Enum Code 값을 검증하고 사용해야했기 때문에 아래와 같은 `Code Enum` 형태를 사용했었다.
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

  private String value;

  FeedContentsTypeCode(String value){
    this.value = value;
  }
  
  public String value() {
    return value;
  }
}
```

Front-end에선 Database에서 조회된 공통코드를 사용하기 때문에 화면에 노출하는 값은 name(NORMAL, VOTE, SHARE, VIDEO), 실제로 코드 내에선 사용하는 값은 code(001, 002, 003, 004) 값으로 
Code 값이 Back-end로 전송하기 때문에 Code Enum 형태를 사용하지 않고 일반적인 Enum 형태를 사용한다면 Service Layer에서 `FeedContentsTypeCode.001.equals(contents)`와 같이 001이 어떤 값인지 다시 찾아봐야하는 불편함이 생기기 때문에 Code Enum 형태를 사용하게 되었고
기존에 만들어둔 @EnumValid나 Converter를 사용하면 Enum 값으로 비교하기 때문에 Front-end에서 "001"로 값이 넘어오는 경우 비교하는 값의 범위가 [NORMAL, VOTE, SHARE, VIDEO] 이기 때문에 호환되지 않았다.

이와 같은 점들을 개선하기 위해서 Jackson을 사용해서 Serializer와 Deserializer를 사용하게 되었다.
Enum 체크해서 value 있으면 enum 값과 value 값들을 모두 검사한다.
그리고 필드 타입을 Enum class를 바로 사용함으로써 @enumvalid 안써도된다.

대신 null은 호환하지 않으며 (valid에 추가할 수 있지만?) @enumvalid는 exclude나 null을 사용할 수 있게 된다.


- 장점은 exclude, null 가능