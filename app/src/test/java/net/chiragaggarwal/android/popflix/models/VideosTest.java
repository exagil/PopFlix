package net.chiragaggarwal.android.popflix.models;

import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class VideosTest {
    @Test
    public void shouldHaveCountEqualToNumberOfVideosPresent() {
        Videos videos = new Videos(new Video(null, null, null, null, null, null, null),
                new Video(null, null, null, null, null, null, null),
                new Video(null, null, null, null, null, null, null)
        );
        assertEquals(3, videos.count());
    }

    @Test
    public void shouldNotBeAbleToGetVideoAtNegativePosition() {
        Videos videos = new Videos(new Video(null, null, null, null, null, null, null),
                new Video(null, null, null, null, null, null, null),
                new Video(null, null, null, null, null, null, null)
        );
        assertEquals(null, videos.get(-1));
    }

    @Test
    public void shouldBeAbleToGetAVideoFromCollectionUsingValidPosition() {
        Video firstVideo = new Video("1", "en", "US", "73dfj391jn", "Trailer", "YouTube", "Trailer");
        Video secondVideo = new Video("2", "en", "US", "74ddj3g1j3", "Trailer", "YouTube", "Trailer");
        Video thirdVideo = new Video("3", "en", "US", "48dfji9kjn", "Trailer", "YouTube", "Trailer");
        Videos videos = new Videos(firstVideo, secondVideo, thirdVideo);

        assertEquals(secondVideo, videos.get(1));
    }

    @Test
    public void shouldNotBeAbleToGetAVideoWithPositionGreaterThanOneLessOfVideosCount() {
        Video firstVideo = new Video("1", "en", "US", "73dfj391jn", "Trailer", "YouTube", "Trailer");
        Video secondVideo = new Video("2", "en", "US", "74ddj3g1j3", "Trailer", "YouTube", "Trailer");
        Videos videos = new Videos(firstVideo, secondVideo);
        assertEquals(null, videos.get(1000));
    }
}