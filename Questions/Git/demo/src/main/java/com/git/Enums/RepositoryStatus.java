package com.git.Enums;

/**
 * Enum representing different states of a Git repository
 * Used with State Pattern
 */
public enum RepositoryStatus {
    CLEAN("No uncommitted changes"),
    DIRTY("Has uncommitted changes"),
    MERGING("In merge process"),
    REBASING("In rebase process"),
    CONFLICTED("Has merge conflicts");
    
    private final String description;
    
    RepositoryStatus(String description) {
        this.description = description;
    }
    
    public String getDescription() {
        return description;
    }
    
    @Override
    public String toString() {
        return name() + " - " + description;
    }
}
