package com.smarthome.application.mapper;

import com.smarthome.application.dto.RoomResponseDto;
import com.smarthome.application.entity.Room;

public class RoomMapper {

    public static RoomResponseDto toDTO(Room room) {

        return new RoomResponseDto(
                room.getRoomId(),
                room.getRoomName(),
                room.getHouse().getHouseId()
        );
    }
}
