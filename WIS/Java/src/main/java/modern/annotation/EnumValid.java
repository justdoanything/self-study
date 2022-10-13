package modern.annotation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE_USE, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
/**
 * validation 로직을 넣을 Class를 명시
 */
@Constraint(validatedBy = StringValidator.class)

/**
 * @Reference : https://jsy1110.github.io/2022/enum-class-validation/
 * @Description
 *   JAVA에서 기본적으로 제공하는 원시타입의 경우 @NotBlacn, @NotNull 등 제공되는 annotation으로 validation을 할 수 있다.
 *   개발자가 원하는 필드의 원하는 validation을 걸고 싶다면 따로 정의해줘야 한다.
 *
 *   예를들어, private Gender gender; 필드가 있을 때 Gender는 MEN, WOMEN만 가능하다고 해보자.
 *   public enum Gender { MEN, WOMEN } 으로 정의하고 @EnumValid annotaion을 적용해서 원하는 validation을 걸어줄 수 있다.
 *
 * @Useage
 *   @EnumValid(enumClass = Gender.class)
 *   private Gender gender;
 *
 *   @EnumValid(enumClass = EnumConstants.StoreType.class)
 *   private String storeType;
 *
 */

public @interface EnumValid {
    String message() default "유효하지 않은 enum 타입입니다.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    Class<? extends java.lang.Enum<?>> enumClass();

    boolean ignoreCase() default false;

    String[] excludeEnumType() default {};
}
