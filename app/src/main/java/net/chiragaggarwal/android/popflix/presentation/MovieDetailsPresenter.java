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
        String movieIdString = movie.idString();
        if (this.moviesProviderService.containsMovieById(movieIdString)) {
            this.moviesProviderService.deleteFavoritedMovie(movieIdString);
            this.movieDetailsView.onDeleteFavoriteMovie();
        } else {
            this.moviesProviderService.saveFavoritedMovie(movie);
            this.movieDetailsView.onSaveFavoriteMovie();
        }
    }
}
