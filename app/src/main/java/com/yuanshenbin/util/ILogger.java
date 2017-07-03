package com.yuanshenbin.util;


import com.orhanobut.logger.Logger;

/**
 * Created by yuanshenbin on 2017/6/30.
 */

public class ILogger {
    /**
     * General log function that accepts all configurations as parameter
     */
    public static void log(int priority, String tag, String message, Throwable throwable) {
        Logger.log(priority, tag, message, throwable);
    }

    public static void d(String message, Object... args) {
        Logger.d(message, args);
    }

    public static void d(Object object) {
        Logger.d(object);
    }

    public static void e(String message, Object... args) {
        Logger.e(null, message, args);
    }

    public static void e(Throwable throwable, String message, Object... args) {
        Logger.e(throwable, message, args);
    }

    public static void i(String message, Object... args) {
        Logger.i(message, args);
    }

    public static void v(String message, Object... args) {
        Logger.v(message, args);
    }

    public static void w(String message, Object... args) {
        Logger.w(message, args);
    }

    /**
     * Tip: Use this for exceptional situations to log
     * ie: Unexpected errors etc
     */
    public static void wtf(String message, Object... args) {
        Logger.wtf(message, args);
    }

    /**
     * Formats the given json content and print it
     */
    public static void json(String json) {
        Logger.json(json);
    }
    /**
     * Formats the given xml content and print it
     */
    public static void xml(String xml) {
        Logger.xml(xml);
    }
}
