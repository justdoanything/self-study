package prj.yong.modern.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Target({ElementType.TYPE_USE, ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = StringValidator.class)
public @interface StringValid {
    String message() default "유효하지 않은 String 값입니다.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String stringClass();
}
