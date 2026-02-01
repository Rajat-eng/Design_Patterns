package com.git.Models.Branch;

import com.git.Models.Commit.Commit;

public class Branch {
    private String name;
    private Commit head;  // Points to the latest commit in this branch

    public Branch(String name, Commit initialCommit) {
        this.name = name;
        this.head = initialCommit;
    }

    public String getName() {
        return name;
    }

    public Commit getHead() {
        return head;
    }

    public void setHead(Commit commit) {
        this.head = commit;
    }

    // Update branch to point to a new commit
    public void advanceHead(Commit newCommit) {
        this.head = newCommit;
    }

    @Override
    public String toString() {
        return name + " -> " + (head != null ? head.getCommitId() : "null");
    }
}
