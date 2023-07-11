package dev.said.service;

import dev.said.config.jwt.JwtUtils;
import dev.said.domains.AuthUser;
import dev.said.enums.Active;
import dev.said.enums.Language;
import dev.said.enums.Role;
import dev.said.repository.AuthUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class AuthServiceTest {

    private AuthService underTest;
    @Mock
    private AuthenticationManager authenticationManager;
    @Mock
    private AuthUserRepository authUserRepository;
    @Mock
    private JwtUtils jwtUtils;
    @Mock
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        underTest = new AuthService(authenticationManager, authUserRepository, jwtUtils, passwordEncoder);
        authUserRepository.save(
                AuthUser.childBuilder()
                        .username("said")
                        .password("$2a$10$XUiwTFxeJsFDWLiH7FAOS.Djuigco0TGUxZumBbtDtRfpKdZN.d0O")
                        .language(Language.UZBEK)
                        .role(Role.ADMIN)
                        .active(Active.ACTIVE)
                        .build());
    }

    @Test
    void canGetAllAuthUsers() {
        //when
        underTest.getAllUsers();
        //then
        verify(authUserRepository).findAll();
    }

    @Test
    @Disabled
    void canLoadUserByUsername() {
        //when
        underTest.loadUserByUsername("said");
        //then
        verify(authUserRepository).findByUsername("said");


    }


    @Test
    @Disabled
    void generateToken() {
    }

    @Test
    @Disabled
    void refreshToken() {
    }

    @Test
    @Disabled
    void login() {
    }

    @Test
    @Disabled
    void resetPassword() {
    }

    @Test
    @Disabled
    void logout() {
    }
}