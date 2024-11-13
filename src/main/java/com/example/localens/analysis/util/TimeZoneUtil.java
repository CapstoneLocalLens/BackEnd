package com.example.localens.analysis.util;

public class TimeZoneUtil {

    public static String getTimeZone(int hour) {
        if (hour >= 0 && hour < 6) {
            return "00~06시";
        } else if (hour >= 6 && hour < 11) {
            return "06~11시";
        } else if (hour >= 11 && hour < 14) {
            return "11~14시";
        } else if (hour >= 14 && hour < 17) {
            return "14~17시";
        } else if (hour >= 17 && hour < 21) {
            return "17~21시";
        } else {
            return "21~24시";
        }
    }
}
