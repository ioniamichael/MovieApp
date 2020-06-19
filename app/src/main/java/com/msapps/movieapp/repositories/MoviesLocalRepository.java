package com.msapps.movieapp.repositories;

import android.app.Application;
import android.os.AsyncTask;

import androidx.lifecycle.LiveData;

import com.msapps.movieapp.data.MovieDao;
import com.msapps.movieapp.data.MoviesAppDatabase;
import com.msapps.movieapp.model.MoviesResponse;

import java.util.List;

public class MoviesLocalRepository {

    private MovieDao movieDao;
    private LiveData<List<MoviesResponse>> movies;

    public MoviesLocalRepository(Application application) {
        MoviesAppDatabase database = MoviesAppDatabase.getInstance(application);
        movieDao = database.getMovieDAO();
    }

    public LiveData<List<MoviesResponse>> getMoviesFromLocalDB() {
        return movieDao.getAllMoviesFromLocalDB();
    }

    public void addMovie(MoviesResponse moviesLocalResponse) {
        new AddMovieAsyncTask(movieDao).execute(moviesLocalResponse);
    }

    private static class AddMovieAsyncTask extends AsyncTask<MoviesResponse, Void, Void> {

        private MovieDao movieDao;

        public AddMovieAsyncTask(MovieDao movieDao) {
            this.movieDao = movieDao;
        }

        @Override
        protected Void doInBackground(MoviesResponse... moviesLocalResponse) {
            movieDao.addMovie(moviesLocalResponse[0]);
            return null;
        }
    }

    public void addAllMovie(List<MoviesResponse> moviesLocalResponses) {
        new AddAllMovieAsyncTask(movieDao).execute(moviesLocalResponses);
    }

    private static class AddAllMovieAsyncTask extends AsyncTask<List<MoviesResponse>, Void, Void> {

        private MovieDao movieDao;

        public AddAllMovieAsyncTask(MovieDao movieDao) {
            this.movieDao = movieDao;
        }


        @Override
        protected Void doInBackground(List<MoviesResponse>... lists) {
            movieDao.addAllMovies(lists[0]);
            return null;
        }
    }

    public MoviesResponse getMovieByTitle(String movieTitle) {
        return movieDao.getMovie(movieTitle);
    }

}
