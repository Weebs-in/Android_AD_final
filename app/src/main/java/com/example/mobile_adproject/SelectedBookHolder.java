package com.example.mobile_adproject;

import com.example.mobile_adproject.models.Book;

public class SelectedBookHolder {
    private static SelectedBookHolder instance;
    private Book selectedBook;

    private SelectedBookHolder() {
        // Private constructor to prevent instantiation from outside
    }

    public static SelectedBookHolder getInstance() {
        if (instance == null) {
            instance = new SelectedBookHolder();
        }
        return instance;
    }

    public void setSelectedBook(Book book) {
        selectedBook = book;
    }

    public Book getSelectedBook() {
        return selectedBook;
    }
}
