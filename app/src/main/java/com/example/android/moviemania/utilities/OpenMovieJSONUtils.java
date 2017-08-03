package com.example.android.moviemania.utilities;

import com.example.android.moviemania.data.Movie;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;

/**
 * Created by Nimisha on 7/28/2017.
 */

public class OpenMovieJSONUtils {

    public static Movie[] getMovieDataFromAPIJSONResult(String movieJSONData) throws JSONException {

        //JSON Name Identifiers
        final String MOVIE_RESULTS = "results";
        final String MOVIE_VOTE_COUNT = "vote_count";
        final String MOVIE_ID = "id";
        final String MOVIE_VIDEO = "video";
        final String MOVIE_VOTE_AVERAGE = "vote_average";
        final String MOVIE_TITLE = "title";
        final String MOVIE_POPULARITY = "popularity";
        final String MOVIE_POSTER_PATH = "poster_path";
        final String MOVIE_ORIGINAL_LANGUAGE = "original_language";
        final String MOVIE_ORIGINAL_TITLE = "original_title";
        final String MOVIE_GENRE_IDS = "genre_ids";
        final String MOVIE_BACKDROP_PATH = "backdrop_path";
        final String MOVIE_ADULT = "adult";
        final String MOVIE_OVERVIEW = "overview";
        final String MOVIE_RELEASE_DATE = "release_date";
        final String MESSAGE_CODE = "cod";

        //Movie Data
        Movie[] parsedMovieData = null;
        //Convert API Result in JSON Object
        JSONObject movieJSON = new JSONObject(movieJSONData);

        /* Is there an error? */
        if (movieJSON.has(MESSAGE_CODE)) {
            int errorCode = movieJSON.getInt(MESSAGE_CODE);

            switch (errorCode) {
                case HttpURLConnection.HTTP_OK:
                    break;
                case HttpURLConnection.HTTP_NOT_FOUND:
                    /* Location invalid */
                    return null;
                default:
                    /* Server probably down */
                    return null;
            }
        }

        JSONArray movieResultArray = movieJSON.getJSONArray(MOVIE_RESULTS);
        parsedMovieData = new Movie[movieResultArray.length()];

        //Loop through JSON Array
        for (int pos = 0; pos < movieResultArray.length(); pos++) {
            JSONObject movieResultObject = movieResultArray.getJSONObject(pos);
            JSONArray genreJSONArray = movieResultObject.getJSONArray(MOVIE_GENRE_IDS);
            int[] genreArray = new int[genreJSONArray.length()];
            for (int genreId = 0; genreId < genreJSONArray.length(); genreId++)
                genreArray[genreId] = genreJSONArray.getInt(genreId);
            Movie movieData = new Movie(movieResultObject.getInt(MOVIE_VOTE_COUNT),
                    movieResultObject.getInt(MOVIE_ID),
                    movieResultObject.getBoolean(MOVIE_VIDEO),
                    movieResultObject.getDouble(MOVIE_VOTE_AVERAGE),
                    movieResultObject.getString(MOVIE_TITLE),
                    movieResultObject.getDouble(MOVIE_POPULARITY),
                    movieResultObject.getString(MOVIE_POSTER_PATH),
                    movieResultObject.getString(MOVIE_ORIGINAL_LANGUAGE),
                    movieResultObject.getString(MOVIE_ORIGINAL_TITLE),
                    genreArray,
                    movieResultObject.getString(MOVIE_BACKDROP_PATH),
                    movieResultObject.getBoolean(MOVIE_ADULT),
                    movieResultObject.getString(MOVIE_OVERVIEW),
                    movieResultObject.getString(MOVIE_RELEASE_DATE));
            parsedMovieData[pos] = movieData;
        }
        return parsedMovieData;
    }
}
