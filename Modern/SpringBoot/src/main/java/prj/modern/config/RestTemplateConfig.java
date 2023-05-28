package prj.modern.config;

import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import java.nio.charset.Charset;
import java.time.Duration;

@Configuration
public class RestTemplateConfig {
    @Value(value = "${spring.rest-template.connection-timeout}")
    private int connectionTimeout;

    @Value(value = "${spring.rest-template.read-timeout}")
    private int readTimeout;

    @Value(value = "${spring.rest-template.max-connection}")
    private int maxConnection;

    @Value(value = "${spring.rest-template.max-per-route}")
    private int maxPerRoute;

    @Bean
    public RestTemplate setRestTemplate(RestTemplateBuilder restTemplateBuilder) {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setHttpClient(
                HttpClientBuilder.create()
                        .setMaxConnPerRoute(maxConnection)
                        .setMaxConnPerRoute(maxPerRoute)
                        .build()
        );
        return restTemplateBuilder
                .requestFactory(() -> factory)
                .setConnectTimeout(Duration.ofMillis(connectionTimeout))
                .setReadTimeout(Duration.ofMillis(readTimeout))
                .additionalMessageConverters(new StringHttpMessageConverter(Charset.forName("UTF-8")))
                // StringHttpMessageConverter : HTTP 응답 본문을 문자열로 변환
                // MappingJackson2HttpMessageConverter : HTTP 응답 본문을 자바 객체로 변환
                // XML을 처리할 경우엔 MappingJackson2XmlHttpMessageConverter을 사용해야 한다.
                .build();
    }
}
