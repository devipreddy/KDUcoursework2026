package com.example.securelock.service.impl;

import com.example.securelock.exception.AuthenticationException;
import com.example.securelock.model.User;
import com.example.securelock.repository.UserRepository;
import com.example.securelock.service.AuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);

    private final UserRepository userRepository;

    public AuthServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User register(String username, String password) {
        log.info("Registering user: {}", username);

        userRepository.findByUsername(username)
                .ifPresent(u -> {
                    throw new AuthenticationException("Username already exists");
                });

        User user = User.builder()
                .username(username)
                .password(password)
                .build();

        return userRepository.save(user);
    }

    @Override
    public User authenticate(String username, String password) {
        log.info("Authenticating user: {}", username);

        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new AuthenticationException("Invalid credentials"));

        if (!user.getPassword().equals(password)) {
            throw new AuthenticationException("Invalid credentials");
        }

        return user;
    }
}
