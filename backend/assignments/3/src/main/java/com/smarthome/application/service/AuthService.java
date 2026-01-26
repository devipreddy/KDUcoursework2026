package com.smarthome.application.service;



import com.smarthome.application.config.JwtUtil;
import com.smarthome.application.dto.AuthResponseDto;
import com.smarthome.application.dto.LoginRequestDto;
import com.smarthome.application.entity.SystemUser;
import com.smarthome.application.repository.SystemUserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.smarthome.application.dto.RegisterRequestDto;
import com.smarthome.application.exception.ConflictException;
import com.smarthome.application.exception.ResourceNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


@Service
public class AuthService {

    private static final Logger log = LoggerFactory.getLogger(AuthService.class);

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final SystemUserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AuthService(AuthenticationManager authenticationManager, JwtUtil jwtUtil, SystemUserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public AuthResponseDto login(LoginRequestDto request) {

        log.info("Authentication attempt for user {}", request.getUsername());
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
        SystemUser user = userRepository.findByUsername(request.getUsername()).orElseThrow(() -> {
            log.warn("Login failed - user not found: {}", request.getUsername());
            return new ResourceNotFoundException("User not found");
        });
        String token = jwtUtil.generateToken(user.getUsername());
        log.info("User {} authenticated successfully", request.getUsername());

        return new AuthResponseDto(token);
    }

    public AuthResponseDto register(RegisterRequestDto request) {
        log.info("Register attempt for user {}", request.getUsername());
        boolean exists = userRepository.findByUsername(request.getUsername()).isPresent();
        if (exists) {
            throw new ConflictException("Username already exists");
        }

        SystemUser user = new SystemUser();
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(user);

        String token = jwtUtil.generateToken(user.getUsername());
        log.info("User {} registered successfully", request.getUsername());
        return new AuthResponseDto(token);
    }

}

