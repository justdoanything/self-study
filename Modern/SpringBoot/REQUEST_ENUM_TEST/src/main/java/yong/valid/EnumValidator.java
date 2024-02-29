package yong.valid;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import yong.annotation.Enum;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class EnumValidator implements ConstraintValidator<Enum, String> {
    private List<String> enumValues;
    private Enum annotation;

    @Override
    public void initialize(Enum value) {
        this.annotation = value;
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
