package com.flurry.sdk;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class ib {
    private jb a = jb.a;
    private ip b = ip.DEFAULT;
    private hz c = hy.IDENTITY;
    private final Map<Type, ic<?>> d = new HashMap();
    private final List<is> e = new ArrayList();
    private final List<is> f = new ArrayList();
    private boolean g;
    private String h;
    private int i = 2;
    private int j = 2;
    private boolean k;
    private boolean l;
    private boolean m = true;
    private boolean n;
    private boolean o;

    public ib a() {
        this.g = true;
        return this;
    }

    public ia b() {
        List arrayList = new ArrayList();
        arrayList.addAll(this.e);
        Collections.reverse(arrayList);
        arrayList.addAll(this.f);
        a(this.h, this.i, this.j, arrayList);
        return new ia(this.a, this.c, this.d, this.g, this.k, this.o, this.m, this.n, this.l, this.b, arrayList);
    }

    private void a(String str, int i, int i2, List<is> list) {
        Object hvVar;
        if (str != null && !"".equals(str.trim())) {
            hvVar = new hv(str);
        } else if (i != 2 && i2 != 2) {
            hvVar = new hv(i, i2);
        } else {
            return;
        }
        list.add(iq.a(jw.b(Date.class), hvVar));
        list.add(iq.a(jw.b(Timestamp.class), hvVar));
        list.add(iq.a(jw.b(java.sql.Date.class), hvVar));
    }
}
