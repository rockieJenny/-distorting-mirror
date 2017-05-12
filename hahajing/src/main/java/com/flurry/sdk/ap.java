package com.flurry.sdk;

import com.flurry.android.impl.ads.protocol.v13.AdFrame;
import com.flurry.android.impl.ads.protocol.v13.AdUnit;
import com.flurry.android.impl.ads.protocol.v13.FrequencyCapResponseInfo;
import com.flurry.android.impl.ads.protocol.v13.FrequencyCapType;
import com.flurry.android.impl.ads.protocol.v13.NativeAdInfo;
import com.flurry.android.impl.ads.protocol.v13.NativeAsset;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public final class ap implements Comparable<ap> {
    private static int a;
    private final int b;
    private final AdUnit c;
    private final List<aq> d = new ArrayList();
    private final Map<String, ar> e = new HashMap();
    private final LinkedList<ek> f = new LinkedList();
    private int g;
    private boolean h = false;
    private boolean i = false;
    private String j;

    public /* synthetic */ int compareTo(Object obj) {
        return a((ap) obj);
    }

    public ap(AdUnit adUnit) {
        int i = a + 1;
        a = i;
        this.b = i;
        this.c = adUnit;
        for (i = 0; i < adUnit.adFrames.size(); i++) {
            this.d.add(new aq());
            String str = ((AdFrame) adUnit.adFrames.get(i)).adGuid;
            this.e.put(str, new ar(str));
        }
    }

    public int a(ap apVar) {
        if (apVar == null) {
            throw new NullPointerException("another cannot be null");
        } else if (this.b > apVar.b) {
            return 1;
        } else {
            if (this.b < apVar.b) {
                return -1;
            }
            return 0;
        }
    }

    public AdUnit a() {
        return this.c;
    }

    public at b() {
        return a(e());
    }

    public at a(String str) {
        return ((ar) this.e.get(str)).a();
    }

    public int c() {
        return this.g;
    }

    public void a(int i) {
        this.g = i;
    }

    public AdFrame b(int i) {
        List list = this.c.adFrames;
        if (list == null || list.size() < i) {
            return null;
        }
        return (AdFrame) list.get(i);
    }

    public ax d() {
        return c(c());
    }

    public ax c(int i) {
        for (ax axVar : ax.values()) {
            if (axVar.toString().equals(h(i))) {
                return axVar;
            }
        }
        return ax.UNKNOWN;
    }

    private String h(int i) {
        AdFrame b = b(i);
        return b != null ? b.adSpaceLayout.format : null;
    }

    public String e() {
        return ((AdFrame) this.c.adFrames.get(this.g)).adGuid;
    }

    public FrequencyCapResponseInfo f() {
        List<FrequencyCapResponseInfo> list = this.c.frequencyCapResponseInfoList;
        if (list == null) {
            return null;
        }
        for (FrequencyCapResponseInfo frequencyCapResponseInfo : list) {
            if (FrequencyCapType.STREAM.equals(frequencyCapResponseInfo.capType)) {
                return frequencyCapResponseInfo;
            }
        }
        return null;
    }

    public cy g() {
        return d(this.g);
    }

    public cy d(int i) {
        if (i < 0 || i > this.d.size()) {
            return null;
        }
        return ((aq) this.d.get(i)).a();
    }

    public void a(int i, cy cyVar) {
        if (i >= 0 && i <= this.d.size()) {
            ((aq) this.d.get(i)).a(cyVar);
        }
    }

    public NativeAdInfo h() {
        return this.c.nativeAdInfo;
    }

    public List<NativeAsset> i() {
        if (this.c.nativeAdInfo != null) {
            return this.c.nativeAdInfo.assets;
        }
        return Collections.emptyList();
    }

    public AdFrame j() {
        return this.c != null ? b(this.g) : null;
    }

    public ax k() {
        return this.c != null ? c(this.g) : ax.UNKNOWN;
    }

    public boolean l() {
        if (k().equals(ax.TAKEOVER)) {
            return true;
        }
        return false;
    }

    public dt m() {
        return e(this.g);
    }

    public dt e(int i) {
        if (i < 0 || i > this.d.size()) {
            return null;
        }
        return ((aq) this.d.get(i)).b();
    }

    public void a(dt dtVar) {
        a(this.g, dtVar);
    }

    public void a(int i, dt dtVar) {
        if (i >= 0 && i <= this.d.size()) {
            ((aq) this.d.get(i)).a(dtVar);
        }
    }

    public List<String> f(int i) {
        if (i < 0 || i > this.d.size()) {
            return Collections.emptyList();
        }
        return ((aq) this.d.get(i)).c();
    }

    public void a(int i, List<String> list) {
        if (i >= 0 && i <= this.d.size()) {
            ((aq) this.d.get(i)).a((List) list);
        }
    }

    public boolean n() {
        List list = this.c.adFrames;
        int i = 0;
        while (i < list.size()) {
            ag a = ag.a(((AdFrame) list.get(i)).cachingEnum);
            if ((ag.CACHE_ONLY.equals(a) || ag.CACHE_OR_STREAM.equals(a)) && g(i)) {
                return true;
            }
            i++;
        }
        return false;
    }

    public boolean o() {
        List list = this.c.adFrames;
        int i = 0;
        while (i < list.size()) {
            if (ag.CACHE_ONLY.equals(ag.a(((AdFrame) list.get(i)).cachingEnum)) && g(i)) {
                return true;
            }
            i++;
        }
        return false;
    }

    public boolean g(int i) {
        return !f(i).isEmpty();
    }

    public String p() {
        return this.c.adSpace;
    }

    public boolean q() {
        return this.h;
    }

    public void a(boolean z) {
        this.h = z;
    }

    public boolean r() {
        return this.i;
    }

    public void b(boolean z) {
        this.i = z;
    }

    public synchronized ek s() {
        ek ekVar;
        synchronized (this.f) {
            if (this.f.size() > 0) {
                ekVar = (ek) this.f.pop();
            } else {
                ekVar = null;
            }
        }
        return ekVar;
    }

    public synchronized ek t() {
        ek ekVar;
        synchronized (this.f) {
            if (this.f.size() > 0) {
                ekVar = (ek) this.f.peek();
            } else {
                ekVar = null;
            }
        }
        return ekVar;
    }

    public void u() {
        synchronized (this.f) {
            this.f.clear();
        }
        a(0);
    }

    public void a(ek ekVar) {
        synchronized (this.f) {
            if (b(ekVar)) {
                this.f.push(ekVar);
            }
        }
    }

    private boolean b(ek ekVar) {
        if (this.f.size() > 0) {
            ek ekVar2 = (ek) this.f.peek();
            if (ekVar2 != null && ekVar2.equals(ekVar)) {
                return false;
            }
        }
        return true;
    }

    public void b(String str) {
        this.j = str;
    }

    public String v() {
        return this.j;
    }
}
