package com.jirbo.adcolony;

class l {
    static l a = new l(0, false);
    static l b = new l(1, false);
    static l c = new l(2, true);
    static l d = new l(3, true);
    int e;
    boolean f;
    StringBuilder g = new StringBuilder();

    l(int i, boolean z) {
        this.e = i;
        this.f = z;
    }

    synchronized l a(char c) {
        l lVar;
        if (this.f) {
            this.g.append(c);
            if (c == '\n') {
                a.a(this.e, this.g.toString());
                this.g.setLength(0);
            }
            lVar = this;
        } else {
            lVar = this;
        }
        return lVar;
    }

    synchronized l a(String str) {
        l lVar;
        if (this.f) {
            if (str == null) {
                this.g.append("null");
            } else {
                int length = str.length();
                for (int i = 0; i < length; i++) {
                    a(str.charAt(i));
                }
            }
            lVar = this;
        } else {
            lVar = this;
        }
        return lVar;
    }

    synchronized l a(Object obj) {
        if (this.f) {
            if (obj == null) {
                a("null");
            } else {
                a(obj.toString());
            }
        }
        return this;
    }

    synchronized l a(double d) {
        l lVar;
        if (this.f) {
            ab.a(d, 2, this.g);
            lVar = this;
        } else {
            lVar = this;
        }
        return lVar;
    }

    synchronized l a(int i) {
        l lVar;
        if (this.f) {
            this.g.append(i);
            lVar = this;
        } else {
            lVar = this;
        }
        return lVar;
    }

    synchronized l a(boolean z) {
        l lVar;
        if (this.f) {
            this.g.append(z);
            lVar = this;
        } else {
            lVar = this;
        }
        return lVar;
    }

    synchronized l b(Object obj) {
        a(obj);
        return a('\n');
    }

    synchronized l b(double d) {
        a(d);
        return a('\n');
    }

    synchronized l b(int i) {
        a(i);
        return a('\n');
    }

    synchronized l b(boolean z) {
        a(z);
        return a('\n');
    }

    synchronized l a() {
        return a('\n');
    }

    boolean b(String str) {
        a(str + '\n');
        return false;
    }

    int c(String str) {
        a(str + '\n');
        return 0;
    }
}
