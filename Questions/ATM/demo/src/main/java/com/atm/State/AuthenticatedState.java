package com.atm.State;
import com.atm.Enums.OperationType;
import com.atm.Services.ATMSystem;

public class AuthenticatedState implements ATMState {
    @Override
    public void insertCard(ATMSystem system,String cardNumber){
        System.err.println("Pin is already enetered");
    }

    public void enterPin(ATMSystem system,String pin){
        System.err.println("Pin is already enetered");
    }

    @Override
    public void selectOperation(ATMSystem system, OperationType opType, int... args){
        switch (opType) {
            case CHECK_BALANCE:
                system.checkBalance();
                break;

            case WITHDRAW_CASH:
                int withdrawAmount = args[0];
                double balance = system.getBankService().getBalance(system.getCurrentCard());

                if(balance < withdrawAmount){
                    System.out.println("Insufficient balance");
                    break;
                }
                system.withdrawCash(withdrawAmount);
                break;

            case DEPOSIT_CASH:
                int amountToDeposit = args[0];
                System.out.println("Processing deposit for ₹" + amountToDeposit);
                system.depositCash(amountToDeposit);
                break;

            default:
                throw new AssertionError();
        }
    }

    @Override
    public void ejectCard(ATMSystem system){
        System.out.println("Ending session. Card has been ejected. Thank you for using our ATM.");
        system.setCurrentCard(null);
        system.setState(new IdleState());
    }
}
