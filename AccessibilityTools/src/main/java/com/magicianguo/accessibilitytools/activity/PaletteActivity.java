package com.magicianguo.accessibilitytools.activity;

import android.app.Activity;
import android.os.Bundle;

import androidx.annotation.Nullable;

import com.magicianguo.accessibilitytools.R;

public class PaletteActivity extends Activity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_palette);
    }
}
