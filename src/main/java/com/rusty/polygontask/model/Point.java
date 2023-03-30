package com.rusty.polygontask.model;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;
import java.util.UUID;

public class Point {

    private final UUID id;
    public final double x;
    public final double y;
    private boolean isIntersectionPoint = false;
    private int basePointIndex;
    private double angle;

    public Point(double x, double y) {
        this.id = UUID.randomUUID();
        BigDecimal bdX = new BigDecimal(x);
        BigDecimal bdY = new BigDecimal(y);
        this.x = bdX.setScale(6, RoundingMode.HALF_UP).doubleValue();
        this.y = bdY.setScale(6, RoundingMode.HALF_UP).doubleValue();
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

    public int getBasePointIndex() {
        return basePointIndex;
    }

    public void setBasePointIndex(int basePointIndex) {
        this.basePointIndex = basePointIndex;
    }

    public UUID getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return point.id.equals(this.id);
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
