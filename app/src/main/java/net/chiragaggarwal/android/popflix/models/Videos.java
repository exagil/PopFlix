package net.chiragaggarwal.android.popflix.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Videos {
    private static final String RESULTS = "results";
    private ArrayList<Video> videos;

    public Videos(Video... videos) {
        initializeVideos(videos);
    }

    public static Videos fromJson(JSONObject videosJsonObject) throws JSONException {
        JSONArray videosResults = videosJsonObject.getJSONArray(RESULTS);
        Videos videos = new Videos();
        for (Integer videoIndex = 0; videoIndex < videosResults.length(); videoIndex++) {
            JSONObject videoJsonObject = videosResults.getJSONObject(videoIndex);
            Video video = Video.fromJson(videoJsonObject, videoIndex);
            videos.add(video);
        }
        return videos;
    }

    public int count() {
        return this.videos.size();
    }

    public Video get(int position) {
        if (isMovieIndexInvalid(position)) return null;
        return this.videos.get(position);
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

    private boolean isMovieIndexInvalid(int position) {
        return isMovieIndexGreaterThanAvailableIndices(position) || isMovieIndexNegative(position);
    }

    private boolean isMovieIndexGreaterThanAvailableIndices(int position) {
        return position > videos.size() - 1;
    }

    private boolean isMovieIndexNegative(int position) {
        return position < 0;
    }
}
