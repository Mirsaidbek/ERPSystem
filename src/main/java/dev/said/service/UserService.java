package dev.said.service;

import dev.said.domains.AuthUser;
import dev.said.domains.EnterOut;
import dev.said.domains.LeaveRequest;
import dev.said.domains.User;
import dev.said.dto.leaverequest.UpdateLeaveRequestStatusDTO;
import dev.said.dto.user.CreateUserDTO;
import dev.said.enums.Active;
import dev.said.enums.Language;
import dev.said.enums.Role;
import dev.said.repository.AuthUserRepository;
import dev.said.repository.EnterOutRepository;
import dev.said.repository.LeaveRequestRepository;
import dev.said.repository.UserRepository;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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

    public User createEmployee(@NonNull CreateUserDTO dto) {

        AuthUser authUser = AuthUser.childBuilder()
                .username(dto.email())
                .password(passwordEncoder.encode(dto.firstName().concat("1234")))
                .language(Language.UZBEK)
                .active(Active.ACTIVE)
                .role(Role.EMPLOYEE)
                .build();
        AuthUser save = authUserRepository.save(authUser);

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
                .role(dto.role())
                .salary(dto.salary())
                .reportingManagerId(dto.reportingManagerId())
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

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public List<LeaveRequest> getListLeaveRequests() {

        return leaveRequestRepository.findAll();
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
}
