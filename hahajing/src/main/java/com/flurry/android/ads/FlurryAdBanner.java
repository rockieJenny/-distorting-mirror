package com.flurry.android.ads;

import android.content.Context;
import android.text.TextUtils;
import android.view.ViewGroup;
import com.flurry.sdk.d;
import com.flurry.sdk.e;
import com.flurry.sdk.fp;
import com.flurry.sdk.fx;
import com.flurry.sdk.fy;
import com.flurry.sdk.fz;
import com.flurry.sdk.gd;
import com.flurry.sdk.hq;
import com.flurry.sdk.i;
import com.flurry.sdk.q;

public final class FlurryAdBanner {
    private static final String a = FlurryAdBanner.class.getSimpleName();
    private q b;
    private final fy<d> c = new fy<d>(this) {
        final /* synthetic */ FlurryAdBanner a;

        {
            this.a = r1;
        }

        public /* synthetic */ void notify(fx fxVar) {
            a((d) fxVar);
        }

        public void a(final d dVar) {
            if (dVar.a == this.a.b && dVar.b != null) {
                final FlurryAdBannerListener b = this.a.d;
                if (b != null) {
                    fp.a().a(new hq(this) {
                        final /* synthetic */ AnonymousClass1 c;

                        public void safeRun() {
                            switch (dVar.b) {
                                case kOnFetched:
                                    b.onFetched(this.c.a);
                                    return;
                                case kOnFetchFailed:
                                    b.onError(this.c.a, FlurryAdErrorType.FETCH, dVar.c.a());
                                    return;
                                case kOnRendered:
                                    b.onRendered(this.c.a);
                                    return;
                                case kOnRenderFailed:
                                    b.onError(this.c.a, FlurryAdErrorType.RENDER, dVar.c.a());
                                    return;
                                case kOnOpen:
                                    b.onShowFullscreen(this.c.a);
                                    return;
                                case kOnClose:
                                    b.onCloseFullscreen(this.c.a);
                                    return;
                                case kOnAppExit:
                                    b.onAppExit(this.c.a);
                                    return;
                                case kOnClicked:
                                    b.onClicked(this.c.a);
                                    return;
                                case kOnVideoCompleted:
                                    b.onVideoCompleted(this.c.a);
                                    return;
                                case kOnClickFailed:
                                    b.onError(this.c.a, FlurryAdErrorType.CLICK, dVar.c.a());
                                    return;
                                default:
                                    return;
                            }
                        }
                    });
                }
            }
        }
    };
    private FlurryAdBannerListener d;

    public FlurryAdBanner(Context context, ViewGroup viewGroup, String str) {
        if (fp.a() == null) {
            throw new IllegalStateException("Flurry SDK must be initialized before starting a session");
        } else if (context == null) {
            throw new IllegalArgumentException("Context cannot be null!");
        } else if (TextUtils.isEmpty(str)) {
            throw new IllegalArgumentException("Ad space must be specified!");
        } else {
            try {
                if (i.a() == null) {
                    throw new IllegalStateException("Could not find FlurryAds module. Please make sure the library is included.");
                }
                this.b = new q(context, viewGroup, str);
                fz.a().a("com.flurry.android.impl.ads.AdStateEvent", this.c);
            } catch (Throwable th) {
                gd.a(a, "Exception: ", th);
            }
        }
    }

    public void destroy() {
        try {
            fz.a().b("com.flurry.android.impl.ads.AdStateEvent", this.c);
            this.d = null;
            if (this.b != null) {
                this.b.a();
                this.b = null;
            }
        } catch (Throwable th) {
            gd.a(a, "Exception: ", th);
        }
    }

    public void setListener(FlurryAdBannerListener flurryAdBannerListener) {
        try {
            this.d = flurryAdBannerListener;
        } catch (Throwable th) {
            gd.a(a, "Exception: ", th);
        }
    }

    public void setTargeting(FlurryAdTargeting flurryAdTargeting) {
        try {
            this.b.a((e) flurryAdTargeting);
        } catch (Throwable th) {
            gd.a(a, "Exception: ", th);
        }
    }

    public void fetchAd() {
        try {
            this.b.v();
        } catch (Throwable th) {
            gd.a(a, "Exception: ", th);
        }
    }

    public void displayAd() {
        try {
            this.b.w();
        } catch (Throwable th) {
            gd.a(a, "Exception: ", th);
        }
    }

    public void fetchAndDisplayAd() {
        try {
            this.b.x();
        } catch (Throwable th) {
            gd.a(a, "Exception: ", th);
        }
    }

    public boolean isReady() {
        try {
            return this.b.u();
        } catch (Throwable th) {
            gd.a(a, "Exception: ", th);
            return false;
        }
    }
}
