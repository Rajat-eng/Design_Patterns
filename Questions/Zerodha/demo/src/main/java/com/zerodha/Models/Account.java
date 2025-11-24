package com.zerodha.Models;


public class Account {
  private double balance;

    public Account(double balance) {
        this.balance = balance;
    }

    public synchronized boolean reserve(double amount) {
        if (amount <= 0) return true;
        if (balance >= amount) {
            balance -= amount;
            return true;
        }
        return false;
    }

    public synchronized void deposit(double amount) {
        balance += amount;
    }

    public synchronized void credit(double amount) { deposit(amount); }

    public synchronized double getBalance() {
        return balance;
    }

    @Override
    public String toString() {
        return String.format("Account{balance=%.2f}", balance);
    }
}
