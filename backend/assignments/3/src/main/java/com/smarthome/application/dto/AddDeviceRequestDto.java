package com.smarthome.application.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import io.swagger.v3.oas.annotations.media.Schema;

@Getter
@Setter
@Schema(description = "Request payload for adding/registering a device to a house")
public class AddDeviceRequestDto {

    @NotBlank(message = "kickstonId is required")
    @Size(max = 100)
    private String kickstonId;

    @NotBlank(message = "deviceUsername is required")
    @Size(max = 100)
    private String deviceUsername;

    @NotBlank(message = "devicePassword is required")
    @Size(min = 6, max = 100)
    private String devicePassword;

    @Schema(description = "Optional room id where the device will be placed", requiredMode = Schema.RequiredMode.AUTO)
    private Long roomId;
}
