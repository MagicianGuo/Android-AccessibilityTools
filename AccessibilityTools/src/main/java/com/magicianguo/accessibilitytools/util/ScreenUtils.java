package com.magicianguo.accessibilitytools.util;

import android.content.res.Configuration;

import com.magicianguo.accessibilitytools.App;

public class ScreenUtils {
    public static boolean isPortrait() {
        return App.get().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
    }
}
