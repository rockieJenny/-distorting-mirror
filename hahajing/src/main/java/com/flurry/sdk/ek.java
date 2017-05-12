package com.flurry.sdk;

import android.text.TextUtils;

public class ek {
    private final r a;
    private final String b;
    private final boolean c;
    private boolean d;

    public ek(r rVar, String str, boolean z, boolean z2) {
        this.a = rVar;
        this.b = str;
        this.c = z;
        this.d = z2;
    }

    public String a() {
        return this.b;
    }

    public boolean b() {
        return this.c;
    }

    public r c() {
        return this.a;
    }

    public boolean d() {
        return this.d;
    }

    public String toString() {
        return "fAdObjectId: " + this.a.d() + ", fLaunchUrl: " + this.b + ", fShouldCloseAd: " + this.c + ", fSendYCookie: " + this.d;
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof ek)) {
            return false;
        }
        ek ekVar = (ek) obj;
        if (TextUtils.equals(this.b, ekVar.b) && this.a.getClass().equals(ekVar.a.getClass()) && this.a.d() == ekVar.a.d() && this.c == ekVar.c && this.d == ekVar.d) {
            return true;
        }
        return false;
    }
}
