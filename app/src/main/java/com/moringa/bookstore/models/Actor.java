package com.moringa.bookstore.models;

import java.io.Serializable;

import android.sax.Element;
import android.sax.EndTextElementListener;

public class Actor implements Serializable
{
    private static final long serialVersionUID = 0L;

    private int mId;
    private String mName;
    private String mImageUrl;
    private String mLink;

    public void clear()
    {
        this.setId(0);
        this.setName("");
        this.setImageUrl("");
        this.setLink("");
    }

    public Actor copy()
    {
        Actor actorCopy = new Actor();

        actorCopy.setId(this.getId());
        actorCopy.setName(this.getName());
        actorCopy.setImageUrl(this.getImageUrl());
        actorCopy.setLink(this.getLink());

        return actorCopy;
    }

    public static Actor appendListener(Element parentElement, int depth)
    {
        final Actor actor = new Actor();
        final Element actorElement = parentElement.getChild("actor");

        actorElement.getChild("id").setEndTextElementListener(new EndTextElementListener()
        {
            @Override
            public void end(String body)
            {
                if (body != null && body != "")
                {
                    actor.setId(Integer.parseInt(body));
                }
            }
        });

        actorElement.getChild("name").setEndTextElementListener(new EndTextElementListener()
        {
            @Override
            public void end(String body)
            {
                actor.setName(body);
            }
        });

        actorElement.getChild("image_url").setEndTextElementListener(new EndTextElementListener()
        {
            @Override
            public void end(String body)
            {
                actor.setImageUrl(body);
            }
        });

        actorElement.getChild("link").setEndTextElementListener(new EndTextElementListener()
        {
            @Override
            public void end(String body)
            {
                actor.setLink(body);
            }
        });

        return actor;
    }

    public int getId()
    {
        return mId;
    }
    public void setId(int id)
    {
        mId = id;
    }
    public String getName()
    {
        return mName;
    }
    public void setName(String name)
    {
        mName = name;
    }
    public String getImageUrl()
    {
        return mImageUrl;
    }
    public void setImageUrl(String imageUrl)
    {
        mImageUrl = imageUrl;
    }
    public String getLink()
    {
        return mLink;
    }
    public void setLink(String link)
    {
        mLink = link;
    }
}
