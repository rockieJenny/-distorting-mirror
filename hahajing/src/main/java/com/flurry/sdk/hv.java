package com.flurry.sdk;

import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

final class hv implements if<Date>, in<Date> {
    private final DateFormat a;
    private final DateFormat b;
    private final DateFormat c;

    public /* synthetic */ Object b(ig igVar, Type type, ie ieVar) throws ik {
        return a(igVar, type, ieVar);
    }

    hv() {
        this(DateFormat.getDateTimeInstance(2, 2, Locale.US), DateFormat.getDateTimeInstance(2, 2));
    }

    hv(String str) {
        this(new SimpleDateFormat(str, Locale.US), new SimpleDateFormat(str));
    }

    public hv(int i, int i2) {
        this(DateFormat.getDateTimeInstance(i, i2, Locale.US), DateFormat.getDateTimeInstance(i, i2));
    }

    hv(DateFormat dateFormat, DateFormat dateFormat2) {
        this.a = dateFormat;
        this.b = dateFormat2;
        this.c = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
        this.c.setTimeZone(TimeZone.getTimeZone("UTC"));
    }

    public ig a(Date date, Type type, im imVar) {
        ig ilVar;
        synchronized (this.b) {
            ilVar = new il(this.a.format(date));
        }
        return ilVar;
    }

    public Date a(ig igVar, Type type, ie ieVar) throws ik {
        if (igVar instanceof il) {
            Date a = a(igVar);
            if (type == Date.class) {
                return a;
            }
            if (type == Timestamp.class) {
                return new Timestamp(a.getTime());
            }
            if (type == java.sql.Date.class) {
                return new java.sql.Date(a.getTime());
            }
            throw new IllegalArgumentException(getClass() + " cannot deserialize to " + type);
        }
        throw new ik("The date should be a string value");
    }

    private Date a(ig igVar) {
        Date parse;
        synchronized (this.b) {
            try {
                parse = this.b.parse(igVar.b());
            } catch (ParseException e) {
                try {
                    parse = this.a.parse(igVar.b());
                } catch (ParseException e2) {
                    try {
                        parse = this.c.parse(igVar.b());
                    } catch (Throwable e3) {
                        throw new io(igVar.b(), e3);
                    }
                }
            }
        }
        return parse;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(hv.class.getSimpleName());
        stringBuilder.append('(').append(this.b.getClass().getSimpleName()).append(')');
        return stringBuilder.toString();
    }
}
