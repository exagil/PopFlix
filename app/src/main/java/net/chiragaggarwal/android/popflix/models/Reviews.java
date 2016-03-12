package net.chiragaggarwal.android.popflix.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.util.ArrayList;

public class Reviews {
    private ArrayList<Review> reviews;
    private static final String RESULTS = "results";

    public Reviews(Review... reviews) {
        initializeReviews(reviews);
    }

    public static Reviews fromJson(JSONObject reviewsJsonObject) throws JSONException, ParseException {
        JSONArray reviewsResults = reviewsJsonObject.getJSONArray(RESULTS);
        Reviews reviews = new Reviews();
        for (Integer reviewIndex = 0; reviewIndex < reviewsResults.length(); reviewIndex++) {
            JSONObject reviewJsonObject = reviewsResults.getJSONObject(reviewIndex);
            Review review = Review.fromJson(reviewJsonObject);
            reviews.add(review);
        }
        return reviews;
    }

    private void initializeReviews(Review[] reviews) {
        this.reviews = new ArrayList<>();
        for (Integer reviewIndex = 0; reviewIndex < reviews.length; reviewIndex++) {
            Review review = reviews[reviewIndex];
            this.reviews.add(review);
        }
    }

    private void add(Review review) {
        this.reviews.add(review);
    }

    public int count() {
        return this.reviews.size();
    }
}
