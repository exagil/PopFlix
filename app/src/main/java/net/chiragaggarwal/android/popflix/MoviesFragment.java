package net.chiragaggarwal.android.popflix;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import net.chiragaggarwal.android.popflix.models.Callback;
import net.chiragaggarwal.android.popflix.models.Error;
import net.chiragaggarwal.android.popflix.models.Movies;

public class MoviesFragment extends Fragment {
    private static final String LOG_TAG = "popflix.movies_fragment";
    private GridView moviesGrid;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movies, container, false);
        setHasOptionsMenu(true);
        initializeViews(view);
        return view;
    }

    private void initializeViews(View view) {
        this.moviesGrid = ((GridView) view.findViewById(R.id.movies_grid));
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.fragment_movies, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.movies_action_refresh:
                fetchMovies();
                break;
        }
        return false;
    }

    private void fetchMovies() {
        new FetchMoviesTask(getContext(), new Callback<Movies, Error>() {
            @Override
            public void onSuccess(Movies movies) {
                MoviesAdapter moviesAdapter = new MoviesAdapter(getContext(), movies);
                moviesGrid.setAdapter(moviesAdapter);
            }

            @Override
            public void onFailure(Error error) {
                showErrorDialog(error);
            }

            @Override
            public void onUnexpectedFailure() {
                Log.e(LOG_TAG, "Fetching Movies - Unexpected Failure");
            }
        }).execute();
    }

    private void showErrorDialog(Error error) {
        new AlertDialog.Builder(getContext())
                .setTitle(error.statusCode.toString())
                .setMessage(error.statusMessage)
                .create()
                .show();
    }
}
