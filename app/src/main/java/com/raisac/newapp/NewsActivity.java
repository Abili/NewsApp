/*
 * Copyright (C) 2016 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.raisac.newapp;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.loader.app.LoaderManager;
import androidx.loader.content.Loader;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.Objects;

@SuppressWarnings({"deprecation", "UnnecessaryLocalVariable"})
public class NewsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<News>> {
    private NewsAdapter adapter;
    private ProgressBar loadingProgressBar;
    private RecyclerView newRecyclerView;
    private TextView noNews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_activity);
        noNews = findViewById(R.id.no_news);

        // Create a fake list of new updates/ headlines
        adapter = new NewsAdapter(new ArrayList<News>(), this);
        newRecyclerView = findViewById(R.id.list);
        loadingProgressBar = findViewById(R.id.progressBar);
        LinearLayoutManager manager = new LinearLayoutManager(this);
        newRecyclerView.setLayoutManager(manager);
        newRecyclerView.setAdapter(adapter);

        //set the secoration of the listview to create the seperation lines
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(newRecyclerView.getContext(),
                manager.getOrientation());
        newRecyclerView.addItemDecoration(dividerItemDecoration);

        // Check the status of the network connection.
        checkForNetworkConnectivity();
    }

    //method to check network connectivity
    private void checkForNetworkConnectivity() {
        ConnectivityManager connMgr = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = null;
        if (connMgr != null) {
            networkInfo = connMgr.getActiveNetworkInfo();
        }
        // If the network is available, connected,  start a Loader AsyncTask.
        if (networkInfo != null && networkInfo.isConnected()) {
            Bundle queryBundle = new Bundle();
            getSupportLoaderManager().restartLoader(0, queryBundle, this);
            Toast.makeText(getApplicationContext(), "Loading...", Toast.LENGTH_LONG).show();
            loadingProgressBar.setVisibility(View.VISIBLE);
        }
        // Otherwise update the TextView to tell the user there is no
        // connection
        else {
            Toast.makeText(getApplicationContext(), "Check Network Connectivity", Toast.LENGTH_LONG).show();
        }
    }

    @NonNull
    @Override
    public Loader<ArrayList<News>> onCreateLoader(int id, @Nullable Bundle args) {
        String SAMPLE_JSON_RESPONSE = "https://content.guardianapis.com/search";
        String url = SAMPLE_JSON_RESPONSE;
        return new NewsLoader(NewsActivity.this, url);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<News>> loader, ArrayList<News> data) {

        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (data != null && !data.isEmpty()) {
            //and set the visibility to visible
            newRecyclerView.setVisibility(View.VISIBLE);
            loadingProgressBar.setVisibility(View.GONE);
            adapter = new NewsAdapter(data, NewsActivity.this);
            newRecyclerView.setAdapter(adapter);
        }
        //if theres no data to be displayed then set the visibility of the empty textview to visible
        else {
            /*incase connection is loast eg turned phone on flight mode , the clear the arraylist to prevent
            showing the information that was already displayed same time as the no news textview
            */
            checkForNetworkConnectivity();
            Objects.requireNonNull(data).clear();
            noNews.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<News>> loader) {

    }
}
