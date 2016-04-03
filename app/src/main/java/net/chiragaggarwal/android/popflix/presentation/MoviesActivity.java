package net.chiragaggarwal.android.popflix.presentation;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import net.chiragaggarwal.android.popflix.R;
import net.chiragaggarwal.android.popflix.models.Movie;
import net.chiragaggarwal.android.popflix.models.MoviesPreference;

public class MoviesActivity extends AppCompatActivity implements MoviesFragment.OnMovieSelectedListener {

    private static final String POP_FLIX = "PopFlix";

    @Override
    public void onMovieSelected(Movie movie) {
        if (isTabletWithLandscapeOrientation())
            showMovieOnRightPane(movie);
        else
            showMovieOnCompleteScreen(movie);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        initializeToolbar();
        initializePreferences();

        showMovies(savedInstanceState);
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateMoviesIfRequired();
    }

    private void initializeToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.default_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(POP_FLIX);
    }

    private void initializePreferences() {
        PreferenceManager.setDefaultValues(this, R.xml.settings, false);
    }

    private void showMovies(Bundle savedInstanceState) {
        if (savedInstanceState == null)
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.movies_placeholder, new MoviesFragment())
                    .commit();
    }

    private void updateMoviesIfRequired() {
        if (isRefreshRequired()) {
            MoviesFragment moviesFragment = (MoviesFragment) this.getSupportFragmentManager()
                    .findFragmentById(R.id.movies_placeholder);
            moviesFragment.refresh();
        }
    }

    private boolean isRefreshRequired() {
        return MoviesPreference.getInstance(this).isRefreshRequired();
    }

    private void showMovieOnCompleteScreen(Movie movie) {
        Intent movieIntent = new Intent(this, DetailsActivity.class);
        movieIntent.putExtra(Movie.TAG, movie);
        startActivity(movieIntent);
    }

    private void showMovieOnRightPane(Movie movie) {
        Bundle detailsArguments = new Bundle();
        detailsArguments.putParcelable(Movie.TAG, movie);

        DetailsFragment detailsFragment = new DetailsFragment();
        detailsFragment.setArguments(detailsArguments);

        getSupportFragmentManager().beginTransaction().
                replace(R.id.movie_placeholder, detailsFragment).
                commit();
    }

    private boolean isTabletWithLandscapeOrientation() {
        View moviePlaceholder = findViewById(R.id.movie_placeholder);
        return isMoviePlaceholderPresent(moviePlaceholder) && isMoviePlaceholderVisible(moviePlaceholder);
    }

    private boolean isMoviePlaceholderPresent(View moviePlaceholder) {
        return moviePlaceholder != null;
    }

    private boolean isMoviePlaceholderVisible(View moviePlaceholder) {
        return moviePlaceholder.getVisibility() == View.VISIBLE;
    }
}
