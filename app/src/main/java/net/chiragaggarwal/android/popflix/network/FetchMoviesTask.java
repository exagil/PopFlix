package net.chiragaggarwal.android.popflix.network;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;

import net.chiragaggarwal.android.popflix.BuildConfig;
import net.chiragaggarwal.android.popflix.NetworkUtilities;
import net.chiragaggarwal.android.popflix.R;
import net.chiragaggarwal.android.popflix.models.Callback;
import net.chiragaggarwal.android.popflix.models.Error;
import net.chiragaggarwal.android.popflix.models.Movies;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;

public class FetchMoviesTask extends AsyncTask<Void, Void, Object> {
    private final NetworkUtilities networkUtilities;
    private Callback<Movies, Error> callback;
    private Context context;
    private String sortOrder;

    public FetchMoviesTask(String sortOrder, Context context, NetworkUtilities networkUtilities,
                           Callback<Movies, Error> callback) {
        this.sortOrder = sortOrder;
        this.context = context;
        this.networkUtilities = networkUtilities;
        this.callback = callback;
    }

    @Override
    protected Object doInBackground(Void... params) {
        if (networkUtilities.isInternetConnectionNotPresent())
            return new Error(Error.INTERNAL_SERVER_ERROR, context.getString(R.string.error_no_internet_connection));
        try {
            URL url = buildFetchMoviesUrl();
            HttpURLConnection connection = ((HttpURLConnection) url.openConnection());
            Integer responseCode = connection.getResponseCode();
            if (networkUtilities.isResponseSuccessful(responseCode)) {
                Movies movies = buildMoviesFromResponse(connection);
                return movies;
            } else {
                Error error = buildErrorFromResponse(connection);
                return error;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Object result) {
        if (result == null)
            this.callback.onUnexpectedFailure();
        else if (result instanceof Movies) {
            Movies movies = ((Movies) result);
            this.callback.onSuccess(movies);
        } else {
            Error error = ((Error) result);
            this.callback.onFailure(error);
        }
    }

    private URL buildFetchMoviesUrl() throws MalformedURLException {
        Uri uri = new Uri.Builder()
                .scheme(context.getString(R.string.scheme))
                .authority(context.getString(R.string.base_url))
                .path(context.getString(R.string.three))
                .appendPath(context.getString(R.string.discover))
                .appendPath(context.getString(R.string.movie))
                .appendQueryParameter(context.getString(R.string.sort_by), this.sortOrder)
                .appendQueryParameter(context.getString(R.string.tmdb_api_key_key),
                        BuildConfig.TMBDB_API_KEY)
                .build();
        URL url = new URL(uri.toString());
        return url;
    }

    private Movies buildMoviesFromResponse(HttpURLConnection connection) throws IOException, JSONException, ParseException {
        JSONObject moviesJson = new JsonResponseDeserializer(connection).buildSuccessfulResponseJsonObject();
        Movies movies = Movies.fromJson(moviesJson);
        return movies;
    }

    private Error buildErrorFromResponse(HttpURLConnection connection) throws IOException, JSONException {
        JSONObject errorJson = new JsonResponseDeserializer(connection).buildErrorJsonObject();
        Error error = Error.fromJSON(errorJson);
        return error;
    }
}
