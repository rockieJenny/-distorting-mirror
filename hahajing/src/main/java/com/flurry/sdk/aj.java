package com.flurry.sdk;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class aj extends ai {
    private final File a;
    private OutputStream b;

    public aj(File file) {
        this.a = file;
    }

    protected OutputStream f() throws IOException {
        if (this.b != null) {
            return this.b;
        }
        if (this.a == null) {
            throw new IOException("No file specified");
        }
        this.b = new FileOutputStream(this.a);
        return this.b;
    }

    protected void g() {
        hp.a(this.b);
        this.b = null;
    }

    protected void h() {
        if (this.a != null) {
            this.a.delete();
        }
    }
}
