package net.chiragaggarwal.android.popflix.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.Nullable;

public class MoviesProvider extends ContentProvider {
    private static final int MOVIES_CODE = 0;
    private static final int MOVIE_CODE = 1;
    private static UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        addMoviesUri();
        addMovieUri();
    }

    @Override
    public boolean onCreate() {
        return true;
    }

    @Nullable
    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        return null;
    }

    @Nullable
    @Override
    public String getType(Uri uri) {
        int matchCode = uriMatcher.match(uri);
        if (matchCode == MOVIES_CODE)
            return PopFlixContract.MoviesEntry.buildMoviesMimeType();
        if (matchCode == MOVIE_CODE)
            return PopFlixContract.MoviesEntry.buildMovieMimeType();
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues movieContentValues) {
        SQLiteDatabase database = DatabaseHelper.getInstance(getContext()).getWritableDatabase();
        Long id = MoviesGateway.getInstance(database).insert(movieContentValues);
        return PopFlixContract.MoviesEntry.buildMovieUri(id);
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }

    private static void addMoviesUri() {
        MoviesProvider.uriMatcher.addURI(PopFlixContract.MoviesEntry.PROVIDER_AUTHORITY,
                PopFlixContract.MoviesEntry.MOVIE_PATH, MOVIE_CODE);
    }

    private static void addMovieUri() {
        MoviesProvider.uriMatcher.addURI(PopFlixContract.MoviesEntry.PROVIDER_AUTHORITY,
                PopFlixContract.MoviesEntry.MOVIES_PATH, MOVIES_CODE);
    }
}
