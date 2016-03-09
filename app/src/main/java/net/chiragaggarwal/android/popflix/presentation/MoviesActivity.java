package net.chiragaggarwal.android.popflix.presentation;

import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import net.chiragaggarwal.android.popflix.R;

public class MoviesActivity extends AppCompatActivity {

    private static final String POP_FLIX = "PopFlix";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        initializeToolbar();
        initializePreferences();

        showMovies();
    }

    private void initializeToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.default_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(POP_FLIX);
    }

    private void initializePreferences() {
        PreferenceManager.setDefaultValues(this, R.xml.settings, false);
    }

    private void showMovies() {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.movies_placeholder, new MoviesFragment())
                .commit();
    }
}
