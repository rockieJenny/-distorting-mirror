package com.flurry.sdk;

import com.flurry.android.impl.ads.protocol.v13.AdUnit;
import com.inmobi.commons.analytics.db.AnalyticsSQLiteHelper;
import java.util.Map.Entry;

public class cp extends gi {
    private static final String a = cp.class.getSimpleName();

    public String a(a aVar, String str) {
        String a = a(str);
        while (a != null) {
            str = a(aVar, str, a);
            a = a(str);
        }
        return str;
    }

    private String a(a aVar, String str, String str2) {
        r b = aVar.c().b();
        at f = aVar.c().f();
        AdUnit a = aVar.c().c().a();
        if (a("fids", str2)) {
            StringBuilder stringBuilder = new StringBuilder();
            int i = 1;
            for (Entry entry : fe.a().h().entrySet()) {
                if (i == 0) {
                    stringBuilder.append(",");
                }
                stringBuilder.append(((fl) entry.getKey()).d).append(":");
                if (((fl) entry.getKey()).e) {
                    stringBuilder.append(new String((byte[]) entry.getValue()));
                } else {
                    stringBuilder.append(hp.a((byte[]) entry.getValue()));
                }
                i = 0;
            }
            gd.a(3, a, "Replacing param fids with: " + stringBuilder.toString());
            return str.replace(str2, hp.c(stringBuilder.toString()));
        } else if (a(AnalyticsSQLiteHelper.EVENT_LIST_SID, str2)) {
            r0 = String.valueOf(fd.a().c());
            gd.a(3, a, "Replacing param sid with: " + r0);
            return str.replace(str2, hp.c(r0));
        } else if (a("lid", str2)) {
            r0 = String.valueOf(f.a());
            gd.a(3, a, "Replacing param lid with: " + r0);
            return str.replace(str2, hp.c(r0));
        } else if (a("guid", str2)) {
            r0 = f.b();
            gd.a(3, a, "Replacing param guid with: " + r0);
            return str.replace(str2, hp.c(r0));
        } else if (a("ats", str2)) {
            r0 = String.valueOf(System.currentTimeMillis());
            gd.a(3, a, "Replacing param ats with: " + r0);
            return str.replace(str2, hp.c(r0));
        } else if (a("apik", str2)) {
            r0 = fp.a().d();
            gd.a(3, a, "Replacing param apik with: " + r0);
            return str.replace(str2, hp.c(r0));
        } else if (a("hid", str2)) {
            r0 = a.adSpace;
            gd.a(3, a, "Replacing param hid with: " + r0);
            return str.replace(str2, hp.c(r0));
        } else if (a("eso", str2)) {
            r0 = Long.toString(System.currentTimeMillis() - fd.a().c());
            gd.a(3, a, "Replacing param eso with: " + r0);
            return str.replace(str2, hp.c(r0));
        } else if (a("uc", str2)) {
            Object obj;
            r0 = "";
            e l = b.l();
            if (l != null) {
                obj = r0;
                for (Entry entry2 : l.getUserCookies().entrySet()) {
                    String str3 = obj + "c_" + hp.c((String) entry2.getKey()) + "=" + hp.c((String) entry2.getValue()) + "&";
                }
            } else {
                obj = r0;
            }
            gd.a(3, a, "Replacing param uc with: " + obj);
            r0 = str.replace(str2, obj);
            if (!obj.equals("") || r0.length() <= 0) {
                return r0;
            }
            return r0.substring(0, r0.length() - 1);
        } else {
            gd.a(3, a, "Unknown param: " + str2);
            return str.replace(str2, "");
        }
    }
}
