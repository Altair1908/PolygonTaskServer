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



        Polygon j = new Polygon();
        j.addPoint(0.0, 0.0);
        j.addPoint(0.0, 4.0);
        j.addPoint(4.0, 4.0);
        j.addPoint(4.0, 0.0);

        Polygon k_in = new Polygon();
        k_in.addPoint(1.5, 0.5);
        k_in.addPoint(0.0, 2.0);
        k_in.addPoint(1.5, 3.5);
        k_in.addPoint(3.0, 2.0);

        Polygon k_out = new Polygon();
        k_out.addPoint(5.5, 0.5);
        k_out.addPoint(4.0, 2.0);
        k_out.addPoint(5.5, 3.5);
        k_out.addPoint(7.0, 2.0);

        Polygon k_top_right = new Polygon();
        k_top_right.addPoint(5.5, 2.5);
        k_top_right.addPoint(4.0, 4.0);
        k_top_right.addPoint(5.5, 5.5);
        k_top_right.addPoint(7.0, 4.0);


//        System.out.println(polygonService.getPolygonSquare(j));
//        polygonService.calculateSquares(e, b);
//        polygonService.calculateSquares(r, h);
        polygonService.calculateSquares(n, m);
//        polygonService.calculateSquares(j, k_in);
//        polygonService.calculateSquares(j, k_out);
    }
}
