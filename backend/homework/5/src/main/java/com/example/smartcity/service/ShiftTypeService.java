package com.example.smartcity.service;

import com.example.smartcity.dto.request.CreateShiftTypeRequest;
import com.example.smartcity.dto.response.ShiftTypeResponse;
import com.example.smartcity.repository.ShiftTypeJdbcRepository;
import com.example.smartcity.util.UuidUtil;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Service
public class ShiftTypeService {

    private static final Logger LOG = LoggerFactory.getLogger(ShiftTypeService.class);

    private final ShiftTypeJdbcRepository repo;

    public ShiftTypeService(ShiftTypeJdbcRepository repo) {
        this.repo = repo;
    }

    public String create(CreateShiftTypeRequest req) {
        LOG.info("Creating shift type for tenantId={} name={}", req.tenantId(), req.name());
        String id = UuidUtil.generate();
        repo.insert(id, req.name(), req.description(), req.tenantId());
        LOG.info("Created shift type id={} for tenantId={}", id, req.tenantId());
        return id;
    }

    public List<ShiftTypeResponse> findByTenant(String tenantId) {
        LOG.debug("Finding shift types for tenantId={}", tenantId);
        List<ShiftTypeResponse> results = repo.findByTenant(tenantId);
        LOG.debug("Found {} shift types for tenantId={}", results.size(), tenantId);
        return results;
    }
}
