package com.git.State;

import com.git.Models.Repository.GitRepository;
import com.git.Models.User.User;
import com.git.Enums.RepositoryStatus;

/**
 * State Pattern - Context:
 * Manages the current state of the repository and delegates operations
 * 
 * State Pattern Benefits:
 * - Eliminates large conditional statements
 * - Each state encapsulates its own behavior
 * - Easy to add new states
 * - State-specific behavior is localized
 */
public class RepositoryStateContext {
    
    private RepositoryState currentState;
    private final GitRepository repository;
    
    // Available states
    private final RepositoryState cleanState;
    private final RepositoryState dirtyState;
    private final RepositoryState mergingState;
    private final RepositoryState rebasingState;
    
    public RepositoryStateContext(GitRepository repository) {
        this.repository = repository;
        
        // Initialize all possible states
        this.cleanState = new CleanState();
        this.dirtyState = new DirtyState();
        this.mergingState = new MergingState();
        this.rebasingState = new RebasingState();
        
        // Start in clean state
        this.currentState = cleanState;
    }
    
    // State transitions
    public void transitionToClean() {
        System.out.println("[State] Transitioning to: CLEAN");
        this.currentState = cleanState;
    }
    
    public void transitionToDirty() {
        System.out.println("[State] Transitioning to: DIRTY");
        this.currentState = dirtyState;
    }
    
    public void transitionToMerging() {
        System.out.println("[State] Transitioning to: MERGING");
        this.currentState = mergingState;
    }
    
    public void transitionToRebasing() {
        System.out.println("[State] Transitioning to: REBASING");
        this.currentState = rebasingState;
    }
    
    // Delegate operations to current state
    public void commit(String message, User author) {
        currentState.commit(repository, message, author);
    }
    
    public void merge(String branchName, User author) {
        currentState.merge(repository, branchName, author);
    }
    
    public void checkout(String branchName) {
        currentState.checkout(repository, branchName);
    }
    
    // Get current state information
    public String getCurrentStateName() {
        return currentState.getStateName();
    }
    
    public RepositoryStatus getCurrentStatus() {
        String stateName = currentState.getStateName();
        switch (stateName) {
            case "CLEAN":
                return RepositoryStatus.CLEAN;
            case "DIRTY":
                return RepositoryStatus.DIRTY;
            case "MERGING":
                return RepositoryStatus.MERGING;
            case "REBASING":
                return RepositoryStatus.REBASING;
            default:
                return RepositoryStatus.CLEAN;
        }
    }
    
    public boolean isClean() {
        return currentState == cleanState;
    }
    
    public boolean isDirty() {
        return currentState == dirtyState;
    }
    
    public boolean isMerging() {
        return currentState == mergingState;
    }
    
    public boolean isRebasing() {
        return currentState == rebasingState;
    }
}
