package com.example.android.moviemania;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import com.example.android.moviemania.data.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Nimisha on 7/28/2017.
 */

public class MovieAdapter extends ArrayAdapter<Movie> {
    //Member Variables
    private static final String LOG_TAG = MovieAdapter.class.getSimpleName();
    private Context mMovieFragmentActivityContext;
    private final ListItemOnCLickHandler mClickHandler;

    //Constructors must have List<> in order to populate List/Grid View
    public MovieAdapter(Activity movieActivity, ListItemOnCLickHandler handler, List<Movie> movieList) {
        super(movieActivity, 0, movieList);
        mMovieFragmentActivityContext = movieActivity;
        mClickHandler = handler;
    }

    //Interface Listener Handler
    public interface ListItemOnCLickHandler {
        void onClick(Movie movieClicked);
    }

    //Class ViewHolder
    public class MovieViewHolder extends RecyclerView.ViewHolder {

        private ImageView mMoviePosterImage;

        //Constructor
        public MovieViewHolder(View view) {
            super(view);
            mMoviePosterImage = (ImageView) view.findViewById(R.id.img_movie_poster);
            showMoviePoster();
        }

        public void showMoviePoster() {
            mMoviePosterImage.setVisibility(View.VISIBLE);
        }
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View movieView, @NonNull ViewGroup viewGroup) {

        //On View Binder Stuff
        final Movie movieDataForThisView = getItem(position);
        Uri posterURI = Uri.parse(movieDataForThisView.getPosterURLString());
        MovieViewHolder movieViewHolder = null;

        //OnCreateViewHolder Stuff
        Context context = viewGroup.getContext();
        int LayoutIDForView = R.layout.movie_view;
        boolean shouldAttachToParentImmediately = false;
        LayoutInflater movieViewLayoutInflator = LayoutInflater.from(context);

        if (movieView == null) {
            movieView = movieViewLayoutInflator.inflate(LayoutIDForView, viewGroup, shouldAttachToParentImmediately);
            movieViewHolder = new MovieViewHolder(movieView);
            movieView.setTag(movieViewHolder);
        } else {
            movieViewHolder = (MovieViewHolder) movieView.getTag();
        }

        Picasso.with(mMovieFragmentActivityContext).load(posterURI).into(movieViewHolder.mMoviePosterImage);

        //Set On Click Listener
        movieView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mClickHandler.onClick(movieDataForThisView);
            }
        });

        return movieView;
    }
}