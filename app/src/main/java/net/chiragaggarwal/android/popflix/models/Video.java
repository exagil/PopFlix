package net.chiragaggarwal.android.popflix.models;

import org.json.JSONObject;

public class Video {
    private String id;
    private String languageCode;
    private String countryCode;
    private String youtubeKey;
    private String name;
    private String website;
    private String type;

    public Video(String id, String languageCode, String countryCode, String youtubeKey, String name,
                 String website, String type) {

        this.id = id;
        this.languageCode = languageCode;
        this.countryCode = countryCode;
        this.youtubeKey = youtubeKey;
        this.name = name;
        this.website = website;
        this.type = type;
    }

    @Override
    public boolean equals(Object thatVideoObject) {
        if (thatVideoObject == null || !(thatVideoObject instanceof Video)) return false;
        return true;
    }

    public static Video fromJson(JSONObject videoJsonObject) {
        return null;
    }
}
