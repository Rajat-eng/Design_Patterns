package com.git.Builder;

import com.git.Models.Commit.Commit;
import com.git.Models.User.User;
import java.util.HashMap;
import java.util.Map;
import java.util.List;
import java.util.ArrayList;

/**
 * Builder Pattern:
 * Provides a fluent interface for creating complex Commit objects
 * Separates construction logic from representation
 */
public class CommitBuilder {
    private String message;
    private User author;
    private List<Commit> parents;
    private Map<String, String> files;
    private Map<String, String> metadata;
    
    public CommitBuilder() {
        this.parents = new ArrayList<>();
        this.files = new HashMap<>();
        this.metadata = new HashMap<>();
    }
    
    public CommitBuilder withMessage(String message) {
        this.message = message;
        return this;
    }
    
    public CommitBuilder by(User author) {
        this.author = author;
        return this;
    }
    
    public CommitBuilder withParent(Commit parent) {
        if (parent != null) {
            this.parents.add(parent);
        }
        return this;
    }
    
    public CommitBuilder withParents(List<Commit> parents) {
        if (parents != null) {
            this.parents.addAll(parents);
        }
        return this;
    }
    
    public CommitBuilder addFile(String filename, String content) {
        this.files.put(filename, content);
        return this;
    }
    
    public CommitBuilder addFiles(Map<String, String> files) {
        this.files.putAll(files);
        return this;
    }
    
    public CommitBuilder withMetadata(String key, String value) {
        this.metadata.put(key, value);
        return this;
    }
    
    public Commit build() {
        if (message == null || message.trim().isEmpty()) {
            throw new IllegalStateException("Commit message is required");
        }
        if (author == null) {
            throw new IllegalStateException("Commit author is required");
        }
        
        Commit commit;
        if (parents.isEmpty()) {
            // Initial commit
            commit = new Commit(message, author);
        } else if (parents.size() == 1) {
            // Regular commit
            commit = new Commit(message, author, parents.get(0));
        } else {
            // Merge commit
            commit = new Commit(message, author, parents);
        }
        
        // Add files
        for (Map.Entry<String, String> entry : files.entrySet()) {
            commit.addFile(entry.getKey(), entry.getValue());
        }
        
        return commit;
    }
    
    // Static factory method for fluent API
    public static CommitBuilder create() {
        return new CommitBuilder();
    }
}
