package net.chiragaggarwal.android.popflix.models;

import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MoviesTest {
    @Test
    public void collectionWithoutAMovieShouldHaveZeroCount() {
        Movies movies = new Movies();
        assertEquals(0, movies.count());
    }

    @Test
    public void collectionWithMoviesShouldHaveAppropriateCount() {
        Movie firstMovie = new Movie(1, null, null, null, null, null, null, null);
        Movie secondMovie = new Movie(2, null, null, null, null, null, null, null);
        Movie thirdMovie = new Movie(3, null, null, null, null, null, null, null);
        Movies movies = new Movies(firstMovie, secondMovie, thirdMovie);
        assertEquals(3, movies.count());
    }

    @Test
    public void shouldNotBeAbleToFetchMovieWithInvalidMovieIndex() {
        Movie firstMovie = new Movie(1, null, null, null, null, null, null, null);
        Movie secondMovie = new Movie(2, null, null, null, null, null, null, null);
        Movies movies = new Movies(firstMovie, secondMovie);
        assertEquals(null, movies.get(2));
    }

    @Test
    public void shouldNotBeAbleToFetchMovieWithNegativeMovieIndex() {
        Movie firstMovie = new Movie(1, null, null, null, null, null, null, null);
        Movie secondMovie = new Movie(2, null, null, null, null, null, null, null);
        Movies movies = new Movies(firstMovie, secondMovie);
        assertEquals(null, movies.get(-1));
    }

    @Test
    public void shouldBeAbleToFetchMovieWithValidMovieIndex() {
        Movie firstMovie = new Movie(1, "first_movie_title", new Date(), "example_one.jpg", 10.21, 10.20, "overview_first", false);
        Movie expectedMovie = new Movie(2, "expected_movie_title", new Date(), "example_expected.jpg", 20.21, 90.10, "expected_overview", true);
        Movie thirdMovie = new Movie(3, "third_movie_title", new Date(), "example_three.jpg", 30.21, 30.90, null, false);
        Movies movies = new Movies(firstMovie, expectedMovie, thirdMovie);

        assertEquals(expectedMovie, movies.get(1));
    }

    @Test
    public void shouldNotBeEqualToNull() {
        Movie firstMovie = new Movie(1, "first_movie_title", new Date(), "example_one.jpg", 10.21, 10.20, "overview_first", false);
        Movies movies = new Movies(firstMovie);
        assertFalse(movies.equals(null));
    }

    @Test
    public void shouldBeEqualToItself() {
        Movie movie = new Movie(1, "first_movie_title", new Date(), "example_one.jpg", 10.21, 10.20, "overview_first", false);
        Movies theseMovies = new Movies(movie);
        assertTrue(theseMovies.equals(theseMovies));
    }

    @Test
    public void shouldNotBeEqualToAnythingOtherThanAMovie() {
        Movie movie = new Movie(1, "first_movie_title", new Date(), "example_one.jpg", 10.21, 10.20, "overview_first", false);
        Movies theseMovies = new Movies(movie);
        assertFalse(theseMovies.equals(new Object()));
    }

    @Test
    public void shouldBeEqualToOtherMovieCollectionWithSameMovieElements() {
        Movie movie = new Movie(1, "first_movie_title", new Date(), "example_one.jpg", 10.21, 10.20, "overview_first", false);
        Movies theseMovies = new Movies(movie);
        Movies thoseMovies = new Movies(movie);
        assertTrue(theseMovies.equals(thoseMovies));
    }

    @Test
    public void shouldNotBeEqualToOtherMovieCollectionWithDifferentMovieElements() {
        Movie firstMoviesCollection = new Movie(1, "first_movie_title", new Date(), "example_one.jpg", 10.21, 10.20, "overview_first", false);
        Movie secondMoviesCollection = new Movie(2, "second_movie_title", new Date(), "example_two.jpg", 12.10, 20.10, "overview_second", true);
        Movies theseMovies = new Movies(firstMoviesCollection);
        Movies thoseMovies = new Movies(secondMoviesCollection);
        assertFalse(theseMovies.equals(thoseMovies));
    }

    @Test
    public void shouldHaveSameHashCodeAsOtherMovieCollectionIfBothAreEqual() {
        Movie moviesCollection = new Movie(1, "first_movie_title", new Date(), "example_one.jpg", 10.21, 10.20, "overview_first", false);
        Movies theseMovies = new Movies(moviesCollection);
        Movies thoseMovies = new Movies(moviesCollection);
        assertEquals(theseMovies.hashCode(), thoseMovies.hashCode());
    }
}
