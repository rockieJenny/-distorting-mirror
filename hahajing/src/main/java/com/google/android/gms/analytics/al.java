package com.google.android.gms.analytics;

import android.content.Context;

class al extends n<am> {

    private static class a implements com.google.android.gms.analytics.n.a<am> {
        private final am Cu = new am();

        public void c(String str, int i) {
            if ("ga_sessionTimeout".equals(str)) {
                this.Cu.Cx = i;
            } else {
                ae.W("int configuration name not recognized:  " + str);
            }
        }

        public void e(String str, String str2) {
            this.Cu.CB.put(str, str2);
        }

        public void e(String str, boolean z) {
            int i = 1;
            am amVar;
            if ("ga_autoActivityTracking".equals(str)) {
                amVar = this.Cu;
                if (!z) {
                    i = 0;
                }
                amVar.Cy = i;
            } else if ("ga_anonymizeIp".equals(str)) {
                amVar = this.Cu;
                if (!z) {
                    i = 0;
                }
                amVar.Cz = i;
            } else if ("ga_reportUncaughtExceptions".equals(str)) {
                amVar = this.Cu;
                if (!z) {
                    i = 0;
                }
                amVar.CA = i;
            } else {
                ae.W("bool configuration name not recognized:  " + str);
            }
        }

        public /* synthetic */ m er() {
            return fs();
        }

        public void f(String str, String str2) {
            if ("ga_trackingId".equals(str)) {
                this.Cu.Cv = str2;
            } else if ("ga_sampleFrequency".equals(str)) {
                try {
                    this.Cu.Cw = Double.parseDouble(str2);
                } catch (NumberFormatException e) {
                    ae.T("Error parsing ga_sampleFrequency value: " + str2);
                }
            } else {
                ae.W("string configuration name not recognized:  " + str);
            }
        }

        public am fs() {
            return this.Cu;
        }
    }

    public al(Context context) {
        super(context, new a());
    }
}
