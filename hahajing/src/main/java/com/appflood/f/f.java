package com.appflood.f;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.RectF;
import com.appflood.e.b;

public final class f extends a {
    private Paint d = new Paint();
    private b e = new b(454.0f, 222.0f);
    private b f = new b(486.0f, 251.0f);
    private RectF g;
    private RectF h;
    private int i = 16;
    private int j;
    private int k = 22;
    private int l = 16;
    private int m = 25;

    public f(Context context, float f, int i) {
        super(context);
        this.e.a(f);
        this.f.a(f);
        this.i = (int) (((float) this.i) * f);
        this.k = (int) (((float) this.k) * f);
        this.l = (int) (((float) this.l) * f);
        this.m = (int) (((float) this.m) * f);
        this.j = (int) (((float) i) * f);
    }

    protected final void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        try {
            int i = this.j;
            this.g = new RectF((float) i, 0.0f, (float) (((int) this.f.a) + i), (float) (((int) this.f.b) + 0));
            this.h = new RectF((float) (this.i + i), (float) (this.i + 0), (float) ((i + this.i) + ((int) this.e.a)), (float) (((int) this.e.b) + 0));
            this.d.setColor(-526083);
            this.d.setStyle(Style.FILL);
            canvas.drawRoundRect(this.g, 3.0f, 3.0f, this.d);
            if (this.c != null) {
                canvas.drawBitmap(this.c, null, this.h, null);
            }
        } catch (Throwable th) {
        }
    }
}
