package com.moringa.bookstore.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import android.sax.Element;
import android.sax.EndElementListener;
import android.sax.EndTextElementListener;

public class Work implements Serializable
{
    private static final long serialVersionUID = 0L;

    private int mBooksCount;
    private int mId;
    private int mOriginalPublicationDay;
    private int mOriginalPublicationMonth;
    private int mOriginalPublicationYear;
    private int mRatingsCount;
    private int mReviewsCount;
    private float mAverageRating;
    private BestBook mBestBook = new BestBook();

    public void clear()
    {
        this.setBooksCount(0);
        this.setId(0);
        this.setOriginalPublicationDay(0);
        this.setOriginalPublicationMonth(0);
        this.setOriginalPublicationYear(0);
        this.setRatingsCount(0);
        this.setReviewsCount(0);
        this.setAverageRating(0);
        mBestBook.clear();
    }

    public Work copy()
    {
        Work workCopy = new Work();

        workCopy.setBooksCount(this.getBooksCount());
        workCopy.setId(this.getId());
        workCopy.setOriginalPublicationDay(this.getOriginalPublicationDay());
        workCopy.setOriginalPublicationMonth(this.getOriginalPublicationMonth());
        workCopy.setOriginalPublicationYear(this.getOriginalPublicationYear());
        workCopy.setRatingsCount(this.getRatingsCount());
        workCopy.setReviewsCount(this.getReviewsCount());
        workCopy.setAverageRating(this.getAverageRating());
        workCopy.setBestBook(this.getBestBook().copy());

        return workCopy;
    }

    public static Work appendListener(final Element parentElement, int depth)
    {
        final Work work = new Work();
        final Element workElement = parentElement.getChild("work");

        appendCommonListeners(workElement, work, depth);

        return work;
    }

    public static List<Work> appendArrayListener(final Element parentElement, int depth)
    {
        final List<Work> works = new ArrayList<Work>();
        final Work work = new Work();
        final Element workElement = parentElement.getChild("work");

        appendCommonListeners(workElement, work, depth);

        workElement.setEndElementListener(new EndElementListener()
        {
            @Override
            public void end()
            {
                works.add(work.copy());
                work.clear();
            }
        });
        return works;
    }

    private static void appendCommonListeners(final Element workElement, final Work work, int depth)
    {
        work.setBestBook(BestBook.appendListener(workElement, depth + 1));

        workElement.getChild("books_count").setEndTextElementListener(new EndTextElementListener()
        {
            @Override
            public void end(String body)
            {
                if (body != null && body != "")
                {
                    work.setBooksCount(Integer.parseInt(body));
                }
            }
        });

        workElement.getChild("id").setEndTextElementListener(new EndTextElementListener()
        {
            @Override
            public void end(String body)
            {
                if (body != null && body != "")
                {
                    work.setId(Integer.parseInt(body));
                }
            }
        });

        workElement.getChild("original_publication_day").setEndTextElementListener(new EndTextElementListener()
        {
            @Override
            public void end(String body)
            {
                if (body != null && body != "")
                {
                    work.setOriginalPublicationDay(Integer.parseInt(body));
                }
            }
        });

        workElement.getChild("original_publication_month").setEndTextElementListener(new EndTextElementListener()
        {
            @Override
            public void end(String body)
            {
                if (body != null && body != "")
                {
                    work.setOriginalPublicationMonth(Integer.parseInt(body));
                }
            }
        });

        workElement.getChild("original_publication_year").setEndTextElementListener(new EndTextElementListener()
        {
            @Override
            public void end(String body)
            {
                if (body != null && body != "")
                {
                    work.setOriginalPublicationYear(Integer.parseInt(body));
                }
            }
        });

        workElement.getChild("ratings_count").setEndTextElementListener(new EndTextElementListener()
        {
            @Override
            public void end(String body)
            {
                if (body != null && body != "")
                {
                    work.setRatingsCount(Integer.parseInt(body));
                }
            }
        });

        workElement.getChild("text_reviews_count").setEndTextElementListener(new EndTextElementListener()
        {
            @Override
            public void end(String body)
            {
                if (body != null && body != "")
                {
                    work.setReviewsCount(Integer.parseInt(body));
                }
            }
        });

        workElement.getChild("average_rating").setEndTextElementListener(new EndTextElementListener()
        {
            @Override
            public void end(String body)
            {
                if (body != null && body != "")
                {
                    work.setAverageRating(Float.parseFloat(body));
                }
            }
        });
    }

    public int getBooksCount()
    {
        return mBooksCount;
    }

    public void setBooksCount(int booksCount)
    {
        mBooksCount = booksCount;
    }

    public int getId()
    {
        return mId;
    }

    public void setId(int id)
    {
        mId = id;
    }

    public int getOriginalPublicationDay()
    {
        return mOriginalPublicationDay;
    }

    public void setOriginalPublicationDay(int originalPublicationDay)
    {
        mOriginalPublicationDay = originalPublicationDay;
    }

    public int getOriginalPublicationMonth()
    {
        return mOriginalPublicationMonth;
    }

    public void setOriginalPublicationMonth(int originalPublicationMonth)
    {
        mOriginalPublicationMonth = originalPublicationMonth;
    }

    public int getOriginalPublicationYear()
    {
        return mOriginalPublicationYear;
    }

    public void setOriginalPublicationYear(int originalPublicationYear)
    {
        mOriginalPublicationYear = originalPublicationYear;
    }

    public int getRatingsCount()
    {
        return mRatingsCount;
    }

    public void setRatingsCount(int ratingsCount)
    {
        mRatingsCount = ratingsCount;
    }

    public int getReviewsCount()
    {
        return mReviewsCount;
    }

    public void setReviewsCount(int reviewsCount)
    {
        mReviewsCount = reviewsCount;
    }

    public float getAverageRating()
    {
        return mAverageRating;
    }

    public void setAverageRating(float averageRating)
    {
        mAverageRating = averageRating;
    }

    public BestBook getBestBook()
    {
        return mBestBook;
    }

    public void setBestBook(BestBook bestBook)
    {
        mBestBook = bestBook;
    }
}