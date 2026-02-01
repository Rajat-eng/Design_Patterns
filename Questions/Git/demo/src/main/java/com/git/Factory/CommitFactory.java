package com.git.Factory;

import com.git.Models.Commit.Commit;
import com.git.Models.User.User;
import java.util.List;

/**
 * Concrete Factory Pattern Implementation:
 * Creates different types of commits based on requirements
 * 
 * Benefits:
 * - Encapsulates object creation logic
 * - Single point for commit instantiation
 * - Easy to modify creation logic without changing clients
 * - Supports different commit types (initial, regular, merge)
 */
public class CommitFactory implements ICommitFactory {
    
    @Override
    public Commit createInitialCommit(String message, User author) {
        System.out.println("[Factory] Creating initial commit");
        return new Commit(message, author);
    }
    
    @Override
    public Commit createRegularCommit(String message, User author, Commit parent) {
        System.out.println("[Factory] Creating regular commit");
        return new Commit(message, author, parent);
    }
    
    @Override
    public Commit createMergeCommit(String message, User author, List<Commit> parents) {
        System.out.println("[Factory] Creating merge commit with " + parents.size() + " parents");
        return new Commit(message, author, parents);
    }
    
    @Override
    public Commit createCommitWithId(String commitId, String message, User author, Commit parent) {
        System.out.println("[Factory] Creating commit with custom ID: " + commitId);
        // For now, create normal commit (could extend Commit class to support custom IDs)
        return new Commit(message, author, parent);
    }
}
