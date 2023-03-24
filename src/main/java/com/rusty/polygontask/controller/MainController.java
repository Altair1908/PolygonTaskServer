package com.rusty.polygontask.controller;

import com.rusty.polygontask.model.Polygon;
import com.rusty.polygontask.service.CalculationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MainController {

    private final CalculationService calculationService;

    @Autowired
    public MainController(CalculationService calculationService) {
        this.calculationService = calculationService;
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



        calculationService.calculate2(r,h);
    }
}
