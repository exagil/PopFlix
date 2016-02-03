package net.chiragaggarwal.android.popflix.presentation;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.chiragaggarwal.android.popflix.R;
import net.chiragaggarwal.android.popflix.models.Movie;

public class DetailsFragment extends Fragment {
    private static final String DIVIDED_BY_TEN = " / 10";

    private TextView movieName;
    private ImageView moviePoster;
    private TextView movieYear;
    private TextView movieOverview;
    private TextView movieAverage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie, container, false);
        setHasOptionsMenu(true);
        Movie movie = fetchMovieFromArguments();
        initializeViews(view);
        showDetailsFor(movie);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.menu_details, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                getActivity().finish();
                break;
        }
        return false;
    }

    private Movie fetchMovieFromArguments() {
        return getArguments().getParcelable(Movie.TAG);
    }

    private void initializeViews(View view) {
        this.movieName = ((TextView) view.findViewById(R.id.movie_name));
        this.moviePoster = ((ImageView) view.findViewById(R.id.movie_poster));
        this.movieYear = ((TextView) view.findViewById(R.id.movie_year));
        this.movieOverview = ((TextView) view.findViewById(R.id.movie_overview));
        this.movieAverage = ((TextView) view.findViewById(R.id.movie_vote_average));
    }

    private void showDetailsFor(Movie movie) {
        this.movieName.setText(movie.originalTitle);
        showPoster(movie);
        this.movieYear.setText(movie.yearString());
        this.movieOverview.setText(movie.overview);
        this.movieAverage.setText(movie.voteAverage + DIVIDED_BY_TEN);
    }

    private void showPoster(Movie movie) {
        Picasso.with(getContext()).
                load(movie.imageUrlString(getContext()))
                .into(this.moviePoster);
    }
}
