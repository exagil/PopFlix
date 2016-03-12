package net.chiragaggarwal.android.popflix.presentation;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
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
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import net.chiragaggarwal.android.popflix.NetworkUtilities;
import net.chiragaggarwal.android.popflix.R;
import net.chiragaggarwal.android.popflix.models.Callback;
import net.chiragaggarwal.android.popflix.models.Error;
import net.chiragaggarwal.android.popflix.models.Movie;
import net.chiragaggarwal.android.popflix.models.Videos;
import net.chiragaggarwal.android.popflix.network.FetchVideosTask;

public class DetailsFragment extends Fragment {
    private static final String DIVIDED_BY_TEN = " / 10";
    private static final String LOG_TAG = "popflix.detailsfragment";

    private TextView movieName;
    private ImageView moviePoster;
    private TextView movieYear;
    private TextView movieOverview;
    private TextView movieAverage;
    private ListView listVideos;
    private ProgressBar videoLoadingProgressBar;
    private TextView textVideoErrorMessage;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_movie, container, false);
        setHasOptionsMenu(true);
        Movie movie = fetchMovieFromArguments();
        initializeViews(view);
        showDetailsFor(movie);
        loadVideosFor(movie);
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
        this.listVideos = (ListView) view.findViewById(R.id.list_videos);
        this.videoLoadingProgressBar = (ProgressBar) view.findViewById(R.id.progressbar_videos);
        this.textVideoErrorMessage = ((TextView) view.findViewById(R.id.text_video_error_message));
    }

    private void showDetailsFor(Movie movie) {
        this.movieName.setText(movie.originalTitle);
        showPoster(movie);
        this.movieYear.setText(movie.yearString());
        this.movieOverview.setText(movie.overview);
        this.movieAverage.setText(movie.voteAverage + DIVIDED_BY_TEN);
    }

    private void loadVideosFor(Movie movie) {
        startVideoLoadingProgressBar();
        Context context = getContext();
        new FetchVideosTask(movie.idString(),
                context,
                new NetworkUtilities(context),
                new Callback<Videos, Error>() {

                    @Override
                    public void onSuccess(Videos videos) {
                        stopVideoLoadingProgressBar();
                        showVideos(videos);
                    }

                    @Override
                    public void onFailure(Error error) {
                        stopVideoLoadingProgressBar();
                        showVideoLoadingFailureError(error.statusMessage);
                    }

                    @Override
                    public void onUnexpectedFailure() {
                        Log.e(LOG_TAG, "Fetching Videos - Unexpected Failure");
                    }
                }).execute();
    }

    private void showPoster(Movie movie) {
        Picasso.with(getContext()).
                load(movie.imageUrlString(getContext()))
                .into(this.moviePoster);
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private void startVideoLoadingProgressBar() {
        this.videoLoadingProgressBar.setVisibility(ProgressBar.VISIBLE);
        this.videoLoadingProgressBar.animate().start();
    }

    @TargetApi(Build.VERSION_CODES.ICE_CREAM_SANDWICH)
    private void stopVideoLoadingProgressBar() {
        this.videoLoadingProgressBar.animate().cancel();
        this.videoLoadingProgressBar.setVisibility(ProgressBar.GONE);
    }

    private void showVideos(Videos videos) {
        MovieVideosAdapter movieVideosAdapter = new MovieVideosAdapter(videos, getContext());
        this.listVideos.setVisibility(ListView.VISIBLE);
        this.listVideos.setAdapter(movieVideosAdapter);
    }

    private void showVideoLoadingFailureError(String message) {
        this.textVideoErrorMessage.setVisibility(TextView.VISIBLE);
        this.textVideoErrorMessage.setText(message);
    }
}
