package com.example.smartcity.controller;

import com.example.smartcity.dto.request.OnboardTenantRequest;
import com.example.smartcity.service.TenantOnboardingService;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/tenants")
public class TenantController {

    private static final Logger LOG = LoggerFactory.getLogger(TenantController.class);

    private final TenantOnboardingService service;

    public TenantController(TenantOnboardingService service) {
        this.service = service;
    }

    @PostMapping("/onboard")
    public void onboard(@RequestBody OnboardTenantRequest request) {
        LOG.info("HTTP POST /api/tenants/onboard tenantId={} shiftTypes={} users={}", request.tenantId(), request.shiftTypes().size(), request.users().size());
        service.onboardTenant(request);
    }
}
