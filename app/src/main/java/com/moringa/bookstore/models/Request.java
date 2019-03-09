package com.moringa.bookstore.models;

import java.io.Serializable;

import android.sax.Element;
import android.sax.EndTextElementListener;

public class Request implements Serializable
{
    private static final long serialVersionUID = 0L;

    private boolean mAuthentication;
    private String mKey;
    private String mMethod;

    public void clear()
    {
        this.setAuthentication(false);
        this.setKey("");
        this.setMethod("");
    }

    public Request copy()
    {
        Request requestCopy = new Request();

        requestCopy.setAuthentication(this.getAuthentication());
        requestCopy.setKey(this.getKey());
        requestCopy.setMethod(this.getMethod());

        return requestCopy;
    }

    public static Request appendListener(Element parentElement)
    {
        final Request request = new Request();
        final Element requestElement = parentElement.getChild("request");

        requestElement.getChild("authentication").setEndTextElementListener(new EndTextElementListener()
        {
            @Override
            public void end(String body)
            {
                request.setAuthentication(Boolean.parseBoolean(body));
            }
        });

        requestElement.getChild("key").setEndTextElementListener(new EndTextElementListener()
        {
            @Override
            public void end(String body)
            {
                request.setKey(body);
            }
        });

        requestElement.getChild("request").setEndTextElementListener(new EndTextElementListener()
        {
            @Override
            public void end(String body)
            {
                request.setMethod(body);
            }
        });

        return request;
    }

    public boolean getAuthentication()
    {
        return mAuthentication;
    }

    public void setAuthentication(boolean authentication)
    {
        mAuthentication = authentication;
    }

    public String getKey()
    {
        return mKey;
    }

    public void setKey(String key)
    {
        mKey = key;
    }

    public String getMethod()
    {
        return mMethod;
    }

    public void setMethod(String method)
    {
        mMethod = method;
    }
}
