package prj.modern.constant.valid;

import org.springframework.util.ObjectUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Description
 *  Java에서 기본적으로 제공하는 원시 타입의 필드에는 Bean Validation에서 제공하는 @NotNull, @NotEmpty 등의 annotation을 사용할 수 있다.
 *  하지만 사용자가 따로 정의한 필드에는 사용할 수 없기 때문에 원하는 Validation을 정의하고 사용해야 한다.
 *
 *  일반적으로 Enum 클래스를 정의하고 Validation할 때 사용할 때 유용하다.
 *  Annotaion으로 사용할 Interface를 정의히고 valid 로직은 ConstraintValidator를 상속 받은 클래스에서 정의한다.
 *
 * @Reference
 *  https://jsy1110.github.io/2022/enum-class-validation/ @Description JAVA에서 기본적으로 제공하는
 *
 */
public class EnumValidator implements ConstraintValidator<Enum, String> {

    private List<String> enums;
    private Enum enumAnnotation;
    @Override
    public void initialize(Enum constraintAnnotation) {
        this.enumAnnotation = constraintAnnotation;
        List<String> excludeEnumType = Arrays.stream(this.enumAnnotation.excludeEnumType()).collect(Collectors.toList());
        enums = Arrays.stream(this.enumAnnotation.enumClass().getEnumConstants())
                .map(eumConstant -> this.enumAnnotation.ignoreCase() ? eumConstant.name().toUpperCase() : eumConstant.name())
                .filter(eumConstant -> !excludeEnumType.contains(eumConstant))
                .collect(Collectors.toList());
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(ObjectUtils.isEmpty(value)){
            return true;
        }else {
            return enums.contains(this.enumAnnotation.ignoreCase() ? value.toUpperCase() : value);
        }
    }
}