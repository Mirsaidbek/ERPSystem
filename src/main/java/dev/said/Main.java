package dev.said;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import dev.said.config.security.SessionUser;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;

@SpringBootApplication
@OpenAPIDefinition
@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
@EnableAdminServer
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    public AuditorAware<Long> auditorProvider(SessionUser sessionUser) {
        return () -> java.util.Optional.of(sessionUser == null ? -1L : sessionUser.id());
    }
}

// TODO: 22.06.2023 Redo the security part - because it is working incorrectly
// TODO: 23.06.2023  Security is working incorrectly - must be corrected
