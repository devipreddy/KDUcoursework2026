package com.smarthome.application.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class MoveDeviceRequestDto {

    @NotNull(message = "newRoomId is required")
    private Long newRoomId;
}
