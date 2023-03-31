package com.rusty.polygontask.model;

import com.rusty.polygontask.enumeration.EdgePoint;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;
import java.util.UUID;

public class Point {

//    private final UUID id;
    public final double x;
    public final double y;
    private boolean isIntersectionPoint = false;
    private EdgePoint edgePoint = EdgePoint.none;
    private int basePointIndex;
    private double angle;
    private boolean deletionMark = false;

    public Point(double x, double y) {
//        this.id = UUID.randomUUID();
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

//    public UUID getId() {
//        return id;
//    }

    public EdgePoint getEdgePoint() {
        return edgePoint;
    }

    public void setEdgePoint(EdgePoint edgePoint) {
        this.edgePoint = edgePoint;
    }

    public boolean isDeletionMark() {
        return deletionMark;
    }

    public void setDeletionMark(boolean deletionMark) {
        this.deletionMark = deletionMark;
    }

    //    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Point point = (Point) o;
//        return point.id.equals(this.id);
//    }


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
        return "[" + x + "; " + y + "] " + isIntersectionPoint + " " + edgePoint;
    }
}
