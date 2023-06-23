//package dev.said.config.security;
//
//import dev.said.config.jwt.JwtUtils;
//import dev.said.domains.AuthUser;
//import dev.said.dto.token.RefreshTokenRequest;
//import dev.said.dto.token.TokenRequest;
//import dev.said.dto.token.TokenResponse;
//import dev.said.enums.TokenType;
//import dev.said.repository.AuthUserRepository;
//import org.springframework.context.annotation.Lazy;
//import org.springframework.lang.NonNull;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
////@Service
//public class UserDetailsService implements org.springframework.security.core.userdetails.UserDetailsService {
//    private final AuthUserRepository authUserRepository;
//    private final JwtUtils jwtUtils;
//    private final AuthenticationManager authenticationManager;
//
//    public UserDetailsService(AuthUserRepository authUserRepository,
//                              JwtUtils jwtUtils,
//                              @Lazy AuthenticationManager authenticationManager) {
//        this.authUserRepository = authUserRepository;
//        this.jwtUtils = jwtUtils;
//        this.authenticationManager = authenticationManager;
//    }
//
//    @Override
//    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
//        AuthUser authUser = authUserRepository.findByUsername(username)
//                .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
//        return new UserDetails(authUser);
//    }
//
//
//    public TokenResponse generateToken(@NonNull TokenRequest tokenRequest) {
//        String username = tokenRequest.username();
//        String password = tokenRequest.password();
//        UsernamePasswordAuthenticationToken authentication =
//                new UsernamePasswordAuthenticationToken(username, password);
//        authenticationManager.authenticate(authentication);
//        return jwtUtils.generateToken(username);
//    }
//
//    public TokenResponse refreshToken(@NonNull RefreshTokenRequest tokenRequest) {
//        String refreshToken = tokenRequest.refreshToken();
//
//        if (!jwtUtils.isValid(refreshToken, TokenType.REFRESH)) {
//            throw new RuntimeException("Invalid refresh token");
//        }
//        String username = jwtUtils.getUsername(refreshToken, TokenType.REFRESH);
//        authUserRepository.findAuthIdByUsername(username);
//
//        return TokenResponse.builder()
//                .refreshToken(refreshToken)
//                .refreshTokenExpiry(jwtUtils.getExpiry(refreshToken, TokenType.REFRESH))
//                .build();
//
//    }
//}
