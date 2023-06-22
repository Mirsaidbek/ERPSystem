package dev.said.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.NonNull;

public record CreateAuthUserDTO(
        @NonNull @NotBlank
        String username,

        @NonNull @NotBlank
        String password
) {
}
