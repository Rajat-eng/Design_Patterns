package com.git.Services.Interfaces;

import java.util.Map;

import com.git.Models.Commit.Commit;

/**
 * Strategy Pattern & Open/Closed Principle (OCP):
 * Different merge strategies can be implemented without modifying existing code.
 */
public interface IMergeStrategy {
    Map<String, String> merge(Commit sourceCommit, Commit targetCommit);
    String getStrategyName();
}
