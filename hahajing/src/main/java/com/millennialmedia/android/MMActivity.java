package com.millennialmedia.android;

import android.app.Activity;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.KeyEvent;
import android.view.MotionEvent;
import java.lang.ref.WeakReference;

public class MMActivity extends Activity {
    private static final String TAG = "MMActivity";
    long creatorAdImplInternalId;
    private MMBaseActivity mmBaseActivity;
    GestureDetector tapDetector;

    private static class InterstitialGestureListener extends SimpleOnGestureListener {
        WeakReference<MMActivity> mmActivityRef;

        public boolean onSingleTapConfirmed(MotionEvent e) {
            MMActivity mmActivity = (MMActivity) this.mmActivityRef.get();
            if (mmActivity != null) {
                Event.adSingleTap(MMAdImplController.getAdImplWithId(mmActivity.creatorAdImplInternalId));
            }
            return false;
        }

        public InterstitialGestureListener(MMActivity mmActivity) {
            this.mmActivityRef = new WeakReference(mmActivity);
        }
    }

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        String className = null;
        this.creatorAdImplInternalId = getIntent().getLongExtra("internalId", -4);
        try {
            className = getIntent().getStringExtra("class");
            this.mmBaseActivity = (MMBaseActivity) Class.forName(className).newInstance();
            this.mmBaseActivity.activity = this;
            this.mmBaseActivity.onCreate(savedInstanceState);
            this.tapDetector = new GestureDetector(getApplicationContext(), new InterstitialGestureListener(this));
        } catch (Exception e) {
            MMLog.e(TAG, String.format("Could not start activity for %s. ", new Object[]{className}), e);
            e.printStackTrace();
            finish();
        }
    }

    void onCreateSuper(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    protected void onDestroy() {
        if (this.mmBaseActivity != null) {
            this.mmBaseActivity.onDestroy();
        } else {
            super.onDestroy();
        }
    }

    void onDestroySuper() {
        super.onDestroy();
    }

    protected void onStart() {
        if (this.mmBaseActivity != null) {
            this.mmBaseActivity.onStart();
        } else {
            super.onStart();
        }
    }

    void onStartSuper() {
        super.onStart();
    }

    protected void onStop() {
        if (this.mmBaseActivity != null) {
            this.mmBaseActivity.onStop();
        } else {
            super.onStop();
        }
    }

    void onStopSuper() {
        super.onStop();
    }

    protected void onRestart() {
        if (this.mmBaseActivity != null) {
            this.mmBaseActivity.onRestart();
        } else {
            super.onRestart();
        }
    }

    void onRestartSuper() {
        super.onRestart();
    }

    protected void onResume() {
        if (this.mmBaseActivity != null) {
            this.mmBaseActivity.onResume();
        } else {
            super.onResume();
        }
    }

    void onResumeSuper() {
        super.onResume();
    }

    protected void onPause() {
        if (this.mmBaseActivity != null) {
            this.mmBaseActivity.onPause();
        } else {
            super.onPause();
        }
    }

    void onPauseSuper() {
        super.onPause();
    }

    protected void onSaveInstanceState(Bundle outState) {
        if (this.mmBaseActivity != null) {
            this.mmBaseActivity.onSaveInstanceState(outState);
        } else {
            super.onSaveInstanceState(outState);
        }
    }

    void onSaveInstanceStateSuper(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        if (this.mmBaseActivity != null) {
            this.mmBaseActivity.onRestoreInstanceState(savedInstanceState);
        } else {
            super.onRestoreInstanceState(savedInstanceState);
        }
    }

    void onRestoreInstanceStateSuper(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        if (this.mmBaseActivity != null) {
            this.mmBaseActivity.onWindowFocusChanged(hasFocus);
        } else {
            super.onWindowFocusChanged(hasFocus);
        }
    }

    void onConfigurationChangedSuper(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    public void onConfigurationChanged(Configuration newConfig) {
        if (this.mmBaseActivity != null) {
            this.mmBaseActivity.onConfigurationChanged(newConfig);
        } else {
            super.onConfigurationChanged(newConfig);
        }
    }

    void onWindowFocusChangedSuper(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }

    public Object onRetainNonConfigurationInstance() {
        if (this.mmBaseActivity != null) {
            return this.mmBaseActivity.onRetainNonConfigurationInstance();
        }
        return super.onRetainNonConfigurationInstance();
    }

    Object onRetainNonConfigurationInstanceSuper() {
        return super.onRetainNonConfigurationInstance();
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (this.mmBaseActivity != null) {
            this.mmBaseActivity.onActivityResult(requestCode, resultCode, data);
        } else {
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    void onActivityResultSuper(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (this.mmBaseActivity != null) {
            return this.mmBaseActivity.onKeyDown(keyCode, event);
        }
        return super.onKeyDown(keyCode, event);
    }

    boolean onKeyDownSuper(int keyCode, KeyEvent event) {
        return super.onKeyDown(keyCode, event);
    }

    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (this.tapDetector != null) {
            this.tapDetector.onTouchEvent(ev);
        }
        if (this.mmBaseActivity != null) {
            return this.mmBaseActivity.dispatchTouchEvent(ev);
        }
        return super.dispatchTouchEvent(ev);
    }

    public void finish() {
        if (this.mmBaseActivity != null) {
            this.mmBaseActivity.finish();
            return;
        }
        Event.overlayClosed(MMAdImplController.getAdImplWithId(this.creatorAdImplInternalId));
        super.finish();
    }

    public void finishSuper() {
        Event.overlayClosed(MMAdImplController.getAdImplWithId(this.creatorAdImplInternalId));
        super.finish();
    }

    boolean dispatchTouchEventSuper(MotionEvent ev) {
        return super.dispatchTouchEvent(ev);
    }

    protected MMBaseActivity getWrappedActivity() {
        return this.mmBaseActivity;
    }
}
