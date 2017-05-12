package com.flurry.sdk;

import com.flurry.android.impl.ads.protocol.v13.Location;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class e {
    private Location a;
    private final Map<String, String> b = new HashMap();
    private Map<String, String> c;
    private Integer d;
    private Integer e;
    private boolean f;
    private String g;

    public e copy() {
        e eVar = new e();
        if (this.a != null) {
            eVar.setLocation(this.a.lat, this.a.lon);
        }
        if (this.b != null) {
            eVar.setUserCookies(new HashMap(this.b));
        }
        if (this.c != null) {
            eVar.setKeywords(new HashMap(this.c));
        }
        if (this.d != null) {
            eVar.setAge(this.d.intValue());
        }
        if (this.e != null) {
            eVar.setGender(this.e.intValue());
        }
        eVar.setEnableTestAds(this.f);
        return eVar;
    }

    public Location getLocation() {
        return this.a;
    }

    public void setLocation(float f, float f2) {
        this.a = new Location();
        this.a.lat = f;
        this.a.lon = f2;
    }

    public void clearLocation() {
        this.a = null;
    }

    public Map<String, String> getUserCookies() {
        return this.b;
    }

    public void setUserCookies(Map<String, String> map) {
        if (map != null && !map.isEmpty()) {
            for (Entry entry : map.entrySet()) {
                if (!(entry.getKey() == null || entry.getValue() == null)) {
                    map.put(entry.getKey(), entry.getValue());
                }
            }
        }
    }

    public void clearUserCookies() {
        this.b.clear();
    }

    public Map<String, String> getKeywords() {
        return this.c;
    }

    public void setKeywords(Map<String, String> map) {
        this.c = map;
    }

    public void clearKeywords() {
        this.c = null;
    }

    public Integer getAge() {
        return this.d;
    }

    public void setAge(int i) {
        this.d = Integer.valueOf(i);
    }

    public void clearAge() {
        this.d = null;
    }

    public Integer getGender() {
        return this.e;
    }

    public void setGender(int i) {
        this.e = Integer.valueOf(i);
    }

    public void clearGender() {
        this.e = null;
    }

    public boolean getEnableTestAds() {
        return this.f;
    }

    public void setEnableTestAds(boolean z) {
        this.f = z;
    }

    public String getFixedAdId() {
        return this.g;
    }

    public void setFixedAdId(String str) {
        this.g = str;
    }

    public void clearFixedAdId() {
        this.g = null;
    }

    public boolean equals(Object obj) {
        if (obj == null || !(obj instanceof e)) {
            return false;
        }
        e eVar = (e) obj;
        if (this.a != eVar.a && this.a != null && !a(this.a, eVar.a)) {
            return false;
        }
        if (this.c != eVar.c && this.c != null && !this.c.equals(eVar.c)) {
            return false;
        }
        if (this.d != eVar.d && this.d != null && !this.d.equals(eVar.d)) {
            return false;
        }
        if (this.e != eVar.e && this.e != null && !this.e.equals(eVar.e)) {
            return false;
        }
        if (this.g == eVar.g || this.g == null || this.g.equals(eVar.g)) {
            return true;
        }
        return false;
    }

    public int hashCode() {
        int i = 17;
        if (this.a != null) {
            i = 17 ^ a(this.a).hashCode();
        }
        if (this.c != null) {
            i ^= this.c.hashCode();
        }
        if (this.d != null) {
            i ^= this.d.hashCode();
        }
        if (this.e != null) {
            i ^= this.e.hashCode();
        }
        if (this.g != null) {
            return i ^ this.g.hashCode();
        }
        return i;
    }

    private boolean a(Location location, Location location2) {
        if (location == null && location2 != null) {
            return false;
        }
        if (location != null && location2 == null) {
            return false;
        }
        Location a = a(location);
        Location a2 = a(location2);
        if (a.lat == a2.lat && a.lon == a2.lon) {
            return true;
        }
        return false;
    }

    private Location a(Location location) {
        if (location == null) {
            return null;
        }
        Location location2 = new Location();
        location2.lat = (float) Math.round(location.lat * 10.0f);
        location2.lon = (float) Math.round(location.lon * 10.0f);
        return location2;
    }
}
