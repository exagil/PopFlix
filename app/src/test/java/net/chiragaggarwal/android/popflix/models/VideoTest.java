package net.chiragaggarwal.android.popflix.models;

import org.junit.Test;

import static junit.framework.TestCase.assertFalse;

public class VideoTest {
    @Test
    public void shouldBeEqualToOtherIfHasSameAttributesAsOther() {
        Video thisVideo = new Video("1", "en", "US", "7jIBCiYg58k", "example movie", "Youtube", "Trailer");
        Video thatVideo = new Video("1", "en", "US", "7jIBCiYg58k", "example movie", "Youtube", "Trailer");
        assert (thisVideo.equals(thatVideo));
    }

    @Test
    public void shouldNotBeEqualToNull() {
        Video thisVideo = new Video("1", "en", "US", "7jIBCiYg58k", "example movie", "Youtube", "Trailer");
        assertFalse(thisVideo.equals(null));
    }
}