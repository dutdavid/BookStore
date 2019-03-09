package com.moringa.bookstore.models;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;

import android.sax.Element;
import android.sax.StartElementListener;

public class Following implements Serializable
{
    private static final long serialVersionUID = 0L;

    private int mStart;
    private int mEnd;
    private int mTotal;
    private List<User> mFollowing = new ArrayList<User>();

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
    public List<User> getFollowing()
    {
        return mFollowing;
    }
    public void setFollowing(List<User> following)
    {
        mFollowing = following;
    }

    public static Following appendListener(final Element parentElement, int depth)
    {
        final Following following = new Following();
        Element followingElement = parentElement.getChild("following");
        followingElement.setStartElementListener(new StartElementListener()
        {
            @Override
            public void start(Attributes attributes)
            {
                following.setStart(Integer.parseInt(attributes.getValue("start")));
                following.setEnd(Integer.parseInt(attributes.getValue("end")));
                following.setTotal(Integer.parseInt(attributes.getValue("total")));
            }
        });

        following.setFollowing(User.appendArrayListener(followingElement, depth + 1));

        return following;
    }
}