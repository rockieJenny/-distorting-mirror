package com.givewaygames.gwgl.utils.gl.delaunay;

public class Triangle {
    public static int _c2 = 0;
    public static int _counter = 0;
    boolean _mark = false;
    int _mc = 0;
    Point a;
    Triangle abnext;
    Point b;
    Triangle bcnext;
    Point c;
    Triangle canext;
    Circle circum;
    boolean halfplane = false;

    public Triangle(Point A, Point B, Point C) {
        this.a = A;
        int res = C.pointLineTest(A, B);
        if (res <= 1 || res == 3 || res == 4) {
            this.b = B;
            this.c = C;
        } else {
            System.out.println("Warning, ajTriangle(A,B,C) expects points in counterclockwise order.");
            System.out.println("" + A + B + C);
            this.b = C;
            this.c = B;
        }
        circumcircle();
    }

    public Triangle(Point A, Point B) {
        this.a = A;
        this.b = B;
        this.halfplane = true;
    }

    public boolean isHalfplane() {
        return this.halfplane;
    }

    public Point p1() {
        return this.a;
    }

    public Point p2() {
        return this.b;
    }

    public Point p3() {
        return this.c;
    }

    public Triangle next_12() {
        return this.abnext;
    }

    public Triangle next_23() {
        return this.bcnext;
    }

    public Triangle next_31() {
        return this.canext;
    }

    public BoundingBox getBoundingBox() {
        return new BoundingBox(new Point(Math.min(this.a.x(), Math.min(this.b.x(), this.c.x())), Math.min(this.a.y(), Math.min(this.b.y(), this.c.y()))), new Point(Math.max(this.a.x(), Math.max(this.b.x(), this.c.x())), Math.max(this.a.y(), Math.max(this.b.y(), this.c.y()))));
    }

    void switchneighbors(Triangle Old, Triangle New) {
        if (this.abnext == Old) {
            this.abnext = New;
        } else if (this.bcnext == Old) {
            this.bcnext = New;
        } else if (this.canext == Old) {
            this.canext = New;
        } else {
            System.out.println("Error, switchneighbors can't find Old.");
        }
    }

    Triangle neighbor(Point p) {
        if (this.a == p) {
            return this.canext;
        }
        if (this.b == p) {
            return this.abnext;
        }
        if (this.c == p) {
            return this.bcnext;
        }
        System.out.println("Error, neighbors can't find p: " + p);
        return null;
    }

    Triangle nextNeighbor(Point p, Triangle prevTriangle) {
        Triangle neighbor = null;
        if (this.a.equals(p)) {
            neighbor = this.canext;
        }
        if (this.b.equals(p)) {
            neighbor = this.abnext;
        }
        if (this.c.equals(p)) {
            neighbor = this.bcnext;
        }
        if (!neighbor.equals(prevTriangle) && !neighbor.isHalfplane()) {
            return neighbor;
        }
        if (this.a.equals(p)) {
            neighbor = this.abnext;
        }
        if (this.b.equals(p)) {
            neighbor = this.bcnext;
        }
        if (this.c.equals(p)) {
            return this.canext;
        }
        return neighbor;
    }

    Circle circumcircle() {
        double u = (((this.a.x - this.b.x) * (this.a.x + this.b.x)) + ((this.a.y - this.b.y) * (this.a.y + this.b.y))) / 2.0d;
        double v = (((this.b.x - this.c.x) * (this.b.x + this.c.x)) + ((this.b.y - this.c.y) * (this.b.y + this.c.y))) / 2.0d;
        double den = ((this.a.x - this.b.x) * (this.b.y - this.c.y)) - ((this.b.x - this.c.x) * (this.a.y - this.b.y));
        if (den == 0.0d) {
            this.circum = new Circle(this.a, Double.POSITIVE_INFINITY);
        } else {
            Point cen = new Point((((this.b.y - this.c.y) * u) - ((this.a.y - this.b.y) * v)) / den, (((this.a.x - this.b.x) * v) - ((this.b.x - this.c.x) * u)) / den);
            this.circum = new Circle(cen, cen.distance2(this.a));
        }
        return this.circum;
    }

    boolean circumcircle_contains(Point p) {
        return this.circum.Radius() > this.circum.Center().distance2(p);
    }

    public String toString() {
        String res = "" + this.a.toString() + this.b.toString();
        if (this.halfplane) {
            return res;
        }
        return res + this.c.toString();
    }

    public boolean contains(Point p) {
        int i;
        boolean ans = false;
        boolean z = this.halfplane;
        if (p == null) {
            i = 1;
        } else {
            i = 0;
        }
        if ((i | z) != 0) {
            return false;
        }
        if (isCorner(p)) {
            return true;
        }
        int a12 = p.pointLineTest(this.a, this.b);
        int a23 = p.pointLineTest(this.b, this.c);
        int a31 = p.pointLineTest(this.c, this.a);
        if ((a12 == 1 && a23 == 1 && a31 == 1) || ((a12 == 2 && a23 == 2 && a31 == 2) || a12 == 0 || a23 == 0 || a31 == 0)) {
            ans = true;
        }
        return ans;
    }

    public boolean contains_BoundaryIsOutside(Point p) {
        int i;
        boolean ans = false;
        boolean z = this.halfplane;
        if (p == null) {
            i = 1;
        } else {
            i = 0;
        }
        if ((i | z) != 0) {
            return false;
        }
        if (isCorner(p)) {
            return true;
        }
        int a12 = p.pointLineTest(this.a, this.b);
        int a23 = p.pointLineTest(this.b, this.c);
        int a31 = p.pointLineTest(this.c, this.a);
        if ((a12 == 1 && a23 == 1 && a31 == 1) || (a12 == 2 && a23 == 2 && a31 == 2)) {
            ans = true;
        }
        return ans;
    }

    public boolean isCorner(Point p) {
        int i = 1;
        int i2 = ((p.x == this.a.x ? 1 : 0) & (p.y == this.a.y ? 1 : 0)) | ((p.x == this.b.x ? 1 : 0) & (p.y == this.b.y ? 1 : 0));
        int i3 = p.x == this.c.x ? 1 : 0;
        if (p.y != this.c.y) {
            i = 0;
        }
        return (i3 & i) | i2;
    }

    public boolean fallInsideCircumcircle(Point[] arrayPoints) {
        boolean isInside = false;
        Point p1 = p1();
        Point p2 = p2();
        Point p3 = p3();
        int i = 0;
        while (!isInside && i < arrayPoints.length) {
            Point p = arrayPoints[i];
            if (!(p.equals(p1) || p.equals(p2) || p.equals(p3))) {
                isInside = circumcircle_contains(p);
            }
            i++;
        }
        return isInside;
    }

    public double z_value(Point q) {
        if (q == null || this.halfplane) {
            throw new RuntimeException("*** ERR wrong parameters, can't approximate the z value ..***: " + q);
        }
        int i;
        int i2;
        if (q.x == this.a.x) {
            i = 1;
        } else {
            i = 0;
        }
        if (q.y == this.a.y) {
            i2 = 1;
        } else {
            i2 = 0;
        }
        if ((i & i2) != 0) {
            return this.a.z;
        }
        if (((q.x == this.b.x ? 1 : 0) & (q.y == this.b.y ? 1 : 0)) != 0) {
            return this.b.z;
        }
        if (((q.x == this.c.x ? 1 : 0) & (q.y == this.c.y ? 1 : 0)) != 0) {
            return this.c.z;
        }
        double X;
        double Y;
        double r;
        double x0 = q.x;
        double x1 = this.a.x;
        double x2 = this.b.x;
        double x3 = this.c.x;
        double y0 = q.y;
        double y1 = this.a.y;
        double y2 = this.b.y;
        double y3 = this.c.y;
        double m01 = 0.0d;
        double k01 = 0.0d;
        double m23 = 0.0d;
        double k23 = 0.0d;
        int flag01 = 0;
        if (x0 != x1) {
            m01 = (y0 - y1) / (x0 - x1);
            k01 = y0 - (m01 * x0);
            if (m01 == 0.0d) {
                flag01 = 1;
            }
        } else {
            flag01 = 2;
        }
        int flag23 = 0;
        if (x2 != x3) {
            m23 = (y2 - y3) / (x2 - x3);
            k23 = y2 - (m23 * x2);
            if (m23 == 0.0d) {
                flag23 = 1;
            }
        } else {
            flag23 = 2;
        }
        if (flag01 == 2) {
            X = x0;
            Y = (m23 * X) + k23;
        } else if (flag23 == 2) {
            X = x2;
            Y = (m01 * X) + k01;
        } else {
            X = (k23 - k01) / (m01 - m23);
            Y = (m01 * X) + k01;
        }
        if (flag23 == 2) {
            r = (y2 - Y) / (y2 - y3);
        } else {
            r = (x2 - X) / (x2 - x3);
        }
        double Z = this.b.z + ((this.c.z - this.b.z) * r);
        if (flag01 == 2) {
            r = (y1 - y0) / (y1 - Y);
        } else {
            r = (x1 - x0) / (x1 - X);
        }
        return this.a.z + ((Z - this.a.z) * r);
    }

    public double z(double x, double y) {
        return z_value(new Point(x, y));
    }

    public Point z(Point q) {
        return new Point(q.x, q.y, z_value(q));
    }
}
