package dev.said.service;

import dev.said.config.security.JwtUtils;
import dev.said.config.security.SessionUser;
import dev.said.domains.AuthUser;
import dev.said.dto.auth.CreateAuthUserDTO;
import dev.said.dto.auth.ResetPasswordDTO;
import dev.said.dto.token.GetTokenDTO;
import dev.said.dto.token.TokenResponse;
import dev.said.repository.AuthUserRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Lazy
@Service
//@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final AuthUserRepository authUserRepository;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;

    public AuthService(AuthenticationManager authenticationManager, AuthUserRepository authUserRepository, JwtUtils jwtUtils, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.authUserRepository = authUserRepository;
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;
    }

    public GetTokenDTO login(CreateAuthUserDTO dto) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.username(),
                        dto.password()
                )
        );
        AuthUser user = authUserRepository.findByUsername(dto.username())
                .orElseThrow();
        TokenResponse tokenResponse = jwtUtils.generateToken(user.getUsername());

        return GetTokenDTO.builder()
                .token(tokenResponse.getAccessToken())
                .build();
    }

    public String resetPassword(ResetPasswordDTO dto) {

        AuthUser authUser = authUserRepository.findByUsername(dto.username())
                .orElseThrow(() -> new RuntimeException("Username or password not correct"));

//        boolean matches = passwordEncoder.matches(dto.oldPassword(), authUser.getPassword());
        if (!passwordEncoder.matches(dto.oldPassword(), authUser.getPassword()))
            throw new RuntimeException("Password or username not correct");

        if (!dto.newPassword().equals(dto.confirmPassword()))
            throw new RuntimeException("Try again. Password and confirm password not match");

        authUser.setPassword(passwordEncoder.encode(dto.newPassword()));
        authUserRepository.save(authUser);

        return "Password changed successfully";
    }

}
