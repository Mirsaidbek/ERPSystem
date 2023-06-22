package dev.said.controllers;

import dev.said.config.security.UserDetailsService;
import dev.said.dto.auth.CreateAuthUserDTO;
import dev.said.dto.auth.ResetPasswordDTO;
import dev.said.dto.token.GetTokenDTO;
import dev.said.dto.token.RefreshTokenRequest;
import dev.said.dto.token.TokenRequest;
import dev.said.dto.token.TokenResponse;
import dev.said.service.AuthService;
import jakarta.validation.Valid;
import lombok.NonNull;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
//@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final UserDetailsService userDetailsService;
    private final AuthService authService;

    public AuthController(UserDetailsService userDetailsService, AuthService authService) {
        this.userDetailsService = userDetailsService;
        this.authService = authService;
    }


    @GetMapping("/token")
    public ResponseEntity<TokenResponse> getToken(@Valid TokenRequest tokenRequest) {
        return ResponseEntity.ok(userDetailsService.generateToken(tokenRequest));
    }

    @PostMapping("/refresh")
    public ResponseEntity<TokenResponse> refresh(@Valid RefreshTokenRequest refreshTokenRequest) {
        return ResponseEntity.ok(userDetailsService.refreshToken(refreshTokenRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<GetTokenDTO> login(
            @NonNull CreateAuthUserDTO dto
    ) {
        return ResponseEntity.ok(authService.login(dto));
    }

    @PutMapping("/reset-password")
    public ResponseEntity<String> resetPassword(
            @NonNull ResetPasswordDTO dto
    ) {
        return ResponseEntity.ok(authService.resetPassword(dto));
    }


    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        return ResponseEntity.noContent().build();
    }


}
