package com.flurry.sdk;

import android.net.Uri;
import android.text.TextUtils;
import com.flurry.android.impl.ads.protocol.v13.AdFrame;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.net.URLConnection;
import java.util.Collections;
import java.util.List;

public class aa {
    private static final String b = aa.class.getSimpleName();
    z a;
    private long c;
    private long d;
    private a e = a.NONE;

    enum a {
        NONE,
        INIT,
        ACTIVE,
        PAUSED
    }

    public synchronized void a(long j, long j2) {
        if (!a()) {
            gd.a(3, b, "Precaching: Initializing AssetCacheManager.");
            this.c = j;
            this.d = j2;
            j();
            this.e = a.INIT;
            a(com.flurry.sdk.ab.a.INIT);
        }
    }

    public boolean a() {
        return !a.NONE.equals(this.e);
    }

    public boolean b() {
        return a.ACTIVE.equals(this.e) || a.PAUSED.equals(this.e);
    }

    public boolean c() {
        return a.PAUSED.equals(this.e);
    }

    public List<ae> d() {
        if (a()) {
            return this.a.d();
        }
        return null;
    }

    public void a(ae aeVar) {
        if (a() && aeVar != null && !ah.QUEUED.equals(aeVar.b()) && !ah.IN_PROGRESS.equals(aeVar.b())) {
            this.a.a(aeVar);
        }
    }

    public synchronized void e() {
        if (a()) {
            if (!b()) {
                gd.a(3, b, "Precaching: Starting AssetCacheManager.");
                this.a = new z("fileStreamCacheDownloader", this.c, this.d, false);
                this.a.e();
                this.e = a.ACTIVE;
                a(com.flurry.sdk.ab.a.START);
            }
        }
    }

    public synchronized void f() {
        if (b()) {
            gd.a(3, b, "Precaching: Stopping AssetCacheManager.");
            this.a.f();
            this.e = a.INIT;
            a(com.flurry.sdk.ab.a.STOP);
        }
    }

    public synchronized void g() {
        if (b()) {
            if (c()) {
                gd.a(3, b, "Precaching: Resuming AssetCacheManager.");
                this.a.g();
                this.e = a.ACTIVE;
                a(com.flurry.sdk.ab.a.RESUME);
            }
        }
    }

    public void a(List<ap> list) {
        if (b() && list != null) {
            int size;
            for (size = list.size() - 1; size >= 0; size--) {
                d((ap) list.get(size));
            }
            size = 0;
            for (ap b : list) {
                int i;
                if (b(b) > 0) {
                    i = 1;
                } else {
                    i = 0;
                }
                i += size;
                if (i < 2) {
                    size = i;
                } else {
                    return;
                }
            }
        }
    }

    public af a(ap apVar) {
        if (!b()) {
            return af.ERROR;
        }
        if (apVar == null) {
            return af.ERROR;
        }
        if (apVar.a() == null) {
            return af.COMPLETE;
        }
        af afVar = af.NOT_STARTED;
        List list = apVar.a().adFrames;
        Object obj = null;
        af afVar2 = afVar;
        int i = 0;
        af afVar3 = afVar2;
        while (i < list.size()) {
            Object obj2 = obj;
            af afVar4 = afVar3;
            for (String b : apVar.f(i)) {
                afVar4 = a(afVar4, this.a.b(b));
                obj2 = 1;
            }
            i++;
            afVar3 = afVar4;
            obj = obj2;
        }
        return obj == null ? af.COMPLETE : afVar3;
    }

    private af a(af afVar, ah ahVar) {
        if (afVar == null) {
            return af.NOT_STARTED;
        }
        if (ahVar == null) {
            return afVar;
        }
        if (ah.ERROR.equals(ahVar)) {
            return af.ERROR;
        }
        if (ah.EVICTED.equals(ahVar)) {
            if (afVar.equals(af.ERROR)) {
                return afVar;
            }
            return af.EVICTED;
        } else if (ah.NONE.equals(ahVar) || ah.CANCELLED.equals(ahVar)) {
            if (afVar.equals(af.ERROR) || afVar.equals(af.EVICTED)) {
                return afVar;
            }
            return af.INCOMPLETE;
        } else if (ah.QUEUED.equals(ahVar) || ah.IN_PROGRESS.equals(ahVar)) {
            if (af.NOT_STARTED.equals(afVar) || af.COMPLETE.equals(afVar)) {
                return af.IN_PROGRESS;
            }
            return afVar;
        } else if (ah.COMPLETE.equals(ahVar) && af.NOT_STARTED.equals(afVar)) {
            return af.COMPLETE;
        } else {
            return afVar;
        }
    }

    public boolean a(r rVar, ap apVar) {
        if (!b() || rVar == null || apVar == null) {
            return false;
        }
        gd.a(3, b, "Precaching: Saving local assets for adObject:" + rVar.d());
        List list = apVar.a().adFrames;
        for (int i = 0; i < list.size(); i++) {
            for (String b : apVar.f(i)) {
                if (!b(rVar, b)) {
                    return false;
                }
            }
        }
        return true;
    }

    public void a(r rVar) {
        if (rVar != null) {
            try {
                File a = ct.a("fileStreamCacheDownloaderTmp", rVar.d());
                gd.a(3, b, "Precaching: Removing local assets for adObject:" + rVar.d());
                ho.b(a);
            } catch (Throwable e) {
                gd.a(6, b, "Precaching: Error removing local assets for adObject:" + rVar.d() + " " + e.getMessage(), e);
            }
        }
    }

    private boolean b(r rVar, String str) {
        if (!b() || TextUtils.isEmpty(str)) {
            return false;
        }
        gd.a(3, b, "Precaching: Saving local asset for adObject:" + rVar.d());
        if (ah.COMPLETE.equals(this.a.b(str))) {
            return c(rVar, str);
        }
        return false;
    }

    public boolean a(ap apVar, String str) {
        if (apVar == null || TextUtils.isEmpty(str)) {
            return false;
        }
        List list = apVar.a().adFrames;
        for (int i = 0; i < list.size(); i++) {
            for (String equals : apVar.f(i)) {
                if (equals.equals(str)) {
                    return true;
                }
            }
        }
        return false;
    }

    public File a(r rVar, String str) {
        if (rVar == null || TextUtils.isEmpty(str)) {
            return null;
        }
        return a(rVar.d(), str);
    }

    public File a(int i, String str) {
        if (TextUtils.isEmpty(str)) {
            return null;
        }
        File file = new File(ct.a("fileStreamCacheDownloaderTmp", i), c(str));
        if (file.exists()) {
            return file;
        }
        return null;
    }

    private void j() {
        try {
            File b = ct.b("fileStreamCacheDownloaderTmp");
            gd.a(3, b, "Precaching: Cleaning temp asset directory: " + b);
            ho.b(b);
        } catch (Throwable e) {
            gd.a(6, b, "Precaching: Error cleaning temp asset directory: " + e.getMessage(), e);
        }
    }

    private void d(ap apVar) {
        if (b() && apVar != null) {
            List list = apVar.a().adFrames;
            for (int i = 0; i < list.size(); i++) {
                for (String b : apVar.f(i)) {
                    b(b);
                }
            }
        }
    }

    public int b(ap apVar) {
        if (!b() || apVar == null) {
            return 0;
        }
        List list = apVar.a().adFrames;
        int i = 0;
        int i2 = 0;
        while (i < list.size()) {
            AdFrame adFrame = (AdFrame) list.get(i);
            int i3 = i2;
            for (String a : apVar.f(i)) {
                if (a(a, adFrame.assetExpirationTimestampUTCMillis)) {
                    i3++;
                }
            }
            i++;
            i2 = i3;
        }
        return i2;
    }

    public void c(ap apVar) {
        if (b() && apVar != null) {
            List list = apVar.a().adFrames;
            for (int i = 0; i < list.size(); i++) {
                AdFrame adFrame = (AdFrame) list.get(i);
                for (String str : apVar.f(i)) {
                    if (a(str, adFrame.assetExpirationTimestampUTCMillis)) {
                        a(str);
                    }
                }
            }
        }
    }

    public void a(String str) {
        if (b()) {
            this.a.a(str);
        }
    }

    public void h() {
        if (b()) {
            this.a.h();
        }
    }

    public List<ae> i() {
        if (b()) {
            return this.a.k();
        }
        return Collections.emptyList();
    }

    private void b(String str) {
        if (!TextUtils.isEmpty(str)) {
            this.a.b(str);
        }
    }

    private boolean a(String str, long j) {
        if (TextUtils.isEmpty(str)) {
            return false;
        }
        an anVar = an.UNKNOWN;
        String path = Uri.parse(str).getPath();
        if (TextUtils.isEmpty(path)) {
            gd.a(5, b, "Precaching: could not identify urlPath for asset: " + str);
        } else {
            String guessContentTypeFromName = URLConnection.guessContentTypeFromName(path);
            gd.a(3, b, "Precaching: assetLink: " + str + " urlPath: " + path + " mimeType: " + guessContentTypeFromName);
            if (guessContentTypeFromName != null) {
                if (guessContentTypeFromName.startsWith("video")) {
                    gd.a(3, b, "Precaching: asset is a video: " + str);
                    anVar = an.VIDEO;
                } else if (guessContentTypeFromName.startsWith("image")) {
                    gd.a(3, b, "Precaching: asset is an image: " + str);
                    anVar = an.IMAGE;
                } else if (guessContentTypeFromName.startsWith("text")) {
                    gd.a(3, b, "Precaching: asset is text: " + str);
                    anVar = an.TEXT;
                } else {
                    gd.a(5, b, "Precaching: could not identify media type for asset: " + str);
                }
            }
        }
        return this.a.a(str, anVar, j);
    }

    private boolean c(r rVar, String str) {
        Closeable closeable;
        Throwable th;
        Object obj;
        Throwable th2;
        Closeable closeable2;
        boolean z;
        Closeable closeable3;
        Closeable closeable4 = null;
        if (rVar == null || TextUtils.isEmpty(str)) {
            return false;
        }
        File file = new File(ct.a("fileStreamCacheDownloaderTmp", rVar.d()), c(str));
        try {
            if (file.exists()) {
                closeable = closeable4;
            } else if (ho.a(file)) {
                OutputStream fileOutputStream = new FileOutputStream(file);
                try {
                    closeable4 = this.a.c(str);
                    if (closeable4 != null) {
                        try {
                            hp.a(closeable4.a(), fileOutputStream);
                            gd.a(3, b, "Precaching: Temp asset " + str + " saved to :" + file.getAbsolutePath());
                        } catch (Throwable e) {
                            th = e;
                            obj = fileOutputStream;
                            closeable = closeable4;
                            th2 = th;
                            try {
                                gd.a(6, b, "Precaching: Error saving temp file for url:" + str + " " + th2.getMessage(), th2);
                                hp.a(closeable);
                                hp.a(closeable2);
                                z = false;
                                if (z) {
                                    return z;
                                }
                                file.delete();
                                return z;
                            } catch (Throwable th3) {
                                th2 = th3;
                                hp.a(closeable);
                                hp.a(closeable2);
                                throw th2;
                            }
                        } catch (Throwable th4) {
                            closeable2 = fileOutputStream;
                            closeable = closeable4;
                            th2 = th4;
                            hp.a(closeable);
                            hp.a(closeable2);
                            throw th2;
                        }
                    }
                    gd.a(3, b, "Precaching: Temp asset not saved.  Could not open cache reader: " + str);
                } catch (Throwable e2) {
                    th = e2;
                    obj = fileOutputStream;
                    closeable = closeable4;
                    th2 = th;
                    gd.a(6, b, "Precaching: Error saving temp file for url:" + str + " " + th2.getMessage(), th2);
                    hp.a(closeable);
                    hp.a(closeable2);
                    z = false;
                    if (z) {
                        return z;
                    }
                    file.delete();
                    return z;
                } catch (Throwable th42) {
                    obj = fileOutputStream;
                    closeable = closeable4;
                    th2 = th42;
                    hp.a(closeable);
                    hp.a(closeable2);
                    throw th2;
                }
            } else {
                throw new IOException("Precaching: Error creating directory to save tmp file:" + file.getAbsolutePath());
            }
            hp.a(closeable4);
            hp.a(closeable);
            z = true;
        } catch (Throwable e3) {
            closeable2 = closeable4;
            closeable3 = closeable4;
            th2 = e3;
            closeable = closeable3;
            gd.a(6, b, "Precaching: Error saving temp file for url:" + str + " " + th2.getMessage(), th2);
            hp.a(closeable);
            hp.a(closeable2);
            z = false;
            if (z) {
                return z;
            }
            file.delete();
            return z;
        } catch (Throwable e32) {
            closeable2 = closeable4;
            closeable3 = closeable4;
            th2 = e32;
            closeable = closeable3;
            hp.a(closeable);
            hp.a(closeable2);
            throw th2;
        }
        if (z) {
            return z;
        }
        file.delete();
        return z;
    }

    private String c(String str) {
        return ct.c(str);
    }

    private synchronized void a(com.flurry.sdk.ab.a aVar) {
        fx abVar = new ab();
        abVar.a = aVar;
        fz.a().a(abVar);
    }
}
