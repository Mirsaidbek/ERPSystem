package dev.said.controllers;

import dev.said.domains.AuthUser;
import dev.said.domains.User;
import dev.said.dto.auth.CreateAuthUserDTO;
import dev.said.dto.auth.UpdateAuthUserDTO;
import dev.said.dto.user.CreateUserDTO;
import dev.said.service.AdminService;
import lombok.NonNull;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequiredArgsConstructor
@PreAuthorize("hasRole('ROLE_ADMIN')")
@RequestMapping("/api/v1/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/add-user")
    public ResponseEntity<AuthUser> addUser(
            @NonNull @ParameterObject CreateAuthUserDTO dto
    ) {
        return ResponseEntity.ok(adminService.addUser(dto));
    }

    @PutMapping("/edit-auth-user")
    public ResponseEntity<AuthUser> updateAuthUser(
            @NonNull @ParameterObject UpdateAuthUserDTO dto
    ) {
//        return ResponseEntity.ok(adminService.editUser(dto));
        adminService.updateAuthUser(dto);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/create-user")
    public ResponseEntity<User> addUser(
            @NonNull @ParameterObject CreateUserDTO dto
    ) {
        return ResponseEntity.ok(adminService.createUser(dto));
    }

}
