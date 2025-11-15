package com.lbs.Strategy;
import java.util.List;

import com.lbs.Models.LibraryItem;

public interface SearchStrategy {
    List<LibraryItem> search(String query, List<LibraryItem> items);
}