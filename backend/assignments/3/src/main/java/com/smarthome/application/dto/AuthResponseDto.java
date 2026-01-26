package com.smarthome.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@AllArgsConstructor
@Schema(description = "Authentication response containing JWT token")
public class AuthResponseDto {

    @Schema(description = "JWT token", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
    private String token;
}

