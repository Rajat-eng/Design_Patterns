package com.git.Services;

import com.git.Models.Commit.Commit;
import com.git.Models.Branch.Branch;
import com.git.Services.Interfaces.ICommitDeletionService;
import java.util.*;

/**
 * Single Responsibility Principle (SRP):
 * This class handles ONLY commit deletion and cleanup operations
 */
public class CommitDeletionService implements ICommitDeletionService {
    
    @Override
    public boolean deleteCommit(String commitId, Set<Commit> allCommits, Map<String, Branch> branches) {
        // Find the commit
        Commit toDelete = allCommits.stream()
            .filter(c -> c.getCommitId().equals(commitId))
            .findFirst()
            .orElse(null);
        
        if (toDelete == null) {
            System.out.println("Commit " + commitId + " not found");
            return false;
        }
        
        // Check if commit is reachable from any branch
        if (isReachable(toDelete, branches)) {
            System.out.println("Cannot delete commit " + commitId + " - it is reachable from a branch");
            System.out.println("Use 'prune' to clean up unreachable commits only");
            return false;
        }
        
        // Remove unreachable commit
        allCommits.remove(toDelete);
        System.out.println("Deleted unreachable commit: " + commitId);
        return true;
    }
    
    @Override
    public int pruneUnreachableCommits(Set<Commit> allCommits, Map<String, Branch> branches) {
        Set<Commit> unreachable = findUnreachableCommits(allCommits, branches);
        
        if (unreachable.isEmpty()) {
            System.out.println("No unreachable commits to prune");
            return 0;
        }
        
        System.out.println("\n=== Pruning Unreachable Commits ===");
        for (Commit commit : unreachable) {
            System.out.println("Pruning: " + commit.getCommitId() + " - " + commit.getMessage());
        }
        
        allCommits.removeAll(unreachable);
        System.out.println("Pruned " + unreachable.size() + " unreachable commits");
        return unreachable.size();
    }
    
    @Override
    public Set<Commit> findUnreachableCommits(Set<Commit> allCommits, Map<String, Branch> branches) {
        // Find all reachable commits
        Set<Commit> reachable = new HashSet<>();
        
        for (Branch branch : branches.values()) {
            if (branch.getHead() != null) {
                collectReachableCommits(branch.getHead(), reachable);
            }
        }
        
        // Find unreachable commits
        Set<Commit> unreachable = new HashSet<>(allCommits);
        unreachable.removeAll(reachable);
        
        return unreachable;
    }
    
    @Override
    public boolean isReachable(Commit commit, Map<String, Branch> branches) {
        for (Branch branch : branches.values()) {
            if (branch.getHead() != null && isReachableFrom(commit, branch.getHead())) {
                return true;
            }
        }
        return false;
    }
    
    private void collectReachableCommits(Commit commit, Set<Commit> reachable) {
        if (commit == null || reachable.contains(commit)) {
            return;
        }
        
        reachable.add(commit);
        
        // Recursively collect all parents
        for (Commit parent : commit.getParents()) {
            collectReachableCommits(parent, reachable);
        }
    }
    
    private boolean isReachableFrom(Commit target, Commit start) {
        Set<Commit> visited = new HashSet<>();
        Queue<Commit> queue = new LinkedList<>();
        
        queue.offer(start);
        visited.add(start);
        
        while (!queue.isEmpty()) {
            Commit current = queue.poll();
            
            if (current.equals(target)) {
                return true;
            }
            
            for (Commit parent : current.getParents()) {
                if (!visited.contains(parent)) {
                    visited.add(parent);
                    queue.offer(parent);
                }
            }
        }
        
        return false;
    }
}
