package net.chiragaggarwal.android.popflix.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Review {

    private static final String ID = "id";
    private static final String AUTHOR = "author";
    private static final String CONTENT = "content";
    private static final String URL = "url";

    private String id;
    public String author;
    public String content;
    private String urlString;

    public Review(String id, String author, String content, String urlString) {
        this.id = id;
        this.author = author;
        this.content = content;
        this.urlString = urlString;
    }

    public static Review fromJson(JSONObject reviewJsonObject) throws JSONException {
        String id = reviewJsonObject.getString(ID);
        String author = reviewJsonObject.getString(AUTHOR);
        String content = reviewJsonObject.getString(CONTENT);
        String urlString = reviewJsonObject.getString(URL);
        return new Review(id, author, content, urlString);
    }
}
