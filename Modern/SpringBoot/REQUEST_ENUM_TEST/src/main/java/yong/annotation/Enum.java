package yong.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import yong.valid.EnumValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE_USE, ElementType.FIELD, ElementType.PARAMETER,})
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
