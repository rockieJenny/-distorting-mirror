package com.flurry.sdk;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import com.flurry.android.AdCreative;
import java.util.Collections;
import java.util.List;

public abstract class bj implements ef, eo {
    protected abstract ec a(Context context, r rVar, AdCreative adCreative, Bundle bundle);

    protected abstract en a(Context context, r rVar, Bundle bundle);

    protected abstract String f();

    protected abstract List<bf> g();

    protected abstract List<ActivityInfo> j();

    protected abstract List<bf> k();

    protected abstract List<String> n();

    protected abstract List<String> o();

    public en a(Context context, r rVar) {
        if (context == null || rVar == null || !a(context, d())) {
            return null;
        }
        Bundle a = a(context);
        if (a != null) {
            return a(context, rVar, a);
        }
        return null;
    }

    public ec b(Context context, r rVar) {
        if (context == null || rVar == null || !b(context, e())) {
            return null;
        }
        Bundle b = b(context);
        if (b == null) {
            return null;
        }
        AdCreative a = cr.a(rVar.k().a());
        if (a != null) {
            return a(context, rVar, a, b);
        }
        return null;
    }

    protected boolean a(Context context, bm bmVar) {
        if (context == null || bmVar == null) {
            return false;
        }
        bi a = a();
        if (a != null) {
            return a.a(context, bmVar);
        }
        return false;
    }

    protected boolean b(Context context, bm bmVar) {
        if (context == null || bmVar == null) {
            return false;
        }
        bi b = b();
        if (b != null) {
            return b.a(context, bmVar);
        }
        return false;
    }

    protected bi a() {
        return c();
    }

    protected bi b() {
        return c();
    }

    protected bi c() {
        return new bh();
    }

    protected bm d() {
        return new bm(f(), g(), h(), i(), j());
    }

    protected bm e() {
        return new bm(f(), k(), l(), m(), Collections.emptyList());
    }

    protected List<String> h() {
        return n();
    }

    protected List<String> i() {
        return o();
    }

    protected List<String> l() {
        return n();
    }

    protected List<String> m() {
        return o();
    }

    protected Bundle a(Context context) {
        return c(context);
    }

    protected Bundle b(Context context) {
        return c(context);
    }

    protected Bundle c(Context context) {
        return hm.d(context);
    }
}
