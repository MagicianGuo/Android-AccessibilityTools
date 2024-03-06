package com.magicianguo.accessibilitytools.util;

import android.accessibilityservice.GestureDescription;
import android.graphics.Path;
import android.os.Build;
import android.util.DisplayMetrics;

import com.magicianguo.accessibilitytools.App;
import com.magicianguo.accessibilitytools.R;
import com.magicianguo.accessibilitytools.constant.TurnPageTime;
import com.magicianguo.accessibilitytools.constant.TurnPageType;
import com.magicianguo.accessibilitytools.service.MyAccessibilityService;

public class AccessibilityServiceManager {
    private static MyAccessibilityService mService;
    private static Thread mThread;

    public static void setService(MyAccessibilityService service) {
        mService = service;
    }

    public static void showViewArea() {
        MyAccessibilityService service = mService;
        if (service == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            AlertManager.showViewArea(service);
        } else {
            ToastUtils.longCall(R.string.toast_view_explorer_unsupported);
        }
    }

    public static void hideViewArea() {
        AlertManager.hideViewArea();
    }

    public static void turnPage(@TurnPageType.Type int type) {
        MyAccessibilityService service = mService;
        if (service == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            DisplayMetrics displayMetrics = App.get().getResources().getDisplayMetrics();
            float width = displayMetrics.widthPixels;
            float height = displayMetrics.heightPixels;
            Path path = new Path();
            switch (type) {
                case TurnPageType.LEFT:
                    path.moveTo(width * 9 / 10F, height / 2F);
                    path.lineTo(width / 10F, height / 2F);
                    break;
                case TurnPageType.UP:
                    path.moveTo(width / 2F, height / 2F + 300);
                    path.lineTo(width / 2F, height / 2F);
                    break;
                case TurnPageType.RIGHT:
                    path.moveTo(width / 10F, height / 2F);
                    path.lineTo(width * 9 / 10F, height / 2F);
                    break;
                case TurnPageType.UP_VIDEO:
                    path.moveTo(width / 2F, height * 8F / 10);
                    path.lineTo(width / 2F, height * 3F / 10);
                    break;
            }
            abortThreadTask();
            mThread = new Thread("turnPage") {
                @Override
                public void run() {
                    long count;
                    long interval;
                    if (type == TurnPageType.UP_VIDEO) {
                        count = Long.MAX_VALUE;
                        interval = TurnPageTime.VIDEO_INTERVAL_TIME;
                    } else {
                        count = TurnPageTime.MANGA_TOTAL_TIME / TurnPageTime.MANGA_INTERVAL_TIME;
                        interval = TurnPageTime.MANGA_INTERVAL_TIME;
                    }
                    for (int i = 0; i < count; i++) {
                        GestureDescription.StrokeDescription strokeDescription = new GestureDescription.StrokeDescription(path, 0L, TurnPageTime.SLIDE_TIME);
                        GestureDescription gestureDescription = new GestureDescription.Builder()
                                .addStroke(strokeDescription)
                                .build();
                        service.dispatchGesture(gestureDescription, null, null);
                        try {
                            sleep(interval);
                        } catch (InterruptedException ignored) {
                            break;
                        }
                    }
                }
            };
            mThread.start();
        } else {
            ToastUtils.longCall(R.string.toast_gesture_unsupported);
        }
    }

    public static void abortThreadTask() {
        if (mThread != null) {
            mThread.interrupt();
        }
    }

    public static void performClick(int clickX, int clickY, int clickInterval, int clickCount) {
        MyAccessibilityService service = mService;
        if (service == null) {
            return;
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            Path path = new Path();
            path.moveTo(clickX - 1F, clickY - 1F);
            path.moveTo(clickX + 0F, clickY + 0F);
            abortThreadTask();
            mThread = new Thread("performClick") {
                @Override
                public void run() {
                    for (int i = 0; i < clickCount; i++) {
                        GestureDescription.StrokeDescription strokeDescription = new GestureDescription.StrokeDescription(path, 0L, 1L);
                        GestureDescription gestureDescription = new GestureDescription.Builder()
                                .addStroke(strokeDescription)
                                .build();
                        service.dispatchGesture(gestureDescription, null, null);
                        try {
                            sleep(clickInterval);
                        } catch (InterruptedException e) {
                            break;
                        }
                    }
                }
            };
            mThread.start();
        } else {
            ToastUtils.longCall(R.string.toast_gesture_unsupported);
        }
    }

}
