package com.atm.Services;

import java.util.concurrent.atomic.AtomicLong;

import com.atm.Chain.DispenseChain;
import com.atm.Chain.Note1000Dispenser;
import com.atm.Chain.Note200Dispenser;
import com.atm.Chain.Note500Dispenser;
import com.atm.Enums.OperationType;
import com.atm.Models.Card;
import com.atm.State.ATMState;
import com.atm.State.IdleState;


public class ATMSystem {
    private static ATMSystem instance;
    private BankService bankService;
    private CashDispenser cashDispenser;
    private ATMState currentState;
    private static final AtomicLong transactionCounter = new AtomicLong(0);
    private Card currentCard;

    private ATMSystem() {
        this.currentState = new IdleState();
        this.bankService = new BankService();

        // Setup the dispenser chain
        DispenseChain c1 = new Note1000Dispenser(1000); // 10 x $1000 notes
        DispenseChain c2 = new Note500Dispenser(20); // 20 x $500 notes
        DispenseChain c3 = new Note200Dispenser(30); // 30 x $100 notes
        c1.setNextChain(c2);
        c2.setNextChain(c3);
        this.cashDispenser = new CashDispenser(c1);
    }

    public static ATMSystem getInstance(){
        if(instance==null){
            synchronized (ATMSystem.class) {
                if(instance==null){
                    instance = new ATMSystem();
                }
            }
        }
        return instance;
    }

    public void setState(ATMState newState){
        this.currentState = newState;
    }

    public void setCurrentCard(Card card) { this.currentCard = card; }

    public void insertCard(String cardNumber) {
        currentState.insertCard(this, cardNumber);
    }

    public void enterPin(String pin) {
        currentState.enterPin(this, pin);
    }

    public void selectOperation(OperationType op, int... args) { currentState.selectOperation(this, op, args); }

    public Card getCard(String cardNumber) {
        return bankService.getCard(cardNumber);
    }

    public boolean authenticate(String pin) {
        return bankService.authenticate(currentCard, pin);
    }

    public void checkBalance() {
        double balance = bankService.getBalance(currentCard);
        System.out.printf("Your current account balance is: $%.2f%n", balance);
    }

    public void withdrawCash(int amount) {
        if (!cashDispenser.canDispenseCash(amount)) {
            throw new IllegalStateException("Insufficient cash available in the ATM.");
        }

        bankService.withdrawMoney(currentCard, amount);

        try {
            cashDispenser.dispenseCash(amount);
        } catch (Exception e) {
            bankService.depositMoney(currentCard, amount); // Deposit back if dispensing fails
        }
    }

    public void depositCash(int amount) {
        bankService.depositMoney(currentCard, amount);
    }

    public Card getCurrentCard() {
        return currentCard;
    }

    public BankService getBankService() {
        return bankService;
    }
}
