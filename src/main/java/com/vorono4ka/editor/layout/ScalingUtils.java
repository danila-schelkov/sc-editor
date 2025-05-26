package com.vorono4ka.editor.layout;

import com.vorono4ka.editor.SystemInfo;

import java.awt.*;

public class ScalingUtils {
    // Note: Got from here: https://github.com/nicolas-van/WorldWindJava/commit/0360d5f1c6117f1b989dc68d2b52085e2cbb7a09#diff-0998fa6b85b93125b092a5a0fc9246247ad761615ddec52c15616096593b5a98R498
    // This is apparently necessary to enable the canvas to resize correctly with JSplitPane.
    public static float getDpiScalingFactor() {
        return SystemInfo.IS_WINDOWS ? Toolkit.getDefaultToolkit().getScreenResolution() / 96.0f : 1;
    }

    private ScalingUtils() {}
}
