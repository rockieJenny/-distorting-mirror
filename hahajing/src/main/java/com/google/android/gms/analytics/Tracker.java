package com.google.android.gms.analytics;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import com.google.analytics.tracking.android.Fields;
import com.google.analytics.tracking.android.HitTypes;
import com.google.android.gms.internal.jx;
import com.google.android.gms.internal.ld;
import com.google.android.gms.internal.lf;
import com.inmobi.commons.analytics.iat.impl.AdTrackerConstants;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

public class Tracker {
    private final TrackerHandler Ce;
    private final Map<String, String> Cf;
    private ah Cg;
    private final k Ch;
    private final ai Ci;
    private final g Cj;
    private boolean Ck;
    private a Cl;
    private am Cm;
    private ExceptionReporter Cn;
    private Context mContext;
    private final Map<String, String> rd;

    private class a implements a {
        private boolean Co = false;
        private int Cp = 0;
        private long Cq = -1;
        private boolean Cr = false;
        private long Cs;
        final /* synthetic */ Tracker Ct;
        private ld wb = lf.if();

        public a(Tracker tracker) {
            this.Ct = tracker;
        }

        private void fq() {
            GoogleAnalytics eY = GoogleAnalytics.eY();
            if (eY == null) {
                ae.T("GoogleAnalytics isn't initialized for the Tracker!");
            } else if (this.Cq >= 0 || this.Co) {
                eY.a(this.Ct.Cl);
            } else {
                eY.b(this.Ct.Cl);
            }
        }

        public void enableAutoActivityTracking(boolean enabled) {
            this.Co = enabled;
            fq();
        }

        public long fn() {
            return this.Cq;
        }

        public boolean fo() {
            return this.Co;
        }

        public boolean fp() {
            boolean z = this.Cr;
            this.Cr = false;
            return z;
        }

        boolean fr() {
            return this.wb.elapsedRealtime() >= this.Cs + Math.max(1000, this.Cq);
        }

        public void i(Activity activity) {
            y.eK().a(com.google.android.gms.analytics.y.a.EASY_TRACKER_ACTIVITY_START);
            if (this.Cp == 0 && fr()) {
                this.Cr = true;
            }
            this.Cp++;
            if (this.Co) {
                Intent intent = activity.getIntent();
                if (intent != null) {
                    this.Ct.setCampaignParamsOnNextHit(intent.getData());
                }
                Map hashMap = new HashMap();
                hashMap.put(Fields.HIT_TYPE, "screenview");
                y.eK().D(true);
                this.Ct.set("&cd", this.Ct.Cm != null ? this.Ct.Cm.k(activity) : activity.getClass().getCanonicalName());
                this.Ct.send(hashMap);
                y.eK().D(false);
            }
        }

        public void j(Activity activity) {
            y.eK().a(com.google.android.gms.analytics.y.a.EASY_TRACKER_ACTIVITY_STOP);
            this.Cp--;
            this.Cp = Math.max(0, this.Cp);
            if (this.Cp == 0) {
                this.Cs = this.wb.elapsedRealtime();
            }
        }

        public void setSessionTimeout(long sessionTimeout) {
            this.Cq = sessionTimeout;
            fq();
        }
    }

    Tracker(String trackingId, TrackerHandler handler, Context context) {
        this(trackingId, handler, k.el(), ai.fl(), g.dZ(), new ad("tracking"), context);
    }

    Tracker(String trackingId, TrackerHandler handler, k clientIdDefaultProvider, ai screenResolutionDefaultProvider, g appFieldsDefaultProvider, ah rateLimiter, Context context) {
        this.rd = new HashMap();
        this.Cf = new HashMap();
        this.Ce = handler;
        if (context != null) {
            this.mContext = context.getApplicationContext();
        }
        if (trackingId != null) {
            this.rd.put(Fields.TRACKING_ID, trackingId);
        }
        this.rd.put(Fields.USE_SECURE, "1");
        this.Ch = clientIdDefaultProvider;
        this.Ci = screenResolutionDefaultProvider;
        this.Cj = appFieldsDefaultProvider;
        this.rd.put("&a", Integer.toString(new Random().nextInt(Integer.MAX_VALUE) + 1));
        this.Cg = rateLimiter;
        this.Cl = new a(this);
        enableAdvertisingIdCollection(false);
    }

    void a(am amVar) {
        ae.V("Loading Tracker config values.");
        this.Cm = amVar;
        if (this.Cm.ft()) {
            String fu = this.Cm.fu();
            set(Fields.TRACKING_ID, fu);
            ae.V("[Tracker] trackingId loaded: " + fu);
        }
        if (this.Cm.fv()) {
            fu = Double.toString(this.Cm.fw());
            set(Fields.SAMPLE_RATE, fu);
            ae.V("[Tracker] sample frequency loaded: " + fu);
        }
        if (this.Cm.fx()) {
            setSessionTimeout((long) this.Cm.getSessionTimeout());
            ae.V("[Tracker] session timeout loaded: " + fn());
        }
        if (this.Cm.fy()) {
            enableAutoActivityTracking(this.Cm.fz());
            ae.V("[Tracker] auto activity tracking loaded: " + fo());
        }
        if (this.Cm.fA()) {
            if (this.Cm.fB()) {
                set(Fields.ANONYMIZE_IP, "1");
                ae.V("[Tracker] anonymize ip loaded: true");
            }
            ae.V("[Tracker] anonymize ip loaded: false");
        }
        enableExceptionReporting(this.Cm.fC());
    }

    public void enableAdvertisingIdCollection(boolean enabled) {
        if (enabled) {
            if (this.rd.containsKey("&ate")) {
                this.rd.remove("&ate");
            }
            if (this.rd.containsKey("&adid")) {
                this.rd.remove("&adid");
                return;
            }
            return;
        }
        this.rd.put("&ate", null);
        this.rd.put("&adid", null);
    }

    public void enableAutoActivityTracking(boolean enabled) {
        this.Cl.enableAutoActivityTracking(enabled);
    }

    public void enableExceptionReporting(boolean enabled) {
        if (this.Ck != enabled) {
            this.Ck = enabled;
            if (enabled) {
                this.Cn = new ExceptionReporter(this, Thread.getDefaultUncaughtExceptionHandler(), this.mContext);
                Thread.setDefaultUncaughtExceptionHandler(this.Cn);
                ae.V("Uncaught exceptions will be reported to Google Analytics.");
                return;
            }
            if (this.Cn != null) {
                Thread.setDefaultUncaughtExceptionHandler(this.Cn.et());
            } else {
                Thread.setDefaultUncaughtExceptionHandler(null);
            }
            ae.V("Uncaught exceptions will not be reported to Google Analytics.");
        }
    }

    long fn() {
        return this.Cl.fn();
    }

    boolean fo() {
        return this.Cl.fo();
    }

    public String get(String key) {
        y.eK().a(com.google.android.gms.analytics.y.a.GET);
        if (TextUtils.isEmpty(key)) {
            return null;
        }
        if (this.rd.containsKey(key)) {
            return (String) this.rd.get(key);
        }
        if (key.equals(Fields.LANGUAGE)) {
            return an.a(Locale.getDefault());
        }
        if (this.Ch != null && this.Ch.ac(key)) {
            return this.Ch.getValue(key);
        }
        if (this.Ci == null || !this.Ci.ac(key)) {
            return (this.Cj == null || !this.Cj.ac(key)) ? null : this.Cj.getValue(key);
        } else {
            return this.Ci.getValue(key);
        }
    }

    public void send(Map<String, String> params) {
        y.eK().a(com.google.android.gms.analytics.y.a.SEND);
        Map hashMap = new HashMap();
        hashMap.putAll(this.rd);
        if (params != null) {
            hashMap.putAll(params);
        }
        for (String str : this.Cf.keySet()) {
            if (!hashMap.containsKey(str)) {
                hashMap.put(str, this.Cf.get(str));
            }
        }
        this.Cf.clear();
        if (TextUtils.isEmpty((CharSequence) hashMap.get(Fields.TRACKING_ID))) {
            ae.W(String.format("Missing tracking id (%s) parameter.", new Object[]{Fields.TRACKING_ID}));
        }
        String str2 = (String) hashMap.get(Fields.HIT_TYPE);
        if (TextUtils.isEmpty(str2)) {
            ae.W(String.format("Missing hit type (%s) parameter.", new Object[]{Fields.HIT_TYPE}));
            str2 = "";
        }
        if (this.Cl.fp()) {
            hashMap.put(Fields.SESSION_CONTROL, "start");
        }
        String toLowerCase = str2.toLowerCase();
        if ("screenview".equals(toLowerCase) || "pageview".equals(toLowerCase) || HitTypes.APP_VIEW.equals(toLowerCase) || TextUtils.isEmpty(toLowerCase)) {
            int parseInt = Integer.parseInt((String) this.rd.get("&a")) + 1;
            if (parseInt >= Integer.MAX_VALUE) {
                parseInt = 1;
            }
            this.rd.put("&a", Integer.toString(parseInt));
        }
        if (toLowerCase.equals(HitTypes.TRANSACTION) || toLowerCase.equals(HitTypes.ITEM) || this.Cg.fe()) {
            this.Ce.u(hashMap);
        } else {
            ae.W("Too many hits sent too quickly, rate limiting invoked.");
        }
    }

    public void set(String key, String value) {
        jx.b((Object) key, (Object) "Key should be non-null");
        y.eK().a(com.google.android.gms.analytics.y.a.SET);
        this.rd.put(key, value);
    }

    public void setAnonymizeIp(boolean anonymize) {
        set(Fields.ANONYMIZE_IP, an.E(anonymize));
    }

    public void setAppId(String appId) {
        set(Fields.APP_ID, appId);
    }

    public void setAppInstallerId(String appInstallerId) {
        set(Fields.APP_INSTALLER_ID, appInstallerId);
    }

    public void setAppName(String appName) {
        set(Fields.APP_NAME, appName);
    }

    public void setAppVersion(String appVersion) {
        set(Fields.APP_VERSION, appVersion);
    }

    public void setCampaignParamsOnNextHit(Uri uri) {
        if (uri != null) {
            Object queryParameter = uri.getQueryParameter(AdTrackerConstants.REFERRER);
            if (!TextUtils.isEmpty(queryParameter)) {
                Uri parse = Uri.parse("http://hostname/?" + queryParameter);
                String queryParameter2 = parse.getQueryParameter("utm_id");
                if (queryParameter2 != null) {
                    this.Cf.put(Fields.CAMPAIGN_ID, queryParameter2);
                }
                queryParameter2 = parse.getQueryParameter("utm_campaign");
                if (queryParameter2 != null) {
                    this.Cf.put(Fields.CAMPAIGN_NAME, queryParameter2);
                }
                queryParameter2 = parse.getQueryParameter("utm_content");
                if (queryParameter2 != null) {
                    this.Cf.put(Fields.CAMPAIGN_CONTENT, queryParameter2);
                }
                queryParameter2 = parse.getQueryParameter("utm_medium");
                if (queryParameter2 != null) {
                    this.Cf.put(Fields.CAMPAIGN_MEDIUM, queryParameter2);
                }
                queryParameter2 = parse.getQueryParameter("utm_source");
                if (queryParameter2 != null) {
                    this.Cf.put(Fields.CAMPAIGN_SOURCE, queryParameter2);
                }
                queryParameter2 = parse.getQueryParameter("utm_term");
                if (queryParameter2 != null) {
                    this.Cf.put(Fields.CAMPAIGN_KEYWORD, queryParameter2);
                }
                queryParameter2 = parse.getQueryParameter("dclid");
                if (queryParameter2 != null) {
                    this.Cf.put("&dclid", queryParameter2);
                }
                String queryParameter3 = parse.getQueryParameter("gclid");
                if (queryParameter3 != null) {
                    this.Cf.put("&gclid", queryParameter3);
                }
            }
        }
    }

    public void setClientId(String clientId) {
        set(Fields.CLIENT_ID, clientId);
    }

    public void setEncoding(String encoding) {
        set(Fields.ENCODING, encoding);
    }

    public void setHostname(String hostname) {
        set(Fields.HOSTNAME, hostname);
    }

    public void setLanguage(String language) {
        set(Fields.LANGUAGE, language);
    }

    public void setLocation(String location) {
        set(Fields.LOCATION, location);
    }

    public void setPage(String page) {
        set(Fields.PAGE, page);
    }

    public void setReferrer(String referrer) {
        set(Fields.REFERRER, referrer);
    }

    public void setSampleRate(double sampleRate) {
        set(Fields.SAMPLE_RATE, Double.toHexString(sampleRate));
    }

    public void setScreenColors(String screenColors) {
        set(Fields.SCREEN_COLORS, screenColors);
    }

    public void setScreenName(String screenName) {
        set("&cd", screenName);
    }

    public void setScreenResolution(int width, int height) {
        if (width >= 0 || height >= 0) {
            set(Fields.SCREEN_RESOLUTION, width + "x" + height);
        } else {
            ae.W("Invalid width or height. The values should be non-negative.");
        }
    }

    public void setSessionTimeout(long sessionTimeout) {
        this.Cl.setSessionTimeout(1000 * sessionTimeout);
    }

    public void setTitle(String title) {
        set(Fields.TITLE, title);
    }

    public void setUseSecure(boolean useSecure) {
        set(Fields.USE_SECURE, an.E(useSecure));
    }

    public void setViewportSize(String viewportSize) {
        set(Fields.VIEWPORT_SIZE, viewportSize);
    }
}
