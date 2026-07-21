package dev.donutquine.utilities;

import dev.donutquine.renderer.impl.swf.objects.MovieClip;
import dev.donutquine.swf.DisplayObjectOriginal;
import dev.donutquine.swf.SupercellSWF;
import dev.donutquine.swf.exceptions.UnableToFindObjectException;
import dev.donutquine.swf.movieclips.MovieClipChild;
import dev.donutquine.swf.movieclips.MovieClipOriginal;
import dev.donutquine.swf.movieclips.MovieClipState;

public final class MovieClipHelper {
    public static void doForAllFrames(MovieClip movieClip, MovieClipFrameIndexConsumer consumer) {
        // TODO: add an ability to choose it by yourself
        int maxFrames;
        if (movieClip.getFrameCount() > 1) {
            maxFrames = movieClip.getFrameCount();
        } else {
            maxFrames = movieClip.getFrameCountRecursive();
        }

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

    public static int getFrameCountRecursive(SupercellSWF swf, MovieClipOriginal movieClip) throws UnableToFindObjectException {
        int frameCount = movieClip.getFrames().size();

        for (MovieClipChild timelineChild : movieClip.getChildren()) {
            DisplayObjectOriginal displayObject = swf.getOriginalDisplayObject(timelineChild.id(), movieClip.getExportName());
            if (displayObject instanceof MovieClipOriginal childMovieClip) {
                int childFrameCount = MovieClipHelper.getFrameCountRecursive(swf, childMovieClip);
                if (childFrameCount > frameCount) {
                    frameCount = childFrameCount;
                }
            }
        }

        return frameCount;
    }

    @FunctionalInterface
    public interface MovieClipFrameIndexConsumer {
        void accept(Integer frameIndex);
    }
}
