package com.atm.State;

import com.atm.Enums.OperationType;
import com.atm.Services.ATMSystem;


public class HasCardState implements ATMState {
@Override
    public void insertCard(ATMSystem system,String cardNumber){
         System.out.println("Error: A card is already inserted. Cannot insert another card.");
    }

    public void enterPin(ATMSystem system,String pin){
         System.out.println("Authenticating PIN...");
        boolean isAuthenticated = system.authenticate(pin);

        if (isAuthenticated) {
            System.out.println("Authentication successful.");
            system.setState(new AuthenticatedState());
        } else {
            System.out.println("Authentication failed: Incorrect PIN.");
            ejectCard(system);
        }
    }

    @Override
    public void selectOperation(ATMSystem system, OperationType opType, int... args){
        System.out.println("Error: Please enter your PIN first to select an operation.");
    }

    @Override
    public void ejectCard(ATMSystem system){
       System.out.println("Card has been ejected. Thank you for using our ATM.");
        system.setCurrentCard(null);
        system.setState(new IdleState());
    }
}
