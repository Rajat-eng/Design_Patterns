package com.git.Services;

import java.util.HashMap;
import java.util.Map;

import com.git.Models.Commit.Commit;
import com.git.Services.Interfaces.IMergeStrategy;

/**
 * Strategy Pattern & Open/Closed Principle (OCP):
 * Simple merge strategy - source overwrites target on conflicts.
 */
public class SimpleMergeStrategy implements IMergeStrategy {
    
    @Override
    public Map<String, String> merge(Commit sourceCommit, Commit targetCommit) {
        Map<String, String> result = new HashMap<>();
        
        // Start with target files
        if (targetCommit != null) {
            result.putAll(targetCommit.getFileSnapshots());
        }
        
        // Overwrite with source files
        if (sourceCommit != null) {
            result.putAll(sourceCommit.getFileSnapshots());
        }
        
        return result;
    }
    
    @Override
    public String getStrategyName() {
        return "Simple Merge (source overwrites conflicts)";
    }
}
