package com.givewaygames.gwgl.utils.gl.delaunay;

import java.lang.reflect.Array;
import java.util.Iterator;

public class GridIndex {
    private Triangle[][] grid;
    private DelaunayTriangulation indexDelaunay;
    private BoundingBox indexRegion;
    private double x_size;
    private double y_size;

    public GridIndex(DelaunayTriangulation delaunay, int xCellCount, int yCellCount) {
        this(delaunay, xCellCount, yCellCount, delaunay.getBoundingBox());
    }

    public GridIndex(DelaunayTriangulation delaunay, int xCellCount, int yCellCount, BoundingBox region) {
        init(delaunay, xCellCount, yCellCount, region);
    }

    private void init(DelaunayTriangulation delaunay, int xCellCount, int yCellCount, BoundingBox region) {
        this.indexDelaunay = delaunay;
        this.indexRegion = region;
        this.x_size = region.getWidth() / ((double) yCellCount);
        this.y_size = region.getHeight() / ((double) xCellCount);
        this.grid = (Triangle[][]) Array.newInstance(Triangle.class, new int[]{xCellCount, yCellCount});
        int i = 0;
        updateCellValues(0, i, xCellCount - 1, yCellCount - 1, this.indexDelaunay.find(middleOfCell(0, 0)));
    }

    public Triangle findCellTriangleOf(Point point) {
        return this.grid[(int) ((point.x() - this.indexRegion.minX()) / this.x_size)][(int) ((point.y() - this.indexRegion.minY()) / this.y_size)];
    }

    public void updateIndex(Iterator<Triangle> updatedTriangles) {
        BoundingBox updatedRegion = new BoundingBox();
        while (updatedTriangles.hasNext()) {
            updatedRegion = updatedRegion.unionWith(((Triangle) updatedTriangles.next()).getBoundingBox());
        }
        if (!updatedRegion.isNull()) {
            if (this.indexRegion.contains(updatedRegion)) {
                Point minInvalidCell = getCellOf(updatedRegion.getMinPoint());
                Point maxInvalidCell = getCellOf(updatedRegion.getMaxPoint());
                updateCellValues((int) minInvalidCell.x, (int) minInvalidCell.y, (int) maxInvalidCell.x, (int) maxInvalidCell.y, findValidTriangle(minInvalidCell));
                return;
            }
            init(this.indexDelaunay, (int) (this.indexRegion.getWidth() / this.x_size), (int) (this.indexRegion.getHeight() / this.y_size), this.indexRegion.unionWith(updatedRegion));
        }
    }

    private void updateCellValues(int startXCell, int startYCell, int lastXCell, int lastYCell, Triangle startTriangle) {
        for (int i = startXCell; i <= lastXCell; i++) {
            startTriangle = this.indexDelaunay.find(middleOfCell(i, startYCell), startTriangle);
            this.grid[i][startYCell] = startTriangle;
            Triangle prevRowTriangle = startTriangle;
            for (int j = startYCell + 1; j <= lastYCell; j++) {
                this.grid[i][j] = this.indexDelaunay.find(middleOfCell(i, j), prevRowTriangle);
                prevRowTriangle = this.grid[i][j];
            }
        }
    }

    private Triangle findValidTriangle(Point minInvalidCell) {
        if (minInvalidCell.x == 0.0d && minInvalidCell.y == 0.0d) {
            return this.indexDelaunay.find(middleOfCell((int) minInvalidCell.x, (int) minInvalidCell.y), null);
        }
        return this.grid[Math.min(0, (int) minInvalidCell.x)][Math.min(0, (int) minInvalidCell.y)];
    }

    private Point getCellOf(Point coordinate) {
        return new Point((double) ((int) ((coordinate.x() - this.indexRegion.minX()) / this.x_size)), (double) ((int) ((coordinate.y() - this.indexRegion.minY()) / this.y_size)));
    }

    private Point middleOfCell(int x_index, int y_index) {
        return new Point((this.indexRegion.minX() + (((double) x_index) * this.x_size)) + (this.x_size / 2.0d), (this.indexRegion.minY() + (((double) y_index) * this.y_size)) + (this.y_size / 2.0d));
    }
}
