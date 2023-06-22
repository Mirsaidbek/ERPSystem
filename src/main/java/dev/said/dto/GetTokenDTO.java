package dev.said.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
//@AllArgsConstructor
//@NoArgsConstructor
@Builder
public class GetTokenDTO {
    private String token;

    public GetTokenDTO() {
    }

    public GetTokenDTO(String token) {
        this.token = token;
    }
}
