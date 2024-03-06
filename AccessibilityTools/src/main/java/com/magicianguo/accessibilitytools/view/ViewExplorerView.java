package com.magicianguo.accessibilitytools.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;

import com.magicianguo.accessibilitytools.R;
import com.magicianguo.accessibilitytools.util.AccessibilityServiceManager;
import com.magicianguo.accessibilitytools.util.AlertManager;
import com.magicianguo.accessibilitytools.util.DimenUtils;

public class ViewExplorerView extends LinearLayout {
    public ViewExplorerView(Context context) {
        super(context);
        init();
    }

    public ViewExplorerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ViewExplorerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_view_explorer_view, this);
        setBackgroundResource(R.drawable.bg_alert_common);
        setOrientation(VERTICAL);
        int padding = (int) DimenUtils.dp2px(10F);
        setPadding(padding, padding, padding, padding);
        setGravity(Gravity.CENTER_HORIZONTAL);

        findViewById(R.id.btn_show_view_area).setOnClickListener(v -> {
            AccessibilityServiceManager.showViewArea();
        });
        findViewById(R.id.btn_hide_view_area).setOnClickListener(v -> {
            AccessibilityServiceManager.hideViewArea();
        });
        findViewById(R.id.btn_close_alert).setOnClickListener(v -> {
            AlertManager.hideViewExplorerView();
        });
    }
}
