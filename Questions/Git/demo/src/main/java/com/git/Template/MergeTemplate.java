package com.git.Template;

import com.git.Models.Commit.Commit;
import com.git.Models.User.User;
import java.util.Map;

/**
 * Template Method Pattern:
 * Defines the skeleton of merge algorithm
 * Subclasses can override specific steps without changing the overall structure
 */
public abstract class MergeTemplate {
    
    // Template method - defines the algorithm structure
    public final boolean merge(Commit source, Commit target, User merger) {
        System.out.println("\n=== Starting Merge Process ===");
        
        // Step 1: Pre-merge checks
        if (!preMergeCheck(source, target)) {
            System.out.println("Pre-merge checks failed");
            return false;
        }
        
        // Step 2: Find common ancestor
        Commit ancestor = findCommonAncestor(source, target);
        if (ancestor == null) {
            System.out.println("Warning: No common ancestor found");
        }
        
        // Step 3: Detect conflicts
        Map<String, String> conflicts = detectConflicts(source, target, ancestor);
        
        // Step 4: Resolve conflicts (hook method)
        if (!conflicts.isEmpty()) {
            boolean resolved = resolveConflicts(conflicts);
            if (!resolved) {
                System.out.println("Merge failed: Unresolved conflicts");
                return false;
            }
        }
        
        // Step 5: Merge files
        Map<String, String> mergedFiles = mergeFiles(source, target, ancestor);
        
        // Step 6: Create merge commit
        Commit mergeCommit = createMergeCommit(source, target, merger, mergedFiles);
        
        // Step 7: Post-merge cleanup
        postMergeCleanup(mergeCommit);
        
        System.out.println("=== Merge Completed Successfully ===");
        return true;
    }
    
    // Concrete methods with default implementation
    protected boolean preMergeCheck(Commit source, Commit target) {
        return source != null && target != null;
    }
    
    protected void postMergeCleanup(Commit mergeCommit) {
        System.out.println("Merge commit created: " + mergeCommit.getCommitId());
    }
    
    // Abstract methods - must be implemented by subclasses
    protected abstract Commit findCommonAncestor(Commit source, Commit target);
    protected abstract Map<String, String> detectConflicts(Commit source, Commit target, Commit ancestor);
    protected abstract boolean resolveConflicts(Map<String, String> conflicts);
    protected abstract Map<String, String> mergeFiles(Commit source, Commit target, Commit ancestor);
    protected abstract Commit createMergeCommit(Commit source, Commit target, User merger, Map<String, String> files);
}
