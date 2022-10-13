package modern.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.List;

/**
 * @Description
 *   검증할 필드의 Type을 ConstraintValidator의 파라미터로 넘겨준다.
 *   String 타입의 필드를 검증하려면 ConstraintValidator<EnumValid, String>가 되어야 하고
 *   enum 타입의 필드를 검증하려면 ConstraintValidator<EnumValid, Enum>가 되야 한다.
 */
public class EnumValidator implements ConstraintValidator<EnumValid, Enum> {

    private List<String> enumValues;
    private EnumValid annotation;

    @Override
    public void initialize(EnumValid constraintAnnotation) {
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
    public boolean isValid(Enum value, ConstraintValidatorContext context) {
        boolean result = false;
        Object[] enumValues = this.annotation.enumClass().getEnumConstants();
        if (enumValues != null) {
            for (Object enumValue : enumValues) {
                if (value == enumValue) {
                    result = true;
                    break;
                }
            }
        }
        return result;
    }
}
