package com.givewaygames.gwgl.utils.gl.delaunay;

public class BoundingBox {
    private double maxx;
    private double maxy;
    private double maxz;
    private double minx;
    private double miny;
    private double minz;

    public BoundingBox() {
        setToNull();
    }

    public BoundingBox(BoundingBox other) {
        if (other.isNull()) {
            setToNull();
        } else {
            init(other.minx, other.maxx, other.miny, other.maxy, other.minz, other.maxz);
        }
    }

    public BoundingBox(double minx, double maxx, double miny, double maxy, double minz, double maxz) {
        init(minx, maxx, miny, maxy, minz, maxz);
    }

    public BoundingBox(Point lowerLeft, Point upperRight) {
        init(lowerLeft.x, upperRight.x, lowerLeft.y, upperRight.y, lowerLeft.z, upperRight.z);
    }

    private void init(double x1, double x2, double y1, double y2, double z1, double z2) {
        if (x1 < x2) {
            this.minx = x1;
            this.maxx = x2;
        } else {
            this.minx = x2;
            this.maxx = x1;
        }
        if (y1 < y2) {
            this.miny = y1;
            this.maxy = y2;
        } else {
            this.miny = y2;
            this.maxy = y1;
        }
        if (z1 < z2) {
            this.minz = z1;
            this.maxz = z2;
            return;
        }
        this.minz = z2;
        this.maxz = z1;
    }

    private void setToNull() {
        this.minx = 0.0d;
        this.maxx = -1.0d;
        this.miny = 0.0d;
        this.maxy = -1.0d;
    }

    public boolean isNull() {
        return this.maxx < this.minx;
    }

    public boolean contains(BoundingBox other) {
        return !isNull() && !other.isNull() && other.minx >= this.minx && other.maxy <= this.maxx && other.miny >= this.miny && other.maxy <= this.maxy;
    }

    public BoundingBox unionWith(BoundingBox other) {
        if (other.isNull()) {
            return new BoundingBox(this);
        }
        if (isNull()) {
            return new BoundingBox(other);
        }
        return new BoundingBox(Math.min(this.minx, other.minx), Math.max(this.maxx, other.maxx), Math.min(this.miny, other.miny), Math.max(this.maxy, other.maxy), Math.min(this.minz, other.minz), Math.max(this.maxz, other.maxz));
    }

    public double minX() {
        return this.minx;
    }

    public double minY() {
        return this.miny;
    }

    public double maxX() {
        return this.maxx;
    }

    public double maxY() {
        return this.maxy;
    }

    public double getWidth() {
        return this.maxx - this.minx;
    }

    public double getHeight() {
        return this.maxy - this.miny;
    }

    public Point getMinPoint() {
        return new Point(this.minx, this.miny, this.minz);
    }

    public Point getMaxPoint() {
        return new Point(this.maxx, this.maxy, this.maxz);
    }
}
