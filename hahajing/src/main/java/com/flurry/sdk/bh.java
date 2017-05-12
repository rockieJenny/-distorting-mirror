package com.flurry.sdk;

import android.content.Context;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class bh implements bi {
    private final List<bi> a;

    public bh() {
        List arrayList = new ArrayList();
        arrayList.add(new bg());
        arrayList.add(new bl());
        arrayList.add(new be());
        arrayList.add(new bk());
        this.a = Collections.unmodifiableList(arrayList);
    }

    public boolean a(Context context, bm bmVar) {
        if (context == null || bmVar == null) {
            return false;
        }
        boolean z = true;
        for (bi a : this.a) {
            boolean z2;
            if (a.a(context, bmVar)) {
                z2 = z;
            } else {
                z2 = false;
            }
            z = z2;
        }
        return z;
    }
}
