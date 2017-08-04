package com.example.android.moviemania.utilities;

import android.content.Context;
import android.net.Uri;
import com.example.android.moviemania.BuildConfig;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by Nimisha on 7/27/2017.
 */

public final class NetworkUtils {

    //Member Variables
    private final static String MOVIE_BASE_URL = "http://api.themoviedb.org/3/movie";
    private final static String MOVIE_POPULAR = "popular";
    private final static String MOVIE_TOP_RATED = "top_rated";
    private final static String API_PARAM_NAME = "api_key";
    private final static String MOVIE_POSTER_BASE_URL = "http://image.tmdb.org/t/p";
    private final static String MOVIE_POSTER_SIZE = "w185";

    //Method to return appropriate Poster URL
    public static URL buildPosterURL(String posterPath){
        Uri builtUri = Uri.parse(MOVIE_POSTER_BASE_URL).buildUpon()
                .appendPath(MOVIE_POSTER_SIZE)
                .appendEncodedPath(posterPath).build();
        URL builtURL = null;

        try {
            builtURL = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return builtURL;
    }

    //Method to return appropriate Poster URL
    public static URL buildBackdropURL(String backdropPath){
        Uri builtUri = Uri.parse(MOVIE_POSTER_BASE_URL).buildUpon()
                .appendPath(MOVIE_POSTER_SIZE)
                .appendPath(backdropPath).build();
        URL builtURL = null;

        try {
            builtURL = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return builtURL;
    }

    //Method to return appropriate Movie URL
    public static URL buildMovieURL(Context activityContext) {
        return buildMovieURL(MOVIE_POPULAR, activityContext);
    }

    public static URL buildMovieURL(String userChoice, Context activityContext) {
        //String apiKey = activityContext.getString(R.string.api_key);
        String apiKey = BuildConfig.API_KEY;
        Uri builtUri = Uri.parse(MOVIE_BASE_URL).buildUpon().appendPath(userChoice).appendQueryParameter(API_PARAM_NAME, apiKey).build();
        URL builtURL = null;

        try {
            builtURL = new URL(builtUri.toString());
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }

        return builtURL;
    }

    //Method to return entire Result from HTTP Response
    public static String getResponseFromHttpURL(URL url) throws IOException {
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        try {
            InputStream inputStream = urlConnection.getInputStream();
            Scanner readScanner = new Scanner(inputStream);
            readScanner.useDelimiter("\\A");

            boolean hasInput = readScanner.hasNext();
            if (hasInput)
                return readScanner.next();
            else
                return null;
        } finally {
            urlConnection.disconnect();
        }
    }
}
