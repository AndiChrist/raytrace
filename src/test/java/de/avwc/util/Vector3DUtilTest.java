package de.avwc.util;

import org.hipparchus.analysis.solvers.LaguerreSolver;
import org.hipparchus.complex.Complex;
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

    private static double Δ = 10e-12;   // DELTA

    @Test
    public void testQuadraticFormula1() {
        Set<Double> solutions = Vector3DUtil.quadraticFormula(4, -12, -40); // 4x² - 12x - 40
        //List<Double> sorted = solutions.stream().sorted().collect(Collectors.toList());
        assertEquals(solutions.size(), 2);

        Iterator iterator = solutions.iterator();
        double t0 = (double) iterator.next();
        double t1 = (double) iterator.next();

        double min = min(t0, t1);
        double max = max(t0, t1);

        assertEquals(min, -2.0, Δ);
        assertEquals(max, 5.0, Δ);
    }

    @Test
    public void testQuadraticFormula2() {
        Set<Double> solutions = Vector3DUtil.quadraticFormula(1, 2, -35); // x² + 2x - 35
        assertEquals(solutions.size(), 2);

        Iterator iterator = solutions.iterator();
        double t0 = (double) iterator.next();
        double t1 = (double) iterator.next();

        double min = min(t0, t1);
        double max = max(t0, t1);

        assertEquals(min, -7.0, Δ);
        assertEquals(max, 5.0, Δ);
    }

    @Test
    public void testQuadraticFormula3() {
        Set<Double> solutions = Vector3DUtil.quadraticFormula(1, -4, 4); // x² - 4x + 4
        assertEquals(solutions.size(), 1);

        Iterator iterator = solutions.iterator();
        double t0 = (double) iterator.next();

        assertEquals(t0, 2.0, Δ);
    }

    @Test
    public void testQuadraticFormula4() {
        Set<Double> solutions = Vector3DUtil.quadraticFormula(1, 12, 37); // x² + 12x + 37
        assertEquals(solutions.size(), 0);
    }

    @Test
    public void testQuadraticFormula5() {
        Set<Double> solutions = Vector3DUtil.quadraticFormula(1, 0, -100);  // x² - 100
        assertEquals(solutions.size(), 2);

        Iterator iterator = solutions.iterator();
        double t0 = (double) iterator.next();
        double t1 = (double) iterator.next();

        double min = min(t0, t1);
        double max = max(t0, t1);

        assertEquals(min, -10.0, Δ);
        assertEquals(max, 10.0, Δ);
    }

    @Test
    public void testQuadraticFormula6() {
        Complex expected1 = new Complex(-Math.sqrt(3d/25d), 0);
        Complex expected0 = new Complex(Math.sqrt(3d/25d), 0);

        double[] coefficients = {-3, 0, 25}; // 25x² - 3 = 0

        Set<Double> solutions = Vector3DUtil.quadraticFormula(coefficients);
        assertEquals(solutions.size(), 2);

        Iterator iterator = solutions.iterator();
        double t0 = (double) iterator.next();
        double t1 = (double) iterator.next();

        double min = min(t0, t1);
        double max = max(t0, t1);

        assertEquals(min, expected1.getReal(), Δ);
        assertEquals(max, expected0.getReal(), Δ);

        // LaguerreSolver
        LaguerreSolver solver = new LaguerreSolver();
        Complex[] result = solver.solveAllComplex(coefficients, 0);

        Complex actual0 = result[0];
        assertSame(expected0, actual0);

        Complex actual1 = result[1];
        assertSame(expected1, actual1);
    }

    @Test
    public void testMath3() {
        Complex expected0 = new Complex(-2, 0);
        Complex expected1 = new Complex(5, 0);

        double[] coefficients = {-40, -12, 4}; // 4x² - 12x - 40
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

        double[] coefficients = {-35, 2, 1}; // x² + 2x - 35
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

        double[] coefficients = {4, -4, 1}; // x² - 4x + 4
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

        double[] coefficients = {37, 12, 1}; // x² + 12x + 37
        LaguerreSolver solver = new LaguerreSolver();
        Complex[] result = solver.solveAllComplex(coefficients, 0);

        Complex actual0 = result[0];
        assertSame(expected0, actual0);

        Complex actual1 = result[1];
        assertSame(expected1, actual1);
    }

    private static void assertSame(double expected, double actual) {
        assertEquals(expected, actual, Δ);
    }

    private static void assertSame(Complex expected, Complex actual) {
        assertSame(expected.getReal(), actual.getReal());
        assertSame(expected.getImaginary(), actual.getImaginary());
    }
}
