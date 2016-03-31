package net.chiragaggarwal.android.popflix.presentation;

import net.chiragaggarwal.android.popflix.models.Movie;
import net.chiragaggarwal.android.popflix.presentation.common.MovieDetailViewModel;

import org.junit.Test;

import java.util.Date;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;

public class MovieDetailViewModelTest {
    @Test
    public void shouldBeAbleToUnfavoriteAFavoritedMovie() {
        Movie movie = new Movie(1, null, new Date(), null, null, null, null, true);
        MovieDetailViewModel movieDetailViewModel = new MovieDetailViewModel(movie);
        movieDetailViewModel.toggleFavorite();
        assertFalse("\nExpected: false\nActual: " + movie.isFavorite(), movie.isFavorite());
    }

    @Test
    public void shouldBeAbleToFavoriteAnUnfavoritedMovie() {
        Movie movie = new Movie(1, null, new Date(), null, null, null, null, false);
        MovieDetailViewModel movieDetailViewModel = new MovieDetailViewModel(movie);
        movieDetailViewModel.toggleFavorite();
        assertTrue("\nExpected: true\nActual: " + movie.isFavorite(), movie.isFavorite());
    }

    @Test
    public void shouldDisplayMarkFavoriteTextWhenMovieNotFavorite() {
        Movie movie = new Movie(1, null, new Date(), null, null, null, null, false);
        MovieDetailViewModel movieDetailViewModel = new MovieDetailViewModel(movie);
        assertEquals("\nExpected: Mark As\nFavorite\nActual: " + movieDetailViewModel.favoriteToggleText(),
                "Mark As\nFavorite", movieDetailViewModel.favoriteToggleText());
    }

    @Test
    public void shouldDisplayMarkUnfavoriteTextWhenMovieFavorite() {
        Movie movie = new Movie(1, null, new Date(), null, null, null, null, true);
        MovieDetailViewModel movieDetailViewModel = new MovieDetailViewModel(movie);
        assertEquals("\nExpected: Mark As\nNot Favorite\nActual: " + movieDetailViewModel.favoriteToggleText(),
                "Mark As\nNot Favorite", movieDetailViewModel.favoriteToggleText());
    }
}