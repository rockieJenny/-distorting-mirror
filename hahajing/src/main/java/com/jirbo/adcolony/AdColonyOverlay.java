package com.jirbo.adcolony;

import android.content.res.Configuration;
import android.graphics.Rect;
import android.os.Handler;
import android.support.v4.view.ViewCompat;
import android.view.Display;
import android.view.View;
import android.widget.FrameLayout.LayoutParams;

public class AdColonyOverlay extends ADCVideo {
    Rect V = new Rect();
    int W = 0;

    public void onConfigurationChanged(Configuration new_config) {
        super.onConfigurationChanged(new_config);
        Display defaultDisplay = getWindowManager().getDefaultDisplay();
        this.u = defaultDisplay.getWidth();
        this.v = defaultDisplay.getHeight();
        a.B = true;
        final View view = new View(this);
        view.setBackgroundColor(ViewCompat.MEASURED_STATE_MASK);
        if (d && this.G.Q) {
            this.P.setLayoutParams(new LayoutParams(this.u, this.v - this.G.m, 17));
            this.O.addView(view, new LayoutParams(this.u, this.v, 17));
            new Handler().postDelayed(new Runnable(this) {
                final /* synthetic */ AdColonyOverlay b;

                public void run() {
                    this.b.O.removeView(view);
                }
            }, 1500);
        }
        this.G.a();
    }
}
