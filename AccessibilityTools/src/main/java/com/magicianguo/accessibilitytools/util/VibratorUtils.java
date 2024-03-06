package com.magicianguo.accessibilitytools.util;

import android.content.Context;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;

import com.magicianguo.accessibilitytools.App;

public class VibratorUtils {
    private static final Vibrator VIBRATOR = (Vibrator) App.get().getSystemService(Context.VIBRATOR_SERVICE);

    public static void make3Vibrate() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            VIBRATOR.vibrate(VibrationEffect.createWaveform(
                    new long[]{1000, 1000, 1000, 1000, 1000, 1000},
                    new int[]{255, 0, 255, 0, 255, 0},
                    -1
            ));
        } else {
            VIBRATOR.vibrate(new long[]{0, 1000, 1000, 1000, 1000, 1000}, -1);
        }
    }
}
