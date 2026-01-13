package com.example.smartcity.controller;


import com.example.smartcity.dto.request.CreateUserRequest;
import com.example.smartcity.dto.request.UpdateUserRequest;
import com.example.smartcity.dto.response.UserResponse;
import com.example.smartcity.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    private static final Logger LOG = LoggerFactory.getLogger(UserController.class);

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<String> create(@RequestBody CreateUserRequest request) {
        LOG.info("HTTP POST /api/users - create user for tenantId={} username={}", request.tenantId(), request.username());
        String id = service.create(request);
        LOG.info("User created id={}", id);
        return ResponseEntity.created(URI.create("/api/users/" + id)).body(id);
    }

    @GetMapping
    public ResponseEntity<List<UserResponse>> byTenant(@RequestParam String tenantId) {
        LOG.debug("HTTP GET /api/users byTenant tenantId={}", tenantId);
        List<UserResponse> list = service.findByTenant(tenantId);
        LOG.debug("Returning {} users for tenantId={}", list.size(), tenantId);
        return ResponseEntity.ok(list);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Void> update(
            @PathVariable String id,
            @RequestParam String tenantId,
            @RequestBody UpdateUserRequest request
    ) {
        try {
            LOG.info("HTTP PUT /api/users/{} tenantId={} - update", id, tenantId);
            service.update(id, tenantId, request);
            LOG.info("Update successful for user id={}", id);
            return ResponseEntity.noContent().build();
        } catch (IllegalStateException ex) {
            LOG.warn("Update failed for user id={} tenantId={}: {}", id, tenantId, ex.getMessage());
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/paged")
    public List<UserResponse> pagedUsers(
            @RequestParam String tenantId,
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam(defaultValue = "asc") String sort
    ) {
        LOG.debug("HTTP GET /api/users/paged tenantId={} page={} size={} sort={}", tenantId, page, size, sort);
        return service.getUsersPaged(tenantId, page, size, sort);
    }

}

