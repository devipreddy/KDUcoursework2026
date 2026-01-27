package com.smarthome.application.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.smarthome.application.entity.Device;
import com.smarthome.application.entity.DeviceInventory;
import com.smarthome.application.entity.Room;
import com.smarthome.application.service.DeviceService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import com.smarthome.application.config.TestSecurityConfig;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(DeviceController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(TestSecurityConfig.class)
class MoveDeviceControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private DeviceService deviceService;

    @Test
    void moveDevice_returnsDevice() throws Exception {
        ObjectMapper mapper = new ObjectMapper();

        Device device = new Device();
        device.setDeviceId(2L);
        DeviceInventory inv = new DeviceInventory();
        inv.setKickstonId("000100");
        device.setInventoryDevice(inv);
        Room room = new Room();
        room.setRoomId(3L);
        device.setRoom(room);

        when(deviceService.moveDeviceToAnotherRoom(eq(1L), eq(2L), any()))
                .thenReturn(device);

        mockMvc.perform(put("/houses/1/devices/2/move-room")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(mapper.writeValueAsString(Map.of("newRoomId", 3))))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.deviceId").value(2))
                .andExpect(jsonPath("$.kickstonId").value("000100"))
                .andExpect(jsonPath("$.roomId").value(3));
    }
}
