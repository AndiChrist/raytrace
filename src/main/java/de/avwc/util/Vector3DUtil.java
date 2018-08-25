package de.avwc.util;

import org.hipparchus.geometry.euclidean.threed.Vector3D;

import javafx.scene.paint.Color;
import java.util.Set;
import java.util.TreeSet;

import static java.lang.Math.sqrt;

/**
 * Created by andichrist on 23.04.17.
 */
public class Vector3DUtil {

    static Vector3D add(Vector3D... vectors) {
        double x = 0.0;
        double y = 0.0;
        double z = 0.0;

        for (Vector3D vector : vectors) {
            x += vector.getX();
            y += vector.getY();
            z += vector.getZ();
        }

        return new Vector3D(x, y, z);
    }

    public static Vector3D move(Vector3D vector, Vector3D positionToLight, double ε) {
        // v = v + p⊗ε
        return vector.add(positionToLight.scalarMultiply(ε));
    }

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

    public static Vector3D multiply(Vector3D a, Vector3D b) {
        return new Vector3D(a.getX()*b.getX(), a.getY()*b.getY(), a.getZ()*b.getZ());
    }

    public static Vector3D multiply(Color a, Vector3D b) {
        return new Vector3D((a.getRed() / 255)*b.getX(), (a.getGreen() / 255)*b.getY(), (a.getBlue() / 255)*b.getZ());
    }

    public static Vector3D divide(Vector3D a, Vector3D b) {
        return new Vector3D(a.getX()/b.getX(), a.getY()/b.getY(), a.getZ()/b.getZ());
    }

    public static Vector3D divide(double a, Vector3D b) {
        return new Vector3D(a/b.getX(), a/b.getY(), a/b.getZ());
    }

    /*
    public static int toInteger(Vector3D vector) {
        return (int) (vector.getX()*255) << 16 | (int) (vector.getY()*255) << 8 | (int) (vector.getZ()*255);
    }
    */

    public static Vector3D inverse(Vector3D vector) {
        return divide(1.0, vector);
    }
}
