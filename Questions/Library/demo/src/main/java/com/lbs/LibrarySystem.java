package com.lbs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.lbs.Enum.ItemType;
import com.lbs.Models.ItemCopy;
import com.lbs.Models.LibraryItem;
import com.lbs.Models.Member;
import com.lbs.Strategy.SearchStrategy;
import com.lbs.factory.ItemFactory;


public class LibrarySystem {
    private final static LibrarySystem INSTANCE = new LibrarySystem();
    private final Map<String, LibraryItem> catalog = new HashMap<>();
    private final Map<String, Member> members = new HashMap<>();
    private final Map<String, ItemCopy> copies = new HashMap<>();
    public static LibrarySystem   getInstance() {
        return INSTANCE;
    }

    public List<ItemCopy> addItem(ItemType type, String id, String title, String author, int numberOfCopies) {
        LibraryItem item = ItemFactory.createItem(type, id, title, author);
        catalog.put(id, item);
        for (int i = 0; i < numberOfCopies; i++) {
            ItemCopy copy = new ItemCopy(id + "-C" + (i + 1),item);
            copies.put(copy.getCopyId(), copy);
        }
        return item.getCopies();
    }

    public Member registerMember(String memberId, String name) {
        Member m = new Member(memberId,name);
        members.put(memberId, m);
        return m;
    }

    public void checkoutItem(String memberId, String copyId) {
        Member member = members.get(memberId);
        ItemCopy copy = copies.get(copyId);
        if (member != null && copy != null) {
            copy.checkout(member);
        }
    }

    public void returnItem(String copyId) {
        ItemCopy copy = copies.get(copyId);
        if (copy != null && !copy.isAvailable()) {
            copy.returnItem();
        }
    }

    public void placeHold(String memberId, String itemId) {
        Member member = members.get(memberId);
        LibraryItem item = catalog.get(itemId);
        ItemCopy copy = item.getCopies().stream().filter(c->!c.isAvailable()).findFirst().orElse(null);
        if (member != null && copy != null && !copy.isAvailable()) {
            copy.placeHold(member);
        }
    }

    public List<LibraryItem> search(String query, SearchStrategy strategy) {
        return strategy.search(query, new ArrayList<>(catalog.values()));
    }
}
