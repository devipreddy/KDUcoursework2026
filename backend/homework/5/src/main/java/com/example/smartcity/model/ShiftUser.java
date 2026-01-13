package com.example.smartcity.model;

import java.time.Instant;

public class ShiftUser {
    private String id;
    private String shiftId;
    private String userId;
    private String tenantId;
    private Instant assignedAt;

    public ShiftUser() {}

    public ShiftUser(String id, String shiftId, String userId, String tenantId) {
        this.id = id;
        this.shiftId = shiftId;
        this.userId = userId;
        this.tenantId = tenantId;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getShiftId() { return shiftId; }
    public void setShiftId(String shiftId) { this.shiftId = shiftId; }
    public String getUserId() { return userId; }
    public void setUserId(String userId) { this.userId = userId; }
    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }
    public Instant getAssignedAt() { return assignedAt; }
    public void setAssignedAt(Instant assignedAt) { this.assignedAt = assignedAt; }
}
