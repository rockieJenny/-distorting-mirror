package com.appflood.e;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.StateListDrawable;
import android.view.View;
import android.widget.SeekBar;
import com.appflood.b.b;
import com.appflood.b.b.a;
import com.appflood.c.f;

public final class i implements a {
    final /* synthetic */ StateListDrawable a;
    final /* synthetic */ View b;

    public i(StateListDrawable stateListDrawable, View view) {
        this.a = stateListDrawable;
        this.b = view;
    }

    public final void a(int i) {
    }

    public final void a(b bVar) {
        Bitmap d = bVar.d();
        if (d != null) {
            this.a.addState(new int[0], new BitmapDrawable(d));
            if (this.b instanceof SeekBar) {
                f.a(new Runnable(this) {
                    private /* synthetic */ i a;

                    {
                        this.a = r1;
                    }

                    public final void run() {
                        j.a();
                        ((SeekBar) this.a.b).setThumb(this.a.a);
                    }
                });
            }
        }
    }
}
