package com.git.Commands;

import com.git.Models.Repository.GitRepository;
import com.git.Models.User.User;

/**
 * Concrete Command:
 * Command to merge branches
 */
public class MergeCommand implements IGitCommand {
    private final GitRepository repository;
    private final String branchName;
    private final User author;
    private boolean executed;
    
    public MergeCommand(GitRepository repository, String branchName, User author) {
        this.repository = repository;
        this.branchName = branchName;
        this.author = author;
        this.executed = false;
    }
    
    @Override
    public void execute() {
        repository.merge(branchName, author);
        executed = true;
    }
    
    @Override
    public void undo() {
        if (executed) {
            System.out.println("Warning: Merge undo not fully implemented - would reset to pre-merge state");
        }
    }
    
    @Override
    public String getCommandName() {
        return "Merge: " + branchName;
    }
    
    @Override
    public boolean canUndo() {
        return false; // Merges are typically not undoable without reset
    }
}
