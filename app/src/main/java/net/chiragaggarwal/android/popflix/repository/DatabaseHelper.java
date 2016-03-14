package net.chiragaggarwal.android.popflix.repository;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String INTEGER = " INTEGER ";
    private static final String PRIMARY_KEY = " PRIMARY KEY ";
    private static final String AUTOINCREMENT = " AUTOINCREMENT ";
    private static final String START_BRACKET = "(";
    private static final String END_BRACKET = ")";
    private static DatabaseHelper databaseHelper;

    public static SQLiteOpenHelper getInstance(Context context) {
        if (databaseHelper == null) {
            String databaseName = PopFlixContract.getInstance(context).getDatabaseName();
            databaseHelper = new DatabaseHelper(context, databaseName, null, DATABASE_VERSION);
        }
        return databaseHelper;
    }

    private DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.beginTransaction();
        db.execSQL(buildCreateMoviesTableSqlStatement());
        db.execSQL(buildCreateVideosTableSqlStatement());
        db.execSQL(buildCreateReviewsTableSqlStatement());
        db.setTransactionSuccessful();
        db.endTransaction();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    @NonNull
    private String buildCreateMoviesTableSqlStatement() {
        return "CREATE TABLE " + PopFlixContract.MoviesEntry.TABLE_NAME +
                START_BRACKET +
                PopFlixContract.MoviesEntry._ID + INTEGER + PRIMARY_KEY + AUTOINCREMENT +
                END_BRACKET;
    }

    @NonNull
    private String buildCreateVideosTableSqlStatement() {
        return "CREATE TABLE " + PopFlixContract.VideosEntry.TABLE_NAME +
                START_BRACKET +
                PopFlixContract.MoviesEntry._ID + INTEGER + PRIMARY_KEY + AUTOINCREMENT +
                END_BRACKET;
    }

    @NonNull
    private String buildCreateReviewsTableSqlStatement() {
        return "CREATE TABLE " + PopFlixContract.ReviewsEntry.TABLE_NAME +
                START_BRACKET +
                PopFlixContract.MoviesEntry._ID + INTEGER + PRIMARY_KEY + AUTOINCREMENT +
                END_BRACKET;
    }
}
