package com.raisac.newapp;

import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import java.net.URL;
import java.util.ArrayList;


public class NewsLoader extends AsyncTaskLoader<ArrayList<News>> {
    private String mUrl;
    /*create a constructor for NewsLoader that takes in the Actitivity that has

     */
    public NewsLoader(NewsActivity context , String url) {
        super(context);
        mUrl = url;
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Nullable
    @Override
    public ArrayList<News> loadInBackground() {
        // Perform the HTTP request for earthquake data and process the response.

        // Don't perform the request if there are no URLs, or the first URL is null
        ArrayList<News> result = Utils.fetchNewsData(mUrl);
        return result;
    }
}
