package com.rusty.polygontask.service;

import com.rusty.polygontask.model.Point;
import com.rusty.polygontask.enumeration.PointPosition;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Optional;

@Service
public class LineSegmentService {

    public double getClockwiseAngle(Point p1, Point p2, Point p3) {
        Point np2 = new Point(p2.getX() - p1.getX(), p2.getY() - p1.getY());
        Point np3 = new Point(p3.getX() - p2.getX(), p3.getY() - p2.getY());

        double numerator = np2.getX() * np3.getX() + np2.getY() * np3.getY();
        double pOneTwoVectorLength = Math.sqrt(Math.pow(np2.getX(), 2) + Math.pow(np2.getY(), 2));
        double pTwoThreeVectorLength = Math.sqrt(Math.pow(np3.getX(), 2) + Math.pow(np3.getY(), 2));
        return Math.acos(numerator / (pOneTwoVectorLength * pTwoThreeVectorLength));
    }

    public PointPosition getLineRelativePointPosition(Point lineFirstPoint, Point lineSecondPoint, Point point) {
        double k = (point.getX() - lineFirstPoint.getX()) * (lineSecondPoint.getY() - lineFirstPoint.getY()) -
                (point.getY() - lineFirstPoint.getY()) * (lineSecondPoint.getX() - lineFirstPoint.getX());
        if (k > 0) {
            return PointPosition.rightSide;
        } else if (k < 0) {
            return PointPosition.leftSide;
        }
        return PointPosition.belongs;
    }

    public Optional<Point> getSegmentsIntersectionPoint(Point p1, Point p2, Point p3, Point p4) {
        Optional<Point> linesIntersectionPointOpt = getLinesIntersectionPoint(p1, p2, p3, p4);
        if (linesIntersectionPointOpt.isPresent()) {
            Point point = linesIntersectionPointOpt.get();
            if (isPointIncluded(p1, p2, point) && isPointIncluded(p3, p4, point)) {
                return Optional.of(point);
            }
        }
        return Optional.empty();
    }

    private boolean isPointIncluded(Point p1, Point p2, Point point) {
        double distance1 = Math.sqrt(Math.pow(point.getX() - p1.getX(), 2) + Math.pow(point.getY() - p1.getY(), 2));
        double distance2 = Math.sqrt(Math.pow(point.getX() - p2.getX(), 2) + Math.pow(point.getY() - p2.getY(), 2));
        double segmentLen = Math.sqrt(Math.pow(p2.getX() - p1.getX(), 2) + Math.pow(p2.getY() - p1.getY(), 2));
        BigDecimal d1 = BigDecimal.valueOf(distance1).setScale(3, RoundingMode.HALF_UP);
        BigDecimal d2 = BigDecimal.valueOf(distance2).setScale(3, RoundingMode.HALF_UP);
        BigDecimal sl = BigDecimal.valueOf(segmentLen).setScale(3, RoundingMode.HALF_UP);
        return sl.compareTo(d1.add(d2)) == 0;
    }

    private Optional<Point> getLinesIntersectionPoint(Point p1, Point p2, Point p3, Point p4) {
        double k1;
        double k2;
        double b1;
        double b2;
        double x;
        double y;

        if (Double.compare(p2.getX(), p1.getX()) != 0 && Double.compare(p4.getX(), p3.getX()) != 0) {
            k1 = getSlopeFactor(p1, p2);
            k2 = getSlopeFactor(p3, p4);
            b1 = p1.getY() - k1 * p1.getX();
            b2 = p3.getY() - k2 * p3.getX();
            x = (b2 - b1) / (k1 - k2);
            y = k1 * x + b1;
            if (Double.compare(Math.abs(k1), Math.abs(k2)) == 0) {
                return Optional.empty();
            }
            return Optional.of(new Point(x, y));
        } else if (Double.compare(p2.getX(), p1.getX()) == 0 && Double.compare(p4.getX(), p3.getX()) != 0) {
            k2 = getSlopeFactor(p3, p4);
            b2 = p3.getY() - k2 * p3.getX();
            x = p2.getX();
            y = k2 * x + b2;
            return Optional.of(new Point(x, y));
        } else if (Double.compare(p2.getX(), p1.getX()) != 0 && Double.compare(p4.getX(), p3.getX()) == 0) {
            k1 = getSlopeFactor(p1, p2);
            b1 = p1.getY() - k1 * p1.getX();
            x = p4.getX();
            y = k1 * x + b1;
            return Optional.of(new Point(x, y));
        } else {
            return Optional.empty();
        }
    }

    private double getSlopeFactor(Point first, Point second) {
        return (second.getY() - first.getY()) / (second.getX() - first.getX());
    }
}
