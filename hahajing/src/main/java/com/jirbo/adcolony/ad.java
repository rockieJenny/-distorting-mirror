package com.jirbo.adcolony;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.view.Display;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.widget.FrameLayout.LayoutParams;

class ad extends h {
    boolean H;

    public ad(String str, AdColonyV4VCAd adColonyV4VCAd) {
        this.F = str;
        this.G = adColonyV4VCAd;
        if (a()) {
            AdColony.activity().addContentView(this, new LayoutParams(-1, -1, 17));
        }
    }

    public void onDraw(Canvas canvas) {
        c();
        int currentTimeMillis = (((int) (System.currentTimeMillis() - this.w)) * 255) / 1000;
        if (currentTimeMillis > 128) {
            currentTimeMillis = 128;
        }
        canvas.drawARGB(currentTimeMillis, 0, 0, 0);
        this.a.a(canvas, this.x, this.y);
        int b = (b() * 3) / 2;
        int remainingViewsUntilReward = this.G.getRemainingViewsUntilReward();
        if (remainingViewsUntilReward == this.G.getViewsPerReward() || remainingViewsUntilReward == 0) {
            a(this.F, "video. You earned");
            if (s) {
                a("Thanks for watching the sponsored", this.z, (int) (((double) this.A) - (((double) b) * 2.5d)), canvas);
                a("video. You earned " + q + ".", this.z, (int) (((double) this.A) - (((double) b) * 1.5d)), canvas);
            } else {
                a("Thanks for watching the sponsored", this.z, (int) (((double) this.A) - (((double) b) * 2.8d)), canvas);
                a("video. You earned " + q, this.z, (int) (((double) this.A) - (((double) b) * 2.05d)), canvas);
                a(r + ".", this.z, (int) (((double) this.A) - (((double) b) * 1.3d)), canvas);
            }
        } else {
            a(this.F, "to earn ");
            String str = remainingViewsUntilReward == 1 ? "video" : "videos";
            if (s) {
                a("Thank you. Watch " + remainingViewsUntilReward + " more " + str, this.z, (int) (((double) this.A) - (((double) b) * 2.5d)), canvas);
                a("to earn " + q + ".", this.z, (int) (((double) this.A) - (((double) b) * 1.5d)), canvas);
            } else {
                a("Thank you. Watch " + remainingViewsUntilReward + " more " + str, this.z, (int) (((double) this.A) - (((double) b) * 2.8d)), canvas);
                a("to earn " + q, this.z, (int) (((double) this.A) - (((double) b) * 2.05d)), canvas);
                a(r + ".", this.z, (int) (((double) this.A) - (((double) b) * 1.3d)), canvas);
            }
        }
        this.b.a(canvas, this.z - (this.b.f / 2), this.A - (this.b.g / 2));
        if (this.H) {
            this.g.a(canvas, this.B, this.D);
        } else {
            this.h.a(canvas, this.B, this.D);
        }
        c("Ok", this.B, this.D, canvas);
        if (currentTimeMillis != 128) {
            invalidate();
        }
    }

    void c() {
        Display defaultDisplay = a.b().getWindowManager().getDefaultDisplay();
        int width = defaultDisplay.getWidth();
        int height = defaultDisplay.getHeight();
        double d = this.n ? 12.0d : 16.0d;
        this.x = (width - this.a.f) / 2;
        this.y = ((height - this.a.g) / 2) - 80;
        this.z = this.x + (this.a.f / 2);
        this.A = this.y + (this.a.g / 2);
        this.D = ((int) (((double) this.a.g) - ((d * p) + ((double) this.h.g)))) + this.y;
        this.B = this.z - (this.h.f / 2);
    }

    public boolean onTouchEvent(MotionEvent event) {
        int x = (int) event.getX();
        int y = (int) event.getY();
        if (event.getAction() == 1) {
            if (a(x, y, this.B, this.D) && this.H) {
                a.J = null;
                ((ViewGroup) getParent()).removeView(this);
                for (int i = 0; i < a.ae.size(); i++) {
                    ((Bitmap) a.ae.get(i)).recycle();
                }
                a.ae.clear();
                a.v = true;
            }
            this.H = false;
            invalidate();
        }
        if (event.getAction() == 0 && a(x, y, this.B, this.D)) {
            this.H = true;
            invalidate();
        }
        return true;
    }
}
