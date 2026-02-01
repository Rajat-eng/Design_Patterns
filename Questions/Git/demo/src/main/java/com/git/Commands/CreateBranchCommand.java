package com.git.Commands;

import com.git.Models.Repository.GitRepository;
import com.git.Models.Branch.Branch;

/**
 * Concrete Command:
 * Command to create a branch
 */
public class CreateBranchCommand implements IGitCommand {
    private final GitRepository repository;
    private final String branchName;
    private boolean executed;
    
    public CreateBranchCommand(GitRepository repository, String branchName) {
        this.repository = repository;
        this.branchName = branchName;
        this.executed = false;
    }
    
    @Override
    public void execute() {
        repository.createBranch(branchName);
        executed = true;
    }
    
    @Override
    public void undo() {
        if (executed) {
            // Note: This will fail for protected branches (main/master)
            // In a real system, would need to store the creator to pass here
            System.out.println("Undoing branch creation: " + branchName);
            // Cannot undo if it's the current branch or protected
        }
    }
    
    @Override
    public String getCommandName() {
        return "Create Branch: " + branchName;
    }
    
    @Override
    public boolean canUndo() {
        return executed;
    }
}
