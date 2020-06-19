package com.msapps.movieapp.repositories;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.msapps.movieapp.model.MoviesResponse;
import com.msapps.movieapp.services.MoviesService;
import com.msapps.movieapp.services.RetrofitInstance;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MoviesRemoteRepository {

    private MutableLiveData<List<MoviesResponse>> mMoviesResponseMutableLiveData = new MutableLiveData<>();

    public MutableLiveData<List<MoviesResponse>> getMoviesResponseMutableLiveData() {

        MoviesService moviesService = RetrofitInstance.getService();
        Call<List<MoviesResponse>> call = moviesService.getAllMovies();
        call.enqueue(new Callback<List<MoviesResponse>>() {
            @Override
            public void onResponse(Call<List<MoviesResponse>> call, Response<List<MoviesResponse>> moviesRemoteResponse) {
                mMoviesResponseMutableLiveData.setValue(moviesRemoteResponse.body());
            }

            @Override
            public void onFailure(Call<List<MoviesResponse>> call, Throwable t) {
            }
        });

        return mMoviesResponseMutableLiveData;
    }

}
