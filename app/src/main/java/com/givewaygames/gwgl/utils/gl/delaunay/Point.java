package com.givewaygames.gwgl.utils.gl.delaunay;

import java.util.Comparator;

public class Point {
    public static final int BEHINDB = 4;
    public static final int ERROR = 5;
    public static final int INFRONTOFA = 3;
    public static final int LEFT = 1;
    public static final int ONSEGMENT = 0;
    public static final int RIGHT = 2;
    double x;
    double y;
    double z;

    public Point() {
        this(0.0d, 0.0d);
    }

    public Point(double x, double y, double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public Point(double x, double y) {
        this(x, y, 0.0d);
    }

    public Point(Point p) {
        this.x = p.x;
        this.y = p.y;
        this.z = p.z;
    }

    public double x() {
        return this.x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double y() {
        return this.y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double z() {
        return this.z;
    }

    public void setZ(double Z) {
        this.z = this.z;
    }

    double distance2(Point p) {
        return ((p.x - this.x) * (p.x - this.x)) + ((p.y - this.y) * (p.y - this.y));
    }

    double distance2(double px, double py) {
        return ((px - this.x) * (px - this.x)) + ((py - this.y) * (py - this.y));
    }

    boolean isLess(Point p) {
        return this.x < p.x || (this.x == p.x && this.y < p.y);
    }

    boolean isGreater(Point p) {
        return this.x > p.x || (this.x == p.x && this.y > p.y);
    }

    public boolean equals(Point p) {
        return this.x == p.x && this.y == p.y;
    }

    public String toString() {
        return new String(" Pt[" + this.x + "," + this.y + "," + this.z + "]");
    }

    public double distance(Point p) {
        return Math.sqrt(Math.pow(p.x() - this.x, 2.0d) + Math.pow(p.y() - this.y, 2.0d));
    }

    public double distance3D(Point p) {
        return Math.sqrt((Math.pow(p.x() - this.x, 2.0d) + Math.pow(p.y() - this.y, 2.0d)) + Math.pow(p.z() - this.z, 2.0d));
    }

    public String toFile() {
        return "" + this.x + " " + this.y + " " + this.z;
    }

    String toFileXY() {
        return "" + this.x + " " + this.y;
    }

    public int pointLineTest(Point a, Point b) {
        double dx = b.x - a.x;
        double dy = b.y - a.y;
        double res = ((this.x - a.x) * dy) - ((this.y - a.y) * dx);
        if (res < 0.0d) {
            return 1;
        }
        if (res > 0.0d) {
            return 2;
        }
        if (dx > 0.0d) {
            if (this.x < a.x) {
                return 3;
            }
            if (b.x < this.x) {
                return 4;
            }
            return 0;
        } else if (dx < 0.0d) {
            if (this.x > a.x) {
                return 3;
            }
            if (b.x > this.x) {
                return 4;
            }
            return 0;
        } else if (dy > 0.0d) {
            if (this.y < a.y) {
                return 3;
            }
            if (b.y < this.y) {
                return 4;
            }
            return 0;
        } else if (dy >= 0.0d) {
            System.out.println("Error, pointLineTest with a=b");
            return 5;
        } else if (this.y > a.y) {
            return 3;
        } else {
            if (b.y > this.y) {
                return 4;
            }
            return 0;
        }
    }

    boolean areCollinear(Point a, Point b) {
        return ((this.x - a.x) * (b.y - a.y)) - ((this.y - a.y) * (b.x - a.x)) == 0.0d;
    }

    Point circumcenter(Point a, Point b) {
        double u = (((a.x - b.x) * (a.x + b.x)) + ((a.y - b.y) * (a.y + b.y))) / 2.0d;
        double v = (((b.x - this.x) * (b.x + this.x)) + ((b.y - this.y) * (b.y + this.y))) / 2.0d;
        double den = ((a.x - b.x) * (b.y - this.y)) - ((b.x - this.x) * (a.y - b.y));
        if (den == 0.0d) {
            System.out.println("circumcenter, degenerate case");
        }
        return new Point((((b.y - this.y) * u) - ((a.y - b.y) * v)) / den, (((a.x - b.x) * v) - ((b.x - this.x) * u)) / den);
    }

    public static Comparator<Point> getComparator(int flag) {
        return new Compare(flag);
    }

    public static Comparator<Point> getComparator() {
        return new Compare(0);
    }
}
