package book.pattern.behavioral.chain;

import java.util.List;
import java.util.Map;

public abstract class EmployeeValidatorAbstract {
    private EmployeeValidatorAbstract nextValidator;

    private boolean hasNext() {
        return this.nextValidator != null;
    }

    public void setNextValidator(EmployeeValidatorAbstract nextValidator) {
        this.nextValidator = nextValidator;
    }

    public abstract void validate(List<Map<String, String>> dataset);

    public void executeValidation(List<Map<String, String>> dataset) {
        if(hasNext()){
            this.nextValidator.validate(dataset);
        }
    }
}
