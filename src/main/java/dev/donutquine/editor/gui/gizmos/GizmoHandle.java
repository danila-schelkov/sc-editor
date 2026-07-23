package dev.donutquine.editor.gizmos;

import org.jetbrains.annotations.NotNull;
import dev.donutquine.math.Point;

public record GizmoHandle(@NotNull Point position, @NotNull GizmoAction action) { }
