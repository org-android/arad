package com.ruitu.arad.util;

import android.util.Log;

import com.ruitu.arad.base.Config;

public final class Logg {
    public static final String TAG = "develop_debug_log";
    private static boolean isDebug = Config.IS_DEBUG;

    public static void i(String msg) {
        if (isDebug) {
            Log.i(TAG, msg);
        }
    }

    public static void d(String msg) {
        if (isDebug) {
            Log.d(TAG, msg);
        }
    }

    public static void w(String msg) {
        if (isDebug) {
            Log.w(TAG, msg);
        }
    }

    public static void e(String msg) {
        if (isDebug) {
            Log.e(TAG, msg);
        }
    }
}
