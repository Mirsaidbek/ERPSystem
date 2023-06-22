package dev.said.dto.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.NonNull;


public record CreateAuthUserDTO(
        @NonNull @NotBlank
        String username,

        @NonNull @NotBlank
        String password
) {
}
