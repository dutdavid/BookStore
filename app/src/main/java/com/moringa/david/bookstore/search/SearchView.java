package com.moringa.david.bookstore.search;


import com.moringa.david.bookstore.model.Book;
import com.moringa.david.bookstore.util.Toastable;

interface SearchView extends Toastable {

    /**
     * Adds a book relevant to the search query
     * @param book book object parsed from the response XML
     */
    void showBookResult(Book book);

}
