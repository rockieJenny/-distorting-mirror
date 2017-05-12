package com.appflood.e;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import com.appflood.b.b;
import com.appflood.b.b.a;

public final class f implements a {
    private /* synthetic */ Drawable[] a;

    public f(Drawable[] drawableArr) {
        this.a = drawableArr;
    }

    public final void a(int i) {
    }

    public final void a(b bVar) {
        Bitmap d = bVar.d();
        if (d != null) {
            this.a[1] = new BitmapDrawable(d);
        }
    }
}
