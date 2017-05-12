package com.givewaygames.gwgl.utils.gl.delaunay;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.TreeSet;
import java.util.Vector;

public class DelaunayTriangulation {
    private Point _bb_max;
    private Point _bb_min;
    private int _modCount;
    private int _modCount2;
    private Vector<Triangle> _triangles;
    private Set<Point> _vertices;
    private Vector<Triangle> addedTriangles;
    private boolean allCollinear;
    private Triangle currT;
    private Vector<Triangle> deletedTriangles;
    private Point firstP;
    private Triangle firstT;
    private GridIndex gridIndex;
    private Point lastP;
    private Triangle lastT;
    private int nPoints;
    private Triangle startTriangle;
    public Triangle startTriangleHull;

    public DelaunayTriangulation() {
        this(new Point[0]);
    }

    public DelaunayTriangulation(Point[] ps) {
        this.nPoints = 0;
        this._modCount = 0;
        this._modCount2 = 0;
        this.gridIndex = null;
        this._modCount = 0;
        this._modCount2 = 0;
        this._bb_min = null;
        this._bb_max = null;
        this._vertices = new TreeSet(Point.getComparator());
        this._triangles = new Vector();
        this.deletedTriangles = null;
        this.addedTriangles = new Vector();
        this.allCollinear = true;
        int i = 0;
        while (ps != null && i < ps.length && ps[i] != null) {
            insertPoint(ps[i]);
            i++;
        }
    }

    public DelaunayTriangulation(String file) throws Exception {
        this(read_file(file));
    }

    public int size() {
        if (this._vertices == null) {
            return 0;
        }
        return this._vertices.size();
    }

    public int trianglesSize() {
        initTriangles();
        return this._triangles.size();
    }

    public int getModeCounter() {
        return this._modCount;
    }

    public void insertPoint(Point p) {
        if (!this._vertices.contains(p)) {
            this._modCount++;
            updateBoundingBox(p);
            this._vertices.add(p);
            Triangle t = insertPointSimple(p);
            if (t != null) {
                Triangle tt = t;
                this.currT = t;
                do {
                    flip(tt, this._modCount);
                    tt = tt.canext;
                    if (tt == t) {
                        break;
                    }
                } while (!tt.halfplane);
                if (this.gridIndex != null) {
                    this.gridIndex.updateIndex(getLastUpdatedTriangles());
                }
            }
        }
    }

    public void deletePoint(Point pointToDelete) {
        Vector<Point> pointsVec = findConnectedVertices(pointToDelete, true);
        if (pointsVec != null) {
            Iterator i$;
            while (pointsVec.size() >= 3) {
                Triangle triangle = findTriangle(pointsVec, pointToDelete);
                this.addedTriangles.add(triangle);
                Point p = findDiagonal(triangle, pointToDelete);
                i$ = pointsVec.iterator();
                while (i$.hasNext()) {
                    Point tmpP = (Point) i$.next();
                    if (tmpP.equals(p)) {
                        pointsVec.removeElement(tmpP);
                        break;
                    }
                }
            }
            deleteUpdate(pointToDelete);
            i$ = this.deletedTriangles.iterator();
            while (i$.hasNext()) {
                if (((Triangle) i$.next()) == this.startTriangle) {
                    this.startTriangle = (Triangle) this.addedTriangles.elementAt(0);
                    break;
                }
            }
            this._triangles.removeAll(this.deletedTriangles);
            this._triangles.addAll(this.addedTriangles);
            this._vertices.remove(pointToDelete);
            this.nPoints = (this.nPoints + this.addedTriangles.size()) - this.deletedTriangles.size();
            this.addedTriangles.removeAllElements();
            this.deletedTriangles.removeAllElements();
        }
    }

    public Point findClosePoint(Point pointToDelete) {
        Triangle triangle = find(pointToDelete);
        Point p1 = triangle.p1();
        Point p2 = triangle.p2();
        double d1 = p1.distance(pointToDelete);
        double d2 = p2.distance(pointToDelete);
        if (triangle.isHalfplane()) {
            return d1 <= d2 ? p1 : p2;
        } else {
            Point p3 = triangle.p3();
            double d3 = p3.distance(pointToDelete);
            if (d1 <= d2 && d1 <= d3) {
                return p1;
            }
            if (d2 > d1 || d2 > d3) {
                return p3;
            }
            return p2;
        }
    }

    private void deleteUpdate(Point pointToDelete) {
        Iterator it = this.addedTriangles.iterator();
        while (it.hasNext()) {
            Triangle addedTriangle1 = (Triangle) it.next();
            Iterator i$ = this.deletedTriangles.iterator();
            while (i$.hasNext()) {
                Triangle deletedTriangle = (Triangle) i$.next();
                if (shareSegment(addedTriangle1, deletedTriangle)) {
                    updateNeighbor(addedTriangle1, deletedTriangle, pointToDelete);
                }
            }
        }
        it = this.addedTriangles.iterator();
        while (it.hasNext()) {
            addedTriangle1 = (Triangle) it.next();
            i$ = this.addedTriangles.iterator();
            while (i$.hasNext()) {
                Triangle addedTriangle2 = (Triangle) i$.next();
                if (addedTriangle1 != addedTriangle2 && shareSegment(addedTriangle1, addedTriangle2)) {
                    updateNeighbor(addedTriangle1, addedTriangle2);
                }
            }
        }
        if (this.gridIndex != null) {
            this.gridIndex.updateIndex(this.addedTriangles.iterator());
        }
    }

    private boolean shareSegment(Triangle t1, Triangle t2) {
        int counter = 0;
        Point t1P1 = t1.p1();
        Point t1P2 = t1.p2();
        Point t1P3 = t1.p3();
        Point t2P1 = t2.p1();
        Point t2P2 = t2.p2();
        Point t2P3 = t2.p3();
        if (t1P1.equals(t2P1)) {
            counter = 0 + 1;
        }
        if (t1P1.equals(t2P2)) {
            counter++;
        }
        if (t1P1.equals(t2P3)) {
            counter++;
        }
        if (t1P2.equals(t2P1)) {
            counter++;
        }
        if (t1P2.equals(t2P2)) {
            counter++;
        }
        if (t1P2.equals(t2P3)) {
            counter++;
        }
        if (t1P3.equals(t2P1)) {
            counter++;
        }
        if (t1P3.equals(t2P2)) {
            counter++;
        }
        if (t1P3.equals(t2P3)) {
            counter++;
        }
        if (counter >= 2) {
            return true;
        }
        return false;
    }

    private void updateNeighbor(Triangle addedTriangle, Triangle deletedTriangle, Point pointToDelete) {
        Point delA = deletedTriangle.p1();
        Point delB = deletedTriangle.p2();
        Point delC = deletedTriangle.p3();
        Point addA = addedTriangle.p1();
        Point addB = addedTriangle.p2();
        Point addC = addedTriangle.p3();
        if (pointToDelete.equals(delA)) {
            deletedTriangle.next_23().switchneighbors(deletedTriangle, addedTriangle);
            if ((addA.equals(delB) && addB.equals(delC)) || (addB.equals(delB) && addA.equals(delC))) {
                addedTriangle.abnext = deletedTriangle.next_23();
            } else if ((addA.equals(delB) && addC.equals(delC)) || (addC.equals(delB) && addA.equals(delC))) {
                addedTriangle.canext = deletedTriangle.next_23();
            } else {
                addedTriangle.bcnext = deletedTriangle.next_23();
            }
        } else if (pointToDelete.equals(delB)) {
            deletedTriangle.next_31().switchneighbors(deletedTriangle, addedTriangle);
            if ((addA.equals(delA) && addB.equals(delC)) || (addB.equals(delA) && addA.equals(delC))) {
                addedTriangle.abnext = deletedTriangle.next_31();
            } else if ((addA.equals(delA) && addC.equals(delC)) || (addC.equals(delA) && addA.equals(delC))) {
                addedTriangle.canext = deletedTriangle.next_31();
            } else {
                addedTriangle.bcnext = deletedTriangle.next_31();
            }
        } else {
            deletedTriangle.next_12().switchneighbors(deletedTriangle, addedTriangle);
            if ((addA.equals(delA) && addB.equals(delB)) || (addB.equals(delA) && addA.equals(delB))) {
                addedTriangle.abnext = deletedTriangle.next_12();
            } else if ((addA.equals(delA) && addC.equals(delB)) || (addC.equals(delA) && addA.equals(delB))) {
                addedTriangle.canext = deletedTriangle.next_12();
            } else {
                addedTriangle.bcnext = deletedTriangle.next_12();
            }
        }
    }

    private void updateNeighbor(Triangle addedTriangle1, Triangle addedTriangle2) {
        Point A1 = addedTriangle1.p1();
        Point B1 = addedTriangle1.p2();
        Point C1 = addedTriangle1.p3();
        Point A2 = addedTriangle2.p1();
        Point B2 = addedTriangle2.p2();
        Point C2 = addedTriangle2.p3();
        if (A1.equals(A2)) {
            if (B1.equals(B2)) {
                addedTriangle1.abnext = addedTriangle2;
                addedTriangle2.abnext = addedTriangle1;
            } else if (B1.equals(C2)) {
                addedTriangle1.abnext = addedTriangle2;
                addedTriangle2.canext = addedTriangle1;
            } else if (C1.equals(B2)) {
                addedTriangle1.canext = addedTriangle2;
                addedTriangle2.abnext = addedTriangle1;
            } else {
                addedTriangle1.canext = addedTriangle2;
                addedTriangle2.canext = addedTriangle1;
            }
        } else if (A1.equals(B2)) {
            if (B1.equals(A2)) {
                addedTriangle1.abnext = addedTriangle2;
                addedTriangle2.abnext = addedTriangle1;
            } else if (B1.equals(C2)) {
                addedTriangle1.abnext = addedTriangle2;
                addedTriangle2.bcnext = addedTriangle1;
            } else if (C1.equals(A2)) {
                addedTriangle1.canext = addedTriangle2;
                addedTriangle2.abnext = addedTriangle1;
            } else {
                addedTriangle1.canext = addedTriangle2;
                addedTriangle2.bcnext = addedTriangle1;
            }
        } else if (A1.equals(C2)) {
            if (B1.equals(A2)) {
                addedTriangle1.abnext = addedTriangle2;
                addedTriangle2.canext = addedTriangle1;
            }
            if (B1.equals(B2)) {
                addedTriangle1.abnext = addedTriangle2;
                addedTriangle2.bcnext = addedTriangle1;
            }
            if (C1.equals(A2)) {
                addedTriangle1.canext = addedTriangle2;
                addedTriangle2.canext = addedTriangle1;
                return;
            }
            addedTriangle1.canext = addedTriangle2;
            addedTriangle2.bcnext = addedTriangle1;
        } else if (B1.equals(A2)) {
            if (A1.equals(B2)) {
                addedTriangle1.abnext = addedTriangle2;
                addedTriangle2.abnext = addedTriangle1;
            } else if (A1.equals(C2)) {
                addedTriangle1.abnext = addedTriangle2;
                addedTriangle2.canext = addedTriangle1;
            } else if (C1.equals(B2)) {
                addedTriangle1.bcnext = addedTriangle2;
                addedTriangle2.abnext = addedTriangle1;
            } else {
                addedTriangle1.bcnext = addedTriangle2;
                addedTriangle2.canext = addedTriangle1;
            }
        } else if (B1.equals(B2)) {
            if (A1.equals(A2)) {
                addedTriangle1.abnext = addedTriangle2;
                addedTriangle2.abnext = addedTriangle1;
            } else if (A1.equals(C2)) {
                addedTriangle1.abnext = addedTriangle2;
                addedTriangle2.bcnext = addedTriangle1;
            } else if (C1.equals(A2)) {
                addedTriangle1.bcnext = addedTriangle2;
                addedTriangle2.abnext = addedTriangle1;
            } else {
                addedTriangle1.bcnext = addedTriangle2;
                addedTriangle2.bcnext = addedTriangle1;
            }
        } else if (B1.equals(C2)) {
            if (A1.equals(A2)) {
                addedTriangle1.abnext = addedTriangle2;
                addedTriangle2.canext = addedTriangle1;
            }
            if (A1.equals(B2)) {
                addedTriangle1.abnext = addedTriangle2;
                addedTriangle2.bcnext = addedTriangle1;
            }
            if (C1.equals(A2)) {
                addedTriangle1.bcnext = addedTriangle2;
                addedTriangle2.canext = addedTriangle1;
                return;
            }
            addedTriangle1.bcnext = addedTriangle2;
            addedTriangle2.bcnext = addedTriangle1;
        } else if (C1.equals(A2)) {
            if (A1.equals(B2)) {
                addedTriangle1.canext = addedTriangle2;
                addedTriangle2.abnext = addedTriangle1;
            } else if (A1.equals(C2)) {
                addedTriangle1.canext = addedTriangle2;
                addedTriangle2.canext = addedTriangle1;
            } else if (B1.equals(B2)) {
                addedTriangle1.bcnext = addedTriangle2;
                addedTriangle2.abnext = addedTriangle1;
            } else {
                addedTriangle1.bcnext = addedTriangle2;
                addedTriangle2.canext = addedTriangle1;
            }
        } else if (C1.equals(B2)) {
            if (A1.equals(A2)) {
                addedTriangle1.canext = addedTriangle2;
                addedTriangle2.abnext = addedTriangle1;
            } else if (A1.equals(C2)) {
                addedTriangle1.canext = addedTriangle2;
                addedTriangle2.bcnext = addedTriangle1;
            } else if (B1.equals(A2)) {
                addedTriangle1.bcnext = addedTriangle2;
                addedTriangle2.abnext = addedTriangle1;
            } else {
                addedTriangle1.bcnext = addedTriangle2;
                addedTriangle2.bcnext = addedTriangle1;
            }
        } else if (C1.equals(C2)) {
            if (A1.equals(A2)) {
                addedTriangle1.canext = addedTriangle2;
                addedTriangle2.canext = addedTriangle1;
            }
            if (A1.equals(B2)) {
                addedTriangle1.canext = addedTriangle2;
                addedTriangle2.bcnext = addedTriangle1;
            }
            if (B1.equals(A2)) {
                addedTriangle1.bcnext = addedTriangle2;
                addedTriangle2.canext = addedTriangle1;
                return;
            }
            addedTriangle1.bcnext = addedTriangle2;
            addedTriangle2.bcnext = addedTriangle1;
        }
    }

    private Point findDiagonal(Triangle triangle, Point point) {
        Point p1 = triangle.p1();
        Point p2 = triangle.p2();
        Point p3 = triangle.p3();
        if (p1.pointLineTest(point, p3) == 1 && p2.pointLineTest(point, p3) == 2) {
            return p3;
        }
        if (p3.pointLineTest(point, p2) == 1 && p1.pointLineTest(point, p2) == 2) {
            return p2;
        }
        if (p2.pointLineTest(point, p1) == 1 && p3.pointLineTest(point, p1) == 2) {
            return p1;
        }
        return null;
    }

    public Point[] calcVoronoiCell(Triangle triangle, Point p) {
        if (triangle.isHalfplane()) {
            Triangle halfplane = triangle;
            Point third = null;
            Triangle neighbor = null;
            if (!halfplane.next_12().isHalfplane()) {
                neighbor = halfplane.next_12();
            } else if (!halfplane.next_23().isHalfplane()) {
                neighbor = halfplane.next_23();
            } else if (!halfplane.next_23().isHalfplane()) {
                neighbor = halfplane.next_31();
            }
            if (!(neighbor.p1().equals(halfplane.p1()) || neighbor.p1().equals(halfplane.p2()))) {
                third = neighbor.p1();
            }
            if (!(neighbor.p2().equals(halfplane.p1()) || neighbor.p2().equals(halfplane.p2()))) {
                third = neighbor.p2();
            }
            if (!(neighbor.p3().equals(halfplane.p1()) || neighbor.p3().equals(halfplane.p2()))) {
                third = neighbor.p3();
            }
            double halfplane_delta = (halfplane.p1().y() - halfplane.p2().y()) / (halfplane.p1().x() - halfplane.p2().x());
            double perp_delta = (1.0d / halfplane_delta) * -1.0d;
            boolean above = true;
            if (((third.x() - halfplane.p1().x()) * halfplane_delta) + halfplane.p1().y() > third.y()) {
                above = false;
            }
            double sign = 1.0d;
            if ((perp_delta < 0.0d && !above) || (perp_delta > 0.0d && above)) {
                sign = -1.0d;
            }
            Point circumcircle = neighbor.circumcircle().Center();
            double y_cell_line = (((circumcircle.x() + (500.0d * sign)) - circumcircle.x()) * perp_delta) + circumcircle.y();
            return new Point[]{circumcircle, new Point(circumcircle.x() + (500.0d * sign), y_cell_line)};
        }
        Vector<Triangle> neighbors = findTriangleNeighborhood(triangle, p);
        Iterator<Triangle> itn = neighbors.iterator();
        Point[] pointArr = new Point[neighbors.size()];
        int index = 0;
        while (itn.hasNext()) {
            int index2 = index + 1;
            pointArr[index] = ((Triangle) itn.next()).circumcircle().Center();
            index = index2;
        }
        return pointArr;
    }

    public Iterator<Triangle> getLastUpdatedTriangles() {
        Vector<Triangle> tmp = new Vector();
        if (trianglesSize() > 1) {
            allTriangles(this.currT, tmp, this._modCount);
        }
        return tmp.iterator();
    }

    private void allTriangles(Triangle curr, Vector<Triangle> front, int mc) {
        if (curr != null && curr._mc == mc && !front.contains(curr)) {
            front.add(curr);
            allTriangles(curr.abnext, front, mc);
            allTriangles(curr.bcnext, front, mc);
            allTriangles(curr.canext, front, mc);
        }
    }

    private Triangle insertPointSimple(Point p) {
        this.nPoints++;
        if (!this.allCollinear) {
            Triangle t = find(this.startTriangle, p);
            if (t.halfplane) {
                this.startTriangle = extendOutside(t, p);
            } else {
                this.startTriangle = extendInside(t, p);
            }
            return this.startTriangle;
        } else if (this.nPoints == 1) {
            this.firstP = p;
            return null;
        } else if (this.nPoints == 2) {
            startTriangulation(this.firstP, p);
            return null;
        } else {
            switch (p.pointLineTest(this.firstP, this.lastP)) {
                case 0:
                    insertCollinear(p, 0);
                    return null;
                case 1:
                    this.startTriangle = extendOutside(this.firstT.abnext, p);
                    this.allCollinear = false;
                    return null;
                case 2:
                    this.startTriangle = extendOutside(this.firstT, p);
                    this.allCollinear = false;
                    return null;
                case 3:
                    insertCollinear(p, 3);
                    return null;
                case 4:
                    insertCollinear(p, 4);
                    return null;
                default:
                    return null;
            }
        }
    }

    private void insertCollinear(Point p, int res) {
        Triangle t;
        Triangle tp;
        switch (res) {
            case 0:
                Triangle u = this.firstT;
                while (p.isGreater(u.a)) {
                    u = u.canext;
                }
                t = new Triangle(p, u.b);
                tp = new Triangle(u.b, p);
                u.b = p;
                u.abnext.a = p;
                t.abnext = tp;
                tp.abnext = t;
                t.bcnext = u.bcnext;
                u.bcnext.canext = t;
                t.canext = u;
                u.bcnext = t;
                tp.canext = u.abnext.canext;
                u.abnext.canext.bcnext = tp;
                tp.bcnext = u.abnext;
                u.abnext.canext = tp;
                if (this.firstT == u) {
                    this.firstT = t;
                    return;
                }
                return;
            case 3:
                t = new Triangle(this.firstP, p);
                tp = new Triangle(p, this.firstP);
                t.abnext = tp;
                tp.abnext = t;
                t.bcnext = tp;
                tp.canext = t;
                t.canext = this.firstT;
                this.firstT.bcnext = t;
                tp.bcnext = this.firstT.abnext;
                this.firstT.abnext.canext = tp;
                this.firstT = t;
                this.firstP = p;
                return;
            case 4:
                t = new Triangle(p, this.lastP);
                tp = new Triangle(this.lastP, p);
                t.abnext = tp;
                tp.abnext = t;
                t.bcnext = this.lastT;
                this.lastT.canext = t;
                t.canext = tp;
                tp.bcnext = t;
                tp.canext = this.lastT.abnext;
                this.lastT.abnext.bcnext = tp;
                this.lastT = t;
                this.lastP = p;
                return;
            default:
                return;
        }
    }

    private void startTriangulation(Point p1, Point p2) {
        Point ps;
        Point pb;
        if (p1.isLess(p2)) {
            ps = p1;
            pb = p2;
        } else {
            ps = p2;
            pb = p1;
        }
        this.firstT = new Triangle(pb, ps);
        this.lastT = this.firstT;
        Triangle t = new Triangle(ps, pb);
        this.firstT.abnext = t;
        t.abnext = this.firstT;
        this.firstT.bcnext = t;
        t.canext = this.firstT;
        this.firstT.canext = t;
        t.bcnext = this.firstT;
        this.firstP = this.firstT.b;
        this.lastP = this.lastT.a;
        this.startTriangleHull = this.firstT;
    }

    private Triangle extendInside(Triangle t, Point p) {
        Triangle h1 = treatDegeneracyInside(t, p);
        if (h1 != null) {
            return h1;
        }
        h1 = new Triangle(t.c, t.a, p);
        Triangle h2 = new Triangle(t.b, t.c, p);
        t.c = p;
        t.circumcircle();
        h1.abnext = t.canext;
        h1.bcnext = t;
        h1.canext = h2;
        h2.abnext = t.bcnext;
        h2.bcnext = h1;
        h2.canext = t;
        h1.abnext.switchneighbors(t, h1);
        h2.abnext.switchneighbors(t, h2);
        t.bcnext = h2;
        t.canext = h1;
        return t;
    }

    private Triangle treatDegeneracyInside(Triangle t, Point p) {
        if (t.abnext.halfplane && p.pointLineTest(t.b, t.a) == 0) {
            return extendOutside(t.abnext, p);
        }
        if (t.bcnext.halfplane && p.pointLineTest(t.c, t.b) == 0) {
            return extendOutside(t.bcnext, p);
        }
        if (t.canext.halfplane && p.pointLineTest(t.a, t.c) == 0) {
            return extendOutside(t.canext, p);
        }
        return null;
    }

    private Triangle extendOutside(Triangle t, Point p) {
        if (p.pointLineTest(t.a, t.b) == 0) {
            Triangle dg = new Triangle(t.a, t.b, p);
            Triangle hp = new Triangle(p, t.b);
            t.b = p;
            dg.abnext = t.abnext;
            dg.abnext.switchneighbors(t, dg);
            dg.bcnext = hp;
            hp.abnext = dg;
            dg.canext = t;
            t.abnext = dg;
            hp.bcnext = t.bcnext;
            hp.bcnext.canext = hp;
            hp.canext = t;
            t.bcnext = hp;
            return dg;
        }
        Triangle ccT = extendcounterclock(t, p);
        Triangle cT = extendclock(t, p);
        ccT.bcnext = cT;
        cT.canext = ccT;
        this.startTriangleHull = cT;
        return cT.abnext;
    }

    private Triangle extendcounterclock(Triangle t, Point p) {
        t.halfplane = false;
        t.c = p;
        t.circumcircle();
        Triangle tca = t.canext;
        if (p.pointLineTest(tca.a, tca.b) < 2) {
            return extendcounterclock(tca, p);
        }
        Triangle nT = new Triangle(t.a, p);
        nT.abnext = t;
        t.canext = nT;
        nT.canext = tca;
        tca.bcnext = nT;
        return nT;
    }

    private Triangle extendclock(Triangle t, Point p) {
        t.halfplane = false;
        t.c = p;
        t.circumcircle();
        Triangle tbc = t.bcnext;
        if (p.pointLineTest(tbc.a, tbc.b) < 2) {
            return extendclock(tbc, p);
        }
        Triangle nT = new Triangle(p, t.b);
        nT.abnext = t;
        t.bcnext = nT;
        nT.bcnext = tbc;
        tbc.canext = nT;
        return nT;
    }

    private void flip(Triangle t, int mc) {
        Triangle u = t.abnext;
        t._mc = mc;
        if (!u.halfplane && u.circumcircle_contains(t.c)) {
            Triangle v;
            if (t.a == u.a) {
                v = new Triangle(u.b, t.b, t.c);
                v.abnext = u.bcnext;
                t.abnext = u.abnext;
            } else if (t.a == u.b) {
                v = new Triangle(u.c, t.b, t.c);
                v.abnext = u.canext;
                t.abnext = u.bcnext;
            } else if (t.a == u.c) {
                v = new Triangle(u.a, t.b, t.c);
                v.abnext = u.abnext;
                t.abnext = u.canext;
            } else {
                throw new RuntimeException("Error in flip.");
            }
            v._mc = mc;
            v.bcnext = t.bcnext;
            v.abnext.switchneighbors(u, v);
            v.bcnext.switchneighbors(t, v);
            t.bcnext = v;
            v.canext = t;
            t.b = v.a;
            t.abnext.switchneighbors(u, t);
            t.circumcircle();
            this.currT = v;
            flip(t, mc);
            flip(v, mc);
        }
    }

    public void write_tsin(String tsinFile) throws Exception {
        FileWriter fw = new FileWriter(tsinFile);
        PrintWriter os = new PrintWriter(fw);
        os.println(this._vertices.size());
        for (Point toFile : this._vertices) {
            os.println(toFile.toFile());
        }
        os.close();
        fw.close();
    }

    public void write_smf(String smfFile) throws Exception {
        int i;
        int len = this._vertices.size();
        Point[] ans = new Point[len];
        Iterator<Point> it = this._vertices.iterator();
        Comparator<Point> comp = Point.getComparator();
        for (i = 0; i < len; i++) {
            ans[i] = (Point) it.next();
        }
        Arrays.sort(ans, comp);
        FileWriter fw = new FileWriter(smfFile);
        PrintWriter os = new PrintWriter(fw);
        os.println("begin");
        for (i = 0; i < len; i++) {
            os.println("v " + ans[i].toFile());
        }
        int t = 0;
        Iterator<Triangle> dt = trianglesIterator();
        while (dt.hasNext()) {
            Triangle curr = (Triangle) dt.next();
            t++;
            if (!curr.halfplane) {
                int i1 = Arrays.binarySearch(ans, curr.a, comp);
                int i2 = Arrays.binarySearch(ans, curr.b, comp);
                int i3 = Arrays.binarySearch(ans, curr.c, comp);
                if (((i3 < 0 ? 1 : 0) | ((i1 < 0 ? 1 : 0) | (i2 < 0 ? 1 : 0))) != 0) {
                    throw new RuntimeException("wrong triangulation inner bug - cant write as an SMF file!");
                }
                os.println("f " + (i1 + 1) + " " + (i2 + 1) + " " + (i3 + 1));
            }
        }
        os.println("end");
        os.close();
        fw.close();
    }

    public int CH_size() {
        int ans = 0;
        Iterator<Point> it = CH_vertices_Iterator();
        while (it.hasNext()) {
            ans++;
            it.next();
        }
        return ans;
    }

    public void write_CH(String tsinFile) throws Exception {
        FileWriter fw = new FileWriter(tsinFile);
        PrintWriter os = new PrintWriter(fw);
        os.println(CH_size());
        Iterator<Point> it = CH_vertices_Iterator();
        while (it.hasNext()) {
            os.println(((Point) it.next()).toFileXY());
        }
        os.close();
        fw.close();
    }

    private static Point[] read_file(String file) throws Exception {
        if ((file.substring(file.length() - 4).equals(".smf") | file.substring(file.length() - 4).equals(".SMF")) != 0) {
            return read_smf(file);
        }
        return read_tsin(file);
    }

    private static Point[] read_tsin(String tsinFile) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(tsinFile));
        String s = bufferedReader.readLine();
        while (s.charAt(0) == '/') {
            s = bufferedReader.readLine();
        }
        StringTokenizer stringTokenizer = new StringTokenizer(s);
        int numOfVer = new Integer(s).intValue();
        Point[] ans = new Point[numOfVer];
        for (int i = 0; i < numOfVer; i++) {
            stringTokenizer = new StringTokenizer(bufferedReader.readLine());
            double d1 = new Double(stringTokenizer.nextToken()).doubleValue();
            ans[i] = new Point((double) ((int) d1), (double) ((int) new Double(stringTokenizer.nextToken()).doubleValue()), new Double(stringTokenizer.nextToken()).doubleValue());
        }
        return ans;
    }

    private static Point[] read_smf(String smfFile) throws Exception {
        return read_smf(smfFile, 1.0d, 1.0d, 1.0d, 0.0d, 0.0d, 0.0d);
    }

    private static Point[] read_smf(String smfFile, double dx, double dy, double dz, double minX, double minY, double minZ) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new FileReader(smfFile));
        String s = bufferedReader.readLine();
        while (s.charAt(0) != 'v') {
            s = bufferedReader.readLine();
        }
        Vector<Point> vec = new Vector();
        while (s != null && s.charAt(0) == 'v') {
            StringTokenizer stringTokenizer = new StringTokenizer(s);
            stringTokenizer.nextToken();
            double d1 = (new Double(stringTokenizer.nextToken()).doubleValue() * dx) + minX;
            double d2 = (new Double(stringTokenizer.nextToken()).doubleValue() * dy) + minY;
            double d3 = (new Double(stringTokenizer.nextToken()).doubleValue() * dz) + minZ;
            vec.add(new Point((double) ((int) d1), (double) ((int) d2), d3));
            s = bufferedReader.readLine();
        }
        Point[] ans = new Point[vec.size()];
        for (int i = 0; i < vec.size(); i++) {
            ans[i] = (Point) vec.elementAt(i);
        }
        return ans;
    }

    public Triangle find(Point p) {
        Triangle searchTriangle = this.startTriangle;
        if (this.gridIndex != null) {
            Triangle indexTriangle = this.gridIndex.findCellTriangleOf(p);
            if (indexTriangle != null) {
                searchTriangle = indexTriangle;
            }
        }
        return find(searchTriangle, p);
    }

    public Triangle find(Point p, Triangle start) {
        if (start == null) {
            start = this.startTriangle;
        }
        return find(start, p);
    }

    private static Triangle find(Triangle curr, Point p) {
        if (p == null) {
            return null;
        }
        Triangle next_t;
        if (curr.halfplane) {
            next_t = findnext2(p, curr);
            if (next_t == null || next_t.halfplane) {
                return curr;
            }
            curr = next_t;
        }
        while (true) {
            next_t = findnext1(p, curr);
            if (next_t == null) {
                return curr;
            }
            if (next_t.halfplane) {
                return next_t;
            }
            curr = next_t;
        }
    }

    private static Triangle findnext1(Point p, Triangle v) {
        if (p.pointLineTest(v.a, v.b) == 2 && !v.abnext.halfplane) {
            return v.abnext;
        }
        if (p.pointLineTest(v.b, v.c) == 2 && !v.bcnext.halfplane) {
            return v.bcnext;
        }
        if (p.pointLineTest(v.c, v.a) == 2 && !v.canext.halfplane) {
            return v.canext;
        }
        if (p.pointLineTest(v.a, v.b) == 2) {
            return v.abnext;
        }
        if (p.pointLineTest(v.b, v.c) == 2) {
            return v.bcnext;
        }
        if (p.pointLineTest(v.c, v.a) == 2) {
            return v.canext;
        }
        return null;
    }

    private static Triangle findnext2(Point p, Triangle v) {
        if (v.abnext != null && !v.abnext.halfplane) {
            return v.abnext;
        }
        if (v.bcnext != null && !v.bcnext.halfplane) {
            return v.bcnext;
        }
        if (v.canext == null || v.canext.halfplane) {
            return null;
        }
        return v.canext;
    }

    private Vector<Point> findConnectedVertices(Point point) {
        return findConnectedVertices(point, false);
    }

    private Vector<Point> findConnectedVertices(Point point, boolean saveTriangles) {
        Set<Point> pointsSet = new HashSet();
        Vector<Point> pointsVec = new Vector();
        Triangle triangle = find(point);
        if (triangle.isCorner(point)) {
            Vector<Triangle> triangles = findTriangleNeighborhood(triangle, point);
            if (triangles == null) {
                System.err.println("Error: can't delete a point on the perimeter");
                return null;
            }
            if (saveTriangles) {
                this.deletedTriangles = triangles;
            }
            Iterator i$ = triangles.iterator();
            while (i$.hasNext()) {
                Triangle tmpTriangle = (Triangle) i$.next();
                Point point1 = tmpTriangle.p1();
                Point point2 = tmpTriangle.p2();
                Point point3 = tmpTriangle.p3();
                if (point1.equals(point) && !pointsSet.contains(point2)) {
                    pointsSet.add(point2);
                    pointsVec.add(point2);
                }
                if (point2.equals(point) && !pointsSet.contains(point3)) {
                    pointsSet.add(point3);
                    pointsVec.add(point3);
                }
                if (point3.equals(point) && !pointsSet.contains(point1)) {
                    pointsSet.add(point1);
                    pointsVec.add(point1);
                }
            }
            return pointsVec;
        }
        System.err.println("findConnectedVertices: Could not find connected vertices since the first found triangle doesn't share the given point.");
        return null;
    }

    private boolean onPerimeter(Vector<Triangle> triangles) {
        Iterator i$ = triangles.iterator();
        while (i$.hasNext()) {
            if (((Triangle) i$.next()).isHalfplane()) {
                return true;
            }
        }
        return false;
    }

    public Vector<Triangle> findTriangleNeighborhood(Triangle firstTriangle, Point point) {
        Vector<Triangle> triangles = new Vector(30);
        triangles.add(firstTriangle);
        Triangle currentTriangle = firstTriangle;
        Triangle nextTriangle = currentTriangle.nextNeighbor(point, null);
        while (nextTriangle != firstTriangle) {
            if (nextTriangle.isHalfplane()) {
                return null;
            }
            triangles.add(nextTriangle);
            Triangle prevTriangle = currentTriangle;
            currentTriangle = nextTriangle;
            nextTriangle = currentTriangle.nextNeighbor(point, prevTriangle);
        }
        return triangles;
    }

    private Triangle findTriangle(Vector<Point> pointsVec, Point p) {
        Point[] arrayPoints = new Point[pointsVec.size()];
        pointsVec.toArray(arrayPoints);
        int size = arrayPoints.length;
        if (size < 3) {
            return null;
        }
        if (size == 3) {
            return new Triangle(arrayPoints[0], arrayPoints[1], arrayPoints[2]);
        }
        for (int i = 0; i <= size - 1; i++) {
            Point p1 = arrayPoints[i];
            int j = i + 1;
            int k = i + 2;
            if (j >= size) {
                j = 0;
                k = 1;
            } else if (k >= size) {
                k = 0;
            }
            Point p2 = arrayPoints[j];
            Point p3 = arrayPoints[k];
            Triangle t = new Triangle(p1, p2, p3);
            if (calcDet(p1, p2, p3) >= 0.0d && !t.contains(p) && !t.fallInsideCircumcircle(arrayPoints)) {
                return t;
            }
            if (size == 4 && calcDet(p1, p2, p3) >= 0.0d && !t.contains_BoundaryIsOutside(p) && !t.fallInsideCircumcircle(arrayPoints)) {
                return t;
            }
        }
        return null;
    }

    private double calcDet(Point A, Point B, Point P) {
        return ((A.x() * (B.y() - P.y())) - (A.y() * (B.x() - P.x()))) + ((B.x() * P.y()) - (B.y() * P.x()));
    }

    public boolean contains(Point p) {
        return !find(p).halfplane;
    }

    public boolean contains(double x, double y) {
        return contains(new Point(x, y));
    }

    public Point z(Point q) {
        return find(q).z(q);
    }

    public double z(double x, double y) {
        Point q = new Point(x, y);
        return find(q).z_value(q);
    }

    private void updateBoundingBox(Point p) {
        double x = p.x();
        double y = p.y();
        double z = p.z();
        if (this._bb_min == null) {
            this._bb_min = new Point(p);
            this._bb_max = new Point(p);
            return;
        }
        if (x < this._bb_min.x()) {
            this._bb_min.x = x;
        } else if (x > this._bb_max.x()) {
            this._bb_max.x = x;
        }
        if (y < this._bb_min.y) {
            this._bb_min.y = y;
        } else if (y > this._bb_max.y()) {
            this._bb_max.y = y;
        }
        if (z < this._bb_min.z) {
            this._bb_min.z = z;
        } else if (z > this._bb_max.z()) {
            this._bb_max.z = z;
        }
    }

    public BoundingBox getBoundingBox() {
        return new BoundingBox(this._bb_min, this._bb_max);
    }

    public Point minBoundingBox() {
        return this._bb_min;
    }

    public Point maxBoundingBox() {
        return this._bb_max;
    }

    public Iterator<Triangle> trianglesIterator() {
        if (size() <= 2) {
            this._triangles = new Vector();
        }
        initTriangles();
        return this._triangles.iterator();
    }

    public Iterator<Point> CH_vertices_Iterator() {
        Vector<Point> ans = new Vector();
        Triangle curr = this.startTriangleHull;
        boolean cont = true;
        double x0 = this._bb_min.x();
        double x1 = this._bb_max.x();
        double y0 = this._bb_min.y();
        double y1 = this._bb_max.y();
        while (cont) {
            boolean sx = curr.p1().x() == x0 || curr.p1().x() == x1;
            boolean sy = curr.p1().y() == y0 || curr.p1().y() == y1;
            if ((((!sy ? 1 : 0) & (!sx ? 1 : 0)) | (sx & sy)) != 0) {
                ans.add(curr.p1());
            }
            if (curr.bcnext != null && curr.bcnext.halfplane) {
                curr = curr.bcnext;
            }
            if (curr == this.startTriangleHull) {
                cont = false;
            }
        }
        return ans.iterator();
    }

    public Iterator<Point> verticesIterator() {
        return this._vertices.iterator();
    }

    private void initTriangles() {
        if (this._modCount != this._modCount2 && size() > 2) {
            this._modCount2 = this._modCount;
            Vector<Triangle> front = new Vector();
            this._triangles = new Vector();
            front.add(this.startTriangle);
            while (front.size() > 0) {
                Triangle t = (Triangle) front.remove(0);
                if (!t._mark) {
                    t._mark = true;
                    this._triangles.add(t);
                    if (!(t.abnext == null || t.abnext._mark)) {
                        front.add(t.abnext);
                    }
                    if (!(t.bcnext == null || t.bcnext._mark)) {
                        front.add(t.bcnext);
                    }
                    if (!(t.canext == null || t.canext._mark)) {
                        front.add(t.canext);
                    }
                }
            }
            for (int i = 0; i < this._triangles.size(); i++) {
                ((Triangle) this._triangles.elementAt(i))._mark = false;
            }
        }
    }

    public void IndexData(int xCellCount, int yCellCount) {
        this.gridIndex = new GridIndex(this, xCellCount, yCellCount);
    }

    public void RemoveIndex() {
        this.gridIndex = null;
    }
}
