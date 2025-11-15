package com.lbs.Models;

import java.util.ArrayList;
import java.util.List;

import com.lbs.Tracking.OrderObserver;

public class Member implements OrderObserver {
    public String membershipId;
    public String name;
    public List<Loan>borrowedBooks;

    public Member(String membershipId,String name) {
        this.membershipId = membershipId;
        this.name=name;
        this.borrowedBooks = new ArrayList<>();
    }

    public String getName() {
        return name;
    }
    @Override
    public void update(){
        System.out.println("Member " + membershipId + " notified of order update.");
    }

    public void addLoan(Loan loan) { borrowedBooks.add(loan); }
    public void removeLoan(Loan loan) { borrowedBooks.remove(loan); }
    public String getId() { return membershipId; }
    public List<Loan> getLoans() { return borrowedBooks; }

}
