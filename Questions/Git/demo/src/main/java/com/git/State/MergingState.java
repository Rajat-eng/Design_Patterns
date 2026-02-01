package com.git.State;

import com.git.Models.Repository.GitRepository;
import com.git.Models.User.User;

/**
 * Concrete State:
 * Merging state - merge in progress
 */
public class MergingState implements RepositoryState {
    
    @Override
    public void commit(GitRepository repo, String message, User author) {
        System.out.println("Completing merge with commit...");
        repo.commit(message, author);
    }
    
    @Override
    public void merge(GitRepository repo, String branchName, User author) {
        System.out.println("ERROR: Already in merging state. Complete current merge first.");
    }
    
    @Override
    public void checkout(GitRepository repo, String branchName) {
        System.out.println("ERROR: Cannot checkout during merge. Complete or abort merge first.");
    }
    
    @Override
    public String getStateName() {
        return "MERGING";
    }
}
