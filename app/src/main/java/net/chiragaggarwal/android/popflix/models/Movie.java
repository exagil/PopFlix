package net.chiragaggarwal.android.popflix.models;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import net.chiragaggarwal.android.popflix.R;

import org.json.JSONException;
import org.json.JSONObject;

public class Movie implements Parcelable {
    public static final String TAG = "net.chiragaggarwal.android.popflix.models,Movie";

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
        if (that == null || !(that instanceof Movie)) return false;
        if (this.hashCode() == that.hashCode()) return true;
        Movie thatMovie = ((Movie) that);
        return (this.originalTitle.equals(thatMovie.originalTitle)) || (this.posterPath.equals(thatMovie.posterPath));
    }

    @Override
    public int hashCode() {
        int result = posterPath != null ? posterPath.hashCode() : 0;
        result = 31 * result + (originalTitle != null ? originalTitle.hashCode() : 0);
        return result;
    }

    private String buildImageUrlString(String baseImageUri, String defaultImageSize, String posterPath) {
        return baseImageUri + SLASH + defaultImageSize + SLASH + posterPath;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.posterPath);
        dest.writeString(this.originalTitle);
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    protected Movie(Parcel in) {
        this.posterPath = in.readString();
        this.originalTitle = in.readString();
    }
}
