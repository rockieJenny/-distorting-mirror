package com.givewaygames.gwgl.utils.gl.delaunay;

public class Circle {
    private Point c;
    private double r;

    public Circle(Point c, double r) {
        this.c = c;
        this.r = r;
    }

    public Circle(Circle circ) {
        this.c = circ.c;
        this.r = circ.r;
    }

    public String toString() {
        return new String(" Circle[" + this.c.toString() + "|" + this.r + "|" + ((int) Math.round(Math.sqrt(this.r))) + "]");
    }

    public Point Center() {
        return this.c;
    }

    public double Radius() {
        return this.r;
    }
}
