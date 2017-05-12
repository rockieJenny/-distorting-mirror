package com.appflood.b;

import com.appflood.e.j;
import com.appflood.e.k;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Vector;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

public final class a {
    public ArrayList<b> a = new ArrayList(4);
    public ThreadPoolExecutor b = ((ThreadPoolExecutor) Executors.newFixedThreadPool(4));
    private LinkedList<b> c = new LinkedList();

    private boolean a(URL url) {
        synchronized (this) {
            try {
                Iterator it = this.a.iterator();
                while (it.hasNext()) {
                    if (k.a(((b) it.next()).a(), url)) {
                        return true;
                    }
                }
            } catch (Throwable th) {
                j.b(th, "Failed in existInWorker");
            }
        }
        return false;
    }

    final void a() {
        Throwable th;
        if (this.b != null) {
            Vector vector;
            Iterator it;
            synchronized (this) {
                Vector vector2;
                try {
                    if (this.c.isEmpty()) {
                        vector = null;
                    } else {
                        vector2 = null;
                        while (this.a.size() < 4 && !this.c.isEmpty()) {
                            try {
                                Runnable runnable;
                                for (int i = 0; i < this.c.size(); i++) {
                                    runnable = (b) this.c.get(i);
                                    if (!runnable.a || !a(runnable.a())) {
                                        this.c.remove(i);
                                        break;
                                    }
                                }
                                runnable = null;
                                if (runnable == null) {
                                    break;
                                } else if (runnable.a && runnable.e()) {
                                    if (vector2 == null) {
                                        vector2 = new Vector();
                                    }
                                    vector2.add(runnable);
                                } else {
                                    this.a.add(runnable);
                                    this.b.submit(runnable);
                                }
                            } catch (Throwable th2) {
                                th = th2;
                            }
                        }
                        vector = vector2;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    vector2 = null;
                    j.b(th, "error occurred in web cache loop");
                    vector = vector2;
                    if (vector != null) {
                        it = vector.iterator();
                        while (it.hasNext()) {
                            ((b) it.next()).a(false);
                        }
                    }
                }
            }
            if (vector != null) {
                it = vector.iterator();
                while (it.hasNext()) {
                    ((b) it.next()).a(false);
                }
            }
        }
    }

    protected final void a(b bVar) {
        synchronized (this) {
            try {
                this.a.remove(bVar);
            } catch (Throwable th) {
            }
        }
        a();
    }

    protected final void b(b bVar) {
        if (bVar.a && bVar.e()) {
            bVar.a(false);
            return;
        }
        synchronized (this) {
            try {
                this.c.add(bVar);
            } catch (Throwable th) {
                j.a(th, "Failed to appendRequest: " + bVar);
            }
        }
        a();
    }

    protected final void c(b bVar) {
        if (bVar.a && bVar.e()) {
            bVar.a(false);
            return;
        }
        synchronized (this) {
            try {
                this.c.addFirst(bVar);
            } catch (Throwable th) {
                th.printStackTrace();
            }
        }
        a();
    }
}
