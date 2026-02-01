package com.git.Observers;

/**
 * Observer Pattern:
 * Base interface for all observers
 */
public interface IGitObserver {
    void onCommitCreated(String commitId, String message, String author);
    void onBranchCreated(String branchName);
    void onBranchDeleted(String branchName);
    void onMergeCompleted(String sourceBranch, String targetBranch);
    void onStashCreated(String stashId);
    void onError(String errorMessage);
}
