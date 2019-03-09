package com.moringa.bookstore.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.xml.sax.Attributes;

import android.sax.Element;
import android.sax.StartElementListener;

public class Reviews implements Serializable
{
    private static final long serialVersionUID = 0L;

    private int mStart;
    private int mEnd;
    private int mTotal;
    private List<Review> mReviews = new ArrayList<Review>();

    public void clear()
    {
        this.setStart(0);
        this.setEnd(0);
        this.setTotal(0);
        this.mReviews.clear();
    }

    public Reviews copy()
    {
        Reviews reviewsCopy = new Reviews();

        reviewsCopy.setStart(this.getStart());
        reviewsCopy.setEnd(this.getEnd());
        reviewsCopy.setTotal(this.getTotal());

        List<Review> reviewList = this.getReviews();
        List<Review> reviewListCopy = reviewsCopy.getReviews();
        if (reviewList != null)
        {
            for ( int i = 0; i < reviewList.size(); i++ )
            {
                reviewListCopy.add(reviewList.get(i).copy());
            }
        }

        return reviewsCopy;
    }

    public static Reviews appendListener(Element parentElement, int depth)
    {
        final Reviews reviews = new Reviews();
        Element reviewsElement = parentElement.getChild("reviews");

        reviewsElement.setStartElementListener(new StartElementListener()
        {
            @Override
            public void start(Attributes attributes)
            {
                reviews.setStart(Integer.parseInt(attributes.getValue("start")));
                reviews.setEnd(Integer.parseInt(attributes.getValue("end")));
                reviews.setTotal(Integer.parseInt(attributes.getValue("total")));
            }
        });

        reviews.setReviews(Review.appendArrayListener(reviewsElement, depth + 1));

        return reviews;
    }

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
    public List<Review> getReviews()
    {
        return mReviews;
    }
    public void setReviews(List<Review> reviews)
    {
        mReviews = reviews;
    }
}