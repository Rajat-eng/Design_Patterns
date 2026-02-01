package com.git.Observers;

import java.util.ArrayList;
import java.util.List;

/**
 * Concrete Observer:
 * Maintains audit trail of all Git operations
 */
public class AuditObserver implements IGitObserver {
    
    private final List<String> auditLog;
    
    public AuditObserver() {
        this.auditLog = new ArrayList<>();
    }
    
    @Override
    public void onCommitCreated(String commitId, String message, String author) {
        auditLog.add("COMMIT_CREATED: " + commitId + " by " + author);
    }
    
    @Override
    public void onBranchCreated(String branchName) {
        auditLog.add("BRANCH_CREATED: " + branchName);
    }
    
    @Override
    public void onBranchDeleted(String branchName) {
        auditLog.add("BRANCH_DELETED: " + branchName);
    }
    
    @Override
    public void onMergeCompleted(String sourceBranch, String targetBranch) {
        auditLog.add("MERGE_COMPLETED: " + sourceBranch + " -> " + targetBranch);
    }
    
    @Override
    public void onStashCreated(String stashId) {
        auditLog.add("STASH_CREATED: " + stashId);
    }
    
    @Override
    public void onError(String errorMessage) {
        auditLog.add("ERROR: " + errorMessage);
    }
    
    public void printEventHistory() {
        System.out.println("\n=== Audit Trail ===");
        for (int i = 0; i < auditLog.size(); i++) {
            System.out.println((i + 1) + ". " + auditLog.get(i));
        }
    }
    
    public void printAuditLog() {
        printEventHistory();
    }
    
    public List<String> getAuditLog() {
        return new ArrayList<>(auditLog);
    }
}
