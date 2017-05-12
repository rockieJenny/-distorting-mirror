package com.jirbo.adcolony;

import android.graphics.Bitmap;
import com.inmobi.re.controller.JSController;

public final class AdColonyV4VCAd extends AdColonyAd {
    AdColonyAdListener v;
    boolean w = false;
    boolean x = false;
    boolean y;

    public AdColonyV4VCAd() {
        a.u = false;
        a.e();
        this.j = "v4vc";
        this.y = false;
        this.k = JSController.FULL_SCREEN;
        this.l = ab.b();
    }

    public AdColonyV4VCAd(String zone_id) {
        a.e();
        this.g = zone_id;
        this.j = "v4vc";
        this.y = false;
        this.k = JSController.FULL_SCREEN;
        this.l = ab.b();
    }

    public AdColonyV4VCAd withListener(AdColonyAdListener listener) {
        this.v = listener;
        return this;
    }

    public AdColonyV4VCAd withConfirmationDialog(boolean setting) {
        this.w = setting;
        return this;
    }

    public AdColonyV4VCAd withResultsDialog(boolean setting) {
        this.x = setting;
        a.u = this.x;
        return this;
    }

    public AdColonyV4VCAd withConfirmationDialog() {
        return withConfirmationDialog(true);
    }

    public AdColonyV4VCAd withResultsDialog() {
        return withResultsDialog(true);
    }

    boolean b() {
        return true;
    }

    boolean a(boolean z) {
        return false;
    }

    public boolean isReady() {
        if (this.g == null) {
            this.g = a.l.f();
            if (this.g == null) {
                return false;
            }
        }
        return a.l.g(this.g);
    }

    public String getRewardName() {
        if (c()) {
            return this.h.j.d;
        }
        return "";
    }

    public int getRewardAmount() {
        if (c()) {
            return this.h.j.c;
        }
        return 0;
    }

    public int getViewsPerReward() {
        if (c()) {
            return this.h.j.f;
        }
        return 0;
    }

    public int getRemainingViewsUntilReward() {
        if (c()) {
            return this.h.j.f - a.l.e(this.h.j.d);
        }
        return 0;
    }

    public void show() {
        a.ad = 0;
        if (this.y) {
            l.d.b((Object) "Show attempt on out of date ad object. Please instantiate a new ad object for each ad attempt.");
            return;
        }
        this.y = true;
        if (!isReady()) {
            AnonymousClass1 anonymousClass1 = new j(this, a.l) {
                final /* synthetic */ AdColonyV4VCAd a;

                void a() {
                    if (this.a.g != null) {
                        this.o.d.a(this.a.g, this.a);
                    }
                }
            };
            this.f = 2;
            if (this.v != null) {
                this.v.onAdColonyAdAttemptFinished(this);
            }
        } else if (a.v) {
            AnonymousClass2 anonymousClass2 = new j(this, a.l) {
                final /* synthetic */ AdColonyV4VCAd a;

                void a() {
                    this.o.d.a(this.a.g, this.a);
                }
            };
            a.v = false;
            c();
            a.K = this;
            a.l.a(this);
            if (this.w) {
                a("Confirmation");
            } else {
                c(true);
            }
        }
    }

    void c(boolean z) {
        if (!z) {
            this.f = 1;
        } else if (a.l.b(this)) {
            if (this.v != null) {
                this.v.onAdColonyAdStarted(this);
            }
            this.f = 4;
        } else {
            this.f = 3;
        }
        if (this.f != 4 && this.v != null) {
            this.v.onAdColonyAdAttemptFinished(this);
        }
    }

    void a() {
        if (this.f == 4 && this.x) {
            a("Result");
        }
        if (this.v != null) {
            this.v.onAdColonyAdAttemptFinished(this);
        }
        a.h();
        if (!(a.u || AdColonyBrowser.B)) {
            for (int i = 0; i < a.ae.size(); i++) {
                ((Bitmap) a.ae.get(i)).recycle();
            }
            a.ae.clear();
        }
        a.L = null;
        if (!this.x) {
            a.v = true;
        }
        System.gc();
    }

    void a(String str) {
        String str2 = "";
        String str3 = ("" + getRewardAmount()) + " " + getRewardName();
        if (str.equals("Confirmation")) {
            a.J = new ac(str3, this);
        } else {
            a.J = new ad(str3, this);
        }
    }
}
