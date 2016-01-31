package net.chiragaggarwal.android.popflix;

import android.content.Context;
import android.net.Uri;
import android.os.AsyncTask;

import net.chiragaggarwal.android.popflix.models.Callback;
import net.chiragaggarwal.android.popflix.models.Movies;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class FetchMoviesTask extends AsyncTask<Void, Void, Movies> {
    private final Callback<Movies> callback;
    private final Context context;

    public FetchMoviesTask(Context context, Callback<Movies> callback) {
        this.context = context;
        this.callback = callback;
    }

    @Override
    protected Movies doInBackground(Void... params) {
        Movies movies = null;
        try {
            URL url = buildFetchMoviesUrl();
            HttpURLConnection connection = ((HttpURLConnection) url.openConnection());
            Integer responseCode = connection.getResponseCode();
            if (isAcceptable(responseCode)) {
                movies = buildMoviesFromResponse(connection);
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return movies;
    }

    @Override
    protected void onPostExecute(Movies movies) {
        if (movies != null) {
            this.callback.onSuccess(movies);
        } else {
            this.callback.onFailure();
        }
    }

    private URL buildFetchMoviesUrl() throws MalformedURLException {
        Uri uri = new Uri.Builder()
                .scheme(context.getString(R.string.scheme))
                .authority(context.getString(R.string.base_url))
                .path(context.getString(R.string.three))
                .appendPath(context.getString(R.string.discover))
                .appendPath(context.getString(R.string.movie))
                .appendQueryParameter(context.getString(R.string.tmdb_api_key_key),
                        BuildConfig.TMBDB_API_KEY).build();
        URL url = new URL(uri.toString());
        return url;
    }

    private boolean isAcceptable(Integer responseCode) {
        return isStartingWithTwo(responseCode);
    }

    private boolean isStartingWithTwo(Integer responseCode) {
        return (responseCode / 100) == 2;
    }

    private Movies buildMoviesFromResponse(HttpURLConnection connection) throws IOException, JSONException {
        InputStream moviesStream = connection.getInputStream();
        BufferedReader moviesReader = new BufferedReader(new InputStreamReader(moviesStream));
        String moviesJsonString = buildMoviesJsonString(moviesReader);
        JSONObject moviesJson = new JSONObject(moviesJsonString);
        Movies movies = Movies.fromJson(moviesJson);
        return movies;
    }

    private String buildMoviesJsonString(BufferedReader moviesReader) throws IOException {
        String moviesResponseLine;
        StringBuilder moviesJsonString = new StringBuilder();

        while ((moviesResponseLine = moviesReader.readLine()) != null)
            moviesJsonString.append(moviesResponseLine);

        return moviesJsonString.toString();
    }
}
