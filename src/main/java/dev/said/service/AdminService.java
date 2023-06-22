package dev.said.service;

import dev.said.domains.AuthUser;
import dev.said.dto.auth.CreateAuthUserDTO;
import dev.said.repository.AuthUserRepository;
import dev.said.repository.UserRepository;
import lombok.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
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

    public AuthUser editUser(CreateAuthUserDTO dto) {


    }
}
