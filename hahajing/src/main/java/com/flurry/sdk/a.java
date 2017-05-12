package com.flurry.sdk;

import android.text.TextUtils;
import java.util.Map;
import java.util.Map.Entry;

public class a {
    private static final String a = a.class.getSimpleName();
    private final au b;
    private final Map<String, String> c;
    private final b d;

    public a(au auVar, Map<String, String> map, b bVar) {
        this.b = auVar;
        this.c = map;
        this.d = bVar;
    }

    public au a() {
        return this.b;
    }

    public Map<String, String> b() {
        return this.c;
    }

    public String a(String str) {
        if (this.c == null || TextUtils.isEmpty(str)) {
            return null;
        }
        return (String) this.c.get(str);
    }

    public String b(String str) {
        if (this.c == null || TextUtils.isEmpty(str)) {
            return null;
        }
        return (String) this.c.remove(str);
    }

    public String a(String str, String str2) {
        if (this.c == null || TextUtils.isEmpty(str)) {
            return null;
        }
        return (String) this.c.put(str, str2);
    }

    public b c() {
        return this.d;
    }

    public static au c(String str) {
        for (au auVar : au.values()) {
            if (auVar.toString().equals(str)) {
                gd.a(5, a, "Action Type for name: " + str + " is " + auVar);
                return auVar;
            }
        }
        return au.AC_UNKNOWN;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("action=");
        stringBuilder.append(this.b.toString());
        stringBuilder.append(",params=");
        for (Entry entry : this.c.entrySet()) {
            stringBuilder.append(",key=" + ((String) entry.getKey()) + ",value=" + ((String) entry.getValue()));
        }
        stringBuilder.append(",");
        stringBuilder.append(",fTriggeringEvent=");
        stringBuilder.append(this.d);
        return stringBuilder.toString();
    }
}
