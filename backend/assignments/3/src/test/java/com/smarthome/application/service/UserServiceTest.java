package com.smarthome.application.service;

import com.smarthome.application.entity.House;
import com.smarthome.application.entity.SystemUser;
import com.smarthome.application.entity.UserHomeMembership;
import com.smarthome.application.repository.RoomRepository;
import com.smarthome.application.repository.SystemUserRepository;
import com.smarthome.application.repository.UserHomeMembershipRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock private SystemUserRepository systemUserRepository;
    @Mock private UserHomeMembershipRepository membershipRepository;
    @Mock private RoomRepository roomRepository;

    @Mock private SecurityContext securityContext;
    @Mock private Authentication authentication;

    @InjectMocks
    private UserService userService;

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
    void getHousesOfUser_ReturnsDistinctHouses() {
        // 1. Arrange
        String username = "homeOwner";
        SystemUser user = new SystemUser();
        user.setUsername(username);

        House house1 = new House();
        house1.setHouseId(1L);
        house1.setHouseName("Beach House");

        House house2 = new House();
        house2.setHouseId(2L);
        house2.setHouseName("City Apt");

        // Membership links
        UserHomeMembership mem1 = new UserHomeMembership();
        mem1.setHouse(house1);
        mem1.setUser(user);

        UserHomeMembership mem2 = new UserHomeMembership();
        mem2.setHouse(house2);
        mem2.setUser(user);

        mockSecurityContext(username);
        when(systemUserRepository.findByUsername(username)).thenReturn(Optional.of(user));
        when(membershipRepository.findByUser(user)).thenReturn(List.of(mem1, mem2));

        // 2. Act
        List<House> result = userService.getHousesOfUser();

        // 3. Assert
        assertEquals(2, result.size());
        assertEquals("Beach House", result.get(0).getHouseName());
        assertEquals("City Apt", result.get(1).getHouseName());
    }
}