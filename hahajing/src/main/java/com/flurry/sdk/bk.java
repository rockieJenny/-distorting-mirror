package com.flurry.sdk;

import android.content.Context;
import android.os.Bundle;
import android.text.TextUtils;
import java.util.List;

public final class bk implements bi {
    private static final String a = bk.class.getSimpleName();

    public boolean a(Context context, bm bmVar) {
        if (bmVar == null) {
            return false;
        }
        Object a = bmVar.a();
        if (TextUtils.isEmpty(a)) {
            return false;
        }
        List<String> c = bmVar.c();
        if (c == null) {
            return false;
        }
        Bundle d = hm.d(context);
        String packageName = context.getPackageName();
        boolean z = true;
        for (String a2 : c) {
            boolean z2;
            if (a(a, packageName, d, a2)) {
                z2 = z;
            } else {
                z2 = false;
            }
            z = z2;
        }
        return z;
    }

    private boolean a(String str, String str2, Bundle bundle, String str3) {
        if (TextUtils.isEmpty(str) || TextUtils.isEmpty(str2) || bundle == null || TextUtils.isEmpty(str3)) {
            return false;
        }
        Object string = bundle.getString(str3);
        if (TextUtils.isEmpty(string)) {
            gd.b(a, str + ": package=\"" + str2 + "\": AndroidManifest.xml should include meta-data node with android:name=\"" + str3 + "\" and not empty value for android:value");
            return false;
        }
        gd.a(3, a, str + ": package=\"" + str2 + "\": AndroidManifest.xml has meta-data node with android:name=\"" + str3 + "\" and android:value=\"" + string + "\"");
        return true;
    }
}
