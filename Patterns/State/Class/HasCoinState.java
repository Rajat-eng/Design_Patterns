package Patterns.State.Class;

import Patterns.State.Interface.VendingMachineState;

public class HasCoinState implements VendingMachineState{
    @Override
    public void insertCoin(VendingMachine machine) {
        System.out.println("Coin already inserted. Select a product.");
    }

    @Override
    public void selectProduct(VendingMachine machine) {
        System.out.println("Product selected. Dispensing now...");
        machine.setState(new DispensingState());
    }

    @Override
    public void dispenseProduct(VendingMachine machine) {
        System.out.println("Select a product first.");
    }
}
