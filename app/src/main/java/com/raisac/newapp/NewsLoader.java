package com.raisac.newapp;

import androidx.annotation.Nullable;
import androidx.loader.content.AsyncTaskLoader;

import java.net.URL;
import java.util.ArrayList;


public class NewsLoader extends AsyncTaskLoader<ArrayList<News>> {
    private static String SAMPLE_JSON_RESPONSE =
            "https://content.guardianapis.com/search?api-key=fbd9d4b9-77ed-45b3-9989-effc49198ca1";

    public NewsLoader(NewsActivity context , String url) {
        super(context);
        SAMPLE_JSON_RESPONSE = url;
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

        ArrayList<News> result = Utils.fetchNewsData(SAMPLE_JSON_RESPONSE);
        return result;
    }
}
