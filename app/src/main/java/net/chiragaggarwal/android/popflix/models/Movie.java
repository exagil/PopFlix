package net.chiragaggarwal.android.popflix.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Movie {
    private static final String ORIGINAL_TITLE = "original_title";
    private static final String POSTER_PATH = "poster_path";
    private final String posterPath;
    private final String originalTitle;

    public Movie(String originalTitle, String posterPath) {
        this.originalTitle = originalTitle;
        this.posterPath = posterPath;
    }

    public static Movie fromJson(JSONObject movieJsonObject) throws JSONException {
        String originalTitle = movieJsonObject.getString(ORIGINAL_TITLE);
        String posterPath = movieJsonObject.getString(POSTER_PATH);
        return new Movie(originalTitle, posterPath);
    }
}
