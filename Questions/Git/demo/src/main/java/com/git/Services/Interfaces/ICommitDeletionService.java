package com.git.Services.Interfaces;

import com.git.Models.Commit.Commit;
import com.git.Models.Branch.Branch;
import java.util.Set;
import java.util.Map;

/**
 * Interface Segregation Principle (ISP):
 * Dedicated interface for commit deletion and cleanup operations
 */
public interface ICommitDeletionService {
    boolean deleteCommit(String commitId, Set<Commit> allCommits, Map<String, Branch> branches);
    int pruneUnreachableCommits(Set<Commit> allCommits, Map<String, Branch> branches);
    Set<Commit> findUnreachableCommits(Set<Commit> allCommits, Map<String, Branch> branches);
    boolean isReachable(Commit commit, Map<String, Branch> branches);
}
