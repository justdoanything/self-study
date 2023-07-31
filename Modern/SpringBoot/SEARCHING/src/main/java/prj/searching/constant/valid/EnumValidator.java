package prj.searching.constant.valid;

import org.springframework.util.ObjectUtils;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

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