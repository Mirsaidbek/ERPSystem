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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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

    public List<LeaveRequest> findAllByEmployeeId(Long employeeId) {

        return leaveRequestRepository.findAllByEmployeeId(employeeId)
                .orElseThrow(() -> new RuntimeException("Leave Request for this employee id not found"));
    }

    public LeaveRequest findLastRequestByEmployeeId(Long employeeId) {
        return leaveRequestRepository.findLastRequestByEmployeeId(employeeId)
                .orElseThrow(() -> new RuntimeException("Leave Request for this employee id not found"));
    }


    public EnterOut doEnter(Long userId) {

        EnterOut enterOut = enterOutRepository.findLastByUserId(userId);

        if (enterOut == null) {
            return enterOutRepository.save(
                    EnterOut.childBuilder()
                            .userId(userId)
                            .enteredAt(LocalDateTime.now())
                            .build()
            );
        } else if (enterOut.getLeftAt().toString().equals(enterOut.getEnteredAt().toString())) {
            throw new RuntimeException("User has not left the office yet");
        } else {
            return enterOutRepository.save(
                    EnterOut.childBuilder()
                            .userId(userId)
                            .enteredAt(LocalDateTime.now())
                            .build()
            );
        }
    }


    public EnterOut doExit(@NonNull Long userId) {

        EnterOut enterOut = enterOutRepository.findLastByUserId(userId);

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

    public String updateProfilePicture(Long userId, String profilePicture) {
        return "Hello";
    }

    public List<Document> getAllDocsBySessionUser() {
        if (sessionUser.id() == -1) {
            throw new RuntimeException("User not found");
        }
        return documentRepository.findAllByCreatedBy(sessionUser.id());
    }
}
