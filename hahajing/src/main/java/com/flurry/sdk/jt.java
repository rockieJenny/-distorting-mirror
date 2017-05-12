package com.flurry.sdk;

import java.io.IOException;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public final class jt extends ir<Time> {
    public static final is a = new is() {
        public <T> ir<T> a(ia iaVar, jw<T> jwVar) {
            return jwVar.a() == Time.class ? new jt() : null;
        }
    };
    private final DateFormat b = new SimpleDateFormat("hh:mm:ss a");

    public /* synthetic */ Object b(jx jxVar) throws IOException {
        return a(jxVar);
    }

    public synchronized Time a(jx jxVar) throws IOException {
        Time time;
        if (jxVar.f() == jy.NULL) {
            jxVar.j();
            time = null;
        } else {
            try {
                time = new Time(this.b.parse(jxVar.h()).getTime());
            } catch (Throwable e) {
                throw new io(e);
            }
        }
        return time;
    }

    public synchronized void a(jz jzVar, Time time) throws IOException {
        jzVar.b(time == null ? null : this.b.format(time));
    }
}
