package dev.said.controllers;

import dev.said.domains.AuthUser;
import dev.said.domains.User;
import dev.said.dto.auth.CreateAuthUserDTO;
import dev.said.service.AdminService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequiredArgsConstructor
@RequestMapping("/api/v1/admin")
public class AdminController {

    private final AdminService adminService;

    public AdminController(AdminService adminService) {
        this.adminService = adminService;
    }

    @PostMapping("/add-user")
    public ResponseEntity<User> addUser(
            @NonNull @ParameterObject CreateAuthUserDTO dto
    ) {
        return ResponseEntity.ok(adminService.addUser(dto));
    }

}
