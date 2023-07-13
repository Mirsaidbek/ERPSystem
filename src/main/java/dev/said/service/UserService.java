package dev.said.service;

import dev.said.config.jwt.JwtUtils;
import dev.said.config.security.SessionUser;
import dev.said.domains.*;
import dev.said.dto.leaverequest.UpdateLeaveRequestStatusDTO;
import dev.said.dto.user.CreateUserDTO;
import dev.said.enums.Active;
import dev.said.enums.Language;
import dev.said.enums.Role;
import dev.said.repository.*;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.Duration;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final AuthUserRepository authUserRepository;
    private final UserRepository userRepository;
    private final LeaveRequestRepository leaveRequestRepository;
    private final EnterOutRepository enterOutRepository;
    private final DocumentService documentService;
    private final JwtUtils jwtUtils;
    private final SessionUser sessionUser;
    private final DocumentRepository documentRepository;

    public User createEmployee(@NonNull CreateUserDTO dto) {

        AuthUser authUser = AuthUser.childBuilder()
                .username(dto.email())
                .password(passwordEncoder.encode(dto.firstName().concat("1234")))
                .language(Language.UZBEK)
                .active(Active.ACTIVE)
                .role(Role.EMPLOYEE)
                .build();
        AuthUser save = authUserRepository.save(authUser);

        Document document = documentRepository.findById(1L)
                .orElseThrow(() -> new RuntimeException("Document not found"));

        User user = User.builder()
                .authUserId(save.getId())
                .firstName(dto.firstName())
                .lastName(dto.lastName())
                .dateOfBirth(dto.dateOfBirth())
                .gender(dto.gender())
                .martialStatus(dto.martialStatus())
                .phoneNumber(dto.phoneNumber())
                .email(dto.email())
                .employmentModel(dto.employmentModel())
                .hireDate(dto.hireDate())
                .resignationDate(dto.resignationDate())
                .probationPeriod(dto.probationPeriod())
                .userRole(dto.userRole())
                .salary(dto.salary())
                .reportingManagerId(dto.reportingManagerId())
                .picture(document)
                .build();


        return userRepository.save(user);
    }

    public List<LeaveRequest> getPendingLeaveRequests() {
        return leaveRequestRepository.findAllPendingLeaveRequests()
                .orElseThrow(() -> new RuntimeException("Pending leave requests not found"));
    }

    public List<LeaveRequest> getApprovedLeaveRequests() {
        return leaveRequestRepository.findAllApprovedLeaveRequests()
                .orElseThrow(() -> new RuntimeException("Approved leave requests not found"));
    }

    public List<LeaveRequest> getRejectedLeaveRequests() {
        return leaveRequestRepository.findAllRejectedLeaveRequests()
                .orElseThrow(() -> new RuntimeException("Rejected leave requests not found"));
    }

    public LeaveRequest updateLeaveRequestStatus(UpdateLeaveRequestStatusDTO dto) {
        return leaveRequestRepository.findById(dto.leaveRequestId())
                .map(leaveRequest -> {
                    leaveRequest.setLeaveRequestStatus(dto.status());
                    return leaveRequestRepository.save(leaveRequest);
                })
                .orElseThrow(() -> new RuntimeException("Leave request not found"));
    }


    public List<LeaveRequest> getListLeaveRequests() {

        return leaveRequestRepository.findAllNonDeletedLeaveRequests();
    }

    public String getWorkedTime(Long userId, String date) {

        List<EnterOut> enterOuts = enterOutRepository.findEnterOutByUserIdAndDate(userId, date)
                .orElseThrow(() -> new RuntimeException("user Id or date is not correct"));

        Duration duration = null;
        long hours = 0;
        long minutes = 0;
        long seconds = 0;

        for (EnterOut obj : enterOuts) {
            duration = Duration.between(obj.getEnteredAt(), obj.getLeftAt());
            hours += duration.toHours();
            minutes += duration.toMinutesPart();
            seconds += duration.toSecondsPart();

            System.out.println(obj.getId() + "=> " + "hours: " + duration.toHours() + ", minutes: " + duration.toMinutesPart() + ", seconds: " + duration.toSecondsPart());
        }


        hours = hours + (minutes / 60);
        minutes = minutes + (seconds / 60);
        seconds = seconds % 60;

        System.out.println("==================================================================================");
        System.out.println("==================================================================================");
        System.out.println("            Worked tIme: " + hours + " hours " + minutes + " minutes " + seconds + " seconds");
        System.out.println("==================================================================================");
        System.out.println("==================================================================================");


        return "worked time in " + date.substring(0, 10) + " is " + hours + " hours " + minutes + " minutes " + seconds + " seconds";
    }


    public String getWorkedTimeWithInterval(Long userId, String startDate, String endDate) {

        List<EnterOut> enterOuts = enterOutRepository.findEnterOutByUserIdAndIntervalDate(userId, startDate, endDate)
                .orElseThrow(() -> new RuntimeException("user Id or date is not correct"));

        Duration duration = null;
        long hours = 0;
        long minutes = 0;
        long seconds = 0;

        for (EnterOut obj : enterOuts) {
            duration = Duration.between(obj.getEnteredAt(), obj.getLeftAt());
            hours += duration.toHours();
            minutes += duration.toMinutesPart();
            seconds += duration.toSecondsPart();
        }

        hours = hours + (minutes / 60);
        minutes = minutes + (seconds / 60);
        seconds = seconds % 60;

        System.out.println("*******************************************************************************");
        System.out.println("*******************************************************************************");
        System.out.println("            Worked tIme: " + hours + " hours " + minutes + " minutes " + seconds + " seconds");
        System.out.println("*******************************************************************************");
        System.out.println("*******************************************************************************");


        return "worked time between " + startDate.substring(0, 10) + " and " + endDate.substring(0, 10) + " is " + hours + " hours, " + minutes + " minutes, " + seconds + " seconds";
    }

    public Document updateProfilePicture(MultipartFile file) {
        if (sessionUser.id() == -1) {
            throw new RuntimeException("User not found");
        }
        if (file.isEmpty()) {
            throw new RuntimeException("File not found");
        }
        Document picture = documentService.saveDocument(file);
        userRepository.updateProfilePicture(picture, sessionUser.id());

        return picture;
    }

    public List<User> findAll() {
        return userRepository.findAllByDeletedFalseAndUserRole();
    }

    public Document updateProfilePicture(MultipartFile file, Long employeeId) {

        if (file.isEmpty()) {
            throw new RuntimeException("File not found");
        }
        Document picture = documentService.saveDocument(file);

        userRepository.findById(employeeId)
                .map(user -> {
                    user.setPicture(picture);
                    return userRepository.save(user);
                })
                .orElseThrow(() -> new RuntimeException("User not found"));

        return picture;
    }

    public User findByUserName(String username) {
        AuthUser authUser = authUserRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return userRepository.findById(authUser.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
