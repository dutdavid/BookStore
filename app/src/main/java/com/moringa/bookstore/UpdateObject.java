package com.moringa.bookstore;

import java.io.Serializable;
import android.sax.Element;

public class UpdateObject implements Serializable
{
    private static final long serialVersionUID = 0L;

    private books mbooks = new books();

    public void clear()
    {
        mbooks.clear();
    }

    public UpdateObject copy()
    {
        UpdateObject updateObjectCopy = new UpdateObject();

        updateObjectCopy.setbooks(mbooks.copy());

        return updateObjectCopy;
    }

    public static UpdateObject appendListener(Element parentElement, int depth)
    {
        final Element updateObjectElement = parentElement.getChild("object");
        final UpdateObject updateObject = new UpdateObject();

        updateObject.setbooks(books.appendListener(updateObjectElement, depth + 1));

        return updateObject;
    }

    public void setbooks(books books)
    {
        mbooks = books;
    }

    public books getbooks()
    {
        return mbooks;
    }
}
