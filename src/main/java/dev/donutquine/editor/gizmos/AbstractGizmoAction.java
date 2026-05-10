package dev.donutquine.editor.gizmos;

public abstract class AbstractGizmoAction implements GizmoAction {
    protected float mouseStartX, mouseStartY;
    protected float mouseX, mouseY;

    @Override
    public void begin(float mouseX, float mouseY) {
        this.mouseStartX = mouseX;
        this.mouseStartY = mouseY;
    }

    @Override
    public void update(float mouseX, float mouseY) {
        this.mouseX = mouseX;
        this.mouseY = mouseY;
    }
}
