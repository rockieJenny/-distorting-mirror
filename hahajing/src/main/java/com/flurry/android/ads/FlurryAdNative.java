package com.flurry.android.ads;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import com.flurry.android.impl.ads.protocol.v13.NativeAsset;
import com.flurry.sdk.d;
import com.flurry.sdk.d.a;
import com.flurry.sdk.e;
import com.flurry.sdk.fp;
import com.flurry.sdk.fx;
import com.flurry.sdk.fy;
import com.flurry.sdk.fz;
import com.flurry.sdk.gd;
import com.flurry.sdk.hq;
import com.flurry.sdk.i;
import com.flurry.sdk.w;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class FlurryAdNative {
    private static final String a = FlurryAdNative.class.getSimpleName();
    private final List<FlurryAdNativeAsset> b = new ArrayList();
    private w c;
    private final fy<d> d = new fy<d>(this) {
        final /* synthetic */ FlurryAdNative a;

        {
            this.a = r1;
        }

        public /* synthetic */ void notify(fx fxVar) {
            a((d) fxVar);
        }

        public void a(final d dVar) {
            if (dVar.a == this.a.c && dVar.b != null) {
                if (a.kOnFetched.equals(dVar.b)) {
                    this.a.a();
                }
                final FlurryAdNativeListener c = this.a.e;
                if (c != null) {
                    fp.a().a(new hq(this) {
                        final /* synthetic */ AnonymousClass1 c;

                        public void safeRun() {
                            switch (dVar.b) {
                                case kOnFetched:
                                    c.onFetched(this.c.a);
                                    return;
                                case kOnFetchFailed:
                                    c.onError(this.c.a, FlurryAdErrorType.FETCH, dVar.c.a());
                                    return;
                                case kOnOpen:
                                    c.onShowFullscreen(this.c.a);
                                    return;
                                case kOnClose:
                                    c.onCloseFullscreen(this.c.a);
                                    return;
                                case kOnAppExit:
                                    c.onAppExit(this.c.a);
                                    return;
                                case kOnClicked:
                                    c.onClicked(this.c.a);
                                    return;
                                case kOnClickFailed:
                                    c.onError(this.c.a, FlurryAdErrorType.CLICK, dVar.c.a());
                                    return;
                                case kOnImpressionLogged:
                                    c.onImpressionLogged(this.c.a);
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
    private FlurryAdNativeListener e;

    public FlurryAdNative(Context context, String str) {
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
                this.c = new w(context, str);
                List arrayList = new ArrayList();
                arrayList.add(Integer.valueOf(1));
                this.c.a(arrayList);
                fz.a().a("com.flurry.android.impl.ads.AdStateEvent", this.d);
            } catch (Throwable th) {
                gd.a(a, "Exception: ", th);
            }
        }
    }

    public void destroy() {
        try {
            fz.a().b("com.flurry.android.impl.ads.AdStateEvent", this.d);
            this.e = null;
            if (this.c != null) {
                this.c.a();
                this.c = null;
            }
        } catch (Throwable th) {
            gd.a(a, "Exception: ", th);
        }
    }

    public void setListener(FlurryAdNativeListener flurryAdNativeListener) {
        try {
            this.e = flurryAdNativeListener;
        } catch (Throwable th) {
            gd.a(a, "Exception: ", th);
        }
    }

    public void setTargeting(FlurryAdTargeting flurryAdTargeting) {
        try {
            this.c.a((e) flurryAdTargeting);
        } catch (Throwable th) {
            gd.a(a, "Exception: ", th);
        }
    }

    public void setSupportedStyles(List<Integer> list) {
        try {
            this.c.a((List) list);
        } catch (Throwable th) {
            gd.a(a, "Exception: ", th);
        }
    }

    public void fetchAd() {
        try {
            this.c.v();
        } catch (Throwable th) {
            gd.a(a, "Exception: ", th);
        }
    }

    public boolean isReady() {
        try {
            return this.c.u();
        } catch (Throwable th) {
            gd.a(a, "Exception: ", th);
            return false;
        }
    }

    public void setTrackingView(View view) {
        try {
            this.c.a(view);
        } catch (Throwable th) {
            gd.a(a, "Exception: ", th);
        }
    }

    public void removeTrackingView() {
        try {
            this.c.w();
        } catch (Throwable th) {
            gd.a(a, "Exception: ", th);
        }
    }

    public int getStyle() {
        try {
            return this.c.x();
        } catch (Throwable th) {
            gd.a(a, "Exception: ", th);
            return 0;
        }
    }

    public FlurryAdNativeAsset getAsset(String str) {
        if (i.a() == null) {
            return null;
        }
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        try {
            FlurryAdNativeAsset flurryAdNativeAsset;
            synchronized (this.b) {
                for (FlurryAdNativeAsset flurryAdNativeAsset2 : this.b) {
                    if (str.equals(flurryAdNativeAsset2.getName())) {
                        break;
                    }
                }
                flurryAdNativeAsset2 = null;
            }
            return flurryAdNativeAsset2;
        } catch (Throwable th) {
            gd.a(a, "Exception: ", th);
            return null;
        }
    }

    public List<FlurryAdNativeAsset> getAssetList() {
        if (i.a() == null) {
            return Collections.emptyList();
        }
        try {
            List arrayList = new ArrayList();
            synchronized (this.b) {
                arrayList.addAll(this.b);
            }
            return Collections.unmodifiableList(arrayList);
        } catch (Throwable th) {
            gd.a(a, "Exception: ", th);
            return Collections.emptyList();
        }
    }

    private void a() {
        if (this.c != null) {
            synchronized (this.b) {
                for (NativeAsset flurryAdNativeAsset : this.c.y()) {
                    this.b.add(new FlurryAdNativeAsset(flurryAdNativeAsset, this.c.d()));
                }
            }
        }
    }
}
