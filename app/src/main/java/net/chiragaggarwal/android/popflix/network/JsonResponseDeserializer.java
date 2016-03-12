package net.chiragaggarwal.android.popflix.network;

import android.support.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;


// JsonResponseDeserializer parses the response from the http api
// into the JSON Format

public class JsonResponseDeserializer {
    private final HttpURLConnection httpURLConnection;

    public JsonResponseDeserializer(HttpURLConnection httpURLConnection) {
        this.httpURLConnection = httpURLConnection;
    }

    public JSONObject buildSuccessfulResponseJsonObject() throws IOException, JSONException {
        BufferedReader successfulResponseReader = getSuccessfulResponseReader();
        return parseJsonFromResponseReader(successfulResponseReader);
    }

    public JSONObject buildErrorJsonObject() throws IOException, JSONException {
        BufferedReader erroneousResponseReader = getErroneousResponseReader();
        return parseJsonFromResponseReader(erroneousResponseReader);
    }

    @NonNull
    private JSONObject parseJsonFromResponseReader(BufferedReader responseReader) throws IOException, JSONException {
        StringBuilder responseBuilder = new StringBuilder();
        String successfulResponseLine;

        while ((successfulResponseLine = responseReader.readLine()) != null)
            responseBuilder.append(successfulResponseLine);

        String jsonResponseString = responseBuilder.toString();
        JSONObject jsonResponse = new JSONObject(jsonResponseString);
        return jsonResponse;
    }

    @NonNull
    private BufferedReader getSuccessfulResponseReader() throws IOException {
        InputStream responseStream = this.httpURLConnection.getInputStream();
        return new BufferedReader(new InputStreamReader(responseStream));
    }

    @NonNull
    private BufferedReader getErroneousResponseReader() throws IOException {
        InputStream errorStream = this.httpURLConnection.getErrorStream();
        return new BufferedReader(new InputStreamReader(errorStream));
    }
}
