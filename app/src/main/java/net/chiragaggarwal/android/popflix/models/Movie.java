package net.chiragaggarwal.android.popflix.models;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
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
    public static final String FAVORITE_SELECTION_ARGS = "1";

    private static final String ORIGINAL_TITLE = "original_title";
    private static final String POSTER_PATH = "poster_path";
    private static final String SLASH = "/";
    private static final String RELEASE_DATE_PATTERN = "yyyy-MM-dd";
    private static final String RELEASE_DATE = "release_date";
    private static final String OVERVIEW = "overview";
    private static final String VOTE_AVERAGE = "vote_average";
    private static final String ID = "id";
    private static final String POPULARITY = "popularity";
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

    public static Movie fromContentValues(ContentValues movieContentValues) throws ParseException {
        Integer id = movieContentValues.getAsInteger(MoviesEntry.MOVIE_ID);
        String originalTitle = movieContentValues.getAsString(MoviesEntry.ORIGINAL_TITLE);
        String serializedReleaseDate = movieContentValues.getAsString(MoviesEntry.RELEASE_DATE);
        Date releaseDate = deserializeReleaseDate(serializedReleaseDate);
        String posterPath = movieContentValues.getAsString(MoviesEntry.POSTER_PATH);
        Double popularity = movieContentValues.getAsDouble(MoviesEntry.POPULARITY);
        Double voteAverage = movieContentValues.getAsDouble(MoviesEntry.VOTE_AVERAGE);
        String overview = movieContentValues.getAsString(MoviesEntry.OVERVIEW);
        Integer serializedIsFavoriteValue = movieContentValues.getAsInteger(MoviesEntry.IS_FAVORITE);
        Boolean isFavorite = deserializeIsFavoriteValue(serializedIsFavoriteValue);
        return new Movie(id, originalTitle, releaseDate, posterPath, popularity, voteAverage, overview, isFavorite);
    }

    public static Movie fromCursor(Cursor movieCursor) throws ParseException {
        int movieIdColumnIndex = movieCursor.getColumnIndex(MoviesEntry.MOVIE_ID);
        int originalTitleColumnIndex = movieCursor.getColumnIndex(MoviesEntry.ORIGINAL_TITLE);
        int posterPathColumnIndex = movieCursor.getColumnIndex(MoviesEntry.POSTER_PATH);
        int releaseDateColumnIndex = movieCursor.getColumnIndex(MoviesEntry.RELEASE_DATE);
        int popularityColumnIndex = movieCursor.getColumnIndex(MoviesEntry.POPULARITY);
        int voteAverageColumnIndex = movieCursor.getColumnIndex(MoviesEntry.VOTE_AVERAGE);
        int overviewColumnIndex = movieCursor.getColumnIndex(MoviesEntry.OVERVIEW);
        int isFavoriteColumnIndex = movieCursor.getColumnIndex(MoviesEntry.IS_FAVORITE);

        Integer id = movieCursor.getInt(movieIdColumnIndex);
        String originalTitle = movieCursor.getString(originalTitleColumnIndex);
        String posterPath = movieCursor.getString(posterPathColumnIndex);
        String serealizedReleaseDate = movieCursor.getString(releaseDateColumnIndex);
        Date releaseDate = deserializeReleaseDate(serealizedReleaseDate);
        Double popularity = movieCursor.getDouble(popularityColumnIndex);
        Double voteAverage = movieCursor.getDouble(voteAverageColumnIndex);
        String overview = movieCursor.getString(overviewColumnIndex);
        Integer serializedIsFavoriteValue = movieCursor.getInt(isFavoriteColumnIndex);
        Boolean isFavorite = deserializeIsFavoriteValue(serializedIsFavoriteValue);
        return new Movie(id, originalTitle, releaseDate, posterPath, popularity, voteAverage, overview, isFavorite);
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

    public String imageUrlString(Context context, ImageSize imageSize) {
        String baseImageUrl = context.getString(R.string.base_image_url);
        return buildImageUrlString(baseImageUrl, imageSize.decode(), this.posterPath);
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
        movieContentValues.put(MoviesEntry.RELEASE_DATE, serializeReleaseDate(this.releaseDate));
        movieContentValues.put(MoviesEntry.POSTER_PATH, this.posterPath);
        movieContentValues.put(MoviesEntry.POPULARITY, this.popularity);
        movieContentValues.put(MoviesEntry.VOTE_AVERAGE, this.voteAverage);
        movieContentValues.put(MoviesEntry.OVERVIEW, this.overview);
        movieContentValues.put(MoviesEntry.IS_FAVORITE, serializeIsFavoriteValue(this.isFavourite));
        return movieContentValues;
    }

    public String idString() {
        if (this.id == null) return null;
        return this.id.toString();
    }

    public Boolean isFavorite() {
        return this.isFavourite;
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

    private static Date deserializeReleaseDate(String dateString) throws ParseException {
        if (dateString == null) return null;
        return new SimpleDateFormat(RELEASE_DATE_PATTERN).parse(dateString);
    }

    private static Boolean deserializeIsFavoriteValue(Integer serializedIsFavoriteValue) {
        Boolean deserializedIsFavoriteValue = Boolean.FALSE;
        if (serializedIsFavoriteValue.equals(FAVORITE)) deserializedIsFavoriteValue = Boolean.TRUE;
        return deserializedIsFavoriteValue;
    }

    private String buildImageUrlString(String baseImageUri, String defaultImageSize, String posterPath) {
        return baseImageUri + SLASH + defaultImageSize + SLASH + posterPath;
    }

    private String serializeReleaseDate(Date releaseDate) {
        SimpleDateFormat serializedReleaseDateFormat = new SimpleDateFormat(YYYY_MM_DD);
        String serializedReleaseDate = serializedReleaseDateFormat.format(releaseDate);
        return serializedReleaseDate;
    }

    private Integer serializeIsFavoriteValue(Boolean isFavourite) {
        Integer serializedIsFavoriteValue = NOT_FAVORITE;
        if (isFavourite.equals(Boolean.TRUE)) serializedIsFavoriteValue = FAVORITE;
        return serializedIsFavoriteValue;
    }
}
