package com.inmobi.re.container;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.net.Uri;
import android.net.http.SslError;
import android.os.AsyncTask;
import android.os.AsyncTask.Status;
import android.os.Binder;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.os.Vibrator;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnKeyListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewParent;
import android.view.WindowManager;
import android.webkit.GeolocationPermissions.Callback;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.URLUtil;
import android.webkit.WebChromeClient;
import android.webkit.WebChromeClient.CustomViewCallback;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.AbsoluteLayout.LayoutParams;
import android.widget.FrameLayout;
import android.widget.MediaController;
import android.widget.VideoView;
import com.google.android.gms.drive.DriveFile;
import com.inmobi.androidsdk.IMBrowserActivity;
import com.inmobi.commons.data.DeviceInfo;
import com.inmobi.commons.internal.ApiStatCollector.ApiEventType;
import com.inmobi.commons.internal.InternalSDKUtil;
import com.inmobi.commons.internal.Log;
import com.inmobi.commons.internal.WrapperFunctions;
import com.inmobi.monetization.internal.imai.IMAIController;
import com.inmobi.re.configs.Initializer;
import com.inmobi.re.container.mraidimpl.MRAIDAudioVideoController;
import com.inmobi.re.container.mraidimpl.MRAIDBasic;
import com.inmobi.re.container.mraidimpl.MRAIDExpandController;
import com.inmobi.re.container.mraidimpl.MRAIDInterstitialController;
import com.inmobi.re.container.mraidimpl.MRAIDResizeController;
import com.inmobi.re.container.mraidimpl.ResizeDimensions;
import com.inmobi.re.controller.JSController.Dimensions;
import com.inmobi.re.controller.JSController.ExpandProperties;
import com.inmobi.re.controller.JSController.PlayerProperties;
import com.inmobi.re.controller.JSController.ResizeProperties;
import com.inmobi.re.controller.JSUtilityController;
import com.inmobi.re.controller.util.AVPlayer;
import com.inmobi.re.controller.util.AVPlayer.playerState;
import com.inmobi.re.controller.util.Constants;
import io.fabric.sdk.android.services.network.HttpRequest;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import org.json.JSONException;
import org.json.JSONObject;

public class IMWebView extends WebView implements Serializable {
    public static final String DIMENSIONS = "expand_dimensions";
    public static final String EXPAND_URL = "expand_url";
    private static Class<?> G = null;
    protected static final int IMWEBVIEW_INTERSTITIAL_ID = 117;
    public static final String PLAYER_PROPERTIES = "player_properties";
    private static int[] d = new int[]{16843039, 16843040};
    private static final long serialVersionUID = 7098506283154473782L;
    public static boolean userInitiatedClose = false;
    private c A;
    private ArrayList<String> B = new ArrayList();
    private AtomicBoolean C = new AtomicBoolean();
    private ViewParent D;
    private int E;
    private boolean F = false;
    private WebViewClient H = new WebViewClient(this) {
        final /* synthetic */ IMWebView a;

        {
            this.a = r1;
        }

        public void onPageStarted(WebView webView, String str, Bitmap bitmap) {
            this.a.setState(ViewState.LOADING);
            Log.debug(Constants.RENDERING_LOG_TAG, "IMWebView-> onPageStarted url: " + str + " p " + this.a.getParent());
            if (this.a.getParent() == null) {
                this.a.F = true;
            }
            if (this.a.z != null) {
                this.a.z.onPageStarted(webView, str, bitmap);
            }
            this.a.mraidLoaded = false;
        }

        public void onReceivedError(WebView webView, int i, String str, String str2) {
            Log.debug(Constants.RENDERING_LOG_TAG, "IMWebView-> error: " + str);
            if (this.a.z != null) {
                this.a.z.onReceivedError(webView, i, str, str2);
            }
            try {
                if (!(this.a.k != ViewState.LOADING || this.a.mListener == null || this.a.C.get())) {
                    this.a.mListener.onError();
                }
                this.a.w = null;
                this.a.f = true;
            } catch (Throwable e) {
                Log.debug(Constants.RENDERING_LOG_TAG, "Exception in webview loading ", e);
            }
        }

        public void onPageFinished(WebView webView, String str) {
            Log.debug(Constants.RENDERING_LOG_TAG, "IMWebView-> onPageFinished, url: " + str);
            if (this.a.z != null) {
                this.a.z.onPageFinished(webView, str);
            }
            if (!this.a.f) {
                try {
                    if (this.a.B.contains(str) && !this.a.mraidLoaded) {
                        this.a.injectJavaScript(Initializer.getMRAIDString());
                    }
                    Log.debug(Constants.RENDERING_LOG_TAG, "IMWebView-> Current State:" + this.a.k);
                    if (this.a.k == ViewState.LOADING) {
                        this.a.injectJavaScript("window.mraid.broadcastEvent('ready');");
                        this.a.injectJavaScript("window._im_imai.broadcastEvent('ready');");
                        Log.debug(Constants.RENDERING_LOG_TAG, "IMWebView-> Firing ready event at " + webView);
                        if (this.a.mOriginalWebviewForExpandUrl != null) {
                            this.a.setState(ViewState.EXPANDED);
                        } else {
                            this.a.setState(ViewState.DEFAULT);
                        }
                        if ((!this.a.mIsInterstitialAd || this.a.mWebViewIsBrowserActivity) && this.a.getVisibility() == 4) {
                            this.a.setVisibility(0);
                        }
                        if (!(this.a.w == null || this.a.C.get())) {
                            this.a.w.sendToTarget();
                        }
                        if (this.a.x != null) {
                            this.a.x.sendToTarget();
                        }
                    }
                } catch (Throwable e) {
                    Log.debug(Constants.RENDERING_LOG_TAG, "Exception in onPageFinished ", e);
                }
            }
        }

        public boolean shouldOverrideUrlLoading(WebView webView, String str) {
            Log.debug(Constants.RENDERING_LOG_TAG, "IMWebView-> shouldOverrideUrlLoading, url:" + str + "webview id" + webView);
            try {
                if (!this.a.mWebViewIsBrowserActivity) {
                    this.a.c(str);
                    return true;
                } else if (str.startsWith("http:") || str.startsWith("https:")) {
                    this.a.doHidePlayers();
                    return false;
                } else {
                    this.a.c(str);
                    return true;
                }
            } catch (Throwable e) {
                Log.internal(Constants.RENDERING_LOG_TAG, "Should override exception", e);
                return false;
            }
        }

        @TargetApi(14)
        public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
            this.a.a(sslErrorHandler, sslError);
        }

        public void onLoadResource(WebView webView, String str) {
            Log.debug(Constants.RENDERING_LOG_TAG, "IMWebView-> onLoadResource:" + str);
            try {
                if (this.a.z != null) {
                    this.a.z.onLoadResource(webView, str);
                }
                if (str != null && str.contains("/mraid.js") && !this.a.getUrl().equals("about:blank") && !this.a.getUrl().startsWith("file:")) {
                    Log.debug(Constants.RENDERING_LOG_TAG, "IMWebView-> onLoadResource:Hippy, Mraid ad alert!...injecting mraid and mraidview object at " + webView.getUrl());
                    String url = this.a.getUrl();
                    if (!this.a.B.contains(url)) {
                        this.a.B.add(url);
                    }
                    if (!this.a.mraidLoaded) {
                        this.a.injectJavaScript(Initializer.getMRAIDString());
                    }
                    this.a.mraidLoaded = true;
                }
            } catch (Throwable e) {
                Log.internal(Constants.RENDERING_LOG_TAG, "Cannot load resource", e);
            }
        }
    };
    private WebChromeClient I = new WebChromeClient(this) {
        final /* synthetic */ IMWebView a;

        {
            this.a = r1;
        }

        public boolean onJsAlert(WebView webView, String str, String str2, final JsResult jsResult) {
            Log.debug(Constants.RENDERING_LOG_TAG, "IMWebView-> onJsAlert, " + str2);
            try {
                Context expandedActivity;
                if (this.a.isExpanded() || this.a.mIsInterstitialAd) {
                    expandedActivity = this.a.getExpandedActivity();
                } else {
                    expandedActivity = webView.getContext();
                }
                new Builder(expandedActivity).setTitle(str).setMessage(str2).setPositiveButton(17039370, new OnClickListener(this) {
                    final /* synthetic */ AnonymousClass10 b;

                    public void onClick(DialogInterface dialogInterface, int i) {
                        jsResult.confirm();
                    }
                }).setCancelable(false).create().show();
            } catch (Throwable e) {
                Log.internal(Constants.RENDERING_LOG_TAG, "webchrome client exception onJSAlert ", e);
            }
            return true;
        }

        public boolean onJsConfirm(WebView webView, String str, String str2, final JsResult jsResult) {
            Log.debug(Constants.RENDERING_LOG_TAG, "IMWebView-> onJsConfirm, " + str2);
            try {
                Context expandedActivity;
                if (this.a.isExpanded() || this.a.mIsInterstitialAd) {
                    expandedActivity = this.a.getExpandedActivity();
                } else {
                    expandedActivity = webView.getContext();
                }
                Builder positiveButton = new Builder(expandedActivity).setTitle(str).setMessage(str2).setPositiveButton(17039370, new OnClickListener(this) {
                    final /* synthetic */ AnonymousClass10 b;

                    public void onClick(DialogInterface dialogInterface, int i) {
                        jsResult.confirm();
                    }
                });
                positiveButton.setNegativeButton(17039360, new OnClickListener(this) {
                    final /* synthetic */ AnonymousClass10 b;

                    public void onClick(DialogInterface dialogInterface, int i) {
                        jsResult.cancel();
                    }
                });
                positiveButton.setCancelable(false).create().show();
            } catch (Throwable e) {
                Log.internal(Constants.RENDERING_LOG_TAG, "webchrome client exception onJSConfirm ", e);
            }
            return true;
        }

        public void onShowCustomView(View view, CustomViewCallback customViewCallback) {
            this.a.n = view;
            this.a.o = customViewCallback;
            Log.debug(Constants.RENDERING_LOG_TAG, "onShowCustomView ******************************" + view);
            try {
                this.a.a(this.a.n, new OnTouchListener(this) {
                    final /* synthetic */ AnonymousClass10 a;

                    {
                        this.a = r1;
                    }

                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        return false;
                    }
                });
                this.a.n.setOnTouchListener(new OnTouchListener(this) {
                    final /* synthetic */ AnonymousClass10 a;

                    {
                        this.a = r1;
                    }

                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        return true;
                    }
                });
                if (view instanceof FrameLayout) {
                    this.a.q = (FrameLayout) view;
                    FrameLayout frameLayout = (FrameLayout) this.a.y.findViewById(16908290);
                    if (this.a.q.getFocusedChild() instanceof VideoView) {
                        Context expandedActivity;
                        this.a.m = (VideoView) this.a.q.getFocusedChild();
                        if (this.a.isExpanded() || this.a.mIsInterstitialAd) {
                            expandedActivity = this.a.getExpandedActivity();
                        } else {
                            expandedActivity = view.getContext();
                        }
                        this.a.m.setMediaController(new MediaController(expandedActivity));
                        this.a.q.setBackgroundColor(ViewCompat.MEASURED_STATE_MASK);
                        this.a.m.setOnCompletionListener(this.a.J);
                        this.a.m.setOnFocusChangeListener(new OnFocusChangeListener(this) {
                            final /* synthetic */ AnonymousClass10 a;

                            {
                                this.a = r1;
                            }

                            public void onFocusChange(View view, boolean z) {
                                this.a.a.m.requestFocus();
                            }
                        });
                        frameLayout.addView(this.a.n, new LayoutParams(-1, -1, 0, 0));
                        Log.debug(Constants.RENDERING_LOG_TAG, "Registering");
                        this.a.a(this.a.n, new OnKeyListener(this) {
                            final /* synthetic */ AnonymousClass10 a;

                            {
                                this.a = r1;
                            }

                            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                                if (4 != keyEvent.getKeyCode() || keyEvent.getAction() != 0) {
                                    return false;
                                }
                                Log.debug(Constants.RENDERING_LOG_TAG, "Back Button pressed when html5 video is playing");
                                this.a.a.m.stopPlayback();
                                this.a.a.f();
                                return true;
                            }
                        });
                        return;
                    }
                    this.a.n = view;
                    view.setBackgroundColor(ViewCompat.MEASURED_STATE_MASK);
                    Log.debug(Constants.RENDERING_LOG_TAG, "adding " + view);
                    frameLayout.addView(view, new LayoutParams(-1, -1, 0, 0));
                    this.a.n.requestFocus();
                    this.a.a(this.a.n, new OnKeyListener(this) {
                        final /* synthetic */ AnonymousClass10 a;

                        {
                            this.a = r1;
                        }

                        public boolean onKey(View view, int i, KeyEvent keyEvent) {
                            if (4 != keyEvent.getKeyCode() || keyEvent.getAction() != 0) {
                                return false;
                            }
                            Log.debug(Constants.RENDERING_LOG_TAG, "Back Button pressed when html5 video is playing");
                            this.a.a.f();
                            return true;
                        }
                    });
                }
            } catch (Throwable e) {
                Log.internal(Constants.RENDERING_LOG_TAG, "IMWebview onShowCustomView exception ", e);
            }
        }

        public void onHideCustomView() {
            this.a.f();
            super.onHideCustomView();
        }

        public void onGeolocationPermissionsShowPrompt(final String str, final Callback callback) {
            try {
                Builder builder = new Builder(this.a.y);
                builder.setTitle("Locations access");
                builder.setMessage("Allow location access").setCancelable(true).setPositiveButton("Accept", new OnClickListener(this) {
                    final /* synthetic */ AnonymousClass10 c;

                    public void onClick(DialogInterface dialogInterface, int i) {
                        callback.invoke(str, true, false);
                    }
                }).setNegativeButton("Decline", new OnClickListener(this) {
                    final /* synthetic */ AnonymousClass10 c;

                    public void onClick(DialogInterface dialogInterface, int i) {
                        callback.invoke(str, false, false);
                    }
                });
                builder.create().show();
                super.onGeolocationPermissionsShowPrompt(str, callback);
            } catch (Throwable e) {
                Log.internal(Constants.RENDERING_LOG_TAG, "Exception while accessing location from creative ", e);
                callback.invoke(str, false, false);
            }
        }
    };
    private OnCompletionListener J = new OnCompletionListener(this) {
        final /* synthetic */ IMWebView a;

        {
            this.a = r1;
        }

        public void onCompletion(MediaPlayer mediaPlayer) {
            try {
                mediaPlayer.stop();
                this.a.q.setVisibility(8);
                this.a.f();
                this.a.y.setContentView(this.a.p);
            } catch (Throwable e) {
                Log.internal(Constants.RENDERING_LOG_TAG, "Media Player onCompletion", e);
            }
        }
    };
    private boolean K = true;
    double a = -1.0d;
    public AtomicBoolean acqMutexcPos = new AtomicBoolean(true);
    public AtomicBoolean acqMutexdPos = new AtomicBoolean(true);
    AtomicBoolean b = new AtomicBoolean(false);
    InstantVideoCallbackCallback c = null;
    public JSONObject curPosition;
    public JSONObject defPosition;
    public AtomicBoolean doNotFireVisibilityChanged = new AtomicBoolean(false);
    private boolean e;
    private boolean f = false;
    private JSUtilityController g;
    private float h;
    private int i;
    public AtomicBoolean isMutexAquired = new AtomicBoolean(false);
    public boolean isTablet = false;
    private int j;
    private ViewState k = ViewState.LOADING;
    private IMWebViewPlayableListener l;
    private VideoView m;
    public MRAIDAudioVideoController mAudioVideoController;
    public MRAIDExpandController mExpandController;
    public MRAIDInterstitialController mInterstitialController;
    public boolean mIsInterstitialAd = false;
    public boolean mIsViewable = false;
    public IMWebViewListener mListener;
    public MRAIDBasic mMraidBasic;
    public IMWebView mOriginalWebviewForExpandUrl = null;
    public MRAIDResizeController mResizeController;
    public boolean mWebViewIsBrowserActivity = false;
    protected boolean mraidLoaded;
    public Object mutex = new Object();
    public Object mutexcPos = new Object();
    public Object mutexdPos = new Object();
    private View n;
    private CustomViewCallback o = null;
    private ViewGroup p;
    public int publisherOrientation;
    private FrameLayout q;
    private ArrayList<a> r;
    private boolean s = false;
    private boolean t = false;
    private boolean u = false;
    private boolean v;
    private Message w;
    public String webviewUserAgent;
    private Message x;
    private Activity y;
    private WebViewClient z;

    public interface IMWebViewListener {
        void onDismissAdScreen();

        void onDisplayFailed();

        void onError();

        void onExpand();

        void onExpandClose();

        void onIncentCompleted(Map<Object, Object> map);

        void onLeaveApplication();

        void onResize(ResizeDimensions resizeDimensions);

        void onResizeClose();

        void onShowAdScreen();

        void onUserInteraction(Map<String, String> map);
    }

    public interface IMWebViewPlayableListener {
        void onPlayableSettingsReceived(Map<String, Object> map);
    }

    public interface InstantVideoCallbackCallback {
        void postFailed(int i);

        void postSuccess();
    }

    public enum ViewState {
        LOADING,
        DEFAULT,
        RESIZED,
        EXPANDED,
        EXPANDING,
        HIDDEN,
        RESIZING
    }

    class a extends AsyncTask<Void, Void, String> {
        File a;
        String b;
        String c;
        int d = -1;
        String e = "";
        final /* synthetic */ IMWebView f;

        protected /* synthetic */ Object doInBackground(Object[] objArr) {
            return a((Void[]) objArr);
        }

        protected /* synthetic */ void onPostExecute(Object obj) {
            a((String) obj);
        }

        a(IMWebView iMWebView, File file, String str, String str2) {
            this.f = iMWebView;
            this.a = file;
            this.b = str;
            this.c = str2;
            if (iMWebView.r == null) {
                iMWebView.r = new ArrayList();
            }
            iMWebView.r.add(this);
        }

        protected String a(Void... voidArr) {
            PackageManager packageManager = this.f.y.getPackageManager();
            if ((packageManager.checkPermission("android.permission.WRITE_EXTERNAL_STORAGE", packageManager.getNameForUid(Binder.getCallingUid())) == 0 ? 1 : 0) == 0) {
                this.d = b.PERMISSION_ERROR.ordinal();
                return "failure";
            } else if (!InternalSDKUtil.checkNetworkAvailibility(InternalSDKUtil.getContext())) {
                this.d = b.NETWORK_ERROR.ordinal();
                return "failure";
            } else if (!this.c.matches("[A-Za-z0-9]+") || this.c.equals("")) {
                this.d = b.CONETNT_ID_ERROR.ordinal();
                return "failure";
            } else if (this.b.equals("") || !URLUtil.isValidUrl(this.b)) {
                this.d = b.CONTENT_URL_ERROR.ordinal();
                return "failure";
            } else if (Environment.getExternalStorageState().equals("mounted")) {
                String replace = Initializer.getConfigParams().getAllowedContentType().replace("\\", "");
                String substring = replace.substring(1, replace.length() - 1);
                String[] split = substring.contains(",") ? substring.split(",") : new String[]{substring};
                int maxSaveContentSize = Initializer.getConfigParams().getMaxSaveContentSize();
                try {
                    int i;
                    long currentTimeMillis = System.currentTimeMillis();
                    HttpURLConnection httpURLConnection = (HttpURLConnection) new URL(this.b).openConnection();
                    httpURLConnection.setRequestMethod(HttpRequest.METHOD_GET);
                    httpURLConnection.setConnectTimeout(5000);
                    String contentType = httpURLConnection.getContentType();
                    android.util.Log.i(Constants.RENDERING_LOG_TAG, "contentType_url: " + contentType);
                    for (int i2 = 0; i2 < split.length; i2++) {
                        if (split[i2].substring(1, split[i2].length() - 1).equals(contentType)) {
                            i = 1;
                            break;
                        }
                    }
                    i = 0;
                    if (i == 0) {
                        this.d = b.CONTENT_TYPE_NOT_SUPPORTED.ordinal();
                        return "failure";
                    }
                    long contentLength = (long) httpURLConnection.getContentLength();
                    if (contentLength >= 0) {
                        android.util.Log.e(Constants.RENDERING_LOG_TAG, "content size: " + contentLength);
                        android.util.Log.e(Constants.RENDERING_LOG_TAG, "max size: " + ((maxSaveContentSize * 1024) * 1024));
                        if (contentLength > ((long) ((maxSaveContentSize * 1024) * 1024))) {
                            this.d = b.CONTENT_SIZE_NOT_SUPPORTED.ordinal();
                            return "failure";
                        }
                    }
                    httpURLConnection.connect();
                    FileOutputStream fileOutputStream = new FileOutputStream(this.a);
                    InputStream inputStream = httpURLConnection.getInputStream();
                    byte[] bArr = new byte[1024];
                    long j = 0;
                    while (true) {
                        int read = inputStream.read(bArr);
                        if (read > 0) {
                            j += (long) read;
                            if (j > ((long) ((maxSaveContentSize * 1024) * 1024))) {
                                this.d = b.CONTENT_SIZE_NOT_SUPPORTED.ordinal();
                                return "failure";
                            }
                            fileOutputStream.write(bArr, 0, read);
                        } else {
                            fileOutputStream.close();
                            j = System.currentTimeMillis();
                            String str = "file://" + this.a.getAbsolutePath();
                            Log.internal(Constants.RENDERING_LOG_TAG, "file path of video: " + str);
                            JSONObject jSONObject = new JSONObject();
                            jSONObject.put("url", this.b);
                            jSONObject.put("saved_url", str);
                            jSONObject.put("size_in_bytes", this.a.length());
                            jSONObject.put("download_started_at", currentTimeMillis);
                            jSONObject.put("download_ended_at", j);
                            this.e = jSONObject.toString().replace("\"", "\\\"");
                            return "success";
                        }
                    }
                } catch (SocketTimeoutException e) {
                    Log.internal(Constants.RENDERING_LOG_TAG, "SocketTimeoutException");
                    this.d = b.NETWORK_ERROR.ordinal();
                    return "failure";
                } catch (FileNotFoundException e2) {
                    Log.internal(Constants.RENDERING_LOG_TAG, "FileNotFoundException");
                    this.d = b.CONTENT_URL_NOT_FOUND.ordinal();
                    return "failure";
                } catch (MalformedURLException e3) {
                    Log.internal(Constants.RENDERING_LOG_TAG, "MalformedURLException");
                    this.d = b.CONTENT_URL_ERROR.ordinal();
                    return "failure";
                } catch (ProtocolException e4) {
                    Log.internal(Constants.RENDERING_LOG_TAG, "ProtocolException");
                    this.d = b.NETWORK_ERROR.ordinal();
                    return "failure";
                } catch (IOException e5) {
                    Log.internal(Constants.RENDERING_LOG_TAG, "IOException");
                    this.d = b.UNKNOWN_ERROR.ordinal();
                    return "failure";
                } catch (JSONException e6) {
                    Log.internal(Constants.RENDERING_LOG_TAG, "JSONException");
                    this.d = b.UNKNOWN_ERROR.ordinal();
                    return "failure";
                } catch (Exception e7) {
                    Log.internal(Constants.RENDERING_LOG_TAG, "Unknown Exception");
                    this.d = b.UNKNOWN_ERROR.ordinal();
                    return "failure";
                }
            } else {
                this.d = b.SD_CARD_ERROR.ordinal();
                return "failure";
            }
        }

        protected void onCancelled() {
            super.onCancelled();
        }

        protected void a(String str) {
            if (str.equals("success")) {
                this.f.injectJavaScript("window.mraid.sendSaveContentResult(\"saveContent_" + this.c + "\", 'success', \"" + this.e + "\");");
                if (this.f.c != null) {
                    this.f.c.postSuccess();
                }
            } else {
                JSONObject jSONObject = new JSONObject();
                try {
                    jSONObject.put("url", this.b);
                    jSONObject.put("reason", this.d);
                    this.f.injectJavaScript("window.mraid.sendSaveContentResult(\"saveContent_" + this.c + "\", 'failure', \"" + jSONObject.toString().replace("\"", "\\\"") + "\");");
                    if (this.f.c != null) {
                        this.f.c.postFailed(this.d);
                    }
                } catch (JSONException e) {
                }
            }
            super.onPostExecute(str);
        }

        public String a() {
            return this.c;
        }
    }

    private enum b {
        UNKNOWN_ERROR,
        MISSING_PARAMETER,
        CONETNT_ID_ERROR,
        CONTENT_URL_ERROR,
        CONTENT_URL_NOT_FOUND,
        NOT_SUPPORTED_SDK,
        CONTENT_TYPE_NOT_SUPPORTED,
        CONTENT_SIZE_NOT_SUPPORTED,
        NETWORK_ERROR,
        PERMISSION_ERROR,
        SD_CARD_ERROR
    }

    static class c extends Handler {
        private final WeakReference<IMWebView> a;
        private final WeakReference<MRAIDExpandController> b;
        private final WeakReference<MRAIDResizeController> c;
        private final WeakReference<MRAIDBasic> d;
        private final WeakReference<MRAIDInterstitialController> e;
        private final WeakReference<MRAIDAudioVideoController> f;

        public c(IMWebView iMWebView, MRAIDBasic mRAIDBasic, MRAIDExpandController mRAIDExpandController, MRAIDInterstitialController mRAIDInterstitialController, MRAIDAudioVideoController mRAIDAudioVideoController, MRAIDResizeController mRAIDResizeController) {
            this.a = new WeakReference(iMWebView);
            this.b = new WeakReference(mRAIDExpandController);
            this.d = new WeakReference(mRAIDBasic);
            this.e = new WeakReference(mRAIDInterstitialController);
            this.f = new WeakReference(mRAIDAudioVideoController);
            this.c = new WeakReference(mRAIDResizeController);
        }

        public void handleMessage(Message message) {
            try {
                IMWebView iMWebView = (IMWebView) this.a.get();
                MRAIDBasic mRAIDBasic = (MRAIDBasic) this.d.get();
                MRAIDExpandController mRAIDExpandController = (MRAIDExpandController) this.b.get();
                MRAIDResizeController mRAIDResizeController = (MRAIDResizeController) this.c.get();
                MRAIDInterstitialController mRAIDInterstitialController = (MRAIDInterstitialController) this.e.get();
                MRAIDAudioVideoController mRAIDAudioVideoController = (MRAIDAudioVideoController) this.f.get();
                if (iMWebView != null) {
                    Log.debug(Constants.RENDERING_LOG_TAG, "IMWebView->handleMessage: msg: " + message);
                    Bundle data = message.getData();
                    String string;
                    AVPlayer aVPlayer;
                    AVPlayer videoPlayer;
                    HashMap hashMap;
                    switch (message.what) {
                        case 1001:
                            switch (iMWebView.k) {
                                case RESIZING:
                                case RESIZED:
                                    mRAIDResizeController.closeResized();
                                    break;
                                case EXPANDING:
                                case EXPANDED:
                                    mRAIDExpandController.closeExpanded();
                                    mRAIDExpandController.mIsExpandUrlValid = false;
                                    break;
                                case HIDDEN:
                                    break;
                                default:
                                    if (!iMWebView.mIsInterstitialAd) {
                                        iMWebView.hide();
                                        break;
                                    } else {
                                        mRAIDInterstitialController.resetContentsForInterstitials();
                                        break;
                                    }
                            }
                        case 1002:
                            iMWebView.setVisibility(4);
                            iMWebView.setState(ViewState.HIDDEN);
                            break;
                        case ApiEventType.API_IMAI_PING /*1003*/:
                            iMWebView.injectJavaScript("window.mraidview.fireChangeEvent({ state: 'default' });");
                            iMWebView.setVisibility(0);
                            break;
                        case ApiEventType.API_IMAI_PING_IN_WEB_VIEW /*1004*/:
                            if (iMWebView.k == ViewState.EXPANDING) {
                                mRAIDExpandController.doExpand(data);
                            }
                            iMWebView.g.setWebViewClosed(false);
                            break;
                        case 1005:
                            if (iMWebView.mListener != null) {
                                iMWebView.mListener.onExpandClose();
                                break;
                            }
                            break;
                        case 1006:
                            try {
                                mRAIDAudioVideoController.playVideoImpl(data, iMWebView.y);
                                break;
                            } catch (Throwable e) {
                                Log.debug(Constants.RENDERING_LOG_TAG, "Play video failed ", e);
                                break;
                            }
                        case 1007:
                            try {
                                mRAIDAudioVideoController.playAudioImpl(data, iMWebView.y);
                                break;
                            } catch (Throwable e2) {
                                Log.debug(Constants.RENDERING_LOG_TAG, "Play audio failed ", e2);
                                break;
                            }
                        case 1008:
                            string = data.getString("message");
                            iMWebView.injectJavaScript("window.mraid.broadcastEvent('error',\"" + string + "\", \"" + data.getString("action") + "\")");
                            break;
                        case 1009:
                            iMWebView.setCloseButton();
                            break;
                        case 1010:
                            aVPlayer = (AVPlayer) mRAIDAudioVideoController.audioPlayerList.get(data.getString("aplayerref"));
                            if (aVPlayer != null) {
                                aVPlayer.pause();
                                break;
                            }
                            break;
                        case 1011:
                            AVPlayer videoPlayer2 = mRAIDAudioVideoController.getVideoPlayer(data.getString("pid"));
                            if (videoPlayer2 == null) {
                                string = "window.mraid.broadcastEvent('error',\"Invalid property ID\", \"pauseVideo\")";
                            } else if (videoPlayer2.getState() == playerState.PLAYING) {
                                videoPlayer2.pause();
                                return;
                            } else if (videoPlayer2.getState() == playerState.INIT) {
                                if (!videoPlayer2.isPrepared()) {
                                    videoPlayer2.setAutoPlay(false);
                                    break;
                                }
                            } else {
                                string = "window.mraid.broadcastEvent('error',\"Invalid player state\", \"pauseVideo\")";
                            }
                            iMWebView.injectJavaScript(string);
                            break;
                        case 1012:
                            ((AVPlayer) message.obj).releasePlayer(false);
                            break;
                        case 1013:
                            string = data.getString("pid");
                            videoPlayer = mRAIDAudioVideoController.getVideoPlayer(string);
                            if (videoPlayer == null) {
                                string = "window.mraid.broadcastEvent('error',\"Invalid property ID\", \"hideVideo\")";
                            } else if (videoPlayer.getState() == playerState.RELEASED) {
                                string = "window.mraid.broadcastEvent('error',\"Invalid player state\", \"hideVideo\")";
                            } else {
                                mRAIDAudioVideoController.videoPlayerList.put(string, videoPlayer);
                                videoPlayer.hide();
                                return;
                            }
                            iMWebView.injectJavaScript(string);
                            break;
                        case 1014:
                            string = data.getString("pid");
                            videoPlayer = mRAIDAudioVideoController.getVideoPlayer(string);
                            if (videoPlayer == null) {
                                string = "window.mraid.broadcastEvent('error',\"Invalid property ID\", \"showVideo\")";
                            } else if (videoPlayer.getState() != playerState.RELEASED && videoPlayer.getState() != playerState.HIDDEN) {
                                string = "window.mraid.broadcastEvent('error',\"Invalid player state\", \"showVideo\")";
                            } else if (mRAIDAudioVideoController.videoPlayer == null || mRAIDAudioVideoController.videoPlayer.getPropertyID().equalsIgnoreCase(string)) {
                                mRAIDAudioVideoController.videoPlayerList.remove(string);
                                mRAIDAudioVideoController.videoPlayer = videoPlayer;
                                videoPlayer.show();
                                return;
                            } else {
                                string = "window.mraid.broadcastEvent('error',\"Show failed. There is already a video playing\", \"showVideo\")";
                            }
                            iMWebView.injectJavaScript(string);
                            break;
                        case 1015:
                            ((AVPlayer) message.obj).mute();
                            break;
                        case 1016:
                            ((AVPlayer) message.obj).unMute();
                            break;
                        case 1017:
                            ((AVPlayer) message.obj).setVolume(data.getInt("volume"));
                            break;
                        case 1018:
                            ((AVPlayer) message.obj).seekPlayer(data.getInt("seek") * 1000);
                            break;
                        case 1019:
                            aVPlayer = (AVPlayer) mRAIDAudioVideoController.audioPlayerList.get(data.getString("aplayerref"));
                            if (aVPlayer != null) {
                                aVPlayer.mute();
                                break;
                            }
                            break;
                        case 1020:
                            aVPlayer = (AVPlayer) mRAIDAudioVideoController.audioPlayerList.get(data.getString("aplayerref"));
                            if (aVPlayer != null) {
                                aVPlayer.unMute();
                                break;
                            }
                            break;
                        case 1021:
                            aVPlayer = (AVPlayer) mRAIDAudioVideoController.audioPlayerList.get(data.getString("aplayerref"));
                            if (aVPlayer != null) {
                                aVPlayer.setVolume(data.getInt("vol"));
                                break;
                            }
                            break;
                        case 1022:
                            ((AVPlayer) message.obj).seekPlayer(data.getInt("seekaudio") * 1000);
                            break;
                        case 1023:
                            mRAIDAudioVideoController.hidePlayers();
                            break;
                        case 1024:
                            mRAIDBasic.open(data.getString(IMWebView.EXPAND_URL));
                            break;
                        case 1025:
                            string = data.getString("injectMessage");
                            if (string != null) {
                                iMWebView.injectJavaScript(string);
                                break;
                            }
                            break;
                        case 1026:
                            mRAIDInterstitialController.handleOrientationForInterstitial();
                            break;
                        case 1027:
                            if (!iMWebView.mIsInterstitialAd) {
                                mRAIDResizeController.onOrientationChange();
                                break;
                            }
                            break;
                        case 1028:
                            if (iMWebView.mListener != null) {
                                iMWebView.mListener.onDismissAdScreen();
                                break;
                            }
                            break;
                        case 1029:
                            mRAIDBasic.getCurrentPosition();
                            break;
                        case 1030:
                            if (iMWebView.k == ViewState.RESIZING) {
                                mRAIDResizeController.doResize(data);
                                break;
                            }
                            break;
                        case 1031:
                            if (iMWebView.mListener != null) {
                                iMWebView.mListener.onResizeClose();
                                break;
                            }
                            break;
                        case 1032:
                            mRAIDBasic.getDefaultPosition();
                            break;
                        case 1033:
                            hashMap = (HashMap) message.getData().getSerializable("map");
                            if (iMWebView.mListener != null) {
                                iMWebView.mListener.onUserInteraction(hashMap);
                                break;
                            }
                            break;
                        case 1034:
                            hashMap = (HashMap) message.getData().getSerializable("incent_ad_map");
                            if (iMWebView.mListener != null) {
                                iMWebView.mListener.onIncentCompleted(hashMap);
                                break;
                            }
                            break;
                        case 1035:
                            iMWebView.disableCloseRegion();
                            break;
                    }
                }
                super.handleMessage(message);
            } catch (Throwable e22) {
                Log.internal(Constants.RENDERING_LOG_TAG, "Webview Handle Message Exception ", e22);
            }
        }
    }

    public IMWebView(Context context, IMWebViewListener iMWebViewListener) {
        super(context);
        this.mListener = iMWebViewListener;
        this.y = (Activity) context;
        c();
    }

    public IMWebView(Context context, IMWebViewListener iMWebViewListener, boolean z, boolean z2) {
        super(context);
        this.y = (Activity) context;
        this.mIsInterstitialAd = z;
        this.mWebViewIsBrowserActivity = z2;
        if (this.mIsInterstitialAd) {
            setId(IMWEBVIEW_INTERSTITIAL_ID);
        }
        this.mListener = iMWebViewListener;
        c();
    }

    public IMWebView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.y = (Activity) context;
        c();
        getContext().obtainStyledAttributes(attributeSet, d).recycle();
    }

    public void setBrowserActivity(Activity activity) {
        if (activity != null) {
            this.y = (IMBrowserActivity) activity;
        }
    }

    public static void setIMAIController(Class<?> cls) {
        G = cls;
    }

    public void setPlayableListener(IMWebViewPlayableListener iMWebViewPlayableListener) {
        this.l = iMWebViewPlayableListener;
    }

    public IMWebViewPlayableListener getPlayableListener() {
        return this.l;
    }

    public void saveOriginalViewParent() {
        if (this.D == null) {
            this.D = getParent();
            if (this.D != null) {
                int childCount = ((ViewGroup) this.D).getChildCount();
                int i = 0;
                while (i < childCount && ((ViewGroup) this.D).getChildAt(i) != this) {
                    i++;
                }
                this.E = i;
            }
        }
    }

    @SuppressLint({"SetJavaScriptEnabled", "NewApi"})
    private void c() {
        b();
        userInitiatedClose = false;
        setScrollContainer(false);
        setVerticalScrollBarEnabled(false);
        setHorizontalScrollBarEnabled(false);
        this.webviewUserAgent = getSettings().getUserAgentString();
        InternalSDKUtil.getUserAgent();
        setBackgroundColor(Initializer.getConfigParams().getWebviewBgColor());
        ((WindowManager) getContext().getSystemService("window")).getDefaultDisplay().getMetrics(new DisplayMetrics());
        if (VERSION.SDK_INT >= 17) {
            getSettings().setMediaPlaybackRequiresUserGesture(false);
        }
        this.h = this.y.getResources().getDisplayMetrics().density;
        this.e = false;
        getSettings().setJavaScriptEnabled(true);
        getSettings().setGeolocationEnabled(true);
        this.g = new JSUtilityController(this, getContext());
        addJavascriptInterface(this.g, "utilityController");
        setWebViewClient(this.H);
        setWebChromeClient(this.I);
        this.mExpandController = new MRAIDExpandController(this, this.y);
        this.mResizeController = new MRAIDResizeController(this, this.y);
        this.mMraidBasic = new MRAIDBasic(this, this.y);
        this.mInterstitialController = new MRAIDInterstitialController(this, this.y);
        this.mAudioVideoController = new MRAIDAudioVideoController(this);
        this.A = new c(this, this.mMraidBasic, this.mExpandController, this.mInterstitialController, this.mAudioVideoController, this.mResizeController);
        this.mExpandController.mSensorDisplay = ((WindowManager) this.y.getSystemService("window")).getDefaultDisplay();
        this.mAudioVideoController.videoValidateWidth = this.y.getResources().getDisplayMetrics().widthPixels;
        try {
            addJavascriptInterface(G.getDeclaredConstructor(new Class[]{IMWebView.class}).newInstance(new Object[]{this}), IMAIController.IMAI_BRIDGE);
        } catch (Exception e) {
            Log.internal(Constants.RENDERING_LOG_TAG, "Error adding js interface imai controller");
        }
        this.g.setWebViewClosed(false);
    }

    @TargetApi(8)
    private void a(final SslErrorHandler sslErrorHandler, final SslError sslError) {
        Builder builder = new Builder(this.y);
        builder.setPositiveButton("Continue", new OnClickListener(this) {
            final /* synthetic */ IMWebView b;

            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                sslErrorHandler.proceed();
            }
        });
        builder.setNegativeButton("Go Back", new OnClickListener(this) {
            final /* synthetic */ IMWebView b;

            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.cancel();
                sslErrorHandler.cancel();
            }
        });
        if (VERSION.SDK_INT >= 14) {
            builder.setNeutralButton("Open Browser", new OnClickListener(this) {
                final /* synthetic */ IMWebView b;

                public void onClick(DialogInterface dialogInterface, int i) {
                    dialogInterface.cancel();
                    this.b.c(WrapperFunctions.getSSLErrorUrl(sslError));
                }
            });
        }
        builder.setTitle("Security Warning");
        builder.setMessage("There are problems with the security certificate for this site.");
        try {
            builder.create().show();
        } catch (Throwable e) {
            Log.internal(Constants.RENDERING_LOG_TAG, "Dialog could not be shown due to an exception.", e);
        }
    }

    private void a(View view, OnTouchListener onTouchListener) {
        view.setOnTouchListener(onTouchListener);
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            int childCount = viewGroup.getChildCount();
            for (int i = 0; i < childCount; i++) {
                a(viewGroup.getChildAt(i), onTouchListener);
            }
        }
    }

    private void a(View view, OnKeyListener onKeyListener) {
        view.setOnKeyListener(onKeyListener);
        view.setFocusable(true);
        view.setFocusableInTouchMode(true);
        if (view instanceof ViewGroup) {
            ViewGroup viewGroup = (ViewGroup) view;
            int childCount = viewGroup.getChildCount();
            for (int i = 0; i < childCount; i++) {
                a(viewGroup.getChildAt(i), onKeyListener);
            }
        }
    }

    public void addJavascriptObject(Object obj, String str) {
        addJavascriptInterface(obj, str);
    }

    public void loadUrl(String str) {
        this.f = false;
        if (this.k != ViewState.EXPANDED) {
            d();
            super.loadUrl(str);
        }
    }

    public void loadData(String str, String str2, String str3) {
        this.f = false;
        super.loadData(str, str2, str3);
    }

    public void loadDataWithBaseURL(String str, String str2, String str3, String str4, String str5) {
        this.f = false;
        if (this.k != ViewState.EXPANDED) {
            d();
            super.loadDataWithBaseURL(str, str2, str3, str4, str5);
        }
    }

    private void d() {
        Log.debug(Constants.RENDERING_LOG_TAG, "IMWebView-> initStates");
        this.k = ViewState.LOADING;
        this.C.set(false);
    }

    public void hide() {
        this.A.sendEmptyMessage(1002);
    }

    public void show() {
        this.A.sendEmptyMessage(ApiEventType.API_IMAI_PING);
    }

    protected void onSizeChanged(int i, int i2, int i3, int i4) {
        super.onSizeChanged(i, i2, i3, i4);
        if (i != 0 && i2 != 0) {
            if (!this.F) {
                a((int) (((float) i) / getDensity()), (int) (((float) i2) / getDensity()));
            }
            this.F = false;
        }
    }

    private void a(int i, int i2) {
        injectJavaScript("window.mraid.broadcastEvent('sizeChange'," + i + "," + i2 + ");");
    }

    protected void onWindowVisibilityChanged(int i) {
        super.onWindowVisibilityChanged(i);
        onIMWebviewVisibilityChanged(i == 0);
        if (i != 0) {
            try {
                if (this.g.supports("vibrate")) {
                    ((Vibrator) this.y.getSystemService("vibrator")).cancel();
                }
            } catch (Throwable e) {
                Log.internal(Constants.RENDERING_LOG_TAG, "Failed to cancel existing vibration", e);
            }
        }
    }

    protected void onIMWebviewVisibilityChanged(boolean z) {
        if (this.mIsViewable != z) {
            this.mIsViewable = z;
            if (!this.doNotFireVisibilityChanged.get()) {
                a(z);
            }
        }
    }

    public void clearView() {
        e();
        super.clearView();
    }

    private void e() {
        if (this.k == ViewState.EXPANDED) {
            this.mExpandController.closeExpanded();
        }
        invalidate();
        this.g.stopAllListeners();
        resetLayout();
    }

    public boolean isBusy() {
        return this.s;
    }

    public void setBusy(boolean z) {
        this.s = z;
    }

    public void resetLayout() {
        ViewGroup.LayoutParams layoutParams = getLayoutParams();
        if (this.v) {
            layoutParams.height = this.i;
            layoutParams.width = this.j;
        }
        setVisibility(0);
        requestLayout();
    }

    public boolean isPageFinished() {
        return this.e;
    }

    public String getSize() {
        return "{ width: " + ((int) (((float) getWidth()) / this.h)) + ", " + "height: " + ((int) (((float) getHeight()) / this.h)) + "}";
    }

    protected void onAttachedToWindow() {
        Log.debug(Constants.RENDERING_LOG_TAG, "IMWebView-> onAttachedToWindow");
        saveOriginalViewParent();
        if (!this.v) {
            ViewGroup.LayoutParams layoutParams = getLayoutParams();
            this.i = layoutParams.height;
            this.j = layoutParams.width;
            this.v = true;
        }
        this.g.registerBroadcastListener();
        super.onAttachedToWindow();
    }

    public ViewState getStateVariable() {
        return this.k;
    }

    public void setState(ViewState viewState) {
        Log.debug(Constants.RENDERING_LOG_TAG, "State changing from " + this.k + " to " + viewState);
        this.k = viewState;
        if (viewState != ViewState.EXPANDING && viewState != ViewState.RESIZING) {
            injectJavaScript("window.mraid.broadcastEvent('stateChange','" + getState() + "');");
        }
    }

    public float getDensity() {
        return this.h;
    }

    public void deinit() {
        if (getStateVariable() == ViewState.EXPANDED || getStateVariable() == ViewState.EXPANDING) {
            close();
        }
    }

    protected void closeOpened(View view) {
        ((ViewGroup) ((Activity) getContext()).getWindow().getDecorView()).removeView(view);
        requestLayout();
    }

    public boolean isExpanded() {
        return this.k == ViewState.EXPANDED;
    }

    protected void onDetachedFromWindow() {
        Log.debug(Constants.RENDERING_LOG_TAG, "IMWebView-> onDetatchedFromWindow");
        this.g.stopAllListeners();
        this.B.clear();
        this.g.unRegisterBroadcastListener();
        if (this.mIsInterstitialAd && !this.mWebViewIsBrowserActivity) {
            this.mInterstitialController.handleInterstitialClose();
        }
        super.onDetachedFromWindow();
    }

    public int getDismissMessage() {
        return 1028;
    }

    public Handler getWebviewHandler() {
        return this.A;
    }

    public void fireOnLeaveApplication() {
        if (this.mListener != null) {
            this.mListener.onLeaveApplication();
        }
    }

    public void fireOnShowAdScreen() {
        if (this.mListener != null && getStateVariable() == ViewState.DEFAULT && !this.mIsInterstitialAd) {
            IMBrowserActivity.requestOnAdDismiss(this.A.obtainMessage(1028));
            this.mListener.onShowAdScreen();
        }
    }

    public void fireOnDismissAdScreen() {
        if (this.mListener != null) {
            this.mListener.onDismissAdScreen();
        }
    }

    public void pageFinishedCallbackForAdCreativeTesting(Message message) {
        this.x = message;
    }

    public void requestOnInterstitialShown(Message message) {
        this.mInterstitialController.mMsgOnInterstitialShown = message;
    }

    public void requestOnInterstitialClosed(Message message) {
        this.mInterstitialController.mMsgOnInterstitialClosed = message;
    }

    public String getState() {
        return this.k.toString().toLowerCase(Locale.ENGLISH);
    }

    public ViewState getViewState() {
        return this.k;
    }

    public void openURL(String str) {
        if (isViewable()) {
            Message obtainMessage = this.A.obtainMessage(1024);
            Bundle bundle = new Bundle();
            bundle.putString(EXPAND_URL, str);
            obtainMessage.setData(bundle);
            this.A.sendMessage(obtainMessage);
            return;
        }
        raiseError("Cannot open URL.Ad is not viewable yet", "openURL");
    }

    public void openExternal(String str) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(Uri.parse(str));
        intent.addFlags(DriveFile.MODE_READ_ONLY);
        try {
            getContext().startActivity(intent);
            if (this.mListener != null) {
                this.mListener.onLeaveApplication();
            }
        } catch (Exception e) {
            raiseError("Request must specify a valid URL", "openExternal");
        }
    }

    public void onOrientationEventChange() {
        this.A.sendEmptyMessage(1027);
    }

    public void setCustomClose(boolean z) {
        this.t = z;
        this.A.sendMessage(this.A.obtainMessage(1009));
    }

    public boolean getCustomClose() {
        return this.t;
    }

    public void close() {
        try {
            a();
            this.g.setWebViewClosed(true);
            if (!this.A.hasMessages(1001)) {
                this.A.sendEmptyMessage(1001);
            }
        } catch (Throwable e) {
            Log.internal(Constants.RENDERING_LOG_TAG, "Exception closing webview. Webview not initialized properly", e);
        }
    }

    public void closeExpanded() {
        this.A.sendEmptyMessage(1005);
    }

    public void closeResized() {
        this.A.sendEmptyMessage(1031);
    }

    public boolean isViewable() {
        return this.mIsViewable;
    }

    private void a(boolean z) {
        Log.debug(Constants.RENDERING_LOG_TAG, "Viewable:" + z);
        injectJavaScript("window.mraid.broadcastEvent('viewableChange'," + isViewable() + ");");
    }

    public String getPlacementType() {
        if (this.mIsInterstitialAd) {
            return "interstitial";
        }
        return "inline";
    }

    public String getCurrentRotation(int i) {
        String str = "-1";
        switch (i) {
            case 0:
                return "0";
            case 1:
                return "90";
            case 2:
                return "180";
            case 3:
                return "270";
            default:
                return str;
        }
    }

    public void setExpandPropertiesForInterstitial(boolean z, boolean z2, String str) {
        setCustomClose(z);
        this.mInterstitialController.orientationValueForInterstitial = str;
        this.mInterstitialController.lockOrientationValueForInterstitial = z2;
        if (this.mWebViewIsBrowserActivity) {
            this.mExpandController.handleOrientationFor2Piece();
        }
        if (isViewable() && this.mIsInterstitialAd) {
            this.A.sendEmptyMessage(1026);
        }
    }

    public void setOrientationPropertiesForInterstitial(boolean z, String str) {
        this.mInterstitialController.orientationValueForInterstitial = str;
        this.mInterstitialController.lockOrientationValueForInterstitial = z;
        if (this.mWebViewIsBrowserActivity) {
            this.mExpandController.handleOrientationFor2Piece();
        }
        if (isViewable() && this.mIsInterstitialAd) {
            this.A.sendEmptyMessage(1026);
        }
    }

    public void expand(String str, ExpandProperties expandProperties) {
        setState(ViewState.EXPANDING);
        this.mExpandController.mIsExpandUrlValid = false;
        this.isMutexAquired.set(true);
        Message obtainMessage = this.A.obtainMessage(ApiEventType.API_IMAI_PING_IN_WEB_VIEW);
        Bundle bundle = new Bundle();
        bundle.putString(EXPAND_URL, str);
        obtainMessage.setData(bundle);
        this.mExpandController.expandProperties = expandProperties;
        Log.debug(Constants.RENDERING_LOG_TAG, "Dimensions: {" + this.mExpandController.expandProperties.x + " ," + this.mExpandController.expandProperties.y + " ," + this.mExpandController.expandProperties.width + " ," + this.mExpandController.expandProperties.height + "}");
        this.mExpandController.tempExpPropsLock = this.mExpandController.expandProperties.lockOrientation;
        this.A.sendMessage(obtainMessage);
    }

    public void resize(ResizeProperties resizeProperties) {
        setState(ViewState.RESIZING);
        this.isMutexAquired.set(true);
        Message obtainMessage = this.A.obtainMessage(1030);
        this.mResizeController.resizeProperties = resizeProperties;
        this.A.sendMessage(obtainMessage);
    }

    public void lockExpandOrientation(Activity activity, boolean z, String str) {
        try {
            if (isConfigChangesListed(activity)) {
                int requestedOrientation = activity.getRequestedOrientation();
                if (requestedOrientation != 0 && requestedOrientation != 1) {
                    if (VERSION.SDK_INT >= 9 && (requestedOrientation == 8 || requestedOrientation == 9 || requestedOrientation == 6 || requestedOrientation == 7)) {
                        return;
                    }
                    if (!z) {
                        requestedOrientation = getIntegerCurrentRotation();
                        this.mExpandController.initialExpandOrientation = activity.getRequestedOrientation();
                        if (str.equalsIgnoreCase("portrait")) {
                            this.mExpandController.useLockOrient = true;
                            activity.setRequestedOrientation(WrapperFunctions.getParamPortraitOrientation(requestedOrientation));
                        } else if (str.equalsIgnoreCase("landscape")) {
                            this.mExpandController.useLockOrient = true;
                            activity.setRequestedOrientation(WrapperFunctions.getParamLandscapeOrientation(requestedOrientation));
                        } else {
                            this.mExpandController.useLockOrient = true;
                            if (activity.getResources().getConfiguration().orientation == 2) {
                                Log.internal(Constants.RENDERING_LOG_TAG, "In allowFalse, none mode dev orientation:ORIENTATION_LANDSCAPE");
                                activity.setRequestedOrientation(0);
                                return;
                            }
                            Log.internal(Constants.RENDERING_LOG_TAG, "In allowFalse, none mode dev orientation:ORIENTATION_PORTRAIT");
                            activity.setRequestedOrientation(1);
                        }
                    } else if (activity.getResources().getConfiguration().orientation == 2) {
                        Log.internal(Constants.RENDERING_LOG_TAG, "In allow true,  device orientation:ORIENTATION_LANDSCAPE");
                    } else {
                        Log.internal(Constants.RENDERING_LOG_TAG, "In allow true,  device orientation:ORIENTATION_PORTRAIT");
                    }
                }
            }
        } catch (Throwable e) {
            Log.debug(Constants.RENDERING_LOG_TAG, "Exception handling the orientation ", e);
        }
    }

    public void sendToCPHandler() {
        this.A.sendEmptyMessage(1029);
    }

    public void sendToDPHandler() {
        this.A.sendEmptyMessage(1032);
    }

    public int getIntegerCurrentRotation() {
        int displayRotation = DeviceInfo.getDisplayRotation(((WindowManager) this.y.getSystemService("window")).getDefaultDisplay());
        if (DeviceInfo.isDefOrientationLandscape(displayRotation, this.y.getResources().getDisplayMetrics().widthPixels, this.y.getResources().getDisplayMetrics().heightPixels)) {
            displayRotation++;
            if (displayRotation > 3) {
                displayRotation = 0;
            }
            if (DeviceInfo.isTablet(this.y.getApplicationContext())) {
                this.isTablet = true;
            }
        }
        return displayRotation;
    }

    public void playAudio(String str, boolean z, boolean z2, boolean z3, String str2, String str3, String str4) {
        synchronized (this.mutex) {
            if (this.isMutexAquired.get()) {
                try {
                    this.mutex.wait();
                } catch (Throwable e) {
                    Log.debug(Constants.RENDERING_LOG_TAG, "mutex failed ", e);
                }
            }
        }
        if (!this.mIsInterstitialAd && this.k != ViewState.EXPANDED) {
            raiseError("Cannot play audio.Ad is not in an expanded state", "playAudio");
        } else if (isViewable()) {
            Parcelable playerProperties = new PlayerProperties();
            playerProperties.setProperties(false, z, z2, z3, str2, str3, str4);
            Bundle bundle = new Bundle();
            bundle.putString(EXPAND_URL, str);
            bundle.putParcelable(PLAYER_PROPERTIES, playerProperties);
            Message obtainMessage = this.A.obtainMessage(1007);
            obtainMessage.setData(bundle);
            this.A.sendMessage(obtainMessage);
        } else {
            raiseError("Cannot play audio.Ad is not viewable yet", "playAudio");
        }
    }

    public void pauseAudio(String str) {
        AVPlayer currentAudioPlayer = this.mAudioVideoController.getCurrentAudioPlayer(str);
        if (currentAudioPlayer == null) {
            raiseError("Invalid property ID", "pauseAudio");
        } else if (currentAudioPlayer.getState() != playerState.PLAYING) {
            if (currentAudioPlayer.getState() != playerState.INIT || currentAudioPlayer.isPrepared()) {
                raiseError("Invalid player state", "pauseAudio");
            } else {
                currentAudioPlayer.setAutoPlay(false);
            }
        } else if (currentAudioPlayer.isPlaying()) {
            Message obtainMessage = this.A.obtainMessage(1010);
            Bundle bundle = new Bundle();
            bundle.putString("aplayerref", str);
            obtainMessage.setData(bundle);
            obtainMessage.sendToTarget();
        }
    }

    public void muteAudio(String str) {
        AVPlayer currentAudioPlayer = this.mAudioVideoController.getCurrentAudioPlayer(str);
        if (currentAudioPlayer == null) {
            raiseError("Invalid property ID", "muteAudio");
        } else if (currentAudioPlayer.getState() == playerState.RELEASED) {
            raiseError("Invalid player state", "muteAudio");
        } else {
            Message obtainMessage = this.A.obtainMessage(1019);
            Bundle bundle = new Bundle();
            bundle.putString("aplayerref", str);
            obtainMessage.setData(bundle);
            obtainMessage.sendToTarget();
        }
    }

    public void unMuteAudio(String str) {
        AVPlayer currentAudioPlayer = this.mAudioVideoController.getCurrentAudioPlayer(str);
        if (currentAudioPlayer == null) {
            raiseError("Invalid property ID", "unmuteAudio");
        } else if (currentAudioPlayer.getState() == playerState.RELEASED) {
            raiseError("Invalid player state", "unmuteAudio");
        } else {
            Message obtainMessage = this.A.obtainMessage(1020);
            Bundle bundle = new Bundle();
            bundle.putString("aplayerref", str);
            obtainMessage.setData(bundle);
            obtainMessage.sendToTarget();
        }
    }

    public boolean isAudioMuted(String str) {
        AVPlayer currentAudioPlayer = this.mAudioVideoController.getCurrentAudioPlayer(str);
        if (currentAudioPlayer != null) {
            return currentAudioPlayer.isMediaMuted();
        }
        raiseError("Invalid property ID", "isAudioMuted");
        return false;
    }

    public void setAudioVolume(String str, int i) {
        if (this.mAudioVideoController.getCurrentAudioPlayer(str) == null) {
            raiseError("Invalid property ID", "setAudioVolume");
            return;
        }
        Message obtainMessage = this.A.obtainMessage(1021);
        Bundle bundle = new Bundle();
        bundle.putInt("vol", i);
        bundle.putString("aplayerref", str);
        obtainMessage.setData(bundle);
        obtainMessage.sendToTarget();
    }

    public int getAudioVolume(String str) {
        AVPlayer currentAudioPlayer = this.mAudioVideoController.getCurrentAudioPlayer(str);
        if (currentAudioPlayer != null) {
            return currentAudioPlayer.getVolume();
        }
        raiseError("Invalid property ID", "getAudioVolume");
        return -1;
    }

    public void seekAudio(String str, int i) {
        AVPlayer currentAudioPlayer = this.mAudioVideoController.getCurrentAudioPlayer(str);
        if (currentAudioPlayer == null) {
            raiseError("Invalid property ID", "seekAudio");
        } else if (currentAudioPlayer.getState() == playerState.RELEASED) {
            raiseError("Invalid player state", "seekAudio");
        } else {
            Message obtainMessage = this.A.obtainMessage(1022);
            Bundle bundle = new Bundle();
            bundle.putInt("seekaudio", i);
            obtainMessage.setData(bundle);
            obtainMessage.obj = currentAudioPlayer;
            obtainMessage.sendToTarget();
        }
    }

    public void playVideo(String str, boolean z, boolean z2, boolean z3, boolean z4, Dimensions dimensions, String str2, String str3, String str4) {
        synchronized (this.mutex) {
            if (this.isMutexAquired.get()) {
                try {
                    this.mutex.wait();
                } catch (Throwable e) {
                    Log.debug(Constants.RENDERING_LOG_TAG, "mutex failed ", e);
                }
            }
        }
        if (!this.mIsInterstitialAd && this.k != ViewState.EXPANDED) {
            raiseError("Cannot play video.Ad is not in an expanded state", "playVideo");
        } else if (!isViewable()) {
            raiseError("Cannot play video.Ad is not viewable yet", "playVideo");
        } else if (this.mAudioVideoController.videoPlayerList.isEmpty() || this.mAudioVideoController.videoPlayerList.size() < 5 || this.mAudioVideoController.videoPlayerList.containsKey(str4)) {
            Message obtainMessage = this.A.obtainMessage(1006);
            Parcelable playerProperties = new PlayerProperties();
            playerProperties.setProperties(z, z2, z3, z4, str2, str3, str4);
            Bundle bundle = new Bundle();
            bundle.putString(EXPAND_URL, str);
            bundle.putParcelable(PLAYER_PROPERTIES, playerProperties);
            Log.debug(Constants.RENDERING_LOG_TAG, "Before validation dimension: (" + dimensions.x + ", " + dimensions.y + ", " + dimensions.width + ", " + dimensions.height + ")");
            this.mAudioVideoController.validateVideoDimensions(dimensions);
            Log.debug(Constants.RENDERING_LOG_TAG, "After validation dimension: (" + dimensions.x + ", " + dimensions.y + ", " + dimensions.width + ", " + dimensions.height + ")");
            bundle.putParcelable(DIMENSIONS, dimensions);
            obtainMessage.setData(bundle);
            this.A.sendMessage(obtainMessage);
        } else {
            raiseError("Player Error. Exceeding permissible limit for saved play instances", "playVideo");
        }
    }

    public void pauseVideo(String str) {
        Message obtainMessage = this.A.obtainMessage(1011);
        Bundle bundle = new Bundle();
        bundle.putString("pid", str);
        obtainMessage.setData(bundle);
        this.A.sendMessage(obtainMessage);
    }

    public void closeVideo(String str) {
        AVPlayer videoPlayer = this.mAudioVideoController.getVideoPlayer(str);
        if (videoPlayer == null) {
            raiseError("Invalid property ID", "closeVideo");
        } else if (videoPlayer.getState() == playerState.RELEASED) {
            raiseError("Invalid player state", "closeVideo");
        } else {
            this.mAudioVideoController.videoPlayerList.remove(str);
            Message obtainMessage = this.A.obtainMessage(1012);
            obtainMessage.obj = videoPlayer;
            this.A.sendMessage(obtainMessage);
        }
    }

    public void hideVideo(String str) {
        Message obtainMessage = this.A.obtainMessage(1013);
        Bundle bundle = new Bundle();
        bundle.putString("pid", str);
        obtainMessage.setData(bundle);
        this.A.sendMessage(obtainMessage);
    }

    public void showVideo(String str) {
        Message obtainMessage = this.A.obtainMessage(1014);
        Bundle bundle = new Bundle();
        bundle.putString("pid", str);
        obtainMessage.setData(bundle);
        this.A.sendMessage(obtainMessage);
    }

    public void muteVideo(String str) {
        AVPlayer videoPlayer = this.mAudioVideoController.getVideoPlayer(str);
        if (videoPlayer == null) {
            raiseError("Invalid property ID", "muteVideo");
        } else if (videoPlayer.getState() == playerState.RELEASED || videoPlayer.getState() == playerState.INIT) {
            raiseError("Invalid player state", "muteVideo");
        } else {
            Message obtainMessage = this.A.obtainMessage(1015);
            obtainMessage.obj = videoPlayer;
            this.A.sendMessage(obtainMessage);
        }
    }

    public void unMuteVideo(String str) {
        AVPlayer videoPlayer = this.mAudioVideoController.getVideoPlayer(str);
        if (videoPlayer == null) {
            raiseError("Invalid property ID", "unMuteVideo");
        } else if (videoPlayer.getState() == playerState.RELEASED || videoPlayer.getState() == playerState.INIT) {
            raiseError("Invalid player state", "unMuteVideo");
        } else {
            Message obtainMessage = this.A.obtainMessage(1016);
            obtainMessage.obj = videoPlayer;
            this.A.sendMessage(obtainMessage);
        }
    }

    public void seekVideo(String str, int i) {
        AVPlayer videoPlayer = this.mAudioVideoController.getVideoPlayer(str);
        if (videoPlayer == null) {
            raiseError("Invalid property ID", "seekVideo");
        } else if (videoPlayer.getState() == playerState.RELEASED || videoPlayer.getState() == playerState.INIT) {
            raiseError("Invalid player state", "seekVideo");
        } else {
            Message obtainMessage = this.A.obtainMessage(1018);
            Bundle bundle = new Bundle();
            bundle.putInt("seek", i);
            obtainMessage.setData(bundle);
            obtainMessage.obj = videoPlayer;
            this.A.sendMessage(obtainMessage);
        }
    }

    public boolean isVideoMuted(String str) {
        AVPlayer videoPlayer = this.mAudioVideoController.getVideoPlayer(str);
        if (videoPlayer != null) {
            return videoPlayer.isMediaMuted();
        }
        raiseError("Invalid property ID", "isVideoMuted");
        return false;
    }

    public void setVideoVolume(String str, int i) {
        AVPlayer videoPlayer = this.mAudioVideoController.getVideoPlayer(str);
        if (videoPlayer == null) {
            raiseError("Invalid property ID", "setVideoVolume");
        } else if (videoPlayer.getState() == playerState.RELEASED) {
            raiseError("Invalid player state", "setVideoVolume");
        } else {
            Message obtainMessage = this.A.obtainMessage(1017);
            Bundle bundle = new Bundle();
            bundle.putInt("volume", i);
            obtainMessage.setData(bundle);
            obtainMessage.obj = videoPlayer;
            this.A.sendMessage(obtainMessage);
        }
    }

    public int getVideoVolume(String str) {
        AVPlayer videoPlayer = this.mAudioVideoController.getVideoPlayer(str);
        if (videoPlayer != null) {
            return videoPlayer.getVolume();
        }
        raiseError("Invalid property ID", "getVideoVolume");
        return -1;
    }

    public void postInjectJavaScript(String str) {
        if (str != null) {
            Message obtainMessage = this.A.obtainMessage(1025);
            Bundle bundle = new Bundle();
            bundle.putString("injectMessage", str);
            obtainMessage.setData(bundle);
            obtainMessage.sendToTarget();
        }
    }

    public void injectJavaScript(String str) {
        if (str != null) {
            try {
                String str2 = "javascript:try{" + str + "}catch(e){}";
                if (str2.length() < 400) {
                    Log.debug(Constants.RENDERING_LOG_TAG, "Injecting JavaScript: " + str2);
                }
                if (!g()) {
                    if (VERSION.SDK_INT < 19) {
                        a(str2);
                    } else {
                        b(str2);
                    }
                }
            } catch (Throwable e) {
                Log.internal(Constants.RENDERING_LOG_TAG, "Error injecting javascript ", e);
            }
        }
    }

    private void a(String str) {
        super.loadUrl(str);
    }

    @TargetApi(19)
    private void b(String str) {
        evaluateJavascript(str, null);
    }

    public void broadcastEventIMraid(final String str, final Object obj) {
        try {
            this.y.runOnUiThread(new Runnable(this) {
                final /* synthetic */ IMWebView c;

                public void run() {
                    if (obj != null) {
                        this.c.injectJavaScript("window.imraid.broadcastEvent('" + str + "'," + obj + ");");
                    } else {
                        this.c.injectJavaScript("window.imraid.broadcastEvent('" + str + "');");
                    }
                }
            });
        } catch (Throwable e) {
            Log.internal(Constants.RENDERING_LOG_TAG, "Exception broadcasting events", e);
        }
    }

    public void broadcastEventIMraid(final String str, final String str2) {
        try {
            this.y.runOnUiThread(new Runnable(this) {
                final /* synthetic */ IMWebView c;

                public void run() {
                    if (str2 != null) {
                        this.c.injectJavaScript("window.imraid.broadcastEvent('" + str + "','" + str2 + "');");
                    } else {
                        this.c.injectJavaScript("window.imraid.broadcastEvent('" + str + "');");
                    }
                }
            });
        } catch (Throwable e) {
            Log.internal(Constants.RENDERING_LOG_TAG, "Exception broadcasting events", e);
        }
    }

    public boolean isConfigChangesListed(Activity activity) {
        boolean z;
        int i = VERSION.SDK_INT;
        int a = a(activity);
        if ((a & 16) == 0 || (a & 32) == 0 || (a & 128) == 0) {
            z = false;
        } else {
            z = true;
        }
        boolean z2;
        if (i < 13 || !((a & 1024) == 0 || (a & 2048) == 0)) {
            z2 = true;
        } else {
            z2 = false;
        }
        if (z && r2) {
            return true;
        }
        return false;
    }

    private int a(Activity activity) {
        for (ResolveInfo resolveInfo : activity.getPackageManager().queryIntentActivities(new Intent(activity, activity.getClass()), 65536)) {
            if (resolveInfo.activityInfo.name.contentEquals(activity.getClass().getName())) {
                break;
            }
        }
        ResolveInfo resolveInfo2 = null;
        return resolveInfo2.activityInfo.configChanges;
    }

    public void raiseError(String str, String str2) {
        Message obtainMessage = this.A.obtainMessage(1008);
        Bundle bundle = new Bundle();
        bundle.putString("message", str);
        bundle.putString("action", str2);
        obtainMessage.setData(bundle);
        this.A.sendMessage(obtainMessage);
    }

    public void setActivity(Activity activity) {
        this.y = activity;
    }

    public Activity getActivity() {
        return this.y;
    }

    public ArrayList<String> getMRAIDUrls() {
        return this.B;
    }

    private void f() {
        if (this.n != null) {
            if (this.o != null) {
                this.o.onCustomViewHidden();
            }
            this.o = null;
            if (!(this.n == null || this.n.getParent() == null)) {
                ((ViewGroup) this.n.getParent()).removeView(this.n);
            }
            this.n = null;
        }
    }

    public void setCloseButton() {
        try {
            CustomView customView = (CustomView) ((ViewGroup) getRootView()).findViewById(IMBrowserActivity.CLOSE_BUTTON_VIEW_ID);
            if (customView != null) {
                customView.setVisibility(getCustomClose() ? 8 : 0);
            }
        } catch (Exception e) {
        }
    }

    public void disableCloseRegion() {
        CustomView customView = (CustomView) ((ViewGroup) getRootView()).findViewById(IMBrowserActivity.CLOSE_REGION_VIEW_ID);
        if (customView != null) {
            customView.disableView(this.u);
        }
    }

    public boolean isPortraitSyncOrientation(int i) {
        return i == 0 || i == 2;
    }

    public boolean isLandscapeSyncOrientation(int i) {
        return i == 1 || i == 3;
    }

    public void setExternalWebViewClient(WebViewClient webViewClient) {
        this.z = webViewClient;
    }

    public void doHidePlayers() {
        this.A.sendEmptyMessage(1023);
    }

    public void cancelLoad() {
        this.C.set(true);
    }

    public void sendasyncPing(String str) {
        this.g.asyncPing(str);
    }

    public void setOriginalParent(ViewParent viewParent) {
        this.D = viewParent;
    }

    public ViewParent getOriginalParent() {
        if (this.D == null) {
            saveOriginalViewParent();
        }
        return this.D;
    }

    public int getOriginalIndex() {
        return this.E;
    }

    public void raiseMicEvent(double d) {
        this.a = d;
        final String str = "window.mraid.broadcastEvent('micIntensityChange'," + d + ")";
        if (this.y != null) {
            this.y.runOnUiThread(new Runnable(this) {
                final /* synthetic */ IMWebView b;

                public void run() {
                    this.b.injectJavaScript(str);
                }
            });
        }
    }

    public void raiseCameraPictureCapturedEvent(String str, int i, int i2) {
        final String str2 = "window.mraidview.fireCameraPictureCatpturedEvent('" + str + "'" + "," + "'" + i + "','" + i2 + "')";
        if (this.y != null) {
            this.y.runOnUiThread(new Runnable(this) {
                final /* synthetic */ IMWebView b;

                public void run() {
                    this.b.injectJavaScript(str2);
                }
            });
        }
    }

    public void raiseVibrateCompleteEvent() {
        if (this.y != null) {
            this.y.runOnUiThread(new Runnable(this) {
                final /* synthetic */ IMWebView a;

                {
                    this.a = r1;
                }

                public void run() {
                    try {
                        this.a.injectJavaScript("window.mraid.broadcastEvent('vibrateComplete')");
                    } catch (Throwable e) {
                        Log.internal(Constants.RENDERING_LOG_TAG, "Exception giviing vibration complete callback", e);
                    }
                }
            });
        }
    }

    public void raiseGalleryImageSelectedEvent(String str, int i, int i2) {
        final String str2 = "window.mraidview.fireGalleryImageSelectedEvent('" + str + "'" + "," + "'" + i + "','" + i2 + "')";
        if (this.y != null) {
            this.y.runOnUiThread(new Runnable(this) {
                final /* synthetic */ IMWebView b;

                public void run() {
                    this.b.injectJavaScript(str2);
                }
            });
        }
    }

    public void resetMraid() {
        this.mExpandController.reset();
        this.mResizeController.reset();
        this.g.reset();
    }

    public boolean isModal() {
        return this.mIsInterstitialAd || this.k == ViewState.EXPANDED;
    }

    private void c(String str) {
        Intent intent = new Intent("android.intent.action.VIEW", Uri.parse(str));
        intent.addFlags(DriveFile.MODE_READ_ONLY);
        this.y.startActivity(intent);
        fireOnLeaveApplication();
    }

    public void mediaPlayerReleased(AVPlayer aVPlayer) {
        this.mAudioVideoController.mediaPlayerReleased(aVPlayer);
    }

    public boolean onTouchEvent(MotionEvent motionEvent) {
        requestFocus();
        return super.onTouchEvent(motionEvent);
    }

    public double getLastGoodKnownMicValue() {
        return this.a;
    }

    public void postInHandler(Runnable runnable) {
        this.A.post(runnable);
    }

    public void destroy() {
        Log.debug(Constants.RENDERING_LOG_TAG, "IMWebView: Destroy called.");
        close();
        postInHandler(new Runnable(this) {
            final /* synthetic */ IMWebView a;

            {
                this.a = r1;
            }

            public void run() {
                if (this.a.getParent() != null) {
                    ((ViewGroup) this.a.getParent()).removeView(this.a);
                }
                this.a.b.set(true);
                super.destroy();
            }
        });
    }

    private boolean g() {
        return this.b.get();
    }

    public void disableHardwareAcceleration() {
        this.K = false;
        Log.internal(Constants.RENDERING_LOG_TAG, "disableHardwareAcceleration called.");
        if (VERSION.SDK_INT >= 14) {
            WrapperFunctions.disableHardwareAccl(this);
            this.mExpandController.disableEnableHardwareAccelerationForExpandWithURLView();
        }
    }

    public boolean isEnabledHardwareAcceleration() {
        return this.K;
    }

    public void userInteraction(HashMap<String, String> hashMap) {
        Message obtainMessage = this.A.obtainMessage(1033);
        Bundle bundle = new Bundle();
        bundle.putSerializable("map", hashMap);
        obtainMessage.setData(bundle);
        obtainMessage.sendToTarget();
    }

    public void saveFile(File file, String str, String str2) {
        new a(this, file, str, str2).execute(new Void[0]);
    }

    void a() {
        try {
            if (this.r != null) {
                for (int i = 0; i < this.r.size(); i++) {
                    a aVar = (a) this.r.get(i);
                    if (aVar.getStatus() == Status.RUNNING) {
                        aVar.cancel(true);
                    }
                }
                this.r.clear();
                this.r = null;
            }
            b();
        } catch (Exception e) {
            Log.internal(Constants.RENDERING_LOG_TAG, "Exception deleting saved content dirs and stopping download task");
        }
    }

    void b() {
        try {
            File file = new File(InternalSDKUtil.getContext().getExternalFilesDir(null) + "/im_cached_content/");
            if (file.exists() && file.isDirectory()) {
                String[] list = file.list();
                for (String file2 : list) {
                    new File(file, file2).delete();
                }
                file.delete();
            }
        } catch (Exception e) {
            Log.internal(Constants.RENDERING_LOG_TAG, "Exception deleting saved content dirs and stopping download task");
        }
    }

    public void cancelSaveContent(String str) {
        if (this.r != null) {
            for (int i = 0; i < this.r.size(); i++) {
                a aVar = (a) this.r.get(i);
                if (aVar.a().equals(str) && aVar.getStatus() == Status.RUNNING) {
                    aVar.cancel(true);
                    return;
                }
            }
        }
    }

    public void setCallBack(InstantVideoCallbackCallback instantVideoCallbackCallback) {
        this.c = instantVideoCallbackCallback;
    }

    public Activity getExpandedActivity() {
        return this.y;
    }

    public void incentCompleted(HashMap<Object, Object> hashMap) {
        Message obtainMessage = this.A.obtainMessage(1034);
        Bundle bundle = new Bundle();
        bundle.putSerializable("incent_ad_map", hashMap);
        obtainMessage.setData(bundle);
        obtainMessage.sendToTarget();
    }

    public void setDisableCloseRegion(boolean z) {
        this.u = z;
        this.A.sendMessage(this.A.obtainMessage(1035));
    }

    public boolean getDisableCloseRegion() {
        return this.u;
    }
}
