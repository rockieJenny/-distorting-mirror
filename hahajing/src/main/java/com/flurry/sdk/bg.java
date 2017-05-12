package com.flurry.sdk;

import android.content.Context;
import android.text.TextUtils;
import java.util.List;

public final class bg implements bi {
    private static final String a = bg.class.getSimpleName();

    public boolean a(Context context, bm bmVar) {
        if (bmVar == null) {
            return false;
        }
        Object a = bmVar.a();
        if (TextUtils.isEmpty(a)) {
            return false;
        }
        List<bf> b = bmVar.b();
        if (b == null) {
            return false;
        }
        String packageName = context.getPackageName();
        boolean z = true;
        for (bf a2 : b) {
            boolean z2;
            if (a(a, packageName, a2)) {
                z2 = z;
            } else {
                z2 = false;
            }
            z = z2;
        }
        return z;
    }

    private boolean a(String str, String str2, bf bfVar) {
        boolean z = false;
        if (!(TextUtils.isEmpty(str) || TextUtils.isEmpty(str2) || bfVar == null)) {
            try {
                if (!TextUtils.isEmpty(bfVar.c())) {
                    Class.forName(bfVar.c());
                    z = true;
                }
            } catch (Throwable e) {
                gd.a(6, a, "failed to find third party ad provider api with exception: ", e);
            } catch (Throwable e2) {
                gd.a(6, a, "failed to initialize third party ad provider api with exception: ", e2);
            } catch (Throwable e22) {
                gd.a(6, a, "failed to link third party ad provider api with exception: ", e22);
            }
            if (z) {
                gd.a(3, a, str + ": package=\"" + str2 + "\": apk has ad provider library with name=\"" + bfVar.a() + "\" and version=\"" + bfVar.b() + "\" or higher");
            } else {
                gd.b(a, str + ": package=\"" + str2 + "\": apk should include ad provider library with name=\"" + bfVar.a() + "\" and version=\"" + bfVar.b() + "\" or higher");
            }
        }
        return z;
    }
}
