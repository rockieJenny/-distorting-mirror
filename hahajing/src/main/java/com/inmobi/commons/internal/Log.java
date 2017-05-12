package com.inmobi.commons.internal;

import com.inmobi.commons.thinICE.icedatacollector.BuildSettings;

public class Log {
    private static INTERNAL_LOG_LEVEL a = INTERNAL_LOG_LEVEL.NOT_SET;

    public enum INTERNAL_LOG_LEVEL {
        NOT_SET(-1),
        NONE(0),
        DEBUG(1),
        VERBOSE(2),
        INTERNAL(3);
        
        private final int a;

        private INTERNAL_LOG_LEVEL(int i) {
            this.a = i;
        }

        public int getValue() {
            return this.a;
        }
    }

    public static INTERNAL_LOG_LEVEL getLogLevel() {
        return a;
    }

    public static INTERNAL_LOG_LEVEL getLogLevelValue(long j) {
        if (j == 2) {
            return INTERNAL_LOG_LEVEL.INTERNAL;
        }
        if (j == 1) {
            return INTERNAL_LOG_LEVEL.DEBUG;
        }
        return INTERNAL_LOG_LEVEL.NONE;
    }

    public static void setInternalLogLevel(INTERNAL_LOG_LEVEL internal_log_level) {
        a = internal_log_level;
        if (a == INTERNAL_LOG_LEVEL.INTERNAL) {
            BuildSettings.DEBUG = true;
        }
    }

    public static void debug(String str, String str2) {
        if (a.getValue() >= INTERNAL_LOG_LEVEL.DEBUG.getValue() || (a == INTERNAL_LOG_LEVEL.NOT_SET && CommonsConfig.getLogLevelConfig() >= INTERNAL_LOG_LEVEL.DEBUG.getValue())) {
            android.util.Log.d(str, str2);
        }
    }

    public static void debug(String str, String str2, Throwable th) {
        switch (a) {
            case DEBUG:
                debug(str, str2);
                return;
            case INTERNAL:
                internal(str, str2, th);
                return;
            default:
                return;
        }
    }

    public static void verbose(String str, String str2) {
        if (a.getValue() >= INTERNAL_LOG_LEVEL.VERBOSE.getValue() || (a == INTERNAL_LOG_LEVEL.NOT_SET && CommonsConfig.getLogLevelConfig() >= INTERNAL_LOG_LEVEL.VERBOSE.getValue())) {
            android.util.Log.i(str, str2);
        }
    }

    public static void verbose(String str, String str2, Throwable th) {
        switch (a) {
            case DEBUG:
            case VERBOSE:
                verbose(str, str2);
                return;
            case INTERNAL:
                internal(str, str2, th);
                return;
            default:
                return;
        }
    }

    public static void internal(String str, String str2) {
        if (a.getValue() >= INTERNAL_LOG_LEVEL.INTERNAL.getValue() || (a == INTERNAL_LOG_LEVEL.NOT_SET && CommonsConfig.getLogLevelConfig() >= INTERNAL_LOG_LEVEL.INTERNAL.getValue())) {
            StackTraceElement stackTraceElement = new Throwable().getStackTrace()[1];
            android.util.Log.v(str, stackTraceElement.getFileName() + ": " + stackTraceElement.getMethodName() + " " + str2);
        }
    }

    public static void internal(String str, String str2, Throwable th) {
        if (a.getValue() >= INTERNAL_LOG_LEVEL.INTERNAL.getValue() || (a == INTERNAL_LOG_LEVEL.NOT_SET && CommonsConfig.getLogLevelConfig() >= INTERNAL_LOG_LEVEL.INTERNAL.getValue())) {
            StackTraceElement stackTraceElement = new Throwable().getStackTrace()[1];
            android.util.Log.e(str, stackTraceElement.getFileName() + ": " + stackTraceElement.getMethodName() + " " + str2, th);
        }
    }
}
