package com.flurry.sdk;

import android.content.Context;
import android.os.Build.VERSION;
import android.os.Environment;
import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public final class ho {
    private static String a = ho.class.getSimpleName();

    public static File a(boolean z) {
        File file = null;
        Context c = fp.a().c();
        if (z && "mounted".equals(Environment.getExternalStorageState()) && (VERSION.SDK_INT >= 19 || c.checkCallingOrSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") == 0)) {
            file = c.getExternalFilesDir(null);
        }
        if (file == null) {
            return c.getFilesDir();
        }
        return file;
    }

    public static File b(boolean z) {
        Context c = fp.a().c();
        File file = null;
        if (z && "mounted".equals(Environment.getExternalStorageState()) && (VERSION.SDK_INT >= 19 || c.checkCallingOrSelfPermission("android.permission.WRITE_EXTERNAL_STORAGE") == 0)) {
            file = c.getExternalCacheDir();
        }
        if (file == null) {
            return c.getCacheDir();
        }
        return file;
    }

    public static boolean a(File file) {
        if (file == null || file.getAbsoluteFile() == null) {
            return false;
        }
        File parentFile = file.getParentFile();
        if (parentFile == null) {
            return true;
        }
        if (parentFile.mkdirs() || parentFile.isDirectory()) {
            return true;
        }
        gd.a(6, a, "Unable to create persistent dir: " + parentFile);
        return false;
    }

    public static boolean b(File file) {
        if (file != null && file.isDirectory()) {
            for (String file2 : file.list()) {
                if (!b(new File(file, file2))) {
                    return false;
                }
            }
        }
        return file.delete();
    }

    @Deprecated
    public static String c(File file) {
        StringBuilder stringBuilder;
        Throwable th;
        Throwable th2;
        if (file == null || !file.exists()) {
            gd.a(4, a, "Persistent file doesn't exist.");
            return null;
        }
        gd.a(4, a, "Loading persistent data: " + file.getAbsolutePath());
        Closeable fileInputStream;
        try {
            fileInputStream = new FileInputStream(file);
            try {
                stringBuilder = new StringBuilder();
                byte[] bArr = new byte[1024];
                while (true) {
                    int read = fileInputStream.read(bArr);
                    if (read <= 0) {
                        break;
                    }
                    stringBuilder.append(new String(bArr, 0, read));
                }
                hp.a(fileInputStream);
            } catch (Throwable th3) {
                th = th3;
                try {
                    gd.a(6, a, "Error when loading persistent file", th);
                    hp.a(fileInputStream);
                    stringBuilder = null;
                    if (stringBuilder != null) {
                        return stringBuilder.toString();
                    }
                    return null;
                } catch (Throwable th4) {
                    th2 = th4;
                    hp.a(fileInputStream);
                    throw th2;
                }
            }
        } catch (Throwable th5) {
            fileInputStream = null;
            th2 = th5;
            hp.a(fileInputStream);
            throw th2;
        }
        if (stringBuilder != null) {
            return stringBuilder.toString();
        }
        return null;
    }

    @Deprecated
    public static void a(File file, String str) {
        Closeable fileOutputStream;
        Throwable th;
        if (file == null) {
            gd.a(4, a, "No persistent file specified.");
        } else if (str == null) {
            gd.a(4, a, "No data specified; deleting persistent file: " + file.getAbsolutePath());
            file.delete();
        } else {
            gd.a(4, a, "Writing persistent data: " + file.getAbsolutePath());
            try {
                fileOutputStream = new FileOutputStream(file);
                try {
                    fileOutputStream.write(str.getBytes());
                    hp.a(fileOutputStream);
                } catch (Throwable th2) {
                    th = th2;
                    try {
                        gd.a(6, a, "Error writing persistent file", th);
                        hp.a(fileOutputStream);
                    } catch (Throwable th3) {
                        th = th3;
                        hp.a(fileOutputStream);
                        throw th;
                    }
                }
            } catch (Throwable th4) {
                th = th4;
                fileOutputStream = null;
                hp.a(fileOutputStream);
                throw th;
            }
        }
    }
}
