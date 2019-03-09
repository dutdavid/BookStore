package com.moringa.bookstore.models;

import java.io.Serializable;

import org.xml.sax.Attributes;

import android.sax.Element;
import android.sax.EndTextElementListener;
import android.sax.StartElementListener;

public class Action implements Serializable
{
    private static final long serialVersionUID = 0L;

    private String mActionType;
    private String mShelfName;
    private int mRating;

    public void clear()
    {
        this.setActionType("");
        this.setShelfName("");
        this.setRating(0);
    }

    public Action copy()
    {
        Action actionCopy = new Action();

        actionCopy.setActionType(this.getActionType());
        actionCopy.setShelfName(this.getShelfName());
        actionCopy.setRating(this.getRating());

        return actionCopy;
    }

    public static Action appendListener(Element parentElement, int depth)
    {
        final Action action = new Action();
        final Element actionElement = parentElement.getChild("action");

        actionElement.setStartElementListener(new StartElementListener()
        {
            @Override
            public void start(Attributes attributes)
            {
                action.setActionType(attributes.getValue("type"));
            }
        });

        actionElement.getChild("rating").setEndTextElementListener(new EndTextElementListener()
        {
            @Override
            public void end(String body)
            {
                if (body != null && body != "")
                {
                    action.setRating(Integer.parseInt(body));
                }
            }
        });

        actionElement.getChild("shelf").setStartElementListener(new StartElementListener()
        {
            @Override
            public void start(Attributes attributes)
            {
                action.setShelfName(attributes.getValue("name"));
            }
        });

        return action;
    }

    public String getActionType()
    {
        return mActionType;
    }
    public void setActionType(String actionType)
    {
        mActionType = actionType;
    }
    public String getShelfName()
    {
        return mShelfName;
    }
    public void setShelfName(String shelfName)
    {
        mShelfName = shelfName;
    }

    public int getRating()
    {
        return mRating;
    }

    public void setRating(int rating)
    {
        mRating = rating;
    }
}
