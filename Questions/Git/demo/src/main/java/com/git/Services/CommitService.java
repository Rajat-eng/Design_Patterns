package com.git.Services;

import com.git.Models.Commit.Commit;
import com.git.Models.User.User;
import com.git.Services.Interfaces.ICommitService;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * Single Responsibility Principle (SRP):
 * This class is responsible ONLY for commit-related operations.
 */
public class CommitService implements ICommitService {
    
    @Override
    public Commit createCommit(String message, User author, Commit parent) {
        return new Commit(message, author, parent);
    }
    
    @Override
    public Commit createMergeCommit(String message, User author, List<Commit> parents) {
        return new Commit(message, author, parents);
    }
    
    @Override
    public List<Commit> getCommitHistory(Commit commit) {
        if (commit == null) {
            return new ArrayList<>();
        }
        return commit.getHistory();
    }
    
    @Override
    public Commit findCommitById(String commitId, Commit head) {
        if (head == null || commitId == null) {
            return null;
        }
        
        List<Commit> history = head.getHistory();
        for (Commit commit : history) {
            if (commit.getCommitId().equals(commitId)) {
                return commit;
            }
        }
        return null;
    }
    
    @Override
    public void displayCommitDetails(Commit commit) {
        if (commit == null) {
            System.out.println("No commit to display");
            return;
        }
        
        System.out.println("\n=== Commit Details ===");
        System.out.println(commit);
        System.out.println("\nFiles:");
        for (Map.Entry<String, String> entry : commit.getFileSnapshots().entrySet()) {
            System.out.println("  - " + entry.getKey());
        }
    }
}
