package dev.said;

import dev.said.config.security.SessionUser;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.AuditorAware;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@OpenAPIDefinition
@SecurityScheme(
        name = "Bearer Authentication",
        type = SecuritySchemeType.HTTP,
        bearerFormat = "JWT",
        scheme = "bearer"
)
@EnableAsync
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }

    @Bean
    public AuditorAware<Long> auditorProvider(SessionUser sessionUser) {
        return () -> java.util.Optional.of(sessionUser == null ? -1L : sessionUser.id());
    }
}

// TODO: 11.07.2023 AuditorAware nimagadir ishlamayapti deb o'ylavomman, shuni to'g'irlash kere.
// TODO: 11.07.2023 Hamma narsani ko'rib chiqish kere qatgadir yengi api lar qo'shish kerak bo'ladi. Masalan : LeaveRequestni cancel qilish -> deleted = true.

