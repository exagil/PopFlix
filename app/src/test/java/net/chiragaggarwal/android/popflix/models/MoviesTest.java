package net.chiragaggarwal.android.popflix.models;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;

public class MoviesTest {
    @Test
    public void collectionWithoutAMovieShouldHaveZeroCount() {
        Movies movies = new Movies();
        assertEquals(0, movies.count());
    }

    @Test
    public void collectionWithMoviesShouldHaveAppropriateCount() {
        Movie firstMovie = new Movie(null, null, null, null, null);
        Movie secondMovie = new Movie(null, null, null, null, null);
        Movie thirdMovie = new Movie(null, null, null, null, null);
        Movies movies = new Movies(firstMovie, secondMovie, thirdMovie);
        assertEquals(3, movies.count());
    }

    @Test
    public void shouldNotBeAbleToFetchMovieWithInvalidMovieIndex() {
        Movie firstMovie = new Movie(null, null, null, null, null);
        Movie secondMovie = new Movie(null, null, null, null, null);
        Movies movies = new Movies(firstMovie, secondMovie);
        assertEquals(null, movies.get(2));
    }

    @Test
    public void shouldNotBeAbleToFetchMovieWithNegativeMovieIndex() {
        Movie firstMovie = new Movie(null, null, null, null, null);
        Movie secondMovie = new Movie(null, null, null, null, null);
        Movies movies = new Movies(firstMovie, secondMovie);
        assertEquals(null, movies.get(-1));
    }

    @Test
    public void shouldBeAbleToFetchMovieWithValidMovieIndex() {
        Movie firstMovie = new Movie("first_movie_title", new Date(), "example_one.jpg", 10.20, "overview_first");
        Movie expectedMovie = new Movie("expected_movie_title", new Date(), "example_expected.jpg", 90.10, "expected_overview");
        Movie thirdMovie = new Movie("third_movie_title", new Date(), "example_three.jpg", 30.90, null);
        Movies movies = new Movies(firstMovie, expectedMovie, thirdMovie);

        assertEquals(expectedMovie, movies.get(1));
    }
}
