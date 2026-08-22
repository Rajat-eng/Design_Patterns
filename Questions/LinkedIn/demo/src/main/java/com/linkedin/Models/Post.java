package com.linkedin.Models;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import com.linkedin.Enums.PostVisibility;

public class Post {
    private final String id;
    private final String authorId;
    private final String content;
    private final Instant createdAt;
    private final String channelName;
    private final PostVisibility visibility;

    private final Set<String> likes = ConcurrentHashMap.newKeySet();
    private final List<Comment> comments = new ArrayList<>();
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    public Post(String id, String authorId, String content, String channelName) {
        this(id, authorId, content, channelName, PostVisibility.PUBLIC);
    }

    public Post(String id, String authorId, String content, String channelName, PostVisibility visibility) {
        this.id = id;
        this.authorId = authorId;
        this.content = content;
        this.channelName = channelName;
        this.visibility = visibility;
        this.createdAt = Instant.now();
    }

    public String getId() {
        return id;
    }

    public String getAuthorId() {
        return authorId;
    }

    public String getContent() {
        return content;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public String getChannelName() {
        return channelName;
    }

    public PostVisibility getVisibility() {
        return visibility;
    }

    public void addLike(String memberId) {
        lock.writeLock().lock();
        try {
            likes.add(memberId);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public void addComment(Comment comment) {
        lock.writeLock().lock();
        try {
            comments.add(comment);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public int getLikeCount() {
        lock.readLock().lock();
        try {
            return likes.size();
        } finally {
            lock.readLock().unlock();
        }
    }

    public List<Comment> getComments() {
        lock.readLock().lock();
        try {
            return new ArrayList<>(comments);
        } finally {
            lock.readLock().unlock();
        }
    }
}
