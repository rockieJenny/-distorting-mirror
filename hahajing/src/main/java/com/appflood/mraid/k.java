package com.appflood.mraid;

import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.os.Environment;
import android.widget.Toast;
import com.appflood.b.b;
import com.appflood.b.b.a;
import com.appflood.e.j;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

final class k extends d {
    k(Map<String, String> map, AFBannerWebView aFBannerWebView) {
        super(map, aFBannerWebView);
    }

    static boolean a(Bitmap bitmap, String str) {
        String str2;
        Date date;
        File file;
        File file2 = new File(Environment.getExternalStorageDirectory(), "MraidImage");
        if (!file2.exists()) {
            file2.mkdirs();
        }
        String str3 = "jpg";
        String str4 = "";
        int lastIndexOf = str.lastIndexOf("/");
        if (lastIndexOf >= 0) {
            String substring = str.substring(lastIndexOf + 1);
            if (substring.contains(".")) {
                String[] split = substring.split(".");
                if (split.length > 0) {
                    str4 = split[0];
                    if (!j.g(split[split.length - 1])) {
                        str3 = split[split.length - 1];
                    }
                    str2 = str4;
                    str4 = str3;
                    str3 = str2;
                    j.c(" name " + str3 + " suffix " + str4);
                    date = new java.sql.Date(System.currentTimeMillis());
                    file = new File(file2, str3 + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ").format(date) + "." + str4);
                    file.createNewFile();
                    OutputStream fileOutputStream = new FileOutputStream(file);
                    bitmap.compress(CompressFormat.JPEG, 50, fileOutputStream);
                    fileOutputStream.flush();
                    fileOutputStream.close();
                    return true;
                }
            }
        }
        str2 = str4;
        str4 = str3;
        str3 = str2;
        j.c(" name " + str3 + " suffix " + str4);
        date = new java.sql.Date(System.currentTimeMillis());
        file = new File(file2, str3 + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSSZ").format(date) + "." + str4);
        try {
            file.createNewFile();
            OutputStream fileOutputStream2 = new FileOutputStream(file);
            bitmap.compress(CompressFormat.JPEG, 50, fileOutputStream2);
            fileOutputStream2.flush();
            fileOutputStream2.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        } catch (IOException e2) {
            e2.printStackTrace();
            return false;
        }
    }

    public final void a() {
        final String b = b("uri");
        new Builder(this.b.getContext()).setPositiveButton("Yes", new OnClickListener(this) {
            final /* synthetic */ k b;

            public final void onClick(DialogInterface dialogInterface, int i) {
                b bVar = new b(b, (byte) 0);
                bVar.a(new a(this) {
                    private /* synthetic */ AnonymousClass1 a;

                    {
                        this.a = r1;
                    }

                    public final void a(int i) {
                        Toast.makeText(this.a.b.b.getContext(), "Download failed!", 0).show();
                    }

                    /* JADX WARNING: inconsistent code. */
                    /* Code decompiled incorrectly, please refer to instructions dump. */
                    public final void a(com.appflood.b.b r5) {
                        /*
                        r4 = this;
                        r1 = r5.d();
                        r0 = r4.a;
                        r0 = r0.b;
                        r0 = r0.b;
                        r0 = r0.getContext();
                        r0 = r0.getContentResolver();
                        r2 = "appflood";
                        r3 = "cpm";
                        r2 = android.provider.MediaStore.Images.Media.insertImage(r0, r1, r2, r3);
                        r0 = new java.lang.StringBuilder;
                        r3 = "finish url ";
                        r0.<init>(r3);
                        r0 = r0.append(r2);
                        r0.toString();
                        com.appflood.e.j.a();
                        r0 = "Download complete!";
                        r2 = com.appflood.e.j.g(r2);
                        if (r2 == 0) goto L_0x0047;
                    L_0x0033:
                        r2 = com.appflood.e.c.i;
                        if (r2 == 0) goto L_0x0045;
                    L_0x0037:
                        r2 = r4.a;
                        r2 = r2.b;
                        r2 = r4.a;
                        r2 = r0;
                        r1 = com.appflood.mraid.k.a(r1, r2);
                        if (r1 != 0) goto L_0x0047;
                    L_0x0045:
                        r0 = "Download failed!";
                    L_0x0047:
                        r1 = r4.a;
                        r1 = r1.b;
                        r1 = r1.b;
                        r1 = r1.getContext();
                        r2 = 0;
                        r0 = android.widget.Toast.makeText(r1, r0, r2);
                        r0.show();
                        return;
                        */
                        throw new UnsupportedOperationException("Method not decompiled: com.appflood.mraid.k.1.1.a(com.appflood.b.b):void");
                    }
                });
                bVar.f();
            }
        }).setNegativeButton("Cancel", null).setTitle("Add photo").setMessage("Sure you want to download?").show();
    }
}
