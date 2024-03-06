package com.magicianguo.accessibilitytools;

import android.app.Application;

public class App extends Application {
    private static App sApp;

    public static App get() {
        return sApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sApp = this;
    }
}
