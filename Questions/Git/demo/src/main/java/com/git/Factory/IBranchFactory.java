package com.git.Factory;

import com.git.Models.Branch.Branch;
import com.git.Models.Commit.Commit;

/**
 * Factory Pattern for Branch creation
 * 
 * Encapsulates the logic for creating different types of branches
 */
public interface IBranchFactory {
    
    /**
     * Create a standard branch
     */
    Branch createBranch(String name, Commit headCommit);
    
    /**
     * Create a feature branch (with feature/ prefix)
     */
    Branch createFeatureBranch(String featureName, Commit headCommit);
    
    /**
     * Create a bugfix branch (with bugfix/ prefix)
     */
    Branch createBugfixBranch(String bugfixName, Commit headCommit);
    
    /**
     * Create a release branch (with release/ prefix)
     */
    Branch createReleaseBranch(String version, Commit headCommit);
}
