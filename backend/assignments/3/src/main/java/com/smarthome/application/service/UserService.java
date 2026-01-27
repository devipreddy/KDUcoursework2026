package com.smarthome.application.service;


import com.smarthome.application.entity.House;
import com.smarthome.application.entity.Room;
import com.smarthome.application.entity.SystemUser;
import com.smarthome.application.entity.UserHomeMembership;
import com.smarthome.application.repository.RoomRepository;
import com.smarthome.application.repository.SystemUserRepository;
import com.smarthome.application.repository.UserHomeMembershipRepository;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import com.smarthome.application.exception.ResourceNotFoundException;
import com.smarthome.application.exception.BadRequestException;

@Service
public class UserService {

    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    private final SystemUserRepository systemUserRepository;
    private final UserHomeMembershipRepository membershipRepository;
    private final RoomRepository roomRepository;


    public UserService(SystemUserRepository systemUserRepository, UserHomeMembershipRepository membershipRepository, RoomRepository roomRepository){
        this.systemUserRepository = systemUserRepository;
        this.membershipRepository = membershipRepository;
        this.roomRepository = roomRepository;
    }

    public List<House> getHousesOfUser(){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();
        SystemUser user = systemUserRepository.findByUsername(username).orElseThrow(() -> {
            log.warn("User not found: {}", username);
            return new ResourceNotFoundException("User not found");
        });

        List<UserHomeMembership> memberships = membershipRepository.findByUser(user);

        return memberships.stream().map(UserHomeMembership::getHouse).distinct().toList();
    }

    public List<Room> getAllRoomsInHouse(Long houseId){
        return roomRepository.findByHouseHouseId(houseId);
    }

    public void deleteUserAccount() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        String username = authentication.getName();
        SystemUser user = systemUserRepository.findByUsername(username)
            .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        if (!user.getOwnedHouses().isEmpty()) {
            log.warn("User {} attempted account deletion but still owns houses", username);
            throw new BadRequestException(
                "Cannot delete account: You are still the admin of " +
                user.getOwnedHouses().size() +
                " house(s). Please transfer ownership of all houses before deleting your account."
            );
        }

        systemUserRepository.delete(user);
    }

    public List<Room> getDeletedRooms(Long houseId) {
        return roomRepository.findAllDeletedRoomsByHouseId(houseId);
    }
}

