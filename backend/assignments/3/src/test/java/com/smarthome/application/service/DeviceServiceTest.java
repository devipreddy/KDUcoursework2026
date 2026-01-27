package com.smarthome.application.service;

import com.smarthome.application.dto.MoveDeviceRequestDto;
import com.smarthome.application.entity.*;
import com.smarthome.application.exception.BadRequestException;
import com.smarthome.application.exception.ResourceNotFoundException;
import com.smarthome.application.repository.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class DeviceServiceTest {

    @Mock private DeviceRepository deviceRepository;
    @Mock private DeviceInventoryRepository inventoryRepository;
    @Mock private HouseRepository houseRepository;
    @Mock private RoomRepository roomRepository;
    @Mock private UserHomeMembershipRepository membershipRepository;
    @Mock private SystemUserRepository userRepository;

    @Mock private SecurityContext securityContext;
    @Mock private Authentication authentication;

    @InjectMocks
    private DeviceService deviceService;

    private void mockSecurityContext(String username) {
        when(securityContext.getAuthentication()).thenReturn(authentication);
        when(authentication.getName()).thenReturn(username);
        SecurityContextHolder.setContext(securityContext);
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    @Test
    void moveDeviceToAnotherRoom_Success() {
        Long houseId = 1L;
        Long deviceId = 100L;
        Long newRoomId = 2L;
        String username = "testUser";

        MoveDeviceRequestDto request = new MoveDeviceRequestDto();
        request.setNewRoomId(newRoomId);

        House house = new House();
        house.setHouseId(houseId);

        SystemUser user = new SystemUser();
        user.setUsername(username);
        user.setUserId(50L);

        Device device = new Device();
        device.setDeviceId(deviceId);
        device.setHouse(house); 

        Room newRoom = new Room();
        newRoom.setRoomId(newRoomId);
        newRoom.setHouse(house); 

        // Mocking behavior
        mockSecurityContext(username);
        when(houseRepository.findById(houseId)).thenReturn(Optional.of(house));
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(membershipRepository.existsByHouseAndUser(house, user)).thenReturn(true);
        when(deviceRepository.findById(deviceId)).thenReturn(Optional.of(device));
        when(roomRepository.findById(newRoomId)).thenReturn(Optional.of(newRoom));
        when(deviceRepository.save(any(Device.class))).thenAnswer(i -> i.getArguments()[0]);

        // 2. Act
        Device result = deviceService.moveDeviceToAnotherRoom(houseId, deviceId, request);

        // 3. Assert
        assertNotNull(result);
        assertEquals(newRoomId, result.getRoom().getRoomId());
        verify(deviceRepository).save(device);
    }

    @Test
    void moveDeviceToAnotherRoom_ThrowsIfRoomNotInSameHouse() {
        // 1. Arrange
        Long houseId = 1L;
        Long otherHouseId = 99L;
        Long newRoomId = 2L;
        String username = "testUser";

        MoveDeviceRequestDto request = new MoveDeviceRequestDto();
        request.setNewRoomId(newRoomId);

        House house = new House();
        house.setHouseId(houseId);

        House otherHouse = new House();
        otherHouse.setHouseId(otherHouseId);

        SystemUser user = new SystemUser();
        
        Room newRoom = new Room();
        newRoom.setRoomId(newRoomId);
        newRoom.setHouse(otherHouse); 

        mockSecurityContext(username);
        when(houseRepository.findById(houseId)).thenReturn(Optional.of(house));
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(membershipRepository.existsByHouseAndUser(house, user)).thenReturn(true);
        when(deviceRepository.findById(any())).thenReturn(Optional.of(new Device()));
        when(roomRepository.findById(newRoomId)).thenReturn(Optional.of(newRoom));

        assertThrows(BadRequestException.class, () -> 
            deviceService.moveDeviceToAnotherRoom(houseId, 100L, request)
        );
        
        verify(deviceRepository, never()).save(any());
    }
    
    @Test
    void moveDeviceToAnotherRoom_ThrowsIfUserNotMember() {

        Long houseId = 1L;
        String username = "intruder";
        
        House house = new House();
        house.setHouseId(houseId);
        SystemUser user = new SystemUser();

        mockSecurityContext(username);
        when(houseRepository.findById(houseId)).thenReturn(Optional.of(house));
        when(userRepository.findByUsername(username)).thenReturn(Optional.of(user));

        when(membershipRepository.existsByHouseAndUser(house, user)).thenReturn(false);

        assertThrows(BadRequestException.class, () -> 
            deviceService.moveDeviceToAnotherRoom(houseId, 100L, new MoveDeviceRequestDto())
        );
    }
}