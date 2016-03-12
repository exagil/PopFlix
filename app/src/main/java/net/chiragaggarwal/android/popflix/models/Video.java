package net.chiragaggarwal.android.popflix.models;

import org.json.JSONException;
import org.json.JSONObject;

public class Video {
    private static final String ID = "id";
    private static final String LANGUAGE_CODE = "iso_639_1";
    private static final String COUNTRY_CODE = "iso_3166_1";
    private static final String VIDEO_KEY = "key";
    private static final String VIDEO_NAME = "name";
    private static final String VIDEO_HOST_WEBSITE = "site";
    private static final String TYPE = "type";
    private String id;
    private String languageCode;
    private String countryCode;
    private String key;
    private String name;
    private String website;
    private String type;

    public Video(String id, String languageCode, String countryCode, String key, String name,
                 String website, String type) {

        this.id = id;
        this.languageCode = languageCode;
        this.countryCode = countryCode;
        this.key = key;
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
                this.key.equals(thatVideo.key) &&
                this.name.equals(thatVideo.name) &&
                this.website.equals(thatVideo.website) &&
                this.type.equals(thatVideo.type);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (languageCode != null ? languageCode.hashCode() : 0);
        result = 31 * result + (countryCode != null ? countryCode.hashCode() : 0);
        result = 31 * result + (key != null ? key.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (website != null ? website.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        return result;
    }

    public static Video fromJson(JSONObject videoJsonObject) throws JSONException {
        String id = videoJsonObject.getString(ID);
        String languageCode = videoJsonObject.getString(LANGUAGE_CODE);
        String countryCode = videoJsonObject.getString(COUNTRY_CODE);
        String key = videoJsonObject.getString(VIDEO_KEY);
        String name = videoJsonObject.getString(VIDEO_NAME);
        String website = videoJsonObject.getString(VIDEO_HOST_WEBSITE);
        String type = videoJsonObject.getString(TYPE);

        return new Video(id, languageCode, countryCode, key, name, website, type);
    }
}