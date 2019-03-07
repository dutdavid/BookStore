package com.moringa.bookstore;



import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class GoodReadsServices {
    public static void findBooks(String bookSearched, Callback callback) {
        OkHttpClient client = new OkHttpClient.Builder()
                .build();

        HttpUrl.Builder urlBuilder = HttpUrl.parse(Constants.BASE_URL).newBuilder();
        urlBuilder.addQueryParameter(Constants.API_PARAMETER, Constants.GOODREADS_API_KEY);
        urlBuilder.addQueryParameter(Constants.QUERY_PARAMETER, bookSearched);
        String url = urlBuilder.build().toString();

        Request request = new Request.Builder()
                .url(url)
                .header("Authorization", Constants.GOODREADS_API_KEY)
                .build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }
}