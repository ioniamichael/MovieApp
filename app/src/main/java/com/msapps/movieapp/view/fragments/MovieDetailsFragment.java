package com.msapps.movieapp.view.fragments;

import android.annotation.SuppressLint;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.text.SpannableString;
import android.text.style.ImageSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.msapps.movieapp.R;
import com.msapps.movieapp.model.MoviesResponse;

public class MovieDetailsFragment extends Fragment {

    private View view;
    private MoviesResponse mMoviesResponse;
    private ImageView mIVMovieImage;
    private TextView mTVMovieTitle, mTVMovieRating, mTVMovieReleaseYear, mTVMovieGenre;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_movie_details, container, false);
        initViews();
        getMovieDetails();
        setMovieDetails();
        return view;
    }


    private void initViews() {
        mIVMovieImage = view.findViewById(R.id.ivMovieImage);
        mTVMovieTitle = view.findViewById(R.id.tvMovieTitle);
        mTVMovieRating = view.findViewById(R.id.tvMovieRating);
        mTVMovieReleaseYear = view.findViewById(R.id.tvMovieReleaseYear);
        mTVMovieGenre = view.findViewById(R.id.tvMovieGenre);
    }

    private void getMovieDetails() {
        mMoviesResponse = getArguments().getParcelable("movieDetails");
    }

    @SuppressLint("SetTextI18n")
    private void setMovieDetails() {
        Glide.with(getContext())
                .load(mMoviesResponse.getImage())
                .placeholder(ContextCompat.getDrawable(getContext(), R.drawable.place_holder_image))
                .into(mIVMovieImage);
        mTVMovieTitle.setText(mMoviesResponse.getTitle());
        mTVMovieRating.setText(getString(R.string.rating) + mMoviesResponse.getRating());
        mTVMovieReleaseYear.setText(getString(R.string.release_year) + mMoviesResponse.getReleaseYear());
        StringBuilder builder = new StringBuilder();
        for (String s : mMoviesResponse.getGenre()) {
            if (s != mMoviesResponse.getGenre().get(mMoviesResponse.getGenre().size() - 1)) {
                builder.append(s);
                builder.append(" ♦ ");
            } else {
                builder.append(s);
            }
        }

        mTVMovieGenre.setText(getString(R.string.genre) + builder.toString().trim());

    }
}