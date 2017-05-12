package com.inmobi.monetization.internal;

import android.content.IntentFilter;
import android.os.Handler;
import android.os.Message;
import com.inmobi.commons.InMobi;
import com.inmobi.commons.ads.cache.AdDatabaseHelper;
import com.inmobi.commons.analytics.bootstrapper.AnalyticsInitializer;
import com.inmobi.commons.data.DeviceInfo;
import com.inmobi.commons.internal.ActivityRecognitionManager;
import com.inmobi.commons.internal.EncryptionUtils;
import com.inmobi.commons.internal.InternalSDKUtil;
import com.inmobi.commons.internal.Log;
import com.inmobi.commons.internal.ThinICE;
import com.inmobi.commons.metric.EventLog;
import com.inmobi.commons.network.ErrorCode;
import com.inmobi.commons.network.Request;
import com.inmobi.commons.network.RequestBuilderUtils;
import com.inmobi.commons.network.Response;
import com.inmobi.commons.network.abstraction.INetworkListener;
import com.inmobi.commons.thirdparty.Base64;
import com.inmobi.commons.uid.UID;
import com.inmobi.monetization.internal.LtvpRuleProcessor.ActionsRule;
import com.inmobi.monetization.internal.carb.CARB;
import com.inmobi.monetization.internal.configs.Initializer;
import com.inmobi.monetization.internal.configs.NetworkEventType;
import com.inmobi.monetization.internal.imai.IMAIController;
import com.inmobi.monetization.internal.objects.LtvpRuleCache;
import com.inmobi.re.container.IMWebView;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class Ad {
    public static final String AD_TYPE_NATIVE = "native";
    protected static final String DEFAULT_NO_OF_ADS = "1";
    protected static final String KEY_AD_FORMAT = "format";
    protected static final String KEY_AD_SIZE = "mk-ad-slot";
    protected static final String KEY_NO_OF_ADS = "mk-ads";
    protected static final String KEY_PLACEMENT_SIZE = "placement-size";
    private static ConnBroadcastReciever e = null;
    boolean a = false;
    private String b = null;
    private long c = 0;
    private AtomicBoolean d = new AtomicBoolean();
    private Map<String, String> f;
    private String g = null;
    private String h = null;
    private String i = null;
    private c j = null;
    private a k = new a(this);
    private ActionsRule l = ActionsRule.MEDIATION;
    private HashMap<String, String> m = null;
    protected IMAdListener mAdListener = null;
    protected boolean mEnableHardwareAcceleration = true;
    protected long mFetchStartTime = 0;
    protected String mImpressionId = null;

    protected enum AD_FORMAT {
        IMAI,
        NATIVE
    }

    static class a extends Handler {
        private final WeakReference<Ad> a;

        public a(Ad ad) {
            this.a = new WeakReference(ad);
        }

        public void handleMessage(Message message) {
            Ad ad = (Ad) this.a.get();
            if (ad != null) {
                switch (message.what) {
                    case 101:
                        long currentTimeMillis = System.currentTimeMillis() - ad.mFetchStartTime;
                        JSONObject jSONObject = new JSONObject();
                        try {
                            jSONObject.put("t", currentTimeMillis);
                            jSONObject.put("m", 1);
                            ad.a(jSONObject, NetworkEventType.CONNECT_ERROR);
                        } catch (JSONException e) {
                            Log.internal(Constants.LOG_TAG, "Error creating metric logs for error at " + System.currentTimeMillis());
                        }
                        ad.setDownloadingNewAd(false);
                        if (ad.mAdListener != null) {
                            ad.mAdListener.onAdRequestFailed(AdErrorCode.NETWORK_ERROR);
                            return;
                        }
                        return;
                    default:
                        return;
                }
            }
        }
    }

    protected abstract Map<String, String> getAdFormatParams();

    protected abstract void handleResponse(c cVar, Response response);

    public Ad(String str) {
        if (str == null || "".equals(str.trim())) {
            this.b = InMobi.getAppId();
        } else {
            this.b = str;
        }
    }

    public Ad(long j) {
        this.c = j;
    }

    protected boolean initialize() {
        if (!InternalSDKUtil.isInitializedSuccessfully()) {
            Log.debug(Constants.LOG_TAG, "Please initialize the sdk");
            return false;
        } else if ((this.b == null || "".equals(this.b.trim())) && 0 == this.c) {
            android.util.Log.e(Constants.LOG_TAG, "Please create an instance of  ad with valid appId/ slotid");
            return false;
        } else {
            try {
                ThinICE.start(InternalSDKUtil.getContext());
            } catch (Exception e) {
                Log.internal(Constants.LOG_TAG, "Cannot start ice. Activity is null");
            }
            try {
                b.a(InternalSDKUtil.getContext());
            } catch (Throwable e2) {
                Log.internal(Constants.LOG_TAG, "IMConfigException occured while initializing interstitial while validating adView", e2);
            }
            InternalSDKUtil.getUserAgent();
            CARB.getInstance().startCarb();
            UID.getInstance().printPublisherTestId();
            IMWebView.setIMAIController(IMAIController.class);
            if (e == null) {
                e = new ConnBroadcastReciever();
            }
            InternalSDKUtil.getContext().registerReceiver(e, new IntentFilter("android.net.conn.CONNECTIVITY_CHANGE"));
            if (this.c > 0) {
                this.l = c();
            }
            return true;
        }
    }

    protected void loadAd() {
        if (InternalSDKUtil.isInitializedSuccessfully()) {
            DeviceInfo.updateDeviceInfo();
            UID.getInstance().printPublisherTestId();
            Log.debug(Constants.LOG_TAG, " >>>> Start loading new Ad <<<<");
            try {
                if (InternalSDKUtil.checkNetworkAvailibility(InternalSDKUtil.getContext())) {
                    if (!b()) {
                        if (getAdFormatParams() == null) {
                            this.f = new HashMap();
                        } else {
                            this.f = getAdFormatParams();
                        }
                        if (!this.f.containsKey(KEY_AD_FORMAT)) {
                            this.f.put(KEY_AD_FORMAT, AD_FORMAT.IMAI.toString().toLowerCase(Locale.getDefault()));
                        }
                        if (!this.f.containsKey(KEY_NO_OF_ADS)) {
                            this.f.put(KEY_NO_OF_ADS, DEFAULT_NO_OF_ADS);
                        }
                        if (!(this.b == null || "".equals(this.b))) {
                            this.f.put("mk-siteid", this.b);
                        }
                        this.j = new c();
                        this.j.b(a());
                        if (this.c > 0) {
                            this.l = c();
                            if (this.l != null) {
                                switch (this.l) {
                                    case ACTIONS_ONLY:
                                    case ACTIONS_TO_MEDIATION:
                                        this.f.put(RequestBuilderUtils.KEY_MK_SITE_SLOT_ID, Long.toString(this.c));
                                        this.f.put(RequestBuilderUtils.RULE_ID, LtvpRuleCache.getInstance(InternalSDKUtil.getContext()).getLtvpRuleId());
                                        int detectedActivity = ActivityRecognitionManager.getDetectedActivity();
                                        if (detectedActivity != -1) {
                                            this.f.put("u-activity-type", detectedActivity + "");
                                        }
                                        if (this.j != null) {
                                            this.j.setUrl(AnalyticsInitializer.getConfigParams().getEndPoints().getHouseUrl());
                                            break;
                                        }
                                        break;
                                    case MEDIATION:
                                        if (this.mAdListener != null) {
                                            this.mAdListener.onAdRequestFailed(AdErrorCode.DO_MONETIZE);
                                            break;
                                        }
                                        break;
                                    case NO_ADS:
                                        if (this.mAdListener != null) {
                                            this.mAdListener.onAdRequestFailed(AdErrorCode.DO_NOTHING);
                                            break;
                                        }
                                        break;
                                    default:
                                        if (this.mAdListener != null) {
                                            this.mAdListener.onAdRequestFailed(AdErrorCode.NO_FILL);
                                            break;
                                        }
                                        break;
                                }
                                if (!(this.l == ActionsRule.ACTIONS_ONLY || this.l == ActionsRule.ACTIONS_TO_MEDIATION)) {
                                    Log.internal(Constants.LOG_TAG, "No actions returned by rule");
                                    return;
                                }
                            }
                        }
                        this.j.a(this.f);
                        setDownloadingNewAd(true);
                        a.a().a(this.b, this.j, new INetworkListener(this) {
                            final /* synthetic */ Ad a;

                            {
                                this.a = r1;
                            }

                            public void onRequestSucceded(Request request, Response response) {
                                if (this.a.b()) {
                                    Response decryptedResponse = this.a.getDecryptedResponse((c) request, response);
                                    if (decryptedResponse == null) {
                                        this.a.handleError((c) request, response);
                                    } else {
                                        Log.internal(Constants.LOG_TAG, "Raw Ad Response: " + decryptedResponse.getResponseBody());
                                        this.a.handleResponse((c) request, decryptedResponse);
                                    }
                                    this.a.collectMetrics(response, System.currentTimeMillis() - this.a.mFetchStartTime, NetworkEventType.FETCH_COMPLETE);
                                    this.a.setDownloadingNewAd(false);
                                    this.a.k.removeMessages(101);
                                }
                            }

                            public void onRequestFailed(Request request, Response response) {
                                if (this.a.b()) {
                                    if (this.a.l != ActionsRule.ACTIONS_TO_MEDIATION) {
                                        this.a.handleError((c) request, response);
                                    } else if (this.a.mAdListener != null) {
                                        this.a.mAdListener.onAdRequestFailed(AdErrorCode.DO_MONETIZE);
                                    }
                                    this.a.setDownloadingNewAd(false);
                                }
                            }
                        });
                        this.mFetchStartTime = System.currentTimeMillis();
                        this.k.sendEmptyMessageDelayed(101, (long) Initializer.getConfigParams().getFetchTimeOut());
                        this.a = Initializer.getLogger().startNewSample();
                        return;
                    } else if (this.mAdListener != null) {
                        this.mAdListener.onAdRequestFailed(AdErrorCode.AD_DOWNLOAD_IN_PROGRESS);
                        return;
                    } else {
                        return;
                    }
                } else if (this.mAdListener != null) {
                    this.mAdListener.onAdRequestFailed(AdErrorCode.NETWORK_ERROR);
                    return;
                } else {
                    return;
                }
            } catch (Throwable e) {
                handleError(this.j, new Response(ErrorCode.INTERNAL_ERROR));
                Log.debug(Constants.LOG_TAG, "Error in loading ad ", e);
                return;
            }
        }
        Log.debug(Constants.LOG_TAG, "Please initialize the sdk");
        if (this.mAdListener != null) {
            this.mAdListener.onAdRequestFailed(AdErrorCode.INVALID_REQUEST);
        }
    }

    private Map<String, String> a() {
        Map<String, String> hashMap = new HashMap();
        if (hashMap != null) {
            if (this.m != null) {
                for (String str : this.m.keySet()) {
                    hashMap.put(str, this.m.get(str));
                }
            }
            if (!(this.h == null || this.i == null)) {
                hashMap.put(this.h, this.i);
            }
            if (this.g != null) {
                hashMap.put("p-keywords", this.g);
            }
        }
        return hashMap;
    }

    private boolean b() {
        return this.d.get();
    }

    protected void setDownloadingNewAd(boolean z) {
        this.d.set(z);
    }

    public void stopLoading() {
        if (b()) {
            if (this.k != null && this.k.hasMessages(101)) {
                this.k.removeMessages(101);
            }
            setDownloadingNewAd(false);
            Log.debug(Constants.LOG_TAG, "Stopped loading an ad");
            if (this.mAdListener != null) {
                this.mAdListener.onAdRequestFailed(AdErrorCode.ADREQUEST_CANCELLED);
            }
        }
    }

    public void setKeywords(String str) {
        if (!InternalSDKUtil.isInitializedSuccessfully()) {
            Log.debug(Constants.LOG_TAG, "Please initialize the sdk");
        } else if (str != null && !"".equals(str)) {
            this.g = str;
        }
    }

    public void setRequestParams(Map<String, String> map) {
        if (!InternalSDKUtil.isInitializedSuccessfully()) {
            Log.debug(Constants.LOG_TAG, "Please initialize the sdk");
        } else if (map != null && !map.isEmpty()) {
            if (this.m == null) {
                this.m = new HashMap();
            }
            for (String str : map.keySet()) {
                this.m.put(str, map.get(str));
            }
        }
    }

    public void setAdListener(IMAdListener iMAdListener) {
        this.mAdListener = iMAdListener;
    }

    protected void handleError(c cVar, Response response) {
        long currentTimeMillis = System.currentTimeMillis() - this.mFetchStartTime;
        if (this.mAdListener == null) {
            return;
        }
        if (response.getStatusCode() == 204) {
            this.mAdListener.onAdRequestFailed(AdErrorCode.NO_FILL);
            collectMetrics(response, currentTimeMillis, NetworkEventType.RESPONSE_ERROR);
        } else if (response.getStatusCode() == 400) {
            Log.debug(Constants.LOG_TAG, "Check the app Id passed in the ad");
            this.mAdListener.onAdRequestFailed(AdErrorCode.INVALID_APP_ID);
            collectMetrics(response, currentTimeMillis, NetworkEventType.RESPONSE_ERROR);
        } else if (response.getError() != null) {
            ErrorCode error = response.getError();
            if (error.equals(ErrorCode.INTERNAL_ERROR)) {
                this.mAdListener.onAdRequestFailed(AdErrorCode.INTERNAL_ERROR);
            } else if (error.equals(ErrorCode.INVALID_REQUEST)) {
                this.mAdListener.onAdRequestFailed(AdErrorCode.INVALID_REQUEST);
            } else if (error.equals(ErrorCode.NETWORK_ERROR)) {
                this.mAdListener.onAdRequestFailed(AdErrorCode.NETWORK_ERROR);
            } else if (error.equals(ErrorCode.CONNECTION_ERROR)) {
            }
            collectMetrics(response, currentTimeMillis, NetworkEventType.CONNECT_ERROR);
        } else {
            this.mAdListener.onAdRequestFailed(AdErrorCode.INTERNAL_ERROR);
        }
    }

    protected void collectMetrics(Response response, long j, NetworkEventType networkEventType) {
        try {
            if (this.a) {
                JSONObject jSONObject = new JSONObject();
                if (response.getStatusCode() > 400) {
                    jSONObject.put("m", response.getError());
                } else if (response.getStatusCode() != 200) {
                    jSONObject.put("m", response.getStatusCode());
                } else {
                    Map headers = response.getHeaders();
                    if (headers != null) {
                        this.mImpressionId = (String) ((List) headers.get(InvalidManifestErrorMessages.IMP_ID_KEY)).get(0);
                        String str = (String) ((List) headers.get(InvalidManifestErrorMessages.SANDBOX_ERR_KEY)).get(0);
                        if (str != null) {
                            Log.debug(Constants.LOG_TAG, "Sandbox error Id: " + str);
                        }
                    }
                    jSONObject.put(AdDatabaseHelper.TABLE_AD, this.mImpressionId);
                }
                jSONObject.put("t", j);
                Initializer.getLogger().logEvent(new EventLog(networkEventType, jSONObject));
            }
        } catch (Exception e) {
            Log.internal(Constants.LOG_TAG, "Error creating metric logs for ad fetch at " + System.currentTimeMillis());
        }
    }

    private void a(JSONObject jSONObject, NetworkEventType networkEventType) {
        if (this.a) {
            Initializer.getLogger().logEvent(new EventLog(networkEventType, jSONObject));
        }
    }

    public void destroy() {
        if (InternalSDKUtil.isInitializedSuccessfully()) {
            e = null;
        } else {
            Log.debug(Constants.LOG_TAG, "Please initialize the sdk");
        }
    }

    protected Response getDecryptedResponse(c cVar, Response response) {
        try {
            return new Response(new String(EncryptionUtils.DeAe(Base64.decode(response.getResponseBody().getBytes(), 0), cVar.b(), cVar.a())), response.getStatusCode(), response.getHeaders());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void setAppId(String str) {
        if (str == null || "".equals(str)) {
            Log.debug(Constants.LOG_TAG, "AppId cannot be null or blank.");
        } else {
            this.b = str;
        }
    }

    public void setSlotId(long j) {
        if (j > 0) {
            this.c = j;
        } else {
            Log.debug(Constants.LOG_TAG, "Invalid slot id");
        }
    }

    private ActionsRule c() {
        return LtvpRuleProcessor.getInstance().getLtvpRuleConfig(this.c);
    }

    protected void setAdRequest(c cVar) {
        this.j = cVar;
    }
}
