package com.msapps.movieapp.view.fragments;

import android.content.res.Configuration;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.msapps.movieapp.R;
import com.msapps.movieapp.adapters.MoviesAdapter;
import com.msapps.movieapp.model.MoviesResponse;
import com.msapps.movieapp.viewmodel.MoviesViewModel;

import java.util.List;

public class MoviesListFragment extends Fragment {

    private View view;

    private MoviesAdapter mAdapter;
    private RecyclerView mRVMovies;
    private MoviesViewModel mMoviesViewModel;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_movies_list, container, false);
        initViewModel();
        getMoviesFromLocalDB();
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    private void initViewModel() {
        mMoviesViewModel = new ViewModelProvider
                .AndroidViewModelFactory(getActivity().getApplication())
                .create(MoviesViewModel.class);
    }

    private void getMoviesFromLocalDB() {
        mMoviesViewModel.getAllMoviesFromLocalDB().observe(this, new Observer<List<MoviesResponse>>() {
            @Override
            public void onChanged(List<MoviesResponse> moviesResponses) {
                fillRecyclerView(moviesResponses);
            }
        });

    }

    private void fillRecyclerView(List<MoviesResponse> moviesResponses) {
        mAdapter = new MoviesAdapter(getContext(), moviesResponses);
        mRVMovies = view.findViewById(R.id.rvMovies);
        RecyclerView.LayoutManager layoutManager;
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            layoutManager = new GridLayoutManager(getContext(), 2);
        } else {
            layoutManager = new GridLayoutManager(getContext(), 3);
        }
        mRVMovies.setLayoutManager(layoutManager);
        mRVMovies.setAdapter(mAdapter);
        mAdapter.setOnMovieItemClickListener(new MoviesAdapter.OnMovieItemClickListener() {
            @Override
            public void onMovieItemClick(MoviesResponse moviesResponse) {
                Bundle bundle = new Bundle();
                bundle.putParcelable("movieDetails", moviesResponse);
                MovieDetailsFragment movieDetailsFragment = new MovieDetailsFragment();
                movieDetailsFragment.setArguments(bundle);
                getFragmentManager()
                        .beginTransaction()
                        .setCustomAnimations(R.anim.enter, R.anim.exit, R.anim.pop_enter, R.anim.pop_exit)
                        .addToBackStack(null)
                        .replace(R.id.fragment_container, movieDetailsFragment)
                        .commit();
            }
        });
    }


}