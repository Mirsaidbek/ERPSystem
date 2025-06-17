package dev.said.controllers;

import dev.said.domains.Document;
import dev.said.domains.LeaveRequest;
import dev.said.domains.User;
import dev.said.dto.leaverequest.UpdateLeaveRequestStatusDTO;
import dev.said.dto.user.CreateUserDTO;
import dev.said.service.UserService;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/user")
public class UserController { // this can be called hr management - User with USER role can be considered as a hr

    private final UserService userService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/create-employee")
    public ResponseEntity<User> createEmployee(
            @NonNull @ParameterObject CreateUserDTO dto
    ) {
        return ResponseEntity.ok(userService.createEmployee(dto));
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/list-employees")
    public ResponseEntity<List<User>> listEmployees() {
        return ResponseEntity.ok(userService.findAll());
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/list-leaverequests")
    public ResponseEntity<List<LeaveRequest>> getListLeaveRequests() {
        return ResponseEntity.ok(userService.getListLeaveRequests());
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/pending-leaverequests")
    public ResponseEntity<List<LeaveRequest>> getPendingLeaveRequests() {
        return ResponseEntity.ok(userService.getPendingLeaveRequests());
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/approved-leaverequests")
    public ResponseEntity<List<LeaveRequest>> getApprovedLeaveRequests() {
        return ResponseEntity.ok(userService.getApprovedLeaveRequests());
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/rejected-leaverequests")
    public ResponseEntity<List<LeaveRequest>> getRejectedLeaveRequests() {
        return ResponseEntity.ok(userService.getRejectedLeaveRequests());
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("/update-leaverequest-status")
    public ResponseEntity<LeaveRequest> updateLeaveRequestStatus(
            @NonNull @ParameterObject UpdateLeaveRequestStatusDTO dto
    ) {
        return ResponseEntity.ok(userService.updateLeaveRequestStatus(dto));
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/get-worked-time/{userId}/{date}")
    public ResponseEntity<String> getWorkedTime(
            @PathVariable Long userId,
            @PathVariable @Parameter(description = "Date must be in the form of 'YYYY-MM-DD'", example = "2025-01-20") String date
    ) {
        return ResponseEntity.ok(userService.getWorkedTime(userId, date.concat(" __:__:__%")));
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/get-worked-time/{userId}/{startDate}/{endDate}")
    public ResponseEntity<String> getWorkedTimeWithInterval(
            @PathVariable Long userId,
            @PathVariable @Parameter(description = "date must be in the form of 'YYYY-MM-DD'", example = "2023-01-20") String startDate,
            @PathVariable @Parameter(description = "date must be in the form of 'YYYY-MM-DD'", example = "2023-12-31") String endDate

    ) {
        return ResponseEntity.ok(userService.getWorkedTimeWithInterval(userId, startDate, endDate));
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_EMPLOYEE')")
    @PutMapping(path = "/update-picture", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Document> update(
            @RequestPart("file") MultipartFile file) {
        return ResponseEntity.ok(userService.updateProfilePicture(file));
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN')")

    @PutMapping(path = "/update-picture-of-employee/{employeeId}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Document> update(
            @RequestPart("file") MultipartFile file,
            @PathVariable @NonNull Long employeeId
    ) {
        return ResponseEntity.ok(userService.updateProfilePicture(file, employeeId));
    }

    @PreAuthorize("hasAnyRole('ROLE_USER', 'ROLE_ADMIN', 'ROLE_EMPLOYEE')")
    @GetMapping(path = "/get-picture")
    public ResponseEntity<Document> getProfilePicture() {
        return ResponseEntity.ok(userService.getProfilePicture());
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping(path = "/update-employee-salary/{employeeId}")
    public ResponseEntity<User> updateEmployeeSalary(@PathVariable Long employeeId) {
        return ResponseEntity.ok(userService.updateEmployeeSalary(employeeId));
    }

}
