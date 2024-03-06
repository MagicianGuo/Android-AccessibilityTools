package com.magicianguo.accessibilitytools.view;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.magicianguo.accessibilitytools.R;
import com.magicianguo.accessibilitytools.constant.TurnPageTime;
import com.magicianguo.accessibilitytools.constant.TurnPageType;
import com.magicianguo.accessibilitytools.util.AccessibilityServiceManager;
import com.magicianguo.accessibilitytools.util.DimenUtils;
import com.magicianguo.accessibilitytools.util.TimeUtils;
import com.magicianguo.accessibilitytools.util.VibratorUtils;

public class TurnPageView extends LinearLayout {
    public TurnPageView(Context context) {
        super(context);
        init();
    }

    public TurnPageView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public TurnPageView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private TextView mTvTime;
    private TextView mBtnChangeSize;
    private View mRlOperation;
    private static final int MSG_COUNT_DOWN_TIME = 1;
    private static final int MSG_COUNT_UP_TIME = 2;
    private static final int MSG_RESET_TIME = 3;
    private final Handler HANDLER = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            int timeSecond;
            if (msg.what == MSG_COUNT_DOWN_TIME) {
                timeSecond = (int) msg.obj;
                // 倒计时
                mTvTime.setText(TimeUtils.toMSTimeStr(timeSecond));
                if (timeSecond == 0) {
                    VibratorUtils.make3Vibrate();
                } else {
                    timeSecond--;
                    startCountDownTime(timeSecond, 1000);
                }
            } else if (msg.what == MSG_COUNT_UP_TIME) {
                timeSecond = (int) msg.obj;
                // 倒计时
                mTvTime.setText(TimeUtils.toMSTimeStr(timeSecond));
                timeSecond++;
                startCountUpTime(timeSecond, 1000);
            } else if (msg.what == MSG_RESET_TIME) {
                mTvTime.setText(TimeUtils.toMSTimeStr(0));
            }
        }
    };

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_turn_page_view, this);
        setBackgroundResource(R.drawable.bg_alert_common);
        setOrientation(VERTICAL);
        int padding = (int) DimenUtils.dp2px(10F);
        setPadding(padding, padding, padding, padding);
        setGravity(Gravity.CENTER_HORIZONTAL);

        mTvTime = findViewById(R.id.tv_time);
        mBtnChangeSize = findViewById(R.id.btn_change_size);
        mRlOperation = findViewById(R.id.rl_operation);

        mBtnChangeSize.setOnClickListener(v -> {
            boolean selected = v.isSelected();
            if (selected) {
                mRlOperation.setVisibility(VISIBLE);
                setAlpha(1F);
                mBtnChangeSize.setText(R.string.btn_txt_make_alert_small);
            } else {
                mRlOperation.setVisibility(GONE);
                setAlpha(0.5F);
                mBtnChangeSize.setText(R.string.btn_txt_make_alert_large);
            }
            v.setSelected(!selected);
        });

        findViewById(R.id.btn_left).setOnClickListener(v -> {
            removeAllMsg();
            startCountDownTime((int) (TurnPageTime.MANGA_TOTAL_TIME / 1000), 0);
            AccessibilityServiceManager.turnPage(TurnPageType.LEFT);
        });
        findViewById(R.id.btn_up).setOnClickListener(v -> {
            removeAllMsg();
            startCountDownTime((int) (TurnPageTime.MANGA_TOTAL_TIME / 1000), 0);
            AccessibilityServiceManager.turnPage(TurnPageType.UP);
        });
        findViewById(R.id.btn_right).setOnClickListener(v -> {
            removeAllMsg();
            startCountDownTime((int) (TurnPageTime.MANGA_TOTAL_TIME / 1000), 0);
            AccessibilityServiceManager.turnPage(TurnPageType.RIGHT);
        });
        findViewById(R.id.btn_up_video).setOnClickListener(v -> {
            removeAllMsg();
            startCountUpTime(0, 0);
            AccessibilityServiceManager.turnPage(TurnPageType.UP_VIDEO);
        });
        findViewById(R.id.btn_turn_stop).setOnClickListener(v -> {
            removeAllMsg();
            resetTime();
            AccessibilityServiceManager.abortThreadTask();
        });
    }

    private void startCountUpTime(int timeSecond, long delay) {
        Message msg = new Message();
        msg.what = MSG_COUNT_UP_TIME;
        msg.obj = timeSecond;
        HANDLER.sendMessageDelayed(msg, delay);
    }

    private void startCountDownTime(int timeSecond, long delay) {
        Message msg = new Message();
        msg.what = MSG_COUNT_DOWN_TIME;
        msg.obj = timeSecond;
        HANDLER.sendMessageDelayed(msg, delay);
    }

    private void resetTime() {
        HANDLER.sendEmptyMessage(MSG_RESET_TIME);
    }

    private void removeAllMsg() {
        HANDLER.removeMessages(MSG_COUNT_DOWN_TIME);
        HANDLER.removeMessages(MSG_COUNT_UP_TIME);
        HANDLER.removeMessages(MSG_RESET_TIME);
    }
}
