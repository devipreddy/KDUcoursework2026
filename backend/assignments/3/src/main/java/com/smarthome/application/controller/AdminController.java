package com.smarthome.application.controller;

import com.smarthome.application.dto.*;
import com.smarthome.application.entity.House;
import com.smarthome.application.entity.Room;
import com.smarthome.application.mapper.HouseMapper;
import com.smarthome.application.mapper.RoomMapper;
import com.smarthome.application.mapper.UserMapper;
import com.smarthome.application.service.AdminService;
import org.springframework.http.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;
import jakarta.validation.constraints.Positive;

import java.net.URI;
import java.util.List;

@RestController
@Tag(name = "Admin", description = "Admin operations for houses, rooms and memberships")
@Validated
public class AdminController {

    private final AdminService adminService;
    private static final Logger log = LoggerFactory.getLogger(AdminController.class);
    public AdminController(AdminService adminService){
        this.adminService = adminService;
    }

    @PostMapping(ApiEndpoints.HOUSES)
    @Operation(summary = "Create a new house")
    public ResponseEntity<HouseResponseDto> createHouse(@Valid @RequestBody CreateHouseRequestDto request) {
        log.info("POST /houses - create house {}", request.getHouseName());
        House house = adminService.createHouse(request);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(house.getHouseId()).toUri();

        return ResponseEntity.created(location).body(HouseMapper.toDTO(house));
    }


    @GetMapping(ApiEndpoints.HOUSES_ALL)
    @Operation(summary = "List all houses")
    public ResponseEntity<List<House>> getAllHouses(){
        log.debug("GET /all - fetch all houses");
        return ResponseEntity.ok(adminService.getAllHouses());
    }


    @PostMapping(ApiEndpoints.HOUSES_ADD_USER)
    @Operation(summary = "Add a user to a house")
    public ResponseEntity<MembershipResponseDto> addUsertoHouse(
            @PathVariable @Positive(message = "houseId must be positive") Long houseId,
            @PathVariable @Positive(message = "userId must be positive") Long userId) {
        log.info("POST /houses/{}/add/user/{} - add user to house", houseId, userId);
        adminService.addUserToHouse(houseId, userId);
        MembershipResponseDto response = new MembershipResponseDto(houseId, userId, "User added successfully to the house");
        return ResponseEntity.ok(response);
    }


    @GetMapping(ApiEndpoints.HOUSES_USERS)
    @Operation(summary = "List all users")
    public ResponseEntity<List<UserResponseDto>> getAllUsers() {
        log.debug("GET /users - fetch users");
        List<UserResponseDto> users = adminService.getAllUsers().stream().map(UserMapper::toDTO).toList();
        return ResponseEntity.ok(users);
    }



    @PutMapping(ApiEndpoints.HOUSES_ADDRESS)
    @Operation(summary = "Update house address")
    public ResponseEntity<String> updateHouseAddress(@PathVariable @jakarta.validation.constraints.Positive(message = "houseId must be positive") Long houseId, @Valid @RequestBody AddressRequestDto addressRequestDto){
        log.info("PUT /houses/{}/address - update address", houseId);
        adminService.updateHouseAddress(houseId, addressRequestDto);
        return ResponseEntity.ok("Address Updated Successfully");
    }


    @PutMapping(ApiEndpoints.HOUSES_TRANSFER)
    @Operation(summary = "Transfer house ownership to another user")
    public ResponseEntity<String> updateOwnerShip(@PathVariable @jakarta.validation.constraints.Positive(message = "houseId must be positive") Long houseId, @PathVariable @jakarta.validation.constraints.Positive(message = "userId must be positive") Long userId){
        log.info("PUT /houses/{}/transfer/{} - transfer ownership", houseId, userId);
        adminService.updateOwnerShip(houseId,userId);
        return ResponseEntity.ok("Ownership Transferred Successfully");
    }


    @PostMapping(ApiEndpoints.HOUSES_ROOMS)
    @Operation(summary = "Create a room in a house")
    public ResponseEntity<RoomResponseDto> createRoom(@PathVariable @jakarta.validation.constraints.Positive(message = "houseId must be positive") Long houseId, @Valid @RequestBody RoomRequestDto dto) {
        log.info("POST /houses/{}/rooms - create room {}", houseId, dto.getRoomName());
        Room room = adminService.createRooms(houseId, dto);
        return ResponseEntity.ok(RoomMapper.toDTO(room));
    }

    @DeleteMapping(ApiEndpoints.HOUSES_DELETE)
    @Operation(summary = "Delete a house (soft delete)")
    public ResponseEntity<String> deleteHouse(@PathVariable @jakarta.validation.constraints.Positive(message = "houseId must be positive") Long houseId) {
        log.info("DELETE /houses/{} - delete house", houseId);
        adminService.deleteHouse(houseId);
        return ResponseEntity.ok("House deleted successfully. All associated rooms and devices have been removed.");
    }

    @DeleteMapping(ApiEndpoints.HOUSES_DELETE_ROOM)
    @Operation(summary = "Delete a room from a house (soft delete)")
    public ResponseEntity<String> deleteRoom(@PathVariable @jakarta.validation.constraints.Positive(message = "houseId must be positive") Long houseId, @PathVariable @jakarta.validation.constraints.Positive(message = "roomId must be positive") Long roomId) {
        log.info("DELETE /houses/{}/rooms/{} - delete room", houseId, roomId);
        adminService.deleteRoom(houseId, roomId);
        return ResponseEntity.ok("Room deleted successfully. All devices in this room have been removed.");
    }

    @DeleteMapping(ApiEndpoints.HOUSES_DELETE_MEMBER)
    @Operation(summary = "Remove a user membership from a house")
    public ResponseEntity<String> removeUserFromHouse(@PathVariable @jakarta.validation.constraints.Positive(message = "houseId must be positive") Long houseId, @PathVariable @jakarta.validation.constraints.Positive(message = "memberId must be positive") Long memberId) {
        log.info("DELETE /houses/{}/members/{} - remove user from house", houseId, memberId);
        adminService.removeUserFromHouse(houseId, memberId);
        return ResponseEntity.ok("User has been removed from the house successfully.");
    }

    @GetMapping(ApiEndpoints.WHOAMI)
    @Operation(summary = "Get current authenticated user info")
    public String whoami() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        return "User = " + auth.getName() +
                " | Authenticated = " + auth.isAuthenticated() +
                " | Authorities = " + auth.getAuthorities();
    }

    @GetMapping(ApiEndpoints.DELETED_HOUSES)
    @Operation(summary = "List soft-deleted houses")
    public ResponseEntity<List<HouseResponseDto>> getDeletedHouses() {
        log.debug("GET /deleted-houses - list deleted houses");
        return ResponseEntity.ok(adminService.getDeletedHouses().stream().map(HouseMapper::toDTO).toList());
    }


}
