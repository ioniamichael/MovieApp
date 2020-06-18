package com.msapps.movieapp.view.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.msapps.movieapp.R;
import com.msapps.movieapp.data.MoviesAppDatabase;
import com.msapps.movieapp.model.MoviesResponse;
import com.msapps.movieapp.viewmodel.SplashActivityViewModel;

import java.util.ArrayList;
import java.util.List;

public class SplashActivity extends AppCompatActivity {

    private final String TAG = "myDebug";

    private SplashActivityViewModel splashActivityViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        splashActivityViewModel = new ViewModelProvider
                .AndroidViewModelFactory(getApplication())
                .create(SplashActivityViewModel.class);

        splashActivityViewModel.getMovieResponseLiveData().observe(this, new Observer<List<MoviesResponse>>() {
            @Override
            public void onChanged(List<MoviesResponse> moviesResponses) {
                if (moviesResponses.size() == 0) {
                    splashActivityViewModel.getAllMovies().observe(SplashActivity.this, new Observer<List<MoviesResponse>>() {
                        @Override
                        public void onChanged(List<MoviesResponse> moviesResponses) {
                            splashActivityViewModel.addAllMoviesToLocalDB(moviesResponses);
                            startActivity(new Intent(SplashActivity.this, MainActivity.class));
                        }
                    });
                }else {
                    startActivity(new Intent(SplashActivity.this, MainActivity.class));
                }
            }
        });

    }


}
