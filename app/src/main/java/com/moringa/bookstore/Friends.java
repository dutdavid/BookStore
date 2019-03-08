package com.moringa.bookstore;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;

import android.sax.Element;
import android.sax.StartElementListener;

public class Friends implements Serializable
{
    private static final long serialVersionUID = 0L;

    private int mStart;
    private int mEnd;
    private int mTotal;
    private List<User> mFriends = new ArrayList<User>();

    public int getStart()
    {
        return mStart;
    }
    public void setStart(int start)
    {
        mStart = start;
    }
    public int getEnd()
    {
        return mEnd;
    }
    public void setEnd(int end)
    {
        mEnd = end;
    }
    public int getTotal()
    {
        return mTotal;
    }
    public void setTotal(int total)
    {
        mTotal = total;
    }
    public List<User> getFriends()
    {
        return mFriends;
    }
    public void setFriends(List<User> friends)
    {
        mFriends = friends;
    }

    public static Friends appendListener(final Element parentElement, int depth)
    {
        final Friends friends = new Friends();
        Element friendsElement = parentElement.getChild("friends");
        friendsElement.setStartElementListener(new StartElementListener()
        {
            @Override
            public void start(Attributes attributes)
            {
                friends.setStart(Integer.parseInt(attributes.getValue("start")));
                friends.setEnd(Integer.parseInt(attributes.getValue("end")));
                friends.setTotal(Integer.parseInt(attributes.getValue("total")));
            }
        });

        friends.setFriends(User.appendArrayListener(friendsElement, depth + 1));

        return friends;
    }
}

