package dev.said.controllers;

import dev.said.domains.User;
import dev.said.dto.user.CreateUserDTO;
import dev.said.service.UserService;
import lombok.NonNull;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
//@RequiredArgsConstructor
@PreAuthorize("hasAnyAuthority('ROLE_ADMIN', 'ROLE_USER')")
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create-employee")
    public ResponseEntity<User> getUser(
            @NonNull @ParameterObject CreateUserDTO dto
    ) {
        return ResponseEntity.ok(userService.createEmployee(dto));
    }

}
