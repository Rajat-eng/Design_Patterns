package com.linkedin.Models;

import java.time.Instant;

import com.linkedin.Enums.ConnectionRequestStatus;

public class ConnectionRequest {
    private final String fromMemberId;
    private final String toMemberId;
    private ConnectionRequestStatus status;
    private final Instant createdAt;
    private Instant updatedAt;

    public ConnectionRequest(String fromMemberId, String toMemberId) {
        this.fromMemberId = fromMemberId;
        this.toMemberId = toMemberId;
        this.status = ConnectionRequestStatus.PENDING;
        this.createdAt = Instant.now();
        this.updatedAt = createdAt;
    }

    public String getFromMemberId() {
        return fromMemberId;
    }

    public String getToMemberId() {
        return toMemberId;
    }

    public ConnectionRequestStatus getStatus() {
        return status;
    }

    public void setStatus(ConnectionRequestStatus status) {
        this.status = status;
        this.updatedAt = Instant.now();
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}
