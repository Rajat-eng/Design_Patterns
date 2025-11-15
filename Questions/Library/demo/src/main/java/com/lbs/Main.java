package com.lbs;


import java.util.List;

import com.lbs.Enum.ItemType;
import com.lbs.Models.ItemCopy;
import com.lbs.Models.Member;
import com.lbs.Strategy.SearchByAuthor;
import com.lbs.Strategy.SearchByTitle;

public class Main {
    public static void main(String[] args) {
        LibrarySystem library = LibrarySystem.getInstance();
        List<ItemCopy> hobbitBook = library.addItem(ItemType.BOOK, "B001", "The Hobbit", "J.R.R. Tolkien", 2);
        List<ItemCopy> duneBook = library.addItem(ItemType.BOOK, "B002", "Dune", "Frank Herbert", 1);
        List<ItemCopy> natGeoMag = library.addItem(ItemType.MAGAZINE, "M001", "National Geographic", "NatGeo Society", 3);

        Member Alice = library.registerMember("MEM01", "Alice");
        Member Bob = library.registerMember("MEM02", "Bob");
        Member Charlie = library.registerMember("MEM03", "Charlie");

        // --- Scenario 1: Searching (Strategy Pattern) ---
        System.out.println("\n=== Scenario 1: Searching for Items ===");

        System.out.println("Searching for title 'Dune':");

        library.search("Dune", new SearchByTitle())
                .forEach(item -> System.out.println("Found: " + item.getTitle()));
        System.out.println("\nSearching for author 'Tolkien':");
        library.search("Tolkien", new SearchByAuthor())
                .forEach(item -> System.out.println("Found: " + item.getTitle()));

        // --- Scenario 2: Checkout and Return (State Pattern) ---
        System.out.println("\n=== Scenario 2: Checkout and Return ===");
        library.checkoutItem(Alice.getId(), hobbitBook.get(0).getCopyId()); // Alice checks out The Hobbit
        library.checkoutItem(Bob.getId(), duneBook.get(0).getCopyId()); // Bob checks out Dune copy 1
        // library.checkoutItem(Charlie.getId(), duneBook.get(0).getCopyId()); // Charlie tries to check out Dune copy  
        library.returnItem(hobbitBook.get(0).getCopyId()); // Alice returns The Hobbit


        // --- Scenario 3: Placing Holds (Observer Pattern) ---
        System.out.println("\n=== Scenario 3: Placing Holds ===");
        library.placeHold(Charlie.getId(), "B002");
        library.returnItem(duneBook.get(0).getCopyId()); // Bob returns Dune copy 1, should notify Charlie

        library.checkoutItem(Alice.getId(),duneBook.get(0).getCopyId()); // Alice tries to check out Dune copy but Charlie has hold

        library.checkoutItem(Charlie.getId(),duneBook.get(0).getCopyId()); // Charlie checks out Dune copy
    }
} 