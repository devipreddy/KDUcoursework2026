package com.smarthome.application.mapper;

import com.smarthome.application.dto.UserResponseDto;
import com.smarthome.application.entity.SystemUser;

public class UserMapper {

    public static UserResponseDto toDTO(SystemUser user) {

        return new UserResponseDto(
                user.getUserId(),
                user.getUsername()
        );
    }
}
