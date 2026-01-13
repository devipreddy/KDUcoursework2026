package com.example.smartcity.model;

import java.time.Instant;

public class ShiftType {
    private String id;
    private String name;
    private String description;
    private boolean active;
    private String tenantId;
    private Instant createdAt;
    private Instant updatedAt;

    public ShiftType() {}

    public ShiftType(String id, String name, String description, boolean active, String tenantId) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.active = active;
        this.tenantId = tenantId;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }
    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}
