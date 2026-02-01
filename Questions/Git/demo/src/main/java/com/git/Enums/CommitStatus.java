package com.git.Enums;

/**
 * Enum for commit status in DAG
 */
public enum CommitStatus {
    REACHABLE("Reachable"),
    UNREACHABLE("Unreachable"),
    DANGLING("Dangling"),
    HEAD("HEAD"),
    MERGED("Merged");
    
    private final String displayName;
    
    CommitStatus(String displayName) {
        this.displayName = displayName;
    }
    
    public String getDisplayName() {
        return displayName;
    }
}
