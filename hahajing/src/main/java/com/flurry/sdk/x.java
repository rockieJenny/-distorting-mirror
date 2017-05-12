package com.flurry.sdk;

import android.text.TextUtils;
import com.flurry.android.impl.ads.protocol.v13.FrequencyCapResponseInfo;
import com.flurry.sdk.ab.a;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.TreeSet;

public class x {
    private static final String a = x.class.getSimpleName();
    private final fy<ab> b = new fy<ab>(this) {
        final /* synthetic */ x a;

        {
            this.a = r1;
        }

        public /* synthetic */ void notify(fx fxVar) {
            a((ab) fxVar);
        }

        public void a(ab abVar) {
            if (a.RESUME.equals(abVar.a)) {
                this.a.d();
            }
        }
    };
    private final fy<ay> c = new fy<ay>(this) {
        final /* synthetic */ x a;

        {
            this.a = r1;
        }

        public /* synthetic */ void notify(fx fxVar) {
            a((ay) fxVar);
        }

        public void a(ay ayVar) {
            this.a.a(ayVar.a);
        }
    };
    private final TreeSet<ap> d = new TreeSet();
    private final String e;

    public x(String str) {
        this.e = str;
        fz.a().a("com.flurry.android.sdk.AssetCacheManagerStatusEvent", this.b);
        fz.a().a("com.flurry.android.impl.ads.FreqCapEvent", this.c);
    }

    public synchronized void a() {
        this.d.clear();
        fz.a().a(this.b);
        fz.a().a(this.c);
    }

    public synchronized int b() {
        e();
        return this.d.size();
    }

    public synchronized void a(Collection<ap> collection) {
        if (collection != null) {
            this.d.addAll(collection);
        }
    }

    public synchronized List<ap> c() {
        List<ap> arrayList;
        arrayList = new ArrayList();
        ap apVar = (ap) this.d.pollFirst();
        if (apVar != null) {
            arrayList.add(apVar);
            Object obj = apVar.a().groupId;
            if (!TextUtils.isEmpty(obj)) {
                Iterator it = this.d.iterator();
                while (it.hasNext()) {
                    apVar = (ap) it.next();
                    if (!obj.equals(apVar.a().groupId)) {
                        break;
                    }
                    arrayList.add(apVar);
                    it.remove();
                }
            }
        }
        return arrayList;
    }

    public synchronized void a(String str) {
        if (!TextUtils.isEmpty(str)) {
            Iterator it = this.d.iterator();
            while (it.hasNext()) {
                ap apVar = (ap) it.next();
                if (apVar.a().groupId.equals(str)) {
                    gd.a(3, a, "Removed grouped ad unit -- adspace: " + apVar.p());
                    it.remove();
                }
            }
        }
    }

    public synchronized void a(az azVar) {
        if (azVar != null) {
            Iterator it = this.d.iterator();
            while (it.hasNext()) {
                ap apVar = (ap) it.next();
                List<FrequencyCapResponseInfo> list = apVar.a().frequencyCapResponseInfoList;
                if (list != null) {
                    for (FrequencyCapResponseInfo frequencyCapResponseInfo : list) {
                        if (azVar.b().equals(frequencyCapResponseInfo.capType) && azVar.c().equals(frequencyCapResponseInfo.id)) {
                            gd.a(3, a, "Removed frequency capped ad unit -- adspace: " + apVar.p());
                            it.remove();
                        }
                    }
                }
            }
        }
    }

    private synchronized void e() {
        Iterator it = this.d.iterator();
        while (it.hasNext()) {
            ap apVar = (ap) it.next();
            if (!hp.a(apVar.a().expiration)) {
                gd.a(3, a, "Removed expired ad unit -- adspace: " + apVar.p());
                it.remove();
            }
        }
    }

    public synchronized void d() {
        final List arrayList = new ArrayList(this.d);
        fp.a().b(new hq(this) {
            final /* synthetic */ x b;

            public void safeRun() {
                i.a().l().a(arrayList);
            }
        });
    }
}
