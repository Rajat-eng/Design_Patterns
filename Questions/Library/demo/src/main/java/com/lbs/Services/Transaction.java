package com.lbs.Services;

import java.util.HashMap;
import java.util.Map;

import com.lbs.Models.ItemCopy;
import com.lbs.Models.Loan;
import com.lbs.Models.Member;
import com.lbs.State.AvailableState;


public class Transaction {
    private static final Transaction INSTANCE = new Transaction();
    Map<String,Loan> transactions = new HashMap<>();

    private Transaction() {
    }

    public static Transaction getInstance() {
        return INSTANCE;
    }

    public void createLoan(ItemCopy itemCopy,Member member) {
        Loan loan = new Loan(itemCopy, member);
        member.addLoan(loan);
        transactions.put(itemCopy.getCopyId(), loan);
    }

    public void returnLoan(ItemCopy itemCopy) {
        Loan loan = transactions.get(itemCopy.getCopyId());
        if (loan != null) {
            itemCopy.setState(new AvailableState());
            loan.setReturnDate(java.time.LocalDate.now().toString());
            Member member = loan.member;
            member.removeLoan(loan);
            transactions.remove(itemCopy.getCopyId());
        }
    }
}
