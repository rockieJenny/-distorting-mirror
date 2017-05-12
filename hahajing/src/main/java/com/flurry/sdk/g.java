package com.flurry.sdk;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import com.flurry.android.impl.ads.protocol.v13.AdFrame;
import com.flurry.sdk.ed.a;
import com.inmobi.monetization.internal.Ad;
import java.util.Collections;
import java.util.Map;

public class g {
    private static final String b = g.class.getSimpleName();
    cp a = new cp();
    private boolean c;

    public void a() {
        this.c = b(null);
    }

    public void a(a aVar, int i) {
        Object obj = null;
        if (aVar.c() != null) {
            obj = aVar.c().a;
        }
        gd.a(3, b, "performAction:action=" + aVar.toString());
        if (i > 10) {
            gd.a(5, b, "Maximum depth for event/action loop exceeded when performing action:" + aVar.toString());
            return;
        }
        switch (aVar.a()) {
            case AC_DIRECT_OPEN:
                a(aVar);
                break;
            case AC_MRAID_PLAY_VIDEO:
                k(aVar);
                break;
            case AC_MRAID_OPEN:
                j(aVar);
                break;
            case AC_DELETE:
                b(aVar);
                break;
            case AC_PROCESS_REDIRECT:
                c(aVar);
                break;
            case AC_VERIFY_URL:
                b(aVar, i);
                break;
            case AC_LAUNCH_PACKAGE:
                d(aVar);
                break;
            case AC_SEND_URL_ASYNC:
                e(aVar);
                break;
            case AC_SEND_AD_LOGS:
                b();
                break;
            case AC_LOG_EVENT:
                f(aVar);
                break;
            case AC_NEXT_FRAME:
                g(aVar);
                break;
            case AC_NEXT_AD_UNIT:
                c(aVar, i);
                break;
            case AC_CHECK_CAP:
                d(aVar, i);
                break;
            case AC_UPDATE_VIEW_COUNT:
                h(aVar);
                break;
            case AC_CLOSE_AD:
                n(aVar);
                break;
            case AC_NOTIFY_USER:
                e(aVar, i);
                break;
            case AC_MRAID_DO_EXPAND:
                m(aVar);
                break;
            case AC_MRAID_DO_COLLAPSE:
                l(aVar);
                break;
            default:
                gd.a(5, b, "Unknown action:" + aVar.a() + ",triggered by:" + obj);
                break;
        }
        i(aVar);
    }

    private void i(a aVar) {
        String str = (String) aVar.c().b.get("requiresCallComplete");
        if (!TextUtils.isEmpty(str) && str.equals("true")) {
            gd.a(3, b, "Fire call complete");
            ed edVar = new ed();
            edVar.b = aVar;
            edVar.a = a.CALL_COMPLETE;
            edVar.b();
        }
    }

    private void j(a aVar) {
        boolean z = true;
        Context a = aVar.c().a();
        r b = aVar.c().b();
        ap c = aVar.c().c();
        boolean o = o(aVar);
        String a2 = aVar.a("url");
        if (TextUtils.isEmpty(a2)) {
            gd.a(6, b, "failed to perform directOpen action: no url in " + aVar.c().a);
        } else if (cv.d(a2)) {
            cu.a(a, a2);
        } else {
            boolean z2;
            boolean equals = "true".equals(aVar.a(Ad.AD_TYPE_NATIVE));
            if ("true".equals(aVar.a("is_privacy"))) {
                z2 = false;
            } else {
                z2 = true;
            }
            if (equals) {
                gd.a(2, b, "Explictly instructed to use native browser");
                cu.a(a, this.a.a(aVar, a2), o);
                return;
            }
            c.a(true);
            if (c.r()) {
                a(b, a2, z2, o);
                return;
            }
            if (equals) {
                z = false;
            }
            a(a, a2, z, b, z2, o);
        }
    }

    private void k(a aVar) {
        Context a = aVar.c().a();
        r b = aVar.c().b();
        ap c = aVar.c().c();
        boolean o = o(aVar);
        String a2 = aVar.a("url");
        if (TextUtils.isEmpty(a2)) {
            gd.a(6, b, "failed to perform directOpen action: no url in " + aVar.c().a);
        } else if (cv.d(a2)) {
            cu.a(a, a2);
        } else {
            boolean equals = "true".equals(aVar.a(Ad.AD_TYPE_NATIVE));
            boolean z = !"true".equals(aVar.a("is_privacy"));
            if (equals) {
                gd.a(2, b, "Explictly instructed to use native browser");
                cu.a(a, this.a.a(aVar, a2), o);
                return;
            }
            c.a(true);
            if (c.r()) {
                a(b, a2, z, o);
            } else {
                cu.a(a, b, a2, z, o);
            }
        }
    }

    private void l(a aVar) {
        gd.a(3, b, "closing ad");
        ed edVar = new ed();
        edVar.b = aVar;
        edVar.c = 0;
        edVar.a = a.DO_COLLAPSE;
        edVar.b();
    }

    private void m(a aVar) {
        gd.a(3, b, "expanding ad");
        ed edVar = new ed();
        edVar.b = aVar;
        edVar.c = 0;
        edVar.a = a.DO_EXPAND;
        edVar.b();
    }

    private void e(a aVar, int i) {
        gd.a(3, b, "notify user");
        ed edVar = new ed();
        edVar.b = aVar;
        edVar.c = i;
        edVar.a = a.SHOW_VIDEO_DIALOG;
        edVar.b();
    }

    private void n(a aVar) {
        gd.a(3, b, "closing ad");
        ed edVar = new ed();
        edVar.b = aVar;
        edVar.c = 0;
        edVar.a = a.CLOSE_AD;
        edVar.b();
    }

    void a(a aVar) {
        boolean z = true;
        Context a = aVar.c().a();
        r b = aVar.c().b();
        ap c = aVar.c().c();
        boolean o = o(aVar);
        String a2 = aVar.a("url");
        if (TextUtils.isEmpty(a2)) {
            gd.a(6, b, "failed to perform directOpen action: no url in " + aVar.c().a);
        } else if (cv.d(a2)) {
            cu.a(a, a2);
        } else {
            boolean z2;
            boolean equals = "true".equals(aVar.a(Ad.AD_TYPE_NATIVE));
            if ("true".equals(aVar.a("is_privacy"))) {
                z2 = false;
            } else {
                z2 = true;
            }
            if (equals) {
                gd.a(2, b, "Explictly instructed to use native browser");
                cu.a(a, this.a.a(aVar, a2), o);
                return;
            }
            a2 = this.a.a(aVar, a2);
            if (c.r()) {
                a(b, a2, z2, o);
                return;
            }
            if (equals) {
                z = false;
            }
            a(a, a2, z, b, z2, o);
        }
    }

    private boolean o(a aVar) {
        boolean z = false;
        String a = aVar.a("sendYCookies");
        if (!TextUtils.isEmpty(a)) {
            try {
                z = Boolean.parseBoolean(a);
            } catch (Exception e) {
                gd.a(6, b, "caught Exception with sendYCookies parameter in onProcessRedirect:" + a);
            }
        }
        return z;
    }

    void b(a aVar) {
        r b = aVar.c().b();
        String a = aVar.a("groupId");
        if (!TextUtils.isEmpty(a)) {
            b.a(a);
        }
    }

    void c(a aVar) {
        Context a = aVar.c().a();
        r b = aVar.c().b();
        Object a2 = aVar.a("url");
        if (!TextUtils.isEmpty(a2)) {
            boolean parseBoolean;
            boolean parseBoolean2;
            Object a3;
            String a4 = aVar.a("sendYCookies");
            if (!TextUtils.isEmpty(a4)) {
                try {
                    parseBoolean = Boolean.parseBoolean(a4);
                } catch (Exception e) {
                    gd.a(6, b, "caught Exception with sendYCookies parameter in onProcessRedirect:" + a4);
                }
                a4 = aVar.a(Ad.AD_TYPE_NATIVE);
                if (!TextUtils.isEmpty(a4)) {
                    try {
                        parseBoolean2 = Boolean.parseBoolean(a4);
                    } catch (Exception e2) {
                        gd.a(6, b, "caught Exception with useNative parameter in onProcessRedirect:" + a4);
                    }
                    if (TextUtils.isEmpty(a2)) {
                        a3 = cv.a(this.a.a(aVar, a2));
                        if (TextUtils.isEmpty(a3)) {
                            if (parseBoolean2) {
                                parseBoolean2 = true;
                            } else {
                                parseBoolean2 = false;
                            }
                            a(a, a3, parseBoolean2, b, true, parseBoolean);
                        }
                    }
                }
                parseBoolean2 = false;
                if (TextUtils.isEmpty(a2)) {
                    a3 = cv.a(this.a.a(aVar, a2));
                    if (TextUtils.isEmpty(a3)) {
                        if (parseBoolean2) {
                            parseBoolean2 = false;
                        } else {
                            parseBoolean2 = true;
                        }
                        a(a, a3, parseBoolean2, b, true, parseBoolean);
                    }
                }
            }
            parseBoolean = false;
            a4 = aVar.a(Ad.AD_TYPE_NATIVE);
            if (TextUtils.isEmpty(a4)) {
                parseBoolean2 = Boolean.parseBoolean(a4);
                if (TextUtils.isEmpty(a2)) {
                    a3 = cv.a(this.a.a(aVar, a2));
                    if (TextUtils.isEmpty(a3)) {
                        if (parseBoolean2) {
                            parseBoolean2 = true;
                        } else {
                            parseBoolean2 = false;
                        }
                        a(a, a3, parseBoolean2, b, true, parseBoolean);
                    }
                }
            }
            parseBoolean2 = false;
            if (TextUtils.isEmpty(a2)) {
                a3 = cv.a(this.a.a(aVar, a2));
                if (TextUtils.isEmpty(a3)) {
                    if (parseBoolean2) {
                        parseBoolean2 = false;
                    } else {
                        parseBoolean2 = true;
                    }
                    a(a, a3, parseBoolean2, b, true, parseBoolean);
                }
            }
        }
    }

    void b(a aVar, int i) {
        Context a = aVar.c().a();
        r b = aVar.c().b();
        ap c = aVar.c().c();
        String a2 = aVar.a("url");
        if (!TextUtils.isEmpty(a2)) {
            aw awVar = a(a2) ? aw.EV_URL_VERIFIED : aw.EV_URL_NOT_VERIFIED;
            f.a().a(awVar.a(), 1);
            co.a(awVar, Collections.emptyMap(), a, b, c, i + 1);
        }
    }

    void d(a aVar) {
        Context a = aVar.c().a();
        boolean o = o(aVar);
        String a2 = aVar.a("package");
        if (!TextUtils.isEmpty(a2)) {
            cu.a(a, a2, aVar.c().b(), o);
        }
    }

    void e(a aVar) {
        Object a = aVar.a("url");
        if (!TextUtils.isEmpty(a)) {
            long currentTimeMillis = 60000 + System.currentTimeMillis();
            String a2 = aVar.a("expirationTimeEpochSeconds");
            if (!TextUtils.isEmpty(a2)) {
                try {
                    currentTimeMillis = System.currentTimeMillis() + (Long.parseLong(a2) * 1000);
                } catch (Exception e) {
                    gd.a(6, b, "caught Exception with expirationTime parameter in onSendUrlAsync:" + a2);
                }
            }
            boolean z = false;
            a2 = aVar.a("sendYCookies");
            if (!TextUtils.isEmpty(a2)) {
                try {
                    z = Boolean.parseBoolean(a2);
                } catch (Exception e2) {
                    gd.a(6, b, "caught Exception with sendYCookies parameter in onSendUrlAsync:" + a2);
                }
            }
            i.a().i().b(new cd(aVar.c().a.a(), aVar.c().d().adGuid, this.a.a(aVar, a), currentTimeMillis, z));
        }
    }

    void b() {
        i.a().r();
    }

    void f(a aVar) {
        boolean z = aVar.b().containsKey("__sendToServer") && aVar.a("__sendToServer").equals("true");
        aVar.b("__sendToServer");
        String e = aVar.c().c().e();
        aw awVar = aVar.c().a;
        Map b = aVar.b();
        gd.a(3, b, "onLogEvent(" + e + ", " + awVar + ", " + z + ", " + b + ")");
        i.a().a(e, awVar, z, b);
    }

    void g(a aVar) {
        ap c = aVar.c().c();
        int c2 = c.c() + 1;
        String a = aVar.a("offset");
        if (a != null) {
            Object obj = -1;
            switch (a.hashCode()) {
                case 3377907:
                    if (a.equals("next")) {
                        obj = null;
                        break;
                    }
                    break;
                case 1126940025:
                    if (a.equals("current")) {
                        obj = 1;
                        break;
                    }
                    break;
            }
            switch (obj) {
                case null:
                    c2 = c.c() + 1;
                    break;
                case 1:
                    return;
                default:
                    try {
                        c2 = Integer.parseInt(a);
                        break;
                    } catch (Exception e) {
                        gd.a(6, b, "caught: " + e.getMessage());
                        break;
                    }
            }
        }
        f(aVar, c2);
    }

    private void f(a aVar, int i) {
        r b = aVar.c().b();
        ap c = aVar.c().c();
        boolean o = o(aVar);
        gd.a(3, b, "goToFrame: triggering event = " + aVar.c().a());
        if (i != c.c() && i < c.a().adFrames.size()) {
            gd.a(3, b, "goToFrame: currentIndex = " + c.c() + " and go to index: " + i);
            AdFrame adFrame = (AdFrame) c.a().adFrames.get(i);
            ax k = c.k();
            String str = adFrame.adSpaceLayout.format;
            if (str.equalsIgnoreCase(k.toString())) {
                gd.a(3, b, "goToFrame: Already a takeover Ad, just move to next frame. " + k.toString() + " to format " + str);
                c.a(i);
                a(b, null, true, o);
                return;
            }
            gd.a(3, b, "goToFrame: Moving now from " + k.toString() + " to format " + str);
            if (str.equalsIgnoreCase(ax.TAKEOVER.toString())) {
                c.a(i);
                cu.a(aVar.c().a(), b, true, o);
            }
        }
    }

    void c(a aVar, int i) {
        if (i > 10) {
            gd.a(5, b, "Maximum depth for event/action loop exceeded when performing action:" + aVar.toString());
            return;
        }
        String a = aVar.a("delay");
        long j = 30;
        if (!TextUtils.isEmpty(a)) {
            try {
                j = Long.parseLong(a);
            } catch (Exception e) {
                gd.a(6, b, "caught Exception with delay parameter in nextAdUnit:" + a);
            }
        }
        aVar.c().b().a(aVar.c().c(), j * 1000);
    }

    void d(a aVar, int i) {
        Context a = aVar.c().a();
        String a2 = aVar.a("idHash");
        if (!TextUtils.isEmpty(a2)) {
            ba k = i.a().k();
            for (az azVar : k.a(a2)) {
                az azVar2;
                aw awVar;
                aw awVar2 = aw.EV_CAP_NOT_EXHAUSTED;
                if (azVar2 != null && k.a(azVar2.e())) {
                    gd.a(4, b, "Discarding expired frequency cap info for id=" + a2);
                    k.a(azVar2.b(), a2);
                    azVar2 = null;
                }
                if (azVar2 == null || azVar2.j() < azVar2.g()) {
                    awVar = awVar2;
                } else {
                    gd.a(4, b, "Frequency cap exhausted for id=" + a2);
                    awVar = aw.EV_CAP_EXHAUSTED;
                }
                f.a().a(awVar.a(), 1);
                co.a(awVar, Collections.emptyMap(), a, aVar.c().b(), aVar.c().c(), i + 1);
            }
        }
    }

    void h(a aVar) {
        String a = aVar.a("idHash");
        if (!TextUtils.isEmpty(a)) {
            for (az azVar : i.a().k().a(a)) {
                azVar.a();
                gd.a(4, b, "updateViewCount:capType=" + azVar.b() + ",id=" + azVar.c() + ",capRemaining=" + azVar.g() + ",totalCap=" + azVar.h() + ",views=" + azVar.j());
                if (azVar.j() >= azVar.g()) {
                    String str = aVar.c().e().adSpace;
                    if (azVar.j() > azVar.g()) {
                        gd.a(6, b, "FlurryAdAction: !! rendering a capped object for id: " + azVar.c() + " for adspace: " + str);
                    } else {
                        gd.a(4, b, "FlurryAdAction: hit cap for id: " + azVar.c() + " for adspace: " + str);
                    }
                    ay ayVar = new ay();
                    ayVar.a = azVar;
                    ayVar.b();
                }
            }
        }
    }

    boolean a(String str) {
        Intent launchIntentForPackage = fp.a().e().getLaunchIntentForPackage(str);
        return launchIntentForPackage != null && hp.a(launchIntentForPackage);
    }

    public void a(Context context, String str, boolean z, r rVar, boolean z2, boolean z3) {
        if (context == null) {
            gd.a(5, b, "Unable to launch url, null context");
            return;
        }
        final String str2 = str;
        final Context context2 = context;
        final boolean z4 = z2;
        final r rVar2 = rVar;
        final boolean z5 = z3;
        final boolean z6 = z;
        fp.a().b(new hq(this) {
            final /* synthetic */ g g;

            public void safeRun() {
                if (TextUtils.isEmpty(str2)) {
                    gd.a(6, g.b, "Failed to launch: " + str2);
                    return;
                }
                String a = cv.a(str2);
                if (!TextUtils.isEmpty(a)) {
                    boolean z = false;
                    if (cv.d(a)) {
                        z = cu.a(context2, a);
                    }
                    if (!z && cv.f(a)) {
                        z = cu.b(context2, a);
                    }
                    if (!z && cv.e(a)) {
                        z = cu.c(context2, a);
                    }
                    if (z && z4) {
                        this.g.a(context2, rVar2);
                        return;
                    }
                    ap k = rVar2.k();
                    if (!z && k.r()) {
                        this.g.a(rVar2, a, z4, z5);
                    } else if (z || !z6) {
                        cu.a(context2, a, z5);
                    } else {
                        cu.a(context2, rVar2, a, z4, z5);
                    }
                }
            }
        });
    }

    private void a(Context context, r rVar) {
        ap k = rVar.k();
        if (k.l()) {
            ea eaVar = new ea();
            co.a(aw.INTERNAL_EV_APP_EXIT, Collections.emptyMap(), context, rVar, k, 0);
            eaVar.e = ea.a.CLOSE_ACTIVITY;
            eaVar.b();
            return;
        }
        co.a(aw.INTERNAL_EV_APP_EXIT, Collections.emptyMap(), context, rVar, k, 0);
    }

    private void a(r rVar, String str, boolean z, boolean z2) {
        ea eaVar = new ea();
        eaVar.e = ea.a.RELOAD_ACTIVITY;
        eaVar.a = rVar;
        eaVar.b = str;
        eaVar.c = z;
        eaVar.d = z2;
        eaVar.b();
    }

    public void a(Context context, String str, boolean z, r rVar) {
        if (context == null) {
            gd.a(5, b, "Cannot process redirect, null context");
        } else {
            a(context, str, z, rVar, false, false);
        }
    }

    boolean b(String str) {
        String packageName = fp.a().c().getPackageName();
        if (str == null) {
            str = "market://details?id=" + packageName;
        }
        return a(str, "android.intent.action.VIEW");
    }

    boolean a(String str, String str2) {
        Intent intent = new Intent(str2);
        intent.setData(Uri.parse(str));
        return hp.a(intent);
    }
}
