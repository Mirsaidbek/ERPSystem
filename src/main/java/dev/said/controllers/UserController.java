package dev.said.controllers;

import dev.said.domains.LeaveRequest;
import dev.said.domains.User;
import dev.said.dto.leaverequest.UpdateLeaveRequestStatusDTO;
import dev.said.dto.user.CreateUserDTO;
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
@PreAuthorize("hasRole('ROLE_USER')")
@RequestMapping("/api/v1/user")
public class UserController {

    private final UserService userService;


    // working with empolyees
    @PostMapping("/create-employee")
    public ResponseEntity<User> createEmployee(
            @NonNull @ParameterObject CreateUserDTO dto
    ) {
        return ResponseEntity.ok(userService.createEmployee(dto));
    }

    @GetMapping("/list-employees")
    public ResponseEntity<List<User>> listEmployees() {
        return ResponseEntity.ok(userService.findAll());
    }


    //working with leaverequests
    @GetMapping("/pending-leaverequests")
    public ResponseEntity<List<LeaveRequest>> getPendingLeaveRequests() {
        return ResponseEntity.ok(userService.getPendingLeaveRequests());
    }

    @GetMapping("/approved-leaverequests")
    public ResponseEntity<List<LeaveRequest>> getApprovedLeaveRequests() {
        return ResponseEntity.ok(userService.getApprovedLeaveRequests());
    }

    @GetMapping("/rejected-leaverequests")
    public ResponseEntity<List<LeaveRequest>> getRejectedLeaveRequests() {
        return ResponseEntity.ok(userService.getRejectedLeaveRequests());
    }

    @PutMapping("/update--leaverequest-status")
    public ResponseEntity<LeaveRequest> updateLeaveRequestStatus(
            @NonNull @ParameterObject UpdateLeaveRequestStatusDTO dto
            ) {
        return ResponseEntity.ok(userService.updateLeaveRequestStatus(dto));
    }

}
