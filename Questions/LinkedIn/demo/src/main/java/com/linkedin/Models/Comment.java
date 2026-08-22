package com.linkedin.Models;

import java.time.Instant;

public class Comment {
    private final String memberId;
    private final String text;
    private final Instant createdAt;

    public Comment(String memberId, String text) {
        this.memberId = memberId;
        this.text = text;
        this.createdAt = Instant.now();
    }

    public String getMemberId() {
        return memberId;
    }

    public String getText() {
        return text;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }
}
