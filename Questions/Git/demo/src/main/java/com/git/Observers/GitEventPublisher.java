package com.git.Observers;

import java.util.ArrayList;
import java.util.List;

/**
 * Observer Pattern:
 * Subject that notifies observers of Git events
 * Follows Open/Closed Principle - can add new observers without modifying this class
 */
public class GitEventPublisher {
    private final List<IGitObserver> observers;
    
    public GitEventPublisher() {
        this.observers = new ArrayList<>();
    }
    
    public void subscribe(IGitObserver observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
            System.out.println("Observer subscribed: " + observer.getClass().getSimpleName());
        }
    }
    
    public void unsubscribe(IGitObserver observer) {
        if (observers.remove(observer)) {
            System.out.println("Observer unsubscribed: " + observer.getClass().getSimpleName());
        }
    }
    
    public void notifyCommitCreated(String commitId, String message, String author) {
        for (IGitObserver observer : observers) {
            observer.onCommitCreated(commitId, message, author);
        }
    }
    
    public void notifyBranchCreated(String branchName) {
        for (IGitObserver observer : observers) {
            observer.onBranchCreated(branchName);
        }
    }
    
    public void notifyBranchDeleted(String branchName) {
        for (IGitObserver observer : observers) {
            observer.onBranchDeleted(branchName);
        }
    }
    
    public void notifyMergeCompleted(String sourceBranch, String targetBranch) {
        for (IGitObserver observer : observers) {
            observer.onMergeCompleted(sourceBranch, targetBranch);
        }
    }
    
    public void notifyStashCreated(String stashId) {
        for (IGitObserver observer : observers) {
            observer.onStashCreated(stashId);
        }
    }
    
    public void notifyError(String errorMessage) {
        for (IGitObserver observer : observers) {
            observer.onError(errorMessage);
        }
    }
}
