package net.chiragaggarwal.android.popflix.data;

import android.content.Context;
import android.database.Cursor;

import net.chiragaggarwal.android.popflix.models.Movie;
import net.chiragaggarwal.android.popflix.models.Movies;

import java.text.ParseException;

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

    public boolean containsFavoriteMovieById(String movieIdString) {
        Cursor moviesCursor = this.context.getContentResolver().query(
                PopFlixContract.MoviesEntry.buildMovieUri(movieIdString),
                null,
                PopFlixContract.MoviesEntry.FAVORITE_SELECTION,
                new String[]{Movie.FAVORITE_SELECTION_ARGS},
                null);
        return isNotNull(moviesCursor) && hasElements(moviesCursor);
    }

    public int deleteFavoritedMovie(String movieIdString) {
        return this.context.getContentResolver().delete(
                PopFlixContract.MoviesEntry.buildMovieUri(movieIdString),
                null, null);
    }

    public Movies loadFavoriteMovies() throws ParseException {
        Cursor moviesCursor = this.context.getContentResolver().query(
                PopFlixContract.MoviesEntry.buildMoviesUri(),
                null,
                PopFlixContract.MoviesEntry.FAVORITE_SELECTION,
                new String[]{Movie.FAVORITE_SELECTION_ARGS},
                null
        );
        return Movies.fromCursor(moviesCursor);
    }

    public Movies markFavoritedMoviesAsFavorite(Movies movies) {
        for (Movie movie : movies) {
            if (this.containsFavoriteMovieById(movie.idString()))
                movie.toggleFavorite();
        }
        return movies;
    }

    private boolean isNotNull(Cursor moviesCursor) {
        return moviesCursor != null;
    }

    private boolean hasElements(Cursor moviesCursor) {
        return moviesCursor.getCount() > 0;
    }
}
