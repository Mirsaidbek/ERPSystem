package dev.said.dto.token;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import org.springdoc.core.annotations.ParameterObject;

@ParameterObject
public record TokenRequest(


        @NotBlank String username,

        @NotBlank String password) {
}
