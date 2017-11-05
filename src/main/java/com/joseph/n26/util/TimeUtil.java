package com.joseph.n26.util;

public class TimeUtil {

    public static boolean isOlderThan60s(long timestamp) {
        return System.currentTimeMillis() - timestamp > 60000;
    }

    public static boolean isFuture(long timestamp) {
        return System.currentTimeMillis() < timestamp;
    }

}
