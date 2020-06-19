package com.msapps.movieapp.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.msapps.movieapp.R;
import com.msapps.movieapp.model.MoviesResponse;
import com.msapps.movieapp.viewmodel.MoviesViewModel;

import java.util.List;

public class SplashActivity extends AppCompatActivity {

    private MoviesViewModel mMoviesViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        mMoviesViewModel = new ViewModelProvider
                .AndroidViewModelFactory(getApplication())
                .create(MoviesViewModel.class);

        mMoviesViewModel.getAllMoviesFromLocalDB().observe(this, new Observer<List<MoviesResponse>>() {
            @Override
            public void onChanged(List<MoviesResponse> moviesResponses) {
                if (moviesResponses.size() == 0) {
                    mMoviesViewModel.getAllMoviesFromRemoveService().observe(SplashActivity.this, new Observer<List<MoviesResponse>>() {
                        @Override
                        public void onChanged(List<MoviesResponse> moviesResponses) {
                            mMoviesViewModel.addAllMoviesToLocalDB(moviesResponses);
                            startMainActivity();
                        }
                    });
                } else {
                    startMainActivity();
                }
            }
        });

    }

    private void startMainActivity(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                startActivity(new Intent(SplashActivity.this, MainActivity.class));
                finish();
            }
        }, 1000);
    }


}

