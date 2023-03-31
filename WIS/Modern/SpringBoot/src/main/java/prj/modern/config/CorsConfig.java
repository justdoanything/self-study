package prj.modern.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Description
 *   CORS 공통 정책을 설정하는 클래스
 */
@Configuration
@EnableWebMvc
public class CorsConfig implements WebMvcConfigurer {
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // 해당 정책을 적용할 특정 URL을 지정할 수 있다.
                .allowedOriginPatterns("*") // 특정한 Origin Domain만 CROS를 허용하면 싶다면 특정 Domain을 적는다.
                .allowedMethods("GET")
                .allowCredentials(true); // 브라우저의 쿠키와 같은 인증 정보를 전송하려면 true로 설정해야 한다.
    }
}
