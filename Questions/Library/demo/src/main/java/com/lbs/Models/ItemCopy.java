package com.lbs.Models;

import com.lbs.State.AvailableState;
import com.lbs.State.ItemState;

public class ItemCopy {
    private final String copyId;
    private final LibraryItem item;
    private ItemState state;

    public ItemCopy(String copyId,LibraryItem item) {
        this.copyId = copyId;
        this.item = item;
        this.state = new AvailableState();
        item.addCopy(this);

    }

    public LibraryItem getItem() {
        return item;
    }

    public String getCopyId() {
        return copyId;
    }

    public boolean isAvailable() {
        return state instanceof AvailableState;
    }

    public void setState(ItemState state) {
        this.state = state;
    }
    public void checkout(Member member) {
        // currently available
        state.borrowItem(this, member);
    }

    public void returnItem() {
        state.returnItem(this);
    }

    public void placeHold(Member member) {
        // all copies are checked out
        state.placeHold(this, member);
    }
}
