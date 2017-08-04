package com.example.android.moviemania;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import com.example.android.moviemania.data.Movie;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MovieDetailActivity extends AppCompatActivity {

    //Bind Views
    @BindView(R.id.img_thumbnail) ImageView mThumbnailImageView;
    @BindView(R.id.tv_movie_title) TextView mMovieTitleTextView;
    @BindView(R.id.tv_movie_overview) TextView mMovieOverviewTextView;
    @BindView(R.id.tv_movie_vote_average) TextView mMovieVoteAverageTextView;
    @BindView(R.id.rb_movie_vote_average) RatingBar mMovieVoteAverageRatingBar;
    @BindView(R.id.tv_release_date) TextView mMovieReleaseDateTextView;

    //Member Variables
    private final static String PARCELABLE_EXTRA_KEY = "Movie";
    private final static String MOVIE_TITLE_TEXT = "Movie Title: ";
    private final static String MOVIE_OVERVIEW_TEXT = "Movie Overview: ";
    private final static String MOVIE_RATING_TEXT = "Movie Rating: ";
    private final static String MOVIE_RELEASE_DATE_TEXT = "Movie Released On: ";
    private Movie mMovieClicked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        //Get the Intent that started this Activity
        Intent intentThatStartedThisActivity = getIntent();

        ButterKnife.bind(this);

        if (intentThatStartedThisActivity != null) {
            if (intentThatStartedThisActivity.hasExtra(PARCELABLE_EXTRA_KEY)) {

                //Extract Movie Details that was clicked
                mMovieClicked = intentThatStartedThisActivity.getParcelableExtra(PARCELABLE_EXTRA_KEY);

                //Double to Float
                //float floatVoteAverage = Double.valueOf(mMovieClicked.getVoteAverage()).floatValue();
                float floatVoteAverage = (float) mMovieClicked.getVoteAverage();

                //Display Movie Details on Screen
                Picasso.with(this).load(mMovieClicked.getPosterURLString()).placeholder(R.mipmap.movies).error(R.mipmap.image_unavailable).into(mThumbnailImageView);
                mMovieTitleTextView.setText(mMovieClicked.getOriginalTitle());
                mMovieOverviewTextView.setText(mMovieClicked.getOverview());
                mMovieVoteAverageTextView.setText(Float.toString(floatVoteAverage));
                mMovieVoteAverageRatingBar.setRating(floatVoteAverage);
                mMovieReleaseDateTextView.setText(mMovieClicked.getReleaseDate());
            }
        }
    }
}