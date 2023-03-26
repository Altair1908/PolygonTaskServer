package com.rusty.polygontask.model;

import java.util.Objects;

public class Point {

    public final double x;
    public final double y;
    private boolean isIntersectionPoint = false;
    private double angle;

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public boolean isIntersectionPoint() {
        return isIntersectionPoint;
    }

    public void setIntersectionPoint(boolean intersectionPoint) {
        isIntersectionPoint = intersectionPoint;
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return Double.compare(point.x, x) == 0 && Double.compare(point.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "[" + x + "; " + y + "]";
    }
}
