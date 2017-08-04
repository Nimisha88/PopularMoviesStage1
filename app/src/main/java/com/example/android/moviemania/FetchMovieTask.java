package com.example.android.moviemania;

import android.os.AsyncTask;
import android.view.View;

import com.example.android.moviemania.data.Movie;
import com.example.android.moviemania.utilities.NetworkUtils;
import com.example.android.moviemania.utilities.OpenMovieJSONUtils;

import java.net.URL;

/**
 * Created by Nimisha on 8/3/2017.
 */

public class FetchMovieTask extends AsyncTask<String, Void, Movie[]> {

    private MovieActivityFragment mMovieActivityFragmentInstance;

    //Constructor
    public FetchMovieTask (MovieActivityFragment movieActivityFragmentInstance) {
        this.mMovieActivityFragmentInstance = movieActivityFragmentInstance;
    }


    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        mMovieActivityFragmentInstance.setProgressBarVisibility(View.VISIBLE);
    }

    @Override
    protected Movie[] doInBackground(String... userSortChoice) {
        URL movieAPIURL = null;

        if (userSortChoice[0] == null)
            movieAPIURL = NetworkUtils.buildMovieURL(mMovieActivityFragmentInstance.getActivity());
        else
            movieAPIURL = NetworkUtils.buildMovieURL(userSortChoice[0], mMovieActivityFragmentInstance.getActivity());

        try {
            String movieJSONData = NetworkUtils.getResponseFromHttpURL(movieAPIURL);
            return OpenMovieJSONUtils.getMovieDataFromAPIJSONResult(movieJSONData);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(Movie[] movieData) {
        mMovieActivityFragmentInstance.setProgressBarVisibility(View.INVISIBLE);

        //Set parsed Movie Data
        if (movieData != null) {
            mMovieActivityFragmentInstance.showMovieDataView();
            mMovieActivityFragmentInstance.setMovieData(movieData);
            mMovieActivityFragmentInstance.setMovieAdapter();
        }
        else {
            mMovieActivityFragmentInstance.showErrorMessageView();
        }
    }
} //End of Async Task Class