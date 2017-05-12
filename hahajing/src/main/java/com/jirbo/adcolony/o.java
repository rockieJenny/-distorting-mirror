package com.jirbo.adcolony;

import com.jirbo.adcolony.ADCDownload.Listener;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

class o implements Listener {
    d a;
    ArrayList<a> b = new ArrayList();
    HashMap<String, a> c = new HashMap();
    int d = 1;
    b e = new b(2.0d);
    int f;
    ArrayList<String> g = new ArrayList();
    boolean h;
    boolean i;
    double j;

    static class a {
        String a;
        String b;
        String c;
        boolean d;
        boolean e;
        int f;
        int g;
        double h;

        a() {
        }
    }

    o(d dVar) {
        this.a = dVar;
    }

    void a() {
        b();
        this.h = true;
    }

    void b() {
        l.a.b((Object) "Loading media info");
        g b = k.b(new f("media_info.txt"));
        if (b == null) {
            b = new g();
            l.a.b((Object) "No saved media info exists.");
        } else {
            l.a.b((Object) "Loaded media info");
        }
        this.d = b.g("next_file_number");
        if (this.d <= 0) {
            this.d = 1;
        }
        c c = b.c("assets");
        if (c != null) {
            this.b.clear();
            for (int i = 0; i < c.i(); i++) {
                g b2 = c.b(i);
                a aVar = new a();
                aVar.a = b2.e("url");
                aVar.b = b2.e("filepath");
                aVar.c = b2.e("last_modified");
                aVar.f = b2.g("file_number");
                aVar.g = b2.g("size");
                aVar.e = b2.h("ready");
                aVar.h = b2.f("last_accessed");
                if (aVar.f > this.d) {
                    this.d = aVar.f + 1;
                }
                this.b.add(aVar);
            }
        }
        c();
    }

    void c() {
        int i;
        HashMap hashMap = new HashMap();
        String str = this.a.f.c;
        String[] list = new File(str).list();
        String[] strArr;
        if (list == null) {
            strArr = new String[0];
        } else {
            strArr = list;
        }
        for (String str2 : r1) {
            String str22 = str + str22;
            hashMap.put(str22, str22);
        }
        HashMap hashMap2 = new HashMap();
        this.j = 0.0d;
        ArrayList arrayList = new ArrayList();
        for (i = 0; i < this.b.size(); i++) {
            a aVar = (a) this.b.get(i);
            if (!aVar.d && aVar.e) {
                String str3 = aVar.b;
                if (hashMap.containsKey(str3) && new File(str3).length() == ((long) aVar.g)) {
                    this.j += (double) aVar.g;
                    arrayList.add(aVar);
                    hashMap2.put(str3, str3);
                }
            }
        }
        this.b = arrayList;
        for (String str4 : r1) {
            Object obj = str + str4;
            if (!hashMap2.containsKey(obj)) {
                l.b.a("Deleting unused media ").b(obj);
                new File(obj).delete();
            }
        }
        this.c.clear();
        for (int i2 = 0; i2 < this.b.size(); i2++) {
            aVar = (a) this.b.get(i2);
            this.c.put(aVar.a, aVar);
        }
        double d = this.a.b.j.g;
        if (d > 0.0d) {
            l.b.a("Media pool at ").a(this.j / 1048576.0d).a("/").a(d / 1048576.0d).b((Object) " MB");
        }
    }

    void d() {
        l.a.b((Object) "Saving media info");
        i cVar = new c();
        for (int i = 0; i < this.b.size(); i++) {
            a aVar = (a) this.b.get(i);
            if (aVar.e && !aVar.d) {
                i gVar = new g();
                gVar.b("url", aVar.a);
                gVar.b("filepath", aVar.b);
                gVar.b("last_modified", aVar.c);
                gVar.b("file_number", aVar.f);
                gVar.b("size", aVar.g);
                gVar.b("ready", aVar.e);
                gVar.b("last_accessed", aVar.h);
                cVar.a(gVar);
            }
        }
        g gVar2 = new g();
        gVar2.b("next_file_number", this.d);
        gVar2.a("assets", cVar);
        k.a(new f("media_info.txt"), gVar2);
        this.i = false;
    }

    boolean a(String str) {
        if (str == null || str.equals("")) {
            return false;
        }
        a aVar = (a) this.c.get(str);
        if (aVar == null) {
            this.a.b.j.a();
            return false;
        } else if (!aVar.e) {
            if (!aVar.d) {
                this.a.b.j.a();
            }
            return false;
        } else if (aVar.d) {
            return false;
        } else {
            aVar.h = ab.c();
            return true;
        }
    }

    String b(String str) {
        a aVar = (a) this.c.get(str);
        if (aVar == null || aVar.b == null) {
            return "(file not found)";
        }
        aVar.h = ab.c();
        this.i = true;
        this.e.a(2.0d);
        return aVar.b;
    }

    void a(String str, String str2) {
        if (str != null && !str.equals("")) {
            if (str2 == null) {
                str2 = "";
            }
            a aVar = (a) this.c.get(str);
            if (aVar != null) {
                aVar.h = ab.c();
                if (aVar.c.equals(str2) && (aVar.e || aVar.d)) {
                    return;
                }
            }
            aVar = new a();
            aVar.a = str;
            this.b.add(aVar);
            aVar.h = ab.c();
            this.c.put(str, aVar);
            if (aVar.f == 0) {
                int h = h();
                String str3 = this.a.f.c + a(str, h);
                aVar.f = h;
                aVar.b = str3;
            }
            aVar.c = str2;
            aVar.d = true;
            aVar.e = false;
            l.a.a("Adding ").a(str).b((Object) " to pending downloads.");
            this.g.add(str);
            this.i = true;
            this.e.a(2.0d);
            a.r = true;
        }
    }

    void e() {
        f();
        if (this.i && this.e.a()) {
            g();
            d();
        }
    }

    void f() {
        if (this.a.b.j.j.equals("wifi") && !q.a()) {
            l.a.b((Object) "Skipping asset download due to CACHE_FILTER_WIFI");
        } else if (!this.a.b.j.j.equals("cell") || q.b()) {
            while (this.f < 3 && this.g.size() > 0) {
                String str = (String) this.g.remove(0);
                a aVar = (a) this.c.get(str);
                if (aVar != null && (str == null || str.equals(""))) {
                    l.d.b((Object) "[ADC ERROR] - NULL URL");
                    new RuntimeException().printStackTrace();
                }
                if (!(aVar == null || str == null || str.equals(""))) {
                    a.r = true;
                    this.f++;
                    new ADCDownload(this.a, str, this, aVar.b).a(aVar).b();
                }
            }
        } else {
            l.a.b((Object) "Skipping asset download due to CACHE_FILTER_CELL.");
        }
    }

    public void on_download_finished(ADCDownload download) {
        a aVar = (a) download.e;
        this.f--;
        this.i = true;
        this.e.a(2.0d);
        aVar.e = download.i;
        aVar.d = false;
        if (download.i) {
            aVar.g = download.m;
            this.j += (double) aVar.g;
            l.a.a("Downloaded ").b(aVar.a);
        }
        a.h();
        f();
    }

    void g() {
        double d = this.a.b.j.g;
        if (d != 0.0d) {
            while (this.j > this.a.b.j.g) {
                int i = 0;
                a aVar = null;
                while (i < this.b.size()) {
                    a aVar2 = (a) this.b.get(i);
                    if (!aVar2.e || (aVar != null && aVar2.h >= aVar.h)) {
                        aVar2 = aVar;
                    }
                    i++;
                    aVar = aVar2;
                }
                if (aVar == null) {
                    return;
                }
                if (aVar.b != null) {
                    l.b.a("Deleting ").b(aVar.b);
                    aVar.e = false;
                    new File(aVar.b).delete();
                    aVar.b = null;
                    this.j -= (double) aVar.g;
                    l.b.a("Media pool now at ").a(this.j / 1048576.0d).a("/").a(d / 1048576.0d).b((Object) " MB");
                    this.i = true;
                    this.e.a(2.0d);
                } else {
                    return;
                }
            }
        }
    }

    int h() {
        this.i = true;
        this.e.a(2.0d);
        int i = this.d;
        this.d = i + 1;
        return i;
    }

    String a(String str, int i) {
        int lastIndexOf = str.lastIndexOf(46);
        if (lastIndexOf == -1) {
            return i + "";
        }
        String substring = str.substring(lastIndexOf);
        if (substring.contains("/")) {
            substring = ".0";
        }
        return i + substring;
    }
}
