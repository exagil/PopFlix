package net.chiragaggarwal.android.popflix.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.Nullable;

import net.chiragaggarwal.android.popflix.models.Movie;

import java.text.ParseException;
import java.util.Arrays;

import static net.chiragaggarwal.android.popflix.data.PopFlixContract.MoviesEntry;

public class MoviesProvider extends ContentProvider {
    private static final int MOVIES_ENDPOINT = 0;
    private static final int MOVIE_ENDPOINT = 1;
    private static UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        addMoviesUri();
        addMovieUri();
    }

    private SQLiteOpenHelper databaseHelper;

    @Override
    public boolean onCreate() {
        this.databaseHelper = DatabaseHelper.getInstance(getContext());
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        Cursor movies = null;
        int matchCode = uriMatcher.match(uri);
        if (matchCode == MOVIES_ENDPOINT && isFavoriteSelection(selection, selectionArgs)) {
            movies = getMoviesGateway().getFavoriteMovies();
        } else if (matchCode == MOVIE_ENDPOINT && isFavoriteSelection(selection, selectionArgs)) {
            String movieId = uri.getLastPathSegment();
            movies = getMoviesGateway().getFavoriteMovie(movieId);
        }
        return movies;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        int matchCode = uriMatcher.match(uri);
        if (matchCode == MOVIES_ENDPOINT)
            return MoviesEntry.buildMoviesMimeType();
        if (matchCode == MOVIE_ENDPOINT)
            return MoviesEntry.buildMovieMimeType();
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues movieContentValues) {
        int matchCode = uriMatcher.match(uri);
        try {
            if (matchCode == MOVIES_ENDPOINT) {
                SQLiteDatabase database = databaseHelper.getWritableDatabase();
                Long id = MoviesGateway.getInstance(database).insertIfFavorite(movieContentValues);
                return MoviesEntry.buildMovieUri(id);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        int matchCode = uriMatcher.match(uri);
        int deletedMovies = 0;
        if (matchCode == MOVIE_ENDPOINT) {
            SQLiteDatabase database = databaseHelper.getWritableDatabase();
            String movieId = uri.getLastPathSegment();
            deletedMovies = MoviesGateway.getInstance(database).delete(movieId);
        }
        return deletedMovies;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

    private static void addMovieUri() {
        MoviesProvider.uriMatcher.addURI(MoviesEntry.PROVIDER_AUTHORITY,
                MoviesEntry.MOVIE_PATH, MOVIE_ENDPOINT);
    }

    private static void addMoviesUri() {
        MoviesProvider.uriMatcher.addURI(MoviesEntry.PROVIDER_AUTHORITY,
                MoviesEntry.MOVIES_PATH, MOVIES_ENDPOINT);
    }

    private boolean isFavoriteSelection(String selection, String[] selectionArgs) {
        return Arrays.asList(selection).contains(MoviesEntry.FAVORITE_SELECTION) &&
                Arrays.asList(selectionArgs).contains(Movie.FAVORITE_SELECTION_ARGS);
    }

    private MoviesGateway getMoviesGateway() {
        SQLiteDatabase database = this.databaseHelper.getWritableDatabase();
        return MoviesGateway.getInstance(database);
    }
}
