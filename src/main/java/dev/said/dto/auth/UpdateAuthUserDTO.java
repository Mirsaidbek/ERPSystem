package dev.said.dto.auth;

import dev.said.enums.Active;
import dev.said.enums.Language;
import dev.said.enums.Role;
import jakarta.validation.constraints.NotBlank;
import lombok.NonNull;


public record UpdateAuthUserDTO(
        @NonNull @NotBlank
        String username,
        Language language,
        Active active,
        Role role
) {
}
