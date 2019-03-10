package com.moringa.david.bookstore.search;


import com.moringa.david.bookstore.model.Book;
import com.moringa.david.bookstore.util.Toastable;

interface SearchView extends Toastable {


    void showBookResult(Book book);

}
