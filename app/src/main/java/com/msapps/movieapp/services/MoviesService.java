package com.msapps.movieapp.services;

import com.msapps.movieapp.model.MoviesResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface MoviesService {

    @GET("movies.json")
    Call<List<MoviesResponse>> getAllMovies();
}
