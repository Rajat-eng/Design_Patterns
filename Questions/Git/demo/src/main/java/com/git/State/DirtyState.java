package com.git.State;

import com.git.Models.Repository.GitRepository;
import com.git.Models.User.User;

/**
 * Concrete State:
 * Dirty state - has uncommitted changes
 */
public class DirtyState implements RepositoryState {
    
    @Override
    public void commit(GitRepository repo, String message, User author) {
        System.out.println("Committing changes...");
        repo.commit(message, author);
    }
    
    @Override
    public void merge(GitRepository repo, String branchName, User author) {
        System.out.println("ERROR: Cannot merge with uncommitted changes. Commit or stash first.");
    }
    
    @Override
    public void checkout(GitRepository repo, String branchName) {
        System.out.println("ERROR: Cannot checkout with uncommitted changes. Commit or stash first.");
    }
    
    @Override
    public String getStateName() {
        return "DIRTY";
    }
}
