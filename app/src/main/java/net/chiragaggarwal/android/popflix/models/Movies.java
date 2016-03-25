package net.chiragaggarwal.android.popflix.models;

import android.database.Cursor;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;

public class Movies {
    private ArrayList<Movie> movies;
    private static final String RESULTS = "results";

    public Movies(Movie... movies) {
        initializeMovies(movies);
    }

    public static Movies fromJson(JSONObject moviesJson) throws JSONException, ParseException {
        JSONArray moviesResults = moviesJson.getJSONArray(RESULTS);
        Movies movies = new Movies();
        for (Integer movieIndex = 0; movieIndex < moviesResults.length(); movieIndex++) {
            JSONObject movieJsonObject = moviesResults.getJSONObject(movieIndex);
            Movie movie = Movie.fromJson(movieJsonObject);
            movies.add(movie);
        }
        return movies;
    }

    public static Movies fromCursor(Cursor moviesCursor) throws ParseException {
        Movies movies = new Movies();
        if (moviesCursor == null || moviesCursor.getCount() == 0) return movies;
        moviesCursor.moveToFirst();
        do {
            Movie movie = Movie.fromCursor(moviesCursor);
            movies.add(movie);
        } while (moviesCursor.moveToNext());
        return movies;
    }

    @Override
    public boolean equals(Object those) {
        if (those == null || (this.getClass() != those.getClass())) return false;
        Movies thoseMovies = (Movies) those;
        for (Integer movieIndex = 0; movieIndex < thoseMovies.count(); movieIndex++) {
            Movie movieOfThisCollection = this.get(movieIndex);
            Movie movieOfThatCollection = thoseMovies.get(movieIndex);
            if (movieOfThisCollection == null || movieOfThatCollection == null) return false;
            else if (!(movieOfThisCollection.equals(movieOfThatCollection))) return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return movies != null ? movies.hashCode() : 0;
    }

    public int count() {
        return this.movies.size();
    }

    public Movie get(Integer movieIndex) {
        if (isMovieIndexInvalid(movieIndex)) return null;
        return this.movies.get(movieIndex);
    }

    private void initializeMovies(Movie[] movies) {
        this.movies = new ArrayList<>();
        for (Integer movieIndex = 0; movieIndex < movies.length; movieIndex++) {
            Movie movie = movies[movieIndex];
            this.movies.add(movie);
        }
    }

    private void add(Movie movie) {
        this.movies.add(movie);
    }

    private boolean isMovieIndexInvalid(Integer movieIndex) {
        return isMovieIndexMoreThanAvailableIndices(movieIndex) ||
                isMovieIndexNegative(movieIndex);
    }

    private boolean isMovieIndexMoreThanAvailableIndices(Integer movieIndex) {
        return (this.movies.size() - 1) < movieIndex;
    }

    private boolean isMovieIndexNegative(Integer movieIndex) {
        return movieIndex < 0;
    }
}
