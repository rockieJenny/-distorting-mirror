package com.flurry.sdk;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;
import com.flurry.sdk.ai.a;
import java.io.File;

public class k {
    private static final String a = k.class.getSimpleName();
    private final fy<fi> b = new fy<fi>(this) {
        final /* synthetic */ k a;

        {
            this.a = r1;
        }

        public /* synthetic */ void notify(fx fxVar) {
            a((fi) fxVar);
        }

        public void a(fi fiVar) {
            if (fiVar.a) {
                this.a.e();
            }
        }
    };
    private final File c;
    private final File d;
    private String e;
    private int f;
    private ai g;

    public k() {
        fz.a().a("com.flurry.android.sdk.NetworkStateEvent", this.b);
        this.c = fp.a().c().getFileStreamPath(".flurryads.mediaassets");
        this.d = fp.a().c().getFileStreamPath(".flurryads.mediaassets.tmp");
    }

    public boolean a() {
        return this.c.exists();
    }

    public File b() {
        if (a()) {
            return this.c;
        }
        return null;
    }

    public void a(String str) {
        if (!TextUtils.isEmpty(str)) {
            this.e = str;
        }
    }

    public void c() {
        this.f = 0;
        fp.a().b(new hq(this) {
            final /* synthetic */ k a;

            {
                this.a = r1;
            }

            public void safeRun() {
                this.a.e();
            }
        });
    }

    private synchronized void e() {
        if (!TextUtils.isEmpty(this.e)) {
            final SharedPreferences sharedPreferences = fp.a().c().getSharedPreferences("FLURRY_SHARED_PREFERENCES", 0);
            if (this.e.equals(sharedPreferences.getString("flurry_last_media_asset_url", null)) && a()) {
                gd.a(3, a, "Media player assets: download not necessary");
            } else {
                String str;
                if (this.f < 3) {
                    str = this.e + "android.zip";
                } else {
                    str = "http://flurry.cachefly.net/vast/videocontrols/v1/android.zip";
                }
                if (this.g != null) {
                    this.g.e();
                }
                this.d.delete();
                gd.a(3, a, "Media player assets: attempting download from url: " + str);
                this.g = new aj(this.d);
                this.g.a(str);
                this.g.a(30000);
                this.g.a(new a(this) {
                    final /* synthetic */ k c;

                    public void a(ai aiVar) {
                        if (aiVar.a() && this.c.d.exists()) {
                            this.c.c.delete();
                            if (this.c.d.renameTo(this.c.c)) {
                                gd.a(3, k.a, "Media player assets: download successful");
                                Editor edit = sharedPreferences.edit();
                                edit.putString("flurry_last_media_asset_url", str);
                                edit.commit();
                            } else {
                                gd.a(3, k.a, "Media player assets: couldn't rename tmp file (giving up)");
                            }
                        } else {
                            gd.a(3, k.a, "Media player assets: download failed");
                            if (fj.a().c()) {
                                this.c.f = this.c.f + 1;
                            }
                            fp.a().a(new hq(this) {
                                final /* synthetic */ AnonymousClass3 a;

                                {
                                    this.a = r1;
                                }

                                public void safeRun() {
                                    this.a.c.e();
                                }
                            }, 10000);
                        }
                        this.c.g = null;
                    }
                });
                this.g.d();
            }
        }
    }
}
