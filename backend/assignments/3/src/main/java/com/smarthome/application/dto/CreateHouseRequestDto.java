package com.smarthome.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor
public class CreateHouseRequestDto {
    @NotBlank(message = "houseName is required")
    @Size(max = 100)
    public String houseName;

    @NotBlank(message = "houseAddress is required")
    @Size(max = 255)
    public String houseAddress;

}
