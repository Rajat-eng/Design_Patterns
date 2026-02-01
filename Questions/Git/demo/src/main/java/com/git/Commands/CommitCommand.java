package com.git.Commands;

import com.git.Models.Repository.GitRepository;
import com.git.Models.User.User;
import com.git.Models.Commit.Commit;

/**
 * Concrete Command:
 * Command to create a commit
 */
public class CommitCommand implements IGitCommand {
    private final GitRepository repository;
    private final String message;
    private final User author;
    private Commit createdCommit;
    
    public CommitCommand(GitRepository repository, String message, User author) {
        this.repository = repository;
        this.message = message;
        this.author = author;
    }
    
    @Override
    public void execute() {
        createdCommit = repository.commit(message, author);
    }
    
    @Override
    public void undo() {
        if (createdCommit != null) {
            // In a real implementation, we would revert the commit
            System.out.println("Warning: Commit undo not fully implemented - would reset HEAD");
        }
    }
    
    @Override
    public String getCommandName() {
        return "Commit: " + message;
    }
    
    @Override
    public boolean canUndo() {
        return createdCommit != null;
    }
}
