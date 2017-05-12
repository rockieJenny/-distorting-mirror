package com.appflood.c;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import com.appflood.b.b;
import com.appflood.e.j;
import com.appflood.e.k;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONObject;

public final class h extends BroadcastReceiver {
    public final void onReceive(Context context, Intent intent) {
        if (intent != null) {
            try {
                if (!j.g(d.w) && !j.g(d.y)) {
                    Map hashMap = new HashMap();
                    JSONObject jSONObject = new JSONObject();
                    Bundle extras = intent.getExtras();
                    if (extras != null) {
                        for (String str : extras.keySet()) {
                            try {
                                jSONObject.put(str, extras.get(str));
                            } catch (Throwable th) {
                            }
                        }
                    }
                    hashMap.put("data", k.d(jSONObject.toString()));
                    new b(d.y, hashMap).f();
                }
            } catch (Throwable th2) {
                j.a(th2, "onReceiver");
            }
        }
    }
}
