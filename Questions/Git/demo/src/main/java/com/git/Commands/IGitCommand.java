package com.git.Commands;

/**
 * Command Pattern:
 * Base interface for all Git commands
 * Supports undo/redo operations
 */
public interface IGitCommand {
    void execute();
    void undo();
    String getCommandName();
    boolean canUndo();
}
