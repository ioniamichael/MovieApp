package com.msapps.movieapp.view.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.msapps.movieapp.R;
import com.msapps.movieapp.model.MoviesResponse;
import com.msapps.movieapp.viewmodel.MoviesViewModel;

import java.util.List;

public class SplashActivity extends AppCompatActivity {

    private final String TAG = "myDebug";

    private MoviesViewModel moviesViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        moviesViewModel = new ViewModelProvider
                .AndroidViewModelFactory(getApplication())
                .create(MoviesViewModel.class);

        moviesViewModel.getAllMoviesFromLocalDB().observe(this, new Observer<List<MoviesResponse>>() {
            @Override
            public void onChanged(List<MoviesResponse> moviesResponses) {
                if (moviesResponses.size() == 0) {
                    moviesViewModel.getAllMoviesFromRemoveService().observe(SplashActivity.this, new Observer<List<MoviesResponse>>() {
                        @Override
                        public void onChanged(List<MoviesResponse> moviesResponses) {
                            moviesViewModel.addAllMoviesToLocalDB(moviesResponses);
                            startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        }
                    });
                } else {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                }
            }
        });

    }


}

