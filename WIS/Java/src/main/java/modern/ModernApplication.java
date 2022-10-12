package modern;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ModernApplication {
    public static void main(String[] args) {
        SpringApplication.run(ModernApplication.class, args);
    }
}
