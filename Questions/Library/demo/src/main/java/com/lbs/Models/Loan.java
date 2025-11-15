package com.lbs.Models;

public class Loan {
    public ItemCopy item;
    public Member member;
    public String loanDate;
    public String returnDate;
    
    public Loan(ItemCopy item, Member member) {
        this.item = item;
        this.member = member;
        this.loanDate = java.time.LocalDate.now().toString();
    }

    public void setReturnDate(String returnDate) {
        this.returnDate = returnDate;
    }   

}
