package prj.yong.modern.config;

import java.nio.charset.Charset;
import java.time.Duration;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.client.RestTemplate;

/**
 * @Description
 *
 * @Reference
 *   - https://www.baeldung.com/spring-rest-template-builder
 */
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
    public RestTemplate restTemplate(final RestTemplateBuilder restTemplateBuilder) {
        HttpComponentsClientHttpRequestFactory factory = new HttpComponentsClientHttpRequestFactory();
        factory.setHttpClient(HttpClientBuilder.create()
                .setMaxConnTotal(maxConnection) // 최대 오픈되는 Connection 수. (연결할 유지할 최대 숫자)
                .setMaxConnPerRoute(maxPerRoute) // IP, PORT 쌍에 대해서 수행할 Connection 수. (특정 경로당 최대 숫자)
                .build());
        return restTemplateBuilder
                .requestFactory(() -> factory)
                .setConnectTimeout(Duration.ofMillis(connectionTimeout)) // connection=timeout
                .setReadTimeout(Duration.ofMillis(readTimeout)) // read-timeout
                .additionalMessageConverters(new StringHttpMessageConverter(Charset.forName("UTF-8")))
                .defaultHeader("Content-Type", "application/json")
                .build();
    }
}
