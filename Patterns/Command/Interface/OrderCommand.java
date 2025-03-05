package Patterns.Command.Interface;

public interface OrderCommand {
    void execute();  // Place order
    void undo();     // Cancel order
}
