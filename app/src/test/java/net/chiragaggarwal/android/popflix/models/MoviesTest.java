package net.chiragaggarwal.android.popflix.models;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MoviesTest {
    @Test
    public void collectionWithoutAMovieShouldHaveZeroCount() {
        Movies movies = new Movies();
        assertEquals(0, movies.count());
    }

    @Test
    public void collectionWithMoviesShouldHaveAppropriateCount() {
        Movies movies = new Movies(new Movie(null, null), new Movie(null, null), new Movie(null, null));
        assertEquals(3, movies.count());
    }

    @Test
    public void shouldNotBeAbleToFetchMovieWithInvalidMovieIndex() {
        Movies movies = new Movies(new Movie(null, null), new Movie(null, null));
        assertEquals(null, movies.get(2));
    }
}