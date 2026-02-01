package com.git.Services;

import java.util.Map;

import com.git.Models.Branch.Branch;
import com.git.Models.Commit.Commit;
import com.git.Services.Interfaces.IBranchService;

/**
 * Single Responsibility Principle (SRP):
 * This class is responsible ONLY for branch management.
 */
public class BranchService implements IBranchService {
    
    @Override
    public Branch createBranch(String name, Commit initialCommit) {
        return new Branch(name, initialCommit);
    }
    
    @Override
    public void deleteBranch(String name, Map<String, Branch> branches) {
        if (branches.containsKey(name)) {
            branches.remove(name);
            System.out.println("Deleted branch: " + name);
        } else {
            System.out.println("Branch " + name + " does not exist");
        }
    }
    
    @Override
    public void listBranches(Map<String, Branch> branches, Branch currentBranch) {
        System.out.println("\n=== Branches ===");
        for (Branch branch : branches.values()) {
            String indicator = (branch == currentBranch) ? "* " : "  ";
            System.out.println(indicator + branch);
        }
    }
    
    @Override
    public Branch getBranch(String name, Map<String, Branch> branches) {
        return branches.get(name);
    }
}
