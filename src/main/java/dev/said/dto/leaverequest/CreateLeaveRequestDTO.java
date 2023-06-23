package dev.said.dto.leaverequest;

import dev.said.enums.leaverequest.LeaveRequestStatus;
import dev.said.enums.leaverequest.LeaveRequestType;
import lombok.NonNull;

public record CreateLeaveRequestDTO(
        @NonNull
        LeaveRequestType leaveRequestType,

        @NonNull
        String startDate,

        @NonNull
        String endDate,

        @NonNull
        LeaveRequestStatus leaveRequestStatus
) {
}
