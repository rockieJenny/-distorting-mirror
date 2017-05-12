package com.google.android.gms.internal;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.internal.hp.a;
import com.google.android.gms.internal.mv.b;
import com.google.android.gms.internal.mv.c;
import com.google.android.gms.internal.mv.d;
import com.inmobi.commons.analytics.db.AnalyticsEvent;
import com.inmobi.commons.analytics.db.AnalyticsSQLiteHelper;
import java.util.ArrayList;
import java.util.List;
import org.xmlrpc.android.IXMLRPCSerializer;

public class hy {
    private static hh a(String str, c cVar) {
        return new hh(qw.f(cVar), new a(str).G(true).au(str).at("blob").fG());
    }

    public static hr a(Action action, String str, long j, String str2, int i) {
        Bundle bundle = new Bundle();
        bundle.putAll(action.fI());
        Bundle bundle2 = bundle.getBundle("object");
        Uri parse = bundle2.containsKey(AnalyticsEvent.EVENT_ID) ? Uri.parse(bundle2.getString(AnalyticsEvent.EVENT_ID)) : null;
        String string = bundle2.getString(IXMLRPCSerializer.TAG_NAME);
        String string2 = bundle2.getString(AnalyticsSQLiteHelper.EVENT_LIST_TYPE);
        Intent a = hz.a(str2, Uri.parse(bundle2.getString("url")));
        hd.a a2 = hr.a(a, string, parse, string2, null);
        a2.a(a(".private:action", d(action.fI())));
        a2.a(aw(str));
        return new hr(hr.a(str2, a), j, i, null, a2.fD());
    }

    private static hh aw(String str) {
        return new hh(str, new a(".private:actionId").G(true).au(".private:actionId").at("blob").fG());
    }

    static c d(Bundle bundle) {
        c cVar = new c();
        List arrayList = new ArrayList();
        for (String str : bundle.keySet()) {
            Object obj = bundle.get(str);
            b bVar = new b();
            bVar.name = str;
            bVar.afz = new d();
            if (obj instanceof String) {
                bVar.afz.NJ = (String) obj;
            } else if (obj instanceof Bundle) {
                bVar.afz.afE = d((Bundle) obj);
            } else {
                Log.e("AppDataSearchClient", "Unsupported value: " + obj);
            }
            arrayList.add(bVar);
        }
        if (bundle.containsKey(AnalyticsSQLiteHelper.EVENT_LIST_TYPE)) {
            cVar.type = bundle.getString(AnalyticsSQLiteHelper.EVENT_LIST_TYPE);
        }
        cVar.afA = (b[]) arrayList.toArray(new b[arrayList.size()]);
        return cVar;
    }
}
