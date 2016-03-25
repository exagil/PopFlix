package net.chiragaggarwal.android.popflix.data;

import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import net.chiragaggarwal.android.popflix.models.Movie;

import org.junit.Test;

import java.text.ParseException;
import java.util.Date;

import static net.chiragaggarwal.android.popflix.data.PopFlixContract.MoviesEntry;
import static net.chiragaggarwal.android.popflix.test_utilities.Assert.assertEqualityOfMoviesCursors;

public class MoviesProviderTest extends AndroidTestCase {

    private Context context;
    private SQLiteDatabase database;

    @Override
    protected void setUp() throws Exception {
        this.context = getContext();
        this.database = DatabaseHelper.getInstance(context).getReadableDatabase();
        this.database.beginTransaction();
    }

    @Override
    protected void tearDown() throws Exception {
        this.database.endTransaction();
    }

    @Test
    public void shouldBeRegisteredProperly() throws PackageManager.NameNotFoundException {
        ComponentName moviesProviderComponent = new ComponentName(getContext().getPackageName(), MoviesProvider.class.getName());
        ProviderInfo moviesProviderInfo = getContext().getPackageManager().getProviderInfo(moviesProviderComponent, 0);
        assertEquals(moviesProviderInfo.authority, MoviesEntry.PROVIDER_AUTHORITY);
    }

    @Test
    public void shouldBeExported() throws PackageManager.NameNotFoundException {
        Context context = getContext();
        String packageName = context.getPackageName();
        ComponentName moviesProviderComponent = new ComponentName(packageName, MoviesProvider.class.getName());
        ProviderInfo moviesProviderInfo = context.getPackageManager().getProviderInfo(moviesProviderComponent, 0);
        assertTrue(moviesProviderInfo.exported);
    }

    @Test
    public void shouldHaveCorrectMimeTypeForMoviesCollection() {
        String expectedType = "vnd.android.cursor.dir/vnd.net.chiragaggarwal.android.popflix.data.movies-provider.movies";
        String actualType = getContext().getContentResolver().getType(MoviesEntry.buildMoviesUri());
        assertEquals(expectedType, actualType);
    }

    @Test
    public void shouldHaveCorrectMimeTypeForMovie() {
        String expectedType = "vnd.android.cursor.item/vnd.net.chiragaggarwal.android.popflix.data.movies-provider.movies";
        String actualType = getContext().getContentResolver().getType(MoviesEntry.buildMovieUri(1));
        assertEquals(expectedType, actualType);
    }

    @Test
    public void shouldNotInsertANonFavoriteMovie() {
        Movie movie = new Movie(1, "Example", new Date(Long.parseLong("1458098572336")), null, 43.169504, 7.21, "Example Review", false);
        context.getContentResolver().insert(MoviesEntry.buildMoviesUri(), movie.toContentValues());
        assertEquals(0, DatabaseUtils.queryNumEntries(database, MoviesEntry.TABLE_NAME));
    }

    @Test
    public void shouldNotInsertMoviesCountWhenTriedToInsertAtInvalidEndpoint() {
        Movie movie = new Movie(1, "Example", new Date(Long.parseLong("1458098572336")), null, 43.169504, 7.21, "Example Review", false);
        context.getContentResolver().insert(MoviesEntry.buildMovieUri(1), movie.toContentValues());
        assertEquals(0, DatabaseUtils.queryNumEntries(database, MoviesEntry.TABLE_NAME));
    }

    @Test
    public void shouldInsertMoviesUniquelyBasedOnMovieId() {
        Movie movie = new Movie(1, "Example", new Date(Long.parseLong("1458098572336")), null, 43.169504, 7.21, "Example Review", true);
        context.getContentResolver().insert(MoviesEntry.buildMoviesUri(), movie.toContentValues());
        context.getContentResolver().insert(MoviesEntry.buildMoviesUri(), movie.toContentValues());
        assertEquals(1, DatabaseUtils.queryNumEntries(database, MoviesEntry.TABLE_NAME));
    }

    @Test
    public void shouldIncreaseMoviesCountByOneWhenTriedToInsertAValidFavoriteMovie() throws ParseException {
        Movie movie = new Movie(1, "Example", new Date(), "example/another_example", 12.34, 56.78, "overview", true);
        ContentValues moviesContentValues = movie.toContentValues();
        getContext().getContentResolver().insert(MoviesEntry.buildMoviesUri(), moviesContentValues);
        assertEquals(1, DatabaseUtils.queryNumEntries(database, MoviesEntry.TABLE_NAME));
    }

    @Test
    public void shouldNotInsertFavoriteMovieWithNoOverview() throws ParseException {
        Movie movie = new Movie(1, null, new Date(), "example/another_example", 12.34, 56.78, "overview", true);
        ContentValues moviesContentValues = movie.toContentValues();
        getContext().getContentResolver().insert(MoviesEntry.buildMoviesUri(), moviesContentValues);
        assertEquals(0, DatabaseUtils.queryNumEntries(database, MoviesEntry.TABLE_NAME));
    }

    @Test
    public void shouldNotInsertAFavoriteMovieWithNoMovieId() throws ParseException {
        Movie movie = new Movie(null, "original_title", new Date(), "example/another_example", 12.34, 56.78, "overview", true);
        ContentValues moviesContentValues = movie.toContentValues();
        getContext().getContentResolver().insert(MoviesEntry.buildMoviesUri(), moviesContentValues);
        assertEquals(0, DatabaseUtils.queryNumEntries(database, MoviesEntry.TABLE_NAME));
    }

    @Test
    public void shouldFetchNoMoviesIfNoMoviesPresent() {
        Cursor cursor = getContext().getContentResolver().query(MoviesEntry.buildMoviesUri(), null, null, null, null);
        assertNull(cursor);
    }

    @Test
    public void shouldKnowHowToFetchFavoriteMovies() throws ParseException {
        Movie movie = new Movie(1, "original_title", new Date(), "example/another_example", 12.34, 56.78, "overview", true);
        ContentValues moviesContentValues = movie.toContentValues();
        this.database.insert(MoviesEntry.TABLE_NAME, null, moviesContentValues);
        Cursor expectedMoviesCursor = MoviesGateway.getInstance(this.database).getFavoriteMovies();
        Cursor actualMoviesCursor = getContext().getContentResolver()
                .query(MoviesEntry.buildMoviesUri(), null, "is_favorite=?", new String[]{"1"}, null);

        assertEqualityOfMoviesCursors(expectedMoviesCursor, actualMoviesCursor);
    }

    @Test
    public void shouldFetchOnlyFavoriteMoviesIfBothFavoriteAndNotFavoriteMoviesArePresent() throws ParseException {
        Movie favoriteMovie = new Movie(1, "original_title", new Date(), "example/another_example", 12.34, 56.78, "overview", true);
        Movie notFavoriteMovie = new Movie(2, "original_title_two", new Date(), "example_this/another_example", 12.34, 56.78, "overview", false);
        ContentValues favoriteMovieContentValues = favoriteMovie.toContentValues();
        this.database.insert(MoviesEntry.TABLE_NAME, null, favoriteMovieContentValues);
        ContentValues notFavoriteMovieContentValues = notFavoriteMovie.toContentValues();
        this.database.insert(MoviesEntry.TABLE_NAME, null, notFavoriteMovieContentValues);

        Cursor expectedMoviesCursor = MoviesGateway.getInstance(this.database).getFavoriteMovies();
        Cursor actualMoviesCursor = getContext().getContentResolver()
                .query(MoviesEntry.buildMoviesUri(), null, "is_favorite=?", new String[]{"1"}, null);

        assertEqualityOfMoviesCursors(expectedMoviesCursor, actualMoviesCursor);
    }

    @Test
    public void shouldFetchNoMoviesIfFavoriteSelectionNotSpecified() throws ParseException {
        Movie favoriteMovie = new Movie(1, "original_title", new Date(), "example/another_example", 12.34, 56.78, "overview", true);
        Movie notFavoriteMovie = new Movie(2, "original_title_two", new Date(), "example_this/another_example", 12.34, 56.78, "overview", false);
        ContentValues favoriteMovieContentValues = favoriteMovie.toContentValues();
        this.database.insert(MoviesEntry.TABLE_NAME, null, favoriteMovieContentValues);
        ContentValues notFavoriteMovieContentValues = notFavoriteMovie.toContentValues();
        this.database.insert(MoviesEntry.TABLE_NAME, null, notFavoriteMovieContentValues);

        Cursor expectedMoviesCursor = null;
        Cursor actualMoviesCursor = getContext().getContentResolver().query(MoviesEntry.buildMoviesUri(), null, null, null, null);

        assertEqualityOfMoviesCursors(expectedMoviesCursor, actualMoviesCursor);
    }
}
