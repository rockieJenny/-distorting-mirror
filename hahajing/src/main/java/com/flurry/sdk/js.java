package com.flurry.sdk;

import java.io.IOException;
import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public final class js extends ir<Date> {
    public static final is a = new is() {
        public <T> ir<T> a(ia iaVar, jw<T> jwVar) {
            return jwVar.a() == Date.class ? new js() : null;
        }
    };
    private final DateFormat b = new SimpleDateFormat("MMM d, yyyy");

    public /* synthetic */ Object b(jx jxVar) throws IOException {
        return a(jxVar);
    }

    public synchronized Date a(jx jxVar) throws IOException {
        Date date;
        if (jxVar.f() == jy.NULL) {
            jxVar.j();
            date = null;
        } else {
            try {
                date = new Date(this.b.parse(jxVar.h()).getTime());
            } catch (Throwable e) {
                throw new io(e);
            }
        }
        return date;
    }

    public synchronized void a(jz jzVar, Date date) throws IOException {
        jzVar.b(date == null ? null : this.b.format(date));
    }
}
