package com.magicianguo.accessibilitytools.service;

import android.accessibilityservice.AccessibilityService;
import android.view.accessibility.AccessibilityEvent;

import com.magicianguo.accessibilitytools.util.AccessibilityServiceManager;

public class MyAccessibilityService extends AccessibilityService {
    public static boolean isEnabled = false;

    @Override
    public void onCreate() {
        isEnabled = true;
        AccessibilityServiceManager.setService(this);
    }

    @Override
    public void onDestroy() {
        isEnabled = false;
        AccessibilityServiceManager.setService(null);
    }

    @Override
    public void onAccessibilityEvent(AccessibilityEvent event) {
    }

    @Override
    public void onInterrupt() {
    }
}
