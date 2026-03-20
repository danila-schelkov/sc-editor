package dev.donutquine.editor.gizmos.drawables;

import java.awt.Color;
import java.util.List;
import dev.donutquine.editor.gizmos.GizmoDrawable;
import dev.donutquine.editor.renderer.DrawApi;
import dev.donutquine.math.Point;

public class WireframeGizmo implements GizmoDrawable {
    private static final Color COLOR = Color.RED;
    private static final float THICKNESS = 4;

    private final List<Point> wireframePoints;
    private final boolean useStrip;

    public WireframeGizmo(List<Point> points, boolean useStrip) {
        this.wireframePoints = points;
        this.useStrip = useStrip;
    }

    @Override
    public void draw(DrawApi renderer) {
        Point p1 = this.wireframePoints.get(0);
        Point p2 = this.wireframePoints.get(1);

        renderer.drawLine(p1, p2, THICKNESS, COLOR);

        for (int i = 2; i < this.wireframePoints.size(); i++) {
            p1 = this.wireframePoints.get(i);
            p2 = this.wireframePoints.get(i - 1);
            renderer.drawLine(p1, p2, THICKNESS, COLOR);

            p2 = this.wireframePoints.get(this.useStrip ? i - 2 : 0);
            renderer.drawLine(p1, p2, THICKNESS, COLOR);
        }
    }
}
