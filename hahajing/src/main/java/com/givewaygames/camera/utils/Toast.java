package com.givewaygames.camera.utils;

import android.content.Context;
import android.os.Handler;
import java.lang.ref.WeakReference;

public class Toast {
    public static final int LENGTH_LONG = 1;
    public static final int LENGTH_SHORT = 0;
    private static final Toast toast = new Toast();
    boolean isInitialized = false;
    WeakReference<Context> weakContext;
    WeakReference<Handler> weakHandler;

    public static Toast getInstance() {
        return toast;
    }

    public void initialize(Context context, Handler handler) {
        this.weakContext = new WeakReference(context);
        this.weakHandler = new WeakReference(handler);
        this.isInitialized = true;
    }

    public static void makeText(int id, int length) {
        getInstance().doToast(id, length);
    }

    public void doToast(final int id, final int length) {
        if (this.isInitialized) {
            Handler handler = (Handler) this.weakHandler.get();
            if (handler != null) {
                handler.post(new Runnable() {
                    public void run() {
                        Context context = (Context) Toast.this.weakContext.get();
                        if (context != null) {
                            android.widget.Toast.makeText(context, id, length).show();
                        }
                    }
                });
            }
        }
    }
}
