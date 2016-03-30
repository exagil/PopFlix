package net.chiragaggarwal.android.popflix.presentation;

import net.chiragaggarwal.android.popflix.models.Movie;

public class MovieDetailViewModel {
    private Movie movie;

    public MovieDetailViewModel(Movie movie) {
        this.movie = movie;
    }

    public void toggleFavorite() {
        this.movie.toggleFavorite();
    }
}
