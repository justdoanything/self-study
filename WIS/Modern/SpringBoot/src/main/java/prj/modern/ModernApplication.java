package prj.modern;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableHystrix
@EnableJpaAuditing
public class ModernApplication {

	public static void main(String[] args) {
		SpringApplication.run(ModernApplication.class, args);
	}

}
