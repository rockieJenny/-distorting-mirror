package com.appflood;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import com.appflood.AppFlood.AFBannerShowDelegate;
import com.appflood.AppFlood.AFEventDelegate;
import com.appflood.AppFlood.AFRequestDelegate;
import com.appflood.c.d;
import com.appflood.c.d.a;
import com.appflood.c.e;
import com.appflood.c.f;
import com.appflood.e.c;
import com.appflood.e.j;
import com.google.ads.AdRequest.ErrorCode;
import com.google.ads.AdSize;
import com.google.ads.mediation.MediationAdRequest;
import com.google.ads.mediation.MediationBannerAdapter;
import com.google.ads.mediation.MediationBannerListener;
import com.google.ads.mediation.MediationInterstitialAdapter;
import com.google.ads.mediation.MediationInterstitialListener;
import com.inmobi.commons.analytics.db.AnalyticsSQLiteHelper;
import org.json.JSONException;
import org.json.JSONObject;

public class AppFloodAdAdapter implements MediationBannerAdapter<AppFloodExtras, AppFloodMediationServer>, MediationInterstitialAdapter<AppFloodExtras, AppFloodMediationServer> {
    private static AFBannerView a;
    private static MediationInterstitialListener c;
    private static MediationBannerListener d;
    private Activity b;

    private void fireListenerInMainThread(final int i, final int i2) {
        f.a(new Runnable(this) {
            private /* synthetic */ AppFloodAdAdapter c;

            public final void run() {
                switch (i2) {
                    case 0:
                        if (i == 0) {
                            AppFloodAdAdapter.d.onPresentScreen(this.c);
                            return;
                        } else {
                            AppFloodAdAdapter.c.onPresentScreen(this.c);
                            return;
                        }
                    case 1:
                        if (i == 0) {
                            AppFloodAdAdapter.d.onDismissScreen(this.c);
                            return;
                        }
                        AppFloodAdAdapter.c.onDismissScreen(this.c);
                        AppFloodAdAdapter.c = null;
                        return;
                    case 2:
                        if (i == 0) {
                            AppFloodAdAdapter.d.onLeaveApplication(this.c);
                            return;
                        } else {
                            AppFloodAdAdapter.c.onLeaveApplication(this.c);
                            return;
                        }
                    case 3:
                        if (i == 0) {
                            AppFloodAdAdapter.d.onReceivedAd(this.c);
                            return;
                        } else {
                            AppFloodAdAdapter.c.onReceivedAd(this.c);
                            return;
                        }
                    case 4:
                        if (i == 0) {
                            AppFloodAdAdapter.d.onFailedToReceiveAd(this.c, ErrorCode.NETWORK_ERROR);
                            return;
                        } else {
                            AppFloodAdAdapter.c.onFailedToReceiveAd(this.c, ErrorCode.NETWORK_ERROR);
                            return;
                        }
                    default:
                        return;
                }
            }
        });
    }

    private synchronized void setEvengDelegate(int i) {
        AppFlood.setEventDelegate(new AFEventDelegate(this) {
            final /* synthetic */ AppFloodAdAdapter a;

            {
                this.a = r1;
            }

            public final void onClick(JSONObject jSONObject) {
                "click " + jSONObject + " bannerListener" + AppFloodAdAdapter.d + " view " + AppFloodAdAdapter.a;
                j.a();
                int a = j.a(jSONObject, AnalyticsSQLiteHelper.EVENT_LIST_TYPE, 0);
                if (a == 4 && AppFloodAdAdapter.c != null) {
                    this.a.fireListenerInMainThread(1, 2);
                } else if (a == 1 && AppFloodAdAdapter.d != null) {
                    this.a.fireListenerInMainThread(0, 0);
                    this.a.fireListenerInMainThread(0, 2);
                    if (AppFloodAdAdapter.a != null && (AppFloodAdAdapter.a instanceof AFBannerView)) {
                        AppFloodAdAdapter.a.setShowDelegate(new AFBannerShowDelegate(this) {
                            private /* synthetic */ AnonymousClass3 a;

                            {
                                this.a = r1;
                            }

                            public final void onResume() {
                                this.a.a.fireListenerInMainThread(0, 1);
                            }
                        });
                    }
                }
            }

            public final void onClose(JSONObject jSONObject) {
                "close " + jSONObject;
                j.a();
                int a = j.a(jSONObject, AnalyticsSQLiteHelper.EVENT_LIST_TYPE, 0);
                if (a == 4 && AppFloodAdAdapter.c != null) {
                    this.a.fireListenerInMainThread(1, 1);
                } else if (a == 1 && AppFloodAdAdapter.d != null) {
                    AppFloodAdAdapter.d = null;
                    AppFloodAdAdapter.a = null;
                }
            }

            public final void onFinish(boolean z, int i) {
                "finish " + z + " adtype " + i + " bannerListener " + AppFloodAdAdapter.d + " interstitialListener" + AppFloodAdAdapter.c;
                j.a();
                if (!z) {
                    return;
                }
                if ((i != 1 || AppFloodAdAdapter.d == null) && i == 4 && AppFloodAdAdapter.c != null) {
                    this.a.fireListenerInMainThread(1, 0);
                }
            }
        });
    }

    public void destroy() {
    }

    public Class<AppFloodExtras> getAdditionalParametersType() {
        return AppFloodExtras.class;
    }

    public View getBannerView() {
        return a;
    }

    public Class<AppFloodMediationServer> getServerParametersType() {
        return AppFloodMediationServer.class;
    }

    public void requestBannerAd(MediationBannerListener mediationBannerListener, final Activity activity, AppFloodMediationServer appFloodMediationServer, final AdSize adSize, MediationAdRequest mediationAdRequest, AppFloodExtras appFloodExtras) {
        j.d("requestBannerAd ");
        AppFlood.initialize(activity, appFloodMediationServer.appKey, appFloodMediationServer.appSecret, 255);
        d = mediationBannerListener;
        setEvengDelegate(1);
        int i = 17;
        float width = ((float) adSize.getWidth()) / ((float) adSize.getHeight());
        if (Math.abs(width - 6.714286f) > Math.abs(width - 11.285714f)) {
            i = 16;
        }
        d.a(new a(this) {
            final /* synthetic */ AppFloodAdAdapter d;

            public final void a() {
                j.a();
                e.a();
                e.a(activity, adSize.getWidth(), adSize.getHeight(), 0, i, new AFRequestDelegate(this) {
                    private /* synthetic */ AnonymousClass2 a;

                    {
                        this.a = r1;
                    }

                    public final void onFinish(JSONObject jSONObject) {
                        int i;
                        try {
                            i = jSONObject.getInt("result");
                        } catch (JSONException e) {
                            e.printStackTrace();
                            i = 0;
                        }
                        "onFinish in admob resutl = " + i + " json = " + jSONObject;
                        j.a();
                        if (i == 1) {
                            i = com.appflood.AFListActivity.AnonymousClass1.b(activity) ? c.g : c.h;
                            Log.d("AppFlood", " w " + i);
                            if (i > 480) {
                                i = (i * 5) / 5;
                            }
                            int height = (adSize.getHeight() * i) / adSize.getWidth();
                            AppFloodAdAdapter.a = new AFBannerView(activity, i);
                            AppFloodAdAdapter.a.setPreload(true);
                            Log.d("AppFlood", " w " + i + " h " + height);
                            AppFloodAdAdapter.a.setSize(i, height, 0);
                            this.a.d.fireListenerInMainThread(0, 3);
                            return;
                        }
                        this.a.d.fireListenerInMainThread(0, 4);
                    }
                });
            }
        });
    }

    public void requestInterstitialAd(MediationInterstitialListener mediationInterstitialListener, final Activity activity, AppFloodMediationServer appFloodMediationServer, MediationAdRequest mediationAdRequest, AppFloodExtras appFloodExtras) {
        j.d("requestInterstitialAd " + mediationInterstitialListener);
        this.b = activity;
        c = mediationInterstitialListener;
        setEvengDelegate(4);
        AppFlood.initialize(activity, appFloodMediationServer.appKey, appFloodMediationServer.appSecret, 255);
        d.a(new a(this) {
            final /* synthetic */ AppFloodAdAdapter a;

            public final void a() {
                e.a();
                e.a(activity, com.appflood.AFListActivity.AnonymousClass1.b(activity) ? c.g : c.h, com.appflood.AFListActivity.AnonymousClass1.b(activity) ? c.h : c.g, 7, 15, new AFRequestDelegate(this) {
                    private /* synthetic */ AnonymousClass1 a;

                    {
                        this.a = r1;
                    }

                    public final void onFinish(JSONObject jSONObject) {
                        int i = 0;
                        try {
                            i = jSONObject.getInt("result");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        "onFinish in admob resutl = " + i + " json = " + jSONObject;
                        j.a();
                        if (i == 1) {
                            this.a.a.fireListenerInMainThread(1, 3);
                        } else {
                            this.a.a.fireListenerInMainThread(1, 4);
                        }
                    }
                });
            }
        });
    }

    public void showInterstitial() {
        AppFlood.showFullScreen(this.b);
    }
}
