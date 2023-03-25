package com.rusty.polygontask.service;

import com.rusty.polygontask.model.Point;
import com.rusty.polygontask.enumeration.PointPosition;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static com.rusty.polygontask.constant.MathConstants.delta;

@Service
public class LineSegmentService {

    public double getClockwiseAngle(Point p1, Point p2, Point p3) {
        Point np2 = new Point(p2.x() - p1.x(), p2.y() - p1.y());
        Point np3 = new Point(p3.x() - p2.x(), p3.y() - p2.y());
        double numerator = np2.x() * np3.x() + np2.y() * np3.y();
        double pOneTwoVectorLength = Math.sqrt(Math.pow(np2.x(), 2) + Math.pow(np2.y(), 2));
        double pTwoThreeVectorLength = Math.sqrt(Math.pow(np3.x(), 2) + Math.pow(np3.y(), 2));
        return Math.acos(numerator / (pOneTwoVectorLength * pTwoThreeVectorLength));
    }

    public PointPosition getLineRelativePointPosition(Point lineFirstPoint, Point lineSecondPoint, Point point) {
        double k = (point.x() - lineFirstPoint.x()) * (lineSecondPoint.y() - lineFirstPoint.y()) -
                (point.y() - lineFirstPoint.y()) * (lineSecondPoint.x() - lineFirstPoint.x());
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
        double distance1 = Math.sqrt(Math.pow(point.x() - p1.x(), 2) + Math.pow(point.y() - p1.y(), 2));
        double distance2 = Math.sqrt(Math.pow(point.x() - p2.x(), 2) + Math.pow(point.y() - p2.y(), 2));
        double segmentLen = Math.sqrt(Math.pow(p2.x() - p1.x(), 2) + Math.pow(p2.y() - p1.y(), 2));
        return Math.abs(segmentLen - distance1 - distance2) < delta;
    }

    private Optional<Point> getLinesIntersectionPoint(Point p1, Point p2, Point p3, Point p4) {
        double k1;
        double k2;
        double b1;
        double b2;
        double x;
        double y;

        boolean isFirstLineVertical = Math.abs(p2.x() - p1.x()) < delta;
        boolean isSecondLineVertical = Math.abs(p4.x() - p3.x()) < delta;
        if (!isFirstLineVertical && !isSecondLineVertical) {
            k1 = getSlopeFactor(p1, p2);
            k2 = getSlopeFactor(p3, p4);
            b1 = p1.y() - k1 * p1.x();
            b2 = p3.y() - k2 * p3.x();
            x = (b2 - b1) / (k1 - k2);
            y = k1 * x + b1;
            if (Math.abs(k1 - k2) < delta) {
                return Optional.empty();
            }
            return Optional.of(new Point(x, y));
        } else if (isFirstLineVertical && !isSecondLineVertical) {
            k2 = getSlopeFactor(p3, p4);
            b2 = p3.y() - k2 * p3.x();
            x = p2.x();
            y = k2 * x + b2;
            return Optional.of(new Point(x, y));
        } else if (!isFirstLineVertical) {
            k1 = getSlopeFactor(p1, p2);
            b1 = p1.y() - k1 * p1.x();
            x = p4.x();
            y = k1 * x + b1;
            return Optional.of(new Point(x, y));
        } else {
            return Optional.empty();
        }
    }

    private double getSlopeFactor(Point first, Point second) {
        return (second.y() - first.y()) / (second.x() - first.x());
    }
}
