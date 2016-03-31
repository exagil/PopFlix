package net.chiragaggarwal.android.popflix.data;

import android.content.Context;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import net.chiragaggarwal.android.popflix.models.Movie;

import org.junit.Test;

import java.text.ParseException;
import java.util.Date;

public class MoviesProviderServiceTest extends AndroidTestCase {
    private Context context;
    private SQLiteDatabase database;
    private MoviesProviderService moviesProviderService;

    @Override
    protected void setUp() throws Exception {
        this.context = getContext();
        this.database = DatabaseHelper.getInstance(context).getReadableDatabase();
        this.moviesProviderService = new MoviesProviderService(this.context);
        this.database.beginTransaction();
    }

    @Override
    protected void tearDown() throws Exception {
        this.database.endTransaction();
    }

    @Test
    public void shouldNotInsertANonFavoriteMovie() {
        Movie movie = new Movie(1, "Example", new Date(Long.parseLong("1458098572336")), null, 43.169504, 7.21, "Example Review", false);
        this.moviesProviderService.saveFavoritedMovie(movie);
        assertEquals(0, DatabaseUtils.queryNumEntries(database, PopFlixContract.MoviesEntry.TABLE_NAME));
    }

    @Test
    public void shouldInsertMoviesUniquelyBasedOnMovieId() {
        Movie movie = new Movie(1, "Example", new Date(Long.parseLong("1458098572336")), null, 43.169504, 7.21, "Example Review", true);
        this.moviesProviderService.saveFavoritedMovie(movie);
        this.moviesProviderService.saveFavoritedMovie(movie);
        assertEquals(1, DatabaseUtils.queryNumEntries(database, PopFlixContract.MoviesEntry.TABLE_NAME));
    }

    @Test
    public void shouldIncreaseMoviesCountByOneWhenTriedToInsertAValidFavoriteMovie() throws ParseException {
        Movie movie = new Movie(1, "Example", new Date(), "example/another_example", 12.34, 56.78, "overview", true);
        this.moviesProviderService.saveFavoritedMovie(movie);
        assertEquals(1, DatabaseUtils.queryNumEntries(database, PopFlixContract.MoviesEntry.TABLE_NAME));
    }

    @Test
    public void shouldNotInsertFavoriteMovieWithNoOverview() throws ParseException {
        Movie movie = new Movie(1, null, new Date(), "example/another_example", 12.34, 56.78, "overview", true);
        this.moviesProviderService.saveFavoritedMovie(movie);
        assertEquals(0, DatabaseUtils.queryNumEntries(database, PopFlixContract.MoviesEntry.TABLE_NAME));
    }

    @Test
    public void shouldNotInsertAFavoriteMovieWithNoMovieId() throws ParseException {
        Movie movie = new Movie(null, "original_title", new Date(), "example/another_example", 12.34, 56.78, "overview", true);
        this.moviesProviderService.saveFavoritedMovie(movie);
        assertEquals(0, DatabaseUtils.queryNumEntries(database, PopFlixContract.MoviesEntry.TABLE_NAME));
    }

    @Test
    public void shouldKnowPersistentMoviePresentById() {
        Movie movie = new Movie(1, "Example", new Date(), "example/another_example", 12.34, 56.78, "overview", true);
        this.moviesProviderService.saveFavoritedMovie(movie);
        assertEquals(true, this.moviesProviderService.containsFavoriteMovieById(movie.idString()));
    }

    @Test
    public void shouldKnowMovieNotPresentIfNotPersistent() {
        Movie movie = new Movie(1, "Example", new Date(), "example/another_example", 12.34, 56.78, "overview", true);
        assertEquals(false, this.moviesProviderService.containsFavoriteMovieById(movie.idString()));
    }

    @Test
    public void shouldNotDeleteMoviesForInexistantMovieId() {
        int deletedRows = this.moviesProviderService.deleteFavoritedMovie("19");
        assertEquals(0, deletedRows);
    }

    @Test
    public void shouldDeleteMovieWithSpecificId() {
        Movie movie = new Movie(1, "original_title", new Date(), "example/another_example", 12.34, 56.78, "overview", true);
        this.database.insert(PopFlixContract.MoviesEntry.TABLE_NAME, null, movie.toContentValues());
        int deletedRows = this.moviesProviderService.deleteFavoritedMovie(movie.idString());
        assertEquals(1, deletedRows);
    }
}
