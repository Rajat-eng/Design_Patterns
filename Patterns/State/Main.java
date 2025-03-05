package Patterns.State;

import Patterns.State.Class.VendingMachine;

public class Main {
    public static void main(String[] args) {
         VendingMachine machine = new VendingMachine();

        // User actions
        machine.insertCoin();   // Insert a coin
        machine.selectProduct(); // Select a product
        machine.dispenseProduct(); // Dispense the product

        // Trying to dispense again without inserting a coin
        machine.dispenseProduct(); // Should not work
    }
}
