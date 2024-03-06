package com.magicianguo.accessibilitytools.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.magicianguo.accessibilitytools.App;

public class SPUtils {
    private static final String TAG = "SPUtils";
    private static final SharedPreferences SP = App.get().getSharedPreferences(TAG, Context.MODE_PRIVATE);

    public static final String KEY_CLICK_X = "KEY_CLICK_X";
    public static final String KEY_CLICK_Y = "KEY_CLICK_Y";
    public static final String KEY_CLICK_INTERVAL = "KEY_CLICK_INTERVAL";
    public static final String KEY_CLICK_COUNT = "KEY_CLICK_COUNT";

    public static void setClickX(int value) {
        SharedPreferences.Editor edit = SP.edit();
        edit.putInt(KEY_CLICK_X, value);
        edit.apply();
    }

    public static int getClickX() {
        return SP.getInt(KEY_CLICK_X, 400);
    }

    public static void setClickY(int value) {
        SharedPreferences.Editor edit = SP.edit();
        edit.putInt(KEY_CLICK_Y, value);
        edit.apply();
    }

    public static int getClickY() {
        return SP.getInt(KEY_CLICK_Y, 400);
    }

    public static void setClickInterval(int value) {
        SharedPreferences.Editor edit = SP.edit();
        edit.putInt(KEY_CLICK_INTERVAL, value);
        edit.apply();
    }

    public static int getClickInterval() {
        return SP.getInt(KEY_CLICK_INTERVAL, 100);
    }

    public static void setClickCount(int value) {
        SharedPreferences.Editor edit = SP.edit();
        edit.putInt(KEY_CLICK_COUNT, value);
        edit.apply();
    }

    public static int getClickCount() {
        return SP.getInt(KEY_CLICK_COUNT, 10);
    }
}
