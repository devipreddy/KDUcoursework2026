package com.smarthome.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@AllArgsConstructor
@Schema(description = "Device summary returned by endpoints")
public class DeviceResponseDto {

    @Schema(description = "Device database id", example = "100")
    private Long deviceId;
    @Schema(description = "Inventory kickston id", example = "000010")
    private String kickstonId;
    @Schema(description = "Associated room id (nullable)", example = "10")
    private Long roomId;
}
