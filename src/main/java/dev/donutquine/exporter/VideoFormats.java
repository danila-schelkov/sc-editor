package dev.donutquine.exporter;

import java.lang.reflect.Field;

@SuppressWarnings("unused")
public final class VideoFormats {
    /**
     * The only one format supporting the alpha channel.
     */
    public static final VideoFormat WEBM = new VideoFormat("webm", "libvpx-vp9", "yuva420p", false);
    /**
     * AV1 is too slow and not tested
     */
    public static final VideoFormat AV1 = new VideoFormat("avi", "libaom-av1", "yuv420p", false);
    /**
     * libx265 doesn't support alpha channel, but hevc_nvenc does.
     */
    public static final VideoFormat HEVC = new VideoFormat("hevc", "libx265", "yuva420p", true);
    /**
     * Doesn't support alpha channel at all.
     */
    public static final VideoFormat MP4 = new VideoFormat("mp4", "libx264", "yuv420p", true);

    // Maybe collect automatically
    private static final VideoFormat[] allFormats = new VideoFormat[] {WEBM, AV1, HEVC, MP4};

    public static VideoFormat getVideoFormatByName(String name) {
        Field[] declaredFields = VideoFormats.class.getDeclaredFields();

        for (Field declaredField : declaredFields) {
            if (declaredField.getType() == VideoFormat.class) {
                VideoFormat videoFormat;
                try {
                    videoFormat = ((VideoFormat) declaredField.get(VideoFormats.class));
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }

                if (videoFormat.name().equalsIgnoreCase(name)) {
                    return videoFormat;
                }
            }
        }

        return null;
    }
}
