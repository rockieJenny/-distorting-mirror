package com.google.android.gms.analytics;

import android.content.Context;
import android.os.Process;
import android.text.TextUtils;
import com.google.analytics.tracking.android.Fields;
import com.google.android.gms.analytics.internal.Command;
import com.google.android.gms.internal.ha;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

class x extends Thread implements f {
    private static x zM;
    private volatile boolean mClosed = false;
    private final Context mContext;
    private final LinkedBlockingQueue<Runnable> zJ = new LinkedBlockingQueue();
    private volatile boolean zK = false;
    private volatile String zL;
    private volatile ak zN;
    private final Lock zO;
    private final List<ha> zP = new ArrayList();

    private class a implements Runnable {
        final /* synthetic */ x zQ;

        private a(x xVar) {
            this.zQ = xVar;
        }

        public void run() {
            this.zQ.zN.dQ();
        }
    }

    private class b implements Runnable {
        final /* synthetic */ x zQ;

        private b(x xVar) {
            this.zQ = xVar;
        }

        public void run() {
            this.zQ.zN.dispatch();
        }
    }

    private class c implements Runnable {
        final /* synthetic */ x zQ;

        private c(x xVar) {
            this.zQ = xVar;
        }

        public void run() {
            this.zQ.zN.dW();
        }
    }

    private class d implements Runnable {
        final /* synthetic */ x zQ;
        private final Map<String, String> zR;

        d(x xVar, Map<String, String> map) {
            this.zQ = xVar;
            this.zR = new HashMap(map);
            String str = (String) map.get("&ht");
            if (str != null) {
                try {
                    Long.valueOf(str);
                } catch (NumberFormatException e) {
                    str = null;
                }
            }
            if (str == null) {
                this.zR.put("&ht", Long.toString(System.currentTimeMillis()));
            }
        }

        private String v(Map<String, String> map) {
            return map.containsKey(Fields.USE_SECURE) ? an.f((String) map.get(Fields.USE_SECURE), true) ? "https:" : "http:" : "https:";
        }

        private void w(Map<String, String> map) {
            q w = a.w(this.zQ.mContext);
            an.a((Map) map, "&adid", w);
            an.a((Map) map, "&ate", w);
        }

        private void x(Map<String, String> map) {
            q dZ = g.dZ();
            an.a((Map) map, Fields.APP_NAME, dZ);
            an.a((Map) map, Fields.APP_VERSION, dZ);
            an.a((Map) map, Fields.APP_ID, dZ);
            an.a((Map) map, Fields.APP_INSTALLER_ID, dZ);
            map.put("&v", "1");
        }

        private boolean y(Map<String, String> map) {
            if (map.get(Fields.SAMPLE_RATE) == null) {
                return false;
            }
            double a = an.a((String) map.get(Fields.SAMPLE_RATE), 100.0d);
            if (a >= 100.0d) {
                return false;
            }
            if (((double) (x.ah((String) map.get(Fields.CLIENT_ID)) % 10000)) < a * 100.0d) {
                return false;
            }
            String str = map.get(Fields.HIT_TYPE) == null ? "unknown" : (String) map.get(Fields.HIT_TYPE);
            ae.V(String.format("%s hit sampled out", new Object[]{str}));
            return true;
        }

        public void run() {
            w(this.zR);
            if (TextUtils.isEmpty((CharSequence) this.zR.get(Fields.CLIENT_ID))) {
                this.zR.put(Fields.CLIENT_ID, k.el().getValue(Fields.CLIENT_ID));
            }
            if (!GoogleAnalytics.getInstance(this.zQ.mContext).getAppOptOut() && !y(this.zR)) {
                if (!TextUtils.isEmpty(this.zQ.zL)) {
                    y.eK().D(true);
                    this.zR.putAll(new HitBuilder().setCampaignParamsFromUrl(this.zQ.zL).build());
                    y.eK().D(false);
                    this.zQ.zL = null;
                }
                x(this.zR);
                this.zQ.zN.b(ac.z(this.zR), Long.valueOf((String) this.zR.get("&ht")).longValue(), v(this.zR), this.zQ.zP);
            }
        }
    }

    private x(Context context) {
        super("GAThread");
        if (context != null) {
            this.mContext = context.getApplicationContext();
        } else {
            this.mContext = context;
        }
        this.zP.add(new ha(Command.APPEND_VERSION, "&_v".substring(1), "ma4.0.4"));
        this.zO = new ReentrantLock();
        start();
    }

    static x A(Context context) {
        if (zM == null) {
            zM = new x(context);
        }
        return zM;
    }

    static String B(Context context) {
        try {
            FileInputStream openFileInput = context.openFileInput("gaInstallData");
            byte[] bArr = new byte[8192];
            int read = openFileInput.read(bArr, 0, 8192);
            if (openFileInput.available() > 0) {
                ae.T("Too much campaign data, ignoring it.");
                openFileInput.close();
                context.deleteFile("gaInstallData");
                return null;
            }
            openFileInput.close();
            context.deleteFile("gaInstallData");
            if (read <= 0) {
                ae.W("Campaign file is empty.");
                return null;
            }
            String str = new String(bArr, 0, read);
            ae.U("Campaign found: " + str);
            return str;
        } catch (FileNotFoundException e) {
            ae.U("No campaign data found.");
            return null;
        } catch (IOException e2) {
            ae.T("Error reading campaign data.");
            context.deleteFile("gaInstallData");
            return null;
        }
    }

    static int ah(String str) {
        int i = 1;
        if (!TextUtils.isEmpty(str)) {
            i = 0;
            for (int length = str.length() - 1; length >= 0; length--) {
                char charAt = str.charAt(length);
                i = (((i << 6) & 268435455) + charAt) + (charAt << 14);
                int i2 = 266338304 & i;
                if (i2 != 0) {
                    i ^= i2 >> 21;
                }
            }
        }
        return i;
    }

    private void b(Runnable runnable) {
        this.zJ.add(runnable);
    }

    private String g(Throwable th) {
        OutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(byteArrayOutputStream);
        th.printStackTrace(printStream);
        printStream.flush();
        return new String(byteArrayOutputStream.toByteArray());
    }

    public void dQ() {
        b(new a());
    }

    public void dW() {
        b(new c());
    }

    public LinkedBlockingQueue<Runnable> dX() {
        return this.zJ;
    }

    public void dY() {
        init();
        Object<Runnable> arrayList = new ArrayList();
        this.zJ.drainTo(arrayList);
        this.zO.lock();
        try {
            this.zK = true;
            for (Runnable run : arrayList) {
                run.run();
            }
        } catch (Throwable th) {
            this.zO.unlock();
        }
        this.zO.unlock();
    }

    public void dispatch() {
        b(new b());
    }

    public Thread getThread() {
        return this;
    }

    protected synchronized void init() {
        if (this.zN == null) {
            this.zN = new w(this.mContext, this);
            this.zN.eB();
        }
    }

    public void run() {
        Process.setThreadPriority(10);
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            ae.W("sleep interrupted in GAThread initialize");
        }
        try {
            init();
            this.zL = B(this.mContext);
            ae.V("Initialized GA Thread");
        } catch (Throwable th) {
            ae.T("Error initializing the GAThread: " + g(th));
            ae.T("Google Analytics will not start up.");
            this.zK = true;
        }
        while (!this.mClosed) {
            try {
                Runnable runnable = (Runnable) this.zJ.take();
                this.zO.lock();
                if (!this.zK) {
                    runnable.run();
                }
                this.zO.unlock();
            } catch (InterruptedException e2) {
                ae.U(e2.toString());
            } catch (Throwable th2) {
                ae.T("Error on GAThread: " + g(th2));
                ae.T("Google Analytics is shutting down.");
                this.zK = true;
            }
        }
    }

    public void u(Map<String, String> map) {
        b(new d(this, map));
    }
}
