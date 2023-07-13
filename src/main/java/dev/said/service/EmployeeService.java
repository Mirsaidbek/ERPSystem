package dev.said.service;

import dev.said.config.security.SessionUser;
import dev.said.domains.Document;
import dev.said.domains.EnterOut;
import dev.said.domains.LeaveRequest;
import dev.said.dto.leaverequest.CreateLeaveRequestDTO;
import dev.said.enums.leaverequest.LeaveRequestStatus;
import dev.said.repository.DocumentRepository;
import dev.said.repository.EnterOutRepository;
import dev.said.repository.LeaveRequestRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final LeaveRequestRepository leaveRequestRepository;
    private final EnterOutRepository enterOutRepository;
    private final SessionUser sessionUser;
    private final DocumentRepository documentRepository;

    public LeaveRequest createLeaveRequest(@NonNull CreateLeaveRequestDTO dto) {

        return leaveRequestRepository.save(LeaveRequest.childBuilder()
                .employeeId(dto.employeeId())
                .leaveRequestType(dto.leaveRequestType())
                .startDate(dto.startDate())
                .endDate(dto.endDate())
                .leaveRequestStatus(LeaveRequestStatus.PENDING)
                .build());
    }

    public List<LeaveRequest> findAllByEmployeeId() {
        if (sessionUser.id() == -1) {
            throw new RuntimeException("User not found");
        }

        return leaveRequestRepository.findAllByEmployeeId(sessionUser.id())
                .orElseThrow(() -> new RuntimeException("Leave Request for this employee id not found"));
    }

    public LeaveRequest findLastRequestByEmployeeId() {
        if (sessionUser.id() == -1) {
            throw new RuntimeException("User not found");
        }

        return leaveRequestRepository.findLastRequestByEmployeeId(sessionUser.id())
                .orElseThrow(() -> new RuntimeException("Leave Request for this employee id not found"));
    }


    public EnterOut doEnter() {

        if (sessionUser.id() == -1) {
            throw new RuntimeException("User not found");
        }

        Long sessionUserId = sessionUser.id();

        EnterOut enterOut = enterOutRepository.findLastByUserId(sessionUserId);

        if (enterOut == null) {
            return enterOutRepository.save(
                    EnterOut.childBuilder()
                            .userId(sessionUserId)
                            .enteredAt(LocalDateTime.now())
                            .build()
            );
        } else if (enterOut.getLeftAt().toString().equals(enterOut.getEnteredAt().toString())) {
            throw new RuntimeException("User has not left the office yet");
        } else {
            return enterOutRepository.save(
                    EnterOut.childBuilder()
                            .userId(sessionUserId)
                            .enteredAt(LocalDateTime.now())
                            .build()
            );
        }
    }


    public EnterOut doExit() {

        if (sessionUser.id() == -1) {
            throw new RuntimeException("User not found");
        }

        Long sessionUserId = sessionUser.id();

        EnterOut enterOut = enterOutRepository.findLastByUserId(sessionUserId);

        if (enterOut == null) {
            throw new RuntimeException("User has not enter the office yet");
        }

        if (!enterOut.getLeftAt().toString().equals(enterOut.getEnteredAt().toString())) {
            throw new RuntimeException("User has not enter the office yet");
        } else {
            enterOut.setLeftAt(LocalDateTime.now());


            LocalDateTime leftAt = enterOut.getLeftAt();

            Duration duration = Duration.between(enterOut.getLeftAt(), enterOut.getEnteredAt());
            long hours = duration.toHours();
            long minutes = duration.toMinutesPart();

            System.out.println("                        ===========================");
            System.out.println("               ================================================");
            System.out.println("        =================================================================");
            System.out.println("==================================================================================");
            System.out.println("                      Time difference: " + hours + " hours " + minutes + " minutes");
            System.out.println("==================================================================================");
            System.out.println("        =================================================================");
            System.out.println("               ================================================");
            System.out.println("                        ===========================");


            return enterOutRepository.save(enterOut);
        }
    }

    public static void main(String[] args) {
        String string = LocalDateTime.now().toString().substring(0, 10);
        System.out.println("string = " + string);
    }



    public LeaveRequest deleteLeaveRequest(Long leaveRequestId) {
        LeaveRequest leaveRequest = leaveRequestRepository.findById(leaveRequestId)
                .orElseThrow(() -> new RuntimeException("Leave Request not found"));

        leaveRequest.setDeleted(true);
        return leaveRequestRepository.save(leaveRequest);
    }
}
