package net.chiragaggarwal.android.popflix.data;

import android.content.Context;
import android.database.Cursor;

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

    public boolean containsMovieById(String movieIdString) {
        Cursor moviesCursor = this.context.getContentResolver().query(
                PopFlixContract.MoviesEntry.buildMoviesUri(),
                null,
                PopFlixContract.MoviesEntry.MOVIE_ID_SELECTION,
                new String[]{movieIdString},
                null);
        return isNotNull(moviesCursor) && hasElements(moviesCursor);
    }

    public void deleteFavoritedMovie(String movieIdString) {

    }

    private boolean isNotNull(Cursor moviesCursor) {
        return moviesCursor != null;
    }

    private boolean hasElements(Cursor moviesCursor) {
        return moviesCursor.getCount() > 0;
    }
}
