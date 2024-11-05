package com.vorono4ka.utilities;

import com.vorono4ka.swf.constants.MovieClipState;
import com.vorono4ka.swf.displayObjects.MovieClip;

public final class MovieClipHelper {
    public static void doForAllFrames(MovieClip movieClip, MovieClipFrameIndexConsumer consumer) {
        int maxFrames = movieClip.getFrames().size();

        int currentFrame = movieClip.getCurrentFrame();
        int loopFrame = movieClip.getLoopFrame();
        MovieClipState state = movieClip.getState();

        for (int i = 0; i < maxFrames; i++) {
            movieClip.gotoAbsoluteTimeRecursive(i * movieClip.getMsPerFrame());
            consumer.accept(i);
        }

        movieClip.resetTimelinePositionRecursive();
        movieClip.gotoAndPlayFrameIndex(currentFrame, loopFrame, state);
    }

    @FunctionalInterface
    public interface MovieClipFrameIndexConsumer {
        void accept(Integer frameIndex);
    }
}
