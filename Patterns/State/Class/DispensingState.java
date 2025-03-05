package Patterns.State.Class;

import Patterns.State.Interface.VendingMachineState;

public class DispensingState implements VendingMachineState {
    @Override
    public void insertCoin(VendingMachine machine) {
        System.out.println("Please wait... Dispensing the product.");
    }

    @Override
    public void selectProduct(VendingMachine machine) {
        System.out.println("Already processing a request.");
    }

    @Override
    public void dispenseProduct(VendingMachine machine) {
        System.out.println("Product dispensed. Returning to idle state.");
        machine.setState(new IdleState());
    }
}
