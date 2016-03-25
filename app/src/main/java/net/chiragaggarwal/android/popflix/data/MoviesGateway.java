package net.chiragaggarwal.android.popflix.data;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.support.annotation.NonNull;

import net.chiragaggarwal.android.popflix.models.Movie;

import java.text.ParseException;

import static net.chiragaggarwal.android.popflix.data.PopFlixContract.MoviesEntry;

public class MoviesGateway {
    private static MoviesGateway instance;
    private SQLiteDatabase sqLiteDatabase;

    public static MoviesGateway getInstance(SQLiteDatabase sqLiteDatabase) {
        if (instance == null) instance = new MoviesGateway(sqLiteDatabase);
        return instance;
    }

    private MoviesGateway(SQLiteDatabase sqLiteDatabase) {
        this.sqLiteDatabase = sqLiteDatabase;
    }

    public Long insertIfFavorite(ContentValues moviesContentValues) throws ParseException {
        Movie movie = Movie.fromContentValues(moviesContentValues);
        if (movie.isFavorite())
            return this.sqLiteDatabase.insert(MoviesEntry.TABLE_NAME, null, moviesContentValues);
        return new Long(-1);
    }

    public long getCount() {
        return DatabaseUtils.queryNumEntries(this.sqLiteDatabase, MoviesEntry.TABLE_NAME);
    }

    public Cursor getFavoriteMovies() {
        return this.sqLiteDatabase.query(MoviesEntry.TABLE_NAME, null, favoriteMoviesSelection(),
                new String[]{Movie.FAVORITE_SELECTION_ARGS}, null, null, null);
    }

    @NonNull
    private String favoriteMoviesSelection() {
        return "is_favorite=?";
    }
}
