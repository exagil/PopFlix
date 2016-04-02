package net.chiragaggarwal.android.popflix.presentation.common;

import net.chiragaggarwal.android.popflix.data.MoviesProviderService;
import net.chiragaggarwal.android.popflix.models.Error;
import net.chiragaggarwal.android.popflix.models.Movie;
import net.chiragaggarwal.android.popflix.models.Movies;
import net.chiragaggarwal.android.popflix.network.MoviesService;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Matchers;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.stubbing.Answer;

import java.text.ParseException;
import java.util.Date;

import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class MoviesPresenterTest {
    private MoviesView moviesView;
    private MoviesProviderService moviesProviderService;
    private MoviesPresenter moviesPresenter;
    private MoviesService moviesService;
    private Movie firstMovie;
    private Movie secondMovie;

    @Before
    public void setup() {
        this.moviesView = mock(MoviesView.class);
        this.moviesProviderService = mock(MoviesProviderService.class);
        this.moviesService = mock(MoviesService.class);
        this.moviesPresenter = new MoviesPresenter(moviesView, moviesService, moviesProviderService);
        this.firstMovie = new Movie(1, "First Movie", new Date(), "example://location/first_movie.jpg", 12.34, 56.78, "First Overview", false);
        this.secondMovie = new Movie(2, "Second Movie", new Date(), "example://location/second_movie.jpg", 98.76, 54.32, "Second Overview", false);
    }

    @Test
    public void shouldSuccessfullyLoadMoviesFromApiWhenSortOrderIsPopularity() throws ParseException {
        String sortOrder = "popularity.desc";
        final Movies movies = new Movies(firstMovie, secondMovie);
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                MoviesService.MoviesCallback moviesCallback = ((MoviesService.MoviesCallback) invocation.getArguments()[1]);
                moviesCallback.onSuccess(movies);
                return null;
            }
        }).when(moviesService).loadMovies(eq(sortOrder), Matchers.<MoviesService.MoviesCallback>any());
        moviesPresenter.fetchMovies(sortOrder);
        verify(moviesService).loadMovies(eq(sortOrder), Matchers.<MoviesService.MoviesCallback>any());
        verify(moviesView).onMoviesLoaded(movies);
    }

    @Test
    public void shouldInformViewAboutErrorWhenNotAbleToLoadMoviesForSortOrderPopular() throws ParseException {
        String sortOrder = "popularity.desc";
        final Error error = new Error(404, "Not Found");
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                MoviesService.MoviesCallback moviesCallback = ((MoviesService.MoviesCallback) invocation.getArguments()[1]);
                moviesCallback.onFailure(error);
                return null;
            }
        }).when(moviesService).loadMovies(eq(sortOrder), Matchers.<MoviesService.MoviesCallback>any());
        moviesPresenter.fetchMovies(sortOrder);
        verify(moviesService).loadMovies(eq(sortOrder), Matchers.<MoviesService.MoviesCallback>any());
        verify(moviesView).onError(error);
    }

    @Test
    public void shouldInformViewAboutUnexpectedErrorWhenNotAbleToLoadMoviesForSortOrderPopular() throws ParseException {
        String sortOrder = "popularity.desc";
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                MoviesService.MoviesCallback moviesCallback = ((MoviesService.MoviesCallback) invocation.getArguments()[1]);
                moviesCallback.onUnexpectedFailure();
                return null;
            }
        }).when(moviesService).loadMovies(eq(sortOrder), Matchers.<MoviesService.MoviesCallback>any());
        moviesPresenter.fetchMovies(sortOrder);
        verify(moviesService).loadMovies(eq(sortOrder), Matchers.<MoviesService.MoviesCallback>any());
        verify(moviesView).onUnexpectedError();
    }

    @Test
    public void shouldSuccessfullyLoadMoviesFromApiWhenSortOrderIsHighestRated() throws ParseException {
        String sortOrder = "vote_average.desc";
        final Movies movies = new Movies(firstMovie, secondMovie);
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                MoviesService.MoviesCallback moviesCallback = ((MoviesService.MoviesCallback) invocation.getArguments()[1]);
                moviesCallback.onSuccess(movies);
                return null;
            }
        }).when(moviesService).loadMovies(eq(sortOrder), Matchers.<MoviesService.MoviesCallback>any());
        moviesPresenter.fetchMovies(sortOrder);
        verify(moviesService).loadMovies(eq(sortOrder), Matchers.<MoviesService.MoviesCallback>any());
        verify(moviesView).onMoviesLoaded(movies);
    }

    @Test
    public void shouldInformViewAboutErrorWhenNotAbleToLoadMoviesForSortOrderHighestRated() throws ParseException {
        String sortOrder = "vote_average.desc";
        final Error error = new Error(404, "Not Found");
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                MoviesService.MoviesCallback moviesCallback = ((MoviesService.MoviesCallback) invocation.getArguments()[1]);
                moviesCallback.onFailure(error);
                return null;
            }
        }).when(moviesService).loadMovies(eq(sortOrder), Matchers.<MoviesService.MoviesCallback>any());
        moviesPresenter.fetchMovies(sortOrder);
        verify(moviesService).loadMovies(eq(sortOrder), Matchers.<MoviesService.MoviesCallback>any());
        verify(moviesView).onError(error);
    }

    @Test
    public void shouldInformViewAboutUnexpectedErrorWhenNotAbleToLoadMoviesForSortOrderHighestRated() throws ParseException {
        String sortOrder = "vote_average.desc";
        doAnswer(new Answer() {
            @Override
            public Object answer(InvocationOnMock invocation) throws Throwable {
                MoviesService.MoviesCallback moviesCallback = ((MoviesService.MoviesCallback) invocation.getArguments()[1]);
                moviesCallback.onUnexpectedFailure();
                return null;
            }
        }).when(moviesService).loadMovies(eq(sortOrder), Matchers.<MoviesService.MoviesCallback>any());
        moviesPresenter.fetchMovies(sortOrder);
        verify(moviesService).loadMovies(eq(sortOrder), Matchers.<MoviesService.MoviesCallback>any());
        verify(moviesView).onUnexpectedError();
    }

    @Test
    public void shouldLoadFavoritesMoviesFromMoviesProviderWhenSortOrderIsFavorites() throws ParseException {
        final Movies movies = new Movies(firstMovie, secondMovie);
        when(moviesProviderService.loadFavoriteMovies()).thenReturn(movies);
        String sortOrder = "favorites";
        moviesPresenter.fetchMovies(sortOrder);

        verify(moviesProviderService).loadFavoriteMovies();
        verify(moviesView).onMoviesLoaded(movies);
    }
}