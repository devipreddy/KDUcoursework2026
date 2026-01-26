package com.smarthome.application.controller;

import com.smarthome.application.entity.House;
import com.smarthome.application.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import com.smarthome.application.config.TestSecurityConfig;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
@AutoConfigureMockMvc(addFilters = false)
@Import(TestSecurityConfig.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @Test
    void getHouses_returnsList() throws Exception {
        House h = new House();
        h.setHouseId(1L);
        h.setHouseName("Integration House");
        com.smarthome.application.entity.SystemUser admin = new com.smarthome.application.entity.SystemUser();
        admin.setUserId(10L);
        h.setAdmin(admin);

        when(userService.getHousesOfUser()).thenReturn(List.of(h));

        mockMvc.perform(get("/user/houses"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].houseName").value("Integration House"));
    }
}
