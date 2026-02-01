package com.git.Factory;

import com.git.Models.Commit.Commit;
import com.git.Models.User.User;
import java.util.List;

/**
 * Factory Pattern:
 * Abstract factory interface for creating different types of commits
 * 
 * Allows creating commits without exposing the creation logic to client
 * and refers to newly created object using a common interface
 */
public interface ICommitFactory {
    
    /**
     * Create an initial commit (no parent)
     */
    Commit createInitialCommit(String message, User author);
    
    /**
     * Create a regular commit with single parent
     */
    Commit createRegularCommit(String message, User author, Commit parent);
    
    /**
     * Create a merge commit with multiple parents
     */
    Commit createMergeCommit(String message, User author, List<Commit> parents);
    
    /**
     * Create a commit with custom ID (for testing/import)
     */
    Commit createCommitWithId(String commitId, String message, User author, Commit parent);
}
