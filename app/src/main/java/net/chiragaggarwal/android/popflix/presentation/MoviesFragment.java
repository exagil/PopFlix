package net.chiragaggarwal.android.popflix.presentation;

import android.app.AlertDialog;
import android.content.Intent;
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
import android.widget.AdapterView;
import android.widget.GridView;

import net.chiragaggarwal.android.popflix.R;
import net.chiragaggarwal.android.popflix.models.Callback;
import net.chiragaggarwal.android.popflix.models.Error;
import net.chiragaggarwal.android.popflix.models.Movie;
import net.chiragaggarwal.android.popflix.models.Movies;
import net.chiragaggarwal.android.popflix.network.FetchMoviesTask;

public class MoviesFragment extends Fragment {
    private static final String LOG_TAG = "popflix.movies_fragment";
    private GridView moviesGrid;
    private MoviesAdapter moviesAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movies, container, false);
        setHasOptionsMenu(true);
        initializeViews(view);
        setOnItemClickListenerForMovieGrid();
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        menu.clear();
        inflater.inflate(R.menu.menu_movies, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.movies_action_refresh:
                fetchMovies();
                break;
            case R.id.movies_action_settings:
                launchSettings();
                break;
        }
        return false;
    }

    private void initializeViews(View view) {
        this.moviesGrid = ((GridView) view.findViewById(R.id.movies_grid));
    }

    private void setOnItemClickListenerForMovieGrid() {
        this.moviesGrid.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Movie movie = moviesAdapter.getItem(position);
                Intent movieIntent = new Intent(getActivity(), DetailsActivity.class);
                movieIntent.putExtra(Movie.TAG, movie);
                startActivity(movieIntent);
            }
        });
    }

    private void fetchMovies() {
        new FetchMoviesTask(getContext(), new Callback<Movies, Error>() {
            @Override
            public void onSuccess(Movies movies) {
                moviesAdapter = new MoviesAdapter(getContext(), movies);
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

    private void launchSettings() {
        Intent settingsIntent = new Intent(getActivity(), SettingsActivity.class);
        startActivity(settingsIntent);
    }

    private void showErrorDialog(Error error) {
        new AlertDialog.Builder(getContext())
                .setTitle(error.statusCode.toString())
                .setMessage(error.statusMessage)
                .create()
                .show();
    }
}
