package com.git.Services;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;

import com.git.Models.Commit.Commit;
import com.git.Services.Interfaces.IDAGService;

/**
 * Single Responsibility Principle (SRP):
 * This class handles ONLY DAG-related operations and algorithms.
 */
public class DAGService implements IDAGService {
    
    @Override
    public Commit findCommonAncestor(Commit commit1, Commit commit2) {
        if (commit1 == null || commit2 == null) {
            return null;
        }
        
        Set<Commit> ancestors1 = new HashSet<>(commit1.getHistory());
        
        Queue<Commit> queue = new LinkedList<>();
        Set<Commit> visited = new HashSet<>();
        queue.offer(commit2);
        visited.add(commit2);
        
        while (!queue.isEmpty()) {
            Commit current = queue.poll();
            if (ancestors1.contains(current)) {
                return current;  // Found common ancestor
            }
            
            for (Commit parent : current.getParents()) {
                if (!visited.contains(parent)) {
                    visited.add(parent);
                    queue.offer(parent);
                }
            }
        }
        
        return null;  // No common ancestor
    }
    
    @Override
    public void visualizeDAG(Commit head, Set<Commit> allCommits) {
        if (head == null) {
            System.out.println("No commits to visualize");
            return;
        }
        
        System.out.println("\n=== Git DAG Visualization ===");
        System.out.println("Total commits (nodes): " + allCommits.size());
        System.out.println("\nDAG structure:");
        head.printDAG(new HashSet<>(), "");
    }
    
    @Override
    public boolean hasCircularDependency(Commit commit) {
        Set<Commit> visited = new HashSet<>();
        Set<Commit> recursionStack = new HashSet<>();
        return detectCycle(commit, visited, recursionStack);
    }
    
    private boolean detectCycle(Commit commit, Set<Commit> visited, Set<Commit> recursionStack) {
        if (commit == null) {
            return false;
        }
        
        if (recursionStack.contains(commit)) {
            return true;  // Cycle detected
        }
        
        if (visited.contains(commit)) {
            return false;  // Already checked
        }
        
        visited.add(commit);
        recursionStack.add(commit);
        
        for (Commit parent : commit.getParents()) {
            if (detectCycle(parent, visited, recursionStack)) {
                return true;
            }
        }
        
        recursionStack.remove(commit);
        return false;
    }
}
