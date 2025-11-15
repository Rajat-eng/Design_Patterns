package com.lbs.State;

import com.lbs.Models.ItemCopy;
import com.lbs.Models.Member;
import com.lbs.Services.Transaction;


public class OnHoldState implements ItemState {
    @Override
    public void borrowItem(ItemCopy item, Member member) {
        if(item.getItem().isObserver(member)){
            Transaction.getInstance().createLoan(item, member);
            item.getItem().removeObserver(member);
            item.setState(new CheckedOutState());
             System.out.println("Hold fulfilled. " + item.getCopyId() + " checked out by " + member.getName());
        }else {
            System.out.println("Item is on hold for another member.");
        }
    }
    @Override
    public void returnItem(ItemCopy item) {
        System.out.println("Item is already on hold.");
    }
    @Override
    public void placeHold(ItemCopy item, Member member) {
        System.out.println("Item is already on hold.");
    }
}
