package com.rusty.polygontask.model;

import java.util.ArrayList;
import java.util.List;

public class Triangle {

    private final List<Point> points = new ArrayList<>();

    public Triangle(Point a, Point b, Point c) {
        points.add(a);
        points.add(b);
        points.add(c);
    }

    public List<Point> getPoints() {
        return points;
    }

    public Point getA() {
        return points.get(0);
    }

    public Point getB() {
        return points.get(1);
    }

    public Point getC() {
        return points.get(2);
    }
}
