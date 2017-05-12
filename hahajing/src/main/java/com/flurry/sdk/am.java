package com.flurry.sdk;

import android.text.TextUtils;
import com.flurry.sdk.al.c;
import java.io.IOException;
import java.io.OutputStream;

public class am extends ai {
    private static final String d = am.class.getSimpleName();
    protected final al a;
    protected final String b;
    protected c c;

    public am(al alVar, String str) {
        this.a = alVar;
        this.b = str;
    }

    protected OutputStream f() throws IOException {
        if (this.c != null) {
            return this.c.a();
        }
        if (this.a == null) {
            throw new IOException("No cache specified");
        } else if (TextUtils.isEmpty(this.b)) {
            throw new IOException("No cache key specified");
        } else {
            this.c = this.a.b(this.b);
            if (this.c != null) {
                return this.c.a();
            }
            throw new IOException("Could not open writer for key: " + this.b);
        }
    }

    protected void g() {
        hp.a(this.c);
        this.c = null;
    }

    protected void h() {
        if (this.a != null && !TextUtils.isEmpty(this.b)) {
            try {
                this.a.c(this.b);
            } catch (Exception e) {
                gd.a(3, d, "Error removing result for key: " + this.b + " -- " + e);
            }
        }
    }
}
