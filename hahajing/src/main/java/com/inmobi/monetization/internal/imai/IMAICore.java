package com.inmobi.monetization.internal.imai;

import android.content.Intent;
import android.net.Uri;
import com.google.android.gms.drive.DriveFile;
import com.inmobi.androidsdk.IMBrowserActivity;
import com.inmobi.commons.analytics.net.AnalyticsCommon.HttpRequestCallback;
import com.inmobi.commons.internal.InternalSDKUtil;
import com.inmobi.commons.internal.Log;
import com.inmobi.monetization.internal.Constants;
import com.inmobi.monetization.internal.configs.Initializer;
import com.inmobi.monetization.internal.imai.db.ClickData;
import com.inmobi.monetization.internal.imai.db.ClickDatabaseManager;
import com.inmobi.re.container.IMWebView;
import com.inmobi.re.container.IMWebView.ViewState;
import java.lang.ref.WeakReference;
import java.util.Random;

public class IMAICore {
    static Random a = new Random();

    public static void initialize() {
        new RequestResponseManager().init();
        ClickDatabaseManager.getInstance().setDBLimit(Initializer.getConfigParams().getImai().getmMaxDb());
    }

    public static void fireErrorEvent(WeakReference<IMWebView> weakReference, String str, String str2, String str3) {
        if (weakReference != null) {
            try {
                if (weakReference.get() != null) {
                    Log.debug(Constants.LOG_TAG, "Fire error event IMAI for action: " + str2 + " " + str);
                    injectJavaScript((IMWebView) weakReference.get(), "window._im_imai.broadcastEvent('error','" + str + "'" + ",'" + str2 + "'" + ",'" + str3 + "'" + ")");
                }
            } catch (Throwable e) {
                Log.internal(Constants.LOG_TAG, "Exception", e);
            }
        }
    }

    public static void fireOpenEmbeddedSuccessful(WeakReference<IMWebView> weakReference, String str) {
        if (weakReference != null) {
            try {
                if (weakReference.get() != null) {
                    Log.debug(Constants.LOG_TAG, "fireOpenEmbeddedSuccessful");
                    if (!(((IMWebView) weakReference.get()).mWebViewIsBrowserActivity || ((IMWebView) weakReference.get()).mIsInterstitialAd)) {
                        IMBrowserActivity.requestOnAdDismiss(((IMWebView) weakReference.get()).getWebviewHandler().obtainMessage(((IMWebView) weakReference.get()).getDismissMessage()));
                        ((IMWebView) weakReference.get()).fireOnShowAdScreen();
                    }
                    injectJavaScript((IMWebView) weakReference.get(), "window._im_imai.broadcastEvent('openEmbeddedSuccessful','" + str + "')");
                }
            } catch (Throwable e) {
                Log.internal(Constants.LOG_TAG, "Exception", e);
            }
        }
    }

    public static void fireOpenExternalSuccessful(WeakReference<IMWebView> weakReference, String str) {
        if (weakReference != null) {
            try {
                if (weakReference.get() != null) {
                    Log.debug(Constants.LOG_TAG, "fireOpenExternalSuccessful");
                    ((IMWebView) weakReference.get()).fireOnLeaveApplication();
                    injectJavaScript((IMWebView) weakReference.get(), "window._im_imai.broadcastEvent('openExternalSuccessful','" + str + "')");
                }
            } catch (Throwable e) {
                Log.internal(Constants.LOG_TAG, "Exception", e);
            }
        }
    }

    public static void firePingSuccessful(WeakReference<IMWebView> weakReference, String str) {
        if (weakReference != null) {
            try {
                if (weakReference.get() != null) {
                    Log.debug(Constants.LOG_TAG, "firePingSuccessful");
                    injectJavaScript((IMWebView) weakReference.get(), "window._im_imai.broadcastEvent('pingSuccessful','" + str + "')");
                }
            } catch (Throwable e) {
                Log.internal(Constants.LOG_TAG, "Exception", e);
            }
        }
    }

    public static void firePingInWebViewSuccessful(WeakReference<IMWebView> weakReference, String str) {
        if (weakReference != null) {
            try {
                if (weakReference.get() != null) {
                    Log.debug(Constants.LOG_TAG, "firePingInWebViewSuccessful");
                    injectJavaScript((IMWebView) weakReference.get(), "window._im_imai.broadcastEvent('pingInWebViewSuccessful','" + str + "')");
                }
            } catch (Throwable e) {
                Log.internal(Constants.LOG_TAG, "Exception", e);
            }
        }
    }

    public static void injectJavaScript(final IMWebView iMWebView, final String str) {
        try {
            iMWebView.getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    iMWebView.injectJavaScript(str);
                }
            });
        } catch (Throwable e) {
            Log.internal(Constants.LOG_TAG, "Error injecting javascript ", e);
        }
    }

    public static void launchEmbeddedBrowser(WeakReference<IMWebView> weakReference, String str) {
        Intent intent = new Intent(((IMWebView) weakReference.get()).getActivity(), IMBrowserActivity.class);
        intent.putExtra(IMBrowserActivity.EXTRA_URL, str);
        intent.putExtra(IMBrowserActivity.EXTRA_BROWSER_ACTIVITY_TYPE, 100);
        intent.setFlags(DriveFile.MODE_READ_ONLY);
        IMBrowserActivity.setWebViewListener(((IMWebView) weakReference.get()).mListener);
        if (!(((IMWebView) weakReference.get()).mWebViewIsBrowserActivity || ((IMWebView) weakReference.get()).mIsInterstitialAd || ((IMWebView) weakReference.get()).getStateVariable() != ViewState.DEFAULT)) {
            intent.putExtra("FIRST_INSTANCE", true);
        }
        ((IMWebView) weakReference.get()).getActivity().getApplicationContext().startActivity(intent);
    }

    public static void launchExternalApp(String str) {
        Intent intent = new Intent("android.intent.action.VIEW");
        intent.setData(Uri.parse(str));
        intent.setFlags(DriveFile.MODE_READ_ONLY);
        InternalSDKUtil.getContext().startActivity(intent);
    }

    public static void ping(final WeakReference<IMWebView> weakReference, final String str, final boolean z) {
        try {
            ((IMWebView) weakReference.get()).getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    try {
                        ClickData clickData = new ClickData(str, z, false, Initializer.getConfigParams().getImai().getMaxRetry());
                        RequestResponseManager requestResponseManager = new RequestResponseManager();
                        requestResponseManager.init();
                        RequestResponseManager.mNetworkQueue.add(0, clickData);
                        requestResponseManager.processClick(InternalSDKUtil.getContext(), new HttpRequestCallback(this) {
                            final /* synthetic */ AnonymousClass2 a;

                            {
                                this.a = r1;
                            }

                            public void notifyResult(int i, Object obj) {
                                try {
                                    Log.internal(Constants.LOG_TAG, "Got PING callback. Status: " + i);
                                    if (i == 0) {
                                        IMAICore.firePingSuccessful(weakReference, str);
                                    } else {
                                        IMAICore.fireErrorEvent(weakReference, "IMAI Ping in http client failed", "ping", str);
                                    }
                                } catch (Throwable e) {
                                    Log.internal(Constants.LOG_TAG, "Exception", e);
                                }
                            }
                        });
                    } catch (Throwable e) {
                        Log.internal(Constants.LOG_TAG, "Exception ping in background", e);
                    }
                }
            });
        } catch (Throwable e) {
            Log.internal(Constants.LOG_TAG, "Failed to ping", e);
        }
    }

    public static void pingInWebview(final WeakReference<IMWebView> weakReference, final String str, final boolean z) {
        try {
            ((IMWebView) weakReference.get()).getActivity().runOnUiThread(new Runnable() {
                public void run() {
                    try {
                        ClickData clickData = new ClickData(str, z, true, Initializer.getConfigParams().getImai().getMaxRetry());
                        RequestResponseManager requestResponseManager = new RequestResponseManager();
                        requestResponseManager.init();
                        RequestResponseManager.mNetworkQueue.add(0, clickData);
                        requestResponseManager.processClick(InternalSDKUtil.getContext(), new HttpRequestCallback(this) {
                            final /* synthetic */ AnonymousClass3 a;

                            {
                                this.a = r1;
                            }

                            public void notifyResult(int i, Object obj) {
                                try {
                                    Log.internal(Constants.LOG_TAG, "Got PING IN WEBVIEW callback. Status: " + i);
                                    if (i == 0) {
                                        IMAICore.firePingInWebViewSuccessful(weakReference, str);
                                    } else {
                                        IMAICore.fireErrorEvent(weakReference, "IMAI Ping in webview failed", "pingInWebview", str);
                                    }
                                } catch (Throwable e) {
                                    Log.internal(Constants.LOG_TAG, "Exception", e);
                                }
                            }
                        });
                    } catch (Throwable e) {
                        Log.internal(Constants.LOG_TAG, "Exception ping in background", e);
                    }
                }
            });
        } catch (Throwable e) {
            Log.internal(Constants.LOG_TAG, "Failed to ping in webview", e);
        }
    }

    public static boolean validateURL(String str) {
        if (str != null && !"".equals(str.trim())) {
            return true;
        }
        Log.internal(Constants.LOG_TAG, "Null url passed");
        return false;
    }

    public static int getRandomNumber() {
        return a.nextInt();
    }
}
