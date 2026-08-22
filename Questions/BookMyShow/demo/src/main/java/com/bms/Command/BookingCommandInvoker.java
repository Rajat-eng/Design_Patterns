package com.bms.Command;

public class BookingCommandInvoker {
    private final BookingCommand command;

    public BookingCommandInvoker(BookingCommand command) {
        this.command = command;
    }

    public void execute() {
        if (command == null || !command.isValid()) {
            System.out.println("Booking command is invalid.");
            return;
        }
        command.execute();
    }
}
