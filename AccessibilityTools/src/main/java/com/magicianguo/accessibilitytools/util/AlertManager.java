package com.magicianguo.accessibilitytools.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.PixelFormat;
import android.os.Build;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import com.magicianguo.accessibilitytools.App;
import com.magicianguo.accessibilitytools.view.ClickToolsView;
import com.magicianguo.accessibilitytools.view.TurnPageView;

@SuppressLint("ClickableViewAccessibility,StaticFieldLeak")
public class AlertManager {
    private static final WindowManager WINDOW_MANAGER = (WindowManager) App.get().getSystemService(Context.WINDOW_SERVICE);
    public static boolean isTurnPageViewShowing = false;
    private static final TurnPageView TURN_PAGE_VIEW = new TurnPageView(App.get());
    private static final WindowManager.LayoutParams TURN_PAGE_VIEW_PARAMS = newAlertParams();
    public static boolean isClickToolsViewShowing = false;
    private static final ClickToolsView CLICK_TOOLS_VIEW = new ClickToolsView(App.get());
    private static final WindowManager.LayoutParams CLICK_TOOLS_VIEW_PARAMS = newAlertParams();

    private static final int STATUS_BAR_HEIGHT = DimenUtils.STATUS_BAR_HEIGHT;

    private static float mDownX = 0F;
    private static float mDownY = 0F;

    private static final View.OnTouchListener ON_TOUCH_LISTENER = new View.OnTouchListener() {
        private WindowManager.LayoutParams targetParams;

        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    mDownX = event.getX();
                    mDownY = event.getY();
                    targetParams = requireParams(v);
                    break;
                case MotionEvent.ACTION_MOVE:
                    float x, y;
                    if (ScreenUtils.isPortrait()) {
                        x = event.getRawX() - mDownX;
                        y = event.getRawY() - mDownY - STATUS_BAR_HEIGHT;
                    } else {
                        x = event.getRawX() - mDownX - STATUS_BAR_HEIGHT;
                        y = event.getRawY() - mDownY;
                    }
                    targetParams.x = (int) x;
                    targetParams.y = (int) y;
                    WINDOW_MANAGER.updateViewLayout(v, targetParams);
                    break;
                case MotionEvent.ACTION_UP:
                    targetParams = null;
            }
            return true;
        }

        private WindowManager.LayoutParams requireParams(View v) {
            if (v.equals(TURN_PAGE_VIEW)) {
                return TURN_PAGE_VIEW_PARAMS;
            } else if (v.equals(CLICK_TOOLS_VIEW)) {
                return CLICK_TOOLS_VIEW_PARAMS;
            }
            throw new RuntimeException("Unknown view: " + v);
        }
    };

    static {
        TURN_PAGE_VIEW.setOnTouchListener(ON_TOUCH_LISTENER);
        CLICK_TOOLS_VIEW.setOnTouchListener(ON_TOUCH_LISTENER);
    }

    public static void showTurnPageView() {
        if (isTurnPageViewShowing) {
            return;
        }
        WINDOW_MANAGER.addView(TURN_PAGE_VIEW, TURN_PAGE_VIEW_PARAMS);
        isTurnPageViewShowing = true;
    }

    public static void hideTurnPageView() {
        if (!isTurnPageViewShowing) {
            return;
        }
        WINDOW_MANAGER.removeView(TURN_PAGE_VIEW);
        isTurnPageViewShowing = false;
    }

    public static void showClickToolsView() {
        if (isClickToolsViewShowing) {
            return;
        }
        WINDOW_MANAGER.addView(CLICK_TOOLS_VIEW, CLICK_TOOLS_VIEW_PARAMS);
        isClickToolsViewShowing = true;
    }

    public static void hideClickToolsView() {
        if (!isClickToolsViewShowing) {
            return;
        }
        WINDOW_MANAGER.removeView(CLICK_TOOLS_VIEW);
        isClickToolsViewShowing = false;
    }

    public static void setClickToolsViewInputEnabled(boolean enable) {
        if (enable) {
            CLICK_TOOLS_VIEW_PARAMS.flags = 0;
        } else {
            CLICK_TOOLS_VIEW_PARAMS.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        }
    }

    private static WindowManager.LayoutParams newAlertParams() {
        return newAlertParams(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
    }

    private static WindowManager.LayoutParams newAlertParams(int width, int height) {
        WindowManager.LayoutParams params = new WindowManager.LayoutParams();
        params.width = width;
        params.height = height;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            params.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY;
        } else {
            params.type = WindowManager.LayoutParams.TYPE_PHONE;
        }
        params.flags = WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE;
        params.gravity = Gravity.START | Gravity.TOP;
        params.format = PixelFormat.RGBA_8888;
        return params;
    }
}
