package net.chiragaggarwal.android.popflix.presentation;

import net.chiragaggarwal.android.popflix.data.MoviesProviderService;
import net.chiragaggarwal.android.popflix.models.Movie;

public class MovieDetailsPresenter {
    private MovieDetailsView movieDetailsView;
    private MoviesProviderService moviesProviderService;

    public MovieDetailsPresenter(MovieDetailsView movieDetailsView, MoviesProviderService moviesProviderService) {
        this.movieDetailsView = movieDetailsView;
        this.moviesProviderService = moviesProviderService;
    }

    public void toggleFavorite(Movie movie) {
        if (!this.moviesProviderService.containsMovieById(movie.idString())) {
            this.moviesProviderService.saveFavoritedMovie(movie);
        }
    }
}
