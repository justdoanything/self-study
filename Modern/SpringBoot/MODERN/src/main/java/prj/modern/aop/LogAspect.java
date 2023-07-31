package prj.modern.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.util.Arrays;

@Component
@Aspect
@Slf4j
public class LogAspect {
    private static final String START_WITH_CLASS_NAME = "Class Name = [";
    private static final String START_WITH_METHOD_NAME = "Method Name = [";

    @Before(
            "(execution(* prj.modern.controller..*(..))"
                    + " || execution(* prj.modern.exception..*(..))"
                    + " || execution(* prj.modern.interceptor..*(..))"
                    + " || execution(* prj.modern.repository..*(..))"
                    + " || execution(* prj.modern.service..*(..)))"
                    + " && !@annotation(NoLogging)"
    )
    public void beforeMethod(JoinPoint joinPoint) {
        StringBuilder beforeMethodLog = new StringBuilder().append("Before method : ");

        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            // TODO : Request 객체에서 Log로 남기고 싶은 값 추가할 수 있다.
            beforeMethodLog.append("Something you want to know in Request: [")
                    .append(requestAttributes.getRequest().getHeaders("Something Name"))
                    .append(requestAttributes.getRequest().getHeaders("Something Name"))
                    .append("], ");
        }
        beforeMethodLog.append(START_WITH_CLASS_NAME)
                .append(joinPoint.getSignature().getDeclaringTypeName())
                .append("], ")
                .append(START_WITH_METHOD_NAME)
                .append(joinPoint.getSignature().getName())
                .append("], ")
                .append("Request Data : [ ")
                .append(Arrays.toString(joinPoint.getArgs()))
                .append("]");
        log.info(beforeMethodLog.toString());
    }

    @AfterReturning(
            pointcut = "(execution(* prj.modern.controller..*(..))"
                    + " || execution(* prj.modern.exception..*(..))"
                    + " || execution(* prj.modern.interceptor..*(..))"
                    + " || execution(* prj.modern.repository..*(..))"
                    + " || execution(* prj.modern.service..*(..)))"
                    + " && !@annotation(NoLogging)",
            returning = "response"
    )
    public void afterMethod(JoinPoint joinPoint, Object response) {
        StringBuilder afterMethodLog = new StringBuilder().append("After method : ");

        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes != null) {
            // TODO : Request 객체에서 Log로 남기고 싶은 값 추가할 수 있다.
            afterMethodLog.append("Something you want to know in Request: [")
                    .append(requestAttributes.getRequest().getHeaders("Something Name"))
                    .append(requestAttributes.getRequest().getHeaders("Something Name"))
                    .append("], ");
        }
        afterMethodLog.append(START_WITH_CLASS_NAME)
                .append(joinPoint.getSignature().getDeclaringTypeName())
                .append("], ")
                .append(START_WITH_METHOD_NAME)
                .append(joinPoint.getSignature().getName())
                .append("], ")
                .append("Response Data : [ ")
                .append(response)
                .append("]");
        log.info(afterMethodLog.toString());
    }
}
