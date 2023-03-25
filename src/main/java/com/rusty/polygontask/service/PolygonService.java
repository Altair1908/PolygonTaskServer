package com.rusty.polygontask.service;

import com.rusty.polygontask.enumeration.ContourDirection;
import com.rusty.polygontask.model.Point;
import com.rusty.polygontask.enumeration.PointPosition;
import com.rusty.polygontask.model.Polygon;
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

    @Autowired
    public void setLineSegmentService(LineSegmentService lineSegmentService) {
        this.lineSegmentService = lineSegmentService;
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

    // Gauss's area formula
    public double getPolygonSquare(Polygon polygon) {
        List<Point> points = polygon.getPoints();
        double component = 0.0;
        for (int i = 0; i < points.size(); i++) {
            int secondPointIndex = i == points.size() - 1 ? 0 : i + 1;
            component += points.get(i).x * points.get(secondPointIndex).y -
                    points.get(i).y * points.get(secondPointIndex).x;
        }
        return 0.5 * Math.abs(component);
    }

    public void createPolygonsWithIntersectionPoints(Polygon polygon1, Polygon polygon2) {
        List<Point> points1 = polygon1.getPoints();
        List<Point> points2 = polygon2.getPoints();
        if (getPolygonContourDirection(polygon1).equals(ContourDirection.counterClockwise)) {
            Collections.reverse(points1);
        }
        if (getPolygonContourDirection(polygon2).equals(ContourDirection.counterClockwise)) {
            Collections.reverse(points2);
        }
        Polygon dupPolygon1 = new Polygon(points1);
        Polygon dupPolygon2 = new Polygon(points2);
        int secondPointIndex1;
        int secondPointIndex2;
        List<Point> edgeIntersectionPoints = new ArrayList<>();
        for (int i = 0; i < points1.size(); i++) {
            secondPointIndex1 = i == points1.size() - 1 ? 0 : i + 1;
            edgeIntersectionPoints.clear();
            for (int t = 0; t < points2.size(); t++) {
                secondPointIndex2 = t == points2.size() - 1 ? 0 : t + 1;
                Optional<Point> intersectionPointOpt = lineSegmentService.getSegmentsIntersectionPoint(
                        points1.get(i), points1.get(secondPointIndex1), points2.get(t), points2.get(secondPointIndex2));
                intersectionPointOpt.ifPresent(edgeIntersectionPoints::add);
            }

            System.out.println(edgeIntersectionPoints);

        }
    }
}



















