package com.givewaygames.gwgl.utils;

import com.crashlytics.android.Crashlytics;

public class Log {
    public static boolean LOG_TO_CONSOLE = false;
    public static boolean MASTER_FLAG = true;
    public static boolean USE_CRASHLYTICS_LOGS = true;
    public static boolean isD;
    public static boolean isE;
    public static boolean isI;
    public static boolean isV = (MASTER_FLAG);
    public static boolean isW;

    static {
        boolean z;
        boolean z2 = true;
        if (MASTER_FLAG) {
            z = true;
        } else {
            z = false;
        }
        isD = z;
        if (MASTER_FLAG) {
            z = true;
        } else {
            z = false;
        }
        isI = z;
        if (MASTER_FLAG) {
            z = true;
        } else {
            z = false;
        }
        isW = z;
        if (!MASTER_FLAG) {
            z2 = false;
        }
        isE = z2;
        refreshShortcuts();
    }

    public static void refreshShortcuts() {
    }

    public static void enableV(boolean enV) {
        isV = enV;
        refreshShortcuts();
    }

    public static void enableD(boolean enD) {
        isD = enD;
        refreshShortcuts();
    }

    public static void enableI(boolean enI) {
        isI = enI;
        refreshShortcuts();
    }

    public static void enableW(boolean enW) {
        isW = enW;
        refreshShortcuts();
    }

    public static void enableE(boolean enE) {
        isE = enE;
        refreshShortcuts();
    }

    public static void v(String tag, String msg) {
        if (!isV) {
            return;
        }
        if (USE_CRASHLYTICS_LOGS && LOG_TO_CONSOLE) {
            Crashlytics.log(2, tag, msg);
        } else if (USE_CRASHLYTICS_LOGS) {
            Crashlytics.log(tag + "/" + msg);
        } else if (LOG_TO_CONSOLE) {
            android.util.Log.v(tag, msg);
        }
    }

    public static void v(String tag, String msg, Throwable t) {
        if (!isV) {
            return;
        }
        if (USE_CRASHLYTICS_LOGS && LOG_TO_CONSOLE) {
            Crashlytics.log(2, tag, msg);
        } else if (USE_CRASHLYTICS_LOGS) {
            Crashlytics.log(tag + "/" + msg + (t != null ? t.getMessage() : ""));
        } else if (LOG_TO_CONSOLE) {
            android.util.Log.v(tag, msg, t);
        }
    }

    public static void d(String tag, String msg) {
        if (!isD) {
            return;
        }
        if (USE_CRASHLYTICS_LOGS && LOG_TO_CONSOLE) {
            Crashlytics.log(3, tag, msg);
        } else if (USE_CRASHLYTICS_LOGS) {
            Crashlytics.log(tag + "/" + msg);
        } else if (LOG_TO_CONSOLE) {
            android.util.Log.d(tag, msg);
        }
    }

    public static void d(String tag, String msg, Throwable t) {
        if (!isD) {
            return;
        }
        if (USE_CRASHLYTICS_LOGS && LOG_TO_CONSOLE) {
            Crashlytics.log(3, tag, msg);
        } else if (USE_CRASHLYTICS_LOGS) {
            Crashlytics.log(tag + "/" + msg + (t != null ? t.getMessage() : ""));
        } else if (LOG_TO_CONSOLE) {
            android.util.Log.d(tag, msg, t);
        }
    }

    public static void i(String tag, String msg) {
        if (!isI) {
            return;
        }
        if (USE_CRASHLYTICS_LOGS && LOG_TO_CONSOLE) {
            Crashlytics.log(4, tag, msg);
        } else if (USE_CRASHLYTICS_LOGS) {
            Crashlytics.log(tag + "/" + msg);
        } else if (LOG_TO_CONSOLE) {
            android.util.Log.i(tag, msg);
        }
    }

    public static void i(String tag, String msg, Throwable t) {
        if (!isI) {
            return;
        }
        if (USE_CRASHLYTICS_LOGS && LOG_TO_CONSOLE) {
            Crashlytics.log(4, tag, msg);
        } else if (USE_CRASHLYTICS_LOGS) {
            Crashlytics.log(tag + "/" + msg + (t != null ? t.getMessage() : ""));
        } else if (LOG_TO_CONSOLE) {
            android.util.Log.i(tag, msg, t);
        }
    }

    public static void w(String tag, String msg) {
        if (!isW) {
            return;
        }
        if (USE_CRASHLYTICS_LOGS && LOG_TO_CONSOLE) {
            Crashlytics.log(5, tag, msg);
        } else if (USE_CRASHLYTICS_LOGS) {
            Crashlytics.log(tag + "/" + msg);
        } else if (LOG_TO_CONSOLE) {
            android.util.Log.w(tag, msg);
        }
    }

    public static void w(String tag, String msg, Throwable t) {
        if (!isW) {
            return;
        }
        if (USE_CRASHLYTICS_LOGS && LOG_TO_CONSOLE) {
            Crashlytics.log(5, tag, msg);
        } else if (USE_CRASHLYTICS_LOGS) {
            Crashlytics.log(tag + "/" + msg + (t != null ? t.getMessage() : ""));
        } else if (LOG_TO_CONSOLE) {
            android.util.Log.w(tag, msg, t);
        }
    }

    public static void e(String tag, String msg) {
        if (!isE) {
            return;
        }
        if (USE_CRASHLYTICS_LOGS && LOG_TO_CONSOLE) {
            Crashlytics.log(6, tag, msg);
        } else if (USE_CRASHLYTICS_LOGS) {
            Crashlytics.log(tag + "/" + msg);
        } else if (LOG_TO_CONSOLE) {
            android.util.Log.e(tag, msg);
        }
    }

    public static void e(String tag, String msg, Throwable t) {
        if (!isE) {
            return;
        }
        if (USE_CRASHLYTICS_LOGS && LOG_TO_CONSOLE) {
            Crashlytics.log(6, tag, msg);
        } else if (USE_CRASHLYTICS_LOGS) {
            Crashlytics.log(tag + "/" + msg + (t != null ? t.getMessage() : ""));
        } else if (LOG_TO_CONSOLE) {
            android.util.Log.e(tag, msg, t);
        }
    }
}
