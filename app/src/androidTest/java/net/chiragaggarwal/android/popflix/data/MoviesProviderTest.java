package net.chiragaggarwal.android.popflix.data;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import net.chiragaggarwal.android.popflix.models.Movie;

import org.junit.Test;

import java.util.Date;

import static net.chiragaggarwal.android.popflix.data.PopFlixContract.MoviesEntry;

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
    public void shouldIncreaseMoviesCountByOneWhenTriedToInsertAValidMovie() {
        Movie movie = new Movie(1, "Example", new Date(Long.parseLong("1458098572336")), null, 43.169504, 7.21, "Example Review", false);
        context.getContentResolver().insert(MoviesEntry.buildMoviesUri(), movie.toContentValues());
        assertEquals(1, DatabaseUtils.queryNumEntries(database, MoviesEntry.TABLE_NAME));
    }

    @Test
    public void shouldNotInsertMoviesCountWhenTriedToInsertATInvalidEndpoint() {
        Movie movie = new Movie(1, "Example", new Date(Long.parseLong("1458098572336")), null, 43.169504, 7.21, "Example Review", false);
        context.getContentResolver().insert(MoviesEntry.buildMovieUri(1), movie.toContentValues());
        assertEquals(0, DatabaseUtils.queryNumEntries(database, MoviesEntry.TABLE_NAME));
    }

    @Test
    public void shouldInsertMoviesUniquelyBasedOnMovieId() {
        Movie movie = new Movie(1, "Example", new Date(Long.parseLong("1458098572336")), null, 43.169504, 7.21, "Example Review", false);
        context.getContentResolver().insert(MoviesEntry.buildMoviesUri(), movie.toContentValues());
        context.getContentResolver().insert(MoviesEntry.buildMoviesUri(), movie.toContentValues());
        assertEquals(1, DatabaseUtils.queryNumEntries(database, MoviesEntry.TABLE_NAME));
    }
}
