package com.git.Commands;

import java.util.Stack;

/**
 * Command Pattern:
 * Invoker that executes commands and maintains history
 * Supports undo operations
 */
public class CommandInvoker {
    private final Stack<IGitCommand> commandHistory;
    private final Stack<IGitCommand> undoneCommands;
    
    public CommandInvoker() {
        this.commandHistory = new Stack<>();
        this.undoneCommands = new Stack<>();
    }
    
    public void executeCommand(IGitCommand command) {
        command.execute();
        commandHistory.push(command);
        undoneCommands.clear(); // Clear redo history when new command is executed
    }
    
    public void undo() {
        if (commandHistory.isEmpty()) {
            System.out.println("Nothing to undo");
            return;
        }
        
        IGitCommand command = commandHistory.pop();
        if (command.canUndo()) {
            command.undo();
            undoneCommands.push(command);
            System.out.println("Undone: " + command.getCommandName());
        } else {
            System.out.println("Cannot undo: " + command.getCommandName());
        }
    }
    
    public void redo() {
        if (undoneCommands.isEmpty()) {
            System.out.println("Nothing to redo");
            return;
        }
        
        IGitCommand command = undoneCommands.pop();
        command.execute();
        commandHistory.push(command);
        System.out.println("Redone: " + command.getCommandName());
    }
    
    public void showHistory() {
        if (commandHistory.isEmpty()) {
            System.out.println("No command history");
            return;
        }
        
        System.out.println("\n=== Command History ===");
        for (int i = 0; i < commandHistory.size(); i++) {
            System.out.println((i + 1) + ". " + commandHistory.get(i).getCommandName());
        }
    }
    
    public int getHistorySize() {
        return commandHistory.size();
    }
}
