package com.example.securelock.controller;

import com.example.securelock.constants.ApiPaths;
import com.example.securelock.dto.LoginRequestDTO;
import com.example.securelock.dto.RegisterRequestDTO;
import com.example.securelock.model.User;
import com.example.securelock.service.AuthService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(ApiPaths.AUTH_BASE)
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping(ApiPaths.REGISTER)
    public User register(@RequestBody RegisterRequestDTO dto) {
        return authService.register(dto.username(), dto.password());
    }

    @PostMapping(ApiPaths.LOGIN)
    public User login(@RequestBody LoginRequestDTO dto) {
        return authService.authenticate(dto.username(), dto.password());
    }
}
