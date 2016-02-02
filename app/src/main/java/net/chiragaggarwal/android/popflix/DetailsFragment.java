package net.chiragaggarwal.android.popflix;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import net.chiragaggarwal.android.popflix.models.Movie;

public class DetailsFragment extends Fragment {
    private TextView movieName;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie, container, false);
        Movie movie = fetchMovieFromArguments();
        initializeViews(view);
        showDetailsFor(movie);
        return view;
    }

    private Movie fetchMovieFromArguments() {
        return getArguments().getParcelable(Movie.TAG);
    }

    private void initializeViews(View view) {
        this.movieName = ((TextView) view.findViewById(R.id.movie_name));
    }

    private void showDetailsFor(Movie movie) {
        this.movieName.setText(movie.originalTitle);
    }
}
