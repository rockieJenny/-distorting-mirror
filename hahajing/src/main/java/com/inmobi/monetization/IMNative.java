package com.inmobi.monetization;

import android.view.ViewGroup;
import com.inmobi.commons.InMobi;
import com.inmobi.commons.internal.Log;
import com.inmobi.monetization.internal.AdErrorCode;
import com.inmobi.monetization.internal.Constants;
import com.inmobi.monetization.internal.IMAdListener;
import com.inmobi.monetization.internal.InvalidManifestErrorMessages;
import com.inmobi.monetization.internal.NativeAd;
import com.inmobi.monetization.internal.NativeAdObject;
import java.util.HashMap;
import java.util.Map;

public class IMNative {
    NativeAd a;
    private String b;
    private String c;
    private String d;
    private IMNativeListener e;
    private String f;
    private IMAdListener g;

    protected IMNative(String str, String str2, String str3) {
        this.b = null;
        this.c = null;
        this.d = null;
        this.e = null;
        this.g = new IMAdListener(this) {
            final /* synthetic */ IMNative a;

            {
                this.a = r1;
            }

            public void onAdRequestFailed(final AdErrorCode adErrorCode) {
                try {
                    this.a.a.getHandler().post(new Runnable(this) {
                        final /* synthetic */ AnonymousClass1 b;

                        public void run() {
                            if (this.b.a.e != null) {
                                this.b.a.e.onNativeRequestFailed(IMErrorCode.a(adErrorCode));
                            }
                        }
                    });
                } catch (Exception e) {
                    Log.debug(Constants.LOG_TAG, "Failed to give callback");
                }
            }

            public void onAdRequestSucceeded() {
                try {
                    NativeAdObject nativeAdObject = this.a.a.getNativeAdObject();
                    this.a.b = nativeAdObject.getPubContent();
                    this.a.c = nativeAdObject.getContextCode();
                    this.a.d = nativeAdObject.getNameSpace();
                    this.a.a.getHandler().post(new Runnable(this) {
                        final /* synthetic */ AnonymousClass1 a;

                        {
                            this.a = r1;
                        }

                        public void run() {
                            try {
                                if (this.a.a.e != null) {
                                    this.a.a.e.onNativeRequestSucceeded(this.a.a);
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                                Log.debug(Constants.LOG_TAG, "Failed to give callback");
                            }
                        }
                    });
                } catch (Exception e) {
                    Log.debug(Constants.LOG_TAG, "Failed to give callback");
                }
            }

            public void onShowAdScreen() {
            }

            public void onDismissAdScreen() {
            }

            public void onLeaveApplication() {
            }

            public void onAdInteraction(Map<String, String> map) {
            }

            public void onIncentCompleted(Map<Object, Object> map) {
            }
        };
        this.b = str;
        this.c = str2;
        this.d = str3;
    }

    public IMNative(String str, IMNativeListener iMNativeListener) {
        this.b = null;
        this.c = null;
        this.d = null;
        this.e = null;
        this.g = /* anonymous class already generated */;
        this.f = str;
        a(iMNativeListener);
    }

    public IMNative(IMNativeListener iMNativeListener) {
        this.b = null;
        this.c = null;
        this.d = null;
        this.e = null;
        this.g = /* anonymous class already generated */;
        this.f = InMobi.getAppId();
        a(iMNativeListener);
    }

    private void a(IMNativeListener iMNativeListener) {
        NativeAdObject nativeAdObject = new NativeAdObject(null, null, null);
        this.e = iMNativeListener;
        this.a = new NativeAd(this.f);
        this.a.setAdListener(this.g);
    }

    public void attachToView(ViewGroup viewGroup) {
        if (this.a != null) {
            this.a.attachToView(viewGroup, this.c, this.d);
        }
    }

    public void detachFromView() {
        if (this.a != null) {
            this.a.detachFromView();
        }
    }

    public void handleClick(HashMap<String, String> hashMap) {
        if (this.a != null) {
            this.a.handleClick(hashMap);
        }
    }

    public String getContent() {
        return this.b;
    }

    public void loadAd() {
        if (this.a != null) {
            this.a.loadAd();
        }
    }

    public void setKeywords(String str) {
        if (str == null || "".equals(str.trim())) {
            Log.debug(Constants.LOG_TAG, "Keywords cannot be null or blank.");
        } else if (this.a != null) {
            this.a.setKeywords(str);
        }
    }

    public void setRequestParams(Map<String, String> map) {
        if (map == null || map.isEmpty()) {
            Log.debug(Constants.LOG_TAG, "Request params cannot be null or empty.");
        } else if (this.a != null) {
            this.a.setRequestParams(map);
        }
    }

    @Deprecated
    public void setRefTagParam(String str, String str2) {
        if (str == null || str2 == null) {
            Log.debug(Constants.LOG_TAG, InvalidManifestErrorMessages.MSG_NIL_KEY_VALUE);
        } else if (str.trim().equals("") || str2.trim().equals("")) {
            Log.debug(Constants.LOG_TAG, InvalidManifestErrorMessages.MSG_EMPTY_KEY_VALUE);
        } else {
            Map hashMap = new HashMap();
            hashMap.put(str, str2);
            if (this.a != null) {
                this.a.setRequestParams(hashMap);
            }
        }
    }
}
