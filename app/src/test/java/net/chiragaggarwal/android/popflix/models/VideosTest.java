package net.chiragaggarwal.android.popflix.models;

import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static junit.framework.TestCase.assertEquals;

public class VideosTest {
    @Test
    public void shouldHaveCountEqualToNumberOfVideosPresent() {
        Videos videos = new Videos(new Video(null, null, null, null, null, null, null, 1),
                new Video(null, null, null, null, null, null, null, 2),
                new Video(null, null, null, null, null, null, null, 3)
        );
        assertEquals(3, videos.count());
    }

    @Test
    public void shouldNotBeAbleToGetVideoAtNegativePosition() {
        Videos videos = new Videos(new Video(null, null, null, null, null, null, null, 1),
                new Video(null, null, null, null, null, null, null, 2),
                new Video(null, null, null, null, null, null, null, 3)
        );
        assertEquals(null, videos.get(-1));
    }

    @Test
    public void shouldBeAbleToGetAVideoFromCollectionUsingValidPosition() {
        Video firstVideo = new Video("1", "en", "US", "73dfj391jn", "Trailer", "YouTube", "Trailer", 1);
        Video secondVideo = new Video("2", "en", "US", "74ddj3g1j3", "Trailer", "YouTube", "Trailer", 2);
        Video thirdVideo = new Video("3", "en", "US", "48dfji9kjn", "Trailer", "YouTube", "Trailer", 3);
        Videos videos = new Videos(firstVideo, secondVideo, thirdVideo);

        assertEquals(secondVideo, videos.get(1));
    }

    @Test
    public void shouldNotBeAbleToGetAVideoWithPositionGreaterThanOneLessOfVideosCount() {
        Video firstVideo = new Video("1", "en", "US", "73dfj391jn", "Trailer", "YouTube", "Trailer", 1);
        Video secondVideo = new Video("2", "en", "US", "74ddj3g1j3", "Trailer", "YouTube", "Trailer", 2);
        Videos videos = new Videos(firstVideo, secondVideo);
        assertEquals(null, videos.get(1000));
    }

    @Test
    public void shouldKnowIfVideoElementsArePresent() {
        Video firstVideo = new Video("1", "en", "US", "73dfj391jn", "Trailer", "YouTube", "Trailer", 1);
        Video secondVideo = new Video("2", "en", "US", "74ddj3g1j3", "Trailer", "YouTube", "Trailer", 2);
        Videos videos = new Videos(firstVideo, secondVideo);
        assertTrue(videos.any());
    }

    @Test
    public void shouldKnowWhenVideoElementsAreNotPresent() {
        Videos videos = new Videos();
        assertFalse(videos.any());
    }

    @Test
    public void shouldFetchTheYouTubeUrlStringForFirstVideo() {
        Video firstVideo = new Video("1", "en", "US", "73dfj391jn", "Trailer", "YouTube", "Trailer", 1);
        Video secondVideo = new Video("2", "en", "US", "74ddj3g1j3", "Trailer", "YouTube", "Trailer", 2);
        Videos videos = new Videos(firstVideo, secondVideo);
        assertEquals("https://www.youtube.com/watch?v=73dfj391jn", videos.getYouTubeUrlStringForFirstVideo());
    }

    @Test
    public void shouldNotFetchYouTubeUrlStringIfNoVideosPresent() {
        Videos videos = new Videos();
        assertEquals(null, videos.getYouTubeUrlStringForFirstVideo());
    }
}
