package net.chiragaggarwal.android.popflix.presentation.common;

import net.chiragaggarwal.android.popflix.models.Movie;

public class MovieDetailViewModel {
    private Movie movie;

    public MovieDetailViewModel(Movie movie) {
        this.movie = movie;
    }

    public void toggleFavorite() {
        this.movie.toggleFavorite();
    }

    public String favoriteToggleText() {
        String favoriteToggleText;
        if (movie.isFavorite())
            favoriteToggleText = "Mark As\nNot Favorite";
        else
            favoriteToggleText = "Mark As\nFavorite";
        return favoriteToggleText;
    }
}
