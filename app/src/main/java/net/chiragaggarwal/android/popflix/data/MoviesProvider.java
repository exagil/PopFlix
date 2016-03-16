package net.chiragaggarwal.android.popflix.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;

public class MoviesProvider extends ContentProvider {
    private static final int MOVIES_CODE = 0;
    private static UriMatcher uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        addMoviesUri();
    }

    private static void addMoviesUri() {
        MoviesProvider.uriMatcher.addURI(PopFlixContract.MoviesEntry.PROVIDER_AUTHORITY,
                PopFlixContract.MoviesEntry.TABLE_NAME, MOVIES_CODE);
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
            return PopFlixContract.MoviesEntry.buildMoviesType();
        return null;
    }

    @Nullable
    @Override
    public Uri insert(Uri uri, ContentValues values) {
        return null;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        return 0;
    }
}
