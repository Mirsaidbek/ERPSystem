package dev.said.controllers;


import dev.said.dto.CreateAuthUserDTO;
import dev.said.dto.GetTokenDTO;
import dev.said.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

@RequestMapping("/api/v1/auth")
public class AuthController {


    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<GetTokenDTO> login(
            CreateAuthUserDTO request
    ) {
        return ResponseEntity.ok(authService.login(request));
    }
}
