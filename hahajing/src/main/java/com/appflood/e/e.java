package com.appflood.e;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;
import com.appflood.b.b;
import com.appflood.b.b.a;
import com.appflood.c.f;

public final class e implements a {
    final /* synthetic */ View a;
    private /* synthetic */ Drawable[] b;

    public e(Drawable[] drawableArr, View view) {
        this.b = drawableArr;
        this.a = view;
    }

    public final void a(int i) {
    }

    public final void a(b bVar) {
        Bitmap d = bVar.d();
        if (d != null) {
            final Drawable bitmapDrawable = new BitmapDrawable(d);
            this.b[0] = bitmapDrawable;
            f.a(new Runnable(this) {
                private /* synthetic */ e b;

                public final void run() {
                    this.b.a.setBackgroundDrawable(bitmapDrawable);
                }
            });
        }
    }
}
