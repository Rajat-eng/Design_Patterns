package com.git.State;

import com.git.Models.Repository.GitRepository;
import com.git.Models.User.User;

/**
 * State Pattern:
 * Represents different states of the repository
 */
public interface RepositoryState {
    void commit(GitRepository repo, String message, User author);
    void merge(GitRepository repo, String branchName, User author);
    void checkout(GitRepository repo, String branchName);
    String getStateName();
}
