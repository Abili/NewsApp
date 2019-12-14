package com.raisac.newapp;

import android.net.Uri;
import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import java.util.ArrayList;

/**
 * Utility class with methods to help perform the HTTP request and
 * parse the response.
 */
@SuppressWarnings("JavaDoc")
final class Utils {


    /**
     * Tag for the log messages
     */
    private static final String LOG_TAG = Utils.class.getSimpleName();

    /**
     * Query the USGS dataset and return an {@link ArrayList< News >} object to represent a single earthquake.
     */
    public static ArrayList<News> fetchNewsData(String responseUrl) {
        // Create URL object
        URL url = createUrl(responseUrl);

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error closing input stream", e);
        }

        // Return the {@link Event}
        return extractFeatureFromJson(jsonResponse);
    }

    private static URL createUrl(String responseUrl) {
        Uri.Builder builder = Uri.parse(responseUrl).buildUpon();
        builder.appendQueryParameter("api-key", "fbd9d4b9-77ed-45b3-9989-effc49198ca1")
                .appendQueryParameter("show-tags", "contributor");
        String url = builder.build().toString();
        try {
            return new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * Make an HTTP request to the given URL and return a String as the response.
     */
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem retrieving the news JSON results.", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return jsonResponse;
    }


    /**
     * Convert the {@link InputStream} into a String which contains the
     * whole JSON response from the server.
     */
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }

    /**
     * Return an {@link ArrayList< News >} object by parsing out information
     * about the first news from the input newsJson string.
     */

    private static ArrayList<News> extractFeatureFromJson(String newsJSON) {
        String author = "";
        ArrayList<News> news = new ArrayList<>();
        // Extract relevant fields from the JSON response and create an {@link Event} object
        // If the JSON string is empty or null, then return early.
        if (TextUtils.isEmpty(newsJSON)) {
            return null;
        }

        try {
            // TODO: Parse the response given by the SAMPLE_JSON_RESPONSE string and
            // build up a list of News objects with the corresponding data.

            JSONObject jsonObject = new JSONObject(newsJSON);
            JSONObject response = jsonObject.getJSONObject("response");
            JSONArray results = response.getJSONArray("results");
            for (int i = 0; i < results.length(); i++) {
                JSONObject object = results.getJSONObject(i);
                String title = object.getString("webTitle");
                String section = object.getString("sectionName");
                String time = object.getString("webPublicationDate");
                String url1 = object.getString("webUrl");
                JSONArray tagsArray = object.getJSONArray("tags");
                for (int j = 0; j < tagsArray.length(); j++) {
                    JSONObject newstag = tagsArray.getJSONObject(j);
                    author = newstag.getString("webTitle");
                }

                News _new = new News(title, time, url1, section, author);
                news.add(_new);

            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Problem parsing the earthquake JSON results", e);
        }
        return news;
    }

}
