package com.rusty.polygontask.model;

import com.rusty.polygontask.enumeration.IntersectionType;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

public class Point {

    public final double x;
    public final double y;
    private IntersectionType intersectionType = IntersectionType.none;
    private double angle;

    public Point(double x, double y) {
        BigDecimal bdX = new BigDecimal(x);
        BigDecimal bdY = new BigDecimal(y);
        this.x = bdX.setScale(6, RoundingMode.HALF_UP).doubleValue();
        this.y = bdY.setScale(6, RoundingMode.HALF_UP).doubleValue();
    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    public IntersectionType getIntersectionType() {
        return intersectionType;
    }

    public void setIntersectionType(IntersectionType intersectionType) {
        this.intersectionType = intersectionType;
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
        return "[" + x + "; " + y + "] " + intersectionType;
    }
}
