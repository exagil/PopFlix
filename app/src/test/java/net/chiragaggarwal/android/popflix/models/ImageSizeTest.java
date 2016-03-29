package net.chiragaggarwal.android.popflix.models;

import org.junit.Test;

import static junit.framework.Assert.assertEquals;

public class ImageSizeTest {
    @Test
    public void smallImageShouldHaveW185AsImageSize() {
        assertEquals("w185", ImageSize.SMALL.decode());
    }

    @Test
    public void mediumImageShouldHaveW300AsImageSize() {
        assertEquals("w300", ImageSize.MEDIUM.decode());
    }

    @Test
    public void largeImageShouldHaveW500AsImageSize() {
        assertEquals("w500", ImageSize.LARGE.decode());
    }
}