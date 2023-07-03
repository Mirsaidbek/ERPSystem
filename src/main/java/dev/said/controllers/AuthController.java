package dev.said.controllers;


import dev.said.config.jwt.JwtUtils;
import dev.said.dto.auth.ResetPasswordDTO;
import dev.said.dto.token.GetTokenDTO;
import dev.said.dto.token.RefreshTokenRequest;
import dev.said.dto.token.TokenRequest;
import dev.said.dto.token.TokenResponse;
import dev.said.service.AuthService;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {

    private final AuthService authService;

    private final AuthenticationManager authenticationManager;
    private final UserDetailsService userDetailsService;
    private final JwtUtils jwtUtils;

    //    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @GetMapping("/token")
    public ResponseEntity<TokenResponse> getToken(@Valid TokenRequest tokenRequest) {
        return ResponseEntity.ok(authService.generateToken(tokenRequest));
    }

    //    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @PostMapping("/refresh")
    public ResponseEntity<TokenResponse> refresh(@Valid RefreshTokenRequest refreshTokenRequest) {
        return ResponseEntity.ok(authService.refreshToken(refreshTokenRequest));
    }

    //    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(
            @NonNull String username,
            @NonNull String password
    ) {
        return ResponseEntity.ok(authService.login(username, password));
    }

    @PostMapping("/authenticate")
    public ResponseEntity<String> authenticate(
            @Valid TokenRequest dto
    ) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(dto.username(), dto.password())
        );
        final UserDetails user = userDetailsService.loadUserByUsername(dto.username());
        if (user != null) {
            return ResponseEntity.ok(String.valueOf(jwtUtils.generateToken(dto.username())));
        }
        return ResponseEntity.badRequest().body("Invalid username or password");
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @PutMapping("/reset-password")
    public ResponseEntity<String> resetPassword(
            @NonNull ResetPasswordDTO dto
    ) {
        return ResponseEntity.ok(authService.resetPassword(dto));
    }

    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @PostMapping("/logout")
    public ResponseEntity<Void> logout() {
        return ResponseEntity.noContent().build();
    }


}
