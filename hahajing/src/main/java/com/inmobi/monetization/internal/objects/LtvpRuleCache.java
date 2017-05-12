package com.inmobi.monetization.internal.objects;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import com.inmobi.commons.analytics.util.AnalyticsUtils;
import com.inmobi.commons.internal.Log;
import com.inmobi.monetization.internal.LtvpRulesObject;
import java.util.HashMap;

public class LtvpRuleCache {
    static LtvpRuleCache a;
    private static String c = "IMAdMLtvpRuleCache";
    private static String d = "IMAdMLtvpRuleId";
    private static String e = "IMAdMLtvpHardExpiry";
    private static String f = "IMAdMLtvpSoftExpiry";
    private SharedPreferences b = null;

    public static LtvpRuleCache getInstance(Context context) {
        if (a == null) {
            synchronized (LtvpRuleCache.class) {
                if (a == null) {
                    a = new LtvpRuleCache(context);
                }
            }
        }
        return a;
    }

    private LtvpRuleCache(Context context) {
        this.b = context.getSharedPreferences(c, 0);
    }

    public void clearLtvpRuleCache() {
        Editor edit = this.b.edit();
        edit.clear();
        edit.commit();
    }

    public void setLtvpRuleConfig(LtvpRulesObject ltvpRulesObject) {
        a(ltvpRulesObject.getRuleId());
        a(ltvpRulesObject.getHardExpiry() + ltvpRulesObject.getTimeStamp());
        b(ltvpRulesObject.getSoftExpiry() + ltvpRulesObject.getTimeStamp());
        a(ltvpRulesObject.getRules());
    }

    private void a(long j) {
        Editor edit = this.b.edit();
        edit.putLong(e, j);
        edit.commit();
    }

    private void b(long j) {
        Editor edit = this.b.edit();
        edit.putLong(f, j);
        edit.commit();
    }

    private void a(String str) {
        Editor edit = this.b.edit();
        edit.putString(d, str);
        edit.commit();
    }

    private void a(HashMap<String, Integer> hashMap) {
        try {
            Editor edit = this.b.edit();
            for (String str : hashMap.keySet()) {
                edit.putInt(str, ((Integer) hashMap.get(str)).intValue());
            }
            edit.commit();
        } catch (Throwable e) {
            Log.internal(AnalyticsUtils.ANALYTICS_LOGGING_TAG, "Exception saving rule map", e);
        }
    }

    public long getSoftExpiryForLtvpRule() {
        return this.b.getLong(f, 0);
    }

    public long getHardExpiryForLtvpRule() {
        return this.b.getLong(e, 0);
    }

    public int getLtvpRule(long j) {
        return this.b.getInt(String.valueOf(j), 0);
    }

    public String getLtvpRuleId() {
        return this.b.getString(d, "");
    }
}
