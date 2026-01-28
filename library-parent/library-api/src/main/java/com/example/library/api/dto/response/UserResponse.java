package com.example.library.api.dto.response;

import com.example.library.api.enums.Role;

import java.time.Instant;
import java.util.UUID;

/**
 * API response representing a user.
 */
public class UserResponse {

    /** Unique user identifier */
    private UUID id;

    /** Username used for authentication */
    private String username;

    /** Assigned role */
    private Role role;

    /** Whether the account is enabled */
    private boolean enabled;

    /** Account creation timestamp */
    private Instant createdAt;


    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getUsername() { return username; }
    public void setUsername(String username) { this.username = username; }

    public Role getRole() { return role; }
    public void setRole(Role role) { this.role = role; }

    public boolean isEnabled() { return enabled; }
    public void setEnabled(boolean enabled) { this.enabled = enabled; }

    public Instant getCreatedAt() { return createdAt; }
    public void setCreatedAt(Instant createdAt) { this.createdAt = createdAt; }
}
