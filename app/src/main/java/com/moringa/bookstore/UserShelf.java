package com.moringa.bookstore;

import java.util.ArrayList;
import java.util.List;
import java.io.Serializable;

import android.sax.Element;
import android.sax.EndElementListener;
import android.sax.EndTextElementListener;

public class UserShelf implements Serializable
{
    private static final long serialVersionUID = 0L;

    private int mBookCount;
    private String mDescription;
    private String mName;

    public UserShelf copy()
    {
        UserShelf shelfCopy = new UserShelf();
        shelfCopy.setBookCount(this.getBookCount());
        shelfCopy.setDescription(this.getDescription());
        shelfCopy.setName(this.getName());
        return shelfCopy;
    }

    public void clear()
    {
        this.setBookCount(0);
        this.setDescription("");
        this.setName("");
    }

    public static UserShelf appendListener(Element parentElement, int depth)
    {
        final UserShelf userShelf = new UserShelf();
        final Element userShelfElement = parentElement.getChild("user_shelf");

        appendCommonListeners(userShelfElement, userShelf, depth);

        return userShelf;
    }

    public static List<UserShelf> appendArrayListener(Element parentElement, int depth)
    {
        final List<UserShelf> userShelfList = new ArrayList<UserShelf>();
        final UserShelf userShelf = new UserShelf();
        final Element userShelfElement = parentElement.getChild("user_shelf");

        appendCommonListeners(userShelfElement, userShelf, depth);

        userShelfElement.setEndElementListener(new EndElementListener()
        {
            @Override
            public void end()
            {
                userShelfList.add(userShelf.copy());
                userShelf.clear();
            }
        });

        return userShelfList;
    }

    private static void appendCommonListeners(final Element userShelfElement, final UserShelf userShelf, int depth)
    {
        userShelfElement.getChild("book_count").setEndTextElementListener(new EndTextElementListener()
        {
            @Override
            public void end(String body)
            {
                userShelf.setBookCount(Integer.parseInt(body));
            }
        });

        userShelfElement.getChild("name").setEndTextElementListener(new EndTextElementListener()
        {
            @Override
            public void end(String body)
            {
                userShelf.setName(body);
            }
        });

        userShelfElement.getChild("description").setEndTextElementListener(new EndTextElementListener()
        {
            @Override
            public void end(String body)
            {
                userShelf.setDescription(body);
            }
        });
    }

    public int getBookCount()
    {
        return mBookCount;
    }

    public void setBookCount(int bookCount)
    {
        mBookCount = bookCount;
    }

    public String getDescription()
    {
        return mDescription;
    }

    public void setDescription(String description)
    {
        mDescription = description;
    }

    public String getName()
    {
        return mName;
    }

    public void setName(String name)
    {
        mName = name;
    }
}