package com.example.library.web.bootstrap;

import com.example.library.domain.entity.User;
import com.example.library.domain.enums.Role;
import com.example.library.domain.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class DataInitializer {

    @Bean
    CommandLineRunner initUsers(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder
    ) {
        return args -> {

            if (userRepository.count() > 0) {
                return;
            }

            User librarian = User.create(
                    "librarian",
                    passwordEncoder.encode("password"),
                    Role.LIBRARIAN
            );

            User member = User.create(
                    "member",
                    passwordEncoder.encode("password"),
                    Role.MEMBER
            );

            userRepository.save(librarian);
            userRepository.save(member);
        };
    }
}
