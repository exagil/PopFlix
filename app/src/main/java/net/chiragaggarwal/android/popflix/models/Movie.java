package net.chiragaggarwal.android.popflix.models;

import android.content.Context;

import net.chiragaggarwal.android.popflix.R;

import org.json.JSONException;
import org.json.JSONObject;

public class Movie {
    private static final String ORIGINAL_TITLE = "original_title";
    private static final String POSTER_PATH = "poster_path";
    private static final String SLASH = "/";

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

    public String imageUrlString(Context context) {
        String baseImageUrl = context.getString(R.string.base_image_url);
        String defaultImageSize = context.getString(R.string.default_image_size);
        return buildImageUrlString(baseImageUrl, defaultImageSize, this.posterPath);
    }

    @Override
    public boolean equals(Object that) {
        if (that == null) return false;
        if (this.hashCode() == that.hashCode()) return true;
        return true;
    }

    private String buildImageUrlString(String baseImageUri, String defaultImageSize, String posterPath) {
        return baseImageUri + SLASH + defaultImageSize + SLASH + posterPath;
    }
}
