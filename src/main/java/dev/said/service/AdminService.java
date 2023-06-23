package dev.said.service;

import dev.said.domains.AuthUser;
import dev.said.domains.User;
import dev.said.dto.auth.UpdateAuthUserDTO;
import dev.said.dto.user.CreateUserDTO;
import dev.said.enums.Active;
import dev.said.enums.Language;
import dev.said.enums.Role;
import dev.said.repository.AuthUserRepository;
import dev.said.repository.UserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {

    private final UserRepository userRepository;
    private final AuthUserRepository authUserRepository;
    private final PasswordEncoder passwordEncoder;

    public User createUser(@NonNull CreateUserDTO dto) {

        AuthUser authUser = AuthUser.childBuilder()
                .username(dto.email())
                .password(passwordEncoder.encode(dto.firstName().concat("1234@")))
                .language(Language.UZBEK)
                .active(Active.ACTIVE)
                .role(Role.USER)
                .build();
        AuthUser save = authUserRepository.save(authUser);

        User user = User.builder()
                .authUserId(save.getId())
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


        return userRepository.save(user);
    }

    public AuthUser updateAuthUser(@NonNull UpdateAuthUserDTO dto) {

        authUserRepository.findByUsername(dto.username())
                .orElseThrow(() -> new RuntimeException("User with given username: %s  not found".formatted(dto.username())));

        authUserRepository.updateAuthUser(dto.username(), dto.language(), dto.active(), dto.role());

        return authUserRepository.findByUsername(dto.username()).orElseThrow();
    }

    public User updateUser(@NonNull CreateUserDTO dto) {
        userRepository.findByEmailAndByFirstNameAndByLastName(dto.email(), dto.firstName(), dto.lastName())
                .orElseThrow(() -> new RuntimeException("User with given email: %s, first name: %s and last name: %s  not found".formatted(dto.email(), dto.firstName(), dto.lastName())));

        userRepository.updateUser(
                dto.firstName(),
                dto.lastName(),
                dto.dateOfBirth(),
                dto.gender(),
                dto.martialStatus(),
                dto.phoneNumber(),
                dto.email(),
                dto.employmentModel(),
                dto.hireDate(),
                dto.resignationDate(),
                dto.probationPeriod(),
                dto.role(),
                dto.salary(),
                dto.reportingManagerId()
        );

        return userRepository.findByEmailAndByFirstNameAndByLastName(dto.email(), dto.firstName(), dto.lastName()).orElseThrow();
    }
}
