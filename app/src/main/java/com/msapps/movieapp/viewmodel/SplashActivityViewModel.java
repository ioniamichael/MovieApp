package com.msapps.movieapp.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.msapps.movieapp.model.MoviesResponse;
import com.msapps.movieapp.repositories.DataBaseRepository;
import com.msapps.movieapp.repositories.MoviesRepository;

import java.util.List;

public class SplashActivityViewModel extends AndroidViewModel {

    private DataBaseRepository dataBaseRepository;
    private MoviesRepository repository = new MoviesRepository();
    private LiveData<List<MoviesResponse>> movieResponseLiveData;


    public SplashActivityViewModel(@NonNull Application application) {
        super(application);
        dataBaseRepository = new DataBaseRepository(application);
    }

    public LiveData<List<MoviesResponse>> getAllMovies() {
        return repository.getMoviesResponseMutableLiveData();
    }

    public LiveData<List<MoviesResponse>> getMovieResponseLiveData() {
        movieResponseLiveData = dataBaseRepository.getMoviesFromLocalDB();
        return movieResponseLiveData;
    }

    public void addAllMoviesToLocalDB(List<MoviesResponse> moviesResponses){
        dataBaseRepository.addAllMovie(moviesResponses);
    }

    public void addMovie(MoviesResponse movie){
        dataBaseRepository.addMovie(movie);
    }


}
