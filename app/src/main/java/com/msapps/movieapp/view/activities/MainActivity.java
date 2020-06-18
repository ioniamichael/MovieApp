package com.msapps.movieapp.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.os.Bundle;

import com.msapps.movieapp.R;
import com.msapps.movieapp.data.MoviesAppDatabase;
import com.msapps.movieapp.model.MoviesResponse;
import com.msapps.movieapp.view.fragments.MoviesListFragment;
import com.msapps.movieapp.viewmodel.MoviesViewModel;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private final String TAG = "myDebug";
    private ArrayList<MoviesResponse> responses;
    private MoviesAppDatabase database;
    private MoviesViewModel moviesViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        database = MoviesAppDatabase.getInstance(getApplicationContext());
        database.getMovieDAO().getAllMoviesFromLocalDB().observe(this, new Observer<List<MoviesResponse>>() {
            @Override
            public void onChanged(List<MoviesResponse> moviesResponses) {

            }
        });
        openMoviesListFragment();
    }

    public void openMoviesListFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new MoviesListFragment())
                .addToBackStack(null)
                .commit();
    }

}