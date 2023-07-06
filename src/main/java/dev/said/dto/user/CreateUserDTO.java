package dev.said.dto.user;

import dev.said.enums.EmploymentModel;
import dev.said.enums.Gender;
import dev.said.enums.MartialStatus;
import dev.said.enums.Role;
import jakarta.validation.constraints.Pattern;
import lombok.NonNull;
import org.springdoc.core.annotations.ParameterObject;

@ParameterObject
public record CreateUserDTO(
        @NonNull String firstName,
        @NonNull String lastName,
        @NonNull String dateOfBirth,
        @NonNull Gender gender,
        @NonNull MartialStatus martialStatus,
        @NonNull
        @Pattern(regexp = "^\\+998\\d{2}\\d{7}$")
        String phoneNumber,
        @NonNull
        @Pattern(regexp = "^([a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6})*$")
        String email,
        @NonNull EmploymentModel employmentModel,
        @NonNull String hireDate,
        @NonNull String resignationDate,
        @NonNull String probationPeriod,
        @NonNull Role role,
        @NonNull Long salary,
        Long reportingManagerId
) {
}
