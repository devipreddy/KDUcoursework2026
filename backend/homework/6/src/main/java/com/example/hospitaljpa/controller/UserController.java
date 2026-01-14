package com.example.hospitaljpa.controller;

import org.springframework.web.bind.annotation.*;
import com.example.hospitaljpa.dto.request.CreateUserRequest;
import com.example.hospitaljpa.dto.response.UserResponse;
import com.example.hospitaljpa.service.UserService;
import static com.example.hospitaljpa.util.ApiConstants.*;
import org.springframework.data.domain.Page;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping(USERS)
@Tag(name = "Users", description = "Create and list users with pagination support")
@Slf4j
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    @Operation(summary = "Create user", description = "Create a new user with name and role")
    @ApiResponse(responseCode = "200", description = "User created successfully",
            content = @Content(mediaType = "application/json", schema = @Schema(implementation = UserResponse.class)))
    public UserResponse create(@RequestBody CreateUserRequest request) {
        log.debug("POST {} - payload: {}", USERS, request);
        var user = service.create(request.getName(), request.getRole());
        log.info("User created via controller id={}", user.getId());
        return new UserResponse(user.getId(), user.getName(), user.getRole());
    }

    @GetMapping
    @Operation(summary = "List users", description = "Retrieve a paginated list of users")
    @ApiResponse(responseCode = "200", description = "Page of users returned",
            content = @Content(mediaType = "application/json"))
    public Page<UserResponse> getUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size) {
        log.debug("GET {} - page={} size={}", USERS, page, size);
        return service.getUsers(page, size);
    }

}
