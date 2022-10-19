package modern.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

@Aspect
@Component
public class ResponseAspect {
    @Autowired private Validator validator;

    @AfterReturning(pointcut = "execution(* modern.controller..*(..))", returning = "response")
    public void validateResponse(JoinPoint joinPoint, Object response) throws Exception {
//        validateResponse(response);
    }

    private void validateResponse(Object object) throws Exception {

        Set<ConstraintViolation<Object>> validationResults = validator.validate(object);

        if (validationResults.size() > 0) {

            StringBuffer sb = new StringBuffer();

            for (ConstraintViolation<Object> error : validationResults) {
                sb.append(error.getPropertyPath())
                        .append(" - ")
                        .append(error.getMessage())
                        .append("\n");
            }

            String msg = sb.toString();
            throw new Exception(msg);
        }
    }
}
