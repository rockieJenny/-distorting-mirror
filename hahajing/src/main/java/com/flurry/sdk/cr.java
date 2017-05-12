package com.flurry.sdk;

import com.flurry.android.AdCreative;
import com.flurry.android.impl.ads.protocol.v13.AdFrame;
import com.flurry.android.impl.ads.protocol.v13.AdReportedId;
import com.flurry.android.impl.ads.protocol.v13.AdSpaceLayout;
import com.flurry.android.impl.ads.protocol.v13.AdUnit;
import com.flurry.android.impl.ads.protocol.v13.Callback;
import com.flurry.android.impl.ads.protocol.v13.FrequencyCapRequestInfo;
import com.flurry.android.impl.ads.protocol.v13.Location;
import com.flurry.android.impl.ads.protocol.v13.ScreenOrientationType;
import com.flurry.android.impl.ads.protocol.v13.SdkAdEvent;
import com.flurry.android.impl.ads.protocol.v13.SdkAdLog;
import com.flurry.android.impl.ads.protocol.v13.StreamInfo;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;

public class cr {
    private static final AtomicInteger a = new AtomicInteger(0);

    public static int a() {
        return a.incrementAndGet();
    }

    public static AdCreative a(AdSpaceLayout adSpaceLayout) {
        if (adSpaceLayout == null) {
            return null;
        }
        return new AdCreative(adSpaceLayout.adHeight, adSpaceLayout.adWidth, adSpaceLayout.format, adSpaceLayout.fix, adSpaceLayout.alignment);
    }

    public static AdCreative a(AdUnit adUnit) {
        if (adUnit == null) {
            return null;
        }
        List list = adUnit.adFrames;
        if (list == null || list.isEmpty()) {
            return null;
        }
        AdFrame adFrame = (AdFrame) list.get(0);
        if (adFrame == null) {
            return null;
        }
        AdSpaceLayout adSpaceLayout = adFrame.adSpaceLayout;
        if (adSpaceLayout == null) {
            return null;
        }
        return a(adSpaceLayout);
    }

    public static ScreenOrientationType b() {
        int j = hn.j();
        if (j == 1) {
            return ScreenOrientationType.PORTRAIT;
        }
        if (j == 2) {
            return ScreenOrientationType.LANDSCAPE;
        }
        return ScreenOrientationType.UNKNOWN;
    }

    public static Location c() {
        Location location = new Location();
        android.location.Location e = fh.a().e();
        if (e != null) {
            double longitude = (double) ((float) e.getLongitude());
            location.lat = (float) hp.a((double) ((float) e.getLatitude()), 3);
            location.lon = (float) hp.a(longitude, 3);
        }
        return location;
    }

    public static List<String> d() {
        List<String> arrayList = new ArrayList();
        HashMap c = fr.a().c();
        if (!(c == null || c.isEmpty())) {
            for (String add : c.keySet()) {
                arrayList.add(add);
            }
        }
        return arrayList;
    }

    public static List<AdReportedId> e() {
        List<AdReportedId> arrayList = new ArrayList();
        for (Entry entry : fe.a().h().entrySet()) {
            AdReportedId adReportedId = new AdReportedId();
            adReportedId.type = ((fl) entry.getKey()).d;
            if (((fl) entry.getKey()).e) {
                adReportedId.id = new String((byte[]) entry.getValue());
            } else {
                adReportedId.id = hp.b((byte[]) entry.getValue());
            }
            arrayList.add(adReportedId);
        }
        return arrayList;
    }

    public static List<FrequencyCapRequestInfo> f() {
        List<FrequencyCapRequestInfo> arrayList = new ArrayList();
        i.a().k().b();
        for (az azVar : i.a().k().a()) {
            FrequencyCapRequestInfo frequencyCapRequestInfo = new FrequencyCapRequestInfo();
            frequencyCapRequestInfo.capType = azVar.b();
            frequencyCapRequestInfo.id = azVar.c();
            frequencyCapRequestInfo.expirationTime = azVar.e();
            frequencyCapRequestInfo.serveTime = azVar.d();
            frequencyCapRequestInfo.lastViewedTime = azVar.k();
            frequencyCapRequestInfo.streamCapDurationMillis = azVar.f();
            frequencyCapRequestInfo.views = azVar.j();
            frequencyCapRequestInfo.capRemaining = azVar.g();
            frequencyCapRequestInfo.totalCap = azVar.h();
            frequencyCapRequestInfo.capDurationType = azVar.i();
            arrayList.add(frequencyCapRequestInfo);
        }
        return arrayList;
    }

    public static List<StreamInfo> g() {
        List<StreamInfo> arrayList = new ArrayList();
        i.a().n().b();
        for (bb bbVar : i.a().n().a()) {
            StreamInfo streamInfo = new StreamInfo();
            streamInfo.adId = bbVar.a();
            streamInfo.lastEvent = bbVar.e();
            streamInfo.renderedTime = bbVar.c();
            arrayList.add(streamInfo);
        }
        return arrayList;
    }

    public static List<SdkAdLog> a(List<at> list) {
        List<SdkAdLog> arrayList = new ArrayList();
        if (list == null) {
            return arrayList;
        }
        for (at atVar : list) {
            SdkAdLog sdkAdLog = new SdkAdLog();
            sdkAdLog.sessionId = atVar.c();
            sdkAdLog.adLogGUID = atVar.b() == null ? "" : atVar.b();
            List arrayList2 = new ArrayList();
            synchronized (atVar) {
                for (as asVar : atVar.d()) {
                    if (asVar.b()) {
                        SdkAdEvent sdkAdEvent = new SdkAdEvent();
                        sdkAdEvent.type = asVar.a();
                        sdkAdEvent.timeOffset = asVar.c();
                        Map d = asVar.d();
                        Map hashMap = new HashMap();
                        hashMap.putAll(d);
                        sdkAdEvent.params = hashMap;
                        arrayList2.add(sdkAdEvent);
                    }
                }
            }
            sdkAdLog.sdkAdEvents = arrayList2;
            if (arrayList2.size() > 0) {
                arrayList.add(sdkAdLog);
            }
        }
        return arrayList;
    }

    public static List<a> a(AdFrame adFrame, b bVar) {
        List<a> arrayList = new ArrayList();
        List<Callback> list = adFrame.callbacks;
        String a = bVar.a.a();
        for (Callback callback : list) {
            if (callback.event.equals(a)) {
                for (CharSequence charSequence : callback.actions) {
                    Map hashMap = new HashMap();
                    String charSequence2 = charSequence.toString();
                    int indexOf = charSequence2.indexOf(63);
                    if (indexOf != -1) {
                        String substring = charSequence2.substring(0, indexOf);
                        charSequence2 = charSequence2.substring(indexOf + 1);
                        if (charSequence2.contains("%{eventParams}")) {
                            charSequence2 = charSequence2.replace("%{eventParams}", "");
                            hashMap.putAll(bVar.b);
                        }
                        hashMap.putAll(hp.h(charSequence2));
                        charSequence2 = substring;
                    }
                    arrayList.add(new a(a.c(charSequence2), hashMap, bVar));
                }
            }
        }
        return arrayList;
    }
}
