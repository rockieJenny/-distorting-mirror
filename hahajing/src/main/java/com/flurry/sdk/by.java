package com.flurry.sdk;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import com.inmobi.commons.InMobi;
import com.inmobi.monetization.IMErrorCode;
import com.inmobi.monetization.IMInterstitial;
import com.inmobi.monetization.IMInterstitial.State;
import com.inmobi.monetization.IMInterstitialListener;
import java.lang.reflect.Method;
import java.util.Collections;
import java.util.Map;

public class by extends bd {
    private static final String b = by.class.getSimpleName();
    private static final Method c = e();
    private final String d;
    private IMInterstitial e;
    private a f;

    final class a implements IMInterstitialListener {
        final /* synthetic */ by a;

        private a(by byVar) {
            this.a = byVar;
        }

        public void onInterstitialFailed(IMInterstitial iMInterstitial, IMErrorCode iMErrorCode) {
            this.a.d(Collections.emptyMap());
            gd.a(3, by.b, "InMobi imAdView ad request failed. ErrorCode  = " + iMErrorCode.toString());
        }

        public void onInterstitialLoaded(IMInterstitial iMInterstitial) {
            gd.a(3, by.b, "InMobi Interstitial ad request completed.");
            if (State.READY.equals(iMInterstitial.getState())) {
                this.a.a(Collections.emptyMap());
                iMInterstitial.show();
            }
        }

        public void onDismissInterstitialScreen(IMInterstitial iMInterstitial) {
            this.a.c(Collections.emptyMap());
            gd.a(3, by.b, "InMobi Interstitial ad dismissed.");
        }

        public void onShowInterstitialScreen(IMInterstitial iMInterstitial) {
            gd.a(3, by.b, "InMobi Interstitial ad shown.");
        }

        public void onLeaveApplication(IMInterstitial iMInterstitial) {
            this.a.b(Collections.emptyMap());
            gd.a(3, by.b, "InMobi onLeaveApplication");
        }

        public void onInterstitialInteraction(IMInterstitial iMInterstitial, Map<String, String> map) {
            this.a.b(Collections.emptyMap());
            gd.a(3, by.b, "InMobi onBannerInteraction");
        }
    }

    public by(Context context, r rVar, Bundle bundle) {
        super(context, rVar);
        this.d = bundle.getString("com.flurry.inmobi.MY_APP_ID");
        InMobi.initialize((Activity) c(), this.d);
    }

    public void a() {
        this.e = new IMInterstitial((Activity) c(), this.d);
        this.f = new a();
        a(this.e, this.f);
        this.e.loadInterstitial();
    }

    private void a(IMInterstitial iMInterstitial, a aVar) {
        if (iMInterstitial != null) {
            try {
                if (c != null) {
                    c.invoke(iMInterstitial, new Object[]{aVar});
                }
            } catch (Exception e) {
                gd.a(3, b, "InMobi set listener failed.");
            }
        }
    }

    private static Method e() {
        for (Method method : IMInterstitial.class.getMethods()) {
            String name = method.getName();
            if (name.equals("setIMInterstitialListener") || name.equals("setImInterstitialListener")) {
                return method;
            }
        }
        return null;
    }
}
