package com.example.android.moviemania;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.android.moviemania.data.Movie;
import com.example.android.moviemania.utilities.NetworkUtils;
import com.example.android.moviemania.utilities.OpenMovieJSONUtils;

import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * Created by T2MISHU on 8/1/2017.
 */

public class MovieActivityFragment extends Fragment implements MovieAdapter.ListItemOnCLickHandler {

    //Member Variables
    private final static String PARCEABLE_EXTRA_KEY = "Movie";
    private final static String PARCEABLE_SAVE_STATE_KEY = "MovieArrayList";
    private boolean mSavedInstanceStateFlag;
    private String mUserSortChoice;
    private GridView mMovieDisplayGridView;
    private ProgressBar mLoadProgressBar;
    private TextView mErrorMessageTextView;
    private MovieAdapter mMovieAdapter;
    private ArrayList<Movie> mMovieDataList;

    //User Sort Choice Setter
    public void setUserSortChoice(String userSortChoice) {
        this.mUserSortChoice = userSortChoice;
    }

    //Saved Instance State Flag Setter
    public void setSavedInstanceStateFlag(boolean savedInstanceStateFlag) {
        this.mSavedInstanceStateFlag = savedInstanceStateFlag;
    }

    //Runs as soon as the class is loaded
    public MovieActivityFragment() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {

        //Saved Instance State
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null || !savedInstanceState.containsKey(PARCEABLE_SAVE_STATE_KEY)) {
            //mMovieDataList = new ArrayList<Movie>(mMovieDataList);
            setSavedInstanceStateFlag(false);
        } else {
            setSavedInstanceStateFlag(true);
            mMovieDataList = savedInstanceState.getParcelableArrayList(PARCEABLE_SAVE_STATE_KEY);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelableArrayList(PARCEABLE_SAVE_STATE_KEY, mMovieDataList);
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView (LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle savedInstanceState) {

        //Inflate Movie Fragment View
        Boolean shouldAttachToParentImmediately = false;
        View movieFragmentView = layoutInflater.inflate(R.layout.fragment_movie, viewGroup, shouldAttachToParentImmediately);

        //Initialize Movie Fragment View Elements
        mErrorMessageTextView = (TextView) movieFragmentView.findViewById(R.id.tv_error_message_display);
        mErrorMessageTextView.setVisibility(View.INVISIBLE);

        mLoadProgressBar = (ProgressBar) movieFragmentView.findViewById(R.id.pb_loading_indicator);
        mLoadProgressBar.setVisibility(View.INVISIBLE);

        mMovieDisplayGridView = (GridView) movieFragmentView.findViewById(R.id.gv_movie_display);
        mMovieDisplayGridView.setVisibility(View.VISIBLE);

        //Load Movies
        loadMovieData();

        return movieFragmentView;

    }

    //Load Movie Data
    public void loadMovieData() {
        showMovieDataView();
        new FetchMovieTask().execute(mUserSortChoice);
    }
    //Show Error Message View
    public void showErrorMessageView() {
        mMovieDisplayGridView.setVisibility(View.INVISIBLE);
        mErrorMessageTextView.setVisibility(View.VISIBLE);
    }

    //Show Movie Data View
    public void showMovieDataView() {
        mMovieDisplayGridView.setVisibility(View.VISIBLE);
        mErrorMessageTextView.setVisibility(View.INVISIBLE);
    }

    //Add setter for fetching parsed Movie Data
    public void setMovieData(Movie[] movieData) {
        if (!mSavedInstanceStateFlag)
            mMovieDataList = new ArrayList<>(Arrays.asList(movieData));
        setSavedInstanceStateFlag(false);
    }

    //Interface Implementation
    @Override
    public void onClick(Movie movieClicked) {
        Context context = getActivity();
        Class detailActivity = MovieDetailActivity.class;
        Intent movieDetailIntent = new Intent(context, detailActivity);
        movieDetailIntent.putExtra(PARCEABLE_EXTRA_KEY, movieClicked);
        startActivity(movieDetailIntent);
    }

    //Class to handle Task in Background
    public class FetchMovieTask extends AsyncTask<String, Void, Movie[]> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Movie[] doInBackground(String... userSortChoice) {
            URL movieAPIURL = null;

            if (userSortChoice[0] == null)
                movieAPIURL = NetworkUtils.buildMovieURL(getActivity());
            else
                movieAPIURL = NetworkUtils.buildMovieURL(userSortChoice[0], getActivity());

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
            mLoadProgressBar.setVisibility(View.INVISIBLE);

            //Set parsed Movie Data
            if (movieData != null) {
                showMovieDataView();
                setMovieData(movieData);
                mMovieAdapter = new MovieAdapter(getActivity(),  MovieActivityFragment.this, mMovieDataList);
                mMovieDisplayGridView.setAdapter(mMovieAdapter);
            }
            else {
                showErrorMessageView();
            }
        }
    } //End of Async Task Class
}
