package net.chiragaggarwal.android.popflix.models;

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

    private void initializeMovies(Movie[] movies) {
        this.movies = new ArrayList<>();
        for (Integer movieIndex = 0; movieIndex < movies.length; movieIndex++) {
            Movie movie = movies[movieIndex];
            this.movies.add(movie);
        }
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

    private void add(Movie movie) {
        this.movies.add(movie);
    }

    public int count() {
        return this.movies.size();
    }

    public Movie get(Integer movieIndex) {
        if (isMovieIndexInvalid(movieIndex)) return null;
        return this.movies.get(movieIndex);
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
