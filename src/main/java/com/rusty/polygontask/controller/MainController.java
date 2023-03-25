package com.rusty.polygontask.controller;

import com.rusty.polygontask.model.Polygon;
import com.rusty.polygontask.service.PolygonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    private final PolygonService polygonService;

    @Autowired
    public MainController(PolygonService polygonService) {
        this.polygonService = polygonService;
    }

    @GetMapping("/test")
    public void triggerTest() {
        Polygon e = new Polygon();
        e.addPoint(0.0, 2.0);
        e.addPoint(2.0, 4.0);
        e.addPoint(4.0, 2.0);
        e.addPoint(2.0, 0.0);

        Polygon b = new Polygon();
        b.addPoint(2.0, 2.0);
        b.addPoint(4.0, 4.0);
        b.addPoint(6.0, 2.0);
        b.addPoint(4.0, 0.0);



        Polygon r = new Polygon();
        r.addPoint(0.0, 0.0);
        r.addPoint(1.0, 15.0);
        r.addPoint(10.0, 15.0);
        r.addPoint(5.0, 16.0);
        r.addPoint(11.0, 15.0);
        r.addPoint(4.0, 14.0);
        r.addPoint(2.5, 5.0);
        r.addPoint(3.0, 5.0);
        r.addPoint(3.0, 6.0);
        r.addPoint(4.0, 4.0);
        r.addPoint(2.0, 4.0);
        r.addPoint(2.0, 12.0);
        r.addPoint(1.0, 12.0);

        Polygon h = new Polygon();
        h.addPoint(0.0, 8.0);
        h.addPoint(0.0, 9.0);
        h.addPoint(4.0, 9.0);
        h.addPoint(2.0, 16.0);
        h.addPoint(9.0, 11.0);
        h.addPoint(4.0, 8.0);



        Polygon n = new Polygon();
        n.addPoint(0.0, 0.0);
        n.addPoint(2.0, 6.0);
        n.addPoint(3.0, 1.0);
        n.addPoint(4.0, 6.0);
        n.addPoint(5.0, 1.0);
        n.addPoint(6.0, 6.0);
        n.addPoint(7.0, 1.0);
        n.addPoint(7.0, 0.0);

        Polygon m = new Polygon();
        m.addPoint(7.0, 2.0);
        m.addPoint(7.0, 7.0);
        m.addPoint(2.0, 6.0);
        m.addPoint(2.0, 1.0);
        m.addPoint(4.0, 1.0);
        m.addPoint(4.0, 6.0);
        m.addPoint(6.0, 6.0);
        m.addPoint(6.0, 2.0);

        polygonService.createPolygonsWithIntersectionPoints(r, h);
    }
}
