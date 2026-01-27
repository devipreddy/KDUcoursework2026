package com.smarthome.application.service;

import com.smarthome.application.dto.*;
import com.smarthome.application.entity.*;
import com.smarthome.application.repository.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.smarthome.application.config.SecurityConstants;

import java.util.List;
import com.smarthome.application.exception.ResourceNotFoundException;
import com.smarthome.application.exception.BadRequestException;
import com.smarthome.application.exception.ConflictException;

@Service
public class DeviceService {
    private static final Logger log = LoggerFactory.getLogger(DeviceService.class);

    private final DeviceRepository deviceRepository;
    private final DeviceInventoryRepository inventoryRepository;
    private final HouseRepository houseRepository;
    private final RoomRepository roomRepository;
    private final UserHomeMembershipRepository membershipRepository;
    private final SystemUserRepository userRepository;

    public DeviceService(DeviceRepository deviceRepository, DeviceInventoryRepository inventoryRepository, HouseRepository houseRepository, RoomRepository roomRepository,
                         UserHomeMembershipRepository membershipRepository, SystemUserRepository userRepository) {

        this.deviceRepository = deviceRepository;
        this.inventoryRepository = inventoryRepository;
        this.houseRepository = houseRepository;
        this.roomRepository = roomRepository;
        this.membershipRepository = membershipRepository;
        this.userRepository = userRepository;
    }

    private SystemUser getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || authentication.getName() == null || authentication.getName().equals("anonymousUser")) {

            log.warn("No authentication found. Returning house admin user for tests.");
            return userRepository.findByUsername(SecurityConstants.DEFAULT_TEST_USER)
                    .orElseThrow(() -> new ResourceNotFoundException("Test user not found"));
        }


        return userRepository.findByUsername(authentication.getName()).orElseThrow(() -> {
                log.warn("Current user not found in repository: {}", authentication.getName());
                return new ResourceNotFoundException("User not found");
            });
    }

    private void validateAdmin(House house, SystemUser user) {
        if (!house.getAdmin().getUserId().equals(user.getUserId())) {
            log.warn("User {} attempted admin-only action on house {}", user.getUsername(), house.getHouseId());
            throw new BadRequestException("Only Admin can perform this action");
        }
    }

    private void validateMembership(House house, SystemUser user) {
        if (!membershipRepository.existsByHouseAndUser(house, user)) {
            log.warn("User {} is not member of house {}", user.getUsername(), house.getHouseId());
            throw new BadRequestException("User is not part of this house");
        }
    }

    public Device registerDeviceToHouse(Long houseId, AddDeviceRequestDto request) {
        log.info("Registering device to house {}: kickstonId={}", houseId, request.getKickstonId());

        House house = houseRepository.findById(houseId).orElseThrow(() -> new ResourceNotFoundException("House not found"));
        SystemUser user = getCurrentUser();
        validateAdmin(house, user);
        DeviceInventory inventoryDevice = inventoryRepository.findById(request.getKickstonId()).orElseThrow(() -> new ResourceNotFoundException("Device not found in inventory"));

        if (!inventoryDevice.getDeviceUsername().equals(request.getDeviceUsername()) || !inventoryDevice.getDevicePassword().equals(request.getDevicePassword())) {
            log.warn("Invalid device credentials for kickstonId={}", request.getKickstonId());
            throw new BadRequestException("Invalid device credentials");
        }

        if (deviceRepository.findByInventoryDevice(inventoryDevice).isPresent()) {
            log.warn("Attempt to register already-registered device kickstonId={}", request.getKickstonId());
            throw new ConflictException("Device already registered");
        }

        Device device = new Device();
        device.setHouse(house);
        device.setInventoryDevice(inventoryDevice);

        if (request.getRoomId() != null) {
            Room room = roomRepository.findById(request.getRoomId()).orElseThrow(() -> new ResourceNotFoundException("Room not found"));

            if (!room.getHouse().getHouseId().equals(houseId)) {
                log.warn("Room {} not in house {} when registering device", request.getRoomId(), houseId);
                throw new BadRequestException("Room not in same house");
            }

            device.setRoom(room);
        }

        Device saved = deviceRepository.save(device);
        log.info("Device {} registered into house {}", saved.getDeviceId(), houseId);
        return saved;
    }

    public Device moveDeviceToAnotherRoom(Long houseId, Long deviceId,MoveDeviceRequestDto request) {

        log.info("Moving device {} in house {} to newRoom {}", deviceId, houseId, request.getNewRoomId());

        House house = houseRepository.findById(houseId).orElseThrow(() -> new ResourceNotFoundException("House not found"));
        SystemUser user = getCurrentUser();
        validateMembership(house, user);

        Device device = deviceRepository.findById(deviceId).orElseThrow(() -> new ResourceNotFoundException("Device not found"));

        Room newRoom = roomRepository.findById(request.getNewRoomId()).orElseThrow(() -> new ResourceNotFoundException("Room not found"));

        if (!newRoom.getHouse().getHouseId().equals(houseId)) {
            throw new BadRequestException("Room not in same house");
        }

        device.setRoom(newRoom);
        Device saved = deviceRepository.save(device);
        log.info("Device {} moved to room {}", deviceId, request.getNewRoomId());
        return saved;
    }

    public List<RoomWithDevicesDto> getRoomsWithDevices(Long houseId) {
        log.debug("Fetching rooms with devices for house {}", houseId);

        House house = houseRepository.findById(houseId).orElseThrow(() -> new ResourceNotFoundException("House not found"));
        SystemUser user = getCurrentUser();
        validateMembership(house, user);

        return roomRepository.findByHouseHouseId(houseId).stream().map(room -> new RoomWithDevicesDto(
                        room.getRoomId(),room.getRoomName(),room.getDevicesInRoom().stream().map(device -> new DeviceResponseDto(
                                        device.getDeviceId(), device.getInventoryDevice().getKickstonId(),room.getRoomId())).toList()
                )).toList();
    }

    public void deleteDevice(Long houseId, Long deviceId) {
        log.info("Deleting device {} from house {}", deviceId, houseId);

        House house = houseRepository.findById(houseId).orElseThrow(() -> new ResourceNotFoundException("House not found"));
        Device device = deviceRepository.findById(deviceId).orElseThrow(() -> new ResourceNotFoundException("Device not found"));
        SystemUser user = getCurrentUser();
        validateAdmin(house, user);

        if (!device.getHouse().getHouseId().equals(houseId)) {
            throw new BadRequestException("Device does not belong to this house");
        }

        deviceRepository.delete(device);
        log.info("Device {} soft-deleted", deviceId);
    }

    public List<Device> getDeletedDevices(Long houseId) {
        log.debug("Retrieving deleted devices for house {}", houseId);
        return deviceRepository.findAllDeletedDevicesByHouseId(houseId);
    }
}

