package dev.said.service;

import dev.said.config.jwt.JwtUtils;
import dev.said.config.security.UserDetails;
import dev.said.domains.AuthUser;
import dev.said.dto.auth.ResetPasswordDTO;
import dev.said.dto.token.GetTokenDTO;
import dev.said.dto.token.RefreshTokenRequest;
import dev.said.dto.token.TokenRequest;
import dev.said.dto.token.TokenResponse;
import dev.said.enums.TokenType;
import dev.said.repository.AuthUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Lazy
@Service
@RequiredArgsConstructor
public class AuthService implements UserDetailsService {

    private final AuthenticationManager authenticationManager;
    private final AuthUserRepository authUserRepository;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;


    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        AuthUser authUser = authUserRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
        return new UserDetails(authUser);
    }


    public TokenResponse generateToken(@NonNull TokenRequest tokenRequest) {
        String username = tokenRequest.username();
        String password = tokenRequest.password();
        UsernamePasswordAuthenticationToken authentication =
                new UsernamePasswordAuthenticationToken(username, password);
        authenticationManager.authenticate(authentication);
        return jwtUtils.generateToken(username);
    }

    public TokenResponse refreshToken(@NonNull RefreshTokenRequest tokenRequest) {
        String refreshToken = tokenRequest.refreshToken();

        if (!jwtUtils.isValid(refreshToken, TokenType.REFRESH)) {
            throw new RuntimeException("Invalid refresh token");
        }
        String username = jwtUtils.getUsername(refreshToken, TokenType.REFRESH);
        authUserRepository.findAuthIdByUsername(username);

        return TokenResponse.builder()
                .refreshToken(refreshToken)
                .refreshTokenExpiry(jwtUtils.getExpiry(refreshToken, TokenType.REFRESH))
                .build();

    }


    public GetTokenDTO login(TokenRequest dto) {
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
