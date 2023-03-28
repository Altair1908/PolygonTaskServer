package com.rusty.polygontask.service;

import com.rusty.polygontask.enumeration.ContourDirection;
import com.rusty.polygontask.model.Point;
import com.rusty.polygontask.model.Polygon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static com.rusty.polygontask.constant.MathConstants.delta;
import static com.rusty.polygontask.constant.MathConstants.pi;

@Service
public class PolygonService {

    private final LineSegmentService lineSegmentService;

    @Autowired
    public PolygonService(LineSegmentService lineSegmentService) {
        this.lineSegmentService = lineSegmentService;
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

    public void calculateSquares(Polygon polygon1, Polygon polygon2) {
        setClockwiseContourDirection(polygon1, polygon2);
        Polygon filledPolygon1 = createPolygonsWithIntersectionPoints(polygon1, polygon2);
        Polygon filledPolygon2 = createPolygonsWithIntersectionPoints(polygon2, polygon1);
        findIntersectionPolygons(filledPolygon1, filledPolygon2);
    }

    private void setClockwiseContourDirection(Polygon polygon1, Polygon polygon2) {
        if (getContourDirection(polygon1).equals(ContourDirection.counterClockwise)) {
            Collections.reverse(polygon1.getPoints());
        }
        if (getContourDirection(polygon2).equals(ContourDirection.counterClockwise)) {
            Collections.reverse(polygon2.getPoints());
        }
    }

    private double getPolygonAnglesSum(Polygon polygon) {
        List<Point> points = polygon.getPoints();
        double sumAngle = 0.0;
        double angle;
        for (int i = 0; i < points.size(); i++) {
            Point p1 = points.get(0);
            Point p2 = points.get(1);
            Point p3 = points.get(2);
            angle = lineSegmentService.getClockwiseAngle(p1, p2, p3);
            sumAngle += angle;
            setVertexAngle(p2, angle);
            Collections.rotate(points, -1);
        }
        return sumAngle;
    }

    private void setVertexAngle(Point point, double angle) {
        point.setAngle(angle);
    }

    private ContourDirection getContourDirection(Polygon polygon) {
        double actualPolygonAnglesSum = getPolygonAnglesSum(polygon);
        double expectedPolygonAnglesSum = pi * (polygon.getPoints().size() - 2);
        if (Math.abs(actualPolygonAnglesSum - expectedPolygonAnglesSum) < delta) {
            return ContourDirection.clockwise;
        } else {
            return ContourDirection.counterClockwise;
        }
    }

    private Polygon createPolygonsWithIntersectionPoints(Polygon polygon1, Polygon polygon2) {
        List<Point> points1 = polygon1.getPoints();
        List<Point> points2 = polygon2.getPoints();
        Polygon filledPolygon = new Polygon(points1);
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
            List<Point> sorted = sortIntersectionPointsByBasePoint(edgeIntersectionPoints, points1.get(i));
            filledPolygon.getPoints().addAll(filledPolygon.getPoints().indexOf(points1.get(i)) + 1, sorted);
        }
        return filledPolygon;
    }

    private List<Point> sortIntersectionPointsByBasePoint(List<Point> edgeIntersectionPoints, Point basePoint) {
        Map<Double, Point> distances = new HashMap<>();
        double distance;

        for (Point point : edgeIntersectionPoints) {
            distance = Math.sqrt(Math.pow(basePoint.x - point.x, 2) + Math.pow(basePoint.y - point.y, 2));
            distances.put(distance, point);
        }
        return distances.entrySet().stream()
                .sorted(Comparator.comparingDouble(Map.Entry::getKey))
                .map(Map.Entry::getValue)
                .toList();
    }

    private void findIntersectionPolygons(Polygon polygon1, Polygon polygon2) {
        Polygon intersectionPolygon;
        while (true) {
            intersectionPolygon = new Polygon();
            int i = nextIntersectionPointIndex(polygon1);
            if (i == -1) break;
            findNextPoint(polygon1, polygon2, 1, i, intersectionPolygon, polygon1.getPoints().get(i));
            Collection<Point> pointsToRemove = getIntersectionPointsFromIntersectionPolygon(intersectionPolygon);
            polygon1.getPoints().removeAll(pointsToRemove);
            polygon2.getPoints().removeAll(pointsToRemove);
            System.out.println(intersectionPolygon.getPoints());
        }
    }

    private void findNextPoint(Polygon first, Polygon second, int polygon, int index, Polygon intersectionPolygon,
                               Point finishPoint) {
        List<Point> points = (polygon == 1) ? first.getPoints() : second.getPoints();
        Point cp = points.get(index);
        intersectionPolygon.addPoint(cp);
        while (true) {
            index++;
            cp = points.get(index);
            if (cp.isIntersectionPoint()) {
                if (cp.equals(finishPoint)) {
                    break;
                }
                polygon = (polygon == 1) ? 2 : 1;
                points = (polygon == 1) ? first.getPoints() : second.getPoints();
                int i = points.indexOf(cp);
                findNextPoint(first, second, polygon, i, intersectionPolygon, finishPoint);
                break;
            } else {
                intersectionPolygon.addPoint(cp);
            }
        }
    }

    private int nextIntersectionPointIndex(Polygon polygon) {
        List<Point> points = polygon.getPoints();
        for (int i = 0; i < points.size(); i++) {
            if (points.get(i).isIntersectionPoint()) {
                return i;
            }
        }
        return -1;
    }

    private Collection<Point> getIntersectionPointsFromIntersectionPolygon(Polygon polygon) {
        List<Point> pointsToRemove = new ArrayList<>();
        for (Point point : polygon.getPoints()) {
            if (point.isIntersectionPoint()) {
                pointsToRemove.add(point);
            }
        }
        return pointsToRemove;
    }
}



















