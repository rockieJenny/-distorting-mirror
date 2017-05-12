package com.flurry.sdk;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class ck implements gx<Bitmap> {
    public /* synthetic */ Object b(InputStream inputStream) throws IOException {
        return a(inputStream);
    }

    public void a(OutputStream outputStream, Bitmap bitmap) throws IOException {
        if (outputStream != null && bitmap != null) {
            throw new UnsupportedOperationException("Serialization for bitmaps is not yet implemented");
        }
    }

    public Bitmap a(InputStream inputStream) throws IOException {
        if (inputStream == null) {
            return null;
        }
        return BitmapFactory.decodeStream(inputStream);
    }
}
