package Patterns.State.Interface;

import Patterns.State.Class.VendingMachine;

public interface VendingMachineState {
    void insertCoin(VendingMachine machine);
    void selectProduct(VendingMachine machine);
    void dispenseProduct(VendingMachine machine);
}
