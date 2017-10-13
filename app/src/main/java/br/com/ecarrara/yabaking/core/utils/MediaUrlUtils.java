package br.com.ecarrara.yabaking.core.utils;

public final class MediaUrlUtils {

    static class VideoExtensions {
        private VideoExtensions() { /* no construction allowed */ }
        static final String EXTENSION_MP4 = ".mp4";
    }


    static class ImageExtensions {
        private ImageExtensions() { /* no construction allowed */ }
        static final String EXTENSION_JPG = ".jpg";
        static final String EXTENSION_JPEG = ".jpeg";
        static final String EXTENSION_PNG = ".png";
    }

    private static final String EMPTY_URL = "";

    private MediaUrlUtils() { /* no construction allowed */ }

    public static String filterValidVideoUrl(String url) {
        if (url.toLowerCase().endsWith(VideoExtensions.EXTENSION_MP4)) {
            return url;
        }
        return EMPTY_URL;
    }

    public static String filterValidImageUrl(String url) {
        final String lowerCaseUrl = url.toLowerCase();
        if (lowerCaseUrl.endsWith(ImageExtensions.EXTENSION_JPG) ||
                lowerCaseUrl.endsWith(ImageExtensions.EXTENSION_JPEG) ||
                lowerCaseUrl.endsWith(ImageExtensions.EXTENSION_PNG)) {
            return url;
        }
        return EMPTY_URL;
    }

}
