package com.magicianguo.accessibilitytools.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.constraintlayout.widget.Group;

import com.magicianguo.accessibilitytools.R;
import com.magicianguo.accessibilitytools.util.AccessibilityServiceManager;
import com.magicianguo.accessibilitytools.util.AlertManager;
import com.magicianguo.accessibilitytools.util.DimenUtils;
import com.magicianguo.accessibilitytools.util.SPUtils;
import com.magicianguo.accessibilitytools.util.ToastUtils;

public class ClickToolsView extends ConstraintLayout {
    public ClickToolsView(Context context) {
        super(context);
        init();
    }

    public ClickToolsView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ClickToolsView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private Group mGroupConfig;
    private TextView mBtnChangeSize;
    private TextView mBtnOperation;
    private EditText mEtClickX;
    private EditText mEtClickY;
    private EditText mEtClickInterval;
    private EditText mEtClickCount;

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_click_tools_view, this);
        setBackgroundResource(R.drawable.bg_alert_common);
        int padding = (int) DimenUtils.dp2px(10F);
        setPadding(padding, padding, padding, padding);

        mGroupConfig = findViewById(R.id.group_config);
        mBtnChangeSize = findViewById(R.id.btn_change_size);
        mBtnOperation = findViewById(R.id.btn_operation);
        mEtClickX = findViewById(R.id.et_set_x);
        mEtClickY = findViewById(R.id.et_set_y);
        mEtClickInterval = findViewById(R.id.et_set_interval);
        mEtClickCount = findViewById(R.id.et_set_count);

        mEtClickX.setText("" + SPUtils.getClickX());
        mEtClickY.setText("" + SPUtils.getClickY());
        mEtClickInterval.setText("" + SPUtils.getClickInterval());
        mEtClickCount.setText("" + SPUtils.getClickCount());

        mBtnChangeSize.setOnClickListener(v -> {
            boolean selected = v.isSelected();
            if (selected) {
                mGroupConfig.setVisibility(VISIBLE);
                setAlpha(1F);
                mBtnChangeSize.setText(R.string.btn_txt_make_alert_small);
            } else {
                mGroupConfig.setVisibility(GONE);
                setAlpha(0.5F);
                mBtnChangeSize.setText(R.string.btn_txt_make_alert_large);
            }
            v.setSelected(!selected);
        });
        mBtnOperation.setOnClickListener(v -> {
            boolean selected = v.isSelected();
            if (selected) {
                if (saveConfig()) {
                    AlertManager.hideClickToolsView();
                    AlertManager.setClickToolsViewInputEnabled(false);
                    AlertManager.showClickToolsView();
                    mBtnOperation.setSelected(false);
                    mBtnOperation.setText(R.string.btn_txt_start_edit);
                }
            } else {
                AlertManager.hideClickToolsView();
                AlertManager.setClickToolsViewInputEnabled(true);
                AlertManager.showClickToolsView();
                mBtnOperation.setSelected(true);
                mBtnOperation.setText(R.string.btn_txt_save_config);
            }
        });
        findViewById(R.id.btn_start_click).setOnClickListener(v -> {
            AccessibilityServiceManager.performClick(
                    SPUtils.getClickX(), SPUtils.getClickY(), SPUtils.getClickInterval(), SPUtils.getClickCount()
            );
        });
        findViewById(R.id.btn_stop_click).setOnClickListener(v -> {
            AccessibilityServiceManager.abortThreadTask();
        });
    }

    private boolean saveConfig() {
        int x = getDecodeNum(mEtClickX.getText().toString());
        if (x < 0) {
            ToastUtils.shortCall(R.string.toast_click_x_coordinate_wrong);
            return false;
        }
        int y = getDecodeNum(mEtClickY.getText().toString());
        if (y < 0) {
            ToastUtils.shortCall(R.string.toast_click_y_coordinate_wrong);
            return false;
        }
        int interval = getDecodeNum(mEtClickInterval.getText().toString());
        if (interval <= 0) {
            ToastUtils.shortCall(R.string.toast_click_interval_wrong);
            return false;
        }
        int count = getDecodeNum(mEtClickCount.getText().toString());
        if (count <= 0) {
            ToastUtils.shortCall(R.string.toast_click_count_wrong);
            return false;
        }
        SPUtils.setClickX(x);
        SPUtils.setClickY(y);
        SPUtils.setClickInterval(interval);
        SPUtils.setClickCount(count);
        ToastUtils.shortCall(R.string.toast_click_params_saved);
        return true;
    }

    private int getDecodeNum(String str) {
        try {
            return Integer.decode(str);
        } catch (NumberFormatException ignored) {
        }
        return -1;
    }
}
