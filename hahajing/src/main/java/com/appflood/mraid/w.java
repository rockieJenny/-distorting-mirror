package com.appflood.mraid;

public abstract class w {
    public abstract String a();

    public String toString() {
        String a = a();
        return a != null ? a.replaceAll("[^a-zA-Z0-9_,:\\s\\{\\}\\'\\\"]", "") : "";
    }
}
