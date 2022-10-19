package modern.annotation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class StringValidator implements ConstraintValidator<StringValid, String> {

    private StringValid annotation;

    @Override
    public void initialize(StringValid constraintAnnotation) {
        this.annotation = constraintAnnotation;
    }

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
