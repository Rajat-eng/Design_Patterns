package com.lbs.State;

import com.lbs.Models.ItemCopy;
import com.lbs.Models.Member;
import com.lbs.Services.Transaction;

public class CheckedOutState implements ItemState {

    @Override
    public void borrowItem(ItemCopy item, Member member) {
        System.out.println("Item is already checked out.");
    }
    @Override
    public void returnItem(ItemCopy item) {
        Transaction.getInstance().returnLoan(item);
        if(item.getItem().hasObservers()) {
            item.getItem().notifyObservers(); 
            item.setState(new OnHoldState());
            System.out.println("Item returned " + item.getCopyId() + " and is now on hold for next member.");    
        }else{
            item.setState(new AvailableState());
            System.out.println("Item returned " + item.getCopyId() + " and is now available.");
        }  
    }

    
    @Override
    public void placeHold(ItemCopy item, Member member) {
        item.getItem().addObserver(member); // add member to waitlist
        System.out.println("Item is checked out. Hold placed by member: " + member.getName());
    }
}
