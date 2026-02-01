package com.git.Factory;

import com.git.Models.Branch.Branch;
import com.git.Models.Commit.Commit;

/**
 * Concrete Factory for creating different types of branches
 * 
 * Factory Pattern Benefits:
 * - Centralized branch creation logic
 * - Enforces naming conventions
 * - Easy to add new branch types
 */
public class BranchFactory implements IBranchFactory {
    
    @Override
    public Branch createBranch(String name, Commit headCommit) {
        System.out.println("[Factory] Creating branch: " + name);
        return new Branch(name, headCommit);
    }
    
    @Override
    public Branch createFeatureBranch(String featureName, Commit headCommit) {
        String branchName = "feature/" + featureName;
        System.out.println("[Factory] Creating feature branch: " + branchName);
        return new Branch(branchName, headCommit);
    }
    
    @Override
    public Branch createBugfixBranch(String bugfixName, Commit headCommit) {
        String branchName = "bugfix/" + bugfixName;
        System.out.println("[Factory] Creating bugfix branch: " + branchName);
        return new Branch(branchName, headCommit);
    }
    
    @Override
    public Branch createReleaseBranch(String version, Commit headCommit) {
        String branchName = "release/" + version;
        System.out.println("[Factory] Creating release branch: " + branchName);
        return new Branch(branchName, headCommit);
    }
}
