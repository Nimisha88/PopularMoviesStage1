//Movie.java

package com.example.android.moviemania.data;

import android.os.Parcel;
import android.os.Parcelable;

import com.example.android.moviemania.utilities.NetworkUtils;

/**
 * Created by Nimisha on 7/28/2017.
 */

public final class Movie implements Parcelable {

    //Member Variables
    private int mVoteCount;
    private int mID;
    private double mVoteAverage;
    private String mTitle;
    private double mPopularity;
    private String mPosterPath;
    private String mOriginalLanguage;
    private String mOriginalTitle;
    private String mBackdropPath;
    private String mOverview;
    private String mReleaseDate;
    private String mPosterURLString;
    private String mBackdropURLString;
    private String mChangeUponView;

    //Constructor(s)
    public Movie(int mVoteCount, int mID, boolean mVideo, double mVoteAverage, String mTitle, double mPopularity, String mPosterPath, String mOriginalLanguage, String mOriginalTitle, int[] mGenreIds, String mBackdropPath, boolean mAdult, String mOverview, String mReleaseDate) {
        this.mVoteCount = mVoteCount;
        this.mID = mID;
        this.mVoteAverage = mVoteAverage;
        this.mTitle = mTitle;
        this.mPopularity = mPopularity;
        this.mPosterPath = mPosterPath;
        this.mOriginalLanguage = mOriginalLanguage;
        this.mOriginalTitle = mOriginalTitle;
        this.mBackdropPath = mBackdropPath;
        this.mOverview = mOverview;
        this.mReleaseDate = mReleaseDate;
        mPosterURLString = NetworkUtils.buildPosterURL(mPosterPath).toString();
        mBackdropURLString = NetworkUtils.buildBackdropURL(mBackdropPath).toString();
    }

    public Movie(Parcel parseParcel) {
        mVoteCount = parseParcel.readInt();
        mID = parseParcel.readInt();
        mVoteAverage = parseParcel.readDouble();
        mTitle = parseParcel.readString();
        mPopularity = parseParcel.readDouble();
        mOriginalLanguage = parseParcel.readString();
        mOriginalTitle = parseParcel.readString();
        mOverview = parseParcel.readString();
        mReleaseDate = parseParcel.readString();
        mPosterURLString = parseParcel.readString();
        mBackdropURLString = parseParcel.readString();
    }

    //Member Variable Getters
    public int getVoteCount() {
        return mVoteCount;
    }

    public int getID() {
        return mID;
    }

    public double getVoteAverage() {
        return mVoteAverage;
    }

    public String getTitle() {
        return mTitle;
    }

    public double getPopularity() {
        return mPopularity;
    }

    public String getOriginalLanguage() {
        return mOriginalLanguage;
    }

    public String getOriginalTitle() {
        return mOriginalTitle;
    }

    public String getOverview() {
        return mOverview;
    }

    public String getReleaseDate() {
        return mReleaseDate;
    }

    public String getPosterURLString() {
        return mPosterURLString;
    }

    public String getBackdropURLString() {
        return mBackdropURLString;
    }

    public String getChangeUponView() {
        return mChangeUponView;
    }

    public void setChangeUponView() {
        if (this.mChangeUponView == null)
            this.mChangeUponView = getOriginalTitle();
        this.mChangeUponView = getChangeUponView() + " :)";
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel outParcel, int flags) {
        outParcel.writeInt(mVoteCount);
        outParcel.writeInt(mID);
        outParcel.writeDouble(mVoteAverage);
        outParcel.writeString(mTitle);
        outParcel.writeDouble(mPopularity);
        outParcel.writeString(mOriginalLanguage);
        outParcel.writeString(mOriginalTitle);
        outParcel.writeString(mOverview);
        outParcel.writeString(mReleaseDate);
        outParcel.writeString(mPosterURLString);
        outParcel.writeString(mBackdropURLString);
    }

    public static final Parcelable.Creator<Movie> CREATOR = new Parcelable.Creator<Movie>() {
        public Movie createFromParcel(Parcel inParcel) {
            return new Movie(inParcel);
        }
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}