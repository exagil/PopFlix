package net.chiragaggarwal.android.popflix.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import net.chiragaggarwal.android.popflix.models.Movie;

import org.junit.Test;

import java.text.ParseException;
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

    @Test
    public void shouldIncreaseMoviesCountByOneWhenTriedToInsertAValidFavoriteMovie() throws ParseException {
        Movie movie = new Movie(1, "Example", new Date(), "example/another_example", 12.34, 56.78, "overview", true);
        ContentValues moviesContentValues = movie.toContentValues();
        moviesGateway.insertIfFavorite(moviesContentValues);
        assertEquals(1, moviesGateway.getCount());
    }

    @Test
    public void shouldNotInsertFavoriteMovieWithNoOverview() throws ParseException {
        Movie movie = new Movie(1, null, new Date(), "example/another_example", 12.34, 56.78, "overview", true);
        ContentValues moviesContentValues = movie.toContentValues();
        moviesGateway.insertIfFavorite(moviesContentValues);
        assertEquals(0, moviesGateway.getCount());
    }

    @Test
    public void shouldNotInsertAFavoriteMovieWithNoMovieId() throws ParseException {
        Movie movie = new Movie(null, "original_title", new Date(), "example/another_example", 12.34, 56.78, "overview", true);
        ContentValues moviesContentValues = movie.toContentValues();
        moviesGateway.insertIfFavorite(moviesContentValues);
        assertEquals(0, moviesGateway.getCount());
    }

    @Test
    public void shouldNotInsertANonFavoriteMovie() throws ParseException {
        Movie movie = new Movie(1, "original_title", new Date(), "example/another_example", 12.34, 56.78, "overview", false);
        ContentValues moviesContentValues = movie.toContentValues();
        moviesGateway.insertIfFavorite(moviesContentValues);
        assertEquals(0, moviesGateway.getCount());
    }

    @Test
    public void shouldNotInsertMovieWithSameMovieIdMoreThanOnce() throws ParseException {
        Movie movie = new Movie(1, "original_title", new Date(), "example/another_example", 12.34, 56.78, "overview", true);
        ContentValues moviesContentValues = movie.toContentValues();
        moviesGateway.insertIfFavorite(moviesContentValues);
        moviesGateway.insertIfFavorite(moviesContentValues);
        assertEquals(1, moviesGateway.getCount());
    }

    @Test
    public void shouldFetchNoFavoriteMoviesIfNoMoviesPresent() {
        Cursor cursor = moviesGateway.getFavoriteMovies();
        assertEquals(0, cursor.getCount());
    }

    @Test
    public void shouldKnowHowToFetchFavoriteMovies() throws ParseException {
        Movie movie = new Movie(1, "original_title", new Date(), "example/another_example", 12.34, 56.78, "overview", true);
        ContentValues moviesContentValues = movie.toContentValues();
        this.database.insert(PopFlixContract.MoviesEntry.TABLE_NAME, null, moviesContentValues);

        Cursor cursor = moviesGateway.getFavoriteMovies();
        assertEquals(1, cursor.getCount());
    }

    @Test
    public void shouldNotFetchNonFavoriteMoviesWhileFetchingFavoriteMovies() throws ParseException {
        Movie movie = new Movie(1, "original_title", new Date(), "example/another_example", 12.34, 56.78, "overview", false);
        ContentValues moviesContentValues = movie.toContentValues();
        this.database.insert(PopFlixContract.MoviesEntry.TABLE_NAME, null, moviesContentValues);

        Cursor cursor = moviesGateway.getFavoriteMovies();
        assertEquals(0, cursor.getCount());
    }

    @Test
    public void shouldDeleteMovieWithParticularMovieId() {
        Movie movie = new Movie(1, "original_title", new Date(), "example/another_example", 12.34, 56.78, "overview", false);
        ContentValues moviesContentValues = movie.toContentValues();
        long movieId = this.database.insert(PopFlixContract.MoviesEntry.TABLE_NAME, null, moviesContentValues);
        int numberOfDeletedMovies = MoviesGateway.getInstance(database).delete(movieId);
        assertEquals(1, numberOfDeletedMovies);
    }

    @Test
    public void shouldNotDeleteInexistantMovie() {
        long invalidMovieId = 19;
        int numberOfDeletedMovies = MoviesGateway.getInstance(database).delete(invalidMovieId);
        assertEquals(0, numberOfDeletedMovies);
    }

    @Test
    public void shouldDeleteMovieWithParticularMovieIdString() {
        Movie movie = new Movie(1, "original_title", new Date(), "example/another_example", 12.34, 56.78, "overview", false);
        ContentValues moviesContentValues = movie.toContentValues();
        long movieId = this.database.insert(PopFlixContract.MoviesEntry.TABLE_NAME, null, moviesContentValues);
        String movieIdString = String.valueOf(movieId);
        int numberOfDeletedMovies = MoviesGateway.getInstance(database).delete(movieIdString);
        assertEquals(1, numberOfDeletedMovies);
    }

    @Test
    public void shouldNotDeleteInexistantMovieUsingMovieString() {
        String invalidMovieIdString = "19";
        int numberOfDeletedMovies = MoviesGateway.getInstance(database).delete(invalidMovieIdString);
        assertEquals(0, numberOfDeletedMovies);
    }
}
