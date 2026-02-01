package com.git.State;

import com.git.Models.Repository.GitRepository;
import com.git.Models.User.User;

/**
 * Concrete State: Rebasing State
 * 
 * Repository is in the middle of a rebase operation
 */
public class RebasingState implements RepositoryState {
    
    @Override
    public void commit(GitRepository repo, String message, User author) {
        System.out.println("[State: REBASING] Cannot commit during rebase. Complete or abort rebase first.");
    }
    
    @Override
    public void merge(GitRepository repo, String branchName, User author) {
        System.out.println("[State: REBASING] Cannot merge during rebase. Complete or abort rebase first.");
    }
    
    @Override
    public void checkout(GitRepository repo, String branchName) {
        System.out.println("[State: REBASING] Cannot checkout during rebase. Complete or abort rebase first.");
    }
    
    @Override
    public String getStateName() {
        return "REBASING";
    }
}
