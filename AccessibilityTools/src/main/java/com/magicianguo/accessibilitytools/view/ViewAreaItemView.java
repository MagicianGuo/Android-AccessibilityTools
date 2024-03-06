package com.magicianguo.accessibilitytools.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.InsetDrawable;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.magicianguo.accessibilitytools.util.SPUtils;

public class ViewAreaItemView extends View {
    public static int alpha = SPUtils.getViewAreaAlpha();
    public static int red = SPUtils.getViewAreaRed();
    public static int green = SPUtils.getViewAreaGreen();
    public static int blue = SPUtils.getViewAreaBlue();

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

    private void init() {
        setBackground(new InsetDrawable(new ColorDrawable(Color.argb(alpha, red, green, blue)), 1, 1, 1, 1));
    }
}
