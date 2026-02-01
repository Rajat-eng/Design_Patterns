package com.git.Factory;

/**
 * Concrete Abstract Factory:
 * Creates families of related Git objects (commits and branches)
 * 
 * Abstract Factory Pattern Benefits:
 * - Ensures related objects are created together
 * - Easy to swap entire product families
 * - Promotes consistency among products
 */
public class GitObjectFactory implements IGitObjectFactory {
    
    private final ICommitFactory commitFactory;
    private final IBranchFactory branchFactory;
    
    public GitObjectFactory() {
        this.commitFactory = new CommitFactory();
        this.branchFactory = new BranchFactory();
    }
    
    @Override
    public ICommitFactory getCommitFactory() {
        return commitFactory;
    }
    
    @Override
    public IBranchFactory getBranchFactory() {
        return branchFactory;
    }
}
