package com.git.Models.Stash;

import com.git.Models.User.User;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Stash represents saved work-in-progress state
 * Follows Single Responsibility Principle
 */
public class Stash {
    private String stashId;
    private String message;
    private User author;
    private LocalDateTime timestamp;
    private String branchName;
    private Map<String, String> savedFiles;

    public Stash(String message, User author, String branchName, Map<String, String> files) {
        this.stashId = UUID.randomUUID().toString().substring(0, 8);
        this.message = message != null ? message : "WIP on " + branchName;
        this.author = author;
        this.timestamp = LocalDateTime.now();
        this.branchName = branchName;
        this.savedFiles = new HashMap<>(files);
    }

    public String getStashId() {
        return stashId;
    }

    public String getMessage() {
        return message;
    }

    public User getAuthor() {
        return author;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public String getBranchName() {
        return branchName;
    }

    public Map<String, String> getSavedFiles() {
        return new HashMap<>(savedFiles);
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return String.format("stash@{%s}: %s on %s (%s)",
            stashId, message, branchName, timestamp.format(formatter));
    }
}
