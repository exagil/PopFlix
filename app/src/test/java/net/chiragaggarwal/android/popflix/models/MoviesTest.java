package net.chiragaggarwal.android.popflix.models;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MoviesTest {
    @Test
    public void collectionWithoutAMovieShouldHaveZeroCount() {
        Movies movies = new Movies();
        assertEquals(0, movies.count());
    }
}