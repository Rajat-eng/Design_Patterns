package com.git.Services;

import java.util.List;

import com.git.Models.Commit.Commit;
import com.git.Models.User.User;

/**
 * Factory Pattern:
 * Encapsulates commit creation logic.
 * Makes it easier to add validation, logging, or different commit types.
 */
public class CommitFactory {
    
    public static Commit createRegularCommit(String message, User author, Commit parent) {
        validateCommitData(message, author);
        return new Commit(message, author, parent);
    }
    
    public static Commit createMergeCommit(String message, User author, List<Commit> parents) {
        validateCommitData(message, author);
        
        if (parents == null || parents.size() < 2) {
            throw new IllegalArgumentException("Merge commit requires at least 2 parents");
        }
        
        return new Commit(message, author, parents);
    }
    
    public static Commit createInitialCommit(User author) {
        return new Commit("Initial commit", author, (Commit) null);
    }
    
    private static void validateCommitData(String message, User author) {
        if (message == null || message.trim().isEmpty()) {
            throw new IllegalArgumentException("Commit message cannot be empty");
        }
        
        if (author == null) {
            throw new IllegalArgumentException("Commit author cannot be null");
        }
    }
}
