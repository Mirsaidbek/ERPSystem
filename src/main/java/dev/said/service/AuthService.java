package dev.said.service;

import dev.said.config.security.JwtUtils;
import dev.said.domains.AuthUser;
import dev.said.dto.auth.CreateAuthUserDTO;
import dev.said.dto.token.GetTokenDTO;
import dev.said.dto.token.TokenResponse;
import dev.said.repository.AuthUserRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Lazy
@Service
//@RequiredArgsConstructor
public class AuthService {

    private final AuthenticationManager authenticationManager;
    private final AuthUserRepository authUserRepository;
    private final JwtUtils jwtUtils;

    public AuthService(AuthenticationManager authenticationManager, AuthUserRepository authUserRepository, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.authUserRepository = authUserRepository;
        this.jwtUtils = jwtUtils;
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
}
