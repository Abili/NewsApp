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

public class NewsActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks<ArrayList<News>> {

    public static final String LOG_TAG = NewsActivity.class.getName();
    NewsAdapter adapter;
    RecyclerView newRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.news_activity);

        // Create a fake list of earthquake locations.
        adapter = new NewsAdapter(new ArrayList<News>(), this);
        newRecyclerView = findViewById(R.id.list);

        LinearLayoutManager manager = new LinearLayoutManager(this);

        newRecyclerView.setLayoutManager(manager);
        newRecyclerView.setAdapter(adapter);


        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(newRecyclerView.getContext(),
                manager.getOrientation());
        newRecyclerView.addItemDecoration(dividerItemDecoration);


        // Check the status of the network connection.
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
        }
        // Otherwise update the TextView to tell the user there is no
        // connection
        else {
            Toast.makeText(getApplicationContext(), "Check your Internet Connection...", Toast.LENGTH_LONG).show();
        }
    }

    @NonNull
    @Override
    public Loader<ArrayList<News>> onCreateLoader(int id, @Nullable Bundle args) {
        String url = "";
        return new NewsLoader(NewsActivity.this, url);
    }

    @Override
    public void onLoadFinished(@NonNull Loader<ArrayList<News>> loader, ArrayList<News> data) {

        // If there is a valid list of {@link Earthquake}s, then add them to the adapter's
        // data set. This will trigger the ListView to update.
        if (data != null && !data.isEmpty()) {
            adapter = new NewsAdapter(data, NewsActivity.this);
            newRecyclerView.setAdapter(adapter);
        }


    }

    @Override
    public void onLoaderReset(@NonNull Loader<ArrayList<News>> loader) {

    }

}
