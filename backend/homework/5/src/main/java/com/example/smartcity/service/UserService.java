package com.example.smartcity.service;

import com.example.smartcity.dto.request.CreateUserRequest;
import com.example.smartcity.dto.request.UpdateUserRequest;
import com.example.smartcity.dto.response.UserResponse;
import com.example.smartcity.repository.UserJdbcRepository;
import com.example.smartcity.util.UuidUtil;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    private final UserJdbcRepository repo;

    public UserService(UserJdbcRepository repo) {
        this.repo = repo;
    }

    public String create(CreateUserRequest req) {
        LOG.info("Creating user for tenantId={} username={}", req.tenantId(), req.username());
        String id = UuidUtil.generate();
        repo.insert(id, req.username(), req.timezone(), req.tenantId());
        LOG.info("Created user id={} for tenantId={}", id, req.tenantId());
        return id;
    }

    public List<UserResponse> findByTenant(String tenantId) {
        LOG.debug("Finding users for tenantId={}", tenantId);
        List<UserResponse> results = repo.findByTenant(tenantId);
        LOG.debug("Found {} users for tenantId={}", results.size(), tenantId);
        return results;
    }

    public void update(String id, String tenantId, UpdateUserRequest req) {
        LOG.info("Updating user id={} tenantId={} username={}", id, tenantId, req.username());
        int updated = repo.update(id, tenantId, req.username(), req.timezone());
        if (updated == 0) {
            LOG.warn("Update affected 0 rows for user id={} tenantId={}", id, tenantId);
            throw new IllegalStateException("User not found");
        }
        LOG.info("Updated user id={} tenantId={} affectedRows={}", id, tenantId, updated);
    }

    public List<UserResponse> getUsersPaged(
            String tenantId,
            int page,
            int size,
            String sort
    ) {
        int offset = page * size;
        LOG.debug("Fetching paged users tenantId={} page={} size={} sort={}", tenantId, page, size, sort);
        List<UserResponse> results = repo.findByTenantPaged(tenantId, size, offset, sort);
        LOG.debug("Paged fetch returned {} rows (tenantId={}, page={})", results.size(), tenantId, page);
        return results;
    }

}

