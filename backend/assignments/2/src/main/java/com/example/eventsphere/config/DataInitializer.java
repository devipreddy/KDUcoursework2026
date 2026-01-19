package com.example.eventsphere.config;

import com.example.eventsphere.entity.TicketUser;
import com.example.eventsphere.repository.TicketUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    private static final Logger log =
            LoggerFactory.getLogger(DataInitializer.class);

    @Bean
    public CommandLineRunner initializeData(
            TicketUserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        return args -> {
            if (userRepository.count() > 0) {
                log.info("Users already exist, skipping data initialization");
                return;
            }

            log.info("Seeding database with default users");

            TicketUser admin = createUser(
                    "System Admin",
                    "admin@eventsphere.com",
                    "1111111111",
                    "admin",
                    "adminpassword",
                    "ADMIN",
                    passwordEncoder
            );

            TicketUser user = createUser(
                    "Event User",
                    "user@eventsphere.com",
                    "2222222222",
                    "user",
                    "userpassword",
                    "USER",
                    passwordEncoder
            );

            userRepository.save(admin);
            userRepository.save(user);

            log.info("Default users created successfully");
        };
    }

    private TicketUser createUser(
            String name,
            String email,
            String phone,
            String username,
            String rawPassword,
            String role,
            PasswordEncoder encoder) {

        return new TicketUser(
                name,
                email,
                phone,
                username,
                encoder.encode(rawPassword),
                role
        );
    }
}
