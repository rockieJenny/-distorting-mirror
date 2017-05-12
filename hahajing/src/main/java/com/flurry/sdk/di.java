package com.flurry.sdk;

import java.util.List;

public class di {
    private db a;
    private String b;
    private dh c;
    private List<String> d;
    private List<String> e;
    private List<String> f;
    private List<dj> g;

    public static class a {
        private di a = new di();

        public a a(db dbVar) {
            this.a.a = dbVar;
            return this;
        }

        public a a(String str) {
            this.a.b = str;
            return this;
        }

        public a a(dh dhVar) {
            this.a.c = dhVar;
            return this;
        }

        public a a(List<String> list) {
            this.a.d = list;
            return this;
        }

        public a b(List<String> list) {
            this.a.e = list;
            return this;
        }

        public a c(List<String> list) {
            this.a.f = list;
            return this;
        }

        public a d(List<dj> list) {
            this.a.g = list;
            return this;
        }

        public di a() {
            return this.a;
        }
    }

    private di() {
    }

    public db a() {
        return this.a;
    }

    public String b() {
        return this.b;
    }

    public dh c() {
        return this.c;
    }

    public List<String> d() {
        return this.d;
    }

    public List<String> e() {
        return this.e;
    }

    public List<String> f() {
        return this.f;
    }

    public List<dj> g() {
        return this.g;
    }
}
