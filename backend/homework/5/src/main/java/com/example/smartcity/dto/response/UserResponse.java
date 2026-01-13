package com.example.smartcity.dto.response;

public record UserResponse(
        String id,
        String username,
        String timezone,
        boolean loggedIn
) {}
