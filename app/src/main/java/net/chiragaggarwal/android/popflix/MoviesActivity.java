package net.chiragaggarwal.android.popflix;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class MoviesActivity extends AppCompatActivity {

    private static final String POP_FLIX = "PopFlix";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movies);
        initializeToolbar();

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.movies_placeholder, new MoviesFragment())
                .commit();
    }

    private void initializeToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.default_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(POP_FLIX);
    }
}
