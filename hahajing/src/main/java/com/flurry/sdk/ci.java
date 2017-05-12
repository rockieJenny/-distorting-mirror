package com.flurry.sdk;

import android.text.TextUtils;
import android.util.Pair;
import android.view.ViewGroup;
import com.flurry.android.impl.ads.protocol.v13.AdFrame;
import com.flurry.android.impl.ads.protocol.v13.AdRequest;
import com.flurry.android.impl.ads.protocol.v13.AdResponse;
import com.flurry.android.impl.ads.protocol.v13.AdUnit;
import com.flurry.android.impl.ads.protocol.v13.AdViewContainer;
import com.flurry.android.impl.ads.protocol.v13.AdViewType;
import com.flurry.android.impl.ads.protocol.v13.Configuration;
import com.flurry.android.impl.ads.protocol.v13.ConfigurationUnion;
import com.flurry.android.impl.ads.protocol.v13.FrequencyCapResponseInfo;
import com.flurry.android.impl.ads.protocol.v13.Location;
import com.flurry.android.impl.ads.protocol.v13.NativeAdConfiguration;
import com.flurry.android.impl.ads.protocol.v13.TargetingOverride;
import io.fabric.sdk.android.services.network.HttpRequest;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class ci {
    private static final String a = ci.class.getSimpleName();
    private final gh<AdRequest> b = new gh("ad request", new cl(AdRequest.class));
    private final gh<AdResponse> c = new gh("ad response", new cl(AdResponse.class));
    private final String d;
    private final List<Integer> e;
    private a f;
    private r g;
    private ap h;
    private x i;
    private List<ap> j;
    private final fy<ff> k = new fy<ff>(this) {
        final /* synthetic */ ci a;

        {
            this.a = r1;
        }

        public /* synthetic */ void notify(fx fxVar) {
            a((ff) fxVar);
        }

        public void a(ff ffVar) {
            this.a.e();
        }
    };

    enum a {
        NONE,
        WAIT_FOR_REPORTED_IDS,
        BUILD_REQUEST,
        REQUEST,
        PREPROCESS
    }

    public ci(String str) {
        this.d = str;
        this.e = Arrays.asList(new Integer[]{Integer.valueOf(0), Integer.valueOf(1), Integer.valueOf(2), Integer.valueOf(3), Integer.valueOf(4), Integer.valueOf(5)});
        this.f = a.NONE;
        d();
    }

    public synchronized void a() {
        d();
    }

    public synchronized void a(r rVar, x xVar, ap apVar) {
        gd.a(3, a, "requestAd: adSpace = " + this.d);
        if (!a.NONE.equals(this.f)) {
            gd.a(3, a, "requestAds: request pending " + this.f);
        } else if (fj.a().c()) {
            this.g = rVar;
            this.h = apVar;
            this.i = xVar;
            i.a().k().b();
            if (fe.a().c()) {
                a(a.BUILD_REQUEST);
                fp.a().b(new hq(this) {
                    final /* synthetic */ ci a;

                    {
                        this.a = r1;
                    }

                    public void safeRun() {
                        this.a.a(this.a.g, this.a.h);
                    }
                });
            } else {
                gd.a(3, a, "No reported ids yet; waiting");
                a(a.WAIT_FOR_REPORTED_IDS);
            }
        } else {
            gd.a(5, a, "There is no network connectivity (requestAds will fail)");
            g();
        }
    }

    public void b() {
        d();
    }

    private synchronized void d() {
        fn.a().a((Object) this);
        a(a.NONE);
        this.i = null;
        this.g = null;
        this.h = null;
        this.j = null;
    }

    private synchronized void a(a aVar) {
        if (aVar == null) {
            aVar = a.NONE;
        }
        gd.a(3, a, "Setting state from " + this.f + " to " + aVar);
        if (a.NONE.equals(this.f) && !a.NONE.equals(aVar)) {
            gd.a(3, a, "Adding request listeners for adspace: " + this.d);
            fz.a().a("com.flurry.android.sdk.IdProviderFinishedEvent", this.k);
        } else if (a.NONE.equals(aVar) && !a.NONE.equals(this.f)) {
            gd.a(3, a, "Removing request listeners for adspace: " + this.d);
            fz.a().a(this.k);
        }
        this.f = aVar;
    }

    private synchronized void e() {
        if (a.WAIT_FOR_REPORTED_IDS.equals(this.f)) {
            gd.a(3, a, "Reported ids retrieved; request may continue");
            a(a.BUILD_REQUEST);
            fp.a().b(new hq(this) {
                final /* synthetic */ ci a;

                {
                    this.a = r1;
                }

                public void safeRun() {
                    this.a.a(this.a.g, this.a.h);
                }
            });
        }
    }

    private synchronized void a(r rVar, ap apVar) {
        if (a.BUILD_REQUEST.equals(this.f)) {
            AdViewType adViewType;
            int i;
            Map keywords;
            boolean enableTestAds;
            Map map;
            Location location;
            List g;
            Map map2;
            boolean z;
            String str;
            a(a.REQUEST);
            ViewGroup f = rVar.f();
            e l = rVar.l();
            if (rVar instanceof q) {
                adViewType = AdViewType.BANNER;
            } else if (rVar instanceof t) {
                adViewType = AdViewType.INTERSTITIAL;
            } else if (rVar instanceof w) {
                adViewType = AdViewType.NATIVE;
            } else {
                adViewType = AdViewType.LEGACY;
            }
            Pair c = hn.c(hn.j());
            int intValue = ((Integer) c.first).intValue();
            int intValue2 = ((Integer) c.second).intValue();
            Pair k = hn.k();
            int intValue3 = ((Integer) k.first).intValue();
            int intValue4 = ((Integer) k.second).intValue();
            if (f == null || f.getHeight() <= 0) {
                i = intValue4;
            } else {
                i = hn.a(f.getHeight());
            }
            if (f == null || f.getWidth() <= 0) {
                intValue4 = intValue3;
            } else {
                intValue4 = hn.a(f.getWidth());
            }
            AdViewContainer adViewContainer = new AdViewContainer();
            adViewContainer.screenHeight = intValue2;
            adViewContainer.screenWidth = intValue;
            adViewContainer.viewHeight = i;
            adViewContainer.viewWidth = intValue4;
            adViewContainer.density = hn.e();
            adViewContainer.screenOrientation = cr.b();
            Location c2 = cr.c();
            Map emptyMap = Collections.emptyMap();
            TargetingOverride targetingOverride = new TargetingOverride();
            targetingOverride.personas = Collections.emptyList();
            targetingOverride.ageRange = -2;
            targetingOverride.gender = -2;
            if (l != null) {
                Location location2 = l.getLocation();
                if (location2 != null) {
                    c2 = location2;
                }
                keywords = l.getKeywords();
                if (keywords != null) {
                    emptyMap = keywords;
                }
                Integer gender = l.getGender();
                if (gender != null) {
                    targetingOverride.ageRange = gender.intValue();
                }
                gender = l.getGender();
                if (gender != null) {
                    targetingOverride.gender = gender.intValue();
                }
                enableTestAds = l.getEnableTestAds();
                map = emptyMap;
                location = c2;
            } else {
                enableTestAds = false;
                map = emptyMap;
                location = c2;
            }
            List e = cr.e();
            List f2 = cr.f();
            if (AdViewType.STREAM.equals(AdViewType.STREAM)) {
                g = cr.g();
            } else {
                g = Collections.emptyList();
            }
            ArrayList arrayList = new ArrayList();
            if (l != null) {
                CharSequence fixedAdId = l.getFixedAdId();
                if (!TextUtils.isEmpty(fixedAdId)) {
                    arrayList.add("FLURRY_VIEWER");
                    arrayList.add(fixedAdId);
                }
            }
            keywords = Collections.emptyMap();
            if (apVar != null) {
                AdUnit a = apVar.a();
                boolean z2 = a.renderTime;
                map2 = a.clientSideRtbPayload;
                z = z2;
            } else {
                map2 = keywords;
                z = false;
            }
            NativeAdConfiguration nativeAdConfiguration = new NativeAdConfiguration();
            List list = null;
            List list2 = null;
            if (rVar instanceof w) {
                w wVar = (w) rVar;
                list = wVar.s();
                list2 = wVar.t();
            }
            if (list == null) {
                nativeAdConfiguration.requestedStyles = Collections.emptyList();
            } else {
                nativeAdConfiguration.requestedStyles = list;
            }
            if (list2 == null) {
                nativeAdConfiguration.requestedAssets = Collections.emptyList();
            } else {
                nativeAdConfiguration.requestedAssets = list2;
            }
            String b = i.a().h().b();
            if (b == null) {
                str = "";
            } else {
                str = b;
            }
            try {
                AdRequest adRequest = new AdRequest();
                adRequest.requestTime = System.currentTimeMillis();
                adRequest.apiKey = fp.a().d();
                adRequest.agentVersion = Integer.toString(fq.a());
                adRequest.adViewType = adViewType;
                adRequest.adSpaceName = this.d;
                adRequest.sessionId = fd.a().c();
                adRequest.adReportedIds = e;
                adRequest.location = location;
                adRequest.testDevice = enableTestAds;
                adRequest.bindings = this.e;
                adRequest.adViewContainer = adViewContainer;
                adRequest.locale = fg.a().c();
                adRequest.timezone = fg.a().d();
                adRequest.osVersion = fm.a().c();
                adRequest.devicePlatform = fm.a().d();
                adRequest.keywords = map;
                adRequest.canDoSKAppStore = false;
                adRequest.networkStatus = fd.a().h().a();
                adRequest.frequencyCapRequestInfoList = f2;
                adRequest.streamInfoList = g;
                adRequest.adTrackingEnabled = fe.a().e();
                adRequest.preferredLanguage = Locale.getDefault().getLanguage();
                adRequest.bcat = arrayList;
                adRequest.userAgent = i.a().s();
                adRequest.targetingOverride = targetingOverride;
                adRequest.sendConfiguration = i.a().m() == null;
                adRequest.origins = cr.d();
                adRequest.renderTime = z;
                adRequest.clientSideRtbPayload = map2;
                adRequest.nativeAdConfiguration = nativeAdConfiguration;
                adRequest.bCookie = str;
                adRequest.appBundleId = hm.c(rVar.e());
                Object a2 = this.b.a((Object) adRequest);
                hr gkVar = new gk();
                gkVar.a(j.a().g());
                gkVar.a(20000);
                gkVar.a(com.flurry.sdk.gl.a.kPost);
                gkVar.a(HttpRequest.HEADER_CONTENT_TYPE, "application/x-flurry");
                gkVar.a("Accept", "application/x-flurry");
                gkVar.a("FM-Checksum", Integer.toString(gh.c(a2)));
                gkVar.a(new gt());
                gkVar.b(new gt());
                gkVar.a(a2);
                gkVar.a(new com.flurry.sdk.gk.a<byte[], byte[]>(this) {
                    final /* synthetic */ ci a;

                    {
                        this.a = r1;
                    }

                    public void a(gk<byte[], byte[]> gkVar, byte[] bArr) {
                        List list;
                        gd.a(3, ci.a, "AdRequest: HTTP status code is:" + gkVar.e());
                        this.a.j = new ArrayList();
                        List emptyList = Collections.emptyList();
                        if (gkVar.c() && bArr != null) {
                            AdResponse adResponse = null;
                            try {
                                adResponse = (AdResponse) this.a.c.d(bArr);
                            } catch (Exception e) {
                                gd.a(5, ci.a, "Failed to decode ad response: " + e);
                            }
                            if (adResponse != null) {
                                ConfigurationUnion configurationUnion = adResponse.configuration;
                                if (configurationUnion != null) {
                                    Configuration configuration = configurationUnion.configuration;
                                    if (configuration != null) {
                                        gd.a(3, ci.a, "Ad server responded with configuration.");
                                        fx cgVar = new cg();
                                        cgVar.a = configuration;
                                        fz.a().a(cgVar);
                                    }
                                }
                                if (adResponse.frequencyCapResponseInfoList != null) {
                                    for (FrequencyCapResponseInfo azVar : adResponse.frequencyCapResponseInfoList) {
                                        i.a().k().a(new az(azVar));
                                    }
                                }
                                if (adResponse.errors.size() > 0) {
                                    gd.b(ci.a, "Ad server responded with the following error(s):");
                                    for (String b : adResponse.errors) {
                                        gd.b(ci.a, b);
                                    }
                                }
                                if (adResponse.adUnits != null) {
                                    list = adResponse.adUnits;
                                } else {
                                    list = emptyList;
                                }
                                if (!TextUtils.isEmpty(this.a.d) && r0.size() == 0) {
                                    gd.b(ci.a, "Ad server responded but sent no ad units.");
                                }
                                for (AdUnit adUnit : r0) {
                                    if (adUnit.adFrames.size() != 0) {
                                        this.a.j.add(new ap(adUnit));
                                    }
                                }
                                this.a.a(a.PREPROCESS);
                                fp.a().b(new hq(this) {
                                    final /* synthetic */ AnonymousClass4 a;

                                    {
                                        this.a = r1;
                                    }

                                    public void safeRun() {
                                        this.a.a.f();
                                    }
                                });
                            }
                        }
                        list = emptyList;
                        for (AdUnit adUnit2 : r0) {
                            if (adUnit2.adFrames.size() != 0) {
                                this.a.j.add(new ap(adUnit2));
                            }
                        }
                        this.a.a(a.PREPROCESS);
                        fp.a().b(/* anonymous class already generated */);
                    }
                });
                fn.a().a((Object) this, gkVar);
            } catch (Exception e2) {
                gd.a(5, a, "Ad request failed with exception: " + e2);
                d();
            }
        }
    }

    private synchronized void f() {
        if (a.PREPROCESS.equals(this.f)) {
            for (ap apVar : this.j) {
                AdUnit a = apVar.a();
                if (a.frequencyCapResponseInfoList != null) {
                    for (FrequencyCapResponseInfo azVar : a.frequencyCapResponseInfoList) {
                        i.a().k().a(new az(azVar));
                    }
                }
                List list = a.adFrames;
                for (int i = 0; i < list.size(); i++) {
                    cy a2 = da.a(((AdFrame) list.get(i)).display);
                    if (a2 != null) {
                        apVar.a(i, a2);
                        if (a2.d()) {
                            break;
                        }
                    }
                }
                for (int i2 = 0; i2 < list.size(); i2++) {
                    apVar.a(i2, ac.a(apVar, i2));
                }
            }
            gd.a(3, a, "Handling ad response for adSpace: " + this.d + ", size: " + this.j.size());
            if (this.j.size() > 0) {
                if (this.i != null) {
                    this.i.a(this.j);
                }
                fp.a().b(new hq(this) {
                    final /* synthetic */ ci a;

                    {
                        this.a = r1;
                    }

                    public void safeRun() {
                        i.a().l().a(this.a.j);
                    }
                });
            }
            g();
            d();
        }
    }

    private void g() {
        fx cjVar = new cj();
        cjVar.a = this;
        cjVar.b = this.d;
        cjVar.c = this.j;
        fz.a().a(cjVar);
    }
}
