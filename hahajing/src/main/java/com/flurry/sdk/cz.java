package com.flurry.sdk;

import android.text.TextUtils;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class cz {
    private static final String a = cz.class.getSimpleName();
    private static int b = 500;

    static int a() {
        return b;
    }

    public static void a(int i) {
        b = i;
    }

    static dl a(List<dl> list) {
        dl dlVar = null;
        if (list != null) {
            for (dl dlVar2 : list) {
                dl dlVar22;
                if (dlVar22.b() > a() || dlVar22.a() == null || (((dlVar22.c() == null || !dlVar22.c().equalsIgnoreCase("video/mp4")) && !dlVar22.a().endsWith("mp4")) || (dlVar != null && dlVar.b() >= dlVar22.b()))) {
                    dlVar22 = dlVar;
                }
                dlVar = dlVar22;
            }
        }
        return dlVar;
    }

    static int a(String str) {
        int i = 0;
        if (str == null) {
            return i;
        }
        Matcher matcher = Pattern.compile("(\\d{2}):(\\d{2}):(\\d{2})").matcher(str);
        if (!matcher.find() || matcher.groupCount() != 3) {
            return i;
        }
        try {
            int parseInt = Integer.parseInt(matcher.group(1));
            int parseInt2 = Integer.parseInt(matcher.group(2));
            return Integer.parseInt(matcher.group(3)) + (((parseInt * 60) * 60) + (parseInt2 * 60));
        } catch (NumberFormatException e) {
            return i;
        }
    }

    public static void a(ap apVar, String str, String str2) {
        cy g = apVar.g();
        if (g != null) {
            List<String> a = g.a(de.Close);
            if (a != null) {
                for (String str3 : a) {
                    if (str3 != null) {
                        gd.a(3, a, "Close Tracking URL: " + str3);
                        a(str, str2, str3);
                    }
                }
            }
        }
    }

    public static void b(ap apVar, String str, String str2) {
        cy g = apVar.g();
        if (g != null) {
            List<String> g2 = g.g();
            if (g2 != null) {
                for (String str3 : g2) {
                    if (str3 != null) {
                        gd.a(3, a, "Error Tracking URL: " + str3);
                        a(str, str2, str3);
                    }
                }
            }
        }
    }

    public static void c(ap apVar, String str, String str2) {
        cy g = apVar.g();
        if (g != null) {
            List<String> a = g.a(df.ClickTracking);
            if (a != null) {
                for (String str3 : a) {
                    if (!TextUtils.isEmpty(str3)) {
                        gd.a(3, a, "ClickTracking Tracking URL: " + str3);
                        a(str, str2, str3);
                    }
                }
            }
        }
    }

    public static void d(ap apVar, String str, String str2) {
        cy g = apVar.g();
        if (g != null) {
            List<String> h = g.h();
            if (h != null) {
                for (String str3 : h) {
                    if (str3 != null) {
                        gd.a(3, a, "Impression Tracking URL: " + str3);
                        a(str, str2, str3);
                    }
                }
            }
        }
    }

    public static void e(ap apVar, String str, String str2) {
        cy g = apVar.g();
        if (g != null) {
            List<String> a = g.a(de.Start);
            if (a != null) {
                for (String str3 : a) {
                    if (str3 != null) {
                        gd.a(3, a, "Start Tracking URL: " + str3);
                        a(str, str2, str3);
                    }
                }
            }
        }
    }

    public static void f(ap apVar, String str, String str2) {
        cy g = apVar.g();
        if (g != null) {
            List<String> a = g.a(de.FirstQuartile);
            if (a != null) {
                for (String str3 : a) {
                    if (str3 != null) {
                        gd.a(3, a, "First Quartile Tracking URL: " + str3);
                        a(str, str2, str3);
                    }
                }
            }
        }
    }

    public static void g(ap apVar, String str, String str2) {
        cy g = apVar.g();
        if (g != null) {
            List<String> a = g.a(de.Midpoint);
            if (a != null) {
                for (String str3 : a) {
                    if (str3 != null) {
                        gd.a(3, a, "Midpoint Tracking URL: " + str3);
                        a(str, str2, str3);
                    }
                }
            }
        }
    }

    public static void h(ap apVar, String str, String str2) {
        cy g = apVar.g();
        if (g != null) {
            List<String> a = g.a(de.ThirdQuartile);
            if (a != null) {
                for (String str3 : a) {
                    if (str3 != null) {
                        gd.a(3, a, "Third Quartile Tracking URL: " + str3);
                        a(str, str2, str3);
                    }
                }
            }
        }
    }

    public static void i(ap apVar, String str, String str2) {
        cy g = apVar.g();
        if (g != null) {
            List<String> a = g.a(de.Complete);
            if (a != null) {
                for (String str3 : a) {
                    if (str3 != null) {
                        gd.a(3, a, "Complete Tracking URL: " + str3);
                        a(str, str2, str3);
                    }
                }
            }
        }
    }

    private static void a(String str, String str2, String str3) {
        i.a().i().b(new cd(str, str2, str3, System.currentTimeMillis() + 900000, false));
    }
}
