package com.git.Observers;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Concrete Observer:
 * Logs all Git events to console
 */
public class LoggingObserver implements IGitObserver {
    
    private String getTimestamp() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss"));
    }
    
    @Override
    public void onCommitCreated(String commitId, String message, String author) {
        System.out.println("[" + getTimestamp() + "] LOG: Commit created - " + 
            commitId + " by " + author + ": " + message);
    }
    
    @Override
    public void onBranchCreated(String branchName) {
        System.out.println("[" + getTimestamp() + "] LOG: Branch created - " + branchName);
    }
    
    @Override
    public void onBranchDeleted(String branchName) {
        System.out.println("[" + getTimestamp() + "] LOG: Branch deleted - " + branchName);
    }
    
    @Override
    public void onMergeCompleted(String sourceBranch, String targetBranch) {
        System.out.println("[" + getTimestamp() + "] LOG: Merge completed - " + 
            sourceBranch + " -> " + targetBranch);
    }
    
    @Override
    public void onStashCreated(String stashId) {
        System.out.println("[" + getTimestamp() + "] LOG: Stash created - " + stashId);
    }
    
    @Override
    public void onError(String errorMessage) {
        System.out.println("[" + getTimestamp() + "] ERROR: " + errorMessage);
    }
}
