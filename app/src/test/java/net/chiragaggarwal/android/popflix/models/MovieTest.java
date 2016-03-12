package net.chiragaggarwal.android.popflix.models;

import android.content.Context;

import net.chiragaggarwal.android.popflix.R;

import org.junit.Test;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MovieTest {
    @Test
    public void movieShouldHaveCorrectImageUrlString() {
        String imagePath = "nBNZadXqJSdt05SHLqgT0HuC5Gm.jpg";
        String expectedString = "http://image.tmdb.org/t/p/w185/" + imagePath;
        Movie movie = new Movie(1, "Interstellar", new Date(), imagePath, null, null);

        Context context = mock(Context.class);
        when(context.getString(R.string.base_image_url)).thenReturn("http://image.tmdb.org/t/p");
        when(context.getString(R.string.default_image_size)).thenReturn("w185");

        assertEquals(expectedString, movie.imageUrlString(context));
    }

    @Test
    public void shouldNotBeEqualToNull() {
        Movie movie = new Movie(1, "example_title", null, "example_poster_path", null, null);
        assertFalse(movie.equals(null));
    }

    @Test
    public void shouldBeEqualToItself() {
        Movie thisMovie = new Movie(1, "example_title", new Date(), "example_poster_path", 10.10, "example_overview");
        assertTrue(thisMovie.equals(thisMovie));
    }

    @Test
    public void shouldNotBeEqualToAnotherMovieWithDifferentAttributes() {
        Movie thisMovie = new Movie(1, "example_title", new Date(), "example_poster_path", 12.34, "example_overview");
        Movie thatMovie = new Movie(2, "example_title_two", new Date(), "example_poster_path_two", 45.67, "example_overview_two");
        assertFalse(thisMovie.equals(thatMovie));
    }

    @Test
    public void shouldBEqualToAnotherMovieWithSameAttributes() {
        Movie thisMovie = new Movie(1, "example_title", new Date(), "example_poster_path", 10.10, "example_pverview");
        Movie thatMovie = new Movie(1, "example_title", new Date(), "example_poster_path", 10.10, "example_pverview");
        assertTrue(thisMovie.equals(thatMovie));
    }

    @Test
    public void shouldNotBeEqualToAnythingOtherThanAMovie() {
        Movie thisMovie = new Movie(1, "example_title", new Date(), "example_poster_path", 10.10, "example_overview");
        assertEquals(false, thisMovie.equals(new Object()));
    }

    @Test
    public void isEqualToMovieCommutatively() {
        Movie thisMovie = new Movie(1, "example_title", new Date(), "example_poster_path", 10.10, "example_overview");
        Movie thatMovie = new Movie(1, "example_title", new Date(), "example_poster_path", 10.10, "example_overview");
        assertEquals(thatMovie, thisMovie);
    }

    @Test
    public void shouldHaveHashcodeSameAsThatOfOtherMovieIfBothAreEqual() {
        Movie thisMovie = new Movie(1, "example_title", new Date(), "example_poster_path", 10.10, "example_overview");
        Movie thatMovie = new Movie(1, "example_title", new Date(), "example_poster_path", 10.10, "example_overview");
        assertEquals(thisMovie.hashCode(), thatMovie.hashCode());
    }

    @Test
    public void shouldHaveYearAsItsDateYear() throws ParseException {
        Date date = new SimpleDateFormat("dd/MM/yyyy").parse("24/03/2015");
        Movie thisMovie = new Movie(1, "example_title", date, "example_poster_path", 10.10, "example_overview");
        assertEquals("2015", thisMovie.yearString());
    }

    @Test
    public void shouldHaveAnIDString() {
        Movie movie = new Movie(123, "example_title", null, "example_poster_path", 10.10, "example_overview");;
        assertEquals("123", movie.idString());
    }

    @Test
    public void shouldHaveNoIdStringIfDoesNotHaveId() {
        Movie movie = new Movie(null, "example_title", null, "example_poster_path", 10.10, "example_overview");;
        assertEquals(null, movie.idString());
    }
}
