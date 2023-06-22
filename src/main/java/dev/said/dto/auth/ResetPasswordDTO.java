package dev.said.dto.auth;

import lombok.NonNull;

public record ResetPasswordDTO(
        @NonNull
        String username,
        @NonNull
        String oldPassword,
        @NonNull
        String newPassword,
        @NonNull
        String confirmPassword
) {
}
