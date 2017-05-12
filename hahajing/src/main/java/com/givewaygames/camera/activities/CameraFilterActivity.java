package com.givewaygames.camera.activities;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Message;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.util.Pair;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager.LayoutParams;
import butterknife.OnClick;
import com.android.camera.ui.SharePopup;
import com.appflood.AppFlood;
import com.crashlytics.android.Crashlytics;
import com.flurry.android.FlurryAgent;
import com.givewaygames.ads.GAUtils;
import com.givewaygames.ads.HouseAds;
import com.givewaygames.ads.InterstitialHelper;
import com.givewaygames.ads.InterstitialHelper.SafeToShow;
import com.givewaygames.ads.SubscribeManager;
import com.givewaygames.camera.billing.BillingWrapperV3;
import com.givewaygames.camera.fragments.BaseDialogFragment;
import com.givewaygames.camera.graphics.GoofyScene;
import com.givewaygames.camera.state.AppState;
import com.givewaygames.camera.state.AppState.Mode;
import com.givewaygames.camera.state.AppState.UiState;
import com.givewaygames.camera.ui.BaseFragment;
import com.givewaygames.camera.ui.FragmentAlertDialog.Builder;
import com.givewaygames.camera.ui.MoreInfoFragment;
import com.givewaygames.camera.ui.ViewHolder;
import com.givewaygames.camera.utils.AnalyticsHelper;
import com.givewaygames.camera.utils.Constants;
import com.givewaygames.camera.utils.MultiplePhotoTaker.ProgramMultiplePhotoTaker;
import com.givewaygames.camera.utils.PauseHandler;
import com.givewaygames.camera.utils.Toast;
import com.givewaygames.camera.utils.Utils;
import com.givewaygames.goofyglass.R;
import com.givewaygames.gwgl.CameraWrapper;
import com.givewaygames.gwgl.CameraWrapper.OnCameraInfoReceived;
import com.givewaygames.gwgl.CameraWrapper.OnCrashlytics;
import com.givewaygames.gwgl.CameraWrapper.OnInvalidCamera;
import com.givewaygames.gwgl.CameraWrapper.SafeCameraInfo;
import com.givewaygames.gwgl.LibState;
import com.givewaygames.gwgl.events.OpenGLErrorEvent;
import com.givewaygames.gwgl.utils.GLHelper;
import com.givewaygames.gwgl.utils.Log;
import com.givewaygames.gwgl.utils.gl.GLProgram;
import com.givewaygames.gwgl.utils.gl.GLTakePhoto;
import com.givewaygames.gwgl.utils.gl.GLTakeScreenshot;
import com.givewaygames.gwgl.utils.gl.GLTakeScreenshot.ScreenshotException;
import com.inmobi.commons.InMobi;
import com.jirbo.adcolony.AdColony;
import com.millennialmedia.android.MMSDK;
import com.squareup.otto.Subscribe;
import io.fabric.sdk.android.Fabric;
import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutionException;

public class CameraFilterActivity extends FragmentActivity implements OnTouchListener, OnInvalidCamera, OnCameraInfoReceived, SafeToShow {
    public static final int ACTIVITY_BILLING = 2;
    public static final int ACTIVITY_SELECT_IMAGE = 0;
    public static final int ACTIVITY_SELECT_IMAGE_KITKAT = 1;
    private static final String BETA = "BJANERJL";
    private static final String FIRST = "AIEARVJA";
    private static final boolean FULL_PAGE_ADS_ENABLED = true;
    public static final int NUM_SHADERS = 46;
    public static final int START_SHADER = 0;
    private static final String TAG = "CameraFilterActivity";
    BillingWrapperV3 billingWrapper;
    GingerbreadSafeBusListener busListener = new GingerbreadSafeBusListener();
    public Set<Fragment> dialogFragments = new HashSet();
    GoofyScene goofyScene = new GoofyScene(this);
    PauseHandler handler = new PauseHandler() {
        public void processMessage(Message msg) {
            boolean isSuccess = true;
            switch (msg.what) {
                case 0:
                    if (CameraFilterActivity.this.isGLLoaded) {
                        CameraFilterActivity.this.onImageSelected((Uri) msg.obj);
                        return;
                    } else {
                        CameraFilterActivity.this.onImageSelectedWait((Uri) msg.obj);
                        return;
                    }
                case 1:
                    if (!CameraFilterActivity.this.isFinishing()) {
                        Toast.makeText(msg.arg1, msg.arg2);
                        return;
                    }
                    return;
                case 2:
                    CameraFilterActivity.this.ui.refreshFilterAdapter(CameraFilterActivity.this, CameraFilterActivity.this.goofyScene);
                    return;
                case 4:
                    if (msg.arg1 != 1) {
                        isSuccess = false;
                    }
                    Pair<String, GLTakePhoto> pair = msg.obj;
                    boolean tryAgain = ((GLTakePhoto) pair.second).tryAgain();
                    GLTakePhoto gLTakePhoto = (GLTakePhoto) pair.second;
                    CameraFilterActivity.this.onPhotoTaken(isSuccess, (String) pair.first, GLTakeScreenshot.getFailureException(), tryAgain);
                    return;
                case 5:
                    CameraFilterActivity.this.onPhotoProgressUpdate(msg.arg1);
                    return;
                default:
                    super.handleMessage(msg);
                    return;
            }
        }
    };
    boolean hasShownOnce = false;
    InterstitialHelper interstitialHelper;
    boolean isAmazon = false;
    boolean isBeta = false;
    boolean isFirstRun;
    boolean isGLLoaded = false;
    boolean isRunning = false;
    Throwable lastOnRenderFailedError = null;
    String lastOnRenderFailedMessage = null;
    long lastProgressTime = 0;
    ProgramMultiplePhotoTaker multiplePhotoTaker = new ProgramMultiplePhotoTaker();
    long onCreateAt = 0;
    long onResumeAt = 0;
    OpenGLAsyncTask openGLAsyncTask;
    Runnable removeLoadingBarRunnable = new Runnable() {
        public void run() {
            CameraFilterActivity.this.ui.hideLoadingFrame();
            CameraFilterActivity.this.getState().setIsLoading(false);
            if (CameraFilterActivity.this.getAnalytics().isEnabled()) {
                long before = CameraFilterActivity.this.trackingFullOnCreate ? CameraFilterActivity.this.onCreateAt : CameraFilterActivity.this.onResumeAt;
                GAUtils.sendTiming(CameraFilterActivity.this.getAnalytics().getTracker(), CameraFilterActivity.this.trackingFullOnCreate ? "onCreate" : "onResume", Long.valueOf(System.currentTimeMillis() - before), "", "");
            }
        }
    };
    SharePopup sharePopup;
    SwitchFFCAsyncTask switchFFCAsyncTask;
    boolean trackingFullOnCreate = false;
    ViewHolder ui = new ViewHolder();

    public class GingerbreadSafeBusListener {
        @Subscribe
        public void onOpenGLError(OpenGLErrorEvent event) {
            CameraFilterActivity.this.onRenderingFailed(CameraFilterActivity.this.getResources().getString(R.string.opengl_critical_error_title), CameraFilterActivity.this.getResources().getString(R.string.opengl_critical_error), event.lastError, event.errorString);
        }
    }

    private class OpenGLAsyncTask extends AsyncTask<Void, Integer, Void> {
        boolean isAlive;

        private OpenGLAsyncTask() {
            this.isAlive = true;
        }

        protected void kill() {
            this.isAlive = false;
        }

        public boolean isAlive() {
            return this.isAlive;
        }

        protected void onPreExecute() {
            super.onPreExecute();
            CameraFilterActivity.this.getState().setIsLoading(true);
            CameraFilterActivity.this.ui.showLoadingFrame();
        }

        protected Void doInBackground(Void... params) {
            HouseAds.getInstance(CameraFilterActivity.this, CameraFilterActivity.this.getAnalytics().getTracker(), CameraFilterActivity.this.isAmazon);
            if (progressCheck(1)) {
                initSceneIfNeeded();
                if (progressCheck(2)) {
                    waitForCameraInit();
                    if (progressCheck(3)) {
                        CameraFilterActivity.this.getCameraWrapper().onResume(CameraFilterActivity.this);
                        if (progressCheck(4)) {
                            CameraFilterActivity.this.goofyScene.refreshCurrentFilter(CameraFilterActivity.this);
                            if (progressCheck(5)) {
                                waitForSurfaceView();
                                waitForCameraData();
                                publishProgress(new Integer[]{Integer.valueOf(6)});
                            }
                        }
                    }
                }
            }
            return null;
        }

        private void waitForCameraInit() {
            while (this.isAlive && !CameraFilterActivity.this.goofyScene.getGlCamera().isInitialized()) {
                try {
                    Thread.sleep(3);
                } catch (InterruptedException e) {
                }
            }
        }

        private void waitForCameraData() {
            while (this.isAlive && CameraFilterActivity.this.getCameraWrapper().getCamera() != null && !CameraFilterActivity.this.goofyScene.getGlCamera().hasSeenDataFromCamera()) {
                try {
                    Thread.sleep(3);
                } catch (InterruptedException e) {
                }
            }
        }

        private void waitForSurfaceView() {
            while (this.isAlive && CameraFilterActivity.this.ui.getGLSurfaceView() != null && !CameraFilterActivity.this.ui.getGLSurfaceView().isReadyToRock()) {
                try {
                    Thread.sleep(3);
                } catch (InterruptedException e) {
                }
            }
        }

        private void initSceneIfNeeded() {
            boolean firstTimeInit = !CameraFilterActivity.this.isGLLoaded;
            GLHelper glHelper = CameraFilterActivity.this.getGLHelper();
            if (firstTimeInit) {
                glHelper.setGLScene(CameraFilterActivity.this.goofyScene.initializeScene(CameraFilterActivity.this, CameraFilterActivity.this.ui.getFauxSurface()));
                CameraFilterActivity.this.handler.sendEmptyMessage(2);
            } else {
                glHelper.setGLScene(CameraFilterActivity.this.goofyScene.getScene());
            }
            CameraFilterActivity.this.isGLLoaded = true;
        }

        private boolean progressCheck(int progress) {
            publishProgress(new Integer[]{Integer.valueOf(progress)});
            if (this.isAlive) {
                return true;
            }
            if (!Log.isI) {
                return false;
            }
            Log.i(CameraFilterActivity.TAG, "OpenGLAsyncTask early out !isAlive");
            return false;
        }

        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            if (values != null && values.length > 0) {
                CameraFilterActivity.this.ui.refreshLoadingProgress(values[0].intValue());
            }
        }

        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            if (CameraFilterActivity.this.getState().getMode() == Mode.CAMERA) {
                boolean hasCamera = CameraFilterActivity.this.getState().hasCamera();
                boolean validCamera = CameraFilterActivity.this.getState().getValidCamera();
                if (!(hasCamera && validCamera)) {
                    CameraFilterActivity.this.changeMode(Mode.FAMOUS);
                }
            }
            CameraFilterActivity.this.removeLoadingBarRunnable.run();
            this.isAlive = false;
        }
    }

    private class RebootCameraAsyncTask extends AsyncTask<Void, Void, Void> {
        private RebootCameraAsyncTask() {
        }

        protected Void doInBackground(Void... params) {
            CameraFilterActivity.this.getCameraWrapper().rebootCamera(CameraFilterActivity.this);
            return null;
        }
    }

    private class SwitchFFCAsyncTask extends AsyncTask<Void, Void, Void> {
        private SwitchFFCAsyncTask() {
        }

        protected Void doInBackground(Void... params) {
            int i = -1;
            while (true) {
                i++;
                if (i >= 3 || !CameraFilterActivity.this.goofyScene.isTakingPhoto()) {
                    CameraFilterActivity.this.getCameraWrapper().switchFFC(CameraFilterActivity.this);
                } else {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                    }
                }
            }
            CameraFilterActivity.this.getCameraWrapper().switchFFC(CameraFilterActivity.this);
            return null;
        }

        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            CameraFilterActivity.this.ui.hidePhotoThumbnail(0);
            CameraFilterActivity.this.switchFFCAsyncTask = null;
        }
    }

    public CameraWrapper getCameraWrapper() {
        return getState().getCameraWrapper();
    }

    public GLHelper getGLHelper() {
        return getState().getGLHelper();
    }

    public AnalyticsHelper getAnalytics() {
        return AnalyticsHelper.getInstance();
    }

    public void onCreate(Bundle savedInstanceState) {
        Uri uri = null;
        super.onCreate(savedInstanceState);
        Fabric.with(this, new Crashlytics());
        Log.enableV(false);
        Log.USE_CRASHLYTICS_LOGS = true;
        Log.LOG_TO_CONSOLE = false;
        if (CameraWrapper.DEBUG) {
            Log.LOG_TO_CONSOLE = true;
            Log.enableV(true);
        }
        if (CameraWrapper.DEBUG) {
            Utils.logHeap(TAG, "onCreate before: ");
        }
        if (Log.isD) {
            Log.d(TAG, "onCreate");
        }
        Crashlytics.setInt("initial_progress", 0);
        Utils.setMemForCrashlytics(1);
        Crashlytics.setInt("memory_class", ((ActivityManager) getSystemService("activity")).getMemoryClass());
        CameraWrapper.onCrashlyticsLog = new OnCrashlytics() {
            public void logString(String key, String value) {
                if (key != null) {
                    Crashlytics.setString(key, value);
                } else {
                    Crashlytics.log(value);
                }
            }

            public void logException(Exception e) {
                Crashlytics.logException(e);
            }
        };
        AppFlood.initialize(this, Constants.APP_FLOOD_APP_KEY, Constants.APP_FLOOD_SECRET_KEY, 255);
        AdColony.configure(this, "version:1.0,store:google", Constants.AD_COLONY_APP_KEY, Constants.AD_COLONY_ZONE);
        MMSDK.initialize(this);
        FlurryAgent.init(this, "BW6CW5MQNSF4QY7Z4KGK");
        InMobi.initialize((Activity) this, "f75caed03ef6417aba28260cefbf9799");
        this.onCreateAt = System.currentTimeMillis();
        this.trackingFullOnCreate = true;
        this.isAmazon = getResources().getBoolean(R.bool.amazon);
        this.ui.onCreate(this.isAmazon);
        Window window = getWindow();
        if (window != null) {
            window.setFlags(1024, 1024);
        }
        getState().setHandler(this.handler);
        this.billingWrapper = new BillingWrapperV3();
        this.billingWrapper.onCreate(this);
        this.interstitialHelper = new InterstitialHelper(this, this, false, "ca-app-pub-6981944082913039/1740191700");
        AnalyticsHelper.getInstance().init(this);
        this.busListener = new GingerbreadSafeBusListener();
        LibState.getInstance().getBus().register(this.busListener);
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
        this.isBeta = prefs.getBoolean(BETA, false);
        if (!this.isBeta && getResources().getBoolean(R.bool.beta)) {
            this.isBeta = true;
            prefs.edit().putBoolean(BETA, true).apply();
        }
        prefs = PreferenceManager.getDefaultSharedPreferences(this);
        this.isFirstRun = prefs.getBoolean(FIRST, true);
        prefs.edit().putBoolean(FIRST, false).apply();
        Crashlytics.setBool("is_first_run", this.isFirstRun);
        String uriStr = prefs.getString(Constants.PREF_LAST_IMAGE_URI, null);
        AppState state = getState();
        if (uriStr != null) {
            uri = Uri.parse(uriStr);
        }
        state.setLastImageUri(uri);
        getState().initHasCamera(getPackageManager());
        getCameraWrapper().setUseFrontFacingCamera(true);
        getCameraWrapper().onCreate(this);
        setContentView(R.layout.main);
        this.ui.refreshDynamicContent(this);
        this.ui.refreshStaticContent(this);
        if (this.isAmazon) {
            setBillingSupported(false, false);
        }
        Crashlytics.setInt("initial_progress", 1);
        Utils.setMemForCrashlytics(3);
        setRotationAnimation();
        this.isGLLoaded = false;
        if (CameraWrapper.DEBUG) {
            Utils.logHeap(TAG, "onCreate after: ");
        }
    }

    private void setRotationAnimation() {
        if (VERSION.SDK_INT >= 18) {
            Window win = getWindow();
            LayoutParams winParams = win.getAttributes();
            winParams.rotationAnimation = 1;
            win.setAttributes(winParams);
        }
    }

    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        this.ui.onConfigurationChanged(this);
        this.handler.post(new Runnable() {
            public void run() {
                CameraFilterActivity.this.swapFrag();
            }
        });
        getCameraWrapper().setOrientation(this);
        this.goofyScene.updateViewOrientations();
    }

    private void swapFrag() {
        if (this.sharePopup != null && this.isRunning) {
            Drawable oldThumbnail = this.sharePopup.getThumbnail();
            this.sharePopup.disableAnimations();
            this.sharePopup = SharePopup.newInstance(this.sharePopup.getUri());
            this.sharePopup.setThumbnailOnCreate(oldThumbnail);
            getSupportFragmentManager().popBackStack();
            getSupportFragmentManager().beginTransaction().setCustomAnimations(0, R.anim.frag_out, 0, R.anim.frag_pop_out).replace(R.id.fragment_dialog_frame, this.sharePopup, Constants.FRAG_SHARE_FRAGMENT).addToBackStack(null).commit();
        }
    }

    protected void onSaveInstanceState(Bundle outState) {
        outState.putString("WORKAROUND_FOR_BUG_19917_KEY", "WORKAROUND_FOR_BUG_19917_VALUE");
        super.onSaveInstanceState(outState);
    }

    public BillingWrapperV3 getBillingWrapper() {
        return this.billingWrapper;
    }

    public boolean isSafeToShowAd() {
        return !this.goofyScene.isTakingPhoto() && this.isRunning;
    }

    private AppState getState() {
        return AppState.getInstance();
    }

    public void setBillingSupported(boolean isUnlocked, boolean canUnlock) {
        boolean z;
        boolean z2 = true;
        if (Log.isD) {
            Log.d(TAG, "setBillingSupported(" + isUnlocked + ", " + canUnlock + ")");
        }
        AppState state = getState();
        if (isUnlocked) {
            z = false;
        } else {
            z = true;
        }
        state.setAdsEnabled(z);
        UiState uiState = getState().getUiState();
        if (isUnlocked || !canUnlock) {
            z2 = false;
        }
        uiState.setUnlockShowing(z2);
        this.ui.refreshAds(this);
    }

    public void onLowMemory() {
        super.onLowMemory();
        if (Log.isD) {
            Log.d(TAG, "onLowMemory");
        }
        Crashlytics.setBool("low_memory", true);
    }

    @TargetApi(14)
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        Crashlytics.setInt("trim_memory", level);
    }

    public void closeDialogFragment(Fragment frag) {
        this.dialogFragments.remove(frag);
        if (frag == this.sharePopup) {
            this.sharePopup = null;
        }
        if (this.dialogFragments.size() == 0) {
            this.ui.setPaused(false);
        }
    }

    public void onBackPressed() {
        if (getState().getUiState().isMenuShowing() && this.dialogFragments.size() == 0) {
            onMenuClicked();
            return;
        }
        boolean doBack = true;
        Fragment frag = Utils.getActiveFragment(getSupportFragmentManager());
        if (frag instanceof BaseFragment) {
            doBack = ((BaseFragment) frag).closeOnOutsideClick();
        } else if (frag instanceof BaseDialogFragment) {
            doBack = ((BaseDialogFragment) frag).closeOnOutsideClick();
        }
        if (doBack) {
            super.onBackPressed();
        }
    }

    @OnClick({2131492955})
    public void onRandomClicked() {
        if (canHandleInputAndNotifyOfInteraction()) {
            this.goofyScene.pickRandomFilter(this);
        }
    }

    @OnClick({2131492953})
    public void onTakePhotoClicked() {
        if (canHandleInputAndNotifyOfInteraction() && this.goofyScene.takePhoto(this)) {
            this.ui.onTakingPhoto();
        }
    }

    @OnClick({2131492954})
    public void onMenuClicked() {
        if (canHandleInputAndNotifyOfInteraction()) {
            this.ui.toggleMenu(this);
        }
    }

    @OnClick({2131492944})
    public void onImportClicked() {
        if (canHandleInputAndNotifyOfInteraction()) {
            Intent intent;
            int message;
            boolean exists;
            if (VERSION.SDK_INT < 19) {
                intent = new Intent();
                intent.setType("image/*");
                intent.setAction("android.intent.action.GET_CONTENT");
                intent = Intent.createChooser(intent, getResources().getString(R.string.select_picture));
                message = 0;
            } else {
                intent = new Intent("android.intent.action.OPEN_DOCUMENT");
                intent.addCategory("android.intent.category.OPENABLE");
                intent.setType("image/*");
                message = 1;
            }
            if (getPackageManager().queryIntentActivities(intent, 65536).size() > 0) {
                exists = true;
            } else {
                exists = false;
            }
            if (exists) {
                startActivityForResult(intent, message);
            } else {
                Toast.makeText(R.string.gallery_not_found, 0);
            }
            getAnalytics().trackUiEvent("Mode", "Import", 0);
        }
    }

    @OnClick({2131492942})
    public void onCameraClicked() {
        if (canHandleInputAndNotifyOfInteraction()) {
            changeMode(Mode.CAMERA);
            getAnalytics().trackUiEvent("Mode", "Camera", 0);
            this.ui.hideModeDropdown();
        }
    }

    @OnClick({2131492943})
    public void onFamousClicked() {
        if (canHandleInputAndNotifyOfInteraction()) {
            changeMode(Mode.FAMOUS);
            getAnalytics().trackUiEvent("Mode", "Famous", 0);
            this.ui.hideModeDropdown();
        }
    }

    @OnClick({2131492956})
    public void onViewTakenPhotoClicked() {
        if (canHandleInputAndNotifyOfInteraction()) {
            showLastPhotoFragment();
        }
    }

    @OnClick({2131492937})
    public void onMoreInfoClicked() {
        if (canHandleInputAndNotifyOfInteraction()) {
            showInfoFragment();
        }
    }

    @OnClick({2131492938})
    public void onViewTakenPhoto() {
        if (canHandleInputAndNotifyOfInteraction()) {
            this.ui.setPaused(true);
            this.billingWrapper.unlockButtonPressed(this);
        }
    }

    @OnClick({2131492940})
    public void onSwitchCameraClicked() {
        if (canHandleInputAndNotifyOfInteraction() && this.switchFFCAsyncTask == null) {
            this.switchFFCAsyncTask = new SwitchFFCAsyncTask();
            this.switchFFCAsyncTask.execute(new Void[0]);
            getAnalytics().trackUiEvent("Switch", "FFC: " + getCameraWrapper().getUseFrontFacingCamera(), 0);
        }
    }

    @OnClick({2131492939})
    public void onSwitchModeClicked() {
        if (canHandleInputAndNotifyOfInteraction()) {
            this.ui.toggleModeDropdown();
        }
    }

    public boolean canHandleInputAndNotifyOfInteraction() {
        if (!this.isRunning || this.ui.isLoadingFrameShowing()) {
            return false;
        }
        if (getCameraWrapper() != null && getGLHelper() != null && this.ui.isPaused()) {
            getSupportFragmentManager().popBackStack();
            return false;
        } else if (!this.goofyScene.isInitialized()) {
            return false;
        } else {
            if (getState().isAdsEnabled()) {
                this.interstitialHelper.onInteraction();
            }
            return true;
        }
    }

    private void changeMode(Mode newMode) {
        if (Log.isD) {
            Log.d(TAG, "changeMode: " + newMode);
        }
        Mode oldMode = getState().getMode();
        if (oldMode != newMode) {
            getState().setMode(newMode);
            this.ui.onModeSelected();
            this.goofyScene.changeMode(this, oldMode);
        }
    }

    @TargetApi(19)
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == -1 && data != null) {
            Uri originalUri = null;
            switch (requestCode) {
                case 0:
                    originalUri = data.getData();
                    break;
                case 1:
                    originalUri = data.getData();
                    getContentResolver().takePersistableUriPermission(originalUri, data.getFlags() & 3);
                    break;
                case 2:
                    this.billingWrapper.onActivityResult(this, resultCode, data);
                    break;
            }
            if (originalUri == null) {
                return;
            }
            if (this.isGLLoaded) {
                onImageSelected(originalUri);
            } else {
                onImageSelectedWait(originalUri);
            }
        }
    }

    private void onImageSelected(Uri uri) {
        this.goofyScene.onImageSelected(this, uri);
        changeMode(Mode.IMPORTED);
        this.ui.hideModeDropdown();
    }

    private void onImageSelectedWait(Uri uri) {
        this.handler.sendMessageDelayed(Message.obtain(this.handler, 0, uri), 10);
    }

    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode != 82) {
            return super.onKeyDown(keyCode, event);
        }
        onMenuClicked();
        return true;
    }

    public void showInfoFragment() {
        if (this.isRunning) {
            getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.frag_in, R.anim.frag_out, R.anim.frag_pop_in, R.anim.frag_pop_out).replace(R.id.fragment_dialog_frame, MoreInfoFragment.newInstance(), Constants.FRAG_MORE_INFO).addToBackStack("showInfoFragment").commit();
            this.ui.setPaused(true);
        }
    }

    public void showLastPhotoFragment() {
        Uri uri = getState().getLastImageUri();
        if (uri != null && this.isRunning) {
            this.sharePopup = SharePopup.newInstance(uri);
            getSupportFragmentManager().beginTransaction().setCustomAnimations(R.anim.frag_in, R.anim.frag_out, R.anim.frag_pop_in, R.anim.frag_pop_out).replace(R.id.fragment_dialog_frame, this.sharePopup, Constants.FRAG_SHARE_FRAGMENT).addToBackStack(null).commit();
            this.ui.setPaused(true);
        }
    }

    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
    }

    protected void onPause() {
        if (Log.isD) {
            Log.d(TAG, "onPause");
        }
        AdColony.pause();
        this.isRunning = false;
        this.handler.pause();
        this.ui.onPause();
        cancelGLAsyncTask();
        getCameraWrapper().onPause(this);
        this.hasShownOnce = false;
        if (Log.isD) {
            Log.d(TAG, "onPause_Done");
        }
        super.onPause();
    }

    protected void onResume() {
        if (Log.isD) {
            Log.d(TAG, "onResume");
        }
        super.onResume();
        AdColony.resume(this);
        this.isRunning = true;
        this.onResumeAt = System.currentTimeMillis();
        this.handler.resume();
        this.interstitialHelper.onResume();
        this.ui.onResume();
        startGLAsyncTask();
        SubscribeManager manager = SubscribeManager.getActiveManager();
        if (manager != null) {
            manager.refresh();
        }
    }

    private synchronized void startGLAsyncTask() {
        cancelGLAsyncTask();
        this.openGLAsyncTask = new OpenGLAsyncTask();
        this.openGLAsyncTask.execute(new Void[0]);
    }

    private synchronized void cancelGLAsyncTask() {
        if (this.openGLAsyncTask != null) {
            this.openGLAsyncTask.kill();
            try {
                this.openGLAsyncTask.get();
            } catch (InterruptedException e) {
            } catch (ExecutionException e2) {
            }
            this.openGLAsyncTask = null;
        }
    }

    protected void onStart() {
        if (Log.isD) {
            Log.d(TAG, "onStart");
        }
        super.onStart();
        GAUtils.onStart(getAnalytics().getTracker(), this);
    }

    protected void onStop() {
        if (Log.isD) {
            Log.d(TAG, "onStop");
        }
        super.onStop();
        GAUtils.onStop(this);
    }

    protected void onDestroy() {
        if (Log.isD) {
            Log.d(TAG, "onDestroy");
        }
        super.onDestroy();
        this.billingWrapper.onDestroy(this);
        this.ui.onDestroy();
    }

    public synchronized void onRenderingFailed(String title, String error, Throwable e, String extraDetails) {
        if (getSupportFragmentManager().findFragmentByTag(Constants.FRAG_OPENGL_ERROR) == null) {
            if (getAnalytics().isEnabled()) {
                if (extraDetails == null) {
                    extraDetails = "";
                }
                if (e == null) {
                    e = new Exception("Unknown exception: " + extraDetails);
                }
                getAnalytics().sendException("onRenderingFailed: " + extraDetails, e, Boolean.valueOf(true));
            }
            if (this.ui.isLoadingFrameShowing()) {
                runOnUiThread(this.removeLoadingBarRunnable);
            }
            this.lastOnRenderFailedMessage = extraDetails;
            this.lastOnRenderFailedError = e;
            new Builder().setTitle(title).setMessage(error).setNegativeButton(R.string.close_app, 1).setPositiveButton(R.string.email, 1).setCancelable(false).show(this, Constants.FRAG_OPENGL_ERROR);
        }
    }

    public void onPhotoTaken(boolean success, String filePath, Throwable exception, boolean tryAgain) {
        if (success || !tryAgain) {
            if (success) {
                Uri lastImageUri = Uri.fromFile(new File(filePath));
                getState().setLastImageUri(lastImageUri);
                PreferenceManager.getDefaultSharedPreferences(this).edit().putString(Constants.PREF_LAST_IMAGE_URI, lastImageUri.toString()).apply();
                this.ui.refreshPhotoThumbnail(this, lastImageUri);
                MediaScannerConnection.scanFile(this, new String[]{filePath}, null, null);
            } else {
                boolean isEmailError = !(exception instanceof ScreenshotException);
                String errorMessage = isEmailError ? getString(R.string.picture_failed_text) : exception.getMessage();
                Builder builder = new Builder();
                builder.setTitle((int) R.string.picture_failed_title).setMessage(errorMessage).setNegativeButton(17039370, 0);
                if (isEmailError) {
                    builder.setPositiveButton(R.string.email, 2);
                }
                builder.show(this, "on_picture_error");
                this.ui.hidePhotoThumbnail(0);
                if (getAnalytics().isEnabled()) {
                    String details = GLTakeScreenshot.getExceptionDetails();
                    Throwable e = GLTakeScreenshot.getFailureException();
                    if (details == null) {
                        details = "No details.";
                    }
                    if (e == null) {
                        e = new Exception("Unknown exception: " + details);
                    }
                    getAnalytics().sendException("onPictureTakenFailed: " + details, e, Boolean.valueOf(false));
                }
            }
            this.multiplePhotoTaker.onPhotoTaken(this);
            return;
        }
        onTakePhotoClicked();
    }

    public void onPhotoProgressUpdate(int progress) {
        Crashlytics.setInt("screenshot_progress", progress);
        long diff = System.currentTimeMillis() - this.lastProgressTime;
        if (Log.isD) {
            Log.d(TAG, "Progress: " + progress + "  time: " + diff);
        }
        this.lastProgressTime = System.currentTimeMillis();
        this.ui.refreshPhotoProgress(this, progress);
        if (progress == 5) {
            this.goofyScene.refreshCurrentFilter(this);
        }
    }

    public void onCameraInfoReceived(SafeCameraInfo cameraInfo) {
        if (getAnalytics().isEnabled()) {
            Log.v(TAG, "Sending track orientation: " + cameraInfo.orientation);
            getAnalytics().sendEvent("usage", "camera_orientation", "" + cameraInfo.orientation, 0);
            getAnalytics().sendEvent("usage", "camera_facing", cameraInfo.facing == SafeCameraInfo.CAMERA_FACING_FRONT ? "front" : "back", 0);
        }
    }

    public void onInvalidCamera(String details, Exception err) {
        if (Log.isV) {
            Log.v(TAG, "invalidCamera: " + details);
        }
        getState().setValidCamera(false);
        if (!this.hasShownOnce && this.isRunning) {
            if (Log.isE) {
                Log.e(TAG, "Camera error dialog");
            }
            new Builder().setTitle((int) R.string.error_camera_title).setMessage((int) R.string.error_camera).setPositiveButton(17039370, 0).show(this, "invalid_camera");
            this.hasShownOnce = true;
            if (getAnalytics().isEnabled()) {
                if (details == null) {
                    details = "";
                }
                if (err == null) {
                    err = new Exception("Unknown exception: " + details);
                }
                if (this.isFirstRun) {
                    getAnalytics().sendException("onInvalidCamera: " + details, err, Boolean.valueOf(true));
                }
                Log.e(TAG, details, err);
            }
        }
    }

    public void onCameraCrash(int error, Camera camera) {
        if (error == 100) {
            new RebootCameraAsyncTask().execute(new Void[0]);
        } else {
            onInvalidCamera("Camera error: " + error, null);
        }
    }

    public void onAlertDialogPositiveButton(int id, Bundle extras) {
        switch (id) {
            case 1:
                MoreInfoFragment.sendEmail(this, getResources().getString(R.string.opengl_critical_error_title), Utils.replaceModelAndCarrier(this, getResources().getString(R.string.error_email_body)) + "\n\n" + this.lastOnRenderFailedMessage + "\n", this.lastOnRenderFailedError);
                finish();
                return;
            case 2:
                MoreInfoFragment.sendEmail(this, getResources().getString(R.string.picture_failed_title), Utils.replaceModelAndCarrier(this, getResources().getString(R.string.error_email_body)) + "\n\n" + GLTakeScreenshot.getExceptionDetails() + "\n", GLTakeScreenshot.getFailureException());
                return;
            default:
                return;
        }
    }

    public void onAlertDialogNegativeButton(int id, Bundle args) {
        switch (id) {
            case 1:
                finish();
                return;
            default:
                return;
        }
    }

    private void takeMultiplePhotos() {
        ArrayList<GLProgram> programsToUse = new ArrayList();
        programsToUse.add(this.goofyScene.getPrograms().getOfType(28));
        programsToUse.add(this.goofyScene.getPrograms().getOfType(28));
        this.multiplePhotoTaker.start(this, programsToUse);
    }

    public void takePhotoOfFilter(final int filter) {
        this.handler.postDelayed(new Runnable() {
            public void run() {
                CameraFilterActivity.this.goofyScene.setFilterWithRandomParams(CameraFilterActivity.this, filter);
                if (CameraFilterActivity.this.goofyScene.takePhoto(CameraFilterActivity.this)) {
                    CameraFilterActivity.this.ui.onTakingPhoto();
                }
            }
        }, 1000);
    }

    public boolean onTouch(View v, MotionEvent event) {
        return true;
    }
}
