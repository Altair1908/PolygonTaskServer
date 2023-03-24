package com.rusty.polygontask.service;

import com.rusty.polygontask.model.Point;
import com.rusty.polygontask.model.Polygon;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CalculationService {

    private final PolygonService polygonService;

    @Autowired
    public CalculationService(PolygonService polygonService) {
        this.polygonService = polygonService;
    }

    public void calculate(Polygon polygon) {

        List<Point> points = polygon.getPoints();
        double component = 0.0;
        for (int i = 0; i < points.size(); i++) {
            int secondPointIndex = i == points.size() - 1 ? 0 : i + 1;
            component += points.get(i).getX() * points.get(secondPointIndex).getY() -
                    points.get(i).getY() * points.get(secondPointIndex).getX();
        }
        double s = 0.5 * Math.abs(component);

        double polygonSquare = polygonService.getPolygonSquare(polygon);
        System.out.println(polygonSquare);
        System.out.println(Double.compare(s, polygonSquare));
    }

    public void calculate2(Polygon polygon1, Polygon polygon2) {
        polygonService.doWork(polygon1, polygon2);
    }
}