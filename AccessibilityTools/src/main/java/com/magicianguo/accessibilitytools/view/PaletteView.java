package com.magicianguo.accessibilitytools.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.os.SystemClock;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.magicianguo.accessibilitytools.R;
import com.magicianguo.accessibilitytools.util.DimenUtils;

import java.util.ArrayList;
import java.util.List;

public class PaletteView extends FrameLayout {
    public PaletteView(@NonNull Context context) {
        super(context);
        init();
    }

    public PaletteView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public PaletteView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private TextView mTvDesc;
    private float mAbsoluteTouchX = 0F;
    private float mAbsoluteTouchY = 0F;
    private int mClickCount = 0;
    private float mDownX = 0F;
    private float mDownY = 0F;
    private final int DP_PIXELS = (int) DimenUtils.dp2px(1);
    private final Paint PAINT = new Paint();
    private final Path PATH = new Path();
    private final List<BeanWave> WAVE_LIST = new ArrayList<>();
    private static final int MSG_UPDATE_WAVE = 1;
    private final Handler HANDLER = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == MSG_UPDATE_WAVE) {
                BeanWave wave = (BeanWave) msg.obj;
                wave.radius += 2 * DP_PIXELS;
                if (wave.radius >= 18 * DP_PIXELS || wave.radius % DP_PIXELS != 0) {
                    wave.radius = -1;
                }
                invalidate();
            }
        }
    };

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_palette_view, this);
        mTvDesc = findViewById(R.id.tv_desc);
        PAINT.setAntiAlias(true);
        PAINT.setStyle(Paint.Style.STROKE);
        PAINT.setStrokeWidth(2 * DP_PIXELS);
        PAINT.setColor(Color.BLACK);
        setBackgroundColor(Color.rgb(0xDD, 0xDD, 0xDD));
        updateDesc();
    }

    @Override
    protected void onDraw(@NonNull Canvas canvas) {
        for (int i = WAVE_LIST.size() - 1; i >= 0; i--) {
            BeanWave beanWave = WAVE_LIST.get(i);
            if (beanWave.radius == -1F) {
                WAVE_LIST.remove(beanWave);
            }
        }
        for (BeanWave b : WAVE_LIST) {
            canvas.drawCircle(mDownX, mDownY, b.radius, PAINT);
        }
        canvas.drawPath(PATH, PAINT);
    }

    private void updateDesc() {
        mTvDesc.setText(getContext().getString(R.string.palette_desc, mAbsoluteTouchX, mAbsoluteTouchY, mClickCount));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mAbsoluteTouchX = event.getRawX();
        mAbsoluteTouchY = event.getRawY();
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = event.getX();
                mDownY = event.getY();
                PATH.reset();
                PATH.moveTo(mDownX, mDownY);
                if (WAVE_LIST.size() < 10) {
                    BeanWave wave = new BeanWave(0);
                    WAVE_LIST.add(wave);
                    long time = SystemClock.uptimeMillis();
                    for (int i = 0; i < 10; i++) {
                        Message msg = new Message();
                        msg.what = MSG_UPDATE_WAVE;
                        msg.obj = wave;
                        HANDLER.sendMessageAtTime(msg, time);
                        time += 30;
                    }
                }
            case MotionEvent.ACTION_MOVE:
                PATH.lineTo(event.getX(), event.getY());
                break;
            case MotionEvent.ACTION_UP:
                mClickCount++;
        }
        invalidate();
        updateDesc();
        return true;
    }

    private static class BeanWave {
        private int radius;

        private BeanWave(int radius) {
            this.radius = radius;
        }

        @Override
        public String toString() {
            return "BeanWave{" +
                    "radius=" + radius +
                    '}';
        }
    }

}
