package modern.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description
 *   검증할 필드의 Type을 ConstraintValidator의 파라미터로 넘겨준다.
 *   String 타입의 필드를 검증하려면 ConstraintValidator<EnumValid, String>가 되어야 하고
 *   enum 타입의 필드를 검증하려면 ConstraintValidator<EnumValid, Enum>가 되야 한다.
 */
public class StringValidator implements ConstraintValidator<EnumValid, String> {

    private List<String> enumValues;
    private EnumValid annotation;

    @Override
    public void initialize(EnumValid constraintAnnotation) {
        this.annotation = constraintAnnotation;
        List<String> excludeEnumType =
                Arrays.stream(this.annotation.excludeEnumType()).collect(Collectors.toList());

        /**
         * enum의 값들은 반드시 대문자여야 한다.
         */
        enumValues =
                Arrays.stream(this.annotation.enumClass().getEnumConstants())
                        .map(
                                constants ->
                                        this.annotation.ignoreCase()
                                                ? constants.name().toUpperCase()
                                                : constants.name())
                        .filter(constants -> !excludeEnumType.contains(constants))
                        .collect(Collectors.toList());
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
            retVal =
                    enumValues.contains(this.annotation.ignoreCase() ? value.toUpperCase() : value);
        } else {
            retVal = true;
        }

        return retVal;
    }
}
