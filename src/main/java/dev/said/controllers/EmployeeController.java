package dev.said.controllers;

import dev.said.domains.EnterOut;
import dev.said.domains.LeaveRequest;
import dev.said.dto.leaverequest.CreateLeaveRequestDTO;
import dev.said.service.EmployeeService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    @PostMapping("/enter/{userId}")
    public ResponseEntity<EnterOut> doEnter(
            @PathVariable @NonNull Long userId
    ) {
        return ResponseEntity.ok(employeeService.doEnter(userId));
    }

    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    @PostMapping("/exit/{userId}")
    public ResponseEntity<EnterOut> doExit(
            @PathVariable @NonNull Long userId
    ) {
        return ResponseEntity.ok(employeeService.doExit(userId));
    }

    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    @PostMapping("/create-leaverequest")
    public ResponseEntity<LeaveRequest> createLeaveRequest(
            @NonNull @ParameterObject CreateLeaveRequestDTO dto
    ) {
        return ResponseEntity.ok(employeeService.createLeaveRequest(dto));
    }

    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    @GetMapping("/list-all-leaverequest/{employeeId}")
    public ResponseEntity<List<LeaveRequest>> getListAllLeaveRequest(
            @NonNull @PathVariable Long employeeId
    ) {
        return ResponseEntity.ok(employeeService.findAllByEmployeeId(employeeId));
    }


    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    @GetMapping("/last-leaverequest/{employeeId}")
    public ResponseEntity<LeaveRequest> getLastLeaveRequest(
            @NonNull @PathVariable Long employeeId
    ) {
        return ResponseEntity.ok(employeeService.findLastRequestByEmployeeId(employeeId));
    }

}
