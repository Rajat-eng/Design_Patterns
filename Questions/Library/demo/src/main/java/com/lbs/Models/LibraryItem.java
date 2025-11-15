package com.lbs.Models;

import java.util.ArrayList;
import java.util.List;

import com.lbs.Tracking.Subject;


public abstract class LibraryItem extends Subject{
    private final String id;
    private final String title;
    private final String publisher;
    private final List<ItemCopy> copies;

    public LibraryItem(String id, String title, String publisher) {
        this.id = id;
        this.title = title;
        this.publisher = publisher;
        this.copies = new ArrayList<>();
    }
    public String getId() { return id; }
    public String getTitle() { return title; }
    public String getPublisher() { return publisher; }

    public void addObserver(Member observer) {
        super.addObserver(observer); 
    }
    public void removeObserver(Member observer) {
        super.removeObserver(observer); 
    }
    @Override
    public void notifyObservers() {
        super.notifyObservers(); 
    }


    public List<ItemCopy> getAvailableCopies() {
        return copies.stream()
                .filter(ItemCopy->ItemCopy.isAvailable())
                .toList();
    }

    public List<ItemCopy> getCopies() {
        return copies;
    }

    public void addCopy(ItemCopy copy) {
        copies.add(copy);
    }

    @Override
    public boolean hasObservers(){
        return super.hasObservers();
    }

    public boolean isObserver(Member member) {
        return super.contains(member);
    }
}
