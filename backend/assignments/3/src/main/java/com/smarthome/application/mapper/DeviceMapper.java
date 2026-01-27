package com.smarthome.application.mapper;

import com.smarthome.application.dto.DeviceResponseDto;
import com.smarthome.application.entity.Device;

public class DeviceMapper {

    public static DeviceResponseDto toDTO(Device device) {

        return new DeviceResponseDto(
                device.getDeviceId(),
                device.getInventoryDevice() != null ? device.getInventoryDevice().getKickstonId() : null,
                device.getRoom() != null ? device.getRoom().getRoomId() : null
        );
    }
}
