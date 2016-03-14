package net.chiragaggarwal.android.popflix.repository;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import java.util.ArrayList;
import java.util.Arrays;

public class DatabaseHelperTest extends AndroidTestCase {
    @Override
    public void setUp() throws Exception {
        super.setUp();
        PopFlixContract popFlixContract = PopFlixContract.getInstance(mContext);
        mContext.deleteDatabase(popFlixContract.getDatabaseName());
    }

    public void shouldCreateDatabaseSuccessfully() {
        String tablesSelectionStatement = "SELECT name FROM sqlite_master WHERE type='table'";
        SQLiteDatabase db = DatabaseHelper.getInstance(getContext()).getReadableDatabase();
        Cursor tablesCursor = db.rawQuery(tablesSelectionStatement, null);
        assertTrue("This means your database has not been implemented properly", tablesCursor.moveToFirst());
        assertTrue("Database is not open, you haven't implemented it the right way", db.isOpen());
    }

    public void shouldCreateRequiredTablesForPopFlix() {
        ArrayList<String> tablesNames = new ArrayList<>();
        tablesNames.add("movies");
        tablesNames.add("videos");
        tablesNames.add("reviews");
        SQLiteDatabase db = DatabaseHelper.getInstance(getContext()).getReadableDatabase();
        Cursor tablesCursor = db.rawQuery("SELECT name FROM " + "sqlite_master" + " WHERE type='table'", null);
        int nameColumnIndex = tablesCursor.getColumnIndex("name");
        tablesCursor.moveToFirst();
        do tablesNames.remove(tablesCursor.getString(nameColumnIndex));
        while (tablesCursor.moveToNext());
        assertEquals(0, tablesNames.size());
    }

    public void moviesTableShouldHaveTheRightColumns() {
        SQLiteDatabase database = DatabaseHelper.getInstance(getContext()).getReadableDatabase();
        Cursor moviesCursor = database.rawQuery("SELECT * FROM movies", null);
        moviesCursor.moveToFirst();
        String[] fetchedColumnNames = moviesCursor.getColumnNames();
        String[] expectedColumnNames = new String[]{"_id", "movie_id", "original_title", "poster_path", "release_date", "popularity", "vote_average", "overview"};
        assertTrue(Arrays.equals(fetchedColumnNames, expectedColumnNames));
    }
}
