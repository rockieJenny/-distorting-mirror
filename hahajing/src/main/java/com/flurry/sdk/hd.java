package com.flurry.sdk;

import java.util.Timer;
import java.util.TimerTask;

class hd {
    private Timer a;
    private a b;

    class a extends TimerTask {
        final /* synthetic */ hd a;

        a(hd hdVar) {
            this.a = hdVar;
        }

        public void run() {
            this.a.a();
            new he().b();
        }
    }

    hd() {
    }

    public synchronized void a(long j) {
        if (b()) {
            a();
        }
        this.a = new Timer("FlurrySessionTimer");
        this.b = new a(this);
        this.a.schedule(this.b, j);
    }

    public synchronized void a() {
        if (this.a != null) {
            this.a.cancel();
            this.a = null;
        }
        this.b = null;
    }

    public boolean b() {
        if (this.a != null) {
            return true;
        }
        return false;
    }
}
