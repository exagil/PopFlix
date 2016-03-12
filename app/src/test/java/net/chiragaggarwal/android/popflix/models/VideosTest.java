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
}