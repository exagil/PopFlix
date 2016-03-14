package net.chiragaggarwal.android.popflix.repository;

import android.content.Context;
import android.provider.BaseColumns;

import net.chiragaggarwal.android.popflix.R;

public class PopFlixContract {
    private static PopFlixContract instance;
    private static Context context;

    public static PopFlixContract getInstance(Context context) {
        if (instance == null) instance = new PopFlixContract(context);
        return instance;
    }

    public String getDatabaseName() {
        return this.context.getString(R.string.database_name);
    }

    public static class MoviesEntry implements BaseColumns {
        public static final String TABLE_NAME = "movies";
        public static final String MOVIE_ID = "movie_id";
        public static final String ORIGINAL_TITLE = "original_title";
        public static final String POSTER_PATH = "poster_path";
        public static final String RELEASE_DATE = "release_date";
        public static final String POPULARITY = "popularity";
        public static final String VOTE_AVERAGE = "vote_average";
        public static final String OVERVIEW = "overview";
    }

    public class VideosEntry {
        public static final String TABLE_NAME = "videos";
    }


    public class ReviewsEntry {
        public static final String TABLE_NAME = "reviews";
    }

    private PopFlixContract(Context context) {
        this.context = context;
    }
}
