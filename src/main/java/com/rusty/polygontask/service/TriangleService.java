package com.rusty.polygontask.service;

import com.rusty.polygontask.enumeration.ContourDirection;
import com.rusty.polygontask.model.Point;
import com.rusty.polygontask.enumeration.PointPosition;
import com.rusty.polygontask.model.Polygon;
import com.rusty.polygontask.model.Triangle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class TriangleService {

    private LineSegmentService lineSegmentService;
    private PolygonService polygonService;

    @Autowired
    public void setLineSegmentService(LineSegmentService lineSegmentService) {
        this.lineSegmentService = lineSegmentService;
    }

    @Autowired
    public void setPolygonService(PolygonService polygonService) {
        this.polygonService = polygonService;
    }

    public double getTriangleSquare(Triangle triangle) {
        Point a = triangle.getA();
        Point b = triangle.getB();
        Point c = triangle.getC();
        return 0.5 * Math.abs(((b.getX() - a.getX()) * (c.getY() - a.getY())) -
                ((c.getX() - a.getX()) * (b.getY() - a.getY())));
    }

    public boolean isFullFilling(Triangle triangle, Polygon polygon) {
        List<Point> trianglePoints = triangle.getPoints();
        List<Point> polygonPoints = polygon.getPoints();
        for (Point point : polygonPoints) {
            if (!trianglePoints.contains(point) && isPointIncluded(triangle, point)) {
                return false;
            }
        }
        return true;
    }

    private boolean isPointIncluded(Triangle triangle, Point point) {
        List<Point> trianglePoints = triangle.getPoints();
        Polygon trianglePolygon = new Polygon(trianglePoints);
        if (polygonService.getPolygonContourDirection(trianglePolygon).equals(ContourDirection.counterClockwise)) {
            Collections.reverse(trianglePoints);
        }
        PointPosition ab = lineSegmentService.getLineRelativePointPosition(triangle.getA(), triangle.getB(), point);
        PointPosition bc = lineSegmentService.getLineRelativePointPosition(triangle.getB(), triangle.getC(), point);
        PointPosition ca = lineSegmentService.getLineRelativePointPosition(triangle.getC(), triangle.getA(), point);
        return !ab.equals(PointPosition.leftSide) &&
                !bc.equals(PointPosition.leftSide) &&
                !ca.equals(PointPosition.leftSide);
    }
}
