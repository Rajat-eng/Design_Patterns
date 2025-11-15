package com.lbs.Strategy;

import java.util.List;

import com.lbs.Models.LibraryItem;

public class SearchByAuthor implements SearchStrategy {
    @Override
    public List<LibraryItem> search(String query, List<LibraryItem> items) {
        return items.stream()
                .filter(item -> item.getPublisher().toLowerCase().contains(query.toLowerCase()))
                .toList();
    }
}
