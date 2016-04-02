package net.chiragaggarwal.android.popflix.presentation.common;

import net.chiragaggarwal.android.popflix.data.MoviesProviderService;
import net.chiragaggarwal.android.popflix.models.Movies;
import net.chiragaggarwal.android.popflix.network.MoviesService;

public class MoviesPresenter {
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

    public void fetchMovies(String sortOrder) {
        this.moviesService.loadMovies(sortOrder, new MoviesService.MoviesCallback() {
            @Override
            public void onSuccess(Movies movies) {
                moviesView.onMoviesLoaded(movies);
            }

            @Override
            public void onFailure(net.chiragaggarwal.android.popflix.models.Error error) {

            }

            @Override
            public void onUnexpectedFailure() {

            }
        });
    }
}
