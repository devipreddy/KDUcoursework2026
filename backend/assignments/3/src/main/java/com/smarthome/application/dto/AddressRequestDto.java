package com.smarthome.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressRequestDto {
    @NotBlank(message = "address is required")
    @Size(max = 255)
    private String address;
}
