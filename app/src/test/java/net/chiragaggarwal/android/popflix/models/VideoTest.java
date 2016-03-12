package net.chiragaggarwal.android.popflix.models;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertFalse;

public class VideoTest {
    @Test
    public void shouldBeEqualToOtherIfHasSameAttributesAsOther() {
        Video thisVideo = new Video("1", "en", "US", "7jIBCiYg58k", "example movie", "Youtube", "Trailer", 1);
        Video thatVideo = new Video("1", "en", "US", "7jIBCiYg58k", "example movie", "Youtube", "Trailer", 2);
        assert (thisVideo.equals(thatVideo));
    }

    @Test
    public void shouldNotBeEqualToNull() {
        Video thisVideo = new Video("1", "en", "US", "7jIBCiYg58k", "example movie", "Youtube", "Trailer", 1);
        assertFalse(thisVideo.equals(null));
    }

    @Test
    public void shouldNotBeEqualToAnythingOtherThanAVideo() {
        Video thisVideo = new Video("1", "en", "US", "7jIBCiYg58k", "example movie", "Youtube", "Trailer", 1);
        assertFalse(thisVideo.equals(new Object()));
    }

    @Test
    public void shouldNotBeEqualIfHasDifferentAttributes() {
        Video thisVideo = new Video("1", "en", "US", "7jIBCiYg58k", "example movie", "Youtube", "Trailer", 1);
        Video thatVideo = new Video("2", "fr", "US", "748sjd246r5", "second example movie", "Youtube", "Trailer", 2);
        assertFalse(thisVideo.equals(thatVideo));
    }

    @Test
    public void shouldBeEqualToItself() {
        Video thisVideo = new Video("1", "en", "US", "7jIBCiYg58k", "example movie", "Youtube", "Trailer", 1);
        assert (thisVideo.equals(thisVideo));
    }

    @Test
    public void shouldBeEqualCommutatively() {
        Video thisVideo = new Video("1", "en", "US", "7jIBCiYg58k", "example movie", "Youtube", "Trailer", 1);
        Video thatVideo = new Video("1", "en", "US", "7jIBCiYg58k", "example movie", "Youtube", "Trailer", 2);
        assert (thatVideo.equals(thisVideo));
    }

    @Test
    public void shouldHaveSameHashCodeIfBothVideoAreSame() {
        Video thisVideo = new Video("1", "en", "US", "7jIBCiYg58k", "example movie", "Youtube", "Trailer", 1);
        Video thatVideo = new Video("1", "en", "US", "7jIBCiYg58k", "example movie", "Youtube", "Trailer", 2);
        assertEquals(thisVideo.hashCode(), thatVideo.hashCode());
    }

    @Test
    public void shouldTakeItsFormattedNameFromItsRealNameAndVideoIndex() {
        Video thisVideo = new Video("1", "en", "US", "7jIBCiYg58k", "Trailer", "Youtube", "Trailer", 1);
        assertEquals("Trailer 1", thisVideo.formattedName());
    }

    @Test
    public void shouldTakeItsFormattedNameFromVideoIndexIfNoNameIsPresent() {
        Video thisVideo = new Video("1", "en", "US", "7jIBCiYg58k", null, "Youtube", "Trailer", 1);
        assertEquals("1", thisVideo.formattedName());
    }
}
