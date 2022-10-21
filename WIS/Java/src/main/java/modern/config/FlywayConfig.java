package modern.config;

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
            if (profile.equals("feature") || profile.equals("dev")) {
                flyway.clean();
            }
            flyway.migrate();
        };
    }
}
