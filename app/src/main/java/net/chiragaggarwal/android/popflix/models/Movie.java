package net.chiragaggarwal.android.popflix.models;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.annotation.NonNull;

import net.chiragaggarwal.android.popflix.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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
    private static final String ID = "id";

    private Integer id;
    public String originalTitle;
    public String posterPath;
    public Date releaseDate;
    public String overview;
    public Double voteAverage;

    public Movie(@NonNull Integer id, String originalTitle, Date releaseDate, String posterPath, Double voteAverage, String overview) {
        this.id = id;
        this.originalTitle = originalTitle;
        this.posterPath = posterPath;
        this.releaseDate = releaseDate;
        this.voteAverage = voteAverage;
        this.overview = overview;
    }

    public static Movie fromJson(JSONObject movieJsonObject) throws JSONException, ParseException {
        Integer id = movieJsonObject.getInt(ID);
        String originalTitle = movieJsonObject.getString(ORIGINAL_TITLE);
        String posterPath = movieJsonObject.getString(POSTER_PATH);
        Date releaseDate = parseReleaseDate(movieJsonObject);
        String overview = movieJsonObject.getString(OVERVIEW);
        Double voteAverage = movieJsonObject.getDouble(VOTE_AVERAGE);
        return new Movie(id, originalTitle, releaseDate, posterPath, voteAverage, overview);
    }

    public String imageUrlString(Context context) {
        String baseImageUrl = context.getString(R.string.base_image_url);
        String defaultImageSize = context.getString(R.string.default_image_size);
        return buildImageUrlString(baseImageUrl, defaultImageSize, this.posterPath);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Movie movie = (Movie) o;

        if (id != null ? !id.equals(movie.id) : movie.id != null) return false;
        if (originalTitle != null ? !originalTitle.equals(movie.originalTitle) : movie.originalTitle != null)
            return false;
        if (posterPath != null ? !posterPath.equals(movie.posterPath) : movie.posterPath != null)
            return false;
        if (releaseDate != null ? !releaseDate.equals(movie.releaseDate) : movie.releaseDate != null)
            return false;
        if (overview != null ? !overview.equals(movie.overview) : movie.overview != null)
            return false;
        return !(voteAverage != null ? !voteAverage.equals(movie.voteAverage) : movie.voteAverage != null);

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (originalTitle != null ? originalTitle.hashCode() : 0);
        result = 31 * result + (posterPath != null ? posterPath.hashCode() : 0);
        result = 31 * result + (releaseDate != null ? releaseDate.hashCode() : 0);
        result = 31 * result + (overview != null ? overview.hashCode() : 0);
        result = 31 * result + (voteAverage != null ? voteAverage.hashCode() : 0);
        return result;
    }

    public String yearString() {
        if (this.releaseDate == null) return null;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(this.releaseDate);
        return String.valueOf(calendar.get(Calendar.YEAR));
    }

    private static Date parseReleaseDate(JSONObject movieJsonObject) throws ParseException, JSONException {
        String releaseDateString = movieJsonObject.getString(RELEASE_DATE);
        if (releaseDateString.isEmpty()) return null;
        return new SimpleDateFormat(RELEASE_DATE_PATTERN).parse(releaseDateString);
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
        dest.writeValue(this.id);
        dest.writeString(this.originalTitle);
        dest.writeString(this.posterPath);
        dest.writeLong(releaseDate != null ? releaseDate.getTime() : -1);
        dest.writeString(this.overview);
        dest.writeValue(this.voteAverage);
    }

    protected Movie(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.originalTitle = in.readString();
        this.posterPath = in.readString();
        long tmpReleaseDate = in.readLong();
        this.releaseDate = tmpReleaseDate == -1 ? null : new Date(tmpReleaseDate);
        this.overview = in.readString();
        this.voteAverage = (Double) in.readValue(Double.class.getClassLoader());
    }

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };

    public String idString() {
        if (this.id == null) return null;
        return this.id.toString();
    }
}
