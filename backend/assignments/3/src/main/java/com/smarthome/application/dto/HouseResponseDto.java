package com.smarthome.application.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@AllArgsConstructor
@Schema(description = "House information returned to clients")
public class HouseResponseDto {
    @Schema(description = "House database id", example = "1")
    Long houseId;
    @Schema(description = "Human-readable house name", example = "Primary Residence")
    String houseName;
    @Schema(description = "Street address of the house", example = "123 Main St, Springfield")
    String houseAddress;
    @Schema(description = "User id of the house admin", example = "42")
    Long adminId;
}
