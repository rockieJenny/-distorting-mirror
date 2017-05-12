package com.flurry.sdk;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AbsoluteLayout;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import com.flurry.android.AdCreative;
import com.flurry.android.ICustomAdNetworkHandler;
import com.flurry.android.impl.ads.protocol.v13.AdFrame;
import com.flurry.android.impl.ads.protocol.v13.AdSpaceLayout;
import com.flurry.android.impl.ads.protocol.v13.AdUnit;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class eg {
    private static final String a = eg.class.getSimpleName();
    private static final Map<Class<? extends ViewGroup>, c> b = b();

    static abstract class c {
        public abstract LayoutParams a(AdSpaceLayout adSpaceLayout);

        private c() {
        }

        public int b(AdSpaceLayout adSpaceLayout) {
            return h(adSpaceLayout) ? d(adSpaceLayout) : f(adSpaceLayout);
        }

        public int c(AdSpaceLayout adSpaceLayout) {
            return i(adSpaceLayout) ? e(adSpaceLayout) : g(adSpaceLayout);
        }

        public int d(AdSpaceLayout adSpaceLayout) {
            return hn.b(adSpaceLayout.adWidth);
        }

        public int e(AdSpaceLayout adSpaceLayout) {
            return hn.b(adSpaceLayout.adHeight);
        }

        public int f(AdSpaceLayout adSpaceLayout) {
            return -1;
        }

        public int g(AdSpaceLayout adSpaceLayout) {
            return -2;
        }

        private static boolean h(AdSpaceLayout adSpaceLayout) {
            return adSpaceLayout.adWidth != 0;
        }

        private static boolean i(AdSpaceLayout adSpaceLayout) {
            return adSpaceLayout.adHeight != 0;
        }
    }

    static final class a extends c {
        a() {
            super();
        }

        public LayoutParams a(AdSpaceLayout adSpaceLayout) {
            gd.a(5, eg.a, "AbsoluteLayout is deprecated, please consider to use FrameLayout or RelativeLayout for banner ad container view");
            return new AbsoluteLayout.LayoutParams(b(adSpaceLayout), c(adSpaceLayout), 0, 0);
        }
    }

    static final class b extends c {
        b() {
            super();
        }

        public LayoutParams a(AdSpaceLayout adSpaceLayout) {
            return new FrameLayout.LayoutParams(b(adSpaceLayout), c(adSpaceLayout), 17);
        }
    }

    static final class d extends c {
        d() {
            super();
        }

        public LayoutParams a(AdSpaceLayout adSpaceLayout) {
            return new LinearLayout.LayoutParams(b(adSpaceLayout), c(adSpaceLayout));
        }
    }

    static final class e extends c {
        private static final Map<String, Integer> a = a();

        e() {
            super();
        }

        public int g(AdSpaceLayout adSpaceLayout) {
            return -1;
        }

        public LayoutParams a(AdSpaceLayout adSpaceLayout) {
            LayoutParams layoutParams = new RelativeLayout.LayoutParams(b(adSpaceLayout), c(adSpaceLayout));
            String[] split = adSpaceLayout.alignment.split("-");
            if (split.length == 2) {
                Integer a = a(split[0]);
                if (a != null) {
                    layoutParams.addRule(a.intValue());
                }
                Integer a2 = a(split[1]);
                if (a2 != null) {
                    layoutParams.addRule(a2.intValue());
                }
            }
            return layoutParams;
        }

        private static Map<String, Integer> a() {
            Map hashMap = new HashMap();
            hashMap.put("b", Integer.valueOf(12));
            hashMap.put("t", Integer.valueOf(10));
            hashMap.put("m", Integer.valueOf(15));
            hashMap.put("c", Integer.valueOf(14));
            hashMap.put("l", Integer.valueOf(9));
            hashMap.put("r", Integer.valueOf(11));
            return Collections.unmodifiableMap(hashMap);
        }

        private static Integer a(String str) {
            return (Integer) a.get(str);
        }
    }

    public static void a(Context context, s sVar) {
        if (context != null && sVar != null) {
            ec b;
            ap k = sVar.k();
            AdUnit a = k.a();
            AdFrame adFrame = (AdFrame) a.adFrames.get(0);
            int i = adFrame.binding;
            String str = adFrame.content;
            AdCreative a2 = cr.a(adFrame.adSpaceLayout);
            ICustomAdNetworkHandler c = j.a().c();
            ef p = i.a().p();
            if (i != 4 || c == null) {
                b = p.b(context, sVar);
            } else {
                b = c.getAdFromNetwork(context, a2, str);
            }
            if (b == null) {
                gd.e(a, "Failed to create view for ad network: " + str + ", network is unsupported on Android, or no ICustomAdNetworkHandler was registered or it failed to return a view.");
                Map hashMap = new HashMap();
                hashMap.put("errorCode", Integer.toString(av.kPrepareFailed.a()));
                if (i == 4) {
                    hashMap.put("binding_3rd_party", Integer.toString(4));
                }
                co.a(aw.EV_RENDER_FAILED, hashMap, context, sVar, k, 1);
                return;
            }
            a(sVar, b, a);
        }
    }

    private static void a(s sVar, ec ecVar, AdUnit adUnit) {
        if (sVar != null && ecVar != null && adUnit != null) {
            View view;
            ViewGroup f = sVar.f();
            View s = sVar.s();
            if (s == null) {
                s = new RelativeLayout(sVar.e());
                sVar.a(s);
                view = s;
            } else {
                view = s;
            }
            ViewGroup viewGroup = (ViewGroup) view.getParent();
            if (viewGroup != null) {
                viewGroup.removeView(view);
            }
            view.removeAllViews();
            viewGroup = (ViewGroup) ecVar.getParent();
            if (viewGroup != null) {
                viewGroup.removeView(ecVar);
            }
            view.addView(ecVar, new RelativeLayout.LayoutParams(-1, -1));
            ecVar.initLayout();
            LayoutParams b = b(f, adUnit);
            if (b != null) {
                view.setLayoutParams(b);
                gd.a(3, a, "banner ad holder layout params = " + b.getClass().getName() + " {width = " + b.width + ", height = " + b.height + "} for banner ad with adSpaceName = " + sVar.g());
            }
            f.setBackgroundColor(369098752);
            f.addView(view, a(f, adUnit));
        }
    }

    private static int a(ViewGroup viewGroup, AdUnit adUnit) {
        int childCount = viewGroup.getChildCount();
        if (adUnit == null || adUnit.adFrames.size() < 1) {
            return childCount;
        }
        AdSpaceLayout adSpaceLayout = ((AdFrame) adUnit.adFrames.get(0)).adSpaceLayout;
        if (adSpaceLayout != null) {
            String[] split = adSpaceLayout.alignment.split("-");
            if (split.length == 2 && "t".equals(split[0])) {
                return 0;
            }
        }
        return childCount;
    }

    private static LayoutParams b(ViewGroup viewGroup, AdUnit adUnit) {
        if (adUnit == null || adUnit.adFrames.size() < 1) {
            return null;
        }
        AdSpaceLayout adSpaceLayout = ((AdFrame) adUnit.adFrames.get(0)).adSpaceLayout;
        if (adSpaceLayout == null) {
            return null;
        }
        c a = a(viewGroup);
        if (a == null) {
            gd.a(5, a, "Ad space layout and alignment from the server is being ignored for ViewGroup subclass " + viewGroup.getClass().getSimpleName());
            return null;
        }
        LayoutParams a2 = a.a(adSpaceLayout);
        if (a2 != null) {
            return a2;
        }
        gd.a(6, a, "Ad space layout and alignment from the server is being ignored for ViewGroup subclass " + viewGroup.getClass().getSimpleName());
        return a2;
    }

    private static c a(ViewGroup viewGroup) {
        return (c) b.get(viewGroup.getClass());
    }

    private static Map<Class<? extends ViewGroup>, c> b() {
        Map hashMap = new HashMap();
        hashMap.put(LinearLayout.class, new d());
        hashMap.put(AbsoluteLayout.class, new a());
        hashMap.put(FrameLayout.class, new b());
        hashMap.put(RelativeLayout.class, new e());
        return Collections.unmodifiableMap(hashMap);
    }
}
