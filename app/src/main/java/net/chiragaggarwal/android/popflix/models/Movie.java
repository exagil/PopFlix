package net.chiragaggarwal.android.popflix.models;

import android.content.ContentValues;
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

import static net.chiragaggarwal.android.popflix.data.PopFlixContract.MoviesEntry;

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
    private static final String POPULARITY = "popularity";
    private static final String IS_FAVORITE = "is_favorite";
    private static final String YYYY_MM_DD = "yyyy-MM-dd";
    private static final Integer NOT_FAVORITE = 0;
    private static final Integer FAVORITE = 1;

    private Integer id;
    public String originalTitle;
    public String posterPath;
    public Date releaseDate;
    public String overview;
    private Boolean isFavourite;
    private Double popularity;
    public Double voteAverage;

    public Movie(@NonNull Integer id, String originalTitle, Date releaseDate, String posterPath,
                 Double popularity, Double voteAverage, String overview, Boolean isFavourite) {

        this.id = id;
        this.originalTitle = originalTitle;
        this.posterPath = posterPath;
        this.releaseDate = releaseDate;
        this.popularity = popularity;
        this.voteAverage = voteAverage;
        this.overview = overview;
        this.isFavourite = isFavourite;
    }

    public static Movie fromJson(JSONObject movieJsonObject) throws JSONException, ParseException {
        Integer id = movieJsonObject.getInt(ID);
        String originalTitle = movieJsonObject.getString(ORIGINAL_TITLE);
        String posterPath = movieJsonObject.getString(POSTER_PATH);
        Date releaseDate = parseReleaseDate(movieJsonObject);
        String overview = movieJsonObject.getString(OVERVIEW);
        Double popularity = movieJsonObject.getDouble(POPULARITY);
        Double voteAverage = movieJsonObject.getDouble(VOTE_AVERAGE);
        return new Movie(id, originalTitle, releaseDate, posterPath, popularity, voteAverage, overview, false);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Movie movie = (Movie) o;

        if (!id.equals(movie.id)) return false;
        if (originalTitle != null ? !originalTitle.equals(movie.originalTitle) : movie.originalTitle != null)
            return false;
        if (posterPath != null ? !posterPath.equals(movie.posterPath) : movie.posterPath != null)
            return false;
        if (releaseDate != null ? !releaseDate.equals(movie.releaseDate) : movie.releaseDate != null)
            return false;
        if (overview != null ? !overview.equals(movie.overview) : movie.overview != null)
            return false;
        if (popularity != null ? !popularity.equals(movie.popularity) : movie.popularity != null)
            return false;
        return !(voteAverage != null ? !voteAverage.equals(movie.voteAverage) : movie.voteAverage != null);

    }

    @Override
    public int hashCode() {
        int result = id.hashCode();
        result = 31 * result + (originalTitle != null ? originalTitle.hashCode() : 0);
        result = 31 * result + (posterPath != null ? posterPath.hashCode() : 0);
        result = 31 * result + (releaseDate != null ? releaseDate.hashCode() : 0);
        result = 31 * result + (overview != null ? overview.hashCode() : 0);
        result = 31 * result + (popularity != null ? popularity.hashCode() : 0);
        result = 31 * result + (voteAverage != null ? voteAverage.hashCode() : 0);
        return result;
    }

    public String imageUrlString(Context context) {
        String baseImageUrl = context.getString(R.string.base_image_url);
        String defaultImageSize = context.getString(R.string.default_image_size);
        return buildImageUrlString(baseImageUrl, defaultImageSize, this.posterPath);
    }

    public String yearString() {
        if (this.releaseDate == null) return null;
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(this.releaseDate);
        return String.valueOf(calendar.get(Calendar.YEAR));
    }

    public ContentValues toContentValues() {
        ContentValues movieContentValues = new ContentValues();
        movieContentValues.put(MoviesEntry.MOVIE_ID, this.id);
        movieContentValues.put(MoviesEntry.ORIGINAL_TITLE, this.originalTitle);
        movieContentValues.put(MoviesEntry.RELEASE_DATE, serializedReleaseDate());
        movieContentValues.put(MoviesEntry.POSTER_PATH, this.posterPath);
        movieContentValues.put(MoviesEntry.POPULARITY, this.popularity);
        movieContentValues.put(MoviesEntry.VOTE_AVERAGE, this.voteAverage);
        movieContentValues.put(MoviesEntry.OVERVIEW, this.overview);
        movieContentValues.put(MoviesEntry.IS_FAVORITE, serializedIsFavoriteValue());
        return movieContentValues;
    }

    public String idString() {
        if (this.id == null) return null;
        return this.id.toString();
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
        dest.writeValue(this.popularity);
        dest.writeValue(this.voteAverage);
    }

    protected Movie(Parcel in) {
        this.id = (Integer) in.readValue(Integer.class.getClassLoader());
        this.originalTitle = in.readString();
        this.posterPath = in.readString();
        long tmpReleaseDate = in.readLong();
        this.releaseDate = tmpReleaseDate == -1 ? null : new Date(tmpReleaseDate);
        this.overview = in.readString();
        this.popularity = (Double) in.readValue(Double.class.getClassLoader());
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

    private static Date parseReleaseDate(JSONObject movieJsonObject) throws ParseException, JSONException {
        String releaseDateString = movieJsonObject.getString(RELEASE_DATE);
        if (releaseDateString.isEmpty()) return null;
        return new SimpleDateFormat(RELEASE_DATE_PATTERN).parse(releaseDateString);
    }

    private String buildImageUrlString(String baseImageUri, String defaultImageSize, String posterPath) {
        return baseImageUri + SLASH + defaultImageSize + SLASH + posterPath;
    }

    private String serializedReleaseDate() {
        SimpleDateFormat serializedReleaseDateFormat = new SimpleDateFormat(YYYY_MM_DD);
        String serializedReleaseDate = serializedReleaseDateFormat.format(this.releaseDate);
        return serializedReleaseDate;
    }

    private Integer serializedIsFavoriteValue() {
        Integer serializedIsFavoriteValue = NOT_FAVORITE;
        if (this.isFavourite.equals(Boolean.TRUE)) serializedIsFavoriteValue = FAVORITE;
        return serializedIsFavoriteValue;
    }
}
