package net.chiragaggarwal.android.popflix.network;

import android.content.Context;

import net.chiragaggarwal.android.popflix.NetworkUtilities;
import net.chiragaggarwal.android.popflix.models.Callback;
import net.chiragaggarwal.android.popflix.models.Error;
import net.chiragaggarwal.android.popflix.models.Movies;

public class MoviesService {

    private Context context;
    private NetworkUtilities networkUtilities;

    public MoviesService(Context context, NetworkUtilities networkUtilities) {
        this.context = context;
        this.networkUtilities = networkUtilities;
    }

    public void loadMovies(String sortOrder, final MoviesCallback moviesCallback) {
        new FetchMoviesTask(
                sortOrder,
                context,
                networkUtilities,
                new Callback<Movies, Error>() {
                    @Override
                    public void onSuccess(Movies movies) {
                        moviesCallback.onSuccess(movies);
                    }

                    @Override
                    public void onFailure(Error error) {
                        moviesCallback.onFailure(error);
                    }

                    @Override
                    public void onUnexpectedFailure() {
                        moviesCallback.onUnexpectedFailure();
                    }
                }
        ).execute();
    }

    public interface MoviesCallback {
        void onSuccess(Movies movies);

        void onFailure(Error error);

        void onUnexpectedFailure();
    }
}
