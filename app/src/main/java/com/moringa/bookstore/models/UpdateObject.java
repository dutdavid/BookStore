package com.moringa.bookstore.models;

import java.io.Serializable;
import android.sax.Element;

public class UpdateObject implements Serializable
{
    private static final long serialVersionUID = 0L;

    private Book mBook = new Book();

    public void clear()
    {
        mBook.clear();
    }

    public UpdateObject copy()
    {
        UpdateObject updateObjectCopy = new UpdateObject();

        updateObjectCopy.setBook(mBook.copy());

        return updateObjectCopy;
    }

    public static UpdateObject appendListener(Element parentElement, int depth)
    {
        final Element updateObjectElement = parentElement.getChild("object");
        final UpdateObject updateObject = new UpdateObject();

        updateObject.setBook(Book.appendListener(updateObjectElement, depth + 1));

        return updateObject;
    }

    public void setBook(Book book)
    {
        mBook = book;
    }

    public Book getBook()
    {
        return mBook;
    }
}