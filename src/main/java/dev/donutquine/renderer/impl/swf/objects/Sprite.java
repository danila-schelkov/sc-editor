package dev.donutquine.renderer.impl.swf.objects;

import dev.donutquine.math.Rect;
import dev.donutquine.swf.ColorTransform;
import dev.donutquine.swf.Matrix2x3;
import dev.donutquine.swf.movieclips.MovieClipState;
import dev.donutquine.utilities.RenderConfig;
import java.util.ArrayList;
import java.util.List;

public abstract class Sprite extends DisplayObject {
    protected List<DisplayObject> children;
    protected boolean isInteractive;
    protected int frameSkippingType;
    protected MovieClipState state;
    private Rect hitArea;

    public Sprite() {
        this.children = new ArrayList<>();
        this.state = MovieClipState.PLAYING;
        this.isInteractive = false;
    }

    @Override
    public boolean render(Matrix2x3 matrix, ColorTransform colorTransform, int renderConfigBits, float deltaTime) {
        Matrix2x3 frameMatrix = calculateFrameMatrix(matrix);
        ColorTransform frameColorTransform = calculateFrameColorTransform(colorTransform);

        int spriteRenderConfigBits = (this.getRenderConfigBits() & 0x3FF) | RenderConfig.getShader(frameColorTransform) | renderConfigBits;

        boolean result = false;
        for (DisplayObject displayObject : this.children) {
            if (displayObject.isVisible()) {
                result |= displayObject.render(frameMatrix, frameColorTransform, spriteRenderConfigBits, deltaTime);
            }
        }

        return result;
    }

    @Override
    public boolean collisionRender(Matrix2x3 matrix) {
        if (!this.isInteractive) return false;

        // TODO: some kind of interaction with stage from lib

        Matrix2x3 frameMatrix = calculateFrameMatrix(matrix);

        if (this.hitArea != null) {
            // TODO: hitAreaTest
        }

        boolean result = false;
        for (DisplayObject child : this.children) {
            if (child.isVisible()) {
                result |= child.collisionRender(frameMatrix);
            }
        }

        return result;
    }

    @Override
    public boolean isSprite() {
        return true;
    }

    public void setInteractiveRecursive(boolean interactive) {
        this.isInteractive = interactive;

        for (DisplayObject child : this.children) {
            child.setInteractiveRecursive(interactive);
        }
    }

    public void setVisibleRecursive(boolean visible) {
        this.isVisible = visible;

        for (DisplayObject child : this.children) {
            child.setVisibleRecursive(visible);
        }
    }

    public void addChild(DisplayObject displayObject) {
        this.addChildAt(displayObject, this.children.size());
    }

    public void addChildAt(DisplayObject displayObject, int index) {
        Sprite parent = displayObject.getParent();

        if (parent != null) {
            int indexInParent = displayObject.getIndexInParent();
            if (parent == this && indexInParent == index) return;

            parent.removeChildAt(indexInParent);
        }

        this.children.add(index, displayObject);
        displayObject.setParent(this);
        displayObject.setIndexInParent(index);

        for (int i = index + 1; i < this.children.size(); i++) {
            this.children.get(i).setIndexInParent(i);
        }
    }

    public void removeChild(DisplayObject displayObject) {
        if (displayObject.getParent() != this || displayObject.getIndexInParent() == -1)
            return;

        this.removeChildAt(displayObject.getIndexInParent());
    }

    public void removeChildAt(int index) {
        DisplayObject displayObject = this.children.get(index);
        displayObject.setParent(null);
        displayObject.setIndexInParent(-1);

        this.children.remove(index);

        for (int i = index; i < this.children.size(); i++) {
            this.children.get(i).setIndexInParent(i);
        }
    }

    public void removeAllChildren() {
        for (int i = this.children.size() - 1; i >= 0; i--) {
            this.removeChildAt(i);
        }
    }

    public int getChildIndex(DisplayObject child) {
        if (child.getParent() == this) {
            return child.getIndexInParent();
        }

        return -1;
    }

    public int getChildrenCount() {
        return this.children.size();
    }

    public DisplayObject getChild(int index) {
        return this.children.get(index);
    }

    public MovieClipState getState() {
        return state;
    }
}
