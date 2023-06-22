package dev.said.service;

import dev.said.domains.AuthUser;
import dev.said.domains.User;
import dev.said.dto.auth.CreateAuthUserDTO;
import dev.said.dto.auth.UpdateAuthUserDTO;
import dev.said.dto.user.CreateUserDTO;
import dev.said.repository.AuthUserRepository;
import dev.said.repository.UserRepository;
import lombok.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
//@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final AuthUserRepository authUserRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminService(UserRepository userRepository,
                        AuthUserRepository authUserRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.authUserRepository = authUserRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthUser addUser(@NonNull CreateAuthUserDTO dto) {

        if (!dto.password().equals(dto.confirmPassword()))
            throw new RuntimeException("Password and confirm password not match");

        AuthUser authUser = AuthUser.childBuilder()
                .username(dto.username())
                .password(passwordEncoder.encode(dto.password()))
                .language(dto.language())
                .active(dto.active())
                .role(dto.role())
                .build();

        return authUserRepository.save(authUser);
    }

    public void updateAuthUser(@NonNull UpdateAuthUserDTO dto) {
        AuthUser authUser = authUserRepository.findByUsername(dto.username())
                .orElseThrow(() -> new RuntimeException("Username not found"));


        authUserRepository.updateAuthUser(dto.username(), dto.language(), dto.active(), dto.role());

    }

    public User createUser(@NonNull CreateUserDTO dto) {

        User.builder()
                .authUserId(1L)
                .firstName(dto.firstName())
                .lastName(dto.lastName())
                .dateOfBirth(dto.dateOfBirth())
                .gender(dto.gender())
                .martialStatus(dto.martialStatus())
                .phoneNumber(dto.phoneNumber())
                .email(dto.email())
                .employmentModel(dto.employmentModel())
                .hireDate(dto.hireDate())
                .resignationDate(dto.resignationDate())
                .probationPeriod(dto.probationPeriod())
                .role(dto.role())
                .salary(dto.salary())
                .reportingManagerId(dto.reportingManagerId())
                .build();

        return null;
    }
}
