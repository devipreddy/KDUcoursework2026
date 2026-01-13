package com.example.smartcity.controller;

import com.example.smartcity.dto.request.CreateShiftTypeRequest;
import com.example.smartcity.dto.response.ShiftTypeResponse;
import com.example.smartcity.service.ShiftTypeService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/shift-types")
public class ShiftTypeController {

    private static final Logger LOG = LoggerFactory.getLogger(ShiftTypeController.class);

    private final ShiftTypeService service;

    public ShiftTypeController(ShiftTypeService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody CreateShiftTypeRequest request) {
        LOG.info("HTTP POST /api/shift-types create - tenantId={} name={}", request.tenantId(), request.name());
        String id = service.create(request);
        LOG.info("Shift type created id={}", id);
        return ResponseEntity.created(URI.create("/api/shift-types/" + id)).body(id);
    }

    @GetMapping
    public ResponseEntity<List<ShiftTypeResponse>> byTenant(@RequestParam String tenantId) {
        LOG.debug("HTTP GET /api/shift-types tenantId={}", tenantId);
        List<ShiftTypeResponse> list = service.findByTenant(tenantId);
        LOG.debug("Returning {} shift types for tenantId={}", list.size(), tenantId);
        return ResponseEntity.ok(list);
    }
}

