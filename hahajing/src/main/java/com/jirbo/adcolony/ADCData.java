package com.jirbo.adcolony;

import com.inmobi.commons.analytics.db.AnalyticsEvent;
import com.inmobi.commons.internal.ApiStatCollector.ApiEventType;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import org.xmlrpc.android.IXMLRPCSerializer;

public class ADCData {
    static i a = new h();
    static i b = new a();
    static i c = new d();

    static class i {
        i() {
        }

        boolean m() {
            return false;
        }

        boolean f() {
            return false;
        }

        boolean k() {
            return false;
        }

        boolean p() {
            return b_() || c();
        }

        boolean b_() {
            return false;
        }

        boolean c() {
            return false;
        }

        boolean a() {
            return false;
        }

        boolean c_() {
            return false;
        }

        boolean g() {
            return true;
        }

        g n() {
            return null;
        }

        c h() {
            return null;
        }

        String b() {
            return q();
        }

        double d() {
            return 0.0d;
        }

        int e() {
            return 0;
        }

        boolean l() {
            return false;
        }

        public String toString() {
            return q();
        }

        String q() {
            af zVar = new z();
            a(zVar);
            return zVar.toString();
        }

        void a(af afVar) {
        }

        void a(af afVar, String str) {
            if (str != null) {
                afVar.b('\"');
                int length = str.length();
                for (int i = 0; i < length; i++) {
                    char charAt = str.charAt(i);
                    switch (charAt) {
                        case '\b':
                            afVar.a("\\b");
                            break;
                        case '\t':
                            afVar.a("\\t");
                            break;
                        case '\n':
                            afVar.a("\\n");
                            break;
                        case '\f':
                            afVar.a("\\f");
                            break;
                        case '\r':
                            afVar.a("\\r");
                            break;
                        case '\"':
                            afVar.a("\\\"");
                            break;
                        case ApiEventType.API_MRAID_PAUSE_VIDEO /*47*/:
                            afVar.a("\\/");
                            break;
                        case '\\':
                            afVar.a("\\\\");
                            break;
                        default:
                            if (charAt >= ' ' && charAt <= '~') {
                                afVar.b(charAt);
                                break;
                            }
                            afVar.a("\\u");
                            int i2 = charAt;
                            for (int i3 = 0; i3 < 4; i3++) {
                                int i4 = (i2 >> 12) & 15;
                                i2 <<= 4;
                                if (i4 <= 9) {
                                    afVar.a((long) i4);
                                } else {
                                    afVar.b((char) ((i4 - 10) + 97));
                                }
                            }
                            break;
                    }
                }
                afVar.b('\"');
            }
        }
    }

    static class a extends i {
        a() {
        }

        boolean a() {
            return true;
        }

        String b() {
            return "false";
        }

        void a(af afVar) {
            afVar.a("false");
        }
    }

    static class b extends i {
        int a;

        b(int i) {
            this.a = i;
        }

        boolean c() {
            return true;
        }

        double d() {
            return (double) this.a;
        }

        int e() {
            return this.a;
        }

        void a(af afVar) {
            afVar.a((long) this.a);
        }
    }

    static class c extends i {
        ArrayList<i> a = new ArrayList();

        c() {
        }

        boolean f() {
            return true;
        }

        boolean g() {
            return this.a.size() == 0 || (this.a.size() == 1 && ((i) this.a.get(0)).g());
        }

        c h() {
            return this;
        }

        void a(af afVar) {
            int size = this.a.size();
            if (size == 0) {
                afVar.a("[]");
            } else if (size == 1 && ((i) this.a.get(0)).g()) {
                afVar.a("[");
                ((i) this.a.get(0)).a(afVar);
                afVar.a("]");
            } else {
                afVar.b("[");
                afVar.i += 2;
                int i = 0;
                int i2 = 1;
                while (i < size) {
                    int i3;
                    if (i2 != 0) {
                        i3 = 0;
                    } else {
                        afVar.c(',');
                        i3 = i2;
                    }
                    ((i) this.a.get(i)).a(afVar);
                    i++;
                    i2 = i3;
                }
                afVar.d();
                afVar.i -= 2;
                afVar.a("]");
            }
        }

        int i() {
            return this.a.size();
        }

        void j() {
            this.a.clear();
        }

        c a(i iVar) {
            this.a.add(iVar);
            return this;
        }

        c a(String str) {
            a(new f(str));
            return this;
        }

        c a(double d) {
            a(new e(d));
            return this;
        }

        c a(int i) {
            a(new b(i));
            return this;
        }

        c a(boolean z) {
            a(z ? ADCData.a : ADCData.b);
            return this;
        }

        c a(c cVar) {
            for (int i = 0; i < cVar.i(); i++) {
                a((i) cVar.a.get(i));
            }
            return this;
        }

        g a(int i, g gVar) {
            i iVar = (i) this.a.get(i);
            if (iVar == null || !iVar.m()) {
                return gVar;
            }
            return iVar.n();
        }

        c a(int i, c cVar) {
            i iVar = (i) this.a.get(i);
            if (iVar == null || !iVar.f()) {
                return cVar;
            }
            return iVar.h();
        }

        String a(int i, String str) {
            i iVar = (i) this.a.get(i);
            if (iVar == null || !iVar.k()) {
                return str;
            }
            return iVar.b();
        }

        double a(int i, double d) {
            i iVar = (i) this.a.get(i);
            if (iVar == null || !iVar.p()) {
                return d;
            }
            return iVar.d();
        }

        int a(int i, int i2) {
            i iVar = (i) this.a.get(i);
            if (iVar == null || !iVar.p()) {
                return i2;
            }
            return iVar.e();
        }

        boolean a(int i, boolean z) {
            i iVar = (i) this.a.get(i);
            if (iVar == null) {
                return z;
            }
            if (iVar.a() || iVar.k()) {
                return iVar.l();
            }
            return z;
        }

        g b(int i) {
            g a = a(i, null);
            return a != null ? a : new g();
        }

        c c(int i) {
            c a = a(i, null);
            return a != null ? a : new c();
        }

        String d(int i) {
            return a(i, "");
        }

        double e(int i) {
            return a(i, 0.0d);
        }

        int f(int i) {
            return a(i, 0);
        }

        boolean g(int i) {
            return a(i, false);
        }

        i a_() {
            return (i) this.a.remove(this.a.size() - 1);
        }
    }

    static class d extends i {
        d() {
        }

        boolean c_() {
            return true;
        }

        String b() {
            return "null";
        }

        void a(af afVar) {
            afVar.a("null");
        }
    }

    static class e extends i {
        double a;

        e(double d) {
            this.a = d;
        }

        boolean b_() {
            return true;
        }

        double d() {
            return this.a;
        }

        int e() {
            return (int) this.a;
        }

        void a(af afVar) {
            afVar.a(this.a);
        }
    }

    static class f extends i implements Serializable {
        String a;

        f(String str) {
            this.a = str;
        }

        boolean k() {
            return true;
        }

        String b() {
            return this.a;
        }

        double d() {
            try {
                return Double.parseDouble(this.a);
            } catch (NumberFormatException e) {
                return 0.0d;
            }
        }

        int e() {
            return (int) d();
        }

        boolean l() {
            String toLowerCase = this.a.toLowerCase();
            if (toLowerCase.equals("true") || toLowerCase.equals("yes")) {
                return true;
            }
            return false;
        }

        void a(af afVar) {
            a(afVar, this.a);
        }
    }

    static class g extends i implements Serializable {
        HashMap<String, i> a = new HashMap();
        ArrayList<String> b = new ArrayList();

        g() {
        }

        boolean m() {
            return true;
        }

        boolean g() {
            return this.a.size() < 0 || (this.a.size() == 1 && ((i) this.a.get(this.b.get(0))).g());
        }

        g n() {
            return this;
        }

        void a(af afVar) {
            int size = this.b.size();
            if (size == 0) {
                afVar.a("{}");
            } else if (size == 1 && ((i) this.a.get(this.b.get(0))).g()) {
                afVar.a("{");
                r0 = (String) this.b.get(0);
                r1 = (i) this.a.get(r0);
                a(afVar, r0);
                afVar.b(':');
                r1.a(afVar);
                afVar.a("}");
            } else {
                afVar.b("{");
                afVar.i += 2;
                int i = 0;
                int i2 = 1;
                while (i < size) {
                    int i3;
                    if (i2 != 0) {
                        i3 = 0;
                    } else {
                        afVar.c(',');
                        i3 = i2;
                    }
                    r0 = (String) this.b.get(i);
                    r1 = (i) this.a.get(r0);
                    a(afVar, r0);
                    afVar.b(':');
                    if (!r1.g()) {
                        afVar.d();
                    }
                    r1.a(afVar);
                    i++;
                    i2 = i3;
                }
                afVar.d();
                afVar.i -= 2;
                afVar.a("}");
            }
        }

        int o() {
            return this.b.size();
        }

        String a(int i) {
            return (String) this.b.get(i);
        }

        boolean a(String str) {
            return this.a.containsKey(str);
        }

        g a(String str, g gVar) {
            i iVar = (i) this.a.get(str);
            if (iVar == null || !iVar.m()) {
                return gVar;
            }
            return iVar.n();
        }

        c a(String str, c cVar) {
            i iVar = (i) this.a.get(str);
            if (iVar == null || !iVar.f()) {
                return cVar;
            }
            return iVar.h();
        }

        ArrayList<String> a(String str, ArrayList<String> arrayList) {
            c c = c(str);
            if (c != null) {
                arrayList = new ArrayList();
                for (int i = 0; i < c.i(); i++) {
                    String d = c.d(i);
                    if (d != null) {
                        arrayList.add(d);
                    }
                }
            }
            return arrayList;
        }

        String a(String str, String str2) {
            i iVar = (i) this.a.get(str);
            if (iVar == null || !iVar.k()) {
                return str2;
            }
            return iVar.b();
        }

        double a(String str, double d) {
            i iVar = (i) this.a.get(str);
            if (iVar == null || !iVar.p()) {
                return d;
            }
            return iVar.d();
        }

        int a(String str, int i) {
            i iVar = (i) this.a.get(str);
            if (iVar == null || !iVar.p()) {
                return i;
            }
            return iVar.e();
        }

        boolean a(String str, boolean z) {
            i iVar = (i) this.a.get(str);
            if (iVar == null) {
                return z;
            }
            if (iVar.a() || iVar.k()) {
                return iVar.l();
            }
            return z;
        }

        g b(String str) {
            g a = a(str, null);
            return a != null ? a : new g();
        }

        c c(String str) {
            c a = a(str, null);
            return a != null ? a : new c();
        }

        ArrayList<String> d(String str) {
            ArrayList<String> a = a(str, null);
            if (a == null) {
                return new ArrayList();
            }
            return a;
        }

        String e(String str) {
            return a(str, "");
        }

        double f(String str) {
            return a(str, 0.0d);
        }

        int g(String str) {
            return a(str, 0);
        }

        boolean h(String str) {
            return a(str, false);
        }

        void a(String str, i iVar) {
            if (!this.a.containsKey(str)) {
                this.b.add(str);
            }
            this.a.put(str, iVar);
        }

        void b(String str, String str2) {
            a(str, new f(str2));
        }

        void b(String str, double d) {
            a(str, new e(d));
        }

        void b(String str, int i) {
            a(str, new b(i));
        }

        void b(String str, boolean z) {
            a(str, z ? ADCData.a : ADCData.b);
        }
    }

    static class h extends i {
        h() {
        }

        boolean a() {
            return true;
        }

        String b() {
            return "true";
        }

        double d() {
            return 1.0d;
        }

        int e() {
            return 1;
        }

        boolean l() {
            return true;
        }

        void a(af afVar) {
            afVar.a("true");
        }
    }

    public static void main(String[] args) {
        System.out.println("==== ADCData Test ====");
        g gVar = new g();
        gVar.b("one", 1);
        gVar.b(AnalyticsEvent.TYPE_TAG_TRANSACTION, 3.14d);
        gVar.b(IXMLRPCSerializer.TAG_NAME, "\"Abe Pralle\"");
        gVar.a("list", new c());
        gVar.a("subtable", new g());
        gVar.b("subtable").b("five", 5);
        System.out.println("LIST:" + gVar.c("list"));
        gVar.c("list").a(3);
        System.out.println(gVar);
        System.out.println(gVar.g("one"));
        System.out.println(gVar.f("one"));
        System.out.println(gVar.g(AnalyticsEvent.TYPE_TAG_TRANSACTION));
        System.out.println(gVar.f(AnalyticsEvent.TYPE_TAG_TRANSACTION));
        System.out.println(gVar.e(IXMLRPCSerializer.TAG_NAME));
        System.out.println(gVar.f(IXMLRPCSerializer.TAG_NAME));
        System.out.println(gVar.g(IXMLRPCSerializer.TAG_NAME));
        System.out.println(gVar.c("list"));
        System.out.println(gVar.c("list2"));
        System.out.println(gVar.c("subtable"));
        System.out.println(gVar.b("subtable"));
        System.out.println(gVar.b("subtable2"));
        System.out.println(gVar.b("list"));
    }
}
