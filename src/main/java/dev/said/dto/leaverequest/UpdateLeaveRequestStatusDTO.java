package dev.said.dto.leaverequest;


import dev.said.enums.leaverequest.LeaveRequestStatus;
import lombok.NonNull;
import org.springdoc.core.annotations.ParameterObject;

@ParameterObject
public record UpdateLeaveRequestStatusDTO(
        @NonNull Long leaveRequestId,
        @NonNull LeaveRequestStatus status
) {
}
