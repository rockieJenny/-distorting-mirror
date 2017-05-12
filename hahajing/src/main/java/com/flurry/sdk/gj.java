package com.flurry.sdk;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.Set;

public class gj {
    private final HashMap<String, String> a = new HashMap();

    public void a(String str, String str2) {
        if (str != null) {
            this.a.put(str, str2);
        }
    }

    public String a(String str) {
        if (str == null) {
            return null;
        }
        return (String) this.a.get(str);
    }

    public int a() {
        return this.a.size();
    }

    public String b() {
        StringBuilder stringBuilder = new StringBuilder();
        Set<Entry> entrySet = this.a.entrySet();
        if (entrySet.size() > 0) {
            for (Entry entry : entrySet) {
                stringBuilder.append(hp.c((String) entry.getKey()));
                stringBuilder.append("=");
                stringBuilder.append(hp.c((String) entry.getValue()));
                stringBuilder.append("&");
            }
            stringBuilder.deleteCharAt(stringBuilder.length() - 1);
        }
        return stringBuilder.toString();
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof gj)) {
            return false;
        }
        gj gjVar = (gj) obj;
        if (gjVar.a() != a()) {
            return false;
        }
        for (Entry entry : this.a.entrySet()) {
            String str = (String) entry.getValue();
            String a = gjVar.a((String) entry.getKey());
            if (str != a && (str == null || !str.equals(a))) {
                return false;
            }
        }
        return true;
    }
}
