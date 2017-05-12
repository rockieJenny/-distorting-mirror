package com.millennialmedia.android;

import android.app.Activity;
import android.content.Context;
import java.lang.ref.WeakReference;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

abstract class MMJSObject {
    private static final String TAG = MMJSObject.class.getName();
    protected WeakReference<Context> contextRef;
    protected WeakReference<MMWebView> mmWebViewRef;

    abstract MMJSResponse executeCommand(String str, Map<String, String> map);

    MMJSObject() {
    }

    void setContext(Context context) {
        this.contextRef = new WeakReference(context);
    }

    void setMMWebView(MMWebView webView) {
        this.mmWebViewRef = new WeakReference(webView);
    }

    AdViewOverlayActivity getBaseActivity() {
        MMWebView mmWebView = (MMWebView) this.mmWebViewRef.get();
        if (mmWebView != null) {
            Activity activity = mmWebView.getActivity();
            if (activity instanceof MMActivity) {
                MMBaseActivity baseActivity = ((MMActivity) activity).getWrappedActivity();
                if (baseActivity instanceof AdViewOverlayActivity) {
                    return (AdViewOverlayActivity) baseActivity;
                }
            }
        }
        return null;
    }

    MMJSResponse runOnUiThreadFuture(Callable<MMJSResponse> callable) {
        FutureTask<MMJSResponse> future = new FutureTask(callable);
        MMSDK.runOnUiThread(future);
        try {
            return (MMJSResponse) future.get();
        } catch (InterruptedException e) {
            MMLog.e(TAG, "Future interrupted", e);
        } catch (ExecutionException e2) {
            MMLog.e(TAG, "Future execution problem: ", e2);
        }
        return null;
    }

    long getAdImplId(String creatorAdImplId) {
        if (creatorAdImplId != null) {
            return (long) Float.parseFloat(creatorAdImplId);
        }
        return -4;
    }
}
