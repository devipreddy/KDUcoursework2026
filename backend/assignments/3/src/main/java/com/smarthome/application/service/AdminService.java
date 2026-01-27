package com.smarthome.application.service;

import com.smarthome.application.dto.AddressRequestDto;
import com.smarthome.application.dto.CreateHouseRequestDto;
import com.smarthome.application.dto.RoomRequestDto;
import com.smarthome.application.entity.House;
import com.smarthome.application.entity.Room;
import com.smarthome.application.entity.Device;
import com.smarthome.application.repository.DeviceRepository;
import com.smarthome.application.entity.SystemUser;
import com.smarthome.application.entity.UserHomeMembership;
import com.smarthome.application.repository.HouseRepository;
import com.smarthome.application.repository.RoomRepository;
import com.smarthome.application.repository.SystemUserRepository;
import com.smarthome.application.repository.UserHomeMembershipRepository;
import org.springframework.dao.DataIntegrityViolationException;
import com.smarthome.application.exception.ResourceNotFoundException;
import com.smarthome.application.exception.BadRequestException;
import com.smarthome.application.exception.ConflictException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service 
public class AdminService {

    private static final Logger log = LoggerFactory.getLogger(AdminService.class);

    private final HouseRepository houseRepository;
    private final SystemUserRepository systemUserRepository;
    private final UserHomeMembershipRepository membershipRepository;
    private final RoomRepository roomRepository;
    private final DeviceRepository deviceRepository;


    public AdminService(HouseRepository houseRepository, SystemUserRepository systemUserRepository, UserHomeMembershipRepository membershipRepository, 
                        RoomRepository roomRepository, DeviceRepository deviceRepository){
        this.houseRepository = houseRepository;
        this.systemUserRepository = systemUserRepository;
        this.membershipRepository = membershipRepository;
        this.roomRepository = roomRepository;
        this.deviceRepository = deviceRepository;
    }

    public House createHouse(CreateHouseRequestDto houseRequest){
        log.info("Creating house: {}", houseRequest.getHouseName());
        SystemUser admin = getUserInContext();
        House house = new House();
        house.setAdmin(admin);
        house.setHouseName(houseRequest.getHouseName());
        house.setHouseAddress(houseRequest.getHouseAddress());

        houseRepository.save(house);
        UserHomeMembership membership = new UserHomeMembership();
        membership.setHouse(house);
        membership.setUser(admin);

        house.getMembersOfHouse().add(membership);
        membershipRepository.save(membership);
        return house;
    }

    public List<House> getAllHouses(){
        log.debug("Fetching all houses");
        return houseRepository.findAll();
    }

    public void addUserToHouse(Long houseId, Long userId){
        log.info("Adding user {} to house {}", userId, houseId);
        House house = houseRepository.findById(houseId).orElseThrow(() -> new ResourceNotFoundException("House Not Found"));
        SystemUser userToBeAdded = systemUserRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
        SystemUser admin = getUserInContext();

        if(admin.equals(userToBeAdded)){
            throw new BadRequestException("User is already Admin");
        }

        if(!admin.equals(house.getAdmin())){
            throw new BadRequestException("Only Admin can add User to Home");
        }

        UserHomeMembership membership = new UserHomeMembership();
        membership.setHouse(house);
        membership.setUser(userToBeAdded);
        house.getMembersOfHouse().add(membership);

        try {
            membershipRepository.save(membership);
        } catch (DataIntegrityViolationException e) {
            throw new ConflictException("User is already a member of this house");
        }

    }

    public List<SystemUser> getAllUsers(){
        log.debug("Fetching all users");
        return systemUserRepository.findAll();
    }

    public void updateHouseAddress(Long houseId, AddressRequestDto addressRequestDto){
        log.info("Updating address for house {}", houseId);
        House house = houseRepository.findById(houseId).orElseThrow(() -> new ResourceNotFoundException("House Not Found"));
        SystemUser admin = getUserInContext();
        if(!admin.equals(house.getAdmin())){
            throw new BadRequestException("Only Admin can Update the Address");
        }
        house.setHouseAddress(addressRequestDto.getAddress());
        houseRepository.save(house);

    }

    public void updateOwnerShip(Long houseId, Long userId){
        log.info("Transferring ownership of house {} to user {}", houseId, userId);
        House house = houseRepository.findById(houseId).orElseThrow(() -> new ResourceNotFoundException("House not Found"));
        SystemUser user = systemUserRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User not Found"));
        SystemUser admin = getUserInContext();

        if (!admin.equals(house.getAdmin()))
            throw new BadRequestException("Only Admin can transfer ownership");

        if (!membershipRepository.existsByHouseAndUser(house, user))
            throw new BadRequestException("New owner must be a member of the house");

        house.setAdmin(user);
        houseRepository.save(house);
    }

    public Room createRooms(Long houseId, RoomRequestDto roomRequestDto){
        log.info("Creating room {} in house {}", roomRequestDto.getRoomName(), houseId);
        House house = houseRepository.findById(houseId).orElseThrow(() -> new ResourceNotFoundException("House not Found"));
        SystemUser admin = getUserInContext();

        if(!admin.equals(house.getAdmin())){
            throw new BadRequestException("Only User can Update Ownership");
        }

        Room room = new Room();
        room.setHouse(house);
        room.setRoomName(roomRequestDto.getRoomName());
        Room savedRoom = roomRepository.save(room);
        house.getRoomsofHouse().add(savedRoom);
        return savedRoom;
    }

    public SystemUser getUserInContext(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String adminUsername = authentication.getName();
        return systemUserRepository.findByUsername(adminUsername).orElseThrow(() -> new ResourceNotFoundException("User not Found"));
    }

    public void deleteHouse(Long houseId) {
        log.info("Deleting house {}", houseId);
        House house = houseRepository.findById(houseId).orElseThrow(() -> new ResourceNotFoundException("House not found"));
        SystemUser admin = getUserInContext();

        if (!admin.equals(house.getAdmin())) {
            throw new BadRequestException("Only the house admin can delete the house");
        }

        houseRepository.delete(house);
    }

    @Transactional
    public void deleteRoom(Long houseId, Long roomId) {
        log.info("Deleting room {} from house {}", roomId, houseId);
        House house = houseRepository.findById(houseId).orElseThrow(() -> new ResourceNotFoundException("House not found"));
        Room room = roomRepository.findById(roomId).orElseThrow(() -> new ResourceNotFoundException("Room not found"));
        SystemUser admin = getUserInContext();

        if (!room.getHouse().getHouseId().equals(houseId)) {
            throw new BadRequestException("Room does not belong to this house");
        }

        if (!admin.equals(house.getAdmin())) {
            throw new BadRequestException("Only the house admin can delete rooms");
        }

        for (Device device : room.getDevicesInRoom()) {
            device.setRoom(null);
            deviceRepository.save(device);
        }

        roomRepository.delete(room);
    }

    public void removeUserFromHouse(Long houseId, Long memberId) {
        log.info("Removing membership {} from house {}", memberId, houseId);
        House house = houseRepository.findById(houseId).orElseThrow(() -> new ResourceNotFoundException("House not found"));
        UserHomeMembership membership = membershipRepository.findById(memberId).orElseThrow(() -> new ResourceNotFoundException("Membership not found"));
        SystemUser admin = getUserInContext();

        if (!membership.getHouse().getHouseId().equals(houseId)) {
            throw new BadRequestException("Membership does not belong to this house");
        }

        if (!admin.equals(house.getAdmin())) {
            throw new BadRequestException("Only the house admin can remove users from the house");
        }

        if (membership.getUser().equals(house.getAdmin())) {
            throw new BadRequestException("Cannot remove the house admin from the house");
        }

        membershipRepository.delete(membership);
    }

    public List<House> getDeletedHouses() {
        log.debug("Fetching deleted houses");
        return houseRepository.findAllDeletedHouses();
    }


}
