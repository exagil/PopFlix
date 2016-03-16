package net.chiragaggarwal.android.popflix.data;

import android.content.ComponentName;
import android.content.Context;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.test.AndroidTestCase;

import org.junit.Test;

public class MoviesProviderTest extends AndroidTestCase {

    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    @Test
    public void shouldBeRegisteredProperly() throws PackageManager.NameNotFoundException {
        ComponentName moviesProviderComponent = new ComponentName(getContext().getPackageName(), MoviesProvider.class.getName());
        ProviderInfo moviesProviderInfo = getContext().getPackageManager().getProviderInfo(moviesProviderComponent, 0);
        assertEquals(moviesProviderInfo.authority, PopFlixContract.MoviesEntry.PROVIDER_AUTHORITY);
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
        String expectedType = getContext().getContentResolver().getType(PopFlixContract.MoviesEntry.buildMoviesUri());
        String actualType = "vnd.android.cursor.dir/vnd.net.chiragaggarwal.android.popflix.data.movies-provider.movies";
        assertEquals(expectedType, actualType);
    }
}
