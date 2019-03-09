package com.moringa.bookstore.models;

import java.io.Serializable;

import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;

import android.sax.Element;
import android.sax.StartElementListener;

public class Followers implements Serializable
{
    private static final long serialVersionUID = 0L;

    private int mStart;
    private int mEnd;
    private int mTotal;
    private List<User> mFollowers = new ArrayList<User>();

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
    public List<User> getFollowers()
    {
        return mFollowers;
    }
    public void setFollowers(List<User> followers)
    {
        mFollowers = followers;
    }

    public static Followers appendListener(final Element parentElement, int depth)
    {
        final Followers followers = new Followers();
        Element followersElement = parentElement.getChild("followers");
        followersElement.setStartElementListener(new StartElementListener()
        {
            @Override
            public void start(Attributes attributes)
            {
                followers.setStart(Integer.parseInt(attributes.getValue("start")));
                followers.setEnd(Integer.parseInt(attributes.getValue("end")));
                followers.setTotal(Integer.parseInt(attributes.getValue("total")));
            }
        });

        followers.setFollowers(User.appendArrayListener(followersElement, depth + 1));

        return followers;
    }
}
