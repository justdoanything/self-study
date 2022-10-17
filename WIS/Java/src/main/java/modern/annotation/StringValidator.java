package modern.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @Description
 *   검증할 필드의 Type을 ConstraintValidator의 파라미터로 넘겨준다.
 *   String 타입의 필드를 검증하려면 ConstraintValidator<EnumValid, String>가 되어야 하고
 *   enum 타입의 필드를 검증하려면 ConstraintValidator<EnumValid, Enum>가 되야 한다.
 */
public class StringValidator implements ConstraintValidator<StringValid, String> {

    private StringValid annotation;

    @Override
    public void initialize(StringValid constraintAnnotation) {
        this.annotation = constraintAnnotation;
    }

    /**
     * 실제 Validation에 사용할 코드
     * @param value object to validate
     * @param context context in which the constraint is evaluated
     *
     * @return
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        boolean retVal = false;

        if (value != null && !value.isEmpty()) {
            retVal = this.annotation.getClass().toString().equals(value);
        } else {
            retVal = true;
        }

        return retVal;
    }
}
