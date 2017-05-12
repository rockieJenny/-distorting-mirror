package com.flurry.sdk;

import android.text.TextUtils;
import java.io.File;

public class ct {
    public static File a(String str) {
        return new File(ho.b(true).getPath() + File.separator + ".fcaches" + File.separator + str);
    }

    public static File b(String str) {
        return new File(ho.a(true).getPath() + File.separator + ".fcaches" + File.separator + str);
    }

    public static File a(String str, int i) {
        return new File(ho.a(true).getPath() + File.separator + ".fcaches" + File.separator + str + File.separator + i);
    }

    public static String c(String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        return String.format("%016x", new Object[]{Long.valueOf(hp.i(str))}).trim();
    }
}
