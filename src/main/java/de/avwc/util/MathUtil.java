package de.avwc.util;

import java.util.Set;
import java.util.TreeSet;

import static java.lang.Math.sqrt;

public class MathUtil {
    // ƒ(x) = ax² + bx + c
    // x₁, x₂ = (-b ± √(b² - 4ac)) / 2a
    public static Set<Double> quadraticFormula(double a, double b, double c) {
        Set<Double> solutions = new TreeSet<>();
        if (a == 0) {
            // It's a degenerate quadratic.
            // We'll treat it as linear equation bx + c = 0
            return solveLinear(b, c);
        }

        // D = b² - 4ac
        double discriminant = b * b - 4 * a * c;

        // if discriminant is negative there are no real roots, so return
        // false as ray misses sphere
        if (discriminant < 0) {
            return solutions;
        }

        // = √(b² - 4ac)
        double distSqrt = sqrt(discriminant);

        double q;
        if (b < 0) {
            q = (-b - distSqrt) / 2;
        } else {
            q = (-b + distSqrt) / 2;
        }

        // possible solutions: t₀ and t₁
        double t0 = q / a;
        double t1 = c / q;

        if (Double.compare(t0, t1) == 0) {
            solutions.add(t0);
        } else {
            solutions.add(t0);
            solutions.add(t1);
        }

        return solutions;
    }

    public static Set<Double> quadraticFormula(double[] coefficients) {
        // coefficients in reverse order: c + bx + ax²
        return quadraticFormula(coefficients[2], coefficients[1], coefficients[0]);
    }

    private static Set<Double> solveLinear(double b, double c) {
        Set<Double> solutions = new TreeSet<>();

        if (b == 0) {
            // No solution (or possibly infinite solutions, if b == 0 too)
            return solutions;
        }

        solutions.add(-c / b);

        return solutions;
    }
}
