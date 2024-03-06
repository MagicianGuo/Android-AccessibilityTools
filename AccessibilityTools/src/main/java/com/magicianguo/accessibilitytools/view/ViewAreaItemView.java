package com.magicianguo.accessibilitytools.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.InsetDrawable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.magicianguo.accessibilitytools.R;
import com.magicianguo.accessibilitytools.util.SPUtils;

public class ViewAreaItemView extends FrameLayout {
    public static int alpha = SPUtils.getViewAreaAlpha();
    public static int red = SPUtils.getViewAreaRed();
    public static int green = SPUtils.getViewAreaGreen();
    public static int blue = SPUtils.getViewAreaBlue();
    public static int textSize = SPUtils.getViewAreaTxtSize();

    public ViewAreaItemView(Context context) {
        super(context);
        init();
    }

    public ViewAreaItemView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ViewAreaItemView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private View mAreaView;
    private TextView mTvAreaInfo;

    private void init() {
        LayoutInflater.from(getContext()).inflate(R.layout.layout_view_area_item, this);
        mAreaView = findViewById(R.id.view_area);
        mTvAreaInfo = findViewById(R.id.tv_area_info);
        mTvAreaInfo.setTextSize(TypedValue.COMPLEX_UNIT_PX, textSize);
        mAreaView.setBackground(new InsetDrawable(new ColorDrawable(Color.argb(alpha, red, green, blue)), 1, 1, 1, 1));
    }

    public void setText(String text) {
        mTvAreaInfo.setText(text);
    }

    public void setAreaSize(int width, int height) {
        FrameLayout.LayoutParams params = (LayoutParams) mAreaView.getLayoutParams();
        params.width = width;
        params.height = height;
        mAreaView.setLayoutParams(params);
    }
}
