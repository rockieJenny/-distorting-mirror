package com.millennialmedia.android;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Parcelable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewParent;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout;
import com.google.android.gms.cast.TextTrackStyle;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;
import java.util.Random;

public final class MMAdView extends MMLayout implements OnClickListener, AnimationListener {
    static final int DEFAULT_RESIZE_PARAM_VALUES = -50;
    private static final String TAG = "MMAdView";
    public static final int TRANSITION_DOWN = 3;
    public static final int TRANSITION_FADE = 1;
    public static final int TRANSITION_NONE = 0;
    public static final int TRANSITION_RANDOM = 4;
    public static final int TRANSITION_UP = 2;
    int height;
    int oldHeight;
    int oldWidth;
    ImageView refreshAnimationimageView;
    int transitionType;
    ResizeView view;
    int width;

    class BannerBounds {
        DTOResizeParameters resizeParams;
        int translationX;
        int translationY;

        private class BoundsResult {
            int size;
            int translation;

            private BoundsResult() {
            }
        }

        BannerBounds(DTOResizeParameters resizeParams) {
            this.resizeParams = resizeParams;
            this.translationX = resizeParams.offsetX;
            this.translationY = resizeParams.offsetY;
        }

        void calculateOnScreenBounds() {
            calculateXBounds();
            calculateYBounds();
        }

        private void calculateXBounds() {
            int[] loc = new int[2];
            MMAdView.this.getLocationInWindow(loc);
            BoundsResult resultX = calculateBounds(loc[0], this.resizeParams.offsetX, this.resizeParams.width, this.resizeParams.xMax);
            this.resizeParams.width = resultX.size;
            this.translationX = resultX.translation;
        }

        private void calculateYBounds() {
            int[] loc = new int[2];
            MMAdView.this.getLocationInWindow(loc);
            BoundsResult resultY = calculateBounds(loc[1], this.resizeParams.offsetY, this.resizeParams.height, this.resizeParams.yMax);
            this.resizeParams.height = resultY.size;
            this.translationY = resultY.translation;
        }

        private BoundsResult calculateBounds(int screenPos, int offset, int size, int max) {
            int newStart = screenPos;
            int newSize = size;
            if ((screenPos + size) + offset > max) {
                newStart = (max - size) + offset;
                if (newStart < 0) {
                    newStart = 0;
                    newSize = max;
                } else if (newStart + size > max) {
                    newStart = max - size;
                }
            } else if (offset > 0) {
                newStart = offset;
            }
            BoundsResult result = new BoundsResult();
            result.translation = newStart - screenPos;
            result.size = newSize;
            return result;
        }

        LayoutParams modifyLayoutParams(LayoutParams oldParams) {
            oldParams.width = this.resizeParams.width;
            oldParams.height = this.resizeParams.height;
            return oldParams;
        }
    }

    class ResizeView extends View {
        public ResizeView(Context context) {
            super(context);
        }

        protected Parcelable onSaveInstanceState() {
            MMLog.d(MMAdView.TAG, "onSaveInstanceState");
            attachToContext(MMAdView.this);
            return super.onSaveInstanceState();
        }

        protected void onRestoreInstanceState(Parcelable state) {
            MMLog.d(MMAdView.TAG, "onRestoreInstanceState");
            MMAdView.this.attachToWindow(MMAdView.this);
            super.onRestoreInstanceState(state);
        }

        synchronized void attachToContext(View view) {
            MMAdView.this.detachFromParent(view);
            if (getParent() != null && (getParent() instanceof ViewGroup)) {
                ((ViewGroup) getParent()).addView(view);
            }
        }
    }

    private static class MMAdViewWebViewClientListener extends BasicWebViewClientListener {
        MMAdViewWebViewClientListener(MMAdImpl adImpl) {
            super(adImpl);
        }

        public void onPageFinished(String url) {
            super.onPageFinished(url);
            MMAdImpl adImpl = (MMAdImpl) this.adImplRef.get();
            if (adImpl != null && adImpl.isTransitionAnimated()) {
                adImpl.animateTransition();
            }
        }
    }

    class MMAdViewMMAdImpl extends MMLayoutMMAdImpl {
        public MMAdViewMMAdImpl(Context context) {
            super(context);
            this.mmWebViewClientListener = new MMAdViewWebViewClientListener(this);
        }

        String getRequestFailedAction() {
            return MMBroadcastReceiver.ACTION_GETAD_FAILED;
        }

        String getRequestCompletedAction() {
            return MMBroadcastReceiver.ACTION_GETAD_SUCCEEDED;
        }

        String getReqType() {
            return "getad";
        }

        boolean isTransitionAnimated() {
            return MMAdView.this.transitionType != 0;
        }

        void prepareTransition(Bitmap bitmap) {
            MMAdView.this.refreshAnimationimageView.setImageBitmap(bitmap);
            MMAdView.this.refreshAnimationimageView.setVisibility(0);
            MMAdView.this.refreshAnimationimageView.bringToFront();
        }

        public boolean isBanner() {
            return true;
        }

        public boolean hasCachedVideoSupport() {
            return false;
        }

        void insertUrlAdMetaValues(Map<String, String> paramsMap) {
            if (MMAdView.this.height > 0) {
                paramsMap.put(MMRequest.KEY_HEIGHT, String.valueOf(MMAdView.this.height));
            }
            if (MMAdView.this.width > 0) {
                paramsMap.put(MMRequest.KEY_WIDTH, String.valueOf(MMAdView.this.width));
            }
            super.insertUrlAdMetaValues(paramsMap);
        }

        boolean isLifecycleObservable() {
            return MMAdView.this.getWindowToken() != null;
        }

        void animateTransition() {
            if (MMAdView.this.refreshAnimationimageView.getDrawable() != null) {
                Animation animation;
                int type = MMAdView.this.transitionType;
                if (type == 4) {
                    type = new Random().nextInt(4);
                }
                switch (type) {
                    case 2:
                        animation = new TranslateAnimation(0.0f, 0.0f, 0.0f, -((float) MMAdView.this.getHeight()));
                        break;
                    case 3:
                        animation = new TranslateAnimation(0.0f, 0.0f, 0.0f, (float) MMAdView.this.getHeight());
                        break;
                    default:
                        animation = new AlphaAnimation(TextTrackStyle.DEFAULT_FONT_SCALE, 0.0f);
                        break;
                }
                animation.setDuration(1000);
                animation.setAnimationListener(MMAdView.this);
                animation.setFillEnabled(true);
                animation.setFillBefore(true);
                animation.setFillAfter(true);
                final Animation animFinal = animation;
                MMSDK.runOnUiThread(new Runnable() {
                    public void run() {
                        MMAdView.this.refreshAnimationimageView.startAnimation(animFinal);
                    }
                });
            }
        }
    }

    public /* bridge */ /* synthetic */ void addBlackView() {
        super.addBlackView();
    }

    public /* bridge */ /* synthetic */ String getApid() {
        return super.getApid();
    }

    public /* bridge */ /* synthetic */ boolean getIgnoresDensityScaling() {
        return super.getIgnoresDensityScaling();
    }

    public /* bridge */ /* synthetic */ RequestListener getListener() {
        return super.getListener();
    }

    public /* bridge */ /* synthetic */ MMRequest getMMRequest() {
        return super.getMMRequest();
    }

    public /* bridge */ /* synthetic */ boolean onTouchEvent(MotionEvent x0) {
        return super.onTouchEvent(x0);
    }

    public /* bridge */ /* synthetic */ void removeBlackView() {
        super.removeBlackView();
    }

    public /* bridge */ /* synthetic */ void setApid(String x0) {
        super.setApid(x0);
    }

    public /* bridge */ /* synthetic */ void setIgnoresDensityScaling(boolean x0) {
        super.setIgnoresDensityScaling(x0);
    }

    public /* bridge */ /* synthetic */ void setListener(RequestListener x0) {
        super.setListener(x0);
    }

    public /* bridge */ /* synthetic */ void setMMRequest(MMRequest x0) {
        super.setMMRequest(x0);
    }

    public MMAdView(Context context) {
        super(context);
        this.transitionType = 4;
        this.height = 0;
        this.width = 0;
        this.oldHeight = DEFAULT_RESIZE_PARAM_VALUES;
        this.oldWidth = DEFAULT_RESIZE_PARAM_VALUES;
        this.adImpl = new MMAdViewMMAdImpl(context);
        init(context);
    }

    @Deprecated
    public MMAdView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    @Deprecated
    public MMAdView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        this.transitionType = 4;
        this.height = 0;
        this.width = 0;
        this.oldHeight = DEFAULT_RESIZE_PARAM_VALUES;
        this.oldWidth = DEFAULT_RESIZE_PARAM_VALUES;
        if (isInEditMode()) {
            initEclipseAd(context);
            return;
        }
        MMLog.d(TAG, "Creating MMAdView from XML layout.");
        this.adImpl = new MMAdViewMMAdImpl(context);
        if (attrs != null) {
            String namespace = "http://millennialmedia.com/android/schema";
            setApid(attrs.getAttributeValue(namespace, "apid"));
            this.adImpl.ignoreDensityScaling = attrs.getAttributeBooleanValue(namespace, "ignoreDensityScaling", false);
            String heightIn = attrs.getAttributeValue(namespace, "height");
            String widthIn = attrs.getAttributeValue(namespace, "width");
            try {
                if (!TextUtils.isEmpty(heightIn)) {
                    this.height = Integer.parseInt(heightIn);
                }
                if (!TextUtils.isEmpty(widthIn)) {
                    this.width = Integer.parseInt(widthIn);
                }
            } catch (NumberFormatException nfe) {
                MMLog.e(TAG, "Error reading attrs file from xml", nfe);
            }
            if (this.adImpl.mmRequest != null) {
                this.adImpl.mmRequest.age = attrs.getAttributeValue(namespace, MMRequest.KEY_AGE);
                this.adImpl.mmRequest.children = attrs.getAttributeValue(namespace, MMRequest.KEY_CHILDREN);
                this.adImpl.mmRequest.education = attrs.getAttributeValue(namespace, MMRequest.KEY_EDUCATION);
                this.adImpl.mmRequest.ethnicity = attrs.getAttributeValue(namespace, MMRequest.KEY_ETHNICITY);
                this.adImpl.mmRequest.gender = attrs.getAttributeValue(namespace, MMRequest.KEY_GENDER);
                this.adImpl.mmRequest.income = attrs.getAttributeValue(namespace, MMRequest.KEY_INCOME);
                this.adImpl.mmRequest.keywords = attrs.getAttributeValue(namespace, MMRequest.KEY_KEYWORDS);
                this.adImpl.mmRequest.marital = attrs.getAttributeValue(namespace, MMRequest.KEY_MARITAL_STATUS);
                this.adImpl.mmRequest.politics = attrs.getAttributeValue(namespace, MMRequest.KEY_POLITICS);
                this.adImpl.mmRequest.vendor = attrs.getAttributeValue(namespace, MMRequest.KEY_VENDOR);
                this.adImpl.mmRequest.zip = attrs.getAttributeValue(namespace, MMRequest.KEY_ZIP_CODE);
            }
            this.goalId = attrs.getAttributeValue(namespace, "goalId");
        }
        init(context);
    }

    public void setBackgroundColor(int color) {
        super.setBackgroundColor(color);
        if (this.adImpl != null && this.adImpl.controller != null && this.adImpl.controller.webView != null) {
            this.adImpl.controller.webView.setBackgroundColor(color);
        }
    }

    private void init(Context context) {
        setBackgroundColor(0);
        this.adImpl.adType = "b";
        setOnClickListener(this);
        setFocusable(true);
        this.refreshAnimationimageView = new ImageView(context);
        this.refreshAnimationimageView.setScaleType(ScaleType.FIT_XY);
        this.refreshAnimationimageView.setVisibility(8);
        addView(this.refreshAnimationimageView, new RelativeLayout.LayoutParams(-2, -2));
        setLayoutParams(new RelativeLayout.LayoutParams(-2, -2));
    }

    private void initEclipseAd(Context context) {
        Exception e;
        String str;
        String str2;
        Throwable th;
        ImageView logoForXML = new ImageView(context);
        String imageUrl = "http://images.millennialmedia.com/9513/192134.gif";
        InputStream is = null;
        OutputStream outputStream = null;
        try {
            String dir = System.getProperty("java.io.tmpdir");
            if (!(dir == null || dir.endsWith(File.separator))) {
                dir = dir + File.separator;
            }
            File file = new File(dir + "millenial355jns6u3l1nmedia.png");
            if (!file.exists()) {
                HttpURLConnection conn = (HttpURLConnection) new URL("http://images.millennialmedia.com/9513/192134.gif").openConnection();
                conn.setDoOutput(true);
                conn.setConnectTimeout(10000);
                conn.connect();
                is = conn.getInputStream();
                OutputStream out = new FileOutputStream(file);
                try {
                    byte[] buffer = new byte[1024];
                    while (true) {
                        int bytesRead = is.read(buffer);
                        if (bytesRead <= 0) {
                            break;
                        }
                        out.write(buffer, 0, bytesRead);
                    }
                    outputStream = out;
                } catch (Exception e2) {
                    e = e2;
                    outputStream = out;
                    try {
                        MMLog.e(TAG, "Error with eclipse xml image display: ", e);
                        if (is != null) {
                            try {
                                is.close();
                            } catch (Exception e3) {
                                e = e3;
                                str = TAG;
                                str2 = "Error closing file";
                                MMLog.e(str, str2, e);
                                addView(logoForXML);
                            }
                        }
                        if (outputStream != null) {
                            outputStream.close();
                        }
                        addView(logoForXML);
                    } catch (Throwable th2) {
                        th = th2;
                        if (is != null) {
                            try {
                                is.close();
                            } catch (Exception e4) {
                                MMLog.e(TAG, "Error closing file", e4);
                                throw th;
                            }
                        }
                        if (outputStream != null) {
                            outputStream.close();
                        }
                        throw th;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    outputStream = out;
                    if (is != null) {
                        is.close();
                    }
                    if (outputStream != null) {
                        outputStream.close();
                    }
                    throw th;
                }
            }
            Bitmap bmp = BitmapFactory.decodeFile(file.getAbsolutePath());
            if (!(logoForXML == null || bmp == null)) {
                logoForXML.setImageBitmap(bmp);
            }
            if (is != null) {
                try {
                    is.close();
                } catch (Exception e5) {
                    e4 = e5;
                    str = TAG;
                    str2 = "Error closing file";
                    MMLog.e(str, str2, e4);
                    addView(logoForXML);
                }
            }
            if (outputStream != null) {
                outputStream.close();
            }
        } catch (Exception e6) {
            e4 = e6;
            MMLog.e(TAG, "Error with eclipse xml image display: ", e4);
            if (is != null) {
                is.close();
            }
            if (outputStream != null) {
                outputStream.close();
            }
            addView(logoForXML);
        }
        addView(logoForXML);
    }

    @Deprecated
    public void onClick(View v) {
        MMLog.d(TAG, "On click for " + v.getId() + " view, " + v + " adimpl" + this.adImpl);
        onTouchEvent(MotionEvent.obtain(0, System.currentTimeMillis(), 1, 0.0f, 0.0f, 0));
    }

    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        ThreadUtils.execute(new Runnable() {
            public void run() {
                float density = MMAdView.this.getContext().getResources().getDisplayMetrics().density;
                if (MMAdView.this.width <= 0) {
                    MMAdView.this.width = (int) (((float) MMAdView.this.getWidth()) / density);
                }
                if (MMAdView.this.height <= 0) {
                    MMAdView.this.height = (int) (((float) MMAdView.this.getHeight()) / density);
                }
            }
        });
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setTransitionType(int type) {
        this.transitionType = type;
    }

    void closeAreaTouched() {
        this.adImpl.unresizeToDefault();
    }

    @Deprecated
    public void onAnimationStart(Animation animation) {
    }

    @Deprecated
    public void onAnimationEnd(Animation animation) {
        this.refreshAnimationimageView.setVisibility(8);
    }

    @Deprecated
    public void onAnimationRepeat(Animation animation) {
    }

    public void getAd() {
        if (this.adImpl == null || this.adImpl.requestListener == null) {
            getAdInternal();
        } else {
            getAd(this.adImpl.requestListener);
        }
    }

    public void getAd(RequestListener listener) {
        if (this.adImpl != null) {
            this.adImpl.requestListener = listener;
        }
        getAdInternal();
    }

    private void getAdInternal() {
        if (this.adImpl != null) {
            this.adImpl.requestAd();
        }
    }

    public void onWindowFocusChanged(boolean windowInFocus) {
        super.onWindowFocusChanged(windowInFocus);
        if (windowInFocus && this.adImpl != null && this.adImpl.controller != null) {
            if (this.adImpl.controller.webView == null) {
                this.adImpl.controller.webView = MMAdImplController.getWebViewFromExistingAdImpl(this.adImpl);
            }
            MMWebView webView = this.adImpl.controller.webView;
            if (webView != null && !webView.isCurrentParent(this.adImpl.internalId) && !webView.mraidState.equals("expanded")) {
                webView.removeFromParent();
                addView(webView);
            }
        }
    }

    private synchronized void detachFromParent(View view) {
        if (view != null) {
            ViewParent parent = getParent();
            if (parent != null && (parent instanceof ViewGroup)) {
                ViewGroup group = (ViewGroup) parent;
                if (view.getParent() != null) {
                    group.removeView(view);
                }
            }
        }
    }

    private synchronized void attachToWindow(View view) {
        detachFromParent(view);
        Context context = getContext();
        if (context != null && (context instanceof Activity)) {
            Window win = ((Activity) context).getWindow();
            if (win != null) {
                View decorView = win.getDecorView();
                if (decorView != null && (decorView instanceof ViewGroup)) {
                    ((ViewGroup) decorView).addView(view);
                }
            }
        }
    }

    synchronized void handleMraidResize(DTOResizeParameters resizeParams) {
        this.refreshAnimationimageView.setImageBitmap(null);
        if (MMSDK.hasSetTranslationMethod()) {
            if (this.view == null) {
                this.view = new ResizeView(getContext());
                this.view.setId(304025022);
                this.view.setLayoutParams(new RelativeLayout.LayoutParams(1, 1));
                this.view.setBackgroundColor(0);
            }
            if (this.view.getParent() == null) {
                ViewParent adViewParent = getParent();
                if (adViewParent != null && (adViewParent instanceof ViewGroup)) {
                    ((ViewGroup) adViewParent).addView(this.view);
                }
            }
            BannerBounds bounds = new BannerBounds(resizeParams);
            if (!resizeParams.allowOffScreen) {
                bounds.calculateOnScreenBounds();
            }
            int[] location = new int[2];
            getLocationInWindow(location);
            attachToWindow(this);
            int[] locAfterAttach = new int[2];
            getLocationInWindow(locAfterAttach);
            setUnresizeParameters();
            int xOld = location[0] - locAfterAttach[0];
            int yOld = location[1] - locAfterAttach[1];
            bounds.modifyLayoutParams(getLayoutParams());
            callSetTranslationX(bounds.translationX + xOld);
            callSetTranslationY(bounds.translationY + yOld);
            setCloseArea(resizeParams.customClosePosition);
        }
    }

    synchronized void handleUnresize() {
        if (MMSDK.hasSetTranslationMethod()) {
            removeCloseTouchDelegate();
            if (!hasDefaultResizeParams()) {
                LayoutParams params = getLayoutParams();
                params.width = this.oldWidth;
                params.height = this.oldHeight;
                callSetTranslationX(0);
                callSetTranslationY(0);
                this.oldWidth = DEFAULT_RESIZE_PARAM_VALUES;
                this.oldHeight = DEFAULT_RESIZE_PARAM_VALUES;
            }
            if (this.view != null) {
                this.isResizing = true;
                this.view.attachToContext(this);
                ViewParent parent = getParent();
                if (parent != null && (parent instanceof ViewGroup)) {
                    ViewGroup group = (ViewGroup) parent;
                    if (this.view.getParent() != null) {
                        group.removeView(this.view);
                    }
                }
                this.isResizing = false;
            }
        }
    }

    private void callSetTranslationX(int translationX) {
        try {
            View.class.getMethod("setTranslationX", new Class[]{Float.TYPE}).invoke(this, new Object[]{Integer.valueOf(translationX)});
        } catch (Exception e) {
            MMLog.e(TAG, "Unable to call setTranslationX", e);
        }
    }

    private void callSetTranslationY(int translationY) {
        try {
            View.class.getMethod("setTranslationY", new Class[]{Float.TYPE}).invoke(this, new Object[]{Integer.valueOf(translationY)});
        } catch (Exception e) {
            MMLog.e(TAG, "Unable to call setTranslationY", e);
        }
    }

    private void setUnresizeParameters() {
        if (hasDefaultResizeParams()) {
            LayoutParams oldParams = getLayoutParams();
            this.oldWidth = oldParams.width;
            this.oldHeight = oldParams.height;
            if (this.oldWidth <= 0) {
                this.oldWidth = getWidth();
            }
            if (this.oldHeight <= 0) {
                this.oldHeight = getHeight();
            }
        }
    }

    private boolean hasDefaultResizeParams() {
        return this.oldWidth == DEFAULT_RESIZE_PARAM_VALUES && this.oldHeight == DEFAULT_RESIZE_PARAM_VALUES;
    }
}
