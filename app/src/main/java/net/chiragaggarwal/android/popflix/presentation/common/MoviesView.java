package net.chiragaggarwal.android.popflix.presentation.common;

import android.content.Context;

import net.chiragaggarwal.android.popflix.models.Error;
import net.chiragaggarwal.android.popflix.models.Movies;

public interface MoviesView {
    void onMoviesLoaded(Movies movies);

    Context getContext();

    void onError(Error error);

    void onUnexpectedError();
}
