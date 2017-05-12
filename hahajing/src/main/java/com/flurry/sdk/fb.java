package com.flurry.sdk;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.app.ActivityManager.MemoryInfo;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.os.Environment;
import android.os.StatFs;
import android.os.SystemClock;
import com.flurry.android.FlurryEventRecordStatus;
import com.flurry.sdk.hh.a;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class fb implements hf, a {
    static int a = 100;
    static int b = 10;
    static int c = 1000;
    static int d = 160000;
    static int e = 50;
    private static final String f = fb.class.getSimpleName();
    private int A = 0;
    private final er B = new er();
    private final fy<ff> C = new fy<ff>(this) {
        final /* synthetic */ fb a;

        {
            this.a = r1;
        }

        public /* synthetic */ void notify(fx fxVar) {
            a((ff) fxVar);
        }

        public void a(ff ffVar) {
            fp.a().b(new hq(this) {
                final /* synthetic */ AnonymousClass1 a;

                {
                    this.a = r1;
                }

                public void safeRun() {
                    this.a.a.b(true);
                }
            });
        }
    };
    private final AtomicInteger g = new AtomicInteger(0);
    private final AtomicInteger h = new AtomicInteger(0);
    private final AtomicInteger i = new AtomicInteger(0);
    private File j;
    private fw<List<ez>> k;
    private boolean l;
    private long m;
    private boolean n;
    private String o;
    private byte p;
    private Long q;
    private final List<ez> r = new ArrayList();
    private final Map<String, List<String>> s = new HashMap();
    private final Map<String, String> t = new HashMap();
    private final Map<String, ev> u = new HashMap();
    private final List<ew> v = new ArrayList();
    private boolean w = true;
    private int x = 0;
    private final List<eu> y = new ArrayList();
    private int z = 0;

    public void a(Context context) {
        hh a = hg.a();
        this.n = ((Boolean) a.a("LogEvents")).booleanValue();
        a.a("LogEvents", (a) this);
        gd.a(4, f, "initSettings, LogEvents = " + this.n);
        this.o = (String) a.a("UserId");
        a.a("UserId", (a) this);
        gd.a(4, f, "initSettings, UserId = " + this.o);
        this.p = ((Byte) a.a("Gender")).byteValue();
        a.a("Gender", (a) this);
        gd.a(4, f, "initSettings, Gender = " + this.p);
        this.q = (Long) a.a("Age");
        a.a("Age", (a) this);
        gd.a(4, f, "initSettings, BirthDate = " + this.q);
        this.j = context.getFileStreamPath(m());
        this.k = new fw(context.getFileStreamPath(n()), ".yflurryreport.", 1, new ha<List<ez>>(this) {
            final /* synthetic */ fb a;

            {
                this.a = r1;
            }

            public gx<List<ez>> a(int i) {
                return new gw(new ez.a());
            }
        });
        d(context);
        a(true);
        fp.a().b(new hq(this) {
            final /* synthetic */ fb a;

            {
                this.a = r1;
            }

            public void safeRun() {
                this.a.k();
            }
        });
        fp.a().b(new hq(this) {
            final /* synthetic */ fb a;

            {
                this.a = r1;
            }

            public void safeRun() {
                this.a.o();
            }
        });
        if (fe.a().c()) {
            fp.a().b(new hq(this) {
                final /* synthetic */ fb a;

                {
                    this.a = r1;
                }

                public void safeRun() {
                    this.a.b(true);
                }
            });
        } else {
            fz.a().a("com.flurry.android.sdk.IdProviderFinishedEvent", this.C);
        }
    }

    public synchronized void b(Context context) {
    }

    public synchronized void c(Context context) {
        a(false);
        a(fd.a().e());
        fp.a().b(new hq(this) {
            final /* synthetic */ fb a;

            {
                this.a = r1;
            }

            public void safeRun() {
                this.a.p();
            }
        });
        if (fe.a().c()) {
            fp.a().b(new hq(this) {
                final /* synthetic */ fb a;

                {
                    this.a = r1;
                }

                public void safeRun() {
                    this.a.r.add(this.a.c());
                    this.a.e();
                }
            });
        }
    }

    public synchronized void a() {
        fz.a().a(this.C);
        if (fe.a().c()) {
            fp.a().b(new hq(this) {
                final /* synthetic */ fb a;

                {
                    this.a = r1;
                }

                public void safeRun() {
                    this.a.b(false);
                }
            });
        }
        hg.a().b("Gender", (a) this);
        hg.a().b("UserId", (a) this);
        hg.a().b("Age", (a) this);
        hg.a().b("LogEvents", (a) this);
    }

    public void a(String str, Object obj) {
        Object obj2 = -1;
        switch (str.hashCode()) {
            case -1752163738:
                if (str.equals("UserId")) {
                    obj2 = 1;
                    break;
                }
                break;
            case -738063011:
                if (str.equals("LogEvents")) {
                    obj2 = null;
                    break;
                }
                break;
            case 65759:
                if (str.equals("Age")) {
                    obj2 = 3;
                    break;
                }
                break;
            case 2129321697:
                if (str.equals("Gender")) {
                    obj2 = 2;
                    break;
                }
                break;
        }
        switch (obj2) {
            case null:
                this.n = ((Boolean) obj).booleanValue();
                gd.a(4, f, "onSettingUpdate, LogEvents = " + this.n);
                return;
            case 1:
                this.o = (String) obj;
                gd.a(4, f, "onSettingUpdate, UserId = " + this.o);
                return;
            case 2:
                this.p = ((Byte) obj).byteValue();
                gd.a(4, f, "onSettingUpdate, Gender = " + this.p);
                return;
            case 3:
                this.q = (Long) obj;
                gd.a(4, f, "onSettingUpdate, Birthdate = " + this.q);
                return;
            default:
                gd.a(6, f, "onSettingUpdate internal error!");
                return;
        }
    }

    public void b() {
        this.l = true;
    }

    private void d(Context context) {
        if (context instanceof Activity) {
            Bundle extras = ((Activity) context).getIntent().getExtras();
            if (extras != null) {
                gd.a(3, f, "Launch Options Bundle is present " + extras.toString());
                for (String str : extras.keySet()) {
                    if (str != null) {
                        Object obj = extras.get(str);
                        this.s.put(str, new ArrayList(Arrays.asList(new String[]{obj != null ? obj.toString() : "null"})));
                        gd.a(3, f, "Launch options Key: " + str + ". Its value: " + r1);
                    }
                }
            }
        }
    }

    @TargetApi(18)
    private void a(boolean z) {
        boolean z2;
        int intExtra;
        int i = -1;
        if (z) {
            this.t.put("boot.time", Long.toString(System.currentTimeMillis() - SystemClock.elapsedRealtime()));
            StatFs statFs = new StatFs(Environment.getRootDirectory().getAbsolutePath());
            StatFs statFs2 = new StatFs(Environment.getExternalStorageDirectory().getAbsolutePath());
            if (VERSION.SDK_INT >= 18) {
                this.t.put("disk.size.total.internal", Long.toString(statFs.getAvailableBlocksLong()));
                this.t.put("disk.size.available.internal", Long.toString(statFs.getAvailableBlocksLong()));
                this.t.put("disk.size.total.external", Long.toString(statFs2.getAvailableBlocksLong()));
                this.t.put("disk.size.available.external", Long.toString(statFs2.getAvailableBlocksLong()));
            } else {
                this.t.put("disk.size.total.internal", Long.toString((long) statFs.getAvailableBlocks()));
                this.t.put("disk.size.available.internal", Long.toString((long) statFs.getAvailableBlocks()));
                this.t.put("disk.size.total.external", Long.toString((long) statFs2.getAvailableBlocks()));
                this.t.put("disk.size.available.external", Long.toString((long) statFs2.getAvailableBlocks()));
            }
            this.t.put("carrier.name", fk.a().c());
            this.t.put("carrier.details", fk.a().d());
        }
        ActivityManager activityManager = (ActivityManager) fp.a().c().getSystemService("activity");
        MemoryInfo memoryInfo = new MemoryInfo();
        activityManager.getMemoryInfo(memoryInfo);
        this.t.put("memory.available" + (z ? ".start" : ".end"), Long.toString(memoryInfo.availMem));
        if (VERSION.SDK_INT >= 16) {
            this.t.put("memory.total" + (z ? ".start" : ".end"), Long.toString(memoryInfo.availMem));
        }
        Intent registerReceiver = fp.a().c().registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
        if (registerReceiver != null) {
            int intExtra2 = registerReceiver.getIntExtra("status", -1);
            z2 = intExtra2 == 2 || intExtra2 == 5;
            int intExtra3 = registerReceiver.getIntExtra("level", -1);
            intExtra = registerReceiver.getIntExtra("scale", -1);
            i = intExtra3;
        } else {
            z2 = false;
            intExtra = -1;
        }
        float f = ((float) i) / ((float) intExtra);
        this.t.put("battery.charging" + (z ? ".start" : ".end"), Boolean.toString(z2));
        this.t.put("battery.remaining" + (z ? ".start" : ".end"), Float.toString(f));
    }

    private synchronized void a(long j) {
        for (ew ewVar : this.v) {
            if (ewVar.a() && !ewVar.b()) {
                ewVar.a(j);
            }
        }
    }

    synchronized ez c() {
        ez ezVar;
        fa faVar = new fa();
        faVar.a(fm.a().e());
        faVar.a(fd.a().c());
        faVar.b(fd.a().e());
        faVar.c(fd.a().g());
        faVar.a(this.t);
        faVar.b(fg.a().c());
        faVar.c(fg.a().d());
        faVar.a(fd.a().h().a());
        faVar.b(hn.j());
        faVar.d(f());
        faVar.a(fh.a().e());
        faVar.c(j());
        faVar.a(this.p);
        faVar.a(this.q);
        faVar.b(i());
        faVar.a(g());
        faVar.a(this.w);
        faVar.b(h());
        faVar.d(this.z);
        try {
            ezVar = new ez(faVar);
        } catch (IOException e) {
            gd.a(5, f, "Error creating analytics session report: " + e);
            ezVar = null;
        }
        if (ezVar == null) {
            gd.e(f, "New session report wasn't created");
        }
        return ezVar;
    }

    public synchronized void d() {
        this.A++;
    }

    public synchronized FlurryEventRecordStatus a(String str, Map<String, String> map, boolean z) {
        FlurryEventRecordStatus flurryEventRecordStatus;
        FlurryEventRecordStatus flurryEventRecordStatus2 = FlurryEventRecordStatus.kFlurryEventRecorded;
        long elapsedRealtime = SystemClock.elapsedRealtime() - fd.a().d();
        String b = hp.b(str);
        if (b.length() == 0) {
            flurryEventRecordStatus = FlurryEventRecordStatus.kFlurryEventFailed;
        } else {
            ev evVar = (ev) this.u.get(b);
            if (evVar != null) {
                evVar.a++;
                gd.e(f, "Event count incremented: " + b);
                flurryEventRecordStatus = FlurryEventRecordStatus.kFlurryEventRecorded;
            } else if (this.u.size() < a) {
                evVar = new ev();
                evVar.a = 1;
                this.u.put(b, evVar);
                gd.e(f, "Event count started: " + b);
                flurryEventRecordStatus = flurryEventRecordStatus2;
            } else {
                gd.e(f, "Too many different events. Event not counted: " + b);
                flurryEventRecordStatus = FlurryEventRecordStatus.kFlurryEventUniqueCountExceeded;
            }
            if (!this.n || this.v.size() >= c || this.x >= d) {
                this.w = false;
            } else {
                Map emptyMap;
                if (map == null) {
                    emptyMap = Collections.emptyMap();
                } else {
                    Map<String, String> map2 = map;
                }
                if (emptyMap.size() > b) {
                    gd.e(f, "MaxEventParams exceeded: " + emptyMap.size());
                    flurryEventRecordStatus = FlurryEventRecordStatus.kFlurryEventParamsCountExceeded;
                } else {
                    ew ewVar = new ew(q(), b, emptyMap, elapsedRealtime, z);
                    if (ewVar.d() + this.x <= d) {
                        this.v.add(ewVar);
                        this.x = ewVar.d() + this.x;
                        flurryEventRecordStatus = FlurryEventRecordStatus.kFlurryEventRecorded;
                    } else {
                        this.x = d;
                        this.w = false;
                        gd.e(f, "Event Log size exceeded. No more event details logged.");
                        flurryEventRecordStatus = FlurryEventRecordStatus.kFlurryEventLogCountExceeded;
                    }
                }
            }
        }
        return flurryEventRecordStatus;
    }

    public synchronized void a(String str, Map<String, String> map) {
        for (ew ewVar : this.v) {
            if (ewVar.a(str)) {
                long elapsedRealtime = SystemClock.elapsedRealtime() - fd.a().d();
                if (map != null && map.size() > 0 && this.x < d) {
                    int d = this.x - ewVar.d();
                    Map hashMap = new HashMap(ewVar.c());
                    ewVar.a((Map) map);
                    if (ewVar.d() + d > d) {
                        ewVar.b(hashMap);
                        this.w = false;
                        this.x = d;
                        gd.e(f, "Event Log size exceeded. No more event details logged.");
                    } else if (ewVar.c().size() > b) {
                        gd.e(f, "MaxEventParams exceeded on endEvent: " + ewVar.c().size());
                        ewVar.b(hashMap);
                    } else {
                        this.x = d + ewVar.d();
                    }
                }
                ewVar.a(elapsedRealtime);
            }
        }
    }

    public synchronized void a(String str, String str2, String str3, Throwable th) {
        Object obj;
        eu euVar;
        int i;
        if (str != null) {
            if ("uncaught".equals(str)) {
                obj = 1;
                this.z++;
                if (this.y.size() < e) {
                    euVar = new eu(r(), Long.valueOf(System.currentTimeMillis()).longValue(), str, str2, str3, th);
                    this.y.add(euVar);
                    gd.e(f, "Error logged: " + euVar.c());
                } else if (obj == null) {
                    for (i = 0; i < this.y.size(); i++) {
                        euVar = (eu) this.y.get(i);
                        if (euVar.c() == null && !"uncaught".equals(euVar.c())) {
                            this.y.set(i, new eu(r(), Long.valueOf(System.currentTimeMillis()).longValue(), str, str2, str3, th));
                            break;
                        }
                    }
                } else {
                    gd.e(f, "Max errors logged. No more errors logged.");
                }
            }
        }
        obj = null;
        this.z++;
        if (this.y.size() < e) {
            euVar = new eu(r(), Long.valueOf(System.currentTimeMillis()).longValue(), str, str2, str3, th);
            this.y.add(euVar);
            gd.e(f, "Error logged: " + euVar.c());
        } else if (obj == null) {
            gd.e(f, "Max errors logged. No more errors logged.");
        } else {
            while (i < this.y.size()) {
                euVar = (eu) this.y.get(i);
                if (euVar.c() == null) {
                }
            }
        }
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    private synchronized void b(boolean r21) {
        /*
        r20 = this;
        monitor-enter(r20);
        if (r21 != 0) goto L_0x000f;
    L_0x0003:
        r0 = r20;
        r2 = r0.r;	 Catch:{ all -> 0x007c }
        r2 = r2.isEmpty();	 Catch:{ all -> 0x007c }
        if (r2 == 0) goto L_0x000f;
    L_0x000d:
        monitor-exit(r20);
        return;
    L_0x000f:
        r2 = 3;
        r3 = f;	 Catch:{ all -> 0x007c }
        r4 = "generating agent report";
        com.flurry.sdk.gd.a(r2, r3, r4);	 Catch:{ all -> 0x007c }
        r19 = 0;
        r3 = new com.flurry.sdk.ex;	 Catch:{ Exception -> 0x007f }
        r2 = com.flurry.sdk.fp.a();	 Catch:{ Exception -> 0x007f }
        r4 = r2.d();	 Catch:{ Exception -> 0x007f }
        r2 = com.flurry.sdk.fm.a();	 Catch:{ Exception -> 0x007f }
        r5 = r2.e();	 Catch:{ Exception -> 0x007f }
        r0 = r20;
        r6 = r0.l;	 Catch:{ Exception -> 0x007f }
        r2 = com.flurry.sdk.fe.a();	 Catch:{ Exception -> 0x007f }
        r7 = r2.e();	 Catch:{ Exception -> 0x007f }
        r0 = r20;
        r8 = r0.m;	 Catch:{ Exception -> 0x007f }
        r2 = com.flurry.sdk.fd.a();	 Catch:{ Exception -> 0x007f }
        r10 = r2.c();	 Catch:{ Exception -> 0x007f }
        r0 = r20;
        r12 = r0.r;	 Catch:{ Exception -> 0x007f }
        r2 = com.flurry.sdk.fe.a();	 Catch:{ Exception -> 0x007f }
        r13 = r2.h();	 Catch:{ Exception -> 0x007f }
        r0 = r20;
        r2 = r0.B;	 Catch:{ Exception -> 0x007f }
        r14 = 0;
        r14 = r2.a(r14);	 Catch:{ Exception -> 0x007f }
        r0 = r20;
        r15 = r0.s;	 Catch:{ Exception -> 0x007f }
        r2 = com.flurry.sdk.fr.a();	 Catch:{ Exception -> 0x007f }
        r16 = r2.c();	 Catch:{ Exception -> 0x007f }
        r17 = java.lang.System.currentTimeMillis();	 Catch:{ Exception -> 0x007f }
        r3.<init>(r4, r5, r6, r7, r8, r10, r12, r13, r14, r15, r16, r17);	 Catch:{ Exception -> 0x007f }
        r2 = r3.a();	 Catch:{ Exception -> 0x007f }
    L_0x006f:
        if (r2 != 0) goto L_0x009b;
    L_0x0071:
        r2 = f;	 Catch:{ all -> 0x007c }
        r3 = "Error generating report";
        com.flurry.sdk.gd.e(r2, r3);	 Catch:{ all -> 0x007c }
    L_0x0078:
        r20.l();	 Catch:{ all -> 0x007c }
        goto L_0x000d;
    L_0x007c:
        r2 = move-exception;
        monitor-exit(r20);
        throw r2;
    L_0x007f:
        r2 = move-exception;
        r3 = f;	 Catch:{ all -> 0x007c }
        r4 = new java.lang.StringBuilder;	 Catch:{ all -> 0x007c }
        r4.<init>();	 Catch:{ all -> 0x007c }
        r5 = "Exception while generating report: ";
        r4 = r4.append(r5);	 Catch:{ all -> 0x007c }
        r2 = r4.append(r2);	 Catch:{ all -> 0x007c }
        r2 = r2.toString();	 Catch:{ all -> 0x007c }
        com.flurry.sdk.gd.e(r3, r2);	 Catch:{ all -> 0x007c }
        r2 = r19;
        goto L_0x006f;
    L_0x009b:
        r3 = 3;
        r4 = f;	 Catch:{ all -> 0x007c }
        r5 = new java.lang.StringBuilder;	 Catch:{ all -> 0x007c }
        r5.<init>();	 Catch:{ all -> 0x007c }
        r6 = "generated report of size ";
        r5 = r5.append(r6);	 Catch:{ all -> 0x007c }
        r6 = r2.length;	 Catch:{ all -> 0x007c }
        r5 = r5.append(r6);	 Catch:{ all -> 0x007c }
        r6 = " with ";
        r5 = r5.append(r6);	 Catch:{ all -> 0x007c }
        r0 = r20;
        r6 = r0.r;	 Catch:{ all -> 0x007c }
        r6 = r6.size();	 Catch:{ all -> 0x007c }
        r5 = r5.append(r6);	 Catch:{ all -> 0x007c }
        r6 = " reports.";
        r5 = r5.append(r6);	 Catch:{ all -> 0x007c }
        r5 = r5.toString();	 Catch:{ all -> 0x007c }
        com.flurry.sdk.gd.a(r3, r4, r5);	 Catch:{ all -> 0x007c }
        r3 = com.flurry.sdk.eq.a();	 Catch:{ all -> 0x007c }
        r3 = r3.c();	 Catch:{ all -> 0x007c }
        r4 = new java.lang.StringBuilder;	 Catch:{ all -> 0x007c }
        r4.<init>();	 Catch:{ all -> 0x007c }
        r5 = "";
        r4 = r4.append(r5);	 Catch:{ all -> 0x007c }
        r5 = com.flurry.sdk.fq.a();	 Catch:{ all -> 0x007c }
        r4 = r4.append(r5);	 Catch:{ all -> 0x007c }
        r4 = r4.toString();	 Catch:{ all -> 0x007c }
        r5 = com.flurry.sdk.fp.a();	 Catch:{ all -> 0x007c }
        r5 = r5.d();	 Catch:{ all -> 0x007c }
        r3.b(r2, r5, r4);	 Catch:{ all -> 0x007c }
        goto L_0x0078;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.flurry.sdk.fb.b(boolean):void");
    }

    public synchronized void e() {
        gd.a(4, f, "Saving persistent agent data.");
        this.k.a(this.r);
    }

    private synchronized void k() {
        gd.a(4, f, "Loading persistent session report data.");
        List list = (List) this.k.a();
        if (list != null) {
            this.r.addAll(list);
        } else if (this.j.exists()) {
            gd.a(4, f, "Legacy persistent agent data found, converting.");
            fc a = et.a(this.j);
            if (a != null) {
                boolean a2 = a.a();
                long b = a.b();
                if (b <= 0) {
                    b = fd.a().c();
                }
                this.l = a2;
                this.m = b;
                p();
                Collection c = a.c();
                if (c != null) {
                    this.r.addAll(c);
                }
            }
            this.j.delete();
            e();
        }
    }

    private void l() {
        this.r.clear();
        this.k.b();
    }

    private String m() {
        return ".flurryagent." + Integer.toString(fp.a().d().hashCode(), 16);
    }

    private String n() {
        return ".yflurryreport." + Long.toString(hp.i(fp.a().d()), 16);
    }

    private void o() {
        SharedPreferences sharedPreferences = fp.a().c().getSharedPreferences("FLURRY_SHARED_PREFERENCES", 0);
        this.l = sharedPreferences.getBoolean("com.flurry.sdk.previous_successful_report", false);
        this.m = sharedPreferences.getLong("com.flurry.sdk.initial_run_time", fd.a().c());
    }

    private void p() {
        Editor edit = fp.a().c().getSharedPreferences("FLURRY_SHARED_PREFERENCES", 0).edit();
        edit.putBoolean("com.flurry.sdk.previous_successful_report", this.l);
        edit.putLong("com.flurry.sdk.initial_run_time", this.m);
        edit.commit();
    }

    private int q() {
        return this.g.incrementAndGet();
    }

    private int r() {
        return this.h.incrementAndGet();
    }

    String f() {
        return this.o == null ? "" : this.o;
    }

    List<ew> g() {
        return this.v;
    }

    List<eu> h() {
        return this.y;
    }

    Map<String, ev> i() {
        return this.u;
    }

    int j() {
        return this.A;
    }
}
