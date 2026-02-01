package com.git.Factory;

/**
 * Abstract Factory Pattern:
 * Provides an interface for creating families of related objects
 * (Commits and Branches) without specifying their concrete classes
 * 
 * This is a higher-level factory that produces other factories
 */
public interface IGitObjectFactory {
    
    /**
     * Get commit factory
     */
    ICommitFactory getCommitFactory();
    
    /**
     * Get branch factory
     */
    IBranchFactory getBranchFactory();
}
