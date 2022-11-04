package modern.aop;

import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;

import modern.exception.CustomException;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class ResponseAspect {
    @Autowired
    private Validator validator;

    @AfterReturning(pointcut = "execution(* modern.controller..*(..))", returning = "response")
    public void validateResponse(JoinPoint joinPoint, Object response) throws CustomException {
        validateResponse(response);
    }

    private void validateResponse(Object object) throws CustomException {

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
            throw new CustomException(msg);
        }
    }
}
