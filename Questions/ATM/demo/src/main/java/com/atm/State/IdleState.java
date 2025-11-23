package com.atm.State;

import com.atm.Enums.OperationType;
import com.atm.Models.Card;
import com.atm.Services.ATMSystem;

public class IdleState implements ATMState {
    @Override
    public void insertCard(ATMSystem system,String cardNumber){
        System.out.println("\nCard has been inserted.");
        Card card = system.getCard(cardNumber);

        if (card == null) {
            ejectCard(system);
        } else {
            system.setCurrentCard(card);
            system.setState(new HasCardState());
        }
    }
    @Override
    public void enterPin(ATMSystem system,String pin){
        System.out.println("Error: Please insert a card first.");
    }

    @Override
    public void selectOperation(ATMSystem system, OperationType opType, int... args){
        System.out.println("Error: Please insert a card first.");
    }

    @Override
    public void ejectCard(ATMSystem system){
       System.out.println("Card has been ejected. Thank you for using our ATM.");
        system.setCurrentCard(null);
        system.setState(new IdleState());
    }
}
