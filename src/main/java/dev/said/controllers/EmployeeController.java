package dev.said.controllers;

import dev.said.domains.LeaveRequest;
import dev.said.dto.leaverequest.CreateLeaveRequestDTO;
import dev.said.enums.leaverequest.LeaveRequestType;
import dev.said.service.EmployeeService;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/employee")
public class EmployeeController {

    private final EmployeeService employeeService;

    @PostMapping("/enter")
    public ResponseEntity<String> makeEnter(){
        return ResponseEntity.ok(employeeService.makeEnter());
    }


    @PostMapping("/exit")
    public ResponseEntity<String> makeExit(){
        return ResponseEntity.ok(employeeService.makeExit());
    }


    // working with leaverequests

    @PostMapping("/create-leaverequest")
    public ResponseEntity<LeaveRequest> createLeaveRequest(
            @NonNull @ParameterObject CreateLeaveRequestDTO dto
    ) {
        return ResponseEntity.ok(employeeService.createLeaveRequest(dto));
    }

    @GetMapping("/list-all-leaverequest/{employeeId}")
    public ResponseEntity<List<LeaveRequest>> getListAllLeaveRequest(
            @NonNull @PathVariable Long employeeId
            ) {
        return ResponseEntity.ok(employeeService.findAllByEmployeeId(employeeId));
    }

    @GetMapping("/last-leaverequest/{employeeId}")
    public ResponseEntity<LeaveRequest> getLastLeaveRequest(
            @NonNull @PathVariable Long employeeId
    ) {
        return ResponseEntity.ok(employeeService.findLastRequestByEmployeeId(employeeId));
    }

}
