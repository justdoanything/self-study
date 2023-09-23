package prj.yong.modern.config;

import java.util.Arrays;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import prj.yong.modern.config.converter.SpringConverter;
import prj.yong.modern.interceptor.AuthenticationInterceptor;

@Configuration
@EnableWebMvc
public class InterceptorConfig implements WebMvcConfigurer {
    @Autowired
    private AuthenticationInterceptor authenticationInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry interceptorRegistry) {
        List<String> urlPatterns = Arrays.asList("/**");
        interceptorRegistry
                .addInterceptor(authenticationInterceptor)
                .addPathPatterns(urlPatterns)
                .excludePathPatterns("");
    }

    /**
     * @Description
     *  Controller 단에서 Param으로 enum 형태를 바로 받을 수 있다.
     *  URL의 경우엔 대부분 소문자로 되어 있고 enum은 대문자로 되어 있기 때문에 이럴 경우 Not Match Error가 발생한다.
     *  URL에서 넘어온 enum 값을 자동으로 대문자로 변환하도록 아래와 같이 Converter를 추가해줄 수 있다.
     * @param registry
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new SpringConverter());
    }
}
