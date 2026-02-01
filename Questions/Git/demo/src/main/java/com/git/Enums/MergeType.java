package com.git.Enums;

/**
 * Enum for merge strategies
 */
public enum MergeType {
    SIMPLE("Simple Merge"),
    THREE_WAY("Three-Way Merge"),
    FAST_FORWARD("Fast-Forward"),
    RECURSIVE("Recursive Merge"),
    OURS("Ours Strategy"),
    THEIRS("Theirs Strategy"),
    REBASE("Rebase - Linear History");
    
    private final String displayName;
    
    MergeType(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
}
