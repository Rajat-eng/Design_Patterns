package com.git.Services.Interfaces;

import java.util.List;

import com.git.Models.Commit.Commit;
import com.git.Models.User.User;

/**
 * Interface Segregation Principle (ISP):
 * Clients should not be forced to depend on interfaces they don't use.
 * This interface only contains commit-related operations.
 */
public interface ICommitService {
    Commit createCommit(String message, User author, Commit parent);
    Commit createMergeCommit(String message, User author, List<Commit> parents);
    List<Commit> getCommitHistory(Commit commit);
    Commit findCommitById(String commitId, Commit head);
    void displayCommitDetails(Commit commit);
}
