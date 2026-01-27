package com.smarthome.application.controller;

import com.smarthome.application.dto.HouseResponseDto;
import com.smarthome.application.dto.RoomResponseDto;
import com.smarthome.application.mapper.HouseMapper;
import com.smarthome.application.mapper.RoomMapper;
import com.smarthome.application.service.UserService;
import org.springframework.http.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.validation.annotation.Validated;


import java.util.List;

@RestController
@Validated
@Tag(name = "User", description = "User-facing endpoints")
public class UserController {

    private final UserService userService;
    private static final Logger log = LoggerFactory.getLogger(UserController.class);

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping(ApiEndpoints.USER_HOUSES)
    @Operation(summary = "Get houses for current authenticated user")
    public ResponseEntity<List<HouseResponseDto>> getHousesOfUser() {
        log.debug("GET /user/houses - fetch houses of current user");
        List<HouseResponseDto> houses = userService.getHousesOfUser().stream().map(HouseMapper::toDTO).toList();
        return ResponseEntity.ok(houses);
    }

    @GetMapping(ApiEndpoints.USER_ROOMS)
    @Operation(summary = "Get rooms in a house")
    public ResponseEntity<List<RoomResponseDto>> getRooms(@PathVariable @jakarta.validation.constraints.Positive(message = "houseId must be positive") Long houseId) {
        log.debug("GET /{}/rooms - fetch rooms in house", houseId);
        List<RoomResponseDto> rooms = userService.getAllRoomsInHouse(houseId).stream().map(RoomMapper::toDTO).toList();
        return ResponseEntity.ok(rooms);
    }

    @GetMapping(ApiEndpoints.USER_DELETED_ROOMS)
    @Operation(summary = "List soft-deleted rooms in a house")
    public ResponseEntity<List<RoomResponseDto>> getDeletedRooms(@PathVariable @jakarta.validation.constraints.Positive(message = "houseId must be positive") Long houseId) {
        log.debug("GET /{}/deleted-rooms - fetch deleted rooms in house", houseId);
        return ResponseEntity.ok(userService.getDeletedRooms(houseId).stream().map(RoomMapper::toDTO).toList());
    }

    @DeleteMapping(ApiEndpoints.USER_ACCOUNT)
    @Operation(summary = "Deactivate current user account")
    public ResponseEntity<String> deleteUserAccount() {
        log.info("DELETE /account - deactivate user account");
        userService.deleteUserAccount();
        return ResponseEntity.ok("Your account has been deactivated successfully.");
    }

}
