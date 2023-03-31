package prj.modern.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @Description
 *   @Retention : Annotion의 유효기간을 지정한다. RUNTIME, CLASS, SOURCE 중에서 선택할 수 있다.
 *     - RUNTINE : Runtime 시에도 Annotaion이 유효하다.
 *     - CLASS : 클래스 파일에서만 Annotaion 정보가 유지된다. Runtime 시에는 정보를 읽을 수 없다.
 *     - SOURCE : 소스 코드 상에서만 정보가 유지된다. Compile 후에는 정보가 삭제된다.
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface NoLogging { }
