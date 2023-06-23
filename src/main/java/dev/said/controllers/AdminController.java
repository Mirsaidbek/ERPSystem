package dev.said.controllers;

import dev.said.domains.AuthUser;
import dev.said.domains.User;
import dev.said.dto.auth.UpdateAuthUserDTO;
import dev.said.dto.user.CreateUserDTO;
import dev.said.service.AdminService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.context.annotation.Role;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
public class AdminController {

    private final AdminService adminService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/create-user")
    public ResponseEntity<User> createUser(
            @NonNull @ParameterObject CreateUserDTO dto
    ) {
        return ResponseEntity.ok(adminService.createUser(dto));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/update-auth-user")
    public ResponseEntity<AuthUser> updateUser(
            @NonNull @ParameterObject UpdateAuthUserDTO dto

    ) {
        return ResponseEntity.ok(adminService.updateAuthUser(dto));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/update-user")
    public ResponseEntity<User> updateUser(
            @NonNull @ParameterObject CreateUserDTO dto
    ) {
        return ResponseEntity.ok(adminService.updateUser(dto));

    }

}
