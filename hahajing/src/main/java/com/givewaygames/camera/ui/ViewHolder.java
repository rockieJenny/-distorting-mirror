package com.givewaygames.camera.ui;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.os.Message;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.Optional;
import com.crashlytics.android.Crashlytics;
import com.givewaygames.camera.activities.CameraFilterActivity;
import com.givewaygames.camera.events.FilterChangedEvent;
import com.givewaygames.camera.graphics.GoofyScene;
import com.givewaygames.camera.state.AppState;
import com.givewaygames.camera.utils.PauseHandler;
import com.givewaygames.camera.utils.Toast;
import com.givewaygames.camera.utils.Utils;
import com.givewaygames.goofyglass.R;
import com.givewaygames.gwgl.CameraWrapper;
import com.givewaygames.gwgl.preview.GLSurfaceView;
import com.givewaygames.gwgl.utils.Log;
import com.givewaygames.gwgl.utils.gl.camera.GLBufferManagedCamera;
import com.givewaygames.gwgl.utils.gl.camera.GLBufferManagedCamera.FauxSurface;
import com.givewaygames.house_ads.AdWrapper;
import com.squareup.otto.Subscribe;
import com.squareup.picasso.Picasso;
import it.sephiroth.android.library.widget.AdapterView.OnItemClickListener;
import it.sephiroth.android.library.widget.HListView;

public class ViewHolder {
    public static final int MSG_HIDE_PHOTO_THUMBNAIL = 1;
    public static final String TAG = "ViewHolder";
    AdWrapper adWrapper;
    @InjectView(2131492942)
    ImageView cameraImageButton;
    @Optional
    @InjectView(2131492947)
    TextView debugExtra;
    @Optional
    @InjectView(2131492945)
    TextView debugLoadProgress;
    @Optional
    @InjectView(2131492946)
    TextView debugPhotoProgress;
    @InjectView(2131492907)
    ViewGroup externalAd;
    Animation externalAdFadeIn;
    Animation externalAdFadeOut;
    @InjectView(2131492936)
    ViewGroup fadeActionGroup;
    @InjectView(2131492943)
    ImageView famousImageButton;
    FauxSurface fauxSurface;
    FilterAdapter filterAdapter;
    Animation filtersHideAnim;
    Animation filtersShowAnim;
    GLSurfaceView glSurfaceView;
    PauseHandler handler = new PauseHandler() {
        public void processMessage(Message msg) {
            switch (msg.what) {
                case 1:
                    ViewHolder.this.startAnimationIf(ViewHolder.this.photoTaken, ViewHolder.this.photoFadeOut, true);
                    return;
                default:
                    return;
            }
        }
    };
    @InjectView(2131492916)
    View hiddenRow;
    @Optional
    @InjectView(2131492949)
    HListView horizontalFilterGroup;
    OnItemClickListener horizontalFilterOnClickListener;
    @InjectView(2131492944)
    ImageView importImageButton;
    @InjectView(2131492906)
    FrameLayout internalAd;
    Animation internalAdFadeIn;
    Animation internalAdFadeOut;
    LayoutMagic layoutMagic = new LayoutMagic();
    @InjectView(2131492950)
    FrameLayout loadingFrame;
    @InjectView(2131492951)
    TextView loadingText;
    Animation menuFadeIn;
    Animation menuFadeOut;
    final AnimationListener menuInListener = new BaseAnimationListener() {
        public void onAnimationEnd(Animation animation) {
            ViewHolder.this.setPaused(false);
        }
    };
    final AnimationListener menuOutListener = new BaseAnimationListener() {
        public void onAnimationEnd(Animation animation) {
            ViewHolder.this.fadeActionGroup.setVisibility(4);
            ViewHolder.this.glSurfaceView.setPaused(false);
        }
    };
    Animation modeHideAnim;
    @Optional
    @InjectView(2131492941)
    ViewGroup modeMenu;
    Animation modeShowAnim;
    @InjectView(2131492937)
    ImageView moreInfoButton;
    public final Runnable notifyOfChange = new Runnable() {
        public void run() {
            int position = ViewHolder.this.getState().getCurrentFilter();
            ViewGroup filterGroup = ViewHolder.this.getFilterGroup();
            boolean doAnimation = ViewHolder.this.getState().getLastFilter() == position;
            if (filterGroup instanceof HListView) {
                ((HListView) ViewHolder.this.getFilterGroup()).setItemChecked(position, true);
                if (doAnimation && ViewHolder.this.filterAdapter != null) {
                    ViewHolder.this.filterAdapter.animate(position, (HListView) ViewHolder.this.getFilterGroup());
                    return;
                }
                return;
            }
            ((ListView) ViewHolder.this.getFilterGroup()).setItemChecked(position, true);
            if (doAnimation && ViewHolder.this.filterAdapter != null) {
                ViewHolder.this.filterAdapter.animate(position, (ListView) ViewHolder.this.getFilterGroup());
            }
        }
    };
    Animation photoFadeIn;
    Animation photoFadeOut;
    @InjectView(2131492956)
    ImageView photoTaken;
    @InjectView(2131492917)
    RelativeLayout previewRelativeLayout;
    int[] progressIds = new int[]{R.string.snarky1, R.string.snarky2, R.string.snarky3, R.string.snarky4, R.string.snarky5};
    @InjectView(2131492955)
    ImageView randomButton;
    SavedViewHolderState savedState = null;
    @InjectView(2131492952)
    View shutterGroup;
    @InjectView(2131492940)
    ImageView switchCameraButton;
    @InjectView(2131492939)
    ImageView switchModeButton;
    @InjectView(2131492953)
    ImageView takePhotoButton;
    @InjectView(2131492954)
    ImageView tilesButton;
    @InjectView(2131492938)
    ImageView unlockButton;
    @Optional
    @InjectView(2131492948)
    ListView verticalFilterGroup;
    AdapterView.OnItemClickListener verticalFilterOnClickListener;

    private static class SavedViewHolderState {
        public int filterAdapterFirstIndex;
        public int filterAdapterFirstTop;
        public int filterAdapterLastBottom;
        public int filterAdapterLastIndex;

        private SavedViewHolderState() {
        }
    }

    private static class OutAnimation extends BaseAnimationListener {
        View animatingView;

        public OutAnimation(View animatingView) {
            this.animatingView = animatingView;
        }

        public void onAnimationEnd(Animation animation) {
            if (this.animatingView != null && this.animatingView.getAnimation() == animation) {
                this.animatingView.setVisibility(4);
                this.animatingView.clearAnimation();
            }
        }
    }

    public ViewHolder() {
        getState().getBus().register(this);
    }

    @Subscribe
    public void onFilterChanged(FilterChangedEvent event) {
        this.handler.post(this.notifyOfChange);
    }

    public AppState getState() {
        return AppState.getInstance();
    }

    public ViewGroup getFilterGroup() {
        boolean isInvalid;
        if (this.verticalFilterGroup == null && this.horizontalFilterGroup == null) {
            isInvalid = true;
        } else {
            isInvalid = false;
        }
        boolean isBothValid;
        if (this.verticalFilterGroup == null || this.horizontalFilterGroup == null) {
            isBothValid = false;
        } else {
            isBothValid = true;
        }
        if (!isInvalid && !isBothValid) {
            return this.verticalFilterGroup != null ? this.verticalFilterGroup : this.horizontalFilterGroup;
        } else {
            throw new RuntimeException("Can't have two filter groups, one needs to be cleared");
        }
    }

    public GLSurfaceView getGLSurfaceView() {
        return this.glSurfaceView;
    }

    public FauxSurface getFauxSurface() {
        return this.fauxSurface;
    }

    public boolean isModeDropdownShowing() {
        return this.modeMenu.getVisibility() == 0;
    }

    public boolean isLoadingFrameShowing() {
        return this.loadingFrame.isShown();
    }

    public boolean isPaused() {
        return this.glSurfaceView.isPaused();
    }

    public void setPaused(boolean isPaused) {
        this.glSurfaceView.setPaused(isPaused);
    }

    public void onCreate(boolean isAmazon) {
        this.adWrapper = new AdWrapper(isAmazon);
    }

    public void onResume() {
        this.handler.resume();
        this.loadingText.setText(R.string.snarky2);
        this.glSurfaceView.onResume();
    }

    public void onPause() {
        this.handler.pause();
        this.glSurfaceView.onPause();
    }

    public void onDestroy() {
        this.adWrapper.onDestroy();
    }

    private void saveState() {
        boolean isInverted;
        int i = 0;
        this.savedState = new SavedViewHolderState();
        ViewGroup filterGroup = getFilterGroup();
        View first = filterGroup.getChildAt(0);
        View last = filterGroup.getChildAt(filterGroup.getChildCount() - 1);
        if (this.filterAdapter == null || !this.filterAdapter.isInverted()) {
            isInverted = false;
        } else {
            isInverted = true;
        }
        int height;
        SavedViewHolderState savedViewHolderState;
        if (filterGroup instanceof HListView) {
            height = filterGroup.getWidth();
            this.savedState.filterAdapterFirstIndex = ((HListView) filterGroup).getFirstVisiblePosition();
            this.savedState.filterAdapterLastIndex = ((HListView) filterGroup).getLastVisiblePosition();
            this.savedState.filterAdapterFirstTop = first == null ? 0 : first.getLeft();
            savedViewHolderState = this.savedState;
            if (last != null) {
                i = height - last.getRight();
            }
            savedViewHolderState.filterAdapterLastBottom = i;
        } else {
            height = filterGroup.getHeight();
            this.savedState.filterAdapterFirstIndex = ((ListView) filterGroup).getFirstVisiblePosition();
            this.savedState.filterAdapterLastIndex = ((ListView) filterGroup).getLastVisiblePosition();
            this.savedState.filterAdapterFirstTop = first == null ? 0 : first.getTop();
            savedViewHolderState = this.savedState;
            if (last != null) {
                i = height - last.getBottom();
            }
            savedViewHolderState.filterAdapterLastBottom = i;
        }
        if (isInverted) {
            int total = this.filterAdapter.getCount();
            int firstIdx = this.savedState.filterAdapterFirstIndex;
            int lastIdx = this.savedState.filterAdapterLastIndex;
            this.savedState.filterAdapterLastIndex = (total - firstIdx) - 1;
            this.savedState.filterAdapterFirstIndex = (total - lastIdx) - 1;
            int top = this.savedState.filterAdapterFirstTop;
            this.savedState.filterAdapterFirstTop = this.savedState.filterAdapterLastBottom;
            this.savedState.filterAdapterLastBottom = top;
        }
    }

    public void onConfigurationChanged(Activity activity) {
        saveState();
        refreshDynamicContent(activity);
        refreshLoadingFrame();
        this.savedState = null;
    }

    public void refreshDynamicContent(Activity activity) {
        refreshDynamicLayout(activity);
        loadStaticAnimationsIfNeeded(activity);
        loadDynamicAnimations(activity);
    }

    public void refreshStaticContent(Activity activity) {
        refreshStaticLayout(activity);
    }

    private void refreshDynamicLayout(Activity activity) {
        int i;
        int i2 = 8;
        int i3 = 0;
        AppState state = getState();
        if (CameraWrapper.DEBUG) {
            Utils.logHeap(TAG, "refreshDynamicLayout");
        }
        ViewGroup mainUI = (ViewGroup) activity.findViewById(R.id.main_ui);
        mainUI.removeAllViews();
        activity.getLayoutInflater().inflate(R.layout.main_ui, mainUI, true);
        ButterKnife.inject((Object) this, activity);
        ButterKnife.inject(activity);
        state.getUiState().refreshConfiguration(activity);
        boolean isMenuShowing = state.getUiState().isMenuShowing();
        boolean isModeShowing = state.getUiState().isModeDropdownShowing();
        boolean isUnlockShowing = state.getUiState().isUnlockShowing();
        boolean hasCamera = state.hasCamera();
        boolean hasMultipleCamera = state.getCameraWrapper().hasMultipleCameras(activity);
        this.cameraImageButton.setEnabled(hasCamera);
        ImageView imageView = this.cameraImageButton;
        if (hasCamera) {
            i = 0;
        } else {
            i = 8;
        }
        imageView.setVisibility(i);
        ImageView imageView2 = this.switchCameraButton;
        if (hasMultipleCamera) {
            i2 = 0;
        }
        imageView2.setVisibility(i2);
        this.takePhotoButton.setVisibility(0);
        ImageView imageView3 = this.unlockButton;
        if (isUnlockShowing) {
            i = 0;
        } else {
            i = 4;
        }
        imageView3.setVisibility(i);
        ViewGroup viewGroup = this.modeMenu;
        if (isModeShowing) {
            i = 0;
        } else {
            i = 4;
        }
        viewGroup.setVisibility(i);
        viewGroup = this.fadeActionGroup;
        if (isMenuShowing) {
            i = 0;
        } else {
            i = 4;
        }
        viewGroup.setVisibility(i);
        this.tilesButton.setImageResource(isMenuShowing ? R.drawable.ic_menu_close : R.drawable.ic_menu);
        imageView3 = this.photoTaken;
        if (isMenuShowing) {
            i = 0;
        } else {
            i = 4;
        }
        imageView3.setVisibility(i);
        FrameLayout frameLayout = this.internalAd;
        if (isMenuShowing) {
            i = 0;
        } else {
            i = 4;
        }
        frameLayout.setVisibility(i);
        ViewGroup viewGroup2 = this.externalAd;
        if (isMenuShowing) {
            i3 = 4;
        }
        viewGroup2.setVisibility(i3);
        Uri lastImageUri = state.getLastImageUri();
        if (lastImageUri != null) {
            refreshPhotoThumbnail(activity, lastImageUri);
        }
        refreshCurrentFilterAdapter();
        refreshAds(activity);
        onModeSelected();
        this.layoutMagic.refreshPhotoThumbnail(state, this);
        this.layoutMagic.refreshAdLayout(state, this);
        this.layoutMagic.refreshActionBarUI(state, this);
        this.layoutMagic.refreshPreviewLayout(activity.getResources(), state, this);
        this.layoutMagic.refreshShutterGroup(state, this);
        this.loadingText.setText(R.string.snarky1);
        Toast.getInstance().initialize(activity, this.handler);
    }

    private void refreshStaticLayout(Activity activity) {
        boolean z;
        if (activity.getResources().getBoolean(R.bool.is_small)) {
            z = false;
        } else {
            z = true;
        }
        GLSurfaceView.IS_STENCILING_ALLOWED = z;
        AppState state = getState();
        this.glSurfaceView = new GLSurfaceView(activity);
        this.glSurfaceView.setKeepScreenOn(true);
        state.setGLHelper(this.glSurfaceView.getGLHelper());
        this.previewRelativeLayout.addView(this.glSurfaceView, 0);
        this.previewRelativeLayout.setOnTouchListener((OnTouchListener) activity);
        if (state.getUiState().isBufferManagedCamera()) {
            this.fauxSurface = GLBufferManagedCamera.newFauxSurface(activity);
            this.previewRelativeLayout.addView(this.fauxSurface, 1);
        }
    }

    private void loadStaticAnimationsIfNeeded(Activity activity) {
        if (this.photoFadeIn == null) {
            this.photoFadeIn = AnimationUtils.loadAnimation(activity, R.anim.photo_fade_in);
            this.photoFadeOut = AnimationUtils.loadAnimation(activity, R.anim.photo_fade_out);
            this.menuFadeIn = AnimationUtils.loadAnimation(activity, R.anim.fade_in);
            this.menuFadeOut = AnimationUtils.loadAnimation(activity, R.anim.fade_out);
            this.internalAdFadeIn = AnimationUtils.loadAnimation(activity, R.anim.fade_in);
            this.internalAdFadeOut = AnimationUtils.loadAnimation(activity, R.anim.fade_out);
            this.externalAdFadeIn = AnimationUtils.loadAnimation(activity, R.anim.fade_in);
            this.externalAdFadeOut = AnimationUtils.loadAnimation(activity, R.anim.fade_out);
        }
    }

    private void loadDynamicAnimations(Activity activity) {
        boolean isLandLeft = getState().getUiState().isLandscapeLeft();
        this.modeShowAnim = isLandLeft ? AnimationUtils.loadAnimation(activity, R.anim.mode_show_left) : AnimationUtils.loadAnimation(activity, R.anim.mode_show);
        this.modeHideAnim = isLandLeft ? AnimationUtils.loadAnimation(activity, R.anim.mode_hide_left) : AnimationUtils.loadAnimation(activity, R.anim.mode_hide);
        this.filtersShowAnim = isLandLeft ? AnimationUtils.loadAnimation(activity, R.anim.menu_in_left) : AnimationUtils.loadAnimation(activity, R.anim.menu_in);
        this.filtersHideAnim = isLandLeft ? AnimationUtils.loadAnimation(activity, R.anim.menu_out_left) : AnimationUtils.loadAnimation(activity, R.anim.menu_out);
        this.modeHideAnim.setAnimationListener(new OutAnimation(this.modeMenu));
        this.filtersHideAnim.setAnimationListener(new OutAnimation(getFilterGroup()));
        this.photoFadeOut.setAnimationListener(new OutAnimation(this.photoTaken));
        this.internalAdFadeOut.setAnimationListener(new OutAnimation(this.internalAd));
        this.externalAdFadeOut.setAnimationListener(new OutAnimation(this.externalAd));
        this.menuFadeIn.setAnimationListener(this.menuInListener);
        this.menuFadeOut.setAnimationListener(this.menuOutListener);
    }

    public void refreshFilterAdapter(final Activity activity, final GoofyScene goofyScene) {
        this.filterAdapter = new FilterAdapter(activity, goofyScene.getPrograms());
        this.filterAdapter.setIsInverted(getState().getUiState().isLandscapeLeft());
        this.horizontalFilterOnClickListener = new OnItemClickListener() {
            public void onItemClick(it.sephiroth.android.library.widget.AdapterView<?> adapterView, View view, int position, long id) {
                if (((CameraFilterActivity) activity).canHandleInputAndNotifyOfInteraction()) {
                    goofyScene.enableFilter(activity, (int) id, true, false);
                    goofyScene.randomizeCurrentVariables();
                }
            }
        };
        this.verticalFilterOnClickListener = new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
                if (((CameraFilterActivity) activity).canHandleInputAndNotifyOfInteraction()) {
                    goofyScene.enableFilter(activity, (int) id, true, false);
                    goofyScene.randomizeCurrentVariables();
                }
            }
        };
        refreshCurrentFilterAdapter();
    }

    private void refreshCurrentFilterAdapter() {
        int firstTop;
        int lastBottom;
        int first;
        int last;
        int i = 0;
        if (this.savedState != null) {
            firstTop = this.savedState.filterAdapterFirstTop;
        } else {
            firstTop = 0;
        }
        if (this.savedState != null) {
            lastBottom = this.savedState.filterAdapterLastBottom;
        } else {
            lastBottom = 0;
        }
        if (this.savedState != null) {
            first = this.savedState.filterAdapterFirstIndex;
        } else {
            first = 0;
        }
        if (this.savedState != null) {
            last = this.savedState.filterAdapterLastIndex;
        } else {
            last = 0;
        }
        int index = first;
        int top = firstTop;
        if (this.filterAdapter != null) {
            boolean doInvert = getState().getUiState().isLandscapeRight();
            this.filterAdapter.setIsInverted(doInvert);
            if (doInvert) {
                index = (this.filterAdapter.getCount() - 1) - last;
                top = lastBottom;
            }
        }
        ViewGroup filterGroup = getFilterGroup();
        if (!AppState.getInstance().getUiState().isMenuShowing()) {
            i = 4;
        }
        filterGroup.setVisibility(i);
        if (filterGroup instanceof HListView) {
            ((HListView) filterGroup).setChoiceMode(1);
            ((HListView) filterGroup).setAdapter(this.filterAdapter);
            ((HListView) filterGroup).setOnItemClickListener(this.horizontalFilterOnClickListener);
            if (this.savedState != null) {
                ((HListView) filterGroup).setSelectionFromLeft(index, top);
                return;
            }
            return;
        }
        ((ListView) filterGroup).setChoiceMode(1);
        ((ListView) filterGroup).setAdapter(this.filterAdapter);
        ((ListView) filterGroup).setOnItemClickListener(this.verticalFilterOnClickListener);
        if (this.savedState != null) {
            ((ListView) filterGroup).setSelectionFromTop(index, top);
        }
    }

    public void refreshAds(Activity activity) {
        this.unlockButton.setVisibility(getState().getUiState().isUnlockShowing() ? 0 : 4);
        this.adWrapper.setInternalLayout(this.internalAd);
        this.adWrapper.setExternalLayout(this.externalAd);
        boolean menuEnabled = getState().getUiState().isMenuShowing();
        boolean adsEnabled = getState().isAdsEnabled();
        if (adsEnabled && !menuEnabled) {
            showExternalAd(activity);
        } else if (adsEnabled && menuEnabled) {
            showInternalAd(activity);
        } else {
            this.externalAd.setVisibility(8);
            this.internalAd.setVisibility(8);
        }
    }

    public void showInternalAd(Activity activity) {
        startAnimationIf(this.externalAd, this.externalAdFadeOut, true);
        startAnimationIf(this.internalAd, this.internalAdFadeIn, false);
        this.adWrapper.setupInternalAd(activity);
    }

    public void showExternalAd(Activity activity) {
        startAnimationIf(this.internalAd, this.internalAdFadeOut, true);
        startAnimationIf(this.externalAd, this.externalAdFadeIn, false);
        this.adWrapper.setupExternalAd(activity);
    }

    public void refreshLoadingProgress(int progress) {
        int idx = progress;
        if (Log.isD) {
            Log.d(TAG, "refreshLoadingProgress: " + idx);
        }
        Crashlytics.setInt("initial_progress", idx);
        if (idx >= 0 && idx < this.progressIds.length && this.loadingText != null) {
            this.loadingText.setText(this.progressIds[idx]);
        }
    }

    public void onTakingPhoto() {
        showPhotoThumbnail(true);
    }

    public void refreshPhotoProgress(Context context, int progress) {
        Picasso picasso = Picasso.with(context);
        if (progress == 0) {
            try {
                this.photoTaken.setImageResource(0);
            } catch (OutOfMemoryError e) {
                if (Log.isE) {
                    Log.e(TAG, "This doesn't bode well.");
                }
                Toast.makeText(R.string.low_memory, 0);
                this.photoTaken.setImageResource(0);
            }
        } else if (progress == 1) {
            picasso.load((int) R.drawable.ic_timer_a).into(this.photoTaken);
        } else if (progress == 4) {
            picasso.load((int) R.drawable.ic_timer_b).placeholder((int) R.drawable.ic_timer_a).into(this.photoTaken);
        } else if (progress == 5) {
            picasso.load((int) R.drawable.ic_timer_c).placeholder((int) R.drawable.ic_timer_b).into(this.photoTaken);
        }
    }

    public void refreshPhotoThumbnail(Context context, Uri uri) {
        int s = (int) (0.5f + context.getResources().getDimension(R.dimen.take_photo_size));
        Picasso.with(context).load(uri).resize(s, s).centerCrop().placeholder((int) R.drawable.ic_timer_c).into(this.photoTaken);
        hidePhotoThumbnail(5000);
    }

    public void toggleMenu(Activity activity) {
        this.glSurfaceView.setPaused(true);
        if (this.fadeActionGroup.getVisibility() == 0) {
            hideMenu();
        } else {
            showMenu();
        }
        refreshAds(activity);
    }

    public void showMenu() {
        getState().getUiState().setMenuShowing(true);
        this.tilesButton.setImageResource(R.drawable.ic_menu_close);
        startAnimationIf(this.fadeActionGroup, this.menuFadeIn, false);
        startAnimationIf(getFilterGroup(), this.filtersShowAnim, false);
        showPhotoThumbnail(false);
    }

    public void hideMenu() {
        getState().getUiState().setMenuShowing(false);
        this.tilesButton.setImageResource(R.drawable.ic_menu);
        this.fadeActionGroup.startAnimation(this.menuFadeOut);
        getFilterGroup().startAnimation(this.filtersHideAnim);
        if (isModeDropdownShowing()) {
            hideModeDropdown();
        }
        hidePhotoThumbnail(0, true);
    }

    public void toggleModeDropdown() {
        if (isModeDropdownShowing()) {
            hideModeDropdown();
        } else {
            showModeDropdown();
        }
    }

    public void showModeDropdown() {
        getState().getUiState().setModeDropdownShowing(true);
        this.modeMenu.startAnimation(this.modeShowAnim);
        this.modeMenu.setVisibility(0);
    }

    public void hideModeDropdown() {
        getState().getUiState().setModeDropdownShowing(false);
        this.modeMenu.startAnimation(this.modeHideAnim);
    }

    public void onModeSelected() {
        AppState state = getState();
        boolean isCamera = state.isModeCamera();
        boolean isFamous = state.isModeFamous();
        boolean isImported = state.isModeImported();
        this.cameraImageButton.setSelected(isCamera);
        this.importImageButton.setSelected(isImported);
        this.famousImageButton.setSelected(isFamous);
        ImageView imageView = this.switchModeButton;
        int i = isCamera ? R.drawable.ic_camera : isFamous ? R.drawable.ic_famous : R.drawable.ic_import;
        imageView.setImageResource(i);
    }

    public void showLoadingFrame() {
        this.loadingFrame.setVisibility(0);
    }

    public void hideLoadingFrame() {
        this.loadingFrame.setVisibility(8);
    }

    public void refreshLoadingFrame() {
        if (!getState().isLoading()) {
            this.loadingFrame.setVisibility(8);
        }
    }

    public void showPhotoThumbnail(boolean clearBeforeShow) {
        this.handler.removeMessages(1);
        if (clearBeforeShow) {
            this.photoTaken.setImageDrawable(null);
        }
        startAnimationIf(this.photoTaken, this.photoFadeIn, false);
    }

    public void hidePhotoThumbnail(int millisDelay) {
        hidePhotoThumbnail(millisDelay, false);
    }

    private void hidePhotoThumbnail(int millisDelay, boolean force) {
        if (!getState().getUiState().isMenuShowing() || force) {
            this.handler.removeMessages(1);
            this.handler.sendEmptyMessageDelayed(1, (long) millisDelay);
        }
    }

    private void startAnimationIf(View view, Animation animation, boolean playIfCurrentlyVisible) {
        if (animation != null) {
            boolean viewVis;
            if (view.getVisibility() == 0) {
                viewVis = true;
            } else {
                viewVis = false;
            }
            if (viewVis == playIfCurrentlyVisible) {
                view.clearAnimation();
                if (!playIfCurrentlyVisible) {
                    view.setVisibility(0);
                }
                view.startAnimation(animation);
            }
        }
    }
}
