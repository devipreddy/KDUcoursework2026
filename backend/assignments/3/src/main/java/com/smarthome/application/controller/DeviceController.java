package com.smarthome.application.controller;

import com.smarthome.application.dto.*;
import com.smarthome.application.entity.Device;
import com.smarthome.application.mapper.DeviceMapper;
import com.smarthome.application.service.DeviceService;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.constraints.Positive;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@Validated
@Tag(name = "Device", description = "Device management endpoints")
public class DeviceController {

    private final DeviceService deviceService;
    private static final Logger log = LoggerFactory.getLogger(DeviceController.class);

    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @PostMapping(ApiEndpoints.DEVICES_REGISTER)
    @Operation(summary = "Register a device to a house")
            public ResponseEntity<DeviceResponseDto> registerDevice(
                @PathVariable @Positive(message = "houseId must be positive") Long houseId,
                @Valid @RequestBody AddDeviceRequestDto request) {

                log.info("POST /{}/devices/register - register device for house {}", houseId, houseId);
                Device device = deviceService.registerDeviceToHouse(houseId, request);
                return ResponseEntity.ok(DeviceMapper.toDTO(device));
    }

    @PutMapping(ApiEndpoints.DEVICES_MOVE)
    @Operation(summary = "Move a device to another room")
            public ResponseEntity<DeviceResponseDto> moveDevice(
                @PathVariable @Positive(message = "houseId must be positive") Long houseId,
                @PathVariable @Positive(message = "deviceId must be positive") Long deviceId,
                @Valid @RequestBody MoveDeviceRequestDto request) {

                log.info("PUT /{}/devices/{}/move-room - move device", houseId, deviceId);
                Device device = deviceService.moveDeviceToAnotherRoom(houseId, deviceId, request);
                return ResponseEntity.ok(DeviceMapper.toDTO(device));
    }

    @GetMapping(ApiEndpoints.DEVICES_ROOMS_WITH)
    @Operation(summary = "Get rooms with devices for a house")
    public ResponseEntity<List<RoomWithDevicesDto>> roomsWithDevices(
            @PathVariable Long houseId) {
        log.debug("GET /{}/rooms-with-devices", houseId);
        return ResponseEntity.ok(deviceService.getRoomsWithDevices(houseId));
    }

    @DeleteMapping(ApiEndpoints.DEVICES_DELETE)
    @Operation(summary = "Delete (soft) a device from a house")
        public ResponseEntity<String> deleteDevice(
            @PathVariable @Positive(message = "houseId must be positive") Long houseId,
            @PathVariable @Positive(message = "deviceId must be positive") Long deviceId) {

        log.info("DELETE /{}/devices/{} - delete device", houseId, deviceId);
        deviceService.deleteDevice(houseId, deviceId);
        return ResponseEntity.ok("Device has been successfully unregistered and removed from the house.");
    }

    @GetMapping(ApiEndpoints.DEVICES_DELETED)
    @Operation(summary = "List soft-deleted devices for a house")
    public ResponseEntity<List<DeviceResponseDto>> getDeletedDevices(@PathVariable @Positive(message = "houseId must be positive") Long houseId) {
        log.debug("GET /{}/deleted-devices - list deleted devices", houseId);
        return ResponseEntity.ok(deviceService.getDeletedDevices(houseId).stream().map(DeviceMapper::toDTO).toList());
    }
}
