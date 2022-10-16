package modern.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

@Component
@Aspect
@Slf4j
public class Slf4jAspect {

    @Before("(execution(* modern.exception..*(..)) "
            + " || execution(* modern.interceptor..*(..))"
            + " || execution(* modern.repository..*(..)))"
            + " && !@annotation(NoLoggingAspect)")
    public void beforeMethod(JoinPoint joinPointØ){

    }
}
