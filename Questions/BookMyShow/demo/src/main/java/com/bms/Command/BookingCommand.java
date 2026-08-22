package com.bms.Command;

public interface BookingCommand {
    void execute();
    default boolean isValid() { return true; }
}
