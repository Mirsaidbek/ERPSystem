package dev.said.dto.user;

import dev.said.enums.EmploymentModel;
import dev.said.enums.Gender;
import dev.said.enums.MartialStatus;
import dev.said.enums.Position;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import lombok.NonNull;
import org.springdoc.core.annotations.ParameterObject;

import java.time.LocalDate;

@ParameterObject
public record CreateUserDTO(
        @NonNull String firstName,
        @NonNull String lastName,
        
        @NonNull 
        @Past(message = "Date of birth must be in the past")
        @Schema(description = "Date of birth", type = "string", format = "date", example = "1990-01-01")
        LocalDate dateOfBirth,
        
        @NonNull Gender gender,
        @NonNull MartialStatus martialStatus,
        
        @NonNull
        @Pattern(regexp = "^\\+998\\d{2}\\d{7}$")
        String phoneNumber,
        
        @NonNull
        @Pattern(regexp = "^([a-zA-Z0-9._%-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6})*$")
        String email,
        
        @NonNull EmploymentModel employmentModel,
        
        @NonNull
        @Schema(description = "Hire date", type = "string", format = "date", example = "2023-01-01")
        LocalDate hireDate,
        
        @Schema(description = "Resignation date (if applicable)", type = "string", format = "date", example = "2024-01-01")
        LocalDate resignationDate,
        
        @NonNull String probationPeriod,
        @NonNull Position position,
        @NonNull Long salary,
        Long reportingManagerId
) {
}
