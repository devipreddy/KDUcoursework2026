package com.smarthome.application.integration;

import com.smarthome.application.entity.Device;
import com.smarthome.application.entity.DeviceInventory;
import com.smarthome.application.entity.House;
import com.smarthome.application.entity.Room;
import com.smarthome.application.entity.SystemUser;
import com.smarthome.application.repository.DeviceInventoryRepository;
import com.smarthome.application.repository.DeviceRepository;
import com.smarthome.application.repository.HouseRepository;
import com.smarthome.application.repository.RoomRepository;
import com.smarthome.application.repository.SystemUserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@org.springframework.context.annotation.Import(com.smarthome.application.config.TestSecurityConfig.class)
class ListRoomsWithDevicesIntegrationTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private SystemUserRepository userRepository;

    @Autowired
    private HouseRepository houseRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private DeviceInventoryRepository inventoryRepository;
    
    @Autowired
    private com.smarthome.application.repository.UserHomeMembershipRepository membershipRepository;

    @BeforeEach
    void setUp() {
        deviceRepository.deleteAll();
        roomRepository.deleteAll();
        houseRepository.deleteAll();
        inventoryRepository.deleteAll();
        userRepository.deleteAll();

        SystemUser user = new SystemUser();
        user.setUsername("itest");
        user.setPassword("pass");
        userRepository.save(user);

        House house = new House();
        house.setHouseName("Integration House");
        house.setAdmin(user);
        houseRepository.save(house);

        Room room = new Room();
        room.setRoomName("Living");
        room.setHouse(house);
        roomRepository.save(room);

        DeviceInventory inv = new DeviceInventory();
        inv.setKickstonId("000100");
        inv.setDeviceUsername("dev");
        inv.setDevicePassword("p");
        inventoryRepository.save(inv);

        Device device = new Device();
        device.setHouse(house);
        device.setRoom(room);
        device.setInventoryDevice(inv);
        deviceRepository.save(device);

        // create membership so the test user is part of the house
        com.smarthome.application.entity.UserHomeMembership membership = new com.smarthome.application.entity.UserHomeMembership();
        membership.setHouse(house);
        membership.setUser(user);
        membershipRepository.save(membership);
    }

    @Test
    void listRoomsWithDevices_returnsRooms() {
        House aHouse = houseRepository.findAll().stream().findFirst().orElseThrow();

        ResponseEntity<Object[]> response = restTemplate.getForEntity("/" + aHouse.getHouseId() + "/rooms-with-devices", Object[].class);

        assertThat(response.getStatusCode().is2xxSuccessful()).isTrue();
        assertThat(response.getBody()).isNotNull();
        assertThat(response.getBody().length).isGreaterThan(0);
    }
}
