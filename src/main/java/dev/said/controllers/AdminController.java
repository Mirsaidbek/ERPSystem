package dev.said.controllers;

import dev.said.domains.AuthUser;
import dev.said.domains.User;
import dev.said.dto.auth.UpdateAuthUserDTO;
import dev.said.dto.user.CreateUserDTO;
import dev.said.repository.UserRepository;
import dev.said.service.AdminService;
import dev.said.service.UserService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
public class AdminController {

    private final AdminService adminService;
    private final UserRepository userRepository;
    private final UserService userService;

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

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/delete-user")
    public ResponseEntity<User> deleteUser(
            @NonNull @RequestBody String username
    ) {
        return ResponseEntity.ok(adminService.deleteUser(username));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/get-user/{username}")
    public ResponseEntity<User> listAllActiveUsers(
            @NonNull @PathVariable String username
    ) {
        return ResponseEntity.ok(userService.findByUserName(username));
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/list-all-active-users")
    public ResponseEntity<List<User>> listAllActiveUsers() {
        return ResponseEntity.ok(userRepository.findAllNonDeletedUsers());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/list-all-users")
    public ResponseEntity<List<User>> listAllUsers() {
        return ResponseEntity.ok(userRepository.findAll());
    }

}
