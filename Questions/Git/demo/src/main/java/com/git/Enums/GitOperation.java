package com.git.Enums;

/**
 * Enum for Git operation types
 */
public enum GitOperation {
    COMMIT("Commit"),
    MERGE("Merge"),
    BRANCH_CREATE("Branch Create"),
    BRANCH_DELETE("Branch Delete"),
    CHECKOUT("Checkout"),
    STASH("Stash"),
    STASH_APPLY("Stash Apply"),
    STASH_POP("Stash Pop"),
    STASH_DROP("Stash Drop"),
    DELETE_COMMIT("Delete Commit"),
    PRUNE("Prune");
    
    private final String displayName;
    
    GitOperation(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
}
