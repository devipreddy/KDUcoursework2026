package com.example.smartcity.dto.request;

public record CreateShiftTypeRequest(
        String name,
        String description,
        String tenantId
) {}
