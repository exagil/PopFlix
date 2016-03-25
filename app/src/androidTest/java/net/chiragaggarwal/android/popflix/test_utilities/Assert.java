package net.chiragaggarwal.android.popflix.test_utilities;

import android.database.Cursor;

import net.chiragaggarwal.android.popflix.models.Movies;

import java.text.ParseException;

import static junit.framework.TestCase.assertEquals;

public class Assert {
    public static void assertEqualityOfMoviesCursors(Cursor expectedMoviesCursor, Cursor actualMoviesCursor) throws ParseException {
        Movies expectedMovies = Movies.fromCursor(expectedMoviesCursor);
        Movies actualMovies = Movies.fromCursor(actualMoviesCursor);
        assertEquals(expectedMovies, actualMovies);
    }
}
