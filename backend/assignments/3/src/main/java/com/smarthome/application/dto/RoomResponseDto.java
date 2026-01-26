package com.smarthome.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RoomResponseDto {
    Long roomId;
    String roomName;
    Long houseId;
}
