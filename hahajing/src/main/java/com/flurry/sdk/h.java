package com.flurry.sdk;

import android.text.TextUtils;
import com.flurry.android.impl.ads.protocol.v13.AdFrame;
import com.flurry.android.impl.ads.protocol.v13.Callback;
import com.flurry.sdk.d.a;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public class h {
    private static final String a = h.class.getSimpleName();
    private static Map<String, au> b = Collections.unmodifiableMap(new HashMap<String, au>() {
        {
            put("playVideo", au.AC_MRAID_PLAY_VIDEO);
            put("open", au.AC_MRAID_OPEN);
            put("expand", au.AC_MRAID_DO_EXPAND);
            put("collapse", au.AC_MRAID_DO_COLLAPSE);
        }
    });
    private static Set<au> c = Collections.unmodifiableSet(new HashSet<au>() {
        {
            add(au.AC_NOTIFY_USER);
            add(au.AC_NEXT_FRAME);
            add(au.AC_CLOSE_AD);
            add(au.AC_MRAID_DO_EXPAND);
            add(au.AC_MRAID_DO_COLLAPSE);
            add(au.AC_VERIFY_URL);
        }
    });
    private final fy<c> d = new fy<c>(this) {
        final /* synthetic */ h a;

        {
            this.a = r1;
        }

        public /* synthetic */ void notify(fx fxVar) {
            a((c) fxVar);
        }

        public void a(c cVar) {
            gd.a(3, h.a, "Detected event was fired :" + cVar.a + " for adSpace:" + cVar.a.e().adSpace);
            this.a.a(cVar);
        }
    };

    public void a() {
        gd.a(3, a, "Unregister Event Handler ");
        fz.a().a(this.d);
    }

    public void b() {
        gd.a(3, a, "Registered Event Handler ");
        fz.a().a("com.flurry.android.impl.ads.AdEvent", this.d);
    }

    private void a(c cVar) {
        b bVar = cVar.a;
        String a = bVar.a.a();
        List<a> a2 = a(bVar.d(), bVar);
        f.a().a(a, 1);
        i.a().n().a(bVar);
        if (a2.isEmpty()) {
            for (Entry entry : b.entrySet()) {
                if (((String) entry.getKey()).equals(bVar.a.a())) {
                    a2.add(new a((au) entry.getValue(), bVar.b, bVar));
                }
            }
        }
        switch (bVar.a) {
            case EV_RENDER_FAILED:
                h(bVar, a2);
                break;
            case EV_RENDERED:
                a(bVar, (List) a2);
                break;
            case EV_VIDEO_START:
                b(bVar, a2);
                break;
            case EV_VIDEO_FIRST_QUARTILE:
                c(bVar, a2);
                break;
            case EV_VIDEO_MIDPOINT:
                d(bVar, a2);
                break;
            case EV_VIDEO_THIRD_QUARTILE:
                e(bVar, a2);
                break;
            case EV_VIDEO_COMPLETED:
                f(bVar, a2);
                break;
            case EV_CLICKED:
                g(bVar, a2);
                break;
            case EV_USER_CONFIRMED:
                g(bVar);
                break;
            case EV_AD_WILL_CLOSE:
                j(bVar, a2);
                break;
            case EV_PRIVACY:
                for (a aVar : a2) {
                    if (aVar.a().equals(au.AC_DIRECT_OPEN)) {
                        aVar.a("is_privacy", "true");
                    }
                }
                break;
            case EV_AD_CLOSED:
                c(bVar);
                break;
            case EV_REQUEST_AD_COLLAPSE:
                c(bVar);
                break;
            case INTERNAL_EV_AD_OPENED:
                a(bVar);
                break;
            case INTERNAL_EV_APP_EXIT:
                b(bVar);
                break;
            case EV_NATIVE_IMPRESSION:
                d(bVar);
                break;
            default:
                gd.a(3, a, "Event not handled: " + bVar.a + " for adSpace:" + bVar.c().p());
                break;
        }
        a(cVar, (List) a2);
    }

    private void a(b bVar, List<a> list) {
        dt m = bVar.c().m();
        if (!m.c()) {
            cz.d(bVar.c(), bVar.a.a(), bVar.d().adGuid);
            m.b(true);
            bVar.c().a(m);
        }
    }

    private void b(b bVar, List<a> list) {
        cz.e(bVar.c(), bVar.a.a(), bVar.d().adGuid);
        dt m = bVar.c().m();
        m.a(true);
        bVar.c().a(m);
    }

    private void c(b bVar, List<a> list) {
        cz.f(bVar.c(), bVar.a.a(), bVar.d().adGuid);
        dt m = bVar.c().m();
        m.c(true);
        bVar.c().a(m);
    }

    private void d(b bVar, List<a> list) {
        cz.g(bVar.c(), bVar.a.a(), bVar.d().adGuid);
        dt m = bVar.c().m();
        m.d(true);
        bVar.c().a(m);
    }

    private void e(b bVar, List<a> list) {
        cz.h(bVar.c(), bVar.a.a(), bVar.d().adGuid);
        dt m = bVar.c().m();
        m.e(true);
        bVar.c().a(m);
    }

    private void f(b bVar, List<a> list) {
        cz.i(bVar.c(), bVar.a.a(), bVar.d().adGuid);
        i.a().l().a(bVar.b());
        i.a().l().g();
        gd.a(3, a, "initLayout onVideoCompleted " + bVar.a());
        if (bVar.e().rewardable) {
            gd.a(3, a, "Ad unit is rewardable, onVideoCompleted listener will fire");
            gd.a(3, a, "Firing onVideoCompleted, adObject=" + bVar.b());
            d dVar = new d();
            dVar.a = bVar.b();
            dVar.b = a.kOnVideoCompleted;
            dVar.b();
            return;
        }
        gd.a(3, a, "Ad unit is not rewardable, onVideoCompleted listener will not fire");
    }

    private void d(b bVar) {
        gd.a(3, a, "Firing onAdImpressionLogged, adObject=" + bVar.b());
        d dVar = new d();
        dVar.a = bVar.b();
        dVar.b = a.kOnImpressionLogged;
        dVar.b();
    }

    private void g(b bVar, List<a> list) {
        bVar.c().p();
        gd.a(3, a, "Firing onClicked, adObject=" + bVar.b());
        d dVar = new d();
        dVar.a = bVar.b();
        dVar.b = a.kOnClicked;
        dVar.b();
        ap c = bVar.c();
        cy g = c.g();
        if (g != null) {
            dt m = bVar.c().m();
            String i = g.i();
            if (!(m == null || TextUtils.isEmpty(i))) {
                c.a(m);
                i.a().o().a(bVar.a(), i, true, bVar.b());
            }
            if (m != null && !m.h()) {
                m.g(true);
                c.a(m);
                cz.c(bVar.c(), bVar.a.a(), bVar.d().adGuid);
            }
        }
    }

    private void h(b bVar, List<a> list) {
        boolean f = f(bVar);
        if (bVar.b.remove("preRender") != null || f) {
            i(bVar, list);
        } else {
            e(bVar);
        }
        cz.b(bVar.c(), bVar.a.a(), bVar.d().adGuid);
        if (bVar.c().l()) {
            d();
        }
        i.a().l().c(bVar.c());
        i.a().l().a(bVar.b());
        i.a().l().g();
    }

    private void i(b bVar, List<a> list) {
        for (a a : list) {
            if (au.AC_NEXT_AD_UNIT.equals(a.a())) {
                Object obj = null;
                break;
            }
        }
        int i = 1;
        if (obj != null) {
            gd.a(3, a, "Firing onFetchFailed, adObject=" + bVar.b());
            d dVar = new d();
            dVar.a = bVar.b();
            dVar.b = a.kOnFetchFailed;
            dVar.b();
        }
    }

    private void e(b bVar) {
        gd.a(3, a, "Firing onRenderFailed, adObject=" + bVar.b());
        d dVar = new d();
        dVar.a = bVar.b();
        dVar.b = a.kOnRenderFailed;
        dVar.b();
    }

    public void a(b bVar) {
        gd.a(3, a, "Firing onOpen, adObject=" + bVar.b());
        d dVar = new d();
        dVar.a = bVar.b();
        dVar.b = a.kOnOpen;
        dVar.b();
    }

    public void b(b bVar) {
        gd.a(3, a, "Firing onAppExit, adObject=" + bVar.b());
        d dVar = new d();
        dVar.a = bVar.b();
        dVar.b = a.kOnAppExit;
        dVar.b();
        d();
    }

    private void d() {
        ea eaVar = new ea();
        eaVar.e = ea.a.CLOSE_ACTIVITY;
        eaVar.b();
    }

    public void c(b bVar) {
        gd.a(3, a, "Firing onClose, adObject=" + bVar.b());
        d dVar = new d();
        dVar.a = bVar.b();
        dVar.b = a.kOnClose;
        dVar.b();
        d();
    }

    private boolean f(b bVar) {
        boolean z;
        if (((String) bVar.b.remove("binding_3rd_party")) != null) {
            z = true;
        } else {
            z = false;
        }
        if (((AdFrame) bVar.e().adFrames.get(0)).binding == 4) {
            return true;
        }
        return z;
    }

    private void j(b bVar, List<a> list) {
        int i;
        cz.a(bVar.c(), bVar.a.a(), bVar.d().adGuid);
        for (a a : list) {
            if (c.contains(a.a())) {
                i = 1;
                break;
            }
        }
        i = 0;
        if (i == 0) {
            list.add(0, new a(au.AC_CLOSE_AD, Collections.emptyMap(), bVar));
            i.a().l().a(bVar.b());
            i.a().l().g();
        }
    }

    public List<a> a(AdFrame adFrame, b bVar) {
        List<a> arrayList = new ArrayList();
        List<Callback> list = adFrame.callbacks;
        String a = bVar.a.a();
        for (Callback callback : list) {
            if (callback.event.equals(a)) {
                for (String str : callback.actions) {
                    String str2;
                    Map hashMap = new HashMap();
                    int indexOf = str2.indexOf(63);
                    if (indexOf != -1) {
                        String substring = str2.substring(0, indexOf);
                        str2 = str2.substring(indexOf + 1);
                        if (str2.contains("%{eventParams}")) {
                            str2 = str2.replace("%{eventParams}", "");
                            hashMap.putAll(bVar.b);
                        }
                        hashMap.putAll(hp.h(str2));
                        str2 = substring;
                    }
                    arrayList.add(new a(a.c(str2), hashMap, bVar));
                }
            }
        }
        return arrayList;
    }

    private void a(c cVar, List<a> list) {
        a aVar = null;
        for (a aVar2 : list) {
            a aVar3;
            if (aVar2.a().equals(au.AC_LOG_EVENT)) {
                aVar2.a("__sendToServer", "true");
                aVar3 = aVar2;
            } else {
                aVar3 = aVar;
            }
            if (aVar2.a().equals(au.AC_LOAD_AD_COMPONENTS)) {
                for (Entry entry : aVar2.c().b.entrySet()) {
                    aVar2.a((String) entry.getKey(), (String) entry.getValue());
                }
            }
            gd.d(a, aVar2.toString());
            i.a().o().a(aVar2, cVar.b + 1);
            aVar = aVar3;
        }
        if (aVar == null) {
            Map hashMap = new HashMap();
            hashMap.put("__sendToServer", "false");
            aVar = new a(au.AC_LOG_EVENT, hashMap, cVar.a);
            gd.d(a, aVar.toString());
            i.a().o().a(aVar, cVar.b + 1);
        }
    }

    private void g(b bVar) {
        i.a().l().a(bVar.b());
        i.a().l().g();
    }
}
