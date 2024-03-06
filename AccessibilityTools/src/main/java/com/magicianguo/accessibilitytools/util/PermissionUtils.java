package com.magicianguo.accessibilitytools.util;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;

import com.magicianguo.accessibilitytools.App;
import com.magicianguo.accessibilitytools.constant.RequestCode;
import com.magicianguo.accessibilitytools.service.MyAccessibilityService;

public class PermissionUtils {
    public static void checkNotificationPermission(Activity activity) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            int result = activity.checkSelfPermission(Manifest.permission.POST_NOTIFICATIONS);
            if (result != PackageManager.PERMISSION_GRANTED) {
                activity.requestPermissions(new String[]{Manifest.permission.POST_NOTIFICATIONS}, RequestCode.NOTIFICATION);
            }
        }
    }

    public static boolean isAccessibilityEnabled() {
        return MyAccessibilityService.isEnabled;
    }

    public static void goAccessibilityPage(Activity activity) {
        Intent intent = new Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS);
        activity.startActivity(intent);
    }

    public static void goAppDetailPage(Activity activity) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                .setData(Uri.parse("package:" + activity.getPackageName()));
        activity.startActivity(intent);
    }

    public static boolean hasAlertPermission() {
        return Settings.canDrawOverlays(App.get());
    }

    public static void requestAlertPermission(Activity activity) {
        Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
                .setData(Uri.parse("package:" + activity.getPackageName()));
        activity.startActivity(intent);
    }
}
