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

    @Test
    public void shouldNotBeAbleToFetchMovieWithNegativeMovieIndex() {
        Movies movies = new Movies(new Movie(null, null), new Movie(null, null));
        assertEquals(null, movies.get(-1));
    }

    @Test
    public void shouldBeAbleToFetchMovieWithValidMovieIndex() {
        Movie firstMovie = new Movie("first_movie_title", "example_one.jpg");
        Movie expectedMovie = new Movie("expected_movie_title", "example_expected.jpg");
        Movie thirdMovie = new Movie("third_movie_title", "example_third.jpg");
        Movies movies = new Movies(firstMovie, expectedMovie, thirdMovie);

        assertEquals(expectedMovie, movies.get(1));
    }
}