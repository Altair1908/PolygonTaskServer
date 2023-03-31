package com.rusty.polygontask.service;

import com.rusty.polygontask.model.Polygon;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class PolygonServiceTest {

    @Autowired
    private PolygonService polygonService;

    @ParameterizedTest
    @MethodSource("data")
    void test_Global(Polygon polygon1, Polygon polygon2, int count) {
        int polygons = polygonService.calculateSquares(polygon1, polygon2);
        assertEquals(count, polygons);
    }

    public static Stream<Arguments> data() {

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

        Polygon o1 = new Polygon();
        o1.addPoint(0.0, 0.0);
        o1.addPoint(2.0, 3.0);
        o1.addPoint(4.0, 0.0);
        Polygon p1 = new Polygon();
        p1.addPoint(1.0, 1.0);
        p1.addPoint(2.0, 3.0);
        p1.addPoint(3.0, 1.0);

        Polygon o2 = new Polygon();
        o2.addPoint(0.0, 0.0);
        o2.addPoint(2.0, 3.0);
        o2.addPoint(4.0, 0.0);
        Polygon p2 = new Polygon();
        p2.addPoint(2.0, 1.0);
        p2.addPoint(2.0, 3.0);
        p2.addPoint(4.0, 3.0);

        Polygon o3 = new Polygon();
        o3.addPoint(0.0, 0.0);
        o3.addPoint(0.0, 3.0);
        o3.addPoint(3.0, 3.0);
        o3.addPoint(3.0, 0.0);
        Polygon p3 = new Polygon();
        p3.addPoint(1.0, 1.0);
        p3.addPoint(1.0, 2.0);
        p3.addPoint(3.0, 2.0);

        Polygon o4 = new Polygon();
        o4.addPoint(0.0, 0.0);
        o4.addPoint(2.0, 3.0);
        o4.addPoint(4.0, 0.0);
        Polygon p4 = new Polygon();
        p4.addPoint(2.0, 3.0);
        p4.addPoint(2.0, 5.0);
        p4.addPoint(4.0, 5.0);

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

        return Stream.of(
            Arguments.of(e, b, 1),
            Arguments.of(r, h, 3),
            Arguments.of(n, m, 3),
            Arguments.of(o1, p1, 1),
            Arguments.of(o2, p2, 1),
            Arguments.of(o3, p3, 1),
            Arguments.of(o4, p4, 0)
        );
    }
}
