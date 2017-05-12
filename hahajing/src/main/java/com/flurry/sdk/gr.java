package com.flurry.sdk;

import com.flurry.sdk.gs.a;
import java.io.Closeable;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

public class gr {
    public static final Integer a = Integer.valueOf(50);
    private static final String d = gr.class.getSimpleName();
    String b;
    LinkedHashMap<String, List<String>> c;

    public gr(String str) {
        a(str);
    }

    void a(String str) {
        this.b = str + "Main";
        e(this.b);
    }

    private void e(String str) {
        this.c = new LinkedHashMap();
        List<String> arrayList = new ArrayList();
        if (j(str)) {
            Collection k = k(str);
            if (k != null && k.size() > 0) {
                arrayList.addAll(k);
                for (String f : arrayList) {
                    f(f);
                }
            }
            i(str);
        } else {
            List<gs> list = (List) new fw(fp.a().c().getFileStreamPath(g(this.b)), str, 1, new ha<List<gs>>(this) {
                final /* synthetic */ gr a;

                {
                    this.a = r1;
                }

                public gx<List<gs>> a(int i) {
                    return new gw(new a());
                }
            }).a();
            if (list == null) {
                gd.c(d, "New main file also not found. returning..");
                return;
            }
            for (gs a : list) {
                arrayList.add(a.a());
            }
        }
        for (String f2 : arrayList) {
            List h = h(f2);
            if (h != null) {
                this.c.put(f2, h);
            }
        }
    }

    private void f(String str) {
        List<String> k = k(str);
        if (k == null) {
            gd.c(d, "No old file to replace");
            return;
        }
        for (String str2 : k) {
            byte[] m = m(str2);
            if (m == null) {
                gd.a(6, d, "File does not exist");
            } else {
                a(str2, m);
                l(str2);
            }
        }
        if (k != null) {
            a(str, k, ".YFlurrySenderIndex.info.");
            i(str);
        }
    }

    private String g(String str) {
        return ".YFlurrySenderIndex.info." + str;
    }

    private synchronized void c() {
        List linkedList = new LinkedList(this.c.keySet());
        b();
        if (!linkedList.isEmpty()) {
            a(this.b, linkedList, this.b);
        }
    }

    public synchronized void a(gq gqVar, String str) {
        Object obj = null;
        synchronized (this) {
            List linkedList;
            gd.a(4, d, "addBlockInfo" + str);
            String a = gqVar.a();
            List list = (List) this.c.get(str);
            if (list == null) {
                gd.a(4, d, "New Data Key");
                linkedList = new LinkedList();
                obj = 1;
            } else {
                linkedList = list;
            }
            linkedList.add(a);
            if (linkedList.size() > a.intValue()) {
                b((String) linkedList.get(0));
                linkedList.remove(0);
            }
            this.c.put(str, linkedList);
            a(str, linkedList, ".YFlurrySenderIndex.info.");
            if (obj != null) {
                c();
            }
        }
    }

    boolean b(String str) {
        return new fw(fp.a().c().getFileStreamPath(gq.a(str)), ".yflurrydatasenderblock.", 1, new ha<gq>(this) {
            final /* synthetic */ gr a;

            {
                this.a = r1;
            }

            public gx<gq> a(int i) {
                return new gq.a();
            }
        }).b();
    }

    public boolean a(String str, String str2) {
        List list = (List) this.c.get(str2);
        boolean z = false;
        if (list != null) {
            b(str);
            z = list.remove(str);
        }
        if (list == null || list.isEmpty()) {
            d(str2);
        } else {
            this.c.put(str2, list);
            a(str2, list, ".YFlurrySenderIndex.info.");
        }
        return z;
    }

    public List<String> a() {
        return new ArrayList(this.c.keySet());
    }

    public List<String> c(String str) {
        return (List) this.c.get(str);
    }

    public synchronized boolean d(String str) {
        boolean b;
        hp.b();
        fw fwVar = new fw(fp.a().c().getFileStreamPath(g(str)), ".YFlurrySenderIndex.info.", 1, new ha<List<gs>>(this) {
            final /* synthetic */ gr a;

            {
                this.a = r1;
            }

            public gx<List<gs>> a(int i) {
                return new gw(new a());
            }
        });
        List<String> c = c(str);
        if (c != null) {
            gd.a(4, d, "discardOutdatedBlocksForDataKey: notSentBlocks = " + c.size());
            for (String str2 : c) {
                b(str2);
                gd.a(4, d, "discardOutdatedBlocksForDataKey: removed block = " + str2);
            }
        }
        this.c.remove(str);
        b = fwVar.b();
        c();
        return b;
    }

    void b() {
        new fw(fp.a().c().getFileStreamPath(g(this.b)), ".YFlurrySenderIndex.info.", 1, new ha<List<gs>>(this) {
            final /* synthetic */ gr a;

            {
                this.a = r1;
            }

            public gx<List<gs>> a(int i) {
                return new gw(new a());
            }
        }).b();
    }

    private synchronized List<String> h(String str) {
        List<String> arrayList;
        hp.b();
        gd.a(5, d, "Reading Index File for " + str + " file name:" + fp.a().c().getFileStreamPath(g(str)));
        List<gs> list = (List) new fw(fp.a().c().getFileStreamPath(g(str)), ".YFlurrySenderIndex.info.", 1, new ha<List<gs>>(this) {
            final /* synthetic */ gr a;

            {
                this.a = r1;
            }

            public gx<List<gs>> a(int i) {
                return new gw(new a());
            }
        }).a();
        arrayList = new ArrayList();
        for (gs a : list) {
            arrayList.add(a.a());
        }
        return arrayList;
    }

    private synchronized void a(String str, byte[] bArr) {
        hp.b();
        gd.a(5, d, "Saving Block File for " + str + " file name:" + fp.a().c().getFileStreamPath(gq.a(str)));
        new fw(fp.a().c().getFileStreamPath(gq.a(str)), ".yflurrydatasenderblock.", 1, new ha<gq>(this) {
            final /* synthetic */ gr a;

            {
                this.a = r1;
            }

            public gx<gq> a(int i) {
                return new gq.a();
            }
        }).a(new gq(bArr));
    }

    private void i(String str) {
        hp.b();
        gd.a(5, d, "Deleting Index File for " + str + " file name:" + fp.a().c().getFileStreamPath(".FlurrySenderIndex.info." + str));
        File fileStreamPath = fp.a().c().getFileStreamPath(".FlurrySenderIndex.info." + str);
        if (fileStreamPath.exists()) {
            gd.a(5, d, "Found file for " + str + ". Deleted - " + fileStreamPath.delete());
        }
    }

    private synchronized void a(String str, List<String> list, String str2) {
        hp.b();
        gd.a(5, d, "Saving Index File for " + str + " file name:" + fp.a().c().getFileStreamPath(g(str)));
        fw fwVar = new fw(fp.a().c().getFileStreamPath(g(str)), str2, 1, new ha<List<gs>>(this) {
            final /* synthetic */ gr a;

            {
                this.a = r1;
            }

            public gx<List<gs>> a(int i) {
                return new gw(new a());
            }
        });
        List arrayList = new ArrayList();
        for (String gsVar : list) {
            arrayList.add(new gs(gsVar));
        }
        fwVar.a(arrayList);
    }

    private synchronized boolean j(String str) {
        File fileStreamPath;
        fileStreamPath = fp.a().c().getFileStreamPath(".FlurrySenderIndex.info." + str);
        gd.a(5, d, "isOldIndexFilePresent: for " + str + fileStreamPath.exists());
        return fileStreamPath.exists();
    }

    private synchronized List<String> k(String str) {
        List<String> arrayList;
        Throwable th;
        Throwable th2;
        Throwable th3;
        List<String> list = null;
        synchronized (this) {
            hp.b();
            gd.a(5, d, "Reading Index File for " + str + " file name:" + fp.a().c().getFileStreamPath(".FlurrySenderIndex.info." + str));
            File fileStreamPath = fp.a().c().getFileStreamPath(".FlurrySenderIndex.info." + str);
            if (fileStreamPath.exists()) {
                gd.a(5, d, "Reading Index File for " + str + " Found file.");
                Closeable dataInputStream;
                try {
                    dataInputStream = new DataInputStream(new FileInputStream(fileStreamPath));
                    try {
                        int readUnsignedShort = dataInputStream.readUnsignedShort();
                        if (readUnsignedShort == 0) {
                            hp.a(dataInputStream);
                        } else {
                            arrayList = new ArrayList(readUnsignedShort);
                            int i = 0;
                            while (i < readUnsignedShort) {
                                try {
                                    int readUnsignedShort2 = dataInputStream.readUnsignedShort();
                                    gd.a(4, d, "read iter " + i + " dataLength = " + readUnsignedShort2);
                                    byte[] bArr = new byte[readUnsignedShort2];
                                    dataInputStream.readFully(bArr);
                                    arrayList.add(new String(bArr));
                                    i++;
                                } catch (Throwable th4) {
                                    th = th4;
                                }
                            }
                            if (dataInputStream.readUnsignedShort() == 0) {
                            }
                            hp.a(dataInputStream);
                            list = arrayList;
                        }
                    } catch (Throwable th32) {
                        th2 = th32;
                        arrayList = null;
                        th = th2;
                        try {
                            gd.a(6, d, "Error when loading persistent file", th);
                            hp.a(dataInputStream);
                            list = arrayList;
                            return list;
                        } catch (Throwable th5) {
                            th32 = th5;
                            hp.a(dataInputStream);
                            throw th32;
                        }
                    }
                } catch (Throwable th6) {
                    th32 = th6;
                    dataInputStream = null;
                    hp.a(dataInputStream);
                    throw th32;
                }
            }
            gd.a(5, d, "Agent cache file doesn't exist.");
            arrayList = null;
            list = arrayList;
        }
        return list;
    }

    private void l(String str) {
        hp.b();
        gd.a(5, d, "Deleting  block File for " + str + " file name:" + fp.a().c().getFileStreamPath(".flurrydatasenderblock." + str));
        File fileStreamPath = fp.a().c().getFileStreamPath(".flurrydatasenderblock." + str);
        if (fileStreamPath.exists()) {
            gd.a(5, d, "Found file for " + str + ". Deleted - " + fileStreamPath.delete());
        }
    }

    private byte[] m(String str) {
        Throwable th;
        Throwable th2;
        byte[] bArr = null;
        hp.b();
        gd.a(5, d, "Reading block File for " + str + " file name:" + fp.a().c().getFileStreamPath(".flurrydatasenderblock." + str));
        File fileStreamPath = fp.a().c().getFileStreamPath(".flurrydatasenderblock." + str);
        if (fileStreamPath.exists()) {
            gd.a(5, d, "Reading Index File for " + str + " Found file.");
            Closeable dataInputStream;
            try {
                dataInputStream = new DataInputStream(new FileInputStream(fileStreamPath));
                try {
                    int readUnsignedShort = dataInputStream.readUnsignedShort();
                    if (readUnsignedShort == 0) {
                        hp.a(dataInputStream);
                    } else {
                        bArr = new byte[readUnsignedShort];
                        dataInputStream.readFully(bArr);
                        if (dataInputStream.readUnsignedShort() == 0) {
                            hp.a(dataInputStream);
                        } else {
                            hp.a(dataInputStream);
                        }
                    }
                } catch (Throwable th3) {
                    th = th3;
                    try {
                        gd.a(6, d, "Error when loading persistent file", th);
                        hp.a(dataInputStream);
                        return bArr;
                    } catch (Throwable th4) {
                        th2 = th4;
                        hp.a(dataInputStream);
                        throw th2;
                    }
                }
            } catch (Throwable th5) {
                dataInputStream = null;
                th2 = th5;
                hp.a(dataInputStream);
                throw th2;
            }
        }
        gd.a(4, d, "Agent cache file doesn't exist.");
        return bArr;
    }
}
