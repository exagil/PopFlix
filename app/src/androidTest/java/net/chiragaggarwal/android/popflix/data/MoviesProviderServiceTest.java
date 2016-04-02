package net.chiragaggarwal.android.popflix.data;

import android.content.Context;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import net.chiragaggarwal.android.popflix.models.Movie;
import net.chiragaggarwal.android.popflix.models.Movies;

import org.junit.Test;

import java.text.ParseException;
import java.util.Date;

import static net.chiragaggarwal.android.popflix.data.PopFlixContract.MoviesEntry;

public class MoviesProviderServiceTest extends AndroidTestCase {
    private Context context;
    private SQLiteDatabase database;
    private MoviesProviderService moviesProviderService;

    @Override
    protected void setUp() throws Exception {
        this.context = getContext();
        DatabaseHelper databaseHelper = DatabaseHelper.getInstance(context);
        databaseHelper.reset();
        this.database = databaseHelper.getReadableDatabase();
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
        assertEquals(0, DatabaseUtils.queryNumEntries(database, MoviesEntry.TABLE_NAME));
    }

    @Test
    public void shouldInsertMoviesUniquelyBasedOnMovieId() {
        Movie movie = new Movie(1, "Example", new Date(Long.parseLong("1458098572336")), null, 43.169504, 7.21, "Example Review", true);
        this.moviesProviderService.saveFavoritedMovie(movie);
        this.moviesProviderService.saveFavoritedMovie(movie);
        assertEquals(1, DatabaseUtils.queryNumEntries(database, MoviesEntry.TABLE_NAME));
    }

    @Test
    public void shouldIncreaseMoviesCountByOneWhenTriedToInsertAValidFavoriteMovie() throws ParseException {
        Movie movie = new Movie(1, "Example", new Date(), "example/another_example", 12.34, 56.78, "overview", true);
        this.moviesProviderService.saveFavoritedMovie(movie);
        assertEquals(1, DatabaseUtils.queryNumEntries(database, MoviesEntry.TABLE_NAME));
    }

    @Test
    public void shouldNotInsertFavoriteMovieWithNoOverview() throws ParseException {
        Movie movie = new Movie(1, null, new Date(), "example/another_example", 12.34, 56.78, "overview", true);
        this.moviesProviderService.saveFavoritedMovie(movie);
        assertEquals(0, DatabaseUtils.queryNumEntries(database, MoviesEntry.TABLE_NAME));
    }

    @Test
    public void shouldNotInsertAFavoriteMovieWithNoMovieId() throws ParseException {
        Movie movie = new Movie(null, "original_title", new Date(), "example/another_example", 12.34, 56.78, "overview", true);
        this.moviesProviderService.saveFavoritedMovie(movie);
        assertEquals(0, DatabaseUtils.queryNumEntries(database, MoviesEntry.TABLE_NAME));
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
        this.database.insert(MoviesEntry.TABLE_NAME, null, movie.toContentValues());
        int deletedRows = this.moviesProviderService.deleteFavoritedMovie(movie.idString());
        assertEquals(1, deletedRows);
    }

    @Test
    public void shouldKnowHowToFetchFavoriteMovies() throws ParseException {
        Movie firstMovie = new Movie(1, "original_title", new Date(), "example/first_image.jpg", 12.34, 56.78, "overview", true);
        Movie secondMovie = new Movie(2, "second_original_title", new Date(), "example/second_image.jpg", 12.34, 56.78, "overview", true);
        this.database.insert(MoviesEntry.TABLE_NAME, null, firstMovie.toContentValues());
        this.database.insert(MoviesEntry.TABLE_NAME, null, secondMovie.toContentValues());

        Movies expectedMovies = new Movies(firstMovie, secondMovie);
        Movies actualMovies = this.moviesProviderService.loadFavoriteMovies();

        assertEquals(expectedMovies, actualMovies);
    }

    @Test
    public void shouldOnlyFetchFavoriteMoviesIfBothFavoriteAndNonFavoriteMoviesPresent() throws ParseException {
        Movie favoriteMovie = new Movie(1, "original_title", new Date(), "example/another_example", 12.34, 56.78, "overview", true);
        Movie notFavoriteMovie = new Movie(2, "original_title_two", new Date(), "example_this/another_example", 12.34, 56.78, "overview", false);
        this.database.insert(MoviesEntry.TABLE_NAME, null, favoriteMovie.toContentValues());
        this.database.insert(MoviesEntry.TABLE_NAME, null, notFavoriteMovie.toContentValues());

        Movies expectedMovies = new Movies(favoriteMovie);
        Movies actualMovies = this.moviesProviderService.loadFavoriteMovies();

        assertEquals(expectedMovies, actualMovies);
    }

    @Test
    public void shoudlLoadNoMoviesIfOnlyNonFavoriteMoviesPresent() throws ParseException {
        Movie notFavoriteMovie = new Movie(2, "original_title_two", new Date(), "example_this/another_example", 12.34, 56.78, "overview", false);
        this.database.insert(MoviesEntry.TABLE_NAME, null, notFavoriteMovie.toContentValues());

        Movies expectedMovies = new Movies();
        Movies actualMovies = this.moviesProviderService.loadFavoriteMovies();

        assertEquals(expectedMovies, actualMovies);
    }
}
