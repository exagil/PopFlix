package net.chiragaggarwal.android.popflix.network;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;

import net.chiragaggarwal.android.popflix.BuildConfig;
import net.chiragaggarwal.android.popflix.NetworkUtilities;
import net.chiragaggarwal.android.popflix.R;
import net.chiragaggarwal.android.popflix.models.Callback;
import net.chiragaggarwal.android.popflix.models.Error;
import net.chiragaggarwal.android.popflix.models.Videos;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;

public class FetchVideosTask extends AsyncTask<Void, Void, Object> {
    private final NetworkUtilities networkUtilities;
    private final String movieIdString;
    private Callback<Videos, Error> callback;
    private Context context;

    public FetchVideosTask(String movieIdString,
                           Context context,
                           NetworkUtilities networkUtilities,
                           Callback<Videos, Error> callback) {
        this.movieIdString = movieIdString;
        this.context = context;
        this.networkUtilities = networkUtilities;
        this.callback = callback;
    }

    @Override
    protected Object doInBackground(Void... params) {
        if (networkUtilities.isInternetConnectionNotPresent())
            return new Error(Error.INTERNAL_SERVER_ERROR, context.getString(R.string.error_no_internet_connection));
        try {
            URL url = buildFetchVideosUrl();
            HttpURLConnection connection = ((HttpURLConnection) url.openConnection());
            Integer responseCode = connection.getResponseCode();
            if (networkUtilities.isResponseSuccessful(responseCode)) {
                Videos videos = buildVideosFromResponse(connection);
                return videos;
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
        else if (result instanceof Videos) {
            Videos movies = ((Videos) result);
            this.callback.onSuccess(movies);
        } else {
            Error error = ((Error) result);
            this.callback.onFailure(error);
        }
    }

    private URL buildFetchVideosUrl() throws MalformedURLException {
        Uri uri = new Uri.Builder()
                .scheme(context.getString(R.string.scheme))
                .authority(context.getString(R.string.base_url))
                .path(context.getString(R.string.three))
                .appendPath(context.getString(R.string.movie))
                .appendPath(this.movieIdString)
                .appendPath(context.getString(R.string.videos))
                .appendQueryParameter(context.getString(R.string.tmdb_api_key_key),
                        BuildConfig.TMBDB_API_KEY)
                .build();
        URL url = new URL(uri.toString());
        return url;
    }

    private Videos buildVideosFromResponse(HttpURLConnection connection) throws IOException, JSONException, ParseException {
        JSONObject videosJson = new JsonResponseDeserializer(connection).buildSuccessfulResponseJsonObject();
        Videos videos = Videos.fromJson(videosJson);
        return videos;
    }

    private Error buildErrorFromResponse(HttpURLConnection connection) throws IOException, JSONException {
        JSONObject errorJson = new JsonResponseDeserializer(connection).buildErrorJsonObject();
        Error error = Error.fromJSON(errorJson);
        return error;
    }
}
