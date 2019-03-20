package de.avwc.util;

import org.hipparchus.geometry.euclidean.threed.Vector3D;

import javafx.scene.paint.Color;

/**
 * Created by andichrist on 23.04.17.
 */
public class Vector3DUtil {

    public static Vector3D add(Vector3D... vectors) {
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

    public static Vector3D multiply(Color a, Vector3D b) {
        return new Vector3D(a.getRed()*b.getX(), a.getGreen()*b.getY(), a.getBlue()*b.getZ());
    }

    public static Vector3D divide(double a, Vector3D b) {
        return new Vector3D(a/b.getX(), a/b.getY(), a/b.getZ());
    }

    public static Vector3D inverse(Vector3D vector) {
        return divide(1.0, vector);
    }
}
