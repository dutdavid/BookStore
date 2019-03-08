package com.moringa.bookstore;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import org.xml.sax.Attributes;

import android.sax.Element;
import android.sax.EndElementListener;
import android.sax.EndTextElementListener;
import android.sax.StartElementListener;

public class Update implements Serializable
{
    private static final long serialVersionUID = 0L;

    private String mUpdateType;
    private String mActionText;
    private String mLink;
    private String mImageUrl;
    private Date mUpdatedAt;
    private Actor mActor = new Actor();
    private Action mAction = new Action();
    private UpdateObject mUpdateObject = new UpdateObject();

    public void clear()
    {
        this.setUpdateType("");
        this.setActionText("");
        this.setLink("");
        this.setImageUrl("");
        this.setUpdatedAt(new Date());
        mActor.clear();
        mAction.clear();
        mUpdateObject.clear();
    }

    public Update copy()
    {
        Update updateCopy = new Update();

        updateCopy.setUpdateType(this.getUpdateType());
        updateCopy.setActionText(this.getActionText());
        updateCopy.setLink(this.getLink());
        updateCopy.setImageUrl(this.getImageUrl());
        updateCopy.setUpdatedAt(this.getUpdatedAt());
        updateCopy.setActor(mActor.copy());
        updateCopy.setAction(mAction.copy());
        updateCopy.setUpdateObject(mUpdateObject.copy());

        return updateCopy;
    }

    public static Update appendListener(Element parentElement, int depth)
    {
        final Update update = new Update();
        final Element updateElement = parentElement.getChild("update");

        appendCommonListeners(updateElement, update, depth);

        return update;
    }

    public static List<Update> appendArrayListener(Element parentElement, int depth)
    {
        final Update update = new Update();
        final List<Update> updateList = new ArrayList<Update>();
        final Element updatesElement = parentElement.getChild("updates");
        final Element updateElement = updatesElement.getChild("update");

        updateElement.setEndElementListener(new EndElementListener()
        {
            @Override
            public void end()
            {
                updateList.add(update.copy());
                update.clear();
            }
        });

        appendCommonListeners(updateElement, update, depth);

        return updateList;
    }

    private static void appendCommonListeners(final Element updateElement, final Update update, int depth)
    {
        updateElement.setStartElementListener(new StartElementListener()
        {
            @Override
            public void start(Attributes attributes)
            {
                update.setUpdateType(attributes.getValue("type"));
            }
        });

        updateElement.getChild("action_text").setEndTextElementListener(new EndTextElementListener()
        {
            @Override
            public void end(String body)
            {
                update.setActionText(body);
            }
        });

        updateElement.getChild("link").setEndTextElementListener(new EndTextElementListener()
        {
            @Override
            public void end(String body)
            {
                update.setLink(body);
            }
        });

        updateElement.getChild("image_url").setEndTextElementListener(new EndTextElementListener()
        {
            @Override
            public void end(String body)
            {
                update.setImageUrl(body);
            }
        });

        updateElement.getChild("updated_at").setEndTextElementListener(new EndTextElementListener()
        {
            @Override
            public void end(String body)
            {
                SimpleDateFormat sdf = new SimpleDateFormat("E, dd MMM yyyy hh:mm:ss Z");
                Date d = new Date();
                try {
                    d = sdf.parse(body);
                } catch (ParseException e) {
                    System.err.println("Failed to parse date: " + body + ": " + e.getMessage());
                }
                update.setUpdatedAt(d);
            }
        });

        update.setActor(Actor.appendListener(updateElement, depth + 1));
        update.setAction(Action.appendListener(updateElement, depth + 1));
        update.setUpdateObject(UpdateObject.appendListener(updateElement, depth + 1));
    }

    public String getLink()
    {
        return mLink;
    }

    public void setLink(String link)
    {
        mLink = link;
    }

    public String getUpdateType()
    {
        return mUpdateType;
    }

    public void setUpdateType(String updateType)
    {
        mUpdateType = updateType;
    }

    public String getActionText()
    {
        return mActionText;
    }

    public void setActionText(String actionText)
    {
        mActionText = actionText;
    }

    public String getImageUrl()
    {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl)
    {
        mImageUrl = imageUrl;
    }

    public Date getUpdatedAt()
    {
        return mUpdatedAt;
    }

    public void setUpdatedAt(Date updatedAt)
    {
        mUpdatedAt = updatedAt;
    }

    public Actor getActor()
    {
        return mActor;
    }

    public void setActor(Actor actor)
    {
        mActor = actor;
    }

    public Action getAction()
    {
        return mAction;
    }

    public void setAction(Action action)
    {
        mAction = action;
    }

    public UpdateObject getUpdateObject()
    {
        return mUpdateObject;
    }

    public void setUpdateObject(UpdateObject updateObject)
    {
        mUpdateObject = updateObject;
    }

    @Override
    public String toString() {
        return "Update[updateType=" + mUpdateType + ", actionText=" + mActionText + ", link=" + mLink + ", imageUrl=" + mImageUrl + ", updatedAt=" + mUpdatedAt + "]";
    }

}