package dev.said.controllers;

import dev.said.domains.LeaveRequest;
import dev.said.dto.leaverequest.CreateLeaveRequestDTO;
import dev.said.enums.leaverequest.LeaveRequestType;
import dev.said.service.EmployeeService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping("/create-leaverequest")
    public ResponseEntity<LeaveRequest> createLeaveRequest(
            @NonNull @ParameterObject CreateLeaveRequestDTO dto
    ) {
        return ResponseEntity.ok(employeeService.createLeaveRequest(dto));

    }
}
