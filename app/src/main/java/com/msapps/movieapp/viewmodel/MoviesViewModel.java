package com.msapps.movieapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.msapps.movieapp.model.MoviesResponse;
import com.msapps.movieapp.repositories.MoviesLocalRepository;
import com.msapps.movieapp.repositories.MoviesRemoteRepository;

import java.util.List;

public class MoviesViewModel extends AndroidViewModel {

    private MoviesLocalRepository mMoviesLocalRepository;
    private MoviesRemoteRepository mMoviesRemoteRepository = new MoviesRemoteRepository();
    private LiveData<List<MoviesResponse>> mMovieResponseLiveData;


    public MoviesViewModel(@NonNull Application application) {
        super(application);
        mMoviesLocalRepository = new MoviesLocalRepository(application);
    }

    public LiveData<List<MoviesResponse>> getAllMoviesFromRemoveService() {
        return mMoviesRemoteRepository.getMoviesResponseMutableLiveData();
    }

    public LiveData<List<MoviesResponse>> getAllMoviesFromLocalDB() {
        mMovieResponseLiveData = mMoviesLocalRepository.getMoviesFromLocalDB();
        return mMovieResponseLiveData;
    }

    public void addAllMoviesToLocalDB(List<MoviesResponse> moviesResponses){
        mMoviesLocalRepository.addAllMovie(moviesResponses);
    }

    public void addMovie(MoviesResponse movie){
        mMoviesLocalRepository.addMovie(movie);
    }


}
