package com.smarthome.application.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AssignDeviceRoomRequestDto {

    @NotNull(message = "roomId is required")
    private Long roomId;
}
