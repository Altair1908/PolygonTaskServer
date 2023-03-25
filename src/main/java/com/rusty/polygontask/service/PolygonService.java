package com.rusty.polygontask.service;

import com.rusty.polygontask.enumeration.ContourDirection;
import com.rusty.polygontask.model.Point;
import com.rusty.polygontask.enumeration.PointPosition;
import com.rusty.polygontask.model.Polygon;
import com.rusty.polygontask.model.Triangle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static com.rusty.polygontask.constant.MathConstants.delta;
import static com.rusty.polygontask.constant.MathConstants.pi;

@Service
public class PolygonService {

    private LineSegmentService lineSegmentService;
    private TriangleService triangleService;

    @Autowired
    public void setLineSegmentService(LineSegmentService lineSegmentService) {
        this.lineSegmentService = lineSegmentService;
    }

    @Autowired
    public void setTriangleService(TriangleService triangleService) {
        this.triangleService = triangleService;
    }

    private double getPolygonAnglesSum(Polygon polygon) {
        List<Point> points = new ArrayList<>(polygon.getPoints());
        double sumAngle = 0.0;
        for (int i = 0; i < points.size(); i++) {
            Point p1 = points.get(0);
            Point p2 = points.get(1);
            Point p3 = points.get(2);
            double angle = lineSegmentService.getClockwiseAngle(p1, p2, p3);
            PointPosition pointPosition = lineSegmentService.getLineRelativePointPosition(p1, p2, p3);
            if (pointPosition.equals(PointPosition.rightSide)) {
                sumAngle += pi - angle;
            } else {
                sumAngle += pi + angle;
            }
            Collections.rotate(points, -1);
        }
        return sumAngle;
    }

    public ContourDirection getPolygonContourDirection(Polygon polygon) {
        double actualPolygonAnglesSum = getPolygonAnglesSum(polygon);
        double expectedPolygonAnglesSum = pi * (polygon.getPoints().size() - 2);
        if (Math.abs(actualPolygonAnglesSum - expectedPolygonAnglesSum) < delta) {
            return ContourDirection.clockwise;
        } else {
            return ContourDirection.counterClockwise;
        }
    }

    public double getPolygonSquare(Polygon polygon) {
        List<Point> points = polygon.getPoints();
        if (getPolygonContourDirection(polygon).equals(ContourDirection.counterClockwise)) {
            Collections.reverse(points);
        }
        double square = 0.0;
        while (points.size() >= 3) {
            Point p1 = points.get(0);
            Point p2 = points.get(1);
            Point p3 = points.get(2);
            Triangle triangle = new Triangle(p1, p2, p3);
            PointPosition pointPosition = lineSegmentService.getLineRelativePointPosition(p1, p2, p3);
            if (pointPosition.equals(PointPosition.rightSide)) {
                if (triangleService.isFullFilling(triangle, polygon)) {
                    square += triangleService.getTriangleSquare(triangle);
                    points.remove(p2);
                } else {
                    Collections.rotate(points, -1);
                }
            } else if (pointPosition.equals(PointPosition.belongs)) {
                points.remove(p2);
            } else if (pointPosition.equals(PointPosition.leftSide)) {
                Collections.rotate(points, -1);
            }
        }
        return square;
    }




    public void doWork(Polygon polygon1, Polygon polygon2) {
        // todo переворачивать
        List<Point> points1 = polygon1.getPoints();
        List<Point> points2 = polygon2.getPoints();
        Point first1;
        Point second1;
        Point first2;
        Point second2;
        int secondPointIndex1;
        int secondPointIndex2;
        for (int i = 0; i < points1.size(); i++) {
            secondPointIndex1 = i == points1.size() - 1 ? 0 : i + 1;
            first1 = points1.get(i);
            second1 = points1.get(secondPointIndex1);
            for (int t = 0; t < points2.size(); t++) {
                secondPointIndex2 = t == points2.size() - 1 ? 0 : t + 1;
                first2 = points2.get(t);
                second2 = points2.get(secondPointIndex2);
                Optional<Point> intersectionPointOpt =
                        lineSegmentService.getSegmentsIntersectionPoint(first1, second1, first2, second2);
                intersectionPointOpt.ifPresent(point -> System.out.println(intersectionPointOpt.get()));
            }
        }
    }
}



















