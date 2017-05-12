package com.flurry.sdk;

import com.flurry.sdk.gl.c;
import java.io.InputStream;
import java.io.OutputStream;

public class gk<RequestObjectType, ResponseObjectType> extends gl {
    private a<RequestObjectType, ResponseObjectType> a;
    private RequestObjectType b;
    private ResponseObjectType c;
    private gx<RequestObjectType> d;
    private gx<ResponseObjectType> e;

    public interface a<RequestObjectType, ResponseObjectType> {
        void a(gk<RequestObjectType, ResponseObjectType> gkVar, ResponseObjectType responseObjectType);
    }

    public void a(RequestObjectType requestObjectType) {
        this.b = requestObjectType;
    }

    public void a(gx<RequestObjectType> gxVar) {
        this.d = gxVar;
    }

    public void b(gx<ResponseObjectType> gxVar) {
        this.e = gxVar;
    }

    public void a(a<RequestObjectType, ResponseObjectType> aVar) {
        this.a = aVar;
    }

    public void safeRun() {
        m();
        super.safeRun();
    }

    private void m() {
        a(new c(this) {
            final /* synthetic */ gk a;

            {
                this.a = r1;
            }

            public void a(gl glVar, OutputStream outputStream) throws Exception {
                if (this.a.b != null && this.a.d != null) {
                    this.a.d.a(outputStream, this.a.b);
                }
            }

            public void a(gl glVar, InputStream inputStream) throws Exception {
                if (glVar.d() && this.a.e != null) {
                    this.a.c = this.a.e.b(inputStream);
                }
            }

            public void a(gl glVar) {
                this.a.n();
            }
        });
    }

    private void n() {
        if (this.a != null && !b()) {
            this.a.a(this, this.c);
        }
    }
}
