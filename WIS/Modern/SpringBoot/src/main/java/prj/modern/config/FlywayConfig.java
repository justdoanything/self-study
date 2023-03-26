package prj.modern.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.flyway.FlywayMigrationStrategy;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FlywayConfig {
    @Value(value = "${spring.profiles.active}")
    private String profile;

    @Bean
    public FlywayMigrationStrategy cleanMigrationStrategy() {
        return flyway -> {
            // TODO : 필요한 경우 특정 프로필에서 flyway 동작을 특정할 수 있다. (예시)
//            if(profile.equals("default")) {
//                flyway.clean();
//            }
            flyway.migrate();
        };
    }
}
