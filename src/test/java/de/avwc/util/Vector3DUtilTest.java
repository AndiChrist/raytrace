package de.avwc.util;

import org.apache.commons.math3.analysis.solvers.LaguerreSolver;
import org.apache.commons.math3.complex.Complex;
import org.junit.Test;

import java.util.Iterator;
import java.util.Set;

import static java.lang.Math.max;
import static java.lang.Math.min;
import static org.junit.Assert.assertEquals;

/**
 * Created by andichrist on 18.05.17.
 */
public class Vector3DUtilTest {

    @Test
    public void testQuadraticFormula1() {
        Set<Double> solutions = Vector3DUtil.quadraticFormula(4, -12, -40);
        Iterator iterator = solutions.iterator();

        assertEquals(solutions.size(), 2);

        double t0 = (double) iterator.next();
        double t1 = (double) iterator.next();

        double min = min(t0, t1);
        double max = max(t0, t1);

        double delta = 0.001;
        assertEquals(min, -2.0, delta);
        assertEquals(max, 5.0, delta);
    }

    @Test
    public void testQuadraticFormula2() {
        Set<Double> solutions = Vector3DUtil.quadraticFormula(1, 2, -35);
        Iterator iterator = solutions.iterator();

        assertEquals(solutions.size(), 2);

        double t0 = (double) iterator.next();
        double t1 = (double) iterator.next();

        double min = min(t0, t1);
        double max = max(t0, t1);

        double delta = 0.001;
        assertEquals(min, -7.0, delta);
        assertEquals(max, 5.0, delta);
    }

    @Test
    public void testQuadraticFormula3() {
        Set<Double> solutions = Vector3DUtil.quadraticFormula(1, -4, 4);
        Iterator iterator = solutions.iterator();

        assertEquals(solutions.size(), 1);

        double t0 = (double) iterator.next();

        double delta = 0.001;
        assertEquals(t0, 2.0, delta);
    }

    @Test
    public void testQuadraticFormula4() {
        Set<Double> solutions = Vector3DUtil.quadraticFormula(1, 12, 37);
        assertEquals(solutions.size(), 0);
    }

    @Test
    public void testMath3() {
        Complex expected0 = new Complex(-2, 0);
        Complex expected1 = new Complex(5, 0);

        double[] coefficients = {-40, -12, 4};
        LaguerreSolver solver = new LaguerreSolver();
        Complex[] result = solver.solveAllComplex(coefficients, 0);

        Complex actual0 = result[0];
        assertSame(expected0, actual0);

        Complex actual1 = result[1];
        assertSame(expected1, actual1);

    }

    @Test
    public void testMath3_2() {
        Complex expected0 = new Complex(5, 0);
        Complex expected1 = new Complex(-7, 0);

        double[] coefficients = {-35, 2, 1};
        LaguerreSolver solver = new LaguerreSolver();
        Complex[] result = solver.solveAllComplex(coefficients, 0);

        Complex actual0 = result[0];
        assertSame(expected0, actual0);

        Complex actual1 = result[1];
        assertSame(expected1, actual1);
    }

    @Test
    public void testMath3_3() {
        Complex expected = new Complex(2, 0);

        double[] coefficients = {4, -4, 1};
        LaguerreSolver solver = new LaguerreSolver();
        Complex[] result = solver.solveAllComplex(coefficients, 0);

        Complex actual0 = result[0];
        assertSame(expected, actual0);

        Complex actual1 = result[1];
        assertSame(expected, actual1);

        assertSame(actual0, actual1);
    }

    @Test
    public void testMath3_4() {
        Complex expected0 = new Complex(-6, -1);
        Complex expected1 = new Complex(-6, 1);

        double[] coefficients = {37, 12, 1};
        LaguerreSolver solver = new LaguerreSolver();
        Complex[] result = solver.solveAllComplex(coefficients, 0);

        Complex actual0 = result[0];
        assertSame(expected0, actual0);

        Complex actual1 = result[1];
        assertSame(expected1, actual1);
    }

    private static void assertSame(double expected, double actual) {
        assertEquals(expected, actual, 10e-12);
    }

    private static void assertSame(Complex expected, Complex actual) {
        assertSame(expected.getReal(), actual.getReal());
        assertSame(expected.getImaginary(), actual.getImaginary());
    }
}
