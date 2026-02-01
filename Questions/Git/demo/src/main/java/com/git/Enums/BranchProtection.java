package com.git.Enums;

/**
 * Enum for branch protection levels
 */
public enum BranchProtection {
    NONE("None"),
    PROTECTED("Protected"),
    LOCKED("Locked");
    
    private final String displayName;
    
    BranchProtection(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
}
