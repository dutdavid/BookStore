package com.moringa.bookstore.models;

import java.io.Serializable;

import android.sax.Element;
import android.sax.EndTextElementListener;

public class BestBook implements Serializable
{
    private static final long serialVersionUID = 0L;

    private int mId;
    private String mTitle;
    private Author mAuthor = new Author();
    private String mImageUrl;
    private String mSmallImageUrl;

    public void clear()
    {
        this.setId(0);
        this.setTitle("");
        this.setImageUrl("");
        this.setSmallImageUrl("");
        this.mAuthor.clear();
    }

    public BestBook copy()
    {
        BestBook bestBookCopy = new BestBook();

        bestBookCopy.setId(this.getId());
        bestBookCopy.setTitle(this.getTitle());
        bestBookCopy.setAuthor(this.getAuthor().copy());
        bestBookCopy.setImageUrl(this.getImageUrl());
        bestBookCopy.setSmallImageUrl(this.getSmallImageUrl());

        return bestBookCopy;
    }

    public static BestBook appendListener(final Element parentElement, int depth)
    {
        final BestBook bestBook = new BestBook();

        Element bestBookElement = parentElement.getChild("best_book");

        bestBook.setAuthor(Author.appendListener(bestBookElement, depth + 1));
        appendCommonListeners(bestBookElement, bestBook);

        return bestBook;
    }


    private static void appendCommonListeners(final Element bestBookElement, final BestBook bestBook)
    {
        bestBookElement.getChild("id").setEndTextElementListener(new EndTextElementListener()
        {
            @Override
            public void end(String body)
            {
                if (body != null && body != "")
                {
                    bestBook.setId(Integer.parseInt(body));
                }
            }
        });

        bestBookElement.getChild("title").setEndTextElementListener(new EndTextElementListener()
        {
            @Override
            public void end(String body)
            {
                bestBook.setTitle(body);
            }
        });

        bestBookElement.getChild("image_url").setEndTextElementListener(new EndTextElementListener()
        {
            @Override
            public void end(String body)
            {
                bestBook.setImageUrl(body);
            }
        });

        bestBookElement.getChild("small_image_url").setEndTextElementListener(new EndTextElementListener()
        {
            @Override
            public void end(String body)
            {
                bestBook.setSmallImageUrl(body);
            }
        });
    }

    public int getId()
    {
        return mId;
    }

    public void setId(int id)
    {
        mId = id;
    }

    public String getTitle()
    {
        return mTitle;
    }

    public void setTitle(String title)
    {
        mTitle = title;
    }

    public Author getAuthor()
    {
        return mAuthor;
    }

    public void setAuthor(Author author)
    {
        mAuthor = author;
    }

    public String getImageUrl()
    {
        return mImageUrl;
    }

    public void setImageUrl(String imageUrl)
    {
        mImageUrl = imageUrl;
    }

    public String getSmallImageUrl()
    {
        return mSmallImageUrl;
    }

    public void setSmallImageUrl(String smallImageUrl)
    {
        mSmallImageUrl = smallImageUrl;
    }
}
