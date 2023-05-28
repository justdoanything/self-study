package book.pattern.behavioral.chain;

import book.pattern.behavioral.chain.validator.DepartmentValidator;
import book.pattern.behavioral.chain.validator.NameValidator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ChainOfResponsibilityPattern {
    public static void main(String[] args) {
        List<Map<String, String>> dataset = new ArrayList<>();
        dataset.add(new HashMap<>(){{
            put("DEPARTMENT", "DEV");
            put("NAME", "John");
        }});
        dataset.add(new HashMap<>(){{
            put("DEPARTMENT", "DEV");
            put("NAME", "Melisa");
        }});
        dataset.add(new HashMap<>(){{
            put("DEPARTMENT", "TEST");
            put("NAME", "Tony");
        }});
        dataset.add(new HashMap<>(){{
            put("DEPARTMENT", "TEST");
            put("NAME", "Hong");
        }});
        dataset.add(new HashMap<>(){{
            put("DEPARTMENT", "MANAGEMENT");
            put("NAME", "Stranger");
        }});
        dataset.add(new HashMap<>(){{
            put("DEPARTMENT", "MANAGEMENT");
            put("NAME", "Chief");
        }});

        DepartmentValidator departmentValidator = new DepartmentValidator();
        NameValidator nameValidator = new NameValidator();
        departmentValidator.setNextValidator(nameValidator);
        departmentValidator.validate(dataset);
    }
}
