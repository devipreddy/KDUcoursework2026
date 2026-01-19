package com.example.eventsphere.controller;

import com.example.eventsphere.dto.LoginRequest;
import com.example.eventsphere.dto.LoginResponse;
import com.example.eventsphere.dto.RegistrationRequest;
import com.example.eventsphere.dto.RegistrationResponse;
import com.example.eventsphere.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class AuthController {

    private static final Logger log =
            LoggerFactory.getLogger(AuthController.class);

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @Operation(
            summary = "User login",
            description = "Authenticate user credentials and return JWT token"
    )
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody LoginRequest request) {

        String token =
                authService.login(request.getUsername(), request.getPassword());

        log.info("Generated token for {}", request.getUsername());

        return ResponseEntity.ok(new LoginResponse(token));
    }

    @Operation(
            summary = "User registration",
            description = "Register a new user account"
    )
    @PostMapping("/register")
    public ResponseEntity<RegistrationResponse> register(
            @RequestBody RegistrationRequest request) {

        RegistrationResponse result =
                authService.register(request);

        log.info("User {} registered successfully", request.getUsername());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(result);
    }
}
