package net.chiragaggarwal.android.popflix.models;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class ReviewsTest {
    @Test
    public void shouldHaveCountSameAsNumberOfReviewsPresent() {
        Reviews reviews = new Reviews(
                new Review("1", "Author One", "Random Content One", "http://example/com/reviews/1"),
                new Review("2", "Author Two", "Random Content Two", "http://example/com/reviews/2"),
                new Review("3", "Author Three", "Random Content Three", "http://example/com/reviews/3"));
        assertEquals(3, reviews.count());
    }

    @Test
    public void shouldHaveZeroCountWhenNoReviewsArePresent() {
        Reviews reviews = new Reviews();
        assertEquals(0, reviews.count());
    }
}