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
    private MoviesGateway moviesGateway;

    @Override
    protected void setUp() throws Exception {
        this.context = getContext();
        this.database = DatabaseHelper.getInstance(context).getWritableDatabase();
        this.database.beginTransaction();
        this.moviesGateway = MoviesGateway.getInstance(this.database);
    }

    @Override
    protected void tearDown() throws Exception {
        this.database.endTransaction();
    }

    @Test
    public void shouldHavePositiveCountWhenMoviesArePresent() {
        Movie movie = new Movie(1, "Example", new Date(), "nothing/everything", 12.34, 12.34, "example", true);
        ContentValues movieContentValues = movie.toContentValues();
        this.database.insert(PopFlixContract.MoviesEntry.TABLE_NAME, null, movieContentValues);
        assertEquals(1, moviesGateway.getCount());
    }

    @Test
    public void shouldHaveZeroCountWhenNoMoviesPresent() {
        assertEquals(0, moviesGateway.getCount());
    }
}