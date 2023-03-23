package prj.searching.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import prj.searching.interceptor.AuthInterceptor;

@Configuration
@EnableWebMvc
public class InterceptorConfig implements WebMvcConfigurer {
    @Autowired
    private AuthInterceptor interceptor;

    @Override
    public void addInterceptors(InterceptorRegistry interceptorRegistry) {
        interceptorRegistry.addInterceptor(interceptor)
                .addPathPatterns("/**")
                .excludePathPatterns("/heath")
                .excludePathPatterns("/swagger-ui/**", "/swagger-resources/**", "/v3/api-docs")
                // TODO : 필요시 추가적으로 제외하고 싶은 경로가 있으면 추가할 수 있다.
                ;
    }
}
