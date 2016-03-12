package net.chiragaggarwal.android.popflix.models;

import org.junit.Test;

public class VideoTest {
    @Test
    public void shouldBeEqualToOtherIfHasSameAttributesAsOther() {
        Video thisVideo = new Video("1", "en", "US", "7jIBCiYg58k", "example movie", "Youtube", "Trailer");
        Video thatVideo = new Video("1", "en", "US", "7jIBCiYg58k", "example movie", "Youtube", "Trailer");
        assert(thisVideo.equals(thatVideo));
    }
}