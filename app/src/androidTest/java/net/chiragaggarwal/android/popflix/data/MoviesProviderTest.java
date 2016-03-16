package net.chiragaggarwal.android.popflix.data;

import android.content.ComponentName;
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
}