package com.givewaygames.camera.state;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build.VERSION;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import com.givewaygames.camera.events.FilterChangedEvent;
import com.givewaygames.goofyglass.R;
import com.givewaygames.gwgl.CameraWrapper;
import com.givewaygames.gwgl.utils.GLHelper;
import com.squareup.otto.Bus;
import com.squareup.otto.ThreadEnforcer;
import java.lang.ref.WeakReference;

public class AppState {
    private static final AppState instance = new AppState();
    Bus bus = new Bus(ThreadEnforcer.ANY);
    CameraWrapper cameraWrapper = new CameraWrapper();
    int currentFilter = 0;
    boolean forceAdsDisabled = false;
    GLHelper glHelper;
    WeakReference<Handler> handler;
    boolean hasCamera = true;
    boolean hasSeen = true;
    boolean isAdsEnabled = false;
    boolean isLoading = true;
    int lastFilter = 0;
    Uri lastImageUri;
    Mode mode = Mode.CAMERA;
    UiState uiState = new UiState();
    boolean validCamera = true;

    public enum Mode {
        CAMERA,
        FAMOUS,
        IMPORTED
    }

    public static class UiState {
        boolean isMenuShowing = false;
        boolean isModeDropdownShowing = false;
        boolean isUnlockShowing = false;
        int orientation;

        public void setMenuShowing(boolean isMenuShowing) {
            this.isMenuShowing = isMenuShowing;
        }

        public void setModeDropdownShowing(boolean isModeDropdownShowing) {
            this.isModeDropdownShowing = isModeDropdownShowing;
        }

        public void setUnlockShowing(boolean isUnlockShowing) {
            this.isUnlockShowing = isUnlockShowing;
        }

        public void refreshConfiguration(Activity activity) {
            this.orientation = getScreenOrientation(activity);
        }

        public boolean isBufferManagedCamera() {
            return VERSION.SDK_INT < 11;
        }

        public boolean isMenuShowing() {
            return this.isMenuShowing;
        }

        public boolean isModeDropdownShowing() {
            return this.isModeDropdownShowing;
        }

        public boolean isUnlockShowing() {
            return this.isUnlockShowing;
        }

        public boolean isPortrait() {
            return this.orientation == 1 || this.orientation == 9;
        }

        public boolean isLandscape() {
            return this.orientation == 0 || this.orientation == 8;
        }

        public boolean isLandscapeLeft() {
            return this.orientation == 8;
        }

        public boolean isLandscapeRight() {
            return this.orientation == 0;
        }

        private int getScreenOrientation(Activity activity) {
            boolean isLandscape = activity.getResources().getBoolean(R.bool.resources_landscape);
            WindowManager windowManager = activity.getWindowManager();
            int rotation = windowManager.getDefaultDisplay().getRotation();
            windowManager.getDefaultDisplay().getMetrics(new DisplayMetrics());
            if (!isLandscape) {
                switch (rotation) {
                    case 1:
                    case 2:
                        this.orientation = 9;
                        break;
                    default:
                        this.orientation = 1;
                        break;
                }
            }
            switch (rotation) {
                case 2:
                case 3:
                    this.orientation = 8;
                    break;
                default:
                    this.orientation = 0;
                    break;
            }
            return this.orientation;
        }
    }

    public static AppState getInstance() {
        return instance;
    }

    public UiState getUiState() {
        return this.uiState;
    }

    public Bus getBus() {
        return this.bus;
    }

    public void setForceAdsDisabled(boolean forceAdsDisabled) {
        this.forceAdsDisabled = forceAdsDisabled;
    }

    public void setIsLoading(boolean isLoading) {
        this.isLoading = isLoading;
    }

    public void setHandler(Handler handler) {
        this.handler = new WeakReference(handler);
    }

    public boolean isLoading() {
        return this.isLoading;
    }

    public Handler getHandler() {
        return (Handler) this.handler.get();
    }

    public CameraWrapper getCameraWrapper() {
        return this.cameraWrapper;
    }

    public GLHelper getGLHelper() {
        return this.glHelper;
    }

    public void setGLHelper(GLHelper glHelper) {
        this.glHelper = glHelper;
    }

    public boolean canStencil() {
        return getGLHelper().getCanStencil();
    }

    public boolean isModeCamera() {
        return this.mode == Mode.CAMERA;
    }

    public boolean isModeFamous() {
        return this.mode == Mode.FAMOUS;
    }

    public boolean isModeImported() {
        return this.mode == Mode.IMPORTED;
    }

    public Mode getMode() {
        return this.mode;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
    }

    public Uri getLastImageUri() {
        return this.lastImageUri;
    }

    public void setLastImageUri(Uri uri) {
        this.lastImageUri = uri;
    }

    public void setAdsEnabled(boolean adsEnabled) {
        if (this.forceAdsDisabled) {
            adsEnabled = false;
        }
        this.isAdsEnabled = adsEnabled;
    }

    public int getCurrentFilter() {
        return this.currentFilter;
    }

    public void setCurrentFilter(int currentFilter) {
        this.lastFilter = this.currentFilter;
        this.currentFilter = currentFilter;
        this.bus.post(new FilterChangedEvent(currentFilter));
    }

    public int getLastFilter() {
        return this.lastFilter;
    }

    public boolean isAdsEnabled() {
        return this.isAdsEnabled;
    }

    public void initHasCamera(PackageManager pm) {
        boolean z = pm.hasSystemFeature("android.hardware.camera") || pm.hasSystemFeature("android.hardware.camera.front");
        this.hasCamera = z;
    }

    public boolean hasCamera() {
        return this.hasCamera;
    }

    public void setValidCamera(boolean isValid) {
        this.validCamera = isValid;
    }

    public boolean getValidCamera() {
        return this.validCamera;
    }
}
