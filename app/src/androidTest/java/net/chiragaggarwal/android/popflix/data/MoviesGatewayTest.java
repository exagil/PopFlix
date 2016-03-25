package net.chiragaggarwal.android.popflix.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import net.chiragaggarwal.android.popflix.models.Movie;

import org.junit.Test;

import java.util.Date;

public class MoviesGatewayTest extends AndroidTestCase {
    private Context context;
    private SQLiteDatabase database;

    @Override
    protected void setUp() throws Exception {
        this.context = getContext();
        this.database = DatabaseHelper.getInstance(context).getWritableDatabase();
        this.database.beginTransaction();
    }

    @Override
    protected void tearDown() throws Exception {
        this.database.endTransaction();
    }

    @Test
    public void shouldHavePositiveCountWhenMoviesArePresent() {
        MoviesGateway moviesGateway = MoviesGateway.getInstance(this.database);
        Movie movie = new Movie(1, "Example", new Date(), "nothing/everything", 12.34, 12.34, "example", true);
        ContentValues movieContentValues = movie.toContentValues();
        long insert = this.database.insert(PopFlixContract.MoviesEntry.TABLE_NAME, null, movieContentValues);
        assertEquals(1, moviesGateway.getCount());
    }

    @Test
    public void shouldHaveZeroCountWhenNoMoviesPresent() {
        MoviesGateway moviesGateway = MoviesGateway.getInstance(this.database);
        assertEquals(0, moviesGateway.getCount());
    }
}