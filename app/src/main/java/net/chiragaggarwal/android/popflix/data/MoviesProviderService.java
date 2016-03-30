package net.chiragaggarwal.android.popflix.data;

import android.content.Context;

import net.chiragaggarwal.android.popflix.models.Movie;

public class MoviesProviderService {
    private Context context;

    public MoviesProviderService(Context context) {
        this.context = context;
    }

    public void saveFavoritedMovie(Movie movie) {
        this.context.getContentResolver().insert(
                PopFlixContract.MoviesEntry.buildMoviesUri(),
                movie.toContentValues()
        );
    }

    public boolean containsMovieById(String s) {
        return false;
    }

    public void deleteFavoritedMovie(String movieIdString) {

    }
}
