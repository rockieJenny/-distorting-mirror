package com.flurry.sdk;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.flurry.android.impl.ads.protocol.v13.NativeAsset;
import com.flurry.android.impl.ads.protocol.v13.NativeAssetType;
import com.flurry.sdk.gl.a;
import java.io.File;

public class m {
    private static final String a = m.class.getSimpleName();

    public String a(NativeAsset nativeAsset, int i) {
        File a = i.a().l().a(i, nativeAsset.value);
        if (a == null) {
            return nativeAsset.value;
        }
        return "file://" + a.getAbsolutePath();
    }

    public View a(Context context, NativeAsset nativeAsset, int i) {
        View view = null;
        if (!(context == null || nativeAsset == null)) {
            switch (nativeAsset.type) {
                case STRING:
                    view = new TextView(context);
                    break;
                case IMAGE:
                    view = new ImageView(context);
                    break;
            }
            a(nativeAsset, view, i);
        }
        return view;
    }

    public void a(NativeAsset nativeAsset, View view, int i) {
        if (nativeAsset != null && view != null) {
            switch (nativeAsset.type) {
                case STRING:
                    a(nativeAsset, view);
                    return;
                case IMAGE:
                    b(nativeAsset, view, i);
                    return;
                default:
                    return;
            }
        }
    }

    private void a(NativeAsset nativeAsset, View view) {
        if (nativeAsset != null && NativeAssetType.STRING.equals(nativeAsset.type)) {
            if (view == null || !(view instanceof TextView)) {
                gd.e(a, "The view must be an instance of TextView in order to load the asset");
            } else {
                ((TextView) view).setText(nativeAsset.value);
            }
        }
    }

    private void b(NativeAsset nativeAsset, View view, int i) {
        if (nativeAsset != null && !TextUtils.isEmpty(nativeAsset.value) && NativeAssetType.IMAGE.equals(nativeAsset.type)) {
            if (view == null || !(view instanceof ImageView)) {
                gd.e(a, "The view must be an instance of ImageView in order to load the asset");
                return;
            }
            final ImageView imageView = (ImageView) view;
            File a = i.a().l().a(i, nativeAsset.value);
            if (a == null) {
                gd.a(3, a, "Cached asset not available for image:" + nativeAsset.value);
                hr gkVar = new gk();
                gkVar.a(nativeAsset.value);
                gkVar.a(40000);
                gkVar.a(a.kGet);
                gkVar.b(new ck());
                gkVar.a(new gk.a<Void, Bitmap>(this) {
                    final /* synthetic */ m b;

                    public void a(gk<Void, Bitmap> gkVar, final Bitmap bitmap) {
                        gd.a(3, m.a, "Image request -- HTTP status code is:" + gkVar.e());
                        if (gkVar.c()) {
                            fp.a().a(new hq(this) {
                                final /* synthetic */ AnonymousClass1 b;

                                public void safeRun() {
                                    imageView.setImageBitmap(bitmap);
                                }
                            });
                        }
                    }
                });
                fn.a().a((Object) this, gkVar);
                return;
            }
            gd.a(3, a, "Cached asset present for image:" + nativeAsset.value);
            final String absolutePath = a.getAbsolutePath();
            fp.a().a(new hq(this) {
                final /* synthetic */ m c;

                public void safeRun() {
                    imageView.setImageBitmap(BitmapFactory.decodeFile(absolutePath));
                }
            });
        }
    }
}
