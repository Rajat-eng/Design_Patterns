package com.lbs.Strategy;

import java.util.List;

import com.lbs.Models.LibraryItem;

public class SearchByTitle implements SearchStrategy {
    @Override
    public List<LibraryItem> search(String query, List<LibraryItem> items) {
        return items.stream()
                .filter(item -> item.getTitle().toLowerCase().contains(query.toLowerCase()))
                .toList();
    }
}
