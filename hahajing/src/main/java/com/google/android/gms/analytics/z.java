package com.google.android.gms.analytics;

import android.content.Context;

class z extends n<aa> {

    private static class a implements com.google.android.gms.analytics.n.a<aa> {
        private final aa Bi = new aa();

        public void c(String str, int i) {
            if ("ga_dispatchPeriod".equals(str)) {
                this.Bi.Bk = i;
            } else {
                ae.W("int configuration name not recognized:  " + str);
            }
        }

        public void e(String str, String str2) {
        }

        public void e(String str, boolean z) {
            if ("ga_dryRun".equals(str)) {
                this.Bi.Bl = z ? 1 : 0;
                return;
            }
            ae.W("bool configuration name not recognized:  " + str);
        }

        public aa eN() {
            return this.Bi;
        }

        public /* synthetic */ m er() {
            return eN();
        }

        public void f(String str, String str2) {
            if ("ga_appName".equals(str)) {
                this.Bi.ya = str2;
            } else if ("ga_appVersion".equals(str)) {
                this.Bi.yb = str2;
            } else if ("ga_logLevel".equals(str)) {
                this.Bi.Bj = str2;
            } else {
                ae.W("string configuration name not recognized:  " + str);
            }
        }
    }

    public z(Context context) {
        super(context, new a());
    }
}
