package net.chiragaggarwal.android.popflix.models;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class ReviewTest {
    @Test
    public void shouldHaveNoTruncatedContentIfDoesNotHaveContent() {
        Review review = new Review("1", "Example Author", null, "http://example.com/content/1");
        assertEquals(null, review.truncatedContent());
    }

    @Test
    public void shouldHaveNoTruncatedContentIf() {
        String reviewContent = "hello this is my content of the review! hope you like it";
        String expectedTruncatedContent = "hello this is my content of the revie...";
        Review review = new Review("1", "Example Author", reviewContent, "http://example.com/content/1");
        assertEquals(expectedTruncatedContent, review.truncatedContent());
    }

    @Test
    public void shouldNotTruncateContentIfContentLengthIsLessThanThirtySevenCharacters() {
        String reviewContent = "my content";
        String nonTruncatedContent = "my content";
        Review review = new Review("1", "Example Author", reviewContent, "http://example.com/content/1");
        assertEquals(nonTruncatedContent, review.truncatedContent());
    }

    @Test
    public void shouldHaveNoTruncatedContentIfBlank() {
        String reviewContent = "                                       ";
        Review review = new Review("1", "Example Author", reviewContent, "http://example.com/content/1");
        assertEquals(null, review.truncatedContent());
    }

    @Test
    public void shouldTrimBeginningAndEndingWhiteSpacesFromContentWhenTruncatedIt() {
        String reviewContent = "          hello this is my content of the review! hope you like it          ";
        String expectedTruncatedContent = "hello this is my content of the revie...";
        Review review = new Review("1", "Example Author", reviewContent, "http://example.com/content/1");
        assertEquals(expectedTruncatedContent, review.truncatedContent());
    }

    @Test
    public void shouldTrimBeginningAndEndingWhiteSpacesFromContentLessThanThirtySevenWordsWhenTruncatedIt() {
        String reviewContent = "          my content          ";
        String expectedTruncatedContent = "my content";
        Review review = new Review("1", "Example Author", reviewContent, "http://example.com/content/1");
        assertEquals(expectedTruncatedContent, review.truncatedContent());
    }
}