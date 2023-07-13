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

    @PreAuthorize("hasAnyRole('ROLE_EMPLOYEE', 'ROLE_ADMIN', 'ROLE_USER')")
    @PostMapping("/enter")
    public ResponseEntity<EnterOut> doEnter() {
        return ResponseEntity.ok(employeeService.doEnter());
    }

    @PreAuthorize("hasAnyRole('ROLE_EMPLOYEE', 'ROLE_ADMIN', 'ROLE_USER')")
    @PostMapping("/exit")
    public ResponseEntity<EnterOut> doExit(
    ) {
        return ResponseEntity.ok(employeeService.doExit());
    }

    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    @PostMapping("/create-leaverequest")
    public ResponseEntity<LeaveRequest> createLeaveRequest(
            @NonNull @ParameterObject CreateLeaveRequestDTO dto
    ) {
        return ResponseEntity.ok(employeeService.createLeaveRequest(dto));
    }

    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    @PostMapping("/delete-leaverequest/{leaveRequestId}")
    public ResponseEntity<LeaveRequest> createLeaveRequest(
            @NonNull @PathVariable Long leaveRequestId
    ) {
        return ResponseEntity.ok(employeeService.deleteLeaveRequest(leaveRequestId));
    }

    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    @GetMapping("/list-all-leaverequest")
    public ResponseEntity<List<LeaveRequest>> getListAllLeaveRequest(
    ) {
        return ResponseEntity.ok(employeeService.findAllByEmployeeId());
    }

    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    @GetMapping("/last-leaverequest")
    public ResponseEntity<LeaveRequest> getLastLeaveRequest(
    ) {
        return ResponseEntity.ok(employeeService.findLastRequestByEmployeeId());
    }

}
