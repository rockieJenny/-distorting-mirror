package com.flurry.sdk;

import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public final class jl extends ir<Date> {
    public static final is a = new is() {
        public <T> ir<T> a(ia iaVar, jw<T> jwVar) {
            return jwVar.a() == Date.class ? new jl() : null;
        }
    };
    private final DateFormat b = DateFormat.getDateTimeInstance(2, 2, Locale.US);
    private final DateFormat c = DateFormat.getDateTimeInstance(2, 2);
    private final DateFormat d = a();

    public /* synthetic */ Object b(jx jxVar) throws IOException {
        return a(jxVar);
    }

    private static DateFormat a() {
        DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
        simpleDateFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
        return simpleDateFormat;
    }

    public Date a(jx jxVar) throws IOException {
        if (jxVar.f() != jy.NULL) {
            return a(jxVar.h());
        }
        jxVar.j();
        return null;
    }

    private synchronized Date a(String str) {
        Date parse;
        try {
            parse = this.c.parse(str);
        } catch (ParseException e) {
            try {
                parse = this.b.parse(str);
            } catch (ParseException e2) {
                try {
                    parse = this.d.parse(str);
                } catch (Throwable e3) {
                    throw new io(str, e3);
                }
            }
        }
        return parse;
    }

    public synchronized void a(jz jzVar, Date date) throws IOException {
        if (date == null) {
            jzVar.f();
        } else {
            jzVar.b(this.b.format(date));
        }
    }
}
