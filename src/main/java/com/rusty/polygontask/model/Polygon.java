package com.rusty.polygontask.model;

import java.util.ArrayList;
import java.util.List;

public class Polygon {

    private final List<Point> points = new ArrayList<>();

    public Polygon() {}

    public Polygon(List<Point> points) {
        this.points.addAll(points);
    }

    public List<Point> getPoints() {
        return points;
    }

    public void addPoint(double x, double y) {
        points.add(new Point(x, y));
    }

    public void addPoint(Point point) {
        points.add(point);
    }
}
