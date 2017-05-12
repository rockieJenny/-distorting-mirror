package com.flurry.sdk;

import android.graphics.Point;
import android.util.SparseArray;
import io.fabric.sdk.android.services.settings.SettingsJsonConstants;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class bx {
    private static final List<Integer> a = a();
    private static final SparseArray<Point> b = b();

    public static int a(Point point) {
        if (point == null) {
            return -1;
        }
        int intValue;
        for (Integer num : a) {
            Point a = a(num.intValue());
            if (a != null && point.x >= a.x && point.y >= a.y) {
                intValue = num.intValue();
                break;
            }
        }
        intValue = -1;
        return intValue;
    }

    public static Point a(int i) {
        return (Point) b.get(i);
    }

    private static List<Integer> a() {
        List arrayList = new ArrayList();
        arrayList.add(Integer.valueOf(11));
        arrayList.add(Integer.valueOf(12));
        arrayList.add(Integer.valueOf(15));
        arrayList.add(Integer.valueOf(10));
        arrayList.add(Integer.valueOf(13));
        return Collections.unmodifiableList(arrayList);
    }

    private static SparseArray<Point> b() {
        SparseArray<Point> sparseArray = new SparseArray();
        sparseArray.put(11, new Point(728, 90));
        sparseArray.put(12, new Point(468, 60));
        sparseArray.put(15, new Point(320, 50));
        sparseArray.put(10, new Point(300, 250));
        sparseArray.put(13, new Point(120, SettingsJsonConstants.ANALYTICS_FLUSH_INTERVAL_SECS_DEFAULT));
        return sparseArray;
    }
}
