package com.appflood.f;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.graphics.RectF;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import com.appflood.b.b;
import com.appflood.b.b.a;
import com.appflood.e.j;
import java.io.ByteArrayInputStream;
import java.util.Timer;
import java.util.TimerTask;

public final class d extends ImageView {
    private Bitmap a;
    private c b = new c();
    private Timer c;
    private TimerTask d;

    public d(Context context) {
        super(context);
        setScaleType(ScaleType.CENTER_INSIDE);
        setBackgroundColor(0);
        b bVar = new b(com.appflood.c.d.z + "video_loading.gif", (byte) 0);
        bVar.a(new a(this) {
            private /* synthetic */ d a;

            {
                this.a = r1;
            }

            public final void a(int i) {
            }

            public final void a(b bVar) {
                " finished " + bVar.a().toString() + bVar.b();
                j.a();
                this.a.b.a(new ByteArrayInputStream(bVar.b()));
                this.a.a = this.a.b.a(0);
            }
        });
        bVar.f();
    }

    public final void onAttachedToWindow() {
        super.onAttachedToWindow();
        if (this.c == null) {
            this.c = new Timer();
            this.d = new TimerTask(this) {
                private /* synthetic */ d a;

                {
                    this.a = r1;
                }

                public final void run() {
                    this.a.postInvalidate();
                }
            };
        }
        this.c.schedule(this.d, 0, 200);
    }

    public final void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.c != null) {
            this.c.cancel();
            this.c = null;
            this.d = null;
        }
    }

    protected final void onDraw(Canvas canvas) {
        if (this.a != null) {
            canvas.drawBitmap(this.a, null, new RectF(new Rect(0, 0, getWidth(), getHeight())), null);
            c cVar = this.b;
            cVar.a++;
            if (cVar.a > cVar.b.size() - 1) {
                cVar.a = 0;
            }
            this.a = ((a) cVar.b.elementAt(cVar.a)).a;
        }
    }
}
