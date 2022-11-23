package com.hichemtech.android_utils;

import android.util.Log;

import com.google.gson.Gson;

public abstract class Logger {
    public static void e(String tag, Object... msgs) {
        String s = "";
        for (Object msg : msgs) {
            s = s.concat(String.valueOf(msg));
            s = s.concat(",");
        }
        if (s.endsWith(",")) {
            s = s.substring(0, s.length() - 1);
        }
        e(tag, s);
    }
    public static void e(String tag, int msg) {
        e(tag, String.valueOf(msg));
    }
    public static void e(Object object) {
        e("Object Logger", object);
    }
    public static void e(String tag, Object object) {
        e(tag, new Gson().toJson(object));
    }
    public static void e(String tag, String msg) {
        e(tag, msg, false);
    }
    public static void e(String tag, String msg, boolean all) {
        if (!all) {
            Log.e(tag, msg);
        }
        else {
            final int CHUNK_SIZE = 4052;  // Typical max logcat payload.
            int offset = 0;
            while (offset + CHUNK_SIZE <= msg.length()) {
                Log.e(tag, msg.substring(offset, offset += CHUNK_SIZE));
            }
            if (offset < msg.length()) {
                Log.e(tag, msg.substring(offset));
            }
        }
    }
}
