package net.chiragaggarwal.android.popflix.presentation;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import net.chiragaggarwal.android.popflix.R;
import net.chiragaggarwal.android.popflix.models.Movie;
import net.chiragaggarwal.android.popflix.models.Movies;

public class MoviesAdapter extends BaseAdapter {
    private final Context context;
    private Movies movies;

    public MoviesAdapter(Context context, Movies movies) {
        this.context = context;
        this.movies = movies;
    }

    public void populateMovies(Movies movies) {
        this.movies = movies;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return this.movies.count();
    }

    @Override
    public Movie getItem(int position) {
        return this.movies.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Movie movie = getItem(position);
        View view;
        if (convertView == null) {
            view = LayoutInflater.from(this.context).inflate(R.layout.grid_item_movie, parent, false);
        } else {
            view = convertView;
        }
        ImageView moviePoster = (ImageView) view.findViewById(R.id.grid_item_movie_poster);
        setImageOnMoviePoster(movie, moviePoster);
        return view;
    }

    public void clear() {
        this.movies = new Movies();
        notifyDataSetChanged();
    }

    private void setImageOnMoviePoster(Movie movie, ImageView moviePoster) {
        Picasso.with(this.context)
                .load(movie.imageUrlString(this.context))
                .into(moviePoster);
        moviePoster.setAdjustViewBounds(true);
    }
}
