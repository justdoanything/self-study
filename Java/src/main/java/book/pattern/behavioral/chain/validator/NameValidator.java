package book.pattern.behavioral.chain.validator;

import book.pattern.behavioral.chain.EmployeeCode;
import book.pattern.behavioral.chain.EmployeeValidatorAbstract;

import java.util.List;
import java.util.Map;

public class NameValidator extends EmployeeValidatorAbstract {
    @Override
    public void validate(List<Map<String, String>> dataset) {
        System.out.println("=========> dataset에 대한 이름 검증 프로세스를 시작합니다.");
        for(Map<String, String> data : dataset){
            System.out.println(data + "에 대한 이름 검증 결과는 " + EmployeeCode.NAME.contains(data.get("NAME")) + " 입니다.");
        }
        this.executeValidation(dataset);
    }
}