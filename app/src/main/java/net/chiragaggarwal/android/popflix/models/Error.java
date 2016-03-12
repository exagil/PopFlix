package net.chiragaggarwal.android.popflix.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Error {
    public static final Integer INTERNAL_SERVER_ERROR = 500;

    private static final String STATUS_CODE = "status_code";
    private static final String STATUS_MESSAGE = "status_message";

    public Integer statusCode;
    public String statusMessage;

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
