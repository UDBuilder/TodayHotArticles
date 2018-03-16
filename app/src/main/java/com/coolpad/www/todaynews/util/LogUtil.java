package com.coolpad.www.todaynews.util;

import android.util.Log;

/**
 * Created by xiongjianbo on 2018/3/6.
 */

public class LogUtil {

    private static final String APP_TAG = "TodayNews";

    public static void d(String tag, String message) {
        print(Log.DEBUG, tag, message);
    }

    public static void v(String tag, String message) {
        print(Log.VERBOSE, tag, message);
    }

    public static void w(String tag, String message) {
        print(Log.WARN, tag, message);
    }

    public static void e(String tag, String message, Exception e) {
        print(Log.ERROR, tag, message + e.getMessage());
    }

    public static void e(String tag, String message) {
        print(Log.ERROR, tag, message);
    }

    public static void i(String tag, String message) {
        print(Log.INFO, tag, message);
    }

    private static void print(int level, String tag, String message) {
        Log.println(level, APP_TAG, String.format("%s -> %s", tag, message));
    }

}
