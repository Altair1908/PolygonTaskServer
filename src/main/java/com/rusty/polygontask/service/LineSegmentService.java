package com.rusty.polygontask.service;

import com.rusty.polygontask.model.Point;
import com.rusty.polygontask.enumeration.PointPosition;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Arrays;
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
            double[] xs = new double[]{p1.getX(), p2.getX(), p3.getX(), p4.getX()};
            double[] ys = new double[]{p1.getY(), p2.getY(), p3.getY(), p4.getY()};
            Arrays.sort(xs);
            Arrays.sort(ys);
            if (xAxisOverlapping(p1, p2, p3, p4) && yAxisOverlapping(p1, p2, p3, p4)) {
                if (point.getX() > xs[1] && point.getX() < xs[2] && point.getX() > ys[1] && point.getY() < ys[2]) {
                    point.setX(BigDecimal.valueOf(point.getX()).setScale(2, RoundingMode.HALF_UP).doubleValue());
                    point.setY(BigDecimal.valueOf(point.getY()).setScale(2, RoundingMode.HALF_UP).doubleValue());
                    return Optional.of(point);
                }
            }
        }
        return Optional.empty();
    }

    private boolean xAxisOverlapping(Point p1, Point p2, Point p3, Point p4) {
        if (p3.getX() <= p4.getX()) {
            return (p1.getX() >= p3.getX() && p1.getX() <= p4.getX()) || (p2.getX() >= p3.getX() && p2.getX() <= p4.getX());
        } else {
            return (p1.getX() >= p4.getX() && p1.getX() <= p3.getX()) || (p2.getX() >= p4.getX() && p2.getX() <= p3.getX());
        }
    }

    private boolean yAxisOverlapping(Point p1, Point p2, Point p3, Point p4) {
        if (p3.getY() <= p4.getY()) {
            return (p1.getY() >= p3.getY() && p1.getY() <= p4.getY()) || (p2.getY() >= p3.getY() && p2.getY() <= p4.getY());
        } else {
            return (p1.getY() >= p4.getY() && p1.getY() <= p3.getY()) || (p2.getY() >= p4.getY() && p2.getY() <= p3.getY());
        }
    }

    private Optional<Point> getLinesIntersectionPoint(Point p1, Point p2, Point p3, Point p4) {
        double k1 = (p2.getY() - p1.getY()) / (p2.getX() - p1.getX());
        double k2 = (p4.getY() - p3.getY()) / (p4.getX() - p3.getX());
        if (Double.compare(k1, k2) == 0) {
            return Optional.empty();
        }
        double b1 = p1.getY() - k1 * p1.getX();
        double b2 = p3.getY() - k2 * p3.getX();
        double x = (b2 - b1) / (k1 - k2);
        double y = k1 * x + b1;
        
        Point value = new Point(x, y);
        value.setX(BigDecimal.valueOf(value.getX()).setScale(2, RoundingMode.HALF_UP).doubleValue());
        value.setY(BigDecimal.valueOf(value.getY()).setScale(2, RoundingMode.HALF_UP).doubleValue());
        System.out.println(value);

        return Optional.of(value);
    }
}
