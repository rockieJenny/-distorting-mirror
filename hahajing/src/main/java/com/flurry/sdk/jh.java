package com.flurry.sdk;

import java.io.IOException;
import java.io.Writer;

public final class jh {

    static final class a extends Writer {
        private final Appendable a;
        private final a b;

        static class a implements CharSequence {
            char[] a;

            a() {
            }

            public int length() {
                return this.a.length;
            }

            public char charAt(int i) {
                return this.a[i];
            }

            public CharSequence subSequence(int i, int i2) {
                return new String(this.a, i, i2 - i);
            }
        }

        private a(Appendable appendable) {
            this.b = new a();
            this.a = appendable;
        }

        public void write(char[] cArr, int i, int i2) throws IOException {
            this.b.a = cArr;
            this.a.append(this.b, i, i + i2);
        }

        public void write(int i) throws IOException {
            this.a.append((char) i);
        }

        public void flush() {
        }

        public void close() {
        }
    }

    public static ig a(jx jxVar) throws ik {
        Object obj = 1;
        try {
            jxVar.f();
            obj = null;
            return (ig) jv.P.b(jxVar);
        } catch (Throwable e) {
            if (obj != null) {
                return ii.a;
            }
            throw new io(e);
        } catch (Throwable e2) {
            throw new io(e2);
        } catch (Throwable e22) {
            throw new ih(e22);
        } catch (Throwable e222) {
            throw new io(e222);
        }
    }

    public static void a(ig igVar, jz jzVar) throws IOException {
        jv.P.a(jzVar, igVar);
    }

    public static Writer a(Appendable appendable) {
        return appendable instanceof Writer ? (Writer) appendable : new a(appendable);
    }
}
