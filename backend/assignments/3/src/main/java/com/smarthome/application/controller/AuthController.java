package com.smarthome.application.controller;


import com.smarthome.application.dto.AuthResponseDto;
import com.smarthome.application.dto.LoginRequestDto;
import com.smarthome.application.service.AuthService;
import com.smarthome.application.dto.RegisterRequestDto;
import org.springframework.http.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import jakarta.validation.Valid;
import org.springframework.validation.annotation.Validated;


@RestController
@Validated
@Tag(name = "Auth", description = "Authentication endpoints")
public class AuthController {

    private final AuthService authService;
    private static final Logger log = LoggerFactory.getLogger(AuthController.class);

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(ApiEndpoints.AUTH_LOGIN)
    @Operation(summary = "Authenticate and receive JWT token")
    public ResponseEntity<AuthResponseDto> login(@Valid @RequestBody LoginRequestDto request) {
        log.info("POST /login - authentication attempt for user: {}", request.getUsername());
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping(ApiEndpoints.AUTH_REGISTER)
    @Operation(summary = "Register a new user and receive JWT token")
    public ResponseEntity<AuthResponseDto> register(@Valid @RequestBody RegisterRequestDto request) {
        log.info("POST /register - register new user: {}", request.getUsername());
        return ResponseEntity.ok(authService.register(request));
    }
}
