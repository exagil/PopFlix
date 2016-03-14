package net.chiragaggarwal.android.popflix.repository;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import org.junit.After;
import org.junit.Test;

import java.util.ArrayList;

public class DatabaseHelperTest extends AndroidTestCase {

    @After
    public void afterEach() {
        PopFlixContract popFlixContract = PopFlixContract.getInstance(getContext());
        getContext().deleteDatabase(popFlixContract.getDatabaseName());
    }

    @Test
    public void shouldCreateDatabaseSuccessfully() {
        String tablesSelectionStatement = "SELECT name FROM sqlite_master WHERE type='table'";
        SQLiteDatabase db = DatabaseHelper.getInstance(getContext()).getReadableDatabase();
        Cursor tablesCursor = db.rawQuery(tablesSelectionStatement, null);
        assertTrue("This means your database has not been implemented properly", tablesCursor.moveToFirst());
        assertTrue("Database is not open, you haven't implemented it the right way", db.isOpen());
    }

    @Test
    public void shouldCreateRequiredTablesForPopFlix() {
        ArrayList<String> tablesNames = new ArrayList<>();
        tablesNames.add(PopFlixContract.MoviesEntry.TABLE_NAME);
        tablesNames.add(PopFlixContract.VideosEntry.TABLE_NAME);
        tablesNames.add(PopFlixContract.ReviewsEntry.TABLE_NAME);
        SQLiteDatabase db = DatabaseHelper.getInstance(getContext()).getReadableDatabase();
        Cursor tablesCursor = db.rawQuery("SELECT name FROM " + "sqlite_master" + " WHERE type='table'", null);
        int nameColumnIndex = tablesCursor.getColumnIndex("name");
        tablesCursor.moveToFirst();
        do tablesNames.remove(tablesCursor.getString(nameColumnIndex));
        while (tablesCursor.moveToNext());
        assertEquals(0, tablesNames.size());
    }
}
