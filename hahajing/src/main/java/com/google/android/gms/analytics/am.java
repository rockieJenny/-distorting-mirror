package com.google.android.gms.analytics;

import android.app.Activity;
import java.util.HashMap;
import java.util.Map;

class am implements m {
    int CA = -1;
    Map<String, String> CB = new HashMap();
    String Cv;
    double Cw = -1.0d;
    int Cx = -1;
    int Cy = -1;
    int Cz = -1;

    am() {
    }

    public String am(String str) {
        String str2 = (String) this.CB.get(str);
        return str2 != null ? str2 : str;
    }

    public boolean fA() {
        return this.Cz != -1;
    }

    public boolean fB() {
        return this.Cz == 1;
    }

    public boolean fC() {
        return this.CA == 1;
    }

    public boolean ft() {
        return this.Cv != null;
    }

    public String fu() {
        return this.Cv;
    }

    public boolean fv() {
        return this.Cw >= 0.0d;
    }

    public double fw() {
        return this.Cw;
    }

    public boolean fx() {
        return this.Cx >= 0;
    }

    public boolean fy() {
        return this.Cy != -1;
    }

    public boolean fz() {
        return this.Cy == 1;
    }

    public int getSessionTimeout() {
        return this.Cx;
    }

    public String k(Activity activity) {
        return am(activity.getClass().getCanonicalName());
    }
}
