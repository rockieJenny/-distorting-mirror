package com.flurry.sdk;

import android.graphics.Point;
import android.util.SparseArray;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public final class cb {
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
        arrayList.add(Integer.valueOf(4));
        arrayList.add(Integer.valueOf(3));
        arrayList.add(Integer.valueOf(1));
        arrayList.add(Integer.valueOf(2));
        return Collections.unmodifiableList(arrayList);
    }

    private static SparseArray<Point> b() {
        SparseArray<Point> sparseArray = new SparseArray();
        sparseArray.put(4, new Point(728, 90));
        sparseArray.put(3, new Point(480, 60));
        sparseArray.put(1, new Point(320, 50));
        sparseArray.put(2, new Point(300, 250));
        return sparseArray;
    }
}
