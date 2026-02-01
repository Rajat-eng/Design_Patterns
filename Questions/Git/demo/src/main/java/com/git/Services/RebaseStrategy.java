package com.git.Services;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.git.Models.Commit.Commit;
import com.git.Models.User.User;
import com.git.Services.Interfaces.IMergeStrategy;

/**
 * Rebase Strategy:
 * Instead of creating a merge commit, reapplies commits from source branch
 * on top of target branch, creating a linear history.
 * 
 * Strategy Pattern: Alternative merge algorithm
 * 
 * Rebase advantages:
 * - Linear history (no merge commits)
 * - Cleaner git log
 * - Each commit can be reviewed individually
 * 
 * Rebase workflow:
 * 1. Find common ancestor
 * 2. Collect commits from source after ancestor
 * 3. Apply each commit on top of target
 * 4. Result is linear chain of commits
 */
public class RebaseStrategy implements IMergeStrategy {
    
    private List<Commit> rebasedCommits;
    
    public RebaseStrategy() {
        this.rebasedCommits = new ArrayList<>();
    }
    
    @Override
    public Map<String, String> merge(Commit sourceCommit, Commit targetCommit) {
        if (sourceCommit == null || targetCommit == null) {
            return new HashMap<>();
        }
        
        // Find common ancestor
        Commit commonAncestor = findCommonAncestor(sourceCommit, targetCommit);
        
        // Collect commits from source that need to be replayed
        List<Commit> commitsToReplay = collectCommitsSince(sourceCommit, commonAncestor);
        
        // Start with target's files as base
        Map<String, String> result = new HashMap<>(targetCommit.getFileSnapshots());
        
        // Replay each commit from source on top of target
        System.out.println("Rebasing " + commitsToReplay.size() + " commits...");
        for (Commit commit : commitsToReplay) {
            System.out.println("  Replaying: " + commit.getCommitId() + " - " + commit.getMessage());
            
            // Apply this commit's changes
            Map<String, String> commitChanges = commit.getFileSnapshots();
            result.putAll(commitChanges);
            
            // Store rebased commit for tracking
            rebasedCommits.add(commit);
        }
        
        System.out.println("Rebase completed successfully!");
        return result;
    }
    
    /**
     * Find common ancestor between two commits using BFS
     */
    private Commit findCommonAncestor(Commit commit1, Commit commit2) {
        if (commit1 == null || commit2 == null) {
            return null;
        }
        
        // Get all ancestors of commit1
        List<Commit> ancestors1 = getAllAncestors(commit1);
        
        // Find first common ancestor in commit2's history
        List<Commit> ancestors2 = getAllAncestors(commit2);
        
        for (Commit ancestor : ancestors2) {
            if (ancestors1.contains(ancestor)) {
                return ancestor;
            }
        }
        
        return null;
    }
    
    /**
     * Get all ancestor commits
     */
    private List<Commit> getAllAncestors(Commit commit) {
        List<Commit> ancestors = new ArrayList<>();
        collectAncestors(commit, ancestors);
        return ancestors;
    }
    
    private void collectAncestors(Commit commit, List<Commit> ancestors) {
        if (commit == null || ancestors.contains(commit)) {
            return;
        }
        
        ancestors.add(commit);
        
        for (Commit parent : commit.getParents()) {
            collectAncestors(parent, ancestors);
        }
    }
    
    /**
     * Collect commits from 'from' back to 'until' (exclusive)
     * Returns commits in chronological order (oldest first)
     */
    private List<Commit> collectCommitsSince(Commit from, Commit until) {
        List<Commit> commits = new ArrayList<>();
        collectCommitsRecursive(from, until, commits);
        
        // Reverse to get chronological order (oldest first)
        List<Commit> chronological = new ArrayList<>();
        for (int i = commits.size() - 1; i >= 0; i--) {
            chronological.add(commits.get(i));
        }
        
        return chronological;
    }
    
    private void collectCommitsRecursive(Commit current, Commit until, List<Commit> commits) {
        if (current == null || current.equals(until) || commits.contains(current)) {
            return;
        }
        
        commits.add(current);
        
        // Continue with parents
        for (Commit parent : current.getParents()) {
            collectCommitsRecursive(parent, until, commits);
        }
    }
    
    @Override
    public String getStrategyName() {
        return "Rebase (linear history, no merge commit)";
    }
    
    /**
     * Get list of commits that were rebased
     */
    public List<Commit> getRebasedCommits() {
        return new ArrayList<>(rebasedCommits);
    }
    
    /**
     * Clear rebased commits tracking
     */
    public void clearRebasedCommits() {
        rebasedCommits.clear();
    }
}
