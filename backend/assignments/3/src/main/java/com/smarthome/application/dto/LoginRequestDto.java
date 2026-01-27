package com.smarthome.application.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@Schema(description = "Login credentials")
public class LoginRequestDto {
    @Schema(description = "Username of the user", example = "user")
    @NotBlank(message = "username is required")
    private String username;

    @Schema(description = "Password of the user", example = "secret")
    @NotBlank(message = "password is required")
    private String password;
}

