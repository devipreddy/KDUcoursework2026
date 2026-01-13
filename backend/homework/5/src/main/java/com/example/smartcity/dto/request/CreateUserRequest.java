package com.example.smartcity.dto.request;



public record CreateUserRequest(
        String username,
        String timezone,
        String tenantId
) {}

