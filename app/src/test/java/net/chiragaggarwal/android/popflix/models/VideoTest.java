package net.chiragaggarwal.android.popflix.models;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

public class VideoTest {
    @Test
    public void shouldBeEqualToOtherIfHasSameAttributesAsOther() {
        Video thisVideo = new Video("1", "en", "US", "7jIBCiYg58k", "example movie", "YouTube", "Trailer", 1);
        Video thatVideo = new Video("1", "en", "US", "7jIBCiYg58k", "example movie", "YouTube", "Trailer", 1);
        assert (thisVideo.equals(thatVideo));
    }

    @Test
    public void shouldNotBeEqualToNull() {
        Video thisVideo = new Video("1", "en", "US", "7jIBCiYg58k", "example movie", "YouTube", "Trailer", 1);
        assertFalse(thisVideo.equals(null));
    }

    @Test
    public void shouldNotBeEqualToAnythingOtherThanAVideo() {
        Video thisVideo = new Video("1", "en", "US", "7jIBCiYg58k", "example movie", "YouTube", "Trailer", 1);
        assertFalse(thisVideo.equals(new Object()));
    }

    @Test
    public void shouldNotBeEqualIfHasDifferentAttributes() {
        Video thisVideo = new Video("1", "en", "US", "7jIBCiYg58k", "example movie", "YouTube", "Trailer", 1);
        Video thatVideo = new Video("2", "fr", "US", "748sjd246r5", "second example movie", "YouTube", "Trailer", 2);
        assertFalse(thisVideo.equals(thatVideo));
    }

    @Test
    public void shouldBeEqualIfHasSameAttributesExceptVideoPosition() {
        Video thisVideo = new Video("1", "en", "US", "7jIBCiYg58k", "example movie", "YouTube", "Trailer", 1);
        Video thatVideo = new Video("1", "en", "US", "7jIBCiYg58k", "example movie", "YouTube", "Trailer", 2);
        assertTrue(thisVideo.equals(thatVideo));
    }

    @Test
    public void shouldBeEqualToItself() {
        Video thisVideo = new Video("1", "en", "US", "7jIBCiYg58k", "example movie", "YouTube", "Trailer", 1);
        assert (thisVideo.equals(thisVideo));
    }

    @Test
    public void shouldBeEqualCommutatively() {
        Video thisVideo = new Video("1", "en", "US", "7jIBCiYg58k", "example movie", "YouTube", "Trailer", 1);
        Video thatVideo = new Video("1", "en", "US", "7jIBCiYg58k", "example movie", "YouTube", "Trailer", 1);
        assert (thatVideo.equals(thisVideo));
    }

    @Test
    public void shouldHaveSameHashCodeIfBothVideoAreSame() {
        Video thisVideo = new Video("1", "en", "US", "7jIBCiYg58k", "example movie", "YouTube", "Trailer", 1);
        Video thatVideo = new Video("1", "en", "US", "7jIBCiYg58k", "example movie", "YouTube", "Trailer", 1);
        assertEquals(thisVideo.hashCode(), thatVideo.hashCode());
    }

    @Test
    public void shouldTakeItsFormattedNameFromItsRealNameAndVideoIndex() {
        Video thisVideo = new Video("1", "en", "US", "7jIBCiYg58k", "Trailer", "YouTube", "Trailer", 1);
        assertEquals("Trailer 1", thisVideo.formattedName());
    }

    @Test
    public void shouldTakeItsFormattedNameFromVideoIndexIfNoNameIsPresent() {
        Video thisVideo = new Video("1", "en", "US", "7jIBCiYg58k", null, "YouTube", "Trailer", 1);
        assertEquals("1", thisVideo.formattedName());
    }

    @Test
    public void shouldNotBuildAYouTubeUriIfKeyIsEmpty() {
        String youtubeUriKey = "  ";
        Video video = new Video("1", "en", "US", youtubeUriKey, null, "YouTube", "Trailer", 1);
        assertEquals(null, video.getYouTubeUri());
    }

    @Test
    public void shouldNotBuildAYouTubeUriIfKeyIsNotPresent() {
        String youtubeUriKey = null;
        Video video = new Video("1", "en", "US", youtubeUriKey, null, "YouTube", "Trailer", 1);
        assertEquals(null, video.getYouTubeUri());
    }

    @Test
    public void shouldNotBuildAYouTubeUriIfWebsiteIsNotYouTube() {
        String youtubeUriKey = "7jIBCiYg58k";
        Video video = new Video("1", "en", "US", youtubeUriKey, null, "SomeRandomTube", "Trailer", 1);
        assertEquals(null, video.getYouTubeUri());
    }

    @Test
    public void shouldNotBuildYouTubeUriStringWhenUriKeyIsEmpty() {
        String youtubeUriKey = "  ";
        Video video = new Video("1", "en", "US", youtubeUriKey, null, "YouTube", "Trailer", 1);
        assertEquals(null, video.getYouTubeUriString());
    }

    @Test
    public void shouldNotBuildYouTubeUriStringWhenUriKeyIsNotPresent() {
        String youtubeUriKey = null;
        Video video = new Video("1", "en", "US", youtubeUriKey, null, "YouTube", "Trailer", 1);
        assertEquals(null, video.getYouTubeUriString());
    }

    @Test
    public void shouldNotBuildYouTubeUriStringWhenWebsiteIsNotYouTube() {
        String youtubeUriKey = "7jIBCiYg58k";
        Video video = new Video("1", "en", "US", youtubeUriKey, null, "SomeRandomTube", "Trailer", 1);
        assertEquals(null, video.getYouTubeUriString());
    }

    @Test
    public void shouldKnowHowToBuildYouTubeUriString() {
        String youtubeUriKey = "7jIBCiYg58k";
        Video video = new Video("1", "en", "US", youtubeUriKey, null, "YouTube", "Trailer", 1);
        assertEquals("https://www.youtube.com/watch?v=7jIBCiYg58k", video.getYouTubeUriString());
    }
}
