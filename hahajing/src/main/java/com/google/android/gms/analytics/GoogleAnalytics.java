package com.google.android.gms.analytics;

import android.app.Activity;
import android.app.Application;
import android.app.Application.ActivityLifecycleCallbacks;
import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Build.VERSION;
import android.os.Bundle;
import com.google.analytics.tracking.android.Fields;
import com.inmobi.commons.analytics.iat.impl.AdTrackerConstants;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

public class GoogleAnalytics extends TrackerHandler {
    private static boolean Bm;
    private static GoogleAnalytics Bs;
    private aj Bn;
    private volatile Boolean Bo;
    private Logger Bp;
    private Set<a> Bq;
    private boolean Br;
    private Context mContext;
    private f yV;
    private String ya;
    private String yb;
    private boolean yt;

    interface a {
        void i(Activity activity);

        void j(Activity activity);
    }

    class b implements ActivityLifecycleCallbacks {
        final /* synthetic */ GoogleAnalytics Bt;

        b(GoogleAnalytics googleAnalytics) {
            this.Bt = googleAnalytics;
        }

        public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        }

        public void onActivityDestroyed(Activity activity) {
        }

        public void onActivityPaused(Activity activity) {
        }

        public void onActivityResumed(Activity activity) {
        }

        public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        }

        public void onActivityStarted(Activity activity) {
            this.Bt.g(activity);
        }

        public void onActivityStopped(Activity activity) {
            this.Bt.h(activity);
        }
    }

    protected GoogleAnalytics(Context context) {
        this(context, x.A(context), v.eu());
    }

    private GoogleAnalytics(Context context, f thread, aj serviceManager) {
        this.Bo = Boolean.valueOf(false);
        this.Br = false;
        if (context == null) {
            throw new IllegalArgumentException(AdTrackerConstants.MSG_APP_CONTEXT_NULL);
        }
        this.mContext = context.getApplicationContext();
        this.yV = thread;
        this.Bn = serviceManager;
        g.y(this.mContext);
        ai.y(this.mContext);
        k.y(this.mContext);
        this.Bp = new p();
        this.Bq = new HashSet();
        eZ();
    }

    private Tracker a(Tracker tracker) {
        if (this.ya != null) {
            tracker.set(Fields.APP_NAME, this.ya);
        }
        if (this.yb != null) {
            tracker.set(Fields.APP_VERSION, this.yb);
        }
        return tracker;
    }

    private int ai(String str) {
        String toLowerCase = str.toLowerCase();
        return "verbose".equals(toLowerCase) ? 0 : "info".equals(toLowerCase) ? 1 : "warning".equals(toLowerCase) ? 2 : "error".equals(toLowerCase) ? 3 : -1;
    }

    static GoogleAnalytics eY() {
        GoogleAnalytics googleAnalytics;
        synchronized (GoogleAnalytics.class) {
            googleAnalytics = Bs;
        }
        return googleAnalytics;
    }

    private void eZ() {
        if (!Bm) {
            ApplicationInfo applicationInfo;
            try {
                applicationInfo = this.mContext.getPackageManager().getApplicationInfo(this.mContext.getPackageName(), 129);
            } catch (NameNotFoundException e) {
                ae.V("PackageManager doesn't know about package: " + e);
                applicationInfo = null;
            }
            if (applicationInfo == null) {
                ae.W("Couldn't get ApplicationInfo to load gloabl config.");
                return;
            }
            Bundle bundle = applicationInfo.metaData;
            if (bundle != null) {
                int i = bundle.getInt("com.google.android.gms.analytics.globalConfigResource");
                if (i > 0) {
                    aa aaVar = (aa) new z(this.mContext).x(i);
                    if (aaVar != null) {
                        a(aaVar);
                    }
                }
            }
        }
    }

    public static GoogleAnalytics getInstance(Context context) {
        GoogleAnalytics googleAnalytics;
        synchronized (GoogleAnalytics.class) {
            if (Bs == null) {
                Bs = new GoogleAnalytics(context);
            }
            googleAnalytics = Bs;
        }
        return googleAnalytics;
    }

    void a(a aVar) {
        this.Bq.add(aVar);
        if (this.mContext instanceof Application) {
            enableAutoActivityReports((Application) this.mContext);
        }
    }

    void a(aa aaVar) {
        ae.V("Loading global config values.");
        if (aaVar.eO()) {
            this.ya = aaVar.eP();
            ae.V("app name loaded: " + this.ya);
        }
        if (aaVar.eQ()) {
            this.yb = aaVar.eR();
            ae.V("app version loaded: " + this.yb);
        }
        if (aaVar.eS()) {
            int ai = ai(aaVar.eT());
            if (ai >= 0) {
                ae.V("log level loaded: " + ai);
                getLogger().setLogLevel(ai);
            }
        }
        if (aaVar.eU()) {
            this.Bn.setLocalDispatchPeriod(aaVar.eV());
        }
        if (aaVar.eW()) {
            setDryRun(aaVar.eX());
        }
    }

    void b(a aVar) {
        this.Bq.remove(aVar);
    }

    void dY() {
        this.yV.dY();
    }

    @Deprecated
    public void dispatchLocalHits() {
        this.Bn.dispatchLocalHits();
    }

    public void enableAutoActivityReports(Application application) {
        if (VERSION.SDK_INT >= 14 && !this.Br) {
            application.registerActivityLifecycleCallbacks(new b(this));
            this.Br = true;
        }
    }

    void g(Activity activity) {
        for (a i : this.Bq) {
            i.i(activity);
        }
    }

    public boolean getAppOptOut() {
        y.eK().a(com.google.android.gms.analytics.y.a.GET_APP_OPT_OUT);
        return this.Bo.booleanValue();
    }

    public Logger getLogger() {
        return this.Bp;
    }

    void h(Activity activity) {
        for (a j : this.Bq) {
            j.j(activity);
        }
    }

    public boolean isDryRunEnabled() {
        y.eK().a(com.google.android.gms.analytics.y.a.GET_DRY_RUN);
        return this.yt;
    }

    public Tracker newTracker(int configResId) {
        Tracker a;
        synchronized (this) {
            y.eK().a(com.google.android.gms.analytics.y.a.GET_TRACKER);
            Tracker tracker = new Tracker(null, this, this.mContext);
            if (configResId > 0) {
                am amVar = (am) new al(this.mContext).x(configResId);
                if (amVar != null) {
                    tracker.a(amVar);
                }
            }
            a = a(tracker);
        }
        return a;
    }

    public Tracker newTracker(String trackingId) {
        Tracker a;
        synchronized (this) {
            y.eK().a(com.google.android.gms.analytics.y.a.GET_TRACKER);
            a = a(new Tracker(trackingId, this, this.mContext));
        }
        return a;
    }

    public void reportActivityStart(Activity activity) {
        if (!this.Br) {
            g(activity);
        }
    }

    public void reportActivityStop(Activity activity) {
        if (!this.Br) {
            h(activity);
        }
    }

    public void setAppOptOut(boolean optOut) {
        y.eK().a(com.google.android.gms.analytics.y.a.SET_APP_OPT_OUT);
        this.Bo = Boolean.valueOf(optOut);
        if (this.Bo.booleanValue()) {
            this.yV.dQ();
        }
    }

    public void setDryRun(boolean dryRun) {
        y.eK().a(com.google.android.gms.analytics.y.a.SET_DRY_RUN);
        this.yt = dryRun;
    }

    @Deprecated
    public void setLocalDispatchPeriod(int dispatchPeriodInSeconds) {
        this.Bn.setLocalDispatchPeriod(dispatchPeriodInSeconds);
    }

    public void setLogger(Logger logger) {
        y.eK().a(com.google.android.gms.analytics.y.a.SET_LOGGER);
        this.Bp = logger;
    }

    void u(Map<String, String> map) {
        synchronized (this) {
            if (map == null) {
                throw new IllegalArgumentException("hit cannot be null");
            }
            an.a((Map) map, Fields.LANGUAGE, an.a(Locale.getDefault()));
            an.a((Map) map, Fields.SCREEN_RESOLUTION, ai.fl());
            map.put("&_u", y.eK().eM());
            y.eK().eL();
            this.yV.u(map);
        }
    }
}
