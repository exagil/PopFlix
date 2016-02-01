package net.chiragaggarwal.android.popflix.models;

import android.content.Context;

import net.chiragaggarwal.android.popflix.R;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MovieTest {
    @Test
    public void movieShouldHaveCorrectImageUrlString() {
        String imagePath = "nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg";
        String expectedString = "http://image.tmdb.org/t/p/w185/" + imagePath;
        Movie movie = new Movie("Interstellar", imagePath);

        Context context = mock(Context.class);
        when(context.getString(R.string.base_image_url)).thenReturn("http://image.tmdb.org/t/p");
        when(context.getString(R.string.default_image_size)).thenReturn("w185");

        assertEquals(expectedString, movie.imageUrlString(context));
    }

    @Test
    public void shouldNotBeEqualToNull() {
        Movie movie = new Movie("example_title", "example_poster_path");
        assertFalse(movie.equals(null));
    }
}