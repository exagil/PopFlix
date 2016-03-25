package net.chiragaggarwal.android.popflix.data;

import android.content.ContentValues;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;

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

    public Long insert(ContentValues moviesContentValues) {
        return this.sqLiteDatabase.insert(MoviesEntry.TABLE_NAME, null, moviesContentValues);
    }

    public long getCount() {
        return DatabaseUtils.queryNumEntries(this.sqLiteDatabase, MoviesEntry.TABLE_NAME);
    }
}
