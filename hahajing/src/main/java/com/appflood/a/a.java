package com.appflood.a;

import android.content.Context;
import android.os.Environment;
import com.appflood.e.c;
import com.appflood.e.j;
import java.io.File;

public final class a {
    private File a;

    public a(String str, Context context) {
        if (c.i) {
            this.a = new File(Environment.getExternalStorageDirectory(), str);
            if (!this.a.exists()) {
                this.a.mkdirs();
            }
        } else {
            this.a = context.getDir(str, 1);
        }
        if (!this.a.exists()) {
            j.b(null, "cache dir " + this.a.getAbsolutePath() + " init fail");
        }
    }

    public final File a(String str) {
        return new File(this.a, j.a(str));
    }

    public final boolean a(String str, byte[] bArr) {
        return com.appflood.e.a.a(a(str), bArr);
    }
}
