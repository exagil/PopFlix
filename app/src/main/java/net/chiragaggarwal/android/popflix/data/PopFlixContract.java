package net.chiragaggarwal.android.popflix.data;

import android.content.ContentUris;
import android.content.Context;
import android.net.Uri;
import android.provider.BaseColumns;
import android.support.annotation.NonNull;

import net.chiragaggarwal.android.popflix.R;

public class PopFlixContract {
    private static final String ANY_NUMBER_MATCHER = "#";
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
        public static final String IS_FAVORITE = "is_favorite";
        private static final String TYPE_PART = "vnd";
        private static final String DOT = ".";
        private static final String COLLECTION_SUBTYPE_PART = "android.cursor.dir";
        private static final String SLASH = "/";
        private static final String ITEM_SUBTYPE_PART = "android.cursor.item";
        public static final String FAVORITE_SELECTION = "is_favorite=?";
        public static final String MOVIE_ID_SELECTION = "movie_id=?";

        public static String PROVIDER_AUTHORITY = "net.chiragaggarwal.android.popflix.data.movies-provider";
        public static final String MOVIES_PATH = TABLE_NAME;
        public static final String MOVIE_PATH = "movies/#";

        public static Uri buildMoviesUri() {
            return Uri.parse(buildMoviesUriString());
        }

        public static String buildMoviesMimeType() {
            String mimeTypeMovies = TYPE_PART + DOT + COLLECTION_SUBTYPE_PART + SLASH + TYPE_PART + DOT +
                    PROVIDER_AUTHORITY + DOT + TABLE_NAME;
            return mimeTypeMovies;
        }

        public static Uri buildMovieUri(long movieId) {
            Uri moviesUri = Uri.parse(buildMoviesUriString());
            Uri movieUri = ContentUris.withAppendedId(moviesUri, movieId);
            return movieUri;
        }

        @NonNull
        private static String buildMoviesUriString() {
            return "content://" + PROVIDER_AUTHORITY + SLASH + TABLE_NAME;
        }

        public static String buildMovieMimeType() {
            return TYPE_PART + DOT + ITEM_SUBTYPE_PART + SLASH + TYPE_PART + DOT + PROVIDER_AUTHORITY + DOT + TABLE_NAME;
        }
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


    public class ReviewsEntry implements BaseColumns {
        public static final String TABLE_NAME = "reviews";
        public static final String REVIEW_ID = "review_id";
        public static final String AUTHOR = "author";
        public static final String CONTENT = "content";
        public static final String URL_STRING = "url_string";
    }

    private PopFlixContract(Context context) {
        this.context = context;
    }
}
