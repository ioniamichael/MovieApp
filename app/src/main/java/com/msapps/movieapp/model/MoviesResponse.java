package com.msapps.movieapp.model;

import android.os.Parcel;
import android.os.Parcelable;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

@Entity(tableName = "movies")
public class MoviesResponse implements Parcelable {

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "movie_title")
    @SerializedName("title")
    @Expose
    private String title;

    @ColumnInfo(name = "movie_image")
    @SerializedName("image")
    @Expose
    private String image;

    @ColumnInfo(name = "movie_rating")
    @SerializedName("rating")
    @Expose
    private double rating;

    @ColumnInfo(name = "movie_release_year")
    @SerializedName("releaseYear")
    @Expose
    private int releaseYear;

    @ColumnInfo(name = "movie_genre")
    @SerializedName("genre")
    @Expose
    private List<String> genre = new ArrayList<>();
    public final static Parcelable.Creator<MoviesResponse> CREATOR = new Creator<MoviesResponse>() {


        @SuppressWarnings({
                "unchecked"
        })
        public MoviesResponse createFromParcel(Parcel in) {
            return new MoviesResponse(in);
        }

        public MoviesResponse[] newArray(int size) {
            return (new MoviesResponse[size]);
        }

    };

    public MoviesResponse(String title, String image, double rating, int releaseYear, List<String> genre) {
        this.title = title;
        this.image = image;
        this.rating = rating;
        this.releaseYear = releaseYear;
        this.genre = genre;
    }

    @Ignore
    protected MoviesResponse(Parcel in) {
        this.title = ((String) in.readValue((String.class.getClassLoader())));
        this.image = ((String) in.readValue((String.class.getClassLoader())));
        this.rating = ((double) in.readValue((double.class.getClassLoader())));
        this.releaseYear = ((int) in.readValue((int.class.getClassLoader())));
        in.readList(this.genre, (java.lang.String.class.getClassLoader()));
    }

    @Ignore
    public MoviesResponse() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getReleaseYear() {
        return releaseYear;
    }

    public void setReleaseYear(int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public List<String> getGenre() {
        return genre;
    }

    public void setGenre(List<String> genre) {
        this.genre = genre;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(title);
        dest.writeValue(image);
        dest.writeValue(rating);
        dest.writeValue(releaseYear);
        dest.writeList(genre);
    }

    public int describeContents() {
        return 0;
    }
}
