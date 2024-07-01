package com.magicianguo.accessibilitytools.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.PixelFormat;
import android.graphics.Rect;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.widget.FrameLayout;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.magicianguo.accessibilitytools.App;
import com.magicianguo.accessibilitytools.constant.ViewAreaType;
import com.magicianguo.accessibilitytools.service.MyAccessibilityService;
import com.magicianguo.accessibilitytools.view.ClickToolsView;
import com.magicianguo.accessibilitytools.view.TurnPageView;
import com.magicianguo.accessibilitytools.view.ViewAreaItemView;
import com.magicianguo.accessibilitytools.view.ViewExplorerView;

import java.util.Date;

@SuppressLint("ClickableViewAccessibility,StaticFieldLeak")
public class AlertManager {
    private static final String TAG = "AlertManager";
    public static boolean isViewAreaTranslateTop = SPUtils.getViewAreaTranslateTop();
    public static boolean isViewAreaTranslateLeft = SPUtils.getViewAreaTranslateLeft();
    private static int viewAreaTranslateTop = 0;
    private static int viewAreaTranslateLeft = 0;
    private static final WindowManager WINDOW_MANAGER = (WindowManager) App.get().getSystemService(Context.WINDOW_SERVICE);
    public static boolean isViewExplorerViewShowing = false;
    private static final ViewExplorerView VIEW_EXPLORER_VIEW = new ViewExplorerView(App.get());
    private static final WindowManager.LayoutParams VIEW_EXPLORER_VIEW_PARAMS = newAlertParams();
    private static boolean isViewAreaViewShowing = false;
    public static int viewAreaType = SPUtils.getViewAreaType();
    public static boolean viewAreaTxtShowPkg = SPUtils.getViewAreaTxtShowPkg();
    public static boolean viewAreaTxtSingleLine = SPUtils.getViewAreaTxtSingleLine();
    public static boolean viewAreaWriteToLog = SPUtils.getViewAreaWriteToLog();
    private static final FrameLayout VIEW_AREA_VIEW = new FrameLayout(App.get());
    private static final WindowManager.LayoutParams VIEW_AREA_VIEW_PARAMS = newAlertParams(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
    public static boolean isTurnPageViewShowing = false;
    private static final TurnPageView TURN_PAGE_VIEW = new TurnPageView(App.get());
    private static final WindowManager.LayoutParams TURN_PAGE_VIEW_PARAMS = newAlertParams();
    private static final View TURN_PAGE_EXTRA_VIEW = new View(App.get());
    private static final WindowManager.LayoutParams TURN_PAGE_VIEW_EXTRA_PARAMS = newAlertParams();
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
                    isViewAreaTranslateTop = SPUtils.getViewAreaTranslateTop();
                    isViewAreaTranslateLeft = SPUtils.getViewAreaTranslateLeft();
                    viewAreaTranslateTop = isViewAreaTranslateTop ? STATUS_BAR_HEIGHT : 0;
                    viewAreaTranslateLeft = isViewAreaTranslateLeft ? STATUS_BAR_HEIGHT : 0;
                    break;
                case MotionEvent.ACTION_MOVE:
                    float x, y;
                    x = event.getRawX() - mDownX - viewAreaTranslateLeft;
                    y = event.getRawY() - mDownY - viewAreaTranslateTop;
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
            if (v.equals(VIEW_EXPLORER_VIEW)) {
                return VIEW_EXPLORER_VIEW_PARAMS;
            } else if (v.equals(TURN_PAGE_VIEW)) {
                return TURN_PAGE_VIEW_PARAMS;
            } else if (v.equals(CLICK_TOOLS_VIEW)) {
                return CLICK_TOOLS_VIEW_PARAMS;
            }
            throw new RuntimeException("Unknown view: " + v);
        }
    };

    static {
        VIEW_EXPLORER_VIEW.setOnTouchListener(ON_TOUCH_LISTENER);
        TURN_PAGE_VIEW.setOnTouchListener(ON_TOUCH_LISTENER);
        CLICK_TOOLS_VIEW.setOnTouchListener(ON_TOUCH_LISTENER);
        VIEW_AREA_VIEW_PARAMS.flags |= WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE;
    }

    public static void showViewExplorerView() {
        if (isViewExplorerViewShowing) {
            return;
        }
        WINDOW_MANAGER.addView(VIEW_EXPLORER_VIEW, VIEW_EXPLORER_VIEW_PARAMS);
        if (alertStateListener != null) {
            alertStateListener.onViewExplorer(true);
        }
        isViewExplorerViewShowing = true;
    }

    public static void hideViewExplorerView() {
        if (!isViewExplorerViewShowing) {
            return;
        }
        WINDOW_MANAGER.removeView(VIEW_EXPLORER_VIEW);
        if (alertStateListener != null) {
            alertStateListener.onViewExplorer(false);
        }
        isViewExplorerViewShowing = false;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public static void showViewArea(MyAccessibilityService service) {
        if (isViewAreaViewShowing) {
            hideViewArea();
        }
        AccessibilityNodeInfo rootInActiveWindow = service.getRootInActiveWindow();
        VIEW_AREA_VIEW.removeAllViews();
        isViewAreaTranslateTop = SPUtils.getViewAreaTranslateTop();
        isViewAreaTranslateLeft = SPUtils.getViewAreaTranslateLeft();
        viewAreaTranslateTop = isViewAreaTranslateTop ? STATUS_BAR_HEIGHT : 0;
        viewAreaTranslateLeft = isViewAreaTranslateLeft ? STATUS_BAR_HEIGHT : 0;
        recursionShowViewArea(rootInActiveWindow, "1");
        WINDOW_MANAGER.addView(VIEW_AREA_VIEW, VIEW_AREA_VIEW_PARAMS);
        isViewAreaViewShowing = true;
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private static void recursionShowViewArea(AccessibilityNodeInfo nodeInfo, String depthLevel) {
        Log.d(TAG, "recursionShowViewArea: nodeInfo = " + nodeInfo + " , depthLevel = " + depthLevel + " ");
        boolean shouldAdd;
        switch (viewAreaType) {
            case ViewAreaType.ALL:
                shouldAdd = true;
                break;
            case ViewAreaType.IMPORTANT:
                shouldAdd = nodeInfo.isImportantForAccessibility();
                break;
            case ViewAreaType.CONTAIN_TEXT:
                shouldAdd = !TextUtils.isEmpty(nodeInfo.getText());
                break;
            default:
                throw new RuntimeException("Unsupported type: " + viewAreaType);
        }
        if (shouldAdd) {
            Rect rect = new Rect();
            nodeInfo.getBoundsInScreen(rect);
            ViewAreaItemView view = new ViewAreaItemView(App.get());
            FrameLayout.LayoutParams params;
            if (viewAreaTxtSingleLine) {
                params = new FrameLayout.LayoutParams(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
                view.setAreaSize(rect.right - rect.left, rect.bottom - rect.top);
            } else {
                params = new FrameLayout.LayoutParams(rect.right - rect.left, rect.bottom - rect.top);
            }
            params.leftMargin = rect.left - viewAreaTranslateLeft;
            params.topMargin = rect.top - viewAreaTranslateTop;
            final String txt = nodeInfo.getViewIdResourceName();
            if (viewAreaTxtShowPkg) {
                view.setText(txt);
            } else {
                if (txt != null && txt.contains(":")) {
                    view.setText(txt.split(":")[1]);
                } else {
                    view.setText(txt);
                }
            }
            VIEW_AREA_VIEW.addView(view, params);
            if (viewAreaWriteToLog) {
                FileManager.writeViewAreaLog(new Date() + " - " + nodeInfo);
            }
        }
        int childCount = nodeInfo.getChildCount();
        for (int i = 0; i < childCount; i++) {
            recursionShowViewArea(nodeInfo.getChild(i), depthLevel + "." + (i + 1));
        }
    }

    public static void hideViewArea() {
        if (isViewAreaViewShowing) {
            VIEW_AREA_VIEW.removeAllViews();
            WINDOW_MANAGER.removeView(VIEW_AREA_VIEW);
        }
        isViewAreaViewShowing = false;
    }

    public static void showTurnPageView() {
        if (isTurnPageViewShowing) {
            return;
        }
        WINDOW_MANAGER.addView(TURN_PAGE_VIEW, TURN_PAGE_VIEW_PARAMS);
        // 增加底部防误触View
        TURN_PAGE_VIEW_EXTRA_PARAMS.width = WindowManager.LayoutParams.MATCH_PARENT;
        TURN_PAGE_VIEW_EXTRA_PARAMS.height = (int) DimenUtils.dp2px(70);
        TURN_PAGE_VIEW_EXTRA_PARAMS.gravity = Gravity.BOTTOM;
        TURN_PAGE_EXTRA_VIEW.setBackgroundColor(Color.argb(0x80, 0x45, 0xCB, 0xA8));
        WINDOW_MANAGER.addView(TURN_PAGE_EXTRA_VIEW, TURN_PAGE_VIEW_EXTRA_PARAMS);
        if (alertStateListener != null) {
            alertStateListener.onTurnPage(true);
        }
        isTurnPageViewShowing = true;
    }

    public static void hideTurnPageView() {
        if (!isTurnPageViewShowing) {
            return;
        }
        WINDOW_MANAGER.removeView(TURN_PAGE_VIEW);
        WINDOW_MANAGER.removeView(TURN_PAGE_EXTRA_VIEW);
        if (alertStateListener != null) {
            alertStateListener.onTurnPage(false);
        }
        isTurnPageViewShowing = false;
    }

    public static void showClickToolsView() {
        if (isClickToolsViewShowing) {
            return;
        }
        WINDOW_MANAGER.addView(CLICK_TOOLS_VIEW, CLICK_TOOLS_VIEW_PARAMS);
        if (alertStateListener != null) {
            alertStateListener.onClickTools(true);
        }
        isClickToolsViewShowing = true;
    }

    public static void hideClickToolsView() {
        if (!isClickToolsViewShowing) {
            return;
        }
        WINDOW_MANAGER.removeView(CLICK_TOOLS_VIEW);
        if (alertStateListener != null) {
            alertStateListener.onClickTools(false);
        }
        isClickToolsViewShowing = false;
    }

    public static void setClickToolsViewInputEnabled(boolean enable) {
        if (enable) {
            CLICK_TOOLS_VIEW_PARAMS.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL;
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

    @Nullable
    private static AlertStateListener alertStateListener;

    public static void setAlertStateListener(@Nullable AlertStateListener listener) {
        alertStateListener = listener;
    }

    public interface AlertStateListener {
        void onViewExplorer(boolean showing);

        void onTurnPage(boolean showing);

        void onClickTools(boolean showing);
    }
}
