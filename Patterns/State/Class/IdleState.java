package Patterns.State.Class;

import Patterns.State.Interface.VendingMachineState;

public class IdleState implements VendingMachineState {
    @Override
    public void insertCoin(VendingMachine machine) {
        System.out.println("Coin inserted. You can now select a product.");
        machine.setState(new HasCoinState());
    }

    @Override
    public void selectProduct(VendingMachine machine) {
        System.out.println("Insert a coin first.");
    }

    @Override
    public void dispenseProduct(VendingMachine machine) {
        System.out.println("Insert a coin and select a product first.");
    }
}
