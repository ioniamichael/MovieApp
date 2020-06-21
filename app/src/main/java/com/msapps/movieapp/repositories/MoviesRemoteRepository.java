package com.msapps.movieapp.repositories;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.google.android.material.snackbar.Snackbar;
import com.msapps.movieapp.model.MoviesResponse;
import com.msapps.movieapp.services.MoviesService;
import com.msapps.movieapp.services.RetrofitInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesRemoteRepository {

    private Application mApplication;

    public MoviesRemoteRepository(Application mApplication) {
        this.mApplication = mApplication;
    }

    private MutableLiveData<List<MoviesResponse>> mMoviesResponseMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<List<MoviesResponse>> getMoviesResponseMutableLiveData() {

        MoviesService moviesService = RetrofitInstance.getService();
        Call<List<MoviesResponse>> call = moviesService.getAllMovies();
        call.enqueue(new Callback<List<MoviesResponse>>() {
            @Override
            public void onResponse(Call<List<MoviesResponse>> call, Response<List<MoviesResponse>> moviesRemoteResponse) {
                mMoviesResponseMutableLiveData.setValue(moviesRemoteResponse.body());
                Log.d("myDebug", "onResponse: ");
            }

            @Override
            public void onFailure(Call<List<MoviesResponse>> call, Throwable t) {
                Toast.makeText(mApplication.getApplicationContext(), "Server is down", Toast.LENGTH_LONG).show();
            }
        });

        return mMoviesResponseMutableLiveData;
    }

}
