package com.magicianguo.accessibilitytools.util;

public class TimeUtils {
    public static String toMSTimeStr(int second) {
        int m = second / 60;
        int s = second - 60 * m;
        return String.format("%02d:%02d", m, s);
    }
}
