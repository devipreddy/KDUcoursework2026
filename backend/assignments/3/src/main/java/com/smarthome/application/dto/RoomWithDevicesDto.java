package com.smarthome.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@AllArgsConstructor
@Schema(description = "Room with its devices")
public class RoomWithDevicesDto {

    @Schema(description = "Room id", example = "10")
    private Long roomId;
    @Schema(description = "Room name", example = "Living Room")
    private String roomName;
    @Schema(description = "List of devices in the room")
    private List<DeviceResponseDto> devices;
}
