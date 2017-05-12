package com.appflood.e;

import android.graphics.drawable.Drawable;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;

public final class g implements OnTouchListener {
    private /* synthetic */ Drawable[] a;

    public g(Drawable[] drawableArr) {
        this.a = drawableArr;
    }

    public final boolean onTouch(View view, MotionEvent motionEvent) {
        if (motionEvent.getAction() == 1 || motionEvent.getAction() == 3) {
            view.setBackgroundDrawable(this.a[0]);
        } else if (motionEvent.getAction() == 0) {
            view.setBackgroundDrawable(this.a[1]);
        }
        return false;
    }
}
