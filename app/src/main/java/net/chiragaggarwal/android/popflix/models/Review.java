package net.chiragaggarwal.android.popflix.models;

import android.support.annotation.NonNull;

import org.json.JSONException;
import org.json.JSONObject;

public class Review {

    private static final String ID = "id";
    private static final String AUTHOR = "author";
    private static final String CONTENT = "content";
    private static final String URL = "url";
    private static final String BLANK_STRING_REGEX = "^\\s+$";
    private static final String CONTINUATION_DOTS = "...";
    private static final int CONTENT_START_INDEX = 0;
    private static final int CONTENT_END_INDEX = 37;
    private static final int MAX_CONTENT_LENGTH = 37;

    private String id;
    public String author;
    private String content;
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

    public String truncatedContent() {
        if (isContentNotValid()) return null;
        String trimmedContent = this.content.trim();
        if (isContentLesserThanThirtySevenCharacters(trimmedContent)) return trimmedContent;
        return truncatedContentWithContinuation(trimmedContent);
    }

    private boolean isContentNotValid() {
        return isContentNotPresent() || isContentBlank();
    }

    private boolean isContentLesserThanThirtySevenCharacters(String trimmedContent) {
        return trimmedContent.length() < MAX_CONTENT_LENGTH;
    }

    @NonNull
    private String truncatedContentWithContinuation(String trimmedContent) {
        return trimmedContent.substring(CONTENT_START_INDEX, CONTENT_END_INDEX) + CONTINUATION_DOTS;
    }

    private boolean isContentNotPresent() {
        return this.content == null;
    }

    private boolean isContentBlank() {
        return this.content.matches(BLANK_STRING_REGEX);
    }
}
