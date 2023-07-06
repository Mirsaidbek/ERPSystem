package dev.said.domains;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import dev.said.enums.leaverequest.LeaveRequestStatus;
import dev.said.enums.leaverequest.LeaveRequestType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})

public class LeaveRequest extends Auditable<Long> {
    @Column(nullable = false)
    private Long employeeId;
    @Enumerated(EnumType.STRING)
    private LeaveRequestType leaveRequestType;
    @Column(nullable = false)
    private String startDate;
    @Column(nullable = false)
    private String endDate;
    @Enumerated(EnumType.STRING)
    private LeaveRequestStatus leaveRequestStatus;

    @Builder(builderMethodName = "childBuilder")
    public LeaveRequest(Long id, LocalDateTime createdAt, LocalDateTime updatedAt, Long createdBy, Long updatedBy, boolean deleted, Long employeeId, String startDate, String endDate, LeaveRequestType leaveRequestType, LeaveRequestStatus leaveRequestStatus) {
        super(id, createdAt, updatedAt, createdBy, updatedBy, deleted);
        this.employeeId = employeeId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.leaveRequestType = leaveRequestType;
        this.leaveRequestStatus = leaveRequestStatus;
    }
}
