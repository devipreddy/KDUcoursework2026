package com.smarthome.application.mapper;

import com.smarthome.application.dto.HouseResponseDto;
import com.smarthome.application.entity.House;

public class HouseMapper {

    public static HouseResponseDto toDTO(House house) {

        return new HouseResponseDto(
                house.getHouseId(),
                house.getHouseName(),
                house.getHouseAddress(),
                house.getAdmin().getUserId()
        );
    }
}
