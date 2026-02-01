package com.git.Services.Interfaces;

import java.util.Map;

import com.git.Models.Branch.Branch;
import com.git.Models.Commit.Commit;

/**
 * Interface Segregation Principle (ISP):
 * Separate interface for branch operations.
 */
public interface IBranchService {
    Branch createBranch(String name, Commit initialCommit);
    void deleteBranch(String name, Map<String, Branch> branches);
    void listBranches(Map<String, Branch> branches, Branch currentBranch);
    Branch getBranch(String name, Map<String, Branch> branches);
}
