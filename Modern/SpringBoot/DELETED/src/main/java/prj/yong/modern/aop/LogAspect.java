package prj.yong.modern.aop;

import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

@Component
@Aspect
@Slf4j
public class LogAspect {
    private static final String CLASS_LOG_FORMAT = "Class Name : [";
    private static final String METHOD_LOG_FORMAT = "Method Name : [";

    @Before("(execution(* modern.exception..*(..))"
            + " || execution(* modern.interceptor..*(..))"
            + " || execution(* modern.repository..*(..)))"
            + " && !@annotation(NoLoggingAspect)")
    public void beforeMethod(JoinPoint joinPointØ) {
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        StringBuilder logStringBuilder = new StringBuilder();
        String logInfo = logStringBuilder.append("원하는 LOG 내용").toString();

        log.info(logInfo);
    }

    @AfterReturning(
            pointcut = "(execution(* modern.exception..*(..))"
                    + " || execution(* modern.interceptor..*(..))"
                    + " || execution(* modern.repository..*(..)))"
                    + " && !@annotation(NoLoggingAspect)",
            returning = "result")
    public void afterMethod(JoinPoint joinPoint, Object result) {
        HttpServletRequest request =
                ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();

        StringBuilder logStringBuilder = new StringBuilder();
        String logInfo = logStringBuilder.append("원하는 LOG 내용").toString();

        log.info(logInfo);
    }
}
