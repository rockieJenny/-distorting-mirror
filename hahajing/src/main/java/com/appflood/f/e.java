package com.appflood.f;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Align;
import android.graphics.Paint.Style;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Typeface;
import com.appflood.b.b.a;
import com.appflood.c.d;
import com.appflood.e.b;
import com.appflood.e.j;

public final class e extends a {
    private Paint d = new Paint();
    private b e = new b(421.0f, 206.0f);
    private b f = new b(104.0f, 40.0f);
    private RectF g;
    private RectF h;
    private Rect i;
    private int j = 13;
    private int k;
    private float l = 20.0f;
    private int m = 16;
    private int n = 25;
    private Bitmap o;

    public e(Context context, float f, int i) {
        super(context);
        this.e.a(f);
        this.f.a(f);
        this.j = (int) (((float) this.j) * f);
        this.l = (float) ((int) (this.l * f));
        this.m = (int) (((float) this.m) * f);
        this.n = (int) (((float) this.n) * f);
        this.k = (int) (((float) i) * f);
        com.appflood.b.b bVar = new com.appflood.b.b(d.z + d.e, (byte) 0);
        bVar.a(new a(this) {
            private /* synthetic */ e a;

            {
                this.a = r1;
            }

            public final void a(int i) {
                j.c("failed to download download png");
            }

            public final void a(com.appflood.b.b bVar) {
                this.a.o = bVar.d();
            }
        });
        bVar.f();
    }

    protected final void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        try {
            int i = this.k;
            int i2 = this.k;
            int height = (getHeight() + i2) - (this.k * 2);
            this.g = new RectF((float) i, (float) i2, (float) ((getWidth() + i) - (this.k * 2)), (float) height);
            this.h = new RectF((float) (this.j + i), (float) (this.j + i2), (float) ((i + this.j) + ((int) this.e.a)), (float) (i2 + ((int) this.e.b)));
            i2 = (int) (this.h.bottom + (((((float) height) - this.h.bottom) - ((float) ((int) this.f.b))) / 2.0f));
            this.i = new Rect((int) ((this.h.right + 2.0f) - ((float) ((int) this.f.a))), i2, ((int) this.h.right) + 2, ((int) this.f.b) + i2);
            this.d.setColor(-526083);
            this.d.setStyle(Style.FILL);
            canvas.drawRoundRect(this.g, 3.0f, 3.0f, this.d);
            if (this.c != null) {
                canvas.drawBitmap(this.c, null, this.h, null);
            }
            Paint paint = new Paint();
            paint.setAntiAlias(true);
            paint.setColor(-11182220);
            paint.setTypeface(Typeface.DEFAULT_BOLD);
            paint.setTextSize(this.l);
            paint.setTextAlign(Align.LEFT);
            if (this.a != null) {
                Rect rect = new Rect();
                paint.getTextBounds(this.a, 0, this.a.length(), rect);
                canvas.drawText(this.a, this.h.left, (float) (((rect.height() << 1) / 3) + this.i.top), paint);
            }
            if (this.b != null) {
                paint.setTypeface(Typeface.DEFAULT);
                paint.setTextSize((float) this.m);
                canvas.drawText(this.b, this.h.left, (float) this.i.bottom, paint);
            }
            canvas.drawBitmap(this.o, null, this.i, null);
        } catch (Throwable th) {
        }
    }
}
