package net.chiragaggarwal.android.popflix.models;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;

import net.chiragaggarwal.android.popflix.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Movie implements Parcelable {
    public static final String TAG = "net.chiragaggarwal.android.popflix.models,Movie";

    private static final String ORIGINAL_TITLE = "original_title";
    private static final String POSTER_PATH = "poster_path";
    private static final String SLASH = "/";
    private static final String RELEASE_DATE_PATTERN = "yyyy-MM-dd";
    private static final String RELEASE_DATE = "release_date";
    private static final String OVERVIEW = "overview";
    private static final String VOTE_AVERAGE = "vote_average";

    public String originalTitle;
    public String posterPath;
    public Date releaseDate;
    public String overview;
    public Double voteAverage;

    public Movie(String originalTitle, Date releaseDate, String posterPath, Double voteAverage, String overview) {
        this.originalTitle = originalTitle;
        this.posterPath = posterPath;
        this.releaseDate = releaseDate;
        this.voteAverage = voteAverage;
        this.overview = overview;
    }

    public static Movie fromJson(JSONObject movieJsonObject) throws JSONException, ParseException {
        String originalTitle = movieJsonObject.getString(ORIGINAL_TITLE);
        String posterPath = movieJsonObject.getString(POSTER_PATH);
        Date releaseDate = new SimpleDateFormat(RELEASE_DATE_PATTERN).
                parse(movieJsonObject.getString(RELEASE_DATE));
        String overview = movieJsonObject.getString(OVERVIEW);
        Double voteAverage = movieJsonObject.getDouble(VOTE_AVERAGE);
        return new Movie(originalTitle, releaseDate, posterPath, voteAverage, overview);
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

        return (this.originalTitle.equals(thatMovie.originalTitle)) &&
                (this.posterPath.equals(thatMovie.posterPath)) &&
                ((this.releaseDate.compareTo(thatMovie.releaseDate)) == 0) &&
                (this.voteAverage == thatMovie.voteAverage) &&
                (this.overview.equals(thatMovie.overview));
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
