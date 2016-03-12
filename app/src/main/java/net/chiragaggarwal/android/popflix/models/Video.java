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
        Video thatVideo = (Video) thatVideoObject;
        return this.id.equals(thatVideo.id) &&
                this.languageCode.equals(thatVideo.languageCode) &&
                this.countryCode.equals(thatVideo.countryCode) &&
                this.youtubeKey.equals(thatVideo.youtubeKey) &&
                this.name.equals(thatVideo.name) &&
                this.website.equals(thatVideo.website) &&
                this.type.equals(thatVideo.type);
    }

    public static Video fromJson(JSONObject videoJsonObject) {
        return null;
    }
}
