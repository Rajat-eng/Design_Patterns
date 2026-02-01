package com.git.Services.Interfaces;

import java.util.Set;

import com.git.Models.Commit.Commit;

/**
 * Interface Segregation Principle (ISP):
 * Dedicated interface for DAG operations.
 */
public interface IDAGService {
    Commit findCommonAncestor(Commit commit1, Commit commit2);
    void visualizeDAG(Commit head, Set<Commit> allCommits);
    boolean hasCircularDependency(Commit commit);
}
