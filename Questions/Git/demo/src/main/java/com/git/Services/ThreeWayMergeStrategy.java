package com.git.Services;

import java.util.HashMap;
import java.util.Map;

import com.git.Models.Commit.Commit;
import com.git.Services.Interfaces.IMergeStrategy;

/**
 * Strategy Pattern:
 * Three-way merge strategy using common ancestor.
 */
public class ThreeWayMergeStrategy implements IMergeStrategy {
    
    private Commit commonAncestor;
    
    public ThreeWayMergeStrategy(Commit commonAncestor) {
        this.commonAncestor = commonAncestor;
    }
    
    @Override
    public Map<String, String> merge(Commit sourceCommit, Commit targetCommit) {
        Map<String, String> result = new HashMap<>();
        Map<String, String> ancestorFiles = commonAncestor != null ? 
            commonAncestor.getFileSnapshots() : new HashMap<>();
        Map<String, String> sourceFiles = sourceCommit.getFileSnapshots();
        Map<String, String> targetFiles = targetCommit.getFileSnapshots();
        
        // Collect all unique filenames
        java.util.Set<String> allFiles = new java.util.HashSet<>();
        allFiles.addAll(ancestorFiles.keySet());
        allFiles.addAll(sourceFiles.keySet());
        allFiles.addAll(targetFiles.keySet());
        
        for (String file : allFiles) {
            String ancestorContent = ancestorFiles.get(file);
            String sourceContent = sourceFiles.get(file);
            String targetContent = targetFiles.get(file);
            
            // Simple three-way logic
            if (sourceContent != null && targetContent != null) {
                if (sourceContent.equals(targetContent)) {
                    result.put(file, sourceContent);
                } else if (ancestorContent != null && ancestorContent.equals(targetContent)) {
                    // Target unchanged, use source
                    result.put(file, sourceContent);
                } else if (ancestorContent != null && ancestorContent.equals(sourceContent)) {
                    // Source unchanged, use target
                    result.put(file, targetContent);
                } else {
                    // Conflict: prefer source
                    result.put(file, sourceContent + "\n<<<CONFLICT>>>\n" + targetContent);
                }
            } else if (sourceContent != null) {
                result.put(file, sourceContent);
            } else if (targetContent != null) {
                result.put(file, targetContent);
            }
        }
        
        return result;
    }
    
    @Override
    public String getStrategyName() {
        return "Three-Way Merge (uses common ancestor)";
    }
}
