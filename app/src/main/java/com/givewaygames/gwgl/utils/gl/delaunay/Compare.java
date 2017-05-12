package com.givewaygames.gwgl.utils.gl.delaunay;

import java.util.Comparator;

public class Compare implements Comparator {
    private int _flag;

    public Compare(int i) {
        this._flag = i;
    }

    public int compare(Object o1, Object o2) {
        if (o1 != null && o2 != null && (o1 instanceof Point) && (o2 instanceof Point)) {
            Point d1 = (Point) o1;
            Point d2 = (Point) o2;
            if (this._flag == 0) {
                if (d1.x > d2.x) {
                    return 1;
                }
                if (d1.x < d2.x) {
                    return -1;
                }
                if (d1.y > d2.y) {
                    return 1;
                }
                if (d1.y < d2.y) {
                    return -1;
                }
                return 0;
            } else if (this._flag == 1) {
                if (d1.x > d2.x) {
                    return -1;
                }
                if (d1.x < d2.x) {
                    return 1;
                }
                if (d1.y > d2.y) {
                    return -1;
                }
                if (d1.y < d2.y) {
                    return 1;
                }
                return 0;
            } else if (this._flag == 2) {
                if (d1.y > d2.y) {
                    return 1;
                }
                if (d1.y < d2.y) {
                    return -1;
                }
                if (d1.x > d2.x) {
                    return 1;
                }
                if (d1.x < d2.x) {
                    return -1;
                }
                return 0;
            } else if (this._flag != 3) {
                return 0;
            } else {
                if (d1.y > d2.y) {
                    return -1;
                }
                if (d1.y < d2.y) {
                    return 1;
                }
                if (d1.x > d2.x) {
                    return -1;
                }
                if (d1.x < d2.x) {
                    return 1;
                }
                return 0;
            }
        } else if (o1 == null && o2 == null) {
            return 0;
        } else {
            if (o1 == null && o2 != null) {
                return 1;
            }
            if (o1 == null || o2 != null) {
                return 0;
            }
            return -1;
        }
    }

    public boolean equals(Object ob) {
        return false;
    }
}
