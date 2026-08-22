package com.linkedin.Models;

import java.time.Instant;

import com.linkedin.Enums.ConnectionType;

public class Connection {
	private final String fromMemberId;
	private final String toMemberId;
	private final ConnectionType type;
	private final Instant createdAt;

	public Connection(String fromMemberId, String toMemberId, ConnectionType type) {
		this.fromMemberId = fromMemberId;
		this.toMemberId = toMemberId;
		this.type = type;
		this.createdAt = Instant.now();
	}

	public String getFromMemberId() {
		return fromMemberId;
	}

	public String getToMemberId() {
		return toMemberId;
	}

	public ConnectionType getType() {
		return type;
	}

	public Instant getCreatedAt() {
		return createdAt;
	}
}
