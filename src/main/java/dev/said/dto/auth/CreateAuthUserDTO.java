package dev.said.dto.auth;

import dev.said.enums.Active;
import dev.said.enums.Language;
import dev.said.enums.Role;
import jakarta.validation.constraints.NotBlank;
import lombok.NonNull;


public record CreateAuthUserDTO(
        @NonNull @NotBlank
        String username,
        @NonNull @NotBlank
        String password,
        @NonNull @NotBlank
        String confirmPassword,
        @NonNull
        Language language,
        @NonNull
        Active active,
        @NonNull
        Role role
) {
}
