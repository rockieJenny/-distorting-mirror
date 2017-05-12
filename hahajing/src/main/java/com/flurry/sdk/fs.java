package com.flurry.sdk;

import android.app.Activity;

public class fs extends fx {
    public Activity a;
    public a b;

    public enum a {
        kCreated,
        kDestroyed,
        kPaused,
        kResumed,
        kStarted,
        kStopped,
        kSaveState
    }

    public fs() {
        super("com.flurry.android.sdk.ActivityLifecycleEvent");
    }
}
