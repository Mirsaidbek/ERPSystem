package dev.said.config.initializers;

import dev.said.domains.AuthUser;
import dev.said.domains.User;
import dev.said.enums.*;
import dev.said.repository.AuthUserRepository;
import dev.said.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Component
@RequiredArgsConstructor
public class AdminInitializer implements ApplicationRunner {

    private final AuthUserRepository authUserRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) {
        String adminUsername = "admin";

        if (!authUserRepository.existsByUsername(adminUsername)) {

            AuthUser adminAuthUser = AuthUser.childBuilder()
                    .username(adminUsername)
                    .password(passwordEncoder.encode("123"))
                    .role(Role.ADMIN)
                    .active(Active.ACTIVE)
                    .language(Language.RUSSIAN)
                    .build();

            authUserRepository.save(adminAuthUser);


            User adminUser = User.builder()
                    .authUserId(adminAuthUser.getId())
                    .firstName("John")
                    .lastName("Doe")
                    .dateOfBirth(LocalDate.of(1990, 1, 1))
                    .gender(Gender.MALE)
                    .martialStatus(MartialStatus.SINGLE)
                    .phoneNumber("123456789")
                    .email("admin@gmail.com")
                    .employmentModel(EmploymentModel.FULL_TIME)
                    .hireDate(LocalDate.now())
                    .probationPeriod("No")
                    .position(Position.ADMINISTRATOR)
                    .salary(0L)
                    .deleted(false)
                    .build();

            userRepository.save(adminUser);

            System.out.println("✅ Default admin user created.");
        } else {
            System.out.println("ℹ️ Admin user already exists.");
        }
    }
}
