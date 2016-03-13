package net.chiragaggarwal.android.popflix.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Videos {
    private static final String RESULTS = "results";
    private static final int FIRST_POSITION_INDEX = 0;
    private ArrayList<Video> videos;

    public Videos(Video... videos) {
        initializeVideos(videos);
    }

    public static Videos fromJson(JSONObject videosJsonObject) throws JSONException {
        JSONArray videosResults = videosJsonObject.getJSONArray(RESULTS);
        Videos videos = new Videos();
        for (Integer videoIndex = 0; videoIndex < videosResults.length(); videoIndex++) {
            JSONObject videoJsonObject = videosResults.getJSONObject(videoIndex);
            Video video = Video.fromJson(videoJsonObject, videoIndex + 1);
            videos.add(video);
        }
        return videos;
    }

    public int count() {
        return getNumberOfVideos();
    }

    public Video get(int position) {
        if (isVideosIndexInvalid(position)) return null;
        return this.videos.get(position);
    }

    public boolean any() {
        return getNumberOfVideos() > 0;
    }

    public String getYouTubeUrlStringForFirstVideo() {
        if (isVideosIndexInvalid(FIRST_POSITION_INDEX)) return null;
        return this.videos.get(FIRST_POSITION_INDEX).getYouTubeUriString();
    }

    private void initializeVideos(Video[] videos) {
        this.videos = new ArrayList<>();
        for (Integer videoIndex = 0; videoIndex < videos.length; videoIndex++) {
            Video video = videos[videoIndex];
            this.videos.add(video);
        }
    }

    private void add(Video video) {
        this.videos.add(video);
    }

    private boolean isVideosIndexInvalid(int position) {
        return isVideoIndexGreaterThanAvailableIndices(position) || isVideoIndexNegative(position);
    }

    private boolean isVideoIndexGreaterThanAvailableIndices(int position) {
        return position > getNumberOfVideos() - 1;
    }

    private boolean isVideoIndexNegative(int position) {
        return position < 0;
    }

    private int getNumberOfVideos() {
        return this.videos.size();
    }
}
