package dev.said.controllers;

import dev.said.domains.Document;
import dev.said.domains.EnterOut;
import dev.said.domains.LeaveRequest;
import dev.said.dto.leaverequest.CreateLeaveRequestDTO;
import dev.said.service.EmployeeService;
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
@RequestMapping("/api/v1/employee")
public class EmployeeController {

    private final EmployeeService employeeService;


    // working with employee enter/exit:


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


    //    ************************************************************************************************

    // working with employee leave request:

    // create leave request
    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    @PostMapping("/create-leaverequest")
    public ResponseEntity<LeaveRequest> createLeaveRequest(
            @NonNull @ParameterObject CreateLeaveRequestDTO dto
    ) {
        return ResponseEntity.ok(employeeService.createLeaveRequest(dto));
    }

    // delete leave request
    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    @PostMapping("/delete-leaverequest/{leaveRequestId}")
    public ResponseEntity<LeaveRequest> createLeaveRequest(
            @NonNull @PathVariable Long leaveRequestId
    ) {
        return ResponseEntity.ok(employeeService.deleteLeaveRequest(leaveRequestId));
    }

    // get list all leave request
    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    @GetMapping("/list-all-leaverequest")
    public ResponseEntity<List<LeaveRequest>> getListAllLeaveRequest(
    ) {
        return ResponseEntity.ok(employeeService.findAllByEmployeeId());
    }

    // get last leave request
    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    @GetMapping("/last-leaverequest")
    public ResponseEntity<LeaveRequest> getLastLeaveRequest(
    ) {
        return ResponseEntity.ok(employeeService.findLastRequestByEmployeeId());
    }

//    ************************************************************************************************

    // working with employee resume:
    // create resume
    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    @PostMapping(name = "/upload-resume", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Document> createResume(
            @RequestPart("file") MultipartFile file
    ) {
        return ResponseEntity.ok(employeeService.createResume(file));
    }

    // update resume
    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    @PutMapping(name = "/update-resume", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Document> updateResume(
            @RequestPart("file") MultipartFile file
    ) {
        return ResponseEntity.ok(employeeService.updateResume(file));
    }

    // delete resume
    @PreAuthorize("hasRole('ROLE_EMPLOYEE')")
    @DeleteMapping(name = "/delete-resume")
    public ResponseEntity<Document> deleteResume(
    ) {
        return ResponseEntity.ok(employeeService.deleteResume());
    }

    // get resume
    @PreAuthorize("hasAnyRole('ROLE_EMPLOYEE', 'ROLE_ADMIN', 'ROLE_USER')")
    @GetMapping(name = "/get-resume")
    public ResponseEntity<Document> getResume(
    ) {
        return ResponseEntity.ok(employeeService.getResume());
    }


    //    ************************************************************************************************


}
