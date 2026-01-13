package com.example.smartcity.model;

import java.time.Instant;

public class User {
    private String id;
    private String username;
    private boolean loggedIn;
    private String timezone;
    private String tenantId;
    private Instant createdAt;
    private Instant updatedAt;

    public User() {}

    public User(String id, String username, boolean loggedIn, String timezone, String tenantId) {
        this.id = id;
        this.username = username;
        this.loggedIn = loggedIn;
        this.timezone = timezone;
        this.tenantId = tenantId;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }
    public boolean isLoggedIn() { return loggedIn; }
    public void setLoggedIn(boolean loggedIn) { this.loggedIn = loggedIn; }
    public String getTimezone() { return timezone; }
    public void setTimezone(String timezone) { this.timezone = timezone; }
    public String getTenantId() { return tenantId; }
    public void setTenantId(String tenantId) { this.tenantId = tenantId; }
    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
    public Instant getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(Instant updatedAt) { this.updatedAt = updatedAt; }
}
