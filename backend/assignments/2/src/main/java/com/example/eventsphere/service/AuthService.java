package com.example.eventsphere.service;

import com.example.eventsphere.config.JwtUtil;
import com.example.eventsphere.dto.RegistrationRequest;
import com.example.eventsphere.dto.RegistrationResponse;
import com.example.eventsphere.entity.TicketUser;
import com.example.eventsphere.repository.TicketUserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AuthService {

    private static final Logger log =
            LoggerFactory.getLogger(AuthService.class);

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final TicketUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(
            AuthenticationManager authenticationManager,
            JwtUtil jwtUtil,
            TicketUserRepository userRepository,
            PasswordEncoder passwordEncoder) {

        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String login(String username, String password) {

        Authentication authentication =
                authenticationManager.authenticate(
                        new UsernamePasswordAuthenticationToken(username, password)
                );

        User user = (User) authentication.getPrincipal();

        List<String> roles = user.getAuthorities()
                .stream()
                .map(a -> a.getAuthority().replace("ROLE_", ""))
                .toList();

        log.info("User logged in: {}", user.getUsername());

        return jwtUtil.generateToken(user.getUsername(), roles);
    }

    public RegistrationResponse register(RegistrationRequest request) {

        validateRegistration(request);

        if (userRepository.findByUsername(request.getUsername()).isPresent()) {
            throw new IllegalArgumentException(
                    "Username already exists: " + request.getUsername());
        }

        TicketUser user = new TicketUser(
                resolveName(request),
                request.getEmail(),
                request.getPhone() != null ? request.getPhone() : "",
                request.getUsername(),
                passwordEncoder.encode(request.getPassword()),
                "USER"
        );

        TicketUser saved = userRepository.save(user);

        log.info("New user registered: {}", saved.getUsername());

        return new RegistrationResponse(
                saved.getId(),
                saved.getUsername(),
                saved.getEmail(),
                "Registration successful. You can now login."
        );
    }


    private void validateRegistration(RegistrationRequest request) {

        if (request.getUsername() == null || request.getUsername().isBlank()) {
            throw new IllegalArgumentException("Username is required");
        }

        if (request.getPassword() == null || request.getPassword().isBlank()) {
            throw new IllegalArgumentException("Password is required");
        }

        if (request.getEmail() == null || request.getEmail().isBlank()) {
            throw new IllegalArgumentException("Email is required");
        }
    }

    private String resolveName(RegistrationRequest request) {
        return (request.getName() != null && !request.getName().isBlank())
                ? request.getName()
                : request.getUsername();
    }
}
