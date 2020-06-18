package com.msapps.movieapp.data;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.msapps.movieapp.model.MoviesResponse;

import java.util.List;

@Dao
public interface MovieDao {

    @Insert
    void addMovie(MoviesResponse moviesResponseLocal);

    @Insert
    void addAllMovies(List<MoviesResponse> moviesResponseList);

    @Query("select * from movies")
    LiveData<List<MoviesResponse>> getAllMoviesFromLocalDB();

    @Query("select * from movies where movie_title ==:title")
    LiveData<MoviesResponse> getMovie(String title);

}
