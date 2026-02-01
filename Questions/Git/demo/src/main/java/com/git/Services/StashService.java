package com.git.Services;

import com.git.Models.Stash.Stash;
import com.git.Models.User.User;
import com.git.Services.Interfaces.IStashService;
import java.util.List;
import java.util.Map;

/**
 * Single Responsibility Principle (SRP):
 * This class handles ONLY stash operations
 */
public class StashService implements IStashService {
    
    @Override
    public Stash createStash(String message, User author, String branchName, Map<String, String> files) {
        if (files == null || files.isEmpty()) {
            System.out.println("No changes to stash");
            return null;
        }
        
        Stash stash = new Stash(message, author, branchName, files);
        System.out.println("Created stash: " + stash.getStashId());
        return stash;
    }
    
    @Override
    public Stash applyStash(Stash stash, Map<String, String> workingDirectory) {
        if (stash == null) {
            System.out.println("No stash to apply");
            return null;
        }
        
        // Apply stashed files to working directory
        workingDirectory.putAll(stash.getSavedFiles());
        System.out.println("Applied stash: " + stash.getStashId());
        return stash;
    }
    
    @Override
    public Stash popStash(List<Stash> stashes, Map<String, String> workingDirectory) {
        if (stashes.isEmpty()) {
            System.out.println("No stash entries found");
            return null;
        }
        
        // Get the most recent stash (last in list)
        Stash stash = stashes.remove(stashes.size() - 1);
        applyStash(stash, workingDirectory);
        System.out.println("Popped stash: " + stash.getStashId());
        return stash;
    }
    
    @Override
    public boolean dropStash(String stashId, List<Stash> stashes) {
        boolean removed = stashes.removeIf(s -> s.getStashId().equals(stashId));
        if (removed) {
            System.out.println("Dropped stash: " + stashId);
        } else {
            System.out.println("Stash " + stashId + " not found");
        }
        return removed;
    }
    
    @Override
    public void listStashes(List<Stash> stashes) {
        if (stashes.isEmpty()) {
            System.out.println("No stash entries found");
            return;
        }
        
        System.out.println("\n=== Stash List ===");
        for (int i = stashes.size() - 1; i >= 0; i--) {
            System.out.println("stash@{" + (stashes.size() - 1 - i) + "}: " + stashes.get(i));
        }
    }
    
    @Override
    public Stash getStash(String stashId, List<Stash> stashes) {
        return stashes.stream()
            .filter(s -> s.getStashId().equals(stashId))
            .findFirst()
            .orElse(null);
    }
}
