package net.chiragaggarwal.android.popflix.presentation;

import net.chiragaggarwal.android.popflix.data.MoviesProviderService;
import net.chiragaggarwal.android.popflix.models.Movie;
import net.chiragaggarwal.android.popflix.presentation.common.MovieDetailsPresenter;
import net.chiragaggarwal.android.popflix.presentation.common.MovieDetailsView;

import org.junit.Test;

import java.util.Date;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MovieDetailsPresenterTest {
    @Test
    public void shouldSaveFavoritedMovieWhenNotPresentAlready() {
        Movie movie = new Movie(1, "example", new Date(), "example", 1.23, 4.56, "example", true);
        MoviesProviderService moviesProviderService = mock(MoviesProviderService.class);
        when(moviesProviderService.containsFavoriteMovieById(movie.idString())).thenReturn(false);
        MovieDetailsView movieDetailsView = mock(MovieDetailsView.class);
        MovieDetailsPresenter movieDetailsPresenter = new MovieDetailsPresenter(movieDetailsView, moviesProviderService);

        movieDetailsPresenter.toggleFavorite(movie);

        verify(moviesProviderService).saveFavoritedMovie(movie);
        verify(movieDetailsView).onSaveFavoriteMovie();
    }

    @Test
    public void shouldNotSaveFavoritedMovieWhenPresentAlready() {
        Movie movie = new Movie(1, "example", new Date(), "example", 1.23, 4.56, "example", true);
        MoviesProviderService moviesProviderService = mock(MoviesProviderService.class);
        MovieDetailsView movieDetailsView = mock(MovieDetailsView.class);
        when(moviesProviderService.containsFavoriteMovieById(movie.idString())).thenReturn(true);
        MovieDetailsPresenter movieDetailsPresenter = new MovieDetailsPresenter(movieDetailsView, moviesProviderService);

        movieDetailsPresenter.toggleFavorite(movie);

        verify(moviesProviderService).deleteFavoritedMovie(movie.idString());
        verify(movieDetailsView).onDeleteFavoriteMovie();
    }
}