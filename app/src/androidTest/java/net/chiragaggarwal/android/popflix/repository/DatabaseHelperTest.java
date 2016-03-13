package net.chiragaggarwal.android.popflix.repository;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import org.junit.After;
import org.junit.Test;

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
}
