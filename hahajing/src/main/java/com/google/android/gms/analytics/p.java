package com.google.android.gms.analytics;

import android.util.Log;

class p implements Logger {
    private int yM = 2;

    p() {
    }

    private String af(String str) {
        return Thread.currentThread().toString() + ": " + str;
    }

    public void error(Exception exception) {
        if (this.yM <= 3) {
            Log.e("GAV4", null, exception);
        }
    }

    public void error(String msg) {
        if (this.yM <= 3) {
            Log.e("GAV4", af(msg));
        }
    }

    public int getLogLevel() {
        return this.yM;
    }

    public void info(String msg) {
        if (this.yM <= 1) {
            Log.i("GAV4", af(msg));
        }
    }

    public void setLogLevel(int level) {
        this.yM = level;
    }

    public void verbose(String msg) {
        if (this.yM <= 0) {
            Log.v("GAV4", af(msg));
        }
    }

    public void warn(String msg) {
        if (this.yM <= 2) {
            Log.w("GAV4", af(msg));
        }
    }
}
