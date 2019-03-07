package com.moringa.bookstore;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;

public class BooksArrayAdapter extends ArrayAdapter {
    private Context mContext;
    private String[] mBooks;
    private String[] mAuthors;

    public BooksArrayAdapter(Context mContext, int resource, String[] mBooks, String[] mAuthors) {
        super(mContext, resource);
        this.mContext = mContext;
        this.mBooks = mBooks;
        this.mAuthors = mAuthors;
    }
    @Override
    public Object getItem(int position) {
        String books = mBooks[position];
        String authors = mAuthors[position];
        return String.format("%s \nWritten By: %s", books, authors);
    }

    @Override
    public int getCount() {
        return mBooks.length;
    }
}