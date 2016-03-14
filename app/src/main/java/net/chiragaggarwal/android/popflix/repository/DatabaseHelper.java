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
    private static final String NOT_NULL = " NOT NULL ";
    private static final String COMMA = ", ";
    private static final String STRING = " STRING ";
    private static final String NUMERIC = " NUMERIC ";
    private static final String TEXT = " TEXT ";
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
    public SQLiteDatabase getReadableDatabase() {
        return super.getReadableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        System.out.println();
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
                PopFlixContract.MoviesEntry._ID + INTEGER + PRIMARY_KEY + AUTOINCREMENT + COMMA +
                PopFlixContract.MoviesEntry.MOVIE_ID + INTEGER + NOT_NULL + COMMA +
                PopFlixContract.MoviesEntry.ORIGINAL_TITLE + STRING + NOT_NULL + COMMA +
                PopFlixContract.MoviesEntry.POSTER_PATH + STRING + COMMA +
                PopFlixContract.MoviesEntry.RELEASE_DATE + STRING + COMMA +
                PopFlixContract.MoviesEntry.POPULARITY + NUMERIC + COMMA +
                PopFlixContract.MoviesEntry.VOTE_AVERAGE + NUMERIC + COMMA +
                PopFlixContract.MoviesEntry.OVERVIEW + TEXT +
                END_BRACKET;
    }

    @NonNull
    private String buildCreateVideosTableSqlStatement() {
        return "CREATE TABLE " + PopFlixContract.VideosEntry.TABLE_NAME +
                START_BRACKET +
                PopFlixContract.VideosEntry._ID + INTEGER + PRIMARY_KEY + AUTOINCREMENT + COMMA +
                PopFlixContract.VideosEntry.VIDEO_ID + INTEGER + NOT_NULL + COMMA +
                PopFlixContract.VideosEntry.LANGUAGE_CODE + STRING + NOT_NULL + COMMA +
                PopFlixContract.VideosEntry.COUNTRY_CODE + STRING + COMMA +
                PopFlixContract.VideosEntry.KEY + STRING + NOT_NULL + COMMA +
                PopFlixContract.VideosEntry.NAME + STRING + COMMA +
                PopFlixContract.VideosEntry.WEBSITE + STRING + NOT_NULL + COMMA +
                PopFlixContract.VideosEntry.TYPE + STRING +
                END_BRACKET;
    }

    @NonNull
    private String buildCreateReviewsTableSqlStatement() {
        return "CREATE TABLE " + PopFlixContract.ReviewsEntry.TABLE_NAME +
                START_BRACKET +
                PopFlixContract.ReviewsEntry._ID + INTEGER + PRIMARY_KEY + AUTOINCREMENT + COMMA +
                PopFlixContract.ReviewsEntry.REVIEW_ID + STRING + NOT_NULL + COMMA +
                PopFlixContract.ReviewsEntry.AUTHOR + STRING + NOT_NULL + COMMA +
                PopFlixContract.ReviewsEntry.CONTENT + TEXT + NOT_NULL + COMMA +
                PopFlixContract.ReviewsEntry.URL_STRING + STRING +
                END_BRACKET;
    }
}
