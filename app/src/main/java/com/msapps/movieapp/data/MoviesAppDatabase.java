package com.msapps.movieapp.data;

import android.content.Context;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.msapps.movieapp.Converters;
import com.msapps.movieapp.model.MoviesResponse;

@Database(entities = {MoviesResponse.class}, version = 1)
@TypeConverters({Converters.class})
public abstract class MoviesAppDatabase extends RoomDatabase {

    private static MoviesAppDatabase instance;

    public abstract MovieDao getMovieDAO();

    public static synchronized MoviesAppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context, MoviesAppDatabase.class, "MoviesDB")
                    .fallbackToDestructiveMigration() //If db version was changed it will deleted and created again
                    .addCallback(callback)
                    .build();
        }
        return instance;
    }

    private static RoomDatabase.Callback callback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new InitialDataAsyncTask(instance).execute();
        }
    };

    private static class InitialDataAsyncTask extends AsyncTask<Void, Void, Void> {

        private MovieDao movieDao;

        public InitialDataAsyncTask(MoviesAppDatabase database) {
            movieDao = database.getMovieDAO();
        }

        @Override
        protected Void doInBackground(Void... voids) {
            movieDao.getAllMoviesFromLocalDB();
            return null;
        }
    }

}
