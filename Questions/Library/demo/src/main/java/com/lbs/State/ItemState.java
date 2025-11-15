package com.lbs.State;

import com.lbs.Models.ItemCopy;
import com.lbs.Models.Member;

public interface ItemState {
    void borrowItem(ItemCopy item, Member member);
    void returnItem(ItemCopy item);
    void placeHold(ItemCopy item, Member member);
}
