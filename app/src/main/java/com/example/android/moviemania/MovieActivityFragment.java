package com.example.android.moviemania;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.example.android.moviemania.data.Movie;
import java.util.ArrayList;
import java.util.Arrays;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by Nimisha on 8/1/2017.
 */

public class MovieActivityFragment extends Fragment implements MovieAdapter.ListItemOnClickHandler {

    //Bind Views
    @BindView(R.id.rv_movie_display) RecyclerView mMovieDisplayRecyclerView;
    @BindView(R.id.pb_loading_indicator) ProgressBar mLoadProgressBar;
    @BindView(R.id.tv_error_message_display) TextView mErrorMessageTextView;

    //Member Variables
    private final static int GRID_COLUMN_COUNT = 3;
    private final static String PARCELABLE_EXTRA_KEY = "Movie";
    private final static String PARCELABLE_SAVE_STATE_KEY = "MovieArrayList";
    private boolean mSavedInstanceStateFlag;
    private String mUserSortChoice;
    private RecyclerView.LayoutManager layoutManager;
    private MovieAdapter mMovieAdapter;
    private ArrayList<Movie> mMovieDataList;
    private Context mMovieActivityContext;
    private Unbinder unbinder;

    //Make Progress Bar Visible/Invisible
    public void setProgressBarVisibility(int pbVisibility) {
        mLoadProgressBar.setVisibility(pbVisibility);
    }

    //Set Movie Adapter with Movie Data from API
    public void setMovieAdapter() {
        mMovieAdapter = new MovieAdapter(getActivity(),  MovieActivityFragment.this, mMovieDataList);
        mMovieDisplayRecyclerView.setAdapter(mMovieAdapter);
    }

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
        if (savedInstanceState == null || !savedInstanceState.containsKey(PARCELABLE_SAVE_STATE_KEY)) {
            setSavedInstanceStateFlag(false);
        } else {
            setSavedInstanceStateFlag(true);
            mMovieDataList = savedInstanceState.getParcelableArrayList(PARCELABLE_SAVE_STATE_KEY);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (!(mMovieDataList == null || mMovieDataList.isEmpty()))
            outState.putParcelableArrayList(PARCELABLE_SAVE_STATE_KEY, mMovieDataList);
        super.onSaveInstanceState(outState);
    }

    @Override
    public View onCreateView (LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle savedInstanceState) {

        //Inflate Movie Fragment View
        Boolean shouldAttachToParentImmediately = false;
        View movieFragmentView = layoutInflater.inflate(R.layout.fragment_movie, viewGroup, shouldAttachToParentImmediately);

        //View Binder
        unbinder = ButterKnife.bind(this, movieFragmentView);

        //Set Visibility of Elements
        showMovieDataView();
        setProgressBarVisibility(View.INVISIBLE);

        layoutManager = new GridLayoutManager(mMovieActivityContext, GRID_COLUMN_COUNT);
        mMovieDisplayRecyclerView.setLayoutManager(layoutManager);
        mMovieDisplayRecyclerView.setHasFixedSize(true);

        //Load Movies
        loadMovieData();

        return movieFragmentView;

    }

    //Butterknife Unbind
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    //To Get Activity
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.mMovieActivityContext = context;
    }

    //Load Movie Data
    public void loadMovieData() {
        showMovieDataView();
        new FetchMovieTask(this).execute(mUserSortChoice);
    }
    //Show Error Message View
    public void showErrorMessageView() {
        mMovieDisplayRecyclerView.setVisibility(View.INVISIBLE);
        mErrorMessageTextView.setVisibility(View.VISIBLE);
    }

    //Show Movie Data View
    public void showMovieDataView() {
        mMovieDisplayRecyclerView.setVisibility(View.VISIBLE);
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
        Class detailActivity = MovieDetailActivity.class;
        Intent movieDetailIntent = new Intent(mMovieActivityContext, detailActivity);
        movieDetailIntent.putExtra(PARCELABLE_EXTRA_KEY, movieClicked);
        startActivity(movieDetailIntent);
    }
}
