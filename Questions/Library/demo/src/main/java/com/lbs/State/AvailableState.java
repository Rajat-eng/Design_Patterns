package com.lbs.State;

import com.lbs.Models.ItemCopy;
import com.lbs.Models.Member;
import com.lbs.Services.Transaction;

public class AvailableState implements ItemState {

    @Override
    public void borrowItem(ItemCopy item, Member member) {
        Transaction.getInstance().createLoan(item,member);
        item.setState(new CheckedOutState());
        System.out.println("Item borrowed by member: " + member.getName());
    }
    @Override
    public void returnItem(ItemCopy item) {
        System.out.println("Item is already available.");
    }

    @Override
    public void placeHold(ItemCopy item, Member member) {
        System.out.println("Item is available. No need to place a hold.");
    }

}


