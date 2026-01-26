package com.smarthome.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RoomRequestDto {
    @NotBlank(message = "roomName is required")
    @Size(max = 100)
    public String roomName;
}
