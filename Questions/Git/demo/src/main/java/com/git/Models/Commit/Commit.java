package com.git.Models.Commit;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.UUID;

import com.git.Models.User.User;

/**
 * Commit represents a node in the Git DAG (Directed Acyclic Graph)
 * Follows Liskov Substitution Principle (LSP):
 * Regular commits and merge commits can be used interchangeably.
 */
public class Commit {
    private String commitId;
    private String message;
    private User author;
    private LocalDateTime timestamp;
    private List<Commit> parents;  // DAG: Multiple parents for merge commits
    private Map<String, String> fileSnapshots;  // filename -> content

    // Initial commit (no parent)
    public Commit(String message, User author) {
        this(message, author, (Commit) null);
    }
    
    // Regular commit with single parent
    public Commit(String message, User author, Commit parent) {
        this.commitId = UUID.randomUUID().toString().substring(0, 8);
        this.message = message;
        this.author = author;
        this.timestamp = LocalDateTime.now();
        this.parents = new ArrayList<>();
        if (parent != null) {
            this.parents.add(parent);
        }
        this.fileSnapshots = new HashMap<>();
    }
    
    // Merge commit with multiple parents (DAG structure)
    public Commit(String message, User author, List<Commit> parents) {
        //parents is the list of parent commits. This commit eill be formed after merging
        this.commitId = UUID.randomUUID().toString().substring(0, 8);
        this.message = message;
        this.author = author;
        this.timestamp = LocalDateTime.now();
        this.parents = new ArrayList<>(parents != null ? parents : new ArrayList<>());
        this.fileSnapshots = new HashMap<>();
    }

    public void addFile(String filename, String content) {
        fileSnapshots.put(filename, content);
    }

    public String getCommitId() {
        return commitId;
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

    public List<Commit> getParents() {
        return new ArrayList<>(parents);
    }
    
    public boolean isMergeCommit() {
        return parents.size() > 1;  
    }

    public Map<String, String> getFileSnapshots() {
        return new HashMap<>(fileSnapshots);
    }

    // Traverse DAG using BFS to get commit history (topological order)
    public List<Commit> getHistory() {
        List<Commit> history = new ArrayList<>();
        Set<Commit> visited = new HashSet<>();
        Queue<Commit> queue = new LinkedList<>();
        
        queue.offer(this);
        visited.add(this);
        
        while (!queue.isEmpty()) {
            Commit current = queue.poll();
            history.add(current);
            
            // Add all parents to queue (DAG traversal)
            for (Commit parent : current.parents) {
                if (!visited.contains(parent)) {
                    visited.add(parent);
                    queue.offer(parent);
                }
            }
        }
        
        return history;
    }
    
    // DFS traversal of DAG for debugging
    public void printDAG(Set<Commit> visited, String indent) {
        if (visited.contains(this)) {
            System.out.println(indent + "commit " + commitId + " (already shown)");
            return;
        }
        
        visited.add(this);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        System.out.println(indent + "commit " + commitId);
        System.out.println(indent + "Author: " + author);
        System.out.println(indent + "Date: " + timestamp.format(formatter));
        if (isMergeCommit()) {
            System.out.println(indent + "Merge: " + parents.get(0).commitId + " " + parents.get(1).commitId);
        }
        System.out.println(indent + "    " + message);
        System.out.println();
        
        for (Commit parent : parents) {
            parent.printDAG(visited, indent);
        }
    }

    @Override
    public String toString() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        StringBuilder sb = new StringBuilder();
        sb.append("commit ").append(commitId);
        if (isMergeCommit()) {
            sb.append(" (MERGE)");
        }
        sb.append("\n");
        if (isMergeCommit()) {
            sb.append("Merge:");
            for (Commit parent : parents) {
                sb.append(" ").append(parent.commitId);
            }
            sb.append("\n");
        }
        sb.append("Author: ").append(author).append("\n");
        sb.append("Date: ").append(timestamp.format(formatter)).append("\n");
        sb.append("\n    ").append(message).append("\n");
        return sb.toString();
    }
}
