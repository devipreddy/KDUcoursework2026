package com.smarthome.application.config;

import com.smarthome.application.entity.DeviceInventory;
import com.smarthome.application.entity.SystemUser;
import com.smarthome.application.repository.DeviceInventoryRepository;
import com.smarthome.application.repository.SystemUserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDateTime;

@Configuration
@Profile("!test & !sql")
public class DataInitializer {

    private static final Logger log = LoggerFactory.getLogger(DataInitializer.class);

    @Bean
    CommandLineRunner seedData(SystemUserRepository userRepository,
                               DeviceInventoryRepository inventoryRepository,
                               PasswordEncoder encoder) {

        return args -> {

            if (userRepository.count() == 0) {
                log.info("Seeding initial users");

                SystemUser admin = new SystemUser();
                admin.setUsername("mike");
                admin.setPassword(encoder.encode("mike123"));

                SystemUser user = new SystemUser();
                user.setUsername("tom");
                user.setPassword(encoder.encode("tom123"));

                SystemUser ravi = new SystemUser();
                ravi.setUsername("ravi");
                ravi.setPassword(encoder.encode("ravi123"));

                userRepository.save(admin);
                userRepository.save(user);
                userRepository.save(ravi);
            }

            if (inventoryRepository.count() == 0) {
                log.info("Seeding initial device inventory");

                DeviceInventory d1 = new DeviceInventory();
                d1.setKickstonId("000010");
                d1.setDeviceUsername("kick001");
                d1.setDevicePassword("pass001");
                d1.setManufactureDateTime(LocalDateTime.now());
                d1.setPlaceOfManufacture("China Hub 1");

                DeviceInventory d2 = new DeviceInventory();
                d2.setKickstonId("000011");
                d2.setDeviceUsername("kick002");
                d2.setDevicePassword("pass002");
                d2.setManufactureDateTime(LocalDateTime.now());
                d2.setPlaceOfManufacture("China Hub 2");

                DeviceInventory d3 = new DeviceInventory();
                d3.setKickstonId("000012");
                d3.setDeviceUsername("kick003");
                d3.setDevicePassword("pass003");
                d3.setManufactureDateTime(LocalDateTime.now());
                d3.setPlaceOfManufacture("China Hub 3");

                inventoryRepository.save(d1);
                inventoryRepository.save(d2);
                inventoryRepository.save(d3);

            }
        };
    }
}
