package dev.donutquine.editor.gizmos;

public interface GizmoAction {
    void begin(float mouseX, float mouseY);

    void update(float mouseX, float mouseY);

    void end();
}
