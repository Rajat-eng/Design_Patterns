package com.linkedin.Services;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

import com.linkedin.Models.Comment;
import com.linkedin.Models.Post;

public class EngagementService {
    private final ExecutorService executor;

    public EngagementService(ExecutorService executor) {
        this.executor = executor;
    }

    public CompletableFuture<Void> likePost(Post post, String memberId) {
        return CompletableFuture.runAsync(() -> post.addLike(memberId), executor);
    }

    public CompletableFuture<Void> commentOnPost(Post post, String memberId, String text) {
        return CompletableFuture.runAsync(() -> post.addComment(new Comment(memberId, text)), executor);
    }
}
