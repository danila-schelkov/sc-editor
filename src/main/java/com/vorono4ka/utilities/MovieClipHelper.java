package com.vorono4ka.utilities;

import com.vorono4ka.renderer.impl.swf.objects.MovieClip;
import com.vorono4ka.swf.movieclips.MovieClipState;

public final class MovieClipHelper {
    public static void doForAllFrames(MovieClip movieClip, MovieClipFrameIndexConsumer consumer) {
        // TODO: add an ability to choose it by yourself
//        int maxFrames = movieClip.getFrames().size();
        int maxFrames = movieClip.getFrameCountRecursive();

        int currentFrame = movieClip.getCurrentFrame();
        int loopFrame = movieClip.getLoopFrame();
        MovieClipState state = movieClip.getState();

        for (int i = 0; i < maxFrames; i++) {
            movieClip.gotoAbsoluteTimeRecursive(i * movieClip.getMsPerFrame());
            if (loopFrame != -1) {
                movieClip.setFrame(loopFrame);
            } else if (state == MovieClipState.STOPPED) {
                movieClip.setFrame(currentFrame);
            }

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
