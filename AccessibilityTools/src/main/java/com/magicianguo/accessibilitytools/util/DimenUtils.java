package com.magicianguo.accessibilitytools.util;

import android.content.res.Resources;

import com.magicianguo.accessibilitytools.App;

public class DimenUtils {
    public static final int STATUS_BAR_HEIGHT = getStatusBarHeight();

    public static float dp2px(float dp) {
        return App.get().getResources().getConfiguration().densityDpi / 160F * dp;
    }

    private static int getStatusBarHeight() {
        Resources resources = App.get().getResources();
        int resId = resources.getIdentifier("status_bar_height", "dimen", "android");
        return resources.getDimensionPixelSize(resId);
    }
}
