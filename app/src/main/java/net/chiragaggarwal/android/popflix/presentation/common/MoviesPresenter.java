package net.chiragaggarwal.android.popflix.presentation.common;

import net.chiragaggarwal.android.popflix.data.MoviesProviderService;
import net.chiragaggarwal.android.popflix.models.Error;
import net.chiragaggarwal.android.popflix.models.Movies;
import net.chiragaggarwal.android.popflix.network.MoviesService;

import java.text.ParseException;

public class MoviesPresenter {
    private static final String FAVORITES = "favorites";
    private MoviesView moviesView;
    private MoviesService moviesService;
    private MoviesProviderService moviesProviderService;

    public MoviesPresenter(MoviesView moviesView,
                           MoviesService moviesService,
                           MoviesProviderService moviesProviderService) {

        this.moviesView = moviesView;
        this.moviesService = moviesService;
        this.moviesProviderService = moviesProviderService;
    }

    public void fetchMovies(String sortOrder) throws ParseException {
        if (sortOrder.equals(FAVORITES)) {
            Movies movies = moviesProviderService.loadFavoriteMovies();
            moviesView.onMoviesLoaded(movies);
        } else {
            loadMoviesFromApi(sortOrder);
        }
    }

    private void loadMoviesFromApi(String sortOrder) {
        this.moviesService.loadMovies(sortOrder, new MoviesService.MoviesCallback() {
            @Override
            public void onSuccess(Movies movies) {
                moviesView.onMoviesLoaded(movies);
            }

            @Override
            public void onFailure(Error error) {
                moviesView.onError(error);
            }

            @Override
            public void onUnexpectedFailure() {
                moviesView.onUnexpectedError();
            }
        });
    }
}
