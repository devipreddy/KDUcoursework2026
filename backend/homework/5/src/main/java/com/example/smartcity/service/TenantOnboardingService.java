package com.example.smartcity.service;

import com.example.smartcity.dto.request.CreateShiftTypeRequest;
import com.example.smartcity.dto.request.CreateUserRequest;
import com.example.smartcity.dto.request.OnboardTenantRequest;
import com.example.smartcity.repository.ShiftTypeJdbcRepository;
import com.example.smartcity.repository.UserJdbcRepository;
import com.example.smartcity.util.UuidUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class TenantOnboardingService {

    private static final Logger LOG = LoggerFactory.getLogger(TenantOnboardingService.class);

    private final UserJdbcRepository userRepo;
    private final ShiftTypeJdbcRepository shiftTypeRepo;

    public TenantOnboardingService(
            UserJdbcRepository userRepo,
            ShiftTypeJdbcRepository shiftTypeRepo
    ) {
        this.userRepo = userRepo;
        this.shiftTypeRepo = shiftTypeRepo;
    }

    @Transactional
    public void onboardTenant(OnboardTenantRequest request) {

        LOG.info("Onboarding tenant id={} shiftTypes={} users={}", request.tenantId(), request.shiftTypes().size(), request.users().size());

        // 1 Save shift types
        for (CreateShiftTypeRequest st : request.shiftTypes()) {
            shiftTypeRepo.insert(
                    UuidUtil.generate(),
                    st.name(),
                    st.description(),
                    request.tenantId()
            );
        }

        // Save users
        for (CreateUserRequest user : request.users()) {
            userRepo.insert(
                    UuidUtil.generate(),
                    user.username(),
                    user.timezone(),
                    request.tenantId()
            );
        }
        
        LOG.info("Completed onboarding tenant id={}", request.tenantId());

        // Force failure (for demo)
        //if (true) {
        //    throw new RuntimeException("Simulated failure at end of transaction");
        //}
    }
}
