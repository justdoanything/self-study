package modern.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

/**
 * @Reference : https://engkimbs.tistory.com/m/746
 * @Description
 *   AOP : Aspect Oriented Programming
 *   관점 지향 프로그래밍으로 어떤 로직을 기준으로 핵심적인 관점, 부가적인 괌점으로 나누어서 보고 그 관점을 기준으로 모듈화한다.
 *   흩어진 관심사(Crosscutting Concerns)를 Aspect로 모듈화하고 핵심적인 비지니스에선 분리하여 재사용하겠다는 것이다.
 */
@Component
@Aspect
public class SpringAspect {
    /**
     * @Aspect : 흩어진 관심사를 모듈화한 결과
     * @Target : @Aspect 를 사용하는 곳
     * @Advice : 부가 기능이 실질적으로 어떻게 동작하는지 명시해놓은 구현체
     * @JoinPoint : @Advice 가 적용될 위치, 끼어들 수 있는 지점으로 Method 진입 시잠, 생성자 호출 시점 등 특정한 시점에 적용할 수 있다.
     * @PointCut : @JoinPoint 의 상세한 스펙을 정의한 것으로 "메서드 진입 시점에 호출할 것" 과 같이 구체적인 @Advice 실행 시점을 정할 수 있다.
     */
    @Around(    // Target 메서드를 감싸서 특정 @Advice 를 실행하기 위한 Annotation
            "execution(* modern.service..*(..))"    // 특정 패키지 아래에 있는 모든 함수에 @Aspect 를 적용
    )
    public void exampleLogging(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        proceedingJoinPoint.proceed(); // 메서드 호출 자체를 감쌈
        System.out.println(System.currentTimeMillis() - startTime);
    }
}
