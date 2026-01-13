package com.example.smartcity.dto.request;

import java.util.List;

public record OnboardTenantRequest(
        String tenantId,
        List<CreateShiftTypeRequest> shiftTypes,
        List<CreateUserRequest> users
) {}
