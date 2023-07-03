package dev.said.dto.leaverequest;

import dev.said.enums.leaverequest.LeaveRequestType;
import lombok.NonNull;

public record CreateLeaveRequestDTO(

        @NonNull
        Long employeeId,

        @NonNull
        LeaveRequestType leaveRequestType,

        @NonNull
        String startDate,

        @NonNull
        String endDate

) {
}
