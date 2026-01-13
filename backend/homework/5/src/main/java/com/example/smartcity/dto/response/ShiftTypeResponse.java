package com.example.smartcity.dto.response;

public record ShiftTypeResponse(
        String id,
        String name,
        String description,
        boolean active
) {}

