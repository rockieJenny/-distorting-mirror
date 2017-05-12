package com.flurry.sdk;

import android.text.TextUtils;
import com.flurry.android.impl.ads.protocol.v13.FrequencyCapResponseInfo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class bc {
    private static final String a = bc.class.getSimpleName();
    private final HashMap<String, bb> b = new HashMap();

    public synchronized void a(b bVar) {
        if (bVar != null) {
            FrequencyCapResponseInfo f = bVar.c().f();
            if (f != null) {
                Object obj = f.id;
                if (!TextUtils.isEmpty(obj)) {
                    bb bbVar = (bb) this.b.get(obj);
                    if (bbVar == null) {
                        bbVar = new bb(obj, f.streamCapDurationMillis);
                        this.b.put(obj, bbVar);
                    }
                    bbVar.a(bVar.a.a());
                    bbVar.b(System.currentTimeMillis());
                    if (aw.EV_RENDERED.equals(bVar.a)) {
                        bbVar.a(System.currentTimeMillis());
                    }
                    bbVar.b(bVar.a.a());
                }
            }
        }
    }

    public synchronized List<bb> a() {
        return new ArrayList(this.b.values());
    }

    public synchronized void b() {
        for (bb bbVar : a()) {
            if (System.currentTimeMillis() > bbVar.d() + bbVar.b()) {
                this.b.remove(bbVar.a());
            }
        }
    }
}
