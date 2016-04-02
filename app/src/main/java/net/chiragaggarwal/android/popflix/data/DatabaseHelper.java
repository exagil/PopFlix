package net.chiragaggarwal.android.popflix.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.NonNull;

import static net.chiragaggarwal.android.popflix.data.PopFlixContract.MoviesEntry;

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
    private static final String BOOLEAN = " BOOLEAN ";
    private static final String UNIQUE = " UNIQUE ";
    private static final String DROP_TABLE = "DROP TABLE ";
    private static DatabaseHelper databaseHelper;

    public static DatabaseHelper getInstance(Context context) {
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
        return "CREATE TABLE " + MoviesEntry.TABLE_NAME +
                START_BRACKET +
                MoviesEntry._ID + INTEGER + PRIMARY_KEY + AUTOINCREMENT + COMMA +
                MoviesEntry.MOVIE_ID + NUMERIC + NOT_NULL + UNIQUE + COMMA +
                MoviesEntry.ORIGINAL_TITLE + STRING + NOT_NULL + COMMA +
                MoviesEntry.POSTER_PATH + STRING + COMMA +
                MoviesEntry.RELEASE_DATE + STRING + COMMA +
                MoviesEntry.POPULARITY + NUMERIC + COMMA +
                MoviesEntry.VOTE_AVERAGE + NUMERIC + COMMA +
                MoviesEntry.OVERVIEW + TEXT + COMMA +
                MoviesEntry.IS_FAVORITE + BOOLEAN +
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

    public void reset() {
        SQLiteDatabase writableDatabase = this.getWritableDatabase();
        writableDatabase.execSQL(DROP_TABLE + PopFlixContract.ReviewsEntry.TABLE_NAME);
        writableDatabase.execSQL(DROP_TABLE + PopFlixContract.VideosEntry.TABLE_NAME);
        writableDatabase.execSQL(DROP_TABLE + PopFlixContract.MoviesEntry.TABLE_NAME);
        onCreate(writableDatabase);
    }
}
