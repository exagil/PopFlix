package net.chiragaggarwal.android.popflix.presentation;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import net.chiragaggarwal.android.popflix.R;
import net.chiragaggarwal.android.popflix.models.Movie;

public class DetailsActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        initializeToolbar();
        Movie movie = getMovieFromIntentExtras();
        showDetailsFragmentFor(movie);
    }

    private void initializeToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.default_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_left_white_24dp);
    }

    private Movie getMovieFromIntentExtras() {
        return getIntent().getParcelableExtra(Movie.TAG);
    }

    private void showDetailsFragmentFor(Movie movie) {
        Bundle movieBundle = prepareMovieBundle(movie);
        DetailsFragment detailsFragment = prepareDetailsFragmentWith(movieBundle);
        show(detailsFragment);
    }

    @NonNull
    private Bundle prepareMovieBundle(Movie movie) {
        Bundle movieBundle = new Bundle();
        movieBundle.putParcelable(Movie.TAG, movie);
        return movieBundle;
    }

    @NonNull
    private DetailsFragment prepareDetailsFragmentWith(Bundle movieBundle) {
        DetailsFragment detailsFragment = new DetailsFragment();
        detailsFragment.setArguments(movieBundle);
        return detailsFragment;
    }

    private void show(DetailsFragment detailsFragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.movie_placeholder, detailsFragment)
                .commit();
    }
}
