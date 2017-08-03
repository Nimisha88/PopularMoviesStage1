package com.example.android.moviemania;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.android.moviemania.data.Movie;
import com.squareup.picasso.Picasso;

public class MovieDetailActivity extends AppCompatActivity {

    private final static String PARCEABLE_EXTRA_KEY = "Movie";
    private final static String MOVIE_TITLE_TEXT = "Movie Title: ";
    private final static String MOVIE_OVERVIEW_TEXT = "Movie Overview: ";
    private final static String MOVIE_RATING_TEXT = "Movie Rating: ";
    private final static String MOVIE_RELEASE_DATE_TEXT = "Movie Released On: ";
    private ImageView mThumbnailImageView;
    private TextView mMovieTitleTextView;
    private TextView mMovieOverviewTextView;
    private TextView mMovieVoteAverageTextView;
    private TextView mMovieReleaseDateTextView;
    private Movie mMovieClicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        //Initialize the Element Views
        mThumbnailImageView = (ImageView) findViewById(R.id.img_thumbnail);
        mMovieTitleTextView = (TextView) findViewById(R.id.tv_movie_title);
        mMovieOverviewTextView = (TextView) findViewById(R.id.tv_movie_overview);
        mMovieVoteAverageTextView = (TextView) findViewById(R.id.tv_movie_vote_average);
        mMovieReleaseDateTextView = (TextView) findViewById(R.id.tv_release_date);

        //Get the Intent that started this Activity
        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity != null) {
            if (intentThatStartedThisActivity.hasExtra(PARCEABLE_EXTRA_KEY)) {

                //Extract Movie Details that was clicked
                mMovieClicked = intentThatStartedThisActivity.getParcelableExtra(PARCEABLE_EXTRA_KEY);

                //Display Movie Details on Screen
                Uri posterURI = Uri.parse(mMovieClicked.getPosterURLString());
                Picasso.with(this).load(posterURI).into(mThumbnailImageView);
                mMovieTitleTextView.setText(mMovieClicked.getOriginalTitle());
                mMovieOverviewTextView.setText(mMovieClicked.getOverview());
                mMovieVoteAverageTextView.setText(Double.toString(mMovieClicked.getVoteAverage()));
                mMovieReleaseDateTextView.setText(mMovieClicked.getReleaseDate());
            }
        }
    }
}