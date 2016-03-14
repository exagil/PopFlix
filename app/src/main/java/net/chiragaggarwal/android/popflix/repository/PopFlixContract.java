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

    public class VideosEntry implements BaseColumns {
        public static final String TABLE_NAME = "videos";
        public static final String VIDEO_ID = "video_id";
        public static final String LANGUAGE_CODE = "language_code";
        public static final String COUNTRY_CODE = "country_code";
        public static final String KEY = "key";
        public static final String NAME = "name";
        public static final String WEBSITE = "website";
        public static final String TYPE = "type";
    }


    public class ReviewsEntry {
        public static final String TABLE_NAME = "reviews";
    }

    private PopFlixContract(Context context) {
        this.context = context;
    }
}
