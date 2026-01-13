package com.example.smartcity.model;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalTime;

public class Shift {
    private String id;
    private String shiftTypeId;
    private LocalDate shiftDate;
    private LocalTime startTime;
    private LocalTime endTime;
    private String tenantId;
    private Instant createdAt;

    public Shift() {}

    public Shift(String id, String shiftTypeId, LocalDate shiftDate, LocalTime startTime, LocalTime endTime, String tenantId) {
        this.id = id;
        this.shiftTypeId = shiftTypeId;
        this.shiftDate = shiftDate;
        this.startTime = startTime;
        this.endTime = endTime;
        this.tenantId = tenantId;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getShiftTypeId() { return shiftTypeId; }
    public void setShiftTypeId(String shiftTypeId) { this.shiftTypeId = shiftTypeId; }
    public LocalDate getShiftDate() { return shiftDate; }
    public void setShiftDate(LocalDate shiftDate) { this.shiftDate = shiftDate; }
    public LocalTime getStartTime() { return startTime; }
    public void setStartTime(LocalTime startTime) { this.startTime = startTime; }
    public LocalTime getEndTime() { return endTime; }
    public void setEndTime(LocalTime endTime) { this.endTime = endTime; }
    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
