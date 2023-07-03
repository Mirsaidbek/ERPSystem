package dev.said.service;

import dev.said.domains.LeaveRequest;
import dev.said.dto.leaverequest.CreateLeaveRequestDTO;
import dev.said.enums.leaverequest.LeaveRequestStatus;
import dev.said.repository.LeaveRequestRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final LeaveRequestRepository leaveRequestRepository;

    public LeaveRequest createLeaveRequest(@NonNull CreateLeaveRequestDTO dto) {

        return leaveRequestRepository.save(LeaveRequest.childBuilder()
                .employeeId(dto.employeeId())
                .leaveRequestType(dto.leaveRequestType())
                .startDate(dto.startDate())
                .endDate(dto.endDate())
                .leaveRequestStatus(LeaveRequestStatus.PENDING)
                .build());
    }

    public List<LeaveRequest> findAllByEmployeeId(Long employeeId) {
        return leaveRequestRepository.findAllByEmployeeId(employeeId)
                .orElseThrow(() -> new RuntimeException("Leave Request for this employee id not found"));
    }

    public LeaveRequest findLastRequestByEmployeeId(Long employeeId) {
        return leaveRequestRepository.findLastRequestByEmployeeId(employeeId)
                .orElseThrow(() -> new RuntimeException("Leave Request for this employee id not found"));
    }


    public String makeEnter() {
        return new Date().toString();
    }


    public String makeExit() {
        return new Date().toString();
    }
}
