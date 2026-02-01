package com.git.State;

import com.git.Models.Repository.GitRepository;
import com.git.Models.User.User;

/**
 * Concrete State:
 * Clean state - no uncommitted changes
 */
public class CleanState implements RepositoryState {
    
    @Override
    public void commit(GitRepository repo, String message, User author) {
        System.out.println("Nothing to commit - working directory is clean");
    }
    
    @Override
    public void merge(GitRepository repo, String branchName, User author) {
        System.out.println("Merge allowed - working directory is clean");
        repo.merge(branchName, author);
    }
    
    @Override
    public void checkout(GitRepository repo, String branchName) {
        System.out.println("Checkout allowed - working directory is clean");
        repo.checkout(branchName);
    }
    
    @Override
    public String getStateName() {
        return "CLEAN";
    }
}
