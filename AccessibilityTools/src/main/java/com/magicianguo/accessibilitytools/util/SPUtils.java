package com.magicianguo.accessibilitytools.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.magicianguo.accessibilitytools.App;
import com.magicianguo.accessibilitytools.constant.OptionValue;
import com.magicianguo.accessibilitytools.constant.ViewAreaType;

public class SPUtils {
    private static final String TAG = "SPUtils";
    private static final SharedPreferences SP = App.get().getSharedPreferences(TAG, Context.MODE_PRIVATE);

    public static final String KEY_CLICK_X = "KEY_CLICK_X";
    public static final String KEY_CLICK_Y = "KEY_CLICK_Y";
    public static final String KEY_CLICK_INTERVAL = "KEY_CLICK_INTERVAL";
    public static final String KEY_CLICK_COUNT = "KEY_CLICK_COUNT";
    public static final String KEY_VIEW_AREA_ALPHA = "KEY_VIEW_AREA_ALPHA";
    public static final String KEY_VIEW_AREA_RED = "KEY_VIEW_AREA_RED";
    public static final String KEY_VIEW_AREA_GREEN = "KEY_VIEW_AREA_GREEN";
    public static final String KEY_VIEW_AREA_BLUE = "KEY_VIEW_AREA_BLUE";
    public static final String KEY_VIEW_AREA_TYPE = "KEY_VIEW_AREA_TYPE";
    public static final String KEY_VIEW_AREA_TXT_SIZE = "KEY_VIEW_AREA_TXT_SIZE";
    public static final String KEY_VIEW_AREA_TXT_SHOW_PKG = "KEY_VIEW_AREA_TXT_SHOW_PKG";
    public static final String KEY_VIEW_AREA_TXT_SINGLE_LINE = "KEY_VIEW_AREA_TXT_SINGLE_LINE";
    public static final String KEY_VIEW_AREA_WRITE_TO_LOG = "KEY_VIEW_AREA_WRITE_TO_LOG";
    public static final String KEY_VIEW_AREA_TRANSLATE_TOP = "KEY_VIEW_AREA_TRANSLATE_TOP";
    public static final String KEY_VIEW_AREA_TRANSLATE_LEFT = "KEY_VIEW_AREA_TRANSLATE_LEFT";

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

    public static void setViewAreaAlpha(int value) {
        SharedPreferences.Editor edit = SP.edit();
        edit.putInt(KEY_VIEW_AREA_ALPHA, value);
        edit.apply();
    }

    public static int getViewAreaAlpha() {
        return SP.getInt(KEY_VIEW_AREA_ALPHA, OptionValue.DEFAULT_ALPHA);
    }

    public static void setViewAreaRed(int value) {
        SharedPreferences.Editor edit = SP.edit();
        edit.putInt(KEY_VIEW_AREA_RED, value);
        edit.apply();
    }

    public static int getViewAreaRed() {
        return SP.getInt(KEY_VIEW_AREA_RED, OptionValue.DEFAULT_RED);
    }

    public static void setViewAreaGreen(int value) {
        SharedPreferences.Editor edit = SP.edit();
        edit.putInt(KEY_VIEW_AREA_GREEN, value);
        edit.apply();
    }

    public static int getViewAreaGreen() {
        return SP.getInt(KEY_VIEW_AREA_GREEN, OptionValue.DEFAULT_GREEN);
    }

    public static void setViewAreaBlue(int value) {
        SharedPreferences.Editor edit = SP.edit();
        edit.putInt(KEY_VIEW_AREA_BLUE, value);
        edit.apply();
    }

    public static int getViewAreaBlue() {
        return SP.getInt(KEY_VIEW_AREA_BLUE, OptionValue.DEFAULT_BLUE);
    }

    public static void setViewAreaType(int value) {
        SharedPreferences.Editor edit = SP.edit();
        edit.putInt(KEY_VIEW_AREA_TYPE, value);
        edit.apply();
    }

    public static int getViewAreaType() {
        return SP.getInt(KEY_VIEW_AREA_TYPE, OptionValue.DEFAULT_VIEW_AREA_TYPE);
    }

    public static void setViewAreaTxtSize(int value) {
        SharedPreferences.Editor edit = SP.edit();
        edit.putInt(KEY_VIEW_AREA_TXT_SIZE, value);
        edit.apply();
    }

    public static int getViewAreaTxtSize() {
        return SP.getInt(KEY_VIEW_AREA_TXT_SIZE, OptionValue.DEFAULT_VIEW_AREA_TXT_SIZE);
    }

    public static void setViewAreaTxtShowPkg(boolean show) {
        SharedPreferences.Editor edit = SP.edit();
        edit.putBoolean(KEY_VIEW_AREA_TXT_SHOW_PKG, show);
        edit.apply();
    }

    public static boolean getViewAreaTxtShowPkg() {
        return SP.getBoolean(KEY_VIEW_AREA_TXT_SHOW_PKG, OptionValue.DEFAULT_VIEW_AREA_TXT_SHOW_PKG);
    }

    public static void setViewAreaTxtSingleLine(boolean show) {
        SharedPreferences.Editor edit = SP.edit();
        edit.putBoolean(KEY_VIEW_AREA_TXT_SINGLE_LINE, show);
        edit.apply();
    }

    public static boolean getViewAreaTxtSingleLine() {
        return SP.getBoolean(KEY_VIEW_AREA_TXT_SINGLE_LINE, OptionValue.DEFAULT_VIEW_AREA_TXT_SINGLE_LINE);
    }

    public static void setViewAreaWriteToLog(boolean show) {
        SharedPreferences.Editor edit = SP.edit();
        edit.putBoolean(KEY_VIEW_AREA_WRITE_TO_LOG, show);
        edit.apply();
    }

    public static boolean getViewAreaWriteToLog() {
        return SP.getBoolean(KEY_VIEW_AREA_WRITE_TO_LOG, OptionValue.DEFAULT_VIEW_AREA_WRITE_TO_LOG);
    }

    public static void setViewAreaTranslateTop(boolean b) {
        SharedPreferences.Editor edit = SP.edit();
        edit.putBoolean(KEY_VIEW_AREA_TRANSLATE_TOP, b);
        edit.apply();
    }

    public static boolean getViewAreaTranslateTop() {
        return SP.getBoolean(KEY_VIEW_AREA_TRANSLATE_TOP, OptionValue.DEFAULT_VIEW_AREA_TRANSLATE_TOP);
    }

    public static void setViewAreaTranslateLeft(boolean b) {
        SharedPreferences.Editor edit = SP.edit();
        edit.putBoolean(KEY_VIEW_AREA_TRANSLATE_LEFT, b);
        edit.apply();
    }

    public static boolean getViewAreaTranslateLeft() {
        return SP.getBoolean(KEY_VIEW_AREA_TRANSLATE_LEFT, OptionValue.DEFAULT_VIEW_AREA_TRANSLATE_LEFT);
    }

}
