package net.chiragaggarwal.android.popflix.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Error {
    private static final String STATUS_CODE = "status_code";
    private static final String STATUS_MESSAGE = "status_message";

    private Integer statusCode;
    private String statusMessage;

    public Error(Integer statusCode, String statusMessage) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
    }

    public static Error fromJSON(JSONObject errorJson) throws JSONException {
        Integer statusCode = errorJson.getInt(STATUS_CODE);
        String statusMessage = errorJson.getString(STATUS_MESSAGE);
        return new Error(statusCode, statusMessage);
    }
}
