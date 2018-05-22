package com.kang.media.util;

import android.util.Log;

public class LogUtil {
    private static final String TAG = "LogUtil";
    private static final boolean debug = true;
    private static final String SPLIT = ">>>>>>";

    public static void e(String pre, String msg) {
        Log.e(TAG, SPLIT + pre + ":::" + msg);
    }

    public static void d(String pre, String msg) {
        if (debug) {
            Log.d(TAG, SPLIT + pre + ":::" + msg);
        }
    }

    public static void i(String pre, String msg) {
        if (debug) {
            Log.i(TAG, SPLIT + pre + ":::" + msg);
        }
    }

    public static void v(String pre, String msg) {
        Log.v(TAG, SPLIT + pre + ":::" + msg);
    }
}
