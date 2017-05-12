package com.jirbo.adcolony;

import android.app.Activity;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build.VERSION;
import android.os.Handler;
import android.view.ViewGroup;
import com.google.android.gms.ads.identifier.AdvertisingIdClient;
import com.google.android.gms.ads.identifier.AdvertisingIdClient.Info;
import java.util.HashMap;

public class AdColony {
    static boolean b;
    boolean a = false;

    private static class a extends AsyncTask<Void, Void, Void> {
        Activity a;
        String b = "";
        boolean c;

        protected /* synthetic */ Object doInBackground(Object[] objArr) {
            return a((Void[]) objArr);
        }

        protected /* synthetic */ void onPostExecute(Object obj) {
            a((Void) obj);
        }

        a(Activity activity) {
            this.a = activity;
        }

        protected Void a(Void... voidArr) {
            try {
                Info advertisingIdInfo = AdvertisingIdClient.getAdvertisingIdInfo(this.a);
                this.b = advertisingIdInfo.getId();
                this.c = advertisingIdInfo.isLimitAdTrackingEnabled();
            } catch (NoClassDefFoundError e) {
                l.d.b((Object) "Google Play Services SDK not installed! Collecting Android Id instead of Advertising Id.");
            } catch (NoSuchMethodError e2) {
                l.d.b((Object) "Google Play Services SDK is out of date! Collecting Android Id instead of Advertising Id.");
            } catch (Exception e3) {
                l.d.b((Object) "Advertising Id not available! Collecting Android Id instead of Advertising Id.");
                e3.printStackTrace();
            }
            return null;
        }

        protected void a(Void voidR) {
            g.a = this.b;
            g.b = this.c;
            AdColony.b = true;
        }
    }

    public static void disable() {
        a.q = true;
    }

    public static void configure(Activity activity, String client_options, String app_id, String... zone_ids) {
        b = false;
        if (VERSION.SDK_INT >= 11) {
            new a(activity).executeOnExecutor(AsyncTask.THREAD_POOL_EXECUTOR, new Void[0]);
        } else {
            new a(activity).execute(new Void[0]);
        }
        a.ah.clear();
        Handler handler = new Handler();
        Runnable anonymousClass1 = new Runnable() {
            public void run() {
                a.y = false;
            }
        };
        if (!a.y || a.z) {
            if (!a.q) {
                if (app_id == null) {
                    a.a("Null App ID - disabling AdColony.");
                    return;
                } else if (zone_ids == null) {
                    a.a("Null Zone IDs array - disabling AdColony.");
                    return;
                } else if (zone_ids.length == 0) {
                    a.a("No Zone IDs provided - disabling AdColony.");
                    return;
                } else {
                    a.b(activity);
                    a.l.a(client_options, app_id, zone_ids);
                    a.o = true;
                    a.y = true;
                    handler.postDelayed(anonymousClass1, 120000);
                }
            } else {
                return;
            }
        }
        if (a.L == null) {
            a.v = true;
        }
        a.af.clear();
        a.ag.clear();
        a.ai = new HashMap();
        for (Object put : zone_ids) {
            a.ai.put(put, Boolean.valueOf(false));
        }
    }

    public static void setCustomID(String new_custom_id) {
        if (!new_custom_id.equals(a.l.a.x)) {
            a.l.a.x = new_custom_id;
            a.y = false;
            a.l.b.d = true;
            a.l.b.b = false;
            a.l.b.c = true;
        }
    }

    public static String getCustomID() {
        return a.l.a.x;
    }

    public static void setDeviceID(String new_device_id) {
        if (!new_device_id.equals(a.l.a.y)) {
            a.l.a.y = new_device_id;
            a.y = false;
            a.l.b.d = true;
            a.l.b.b = false;
            a.l.b.c = true;
        }
    }

    public static String getDeviceID() {
        return a.l.a.y;
    }

    public static boolean isTablet() {
        return g.i();
    }

    public static void resume(final Activity activity) {
        l.c.b((Object) "[ADC] AdColony resume called.");
        a.t = false;
        a.a(activity);
        a.s = false;
        if (activity == null) {
            l.d.b((Object) "Activity reference is null. Disabling AdColony.");
            disable();
            return;
        }
        new Thread(new Runnable() {
            public void run() {
                activity.runOnUiThread(new Runnable(this) {
                    final /* synthetic */ AnonymousClass2 a;

                    {
                        this.a = r1;
                    }

                    public void run() {
                        for (int i = 0; i < a.ah.size(); i++) {
                            AdColonyNativeAdView adColonyNativeAdView = (AdColonyNativeAdView) a.ah.get(i);
                            if (!(adColonyNativeAdView == null || a.b() != adColonyNativeAdView.d || adColonyNativeAdView.r)) {
                                adColonyNativeAdView.x = false;
                                adColonyNativeAdView.invalidate();
                                if (adColonyNativeAdView.O != null) {
                                    adColonyNativeAdView.O.a = false;
                                    adColonyNativeAdView.O.invalidate();
                                }
                            }
                        }
                    }
                });
            }
        }).start();
        a.D = false;
    }

    public static void pause() {
        l.c.b((Object) "[ADC] AdColony pause called.");
        a.t = true;
        for (int i = 0; i < a.ah.size(); i++) {
            if (a.ah.get(i) != null) {
                AdColonyNativeAdView adColonyNativeAdView = (AdColonyNativeAdView) a.ah.get(i);
                adColonyNativeAdView.x = true;
                if (!(adColonyNativeAdView.aa == null || adColonyNativeAdView.r || !adColonyNativeAdView.aa.isPlaying())) {
                    if (a.v) {
                        adColonyNativeAdView.O.setVisibility(0);
                    }
                    adColonyNativeAdView.c();
                }
            }
        }
    }

    public static void onBackPressed() {
        int i = 0;
        if (a.J == null) {
            return;
        }
        if ((a.J instanceof ac) || (a.J instanceof ad)) {
            ((ViewGroup) a.J.getParent()).removeView(a.J);
            a.v = true;
            a.J.G.c(false);
            while (i < a.ae.size()) {
                ((Bitmap) a.ae.get(i)).recycle();
                i++;
            }
            a.ae.clear();
            a.J = null;
        }
    }

    public static Activity activity() {
        return a.b();
    }

    public static boolean isZoneV4VC(String zone_id) {
        if (a.l == null || a.l.b == null || a.l.b.j == null || a.l.b.j.n == null) {
            return false;
        }
        return a.l.b.a(zone_id, false);
    }

    public static boolean isZoneNative(String zone_id) {
        if (a.l == null || a.l.b == null || a.l.b.j == null || a.l.b.j.n == null || a.l.b.j.n.a(zone_id) == null || a.l.b.j.n.a(zone_id).i == null || a.l.b.j.n.a(zone_id).i.a == null) {
            return false;
        }
        for (int i = 0; i < a.l.b.j.n.a(zone_id).i.a.size(); i++) {
            if (a.l.b.j.n.a(zone_id).i.a(i).w.a) {
                return true;
            }
        }
        return false;
    }

    public static int getRemainingV4VCForZone(String zone_id) {
        if (a.l == null || a.l.h == null || a.l.b == null || a.l.b.j == null || a.l.b.j.n == null) {
            return l.c.c("getRemainingV4VCForZone called before AdColony has finished configuring.");
        }
        ab a = a.l.b.j.n.a(zone_id);
        if (a.j.a) {
            return a.j.b.a - a.l.h.b(zone_id);
        }
        return l.c.c("getRemainingV4VCForZone called with non-V4VC zone.");
    }

    public static void addV4VCListener(AdColonyV4VCListener listener) {
        if (!a.af.contains(listener)) {
            a.af.add(listener);
        }
    }

    public static void removeV4VCListener(AdColonyV4VCListener listener) {
        a.af.remove(listener);
    }

    public static void addAdAvailabilityListener(AdColonyAdAvailabilityListener listener) {
        if (!a.ag.contains(listener)) {
            a.ag.add(listener);
        }
    }

    public static void removeAdAvailabilityListener(AdColonyAdAvailabilityListener listener) {
        a.ag.remove(listener);
    }

    public static void notifyIAPComplete(String product_id, String trans_id) {
        notifyIAPComplete(product_id, trans_id, null, 0.0d);
    }

    public static void notifyIAPComplete(String product_id, String trans_id, String currency_code, double price) {
        l.c.b((Object) "notifyIAPComplete() called.");
        i gVar = new g();
        gVar.b("product_id", product_id);
        if (price != 0.0d) {
            gVar.b("price", price);
        }
        gVar.b("trans_id", trans_id);
        gVar.b("quantity", 1);
        if (currency_code != null) {
            gVar.b("price_currency_code", currency_code);
        }
        if (a.F) {
            a.l.d.a("in_app_purchase", (g) gVar);
        } else {
            a.aa.a(gVar);
        }
    }

    public static void cancelVideo() {
        if (a.L != null) {
            a.L.finish();
            a.ab = true;
            a.N.b(null);
        }
    }

    public static String statusForZone(String zone_id) {
        if (a.l == null || a.l.b == null || a.l.b.j == null || a.l.b.j.n == null) {
            return "unknown";
        }
        if (a.q) {
            return "unknown";
        }
        ab a = a.l.b.j.n.a(zone_id);
        if (a != null) {
            if (!a.e) {
                return "off";
            }
            if (a.f && a.l.b.c(zone_id, true)) {
                return "active";
            }
            return "loading";
        } else if (a.p) {
            return "invalid";
        } else {
            return "unknown";
        }
    }

    public static void get_images(String zone_id) {
        a.l.a.b(zone_id);
    }

    public static void disableDECOverride() {
        a.e = null;
    }

    public static void forceMobileCache() {
        if (!a.E) {
            a.E = true;
            a.y = false;
            a.l.b.d = true;
            a.l.b.b = false;
            a.l.b.c = true;
        }
    }
}
